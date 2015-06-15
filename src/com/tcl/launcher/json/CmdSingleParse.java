package com.tcl.launcher.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcl.launcher.json.entry.CmdSingle;
import com.tcl.launcher.json.entry.Command;
import com.tcl.launcher.json.entry.Episode;
import com.tcl.launcher.json.entry.Movie;
import com.tcl.launcher.json.entry.Site;
import com.tcl.launcher.json.entry.TvPlay;
import com.tcl.launcher.json.entry.VodBrief;
import com.tcl.launcher.util.Utils;

/**
 * 详情命令解析
 * 
 * @author
 * 
 */
public class CmdSingleParse extends ParseJsonBase {

	private static CmdSingleParse parse = null;

	private CmdSingleParse() {
	}

	public static CmdSingleParse getInstance() {
		if (null == parse) {
			parse = new CmdSingleParse();
		}

		return parse;
	}

	public Object parseCmdSingle(JSONObject json) {

		CmdSingle single = new CmdSingle();

		try {
			single.setRc(json.optString(ParseJsonUtil.RC));
			single.setQuestion(json.optString(ParseJsonUtil.QUESTION));
			single.setCommand(json.optString(ParseJsonUtil.ACTION_COMMAND));
			single.setTips(json.optString(ParseJsonUtil.TIPS));

			JSONObject error = json.optJSONObject(ParseJsonUtil.ERROR);
			if (null != error) {
				String errorArr = error.optString("error");
				single.setError(errorArr);
			}

			JSONObject content = json.optJSONObject(ParseJsonUtil.ACTION_DATA);

			if (null != content) {
				single.setDomain(content.optString(ParseJsonUtil.DOMAIN));

				List<Command> commands = resolveCommand(content);
				single.setCommands(commands);

				JSONObject result = content.optJSONObject(ParseJsonUtil.ACTION_RESULT);

				// 根据类型区分（电影、电视剧。。。）
				if (single.getCommand().equalsIgnoreCase("MOVIE_SHOW_SINGLE")
						|| single.getCommand().equalsIgnoreCase("MOVIE_PLAY")) {
					Movie movie = parseVodMovieValues(result);
					single.setMovie(movie);
				} else if (single.getCommand().equalsIgnoreCase("TVPLAY_SHOW_SINGLE")
						|| single.getCommand().equalsIgnoreCase("TVPLAY_PLAY")) {
					TvPlay tvPlay = parseVodTvValues(result);
					single.setTvPlay(tvPlay);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return single;
	}

	public Movie parseVodMovieValues(JSONObject result) throws JSONException {
		Movie movie = null;
		try {
			movie = parseMovie(result);

			movie.setVideoSource(parseVodMovieSource(result));
			movie.setRecommends(parseRecommends(result));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return movie;
	}

	private Movie parseMovie(JSONObject result) {
		Movie movie = new Movie();

		movie.setMovieid(result.optString("movieid"));
		movie.setCnTitle(result.optString("cnTitle"));
		movie.setEnTitle(result.optString("enTitle"));
		movie.setDirectors(result.optString("directors"));
		movie.setScreenwriter(result.optString("screenwriter"));
		movie.setActors(result.optString("actors"));
		movie.setTypes(result.optString("types"));
		movie.setOfficiawebsite(result.optString("officiawebsite"));
		movie.setProducercountries(result.optString("producercountries"));
		movie.setLanguage(result.optString("language"));
		movie.setReleasedate(result.optString("releasedate"));
		String duration = result.optString("duration");

		if (Utils.isNotEmpty(duration)) {
			movie.setDuration((Math.round(Integer.parseInt(duration) / 60)) + ""); // 单位s转换为min
		}

		movie.setRenames(result.optString("renames"));
		movie.setDateoffirstrelease(result.optString("dateoffirstrelease"));
		movie.setOfficialstation(result.optString("officialstation"));
		movie.setIntro(result.optString("intro"));
		movie.setPicture(result.optString("picture"));
		movie.setTags(result.optString("tags"));
		movie.setAwardwinning(result.optString("awardwinning"));
		movie.setAlsolike(result.optString("alsolike"));
		movie.setThumbnail(result.optString("thumbnail"));
		movie.setPageurl(result.optString("pageurl"));
		movie.setCanplay(result.optString("canplay"));
		movie.setCnSubTitle(result.optString("cnSubTitle"));
		movie.setRating(result.optString("rating"));
		movie.setHot(result.optString("hot"));
		movie.setEnSubTitle(result.optString("enSubTitle"));
		movie.setImghUrl(result.optString("imghUrl"));
		movie.setImgvUrl(result.optString("imgvUrl"));
		movie.setsIntro(result.optString("sIntro"));
		movie.setSourceMovieId(result.optString("sourceMovieId"));
		movie.setAreas(result.optString("areas"));
		movie.setSites(result.optString("sites"));

		return movie;
	}

	/**
	 * 播放源解析
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	private List<Site> parseVodMovieSource(JSONObject jsonObject) throws JSONException {
		List<Site> list = new ArrayList<Site>();
		JSONArray vedioSources = null;
		try {
			Object videoObject = jsonObject.opt("videoSource");

			if (videoObject != null && videoObject instanceof JSONArray) {

				vedioSources = (JSONArray) videoObject;

			}

			if (null != vedioSources && vedioSources.length() > 0) {
				for (int i = 0; i < vedioSources.length(); i++) {
					JSONObject vedioSource = vedioSources.optJSONObject(i);

					Site vs = new Site();

					vs.setTitle(vedioSource.optString("title"));
					vs.setUrl(vedioSource.optString("url"));
					vs.setSiteUrl(vedioSource.optString("siteUrl"));
					vs.setSourceMovieId(vedioSource.optString("sourceMovieId"));
					String type = vedioSource.optString("type");
					vs.setSiteName(vedioSource.optString("siteName"));
					vs.setSiteLogo(vedioSource.optString("siteLogo"));
					vs.setMovieId(vedioSource.optString("movieId"));
					String play_url = vedioSource.optString("playUrl");
					vs.setType(type);
					vs.setPlayUrl(play_url);
					list.add(vs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 推荐解析
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	public List<VodBrief> parseRecommends(JSONObject jsonObject) throws JSONException {

		List<VodBrief> list = null;

		JSONArray recommends = null;
		try {
			Object recommendsObject = jsonObject.opt("recommends");

			if (recommendsObject != null && recommendsObject instanceof JSONArray) {
				recommends = (JSONArray) recommendsObject;
			}

			if (null != recommends && recommends.length() > 0) {
				list = new ArrayList<VodBrief>();

				for (int i = 0; i < recommends.length(); i++) {
					JSONObject item = recommends.optJSONObject(i);

					VodBrief vod = new VodBrief();
					vod.setId(item.optString("id"));
					vod.setTitle(item.optString("title"));
					vod.setImg(item.optString("img"));
					vod.setDomain(item.optString("domain"));
					vod.setCmd(item.optString("cmd"));
					vod.setSubTitle(item.optString("subTitle"));

					JSONArray arr = item.optJSONArray(ParseJsonUtil.PARS);
					HashMap<String, String> pars = null;
					if (null != arr) {
						pars = new HashMap<String, String>();
						for (int j = 0; j < arr.length(); j++) {
							JSONObject par = arr.optJSONObject(j);
							pars.put(par.optString("key"), par.optString("value"));
						}
					}
					vod.setPars(pars);

					list.add(vod);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public TvPlay parseVodTvValues(JSONObject result) throws JSONException {
		TvPlay tvplay = null;
		try {
			if (result != null) {

				tvplay = parseTvplayBaseInfo(result);

				tvplay.setRecommends(parseRecommends(result));
				tvplay.setEpisodes(parseEpisodeInfo(result));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tvplay;
	}

	private TvPlay parseTvplayBaseInfo(JSONObject object) {
		TvPlay tvplay;
		tvplay = new TvPlay();
		tvplay.setTvplayId(object.optString("tvplayId"));
		tvplay.setCnTitle(object.optString("cnTitle"));
		tvplay.setEnTitle(object.optString("enTitle"));
		tvplay.setDirectors(object.optString("directors"));
		tvplay.setScreenwriter(object.optString("screenwriter"));
		tvplay.setActors(object.optString("actors"));
		tvplay.setTypes(object.optString("types"));
		tvplay.setSeason(object.optString("season"));
		tvplay.setSeasonNum(object.optString("seasonNum"));
		tvplay.setSeasonType(object.optString("seasonType"));
		tvplay.setTags(object.optString("tags"));
		tvplay.setLanguage(object.optString("language"));
		tvplay.setReleasedate(object.optString("releasedate"));
		tvplay.setRenames(object.optString("renames"));
		tvplay.setIntro(object.optString("intro"));
		tvplay.setUrl(object.optString("url"));
		tvplay.setCnSubTitle(object.optString("cnSubTitle"));
		tvplay.setRating(object.optString("rating"));
		tvplay.setHot(object.optString("hot"));
		tvplay.setEnSubTitle(object.optString("enSubTitle"));
		tvplay.setImghUrl(object.optString("imghUrl"));
		tvplay.setImgvUrl(object.optString("imgvUrl"));
		tvplay.setsIntro(object.optString("sIntro"));
		tvplay.setSourceTvplayId(object.optString("sourceTvplayId"));
		tvplay.setFinish(object.optString("finish"));
		tvplay.setUpdateStatus(object.optString("updateStatus"));
		tvplay.setAreas(object.optString("areas"));
		tvplay.setMaxEpisode(object.optString("maxEpisode"));
		tvplay.setCurEpisode(object.optString("curEpisode"));
		tvplay.setSites(object.optString("sites"));
		return tvplay;
	}

	private List<Episode> parseEpisodeInfo(JSONObject object) {
		List<Episode> list = new ArrayList<Episode>();
		JSONArray episodes = null;
		try {
			Object episode_object = object.opt("episodes");

			if (episode_object != null && episode_object instanceof JSONArray) {
				episodes = (JSONArray) episode_object;
			}

			if (null != episodes && episodes.length() > 0) {
				for (int i = 0; i < episodes.length(); i++) {
					JSONObject episode = episodes.optJSONObject(i);

					Episode tvplayEpisode = new Episode();
					tvplayEpisode.setSingleTitle(episode.optString("singleTitle"));
					tvplayEpisode.setUrl(episode.optString("url"));
					tvplayEpisode.setEpisode(episode.optString("episode"));
					tvplayEpisode.setThumbnail(episode.optString("thumbnail"));
					tvplayEpisode.setPlayUrl(episode.optString("playUrl"));
					tvplayEpisode.setType(episode.optString("type"));

					Object site_object = episode.opt("sites");

					if (site_object != null && site_object instanceof JSONArray) {

						List<Site> sites = new ArrayList<Site>();
						JSONArray site_array = (JSONArray) site_object;

						for (int j = 0; j < site_array.length(); j++) {
							JSONObject imte = site_array.optJSONObject(j);

							Site site = new Site();

							site.setSiteOrder(imte.optString("siteOrder"));
							site.setSiteUrl(imte.optString("siteUrl"));
							site.setSiteLogo(imte.optString("siteLogo"));
							site.setSiteName(imte.optString("siteName"));
							site.setUrl(imte.optString("url"));
							site.setPlayUrl(imte.optString("playUrl"));
							site.setType(imte.optString("type"));

							sites.add(site);
						}

						tvplayEpisode.setSites(sites);

					}
					list.add(tvplayEpisode);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
