package com.previred.rezagos.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.previred.rezagos.dto.ElementoIdDTO;
import com.previred.rezagos.dto.ElementoStringListaDTO;
import com.previred.rezagos.dto.TiposElemento;

public class ArchivosLocalesDAO {
	
	private static final String base = "archivos/";
	private static final String baseMac = base+"mac201909.txt";
	private static final String baseRezagos = base+"rezagos.txt";
	
	public List<ElementoIdDTO> obtieneTodosLosApellidosMac(TiposElemento tipo) throws DAOException{
		return obtieneTodosLosApellidos(baseMac + tipo.getArchivo());
	}

	public List<ElementoStringListaDTO> obtieneTodosLosApellidosRezagos(TiposElemento tipo) throws DAOException{
		return obtieneTodosLosApellidosRezagos(baseRezagos + tipo.getArchivo());
	}

	private List<ElementoIdDTO> obtieneTodosLosApellidos(String nombreArchivo) throws DAOException {
		List<ElementoIdDTO> lista = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {

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

	private List<ElementoStringListaDTO> obtieneTodosLosApellidosRezagos(String nombreArchivo) throws DAOException {
		List<ElementoStringListaDTO> lista = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {

			String linea;
			linea = br.readLine();
			while (linea != null) {
				ElementoStringListaDTO to = new ElementoStringListaDTO();
				String campos[] = linea.split("\t");
				to.setValor(campos[0]);
				to.setId(campos[1]);
				List<Integer> listaRezagos = new ArrayList<>(); 
				to.setLista(listaRezagos);
				lista.add(to);
				
				String rezagos[] = campos[2].split(",");
				for (String str: rezagos)
					listaRezagos.add(Integer.parseInt(str));
				
				linea = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}	
		return lista;
	}
}
