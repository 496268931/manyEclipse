<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务管理</title>
<link rel="stylesheet" href="${basePath}/static/css/taskcommon.css" type="text/css" />
<link rel="stylesheet" href="${basePath}/static/css/addsptask/addsptask.css" type="text/css" />
<script type="text/javascript" src="${basePath}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${basePath}/static/js/addsptask/addsptask.js"></script>
<script type="text/javascript" src="${basePath}/static/js/addsptask/dtree.js"></script>
<script type="text/javascript" src="${basePath}/static/js/addsptask/tasktree.js"></script>
<script type="text/javascript" src="${basePath}/static/js/addsptask/myParamDefination.js"></script>
<script type="text/javascript" src="${basePath}/static/js/addsptask/tasktools.js"></script>
<script type="text/javascript" src="${basePath}/static/js/addsptask/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/common.js"></script>

<script type="text/javascript">
var basePath = '${pageContext.request.contextPath}';
var paramobj = {};
</script>
</head>
<body>
<form id="taskmanager" name="taskmanager" action="" method="post">
	<input type="hidden" id="paramid" name="paramid" value=""/>
	<input type="hidden" id="taskid" name="taskid" value="${taskid}"/>
	<input type="hidden" id="childtaskid" name="childtaskid" valiue="${childtaskid}"/>
	<input type="hidden" id="tagid" name="tagid" value=""/>
</form>
<div id="container">
   <div id="cardcontent">
      <div id="cardleft">
	     <div id="divNavgationTitle" class="cssOuter">
			<table class="cssTitle">
	          	<tr>
					<td class="cssTdLeft">
						<img src="${basePath}/static/imags/arrowdown.gif" id="navArrow" onClick="leftDivSH('divNavgation')"/>
					</td>
					<td class="cssTdRight" onClick="leftDivSH('divNavgation')">
						我的任务
					</td>					
				</tr>				
			</table>
	     </div> 
	     <div class="dtree" id="divNavgation" style="height:545px;">
			<table style="width:90%;">
		 		<tr><td align="right">
			 		<label class="lblFont" onclick="newNavgationPage();">新建</label>
					<label class="lblFont">|</label>
					<label class="lblFont" onclick="delNavgationTree()";>删除</label>
	     		</td></tr>
	     		<tr><td>
					<div id="parenttochildtree"></div>
				</td></tr>	
			</table>	
	     </div>
	  </div>
	  <div id="cardcenter">
	    <div id="divNavgationTitle" class="cssOuter">
			<table class="cssTitle">
	          	<tr>
					<td class="cssTdLeft">
						<img src="${basePath}/static/imags/arrowdown.gif" id="navArrow"/>
					</td>
					<td class="cssTdRight">
						参数定义
					</td>					
				</tr>				
			</table>
	     </div>
	     <div class="dtree" id="divtasktree">
			
			<table border="0" width="98%">
				<tr>
					<td width="230px">
					   <table>
					     <tr><td>父参数名:<input type="text" name="parentParamName" id="parentParamName"></td></tr>
					   	 <tr><td>子参数名:<input type="text" name="paramName" id="paramName"></td></tr>
					   	 <tr><td align="center">选择参数类型？</td></tr>
					   	 <tr><td align="center">
					   	 	<label><input name="Fruit" type="radio" value="5" onclick="radObjHandle()"/>整数 </label> 
							<label><input name="Fruit" type="radio" value="13" onclick="radObjHandle()"/>字符串 </label> 
							<label><input name="Fruit" type="radio" value="23" onclick="radObjHandle()"/>对象</label><br/>
							<label><input name="Fruit" type="radio" value="24" onclick="radObjHandle()"/>数组:
							<select id="selArrType" name="selArrType">
								<option value="5">整数</option>
								<option value="13">字符串</option>
								<option value="18">日期</option>
								<option value="23">对象</option>
								<option value="24">数组</option>
							</select>
							</label><br/>
							<label><input name="Fruit" type="radio" value="18" onclick="radObjHandle()"/>日期 
							 <input id="seldatetime" name="seldatetime" class="Wdate" type="text" readonly="readonly" style="width:150px;" onclick="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
							</label>
							
						</td></tr>
						<tr><td align="center" >参数值</td></tr>
						<td align="center" colspan="4">
							&nbsp;<textarea name="paramContent" id="paramContent" rows="4" cols="25" >${schContent}</textarea>
						</td></tr>
						<tr><td style="text-align:center;">
							<input type='button' class="btn" name='addParamTreeBtn' id='addParamTreeBtn' value="确&nbsp;定"/>
							&nbsp;&nbsp;&nbsp;
							<input type='button' class="btn" name='delParamTreeBtn' id='eldParamTreeBtn' value="删&nbsp;除"/>
						</td></tr>
					   </table>
					</td>
					<td><div id="taskParamesTree"  style="width:98%;"></div></td>
				</tr>
			</table>
	     </div>
	     <div align="center">
	     	<hr/>
	     	<input type="button" id="saveTaskBut" value="保&nbsp;存"/>
	     </div>
	  </div>
   </div>
</div>

</body>
</html>