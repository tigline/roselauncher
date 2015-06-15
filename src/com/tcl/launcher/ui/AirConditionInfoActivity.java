package com.tcl.launcher.ui;

import java.io.InputStream;

import com.tcl.launcher.R;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.launcher.core.VoiceCommandCallback;
import com.tcl.launcher.json.entry.CmdCtrl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AirConditionInfoActivity extends VoiceBarActivity {
	private float[] air_consume_month_5 = new float[] {
			// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
			7.5f, 10.6f, 10.8f, 8.5f, 7.4f, 7.0f, 8.7f, 8.9f, 11.2f, 11.1f, 7.1f, 7.0f, 8.6f, 8.9f,
			8.5f,
			// 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
			11.0f, 11.6f, 8.4f, 7.4f, 8.2f, 8.0f, 8.2f, 10.6f, 10.9f, 6.7f, 7.8f, 8.5f, 7.4f, 7.7f,
			11.2f, 10.8f };

	private float[] air_temperature_month_5 = new float[] {
			// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
			24.5f, 24.8f, 24.7f, 24.4f, 25.5f, 25.1f, 27.0f, 27.3f, 24.0f, 24.7f, 25.5f, 24.9f,
			27.8f, 27.3f, 24.4f,
			// 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
			27.1f, 27.8f, 24.5f, 25.1f, 27.4f, 24.7f, 26.0f, 24.7f, 27.5f, 24.8f, 25.3f, 24.2f,
			24.1f, 25.9f, 27.6f, 25.7f };

	private float[] air_on_time_month_5 = new float[] {
			// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
			6.1f, 10.3f, 10.0f, 7.1f, 6.9f, 7.2f, 7.5f, 7.9f, 11.8f, 11.0f, 6.4f, 5.9f, 7.0f, 7.5f,
			7.2f,
			// 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
			11.3f, 11.1f, 7.5f, 6.9f, 7.2f, 7.1f, 6.9f, 9.9f, 10.4f, 5.1f, 5.5f, 7.5f, 6.3f, 6.9f,
			11.8f, 10.2f };

	private float[] air_efficiency_month_5 = new float[] { 19.2f, 40.8f, 27.4f, 12.6f };

	// private float []air_consume_month_random = new float[31];

	// ====图标类型====
	public enum DataType {
		CONSUME, TEMPERATURE, ON_TIME, EFFICIENCY
	}

	private FrameLayout rightViewFrameLayout;
	private int rightViewWidth; // 画图区间-宽度
	private int rightViewHeight; // 画图区间-高度

	private ImageView iv_char_bg; // 图标坐标背景图

	private LinearLayout leftItemLinearLayout;

	private AirConditionInfoLineChartView lineChartView; // 图表

	private Button iBtnConsume;
	private Button iBtnTemperature;
	private Button iBtnOnTime;
	private Button iBtnEfficiency;

	private Button iBtnBack;

	private Bitmap bitmapConsume;
	private Bitmap bitmapTemperature;
	private Bitmap bitmapOnTime;

	private boolean isFirstActivityCreat = true;

	VoiceCommandCallback mCommandCallback = new VoiceCommandCallback() {
		@Override
		public boolean onLocalCommandDeal(CmdCtrl cmdCtrl) {
			if (cmdCtrl.getCommand().equals(CommandConstants.TV_GO_UP))// 向上
			{
				switch (leftItemLinearLayout.getFocusedChild().getId()) {
				case R.id.left_menu_btn_temperature:
					iBtnConsume.requestFocus();
					iBtnConsume.requestFocusFromTouch();
					break;
				case R.id.left_menu_btn_on:
					iBtnTemperature.requestFocus();
					iBtnTemperature.requestFocusFromTouch();
					break;
				case R.id.left_menu_btn_efficiency:
					iBtnOnTime.requestFocus();
					iBtnOnTime.requestFocusFromTouch();
					break;
				default:
					break;
				}
			} else if (cmdCtrl.getCommand().equals(CommandConstants.TV_GO_DOWN))// 向下
			{
				switch (leftItemLinearLayout.getFocusedChild().getId()) {
				case R.id.left_menu_btn_energy:
					iBtnTemperature.requestFocus();
					iBtnTemperature.requestFocusFromTouch();
					break;
				case R.id.left_menu_btn_temperature:
					iBtnOnTime.requestFocus();
					iBtnOnTime.requestFocusFromTouch();
					break;
				case R.id.left_menu_btn_on:
					iBtnEfficiency.requestFocus();
					iBtnEfficiency.requestFocusFromTouch();
					break;
				default:
					break;
				}
			} else if (cmdCtrl.getCommand().equals(CommandConstants.AIR_CONDITION_REQ_QUIT))// 退出查询
			{
				finish();
			} else if (cmdCtrl.getCommand().equals(CommandConstants.TV_GO_BACK))// 返回
			{
				finish();
			} else if (cmdCtrl.getCommand().equals(CommandConstants.TV_OPEN_MENU))// 菜单
			{
				finish();
				// openActivity(AirConditionInfoActivity.class);
			}
			/*
			 * else if(cmdCtrl.getCommand().equals(CommandConstants.帮助))//帮助 {
			 * finish(); //openActivity(AirConditionInfoActivity.class); }
			 */
			else {
				return true;
			}

			return false;
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		mApplication.setmVoiceCommandCallback(mCommandCallback);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_air_condition_info);

		// ====控件初始化====
		findViewAndInit();

		// ====判断SD卡是否有数据文件，如果有则更新获取SD卡数据文件====
		air_consume_month_5 = AirConditionInfoReadData.getInstance().readSdCardeData(
				air_consume_month_5, DataType.CONSUME);
		air_temperature_month_5 = AirConditionInfoReadData.getInstance().readSdCardeData(
				air_temperature_month_5, DataType.TEMPERATURE);
		air_on_time_month_5 = AirConditionInfoReadData.getInstance().readSdCardeData(
				air_on_time_month_5, DataType.ON_TIME);
		air_efficiency_month_5 = AirConditionInfoReadData.getInstance().readSdCardeData(
				air_efficiency_month_5, DataType.EFFICIENCY);

		bitmapConsume = readBitMap(AirConditionInfoActivity.this, R.drawable.air_info_consume_bg);
		bitmapTemperature = readBitMap(AirConditionInfoActivity.this,
				R.drawable.air_info_temperature_bg);
		bitmapOnTime = readBitMap(AirConditionInfoActivity.this, R.drawable.air_info_on_time_bg);
	}

	// ====界面启动完成触发====
	public void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus) {
			Log.i("====zgx====", "====zgx====onWindowFocusChanged");

			rightViewWidth = rightViewFrameLayout.getWidth();
			rightViewHeight = rightViewFrameLayout.getHeight();

			if (isFirstActivityCreat == true) {
				lineChartView = new AirConditionInfoLineChartView(AirConditionInfoActivity.this);
				rightViewFrameLayout.addView(lineChartView);
				lineChartView.setData(rightViewWidth, rightViewHeight, air_consume_month_5,
						DataType.CONSUME, iv_char_bg);
				lineChartView.invalidate();

				iBtnConsume.requestFocus();
				iBtnConsume.requestFocusFromTouch();

				isFirstActivityCreat = false;
			}
		}
	}

	// ====界面启动接口====
	public static void launch(Context mContext) {
		Intent intent = new Intent(mContext, AirConditionInfoActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}

	// ====控件初始化====
	private void findViewAndInit() {
		rightViewFrameLayout = (FrameLayout) findViewById(R.id.fy_right_view);

		iv_char_bg = (ImageView) findViewById(R.id.iv_char_bg);

		leftItemLinearLayout = (LinearLayout) findViewById(R.id.ly_left_menu);

		iBtnConsume = (Button) findViewById(R.id.left_menu_btn_energy);
		iBtnTemperature = (Button) findViewById(R.id.left_menu_btn_temperature);
		iBtnOnTime = (Button) findViewById(R.id.left_menu_btn_on);
		iBtnEfficiency = (Button) findViewById(R.id.left_menu_btn_efficiency);

		iBtnConsume.setOnFocusChangeListener(listenFocusChangeListener);
		iBtnTemperature.setOnFocusChangeListener(listenFocusChangeListener);
		iBtnOnTime.setOnFocusChangeListener(listenFocusChangeListener);
		iBtnEfficiency.setOnFocusChangeListener(listenFocusChangeListener);

		iBtnBack = (Button) findViewById(R.id.left_menu_btn_back);
		iBtnBack.setOnClickListener(onclick);
	}

	// ====焦点触发事件====
	private OnFocusChangeListener listenFocusChangeListener = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View arg0, boolean arg1) {
			if (lineChartView == null)
				return;

			switch (arg0.getId()) {
			case R.id.left_menu_btn_energy:
				if (arg1 == true) {
					iv_char_bg.setImageBitmap(bitmapConsume);
					iv_char_bg.setVisibility(View.VISIBLE);
					lineChartView.setData(rightViewWidth, rightViewHeight, air_consume_month_5,
							DataType.CONSUME, iv_char_bg);
					lineChartView.invalidate();
				}
				break;
			case R.id.left_menu_btn_temperature:
				if (arg1 == true) {
					iv_char_bg.setImageBitmap(bitmapTemperature);
					iv_char_bg.setVisibility(View.VISIBLE);
					lineChartView.setData(rightViewWidth, rightViewHeight, air_temperature_month_5,
							DataType.TEMPERATURE, iv_char_bg);
					lineChartView.invalidate();
				}
				break;
			case R.id.left_menu_btn_on:
				if (arg1 == true) {
					iv_char_bg.setImageBitmap(bitmapOnTime);
					iv_char_bg.setVisibility(View.VISIBLE);
					lineChartView.setData(rightViewWidth, rightViewHeight, air_on_time_month_5,
							DataType.ON_TIME, iv_char_bg);
					lineChartView.invalidate();
				}
				break;
			case R.id.left_menu_btn_efficiency:
				if (arg1 == true) {
					iv_char_bg.setVisibility(View.INVISIBLE);
					lineChartView.setData(rightViewWidth, rightViewHeight, air_efficiency_month_5,
							DataType.EFFICIENCY, iv_char_bg);
					lineChartView.invalidate();
				}
				break;
			default:
				break;
			}
		}
	};

	private Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	// ====按键点击事件====
	OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View localView) {
			switch (localView.getId()) {
			case R.id.left_menu_btn_back:
				finish();
				break;
			default:
				break;
			}
		}
	};

	// ====按键触发事件====
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			AirConditionInfoActivity.this.finish();
			break;
		default:
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		Log.i("====AirInfoActivity====", "====onDestroy()====");
		bitmapConsume.recycle();
		bitmapConsume = null;
		bitmapOnTime.recycle();
		bitmapOnTime = null;
		bitmapTemperature.recycle();
		bitmapTemperature = null;
		System.gc();
		super.onDestroy();
	}

}
