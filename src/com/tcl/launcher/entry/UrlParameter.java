package com.tcl.launcher.entry;

import java.io.Serializable;
import java.net.URLEncoder;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 视频伴侣 URL参数实体类
 */
public class UrlParameter implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String latitude;// 纬度

	private String longitude;// 经度

	private String time;

	private String address;

	private String imei;// 移动设备身份码

	private String imsi;// 国际移动用户识别码

	private String sdk;// Android sdk版本

	private String model;// 机器型号

	private String os;// 操作系统

	private String question;// 用户问题

	private String domain;

	private String pageid;

	public String getPageid()
	{
		return pageid;
	}

	public void setPageid(String pageid)
	{
		this.pageid = pageid;
	}

	@SuppressWarnings("deprecation")
	public UrlParameter(Context context)
	{
		try
		{
			TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			this.imei = manager.getDeviceId();
			this.imsi = manager.getSubscriberId();
			this.sdk = android.os.Build.VERSION.SDK;
			String android_model = android.os.Build.MODEL;
			this.model = URLEncoder.encode(android_model, "UTF-8");
			this.os = android.os.Build.VERSION.RELEASE;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getImei()
	{
		return imei;
	}

	public void setImei(String imei)
	{
		this.imei = imei;
	}

	public String getImsi()
	{
		return imsi;
	}

	public void setImsi(String imsi)
	{
		this.imsi = imsi;
	}

	public String getSdk()
	{
		return sdk;
	}

	public void setSdk(String sdk)
	{
		this.sdk = sdk;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public String getOs()
	{
		return os;
	}

	public void setOs(String os)
	{
		this.os = os;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}

	public String getDomain()
	{
		return domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}

}
