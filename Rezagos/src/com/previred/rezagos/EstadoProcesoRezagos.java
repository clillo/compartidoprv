package com.previred.rezagos;

import java.util.List;

import com.previred.utils.paralelismo.EstadoProcesoParalelo;

public class EstadoProcesoRezagos extends EstadoProcesoParalelo{

	private long registrosTotales;
	private long matchs;
	
	private List<String> listaMac;
	private List<String> listaRezagos;
	
	public List<String> getListaMac() {
		return listaMac;
	}

	public void setListaMac(List<String> listaMac) {
		this.listaMac = listaMac;
	}

	public List<String> getListaRezagos() {
		return listaRezagos;
	}

	public void setListaRezagos(List<String> listaRezagos) {
		this.listaRezagos = listaRezagos;
	}

	public long getRegistrosTotales() {
		return registrosTotales;
	}

	public void setRegistrosTotales(long registrosTotales) {
		this.registrosTotales = registrosTotales;
	}

	public long getMatchs() {
		return matchs;
	}

	public void setMatchs(long matchs) {
		this.matchs = matchs;
	}
	
}
