package com.tcl.launcher.ui.homecloud.FileManager.Receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.util.Log;

import com.tcl.launcher.ui.homecloud.Classlib.Const.Const;
import com.tcl.launcher.ui.homecloud.Classlib.Const.Const.CapacityType;
import com.tcl.launcher.ui.homecloud.Classlib.Utils.SysUtils;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileMessage;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileManager;
import com.tcl.launcher.ui.homecloud.ui.FileUtil;


public class FileStorageReceiver extends BroadcastReceiver {

	private IntentFilter m_filter = new IntentFilter();
	private Context m_context;

	public FileStorageReceiver(Context context) {
		m_context = context;
		m_filter.addAction(Intent.ACTION_MEDIA_MOUNTED);		
		m_filter.addAction(Intent.ACTION_MEDIA_REMOVED);	
		m_filter.addAction(Intent.ACTION_MEDIA_EJECT);
		m_filter.addDataScheme("file");
	}

	public Intent registerReceiver() {
		return m_context.registerReceiver(this, m_filter);
	}

	public void unregisterReceiver() {
		m_context.unregisterReceiver(this);
	}

	@Override
	public void onReceive(Context context, Intent intent) {			

		String current_index = FileUtil.readLog(SysUtils.Instance().getBoaRootPath()+"/INFORMATION.txt");
		if ((current_index == null) || current_index.equals("null")) {
			current_index = "0";
		}
		if(intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED) || intent.getAction().equals(Intent.ACTION_MEDIA_EJECT)){
			try{
				Thread.sleep(2000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		String sdcard = do_exec("df", Const.SD_DIR_FILTER);
		String usb = do_exec("df", Const.USB_DIR_FILTER);
//		String sdcard = ApplicationExceptionCatch.getInstance().getSdcardDir();
//		String usb = ApplicationExceptionCatch.getInstance().getUsbDir();

		if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {

			boolean isSDcardInjected = current_index.equalsIgnoreCase("1")
					&& sdcard != null;
			boolean isUsbInjected = current_index.equalsIgnoreCase("2")
					&& usb != null;
			if (isSDcardInjected || isUsbInjected) {
				Log.e("@@@@@", " device injected");
				FileManager.getInstance().reInit();
			}

		} else if (intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED) || intent.getAction().equals(Intent.ACTION_MEDIA_EJECT)) {			
			boolean isUsbEjected = current_index.equalsIgnoreCase("2")
					&& usb == null;
			boolean isSDcardEjected = current_index.equalsIgnoreCase("1") && sdcard == null;
			if (isUsbEjected || isSDcardEjected) {
				Log.e("@@@@@", " usb ejected");				

				new Thread() {
					@Override
					public void run() {
						resetStorage();
					}

				}.start();
				
//				StorageMessageBox msgBox = new StorageMessageBox(context);
//				msgBox.ShowMessageBox();
			}
		}
		/*
		else if (intent.getAction().equals(Intent.ACTION_MEDIA_EJECT)) {
			boolean isSDcardEjected = current_index.equalsIgnoreCase("1") && sdcard == null;			
			if (isSDcardEjected) {
				Log.e("@@@@@", " sd card ejected");			

				new Thread() {
					@Override
					public void run() {
						resetStorage();
					}

				}.start();
				
				
//				StorageMessageBox msgBox = new StorageMessageBox(context);
//				msgBox.ShowMessageBox();
			}
		}*/

	}

	public String do_exec(String cmd, String filter) {
		BufferedReader in = null;
		String result = "";
		String line = "";
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			in = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));

			while ((line = in.readLine()) != null
					&& line.contains(filter) == false) {
			}
			in.close();
			result = line;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		if (result != null) {
			Pattern pattern = Pattern.compile("[ ]+");
			String[] strs = pattern.split(result);

			System.out.println();
			return strs[0];
		}
		return null;
	}

	private void resetStorage() {		
		String memory;
		memory = SysUtils.Instance().sdcardPath;

		if (memory != null) {
			Log.e("@@@@", memory);
			SysUtils.Instance().setBoaVirtualRootDir(memory + "/tcloudboa");
			SysUtils.Instance().getDeviceModel()
					.setDevice_type(CapacityType.memory_card);
			FileUtil.WriteConfInfo(SysUtils.Instance().getBoaRootPath()+"/INFORMATION.txt", "0"); // flash
			SharedPreferences preference = m_context.getSharedPreferences(
					Const.USER_INFO, Context.MODE_PRIVATE);
			Editor editor = preference.edit();
			editor.putString("boaVirtualDir", SysUtils.Instance()
					.getBoaVirtualRootDir());
			editor.putString("storageDir", memory);
			editor.commit();

//			CommandUtils.Instance().RestartBoa(m_context);		
			//
			FileManager.getInstance().reInit();		
			
			Intent intent = new Intent(FileMessage.MSG_STORAGE_CHANGED);	
			m_context.sendBroadcast(intent);		
		}
	}

}