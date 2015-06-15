package com.tcl.launcher.util;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

/**
 * @author caomengqi/caomengqi@tcl.com
 * 2015年5月20日
 * @JDK version 1.8
 * @brief  从网络获取图片
 * @version
 */
public class ImgLoader {
	private Context mContext;
	
	private static ImgLoader mImgLoader;
	private RequestQueue mQueue;
	private ImageLoader mImageLoader;
	
	private ImgLoader(Context context){
		mContext = context;
		mQueue = Volley.newRequestQueue(context.getApplicationContext());
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}
	
	public static ImgLoader getInstance(Context context){
		if(mImgLoader == null){
			mImgLoader = new ImgLoader(context);
		}
		return mImgLoader;
	}
	
	public void loadImage(String imgUrl, ImageView view, int defaultPicId, int failedPicId){
		ImageListener listener = ImageLoader.getImageListener(view, defaultPicId, failedPicId);
		mImageLoader.get(imgUrl, listener);
	}
}
