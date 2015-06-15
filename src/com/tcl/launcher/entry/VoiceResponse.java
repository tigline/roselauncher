package com.tcl.launcher.entry;

public class VoiceResponse {
	private String rc;
	private String error;
	private String question;
	private String commmand;
	private Object data;
	private Object semantic;
	private String ad;
	private String tips;
	
	public String getRc() {
		return rc;
	}
	public void setRc(String rc) {
		this.rc = rc;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCommmand() {
		return commmand;
	}
	public void setCommmand(String commmand) {
		this.commmand = commmand;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getSemantic() {
		return semantic;
	}
	public void setSemantic(Object semantic) {
		this.semantic = semantic;
	}
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	
}
