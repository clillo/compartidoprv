package com.previred.rezagos.indice;

import java.util.List;

import com.previred.rezagos.dto.ElementoIdDTO;
import com.previred.rezagos.dto.ElementoStringListaDTO;
import com.previred.rezagos.dto.TiposElemento;
import com.previred.utils.paralelismo.EstadoProcesoParalelo;

public class EstadoProcesoIndice extends EstadoProcesoParalelo{

	private List<ElementoIdDTO> listaMac;
	private List<ElementoStringListaDTO> listaRezagos;
	private long registrosTotales;
	private long matchs;
	private int idThead;
	
	private TiposElemento tipo;
	
	public void consolidaResultados(EstadoProcesoIndice epn){
		registrosTotales += epn.registrosTotales;
		matchs += epn.matchs;
	}

	public String obtieneDirectorioBase() {
		return "g:/rezagos/archivos/"; 
	}
	
	public String obtieneNombreArchivoSalida() {
		//nombreArchivo = "archivos/th"+threadId+".txt";
		return obtieneDirectorioBase()+"th"+idThead+"."+tipo+".txt"; 
	}
	
	public int getIdThead() {
		return idThead;
	}

	public void setIdThead(int idThead) {
		this.idThead = idThead;
	}

	public List<ElementoIdDTO> getListaMac() {
		return listaMac;
	}

	public void setListaMac(List<ElementoIdDTO> listaMac) {
		this.listaMac = listaMac;
	}

	public List<ElementoStringListaDTO> getListaRezagos() {
		return listaRezagos;
	}

	public void setListaRezagos(List<ElementoStringListaDTO> listaRezagos) {
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
	public TiposElemento getTipo() {
		return tipo;
	}
	public void setTipo(TiposElemento tipo) {
		this.tipo = tipo;
	}
}
