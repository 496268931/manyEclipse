<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base target="_parent">
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <SCRIPT language=JavaScript>   
	function g(formname)    {   
		var url = "http://www.jd.com";   
		if (formname.s[1].checked) {   
		    formname.ct.value = "2097152";   
		}   
		else {   
		    formname.ct.value = "0";   
		}   
		formname.
		formname.action = url;   
		return true;   
	}   
</SCRIPT>
  <body>
    
     <form name="f1" onsubmit="return g(this)">   
        
		<table bgcolor="#FFFFFF" style="font-size:9pt;"> 
		  <tr>
		  <td><a href="http://www.jd.com">京东</a></td>
		  </tr>  
		 </table>   
	</form>
  </body>
</html>
