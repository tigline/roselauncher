package com.tcl.launcher.entry;

public class CommandResponse {
	private String rc;         //应答码
	private String error;      //对应答码的错误消息
	private String command;    //命令
	private String data;       //数据
	private String semantic;   //语义解析格式化表示
	private String ad;         //广告推广
	private String tips;       //结果内容的关联信息，作为用户后续交互的引导展示
}
