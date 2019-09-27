package com.previred.rezagos.dto;

import java.util.List;

public class ElementoIntListaDTO {

	private int id;
	private List<String> valor;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getValor() {
		return valor;
	}
	public void setValor(List<String> valor) {
		this.valor = valor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		ElementoIntListaDTO other = (ElementoIntListaDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
