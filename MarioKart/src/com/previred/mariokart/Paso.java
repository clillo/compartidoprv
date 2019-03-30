package com.previred.mariokart;

public class Paso {
	
	private int id;
	private Accion accion;
	private long tiempo;
	
	public Paso(int id, Accion accion, long tiempo) {
		super();
		this.id = id;
		this.accion = accion;
		this.tiempo = tiempo;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}

	public int getId() {
		return id;
	}	
}
