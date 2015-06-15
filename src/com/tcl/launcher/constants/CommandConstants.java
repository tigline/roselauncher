package com.tcl.launcher.constants;

public class CommandConstants {
	public static final int COMMAND_EXCUTE_OK = 1;
	public static final int COMMAND_EXCUTE_FAILED = 0;
	
	public static final String LEAD_DIALOG = "LEAD_DIALOG";
	
	public static final String PREFIX_GLOBAL_COMMAND = "TV_";
	public static final String PREFIX_MOVIE_COMMAND = "MOVIE_";
	public static final String PREFIX_TVPLAY_COMMAND = "TVPLAY_";
	public static final String PREFIX_VARIETY_COMMAND = "VARIETY_";
	public static final String PREFIX_CARTOON_COMMAND = "CARTOON_";
	public static final String PREFIX_MUSIC_COMMAND = "MUSIC_";
	public static final String PREFIX_ALL_COMMAND = "ALL_";
	public static final String PREFIX_DEVICE_COMMAND = "DEVICE_";
	public static final String PREFIX_AIR_COMMAND = "AIR_";
	public static final String PREFIX_HOME_CLOUD_COMMAND = "HOME_CLOUD_";
	
	// 影视音乐
	public static final String MOVIE_SHOW_LIST = "MOVIE_SHOW_LIST"; // 电影列表页
	public static final String MOVIE_SHOW_SINGLE = "MOVIE_SHOW_SINGLE"; // 电影详情页
	public static final String MOVIE_PLAY = "MOVIE_PLAY"; // 播放电影
	public static final String TVPLAY_SHOW_LIST = "TVPLAY_SHOW_LIST"; // 电视剧列表页
	public static final String TVPLAY_SHOW_SINGLE = "TVPLAY_SHOW_SINGLE"; // 电视剧详情页
	public static final String TVPLAY_PLAY = "TVPLAY_PLAY"; // 播放电视剧
	public static final String VARIETY_SHOW_LIST = "VARIETY_SHOW_LIST"; // 综艺列表页
	public static final String VARIETY_SHOW_SINGLE = "VARIETY_SHOW_SINGLE";// 综艺详情页
	public static final String VARIETY_PLAY = "VARIETY_PLAY"; // 播放综艺节目
	public static final String CARTOON_SHOW_LIST = "CARTOON_SHOW_LIST"; // 动漫列表页
	public static final String CARTOON_SHOW_SINGLE = "CARTOON_SHOW_SINGLE"; // 动漫详情页
	public static final String CARTOON_PLAY = "CARTOON_PLAY"; // 播放动漫
	public static final String MUSIC_SHOW_LIST = "MUSIC_SHOW_LIST"; // 音乐列表页
	public static final String MUSIC_SHOW_SINGLE = "MUSIC_SHOW_SINGLE"; // 音乐详情页
	public static final String MUSIC_PLAY = "MUSIC_PLAY"; // 播放音乐
	public static final String MUSIC_LIST_PLAY = "MUSIC_LIST_PLAY"; // 播放音乐列表
	public static final String ALL_SHOW_LIST = "ALL_SHOW_LIST"; // 多领域页面综合播放
	// 针对影视和音乐的控制命令
	public static final String PAUSE = "PAUSE"; // 暂停
	public static final String SPEED = "SPEED"; // 快进
	public static final String PLAY = "PLAY"; // 播放
	public static final String BACKWARKS = "BACKWARKS"; //快退
	// 智能电视控制
	public static final String TV_VOL_ADD = "TV_VOL_ADD"; // 电视音量加
	public static final String TV_VOL_LOWER = "TV_VOL_LOWER"; // 电视音量减
	public static final String TV_VOL_MUTE_ON = "TV_VOL_MUTE_ON"; // 电视静音（开，关）
	public static final String TV_VOL_MUTE_OFF = "TV_VOL_MUTE_OFF"; // 电视静音（开，关）
	public static final String TV_SOUND_MODE_SET = "TV_SOUND_MODE_SET"; // 设置电视声音模式（音乐厅，电影院，新闻，标准）
	public static final String TV_AROUND_SOUND_PRO_SET = "TV_AROUND_SOUND_PRO_SET";// 设置电视声音场景（桌面模式，挂壁模式）
	public static final String TV_SOUNDTRACK = "TV_SOUNDTRACK";// 5.1声道，立体声
	public static final String TV_START_APP = "TV_START_APP";// 启动APP
	public static final String TV_GO_BACK = "TV_GO_BACK";// 返回
	public static final String TV_GO_HOME = "TV_GO_HOME";// 首页
	public static final String TV_GO_LEFT = "TV_GO_LEFT";// 向左
	public static final String TV_GO_RIGHT = "TV_GO_RIGHT";// 向右
	public static final String TV_GO_DOWN = "TV_GO_DOWN";// 向下
	public static final String TV_GO_UP = "TV_GO_UP";// 向上
	public static final String TV_GO_OK = "TV_GO_OK";// 确定
	public static final String TV_OPEN_MENU = "TV_OPEN_MENU";// 打开菜单
	public static final String TV_START_UP = "TV_START_UP";// 开机
	public static final String TV_SHUTDOWN = "TV_SHUTDOWN";// 关机
	public static final String TV_GO_HELP = "TV_GO_HELP";// 关机

