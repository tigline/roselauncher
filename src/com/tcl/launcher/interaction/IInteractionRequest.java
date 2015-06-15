package com.tcl.launcher.interaction;

import com.tcl.launcher.core.VoiceControl;

import android.os.Handler;


/**
 * 用户请求，服务器交互
 * 
 * @author pc
 * 
 */
public interface IInteractionRequest
{
	public void question(String server_url,VoiceControl control);
}
