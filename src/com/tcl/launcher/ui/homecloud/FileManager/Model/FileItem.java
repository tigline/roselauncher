package com.tcl.launcher.ui.homecloud.FileManager.Model;

import com.tcl.launcher.ui.homecloud.FileManager.Model.FileModels.FileType;

public class FileItem {

	public String path;
	public String name;
	public FileType type;
	public long size;
	public String uploadtime;
	public boolean isNew;
	public String author;

	public FileItem() {
		type = FileType.Document;
		name = "unknown";
		path = "unknown";
		author = "unknown";
		uploadtime = "unknown";
		size = 0;
		isNew = false;
	}
}
