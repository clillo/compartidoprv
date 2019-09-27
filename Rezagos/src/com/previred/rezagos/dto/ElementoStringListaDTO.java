package com.previred.rezagos.dto;

import java.util.List;

public class ElementoStringListaDTO {

	private String id;
	private String valor;
	private List<Integer> lista;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public List<Integer> getLista() {
		return lista;
	}
	public void setLista(List<Integer> lista) {
		this.lista = lista;
	}
}
