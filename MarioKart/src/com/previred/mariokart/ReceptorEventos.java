package com.previred.mariokart;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/eventos"})
public class ReceptorEventos extends HttpServlet{
	
	private static final long serialVersionUID = -2827663265593547983L;

	private HashMap<Integer, Punto> posiciones;
	
	public ReceptorEventos(HashMap<Integer, Punto> posiciones) {
		this.posiciones = posiciones;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			int x = Integer.parseInt(request.getParameter("x"));
			int y = Integer.parseInt(request.getParameter("y"));
			String nombre = request.getParameter("nombre");
			Punto p = new Punto();
			p.setX(x);
			p.setY(y);
			p.setNombre(nombre);
			posiciones.put(id, p);
		//	if (id==0)
			//	System.out.println(id+"\t"+x+"\t"+y);

		} catch (NumberFormatException e) {
			
		}
		
		//controlador.notificar(1, "{\"x\":"+x+", \"y\": "+y+"}");
		//new Carrera(canal, id).start();
	}
	
}