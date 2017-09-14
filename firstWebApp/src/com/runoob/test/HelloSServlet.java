package com.runoob.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloSServlet implements Servlet{

	private int count;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy");
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		System.out.println("ServletConfig");
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		System.out.println("getServletInfo");
		return null;
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		// TODO Auto-generated method stub
		
		URL url = null;
		try {
			url = new URL("https://api.weibo.com/oauth2/authorize?client_id=1651601463&redirect_uri=http://sns.whalecloud.com/sina/callback?imei=c1cbc99be4cf339b14869bb91d963cf&appkey=541cf68dfd98c51895027d3c&key=1651601463&secret=84502c2a408c8de5a1c6b7f76d574a84&pcv=2.0&response_type=code");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection)url.openConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			conn.getResponseCode();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String realUrl=conn.getURL().toString();
		System.out.println(realUrl);
		conn.disconnect();
		
		
		
		
		
		
		System.out.println("init");
		System.out.println("init");
		
		String user = servletConfig.getInitParameter("user");
		System.out.println("user:"+user);
		
		Enumeration<String> names=servletConfig.getInitParameterNames();
		while(names.hasMoreElements()){
			String name=names.nextElement();
			String value = servletConfig.getInitParameter(name);
			System.out.println("^^"+name+":"+value);
			
		}
		
		String servletName = servletConfig.getServletName();
		System.out.println(servletName);
		
		
		//��ȡServletContext����
		ServletContext servletContext=servletConfig.getServletContext();
		
		String drive = servletContext.getInitParameter("driver");
		System.out.println("driver:"+drive);
		
		Enumeration<String> names2=servletContext.getInitParameterNames();
		while (names2.hasMoreElements()) {
			String name=names2.nextElement();
			System.out.println("-->"+name);
		}
		
		
		
		String realPath=servletContext.getRealPath("/RealPath");
		//��ȡ�ڷ������ϵľ���·���������ǲ���ǰ������·��
		//E:\java\apache-tomcat-7.0.65\webapps\firstWebApp\RealPath
		System.out.println(realPath);
		
		String contextPath =servletContext.getContextPath();
		System.out.println(contextPath);
		
		try {
			ClassLoader classLoader=getClass().getClassLoader();
			InputStream is = classLoader.getResourceAsStream("jdbc.properties");
			System.out.println("1."+is);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		try {
			 
			InputStream is2 = servletContext.getResourceAsStream("jdbc.properties");
			System.out.println("2."+is2);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
		
		
		
		
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("service");
		count++;
		System.out.println(count);
		
		
	}
	
	public HelloSServlet(){
		System.out.println("helloSServlet 's construction");
	}

}
