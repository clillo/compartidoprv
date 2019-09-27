package com.previred.rezagos.preproceso;

import com.previred.rezagos.dao.DAOException;
import com.previred.utils.formato.Formatos;

public class PreProceso {

	public static void main(String[] args) throws DAOException {
		long tiempo = System.currentTimeMillis();
		
		System.out.println("Comienza a leer los registros del MAC");
		PreProcesaArchivoMac ppa = new PreProcesaArchivoMac();
		ppa.procesa();
			
		System.out.println("Comienza a leer los registros de rezagos");
		PreProcesosRezagos rezagos = new PreProcesosRezagos();
		rezagos.procesa();

		System.out.println("Tiempo total del pre-proceso: "+ Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
	}
}
