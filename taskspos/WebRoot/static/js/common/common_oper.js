/**
 *
 * @param size 尺寸 large:大号,full:铺满,medium中号 默认大号
 * @returns {{width: string, height: string, offset: string}}
 */
function computeFrameSize(size) {
    var widthRate = 0.8;
    var heightRate = 0.8;
    var offsetRate = 0.1;
    if (size == "medium") {
        widthRate = 0.6;
    } else if (size == "full") {
        widthRate = 0.95;
        heightRate = 0.9;
        offsetRate = 0.05;
    }

    var centerFrame = window;
    var baseWith = centerFrame.innerWidth || centerFrame.document.documentElement.clientWidth;
    var baseHeight = centerFrame.innerHeight || centerFrame.document.documentElement.clientHeight;
    var autoWidth = Math.ceil(baseWith * widthRate, 0);
    var autoHeight = Math.ceil(baseHeight * heightRate, 0);
    var autoOffset = Math.ceil(baseHeight * offsetRate, 0);

    return {width: autoWidth.toString(), height: autoHeight.toString(), offset: autoOffset.toString()};
}
/**
 *
 * @param url
 * @param size 尺寸 large:大号,full:铺满,medium中号 默认大号
 * @param resourcecode
 */
function openIFrame(url, size, resourcecode) {
    var e = arguments.callee.caller.arguments[0] || window.event;
    var src = e.srcElement || e.target;
    $(src).blur();

    var frameSize = computeFrameSize(size);

	var index = $.layer({
		type : 2,
		offset : [ frameSize.offset, "" ],
		title : "",
		area : [ frameSize.width, frameSize.height ],
        moveOut: false
	});
	if (resourcecode) {
		var suffix = "__resourcecode__=" + resourcecode;
		if (url.indexOf("?") == -1) {
			url += "?";
		} else {
			url += "&";
		}
		url += suffix;
	}
	layer.iframeSrc(index, url);
    $(window).resize(size, resizeIFrame);
}

function resizeIFrame(event) {
    var size = computeFrameSize(event.data);
    layer.area(layer.getFrameIndex(), {width: size.width, height: size.height, offset: size.offset});
}

function openIFrame2(url, offset,area, resourcecode) {
    var e = arguments.callee.caller.arguments[0] || window.event;
    var src = e.srcElement || e.target;
    $(src).blur();
	var index = $.layer({
		type : 2,
		title : "",
		offset : offset,
		area : area,
        moveOut: false
	});
	if (resourcecode) {
		var suffix = "__resourcecode__=" + resourcecode;
		if (url.indexOf("?") == -1) {
			url += "?";
		} else {
			url += "&";
		}
		url += suffix;
	}
	layer.iframeSrc(index, url);
}


/**
 * 填充下拉框
 * 
 * @param select
 *            待填充select(jquery对象）
 * @param items
 *            数据
 * @param defaultOpt
 *            默认option
 * @param optName
 *            option展示字段
 * @param optValue
 *            option中value属性
 * @param defaultValue
 *            默认选中的值
 */
function fillSelectByAjax(select, items, defaultOpt, optName, optValue,
		defaultValue) {
	select.empty();
	if (!items || !items.length) {
		var emptyOption = "<option value=\"\">" + defaultOpt + "</option>";
		select.append(emptyOption);
		return;
	}
	if (defaultOpt && defaultOpt !== "null") {
		var defaultOption = "<option value=\"\">" + defaultOpt + "</option>";
		select.append(defaultOption);
	}
	for ( var i in items) {
		var item = items[i];
		var selected = "";
		if (defaultValue && defaultValue == item[optValue]) {
			selected = "selected=\"selected\"";
		}
		var option = "<option value=\"" + item[optValue] + "\"" + selected
				+ ">" + item[optName] + "</option>";
		select.append(option);
	}
}
/**
 * 填充城市下拉框
 * 
 * @param provinceId
 *            省份id
 * @param citySelect
 *            城市select,jquery对象
 */
