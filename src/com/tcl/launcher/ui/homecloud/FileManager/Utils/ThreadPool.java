package com.tcl.launcher.ui.homecloud.FileManager.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	private ExecutorService mImageThreadPool = null;

	public ExecutorService getThreadPool(int nCnt) {
		if (mImageThreadPool == null) {
			synchronized (ExecutorService.class) {
				if (mImageThreadPool == null) {				
					mImageThreadPool = Executors.newFixedThreadPool(nCnt);
				}
			}
		}

		return mImageThreadPool;
	}
	
	public synchronized void cancelTask() {
		if(mImageThreadPool != null){
			mImageThreadPool.shutdownNow();
			mImageThreadPool = null;
		}
	}

}
