package com.previred.mariokart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/recibeAI"})
public class ReceptorAI extends HttpServlet{
	
	private static final long serialVersionUID = -2827663265593547983L;

	
	public ReceptorAI() {

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			String id = request.getParameter("id");
			String aipoint = request.getParameter("aipoint");
			String aipointx = request.getParameter("aipointx");
			String aipointy = request.getParameter("aipointx");
			String speedinc = request.getParameter("speedinc");
			String rotincdir = request.getParameter("rotincdir");

			System.out.println(id+"\t"+aipoint+"\t"+aipointx+"\t"+aipointy+"\t"+speedinc+"\t"+rotincdir);

		} catch (NumberFormatException e) {
			
		}
	}
	
}