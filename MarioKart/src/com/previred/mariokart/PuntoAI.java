package com.previred.mariokart;

public class PuntoAI {

	private String id;
	private String aipoint;
	private String aipointx;
	private String aipointy;
	private String speedinc;
	private String rotincdir;
	
	public void setId(String id) {
		this.id = id;
	}
	public void setAipoint(String aipoint) {
		this.aipoint = aipoint;
	}
	public void setAipointx(String aipointx) {
		this.aipointx = aipointx;
	}
	public void setAipointy(String aipointy) {
		this.aipointy = aipointy;
	}
	public void setSpeedinc(String speedinc) {
		this.speedinc = speedinc;
	}
	public void setRotincdir(String rotincdir) {
		this.rotincdir = rotincdir;
	}
	
	
	public String toJson() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\" :");
		builder.append(id);
		builder.append(", \"aipoint\" :");
		builder.append(aipoint);
		builder.append(", \"aipointx\" :");
		builder.append(aipointx);
		builder.append(", \"aipointy\" :");
		builder.append(aipointy);
		builder.append(", \"speedinc\" :");
		builder.append(speedinc);
		builder.append(", \"rotincdir\" :");
		builder.append(rotincdir);
		builder.append("}");
		return builder.toString();
	}
	
	
}
