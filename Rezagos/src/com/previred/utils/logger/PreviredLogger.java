package com.previred.utils.logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;

/**
 * clase para el manejo de los logs aplicativos.
 *
 * @author ralfaro
 *
 */
public class PreviredLogger implements Serializable{

	private static final long serialVersionUID = -6462409092882664900L;
	private static HashMap<String, Logger> listaLoggers;
	private static final String LOG4J_PATTERN = "[%d{ISO8601}] %m%n";
	private static String indentor;
	private static String aplicacionPorDefecto;

	private final boolean tieneAplicacionDefecto;
	private final String aplicacionDefectoInstancia;

	static{
		System.out.println("com.previred.utils.logs.PreviredLogger Inicializando Pool de Logs");
		listaLoggers = new HashMap< >();
		aplicacionPorDefecto = null;
		indentor = "";
	}

	/**
	 * constructor de log por defecto
	 */
	public PreviredLogger() {
		aplicacionDefectoInstancia = null;
		tieneAplicacionDefecto = false;
	}

	/**
	 * constructor de log por aplicacion
	 */
	public PreviredLogger(String appPorDefecto) {
		aplicacionDefectoInstancia = appPorDefecto;
		tieneAplicacionDefecto = true;
	}
	/**
	 * metodo para la inicializacion del log
	 * @param to
	 * @param nombreAplicacion
	 * @author pcornejo
	 */

	public static synchronized void init(Log4JTO to, String nombreAplicacion){
	  init(to, nombreAplicacion,null, false, false);
	}
	
	/**
	 * Se agrega por cambio de implementacion del metodo llamado, inicializacion del log
	 * @param to
	 * @param nombreAplicacion
	 * @param porDefecto
	 * @author pcornejo
	 */
	public static synchronized void init(Log4JTO to, String nombreAplicacion, boolean porDefecto) {
		  init(to, nombreAplicacion,null, porDefecto, false);
	}
		
	/**
	 * Se agrega init por cambio de implementacion del metodo llamado, inicializacion del log
	 * @param to
	 * @param nombreAplicacion
	 * @param porDefecto
	 * @param llamadaDesdeConsola
	 * @author pcornejo
	 */
	public static synchronized void init(Log4JTO to, String nombreAplicacion, boolean porDefecto, boolean llamadaDesdeConsola) {
		 init(to, nombreAplicacion,null, porDefecto, llamadaDesdeConsola);
	}
	
	/**
	 * este metodo permite inicializar un log para una aplicacion especifica
	 * @param to, datos con el log a generar
	 * @param nombreAplicacion, nombre de la aplicacion a la cual se le va agenerar el log.
	 * @param porDefecto
	 */
	public static synchronized void init(Log4JTO to, String nombreAplicacion,String patron, boolean porDefecto, boolean llamadaDesdeConsola) {
		String fileName;
		if (to==null){
			System.out.println("Error, no hay una aplicaci�n por defecto: "+nombreAplicacion);
			return;
		}

		if (to.getLogdir().endsWith("\\")){
			fileName = to.getLogdir() + nombreAplicacion + ".log";
		}
		else if (to.getLogdir().endsWith("/")) {
			fileName = to.getLogdir() + nombreAplicacion + ".log";
		} else {
			fileName = to.getLogdir() + "/" + nombreAplicacion + ".log";
		}

		new File(fileName).delete();

		Logger logger;
	    if (porDefecto){
	    	aplicacionPorDefecto = nombreAplicacion;
	    	logger = Logger.getRootLogger();
	    } else {
			logger = Logger.getLogger(nombreAplicacion);
		}
	    
	    Enumeration<?> enu = logger.getAllAppenders();
        if(enu.hasMoreElements() && !(enu.nextElement() instanceof ConsoleAppender)){
              System.out.println("WARNING: Este log "+nombreAplicacion+" ya tiene un appender, se ignora.");
              listaLoggers.put(nombreAplicacion, logger);
              return;
        }

	    logger.setAdditivity(false);

		PatternLayout layout = null;
		if(patron == null){
			layout = new PatternLayout(LOG4J_PATTERN);
		}else{
			layout = new PatternLayout(patron);
		}
		try{
			FileAppender appender;

			appender = new DailyRollingFileAppender(layout, fileName, "'.['dd_MM_yyyy'].txt'" );
//			System.out.println("\t["+nombreAplicacion+"] Log en \""+fileName+"\"\t" + Thread.currentThread().getName());

			logger.removeAllAppenders();
			logger.addAppender(appender);
			if(llamadaDesdeConsola) {
				WriterAppender appender2 = new ConsoleAppender(new PatternLayout());
				logger.addAppender(appender2);
			//	logger.setAdditivity(true);
				logger.addAppender(appender);
			}			
		}catch(IOException e){
			System.out.println("No se pudo inicializar el archivo de la aplicacion ["+nombreAplicacion+"]Log en \""+fileName+"\" se usara la consola");
			System.err.println(e.getMessage());
			WriterAppender appender = new ConsoleAppender(new PatternLayout());
			logger.addAppender(appender);	 
		}
		setLevel(to.getLevel(), logger);
		indentor = to.getIndentation();
		listaLoggers.put(nombreAplicacion, logger);
	}

