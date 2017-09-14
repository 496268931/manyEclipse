$(function () {
	$("#save").on("click", save);
	$("#cancel").on("click", closePage);
	var initLeftList = eval('document.forms[0].chosen');
	if (initLeftList.options.length > 1 && initLeftList.options[0].value == 'temp') {
		initLeftList.options[0] = null;
	}
});

function commonAll(current, toList, from, to) {
	txt = current.text;
	val = current.value;
	var opt = new Option(txt, val);
	toList.options[toList.length] = opt;
}

function selectByDouble(from, to) {
	var opt;
	opt = $("#" + from).find("option:selected")[0];//.text();
	var fromList = eval('document.forms[0].' + from);
	var toList = eval('document.forms[0].' + to);
	if (toList.options.length > 0 && toList.options[0].value == 'temp') {
		toList.options.length = 0;
	}
	if (opt.value == 'temp') {
		alert('不能选择这个组件!');
		return;
	}
	commonAll(opt, toList, from, to);
	fromList.options[opt.index] = null;
}


function copyToList(from, to) {
	var fromList = eval('document.forms[0].' + from);
	var toList = eval('document.forms[0].' + to);
	if (toList.options.length > 0 && toList.options[0].value == 'temp') {
		toList.options.length = 0;
	}
	var sel = false;
	for (var i = 0; i < fromList.options.length; i++) {
		var current = fromList.options[i];
		if (current.selected) {
			sel = true;
			if (current.value == 'temp') {
				alert('不能选择这个组件!');
				return;
			}
			commonAll(current, toList, from, to);
			fromList.options[i] = null;
			i--;
		}
	}
	if (!sel) alert('你还没有选择任何组件!');
}

function allSelect(from, to) {
	var fromList = eval('document.forms[0].' + from);
	var toList = eval('document.forms[0].' + to);
	//如果chsen列表框为0或第一个选项值为'temp'
	if (toList.options.length > 0 && toList.options[0].value == 'temp') {
		toList.options.length = 0;
	}

	for (var i = 0; i < fromList.length; i++) {
		fromList.options[i].selected = true;
	}
	var sel = false;

	for (var i = 0; i < fromList.options.length; i++) {
		var current = fromList.options[i];
		current.ondblclick = function () {
			removeByDbClick(to, from, this);
		};
		if (current.selected) {
			sel = true;
			if (current.value == 'temp') {
				alert('不能选择这个组件!');
				return;
			}
			commonAll(current, toList, from, to);
			fromList.options[i] = null;
			i--;
		}
	}
	if (!sel) alert('你还没有选择任何组件!');
}

function validateNum(selectLength) {
	var columnNum = $('#columnNum').val();
	var rowNum = $('#rowNum').val();
	var maxNum = parseInt(columnNum) * parseInt(rowNum);
	if (selectLength > maxNum) {
		var errorMessage = "行数：" + rowNum + ",列数：" + columnNum + ",最多允许组件数量：" + maxNum;
		return {success: false, message: errorMessage};
	}
	return {success: true};

}

function save() {
	var leftList = eval('document.forms[0].chosen');
	if ((leftList.options.length > 0 && leftList.options[0].value == 'temp') || leftList.options.length == 0) {
		alert("你还没有选择任何组件！");
		return false;
	}
	var validateResult = validateNum(leftList.options.length);
	if (!validateResult.success) {
		alert(validateResult.message);
		return false;
	}
	$.ajax({
		async   : false,
		url     : basePath + "/ProjectController/deleteProjectHotRegion.do",
		type    : "post",
		data    : $("#hmsProjectHotRegionForm").serialize(),
		dataType: "json",
		success : function (data) {
		}
	});

	var ids = '';
	for (var i = 0; i < leftList.options.length; i++) {
		var current = leftList.options[i];
		id = current.value;
		ids = ids + "," + id;
	}

	ids = ids.substring(1, ids.length);
	var portalId = $("#portalId").val();
	$.ajax({
		async   : false,
		url     : basePath + "/PortalController/configurePortletPostions.do?r=" + new Date().getTime(),
		type    : "post",
		data    : {portalId: portalId, ids: ids},
		dataType: "json",
		success : function (data) {
			if (data.status == 1) {
				alert(data.message);
				parent.refresh();
			} else {
				alert(data.message);
			}
		}
	});
}
/**
 * 关闭遮罩层
 */
function closePage() {
	if (parent.layer.getFrameIndex != undefined) {
		parent.layer.close(parent.layer.getFrameIndex());
	} else {
		self.close();
	}
}
