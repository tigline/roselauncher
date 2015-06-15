package com.tcl.launcher.json;

public class ParseJsonUtil {
	// 根部字段（未加入semantic和ad）
	public static final String RC = "rc";
	public static final String ERROR = "error";
	public static final String ACTION_COMMAND = "command";
	public static final String ACTION_DATA = "data";
	public static final String TIPS = "tips";

	// "data"下字段
	public static final String QUESTION = "question";
	public static final String DOMAIN = "domain";
	public static final String TOTAL = "total";
	public static final String PN = "pn";
	public static final String PS = "ps";
	public static final String PC = "pc";
	public static final String ACTION_RESULT = "result";
	public static final String DYNAMIC_COMMAND = "dynamicCommand";

	// "dynamicCommand"下字段
	public static final String STATEMENT = "statement";
	public static final String COMMAND = "command";
	public static final String PARS = "pars";
	public static final String MATCHTYPE = "matchtype";
}
