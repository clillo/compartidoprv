package com.previred.utils.paralelismo;

/**
 * Clase principal para activar el paralelismo
 * @author clillo
 *
 */
public abstract class CoordinadorThreads extends Thread{

	private int cuantosCorriendo;
	private int numeroThreads;
	
	public CoordinadorThreads(EstadoProcesoParalelo estadoProceso){
		numeroThreads = estadoProceso.getNumeroThreads();
	}
	
	/**
	 * Cuando un thread ha terminado su trabajo debe llamar a este método
	 * @param procesoThread
	 * @author clillo
	 */
	public synchronized void muere(EstadoProcesoParalelo procesoThread){
		cuantosCorriendo--;
		notifyAll();
	}
	
	/**
	 * Método que duerme el thread actual en espera que los hijos le manden una señal cuando invoquen al método "muere"
	 * @author clillo
	 */
	public synchronized void esperaAQueTodasMueran(){
		while (cuantosCorriendo>0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metodo que obtiene el estados de la instancia
	 * @return
	 */
	public abstract EstadoProcesoParalelo obtieneInstanciaEstado(int idThread);
	
	/**
	 * Metodo que obtiene la instacia proceso
	 * @param coordinador
	 * @param threadId
	 * @param estadoProceso
	 * @return
	 */
	public abstract ThreadProceso obtieneInstanciaProceso(CoordinadorThreads coordinador, int threadId, EstadoProcesoParalelo estadoProceso);
	
	/**
	 * Metodo que inicia los estados finales
	 * @param threadId
	 * @param estadoEspecifico
	 */
	public abstract void informaEstadosFinales(int threadId, EstadoProcesoParalelo estadoEspecifico);
	
	/**
	 * Metodo que inicia los estados especificos
	 * @param threadId
	 * @param estadoEspecifico
	 */
	public abstract void iniciaEstadosEspecificos(int threadId, EstadoProcesoParalelo estadoEspecifico);
	
	/**
	 * metodo que inicia el thread
	 */
	public void iniciaProceso(){
		this.start();
		try {
			this.join();
		} catch (InterruptedException e) {
		}
	}
	
	@Override
	public void run() {
		cuantosCorriendo=0;
		
		EstadoProcesoParalelo estadoProcesoEspecifico[] = new EstadoProcesoParalelo[numeroThreads];
		
		for (int i=0; i<numeroThreads; i++){
			estadoProcesoEspecifico[i] = obtieneInstanciaEstado(i);
			iniciaEstadosEspecificos(i, estadoProcesoEspecifico[i]);
		}
				
		for (int i=0; i<numeroThreads; i++){						
			ThreadProceso mt = obtieneInstanciaProceso(this, i, estadoProcesoEspecifico[i]);
			mt.start();
			cuantosCorriendo++;
		}
		
		esperaAQueTodasMueran();
		
		for (int i=0; i<numeroThreads; i++)
			informaEstadosFinales(i, estadoProcesoEspecifico[i]);
	}
}
