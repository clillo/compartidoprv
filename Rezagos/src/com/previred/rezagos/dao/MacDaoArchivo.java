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

import com.previred.rezagos.dto.ElementoIdDTO;
import com.previred.rezagos.dto.MacDTO;
import com.previred.rezagos.dto.RezagosDTO;
import com.previred.utils.formato.Formatos;

public class MacDaoArchivo implements IMacDao {
	
	private static final String base = "archivos/mac201909.txt";

	@Override
	public List<MacDTO> obtieneTodosLosRegistros() throws DAOException {
		List<MacDTO> lista = new ArrayList<MacDTO>();
		String fileName = "C:\\Users\\clillo\\Documents\\Proyectos\\rezagos\\mac201909.csv";
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
				MacDTO to = new MacDTO();
				//System.out.println(linea);
				try {
					to.setRut(Integer.parseInt(campos[0]));
				
					to.setNombre(campos[3]);
					to.setApellidoPaterno(campos[4]);
					to.setApellidoMaterno(campos[5]);
					lista.add(to);
				} catch (NumberFormatException e) {
					System.err.println(linea);
				}
//				System.out.println(to);
				n++;
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

	private void imprimeEstadisticaRuts(HashMap<Integer, Integer> ocurrenciasRuts) {
		int afp1=0;
		int afp2=0;
		int afp3=0;
		int afp4=0;
		int afp5=0;
		int afp6=0;
		for (Integer ape: ocurrenciasRuts.keySet())
			switch (ocurrenciasRuts.get(ape)) {
			case 1:
				afp1++;
				break;
			case 2:
				afp2++;
				break;
			case 3:
				afp3++;
				break;
			case 4:
				afp4++;
				break;
			case 5:
				afp5++;
				break;
			case 6:
				afp6++;
				break;

			}

		System.out.println("Ruts del MAC en 1 AFP: " + Formatos.obtieneNumero(afp1));
		System.out.println("Ruts del MAC en 2 AFP: " + Formatos.obtieneNumero(afp2));
		System.out.println("Ruts del MAC en 3 AFP: " + Formatos.obtieneNumero(afp3));
		System.out.println("Ruts del MAC en 4 AFP: " + Formatos.obtieneNumero(afp4));
		System.out.println("Ruts del MAC en 5 AFP: " + Formatos.obtieneNumero(afp5));
		System.out.println("Ruts del MAC en 6 AFP: " + Formatos.obtieneNumero(afp6));

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
		
		long llave;
		
		int n = 0;
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
					rut = Integer.parseInt(campos[1]);
					afp = Long.valueOf(campos[0]);
					llave = afp + rut*10_000l;
					agregaALista(rut, listaRuts, ocurrenciasRuts, mapeoRuts, afp);
					agregaALista(campos[3], listaNombres, ocurrenciasNombres, mapeoNombres, llave);
					
					agregaALista(campos[4], listaApellidos, ocurrenciasApellidos, mapeoApellidos, llave+10_000_000_000_000l);
					if(campos.length>5) 
						agregaALista(campos[5], listaApellidos, ocurrenciasApellidos, mapeoApellidos, llave+ 20_000_000_000_000l);
					
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
			
		System.out.println("Tiempo de procesamiento del MAC: " + Formatos.obtieneNumero(n) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
		
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

		imprimeEstadisticaRuts(ocurrenciasRuts);
		
		System.out.println("Tiempo de creación de los archivos asociados al MAC: " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));

		System.out.println("Registros del MAC: " + Formatos.obtieneNumero(n));
		System.out.println("Apellidos distintos del MAC: " + Formatos.obtieneNumero(listaApellidos.size()));
		System.out.println("Nombres distintos del MAC: " + Formatos.obtieneNumero(listaNombres.size()));
		System.out.println("Ruts distintos del MAC: " + Formatos.obtieneNumero(listaRuts.size()));
		System.out.println("-----------------------------------------------------------");
	}
	
	public HashSet<MacDTO> obtieneDatosCompletos() throws DAOException {
		long tiempo = System.currentTimeMillis();

		HashSet<MacDTO> lista = new HashSet<>();
		
		int n=0;
		try (BufferedReader br = new BufferedReader(new FileReader(base+".salida.txt"))) {

			String linea;
			linea = br.readLine();
			while (linea != null) {
				if (n==0) {
					n++;
					linea = br.readLine();
					continue;
				}

				if (n%1_000_000==0) {
					System.out.println("Procesados " + Formatos.obtieneNumero(n) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
				
				}

				String campos[] = linea.split("\t");

				try {
					MacDTO to = new MacDTO();
					to.setIdProceso(n);
					to.setAfp(Integer.parseInt(campos[0]));					
					to.setRut(Integer.parseInt(campos[1]));
					to.setIdRut(Integer.parseInt(campos[2]));
					to.setNombre(campos[3]);
					to.setIdNombre(Integer.parseInt(campos[4]));
					to.setApellidoPaterno(campos[5]);
					to.setIdPaterno(Integer.parseInt(campos[6]));
					if(campos.length>7) {
						to.setApellidoMaterno(campos[7]);
						to.setIdMaterno(Integer.parseInt(campos[8]));
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
			
		System.out.println("Tiempo de procesamiento del archivo MAC: " + Formatos.obtieneNumero(n) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
		
		tiempo = System.currentTimeMillis();

		System.out.println("-----------------------------------------------------------");
		return lista;
	}


	@Override
	public List<ElementoIdDTO> obtieneTodosLosRuts() throws DAOException {
		List<ElementoIdDTO> lista = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(base + ".ruts.txt"))) {

			String linea;
			linea = br.readLine();
			while (linea != null) {
				ElementoIdDTO to = new ElementoIdDTO();
				String campos[] = linea.split("\t");
				to.setValor(campos[0]);
				to.setId(campos[1]);
				lista.add(to);
				linea = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);

		}	
		return lista;
	}

}
