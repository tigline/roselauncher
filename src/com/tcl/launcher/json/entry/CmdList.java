package com.tcl.launcher.json.entry;

import java.io.Serializable;
import java.util.List;

public class CmdList extends Page implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<VodBrief> vods;

	private List<Command> commands; // 当前页面动态指令
	

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}


	public List<VodBrief> getVods() {
		return vods;
	}

	public void setVods(List<VodBrief> vods) {
		this.vods = vods;
	}

}
