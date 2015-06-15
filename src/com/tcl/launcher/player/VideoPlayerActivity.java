package com.tcl.launcher.player;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue.IdleHandler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.launcher.R;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.launcher.core.VoiceCommandCallback;
import com.tcl.launcher.core.VoiceControl;
import com.tcl.launcher.http.HttpUtils;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.json.entry.CmdSingle;
import com.tcl.launcher.json.entry.Episode;
import com.tcl.launcher.json.entry.Site;
import com.tcl.launcher.ui.MediaDetailActivity;
import com.tcl.launcher.util.FlvcdHelper;
import com.tcl.launcher.util.TLog;

/**
 * @ClassName: VideoPlayerActivity
 * @Description: videoplayer activity
 * @author liude
 * @date 2015年5月20日 上午10:53:26
 * 
 */
public class VideoPlayerActivity extends VoiceBarActivity implements OnPreparedListener,
		OnCompletionListener, OnErrorListener, OnBufferingUpdateListener, OnClickListener {

	public static final String KEY_URL = "play_url";

	private final static String TAG = "VideoPlayerActivity";
	
	//private final static Object mLock = new Object();
	
	/** 
	* @Fields mLock : 线程锁，控制中断播放进度更新线程 
	*/ 
	private static Lock mLock = new ReentrantLock(); 
	
	/** 
	* @Fields mUpdateProgressThread : 更新播放进度条线程 
	*/ 
	private Thread mUpdateProgressThread = null;

	/**
	 * @Fields STEP_SPEED : 快进、快退时步进大小
	 */
	private final static int MIN_SEEK_SPEED = 10 * 1000;

	/**
	 * @Fields NUM_SEEK_STEP : 快进/快退时将视频划分为NUM_SEEK_STEP段
	 */
	private final static int NUM_SEEK_STEP = 200;

	/**
	 * @Fields MSG_HIDE_CONTROLLER : 隐藏进度控制弹出框消息
	 */
	private final static int MSG_HIDE_CONTROLLER = 1;

	/**
	 * @Fields MSG_FINISH_SELF : 错误结束activity消息
	 */
	private final static int MSG_FINISH_SELF = 2;

	private final static int MSG_PLAY_VIDEO = 3;

	/**
	 * @Fields MSG_SEEK_FOWARD : 快进消息
	 */
	private final static int MSG_SEEK_FOWARD = 4;

	/**
	 * @Fields MSG_SEEK_REWIND : 快退消息
	 */
	private final static int MSG_SEEK_REWIND = 5;
	
	private final static int MSG_SHOW_TOAST	= 6;
	
	private final static String KEY_MSG_SHOW_TOAST = "key_msg_show_toast";
	
	private static boolean mIsInThePlay = false;
	
	//private final static int MSG_SET_PROGRESS = 6;

	/**
	 * @Fields TIME_SEEK_TIMER : 自动快/快退进间隔
	 */
	private final static int TIME_SEEK_TIMER = 1000;

	/**
	 * @Fields mCurPosWhenSeek : 快进/快退时，获取当前位置
	 */
	private static int mCurPosWhenSeek = 0;

	/**
	 * @Fields mIsFirstGetCurPosWhenSeekIdent : 快进/快退时，只有开始时获取一次当前位置
	 */
	private static int mIsFirstGetCurPosWhenSeekIdent = -1;

	/**
	 * @Fields PROGRESS_FORWARD : 进度控制标志，快进
	 */
	private final static int PROGRESS_FORWARD = 0;

	/**
	 * @Fields PROGRESS_FORWARD : 进度控制标志，快退
	 */
	private final static int PROGRESS_REWIND = 1;

	/**
	 * @Fields TIME_POPDLG_HIDE : 进度控制弹出框隐藏时间
	 */
	private final static int TIME_POPDLG_HIDE = 6868;

	/**
	 * @Fields TIME_ERROR_FINISH : 出错时activity结束时间
	 */
	private final static int TIME_ERROR_FINISH = 3000;

	/**
	 * @Fields URL_KEY : intent传递URL参数的key值
	 */
	public static final String URL_KEY = "url_key";

	/**
	 * @Fields VIDEONAME_KEY : intent传递影片名字时的key值
	 */
	private static final String VIDEONAME_KEY = "videoname_key";

	/**
	 * @Fields INIT_TIME : video时间初始化值
	 */
	private final static String INIT_TIME = "00:00:00";

	/**
	 * @Fields 接收到的视频url地址
	 */
	private String mURLString = null;

	/**
	 * @Fields mVideoNameString : video视频名字
	 */
	private String mVideoNameString = null;

	/**
	 * @Fields mVideoNameTextView : 显示video视频名字的textview
	 */
	private TextView mVideoNameTextView;

	/**
	 * @Fields mMediaPlayer : MediaPlayer控件
	 */
	private MediaPlayer mMediaPlayer;

	/**
	 * @Fields mSurfaceView : SurfaceView控件
	 */
	private SurfaceView mSurfaceView;

	/**
	 * @Fields mSurfaceHolder : 视频播放控制
	 */
	private SurfaceHolder mSurfaceHolder;

	/**
	 * @Fields mPlayButton : 开始播放按钮
	 */
	private ImageButton mPlayButton;

	/**
	 * @Fields mPlayPauseButton : 弹出框中播放、暂停按钮
	 */
	private ImageButton mPlayPauseButton;

	/**
	 * @Fields mTimeSeekBar : 播放进度条
	 */
	private SeekBar mTimeSeekBar;

	/**
	 * @Fields mPlayedTimeTextView : 已播放时间 TextView
	 */
	private TextView mPlayedTimeTextView;

	/**
	 * @Fields mTotalTimeTextView : 总时长TextView
	 */
	private TextView mTotalTimeTextView;

	/**
	 * @Fields mVideoCurPositionTimeInt : 当前播放位置
	 */
	private int mVideoCurPositionTimeInt;

	/**
	 * @Fields mVideoTimeLong : video总时长
	 */
	private int mVideoTimeInt;

	/**
	 * @Fields mVideoTimeString : video总时长
	 */
	private String mVideoTimeString;

	/**
	 * @Fields seekBarAutoFlag : 播放进度条是否自动滑动标志
	 */
	//private boolean mSeekBarAutoFlag;

	/**
	 * @Fields mLoadProgressBar : 加载进度条
	 */
	private ProgressBar mLoadProgressBar;

	/**
	 * @Fields mControlView : 进度控制layout
	 */
	private View mControlView;

	/**
	 * @Fields mControlPopupWindow : 进度控制弹出框
	 */
	private PopupWindow mControlPopupWindow;

	/**
	 * @Fields mIsControllerShow : 进度控制弹出框是否已经显示
	 */
	private boolean mIsControllerShow = false;

	/**
	 * @Fields mScreenWidth : 屏幕宽度
	 */
	private static int mScreenWidth;

	/**
	 * @Fields mScreenHeight : 屏幕高度
	 */
	private static int mScreenHeight;

	/**
	 * @Fields mControlHeight : 进度控制弹出框高度
	 */
	private static int mControlHeight;

	/**
	 * @Fields mSeekTimer : 自动快进/快退timer
	 */
	private final Timer mSeekTimer = new Timer();

	/**
	 * @Fields mSeekFowardTimerTask : 自动快进TimerTask
	 */
	private TimerTask mSeekFowardTimerTask = null;

	/**
	 * @Fields mSeekRewindTimerTask : 自动快退TimerTask
	 */
	private TimerTask mSeekRewindTimerTask = null;

	/**
	 * @Fields mSeekSpeed : 快进/快退时，每一步跳转的时间
	 */
	private int mSeekSpeed = 0;

	// private GestureDetector mGestureDetector = null;
	// private Handler mHandler;
	// private static String TAG = "VidepPlayerActivity";
	private CmdSingle mCmdSingle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoplayer_layout);
		

		// 获得屏幕尺寸
		try {
			getScreenSize();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 初始化
		initViews();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			mCmdSingle = (CmdSingle) getIntent().getExtras().getSerializable(VoiceControl.CMD_PAGE);
		}
		
		// 设置本地命令回调
		if (null != mApplication) {
			mApplication.setmVoiceCommandCallback(new VideoVoiceCommandCallback());
		}
	}

	/**
	 * @ClassName: VideoVoiceCommandCallback
	 * @Description: 本地命令回调
	 * @author liude
	 * @date 2015年5月29日 上午9:46:06
	 * 
	 */
	private class VideoVoiceCommandCallback implements VoiceCommandCallback {
		@Override
		public boolean onLocalCommandDeal(CmdCtrl cmdCtrl) {
			cancelAllTimerTask();

			// 暂停
			if (cmdCtrl.getCommand().equals(CommandConstants.PAUSE)) {
				// 显示进度控制弹出框
				reShowController();
				// 暂停播放
				playPause();
				return false;
				// 播放
			} else if (cmdCtrl.getCommand().equals(CommandConstants.PLAY)) {
				// 显示进度控制弹出框
				reShowController();
				playResume();
				return false;
				// 快进
			} else if (cmdCtrl.getCommand().equals(CommandConstants.SPEED)) {
				mSeekFowardTimerTask = newSeekForwardTimerTask();
				mSeekTimer.schedule(mSeekFowardTimerTask, 0, TIME_SEEK_TIMER);
				showController();
				return false;
				// 快退
			} else if (cmdCtrl.getCommand().equals(CommandConstants.BACKWARKS)) {
				mSeekRewindTimerTask = newSeekRewindTimerTask();
				mSeekTimer.schedule(mSeekRewindTimerTask, 0, TIME_SEEK_TIMER);
				showController();
				return false;
			}

			return true;
		}
	}

	/**
	 * @Title: initViews
	 * @Description: init views
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	private void initViews() {
		// 获得控件对象
		mControlView = getLayoutInflater().inflate(R.layout.controler_layout, null);
		mControlPopupWindow = new PopupWindow(mControlView);
		mSurfaceView = (SurfaceView) findViewById(R.id.video_surfaceview);
		mPlayButton = (ImageButton) findViewById(R.id.play_button);
		mPlayPauseButton = (ImageButton) mControlView.findViewById(R.id.playpause_button);
		mTimeSeekBar = (SeekBar) mControlView.findViewById(R.id.playtime_seekbar);
		mPlayedTimeTextView = (TextView) mControlView.findViewById(R.id.playedtime_textview);
		mTotalTimeTextView = (TextView) mControlView.findViewById(R.id.totaltime_textview);
		mLoadProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);

		// 隐藏播放按钮
		mPlayButton.setVisibility(View.GONE);

		// 设置mSurfaceHolder
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceHolder.addCallback(new SurfaceCallback());

		// 设置按钮监听
		mPlayButton.setOnClickListener(this);
		mPlayPauseButton.setOnClickListener(this);
		
//		mIsInThePlay = false;

		// 加载进度控制弹出框
		Looper.myQueue().addIdleHandler(new IdleHandler() {
			@Override
			public boolean queueIdle() {

				// 显示控制弹出框
				if (null != mControlPopupWindow) {
					mControlPopupWindow.showAtLocation(
							VideoPlayerActivity.this.findViewById(R.id.videoplayer_mainlayout),
							Gravity.BOTTOM, 0, 0);
					mControlPopupWindow.update(0, 0, mScreenWidth, mControlHeight);
					mIsControllerShow = true;
					hideControllerDely();
				}

				return false;
			}
		});
	}

	/**
	 * @Title: resetValues
	 * @Description: 重置变量，播放下一集、上一集的时候重置
	 * @param
	 * @return void
	 */
	private void resetValues() {
		//mURLString = null;
		mIsInThePlay = false;
		mVideoNameString = null;
		mVideoCurPositionTimeInt = -1;
		mVideoTimeInt = -1;
		mVideoTimeString = null;
		mUpdateProgressThread = null;
		//mSeekBarAutoFlag = false;
		//mUpdateProgressThread.
		//mIsControllerShow = false;
		cancelAllTimerTask();
		mSeekSpeed = -1;
		mIsFirstGetCurPosWhenSeekIdent = -1;
		mCurPosWhenSeek = 0;
	}

	/**
	 * @Title: resetViewsState
	 * @Description: 重置控件状态，播放下一集、上一集的时候重置
	 * @param
	 * @return void
	 * @throws
	 */
	private void resetViewsState() {
		// 隐藏进度控制条弹出框
		hideController();
		// 重新显示加载进度条
		mLoadProgressBar.setVisibility(View.VISIBLE);
		// 播放按钮隐藏
		mPlayButton.setVisibility(View.GONE);
		// 进度控制条中按钮图片
		mPlayPauseButton.setImageResource(R.drawable.video_pause);
		// 重置播放时间TextView
		mPlayedTimeTextView.setText(INIT_TIME);
		mTotalTimeTextView.setText(INIT_TIME);
		// 重置进度条
		mTimeSeekBar.setMax(0);
		mTimeSeekBar.setProgress(0);
	}

	/**
	 * @Title: newSeekRewindTimerTask
	 * @Description: 新建自动快进的TimerTask，每次cancel后都需要重新new
	 * @param
	 * @return TimerTask
	 * @throws
	 */
	private TimerTask newSeekForwardTimerTask() {
		return new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(MSG_SEEK_FOWARD);
			}
		};
	}

	/**
	 * @Title: newSeekRewindTimerTask
	 * @Description: 新建自动快退的TimerTask，每次cancel后都需要重新new
	 * @param
	 * @return TimerTask
	 * @throws
	 */
	private TimerTask newSeekRewindTimerTask() {
		return new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(MSG_SEEK_REWIND);
			}
		};
	}

	/**
	 * @Title: getScreenSize
	 * @Description: 获得屏幕尺寸
	 * @param
	 * @return void
	 * @throws IOException
	 */
	private void getScreenSize() throws IOException {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		// 屏幕尺寸
		mScreenWidth = displayMetrics.widthPixels;
		mScreenHeight = displayMetrics.heightPixels;
		mControlHeight = mScreenHeight / 4;
	}

	// 处理消息
	@SuppressLint("ShowToast") Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 隐藏进度控制弹出框
			case MSG_HIDE_CONTROLLER:
				if (mIsControllerShow) {
					hideController();
				}
				break;

			// finish self activity
			case MSG_FINISH_SELF:
				clearRes();
				VideoPlayerActivity.this.finish();
				break;

			case MSG_PLAY_VIDEO:
				playNewVideo();
				//playVideo();
				break;

			// 快进
			case MSG_SEEK_FOWARD:
				playProgressContr(PROGRESS_FORWARD, mSeekSpeed);
				break;

			// 快退
			case MSG_SEEK_REWIND:
				playProgressContr(PROGRESS_REWIND, mSeekSpeed);
				break;
				
			case MSG_SHOW_TOAST:
				String strMsg = msg.getData().getString(KEY_MSG_SHOW_TOAST);
				//TLog.i("Tlog", strMsg);
				if (null != strMsg) {
					Toast.makeText(VideoPlayerActivity.this, strMsg, Toast.LENGTH_LONG).show();
				}				
				break;
			}
		}
	};

	/**
	 * @Title: hideControllerDely
	 * @Description: 延迟隐藏进度控制弹出框
	 * @param
	 * @return void
	 * @throws
	 */
	private void hideControllerDely() {
		mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROLLER, TIME_POPDLG_HIDE);
	}

	/**
	 * @Title: cancelDelayHide
	 * @Description: 取消隐藏进度控制弹出框
	 * @param
	 * @return void
	 * @throws
	 */
	private void cancelDelayHide() {
		mHandler.removeMessages(MSG_HIDE_CONTROLLER);
	}

	/**
	 * @Title: showController
	 * @Description: 显示进度控制弹出框
	 * @param
	 * @return void
	 * @throws
	 */
	private void showController() {
		if (!mIsControllerShow) {
			mControlPopupWindow.update(0, 0, mScreenWidth, mControlHeight);
			mIsControllerShow = true;
		}
	}

	/**
	 * @Title: hideController
	 * @Description: 隐藏进度控制弹出框
	 * @param
	 * @return void
	 * @throws
	 */
	private void hideController() {
		if (mControlPopupWindow.isShowing()) {
			mControlPopupWindow.update(mScreenWidth, 0, 0, 0);
			mIsControllerShow = false;
		}
	}

	/**
	 * @Title: reShowController
	 * @Description: 重新显示进度控制弹出框
	 * @param
	 * @return void
	 * @throws
	 */
	private void reShowController() {
		// 假如还未显示
		if (!mIsControllerShow) {
			// 显示
			showController();
			// 延迟自动隐藏
			hideControllerDely();
		} else {// 假如已经显示
				// 取消延迟隐藏
			cancelDelayHide();
			// 重新设置延迟自动隐藏
			hideControllerDely();
		}
	}

	/**
	 * @Title: playProgressContr
	 * @Description: 控制video播放进度
	 * @param iDir
	 *            进度控制方向
	 * @return void
	 * @throws
	 */
	private void playProgressContr(int iDir) {
		if (null != mMediaPlayer) {

			int iDestPos = 0;
			int iCurPos = mMediaPlayer.getCurrentPosition();
			// 快进
			if (PROGRESS_FORWARD == iDir) {
				// 剩余时间
				int iLeftVideoTime = mVideoTimeInt - iCurPos;
				if (iLeftVideoTime <= mSeekSpeed) {
					iDestPos = iCurPos + iLeftVideoTime;
				} else {
					iDestPos = iCurPos + mSeekSpeed;
				}

			} else if (PROGRESS_REWIND == iDir) {// 快退
				// 已播放时间
				int iPlayedVideoTime = iCurPos;
				if (iPlayedVideoTime <= mSeekSpeed) {
					iDestPos = 0;
				} else {
					iDestPos = iCurPos - mSeekSpeed;
				}
			}

			mMediaPlayer.seekTo(iDestPos);
			// 更新控件显示
			mVideoCurPositionTimeInt = iDestPos;
			mTimeSeekBar.setProgress(iDestPos);
			mPlayedTimeTextView.setText(getTime(iDestPos));
		}
	}

	/**
	 * @Title: playProgressContr
	 * @Description: 控制video播放进度
	 * @param iDir
	 *            进度控制方向
	 * @param iSeekTime
	 *            进度控制进度
	 * @return void 返回类型
	 * @throws
	 */
	private void playProgressContr(int iDir, int iSeekTime) {
		if (null != mMediaPlayer && 0 <= iSeekTime) {
			if (null != mMediaPlayer) {

				int iDestPos = 0;
				if (-1 == mIsFirstGetCurPosWhenSeekIdent) {
					mCurPosWhenSeek = mMediaPlayer.getCurrentPosition();
					mIsFirstGetCurPosWhenSeekIdent = 1;
				}

				// 快进
				if (PROGRESS_FORWARD == iDir) {
					iDestPos = mCurPosWhenSeek + iSeekTime;
					if (iDestPos > mVideoTimeInt) {
						iDestPos = mVideoTimeInt;
					}
					// 快退
				} else if (PROGRESS_REWIND == iDir) {
					iDestPos = mCurPosWhenSeek - iSeekTime;
					if (0 > iDestPos) {
						iDestPos = 0;
					}
				}

				mCurPosWhenSeek = iDestPos;

				mMediaPlayer.seekTo(iDestPos);
				// 更新控件显示
				mVideoCurPositionTimeInt = iDestPos;
				mTimeSeekBar.setProgress(iDestPos);
				mPlayedTimeTextView.setText(getTime(iDestPos));

				// 快退到开始位置时，取消自动回退定时器
				if (0 == iDestPos && null != mSeekRewindTimerTask) {
					mSeekRewindTimerTask.cancel();
					mCurPosWhenSeek = 0;
					mIsFirstGetCurPosWhenSeekIdent = -1;
				}

				// 快进到结束位置时，取消自动快进定时器
				if (mVideoTimeInt == iDestPos && null != mSeekFowardTimerTask) {
					mSeekFowardTimerTask.cancel();
					mCurPosWhenSeek = 0;
					mIsFirstGetCurPosWhenSeekIdent = -1;
				}
			}
		}
	}

	/**
	 * @Title: playPause
	 * @Description: 暂停播放
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	private void playPause() {
		if (null != mMediaPlayer) {
			if (mMediaPlayer.isPlaying()) {
				// 暂停
				mMediaPlayer.pause();

				// 记录当前播放位置
				mVideoCurPositionTimeInt = mMediaPlayer.getCurrentPosition();

				// 更新控件
				mPlayButton.setVisibility(View.VISIBLE);
				mPlayPauseButton.setImageResource(R.drawable.video_play);
			}
		}
	}

	/**
	 * @Title: playResume
	 * @Description: 继续播放
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	private void playResume() {
		if (null != mMediaPlayer) {
			// 假如未在播放中
			if (!mMediaPlayer.isPlaying()) {
				// 控件显示更新
				mPlayButton.setVisibility(View.GONE);
				mPlayPauseButton.setImageResource(R.drawable.video_pause);

				// 跳转到新位置，可能在暂停过程中有进度控制
				mMediaPlayer.seekTo(mVideoCurPositionTimeInt);
				mVideoCurPositionTimeInt = -1;

				// 开始播放
				mMediaPlayer.start();
			}
		}
	}

	/**
	 * @Title: playPauseOrResume
	 * @Description: 自动判断控制video暂停还是继续播放
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	private void playPauseOrResume() {
		if (null != mMediaPlayer) {
			// 假如正在播放
			if (mMediaPlayer.isPlaying()) {
				// 暂停
				mMediaPlayer.pause();
				mVideoCurPositionTimeInt = mMediaPlayer.getCurrentPosition();
				mPlayButton.setVisibility(View.VISIBLE);
				mPlayPauseButton.setImageResource(R.drawable.video_play);
			} else {// 假如暂停中
				mPlayButton.setVisibility(View.GONE);
				mPlayPauseButton.setImageResource(R.drawable.video_pause);
				mMediaPlayer.seekTo(mVideoCurPositionTimeInt);
				mVideoCurPositionTimeInt = -1;
				mMediaPlayer.start();
			}
		}
	}

	private class SurfaceCallback implements SurfaceHolder.Callback {

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// 播放视频
			// playVideo();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					getURL();
				}
			}).start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// surfaceView销毁,同时销毁mediaPlayer
			if (null != mMediaPlayer) {
				mMediaPlayer.release();
				mMediaPlayer = null;
			}
		}
		
		private void getURL(){
			String videoUrl = null;
			String command = mCmdSingle.getCommand();
			String url = null;
			if (command.startsWith("MOVIE")) {
				List<Site> moveList = mCmdSingle.getMovie().getVideoSource();
				if(moveList != null && moveList.size() > 0){
					url = moveList.get(0).getUrl();
				}
				
			} else {
				int episode = 1;
				String episodeStr = getIntent().getExtras().getString(MediaDetailActivity.EPISODE);
				if(episodeStr != null){
					episode = Integer.valueOf(episodeStr);
				}
				TLog.i(TAG, "play episode : " + episode);
				List<Episode> tvList = mCmdSingle.getTvPlay().getEpisodes();
				if(tvList != null && tvList.size() > 0){
					url = tvList.get(episode - 1).getUrl();
				}
			}

			String str = "http://120.24.65.74:8080/rs/api/getPlayUrlForFlvcd.json?url=%s&format=super";
			String finalUrl = String.format(str, url);
			TLog.i("videoplayer", "video url" + finalUrl);
			try {
				videoUrl = HttpUtils.getByHttpClient(VideoPlayerActivity.this, finalUrl,
						null);
				TLog.i("videoplayer", "video url" + videoUrl);
				JSONObject ob = new JSONObject(videoUrl);
				videoUrl = ob.optString("playUrl");
				TLog.i("videoplayer", "final video url" + videoUrl);
				
				//假如地址为null或""
				if (TextUtils.isEmpty(videoUrl)) {	
					Message msg = new Message();
					msg.what = MSG_SHOW_TOAST;
					Bundle bundle = new Bundle();
					bundle.putString(KEY_MSG_SHOW_TOAST, "URL解析错误");
					msg.setData(bundle);
					mHandler.sendMessage(msg);
					
					//假如当前正在播放，则忽视该错误
					if(true == mIsInThePlay) {
						return;
					}else {
						mHandler.sendEmptyMessageDelayed(MSG_FINISH_SELF, TIME_ERROR_FINISH);
					}
				}else {//假如不为null或""
					mURLString = videoUrl;
					mHandler.sendEmptyMessage(MSG_PLAY_VIDEO);
				}
				
			} catch (IOException | JSONException e) {
				TLog.i("videoplayer", "IOException");
				e.printStackTrace();
			} catch (Exception e) {
				TLog.i("videoplayer", "Exception");
				e.printStackTrace();
				getPlayUrlLocal(url);
			}
		}
	}

	private void getPlayUrlLocal(final String finalUrl) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String videoUrl = null;
					videoUrl = FlvcdHelper.getFlvcd(finalUrl).get(0);
					TLog.i("videoplayer", "video url" + videoUrl);
					mURLString = videoUrl;
					mHandler.sendEmptyMessage(MSG_PLAY_VIDEO);
				} catch (Exception e) {
					TLog.i("videoplayer", "Exception");
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * @Title: playVideo
	 * @Description: play video
	 * @param @throws IOException 设定文件
	 * @return void
	 * @throws
	 */
	private void playVideo() {
		mMediaPlayer = new MediaPlayer();
		// 重置 mMediaPlayer
		mMediaPlayer.reset();
		// 显示在surfaceview控件上
		mMediaPlayer.setDisplay(mSurfaceHolder);
		// 设置音效
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		// 设置媒体加载完成监听
		mMediaPlayer.setOnPreparedListener(this);
		// 设置媒体播放完毕监听
		mMediaPlayer.setOnCompletionListener(this);
		// 设置错误监听
		mMediaPlayer.setOnErrorListener(this);
		// 设置缓存变化监听
		mMediaPlayer.setOnBufferingUpdateListener(this);
		// 设置URL
		Uri uri = Uri.parse(mURLString);
		try {
			mMediaPlayer.setDataSource(VideoPlayerActivity.this, uri);
			mMediaPlayer.prepareAsync();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "视频加载错误", Toast.LENGTH_LONG).show();
			if(true == mIsInThePlay) {
				return;
			}else {
				mHandler.sendEmptyMessageDelayed(MSG_FINISH_SELF, TIME_ERROR_FINISH);
			}
		}
	}

	/**
	 * @Title: playNewVideo
	 * @Description: 播放下一集/上一集
	 * @param
	 * @return void
	 * @throws
	 */
	private void playNewVideo() {
		// mMediaPlayer对象清理
		clearMediaPlayer();
		// 重置变量
		resetValues();
		// 重置控件状态
		resetViewsState();
		// 播放
		playVideo();
	}

	/*
	 * (非 Javadoc) Title: onPrepared Description: video加载完成
	 * @param mPlayer
	 * @see
	 * android.media.MediaPlayer.OnPreparedListener#onPrepared(android.media.MediaPlayer)
	 */
	@Override
	public void onPrepared(MediaPlayer mPlayer) {
		// 隐藏加载进度条
		mLoadProgressBar.setVisibility(View.INVISIBLE);
		// 进度条自动移动
		//mSeekBarAutoFlag = true;
		// 设置seekbar控件最大值
		mTimeSeekBar.setMax(mMediaPlayer.getDuration());
		// video总时长
		mVideoTimeInt = mMediaPlayer.getDuration();		
		mVideoTimeString = getTime(mVideoTimeInt);
		mTotalTimeTextView.setText(mVideoTimeString);
		//Log.d(TAG, "mVideoTimeInt:" + mVideoTimeInt);
		// 获得每次快进、快退跳动的时间
		mSeekSpeed = getSeekSpeed(mVideoTimeInt);
		// 设置seekbar拖动监听
		mTimeSeekBar.setOnSeekBarChangeListener(new SeekBarChangeListener());
		// 开始播放
		mMediaPlayer.start();
		// 启动更新进度条的线程
		mUpdateProgressThread = new Thread(runnable);
		mUpdateProgressThread.start();
		// 设置屏幕常亮
		mMediaPlayer.setScreenOnWhilePlaying(true);
		mSurfaceHolder.setKeepScreenOn(true);
		
		mIsInThePlay = true;
	}

	/**
	 * @Title: getSeekSpeed
	 * @Description: 获得快进/快退时每一次跳转的时间
	 * @param： videoTime video的总时长
	 * @return int 每一次跳转的时间
	 */
	private int getSeekSpeed(int videoTime) {
		// video总长度 / 跳转次数
		int speed = videoTime / NUM_SEEK_STEP;
		// 每次跳转最小时间
		if (MIN_SEEK_SPEED > speed) {
			speed = MIN_SEEK_SPEED;
		}
		return speed;
	}

	/**
	 * @Fields runnable : 更新进度条线程
	 */
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// 退出时先中断线程
			try {
				mLock.lockInterruptibly();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			try {
				while (true) {
					if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
						mTimeSeekBar.setProgress(mMediaPlayer
								.getCurrentPosition());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				mLock.unlock();
			}

		}
	};

	/**
	 * @ClassName: SeekBarChangeListener
	 * @Description: 监听进度条拖动事件
	 * @author liude
	 * @date 2015年5月21日 下午2:59:25
	 * 
	 */
	private class SeekBarChangeListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar sBar, int progress, boolean fromUser) {
			if (0 <= progress) {
				// 假如是用户拖动
				if (fromUser) {
					cancelAllTimerTask();
					// 更新播放位置
					mMediaPlayer.seekTo(progress);
					mVideoCurPositionTimeInt = progress;
				}
			}
			mPlayedTimeTextView.setText(getTime(progress));
		}

		@Override
		public void onStartTrackingTouch(SeekBar sBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar sBar) {

		}

	}

	/**
	 * @Title: getTime
	 * @Description: 转换播放时间
	 * @param millionTime
	 *            传入毫秒值
	 * @return String 播放时间
	 * @throws
	 */
	@SuppressLint("SimpleDateFormat")
	private String getTime(long millionTime) {
		
		// 获取日历函数
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millionTime);
		SimpleDateFormat sDateFormat = null;
		// 判断是否大于60分钟，如果大于就显示小时。设置日期格式
		if (millionTime / 60000 >= 60) {
			sDateFormat = new SimpleDateFormat("HH:mm:ss");
		} else {
			sDateFormat = new SimpleDateFormat("mm:ss");
		}
		sDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		return sDateFormat.format(calendar.getTime());
	}

	/*
	 * (非 Javadoc) Title: onCompletion Description: 视频播放完毕
	 * @param mPlayer
	 * @see
	 * android.media.MediaPlayer.OnCompletionListener#onCompletion(android.media
	 * .MediaPlayer)
	 */
	@Override
	public void onCompletion(MediaPlayer mPlayer) {
		Toast.makeText(this, "MEDIA_PLAY_COMPLETE", Toast.LENGTH_SHORT).show();
		mTimeSeekBar.setProgress(Integer.parseInt(String.valueOf(mVideoTimeInt)));
		mHandler.sendEmptyMessageDelayed(MSG_FINISH_SELF, TIME_ERROR_FINISH);
	}

	/*
	 * (非 Javadoc) Title: onError Description: 视频播放错误捕捉
	 * @param mPlayer
	 * @param what
	 * @param extra
	 * @return
	 * @see
	 * android.media.MediaPlayer.OnErrorListener#onError(android.media.MediaPlayer
	 * , int, int)
	 */
	@Override
	public boolean onError(MediaPlayer mPlayer, int what, int extra) {
		switch (what) {
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Toast.makeText(this, "MEDIA_ERROR_UNKNOWN", Toast.LENGTH_SHORT).show();
			break;
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			Toast.makeText(this, "MEDIA_ERROR_SERVER_DIED", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

		switch (extra) {
		case MediaPlayer.MEDIA_ERROR_IO:
			Toast.makeText(this, "MEDIA_ERROR_IO", Toast.LENGTH_SHORT).show();
			break;
		case MediaPlayer.MEDIA_ERROR_MALFORMED:
			Toast.makeText(this, "MEDIA_ERROR_MALFORMED", Toast.LENGTH_SHORT).show();
			break;
		case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
			Toast.makeText(this, "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK",
					Toast.LENGTH_SHORT).show();
			break;
		case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
			Toast.makeText(this, "MEDIA_ERROR_TIMED_OUT", Toast.LENGTH_SHORT).show();
			break;
		case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
			Toast.makeText(this, "MEDIA_ERROR_UNSUPPORTED", Toast.LENGTH_SHORT).show();
			break;
		}

		//clearRes();
		// VideoPlayerActivity.this.finish();
		mHandler.sendEmptyMessageDelayed(MSG_FINISH_SELF, TIME_ERROR_FINISH);
		return false;
	}

	/*
	 * (非 Javadoc) Title: onBufferingUpdate Description: 视频缓冲更新
	 * @param mPlayer
	 * @param percent
	 * @see
	 * android.media.MediaPlayer.OnBufferingUpdateListener#onBufferingUpdate
	 * (android.media.MediaPlayer, int)
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer mPlayer, int percent) {
		Log.d(TAG, "onBufferingUpdate():" + percent);
		mLoadProgressBar.setSecondaryProgress(percent);
	}

	/*
	 * (非 Javadoc) Title: dispatchKeyEvent Description: 按键事件监听
	 * @param event
	 * @return
	 * @see android.app.Activity#dispatchKeyEvent(android.view.KeyEvent)
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		cancelAllTimerTask();
		
		int keyEvent = event.getKeyCode();
		switch (keyEvent) {
		// 暂停、播放按键
		case KeyEvent.KEYCODE_SPACE:
			mSeekTimer.cancel();
			// 显示进度控制弹出框
			reShowController();
			// 暂停或继续播放
			playPauseOrResume();
			break;
		// 快退
		case KeyEvent.KEYCODE_DPAD_LEFT:
			mSeekTimer.cancel();
			reShowController();
			mHandler.sendEmptyMessage(MSG_SEEK_REWIND);
			// playProgressContr(PROGRESS_REWIND, mSeekSpeed);
			break;
		// 快进
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			mSeekTimer.cancel();
			reShowController();
			mHandler.sendEmptyMessage(MSG_SEEK_FOWARD);
			// playProgressContr(PROGRESS_FORWARD, mSeekSpeed);
			break;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onBackPressed() {
		clearRes();
		VideoPlayerActivity.this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onStop() {
		super.onStop();
		clearRes();
		VideoPlayerActivity.this.finish();
	}

	/**
	 * @Title: clearRes
	 * @Description: 清除资源
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void clearRes() {
		// 进度控制弹出框清理
		if (null != mControlPopupWindow) {
			mControlPopupWindow.dismiss();
		}

		// mMediaPlayer对象清理
		clearMediaPlayer();
		//退出所有TimerTask
		cancelAllTimerTask();

		mHandler.removeMessages(MSG_HIDE_CONTROLLER);
	}

	/**
	 * @Title: clearMediaPlayer
	 * @Description: 清理MediaPlayer资源
	 * @param
	 * @return void
	 * @throws
	 */
	private void clearMediaPlayer() {
		//中断播放进度更新线程
		if(null != mUpdateProgressThread){
			mUpdateProgressThread.interrupt();
			mUpdateProgressThread = null;
		}
		
		// mMediaPlayer对象清理
		if (null != mMediaPlayer) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
				mIsInThePlay = false;
			}

			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	/*
	 * (非 Javadoc) Title: onClick Description: 按钮click事件监听
	 * @param v
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		cancelAllTimerTask();

		try {
			switch (v.getId()) {
			case R.id.play_button:
				playResume();
				// 显示进度控制弹出框
				reShowController();
				break;

			case R.id.playpause_button:
				// 显示进度控制弹出框
				reShowController();
				playPauseOrResume();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: cancelAllTimerTask
	 * @Description: 终止所有TimerTask
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	private void cancelAllTimerTask() {
		if (null != mSeekFowardTimerTask) {
			mSeekFowardTimerTask.cancel();
		}
		if (null != mSeekRewindTimerTask) {
			mSeekRewindTimerTask.cancel();
		}

		mCurPosWhenSeek = 0;
		mIsFirstGetCurPosWhenSeekIdent = -1;
	}
}
