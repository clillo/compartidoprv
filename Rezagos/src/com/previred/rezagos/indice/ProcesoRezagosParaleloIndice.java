package com.previred.rezagos.indice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.previred.rezagos.dto.ElementoStringListaDTO;
import com.previred.rezagos.dto.TiposElemento;
import com.previred.utils.UtilesProcesos;
import com.previred.utils.formato.Formatos;
import com.previred.utils.logger.PreviredLogger;
import com.previred.utils.paralelismo.CoordinadorThreads;
import com.previred.utils.paralelismo.EstadoProcesoParalelo;
import com.previred.utils.paralelismo.ThreadProceso;

public class ProcesoRezagosParaleloIndice extends ThreadProceso{

	private EstadoProcesoIndice estadoRezagos;
	private CoordinadorRezagosPorIndice coordinador;
	private List<PreviredLogger> listaArchivosHash;
		
	public ProcesoRezagosParaleloIndice(CoordinadorThreads coordinador, int threadId, EstadoProcesoParalelo estadoProceso) {
		super(coordinador, threadId, estadoProceso);
		this.coordinador = (CoordinadorRezagosPorIndice)coordinador;
		this.estadoRezagos = (EstadoProcesoIndice)estadoProceso;
	}
	
	private void escribeEnArchivo(StringBuilder sb) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(estadoRezagos.obtieneNombreArchivoSalida(), true));){    
			 writer.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();	
		}	
		
	}
	
	private int calculaLevenshtein(String mac, String rezagos) {
		int s = LevenshteinDistance.getDefaultInstance().apply(rezagos, mac);
		double largoMaximo = Math.max(rezagos.length(), mac.length())*1.0;
		
		return (int)((1.0 - s/largoMaximo) * 100.0);
	}


	@Override
	protected void procesa(int threadId, EstadoProcesoParalelo estadoProceso) {
		long tiempo = System.currentTimeMillis();
		
		new File(estadoRezagos.obtieneNombreArchivoSalida()).delete();
		
		List<ElementoStringListaDTO> listaRezagos = coordinador.getLista().get(threadId);
		long ultimoTiempo = System.currentTimeMillis();
		
		long n=0;
		long nSobre65=0;
		int totalRezagos = listaRezagos.size();
		int restantesRezagos= totalRezagos;
		
		StringBuilder sb = new StringBuilder();

		IndexSearcher idxSearcher = Indice.obtenerBuscadorIndice();
		StringBuilder sbOriginales = new StringBuilder();
		
		for(ElementoStringListaDTO r: listaRezagos){
			FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("valor", r.getValor()));
			
			try {
				TopDocs docs = idxSearcher.search(fuzzyQuery, 20);
				for (ScoreDoc doc : docs.scoreDocs) {
				    Document thisDoc = idxSearcher.doc(doc.doc);
					int porcentaje = calculaLevenshtein(thisDoc.get("valor"), r.getValor());
				    
					if (porcentaje>=65){
						sb.append(r.getId());							
						sb.append('\t');
						sb.append(thisDoc.get("id"));
						sb.append('\t');
						sb.append(String.valueOf(porcentaje));
						
//						sb.append('\t');
//						sb.append(r.getValor());							
//						sb.append('\t');
//						sb.append(thisDoc.get("valor"));
						sb.append('\n');
						nSobre65++;
						
						
						List<Integer> listaOriginales = r.getLista();
						for(Integer i:listaOriginales) {
							sbOriginales.delete(0, sbOriginales.length());
							sbOriginales.append(String.valueOf(i));							
							sbOriginales.append('\t');
							if(coordinador.getEstadoProcesoGeneral().getTipo() != TiposElemento.ruts) {
								sbOriginales.append(r.getId());							
								sbOriginales.append('\t');
								sbOriginales.append(thisDoc.get("id"));
							}else {
								sbOriginales.append(r.getValor());							
								sbOriginales.append('\t');
								sbOriginales.append(thisDoc.get("valor"));
							}
							
							sbOriginales.append('\t');
							sbOriginales.append(String.valueOf(porcentaje));
							int hash = UtilesProcesos.hashRut(i, CoordinadorRezagosPorIndice.NRO_HASH);
							PreviredLogger logger = listaArchivosHash.get(hash);
							logger.debug(sbOriginales.toString(), coordinador.getEstadoProcesoGeneral().getTipo()+"."+hash);
						}
						
					}else
						break;
						
					n++;
				}

				if ((System.currentTimeMillis() - ultimoTiempo)>10_000) {
					escribeResultados(threadId, sb, n, tiempo, restantesRezagos, totalRezagos, nSobre65); 
					ultimoTiempo = System.currentTimeMillis();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			restantesRezagos--;
		}
		
		escribeResultados(threadId, sb, n, tiempo, restantesRezagos, totalRezagos, nSobre65);
		estadoRezagos.setMatchs(estadoRezagos.getMatchs() + nSobre65);
		estadoRezagos.setRegistrosTotales(estadoRezagos.getRegistrosTotales()+ totalRezagos);
	}
	
	private void escribeResultados(int threadId, StringBuilder sb, long n, long tiempo, int restantesMac, int totalMac, long nSobre65) {
		escribeEnArchivo(sb);
		sb.delete(0, sb.length());
		StringBuilder sbLog = new StringBuilder();
		sbLog.append("\t El thread ").append(threadId);
		sbLog.append(" ha procesado ").append(Formatos.obtieneNumero(n));
		sbLog.append(" registros en ").append(Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
		sbLog.append(" Porcentaje de avance Rezagos ").append(Formatos.obtieneNumero(100 - (restantesMac/(totalMac*1.0)*100)));
		sbLog.append(" Faltan ").append(Formatos.obtieneNumero(restantesMac));
		sbLog.append(" registros del Rezagos por revisar. Se han encontrado ").append(Formatos.obtieneNumero(nSobre65));
		sbLog.append(" match sobre 65% ");
		ProcesaIndiceParalelo.log(sbLog.toString());
	}

	public void setListaArchivosHash(List<PreviredLogger> listaArchivosHash) {
		this.listaArchivosHash = listaArchivosHash;
	}
}
