package com.previred.rezagos.dao;

/**
 * Clase que maneja excepciones para DAO
 * @author clillo
 */
public class DAOException extends Exception{

	private static final long serialVersionUID = 1L;

	public DAOException(String msg) {
		super(msg);
	}
	
	public DAOException(Exception e) {
		super(e.getMessage());
		this.setStackTrace(e.getStackTrace());
	}
	
	/**
	 * Constructor spersonalizar mensaje
	 * @param mensaje
	 * @param e
	 * @author pmateu
	 */
	public DAOException(String mensaje, Exception e) {
		super(mensaje + e.getMessage());
		this.setStackTrace(e.getStackTrace());
	}
	
	public void manejaExcepcion(){
		printStackTrace();
	}
}
