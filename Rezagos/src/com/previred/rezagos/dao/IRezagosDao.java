package com.previred.rezagos.dao;

import java.util.HashSet;
import java.util.List;

import com.previred.rezagos.dto.RezagosDTO;

public interface IRezagosDao {

	public List<RezagosDTO> obtieneTodosLosRegistros() throws DAOException;

	public void obtieneTodosLosDatosSeparados() throws DAOException;
	
	public List<String> obtieneTodosLosApellidos() throws DAOException;
	
	public List<String> obtieneTodosLosRuts() throws DAOException;

	public HashSet<RezagosDTO> obtieneDatosCompletos() throws DAOException;
}
