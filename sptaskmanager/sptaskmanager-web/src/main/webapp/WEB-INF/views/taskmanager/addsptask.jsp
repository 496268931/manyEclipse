<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
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
	<script type="text/javascript" src="${basePath}/static/js/createsptask/myParamPath.js"></script>
<script type="text/javascript">
	var basePath = '${pageContext.request.contextPath}';
	var paramobj = {};
</script>
</head>
<body>
	<form id="taskmanager" name="taskmanager" action="" method="post">
		<input type="hidden" id="paramid" name="paramid" value="" /> <input
			type="hidden" id="taskid" name="taskid" value="${taskid}" /> <input
			type="hidden" id="childtaskid" name="childtaskid"
			value="${childtaskid}" /> <input type="hidden" id="tagid"
			name="tagid" value="" />
	</form>
	<div id="container">
		<div id="cardcontent">
			<div id="cardleft">
				<div id="divNavgationTitle" class="cssOuter">
					<table class="cssTitle">
						<tr>
							<td class="cssTdLeft"><img
								src="${basePath}/static/imags/arrowdown.gif" id="navArrow"
								onClick="leftDivSH('divNavgation')" /></td>
							<td class="cssTdRight" onClick="leftDivSH('divNavgation')">
								我的任务</td>
						</tr>
					</table>
				</div>
				<div class="dtree" id="divNavgation" style="height: 545px;">
					<table style="width: 90%;">
						<tr>
							<td align="right"><label class="lblFont"
								onclick="newNavgationPage();">新建</label> <label class="lblFont">|</label>
								<label class="lblFont" onclick="delNavgationTree()";>删除</label>
							</td>
						</tr>
						<tr>
							<td>
								<div id="parenttochildtree"></div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="cardcenter">
				<div id="divNavgationTitle" class="cssOuter">
					<table class="cssTitle">
						<tr>
							<td class="cssTdLeft"><img
								src="${basePath}/static/imags/arrowdown.gif" id="navArrow" /></td>
							<td class="cssTdRight">参数定义</td>
						</tr>
					</table>
				</div>
				<div class="dtree" id="divtasktree">

					<table border="0" width="98%">
						<tr>
							<td width="230px">
								<table>
									<tr>
										<td>父参数名:<input type="text" name="parentParamName"
											id="parentParamName"></td>
									</tr>
									<tr>
										<td>子参数名:<input type="text" name="paramName"
											id="paramName"></td>
									</tr>
									<tr>
										<td align="center">选择参数类型？</td>
									</tr>
									<tr>
										<td align="center"><label><input name="Fruit"
												type="radio" value="5" onclick="radObjHandle()" />整数 </label> <label><input
												name="Fruit" type="radio" value="13"
												onclick="radObjHandle()" />字符串 </label> <label><input
												name="Fruit" type="radio" value="23"
												onclick="radObjHandle()" />对象</label><br /> <label><input
												name="Fruit" type="radio" value="24"
												onclick="radObjHandle()" />数组: <select id="selArrType"
												name="selArrType">
													<option value="5">整数</option>
													<option value="13">字符串</option>
													<option value="18">日期</option>
													<option value="23">对象</option>
													<option value="24">数组</option>
											</select> </label><br /> <label><input name="Fruit" type="radio"
												value="18" onclick="radObjHandle()" />日期 <input
												id="seldatetime" name="seldatetime" class="Wdate"
												type="text" readonly="readonly" style="width: 150px;"
												onclick="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
										</label></td>
									</tr>
									<tr>
										<td align="center">参数值</td>
									</tr>
									<td align="center" colspan="4">&nbsp;<textarea
											name="paramContent" id="paramContent" rows="4" cols="25">${schContent}</textarea>
									</td>
									</tr>
									<tr>
										<td style="text-align: center;"><input type='button'
											class="btn" name='addParamTreeBtn' id='addParamTreeBtn'
											value="确&nbsp;定" /> &nbsp;&nbsp;&nbsp; <input type='button'
											class="btn" name='delParamTreeBtn' id='eldParamTreeBtn'
											value="删&nbsp;除" /></td>
									</tr>
								</table>
							</td>
							<td><div id="taskParamesTree" style="width: 98%;"></div></td>
						</tr>
					</table>
				</div>
				<div>
					<table class="formtable" style="width: 95%">
						<!-- //用于定义任务本身的属性 taskPro -->
						<tr style="text-align: center;">
							<td colspan="3">任务本身属性
								<div class="divhr"></div>
							</td>
						</tr>
						<tr>
							<td class="tdleft">任务级别：</td>
							<td><select id="tp_tasklevel" name="tasklevel"
								class="shortselect">
									<option value="1">一</option>
									<option value="2">二</option>
									<option value="3">三</option>
									<option value="4">四</option>
									<option value="5">五</option>
							</select></td>
							<td class="tdtip">数字越小级别越高</td>
						</tr>

						<tr>
							<td class="tdleft">任务类型：</td>
							<td><input type="text" name="taskclassify"
								id="tp_taskclassify" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;应用于子任务： <input
								type="radio" value="true" name="taskchild" id="tp_taskchild1"
								checked="checked" /><label for="tp_taskchild1">是</label> <input
								type="radio" value="false" name="taskchild" id="tp_taskchild0" /><label
								for="tp_taskchild0">否</label></td>
						</tr>

						<tr>
							<td class="tdleft">指定MAC：</td>
							<td><input type="text" name="spcfdmac" id="tp_spcfdmac" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;应用于子任务：
								<input type="radio" value="true" name="macchild"
								id="tp_macchild1" checked="checked" /><label for="tp_macchild1">是</label>
								<input type="radio" value="false" name="macchild"
								id="tp_macchild0" /><label for="tp_macchild0">否</label></td>
							<td class="tdtip">例如：8C-7B-9D-43-50-89</td>
						</tr>


						<tr>
							<td class="tdleft">立即更新：</td>
							<td><input type="radio" value="1" name="iscommit"
								id="tp_iscommit1" checked="checked" /><label for="tp_iscommit1">是</label>
								<input type="radio" value="0" name="iscommit" id="tp_iscommit0" /><label
								for="tp_iscommit0">否</label></td>
							<td class="tdtip">每次立即更新检索引擎数据</td>
						</tr>
						<tr>
							<td class="tdleft">是否计算轨迹：</td>
							<td><input type="radio" id="tp_iscalctrend1" value="1"
								name="iscalctrend"><label for="tp_iscalctrend1">是</label>
								<input type="radio" id="tp_iscalctrend0" value="0"
								name="iscalctrend" checked="checked"><label
								for="tp_iscalctrend0">否</label></td>
							<td class="tdtip">任务结束时是否计算轨迹</td>
						</tr>
						<tr>
							<td class="tdleft">摘 要：</td>
							<td><textarea name="remarks" id="tp_remarks" rows="5"
									cols="25"></textarea></td>
							<td class="tdtip"></td>
						</tr>

						<!-- /*
						<tr>
							<td class="tdleft">网页模板：</td>
							<td><select id="tp_template" name="template">
							</select></td>
							<td class="tdtip"></td>
						</tr>
						*/ //URL -->
						<tr id="tp13_1">
							<td class="tdleft">URL生成方式：</td>
							<td><input type="radio" id="tp_urltype0" value="consts"
								name="urltype" checked="checked"><label
								for="tp_urltype0">常量赋值</label> <input type="radio"
								id="tp_urltype1" value="gen" name="urltype"><label
								for="tp_urltype1">根据规则生成(Enum, Object)</label></td>
							<td class="tdtip">URL的生成方式</td>
						</tr>
						<tr id="tp13_2">
							<td class="tdleft">URL：</td>
							<td><input type="text" style="width: 475px"
								id="tp_listweburl" /> <input type="button" id="tp_datafrom_btn"
								value="添加变量" /> <input type="button" id="tp_datafrom_clear_btn"
								value="清除" /></td>
							<td class="tdtip"></td>
						</tr>
						<!-- /*
						<tr>
							<td class="tdleft">url规则：</td>
							<td><span id="tp_listurlrule"
								style="word-break: break-all; display: inline-block;"></span></td>
							<td class="tdtip"></td>
						</tr>
						*/ -->
						<tr>
							<td class="tdleft">设置模板：</td>
							<td><input type="button" id="tp_grabsteps_btn" value="设置模板" />
							</td>
							<td class="tdtip"></td>
						</tr>
						<tr>
							<td class="tdleft">步骤：</td>
							<td><span id="tp_grabsteps"
								style="word-break: break-all; display: inline-block;"></span></td>
							<td class="tdtip"></td>
						</tr>


						<!-- //添加分词方案 -->
						<tr>
							<td class="tdleft">分词方案：</td>
							<td><input type="button" value="设置"
								onclick="popPlan2('commonTask');" id="tp_dictionaryPlan2" /></td>
							<input type="hidden" name="dictionaryPlan"
								id="tph_dictionaryPlan2" value="[[]]" />
							</td>
							<td class="tdtip">系统对每个方案的分词结果去重后保存,未配置则用默认词典。</td>
						</tr>
						<tr>
							<td class="tdleft">已添加：</td>
							<td><span class="selwordsbox" id="tp_dictionaryPlanText2">默认方案</span></td>
							</td>
							<td class="tdtip"></td>
						</tr>
						<tr>
							<td class="tdleft">任务超时：</td>
							<td><input id="tp_duration" name="duration" type="text"
								value="3600" style="width: 150px;" />秒</td>
							<td class="tdtip">完成任务的预期时间</td>
						</tr>
						<tr>
							<td class="tdleft">启动时间：</td>
							<td><input id="tp_activatetime" name="activatetime"
								class="Wdate" type="text" readonly="readonly"
								style="width: 150px;"
								onclick="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
							<td class="tdtip"></td>
						</tr>
						<tr>
							<td class="tdleft">冲突延迟：</td>
							<td><input id="tp_conflictdelay" name="conflictdelay"
								type="text" />秒</td>
							<td class="tdtip"></td>
						</tr>
						<tr>
							<td class="tdleft">数据入库：</td>
							<td><input type="radio" value="1" name="dataSave"
								id="tp_dataSave1" checked="checked" /><label for="tp_dataSave1">是</label>
								<input type="radio" value="0" name="dataSave" id="tp_dataSave0" /><label
								for="tp_dataSave0">否</label></td>
							<td class="tdtip">当前任务的抓取数据是否入库</td>
						</tr>
						<tr>
							<td class="tdleft">过滤HTML标签：</td>
							<td><input type="radio" value="1" name="filPageTag"
								id="tp_filPageTag1" checked="checked" /><label
								for="tp_filPageTag1">是</label> <input type="radio" value="0"
								name="filPageTag" id="tp_filPageTag0" /><label
								for="tp_filPageTag0">否</label></td>
							<td class="tdtip">每次立即更新检索引擎数据</td>
						</tr>
						<tr>
							<td class="tdleft">添加用户：</td>
							<td><input type="radio" value="1" name="addUser"
								id="tp_addUser1" checked="checked" /><label for="tp_addUser1">是</label>
								<input type="radio" value="0" name="addUser" id="tp_addUser0" /><label
								for="tp_addUser0">否</label></td>
							<td class="tdtip">抓取到的用户数据是否入库</td>
						</tr>

						<tr>
							<td class="tdleft">默认生成时间字段：</td>
							<td><input type="radio" value="1" name="genCreatedAt"
								id="tp_genCreatedAt1" checked="checked" /><label
								for="tp_genCreatedAt1">是</label> <input type="radio" value="0"
								name="genCreatedAt" id="tp_genCreatedAt0" /><label
								for="tp_genCreatedAt0">否</label></td>
							<td class="tdtip">如果采集的数据没有created_at字段，系统会以采集时间作为文章的创建时间</td>
						</tr>

						<tr>
							<td class="tdleft">是否派生子任务：</td>
							<td><input type="radio" value="1" name="isGenChildTask"
								id="tp_isGenChildTask1" checked="checked" /><label
								for="tp_isGenChildTask1">是</label> <input type="radio" value="0"
								name="isGenChildTask" id="tp_isGenChildTask0" /><label
								for="tp_isGenChildTask0">否</label></td>
							<td class="tdtip"></td>
						</tr>


						<!-- //过滤条件 -->
						<tr style="text-align: center;">
							<td colspan="3">抓取所需帐号
								<div class="divhr"></div>
							</td>
						</tr>
						<tr>
							<td class="tdleft">帐号来源：</td>
							<td><input type="button" value="选择源" id="tp_sourceid" /></td>
							<td class="tdtip">先指定源再选择帐号</td>
						</tr>
						<tr>
							<td class="tdleft">已添加：</td>
							<td colspan="2"><span id="tp_addedsourceid"></span><input
								type="hidden" id="tp_hdaddedsourceid" name="source" /></td>
						</tr>
						<tr>
							<td class="tdleft">抓取帐号：</td>
							<td><input type="button" id="tp_accountid" value="添加"></td>
							<td class="tdtip">对应源下可用抓取帐号,未添加帐号时需在抓取客户端输入用户名密码</td>
						</tr>
						<tr>
							<td class="tdleft">已添加：</td>
							<td colspan="2"><span id="tp_addedaccountid"></span><input
								type="hidden" id="tp_hdaddedaccountid" name="accountid" /></td>
						</tr>
						<tr>
							<td class="tdleft">使用全局帐号：</td>
							<td><input type="radio" id="tp_globalaccount1" value="1"
								name="tp_globalaccount"><label for="tp_globalaccount1">是</label>
								<input type="radio" id="tp_globalaccount0" value="0"
								name="tp_globalaccount" checked="checked"><label
								for="tp_globalaccount0">否</label></td>
						</tr>
						<tr id="tp10_8">
							<td class="tdleft">是否切换帐号：</td>
							<td><input type="radio" id="tp_isswitch1" value="1"
								name="tp_isswitch"><label for="tp_isswitch1">是</label> <input
								type="radio" id="tp_isswitch0" value="0" name="tp_isswitch"
								checked="checked"><label for="tp_isswitch0">否</label></td>
						</tr>
						<tr id="tp10_12">
							<td class="tdleft">退出登录：</td>
							<td><input type="radio" id="tp_logoutfirst1" value="1"
								name="tp_logoutfirst"><label for="tp_logoutfirst1">是</label>
								<input type="radio" id="tp_logoutfirst0" value="0"
								name="tp_logoutfirst" checked="checked"><label
								for="tp_logoutfirst0">否</label></td>
							<td class="tdtip">任务开始前退出当前登录账号</td>
						</tr>
						<tr id="tp10_9" style="display: none;">
							<td class="tdleft">帐号切换策略：</td>
							<td><input type="text" id="tp_switchpage" name="switchpage"
								value="5" class="shortinput">页/<input type="text"
								id="tp_switchtime" name="switchtime" value="0"
								class="shortinput">秒</td>
							<td class="tdtip">帐号切换频率,0秒表示不限时间</td>
						</tr>
						<!-- //过滤条件 -->
						<tr style="text-align: center;">
							<td colspan="3">过滤条件
								<div class="divhr"></div>
							</td>
						</tr>

						<tr>
							<td class="tdleft">过滤条件赋值：</td>
							<td><input type="button" id="tp_paramfilter_btn"
								value="过滤条件赋值" /></td>
							<td class="tdtip"></td>
						</tr>
						<tr>
							<td class="tdleft">过滤条件：</td>
							<td><span id="tp_paramfilter_span"
								style="word-break: break-all; display: inline-block;"></span></td>
							<td class="tdtip"></td>
						</tr>

						<!-- //派生任务规则 -->
						<tr id="tp11_0" style="text-align: center; display: none;">
							<td colspan="3">派生任务规则
								<div class="divhr"></div>
							</td>
						</tr>

						<!-- //拆分步长 -->
						<tr id="tp11_1" style="display: none;">
							<td class="tdleft">拆分步长：</td>
							<td><input id="tp_splitStep" name="tp_splitStep" type="text" />
							</td>
							<td class="tdtip"></td>
						</tr>
						<!-- /*
						<tr>
							<td class="tdleft">url生成配置：</td>
							<td><input type="text" style="width: 475px;"
								id="tp_urlconfigrule_text" /> <input type="button"
								id="tp_urlconfigrule_btn" value="添加变量" /> <input type="button"
								id="tp_urlconfigrule_clear" value="清除" /></td>
							<td class="tdtip"></td>
						</tr>
						*/ -->
						<tr id="tp11_2" style="display: none;">
							<td class="tdleft">参数传递：</td>
							<td><br /> <input id="tp_fromPath_text"
								name="tp_fromPath_text" type="text" /> <input
								id="tp_fromPath_btn" name="tp_fromPath_btn" type="button"
								value="获取参数路径" /> <br /> <input id="tp_toPath_text"
								name="tp_toPath_text" type="text" /> <input id="tp_toPath_btn"
								name="tp_toPath_btn" type="button" value="赋值参数路径" /> //目标类型
								<div id="tp_targetType_div" style="display: none;">
									<input type="radio" id="tp_targetType0" value="0"
										name="tp_targetType" checked="checked"><label
										for="tp_targetType0">目标子任务</label> <input type="radio"
										id="tp_targetType1" value="1" name="tp_targetType"><label
										for="tp_targetType1">目标父任务</label>
								</div> <br /> <input id="tp_params_add" name="tp_params_add"
								type="button" value="添加" /></td>
							<td class="tdtip"></td>
						</tr>

						<tr id="tp11_3" style="display: none;">
							<td class="tdleft">参数：</td>
							<td><span id="tp_taskGenConf_params"></span></td>
							<td class="tdtip"></td>
						</tr>

						<tr id="tp11_4" style="display: none;">
							<td class="tdleft">URL生成方式：</td>
							<td><input type="radio" id="tp_genconf_urltype0"
								value="consts" name="genconf_urltype" checked="checked"><label
								for="tp_genconf_urltype0">常量赋值</label> <input type="radio"
								id="tp_genconf_urltype1" value="gen" name="genconf_urltype"><label
								for="tp_genconf_urltype1">根据规则生成(Enum, Object)</label></td>
							<td class="tdtip">URL的生成方式</td>
						</tr>
						<tr id="tp11_5" style="display: none;">
							<td class="tdleft">URL：</td>
							<td><input type="text" style="width: 475px"
								id="tp_genconf_listweburl" /> <input type="button"
								id="tp_genconf_datafrom_btn" value="添加变量" /> <input
								type="button" id="tp_genconf_datafrom_clear_btn" value="清除" />
							</td>
							<td class="tdtip"></td>
						</tr>

						<!--  //参数路径 -->
						<tr id="tp12_0" style="text-align: center;">
							<td colspan="3">参数路径
								<div class="divhr"></div>
							</td>
						</tr>

						<tr id="tp12_1">
							<td class="tdleft">参数路径：</td>
							<td><input id="tp_paramMap_btn" name="tp_paramMap_btn"
								type="button" value="添加路径" /></td>
							<td class="tdtip"></td>
						</tr>

						<tr id="tp11_3">
							<td class="tdleft">参数：</td>
							<td><span id="tp_paramMap_params"></span></td>
							<td class="tdtip"></td>
						</tr>
					</table>
				</div>
				
				<div align="center">
					<hr />
					<input type="button" id="saveTaskBut" value="保&nbsp;存" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" id="closeTaskBut" value="关&nbsp;闭" />
				</div>
			</div>
		</div>
	</div>

</body>
</html>