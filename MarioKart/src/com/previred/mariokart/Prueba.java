package com.previred.mariokart;

import java.io.File;
import java.util.HashMap;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

public class Prueba {

	public static void main(String[] args) throws InterruptedException, LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		
		String base = new File("").getAbsolutePath();
		
		Context ctx = tomcat.addContext("/", base);
	

		HashMap<Integer, Punto> posiciones = new HashMap<>();

		ControladorPosiciones cp = new ControladorPosiciones(posiciones);
		EnviaPosiciones ep = new EnviaPosiciones(posiciones);
		
		Canal canal = new Canal(ep);

		Tomcat.addServlet(ctx, "recibeInicioFin", new ReceptorInicioFin(ep));
		ctx.addServletMapping("/recibeInicioFin/*", "recibeInicioFin");
		
		Tomcat.addServlet(ctx, "hello", canal);
		ctx.addServletMapping("/hello/*", "hello");
		
		Tomcat.addServlet(ctx, "envia", ep);
		ctx.addServletMapping("/envia/*", "envia");
		
		Tomcat.addServlet(ctx, "tablero", cp);
		ctx.addServletMapping("/tablero/*", "tablero");
		
		Tomcat.addServlet(ctx, "eventos", new ReceptorEventos(posiciones));
		ctx.addServletMapping("/eventos/*", "eventos");
		
		Tomcat.addServlet(ctx, "traza", new ReceptorTraza(canal));
		ctx.addServletMapping("/traza/*", "traza");

		Tomcat.addServlet(ctx, "recibe", new EnviaCaracteres());
		ctx.addServletMapping("/recibe/*", "recibe");
		
		Tomcat.addServlet(ctx, "recibeAI", new ReceptorAI());
		ctx.addServletMapping("/recibeAI/*", "recibeAI");
		
		ctx.addWelcomeFile("index.html");
		
		Wrapper dServ = ctx.createWrapper();
		dServ.setName("default");
		dServ.setServletClass("org.apache.catalina.servlets.DefaultServlet");
		dServ.addInitParameter("debug", "0");
		dServ.addInitParameter("listings", "true");
		dServ.setLoadOnStartup(1);
		
		ctx.addChild(dServ);
		
		ctx.addServletMapping("/", "default");
				
		tomcat.start();
		tomcat.getServer().await();
	}
}
