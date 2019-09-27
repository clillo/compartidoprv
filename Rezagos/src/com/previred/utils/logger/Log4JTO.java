package com.previred.utils.logger;

import java.io.Serializable;


/**
 * clase para el manejo de los datos de creacion del log
 * @author ralfaro
 *
 */
public class Log4JTO implements Serializable {

	private static final long serialVersionUID = -8724153530867196299L;
	private String id;
	private String logdir;
	private String logtype;
	private String level;
	private String maxfilesize;
	private String maxbackupindex;
	private String indentation;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogdir() {
		return logdir;
	}
	public void setLogdir(String logdir) {
		this.logdir = logdir;
	}
	public String getLogtype() {
		return logtype;
	}
	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMaxfilesize() {
		return maxfilesize;
	}
	public void setMaxfilesize(String maxfilesize) {
		this.maxfilesize = maxfilesize;
	}
	public String getMaxbackupindex() {
		return maxbackupindex;
	}
	public void setMaxbackupindex(String maxbackupindex) {
		this.maxbackupindex = maxbackupindex;
	}
	public String getIndentation() {
		return indentation;
	}
	public void setIndentation(String indentation) {
		this.indentation = indentation;
	}
	
	/**
	 * envia al log los datos con los cual se generan
	 * @return
	 */
	public String toLog(){
		StringBuilder sb = new StringBuilder();
		sb.append("id:[");
		sb.append(id);
		sb.append("] logdir:[");
		sb.append(logdir);		
		sb.append("] logtype:[");
		sb.append(logtype);
		sb.append("] level:[");
		sb.append(level);
		sb.append("] maxfilesize:[");
		sb.append(maxfilesize);
		sb.append("] maxbackupindex:[");
		sb.append(maxbackupindex);
		sb.append("] indentation:[");
		sb.append(indentation);
		sb.append(']');
		return sb.toString();
	}
}
