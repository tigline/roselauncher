package com.tcl.launcher;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.launcher.core.VoiceCommandCallback;
import com.tcl.launcher.core.VoiceControl;
import com.tcl.launcher.entry.UrlParameter;
import com.tcl.launcher.interaction.IDynamicCommandIntercept;
import com.tcl.launcher.interaction.IInteractionRequest;
import com.tcl.launcher.interaction.factory.SingInteractionRequestFactory;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.ui.MainActivity;
import com.tcl.launcher.ui.homecloud.ui.CameraActivity;
import com.tcl.launcher.ui.homecloud.ui.FileSearchActivity;
import com.tcl.launcher.util.Constant;
import com.tcl.launcher.util.ContactUrl;
import com.tcl.launcher.util.JsonParser;
import com.tcl.launcher.util.LocationUtil;
import com.tcl.launcher.util.TLog;
import com.tcl.launcher.util.TipsVoicePlayer;
import com.tcl.launcher.util.Utils;
import com.tcl.launcher.widget.FloatView;
import com.tcl.launcher.widget.VoiceBar;
import com.tcl.launcher.xf.IatSettings;

/**
 * @author caomengqi/caomengqi@tcl.com 2015年5月8日
 * @JDK version 1.8
 * @brief 全局类，保存一些全局变量以及和语音控制的通信。
 * @version 1.0
 */
@SuppressLint("HandlerLeak")
public class LauncherApplication extends Application {
	private static final String TAG = "LauncherApplication";
	private static final int TIME_VOICE_BAR_LAST = 20 * 1000;

	public static final int HANDLER_MSG_VOICE_BAR_SHOW = 1;
	public static final int HANDLER_MSG_VOICE_BAR_HIDE = 2;

	public static final int HANDLER_ERROR_TIPS = 3;
	public static final int HANDLER_VOICE_MSG = 4;
	public static final int HANDLER_INIT_VOICE = 5;

	public static final int HANDLER_SPIRTE_LISTENING = 6;
	public static final int HANDLER_SPIRTE_WAITING = 7;
	public static final int HANDLER_SPIRTE_NORMAL = 8;

	private VoiceCommandCallback mVoiceCommandCallback; // 本地命令执行回调

	// private List<VoiceBarActivity> mActivityList; //activity列表
	private VoiceBarActivity mLastActivity;
	public VoiceControl mVoiceControl;

	FloatView mFloatView; // 浮动形象
	VoiceBar mVoiceBar; // 底部语音控制条

	Timer mTimer; // 循环检查是否有语音输入

	private String mCloudGrammar = null;// 云端语法文件
	private String mCloudGrammarID;// 云端语法id
	private String mEngineType = SpeechConstant.TYPE_CLOUD;// 引擎类型

	private VoiceWakeuper mIvw; // 语音唤醒对象
	private SpeechRecognizer mAsr;// 语音识别对象
	// 语音听写对象
	private SpeechRecognizer mIat;
	private SharedPreferences mSharedPreferences;

	private String user_request = "";
	// public XFVoiceSpeaker xf_voice_speaker = null;
	public UrlParameter url_parameter = null;

	private AnimationDrawable mAnimationDrawable;

	@Override
	public void onCreate() {
		super.onCreate();

		CrashHandler mCrashHandler = CrashHandler.getInstance();
		mCrashHandler.init(this);

		mVoiceBar = new VoiceBar(this);
		mVoiceBar.addToWindow();

		mFloatView = new FloatView(this);
		mFloatView.addToWindow();

		mVoiceBar.setVisibility(View.GONE);
		mFloatView.setVisibility(View.GONE);

		mVoiceControl = VoiceControl.getInstance(this);

	}

	public void setLastActivity(VoiceBarActivity activity) {
		mLastActivity = activity;
	}

	public synchronized VoiceBarActivity getLastActivity() {
		return mLastActivity;
	}

