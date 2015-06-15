package com.tcl.launcher.interaction.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.tcl.launcher.entry.RoseResultVO;
import com.tcl.launcher.interaction.IDynamicCommandIntercept;
import com.tcl.launcher.json.entry.Command;
import com.tcl.launcher.util.TLog;

/**
 * 动态指令拦截
 * 
 * @author Administrator
 * 
 */
public class IDynamicCommandInterceptImpl implements IDynamicCommandIntercept {

	public static String HOME_PAGE = "HOME_PAGE";
	public static String HOME_CLOUD_CAMERA_PAGE = "HOME_CLOUD_CAMERA_PAGE";
	public static String HOME_CLOUD_PHOTO_SEARCHE_PAGE = "HOME_CLOUD_PHOTO_SEARCHE_PAGE";
	public static String HOME_CLOUD_NOTICE_PAGE = "HOME_CLOUD_NOTICE_PAGE";
	public static String MOVIE_PLAY_PAGE = "MOVIE_PLAY_PAGE";
	public static String MOVIE_SHOW_LIST_PAGE = "MOVIE_SHOW_LIST_PAGE";
	public static String MOVIE_SHOW_SINGLE_PAGE = "MOVIE_SHOW_SINGLE_PAGE";
	public static String TVPLAY_SHOW_LIST_PAGE = "TVPLAY_SHOW_LIST_PAGE";
	public static String TVPLAY_SHOW_SINGLE_PAGE = "TVPLAY_SHOW_SINGLE_PAGE";
	public static String TVPLAY_PLAY_PAGE = "TVPLAY_PLAY_PAGE";

	final public static String match = "match";
	final public static String match_regex = "match_regex";
	public final static String pinyin_match = "pinyin_match";
	final public static String pinyin_match_regex = "pinyin_match_regex";

	public static String COMMAND_MOVIE_SHOW_LIST = "MOVIE_SHOW_LIST";
	public static String COMMAND_MOVIE_SHOW_SINGLE = "MOVIE_SHOW_SINGLE";
	public static String COMMAND_MOVIE_PLAY = "MOVIE_PLAY";
	public static String COMMAND_MOVIE_PLAY_CURRENT = "MOVIE_PLAY_CURRENT";
	public static String COMMAND_TVPLAY_SHOW_LIST = "TVPLAY_SHOW_LIST";
	public static String COMMAND_TVPLAY_SHOW_SINGLE = "TVPLAY_SHOW_SINGLE";
	public static String COMMAND_TVPLAY_PLAY = "TVPLAY_PLAY";
	public static String COMMAND_TVPLAY_PLAY_CURRENT = "TVPLAY_PLAY_CURRENT";

	public static String COMMAND_TVPLAY_PLAY_EPISODE = "TVPLAY_PLAY_EPISODE";
	public static String COMMAND_TVPLAY_PLAY_NEXT_EPISODE = "TVPLAY_PLAY_NEXT_EPISODE";
	public static String COMMAND_TVPLAY_PLAY_LAST_EPISODE = "TVPLAY_PLAY_LAST_EPISODE";
	public static String COMMAND_VARIETY_SHOW_LIST = "VARIETY_SHOW_LIST";
	public static String COMMAND_VARIETY_SHOW_SINGLE = "VARIETY_SHOW_SINGLE";
	public static String COMMAND_VARIETY_PLAY = "VARIETY_PLAY";
	public static String COMMAND_CARTOON_SHOW_LIST = "CARTOON_SHOW_LIST";
	public static String COMMAND_CARTOON_SHOW_SINGLE = "CARTOON_SHOW_SINGLE";
	public static String COMMAND_CARTOON_PLAY = "CARTOON_PLAY";
	public static String COMMAND_MUSIC_SHOW_LIST = "MUSIC_SHOW_LIST";
	public static String COMMAND_MUSIC_SHOW_SINGLE = "MUSIC_SHOW_SINGLE";
	public static String COMMAND_MUSIC_PLAY = "MUSIC_PLAY";
	public static String COMMAND_MUSIC_LIST_PLAY = "MUSIC_LIST_PLAY";

	public static String COMMAND_PLAY = "PLAY";
	public static String COMMAND_PAUSE = "PAUSE";
	public static String COMMAND_SPEED = "SPEED";
	public static String COMMAND_BACKWARKS = "BACKWARKS";

	public static String NEXT_PAGE = "NEXT_PAGE";
	public static String PREVIOUS_PAGE = "PREVIOUS_PAGE";

