package com.tcl.launcher.ui.homecloud.FileManager.Utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tcl.launcher.ui.homecloud.Classlib.Const.Const;

public class Utils {

	public static boolean isServiceRunning(Context context, String name) {
		boolean bRunning = false;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> infos = am.getRunningServices(1000);
		for (RunningServiceInfo info : infos) {
			if (info.service.getClassName().equalsIgnoreCase(name)) {
				bRunning = true;
				break;
			}
		}
		return bRunning;
	}

	public static boolean startService(Context context, String name) {
		boolean bRunning = false;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> infos = am.getRunningServices(1000);
		for (RunningServiceInfo info : infos) {
			if (info.service.getClassName().equalsIgnoreCase(name)) {
				bRunning = true;
				break;
			}
		}
		return bRunning;
	}

//	public static String getBoaVirtualRootDir() {
//		SharedPreferences preference = ApplicationExceptionCatch.getInstance()
//				.getApplicationContext()
//				.getSharedPreferences(Const.USER_INFO, Context.MODE_PRIVATE);
//		String boaVirtualRootDir = preference.getString("boaVirtualDir",
//				ApplicationExceptionCatch.getInstance().getMemoryDir() + "/tcloudboa");
//		return boaVirtualRootDir;
//
//	}

	public static void sendBroadcastMessage(String msg) {

//		Intent intent = new Intent(msg);
//		ApplicationExceptionCatch.getInstance().getApplicationContext()
//				.sendBroadcast(intent);
	}
	
	public static String getSortType()
	{
//		SharedPreferences preference = ApplicationExceptionCatch.getInstance()
//				.getApplicationContext()
//				.getSharedPreferences(Const.USER_INFO, Context.MODE_PRIVATE);
//		String sortType = preference.getString("sortType", "name");
		return "name";	
	}
	
	public static void setSortType(String type)
	{
//		SharedPreferences preference = ApplicationExceptionCatch.getInstance()
//				.getApplicationContext()
//				.getSharedPreferences(Const.USER_INFO, Context.MODE_PRIVATE);
//		Editor editor = preference.edit();
//		editor.putString("sortType", type);
//		editor.commit();	
	}
}