	/**
	 * cambia el nivel del log
	 * @param level
	 * @param nombreAplicacion
	 */
	public static void setLevel(String level, String nombreAplicacion) {
		Logger logger = listaLoggers.get(nombreAplicacion);
		setLevel(level, logger);
	}

	/**
	 * cambia el nivel del log
	 * @param level
	 * @param logger
	 */
	private static void setLevel(String level, Logger logger) {
		if ("DEBUG".equalsIgnoreCase(level)) {
			logger.setLevel(Level.DEBUG);
		} else if ("INFO".equalsIgnoreCase(level)) {
			logger.setLevel(Level.INFO);
		} else if ("WARN".equalsIgnoreCase(level)) {
			logger.setLevel(Level.WARN);
		} else if ("ERROR".equalsIgnoreCase(level)) {
			logger.setLevel(Level.ERROR);
		} else if ("FATAL".equalsIgnoreCase(level)) {
			logger.setLevel(Level.FATAL);
		} else if ("ALL".equalsIgnoreCase(level)) {
			logger.setLevel(Level.ALL);
		} else if ("OFF".equalsIgnoreCase(level)) {
			logger.setLevel(Level.OFF);
		} else {
			logger.setLevel(Level.INFO);
		}
	}

	private void log(String text, String level, String nombreAplicacion) {
		Logger logger = listaLoggers.get(nombreAplicacion);

		if (logger == null){
			System.out.println("["+nombreAplicacion+"]Se esta tratando de usar el log y no se ha inicializado correctamente");
			logger = Logger.getRootLogger();
		}

		if ("INFO".equalsIgnoreCase(level)) {
			logger.info(text);
		} else if ("DEBUG".equalsIgnoreCase(level)) {
			logger.debug(text);
		} else if ("WARN".equalsIgnoreCase(level)) {
			logger.warn(text);
		} else if ("ERROR".equalsIgnoreCase(level)) {
			logger.error(text);
		} else if ("FATAL".equalsIgnoreCase(level)) {
			logger.fatal(text);
		}
	}

	private void log(String text, String level, String className, String nombreAplicacion) {
		StringBuilder sb = new StringBuilder(indentor);
		sb.append("[");
		sb.append(level);
        sb.append("] [");
        sb.append(className);
        sb.append("]");
        sb.append(" ");
        sb.append(text);

        log(sb.toString(), level, nombreAplicacion);
	}

	private String objectToString(Object o){
		Object objeto = o;
		if (o==null) {
			objeto = this;
		}
		String clase = objeto.getClass().getName();
		clase = clase.substring(clase.lastIndexOf('.')+ 1, clase.length());
		return clase;
	}