	public static String LAST_EPISODE = "LAST_EPISODE";
	public static String NEXT_EPISODE = "NEXT_EPISODE";

	public static Map<String, List<Command>> staticCommand = new HashMap<String, List<Command>>();

	public IDynamicCommandInterceptImpl() {

		initPageCommand();
	}

	private static void initPageCommand() {
		List<Command> tvplay_play_page = new ArrayList<Command>();
		Command tvplay_play_page_play = new Command();
		tvplay_play_page_play.setCommand(COMMAND_PLAY);
		tvplay_play_page_play.setStatement("播放");
		tvplay_play_page_play.setMatchtype(match);
		tvplay_play_page.add(tvplay_play_page_play);

		Command tvplay_play_page_pause = new Command();
		tvplay_play_page_pause.setCommand(COMMAND_PAUSE);
		tvplay_play_page_pause.setStatement("(展厅|站厅|暂停)");
		tvplay_play_page_pause.setMatchtype(match_regex);
		tvplay_play_page.add(tvplay_play_page_pause);

		Command tvplay_play_page_speed = new Command();
		tvplay_play_page_speed.setCommand(COMMAND_SPEED);
		tvplay_play_page_speed.setStatement("(快进|前进)");
		tvplay_play_page_speed.setMatchtype(match_regex);
		tvplay_play_page.add(tvplay_play_page_speed);

		Command tvplay_play_page_backwarks = new Command();
		tvplay_play_page_backwarks.setCommand(COMMAND_BACKWARKS);
		tvplay_play_page_backwarks.setStatement("(快退|后退)");
		tvplay_play_page_backwarks.setMatchtype(match_regex);
		tvplay_play_page.add(tvplay_play_page_backwarks);

		Command tvplay_play_page_last_episode = new Command();
		tvplay_play_page_last_episode.setCommand(LAST_EPISODE);
		tvplay_play_page_last_episode.setStatement("(上一集|上一基|上一击|上一及|上一级|上一节)");
		tvplay_play_page_last_episode.setMatchtype(match_regex);
		tvplay_play_page.add(tvplay_play_page_last_episode);
		Command tvplay_play_page_next_episode = new Command();
		tvplay_play_page_next_episode.setCommand(NEXT_EPISODE);
		tvplay_play_page_next_episode.setStatement("(下一集|下一基|下一击|下一及|下一级|下一节)");
		tvplay_play_page_next_episode.setMatchtype(match_regex);
		tvplay_play_page.add(tvplay_play_page_next_episode);

		staticCommand.put(TVPLAY_PLAY_PAGE, tvplay_play_page);

		List<Command> tvplay_show_single_page = new ArrayList<Command>();
		Command tvplay_show_single_page_play = new Command();
		tvplay_show_single_page_play.setCommand(COMMAND_TVPLAY_PLAY_CURRENT);
		tvplay_show_single_page_play.setStatement("播放");
		tvplay_show_single_page_play.setMatchtype(match);
		tvplay_show_single_page.add(tvplay_show_single_page_play);
		staticCommand.put(TVPLAY_SHOW_SINGLE_PAGE, tvplay_show_single_page);

		List<Command> movie_show_single_page = new ArrayList<Command>();
		Command movie_show_single_page_play = new Command();
		movie_show_single_page_play.setCommand(COMMAND_MOVIE_PLAY_CURRENT);
		movie_show_single_page_play.setStatement("播放");
		movie_show_single_page_play.setMatchtype(match);
		movie_show_single_page.add(movie_show_single_page_play);
		staticCommand.put(MOVIE_SHOW_SINGLE_PAGE, movie_show_single_page);

		List<Command> movie_play_page = new ArrayList<Command>();
		Command movie_play_page_play = new Command();
		movie_play_page_play.setCommand(COMMAND_PLAY);
		movie_play_page_play.setStatement("播放");
		movie_play_page_play.setMatchtype(match);
		movie_play_page.add(movie_play_page_play);

		Command movie_play_page_pause = new Command();
		movie_play_page_pause.setCommand(COMMAND_PAUSE);
		movie_play_page_pause.setStatement("(展厅|站厅|暂停)");
		movie_play_page_pause.setMatchtype(match_regex);
		movie_play_page.add(movie_play_page_pause);

		Command movie_play_page_speed = new Command();
		movie_play_page_speed.setCommand(COMMAND_SPEED);
		movie_play_page_speed.setStatement("(快进|前进)");
		movie_play_page_speed.setMatchtype(match_regex);
		movie_play_page.add(movie_play_page_speed);

		Command movie_play_page_backwarks = new Command();
		movie_play_page_backwarks.setCommand(COMMAND_BACKWARKS);
		movie_play_page_backwarks.setStatement("(快退|后退)");
		movie_play_page_backwarks.setMatchtype(match_regex);
		movie_play_page.add(movie_play_page_backwarks);

		staticCommand.put(MOVIE_PLAY_PAGE, movie_play_page);

		List<Command> general_page = new ArrayList<Command>();

		Command general_shutdown = new Command();
		general_shutdown.setCommand("TV_SHUTDOWN");
		general_shutdown.setStatement("关机");
		general_shutdown.setMatchtype(match);
		general_page.add(general_shutdown);

		Command general_start_up = new Command();
		general_start_up.setCommand("TV_START_UP");
		general_start_up.setStatement("开机");
		general_start_up.setMatchtype(match);
		general_page.add(general_start_up);

		Command general_up = new Command();
		general_up.setCommand("TV_GO_UP");
		general_up.setStatement("向上");
		general_up.setMatchtype(match);
		general_page.add(general_up);

		Command general_down = new Command();
		general_down.setCommand("TV_GO_DOWN");
		general_down.setStatement("向下");
		general_down.setMatchtype(match);
		general_page.add(general_down);

		Command general_right = new Command();
		general_right.setCommand("TV_GO_RIGHT");
		general_right.setStatement("向右");
		general_right.setMatchtype(match);
		general_page.add(general_right);

		Command general_left = new Command();
		general_left.setCommand("TV_GO_LEFT");
		general_left.setStatement("向左");
		general_left.setMatchtype(match);
		general_page.add(general_left);

		Command general_home = new Command();
		general_home.setCommand("TV_GO_HOME");
		general_home.setStatement("(回首页|首页|回到首页)");
		general_home.setMatchtype(match_regex);
		general_page.add(general_home);

		Command general_back = new Command();
		general_back.setCommand("TV_GO_BACK");
		general_back.setStatement("(返回|后退)");
		general_back.setMatchtype(match_regex);
		general_page.add(general_back);

		staticCommand.put("GENERAL_PAGE", general_page);
	}

