package com.tcl.launcher.json.entry;

import java.io.Serializable;
import java.util.List;

public class CmdSingle extends Page implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	//command值为MOVIE_SHOW_SINGLE时movie有效，值为TVPLAY_SHOW_SINGLE时tvPlay有效
	private Movie movie;
	private TvPlay tvPlay;

	private List<Command> commands; // 当前页面动态指令

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public TvPlay getTvPlay() {
		return tvPlay;
	}

	public void setTvPlay(TvPlay tvPlay) {
		this.tvPlay = tvPlay;
	}
	
	public static CmdSingle Copy(CmdSingle cmdSingle){
		CmdSingle single = new CmdSingle();
		single.setCommand(cmdSingle.getCommand());
		single.setRc(cmdSingle.getRc());
		single.setQuestion(cmdSingle.getQuestion());
		single.setError(cmdSingle.getError());
		single.setMovie(cmdSingle.getMovie());
		single.setTvPlay(cmdSingle.getTvPlay());
		single.setTips(cmdSingle.getTips());
		single.setPc(cmdSingle.getPc());
		single.setPn(cmdSingle.getPn());
		single.setPs(cmdSingle.getPs());
		single.setTotal(cmdSingle.getTotal());
		single.setCommands(cmdSingle.getCommands());
		single.setDomain(cmdSingle.getDomain());
		return single;
	}
}
