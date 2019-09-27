package com.previred.utils.paralelismo;

/**
 * Clase abstracta que realizará el trabajo en paralelo
 * @author clillo
 *
 */
public abstract class ThreadProceso extends Thread{

	private CoordinadorThreads coordinador;
	private int threadId;
	private EstadoProcesoParalelo estadoProceso;
			
	public ThreadProceso() {
		// TODO Auto-generated constructor stub
	}
	
	public ThreadProceso(CoordinadorThreads coordinador, int threadId, EstadoProcesoParalelo estadoProceso) {
	   this.coordinador = coordinador;
	   this.threadId = threadId;
	   this.estadoProceso = estadoProceso;
	}
	
	protected abstract void procesa(int threadId, EstadoProcesoParalelo estadoProceso);

	@Override
	public void run() {		
		procesa(threadId, estadoProceso);	
		coordinador.muere(estadoProceso);
	}

	public CoordinadorThreads getCoordinador() {
		return coordinador;
	}
	
	
}
