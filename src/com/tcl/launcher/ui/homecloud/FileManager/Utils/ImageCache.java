package com.tcl.launcher.ui.homecloud.FileManager.Utils;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache {
	
	private LruCache<String, Bitmap> mMemoryCache;
	
	public ImageCache(){
		int maxMemory = (int) Runtime.getRuntime().maxMemory();  
        int mCacheSize = maxMemory / 8;
      
		mMemoryCache = new LruCache<String, Bitmap>(mCacheSize){
			
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
			
		};	
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}
}
