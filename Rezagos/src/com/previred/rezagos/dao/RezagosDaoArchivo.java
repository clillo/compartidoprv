 package com.previred.rezagos.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.previred.rezagos.dto.RezagosDTO;
import com.previred.utils.formato.Formatos;

public class RezagosDaoArchivo implements IRezagosDao{
	
	private static final String base = "archivos/rezagos.txt";
    
	@Override
	public List<RezagosDTO> obtieneTodosLosRegistros() throws DAOException {
		List<RezagosDTO> lista = new ArrayList<RezagosDTO>();
		String fileName = "C:\\Users\\clillo\\Documents\\Proyectos\\rezagos\\rezagos.csv";
		int n=0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String linea;
			linea = br.readLine();
			while (linea != null) {
				if (n==0) {
					n++;
					linea = br.readLine();
					continue;
				}
				String campos[] = linea.split(";");
				RezagosDTO to = new RezagosDTO();
				//System.out.println(linea);
				try {
					to.setIdRezago(Integer.parseInt(campos[0]));
					to.setRut(Integer.parseInt(campos[2]));
					
//					if (campos[3].startsWith("\""))
//						to.setNombre(campos[3].substring(1));
//					else
//						to.setNombre(campos[3]);
//					
//					if (campos[3].endsWith("\""))
//						to.setNombre(to.getNombre().substring(0, campos[3].length()-2).trim());
//					else
//						to.setNombre(to.getNombre().trim());
//
//					if (campos[4].startsWith("\""))
//						to.setApellidoPaterno(campos[4].substring(1));
//					else
//						to.setApellidoPaterno(campos[4]);
//					
//					if (campos[4].endsWith("\""))
//						to.setApellidoPaterno(to.getApellidoPaterno().substring(0, campos[4].length()-2).trim());
//					else
//						to.setApellidoPaterno(to.getApellidoPaterno().trim());
//
//					if (campos[5].startsWith("\""))
//						to.setApellidoMaterno(campos[5].substring(1));
//					else
//						to.setApellidoMaterno(campos[5]);
//					if (campos[5].endsWith("\""))
//						to.setApellidoMaterno(to.getApellidoMaterno().substring(0, campos[5].length()-2).trim());
//					else
//						to.setApellidoMaterno(to.getApellidoMaterno().trim());

					lista.add(to);
				} catch (Exception e) {
					System.err.println(linea);
					System.err.println(n);
					System.exit(0);
				}
				n++;
			//	System.out.println(to);
				linea = br.readLine();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
		
		System.out.println(n);

		return lista;
	}
	
	private void agregaALista(String campo, HashSet<String> lista, HashMap<String, Integer> ocurrencias, HashMap<String, ArrayList<Long>> mapeo, long llave) {
		campo = Formatos.limpiaCampo(campo);
		if (campo.length()==0)
			return;
		
		if (!lista.contains(campo)){
			lista.add(campo);
			ocurrencias.put(campo, 1);
			ArrayList<Long> listaDatos = new ArrayList<>();
			listaDatos.add(llave);
			mapeo.put(campo, listaDatos);
		}else {
			int anterior = ocurrencias.get(campo);
			ocurrencias.put(campo, anterior + 1);
			ArrayList<Long> listaDatos = mapeo.get(campo);
			listaDatos.add(llave);
		}
	}

	private void agregaALista(int campo, HashSet<Integer> lista, HashMap<Integer, Integer> ocurrencias, HashMap<Integer, ArrayList<Long>> mapeo, long afp) {
		
		if (!lista.contains(campo)){
			lista.add(campo);
			ocurrencias.put(campo, 1);
			ArrayList<Long> listaDatos = new ArrayList<>();
			listaDatos.add(afp);
			mapeo.put(campo, listaDatos);
		}else {
			int anterior = ocurrencias.get(campo);
			ocurrencias.put(campo, anterior + 1);
			ArrayList<Long> listaDatos = mapeo.get(campo);
			listaDatos.add(afp);
		}
	}
	
	private void escribeListaAArchivo(HashSet<String> lista, String nombre) throws DAOException {
		List<String> listaOrdenada = new ArrayList<>();
		
		for (String ape: lista)
			listaOrdenada.add(ape);

		Collections.sort(listaOrdenada);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter (nombre));){
		    
		    for (String ape: listaOrdenada) {
		        writer.write(ape);
		        writer.write('\n');
		    }

		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}	
	}

	private void escribeListaAArchivoNumero(HashSet<Integer> lista, String nombre) throws DAOException {
		List<Integer> listaOrdenada = new ArrayList<>();
		
		for (Integer ape: lista)
			listaOrdenada.add(ape);

		Collections.sort(listaOrdenada);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter (nombre));){
		    
		    for (Integer ape: listaOrdenada) {
		        writer.write(String.valueOf(ape));
		        writer.write('\n');
		    }

		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}	
	}
	
	private void escribeListaAArchivo(HashMap<String, Integer> ocurrencias, String nombre) throws DAOException {
		List<String> lista = new ArrayList<>();
		
		for (String ape: ocurrencias.keySet())
			lista.add(ape);
		
		Collections.sort(lista);
		
 		try (BufferedWriter writer = new BufferedWriter(new FileWriter (nombre));){		    
			for (String ape: lista) {
		        writer.write(ape);
		        writer.write('\t');
		        writer.write(ocurrencias.get(ape)+"");
		        writer.write('\n');
		    }

		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}	
	}

	private void escribeListaAArchivo(HashMap<String, Integer> ocurrencias, HashMap<String, ArrayList<Long>> mapeo, String nombre) throws DAOException {
		List<String> lista = new ArrayList<>();
		
		for (String ape: ocurrencias.keySet())
			lista.add(ape);
		
		Collections.sort(lista);
		
 		try (BufferedWriter writer = new BufferedWriter(new FileWriter (nombre));){		    
			for (String ape: lista) {
		        writer.write(ape);
		        writer.write('\t');
		        writer.write(ocurrencias.get(ape)+"");
		        writer.write('\t');
		        ArrayList<Long> listaDatos = mapeo.get(ape);
		        for (Long l: listaDatos) {
			        writer.write(String.valueOf(l));
			        writer.write(',');
		        }
		        	
		        writer.write('\n');
		    }

		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}	
	}
	
	private void escribeListaAArchivoNumero(HashMap<Integer, Integer> ocurrencias, String nombre) throws DAOException {
		List<Integer> lista = new ArrayList<>();
		
		for (int ape: ocurrencias.keySet())
			lista.add(ape);
		
		Collections.sort(lista);
		
 		try (BufferedWriter writer = new BufferedWriter(new FileWriter (nombre));){		    
			for (int ape: lista) {
		        writer.write(String.valueOf(ape));
		        writer.write('\t');
		        writer.write(ocurrencias.get(ape)+"");
		        writer.write('\n');
		    }

		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}	
	}

	private void escribeListaAArchivoNumero(HashMap<Integer, Integer> ocurrencias, HashMap<Integer, ArrayList<Long>> mapeo, String nombre) throws DAOException {
		List<Integer> lista = new ArrayList<>();
		
		for (Integer ape: ocurrencias.keySet())
			lista.add(ape);
		
		Collections.sort(lista);
		
 		try (BufferedWriter writer = new BufferedWriter(new FileWriter (nombre));){		    
			for (Integer ape: lista) {
				writer.write(String.valueOf(ape));
		        writer.write('\t');
		        writer.write(ocurrencias.get(ape)+"");
		        writer.write('\t');
		        ArrayList<Long> listaDatos = mapeo.get(ape);
		        for (Long l: listaDatos) {
			        writer.write(String.valueOf(l));
			        writer.write(',');
		        }
		        	
		        writer.write('\n');
		    }

		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}	
	}


	@Override
	public void obtieneTodosLosDatosSeparados() throws DAOException {
		long tiempo = System.currentTimeMillis();

		HashSet<Integer> listaRuts = new HashSet<Integer>();
		HashSet<String> listaApellidos = new HashSet<String>();
		HashSet<String> listaNombres = new HashSet<String>();
		HashMap<Integer, ArrayList<Long>> mapeoRuts = new HashMap<>();
		HashMap<String, ArrayList<Long>> mapeoApellidos = new HashMap<>();
		HashMap<String, ArrayList<Long>> mapeoNombres = new HashMap<>();
		HashMap<Integer, Integer> ocurrenciasRuts = new HashMap<>();
		HashMap<String, Integer> ocurrenciasApellidos = new HashMap<>();
		HashMap<String, Integer> ocurrenciasNombres = new HashMap<>();
		
		int n=0;
		
		long llave;

		int rut = 0;
		long afp = 0;
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
					rut = Integer.parseInt(campos[2]);
					afp = Long.valueOf(campos[1]);
					llave = Long.valueOf(campos[0]);
					if(campos.length>5) { 
						agregaALista(rut, listaRuts, ocurrenciasRuts, mapeoRuts, llave);
						agregaALista(campos[4], listaNombres, ocurrenciasNombres, mapeoNombres, llave);
						agregaALista(campos[5], listaApellidos, ocurrenciasApellidos, mapeoApellidos, llave+10_000_000_000_000l);
					
						if(campos.length>6) 
							agregaALista(campos[6], listaApellidos, ocurrenciasApellidos, mapeoApellidos, llave+20_000_000_000_000l);

					}
					
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
		escribeListaAArchivoNumero(listaRuts, base + ".ruts.txt");

		escribeListaAArchivo(ocurrenciasApellidos, base + ".apellidos.ocurrencias.txt");
		escribeListaAArchivo(ocurrenciasNombres, base + ".nombres.ocurrencias.txt");
		escribeListaAArchivoNumero(ocurrenciasRuts, base + ".ruts.ocurrencias.txt");

		escribeListaAArchivo(ocurrenciasApellidos, mapeoApellidos, base + ".apellidos.datos.txt");
		escribeListaAArchivo(ocurrenciasNombres, mapeoNombres, base + ".nombres.datos.txt");
		escribeListaAArchivoNumero(ocurrenciasRuts, mapeoRuts, base + ".ruts.datos.txt");

		
		System.out.println("Tiempo de creación de los archivos asociados al Rezagos: " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));

		System.out.println("Registros del Rezagos: " + Formatos.obtieneNumero(n));
		System.out.println("Apellidos distintos del Rezagos: " + Formatos.obtieneNumero(listaApellidos.size()));
		System.out.println("Nombres distintos del Rezagos: " + Formatos.obtieneNumero(listaNombres.size()));
		System.out.println("Ruts distintos del Rezagos: " + Formatos.obtieneNumero(listaRuts.size()));
		System.out.println("-----------------------------------------------------------");
	}

	public HashSet<RezagosDTO> obtieneDatosCompletos() throws DAOException {
		long tiempo = System.currentTimeMillis();

		HashSet<RezagosDTO> lista = new HashSet<RezagosDTO>();
		
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

				String campos[] = linea.split("\t");

				try {
					RezagosDTO to = new RezagosDTO();
					to.setIdProceso(n);
					to.setRut(Integer.parseInt(campos[2]));
					to.setAfp(Integer.parseInt(campos[1]));
					to.setIdRezago(Long.valueOf(campos[0]));
					if(campos.length>5) { 
						to.setNombre(campos[4]);
						to.setApellidoPaterno(campos[5]);
					
						if(campos.length>6) 
							to.setApellidoMaterno(campos[6]);
					}
					lista.add(to);
				} catch (Exception e) {
				//	e.printStackTrace();
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

		System.out.println("-----------------------------------------------------------");
		return lista;
	}
	
	@Override
	public List<String> obtieneTodosLosApellidos() throws DAOException {
		List<String> lista = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(base + ".apellidos.txt"))) {

			String linea;
			linea = br.readLine();
			while (linea != null) {
				lista.add(linea);
				linea = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}	
		return lista;
	}

	@Override
	public List<String> obtieneTodosLosRuts() throws DAOException {
		List<String> lista = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(base + ".ruts.txt"))) {

			String linea;
			linea = br.readLine();
			while (linea != null) {
				lista.add(linea);
				linea = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}	
		return lista;
	}

}
