package com.previred.rezagos.dto;

public enum TiposElemento {

	apellidos(".apellidos.txt"), nombres(".nombres.txt"), ruts(".ruts.txt");
	
	private String archivo;

	private TiposElemento(String archivo) {
		this.archivo = archivo;
	}

	public String getArchivo() {
		return archivo;
	}
	
}
