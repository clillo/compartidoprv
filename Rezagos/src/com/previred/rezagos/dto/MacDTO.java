package com.previred.rezagos.dto;

public class MacDTO {

	private int idProceso;
	private int idRut;
	private int rut;
	private int idNombre;
	private String nombre;
	private int idPaterno;
	private String apellidoPaterno;
	private int idMaterno;
	private String apellidoMaterno;
	private int afp;
	
	public int getAfp() {
		return afp;
	}
	public void setAfp(int afp) {
		this.afp = afp;
	}
	public int getRut() {
		return rut;
	}
	public void setRut(int rut) {
		this.rut = rut;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public int getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}
	public int getIdRut() {
		return idRut;
	}
	public void setIdRut(int idRut) {
		this.idRut = idRut;
	}
	public int getIdNombre() {
		return idNombre;
	}
	public void setIdNombre(int idNombre) {
		this.idNombre = idNombre;
	}
	public int getIdPaterno() {
		return idPaterno;
	}
	public void setIdPaterno(int idPaterno) {
		this.idPaterno = idPaterno;
	}
	public int getIdMaterno() {
		return idMaterno;
	}
	public void setIdMaterno(int idMaterno) {
		this.idMaterno = idMaterno;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idProceso;
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
		MacDTO other = (MacDTO) obj;
		if (idProceso != other.idProceso)
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MacDTO [idProceso=");
		builder.append(idProceso);
		builder.append(", idRut=");
		builder.append(idRut);
		builder.append(", rut=");
		builder.append(rut);
		builder.append(", idNombre=");
		builder.append(idNombre);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", idPaterno=");
		builder.append(idPaterno);
		builder.append(", apellidoPaterno=");
		builder.append(apellidoPaterno);
		builder.append(", idMaterno=");
		builder.append(idMaterno);
		builder.append(", apellidoMaterno=");
		builder.append(apellidoMaterno);
		builder.append(", afp=");
		builder.append(afp);
		builder.append("]");
		return builder.toString();
	}	
	
}
