package com.tcl.launcher.json.entry;

import java.io.Serializable;

public class Site implements Serializable
{
	//videoSource结构：title, url, siteUrl, sourceMovieId, type, siteName, siteLogo, movieId, playUrl
	//episodes下的sites结构：siteOrder, siteUrl, siteLogo, siteName, url, playUrl, type
	
	private static final long serialVersionUID = -5673291432748458685L;

	private String title;
	
	private String url;
	
	private String siteUrl;
	
	private String sourceMovieId;
	
	private String type;

	private String siteName;

	private String siteLogo;
	
	private String movieId;

	private String playUrl;
	
	private String siteOrder;

	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getSiteOrder()
	{
		return siteOrder;
	}

	public void setSiteOrder(String siteOrder)
	{
		this.siteOrder = siteOrder;
	}

	public String getSiteUrl()
	{
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl)
	{
		this.siteUrl = siteUrl;
	}

	public String getSiteLogo()
	{
		return siteLogo;
	}

	public void setSiteLogo(String siteLogo)
	{
		this.siteLogo = siteLogo;
	}

	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public String getPlayUrl()
	{
		return playUrl;
	}

	public void setPlayUrl(String playUrl)
	{
		this.playUrl = playUrl;
	}
	
	public String getSourceMovieId()
	{
		return sourceMovieId;
	}

	public void setSourceMovieId(String sourceMovieId)
	{
		this.sourceMovieId = sourceMovieId;
	}

	public String getMovieId()
	{
		return movieId;
	}

	public void setMovieId(String movieId)
	{
		this.movieId = movieId;
	}
	
}
