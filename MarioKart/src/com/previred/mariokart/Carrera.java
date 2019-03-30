package com.previred.mariokart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Carrera extends Thread {
	
	private Canal canal;
	private int idAuto;
	
	private List<Paso> pasos = new ArrayList<>();
	
	public Carrera(Canal canal, int idAuto) {
		super();
		this.canal = canal;	
		this.idAuto = idAuto;
		
//		pasos.add(new Paso(1, Accion.avanzar, 1500));
//		pasos.add(new Paso(2, Accion.izquerda, 900));
//		pasos.add(new Paso(3, Accion.derecho, 600));
//		pasos.add(new Paso(4, Accion.derecha, 200));
//		
//		pasos.add(new Paso(5, Accion.derecho, 3000));
//		pasos.add(new Paso(6, Accion.izquerda, 900));
//		
//		pasos.add(new Paso(7, Accion.derecho, 400));
//		pasos.add(new Paso(8, Accion.izquerda, 300));
//		pasos.add(new Paso(9, Accion.derecho, 2400));
//		
//		
//		pasos.add(new Paso(10, Accion.izquerda, 1200));
//		pasos.add(new Paso(11, Accion.derecho, 3500));
//
//		pasos.add(new Paso(4, Accion.derecha, 200));

//		pasos.add(new Paso(Accion.detener, 200));
	
		
		try {
			List<String> lineas = Files.readAllLines(Paths.get("movs.txt"));
			
			for (String str: lineas){
				String fila[] = str.split("\t");
				int id = Integer.parseInt(fila[0].trim());
				String tipo = fila[1].trim();	
				int tiempo = Integer.parseInt(fila[2].trim());
				Accion a = Accion.obieneAccion(tipo);
				if (a==null){
					System.out.println(tipo);
					System.exit(0);
				}
				pasos.add(new Paso(id, a, tiempo+50));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void run() {						
		for (Paso paso: pasos){		
//			try {
//				Thread.sleep((long)(Math.random()*50));
//			} catch (InterruptedException e) {
//			}
			
			canal.notificar(idAuto, "{\"id\": "+paso.getId()+", \"accion\": \""+paso.getAccion()+"\"}");

			try {
				Thread.sleep(paso.getTiempo());
			} catch (InterruptedException e) {
			}
		}
	}
}