	public void info(String toLog, Object o, String nombreAplicacion) {
		log(toLog, "INFO", objectToString(o), nombreAplicacion);
	}

	public void info(String toLog, Object o){
		info(toLog, o, aplicacionPorDefecto);
	}

	public void info(String toLog, String app){
		log(toLog, "INFO", app);
	}

	public void info(String toLog){
		if (tieneAplicacionDefecto) {
			log(toLog, "INFO", aplicacionDefectoInstancia);
		} else {
			log(toLog, "INFO", aplicacionPorDefecto);
		}
	}

	public void debug(String toLog, Object o, String nombreAplicacion) {
		log(toLog, "DEBUG", objectToString(o), nombreAplicacion);
	}

	public void debug(String toLog, Object o){
		debug(toLog, o, aplicacionPorDefecto);
	}

	public void debug(String toLog, String app){
		log(toLog, "DEBUG", app);
	}

	public void debug(String toLog){
		if (tieneAplicacionDefecto) {
			log(toLog, "DEBUG", aplicacionDefectoInstancia);
		} else {
			log(toLog, "DEBUG", aplicacionPorDefecto);
		}
	}

	public void warn(String toLog, Object o, String nombreAplicacion) {
		log(toLog, "WARN", objectToString(o), nombreAplicacion);
	}

	public void warn(String toLog, String claseInvocacion, String nombreAplicacion) {
		log(toLog, "WARN", claseInvocacion, nombreAplicacion);
	}
	
	public void warn(String toLog, Object o){
		warn(toLog, o, aplicacionPorDefecto);
	}

	public void warn(String toLog){
		log(toLog, "WARN", aplicacionPorDefecto);
	}

	public void error(String toLog, Object o, String nombreAplicacion) {
		log(toLog, "ERROR", objectToString(o), nombreAplicacion);
	}

	public void error(String toLog, String claseInvocacion, String nombreAplicacion) {
		log(toLog, "ERROR", claseInvocacion, nombreAplicacion);
	}

	public void error(String toLog, Object o){
		error(toLog, o, aplicacionPorDefecto);
	}

	public void error(String toLog, String app){
		log(toLog, "ERROR", app);
	}

	public void error(String toLog){
		log(toLog, "ERROR", aplicacionPorDefecto);
	}

	public void fatal(String toLog, Object o, String nombreAplicacion) {
		log(toLog, "FATAL", objectToString(o), nombreAplicacion);
	}

	public void fatal(String toLog, Object o){
		fatal(toLog, o, aplicacionPorDefecto);
	}

	public void fatal(String toLog){
		log(toLog, "FATAL", aplicacionPorDefecto);
	}

	public static void stDebug(String toLog, Object o, String nombreAplicacion) {
		stDebug(toLog, o, nombreAplicacion, false);
	}

	public static void stDebug(String toLog, Object o) {
		stDebug(toLog, o, aplicacionPorDefecto, false);
	}

	public static void stDebug(String toLog, Object o, boolean ignoraError) {
		stDebug(toLog, o, aplicacionPorDefecto, ignoraError);
	}

	public static void stDebug(String toLog, Object o, String nombreAplicacion, boolean ignoraError) {
		Logger logger = listaLoggers.get(nombreAplicacion);


		StringBuilder sb = new StringBuilder(indentor);
		sb.append("[DEBUG][");
		String clase = o.getClass().getName();
		clase = clase.substring(clase.lastIndexOf('.') + 1);
        sb.append(clase);
        sb.append("]");
        sb.append(" ");
        sb.append(toLog);

		if (logger == null && !ignoraError){
			System.out.println("["+nombreAplicacion+"] Se esta tratando de usar un log y no se ha inicializado correctamente");
			System.out.println(sb.toString());
			return;
		}

		if (logger!=null) {
			logger.debug(sb.toString());
		}
	}

	public static void stManejaExcepcion(Exception e, String nombreAplicacion){
		stManejaExcepcion(e, nombreAplicacion, false);
	}

