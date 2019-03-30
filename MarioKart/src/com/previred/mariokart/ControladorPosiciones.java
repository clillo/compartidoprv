package com.previred.mariokart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/tablero"})
public class ControladorPosiciones extends HttpServlet{
	
	private static final long serialVersionUID = -2827663265593547983L;
	
	private HashMap<Integer, Punto> posiciones;

	public ControladorPosiciones(HashMap<Integer, Punto> posiciones) {
		this.posiciones = posiciones;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//int id = Integer.parseInt(request.getParameter("id"));
		
		PrintWriter writer = response.getWriter();

		List<Punto> lista = new ArrayList<>(); 
		for (int i=0; i<7; i++){
			if (posiciones.containsKey(i)){
				Punto p = posiciones.get(i);
				lista.add(p);
			}
		}
		
		if (lista.size()==0){
			writer.flush();
			return;
		}
		
		Collections.sort(lista);
		//System.out.println(lista.size());
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Punto p: lista)
			sb.append("{\"x\":"+p.getX()+", \"y\": "+p.getY()+", \"id\": "+p.getId()+" , \"nombre\": \""+p.getNombre() + "\" } ,");
		
		sb.deleteCharAt(sb.length()-1);
		sb.append("]\n\n");
		writer.write(sb.toString());
		writer.flush();
	}

}