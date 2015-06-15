package com.tcl.launcher.json.entry;

import java.io.Serializable;

public class Page implements Serializable {
	// 根部结构：rc, error, question, command, data, semantic, ad, tips
	// data结构：domain, total, pn, ps, pc, result, dynamicCommand, pars

	private static final long serialVersionUID = 4626467039752331354L;

	private String rc;
	
	private String error;
	
	private String question;
	
	private String command;
	
	private String tips;
	

	private String domain;

	private int total;

	private int pn;

	private int ps;

	private int pc;
	

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getPn() {
		return pn;
	}

	public void setPn(int pn) {
		this.pn = pn;
	}

	public int getPs() {
		return ps;
	}

	public void setPs(int ps) {
		this.ps = ps;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
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

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}
