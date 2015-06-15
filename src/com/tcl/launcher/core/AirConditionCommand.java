package com.tcl.launcher.core;

import android.os.Bundle;
import android.util.Log;

import com.tcl.launcher.LauncherApplication;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.ui.AirConditionInfoActivity;
import com.tcl.launcher.util.TLog;
import com.tcl.launcher.widget.VoiceDialog;
import com.tcl.rosesmarthome.SmartHome;

/**
 * @author caomengqi/caomengqi@tcl.com 2015年5月28日
 * @JDK version 1.8
 * @brief TV全局控制的指令
 * @version
 */
public class AirConditionCommand {
	public static final String TAG = "AirConditionCommand";

	private LauncherApplication mApplication;
	private SmartHome mSmartHome;

	public AirConditionCommand(LauncherApplication application, SmartHome mSmartHome) {
		mApplication = application;
		this.mSmartHome = mSmartHome;
	}

	public void exCuteCommand(CmdCtrl cmdCtrl) {
		String command = cmdCtrl.getCommand();
		TLog.i(TAG, "control command : " + command);
		if (CommandConstants.AIR_CONDITION_OPEN.equals(command)) {
			int reuslt = mSmartHome.setSmartHomeCommand(command, 0);
			checkSuccess(command, reuslt);
		} else if (CommandConstants.AIR_CONDITION_CLOSE.equals(command)) {
			int reuslt = mSmartHome.setSmartHomeCommand(command, 0);
			checkSuccess(command, reuslt);
		} else if (CommandConstants.AIR_CONDITION_TEMP_SET.equals(command)) {
			String tempStr = cmdCtrl.getPars().get("temp");
			if (tempStr != null) {
				TLog.i(TAG, "air con set temp : " + tempStr);
				int temperature = Integer.valueOf(tempStr);
				int reuslt = mSmartHome.setSmartHomeCommand(command, temperature);
				TLog.i(TAG, "aircon set temp result : " + reuslt);
				checkSuccess(command, reuslt, temperature);
			}//HZ SmartHome zgx@tcl.com 2015.6.4
		}else if (CommandConstants.AIR_CONDITION_TEMP_ADD_N.equals(command)) {
			String tempStr = cmdCtrl.getPars().get("temp");
			if (tempStr != null) {
				TLog.i(TAG, "air con add temp : " + tempStr);
				int temperature = Integer.valueOf(tempStr);
				int reuslt = mSmartHome.setSmartHomeCommand(command, temperature);
				TLog.i(TAG, "aircon set temp result : " + reuslt);
			}//HZ SmartHome zgx@tcl.com 2015.6.4
		}else if (CommandConstants.AIR_CONDITION_REQ_HISTORY.equals(command)) {
			Bundle b = new Bundle();
			b.putSerializable(VoiceControl.CMD_PAGE, cmdCtrl);
			mApplication.mVoiceControl.openActivity(mApplication.getLastActivity(), AirConditionInfoActivity.class, b);			
		} else {
			mSmartHome.setSmartHomeCommand(command, 0);
		}
	}
	
	private void checkSuccess(String command, int result, int... params){
		if(result == CommandConstants.COMMAND_EXCUTE_OK){
			VoiceDialog dialog = new VoiceDialog(mApplication.getLastActivity());
			if(CommandConstants.AIR_CONDITION_OPEN.equals(command)){
				dialog.showLightViews(VoiceDialog.DIALOG_AIRCON, command);
			}else if(CommandConstants.AIR_CONDITION_CLOSE.equals(command)){
				dialog.showLightViews(VoiceDialog.DIALOG_AIRCON, command);
			}else if(CommandConstants.AIR_CONDITION_TEMP_SET.equals(command)){
				dialog.showLightViews(VoiceDialog.DIALOG_AIRCON, command, params);
			}
		}
	}
}
