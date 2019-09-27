package com.previred.utils.basedatos;

import java.io.Serializable;

/**
 * Almacena los datos necesarios para crear una conexión a la base de datos
 * @author clillo
 *
 */
public class BaseDatosConexionTO implements Serializable {

	private static final long serialVersionUID = 8374366683422566781L;

	private String alias;
	private String databaseType;
	private String server;
	private String port;
	private String databaseName;
	private String selectNethod;
	private String userName;
	private String password;
	private String driver;
	private String instance;
	private String url;
	private int maxActive;
	private int maxWait;
	private int maxIdle;
	
	public BaseDatosConexionTO() {
		url = null;
	}
	
	public String getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		if (port==null || port.equals(""))
			port="1433";
		try{
			Integer.parseInt(port);
		}catch (Exception e) {
			port="1433";
		}
		this.port = port;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getSelectNethod() {
		return selectNethod;
	}
	public void setSelectNethod(String selectNethod) {
		this.selectNethod = selectNethod;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseDatosConexionTO other = (BaseDatosConexionTO) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		return true;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("alias:[");
		sb.append(alias);
		sb.append("] databaseType:[");
		sb.append(databaseType);		
		sb.append("] server:[");
		sb.append(server);
		sb.append("] port:[");
		sb.append(port);
		sb.append("] databaseName:[");
		sb.append(databaseName);
		sb.append("] selectNethod:[");
		sb.append(selectNethod);
		sb.append("] userName:[");
		sb.append(userName);
		sb.append("] driver:[");
		sb.append(driver);
		sb.append("] instance:[");
		sb.append(instance);
		sb.append("] maxActive:[");
		sb.append(maxActive);
		sb.append("] maxWait:[");
		sb.append(maxWait);
		sb.append("] maxIdle:[");
		sb.append(maxIdle);
		sb.append("] URL:[");
		sb.append(url);
		sb.append("] Password:[");
		if (password!=null && !password.equals(""))
			sb.append("XXXXX");
		sb.append(']');
		return sb.toString();
	}
}