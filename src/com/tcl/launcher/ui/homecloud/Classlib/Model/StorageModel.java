package com.tcl.launcher.ui.homecloud.Classlib.Model;

public class StorageModel {

	private String name;
	
	private String path;
	
	private String used_space;
	
	private String total_space;
	
	private int used_percent ;
	
	private boolean isusing;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUsed_space() {
		return used_space;
	}

	public void setUsed_space(String used_space) {
		this.used_space = used_space;
	}

	public String getTotal_space() {
		return total_space;
	}

	public void setTotal_space(String total_space) {
		this.total_space = total_space;
	}

	public boolean isIsusing() {
		return isusing;
	}

	public void setIsusing(boolean isusing) {
		this.isusing = isusing;
	}

	public int getUsed_percent() {
		return used_percent;
	}

	public void setUsed_percent(int used_percent) {
		this.used_percent = used_percent;
	}
	
	
}
