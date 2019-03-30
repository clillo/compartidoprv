package com.previred.mariokart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

@WebServlet(urlPatterns = {"/envia"})
public class EnviaPosiciones extends HttpServlet{
	
	private static final long serialVersionUID = -2827663265593547983L;

	private HashMap<Integer, Punto> posiciones;
	private ArrayList<PuntoAI> camino1;
	private ArrayList<PuntoAI> camino2;
	private ArrayList<PuntoAI> camino3;
	
	private int indice;
	private boolean iniciaCarrera = false;
	
	public EnviaPosiciones(HashMap<Integer, Punto> posiciones) {
		this.posiciones = posiciones;
		this.camino1 = new ArrayList<>();
		this.camino2 = new ArrayList<>();
		this.camino3 = new ArrayList<>();
		
		/*leeArchivo(camino1, "dum.txt");
		leeArchivo(camino2, "dum2.txt");
		leeArchivo(camino3, "dum3.txt");
		*/indice = 0;
	}
	
	private void leeArchivo(ArrayList<PuntoAI> camino, String nombreArchivo){
		try {
			List<String> lineas = Files.readAllLines(Paths.get(nombreArchivo));
			
			for (String str: lineas){
				PuntoAI p = new PuntoAI();
				String fila[] = str.split("\t");
				p.setId(fila[0].trim());
				p.setAipoint(fila[1].trim());
				p.setAipointx(fila[2].trim());
				p.setAipointy(fila[3].trim());
				p.setSpeedinc(fila[4].trim());
				p.setRotincdir(fila[5].trim());
				camino.add(p);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void iniciaCarrera(){
		iniciaCarrera = true;
		indice = 0;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		if (request.getParameter("dummy")==null)
			if(!iniciaCarrera){
				PrintWriter writer = response.getWriter();
				writer.write("{}");
				writer.flush();
				return;
			}
		  
		StringBuffer jb = new StringBuffer();
		String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) {  }

		  String json = jb.toString();
		  Gson gson = new Gson();
		  
		  Type listType = new TypeToken<ArrayList<Punto>>(){}.getType();
		  List<Punto> list = gson.fromJson(json, listType);
		  
		  for(Punto p: list){
			  posiciones.put(p.getId(), p);
			//  System.out.println(p.getNombre());
			  //System.out.print(p.getId()+" ");
		  }
		 // System.out.println();
		
//		PuntoAI p1 = camino1.get(indice);
//		PuntoAI p2 = camino2.get(indice);
//		PuntoAI p3 = camino3.get(indice);
//		if (p1==null || p2==null || p3==null)
//			return;
//		
//		PrintWriter writer = response.getWriter();
//		writer.write("[ " + p1.toJson() + ", " +p2.toJson() + ", " +p3.toJson() +"]");
//		writer.flush();
//		indice++;
//		if (indice>=camino1.size())
//			indice=0;
	}

}