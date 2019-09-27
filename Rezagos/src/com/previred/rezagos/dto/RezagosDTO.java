package com.previred.rezagos.dto;

public class RezagosDTO {

	private int idProceso;
	private long idRezago;
	private int rut;
	private int afp;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	
	public int getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}
	public long getIdRezago() {
		return idRezago;
	}
	public void setIdRezago(long idRezago) {
		this.idRezago = idRezago;
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
	
	public int getAfp() {
		return afp;
	}
	public void setAfp(int afp) {
		this.afp = afp;
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
		RezagosDTO other = (RezagosDTO) obj;
		if (idProceso != other.idProceso)
			return false;
		return true;
	}
}
