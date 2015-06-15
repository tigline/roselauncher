package com.tcl.launcher.widget;

import com.tcl.launcher.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class VoiceBar {
	private static final String TAG = "VoiceBar";

	private Context mContext;
	private WindowManager mWindowManager;

	private Button mHelp;
	private Button mHome;
	private View mContentView;

	public VoiceBar(Context context) {
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		mContentView = inflater.inflate(R.layout.voice_bar, null);
		mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(
				Context.WINDOW_SERVICE);
		mHelp = (Button) mContentView.findViewById(R.id.voice_bar_help);
		mHome = (Button) mContentView.findViewById(R.id.voice_bar_menu);
	}
	
	public void addToWindow() {
		WindowManager.LayoutParams param = new WindowManager.LayoutParams();
		param.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 系统提示类型,重要
		param.format = 1;
		param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
		param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
		param.windowAnimations = R.style.window_anim;
		//param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制

		param.alpha = 1.0f;

		param.gravity = Gravity.BOTTOM | Gravity.LEFT; 
		// 以屏幕左上角为原点，设置x、y初始值

		// 设置悬浮窗口长宽数据
		param.width = WindowManager.LayoutParams.MATCH_PARENT;
		param.height = WindowManager.LayoutParams.WRAP_CONTENT;

		mWindowManager.addView(mContentView, param);
	}

	public int getVisibility() {
		return mContentView.getVisibility();
	}
	
	public void setVisibility(int visibility){
		mContentView.setVisibility(visibility);
	}
}
