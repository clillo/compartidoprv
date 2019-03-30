package com.previred.mariokart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/recibeInicioFin"})
public class ReceptorInicioFin extends HttpServlet{
	
	private static final long serialVersionUID = -2827663265593547983L;

	private EnviaPosiciones enviaPos;
	
	public ReceptorInicioFin(EnviaPosiciones enviaPos) {
		super();
		this.enviaPos = enviaPos;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			String evento = request.getParameter("evento");
		//	System.out.println(evento);
			enviaPos.iniciaCarrera();
			

		} catch (NumberFormatException e) {
			
		}
	}
	
}