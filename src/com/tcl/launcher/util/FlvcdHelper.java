package com.tcl.launcher.util;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class FlvcdHelper {
	private static final String TAG = "FlvcdHelper";
	
	public static void getFlvcd(String url, Handler handler) {

		TLog.i(TAG, "原始请求解析路径:" + url);

		ArrayList<String> urllist = new ArrayList<String>();

		try {

			url = URLEncoder.encode(url, "UTF-8");

			String flvcd = "http://www.flvcd.com/parse.php?format=&kw={url}";

			flvcd = flvcd.replace("{url}", url);
			String charset = "gbk";

			String html = HttpClientUtil.get(flvcd, charset);

			String regex = "<BR><a href=\"(.*?)\"";

			html = html.replaceAll("\n", "");

			Pattern p = Pattern.compile(regex, Pattern.MULTILINE);

			Matcher m = p.matcher(html);

			urllist = new ArrayList<String>();

			while (m.find()) {
				String mp4Url = m.group(1);
				urllist.add(mp4Url);
			}

			// 单条结果时
			if (urllist.size() == 0) {
				regex = "下载地址.*?<a href=\"(.*?)\"";
				p = Pattern.compile(regex, Pattern.DOTALL);
				m = p.matcher(html);
				while (m.find()) {
					String mp4Url = m.group(1);
					urllist.add(mp4Url);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// if (Utils.isNotEmpty(urllist)) {
		// Bundle bundle = new Bundle();
		// bundle.putSerializable("url_list", (Serializable) urllist);
		// Message message = handler.obtainMessage();
		// message.what = Constant.FLVCD_URL_LIST;
		// message.setData(bundle);
		// message.sendToTarget();
		// }
	}

	public static List<String> getFlvcd(String url) {

		TLog.i(TAG, "原始请求解析路径:" + url);

		ArrayList<String> urllist = new ArrayList<String>();

		try {
			url = URLEncoder.encode(url, "UTF-8");
			String flvcd = "http://www.flvcd.com/parse.php?format=&kw={url}";
			flvcd = flvcd.replace("{url}", url);
			String charset = "gbk";
			String html = HttpClientUtil.get(flvcd, charset);
			String regex = "<BR><a href=\"(.*?)\"";
			html = html.replaceAll("\n", "");
			Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
			Matcher m = p.matcher(html);
			urllist = new ArrayList<String>();
			while (m.find()) {
				String mp4Url = m.group(1);
				urllist.add(mp4Url);
			}
			// 单条结果时
			if (urllist.size() == 0) {
				regex = "下载地址.*?<a href=\"(.*?)\"";
				p = Pattern.compile(regex, Pattern.DOTALL);
				m = p.matcher(html);
				while (m.find()) {
					String mp4Url = m.group(1);
					urllist.add(mp4Url);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return urllist;
	}
}
