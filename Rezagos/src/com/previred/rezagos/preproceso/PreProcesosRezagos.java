package com.previred.rezagos.preproceso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.previred.rezagos.dao.DAOException;
import com.previred.rezagos.dto.ElementoIntListaDTO;
import com.previred.rezagos.indice.CoordinadorRezagosPorIndice;
import com.previred.utils.UtilesProcesos;
import com.previred.utils.formato.Formatos;
import com.previred.utils.logger.Log4JTO;
import com.previred.utils.logger.PreviredLogger;

public class PreProcesosRezagos {

	private static final String base = "archivos/rezagos.txt";
	
	private int agregaALista(String campo, HashMap<String, ElementoIntListaDTO> lista, boolean limpiar, String idRezago) {
		if (limpiar)
			campo = Formatos.limpiaCampo(campo);
		
		ElementoIntListaDTO elemento = lista.get(campo);
		if (elemento == null){
			int n = lista.entrySet().size();
			elemento = new ElementoIntListaDTO();
			elemento.setId(n);
			List<String> listaRezagos = new ArrayList<>();
			listaRezagos.add(idRezago);
			elemento.setValor(listaRezagos);
			lista.put(campo, elemento);
			return n;
		}
		
		List<String> listaRezagos = elemento.getValor();
		listaRezagos.add(idRezago);
		return elemento.getId();
	}
	
	private int agregaALista(String campo, HashMap<String, ElementoIntListaDTO> lista, String idRezago) {
		return agregaALista(campo, lista, true, idRezago);
	}
	
	private void escribeListaAArchivo(HashMap<String, ElementoIntListaDTO> lista, String nombre) throws DAOException {

 		try (BufferedWriter writer = new BufferedWriter(new FileWriter (nombre));){		    
 			
		    for (Entry<String, ElementoIntListaDTO> ape: lista.entrySet()) {
		        writer.write(ape.getKey());
		        writer.write('\t');
		        writer.write(String.valueOf(ape.getValue().getId()));
		        writer.write('\t');
		        for (String str: ape.getValue().getValor()) {
		        	writer.write(str);
		        	writer.write(',');
		        }
		        writer.write('\n');
		    }
		    
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
	
	public void procesa() throws DAOException {
		long tiempo = System.currentTimeMillis();

		HashMap<String, ElementoIntListaDTO> listaRuts = new HashMap<String, ElementoIntListaDTO>();
		HashMap<String, ElementoIntListaDTO> listaApellidos = new HashMap<String, ElementoIntListaDTO>();
		HashMap<String, ElementoIntListaDTO> listaNombres = new HashMap<String, ElementoIntListaDTO>();
		
		List<PreviredLogger> listaArchivosHash = new ArrayList<>();
		for (int i=0; i<CoordinadorRezagosPorIndice.NRO_HASH; i++) 		
			listaArchivosHash.add(obtieneArchivo(i));
		
		StringBuilder sb = new StringBuilder();
		int n=0;
		try (BufferedReader br = new BufferedReader(new FileReader(base))) {

			String linea;
			linea = br.readLine();
			while (linea != null) {
				if (n==0) {
					n++;
					linea = br.readLine();
					continue;
				}

				if (n%1_000_00==0)
					System.out.println("Procesados " + Formatos.obtieneNumero(n) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));

				String campos[] = linea.split("\t");

				try {
					int idRezago=Integer.parseInt(campos[0]);
					
					sb.append(campos[0]); // id
					sb.append('\t');
					sb.append(campos[1]); // afp
					sb.append('\t');

					int idRut = -1;
					int idNombre = -1;
					int idPaterno = -1;
					int idMaterno = -1;
					
					String strRut = "";
					String strNombre = "";
					String strPaterno = "";
					String strMaterno = "";
					
					strRut = campos[2];
					idRut = agregaALista(strRut, listaRuts, false, campos[0]);
					
					if(campos.length>5) { 
						strNombre = campos[4];
						idNombre=agregaALista(campos[4], listaNombres, campos[0]);

						strPaterno=campos[5];
						idPaterno=agregaALista(campos[5], listaApellidos, campos[0]);
					
						if(campos.length>6) {
							idMaterno=agregaALista(campos[6], listaApellidos, campos[0]);
							strMaterno=campos[6];
						}
						
						sb.append(String.valueOf(idRut));
						sb.append('\t');
						sb.append(String.valueOf(idNombre));
						sb.append('\t');
						sb.append(String.valueOf(idPaterno));
						sb.append('\t');
						sb.append(String.valueOf(idMaterno));
						sb.append('\t');
						sb.append(strRut);
//						sb.append('\t');
//						sb.append(strNombre);
//						sb.append('\t');
//						sb.append(strPaterno);
//						sb.append('\t');
//						sb.append(strMaterno);
//						sb.append('\t');

					}
					
					int hash = UtilesProcesos.hashRut(idRezago, CoordinadorRezagosPorIndice.NRO_HASH);
					PreviredLogger logger = listaArchivosHash.get(hash);
					logger.debug(sb.toString(), "rezagos."+hash);
					sb.delete(0, sb.length());
					
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(linea+"["+e.getMessage()+"]");
				}

				n++;
				linea = br.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}			

		System.out.println("Tiempo de procesamiento del archivo de Rezagos: " + Formatos.obtieneNumero(n) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
		
		tiempo = System.currentTimeMillis();
		
		escribeListaAArchivo(listaApellidos, base + ".apellidos.txt");
		escribeListaAArchivo(listaNombres, base + ".nombres.txt");
		escribeListaAArchivo(listaRuts, base + ".ruts.txt");

		System.out.println("Tiempo de creación de los archivos asociados al Rezagos: " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));

		System.out.println("Registros del Rezagos: " + Formatos.obtieneNumero(n));
		System.out.println("Apellidos distintos del Rezagos: " + Formatos.obtieneNumero(listaApellidos.size()));
		System.out.println("Nombres distintos del Rezagos: " + Formatos.obtieneNumero(listaNombres.size()));
		System.out.println("Ruts distintos del Rezagos: " + Formatos.obtieneNumero(listaRuts.size()));
		System.out.println("-----------------------------------------------------------");
	}
	
	private PreviredLogger obtieneArchivo(int hash) {
		PreviredLogger logger = new PreviredLogger();
		Log4JTO to = new Log4JTO();
		String directorioBase = "hash/rezagos";

		to.setLogdir(directorioBase);
		to.setLogtype("DailyRolling");
		to.setMaxfilesize("100");
		to.setMaxbackupindex("100");
		to.setLevel("DEBUG");
		to.setIndentation("\t");

		PreviredLogger.init(to, "rezagos."+hash, "%m%n", false, false);
		return logger;
	}
}
