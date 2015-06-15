package com.tcl.launcher.json.entry;

import java.io.Serializable;
import java.util.List;

public class MutiCmd extends Page implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String[] commands;
	
	List<CmdCtrl> datas;
	
	public String[] getCmds() {
		return commands;
	}

	public void setCmds(String[] commands) {
		this.commands = commands;
	}
	
	public List<CmdCtrl> getDatas() {
		return datas;
	}

	public void setDatas(List<CmdCtrl> datas) {
		this.datas = datas;
	}
	
}