	public static void stManejaExcepcion(Exception e, String nombreAplicacion, boolean ignoraError){
		e.printStackTrace();
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		stDebug(sw.toString(), PreviredLogger.class, nombreAplicacion, ignoraError);
	}

	public static void stManejaExcepcion(Exception e){
		stManejaExcepcion(e, aplicacionPorDefecto, false);
	}

	public void manejaExcepcion(Throwable e, String nombreAplicacion){
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		error(sw.toString(), this, nombreAplicacion);
	}

	/**Imprime la causa de una excepcion, sin el stacktrace.
	 * @param e, excepcion a imprimir.
	 * @param o, objeto desde donde se lanza la excepcion.
	 * @param nombreAplicacion, nombre del log a usar.
	 * @author pstewart
	 */
	public void manejaExcepcionCorta(Throwable e, Object o, String nombreAplicacion){
		StringBuilder sb = new StringBuilder(indentor);
		if(!tieneAdditivity(nombreAplicacion)){
			sb.append(Thread.currentThread().toString());
	        sb.append("] Objeto [");
		}else{
	        sb.append("[");
		}
        sb.append(o.getClass().getSimpleName());
        sb.append("]");
        sb.append(" ");
        sb.append(e.getMessage());

		error(sb.toString(), this, nombreAplicacion);
	}

	/**Imprime la causa de una excepcion, sin el stacktrace. Uso en JSF
	 * @param e, excepcion a imprimir.
	 * @param idSesion, id de sesion del usuario.
	 * @param ip, ip del usuario.
	 * @param o, objeto desde donde se lanza la excepcion.
	 * @param nombreAplicacion, nombre del log a usar.
	 * @author pstewart
	 */
	public void manejaExcepcionCorta(Throwable e, String idSesion, String ip, Object o, String nombreAplicacion){
		StringBuilder sb = new StringBuilder(indentor);
		if(!tieneAdditivity(nombreAplicacion)){
			sb.append(Thread.currentThread().toString());
			sb.append("] Sesion [");
	        sb.append(idSesion);
	        sb.append("] IP [");
	        sb.append(ip);
	        sb.append("] Objeto [");
		}else{
	        sb.append("[");
		}
        sb.append(o.getClass().getSimpleName());
        sb.append("]");
        sb.append(" ");
        sb.append(e.getMessage());

		error(sb.toString(), this, nombreAplicacion);
	}

	public void manejaExcepcion(Exception e){
		manejaExcepcion(e, aplicacionPorDefecto);
	}

	/**Debug para ser usado en BOs y aplicaciones Web.
	 * @param toLog
	 * @param rutUsuario
	 * @param o
	 * @param nombreAplicacion
	 * @author pstewart
	 */
	public void debugWeb(String toLog, String rutUsuario, Object o, String nombreAplicacion) {
		StringBuilder sb = new StringBuilder(indentor);
		sb.append(Thread.currentThread().toString());
		sb.append("[");
		sb.append(rutUsuario);
		sb.append("][DEBUG][");
        sb.append(o.getClass().getSimpleName());
        sb.append("]");
        sb.append(" ");
        sb.append(toLog);
        log(sb.toString(), "DEBUG", nombreAplicacion);
	}

	/**INFO para ser usado en BOs y aplicaciones Web.
	 * @param toLog
	 * @param rutUsuario
	 * @param o
	 * @param nombreAplicacion
	 * @author pstewart
	 */
	public void infoWeb(String toLog, String rutUsuario, Object o, String nombreAplicacion) {
		StringBuilder sb = new StringBuilder(indentor);
		sb.append(Thread.currentThread().toString());
		sb.append("[");
		sb.append(rutUsuario);
		sb.append("][INFO][");
        sb.append(o.getClass().getSimpleName());
        sb.append("]");
        sb.append(" ");
        sb.append(toLog);
        log(sb.toString(), "INFO", nombreAplicacion);
	}

