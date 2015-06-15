package com.tcl.launcher.ui.homecloud.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.os.storage.StorageManager;

public class StorageList {
	private Context mActivity;
	private StorageManager mStorageManager;
	private Method mMethodGetPaths;
	
	public StorageList(Context activity) {
		mActivity = activity;
		if (mActivity != null) {
			mStorageManager = (StorageManager)mActivity
					.getSystemService(Activity.STORAGE_SERVICE);
			try {
				mMethodGetPaths = mStorageManager.getClass()
						.getMethod("getVolumePaths");
				//通过调用类的实例mStorageManager的getClass()获取StorageManager类对应的Class对象  
                //getMethod("getVolumePaths")返回StorageManager类对应的Class对象的getVolumePaths方法，这里不带参数  
                //getDeclaredMethod()----可以不顾原方法的调用权限  
                //mMethodGetPathsState=mStorageManager.getClass().  
                //      getMethod("getVolumeState",String.class);//String.class形参列表  
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String[] getVolumePaths() {
		String[] paths = null;
		try {
			paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return paths;
	}
}
