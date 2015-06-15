package com.tcl.launcher.util;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;

import com.tcl.launcher.LauncherApplication;

/**
 * 提示音播放器
 * 
 * @author pc
 * 
 */
public class TipsVoicePlayer implements OnPreparedListener, OnCompletionListener, OnErrorListener {
	private static TipsVoicePlayer voicePlayer = null;

	private LauncherApplication voiceVideoApplication = null;

	private MediaPlayer tipMediaPlayer = null;

	private TipsVoicePlayer(LauncherApplication voiceVideoApplication) {
		this.voiceVideoApplication = voiceVideoApplication;
	}

	public static TipsVoicePlayer getInstance(LauncherApplication voiceVideoApplication) {
		if (null == voicePlayer) {
			voicePlayer = new TipsVoicePlayer(voiceVideoApplication);
		}

		return voicePlayer;
	}

	@Override
	public boolean onError(MediaPlayer mediaPlayer, int arg1, int arg2) {
		stopLocalMp3();
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mediaPlayer) {
		stopLocalMp3();
	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		if (null != tipMediaPlayer) {
			tipMediaPlayer.start();
		}
	}

	/**
	 * 播放提示音
	 */
	public void playLocalMp3() {
		try {
			if (null == tipMediaPlayer) {
				tipMediaPlayer = new MediaPlayer();
			}

			AssetManager assetManager = voiceVideoApplication.getAssets();
			AssetFileDescriptor fileDescriptor = assetManager.openFd("tips.mp3");
			tipMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
					fileDescriptor.getStartOffset(), fileDescriptor.getLength());
			tipMediaPlayer.setOnCompletionListener(this);
			tipMediaPlayer.setOnPreparedListener(this);
			tipMediaPlayer.setOnErrorListener(this);
			tipMediaPlayer.prepareAsync();
		} catch (Exception e) {
			TLog.i("TipsVoicePlayer", "playLocalMp3 Exception:" + e.toString());
		}
	}

	/**
	 * 播放提示音
	 */
	public void stopLocalMp3() {
		try {
			if (null != tipMediaPlayer) {
				tipMediaPlayer.stop();
				tipMediaPlayer.release();
				tipMediaPlayer = null;
			}
		} catch (Exception e) {
			TLog.i("TipsVoicePlayer", "playLocalMp3 Exception:" + e.toString());
		}
	}
}
