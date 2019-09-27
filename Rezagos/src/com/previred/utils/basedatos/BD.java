package com.previred.utils.basedatos;

/**
 * Enum para el manejo de Bases de Datos
 * 
 * @author pstewart
 */
public enum BD {
	BACK_END			("_bckend",			"BackEnd"),
	BACK_LISTS			("_bcklst",			"Backlists"),
	CUPONES				("_cupones",		"Cupones"),
	GOLIATH				("_gth",			"Goliath"),
	HISTORICO_DNP		("_htdnp",			"Historico DNP"),
	HISTORICO			("_htop",			"Historico Operacional"),
	MAC					("_mac",			"MAC"),
	MAC_LECTURA			("_mac_lectura",	"MAC Lectura"),
	OPERACIONAL			("",				"Previred Operacional"),
	PRIVILEGIOS			("_priv",			"Privilegios"),
	PRIVILEGIOS_SESION	("_priv_sesion",	"Privilegios Sesion"),
	DEUDAS				("_deudas",			"Deudas"),
	NOTIFICACIONES		("_notificaciones",	"Notificaciones"),
	PRUEBAS				("_pruebas",		"Pruebas"),
	PROCESOS			("_procesos",		"Procesos"),
	SESSION_HTTP_NEGOCIO("_sesiones", 		"Previred Sesiones");

	private String cod;
	private String glosa;

	private BD(String cod, String glosa) {
		this.cod = cod;
		this.glosa = glosa;
	}

	/** Obtiene el String de conexion para JNDI */
	public String toString() {
		return cod;
	}

	/** Obtiene la glosa de la BD */
	public String getGlosa() {
		return glosa;
	}

	/**
	 * Obtiene la BD a partir del string de conexion.
	 * 
	 * @param bd
	 * @return
	 */
	public static BD getBDFromCod(String bd) {
		if (bd == null)
			return null;

		for (BD bdTmp : BD.values()) {
			if (bdTmp.toString().equals(bd))
				return bdTmp;
		}
		return null;
	}

	/**
	 * Obtiene la BD a partir del nombre de la base de datos.
	 * 
	 * @param bd
	 * @return
	 */
	public static BD getBDFromGlosa(String bd) {
		if (bd == null)
			return null;

		for (BD bdTmp : BD.values()) {
			if (bdTmp.getGlosa().equalsIgnoreCase(bd))
				return bdTmp;
		}
		return null;
	}
}
