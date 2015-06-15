package com.tcl.launcher.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.tcl.launcher.R;

public class FloatView extends ImageView {
	private static final String TAG = "FloatView";

	private WindowManager mWindowManager;
	private Context mContext;

	public FloatView(Context context) {
		super(context);
		mContext = context;
		mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(
				Context.WINDOW_SERVICE);
		this.setBackgroundResource(R.drawable.voice_bar_sprite);
	}

	public void addToWindow() {
		WindowManager.LayoutParams param = new WindowManager.LayoutParams();
		param.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 系统提示类型,重要
		param.format = 1;
		param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
		param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
		param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制

		param.alpha = 1.0f;

		param.gravity = Gravity.RIGHT | Gravity.BOTTOM; // 调整悬浮窗口至左上角
		// 以屏幕左上角为原点，设置x、y初始值
		param.width = WindowManager.LayoutParams.WRAP_CONTENT;
		param.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowManager.addView(this, param);
	}
	
	public void removeFromWindow(){
		mWindowManager.removeView(this);
	}
	
	public boolean isInWindow(){
		return false;
	}
	
	public void playAnimation() {
		this.setBackgroundResource(R.anim.animation_listen);
		AnimationDrawable drawable = (AnimationDrawable) getBackground();
		drawable.start();
	}
	
	public void stopAnimation(){
		
	}
}
