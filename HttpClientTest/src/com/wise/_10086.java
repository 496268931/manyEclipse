package com.wise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;

public class _10086 {

	public void test() throws HttpException, IOException {
		String url = "http://12582.10086.cn/user/login/verifycode.aspx";
		HttpClient httpClient = new HttpClient();
		DefaultHttpParams.getDefaultParams().setParameter(
				"http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		GetMethod getMethod = new GetMethod(
				url);
		getMethod.setRequestHeader("Host", "12582.10086.cn");
		getMethod
				.setRequestHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
		getMethod
				.setRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		getMethod.setRequestHeader("Accept-Language", "zh-cn,zh;q=0.5");
		getMethod.setRequestHeader("Accept-Charset",
				"GB2312,utf-8;q=0.7,*;q=0.7");
		getMethod.setRequestHeader("Connection", "keep-alive");
		// getMethod.setRequestHeader("Cookie",
		// "Hm_lvt_6e06bb5a029d6c5473951d1079638828=1328777184942; Hm_lvt_e64244e1e591d0337e17a12b714c0996=1328777186856; WT_FPC=id=183.16.35.230-1530895312.30204303:lv=1328174810886:ss=1328174810886; ASP.NET_SessionId=gj25p555exiqjd45kdcqoq55; BIGipServernxt-wz=369797312.20480.0000; Hm_lpvt_6e06bb5a029d6c5473951d1079638828=1328777184942; Hm_lpvt_e64244e1e591d0337e17a12b714c0996=1328777186856; .12582portals=4CF97704261E34DBE12913CBA18211005E960282A771D94FF3709BAFC99610A7397BE8293ADB2E876A0A7380AC4A158016419745F27511D6E79F82A408D009519D6DDFE18A578E5CFC5F48025C75B33B6EBD43953A7DB05AEBAAB856E0AA29112818B4910350AADACD2012F5DE56297B1F6622ED7F4959D31E19C474E48F7773D7966437");
		getMethod.setRequestHeader("Cache-Control", "max-age=0");
		int code = httpClient.executeMethod(getMethod);
		Header header = getMethod.getResponseHeader("Set-cookie");
		System.out.println("1");
		System.out.println(header.getValue());
		String headerCookie = header.getValue();
		String SessionId = headerCookie.substring(
				headerCookie.indexOf("NET_SessionId=")
						+ "NET_SessionId=".length(), headerCookie.indexOf(";"));
		System.out.println("2");
		System.out.println(SessionId);
		String BIGipServernxt = headerCookie.substring(
				headerCookie.indexOf("BIGipServernxt-wz=")
						+ "BIGipServernxt-wz=".length(), headerCookie.length());
		BIGipServernxt = BIGipServernxt.substring(0,
				BIGipServernxt.indexOf(";"));
		StringBuffer sb = new StringBuffer();
		sb.append("Hm_lvt_6e06bb5a029d6c5473951d1079638828=1328778307106; ");
		sb.append("Hm_lvt_e64244e1e591d0337e17a12b714c0996=1328778308090; ");
		sb.append("WT_FPC=id=183.16.35.230-1530895312.30204303:lv=1328174810886:ss=1328174810886; ");
		sb.append("ASP.NET_SessionId=").append(SessionId).append("; ");
		sb.append("BIGipServernxt-wz=").append(BIGipServernxt).append("; ");
		sb.append("Hm_lpvt_6e06bb5a029d6c5473951d1079638828=1328778307106; ");
		sb.append("Hm_lpvt_e64244e1e591d0337e17a12b714c0996=1328778308090");
		System.out.println("3");
		System.out.println(sb.toString());
		String ValidCode = savegif(getMethod);
		getMethod.releaseConnection();
		// 第二次链接
		httpClient.getHostConfiguration().setHost("12582.10086.cn", 80, "http");
		PostMethod method = getPostMethod(ValidCode);
		method.setRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
		method.setRequestHeader("Host", "12582.10086.cn");
		method.setRequestHeader("Accept",
				"application/json, text/javascript, */*");
		method.setRequestHeader("Accept-Language", "zh-cn,zh;q=0.5");
		// method.setRequestHeader("Accept-Encoding", "gzip, deflate");
		method.setRequestHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		method.setRequestHeader("Connection", "keep-alive");
		method.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		method.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		method.setRequestHeader("Referer", "http://12582.10086.cn/user/login/");
		method.setRequestHeader("Cookie", sb.toString());
		httpClient.executeMethod(method);
		System.out.println("4");
		System.out.println(method.getStatusCode());
		System.out.println("5");
		System.out.println(method.getResponseBodyAsString());
		// 第三次链接
		Header header1 = method.getResponseHeader("Set-cookie");
		System.out.println("6");
		System.out.println("dd=" + header1.getValue());
		sb.append("; " + header1.getValue());
		Cookie[] cookies = httpClient.getState().getCookies();
		method.releaseConnection();
		String my = "http://12582.10086.cn/my";
		System.out.println("7");
		System.out.println(sb.toString());
		getMethod = new GetMethod(my);
		getMethod.setRequestHeader("Host", "12582.10086.cn");
		getMethod
				.setRequestHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
		getMethod
				.setRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		getMethod.setRequestHeader("Accept-Language", "zh-cn,zh;q=0.5");
		getMethod.setRequestHeader("Accept-Charset",
				"GB2312,utf-8;q=0.7,*;q=0.7");
		getMethod.setRequestHeader("Connection", "keep-alive");
		getMethod.setRequestHeader("Referer",
				"http://12582.10086.cn/user/login");
		getMethod.setRequestHeader("Cookie", sb.toString());
		code = httpClient.executeMethod(getMethod);
		System.out.println("8");
		System.out.println(getMethod.getStatusCode());
		System.out.println("9");
		System.out.println(getMethod.getResponseBodyAsString());
		File storeFile = new File("c:/2008sohu.html");
		FileOutputStream output = new FileOutputStream(storeFile);
		// 得到网络资源的字节数组,并写入文件
		output.write(getMethod.getResponseBody());
		output.close();
	}

	public String savegif(GetMethod getMethod) throws IOException {
		File storeFile = new File("c:/2008sohu.gif");
		FileOutputStream output = new FileOutputStream(storeFile);
		// 得到网络资源的字节数组,并写入文件
		output.write(getMethod.getResponseBody());
		output.close();
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		String ValidCode = "";
		try {
			ValidCode = br.readLine();
			br.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ValidCode;
	}

	private PostMethod getPostMethod(String ValidCode) {
		PostMethod post = new PostMethod("/ajax/postlogin");
		NameValuePair[] simcard = { new NameValuePair("email", "XXX"),
				new NameValuePair("password", "XXX"),
				new NameValuePair("ValidCode", ValidCode),
				new NameValuePair("rme", "0"), };
		post.setRequestBody(simcard);
		return post;
	}

	public static void main(String args[]) {
		_10086 _10086 = new _10086();
		try {
			_10086.test();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}