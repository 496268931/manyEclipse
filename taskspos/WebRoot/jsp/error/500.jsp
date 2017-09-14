<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<c:set var="staticPath" value="http://zrbcxz.chinazrbc.com/console"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>您要访问的页面出问题了！</title>
<link href="${staticPath}/static/css/error.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="err500">
<div class="err500-1">
<div class="err500-f">您要访问的页面出问题了！</div>
<div class="err500-x">您可以，<span><a href="${context}/jsp/login/login.jsp">返回登录></a></span></div>
</div>
</div>
</body>
</html>
