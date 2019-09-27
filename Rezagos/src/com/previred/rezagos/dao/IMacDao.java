package com.previred.rezagos.dao;

import java.util.HashSet;
import java.util.List;

import com.previred.rezagos.dto.ElementoIdDTO;
import com.previred.rezagos.dto.MacDTO;

public interface IMacDao {
	
	public List<MacDTO> obtieneTodosLosRegistros() throws DAOException;

	public void obtieneTodosLosDatosSeparados() throws DAOException;

	public List<ElementoIdDTO> obtieneTodosLosRuts() throws DAOException;

	public HashSet<MacDTO> obtieneDatosCompletos() throws DAOException;
}
