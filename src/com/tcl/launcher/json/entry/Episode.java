package com.tcl.launcher.json.entry;

import java.io.Serializable;
import java.util.List;

import android.annotation.SuppressLint;

@SuppressLint("UseValueOf")
public class Episode implements Serializable, Cloneable, Comparable<Episode>
{
	private static final long serialVersionUID = 1L;
	
	//episodes结构：singleTitle, url, episode, thumbnail, playUrl, type, sites
	
	// private Integer episodesId;
	private String singleTitle;
	private String url;
	private String episode;
	private String thumbnail;
	private String playUrl;
	private String type;

	private List<Site> sites;
	

	public List<Site> getSites()
	{
		return sites;
	}

	public void setSites(List<Site> sites)
	{
		this.sites = sites;
	}

	public Episode()
	{
		super();
	}

	//
	// public Integer getEpisodesId()
	// {
	// return episodesId;
	// }
	//
	// public void setEpisodesId(Integer episodesId)
	// {
	// this.episodesId = episodesId;
	// }
	//
	public String getSingleTitle()
	{
		return singleTitle;
	}
	
	public void setSingleTitle(String singleTitle)
	{
		this.singleTitle = singleTitle == null ? null : singleTitle.trim();
	}

	public String getEpisode()
	{
		return episode;
	}

	public void setEpisode(String episode)
	{
		this.episode = episode == null ? null : episode.trim();
	}

	// public Integer getCartoonId()
	// {
	// return cartoonId;
	// }
	//
	// public void setCartoonId(Integer cartoonId)
	// {
	// this.cartoonId = cartoonId;
	// }
	//
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url == null ? null : url.trim();
	}
	
	public String getPlayUrl()
	{
		return playUrl;
	}
	
	public void setPlayUrl(String playUrl)
	{
		this.playUrl = playUrl == null ? null : playUrl.trim();
	}
	//
	// public String getIsPlay()
	// {
	// return isPlay;
	// }
	//
	// public void setIsPlay(String isPlay)
	// {
	// this.isPlay = isPlay == null ? null : isPlay.trim();
	// }
	//
	// public String getSiteOrder()
	// {
	// return siteOrder;
	// }
	//
	// public void setSiteOrder(String siteOrder)
	// {
	// this.siteOrder = siteOrder == null ? null : siteOrder.trim();
	// }
	//
	// public String getSiteUrl()
	// {
	// return siteUrl;
	// }
	//
	// public void setSiteUrl(String siteUrl)
	// {
	// this.siteUrl = siteUrl == null ? null : siteUrl.trim();
	// }
	//
	public String getThumbnail()
	{
		return thumbnail;
	}

	public void setThumbnail(String thumbnail)
	{
		this.thumbnail = thumbnail == null ? null : thumbnail.trim();
	}

	// public String getSourceId()
	// {
	// return sourceId;
	// }
	//
	// public void setSourceId(String sourceId)
	// {
	// this.sourceId = sourceId == null ? null : sourceId.trim();
	// }
	//
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type == null ? null : type.trim();
	}

	//
	// public String getSiteLogo()
	// {
	// return siteLogo;
	// }
	//
	// public void setSiteLogo(String siteLogo)
	// {
	// this.siteLogo = siteLogo == null ? null : siteLogo.trim();
	// }
	//
	// public String getSiteName()
	// {
	// return siteName;
	// }
	//
	// public void setSiteName(String siteName)
	// {
	// this.siteName = siteName == null ? null : siteName.trim();
	// }

	public Episode clone()
	{
		Episode o = null;
		try
		{
			o = (Episode) super.clone();
		}
		catch (CloneNotSupportedException ex)
		{
			ex.printStackTrace();
		}
		return o;
	}

	@Override
	public int compareTo(Episode o)
	{
		return new Integer(getEpisode()).compareTo(new Integer(o.getEpisode()));
	}

}