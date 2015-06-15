package com.tcl.launcher.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;

import com.tcl.launcher.LauncherApplication;
import com.tcl.launcher.R;
import com.tcl.launcher.json.ViewDispatcher;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.json.entry.CmdList;
import com.tcl.launcher.json.entry.CmdSingle;
import com.tcl.launcher.json.entry.MutiCmd;
import com.tcl.launcher.json.entry.Page;
import com.tcl.launcher.player.VideoPlayerActivity;
import com.tcl.launcher.ui.MediaDetailActivity;
import com.tcl.launcher.ui.MediaSearchResultActivity;
import com.tcl.launcher.util.TLog;
import com.tcl.rosesmarthome.SmartHome;

/**
 * @author caomengqi/caomengqi@tcl.com 2015年5月26日
 * @JDK version 1.8
 * @brief 语音控制类，从后台接收到和语音接口通信的消息后，通知前台的更新。
 * @version
 */
public class VoiceControl {
	private static final String TAG = "VoiceControl";

	public static final String CMD_PAGE = "CmdPage";

	public static final String COMMAND_PLAY = "_PLAY";

	public static final int RC_SUCCESS = 0;
	public static final int RC_ERROR_INVALID_REQUEST = 1;
	public static final int RC_ERROR_SERVER_INNER_ERROR = 2;
	public static final int RC_ERROR_SEMANTIC_ERROR = 3;
	public static final int RC_ERROR_UNKOWN_SCENERY = 4;

	private TvGlobalCommand mTvGlobalCommand;
	private AirConditionCommand mAirConditionCommand;
	private DeviceCommand mdDeviceCommand;
	private HomeCloudCommand mHomeCloudCommand;
	//
	// // 电影， 电视剧， 动漫， 综艺， 音乐， 智能电视控制， 开关， 空调， 家庭云
	// public static final String[] DOMAINS = { "MOVIE", "TVPLAY", "CARTOON",
	// "VARIETY", "MUSIC",
	// "TV", "DEVICE", "AIR", "HOME_CLOUD" };

	private static VoiceControl mControl;
	private LauncherApplication mContext;

	public SmartHome mSmartHome; // HZ SmartHome zgx@tcl.com 2015.6.4

	private VoiceControl(LauncherApplication context) {
		mContext = context;

		mSmartHome = new SmartHome(); // HZ SmartHome zgx@tcl.com 2015.6.4
		mSmartHome.createSmartHome(mContext); // HZ SmartHome zgx@tcl.com
												// 2015.6.4

		mTvGlobalCommand = new TvGlobalCommand(context);
		mAirConditionCommand = new AirConditionCommand(context, mSmartHome); // HZ
		// SmartHome
		// zgx@tcl.com
		// 2015.6.4
		mdDeviceCommand = new DeviceCommand(context, mSmartHome); // HZ
																	// SmartHome
																	// zgx@tcl.com
																	// 2015.6.4
		mHomeCloudCommand = new HomeCloudCommand(context);
	}

	public static VoiceControl getInstance(LauncherApplication context) {
		if (mControl == null) {
			mControl = new VoiceControl(context);
		}
		return mControl;
	}

