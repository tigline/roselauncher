package com.tcl.launcher.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @author caomengqi/caomengqi@tcl.com
 * 2015年5月8日
 * @JDK version 1.8
 * @brief 和语音控制进行通信的服务
 * @version
 */
public class VoiceVideoService extends Service{
	private LocalBinder mBinder = new LocalBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}



	public class LocalBinder extends Binder{
		public VoiceVideoService getService(){
			return VoiceVideoService.this;
		}
	}
}
