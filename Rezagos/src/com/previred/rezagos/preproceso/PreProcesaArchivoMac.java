package com.previred.rezagos.preproceso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.previred.rezagos.dao.DAOException;
import com.previred.utils.formato.Formatos;

public class PreProcesaArchivoMac {

	private static final String base = "archivos/mac201909.txt";

	private int agregaALista(String campo, HashMap<String, Integer> lista, boolean limpiar) {
		if (limpiar)
			campo = Formatos.limpiaCampo(campo);
		if (!lista.containsKey(campo)){
			int n = lista.entrySet().size();
			lista.put(campo, n);
			return n;
		}
		return lista.get(campo);
	}
	
	private int agregaALista(String campo, HashMap<String, Integer> lista) {
		return agregaALista(campo, lista, true);
	}

	private void escribeListaAArchivo(HashMap<String, Integer> lista, String nombre) throws DAOException {

 		try (BufferedWriter writer = new BufferedWriter(new FileWriter (nombre));){		    
 			
		    for (Entry<String, Integer> ape: lista.entrySet()) {
		        writer.write(ape.getKey());
		        writer.write('\t');
		        writer.write(String.valueOf(ape.getValue()));
		        writer.write('\n');
		    }
		    
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public void procesa() throws DAOException {
		long tiempo = System.currentTimeMillis();
		HashMap<String, Integer> listaRuts = new HashMap<String, Integer>();
		HashMap<String, Integer> listaApellidos = new HashMap<String, Integer>();
		HashMap<String, Integer> listaNombres = new HashMap<String, Integer>();
	
		int n = 0;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter (base+".salida.txt"));){		    

			try (BufferedReader br = new BufferedReader(new FileReader(base))) {
	
				String linea;
				linea = br.readLine();
				while (linea != null) {
					if (n==0) {
						n++;
						linea = br.readLine();
						continue;
					}
					
					if (n%500_000==0) {
						System.out.println("Procesados " + Formatos.obtieneNumero(n) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
						//break;
					}
	
					String campos[] = linea.split("\t");
	
					try {
						

						writer.write(campos[0]);
						writer.write('\t');
						
						int id = agregaALista(campos[1], listaRuts, false);
						writer.write(campos[1]);
						writer.write('\t');
						writer.write(String.valueOf(id));
						writer.write('\t');
						
						id = agregaALista(campos[3], listaNombres);
						writer.write(campos[3]);
						writer.write('\t');
						writer.write(String.valueOf(id));
						writer.write('\t');						
						
						id = agregaALista(campos[4], listaApellidos);
						writer.write(campos[4]);
						writer.write('\t');
						writer.write(String.valueOf(id));
						writer.write('\t');
						
						if(campos.length>5) {
							id = agregaALista(campos[5], listaApellidos);
							writer.write(campos[5]);
							writer.write('\t');
							writer.write(String.valueOf(id));
						}
						
						writer.write('\n');
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
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}
		
		System.out.println("Tiempo de procesamiento del MAC: " + Formatos.obtieneNumero(n) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
		
		tiempo = System.currentTimeMillis();
		
		escribeListaAArchivo(listaApellidos, base + ".apellidos.txt");
		escribeListaAArchivo(listaNombres, base + ".nombres.txt");
		escribeListaAArchivo(listaRuts, base + ".ruts.txt");

		System.out.println("Tiempo de creación de los archivos asociados al MAC: " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));

		System.out.println("Registros del MAC: " + Formatos.obtieneNumero(n));
		System.out.println("Apellidos distintos del MAC: " + Formatos.obtieneNumero(listaApellidos.size()));
		System.out.println("Nombres distintos del MAC: " + Formatos.obtieneNumero(listaNombres.size()));
		System.out.println("Ruts distintos del MAC: " + Formatos.obtieneNumero(listaRuts.size()));
		System.out.println("-----------------------------------------------------------");
	}
}
