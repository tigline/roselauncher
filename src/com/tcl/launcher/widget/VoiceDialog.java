package com.tcl.launcher.widget;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tcl.launcher.R;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.base.VoiceBarActivity.OnFinishCallBack;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.launcher.util.TLog;

/**
 * @author caomengqi/caomengqi@tcl.com 2015年5月26日
 * @JDK version 1.8
 * @brief 全局控制提示弹出窗口
 * @version
 */
public class VoiceDialog extends Dialog {
	private static final String TAG = "VoiceDialog";

	public static final int DIALOG_LAST_TIME = 5000;

	public static final int DIALOG_LIGHT = 1;
	public static final int DIALOG_AIRCON = 2;
	public static final int DIALOG_VIDEO = 3;
	public static final int DIALOG_ERROR = 4;
	public static final int DIALOG_SOUND = 5;

	public static final String OPERATION_OPEN = "open";
	public static final String OPERATION_CLOSE = "close";
	public static final String OPERATION_SET = "set";

	public static final int LOCATION_X = 750;
	public static final int LOCATION_y = 700;

	private VoiceBarActivity mContext;
	private LayoutInflater mInflater;

	private int mWhat;

	private ImageView mImageView;
	private TextView mTextView;
	private SeekBar mSeekBar;

	public VoiceDialog(VoiceBarActivity context) {
		super(context, R.style.Theme_Transparent);
		mContext = context;
		mInflater = LayoutInflater.from(context);

		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);

		lp.x = LOCATION_X; // 新位置X坐标
		lp.y = LOCATION_y; // 新位置Y坐标
		dialogWindow.setAttributes(lp);
		context.setmOnFinishCallBack(new OnFinishCallBack() {

			@Override
			public void onFinish() {
				mDialogHandler.removeCallbacks(disappearTask);
				if (VoiceDialog.this.isShowing()) {
					VoiceDialog.this.dismiss();
				}
			}
		});

	}

	Handler mDialogHandler = new Handler() {
		public void handleMessage(Message msg) {

		}
	};

	/**
	 * 弹出全局提示
	 * 
	 * @param what
	 *            提示类别 空调，开关，灯光等
	 * @param command
	 *            操作 打开，关闭或其他操作
	 * @param params
	 *            具体参数，如空调度数等
	 */
	public void showLightViews(int what, String command, int... params) {
		TLog.i(TAG, "dialog is showing? : " + isShowing());
		if (isShowing()) {
			if (mWhat != what) {
				dismiss();
				showLightViews(what, command, params);
			} else {
				refreshViews(what, command, params);
				mDialogHandler.removeCallbacks(disappearTask);
				mDialogHandler.postDelayed(disappearTask, DIALOG_LAST_TIME);
			}
		} else {
			mWhat = what;
			View view = getView(what);
			setContentView(view);
			show();
			refreshViews(what, command, params);
			mDialogHandler.postDelayed(disappearTask, DIALOG_LAST_TIME);
		}
	}

	private View getView(int what) {
		View view = mInflater.inflate(R.layout.voice_dialog_general, null);
		mImageView = (ImageView) view.findViewById(R.id.voice_dialog_img);
		mTextView = (TextView) view.findViewById(R.id.voice_dialog_text);
		mSeekBar = (SeekBar) view.findViewById(R.id.voice_dialog_seekbar);
		return view;
	}

	/**
	 * 在弹出框内显示具体的内容()
	 * 
	 * @param what
	 *            提示类别 空调，开关，灯光等
	 * @param command
	 *            操作 打开，关闭或其他操作
	 * @param params
	 *            具体参数，如空调度数等
	 */
	private void refreshViews(int what, String command, int... params) {
		switch (what) {
		case DIALOG_ERROR:
			mImageView.setVisibility(View.GONE);
			mSeekBar.setVisibility(View.GONE);
			mTextView.setVisibility(View.VISIBLE);
			mTextView.setText(command);
			break;
		case DIALOG_LIGHT:
			mImageView.setVisibility(View.VISIBLE);
			mSeekBar.setVisibility(View.GONE);
			mTextView.setVisibility(View.VISIBLE);
			int drawableId = -1;
			String text = null;
			if (CommandConstants.DEVICE_ONE_POWER_OPEN.equals(command)) {
				drawableId = R.drawable.voice_tips_light_open;
				text = mContext.getString(R.string.voice_dialog_tips_light_open);
			} else if (CommandConstants.DEVICE_ONE_POWER_CLOSE.equals(command)) {
				drawableId = R.drawable.voice_tips_light_close;
				text = mContext.getString(R.string.voice_dialog_tips_light_close);
			}
			if (drawableId != -1) {
				mImageView.setBackgroundResource(drawableId);
				mTextView.setText(text);
			}
			break;
		case DIALOG_AIRCON:
			mImageView.setVisibility(View.VISIBLE);
			mSeekBar.setVisibility(View.GONE);
			mTextView.setVisibility(View.VISIBLE);
			int airDrawableId = -1;
			String airText = null;
			if (CommandConstants.AIR_CONDITION_OPEN.equals(command)) {
				airDrawableId = R.drawable.voice_tips_aircon_open;
				airText = mContext.getString(R.string.voice_dialog_tips_aircon_open);
			} else if (CommandConstants.AIR_CONDITION_CLOSE.equals(command)) {
				airDrawableId = R.drawable.voice_tips_aircon_close;
				airText = mContext.getString(R.string.voice_dialog_tips_aircon_close);
			} else if (CommandConstants.AIR_CONDITION_TEMP_SET.equals(command)) {
				airDrawableId = R.drawable.voice_tips_aircon_close;
				String str = mContext.getString(R.string.voice_dialog_tips_aircon_set_temp);
				airText = String.format(str, params[0]);
			}

			if (airDrawableId != -1) {
				mImageView.setBackgroundResource(airDrawableId);
				mTextView.setText(airText);
			}

			break;
		case DIALOG_VIDEO:
			break;
		case DIALOG_SOUND:
			mImageView.setVisibility(View.GONE);
			mSeekBar.setVisibility(View.VISIBLE);
			mTextView.setVisibility(View.VISIBLE);
			mSeekBar.setMax(params[0]);
			mSeekBar.setProgress(params[1]);
			mTextView.setText(mContext.getString(R.string.volume) + params[1]);
			break;
		}

	}

	Runnable disappearTask = new Runnable() {

		@Override
		public void run() {
			dismiss();
		}
	};

	public void hideViews() {

	}
}
