<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务管理</title>
<link href="${context}/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${context}/static/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${context}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	var CONTEXT = "${context}";
</script>
<script type="text/javascript" src="${context}/static/js/common/overtime.js"></script>
<script language="JavaScript">
	function forwardpage(){
		var _topwin = window;
		while (_topwin != _topwin.parent.window) {
			_topwin = _topwin.parent.window;
		}
		if (window != _topwin)
		_topwin.document.location.href = '${context}/jsp/login/login.jsp';
	}
	forwardpage();
	$(function(){
		$("#errorInfo").css("display","none");
		var errorInfo ="${errorInfo}";
		if(errorInfo){
			$("#errorInfo").css("display","block").html(errorInfo);
			
		}
	});
	function refresh() {
        document.getElementById('img').src="<%=request.getContextPath() %>/kaptcha.jpg?"+Math.random();
    }
</script>
</head>
<%
String remoteAddr=request.getRemoteAddr();
String key = "0:0:0:0:0:0:0:1";
if(remoteAddr.equals(key)){
// 	response.setStatus(HttpServletResponse.SC_NOT_FOUND);//返回404状态码
// 	request.getRequestDispatcher("/404.jsp").forward(request,response);
}
%>
<body>
<form action="${context}/loginController/login.do" method="post">
	<div class="wrap">
		<div class="loginPanel">
			<ul>
				<li><span>用户名：</span><input type="text" class="input-txt-style" value=""  name="username"/></li>
				<li><span>密    码：</span><input type="password" class="input-psw-style" value="" name="password"/></li>
				<li><span>验证码：</span>
					<input type="text" class="input-psw-style" value="禁用" name="checkCode" style="width:50px;"/>
					<img id="img" src="<%=request.getContextPath() %>/kaptcha.jpg" width="90" height="20" onclick="refresh();" alt="点击更换验证码" title="点击更换验证码"/>
				</li>
				<li><span></span><div id="errorInfo" style="display: none;color: red;"></div></li>
				<li><span></span><input type="submit" value="登陆" class="btn-style01"/></li>
			</ul>
		</div>
	</div>
</form>
</body>
<%System.out.print("PATH: "+ getServletContext().getRealPath(""));%>
</html>