function fillCitySelectByProvince(provinceId, citySelect, selectedCity) {
	$.ajax({
		url : basePath + "/CityController/getCityListByProvince.do",
		type : "post",
		data : {
			provinceId : provinceId
		},
		dataType : "json",
		success : function(data) {
			fillSelectByAjax(citySelect, data, "", "cityName", "cityId",
					selectedCity);
		}
	});
}
/**
 * 根据城市逆推省
 * 
 * @param citySelect
 * @param provinceSelect
 */
function setProvinceByCity(citySelect, provinceSelect) {
	var provinceId = citySelect.find(":selected").attr("provinceId");
	provinceSelect.val(provinceId);
	fillCitySelectByProvince(provinceId, citySelect, citySelect.val());
}

/**
 * 保留form信息提交
 * 
 * @param formId
 * @param tableId
 */
function submitWithParam(formId, tableId) {
	var parameterString = $.jmesa.createParameterStringForLimit(tableId);
	var path = $("#" + formId).attr("action") + "?date=" + new Date().getTime();
	if (parameterString) {
		path += "&" + parameterString;
	}
	$("#" + formId).attr("action", path);
	$("#" + formId).submit();
}
/**
 * 提交表单前校验
 * 
 * @param formId
 *            表单id
 * @param submitFun
 *            提交方法
 */
function submitWithValidator(formId, submitFun) {
	$("#" + formId).validator({
		valid : submitFun
	});
	$("#" + formId).trigger("submit");
}

/**
 * 去除文本框前后空格，查询时常用
 * 
 * @param parentId
 *            包含文本输入框父元素id
 */
function trimTextInput(parentId) {
	$("#" + parentId).find("input[type='text']").each(function() {
		$(this).val($.trim($(this).val()));
	});
}

/**
 * 返回会员id，并执行回调，回调函数必须接受会员id
 */
function handleCallbackFn() {
	var id = getCheckedId();
	if (id) {
		var callbackFn = $("#callbackFn").val();
		if (callbackFn) {
			opener[callbackFn].call(opener, id);
			window.close();
		} else {
			alert("回调失败，没有指定回调函数！");
		}
	} else {
		alert("请选择记录！");
	}
}

var ChooseUrlEnum = {
	MEMBER : new Url(
			"/MemberController/searchMemberList.do?isChoose=true&callbackFn=",
			"选择会员"),
	EMPLOYEE : new Url(
			"/EmployeeController/getAllEmployee.do?isChoose=true&callbackFn=",
			"选择员工"),
	TZGW : new Url(
			"/EmployeeController/getAllEmployee.do?isChoose=true&roleFlag=tzgw&callbackFn=",
			"选择投资顾问"),
	LSZJ : new Url(
			"/EmployeeController/getAllEmployee.do?isChoose=true&roleFlag=lszj&callbackFn=",
			"选择隶属总监"),
	LEND : new Url(
			"/LendController/searchLendList.do?isChoose=true&callbackFn=",
			"选择理财人"),
	LEND_PRODUCT : new Url(
			"/LendProductController/searchLendProductList.do?isChoose=true&callbackFn=",
			"选择理财产品"),
	LEND_ACCOUNT : new Url(
			"/LendAccountController/searchLendAccountList.do?isChoose=true&callbackFn=",
			"选择理财人帐户信息"),
	LEND_AFFILIATION : new Url(
			"/LendAffiliationController/searchLendAffiliationList.do?isChoose=true&callbackFn=",
			"选择理财人帐户信息"),
	LEND_CONTRACT : new Url(
			"/LendContractController/searchLendContractList.do?isChoose=true&callbackFn=",
			"选择理财人帐户信息"),
    TITLE : new Url("/TitleController/searchTitleList.do?isChoose=true&callbackFn=",
    		"选择职位等级"),
    DEPTS : new Url("/DepartmentController/getAllDepartment.do?isChoose=true&callbackFn=",
    		"选择个贷职场")
};
/**
 * 进入选择页面
 * 
 * @param callbackFn
 *            回掉函数名称
 * @param chooseUrl
 *            选择列表url
 */
function chooseSomething(callbackFn, chooseUrl) {
	window.open(basePath + chooseUrl.url + callbackFn);
}

