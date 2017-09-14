package com.bxt.sptask.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.nio.charset.Charset;

import com.bxt.sptask.utils.ByteArrayUtils;

public class HttpRequestSender {

	// private static final String[] methods = { "GET", "POST", "HEAD",
	// "OPTIONS", "PUT", "DELETE", "TRACE" };

	public static final String HTTP_METHOD_GET = "GET";

	public static final String HTTP_METHOD_POST = "POST";

	public static final String HTTP_METHOD_HEAD = "HEAD";

	public static final String HTTP_METHOD_OPTIONS = "OPTIONS";

	public static final String HTTP_METHOD_PUT = "PUT";

	public static final String HTTP_METHOD_DELETE = "DELETE";

	public static final String HTTP_METHOD_TRACE = "TRACE";

	// 默认类型:表单
	public static final String CONTENT_TYPE_Form_URLENCODED = "application/x-www-form-urlencoded";

	public static final String CONTENT_TYPE_TEXT_XML_UTF8 = "text/xml;charset=utf8";
	public static final String CONTENT_TYPE_JSON_UTF8 = "application/json;charset=utf8";
	// public static final String CONTENT_TYPE_TEXT_XML = "";
	// public static final String CONTENT_TYPE_TEXT_XML = "";

	/**
     *
     */
//	static {
//		URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
//			@Override
//			public URLStreamHandler createURLStreamHandler(String protocol) {
//				return new sun.net.www.protocol.http.Handler();
//			}
//		});
//	}