	/**INFO para ser usado en BOs y aplicaciones Web.
	 * @param toLog
	 * @param rutUsuario
	 * @param o
	 * @param nombreAplicacion
	 * @author pstewart
	 */
	public void infoWeb(String toLog, String rutUsuario, String ip, Object o, String nombreAplicacion) {
		StringBuilder sb = new StringBuilder(indentor);
		sb.append(Thread.currentThread().toString());
		sb.append("[");
		sb.append(rutUsuario);
		sb.append("][");
		sb.append(ip);
		sb.append("][INFO][");
        sb.append(o.getClass().getSimpleName());
        sb.append("]");
        sb.append(" ");
        sb.append(toLog);
        log(sb.toString(), "INFO", nombreAplicacion);
	}

	/**Agraga un registro en modo INFO para una aplicacion JSF.
	 * @param toLog, registro a loggear.
	 * @param idSesion, id de session del usuario.
	 * @param ip, ip del usuario conectado.
	 * @param o, objeto desde donde se invoca el logger.
	 * @param nombreAplicacion, nombre del log a usar.
	 */
	public void infoJSF(String toLog, String idSesion, String ip,  Object o, String nombreAplicacion) {
		StringBuilder sb = new StringBuilder(indentor);
		if(!tieneAdditivity(nombreAplicacion)){
			sb.append("[INFO] ");
			sb.append(Thread.currentThread().toString());
			sb.append("] Sesion [");
	        sb.append(idSesion);
	        sb.append("] IP [");
	        sb.append(ip);
	        sb.append("] Objeto [");
		}else{
	        sb.append("[");
		}
        sb.append(o.getClass().getSimpleName());
        sb.append("]");
        sb.append(" ");
        sb.append(toLog);
        log(sb.toString(), "INFO", nombreAplicacion);
	}
	
	/**Agraga un registro en modo INFO para una aplicacion JSF.
	 * @param toLog, registro a loggear.
	 * @param idSesion, id de session del usuario.
	 * @param ip, ip del usuario conectado.
	 * @param o, objeto desde donde se invoca el logger.
	 * @param nombreAplicacion, nombre del log a usar.
	 */
	public void infoJSF(String toLog, String idSesion, String ip,  String className, String nombreAplicacion) {
		StringBuilder sb = new StringBuilder(indentor);
		if(!tieneAdditivity(nombreAplicacion)){
			sb.append("[INFO] ");
			sb.append(Thread.currentThread().toString());
			sb.append(" Sesion [");
	        sb.append(idSesion);
	        sb.append("] IP [");
	        sb.append(ip);
	        sb.append("] Objeto [");
		}else{
			sb.append("[INFO] [");
	        sb.append(idSesion);
	        sb.append("] [");
		}
        sb.append(className);
        sb.append("]");
        sb.append(" ");
        sb.append(toLog);
        log(sb.toString(), "INFO", nombreAplicacion);
	}
	
	/**Imprime en el log en modo info, requiere el nombre de la clase (e informacion adicional) 
	 * obtenida dinamicamente desde el logger de donde se invoca.
	 * @param toLog
	 * @param className
	 * @param nombreAplicacion
	 * @author pstewart
	 */
	public void info(String toLog, String className, String nombreAplicacion) {
		StringBuilder sb = new StringBuilder(indentor);
		if(!tieneAdditivity(nombreAplicacion)){
			sb.append("[INFO] ");
			sb.append(Thread.currentThread().toString());
	        sb.append("] Objeto [");
		}else{
			sb.append("[INFO] [");
		}
        sb.append(className);
        sb.append("]");
        sb.append(" ");
        sb.append(toLog);
        log(sb.toString(), "INFO", nombreAplicacion);
	}

