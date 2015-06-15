package com.tcl.launcher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

import com.tcl.launcher.util.TLog;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

/**
 * @author caomengqi
 * 2015年2月10日
 * @JDK version 1.8
 * @brief handler to record uncaught exception
 * @version
 */
public class CrashHandler implements UncaughtExceptionHandler{
	
	private static CrashHandler handler;
	private LauncherApplication mContext;

	private CrashHandler() {

	}

	public static CrashHandler getInstance() {
		if (handler == null) {
			handler = new CrashHandler();
		}
		return handler;
	}
	
	/**
	 * 初始化
	 * @param context
	 */
	public void init(LauncherApplication context) {
		Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
		mContext = context;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		LauncherApplication application = mContext;
		String logDir = null;
		if (Environment.getExternalStorageDirectory() != null) {
			logDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "TCL" + File.separator + "log";

			File file = new File(logDir);
			boolean mkSuccess;
			if (!file.isDirectory()) {
				mkSuccess = file.mkdirs();
				if (!mkSuccess) {
					mkSuccess = file.mkdirs();
				}
			}
			try {
				FileWriter fw = new FileWriter(logDir + File.separator + "error.log", true);
				fw.write(new Date() + "\n");
				StackTraceElement[] stackTrace = arg1.getStackTrace();
				fw.write(arg1.getMessage() + "\n");
				TLog.e("crash", arg1.getMessage());
				for (int i = 0; i < stackTrace.length; i++) {
					fw.write("file:" + stackTrace[i].getFileName() + " class:" + stackTrace[i].getClassName() + " method:" + stackTrace[i].getMethodName() + " line:" + stackTrace[i].getLineNumber() + "\n");
					TLog.e("crash", stackTrace[i].getFileName() + " class:" + stackTrace[i].getClassName() + " method:" + stackTrace[i].getMethodName() + " line:" + stackTrace[i].getLineNumber());
				}
				fw.write("\n");
				fw.close();
			} catch (IOException e) {
				
			}
		}

		ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		manager.killBackgroundProcesses(mContext.getPackageName());
		manager.restartPackage(mContext.getPackageName());
		
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(startMain);
			System.exit(0);
		} else {// android2.1
			ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
			am.restartPackage(mContext.getPackageName());
		}
	}
	
}
