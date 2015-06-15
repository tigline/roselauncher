package com.tcl.launcher.ui.homecloud.FileManager.Utils;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class FileUtils {
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static String getLastModifyTime(long modTime) {	
		return dateFormat.format(new Date(modTime));
	}

	public static String combinePath(String path1, String path2) {
		if (path1.endsWith(File.separator))
			return path1 + path2;

		return path1 + File.separator + path2;
	}
	
	public static boolean isHidenDir(String path) {
		File file = new File(path);
		if(file.isDirectory() && file.isHidden())
		{
			return true;
		}
		
		return false;
		
	}
	
	
    public static String getExtFromFilename(String filename) {
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(dotPosition + 1, filename.length());
        }
        return "";
    }

	

}
