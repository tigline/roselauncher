package com.tcl.launcher.entry;

import java.io.Serializable;

public class RoseResultVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rc;
	private String error;
	private String question;
	private Object command;
	private Object data;
	private Object semantic;
	private Object ad;
	private Object tips;

	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

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

	public Object getCommand() {
		return command;
	}

	public void setCommand(Object command) {
		this.command = command;
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

	public Object getAd() {
		return ad;
	}

	public void setAd(Object ad) {
		this.ad = ad;
	}

	public Object getTips() {
		return tips;
	}

	public void setTips(Object tips) {
		this.tips = tips;
	}

}
