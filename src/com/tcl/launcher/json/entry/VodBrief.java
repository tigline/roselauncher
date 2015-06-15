package com.tcl.launcher.json.entry;

import java.io.Serializable;
import java.util.HashMap;

public class VodBrief implements Serializable
{
	//电影（电视剧）简要信息：id, title, img, domain, cmd pars(key+value), subTitle
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String title;
	
	private String img;
	
	private String domain;
	
	private String cmd;
	
	private String subTitle;
	
	private HashMap<String, String> pars;
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}
	
	public String getDomain()
	{
		return domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}
	
	public String getCmd()
	{
		return cmd;
	}

	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}
	
	public String getSubTitle()
	{
		return subTitle;
	}

	public void setSubTitle(String subTitle)
	{
		this.subTitle = subTitle;
	}
	
	public HashMap<String, String> getPars()
	{
		return pars;
	}

	public void setPars(HashMap<String, String> pars)
	{
		this.pars = pars;
	}
}
