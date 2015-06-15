package com.tcl.launcher.base;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;

import com.tcl.launcher.LauncherApplication;
import com.tcl.launcher.R;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.launcher.core.VoiceControl;
import com.tcl.launcher.entry.Instructions;
import com.tcl.launcher.json.entry.Page;
import com.tcl.launcher.util.TLog;
import com.tcl.launcher.widget.FloatView;
import com.tcl.launcher.widget.VoiceBar;
import com.tcl.launcher.widget.VoiceDialog;

/**
 * @author caomengqi/caomengqi@tcl.com 2015年5月8日
 * @JDK version 1.8
 * @brief 带有语音控制条的activity，有语音输入时，控制条出现，一段时间内无语音出入时，控制条消失
 * @version
 */
public class VoiceBarActivity extends BaseActivity {
	private static final String TAG = "VoiceBarActivity";

	// private VoiceBar mVoiceBar; // 语音条
	private List<Instructions> mLocalInstructions;
	protected LauncherApplication mApplication;
	private OnFinishCallBack mOnFinishCallBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = (LauncherApplication) getApplication();
	}

	@Override
	protected void onStart() {
		super.onStart();
		TLog.i(TAG, "onStart");
		mApplication.setLastActivity(this);
		Bundle b = getIntent().getExtras();
		Page page = null;
		if (b != null && (page = (Page) b.getSerializable(VoiceControl.CMD_PAGE)) != null) {
			mApplication.url_parameter.setDomain(page.getDomain());
			setPageId(getPageIdFromCommand(page.getCommand()));
			TLog.i(TAG, "set pageId and Domain : " + page.getDomain());
		}else{
			mApplication.url_parameter.setDomain("");
			setPageId("");
			TLog.i(TAG, "no pageId and domain");
		}
		mApplication.setmVoiceCommandCallback(null);
		if(mApplication.isSpeaking()){
			mApplication.showVoiceBar();
		}
	}


	@Override
	protected void onStop() {
		super.onStop();
	}
	
	// private ViewGroup getContentView() {
	// ViewGroup view = (ViewGroup) getWindow().getDecorView();
	// return view;
	// }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 根据命令获取当前页面的pageId并保存到application当中供语音请求作为上下文使用
	 * @param command
	 * @return
	 */
	private String getPageIdFromCommand(String command) {
		if(CommandConstants.MOVIE_SHOW_LIST.equals(command)){
			return CommandConstants.PAGE_ID_MOVIE_SHOW_LIST;
		}else if(CommandConstants.MOVIE_SHOW_SINGLE.equals(command)){
			return CommandConstants.PAGE_ID_MOVIE_SHOW_SINGLE;
		}else if(CommandConstants.MOVIE_PLAY.equals(command)){
			return CommandConstants.PAGE_ID_MOVIE_PLAY;
		}else if(CommandConstants.TVPLAY_SHOW_LIST.equals(command)){
			return CommandConstants.PAGE_ID_TVPLAY_SHOW_LIST;
		}else if(CommandConstants.TVPLAY_SHOW_SINGLE.equals(command)){
			return CommandConstants.PAGE_ID_TVPLAY_SHOW_SINGLE;
		}else if(CommandConstants.TVPLAY_PLAY.equals(command)){
			return CommandConstants.PAGE_ID_TVPLAY_PLAY;
		}else if(CommandConstants.AIR_CONDITION_REQ_HISTORY.equals(command)){
			return CommandConstants.AIR_CONDITION_REQ_HISTORY;
		}else if(CommandConstants.HOME_CLOUD_OPEN_CAMERA.equals(command)){
			return CommandConstants.PAGE_ID_CAMERA_PAGE;
		}else if(CommandConstants.HOME_CLOUD_SEARCH_PHOTO.equals(command)){
			return CommandConstants.PAGE_ID_PHOTO;
		}else if(CommandConstants.HOME_CLOUD_PLAY_SELECT_PHOTO.equals(command)){
			return CommandConstants.PAGE_ID_PHOTO;
		}else if(CommandConstants.HOME_CLOUD_PLAY_LAST_PHOTO.equals(command)){
			return CommandConstants.PAGE_ID_CAMERA_PAGE;
		}
		
		return null;
	}
	
	@Override
	public void finish() {
		if(mOnFinishCallBack != null){
			mOnFinishCallBack.onFinish();
		}
		super.finish();
	}

	protected void setPageId(String pageId){
		mApplication.url_parameter.setPageid(pageId);
	}
	
	public void showTips(String string){
		VoiceDialog dialog = new VoiceDialog(this);
		dialog.showLightViews(VoiceDialog.DIALOG_ERROR, string, 0);
	}
	
	public void setmOnFinishCallBack(OnFinishCallBack mOnFinishCallBack) {
		this.mOnFinishCallBack = mOnFinishCallBack;
	}
	
	public interface OnFinishCallBack{
		public void onFinish();
	}
}
