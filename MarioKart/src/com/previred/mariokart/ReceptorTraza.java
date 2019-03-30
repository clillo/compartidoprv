package com.previred.mariokart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/traza"})
public class ReceptorTraza extends HttpServlet{
	
	private static final long serialVersionUID = -2827663265593547983L;

	private Canal canal;
	private long tiempoAnterior;
	private int id = 0;
	
	private String accionAnterior="";
	
	public ReceptorTraza(Canal canal) {
		super();
		this.canal = canal;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String tipo = request.getParameter("id");
		if (tipo.equals(accionAnterior))
			return;
		accionAnterior = tipo;
		long tiempo = System.currentTimeMillis() - tiempoAnterior;
		System.out.println(id + "\t" + tipo +"\t "+tiempo);
		tiempoAnterior = System.currentTimeMillis();
		id++;
	}
	
}