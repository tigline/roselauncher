package com.tcl.launcher.json.entry;

import java.io.Serializable;
import java.util.HashMap;

public class Command implements Serializable {
	private static final long serialVersionUID = 1L;

	// dynamicCommand结构：statement, command, pars(key+value), matchtype
	private String command;

	private HashMap<String, String> pars;

	private String statement;

	private String matchtype;

	public String getCommand() {
		return command;
	}

	public String getMatchtype() {
		return matchtype;
	}

	public HashMap<String, String> getPars() {
		return pars;
	}

	public void setPars(HashMap<String, String> pars) {
		this.pars = pars;
	}

	public void setMatchtype(String matchtype) {
		this.matchtype = matchtype;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
}