	Handler mApplicationHandler = new Handler() {
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case HANDLER_MSG_VOICE_BAR_SHOW:
				// 有语音输入时，在屏幕下方显示语音控制条
				if (url_parameter != null
						&& CommandConstants.PAGE_ID_HOME.equals(url_parameter.getPageid())) {
					MainActivity activity = (MainActivity) getLastActivity();
					activity.showActionBar(null);
				} else {
					TLog.i(TAG, "show voice bar");
					mVoiceBar.setVisibility(View.VISIBLE);
					mFloatView.setVisibility(View.VISIBLE);
				}
				break;
			case HANDLER_MSG_VOICE_BAR_HIDE:
				// 在一定的时间内无语音输入后，语音控制条消失
				if (url_parameter != null
						&& CommandConstants.PAGE_ID_HOME.equals(url_parameter.getPageid())) {
//					MainActivity activity = (MainActivity) getLastActivity();
//					activity.hideActionBar();
				} else {
					mVoiceBar.setVisibility(View.GONE);
					mFloatView.setVisibility(View.GONE);
				}
				break;
			case HANDLER_ERROR_TIPS:
				// 错误提示
				if (url_parameter != null
						&& CommandConstants.PAGE_ID_HOME.equals(url_parameter.getPageid())) {
					MainActivity activity = (MainActivity) getLastActivity();
					if (msg.obj != null) {
						activity.showActionBar(msg.obj.toString());
					}
				} else {
					TLog.i(TAG, "show voice bar");
					if (msg.obj != null) {
						getLastActivity().showTips(msg.obj.toString());
					}
				}

				judge_voice_wackup();

				break;
			case HANDLER_VOICE_MSG:
				mVoiceControl.onObjectResponse(msg.obj);
				// 处理消息

				judge_voice_wackup();
				break;
			case HANDLER_INIT_VOICE:
				// 初始化声音识别
				break;
			case HANDLER_SPIRTE_LISTENING:
				if (url_parameter != null
						&& CommandConstants.PAGE_ID_HOME.equals(url_parameter.getPageid())) {
					MainActivity activity = (MainActivity) getLastActivity();
					activity.startSpriteAnimation(HANDLER_SPIRTE_LISTENING);
				} else {
					startSpriteAnimation(HANDLER_SPIRTE_LISTENING);
				}
				break;
			case HANDLER_SPIRTE_WAITING:
				if (url_parameter != null
						&& CommandConstants.PAGE_ID_HOME.equals(url_parameter.getPageid())) {
					MainActivity activity = (MainActivity) getLastActivity();
					activity.startSpriteAnimation(HANDLER_SPIRTE_WAITING);
				} else {
					startSpriteAnimation(HANDLER_SPIRTE_WAITING);
				}
				break;
			case HANDLER_SPIRTE_NORMAL:

				if (url_parameter != null
						&& CommandConstants.PAGE_ID_HOME.equals(url_parameter.getPageid())) {
					MainActivity activity = (MainActivity) getLastActivity();
					activity.stopSpriteAnimation();
				} else {
					stopSpriteAnimation();
				}
				break;
			}
		}
	};

	public void sendHanlderMsg(int what, Object msg) {
		Message message = mApplicationHandler.obtainMessage();
		message.what = what;
		if (msg != null) {
			message.obj = msg;
		}
		message.sendToTarget();
	}

	public VoiceBar getVoiceBar() {
		return mVoiceBar;
	}

	public void hideVoiceBar() {
		mVoiceBar.setVisibility(View.GONE);
		mFloatView.setVisibility(View.GONE);
	}

	public void showVoiceBar() {
		mVoiceBar.setVisibility(View.VISIBLE);
		mFloatView.setVisibility(View.VISIBLE);
	}

	public FloatView getmFloatView() {
		return mFloatView;
	}

	public void startSpriteAnimation(int what) {
		if (what == HANDLER_SPIRTE_LISTENING) {
			mFloatView.setBackgroundResource(R.anim.animation_listen);
		} else if (what == HANDLER_SPIRTE_WAITING) {
			mFloatView.setBackgroundResource(R.anim.animation_wait);
		}
		mAnimationDrawable = (AnimationDrawable) mFloatView.getBackground();
		mAnimationDrawable.start();
	}

	public void stopSpriteAnimation() {
		if (mAnimationDrawable != null) {
			mAnimationDrawable.stop();
		}
		mFloatView.setBackgroundResource(R.drawable.voice_bar_sprite);
	}

	public VoiceCommandCallback getmVoiceCommandCallback() {
		return mVoiceCommandCallback;
	}

	public void setmVoiceCommandCallback(VoiceCommandCallback mVoiceCommandCallback) {
		this.mVoiceCommandCallback = mVoiceCommandCallback;
	}

	/**
	 * 执行控制命令，先过滤本地命令,如果已经执行本地命令，不再执行全局命令
	 * 
	 * @param cmdCtrl
	 */
	public void onControlCommand(CmdCtrl cmdCtrl) {
		boolean ifExcuteGlobal = excuteActivityCommand(cmdCtrl);
		if (ifExcuteGlobal) {
			TLog.i(TAG, "ExcuteGlobal");
			excuteGlobalCommand(cmdCtrl);
		}
	}

	/**
	 * 执行全局命令
	 * 
	 * @param cmdCtrl
	 * @return
	 */
	public boolean excuteGlobalCommand(CmdCtrl cmdCtrl) {
		String command = cmdCtrl.getCommand();
		if (CommandConstants.LEAD_DIALOG.equals(command)) {
			String str = cmdCtrl.getTips();
			TLog.i(TAG, "leadDialog tips : " + str);
			speek(str);
			sendHanlderMsg(HANDLER_ERROR_TIPS, str);
		} else {
			TLog.i(TAG, "ctrl command : " + command);
			if (command.startsWith(CommandConstants.PREFIX_GLOBAL_COMMAND)) {
				mVoiceControl.excuteTvControlCommand(cmdCtrl);
				return true;
			} else if (command.startsWith(CommandConstants.PREFIX_DEVICE_COMMAND)) {
				mVoiceControl.excuteDeviceControlCommand(cmdCtrl);
			} else if (command.startsWith(CommandConstants.PREFIX_AIR_COMMAND)) {
				mVoiceControl.excuteAirConditionControlCommand(cmdCtrl);
			} else if (command.startsWith(CommandConstants.PREFIX_HOME_CLOUD_COMMAND)) {
				if (command.equals(CommandConstants.HOME_CLOUD_OPEN_CAMERA)) {
					Bundle b = new Bundle();
					b.putSerializable("CmdPage", cmdCtrl);
					mVoiceControl.openActivity(LauncherApplication.this.getLastActivity(),
							CameraActivity.class, b);
				} else if (command.equals(CommandConstants.HOME_CLOUD_SEARCH_PHOTO)) {
					Bundle b = new Bundle();
					b.putSerializable("CmdPage", cmdCtrl);
					mVoiceControl.openActivity(LauncherApplication.this.getLastActivity(),
							FileSearchActivity.class, b);
				} else if (command.equals(CommandConstants.HOME_CLOUD_NOTICE_PLAY)) {
					// Bundle b = new Bundle();
					// b.putSerializable("CmdPage", cmdCtrl);
					// mVoiceControl.openActivity(LauncherApplication.this.getLastActivity(),
					// FileSearchActivity.class, b);
				}

			}
		}
		return false;
	}

	/**
	 * 执行当前页面的指令
	 * 
	 * @param cmdCtrl
	 * @return true 继续执行全局指令 false 不再执行全局命令
	 */
	public boolean excuteActivityCommand(CmdCtrl cmdCtrl) {
		boolean ifExcuteGlobal = true;

		if (mVoiceCommandCallback != null) {
			ifExcuteGlobal = mVoiceCommandCallback.onLocalCommandDeal(cmdCtrl);
		}

		return ifExcuteGlobal;
	}

	public void sendKeyCode(final int keyCode) {
		new Thread() {
			public void run() {
				try {
					Instrumentation inst = new Instrumentation();
					inst.sendKeyDownUpSync(keyCode);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 初始化操作
	 */
	public void init() {

		if (null == url_parameter) {
			url_parameter = new UrlParameter(this);
		}

		if (null == url_parameter.getLatitude()) {
			location();
		}
		StringBuffer param1 = new StringBuffer();
		param1.append("appid=" + getString(R.string.app_id));
		param1.append(",");
		// 设置使用v5+
		param1.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
		SpeechUtility.createUtility(this, param1.toString());

		// 加载识唤醒地资源，resPath为本地识别资源路径
		StringBuffer param = new StringBuffer();
		String resPath = ResourceUtil.generateResourcePath(LauncherApplication.this,
				RESOURCE_TYPE.assets, "ivw/" + getString(R.string.app_id) + ".jet");
		param.append(ResourceUtil.IVW_RES_PATH + "=" + resPath);
		param.append("," + ResourceUtil.ENGINE_START + "=" + SpeechConstant.ENG_IVW);
		boolean ret = SpeechUtility.getUtility().setParameter(ResourceUtil.ENGINE_START,
				param.toString());
		if (!ret) {
			TLog.i(TAG, "ret false");
		}

		mIvw = VoiceWakeuper.createWakeuper(this, null);// 初始化唤醒对象
		mAsr = SpeechRecognizer.createRecognizer(this, null);// 初始化识别对象---唤醒+识别,用来构建语法
		mCloudGrammar = readFile(this, "wake_grammar_sample.abnf", "utf-8");// 初始化语法文件
		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);// 设置参数
		mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");

		constructGrammar();// 开始构建语法
		init_xf_voice();
		init_xf_tts();

	}

	/**
	 * 读取asset目录下文件。
	 * 
	 * @return content
	 */
	public static String readFile(Context mContext, String file, String code) {
		int len = 0;
		byte[] buf = null;
		String result = "";
		try {
			InputStream in = mContext.getAssets().open(file);
			len = in.available();
			buf = new byte[len];
			in.read(buf, 0, len);

			result = new String(buf, code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 初始化讯飞语音
	 */
	private void init_xf_voice() {
		mIat = SpeechRecognizer.createRecognizer(this, mInitListener);// 初始化识别对象
		mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
	}

	/**
	 * 百度定位
	 */
	private void location() {
		try {
			LocationUtil location = new LocationUtil(this, url_parameter);// 百度定位操作
			location.getLocation();
		} catch (Exception e) {
			TLog.i(TAG, "location Exception:" + e.toString());
		}
	}

	/**
	 * 构建语法
	 */
	private void constructGrammar() {
		int ret_int = mAsr.buildGrammar("abnf", mCloudGrammar, grammarListener);// 开始构建语法
		if (ret_int != ErrorCode.SUCCESS) {
			TLog.i(TAG, "constructGrammar error : " + ret_int);
		}
	}

	/**
	 * 启动讯飞语音唤醒
	 */
	private void start_xf_wackup() {
		mIvw = VoiceWakeuper.getWakeuper();// 非空判断，防止因空指针使程序崩溃

		if (mIvw != null) {
			// resultString = "";
			// recoString = "";
			// txt_show_msg.setText(resultString);

			mIvw.setParameter(SpeechConstant.PARAMS, null);// 清空参数
			mIvw.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);// 设置识别引擎
			mIvw.setParameter(SpeechConstant.IVW_THRESHOLD, "0:" + -20);// 唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入
			mIvw.setParameter(SpeechConstant.IVW_SST, "oneshot");// 设置唤醒+识别模式
			mIvw.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);// 设置识别引擎
			mIvw.setParameter(SpeechConstant.RESULT_TYPE, "json");// 设置返回结果格式
			// mIvw.setParameter(SpeechConstant.KEEP_ALIVE, "1");
			// String keepAlive = mIvw.getParameter(SpeechConstant.KEEP_ALIVE);
			// TLog.i(TAG, "keep alive : " + keepAlive);

			if (!TextUtils.isEmpty(mCloudGrammarID)) {
				mIvw.setParameter(SpeechConstant.CLOUD_GRAMMAR, mCloudGrammarID);// 设置云端识别使用的语法id
				mIvw.startListening(mWakeuperListener);
			} else {
				TLog.i(TAG, "need constructGrammar");
			}
		} else {
			TLog.i(TAG, "wackup uninit");
		}
	}

	/**
	 * 停止讯飞语音唤醒
	 */
	private void stop_xf_wackup() {
		mIvw = VoiceWakeuper.getWakeuper();
		if (mIvw != null) {
			mIvw.stopListening();
		} else {
			TLog.i(TAG, "wakeup uninit");
		}
	}

	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				TLog.i(TAG, "onInit error" + code);
			}
		}
	};

	/**
	 * 初始化Layout。
	 */
	private void start_xf_voice() {
		user_request = "";
		setParam();
		int ret = mIat.startListening(recognizerListener);
		if (ret != ErrorCode.SUCCESS) {
			TLog.i(TAG, "listening error : " + ret);
		} else {
			TLog.i(TAG, "start_xf_voice success");
		}
	}

	/**
	 * 参数设置
	 * 
	 * @param param
	 * @return
	 */
	public void setParam() {
		// 清空参数
		mIat.setParameter(SpeechConstant.PARAMS, null);
		String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);// 设置引擎

		if (lag.equals("en_us")) {
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");// 设置语言
		} else {
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");// 设置语言
			mIat.setParameter(SpeechConstant.ACCENT, lag); // 设置语言区域
		}

		mIat.setParameter(SpeechConstant.VAD_BOS,
				mSharedPreferences.getString("iat_vadbos_preference", "4000"));// 设置语音前端点
		mIat.setParameter(SpeechConstant.VAD_EOS,
				mSharedPreferences.getString("iat_vadeos_preference", "1000"));// 设置语音后端点
		mIat.setParameter(SpeechConstant.ASR_PTT,
				mSharedPreferences.getString("iat_punc_preference", "1"));// 设置标点符号
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()
				+ "/iflytek/wavaudio.pcm");// 设置音频保存路径
	}

	/**
	 * 听写监听器。
	 */
	private RecognizerListener recognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			TLog.i(TAG, "onBeginOfSpeech");
			sendHanlderMsg(HANDLER_SPIRTE_LISTENING, null);
		}

		@Override
		public void onError(SpeechError error) {
			sendHanlderMsg(HANDLER_SPIRTE_NORMAL, null);
			// String errorStr = error.getErrorDescription();
			// TLog.i(TAG, errorStr);
			//
			// sendHanlderMsg(HANDLER_ERROR_TIPS, errorStr);
			// sendHanlderMsg(HANDLER_SPIRTE_NORMAL, null);
			// speek(errorStr);
			// mIat.stopListening();
			// TLog.i(TAG, "stopListening");
			// start_xf_wackup();
			start_xf_voice();
		}

		@Override
		public void onEndOfSpeech() {
			TLog.i(TAG, "onEndOfSpeech");
			sendHanlderMsg(HANDLER_SPIRTE_WAITING, null);
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			sendHanlderMsg(HANDLER_SPIRTE_NORMAL, null);
			String text = JsonParser.parseIatResult(results.getResultString());
			user_request = user_request + text;
			if (isLast) {

				String temp_question = Utils.punctuation(user_request);

				TLog.i(TAG, "user_question result：" + temp_question);

				if (Utils.isNotEmpty(temp_question) && temp_question.length() > 1) {

					IDynamicCommandIntercept intercept = SingInteractionRequestFactory
							.getInstance().getIDynamicCommandInterceptImpl();
					String intercept_command = intercept.interceptLocalCommand(
							url_parameter.getPageid(), url_parameter.getDomain(), user_request);
					TLog.i(TAG, "本地识别ok:" + intercept_command + "====" + url_parameter.getPageid());
					if (Utils.isNotEmpty(intercept_command)) {
						mVoiceControl.handResponse(intercept_command);
						TLog.i(TAG, "本地识别ok:" + intercept_command);
					} else {
						try {
							user_request = URLEncoder.encode(user_request, "utf-8");
							url_parameter.setQuestion(user_request);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}

						String semantic_url = ContactUrl.contactSemanticUrl(url_parameter);

						IInteractionRequest interactionRequest = SingInteractionRequestFactory
								.getInstance().getIQuestionServiceImpl();
						interactionRequest.question(semantic_url, mVoiceControl);
					}

					voice_controller();
				} else {
					judge_voice_wackup();
				}

				user_request = "";
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			Log.d(TAG, "当前正在说话，音量大小：" + volume);
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

		}
	};

	// ===================tts

	private SpeechSynthesizer mTts;

	private void init_xf_tts() {
		mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
	}

	public void speek(final String tts_txt) {
		TLog.i(TAG, "speek" + tts_txt);
		new Thread() {
			@Override
			public void run() {
				// 设置参数
				setTTSParam();
				int code = mTts.startSpeaking(tts_txt, mTtsListener);
				if (code != ErrorCode.SUCCESS) {
					TLog.i(TAG, "speek error " + code);
				}
			}
		}.start();

	}

	/**
	 * 参数设置
	 * 
	 * @param param
	 * @return
	 */
	private void setTTSParam() {
		mTts.setParameter(SpeechConstant.PARAMS, null);// 清空参数
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);// 设置使用云端引擎
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");// 设置发音人
		mTts.setParameter(SpeechConstant.SPEED,
				mSharedPreferences.getString("speed_preference", "50"));// 设置语速
		mTts.setParameter(SpeechConstant.PITCH,
				mSharedPreferences.getString("pitch_preference", "50"));// 设置音调
		mTts.setParameter(SpeechConstant.VOLUME,
				mSharedPreferences.getString("volume_preference", "50"));// 设置音量
		mTts.setParameter(SpeechConstant.STREAM_TYPE,
				mSharedPreferences.getString("stream_preference", "3"));// 设置播放器音频流类型
	}

	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {
		@Override
		public void onSpeakBegin() {
			TLog.i(TAG, "mTtsListener.onSpeakBegin");
		}

		@Override
		public void onSpeakPaused() {
			TLog.i(TAG, "mTtsListener.onSpeakPaused");
		}

		@Override
		public void onSpeakResumed() {
			TLog.i(TAG, "mTtsListener.onSpeakResumed");
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error == null) {
				TLog.i(TAG, "mTtsListener.onCompleted");
			} else if (error != null) {
				TLog.i(TAG, error.getPlainDescription(true));
			}
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

		}
	};

	private WakeuperListener mWakeuperListener = new WakeuperListener() {

		@Override
		public void onResult(WakeuperResult result) {
			try {
				voice_controller();

				String text = result.getResultString();
				TLog.i(TAG, "wake up result" + text);
				stop_xf_wackup();
				start_xf_voice();
				is_talked_over = false;
				TipsVoicePlayer.getInstance(LauncherApplication.this).playLocalMp3();
				sendHanlderMsg(HANDLER_MSG_VOICE_BAR_SHOW, null);
			} catch (Exception e) {
				TLog.i(TAG, "onResult Error");
				e.printStackTrace();
			}
		}

		@Override
		public void onError(SpeechError error) {
			TLog.i(TAG, error.getPlainDescription(true));
		}

		@Override
		public void onBeginOfSpeech() {
			TLog.i(TAG, "mWakeuperListener.onBeginOfSpeech");
		}

		@Override
		public void onEvent(int eventType, int isLast, int arg2, Bundle obj) {
			Log.d(TAG, "eventType:" + eventType + "arg1:" + isLast + "arg2:" + arg2);

			if (SpeechEvent.EVENT_IVW_RESULT == eventType) {
				RecognizerResult reslut = ((RecognizerResult) obj
						.get(SpeechEvent.KEY_EVENT_IVW_RESULT));
				TLog.i(TAG,
						"parseGrammarResult"
								+ JsonParser.parseGrammarResult(reslut.getResultString()));
			}
		}

	};

	/**
	 * 初始化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			TLog.i(TAG, "InitListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				TLog.i(TAG, "onInit error : " + code);
			}
		}
	};

	private GrammarListener grammarListener = new GrammarListener() {
		@Override
		public void onBuildFinish(String grammarId, SpeechError error) {
			if (error == null) {
				mCloudGrammarID = grammarId;
				TLog.i(TAG, "onBuildFinish success" + grammarId);
				start_xf_wackup();
			} else {
				TLog.i(TAG, "onBuildFinish failed" + error.getErrorCode());
			}
		}
	};

	/**
	 * 录音控制器
	 */
	private boolean is_talked_over = true;

	public boolean isSpeaking() {
		return !is_talked_over;
	}

	private Timer voice_timer = new Timer();
	private VoiceTimerTask voice_timerTask = new VoiceTimerTask();

	class VoiceTimerTask extends TimerTask {
		@Override
		public void run() {
			is_talked_over = true;
			sendHanlderMsg(HANDLER_MSG_VOICE_BAR_HIDE, null);
		}
	}

	/**
	 * 语音还是唤醒启动判断
	 */
	private void judge_voice_wackup() {
		sendHanlderMsg(HANDLER_SPIRTE_NORMAL, null);
		if (is_talked_over) {
			start_xf_wackup();

		} else {
			start_xf_voice();
		}
	}

	/**
	 * 语音录入定时器
	 */
	private void voice_controller() {
		if (null != voice_timerTask) {
			voice_timerTask.cancel();
		}

		voice_timerTask = new VoiceTimerTask();
		voice_timer.schedule(voice_timerTask, TIME_VOICE_BAR_LAST);
	}

}
