package com.tcl.launcher.http;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author caomengqi
 * 2015年1月16日
 * @JDK version 1.8
 * @brief  api for http protocol
 * @version
 */
public class HttpUtils {
	private static final String TAG = "HttpUtils";
	
	static RequestQueue mRequestQueue;
	static HttpUtils mHttpUtils;
			
	
	private HttpUtils(Context context){
		mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
	}
	
	public static HttpUtils getInstance(Context context){
		if(mHttpUtils == null)
			mHttpUtils = new HttpUtils(context);
		
		return mHttpUtils;
	}
	
	// 网络连接部分
		public static String postByHttpURLConnection(String strUrl,
				NameValuePair... nameValuePairs) {
			return CustomHttpURLConnection.PostFromWebByHttpURLConnection(strUrl,
					nameValuePairs);
		}

		public static String getByHttpURLConnection(String strUrl,
				NameValuePair... nameValuePairs) {
			return CustomHttpURLConnection.GetFromWebByHttpUrlConnection(strUrl,
					nameValuePairs);
		}

		public static String postByHttpClient(Context context,String strUrl,
				NameValuePair... nameValuePairs) {
			return CustomHttpClient.PostFromWebByHttpClient(context,strUrl, nameValuePairs);
		}

		public static String getByHttpClient(Context context,String strUrl,
				NameValuePair... nameValuePairs) throws Exception {
			return CustomHttpClient.getFromWebByHttpClient(context,strUrl, nameValuePairs);
		}

		// ------------------------------------------------------------------------------------------
		// 网络连接判断
		// 判断是否有网络
//		public static boolean isNetworkAvailable(Context context) {
//			return NetWorkHelper.isNetworkAvailable(context);
//		}
		
		
}