	/**
	 * 本地指令过滤处理
	 */
	@Override
	public String interceptLocalCommand(String page_id, String domian, String question) {
		List<Command> commands = staticCommand.get(page_id);
		Command command = null;

		if (null != commands && commands.size() > 0) {
			command = getDyamicCommand(question, commands);
		}
		if (commands == null) {

			commands = staticCommand.get("GENERAL_PAGE");

			if (null != commands && commands.size() > 0) {
				command = getDyamicCommand(question, commands);
			}
		}

		if (command != null) {
			TLog.i("dynamicCommand : ", "command :" + command + "pageid :" + page_id);
			if (TVPLAY_SHOW_SINGLE_PAGE.equalsIgnoreCase(page_id)) {
				return resultVisualizere(question, command.getCommand(), "TVPLAY");
			} else if (TVPLAY_PLAY_PAGE.equalsIgnoreCase(page_id)) {
				return resultVisualizere(question, command.getCommand(), "TVPLAY");
			} else if (MOVIE_PLAY_PAGE.equalsIgnoreCase(page_id)) {
				return resultVisualizere(question, command.getCommand(), "MOVIE");
			} else if (MOVIE_SHOW_SINGLE_PAGE.equalsIgnoreCase(page_id)) {
				return resultVisualizere(question, command.getCommand(), "MOVIE");
			}
			return resultVisualizere(question, command.getCommand(), "TV");
		}

		return null;
	}

	public String resultVisualizere(String question, String command, String domian) {
		RoseResultVO roseResultVO = new RoseResultVO();
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("domian", domian);
		roseResultVO.setRc("0");
		roseResultVO.setData(results);
		roseResultVO.setCommand(command);
		roseResultVO.setQuestion(question);

		StringWriter sw = new StringWriter();

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(sw, roseResultVO);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result_json = sw.toString();
		return result_json;
	}

	private Command getDyamicCommand(String question, List<Command> cdcs) {

		for (Command dc : cdcs) {

			if (dc.getMatchtype() == null || match.equalsIgnoreCase(dc.getMatchtype())) {
				if (question.equalsIgnoreCase(dc.getStatement())) {
					return dc;
				}
			} else if (match_regex.equalsIgnoreCase(dc.getMatchtype())) {
				String regex = dc.getStatement();
				Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(question);
				if (m.find()) {
					return dc;
				}
			}

		}

		return null;

	}

}
