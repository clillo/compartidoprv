package com.previred.mariokart;

public class Punto implements Comparable<Punto>{

	private int x;
	private int y;
	private int id;
	private int pos;
	private String nombre;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Punto [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", id=");
		builder.append(id);
		builder.append(", pos=");
		builder.append(pos);
		builder.append("]");
		return builder.toString();
	}
	@Override
	public int compareTo(Punto o) {
		return pos - o.pos;
	}
}
