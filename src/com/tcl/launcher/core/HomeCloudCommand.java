package com.tcl.launcher.core;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.tcl.launcher.LauncherApplication;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.ui.HelpActivity;
import com.tcl.launcher.ui.MainActivity;
import com.tcl.launcher.util.TLog;
import com.tcl.launcher.widget.VoiceDialog;

/**
 * @author caomengqi/caomengqi@tcl.com
 * 2015年5月28日
 * @JDK version 1.8
 * @brief  TV全局控制的指令
 * @version
 */
public class HomeCloudCommand {
	public static final String TAG = "HomeCloudCommand";
	
	private LauncherApplication mApplication;
	
	public HomeCloudCommand(LauncherApplication application){
		mApplication = application;
	}
	
	public void exCuteCommand(CmdCtrl cmdCtrl){
		String command = cmdCtrl.getCommand();
		if(CommandConstants.TV_GO_OK.equals(command)){
			
		}else if(CommandConstants.TV_GO_BACK.equals(command)){
			TLog.i(TAG, "TV_GO_BACK");
			VoiceBarActivity activity = mApplication.getLastActivity();
			if(activity != null){
				activity.onBackPressed();
			}
		}else if(CommandConstants.TV_GO_UP.equals(command)){
			
		}else if(CommandConstants.TV_GO_LEFT.equals(command)){
			
		}else if(CommandConstants.TV_GO_RIGHT.equals(command)){
			
		}else if(CommandConstants.TV_GO_DOWN.equals(command)){
			
		}else if(CommandConstants.TV_GO_HOME.equals(command)){
			TLog.i(TAG, "TV_GO_HOME");
			Intent intent = new Intent();
			intent.setClass(mApplication.getLastActivity(), MainActivity.class);
			mApplication.getLastActivity().startActivity(intent);
		}else if(CommandConstants.TV_VOL_ADD.equals(command)){
		}else if(CommandConstants.TV_VOL_LOWER.equals(command)){
		}else if(CommandConstants.TV_VOL_MUTE_OFF.equals(command)){
			
		}else if(CommandConstants.TV_VOL_MUTE_ON.equals(command)){
			
		}else if(CommandConstants.TV_SOUNDTRACK.equals(command)){
			
		}else if(CommandConstants.TV_SOUND_MODE_SET.equals(command)){
			
		}else if(CommandConstants.TV_AROUND_SOUND_PRO_SET.equals(command)){
			
		}else if(CommandConstants.TV_OPEN_MENU.equals(command)){
			
		}else if(CommandConstants.TV_START_UP.equals(command)){
			
		}else if(CommandConstants.TV_START_APP.equals(command)){
			
		}else if(CommandConstants.TV_SHUTDOWN.equals(command)){
			
		}else if(CommandConstants.TV_GO_HELP.equals(command)){
			mApplication.getLastActivity().openActivity(HelpActivity.class);
		}
	}
}
