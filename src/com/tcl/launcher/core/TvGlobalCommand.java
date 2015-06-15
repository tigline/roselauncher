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
public class TvGlobalCommand {
	public static final String TAG = "TvGlobalCommand";
	
	public static final int VOLUME_ADD = 1;
	public static final int VOLUME_SUB = 2;
	
	private int mCurrentVolume = 0;
	
	private LauncherApplication mApplication;
	private AudioManager mAudioManager;
	
	public TvGlobalCommand(LauncherApplication application){
		mApplication = application;
		mAudioManager = (AudioManager) application.getSystemService(Context.AUDIO_SERVICE);
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
			setVolume(VOLUME_ADD);
		}else if(CommandConstants.TV_VOL_LOWER.equals(command)){
			setVolume(VOLUME_SUB);
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

	private void setVolume(int volumeAdd) {
		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		//TLog.i(TAG, "max Volume : " + maxVolume + ", current Volume :" + currentVolume);
		if(volumeAdd == VOLUME_ADD){
			mCurrentVolume += 5;
			if(mCurrentVolume > maxVolume){
				mCurrentVolume = maxVolume;
			}
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
		}else if(volumeAdd == VOLUME_SUB){
			mCurrentVolume -= 5;
			if(mCurrentVolume < 0){
				mCurrentVolume = 0;
			}
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
		}
		
		mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		showVolumeDialog(maxVolume, mCurrentVolume);
	}
	
	
	
	private void showVolumeDialog(int maxVolume, int currentVolume) {
		VoiceBarActivity activity = mApplication.getLastActivity();
		VoiceDialog dialog = new VoiceDialog(activity);
		dialog.showLightViews(VoiceDialog.DIALOG_SOUND, null, new int[]{maxVolume, currentVolume});
	}
	
}
