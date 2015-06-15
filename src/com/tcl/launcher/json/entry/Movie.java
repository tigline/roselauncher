package com.tcl.launcher.json.entry;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable
{

	private int sqlId;

	private String current_position;

	/**
	 *  电影详细信息类
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.movieId Remark - *NO SETTING* DBType - INTEGER
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String movieid;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.cn_Title Remark - 中文名称 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String cnTitle;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.en_Title Remark - 英文名称 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String enTitle;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.directors Remark - 导演 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String directors;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.screenwriter Remark - 编剧 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String screenwriter;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.actors Remark - 演员 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String actors;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.types Remark - 类型 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String types;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.officiaWebsite Remark - 官网 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String officiawebsite;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.producerCountries Remark - 国家 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String producercountries;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.language Remark - 语言 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String language;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.releaseDate Remark - 发行日期 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String releasedate;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.duration Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String duration;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.renames Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String renames;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.dateOfFirstRelease Remark - 首映日期 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String dateoffirstrelease;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.officialStation Remark - *NO SETTING* DBType -
	 * VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String officialstation;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.intro Remark - 简述 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String intro;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.picture Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String picture;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.tags Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String tags;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.awardWinning Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String awardwinning;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.alsoLike Remark - 同类 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String alsolike;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.thumbnail Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String thumbnail;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.pageUrl Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String pageurl;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.canplay Remark - 是否能看1能看，0不能看 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String canplay;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.cn_sub_title Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String cnSubTitle;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.rating Remark - 评分 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String rating;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.hot Remark - 热度 DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String hot;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.en_sub_title Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String enSubTitle;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.imgh_url Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String imghUrl;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.imgv_url Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String imgvUrl;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.s_intro Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String sIntro;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.source_movie_id Remark - 百度id DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String sourceMovieId;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.areas Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String areas;
	/**
	 * This field was generated by THB Generator. This field corresponds to the
	 * database column movie.sites Remark - *NO SETTING* DBType - VARCHAR
	 * 
	 * @mbggenerated Fri Mar 07 14:19:52 CST 2014
	 */
	private String sites;

	private List<Site> videoSource;

	private List<VodBrief> recommends;

	public String getCurrent_position()
	{
		return current_position;
	}

	public void setCurrent_position(String current_position)
	{
		this.current_position = current_position;
	}

	public int getSqlId()
	{
		return sqlId;
	}

	public void setSqlId(int sqlId)
	{
		this.sqlId = sqlId;
	}

	public List<VodBrief> getRecommends()
	{
		return recommends;
	}

	public void setRecommends(List<VodBrief> recommends)
	{
		this.recommends = recommends;
	}

	public Movie()
	{
		super();
	}

	public String getMovieid()
	{
		return movieid;
	}

	public void setMovieid(String movieid)
	{
		this.movieid = movieid;
	}

	public String getCnTitle()
	{
		return cnTitle;
	}

	public void setCnTitle(String cnTitle)
	{
		this.cnTitle = cnTitle == null ? null : cnTitle.trim();
	}

	public String getEnTitle()
	{
		return enTitle;
	}

	public void setEnTitle(String enTitle)
	{
		this.enTitle = enTitle == null ? null : enTitle.trim();
	}

	public String getDirectors()
	{
		return directors;
	}

	public void setDirectors(String directors)
	{
		this.directors = directors == null ? null : directors.trim();
	}

	public String getScreenwriter()
	{
		return screenwriter;
	}

	public void setScreenwriter(String screenwriter)
	{
		this.screenwriter = screenwriter == null ? null : screenwriter.trim();
	}

	public String getActors()
	{
		return actors;
	}

	public void setActors(String actors)
	{
		this.actors = actors == null ? null : actors.trim();
	}

	public String getTypes()
	{
		return types;
	}

	public void setTypes(String types)
	{
		this.types = types == null ? null : types.trim();
	}

	public String getOfficiawebsite()
	{
		return officiawebsite;
	}

	public void setOfficiawebsite(String officiawebsite)
	{
		this.officiawebsite = officiawebsite == null ? null : officiawebsite.trim();
	}

	public String getProducercountries()
	{
		return producercountries;
	}

	public void setProducercountries(String producercountries)
	{
		this.producercountries = producercountries == null ? null : producercountries.trim();
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language == null ? null : language.trim();
	}

	public String getReleasedate()
	{
		return releasedate;
	}

	public void setReleasedate(String releasedate)
	{
		this.releasedate = releasedate == null ? null : releasedate.trim();
	}

	public String getDuration()
	{
		return duration;
	}

	public void setDuration(String duration)
	{
		this.duration = duration == null ? null : duration.trim();
	}

	public String getRenames()
	{
		return renames;
	}

	public void setRenames(String renames)
	{
		this.renames = renames == null ? null : renames.trim();
	}

	public String getDateoffirstrelease()
	{
		return dateoffirstrelease;
	}

	public void setDateoffirstrelease(String dateoffirstrelease)
	{
		this.dateoffirstrelease = dateoffirstrelease == null ? null : dateoffirstrelease.trim();
	}

	public String getOfficialstation()
	{
		return officialstation;
	}

	public void setOfficialstation(String officialstation)
	{
		this.officialstation = officialstation == null ? null : officialstation.trim();
	}

	public String getIntro()
	{
		return intro;
	}

	public void setIntro(String intro)
	{
		this.intro = intro == null ? null : intro.trim();
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture(String picture)
	{
		this.picture = picture == null ? null : picture.trim();
	}

	public String getTags()
	{
		return tags;
	}

	public void setTags(String tags)
	{
		this.tags = tags == null ? null : tags.trim();
	}

	public String getAwardwinning()
	{
		return awardwinning;
	}

	public void setAwardwinning(String awardwinning)
	{
		this.awardwinning = awardwinning == null ? null : awardwinning.trim();
	}

	public String getAlsolike()
	{
		return alsolike;
	}

	public void setAlsolike(String alsolike)
	{
		this.alsolike = alsolike == null ? null : alsolike.trim();
	}

	public String getThumbnail()
	{
		return thumbnail;
	}

	public void setThumbnail(String thumbnail)
	{
		this.thumbnail = thumbnail == null ? null : thumbnail.trim();
	}

	public String getPageurl()
	{
		return pageurl;
	}

	public void setPageurl(String pageurl)
	{
		this.pageurl = pageurl == null ? null : pageurl.trim();
	}

	public String getCanplay()
	{
		return canplay;
	}

	public void setCanplay(String canplay)
	{
		this.canplay = canplay == null ? null : canplay.trim();
	}

	public String getCnSubTitle()
	{
		return cnSubTitle;
	}

	public void setCnSubTitle(String cnSubTitle)
	{
		this.cnSubTitle = cnSubTitle == null ? null : cnSubTitle.trim();
	}

	public String getRating()
	{
		return rating;
	}

	public void setRating(String rating)
	{
		this.rating = rating == null ? null : rating.trim();
	}

	public String getHot()
	{
		return hot;
	}

	public void setHot(String hot)
	{
		this.hot = hot == null ? null : hot.trim();
	}

	public String getEnSubTitle()
	{
		return enSubTitle;
	}

	public void setEnSubTitle(String enSubTitle)
	{
		this.enSubTitle = enSubTitle == null ? null : enSubTitle.trim();
	}

	public String getImghUrl()
	{
		return imghUrl;
	}

	public void setImghUrl(String imghUrl)
	{
		this.imghUrl = imghUrl == null ? null : imghUrl.trim();
	}

	public String getImgvUrl()
	{
		return imgvUrl;
	}

	public void setImgvUrl(String imgvUrl)
	{
		this.imgvUrl = imgvUrl == null ? null : imgvUrl.trim();
	}

	public String getsIntro()
	{
		return sIntro;
	}

	public void setsIntro(String sIntro)
	{
		this.sIntro = sIntro == null ? null : sIntro.trim();
	}

	public String getSourceMovieId()
	{
		return sourceMovieId;
	}

	public void setSourceMovieId(String sourceMovieId)
	{
		this.sourceMovieId = sourceMovieId == null ? null : sourceMovieId.trim();
	}

	public String getAreas()
	{
		return areas;
	}

	public void setAreas(String areas)
	{
		this.areas = areas == null ? null : areas.trim();
	}

	public String getSites()
	{
		return sites;
	}

	public void setSites(String sites)
	{
		this.sites = sites == null ? null : sites.trim();
	}

	public List<Site> getVideoSource()
	{
		return videoSource;
	}

	public void setVideoSource(List<Site> videoSource)
	{
		this.videoSource = videoSource;
	}

}