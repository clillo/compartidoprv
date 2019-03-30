package com.previred.mariokart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/hello"}, asyncSupported = true)
public class Canal extends HttpServlet{
	
	private static final long serialVersionUID = -2827663265593547983L;
	private ConcurrentMap<Integer, AsyncContext> contexts = new ConcurrentHashMap<>();

	private EnviaPosiciones enviaPos;
	
	public Canal(EnviaPosiciones enviaPos) {
		super();
		this.enviaPos = enviaPos;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		final AsyncContext asyncContext = request.startAsync(request, response);
		asyncContext.setTimeout(0); 

		System.out.println("Llego el cliente "+id);
		
		enviaPos.iniciaCarrera();
		
		contexts.put(id, asyncContext);
	}
	
	public void notificar(int userId, String notification){
		try{
			if(contexts.containsKey(userId)){
				//System.out.println("Tratando de enviar al cliente "+userId);
				AsyncContext asyncContext = contexts.get(userId);
				PrintWriter writer = asyncContext.getResponse().getWriter();
				writer.write("data: " + notification + "\n\n");
				writer.flush();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}