package com.tcl.launcher.ui.homecloud.ui;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import android.os.Environment;

public class FileUtil {
	public static String formetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = fileS + " B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + " K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + " M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + " G";
		}
		return fileSizeString;
	}

//	public static void writeLog(String fileName,String str) {
//		File file = new File(Environment.getExternalStorageDirectory(),
//				fileName);
//		if (!file.exists()) {
//			try {
//				file.createNewFile();
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		FileOutputStream outputStream;
//		try {
//			outputStream = new FileOutputStream(file);
//			outputStream.write(str.getBytes());
//			outputStream.close();
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}

	public static void WriteConfInfo(String filePath,String context) {
		File file = new File(filePath);
//		Environment.getExternalStorageDirectory(),fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file);
			outputStream.write(context.getBytes());
			outputStream.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static String readLog(String filePath){
		File file = new File(filePath);
		FileInputStream inputStream;
		String storage_Info = null;
		try {
			inputStream = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			for (int i = 0; i < buffer.length; i++) {
				buffer[i] = (byte) inputStream.read();
			}
			inputStream.close();
			storage_Info = new String(buffer);
			System.out.println("----读取的内容为----：" + storage_Info); // 把byte数组变为字符串输出
			return storage_Info;
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return storage_Info;
	}
	
	public static String combinPath(String path, String fileName) {
		return path + (path.endsWith(File.separator) ? "" : File.separator)
				+ fileName;
	}
}
