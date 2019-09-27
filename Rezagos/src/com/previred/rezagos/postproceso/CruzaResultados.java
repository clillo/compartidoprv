package com.previred.rezagos.postproceso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.previred.rezagos.dao.DAOException;
import com.previred.rezagos.dao.IMacDao;
import com.previred.rezagos.dao.IRezagosDao;
import com.previred.rezagos.dao.MacDaoArchivo;
import com.previred.rezagos.dao.RezagosDaoArchivo;
import com.previred.rezagos.dto.MacDTO;
import com.previred.rezagos.dto.RezagosDTO;
import com.previred.utils.formato.Formatos;
import com.previred.utils.logger.Log4JTO;
import com.previred.utils.logger.PreviredLogger;

public class CruzaResultados {
	
	private static final String directorioBase = "hash/";
	
	private static PreviredLogger logger = new PreviredLogger();
	
	public CruzaResultados() {
        Log4JTO to = new Log4JTO();
        String directorioBase = "logs";
        
        to.setLogdir(directorioBase);
        to.setLogtype("DailyRolling"); 
        to.setMaxfilesize("100");
        to.setMaxbackupindex("100");
        to.setLevel("INFO");
        to.setIndentation("\t");
      
        PreviredLogger.init(to,  "cruzaresultados", "[%d{ISO8601}] %m%n", true, true);
	}
	
	private HashMap<Integer, String> leerRuts(int idArchivo) throws DAOException {
		HashMap<Integer, String> listaRuts = new HashMap<>();
		String base = directorioBase + "ruts/ruts." + idArchivo+ ".log";

		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(base))) {
				
				String linea;
				linea = br.readLine();
				while (linea != null) {				
					String campos[] = linea.split("\t");
	
					try {
						int idRezagos = Integer.parseInt(campos[0]);
						sb.append(campos[1]);
						sb.append('\t');
					//	sb.append(campos[2]);
					//	sb.append('\t');
						sb.append(campos[3]);
						sb.append('\t');
						//System.out.println(idRezagos);
						listaRuts.put(idRezagos, sb.toString());
						sb.delete(0, sb.length());
						
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println(linea+"["+e.getMessage()+"]");
					}
	
					linea = br.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new DAOException(e);
	
			}			
		return listaRuts;
	}
	
	private  void completaRezagos(int idArchivo) throws DAOException {
		long tiempo = System.currentTimeMillis();
		int n=0;
		String base = directorioBase + "rezagos/rezagos." + idArchivo+ ".log";
		
		HashMap<Integer, String> listaRuts = leerRuts(idArchivo);
		
		try (BufferedReader br = new BufferedReader(new FileReader(base))) {
			
			String linea;
			linea = br.readLine();
			while (linea != null) {			
				if (n%1000==0) {
					logger.info("Procesados " + Formatos.obtieneNumero(n) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
				}

				String campos[] = linea.split("\t");

				try {
					int idRezagos = Integer.parseInt(campos[0]);
					System.out.println(linea+"\t"+listaRuts.get(idRezagos));
					
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
	}

	public static void main(String[] args) throws DAOException {
		long tiempo = System.currentTimeMillis();
		
		System.out.println("Comienza a leer los registros de Rezagos");

		CruzaResultados cr = new CruzaResultados();
		cr.completaRezagos(0);
		
		System.out.println("Tiempo total del cruce: "+ Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
	}
}
