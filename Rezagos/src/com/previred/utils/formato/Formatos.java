package com.previred.utils.formato;

import java.text.DecimalFormat;

public class Formatos {

	public static String obtieneNumero(double d) {
		return  new DecimalFormat("###,###").format(d);
	}
	
	public static String limpiaCampo(String str) {
		char [] c = str.toLowerCase().toCharArray();
		boolean letra = false;
		StringBuilder sb = new StringBuilder();
		
		int n=0;
		while (n<c.length) {
			if (c[n]!=' ') {
				if (Character.isLetter(c[n]))
					sb.append(c[n]);
				letra = true;
			}
			else
				if (letra) {
					letra = false;
					sb.append(' ');
				}	
			n++;
		}
		
		return sb.toString().trim();
	}
	
	public static String obtieneTiempo(long l) {
		if (l<1000)
			return String.valueOf(l) + " ms.";
		
		if (l<60000)
			return String.valueOf(l/1000) + " seg.";

		if (l<60000*60)
			return String.valueOf(l/60000) + " min. " + String.valueOf(l%60000/1000) + " seg.";

		return String.valueOf(l/60000*60) + " hr.";
	}

}