	public static String excuteHttpReq(String requestData, String charset,
			String requstUrl, long timeout, String contentType) {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(null, requstUrl,
					new sun.net.www.protocol.http.Handler());

			urlConnection = (HttpURLConnection) url.openConnection();
			// com.sun.net.ssl.internal.www.protocol.https.Handler()

			urlConnection.setReadTimeout((int) 5000 * 3600 * 1000);
			urlConnection.setConnectTimeout(5000 * 3600 * 1000);
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod(HTTP_METHOD_POST);
			// urlConnection.setRequestProperty("Content-type",
			// "text/xml;charset=" + charset);

			// urlConnection.setRequestProperty("Content-type",
			// "application/json;charset=" + charset);

			if (null != contentType && contentType.length() > 0) {
				urlConnection.setRequestProperty("Content-type", contentType);
			} else {
				urlConnection.setRequestProperty("Content-type",
						"application/x-www-form-urlencoded");
			}

			urlConnection.addRequestProperty("http.keepAlive", "false");

			if (null != requestData && requestData.length() > 0) {
				urlConnection.addRequestProperty("Content-Length", String
						.valueOf(requestData.getBytes(charset).length));
			}
			urlConnection.addRequestProperty("Connection", "Close");

			urlConnection.connect();
			outputStream = urlConnection.getOutputStream();

			if (null != requestData && requestData.length() > 0) {
				outputStream.write(requestData.getBytes(charset));
			}
			outputStream.flush();

			int respHttpCode = urlConnection.getResponseCode();


			if (respHttpCode != HttpURLConnection.HTTP_OK) {
				inputStream = urlConnection.getErrorStream();
				byte[] respData = ByteArrayUtils.copyToByteArray(inputStream);
				String errorMsg = new String(respData, charset);
				throw new RuntimeException("Illegal httpStatusCode '"
						+ respHttpCode + "' for url '" + requstUrl
						+ "' ErrorMsg:[" + errorMsg + "].");
			} else {
				inputStream = urlConnection.getInputStream();
				byte[] respData = ByteArrayUtils.copyToByteArray(inputStream);
				return new String(respData, charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"HttpRequestSender excute Http post request exception, cause:["
							+ e.getMessage() + "].", e);
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != urlConnection) {
				urlConnection.disconnect();
			}
		}
	}

	public static String excuteTaskHttpGetReq(String charset, String requstUrl,
			long timeout) {

		if (timeout <= 0) {
			timeout = 5000;
		}

		OutputStream outputStream = null;
		InputStream inputStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(null, requstUrl,
					new sun.net.www.protocol.http.Handler());

			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setReadTimeout((int) timeout);
			urlConnection.setConnectTimeout((int) timeout + 100 * 1000);
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod(HTTP_METHOD_GET);

//			urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//			urlConnection.setRequestProperty("Accept-Language","gzip, deflate, sdch");
//			urlConnection.setRequestProperty("Cache-Control","max-age=0");
//			urlConnection.setRequestProperty("Connection","keep-alive");
//			urlConnection.setRequestProperty("Upgrade-Insecure-Requests","1");
//			urlConnection.setRequestProperty("Cookie", "__utma=212713940.79864598.1481175073.1481175073.1481175073.1; __utmz=212713940.1481175073.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); PHPSESSID=eqvtafanjqn7lklboqcbbe2fv0");
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");
			
			urlConnection.setRequestProperty("Content-type",
					"application/json;charset=" + charset);

			urlConnection.addRequestProperty("http.keepAlive", "false");
			urlConnection.addRequestProperty("Connection", "Close");

			urlConnection.connect();

			int respHttpCode = urlConnection.getResponseCode();

			if (respHttpCode != HttpURLConnection.HTTP_OK) {
				inputStream = urlConnection.getErrorStream();
				String errorMsg = null;
				if (null != inputStream) {
					byte[] respData = ByteArrayUtils
							.copyToByteArray(inputStream);
					errorMsg = new String(respData, charset);
				}
				throw new RuntimeException("Illegal httpStatusCode '"
						+ respHttpCode + "' for url '" + requstUrl
						+ "' ErrorMsg:[" + errorMsg + "].");
			} else {
				inputStream = urlConnection.getInputStream();
				byte[] respData = ByteArrayUtils.copyToByteArray(inputStream);

				return removeHeader4UTF8(respData, charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"HttpRequestSender excute Http post request exception, cause:["
							+ e.getMessage() + "].", e);
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != urlConnection) {
				urlConnection.disconnect();
			}
		}
	}

	private static String removeHeader4UTF8(byte[] respBytes, String charset) {
		byte[] respNew = new byte[respBytes.length - 3];
		if (respBytes[0] == 0xef && respBytes[1] == 0xbb
				&& respBytes[2] == 0xbf) {
			System.arraycopy(respBytes, 3, respNew, 0, respBytes.length - 3);
			respBytes = respNew;
		}
		String respData = new String(respBytes, Charset.forName(charset));
		if (respData.equals("")) {
			throw new RuntimeException(
					"solrStore load dicctionary error,response is null!");
		}
		// 去除window中 utf8的php有bom
		if (((int) respData.charAt(0)) == 65279) {
			respData = respData.substring(1);
		} else if (((int) respData.charAt(0)) == 38168) {
			respData = respData.substring(1);
		} else {
			int ttt = ((int) respData.charAt(0));
		}
		return respData;
	}

	public static void main(String[] args) {
		String charset = "utf-8";
		//String requstUrl1 = "http://127.0.0.1:8081/sysadmin/model/taskagent.php?type=get&tasktype=[{\"tasktype\":2,\"task\":15}]&host=wangcc-fa-ad-cd";
		//String requstUrl  = "http://127.0.0.1:8081/sysadmin/model/taskagent.php?type=get&tasktype=[{\"tasktype\":2,\"task\":20}]&host=wangcc-fa-ad-cd";
		String requstUrl  = "http://192.168.0.20:8081/sysadmin/model/taskagent.php?type=javaget&task=20&host=wangcc-fa-ad-cd&taskid=392873";
		long timeout = 60000;
		String strrsp = HttpRequestSender.excuteTaskHttpGetReq(charset, requstUrl,
				timeout);
		
		System.out.println(strrsp);
	}

}
