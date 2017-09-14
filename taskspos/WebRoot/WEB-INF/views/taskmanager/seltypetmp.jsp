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
<script type="text/javascript" src="${basePath}/static/js/dtree.js"></script>
<script type="text/javascript" src="${basePath}/static/js/seltypetmp.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/common.js"></script>

<script type="text/javascript">
var basePath = '${pageContext.request.contextPath}';
</script>
</head>
<body>
<form id="taskmanager" name="taskmanager" action="" method="post">
	<input type="hidden" id="myclassifyid" name="myclassifyid" value=""/>
	<input type="hidden" id="sptmpid" name="sptmpid" value=""/>
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
						我的分类
					</td>					
				</tr>				
			</table>
	     </div> 
	     <div class="dtree" id="divNavgation" style="height:545px;">
			<table style="width:90%;">
		 		<tr><td align="right">
			 		<label class="lblFont" onclick="showEditPage('Navgation', 'navigationEdit.jsp');">修改</label>
					<label class="lblFont">|</label>
					<label class="lblFont" onclick="delNavgationTree()";>删除</label>
	     		</td></tr>
	     		<tr><td>
					<div id="divNavgationTree"></div>
				</td></tr>	
			</table>	
	     </div>
 	 </div>
 	 <div id="cardcenter">
 	 	<div id="searchTop">
		    <hr size="1" width="98%"/>
			 <div> 
				&nbsp;&nbsp;&nbsp;爬虫配置名称： <input type="text" name="spi_name" id="spi_name" />
				模板类型： <select name="spi_taskpagestyletype" id="spi_taskpagestyletype">
					<option value=''>请选择</option>
					<option value="1">文章列表</option>
			    	<option value="2">文章详情</option>
			    	<option value="3">用户详情</option>
			    	<option value="4">文章列表、文章详情、用户详情综合</option>
				</select>
                <input type="button" name="spi_search" value="查询" id="spi_search" />
                <hr size="1" width="98%"/>
             </div>
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
		  				   <option value="5">5</option>
		 				   <option value="10">10</option>
		 				   <option value="20">20</option>
						</select>项
			       </td>
			       <td  width="460px" align="right" >&nbsp;
			       <input type="button" id="saveBtn" class="btn"   value="新建爬虫模板" />&nbsp;&nbsp;&nbsp;&nbsp;</td >
	             </tr>
	         </table>
             <table id="spiderconfiginfo" width="98%" border="0" cellspacing="0" cellpadding="0" class="list">
                <thead>
	                <tr>
	                    <th width="4%" scope="col">选择</th>
	                    <th width="15%" scope="col">名称</th>
	                    <th width="30%" scope="col">内容</th>
	                    <th scope="col">url生成配置</th>
	                    <th scope="col">模板类型</th>
	                    <th scope="col">分析抓取url正则表达式</th>
	                    <th scope="col">详情url正则表达式</th>
	                    <th width="160px" scope="col">操作</th>
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