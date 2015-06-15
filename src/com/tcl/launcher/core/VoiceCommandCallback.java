package com.tcl.launcher.core;

import com.tcl.launcher.json.entry.CmdCtrl;

/**
 * @author caomengqi/caomengqi@tcl.com
 * 2015年5月21日
 * @JDK version 1.8
 * @brief  对已经解析好的语音命令已经处理，各个界面需实现该接口.
 * @version
 */
public interface VoiceCommandCallback {
	/**
	 * 本地命令的响应
	 * @param command 语音指令
	 */
	public boolean onLocalCommandDeal(CmdCtrl cmdCtrl);
}
