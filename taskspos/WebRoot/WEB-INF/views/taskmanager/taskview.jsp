<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务管理</title>
<link rel="stylesheet" href="${basePath}/static/css/taskcommon.css" type="text/css" />
<script type="text/javascript" src="${basePath}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${basePath}/static/js/json2.js"></script>
<script type="text/javascript" src="${basePath}/static/js/base_common.js"></script>
<script type="text/javascript" src="${basePath}/static/js/config.js"></script>
<script type="text/javascript" src="${basePath}/static/js/taskview.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/inputValidate.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/common.js"></script>
<script type="text/javascript" src="${basePath}/static/js/dtree.js"></script>

</head>
<script type="text/javascript">
var basePath = '${pageContext.request.contextPath}';
</script>

<body>
<form id="taskmanager" name="taskmanager" action=""></form>
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
						我的分类
					</td>					
				</tr>				
			</table>
	     </div> 
	     <div class="dtree" id="divNavgation" style="height:550px;">
			<table style="width:90%;">
	     		<tr><td>
					<div id="divNavgationTree"></div>
				</td></tr>	
			</table>	
	     </div>
 	 </div>
 	 <div id="cardcenter">
		 <div id="searchTop">
		    <hr size="1" width="98%"/>
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
			    <tr>
				    <td style="padding:5px 0px;align:left">&nbsp;&nbsp;任务类型：
					    <select id="seltask" name="task">
					    	<option value="">任务类型</option>
					    	<option value="20">通用任务</option>
					    </select>
				    </td>
				    <td>任务内容类型：
				    <select id="seltaskpagestyletype" name="taskpagestyletype">
				    	<option value="">任务内容类型</option>
				    	<option value="1">文章列表</option>
				    	<option value="2">文章详情</option>
				    	<option value="3">用户详情</option>
				    	<option value="4">文章列表、文章详情、用户详情综合</option>
				    </select></td>
				     <td>任务级别：
				     <select id="seltasklevel" name="tasklevel">
				        <option value="">类型级别</option>
				        <option value="1">一</option>
				    	<option value="2">二</option>
				    	<option value="3">三</option>
				    	<option value="4">四</option>
				    	<option value="5">五</option>
				    </select></td>
				    <td>&nbsp;&nbsp;选择状态：
				        <select id="seltaskstatus" name="taskstatus">
				         <option value="">选择状态</option>
					 	 <option value="1">正常</option>
						 <option value="2">停止</option>
						 <option value="4">排队中</option>
						 <option value="7">挂起</option>
						 <option value="5">崩溃</option>
						 <option value="0">等待启动</option>
						 <option value="6">等待验证</option>
						 <option value="-1">等待停止</option>
						 <option value="8">异常任务</option>
					    </select>
				    </td>
				</tr>
				<tr>
				   <td>&nbsp;&nbsp;选择机器：
			   		  <select id="selmachine" name="machine">
				    	<option value="">选择机器</option>
				      </select>
				   </td>
				   <td colspan="2">&nbsp;&nbsp;ID:&nbsp;&nbsp;<input type="text" size="15" name="startID"/>-<input type="text" size="15" name="endID"/></td>
				   <td rowspan="2">&nbsp;&nbsp;<input type="button" id="schTaskBtn" value="查  询"/></td>
				 </tr>
			</table>
			<hr size="1" width="98%"/>
		 </div>
		 <div id="viewTop">
			<table width=100% border="0" cellpadding="0" cellspacing="0" >
				<tr class="titlebg">
				    <td align="center" style="width:100px; height:17px;">
				       	命中数<span id="spanHitCount" style="color: red;">0</span>
					</td>
					<td align="center" style="width:170px; height: 17px;">
					    &nbsp;<span class="pagefont">当前页</span> 
			               <span id="pageNum" style="color: blue;">0</span>
			               /
			               <span id="pageCount" style="color: red;">0</span>&nbsp;&nbsp;
			        </td>
			        <td align="center" style="width:400px; height: 17px;">
			              &nbsp;
						<img id="fp" src="${basePath}/static/imags/firstpage.gif" alt="首  页"/>                        
				              &nbsp;
						<img id="pp" src="${basePath}/static/imags/prepage.gif" alt="上一页"/> 
				              &nbsp;
						<img id="np" src="${basePath}/static/imags/nextpage.gif" alt="下一页"/>                             
				              &nbsp;
						<img id="lp" src="${basePath}/static/imags/lastpage.gif" alt="尾  页"/>&nbsp;                             
				                                        转到&nbsp;<input type="text" id="txtPageNum" size="4" value="0">页
						<img src="${basePath}/static/imags/gopage.gif" align="absbottom" ID="btnGo" art="转到"/> 
						&nbsp;每页
		                <select id="vp" name="pagesize" onchange="changesel('vp')">
		  				   <option value="10">10</option>
		 				   <option value="20">20</option>
		 				   <option value="30">30</option>
						</select>项
			       </td>
			       <td height="17" align="right" >
			       	<input type="button" id="saveBtn" class="btn"   value="新增数据迁移任务" />&nbsp;
			    	<!-- <input type="button" id="schBtn" class="btn"  value="新增分析任务" />&nbsp; -->
					<input type="button" id="newSpBtn" class="btn"   value="新增抓取任务" />&nbsp;
					<input type="button" id="newTaskBtn" class="btn"   value="配置抓取任务" />&nbsp;&nbsp;
			       </td>
			    </tr>
			</table>
			<table width="98%" border="0" cellspacing="0" cellpadding="0" class="list" id="mytbl">
			  <thead>
				<tr>
			        <th scope="col" style="width: 35px"><span>选择</span></th>
					<th scope="col" style="width: 35px"><span name="sort" target="id" style="display:inline-block;cursor:pointer">ID<span class="ui-icon ui-icon-triangle-1-n" style="display:inline-block;"></span></span></th>
					<th scope="col" style="width: 70px">类型</th>
					<th scope="col" style="width: 70px">内容类型</th>
					<th scope="col" style="width: 30px"><span name="sort" _target="tasklevel" style="display:inline-block;cursor:pointer">级别<span class="ui-icon ui-icon-carat-2-n-s" style="display:inline-block"></span></span></th>
					<th scope="col" style="width: 60px">状态</th>
					<th scope="col" style="width: 50px"><span name="sort" _target="datastatus" style="display:inline-block;cursor:pointer">数据<span class="ui-icon ui-icon-carat-2-n-s" style="display:inline-block"></span></span></th>
					<th scope="col" style="width: 90px"><span name="sort" _target="activatetime" style="display:inline-block;cursor:pointer">启动时间<span class="ui-icon ui-icon-carat-2-n-s" style="display:inline-block"></span></span></th>
					<th scope="col" style="width: 90px"><span name="sort" _target="starttime" style="display:inline-block;cursor:pointer">开始时间<span class="ui-icon ui-icon-carat-2-n-s" style="display:inline-block"></span></span></th>
					<th scope="col" style="width: 40px"><span name="sort" _target="tenantid" style="display:inline-block;cursor:pointer">租户<span class="ui-icon ui-icon-carat-2-n-s" style="display:inline-block"></span></span></th>
					<th scope="col" style="width: 55px"><span name="sort" _target="userid" style="display:inline-block;cursor:pointer">用户名<span class="ui-icon ui-icon-carat-2-n-s" style="display:inline-block"></span></span></th>
					<th scope="col" style="width: 60px">当前机器</th>
					<th scope="col">摘要</th>
					<th scope="col" style="width: 200px">操作</th>
				</tr>
			  </thead>
			  <tbody>
			  </tbody>
			<tfoot>
			    <tr>
			        <th colspan='14' align='left'><div style='text-align:left;' id='eventalertdeletediv'></div></th>
			    </tr>
			</tfoot>
			</table>
		 </div>
	   </div>
   </div>
</div>
</body>
</html>