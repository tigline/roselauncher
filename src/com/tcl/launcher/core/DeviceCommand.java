package com.tcl.launcher.core;

import com.tcl.launcher.LauncherApplication;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.util.TLog;
import com.tcl.launcher.widget.VoiceDialog;
import com.tcl.rosesmarthome.SmartHome;

/**
 * @author caomengqi/caomengqi@tcl.com 2015年5月28日
 * @JDK version 1.8
 * @brief TV全局控制的指令
 * @version
 */
public class DeviceCommand {
	
	public static final String TAG = "DeviceCommand";

	private LauncherApplication mApplication;
	private SmartHome mSmartHome;

	public DeviceCommand(LauncherApplication application, SmartHome mSmartHome) {
		mApplication = application;
		this.mSmartHome = mSmartHome;
	}

	public void exCuteCommand(CmdCtrl cmdCtrl) {
		String command = cmdCtrl.getCommand();
		TLog.i(TAG, "control command : " + command);
		int result = mSmartHome.setSmartHomeCommand(command, 0);
		TLog.i(TAG, "command result : " + result);
		if(result == CommandConstants.COMMAND_EXCUTE_OK){
			VoiceDialog dialog = new VoiceDialog(mApplication.getLastActivity());
			if(CommandConstants.DEVICE_ONE_POWER_OPEN.equals(command)){
				dialog.showLightViews(VoiceDialog.DIALOG_LIGHT, command);
			}else if(CommandConstants.DEVICE_ONE_POWER_CLOSE.equals(command)){
				dialog.showLightViews(VoiceDialog.DIALOG_LIGHT, command);
			}
		}
	}

}
