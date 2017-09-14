<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>用户管理详情</title>
</head>
<body>
<form:form id="usersForm" action="${context}/UserController/saveOrUpdateUser.do" method="post" modelAttribute="userVO">
    <form:hidden path="userid"/>
	<div id="main">
	    <div id="main_panel">
	        <table class="edit_table">
	            <thead>
	            <tr>
	                <th colspan="4">
                        <c:if test="${userVO.userid != null}">修改用户</c:if>
                        <c:if test="${userVO.userid == null}">新建用户</c:if>
	                </th>
	            </tr>
	            </thead>
	        
	            <tr>
	                    <th><span style="color: red;">* </span><label for="alloweditinfo">是否允许修改个人信息</label></th>
	                    <td>
	                        <input id="alloweditinfo" name="alloweditinfo" type="text" value="${userVO.alloweditinfo}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="binduserid">usertype为2时，此字段表示绑定的用户</label></th>
	                    <td>
	                        <input id="binduserid" name="binduserid" type="text" value="${userVO.binduserid}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="email">邮件地址</label></th>
	                    <td>
	                        <input id="email" name="email" type="text" value="${userVO.email}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="expiretime">账号失效时间</label></th>
	                    <td>
	                        <input id="expiretime" name="expiretime" type="text" value="${userVO.expiretime}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="isonline">是否在线0.离线1.在线</label></th>
	                    <td>
	                        <input id="isonline" name="isonline" type="text" value="${userVO.isonline}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="onlinetime">在线时长</label></th>
	                    <td>
	                        <input id="onlinetime" name="onlinetime" type="text" value="${userVO.onlinetime}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="password">密码</label></th>
	                    <td>
	                        <input id="password" name="password" type="text" value="${userVO.password}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="realname">真实姓名</label></th>
	                    <td>
	                        <input id="realname" name="realname" type="text" value="${userVO.realname}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="tenantid">租户ID</label></th>
	                    <td>
	                        <input id="tenantid" name="tenantid" type="text" value="${userVO.tenantid}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="updatetime">updatetime</label></th>
	                    <td>
	                        <input id="updatetime" name="updatetime" type="text" value="${userVO.updatetime}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="username">用户名</label></th>
	                    <td>
	                        <input id="username" name="username" type="text" value="${userVO.username}"/>
	                    </td>
	            </tr>
	            <tr>
	                    <th><span style="color: red;">* </span><label for="usertype">用户类型，1普通用户，2只读用户（绑定到一个普通用户）</label></th>
	                    <td>
	                        <input id="usertype" name="usertype" type="text" value="${userVO.usertype}"/>
	                    </td>
	            </tr>
	            <tr>
	            </tr>
	        </table>
            <div class="action_bar">
                
                <input type="submit" class="btn_action" id="save" value="保存"/>
              
                <input type="button" class="btn_action" id="cancel" value="取消"/>
            </div>
	    </div>
	</div>
</form:form>
</body>
</html>