/**
 * 详情页url常量
 * 
 * @returns {ViewUrlEnum}
 * @constructor
 */
var ViewUrlEnum = {
	EMPLOYEE : new Url("/EmployeeController/viewEmployee.do?id=", "员工"),
	MEMBER : new Url("/MemberController/toMemberDetail.do?isView=true&id=", "会员"),
    CUSTOMER : new Url("/CustomerController/toCustomerDetail.do?isView=true&id=", "客户"),
	MEMBER_CARD_INVENTORY : new Url(
			"/MemberCardInventoryController/toMemberCardInventoryDetail.do?isView=true&id=",
			"会员卡库存"),
	MEMBER_CARD_TYPE : new Url(
			"/MemberCardTypeController/toMemberCardTypeDetail.do?isView=true&id=",
			"会员卡类型"),
	CARD_ORDER : new Url(
			"/MemberCardOrderController/toDetail.do?isView=true&operType=2&id=",
			"会员卡订单", {
				area : {
					width : '1200px',
					height : '750px'
				}
			}),
	DISTRICT : new Url(
			"/DistrictController/toDistrictDetail.do?isView=true&districtId=",
			"行政区域"),
	CITY : new Url("/CityController/toCityDetail.do?isView=true&id=", "城市"),
	BANK : new Url("/BankController/toBankDetail.do?isView=true&bankId=", "银行"),
	PROVINCE : new Url(
			"/ProvinceController/toProvinceDetail.do?isView=true&provinceId=",
			"省份")

};

function Url(url, desc, param) {
	this.url = url;
	this.desc = desc;
	this.param = param;
}

/**
 * 查看详情
 * 
 * @param id
 *            详情id
 * @param viewUrl
 *            详情url, ViewUrlEnum().someDetail
 * @see ViewUrlEnum
 * @see Url
 */
function viewDetail(id, viewUrl) {
	if (id && viewUrl && viewUrl instanceof Url) {
		var url = basePath + viewUrl.url + id;
		openIFrame(url, null, __resourcecode__);
	} else {
		alert("请传入有效参数");
	}
}

function clearById(id) {
	var $element = $("#" + id);
	if (!$element) {
		return;
	}
	$element.val("");
}
/**
 * 局部刷新
 * 
 * @param id
 * @param url
 */
function refreshPart(id, url) {
    if(url.indexOf("?")>-1){
        url = url+"&random="+randomPara;
    }else{
        url = url +"?random="+randomPara;
    }
	$("#" + id + "_content").load(basePath + url);
}

function loadPart(id, url) {
	$("#" + id).load(basePath + url);
}

/**
 * 关闭遮罩层
 */
function closePage() {
	parent.layer.close(parent.layer.getFrameIndex());
}

/**
 * 全选js方法
 * 
 * @param nameId
 * @param checkValue
 */
function checkAllByName(nameId, checkValue) {
	var chkBoxObject = document.getElementsByName(nameId);
	if (chkBoxObject != undefined) {
		for (var i = 0; i < chkBoxObject.length; i++) {
			chkBoxObject[i].checked = checkValue;
		}
	}
}

/**
 * 获取选中的ID
 * 
 * @returns
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}

function getAllRadioId(nameId, id) {
	var radioValue = '';
	var chkBoxObject = document.getElementsByName(nameId);
	if (chkBoxObject != undefined) {
		for (var i = 0; i < chkBoxObject.length; i++) {
			radioValue += chkBoxObject[i].value.split(",")[1] + ",";
		}
		radioValue += "0";
	}
	$("#" + id).val(radioValue);
}

$(document).keydown(function(e){
    var target = e.target ;
    var tag = e.target.tagName.toUpperCase();
    if(e.keyCode == 8){
     if((tag == 'INPUT' && !$(target).attr("readonly"))||(tag == 'TEXTAREA' && !$(target).attr("readonly"))){
      if((target.type.toUpperCase() == "RADIO") || (target.type.toUpperCase() == "CHECKBOX")){
       return false ;
      }else{
       return true ; 
      }
     }else{
      return false ;
     }
    }
});


$("input[readonly!='readonly']:not(:hidden,:disabled)").first().trigger('focus');