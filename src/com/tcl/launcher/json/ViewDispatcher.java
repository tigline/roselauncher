package com.tcl.launcher.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.json.entry.MutiCmd;
import com.tcl.launcher.json.entry.Page;
import com.tcl.launcher.util.Utils;

public class ViewDispatcher {
	private static ViewDispatcher viewDispatcher = null;

	private ViewDispatcher() {
	}

	public static ViewDispatcher getInstance() {
		if (null == viewDispatcher) {
			viewDispatcher = new ViewDispatcher();
		}

		return viewDispatcher;
	}

	private static String[] cmdList = { "MOVIE_SHOW_LIST", "TVPLAY_SHOW_LIST", "VARIETY_SHOW_LIST",
			"CARTOON_SHOW_LIST", "MUSIC_SHOW_LIST", "MUSIC_LIST_PLAY", "ALL_SHOW_LIST" };
	private static String[] cmdSingle = { "MOVIE_SHOW_SINGLE", "MOVIE_PLAY", "TVPLAY_SHOW_SINGLE",
			"TVPLAY_PLAY", "VARIETY_SHOW_SINGLE", "VARIETY_PLAY", "CARTOON_SHOW_SINGLE",
			"CARTOON_PLAY", "MUSIC_SHOW_SINGLE", "MUSIC_PLAY" };
	private static String[] cmdCtrl = { "TV_GO_HELP", "TV_START_APP", "TV_GO_BACK", "TV_GO_HOME",
			"TV_GO_LEFT", "TV_GO_RIGHT", "TV_GO_DOWN", "TV_GO_UP", "TV_GO_OK", "TV_OPEN_MENU",
			"TV_START_UP", "TV_SHUTDOWN", "TV_VOL_ADD", "TV_VOL_LOWER", "TV_VOL_MUTE_ON",
			"TV_VOL_MUTE_OFF", "TV_SOUND_MODE_SET", "TV_AROUND_SOUND_PRO_SET", "TV_SOUNDTRACK",
			"DEVICE_ONE_POWER_OPEN", "DEVICE_ONE_POWER_CLOSE", "DEVICE_TWO_POWER_OPEN",
			"DEVICE_TWO_POWER_CLOSE", "DEVICE_THREE_POWER_OPEN", "DEVICE_THREE_POWER_CLOSE",
			"DEVICE_FOUR_POWER_OPEN", "DEVICE_FOUR_POWER_CLOSE", "AIR_CONDITION_OPEN",
			"AIR_CONDITION_CLOSE", "AIR_CONDITION_TEMP_SET", "AIR_CONDITION_TEMP_ADD_N",
			"AIR_CONDITION_TEMP_SUB_N", "AIR_CONDITION_TEMP_SUB_L", "AIR_CONDITION_TEMP_SUB_M",
			"AIR_CONDITION_TEMP_SUB_H", "AIR_CONDITION_TEMP_ADD_L", "AIR_CONDITION_TEMP_ADD_M",
			"AIR_CONDITION_TEMP_ADD_H", "AIR_CONDITION_FAN_LOW", "AIR_CONDITION_FAN_MIDDLE",
			"AIR_CONDITION_FAN_HIGH", "AIR_CONDITION_MODLE_BLOW", "AIR_CONDITION_MODLE_HEAT",
			"AIR_CONDITION_MODLE_COOL", "AIR_CONDITION_MODLE_DRY", "AIR_CONDITION_MODLE_AUTO",
			"AIR_CONDITION_REQ_ACTUAL", "AIR_CONDITION_REQ_HISTORY", "AIR_CONDITION_REQ_QUIT",
			"HOME_CLOUD_OPEN_CAMERA", "HOME_CLOUD_TAKE_PHOTO", "HOME_CLOUD_PLAY_LAST_PHOTO",
			"HOME_CLOUD_PHOTO_QUIT", "HOME_CLOUD_SEARCH_PHOTO", "HOME_CLOUD_PLAY_SELECT_PHOTO",
			"HOME_CLOUD_SEARCHE_QUIT", "HOME_CLOUD_NOTICE_PLAY", "HOME_CLOUD_NOTICE_QUIT", "PLAY",
			"PAUSE", "SPEED", "BACKWARKS", "MOVIE_PLAY_CURRENT", "TVPLAY_PLAY_CURRENT",
			"LAST_EPISODE", "NEXT_EPISODE", "TVPLAY_PLAY_EPISODE" };

	/**
	 * json串解析成对象处理
	 */
	public Object JsonToObject(String json) {
		String action_value = null;

		try {

			JSONObject jsonObject = new JSONObject(json);

			JSONArray cmdArray = jsonObject.optJSONArray(ParseJsonUtil.ACTION_COMMAND);
			if (null != cmdArray) {
				MutiCmd mutiCmd = new MutiCmd();

				try {
					mutiCmd.setRc(jsonObject.optString(ParseJsonUtil.RC));
					mutiCmd.setTips(jsonObject.optString(ParseJsonUtil.TIPS));
					mutiCmd.setQuestion(jsonObject.optString(ParseJsonUtil.QUESTION));

					String errorArr = jsonObject.optString("error");
					mutiCmd.setError(errorArr);

					JSONArray dataArray = jsonObject.optJSONArray(ParseJsonUtil.ACTION_DATA);

					if (null != dataArray) {
						String[] commands = new String[dataArray.length()];
						List<CmdCtrl> datas = new ArrayList<CmdCtrl>();

						for (int i = 0; i < dataArray.length(); i++) {
							commands[i] = cmdArray.optString(i);
							CmdCtrl ctrl = new CmdCtrl();
							ctrl.setDomain(dataArray.optJSONObject(i).optString(
									ParseJsonUtil.DOMAIN));
							ctrl.setCommand(dataArray.optJSONObject(i).optString(
									ParseJsonUtil.COMMAND));

							JSONArray jsonArray = dataArray.optJSONObject(i).optJSONArray(
									ParseJsonUtil.PARS);
							HashMap<String, String> pars = null;
							if (null != jsonArray) {
								pars = new HashMap<String, String>();
								for (int j = 0; j < jsonArray.length(); j++) {
									JSONObject items = jsonArray.optJSONObject(j);
									pars.put(items.optString("key"), items.optString("value"));
								}
							}
							ctrl.setPars(pars);
							datas.add(ctrl);
						}
						mutiCmd.setCmds(commands);
						mutiCmd.setDatas(datas);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				return mutiCmd;
			}

			action_value = jsonObject.optString(ParseJsonUtil.ACTION_COMMAND);

			if (Utils.isNotEmpty(action_value)) {
				if ("LEAD_DIALOG".equalsIgnoreCase(action_value)) {
					return CmdCtrlParse.getInstance().parseCmdCtrl(jsonObject);
				} else if (Arrays.asList(cmdList).contains(action_value)) {
					return CmdListParse.getInstance().parseCmdList(jsonObject);
				} else if (Arrays.asList(cmdSingle).contains(action_value)) {
					return CmdSingleParse.getInstance().parseCmdSingle(jsonObject);
				} else if (Arrays.asList(cmdCtrl).contains(action_value)) {
					return CmdCtrlParse.getInstance().parseCmdCtrl(jsonObject);
				} else {
					return CmdCtrlParse.getInstance().parseCmdCtrl(jsonObject);
				}
			} else {
				Page page = new Page();
				page.setRc(jsonObject.optString(ParseJsonUtil.RC));
				page.setError(jsonObject.optString(ParseJsonUtil.ERROR));
				page.setTips(jsonObject.optString(ParseJsonUtil.TIPS));
				return page;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return action_value;
	}

}
