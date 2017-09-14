package com.runoob.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


import javax.servlet.http.HttpServletRequest;
import java.util.*;
public class LoginServlet implements Servlet {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("请求来了。。。");
		
		System.out.println(request);
		
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		System.out.println(user+","+password);
		
		System.out.println();
		String interesting =request.getParameter("interesting");
		System.out.println(interesting);
		
		System.out.println();
		String[] interestings = request.getParameterValues("interesting");
		for(String intest:interestings){
			System.out.println("-->"+intest);
		}
		
		System.out.println();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String val = request.getParameter(name);
			System.out.println("^^"+name+":"+val);
			
		}
		
		Map<String, String[]> map =   request.getParameterMap();
		for(Map.Entry<String, String[]> entry:((Map<String, String[]>) map).entrySet()){
			System.out.println("**"+entry.getKey()+":"+Arrays.asList(entry.getValue()));
		}
		

		HttpServletRequest httpServletRequest=(HttpServletRequest) request;
		
		String requestURI = httpServletRequest.getRequestURI();
		System.out.println(requestURI);

		String method = httpServletRequest.getMethod();
		System.out.println(method);
		
		//如果是get请求，返回user=atguigu&password=123456&interesting=party&interesting=shopping&interesting=tv
		//如果是post请求，返回null
		String queryString = httpServletRequest.getQueryString();
		System.out.println(queryString); 
		
		//http://localhost:8080/firstWebApp/loginServlet?user=atguigu&password=123&interesting=party&interesting=tv
		//    返回/loginServlet
		String servletPath = httpServletRequest.getServletPath();
		System.out.println(servletPath);
		
		response.setContentType("application/word");
		
		PrintWriter out = response.getWriter();
		out.print("helloworld");
				
		
	}

	 
}
