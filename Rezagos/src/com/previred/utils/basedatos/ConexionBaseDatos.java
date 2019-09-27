package com.previred.utils.basedatos;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import com.previred.rezagos.dao.DAOException;

/**
 * Wrapper para una conexión a base de datos por JDBC
 * @author clillo
 *
 */
public class ConexionBaseDatos {
	
	// Si el método que obtiene las conexiones falla, se re-intenta una nueva conexión
	private static final int NUMERO_INTENTOS_CONEXION = 3;
	private static final int MILISEGUNDOS_ENTRE_CONEXION = 10000;
	
	private BasicDataSource dataSource;
	private String alias;
	private BaseDatosConexionTO datosConexion;

	/**
	 * Crea un datasource desde un TO
	 * @param to
	 */
	public ConexionBaseDatos(BaseDatosConexionTO to) {
		this.alias = to.getAlias();
		this.datosConexion = to;
		crearConexion();
	}

	/**
	 * Intenta obtener una conexión, si falla reintenta cada 10 segundos
	 * @return
	 * @throws DAOException
	 */
	public Connection getConnection() throws DAOException {
		
		Connection miConnection = null;
		
		int intentos=0;
		while (miConnection==null){
			intentos++;
			if (intentos>NUMERO_INTENTOS_CONEXION){
				throw new DAOException("No hay conexión a la Base de Datos "+alias+" ya se cumplió la cantidad máxima de reintentos, se lanzará la excepción");
			}
			
			try {
				miConnection = dataSource.getConnection();
				return miConnection;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			System.out.println("\tNo hay conexión a "+alias+" se esperarán "+MILISEGUNDOS_ENTRE_CONEXION+ " msec. en "+intentos+ " intentos");
			
			try {
				Thread.sleep(MILISEGUNDOS_ENTRE_CONEXION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	public String getAlias() {
		return alias;
	}

	/**
	 * Método que crea una nueva conexión para los datos actuales
	 * @author clillo
	 */
	private void crearConexion(){
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(datosConexion.getDriver());
		dataSource.setUsername(datosConexion.getUserName());
		dataSource.setPassword(datosConexion.getPassword());
		
		if (datosConexion.getUrl() != null)
			dataSource.setUrl(datosConexion.getUrl());
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("jdbc:").append(datosConexion.getDatabaseType()).append("://").append(datosConexion.getServer()).append(':').append(datosConexion.getPort());
			
			if (datosConexion.getInstance()!=null && !datosConexion.getInstance().equals(""))
				sb.append(";instance=").append(datosConexion.getInstance());

			sb.append(";DatabaseName=").append(datosConexion.getDatabaseName()).append(";SelectMethod=").append(datosConexion.getSelectNethod());

			dataSource.setUrl(sb.toString());
		}
		
		System.out.println("\tCreada conexi�n a base de datos: "+dataSource.getUrl());
		
		dataSource.setMaxActive(datosConexion.getMaxActive());
		dataSource.setMaxIdle(datosConexion.getMaxIdle());
		dataSource.setMaxWait(datosConexion.getMaxWait());
		dataSource.setValidationQuery("SELECT 1");	
	}
}
