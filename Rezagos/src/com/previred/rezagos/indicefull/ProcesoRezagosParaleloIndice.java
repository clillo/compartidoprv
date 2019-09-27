package com.previred.rezagos.indicefull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.previred.rezagos.indice.CoordinadorRezagosPorIndice;
import com.previred.rezagos.indice.EstadoProcesoIndice;
import com.previred.utils.formato.Formatos;
import com.previred.utils.paralelismo.CoordinadorThreads;
import com.previred.utils.paralelismo.EstadoProcesoParalelo;
import com.previred.utils.paralelismo.ThreadProceso;

public class ProcesoRezagosParaleloIndice extends ThreadProceso{

	private static final String DIRECTORIO = "index";
	private EstadoProcesoIndice estadoRezagos;
	private CoordinadorRezagosPorIndice coordinador;
		
	public ProcesoRezagosParaleloIndice() {
		
	}

	public void crearIndice(List<String> listaApellidosMac) throws Exception {
		Directory dir = FSDirectory.open(Paths.get(DIRECTORIO));
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(OpenMode.CREATE);

		IndexWriter writer = new IndexWriter(dir, iwc);
		
		int n=0;
		for(String str: listaApellidosMac) {
			Document doc = new Document();
			StringField s1 = new StringField("apellido", str, Field.Store.YES); 
			doc.add(s1);
			StringField s2 = new StringField("id", String.valueOf(n), Field.Store.YES); 
			doc.add(s2);
			writer.addDocument(doc);
			n++;
		}
		
		writer.close();
	}

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
		
		List<String> listaRezagos = null;// coordinador.getLista().get(threadId);
		long ultimoTiempo = System.currentTimeMillis();
		
		int idRezagos = 0;
		long n=0;
		long nSobre65=0;
		int totalRezagos = listaRezagos.size();
		int restantesRezagos= totalRezagos;
		
		StringBuilder sb = new StringBuilder();

		IndexSearcher idxSearcher = null;
		try {
			Directory dir = FSDirectory.open(Paths.get(DIRECTORIO));
			IndexReader reader = DirectoryReader.open(dir);
			idxSearcher = new IndexSearcher(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		idRezagos = 0;
		for(String r: listaRezagos){
			FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("apellido", r));
			
			try {
				TopDocs docs = idxSearcher.search(fuzzyQuery, 5);
				for (ScoreDoc doc : docs.scoreDocs) {
				    Document thisDoc = idxSearcher.doc(doc.doc);
					int porcentaje = calculaLevenshtein(thisDoc.get("apellido"), r);
				    
					if (porcentaje>65){
							//sb.append(r);
							sb.append(thisDoc.get("id"));
							sb.append('\t');
							sb.append(String.valueOf(idRezagos));
							//sb.append(thisDoc.get("apellido"));
							sb.append('\t');
							sb.append(String.valueOf(porcentaje));			 
							sb.append('\n');
							nSobre65++;
						}
						
					n++;
				}

				if ((System.currentTimeMillis() - ultimoTiempo)>10_000) {
					escribeResultados(threadId, sb, n, tiempo, restantesRezagos, totalRezagos, nSobre65); 
					ultimoTiempo = System.currentTimeMillis();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			idRezagos++;
			restantesRezagos--;
		}
		
		escribeResultados(threadId, sb, n, tiempo, restantesRezagos, totalRezagos, nSobre65);
		estadoRezagos.setMatchs(estadoRezagos.getMatchs() + nSobre65);
		estadoRezagos.setRegistrosTotales(estadoRezagos.getRegistrosTotales()+ idRezagos);
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
		System.out.println(sbLog.toString());
	}
}
