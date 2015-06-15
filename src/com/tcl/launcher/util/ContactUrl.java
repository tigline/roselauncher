package com.tcl.launcher.util;

import android.text.TextUtils;

import com.tcl.launcher.entry.UrlParameter;

/**
 * 从URL获取数据 然后进行处理
 */
public class ContactUrl {
	private static final String TAG = "ContactUrl";
	
	/**
	 * 拼接语义请求路径
	 */
	public static String contactSemanticUrl(UrlParameter parameter) {
		StringBuffer stemp = null;
		stemp = new StringBuffer(Constant.SemanticUrl.BASE_SEMANTIC_URL);

		fillPara(parameter, stemp);

		TLog.i(TAG,"url:" + stemp.toString());

		return stemp.toString();
	}

	/**
	 * 参数拼接
	 * 
	 * @param parameter
	 * @param stemp
	 */
	private static void fillPara(UrlParameter parameter, StringBuffer stemp) {
		if (null != parameter) {
			stemp.append("&question=");
			stemp.append(parameter.getQuestion());
			stemp.append("&IMEI=");
			stemp.append(TextUtils.isEmpty(parameter.getImei())? "test1234" : parameter.getImei());
			stemp.append("&IMSI=");
			stemp.append(parameter.getImsi());
			stemp.append("&SDK=");
			stemp.append(parameter.getSdk());
			stemp.append("&model=");
			stemp.append(parameter.getModel());
			stemp.append("&OS=");
			stemp.append(parameter.getOs());
			stemp.append("&latitude=");
			stemp.append(parameter.getLatitude());
			stemp.append("&longitude=");
			stemp.append(parameter.getLongitude());
			stemp.append("&domain=");
			stemp.append(parameter.getDomain());
			stemp.append("&pageid=");
			stemp.append(parameter.getPageid());

			if (Utils.isNotEmpty((parameter.getAddress()))) {
				stemp.append("&address=");
				stemp.append(ClientRequestUtil.encode(ClientRequestUtil.encode((parameter
						.getAddress()))));
			} else {
				stemp.append("&address=");
				stemp.append(ClientRequestUtil.encode(ClientRequestUtil
						.encode((Constant.DEFULT_ADDR))));
			}
		} else {
			stemp.append("?IMEI=453");
		}
	}
}
