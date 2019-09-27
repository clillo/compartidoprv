package com.previred.utils;

import java.io.File;

/**
 * clase con metodos comunes para el proceso de rendicion.
 * 
 * @author clillo
 */
public class UtilesProcesos {

	private static final int[] primos = new int []{419, 211, 367, 239, 599, 167, 587, 673, 241, 89};
	private static final long[] pot10 = new long []{1000000000l, 100000000l, 10000000l, 1000000l, 100000l, 10000l, 1000l, 100l, 10l, 1l};

	/**
	 * Método que se usa para dejar todos los ruts pagador en el mismo thread.
	 * @param n
	 * @return
	 * @author clillo
	 */
	public static int hashRut(int n, int nroThreads){
		long hash = 0;
		byte[] b = new byte[10];
			
		long nn = n;
		for (int i = 0; i < 10; i++){
			b[i] = (byte)(nn / pot10[i]);
			nn = nn%pot10[i];
		}
		
		for (int i = 0; i < 10; i++) 
			hash += b[i] * primos[i];
		
		return (int) (hash%nroThreads);
	}
	
	/**
	 * permite eliminar los archivos de control que comienzen con cierto nombre de una ruta especifica.
	 * 
	 * @param ruta, directorio a limpiar.
	 * @param prefijo, nombre con el cual comienza el archivo 
	 * @author ralfaro
	 */
	
	public static void limpiaDirectorio(String ruta, String prefijo){
		File dir = new File(ruta);
		String[] archivos = dir.list();
		if(archivos != null){
			for (String file : archivos) {
				if (file.startsWith(prefijo) && file.endsWith("ctrl")){
					new File(ruta+'/'+file).delete();
				}
			}
		}
	}
}
