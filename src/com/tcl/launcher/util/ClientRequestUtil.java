package com.tcl.launcher.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.tcl.launcher.core.VoiceControl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/**
 * @see encode()
 * @see getInfomations()
 * @see initHttp()
 * @see setValues()
 * @see updateValues()
 * @see getHttpClient()
 */
public class ClientRequestUtil {
	private static String ENCODE_FASHION = "UTF-8";

	/**
	 * URL Encode Dispose
	 * 
	 * @param String
	 *            URL
	 * @return String Encode Result
	 * @throws
	 */
	public static String encode(String URL) {
		String result = URL;
		try {
			if (Utils.isNotEmpty(URL)) {

				result = URLEncoder.encode(URL, ENCODE_FASHION);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void executeHttpGet(String request_url, VoiceControl control) {
		String result = null;
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		try {
			url = new URL(request_url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(2 * 1000);
			connection.setReadTimeout(4 * 1000);
			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}

			result = strBuffer.toString();
		} catch (Exception e) {
			TLog.i("Client", "ClientRequestUtil executeHttpGet Exception:" + e.toString());
		} finally {
			control.handResponse(result);

			if (connection != null) {
				connection.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String executeHttpGet(String request_url) {
		String result = null;
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		try {
			url = new URL(request_url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(2 * 1000);
			connection.setReadTimeout(4 * 1000);
			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}

			result = strBuffer.toString();
		} catch (Exception e) {
			TLog.i("Client", "ClientRequestUtil executeHttpGet Exception:" + e.toString());
		} finally {
			if (TextUtils.isEmpty(result)) {
				result = "connect_out_time";
			}

			if (connection != null) {
				connection.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
}
