package com.tcl.launcher.json;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tcl.launcher.json.entry.CmdCtrl;

/**
 * 列表命令解析
 * 
 * @author 
 * 
 */
public class CmdCtrlParse extends ParseJsonBase
{

	private static CmdCtrlParse parse = null;

	private CmdCtrlParse()
	{
	}

	public static CmdCtrlParse getInstance()
	{
		if (null == parse)
		{
			parse = new CmdCtrlParse();
		}

		return parse;
	}

	public Object parseCmdCtrl(JSONObject json)
	{

		CmdCtrl ctrl = new CmdCtrl();

		try
		{
			ctrl.setRc(json.optString(ParseJsonUtil.RC));
			ctrl.setCommand(json.optString(ParseJsonUtil.ACTION_COMMAND));
			ctrl.setTips(json.optString(ParseJsonUtil.TIPS));
			ctrl.setQuestion(json.optString(ParseJsonUtil.QUESTION));
			
			JSONObject error = json.optJSONObject(ParseJsonUtil.ERROR);
			if(null != error){
				String errorArr = error.optString("error");
				ctrl.setError(errorArr);
			}

			JSONObject content = json.optJSONObject(ParseJsonUtil.ACTION_DATA);

			if (null != content)
			{				
				ctrl.setDomain(content.optString(ParseJsonUtil.DOMAIN));
				
				JSONArray jsonArray = content.optJSONArray(ParseJsonUtil.PARS);
				HashMap<String, String> pars = null;
				if (null != jsonArray)
				{
					pars = new HashMap<String, String>();
					for (int i = 0; i < jsonArray.length(); i++)
					{
						JSONObject items = jsonArray.optJSONObject(i);					
						pars.put(items.optString("key"), items.optString("value"));						
					}
				}
				ctrl.setPars(pars);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return ctrl;
	}


}