	/**Agraga un registro en modo INFO para una aplicacion JSF.
	 * @param toLog, registro a loggear.
	 * @param idSesion, id de session del usuario.
	 * @param ip, ip del usuario conectado.
	 * @param o, objeto desde donde se invoca el logger.
	 * @param nombreAplicacion, nombre del log a usar.
	 */
	public void warnJSF(String toLog, String idSesion, String ip,  String className, String nombreAplicacion) {
		StringBuilder sb = new StringBuilder(indentor);
		if(!tieneAdditivity(nombreAplicacion)){
			sb.append("[WARN] ");
			sb.append(Thread.currentThread().toString());
			sb.append(" Sesion [");
	        sb.append(idSesion);
	        sb.append("] IP [");
	        sb.append(ip);
	        sb.append("] Objeto [");
		}else{
			sb.append("[WARN] [");
	        sb.append(idSesion);
	        sb.append("] [");
		}
        sb.append(className);
        sb.append("]");
        sb.append(" ");
        sb.append(toLog);
        log(sb.toString(), "WARN", nombreAplicacion);
	}

	/**Agraga un registro en modo debug para una aplicacion JSF.
	 * @param toLog, registro a loggear.
	 * @param idSesion, id de session del usuario.
	 * @param ip, ip del usuario conectado.
	 * @param o, objeto desde donde se invoca el logger.
	 * @param nombreAplicacion, nombre del log a usar.
	 */
	public void debugJSF(String toLog, String idSesion, String ip,  Object o, String nombreAplicacion) {
		StringBuilder sb = new StringBuilder(indentor);
		if(!tieneAdditivity(nombreAplicacion)){
			sb.append("[DEBUG] ");
			sb.append(Thread.currentThread().toString());
			sb.append(" Sesion [");
	        sb.append(idSesion);
	        sb.append("] IP [");
	        sb.append(ip);
	        sb.append("] Objeto [");
		}else{
	        sb.append("[");
		}
        sb.append(o.getClass().getSimpleName());
        sb.append("]");
        sb.append(" ");
        sb.append(toLog);
        log(sb.toString(), "DEBUG", nombreAplicacion);
	}

	public static void setAplicacionPorDefecto(String aplicacionPorDefecto) {
		PreviredLogger.aplicacionPorDefecto = aplicacionPorDefecto;
	}

	public static boolean tieneLog(String nombreAplicacion){
		Logger logger = listaLoggers.get(nombreAplicacion);
		return logger != null;
	}

	public static synchronized void init() {
		if (aplicacionPorDefecto==null){
			aplicacionPorDefecto="consola";
			Log4JTO to = new Log4JTO();
			to.setLogdir("/");
			to.setLevel("INFO");
			init(to, aplicacionPorDefecto);
		}
	}
	
	public static synchronized void init(Logger logger) {
		WriterAppender appender = new ConsoleAppender(new PatternLayout());
		logger.addAppender(appender);
	}
	
	/**Log para auditoria. Usar el TO para generar la linea.
	 * @param toLog
	 * @param nombreAplicacion
	 * @author pstewart
	 */
	public void logAuditoria(String toLog, String nombreAplicacion) {
        log(toLog, "INFO", nombreAplicacion);
	}
	
	
	/**Verifica si un logger tiene seteado la propiedad Additivity.<br>
	 * Se usa para verificar si esta corriendo en modo desarrollo o no. 
	 * @param nombreAplicacion
	 * @return
	 * @author pstewart
	 */
	private boolean tieneAdditivity(String nombreAplicacion){
		Logger logger = listaLoggers.get(nombreAplicacion);
		if(logger == null)
			return false;
		return logger.getAdditivity();
	}
	
	
	/**
	 *  Sirve para logear rápido en ambientes batch
	 * @return
	 */
	public Logger obtieneLogBatch(){
		return Logger.getRootLogger();
	}
	
	/**
	 * Agrega el appender console al log para que la salida del log se vaya a la salida estándar
	 * @param nombreAplicacion
	 */
	public void agregarLogAConsola(String nombreAplicacion){
		Logger logger = listaLoggers.get(nombreAplicacion);
		if(logger == null)
			return ;
		WriterAppender appender = new ConsoleAppender(new PatternLayout());
		logger.addAppender(appender);
	}

}
