package com.tcl.launcher.util;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.tcl.launcher.entry.UrlParameter;

public class LocationUtil {
	private static final String TAG = "LocationUtil";
	
	private LocationClient locationClient = null;

	public LocationUtil(Context context, final UrlParameter parameter) {
		TLog.i(TAG,"BaiDuLocation4..............");

		locationClient = new LocationClient(context);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setPoiExtraInfo(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setScanSpan(500); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.disableCache(true);// 禁止启用缓存定位
		locationClient.setLocOption(option);

		// 注册位置监听器
		locationClient.registerLocationListener(new BDLocationListener() {

			public void onReceiveLocation(BDLocation location) {
				if (location == null)
					return;

				if (Utils.isNotEmpty("" + location.getLatitude())) {
					parameter.setLatitude("" + location.getLatitude());
				} else {
					parameter.setLatitude("");
				}

				if (Utils.isNotEmpty("" + location.getLongitude())) {
					parameter.setLongitude("" + location.getLongitude());
				} else {
					parameter.setLongitude("");
				}

				TLog.i(TAG,"定位类型为：" + location.getLocType() + "__________________定位地址为："
						+ location.getAddrStr());

				if (location.getLocType() == BDLocation.TypeNetWorkLocation)// 天气预报没有显示，是因为没有得到地址，这行代码没有进入
				{
					if (Utils.isNotEmpty(location.getAddrStr())) {
						parameter.setAddress(location.getAddrStr());
						TLog.i(TAG,"定位地址为：" + location.getAddrStr());
					} else {
						TLog.i(TAG,"address value is defult....");
					}
				}
			}

			public void onReceivePoi(BDLocation location) {
			}

		});
	}

	/**
	 * 定位
	 */
	public void getLocation() {
		try {
			if (null != locationClient) {
				if (!locationClient.isStarted()) {
					locationClient.start();
				}

				locationClient.requestLocation();
			} else {
				TLog.i(TAG,"定位出现异常！");
			}
		} catch (Exception e) {
			TLog.i(TAG,"getLocation Exception:" + e.toString());
		}
	}
}
