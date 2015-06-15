package com.tcl.launcher.widget;

import android.media.AudioManager;

public class VolumeView {
	private static final String TAG = "VolumeView";
	
	private boolean isShowing;
	private Object mMutex;
	private AudioManager mAudioManager;
	
	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}

	public void disspear(){
		synchronized(mMutex){
			if(isShowing){
				isShowing = false;
			}
		}
	}
	
	public void show(){
		synchronized(mMutex){
			if(!isShowing){
				isShowing = true;
			}
		}
	}
	
	private void initMutex(){
		if(mMutex == null){
			mMutex = new Object();
		}
	}
	
	public void increVolume(int value){
		mAudioManager.adjustVolume(AudioManager.ADJUST_RAISE, 0);
	}
	
	public void decreVolume(int value){
		mAudioManager.adjustVolume(AudioManager.ADJUST_LOWER, 0);
	}
	
}
