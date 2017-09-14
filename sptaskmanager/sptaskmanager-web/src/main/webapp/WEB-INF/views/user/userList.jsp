<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>用户管理列表</title>
</head>
<body>
	<table>
		<tr>
	       		<td>id</td>
	       		<td>用户名</td>
	       		<td>是否允许修改个人信息</td>
	       		<td>邮件地址</td>
	       		<td>真实姓名</td>
       		 </tr>
       <c:forEach items="${userList}" var="item">
       		<tr>
	       		<td>${item.userid}</td>
	       		<td>${item.username}</td>
	       		<td>${item.alloweditinfo}</td>
	       		<td>${item.email}</td>
	       		<td>${item.realname}</td>
	       		<td><a href="http://localhost:8080/taskspos/UserController/form.do?userid=${item.userid}">编辑</a></td>
       		     <td><a href="http://localhost:8080/taskspos/UserController/deleteUser.do?userid=${item.userid}">删除</a></td>
       		 </tr>
       </c:forEach>
      </table>
</body>
</html>