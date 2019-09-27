package com.previred.rezagos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.previred.rezagos.dto.ElementoIdDTO;
import com.previred.rezagos.dto.MacDTO;
import com.previred.utils.basedatos.BD;
import com.previred.utils.basedatos.DAOFactorySQLServer;

public class MacDaoSqlServer implements IMacDao {
	
	/**
	 *         select  
	afp			   =  CONVERT([char](4),cod_institucion)	
	,rut_afiliado	   =  CONVERT([varchar](10),rut)
	,dv_afiliado	   =  CONVERT([varchar](1),dv)
	,nombrestrabajador =  CONVERT( [varchar](50),upper(Ltrim(ltRim(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( nombres,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n'),'  ',' ')))))
	,apellidopaterno   =  CONVERT( [varchar](50),upper(Ltrim(ltRim(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( ap_paterno,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n'),'  ',' ')))))
	,apellidomaterno   =  CONVERT( [varchar](50),upper(Ltrim(ltRim(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( ap_materno,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n'),'  ',' ')))))
  FROM [mac].[dbo].[Afiliados]   where periodo = 201909
  
select [id]=ROW_NUMBER() OVER(ORDER BY rut ASC),[rut],[dv_rut],[nombres],[ap_paterno],[ap_materno]
from (
select    
                 rut                         = convert(varchar(20),rut)
                ,dv_rut                 = c.dv
                ,nombres    = upper(Ltrim(ltRim(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(c.nombres,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n'),'  ',' '))))
                ,ap_paterno = upper(Ltrim(ltRim(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(c.ap_paterno,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n'),'  ',' '))))
                ,ap_materno = upper(Ltrim(ltRim(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(c.ap_materno,'á','a'),'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n'),'  ',' '))))
                ,rn= (ROW_NUMBER() OVER (PARTITION BY rut ORDER BY LEN( CONCAT(nombres,ap_paterno,ap_materno) )DESC ))
from mac..Afiliados c
where periodo='201909'
and (LEN(rut)>6) 
--and c.cod_institucion = '1032'
) tbl


	 */

	@Override
	public List<MacDTO> obtieneTodosLosRegistros() throws DAOException {
		ResultSet rs = null;
		MacDTO to = null;

		List<MacDTO> lista = new ArrayList<MacDTO>();
		try (Connection conn = DAOFactorySQLServer.getConnection(BD.MAC); 
					
			CallableStatement stmt = conn.prepareCall("{call spLST_Instituciones_Bancos_Deudas()}");) {

			rs = stmt.executeQuery();

			while (rs.next()) {
				to = new MacDTO();
				to.setNombre(rs.getString("cod_institucion"));
				to.setApellidoPaterno(rs.getString("cod_banco"));
				to.setApellidoMaterno(rs.getString("convenio_fondo"));
			//	to.setCodAfp(Integer.parseInt(rs.getString("cta_fondo_banco")));

				lista.add(to);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return lista;

	}

	@Override
	public void obtieneTodosLosDatosSeparados() throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ElementoIdDTO> obtieneTodosLosRuts() throws DAOException {
		
		return null;
	}

	@Override
	public HashSet<MacDTO> obtieneDatosCompletos() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
