package com.previred.rezagos.indice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.previred.rezagos.dto.ElementoStringListaDTO;
import com.previred.utils.formato.Formatos;
import com.previred.utils.logger.Log4JTO;
import com.previred.utils.logger.PreviredLogger;
import com.previred.utils.paralelismo.CoordinadorThreads;
import com.previred.utils.paralelismo.EstadoProcesoParalelo;
import com.previred.utils.paralelismo.ThreadProceso;

public class CoordinadorRezagosPorIndice extends CoordinadorThreads{

	public static final int NRO_HASH=80;
	
	private List<EstadoProcesoIndice> estados;
	private EstadoProcesoIndice estadoProcesoGeneral;
	private List<List<ElementoStringListaDTO>> lista;
	
	public List<List<ElementoStringListaDTO>> getLista() {
		return lista;
	}

	private List<PreviredLogger> listaArchivosHash;
	
	public CoordinadorRezagosPorIndice(EstadoProcesoIndice estadoProceso) {
		super(estadoProceso);
		estadoProcesoGeneral = estadoProceso;
		List<ElementoStringListaDTO> listaRezagos = estadoProcesoGeneral.getListaRezagos();
		
		estados = new ArrayList<>();
		
		lista = new ArrayList<>(); 
		
		for (int i=0; i<estadoProcesoGeneral.getNumeroThreads(); i++) {
			EstadoProcesoIndice ebp = new EstadoProcesoIndice(); 
			ebp.setIdThead(i);
			ebp.setTipo(estadoProcesoGeneral.getTipo());
			estados.add(ebp);
			lista.add(new ArrayList<>());
		}

		int n = 0;
		int indice = 0;
		int cuantosPorThead = (int)(listaRezagos.size()/estadoProceso.getNumeroThreads());

		for (ElementoStringListaDTO elemento: listaRezagos){
			if (n++>=cuantosPorThead) {
				n=0;
				indice++;
			}
				
			lista.get(indice).add(elemento);
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<estadoProcesoGeneral.getNumeroThreads(); i++)
			sb.append("\tThread-").append(i).append(" procesara ").append(Formatos.obtieneNumero(lista.get(i).size())).append(" registros de Rezagos\n");

		ProcesaIndiceParalelo.log(sb.toString());
		
		listaArchivosHash = new ArrayList<>();
		for (int i=0; i<NRO_HASH; i++) 		
			listaArchivosHash.add(obtieneArchivo(i));
	}
	
	private PreviredLogger obtieneArchivo(int hash) {
		PreviredLogger logger = new PreviredLogger();
		Log4JTO to = new Log4JTO();
		String directorioBase = "hash/" + estadoProcesoGeneral.getTipo();

		to.setLogdir(directorioBase);
		to.setLogtype("DailyRolling");
		to.setMaxfilesize("100");
		to.setMaxbackupindex("100");
		to.setLevel("DEBUG");
		to.setIndentation("\t");

		PreviredLogger.init(to, estadoProcesoGeneral.getTipo()+"."+hash, "%m%n", false, false);

		return logger;
	}
		
	public EstadoProcesoIndice getEstadoProcesoGeneral() {
		return estadoProcesoGeneral;
	}

	public void consolidaArchivos() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(estadoProcesoGeneral.obtieneDirectorioBase()+"consolidado.txt", true));){    
			 
			try {
				for (EstadoProcesoIndice estado: estados) {
					//System.out.println("leyendo archivo: "+estado.obtieneNombreArchivoSalida());
					try (BufferedReader br = new BufferedReader(new FileReader(estado.obtieneNombreArchivoSalida()))) {
		
						String linea;
						linea = br.readLine();
						while (linea != null) {
							writer.write(linea);
							linea = br.readLine();
						}
					}
					//System.out.println("borrando archivo: "+estado.obtieneNombreArchivoSalida());
				//	new File(estado.obtieneNombreArchivoSalida()).delete();
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();	
		}	
	}

	@Override
	public EstadoProcesoParalelo obtieneInstanciaEstado(int idThread) {
		return estados.get(idThread);
	}

	@Override
	public ThreadProceso obtieneInstanciaProceso(CoordinadorThreads coordinador, int threadId,	EstadoProcesoParalelo estadoProceso) {
		ProcesoRezagosParaleloIndice p = new ProcesoRezagosParaleloIndice(coordinador, threadId, estadoProceso);
		p.setListaArchivosHash(listaArchivosHash);
		return p;
	}

	@Override
	public void informaEstadosFinales(int threadId, EstadoProcesoParalelo estadoEspecifico) {
		EstadoProcesoIndice ebp = (EstadoProcesoIndice)estadoEspecifico;
		estadoProcesoGeneral.consolidaResultados(ebp);
	}

	@Override
	public void iniciaEstadosEspecificos(int threadId, EstadoProcesoParalelo estadoEspecifico) {		
	}

}
