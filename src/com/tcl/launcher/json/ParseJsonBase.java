package com.tcl.launcher.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcl.launcher.json.entry.Command;

/**
 * json解析
 * 
 * @author
 * 
 */
public class ParseJsonBase {
	// 获取当前页面动态指令
	public List<Command> resolveCommand(JSONObject jsonObject) throws JSONException {
		List<Command> commands = null;

		try {
			JSONArray jsonArray = jsonObject.optJSONArray(ParseJsonUtil.DYNAMIC_COMMAND);
			if (null != jsonArray && jsonArray.length() > 0) {
				commands = new ArrayList<Command>();
				for (int i = 0; i < jsonArray.length(); i++) {
					Command command = new Command();

					JSONObject items = jsonArray.optJSONObject(i);
					command.setCommand(items.optString(ParseJsonUtil.COMMAND));
					command.setStatement(items.optString(ParseJsonUtil.STATEMENT));
					command.setMatchtype(items.optString(ParseJsonUtil.MATCHTYPE));

					command.setPars(getPars(items));

					commands.add(command);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return commands;
	}

	public HashMap<String, String> getPars(JSONObject json) {
		HashMap<String, String> pars = null;
		try {
			JSONArray jsonArray = json.optJSONArray(ParseJsonUtil.PARS);

			if (null != jsonArray) {
				pars = new HashMap<String, String>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject items = jsonArray.optJSONObject(i);
					pars.put(items.optString("key"), items.optString("value"));
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return pars;
	}

}
