package com.tcl.launcher.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.launcher.LauncherApplication;
import com.tcl.launcher.R;
import com.tcl.launcher.Test;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.launcher.core.VoiceCommandCallback;
import com.tcl.launcher.entry.HostItem;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.ui.fragment.MediaFragment;
import com.tcl.launcher.ui.fragment.SmartHomeFragment;
import com.tcl.launcher.util.TLog;
import com.tcl.launcher.widget.LauncherHost;
import com.tcl.launcher.widget.LauncherHost.onChooseListener;
import com.tcl.launcher.widget.VoiceDialog;

/**
 * @author caomengqi/caomengqi@tcl.com 2015年5月8日
 * @JDK version 1.8
 * @brief 主界面
 * @version
 */
public class MainActivity extends VoiceBarActivity implements onChooseListener {
	private static final String TAG = "MainActivity";

	public static final String ACTION_SHOW_VOICE_BAR = "show_voice_bar";
	public static final String ACTION_HIDE_VOICE_BAR = "hide_voice_bar";
	
	

	private LauncherHost mHost;
	private FragmentManager mFragmentManager;

	private TextView mSpriteTextView;
	private ImageView mSpriteImageView;
	
	private AnimationDrawable mAnimationDrawable;
	
	private Button mHelp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFragmentManager = getFragmentManager();
		findViews();

		initFragment(mFragmentManager);
		mApplication.init();

		getScreenInfo();
	}

	public void getScreenInfo() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		TLog.i(TAG, "width : " + dm.widthPixels + ", height : " + dm.heightPixels + "density,"
				+ dm.density + "densityDpi" + dm.densityDpi);
	}

	private void findViews() {
		mHost = (LauncherHost) findViewById(R.id.main_tabhosts);
		List<HostItem> list = new ArrayList<HostItem>();
		HostItem item1 = new HostItem();
		item1.setName("影音媒体");
		item1.setIndex(LauncherHost.INDEX_MEDIA);
		list.add(item1);
		HostItem item2 = new HostItem();
		item2.setName("游戏娱乐");
		item2.setIndex(LauncherHost.INDEX_GAME);
		list.add(item2);
		HostItem item3 = new HostItem();
		item3.setName("成长教育");
		item3.setIndex(LauncherHost.INDEX_EDUCATION);
		list.add(item3);
		HostItem item4 = new HostItem();
		item4.setName("应用市场");
		item4.setIndex(LauncherHost.INDEX_APP_MARKET);
		list.add(item4);
		HostItem item5 = new HostItem();
		item5.setName("智能家居");
		item5.setIndex(LauncherHost.INDEX_SMART_HOME);
		list.add(item5);
		HostItem item6 = new HostItem();
		item6.setName("我的电视");
		item6.setIndex(LauncherHost.INDEX_MY_TV);
		list.add(item6);
		mHost.setmChooseListener(this);
		mHost.setmHostList(list);
		mHost.setSelectedIndex(0);

		mSpriteTextView = (TextView) findViewById(R.id.main_sprite_text);
		mSpriteTextView.setVisibility(View.INVISIBLE);
		mSpriteImageView = (ImageView) findViewById(R.id.main_sprite_image);
		
		mHelp = (Button) findViewById(R.id.main_help);
		mHelp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openActivity(HelpActivity.class);
			}
		});
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mApplication.hideVoiceBar();
		mApplication.url_parameter.setDomain("");
		mApplication.setmVoiceCommandCallback(mVoiceCommandCallback);
		setPageId(CommandConstants.PAGE_ID_HOME);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void initFragment(FragmentManager manager) {
		MediaFragment fragment = MediaFragment.getInstance();
		manager.beginTransaction().replace(R.id.main_container, fragment).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onChoose(HostItem item) {
		int index = item.getIndex();
		switch (index) {
		case LauncherHost.INDEX_MY_TV:
			break;
		case LauncherHost.INDEX_SMART_HOME:
			mFragmentManager.beginTransaction().replace(R.id.main_container, SmartHomeFragment.getInstance(mApplication)).commit();
			break;
		case LauncherHost.INDEX_APP_MARKET:
			break;
		case LauncherHost.INDEX_EDUCATION:
			VoiceDialog voiceDialog = new VoiceDialog(MainActivity.this);
			voiceDialog.showLightViews(VoiceDialog.DIALOG_LIGHT, null, 0);
			break;
		case LauncherHost.INDEX_GAME:
			mApplication.mVoiceControl.handResponse(Test.getVoiceResponse());
			break;
		case LauncherHost.INDEX_MEDIA:
			mFragmentManager.beginTransaction().replace(R.id.main_container, MediaFragment.getInstance()).commit();
			break;
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		MainActivity.this.finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	Runnable SetSpriteTextViewInvisible = new Runnable() {
		@Override
		public void run() {
			mSpriteTextView.setVisibility(View.INVISIBLE);
		}
	};

	public void startSpriteAnimation(int what) {
		// if (what == LauncherApplication.HANDLER_SPIRTE_LISTENING) {
		// mSpriteImageView.setBackgroundResource(R.anim.animation_listen);
		// } else
		if (what == LauncherApplication.HANDLER_SPIRTE_WAITING) {
			mSpriteImageView.setBackgroundResource(R.anim.home_sprite_wait);
			mAnimationDrawable = (AnimationDrawable) mSpriteImageView.getBackground();
			mAnimationDrawable.start();
		}

	}

	public void stopSpriteAnimation() {
		if (mAnimationDrawable != null) {
			mAnimationDrawable.stop();
		}
		mSpriteImageView.setBackgroundResource(R.drawable.home_robot);
	}
	
	VoiceCommandCallback mVoiceCommandCallback = new VoiceCommandCallback() {
		
		@Override
		public boolean onLocalCommandDeal(CmdCtrl cmdCtrl) {
			String command = cmdCtrl.getCommand();
			if(CommandConstants.MAIN_SMART_HOME.equals(command)){
				mHost.setSelectedIndex(LauncherHost.INDEX_SMART_HOME - 1);
				return false;
			}else if(CommandConstants.MAIN_MEDIA.equals(command)){
				mHost.setSelectedIndex(LauncherHost.INDEX_MEDIA - 1);
				return false;
			}
			return true;
		}
	};
	
	Handler mHandler = new Handler();
	public void showActionBar(String string) {
		if (mSpriteTextView != null) {
			mSpriteTextView.setVisibility(View.VISIBLE);
			mHandler.removeCallbacks(SetSpriteTextViewInvisible);
			mHandler.postDelayed(SetSpriteTextViewInvisible, VoiceDialog.DIALOG_LAST_TIME);
			if (string == null || TextUtils.isEmpty(string)) {
				mSpriteTextView.setText(R.string.sprite_hello);
			} else {
				mSpriteTextView.setText(string);
			}
		}
	}

	public void hideActionBar() {
		
	}
}
