package com.tcl.launcher.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tcl.launcher.json.entry.CmdList;
import com.tcl.launcher.json.entry.Command;
import com.tcl.launcher.json.entry.VodBrief;

/**
 * 列表命令解析
 * 
 * @author 
 * 
 */
public class CmdListParse extends ParseJsonBase
{

	private static CmdListParse parse = null;

	private CmdListParse()
	{
	}

	public static CmdListParse getInstance()
	{
		if (null == parse)
		{
			parse = new CmdListParse();
		}

		return parse;
	}

	public Object parseCmdList(JSONObject json)
	{

		CmdList list = new CmdList();

		try
		{
			list.setRc(json.optString(ParseJsonUtil.RC));
			list.setQuestion(json.optString(ParseJsonUtil.QUESTION));
			list.setCommand(json.optString(ParseJsonUtil.ACTION_COMMAND));
			list.setTips(json.optString(ParseJsonUtil.TIPS));
			
			JSONObject error = json.optJSONObject(ParseJsonUtil.ERROR);
			if(null != error){
				String errorArr = error.optString("error");
				list.setError(errorArr);
			}

			JSONObject content = json.optJSONObject(ParseJsonUtil.ACTION_DATA);

			if (null != content)
			{				
				list.setDomain(content.optString(ParseJsonUtil.DOMAIN));
				list.setTotal(content.optInt(ParseJsonUtil.TOTAL));
				list.setPs(content.optInt(ParseJsonUtil.PS));
				list.setPc(content.optInt(ParseJsonUtil.PC));				
				list.setPn(content.optInt(ParseJsonUtil.PN));

				List<Command> commands = resolveCommand(content);
				list.setCommands(commands);

				JSONArray jsonArray = content.optJSONArray(ParseJsonUtil.ACTION_RESULT);
				List<VodBrief> movies = new ArrayList<VodBrief>();

				if (null != jsonArray)
				{
					for (int i = 0; i < jsonArray.length(); i++)
					{
						JSONObject items = jsonArray.optJSONObject(i);
						VodBrief vod = new VodBrief();
						vod.setId(items.optString("id"));
						vod.setTitle(items.optString("title"));
						vod.setImg(items.optString("img"));
						vod.setDomain(items.optString("domain"));
						vod.setCmd(items.optString("cmd"));
						vod.setSubTitle(items.optString("subTitle"));
						
						JSONArray arr = items.optJSONArray(ParseJsonUtil.PARS);
						HashMap<String, String> pars = null;
						if (null != arr)
						{
							pars = new HashMap<String, String>();
							for (int j = 0; j < arr.length(); j++)
							{
								JSONObject par = arr.optJSONObject(j);					
								pars.put(par.optString("key"), par.optString("value"));						
							}
						}
						vod.setPars(pars);

						movies.add(vod);
					}
				}

				list.setVods(movies);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}


}
