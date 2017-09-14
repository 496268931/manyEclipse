<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<c:set var="staticPath" value="http://zrbcxz.chinazrbc.com/console"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无权限访问！</title>
<link href="${staticPath}/static/css/error.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="err403">
<div class="err403-1">
<div class="err403-f">禁止访问：访问被拒绝！</div>
<div class="err403-x"><span><a href="${context}/jsp/login/login.jsp"><span id="num">10</span> 秒</a></span>&nbsp;后自动跳转登录页面！</div>
</div>
</div>
<script type="text/javascript">
	var num = document.getElementById("num").innerText;
	var interval = null;
	function goBack() {
		num--;
		document.getElementById("num").innerText = num;
		if (num === 0) {
			clearInterval(interval);
			window.location.href="${context}/jsp/login/login.jsp"; 
		}
	}
	interval = setInterval(goBack, 2000);
</script>
	
</body>
</html>
