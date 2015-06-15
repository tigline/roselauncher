package com.tcl.launcher.json.entry;

import java.io.Serializable;
import java.util.HashMap;

public class CmdCtrl extends Page implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String, String> pars;

	public HashMap<String, String> getPars() {
		return pars;
	}

	public void setPars(HashMap<String, String> pars) {
		this.pars = pars;
	}

}
