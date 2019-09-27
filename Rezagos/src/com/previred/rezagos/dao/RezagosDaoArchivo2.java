package com.previred.rezagos.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import com.previred.rezagos.dto.MacDTO;
import com.previred.rezagos.dto.RezagosDTO;
import com.previred.utils.formato.Formatos;

public class RezagosDaoArchivo2 implements IRezagosDao{
    
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
	
	private void agregaApellido(String apellido, HashSet<String> listaApellidos, HashMap<String, Integer> ocurrencias) {
		apellido = apellido.toLowerCase();
		if (!listaApellidos.contains(apellido)){
			listaApellidos.add(apellido);
			ocurrencias.put(apellido, 1);
		}else {
			int anterior = ocurrencias.get(apellido);
			ocurrencias.put(apellido, anterior + 1);
		}
	}
	
	@Override
	public void obtieneTodosLosDatosSeparados() throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> obtieneTodosLosApellidos() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> obtieneTodosLosRuts() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<RezagosDTO> obtieneDatosCompletos() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