	public void openActivity(Activity activity, Class<?> pClass, Bundle bundle) {
		Intent intent = new Intent(activity, pClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
	}

	//
	// public void sendBroadCast(String action, Bundle bundle) {
	// Intent intent = new Intent(action);
	// if (bundle != null) {
	// intent.putExtras(bundle);
	// }
	// mContext.sendBroadcast(intent);
	// }

	/**
	 * 对语义解析之后的命令进行解析。
	 * 
	 * @param result
	 *            返回命令的json格式
	 */
	public void handResponse(String result) {
		if (TextUtils.isEmpty(result)) {
			mContext.sendHanlderMsg(LauncherApplication.HANDLER_ERROR_TIPS,
					mContext.getString(R.string.http_error));
			return;
		}
		TLog.i(TAG, "result : " + result);
		ViewDispatcher dispatcher = ViewDispatcher.getInstance();
		Object object = dispatcher.JsonToObject(result);
		if (object == null) {
			mContext.sendHanlderMsg(LauncherApplication.HANDLER_ERROR_TIPS,
					mContext.getString(R.string.http_error));
			return;
		}

		// dispatcher.disposeObject(VoiceVideoApplication.this, object,
		// null);
		Page page = (Page) object;
		int rc = Integer.valueOf(page.getRc());
		if (rc != RC_SUCCESS) {
			String error = page.getError();
			if (TextUtils.isEmpty(error)) {
				error = page.getTips();
			}
			if (!TextUtils.isEmpty(error)) {
				mContext.speek(error);
			}
			mContext.sendHanlderMsg(LauncherApplication.HANDLER_ERROR_TIPS, error);
			return;
		}

		mContext.sendHanlderMsg(LauncherApplication.HANDLER_VOICE_MSG, object);
	}

	/**
	 * 根据解析完成的实体类执行对应的指令
	 * 
	 * @param object
	 */
	public void onObjectResponse(Object object) {
		if (object instanceof MutiCmd) {
			MutiCmd mutiCmd = (MutiCmd) object;
			onMutiCmdResponse(mutiCmd);
		} else if (object instanceof CmdList) {
			CmdList cmdList = (CmdList) object;
			onListResponse(cmdList);
		} else if (object instanceof CmdSingle) {
			CmdSingle cmdSingle = (CmdSingle) object;
			onSingleResponse(cmdSingle);
		} else if (object instanceof CmdCtrl) {
			CmdCtrl cmdCtrl = (CmdCtrl) object;
			onCtrlResponse(cmdCtrl);
		}
	}
	
	/**
	 * 多命令的处理
	 * @param mutiCmd
	 */
	private void onMutiCmdResponse(MutiCmd mutiCmd) {
		String[] cmds = mutiCmd.getCmds();
		TLog.i(TAG, "onMutiCmdResponse");
		if (cmds != null && cmds.length > 0) {
			TLog.i(TAG, "onMutiCmdResponse : " + mutiCmd.getCmds().length);
			CmdCtrl cmdCtrl = new CmdCtrl();
			for(int i = 0; i < cmds.length; i++){
				cmdCtrl.setCommand(cmds[i]);
				onCtrlResponse(cmdCtrl);
			}
		}

	}

	/**
	 * 执行列表的指令
	 * 
	 * @param cmdList
	 */
	private void onListResponse(CmdList cmdList) {
		Bundle b = new Bundle();
		b.putSerializable(CMD_PAGE, cmdList);
		openActivity(mContext.getLastActivity(), MediaSearchResultActivity.class, b);
	}

	/**
	 * 执行详情的页面
	 * 
	 * @param cmdList
	 */
	private void onSingleResponse(CmdSingle cmdSingle) {
		String command = cmdSingle.getCommand();
		if (command != null && command.contains(COMMAND_PLAY)) {
			Bundle b = new Bundle();
			b.putSerializable(CMD_PAGE, cmdSingle);
			openActivity(mContext.getLastActivity(), VideoPlayerActivity.class, b);
		} else {
			Bundle b = new Bundle();
			b.putSerializable(CMD_PAGE, cmdSingle);
			openActivity(mContext.getLastActivity(), MediaDetailActivity.class, b);
		}

	}

	/**
	 * 执行控制命令
	 * 
	 * @param cmdCtrl
	 */
	private void onCtrlResponse(CmdCtrl cmdCtrl) {
		TLog.i(TAG, "onControlCommand");
		mContext.onControlCommand(cmdCtrl);
	}

	public void excuteTvControlCommand(CmdCtrl cmdCtrl) {
		mTvGlobalCommand.exCuteCommand(cmdCtrl);
	}

	public void excuteDeviceControlCommand(CmdCtrl cmdCtrl) {
		mdDeviceCommand.exCuteCommand(cmdCtrl); // HZ SmartHome zgx@tcl.com
												// 2015.6.4
	}

	public void excuteAirConditionControlCommand(CmdCtrl cmdCtrl) {
		mAirConditionCommand.exCuteCommand(cmdCtrl); // HZ SmartHome zgx@tcl.com
														// 2015.6.4
	}

	public void excuteHomeCloudCommand(CmdCtrl cmdCtrl) {
		mHomeCloudCommand.exCuteCommand(cmdCtrl);
	}
}
