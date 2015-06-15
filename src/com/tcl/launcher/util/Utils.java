package com.tcl.launcher.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

public class Utils {
	public static final String NULL = "null";

	public static void setSystemVolume(Context context) {

	}

	public static int getSystemVolume(Context context) {
		return 1;
	}

	/**
	 * 判断当前字符串是否为空
	 */
	public static boolean isNotEmpty(String str) {
		if (null != str && str.trim().length() > 0 && !NULL.equalsIgnoreCase(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 去标点符号
	 * 
	 * @param str
	 * @return
	 */
	public static String punctuation(String str) {
		if (!isNotEmpty(str)) {
			return null;
		} else {
			Pattern p = Pattern.compile("[.,。，！？“”*：；\"\\?!:']");// 增加对应的标点

			Matcher m = p.matcher(str);

			return m.replaceAll(""); // 把标点符号替换成空，即去掉英文标点符号
		}
	}

}
