package com.previred.rezagos.indice;

import java.io.IOException;
import java.util.List;

import com.previred.rezagos.dao.ArchivosLocalesDAO;
import com.previred.rezagos.dao.DAOException;
import com.previred.rezagos.dto.ElementoIdDTO;
import com.previred.rezagos.dto.ElementoStringListaDTO;
import com.previred.rezagos.dto.TiposElemento;
import com.previred.utils.formato.Formatos;
import com.previred.utils.logger.Log4JTO;
import com.previred.utils.logger.PreviredLogger;

public class ProcesaIndiceParalelo {
	
	private static PreviredLogger logger = new PreviredLogger();
	
	public ProcesaIndiceParalelo() {
        Log4JTO to = new Log4JTO();
        String directorioBase = "logs";
        
        to.setLogdir(directorioBase);
        to.setLogtype("DailyRolling"); 
        to.setMaxfilesize("100");
        to.setMaxbackupindex("100");
        to.setLevel("INFO");
        to.setIndentation("\t");
      
        PreviredLogger.init(to,  "procesaIndice", "[%d{ISO8601}] %m%n", true, true);
	}
	
	private void revisaSimilaridad(int nroThreads, long tiempo, TiposElemento tipo) throws DAOException, IOException {
		logger.info("Comienza a leer los "+tipo+" del MAC");
		ArchivosLocalesDAO daoArchivos = new ArchivosLocalesDAO();
		List<ElementoIdDTO> listaApellidosMac = daoArchivos.obtieneTodosLosApellidosMac(tipo);	
		logger.info(tipo+" del MAC " + Formatos.obtieneNumero(listaApellidosMac.size()) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
		logger.info("Comienza a leer los "+tipo+" de rezagos");
		List<ElementoStringListaDTO> listaRezagos  = daoArchivos.obtieneTodosLosApellidosRezagos(tipo);
		logger.info(tipo+" de Rezagos " + Formatos.obtieneNumero(listaRezagos.size()) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
		
		logger.info("Comienza a crear el indice para los "+tipo);
		Indice.crearIndice(listaApellidosMac);
		logger.info("Indice de "+tipo+" en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
		
		logger.info("Comienza a procesar en paralelo los "+tipo);
		EstadoProcesoIndice estado = new EstadoProcesoIndice();
		estado.setListaMac(listaApellidosMac);
		estado.setListaRezagos(listaRezagos);
		estado.setNumeroThreads(nroThreads);
		estado.setTipo(tipo);
		
		CoordinadorRezagosPorIndice procesador = new CoordinadorRezagosPorIndice(estado);
		procesador.iniciaProceso();
		//procesador.consolidaArchivos();
			
		logger.info("Cantidad de registros de rezagos " + Formatos.obtieneNumero(estado.getRegistrosTotales()));
		logger.info("Cantidad de potenciales match " + Formatos.obtieneNumero(estado.getMatchs()));
	}
	
	public static void log(String toLog) {
		logger.info(toLog);
	}
		
	public void procesa() throws DAOException, IOException {
		long tiempo = System.currentTimeMillis();
		int nroThreads = 8;
		
		logger.info("Numero de threads: "+nroThreads);
	//	revisaSimilaridad(nroThreads, tiempo, TiposElemento.apellidos);
	//	revisaSimilaridad(nroThreads, tiempo, TiposElemento.nombres);
		revisaSimilaridad(nroThreads, tiempo, TiposElemento.ruts);
						
		logger.info("Tiempo total del proceso: "+ Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
	}

	public static void main(String[] args) throws Exception {
		
		ProcesaIndiceParalelo proceso = new ProcesaIndiceParalelo();
		proceso.procesa();
	}
}
