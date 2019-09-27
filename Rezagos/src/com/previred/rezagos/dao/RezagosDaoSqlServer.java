package com.previred.rezagos.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import com.previred.rezagos.dto.MacDTO;
import com.previred.rezagos.dto.RezagosDTO;

public class RezagosDaoSqlServer implements IRezagosDao{
	
	/*
	 * 
	 *   select id
	,afp			   =  CONVERT([char](4),afp)	
	,rut_afiliado	   =  CONVERT([varchar](10),rut_afiliado)
	,dv_afiliado	   =  CONVERT([varchar](1),dv_afiliado)
	,nombrestrabajador =  CONVERT( [varchar](50),upper(Ltrim(ltRim(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( nombres_trabajador,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n'),'  ',' ')))))
	,apellidopaterno   =  CONVERT( [varchar](50),upper(Ltrim(ltRim(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( ap_paterno,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n'),'  ',' ')))))
	,apellidomaterno   =  CONVERT( [varchar](50),upper(Ltrim(ltRim(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( ap_materno,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n'),'  ',' ')))))
  FROM [previred_integracion].[ag].[rzg_rezagos]  
  
  (non-Javadoc)
	 * @see com.previred.rezagos.dao.IRezagosDao#obtieneTodosLosRegistros()
	 */
    
	@Override
	public List<RezagosDTO> obtieneTodosLosRegistros() throws DAOException {
		return null;
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
