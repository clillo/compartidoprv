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

@WebServlet(urlPatterns = {"/recibe"})
public class EnviaCaracteres extends HttpServlet{
	
	private static final long serialVersionUID = -2827663265593547983L;


	private int indice;
	private boolean iniciaCarrera = false;
	
	public EnviaCaracteres() {

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

		String colores[] = {"#f8f800", "#c01f18", "#28fd33", "#b518c0", "#00168a", "#fd6207", "#040404"};
		String aCharacters [] = {"mario_amarillo",  "peach_rojo", "mario_verde", "peach_rosado","mario_azul", "peach_naranjo", "mario_negro"};
		String nombres [] = {"Fabian Badilla", "Natalia Villagran", "Gerardo Valenzuela", "Carolina Montoya", "Luis Molina", "Miguel Gonzalez", "Cristian Angulo" };

		List<String> lista = new ArrayList<String>();
		
		for (int i=0; i<7; i++)
			lista.add(aCharacters[i]);
		
		PrintWriter writer = response.getWriter();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (String p: lista)
			sb.append("{\"caracter\": \""+ p + "\"} ,");
		
		sb.deleteCharAt(sb.length()-1);
		sb.append("]\n\n");
		writer.write(sb.toString());
		writer.flush();
		
	}

}