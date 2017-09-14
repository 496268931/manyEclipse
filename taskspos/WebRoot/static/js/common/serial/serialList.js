$(function() {
	$("#add").on("click", addSerial);
	$("#modify").on("click", modifySerial);
	$("#delete").on("click", deleteSerial);
});

/**
 * 新增序列号表
 * 
 * @version V1.0 2014-05-08 下午 07:54:07 星期四
 * @author ms(ms@chinazrbc.com)
 */
function addSerial() {
	openIFrame(basePath + "/SerialController/toSerialDetail.do", null,
			__resourcecode__);
}

/**
 * 修改序列号表
 * 
 * @version V1.0 2014-05-08 下午 07:54:07 星期四
 * @author ms(ms@chinazrbc.com)
 */
function modifySerial() {
	var id = getCheckedId();
	if (id) {
		openIFrame(basePath + "/SerialController/toSerialDetail.do?id=" + id,
				null, __resourcecode__);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 删除序列号表
 * 
 * @version V1.0 2014-05-08 下午 07:54:07 星期四
 * @author ms(ms@chinazrbc.com)
 */
function deleteSerial() {
	var id = getCheckedId();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url : basePath + "/SerialController/deleteSerial.do",
				type : "post",
				data : {
					id : id
				},
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						refresh();
						layer.close(delLayer);
						alert(data.message);
					} else {
						alert(data.message);
					}
				}
			});
		}
	} else {
		alert("请选择记录！");
	}
}

/**
 * 刷新列表
 * 
 * @version V1.0 2014-05-08 下午 07:54:07 星期四
 * @author ms(ms@chinazrbc.com)
 */
function refresh() {
	submitWithParam("serialListForm", "serialList");
}

/**
 * 获取选中id
 * 
 * @version V1.0 2014-05-08 下午 07:54:07 星期四
 * @author ms(ms@chinazrbc.com)
 */
function getCheckedId() {
	return $("[name='_id']:checked").val();
}