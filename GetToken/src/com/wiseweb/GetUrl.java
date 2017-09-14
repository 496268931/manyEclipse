package com.wiseweb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetUrl
 */

public class GetUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().write("����̳̣�http://www.runoob.com");
		GetUrl getUrl=(GetUrl) new GetUrl();
		System.out.println(getUrl.getRequestURL(request));
	}

	private String getRequestURL(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (request == null) { 
			return ""; 
			} 
			String url = ""; 
			url = request.getContextPath(); 
			url = url + request.getServletPath(); 

			java.util.Enumeration names = request.getParameterNames(); 
			int i = 0; 
			if (!"".equals(request.getQueryString()) || request.getQueryString() != null) { 
			url = url + "?" + request.getQueryString(); 
			} 

			if (names != null) { 
			while (names.hasMoreElements()) { 
			String name = (String) names.nextElement(); 
			if (i == 0) { 
			url = url + "?"; 
			} else { 
			url = url + "&"; 
			} 
			i++; 

			String value = request.getParameter(name); 
			if (value == null) { 
			value = ""; 
			} 

			url = url + name + "=" + value; 
			try { 
			// java.net.URLEncoder.encode(url, "ISO-8859"); 
			} catch (Exception e) { 
			e.printStackTrace(); 
			} 
			} 
			} 
			try { 
			// String enUrl = java.net.URLEncoder.encode(url, "utf-8"); 
			} catch (Exception ex) { 
			ex.printStackTrace(); 
			} 

			return url; 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
