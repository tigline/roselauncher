package com.tcl.launcher.util;


public class Constant {
	public static final int INIT_XF_VOICE = 1001;
	public static final int START_XF_VOICE = 1002;
	public static final int STOP_XF_VOICE = 1003;
	public static final int INIT_XF_TTS = 1004;
	public static final int START_XF_TTS = 1005;
	public static final int INIT_XF_WAKEUP = 1006;
	public static final int START_XF_WAKEUP = 1007;
	public static final int STOP_XF_WAKEUP = 1008;
	public static final String XF_VOICE_KEY = "appid=535625b1";
	public static final String XF_VOICE_SHAREDPREFERENCES = "com.iflytek.box.voice.setting";
	public static final String XF_VOICE_NAME = "xiaoyan";// xiaoyan xiaoqian
															// jiajia nannan
	public static final String XF_VOICE_SPEED = "50";
	public static final String XF_VOICE_VOLUME = "50";

	public static final int OPEN_DIALOG = 2007;
	public static final int HINDE_DIALOG = 2003;

	public static final String APP_DEFULT_VALUE = "null";
	public static final String QUSTUION_INDEX = "qustuion_index";
	public static final String SERVER_RESULT_INDEX = "server_result_index";
	public static final int SERVER_HANDER_FLAG = 8888;

	public static final int FLVCD_URL_LIST = 1112;
	public static final String DEFULT_ADDR = "广东省深圳市";


	public class SemanticUrl {
		// http://120.24.65.74:8080/rs/api/roseEngineControl.json?engineid=03EDD589014D1000E00000030A780239&question=刘德华的电影&imei=test
		public static final String BASE_SERVER_URL = "http://120.24.65.74:8080/rs/api";
		public static final String BASE_SEMANTIC_URL = BASE_SERVER_URL
				+ "/roseEngineControl.json?engineid=03EDD589014D1000E00000030A780239";
		// http://120.24.65.74:8080/rs/api/roseItemDetail.json?engineid=03EDD589014D1000E00000030A780239&question=5891&imei=test&domain=TVPLAY
		public static final String BASE_INFO_URL = BASE_SERVER_URL
				+ "/roseItemDetail.json?engineid=03EDD589014D1000E00000030A780239";
	}

	public class DisplayKeyIndex {
		public static final String DISPLAY_QUESTION = "display_question";
		public static final String DISPLAY_ANSWER = "display_answer";
		public static final String DISPLAY_WEATHER = "display_weather";
		public static final String DISPLAY_CHAT = "display_chat";
		public static final String DISPLAY_STOCK = "display_stock";
		public static final String QUESTION = "question";
		public static final String ANSWER = "answer";
		public static final String KEY = "key";
		public static final int ROTATE_ANIMATION_VISIBLE = 1015;
		public static final int ROTATE_ANIMATION_GONE = 1016;
		public static final int LEVEL_INIT = 1017;
	}

}
