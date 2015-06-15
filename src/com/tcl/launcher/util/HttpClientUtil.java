package com.tcl.launcher.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil
{
	public static final String UTF_8 = "UTF-8";

	public static final String GBK = "GBK";

	private static DefaultHttpClient httpclient;

	static
	{
		HttpParams params = new BasicHttpParams();
		params.setParameter("http.connection.timeout", 60000);
		params.setParameter("http.socket.timeout", 60000);
		params.setParameter("http.protocol.element-charset", UTF_8);
		params.setParameter("http.protocol.content-charset", UTF_8);

		final SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		final ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(params, registry);
		httpclient = new DefaultHttpClient(manager, params);
	}

	public static String get(String uri, String responseEncode) throws IOException
	{
		HttpGet httpGet = new HttpGet(uri);
		HttpResponse httpResponse = httpclient.execute(httpGet);

		String charset = EntityUtils.getContentCharSet(httpResponse.getEntity());
		if (null == charset || "".equalsIgnoreCase(charset))
		{
			charset = responseEncode;
		}
		
		String s = EntityUtils.toString(httpResponse.getEntity(), charset);
		httpResponse.getEntity().consumeContent();
		return s;
	}

	public static HttpResponse getResponse(String uri, String responseEncode) throws IOException
	{
		HttpGet httpGet = new HttpGet(uri);
		HttpResponse httpResponse = httpclient.execute(httpGet);

		@SuppressWarnings("unused")
		String charset = responseEncode == null ? EntityUtils.getContentCharSet(httpResponse.getEntity()) : responseEncode;
		return httpResponse;
	}

	public static String getRedirectUrl(String uri, String responseEncode) throws IOException
	{
		HttpGet httpGet = new HttpGet(uri);
		httpGet.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
		HttpResponse httpResponse = httpclient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		httpResponse.getEntity().consumeContent();
		if (statusCode == 301 || statusCode == 302)
		{
			return httpResponse.getFirstHeader("Location").getValue();
		}
		else
		{
			return null;
		}

	}

	public static String get(String url, Map<String, String> params, String encode, String responseEncode) throws IOException
	{
		return get(addParams(url, params, encode), responseEncode);
	}

	public static String post(String uri, Map<String, String> m, String encoding) throws IOException
	{
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(getParamList(m), encoding);
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(entity);
		HttpResponse httpResponse = httpclient.execute(httpPost);
		String charset = encoding == null ? EntityUtils.getContentCharSet(httpResponse.getEntity()) : encoding;
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		{
			String s = EntityUtils.toString(httpResponse.getEntity(), charset);
			httpResponse.getEntity().consumeContent();
			return s;
		}
		else
		{
			httpResponse.getEntity().consumeContent();
			httpPost.abort();
			return null;
		}
	}

	public static HttpResponse postContent(String uri, Map<String, String> m, String encoding) throws IOException
	{
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(getParamList(m), encoding);
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(entity);
		return httpclient.execute(httpPost);
	}

	public static List<NameValuePair> getParamList(Map<String, String> params)
	{
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet())
		{
			qparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return qparams;
	}

	public static String addParams(String baseURI, Map<String, String> params, String encode)
	{
		if (baseURI == null)
			return null;
		String paramStr = URLEncodedUtils.format(getParamList(params), encode);
		char sp = (baseURI.indexOf('?') != -1) ? '&' : '?';
		return baseURI + sp + paramStr;
	}

}
