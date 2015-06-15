package com.tcl.launcher.ui.homecloud.Classlib.Const;

/**
 * @author qinbl
 *
 */
public class Const {
	
	
	public enum StatusCode
	{
		NetException,
		Success,
	}

	public enum CapacityType
	{
		memory_card,
		SD_card,
		USB,
	}
	
	public enum ServerStatus
	{
		Starting,
		Running,
		Stoped,
	}
	
	
	public enum ResultType
	{
		succ,
		failed,
		exist,
	}
	
	public enum ModuleType
	{
		sys,
		photo,
		music,
		video,
		document,
		frends,
		management,
		share,
		other,
	}
	
	public final static String USER_INFO = "userInfo";
	
	public final static int NatTraversalServiceVerCode = 205;
	
	/*OTT盒子
	public final static String MEMORY_DIR_FILTER = "/mnt/sdcard";
	public final static String SD_DIR_FILTER = "/mnt/external_s";
	public final static String USB_DIR_FILTER = "/mnt/usb_storage/";
	*/
	
	//小七盒子
	public final static String MEMORY_DIR_FILTER = "/mnt/sdcard";
	public final static String SD_DIR_FILTER = "/storage/external_s";
	public final static String USB_DIR_FILTER = "/storage/external_storage/sdb";
}
