package com.previred.rezagos.dto;

public class ComparacionDTO {

	private long idMac;
	private int rutMac;
	private String nombreMac;
	private String apellidoPaternoMac;
	private String apellidoMaternoMac;
	
	private long idRezago;
	private int rutRez;
	private String nombreRez;
	private String apellidoPaternoRez;
	private String apellidoMaternoRez;
	
	private boolean exactoRut;
	private boolean exactoNombres;
	private boolean exactoApellidoPaterno;
	private boolean exactoApellidoMaterno;

	private boolean imposibleRut;
	private boolean imposibleNombres;
	private boolean imposibleApellidoPaterno;
	private boolean imposibleApellidoMaterno;
	
	private int similaridadRut;
	private int similaridadNombres;
	private int similaridadApellidoPaterno;
	private int similaridadApellidoMaterno;
	
	public long getIdMac() {
		return idMac;
	}
	public void setIdMac(long idMac) {
		this.idMac = idMac;
	}
	public int getRutMac() {
		return rutMac;
	}
	public void setRutMac(int rutMac) {
		this.rutMac = rutMac;
	}
	public String getNombreMac() {
		return nombreMac;
	}
	public void setNombreMac(String nombreMac) {
		this.nombreMac = nombreMac;
	}
	public String getApellidoPaternoMac() {
		return apellidoPaternoMac;
	}
	public void setApellidoPaternoMac(String apellidoPaternoMac) {
		this.apellidoPaternoMac = apellidoPaternoMac;
	}
	public String getApellidoMaternoMac() {
		return apellidoMaternoMac;
	}
	public void setApellidoMaternoMac(String apellidoMaternoMac) {
		this.apellidoMaternoMac = apellidoMaternoMac;
	}
	public long getIdRezago() {
		return idRezago;
	}
	public void setIdRezago(long idRezago) {
		this.idRezago = idRezago;
	}
	public int getRutRez() {
		return rutRez;
	}
	public void setRutRez(int rutRez) {
		this.rutRez = rutRez;
	}
	public String getNombreRez() {
		return nombreRez;
	}
	public void setNombreRez(String nombreRez) {
		this.nombreRez = nombreRez;
	}
	public String getApellidoPaternoRez() {
		return apellidoPaternoRez;
	}
	public void setApellidoPaternoRez(String apellidoPaternoRez) {
		this.apellidoPaternoRez = apellidoPaternoRez;
	}
	public String getApellidoMaternoRez() {
		return apellidoMaternoRez;
	}
	public void setApellidoMaternoRez(String apellidoMaternoRez) {
		this.apellidoMaternoRez = apellidoMaternoRez;
	}
	public boolean isExactoRut() {
		return exactoRut;
	}
	public void setExactoRut(boolean exactoRut) {
		this.exactoRut = exactoRut;
	}
	public boolean isExactoNombres() {
		return exactoNombres;
	}
	public void setExactoNombres(boolean exactoNombres) {
		this.exactoNombres = exactoNombres;
	}
	public boolean isExactoApellidoPaterno() {
		return exactoApellidoPaterno;
	}
	public void setExactoApellidoPaterno(boolean exactoApellidoPaterno) {
		this.exactoApellidoPaterno = exactoApellidoPaterno;
	}
	public boolean isExactoApellidoMaterno() {
		return exactoApellidoMaterno;
	}
	public void setExactoApellidoMaterno(boolean exactoApellidoMaterno) {
		this.exactoApellidoMaterno = exactoApellidoMaterno;
	}
	public boolean isImposibleRut() {
		return imposibleRut;
	}
	public void setImposibleRut(boolean imposibleRut) {
		this.imposibleRut = imposibleRut;
	}
	public boolean isImposibleNombres() {
		return imposibleNombres;
	}
	public void setImposibleNombres(boolean imposibleNombres) {
		this.imposibleNombres = imposibleNombres;
	}
	public boolean isImposibleApellidoPaterno() {
		return imposibleApellidoPaterno;
	}
	public void setImposibleApellidoPaterno(boolean imposibleApellidoPaterno) {
		this.imposibleApellidoPaterno = imposibleApellidoPaterno;
	}
	public boolean isImposibleApellidoMaterno() {
		return imposibleApellidoMaterno;
	}
	public void setImposibleApellidoMaterno(boolean imposibleApellidoMaterno) {
		this.imposibleApellidoMaterno = imposibleApellidoMaterno;
	}
	public int getSimilaridadRut() {
		return similaridadRut;
	}
	public void setSimilaridadRut(int similaridadRut) {
		this.similaridadRut = similaridadRut;
	}
	public int getSimilaridadNombres() {
		return similaridadNombres;
	}
	public void setSimilaridadNombres(int similaridadNombres) {
		this.similaridadNombres = similaridadNombres;
	}
	public int getSimilaridadApellidoPaterno() {
		return similaridadApellidoPaterno;
	}
	public void setSimilaridadApellidoPaterno(int similaridadApellidoPaterno) {
		this.similaridadApellidoPaterno = similaridadApellidoPaterno;
	}
	public int getSimilaridadApellidoMaterno() {
		return similaridadApellidoMaterno;
	}
	public void setSimilaridadApellidoMaterno(int similaridadApellidoMaterno) {
		this.similaridadApellidoMaterno = similaridadApellidoMaterno;
	}
}
