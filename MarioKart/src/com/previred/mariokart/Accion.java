package com.previred.mariokart;

public enum Accion {
	avanzar("up-on"), 
	detener("up-off"), 
	izquerda("left-on"), 
	izquerdaOff("left-off"), 
	derecha("rigth-on"), 
	derechaOff("rigth-off");
	
	String str;

	private Accion(String str) {
		this.str = str;
	}
	
	public static Accion obieneAccion(String str){
		for (Accion a: values())
			if (a.str.equals(str))
				return a;
		return null;
	}
}