	// 设备控制
	public static final String DEVICE_ONE_POWER_OPEN = "DEVICE_ONE_POWER_OPEN";
	public static final String DEVICE_ONE_POWER_CLOSE = "DEVICE_ONE_POWER_CLOSE";
	public static final String DEVICE_TWO_POWER_OPEN = "DEVICE_TWO_POWER_OPEN";
	public static final String DEVICE_TWO_POWER_CLOSE = "DEVICE_TWO_POWER_CLOSE";
	public static final String DEVICE_THREE_POWER_OPEN = "DEVICE_THREE_POWER_OPEN";
	public static final String DEVICE_THREE_POWER_CLOSE = "DEVICE_THREE_POWER_CLOSE";
	public static final String DEVICE_FOUR_POWER_OPEN = "DEVICE_FOUR_POWER_OPEN";
	public static final String DEVICE_FOUR_POWER_CLOSE = "DEVICE_FOUR_POWER_CLOSE";
	
	public static final String AIR_CONDITION_OPEN = "AIR_CONDITION_OPEN";
	public static final String AIR_CONDITION_CLOSE = "AIR_CONDITION_CLOSE";
	public static final String AIR_CONDITION_TEMP_SET = "AIR_CONDITION_TEMP_SET";
	public static final String AIR_CONDITION_TEMP_ADD_N = "AIR_CONDITION_TEMP_ADD_N";

	public static final String AIR_CONDITION_TEMP_SUB_L = "AIR_CONDITION_TEMP_SUB_L";
	public static final String AIR_CONDITION_TEMP_SUB_M = "AIR_CONDITION_TEMP_SUB_M";
	public static final String AIR_CONDITION_TEMP_SUB_H = "AIR_CONDITION_TEMP_SUB_H";

	public static final String AIR_CONDITION_TEMP_ADD_L = "AIR_CONDITION_TEMP_ADD_L";
	public static final String AIR_CONDITION_TEMP_ADD_M = "AIR_CONDITION_TEMP_ADD_M";
	public static final String AIR_CONDITION_TEMP_ADD_H = "AIR_CONDITION_TEMP_ADD_H";

	public static final String AIR_CONDITION_FAN_L = "AIR_CONDITION_FAN_L";
	public static final String AIR_CONDITION_FAN_M = "AIR_CONDITION_FAN_M";
	public static final String AIR_CONDITION_FAN_H = "AIR_CONDITION_FAN_H";

	public static final String AIR_CONDITION_MODLE_BLOW = "AIR_CONDITION_MODLE_BLOW";
	public static final String AIR_CONDITION_MODLE_HEAT = "AIR_CONDITION_MODLE_HEAT";
	public static final String AIR_CONDITION_MODLE_COOL = "AIR_CONDITION_MODLE_COOL";
	public static final String AIR_CONDITION_MODLE_DRY = "AIR_CONDITION_MODLE_DRY";
	public static final String AIR_CONDITION_MODLE_AUTO = "AIR_CONDITION_MODLE_AUTO";

	public static final String AIR_CONDITION_REQ_ACTUAL = "AIR_CONDITION_REQ_ACTUAL";
	public static final String AIR_CONDITION_REQ_HISTORY = "AIR_CONDITION_REQ_HISTORY";
	public static final String AIR_CONDITION_REQ_QUIT = "AIR_CONDITION_REQ_QUIT";
	// 家庭云
	public static final String HOME_CLOUD_OPEN_CAMERA = "HOME_CLOUD_OPEN_CAMERA";
	public static final String HOME_CLOUD_TAKE_PHOTO = "HOME_CLOUD_TAKE_PHOTO";
	public static final String HOME_CLOUD_PLAY_LAST_PHOTO = "HOME_CLOUD_PLAY_LAST_PHOTO";
	public static final String HOME_CLOUD_PHOTO_QUIT = "HOME_CLOUD_PHOTO_QUIT";
	public static final String HOME_CLOUD_SEARCH_PHOTO = "HOME_CLOUD_SEARCH_PHOTO";
	public static final String HOME_CLOUD_PLAY_SELECT_PHOTO = "HOME_CLOUD_PLAY_SELECT_PHOTO";
	public static final String HOME_CLOUD_SEARCHE_QUIT = "HOME_CLOUD_SEARCHE_QUIT";
	public static final String HOME_CLOUD_NOTICE_PLAY = "HOME_CLOUD_NOTICE_PLAY";
	public static final String HOME_CLOUD_NOTICE_QUIT = "HOME_CLOUD_NOTICE_QUIT";
	//主界面
	public static final String MAIN_SMART_HOME = "SELECT_SMART_HOME";
	public static final String MAIN_MEDIA= "SELECT_MEDIA";
	
	//pageid
	public static final String PAGE_ID_HOME = "HOME_PAGE";
	public static final String PAGE_ID_CAMERA_PAGE = "HOME_CLOUD_CAMERA_PAGE";
	public static final String PAGE_ID_PHOTO = "HOME_CLOUD_PHOTO_SEARCHE_PAGE";
	public static final String PAGE_ID_UPLOAD_PHOTO = "HOME_CLOUD_NOTICE_PAGE";
	public static final String PAGE_ID_MOVIE_PLAY = "MOVIE_PLAY_PAGE";
	public static final String PAGE_ID_MOVIE_SHOW_LIST = "MOVIE_SHOW_LIST_PAGE";
	public static final String PAGE_ID_MOVIE_SHOW_SINGLE = "MOVIE_SHOW_SINGLE_PAGE";
	public static final String PAGE_ID_TVPLAY_PLAY = "TVPLAY_PLAY_PAGE";
	public static final String PAGE_ID_TVPLAY_SHOW_LIST = "TVPLAY_SHOW_LIST_PAGE";
	public static final String PAGE_ID_TVPLAY_SHOW_SINGLE = "TVPLAY_SHOW_SINGLE_PAGE";
}
