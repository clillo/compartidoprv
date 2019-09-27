package com.previred.utils.basedatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.previred.rezagos.dao.DAOException;

/**
 * Clase que permite el manejo de conexiones de base de datos en ambientes batch y web 
 * @author clillo
 *
 */
public class DAOFactorySQLServer{

	private static final String PREFIJO_CONF = "sqlserver";
	private static final String PREFIJO_TOMCAT = "dsSqlServer";

	// Estructura con DataSources Disponibles
	private static HashMap<String, ConexionBaseDatos> dataSources = null;

	private static boolean tieneLog;
	
	public DAOFactorySQLServer() {
		super();
	}
	
	/**
	 * Permite saber si está disponible un datasource para su uso
	 * Usado actualmente en ControlContratistas para saber si está disponible el conector de deudas
	 * @param nombre
	 * @return
	 * @author clillo
	 */
	public static boolean existeDataSource(String nombre){
		if (dataSources==null){
			try {
				Context initContext;
				initContext = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/");
				DataSource ds = ((DataSource)envContext.lookup(PREFIJO_TOMCAT+nombre));
				if (ds == null) 
					return false;
			} catch (NamingException e) {
				return false;
			}
			
			return true;
		}

		String nombreFull = PREFIJO_CONF+nombre;
		// obtiene el pool seteado desde el archivo de configuración
		ConexionBaseDatos cbd = dataSources.get(nombreFull);
		if (cbd == null)
			return false;
		
		return true;
	}
	
	/**
	 * Obtiene una conexión desde el Pool de Base de Datos
	 * @param nombre
	 * @return
	 * @throws DAOException
	 */
	public static Connection getConnection(String nombre) throws DAOException {
		if (dataSources==null){
			// obtiene el pool del JBoss
			Context initContext;
			try {
				initContext = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/");
				DataSource ds = ((DataSource)envContext.lookup(PREFIJO_TOMCAT+nombre));
				if (ds == null) {
					throw new DAOException("No existe el nombre ["+PREFIJO_TOMCAT+nombre+"] en los datasources de JBoss");
				} 
				return ds.getConnection();
			} catch (NamingException e) {
				throw new DAOException("No existe el nombre ["+PREFIJO_TOMCAT+nombre+"] en los datasources de JBoss");
			} catch (SQLException e) {
				throw new DAOException(e);
			} catch (Exception e) {
				throw new DAOException(e);
			} 
		}
		
		String nombreFull = PREFIJO_CONF+nombre;
		// obtiene el pool seteado desde el archivo de configuración
		ConexionBaseDatos cbd = dataSources.get(nombreFull);
		return cbd.getConnection();
	}
		
	/**
	 * Obtiene una conexión desde el Pool de Base de Datos
	 * @param nombre
	 * @return
	 * @throws DAOException
	 */
	public static Connection getConnection(BD baseDatos) throws DAOException {
		String nombre = baseDatos.toString();
		return getConnection(nombre);		
	}
	
	public static Connection getConnection() throws DAOException {		
		return getConnection(BD.OPERACIONAL);
	}
	
	public static synchronized void setPool(ArrayList<BaseDatosConexionTO> lista) {
		setPool(lista, false);
	}
	
	private void initBaseDatos(String ipBaseDatos, String nombre, String password){
		String bases[] = {"MAC", "GOLIATH"};
		String alias[] = {"sqlserver", "sqlserver_rzg_rezagos"};
		
		ArrayList<BaseDatosConexionTO> listaDataSources = new ArrayList<BaseDatosConexionTO>();
		
		for (int i=0; i<bases.length; i++){
			BaseDatosConexionTO to = new BaseDatosConexionTO();
			to.setAlias(alias[i]);
			to.setDriver("net.sourceforge.jtds.jdbc.Driver");
			to.setUrl("jdbc:jtds:sqlserver://"+ipBaseDatos+";DatabaseName="+bases[i]+";SelectMethod=Cursor");
			to.setInstance("");
			to.setMaxActive(100);
			to.setMaxWait(-1);
			to.setMaxIdle(8);
			to.setUserName(nombre);
			to.setPassword(password);
			listaDataSources.add(to);
		}
		
		com.previred.utils.basedatos.DAOFactorySQLServer.setPool(listaDataSources);
	}
	
	/**
	 * Obtiene el pool desde un arreglo de objetos "BaseDatosConexionTO"
	 * @param lista
	 */
	public static synchronized void setPool(ArrayList<BaseDatosConexionTO> lista, boolean force) {
		if (!force && dataSources!=null){
			System.out.println("Ya se defini� un pool para este contexto");
			return;
		}
		
		System.out.println("Definiendo un pool de base de datos. Usando un arreglo definido en el archivo de configuraci�n global");
		
		dataSources = new HashMap<String, ConexionBaseDatos>();
		
		for (BaseDatosConexionTO to:lista)	
			dataSources.put(to.getAlias(), new ConexionBaseDatos(to));
	}
}

