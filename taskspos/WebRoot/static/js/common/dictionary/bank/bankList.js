$(function() {
	$.getScript(basePath + "/static/js/common/common_oper.js");
	$("#add").on("click", addBank);
	$("#modify").on("click", modifyBank);
	$("#delete").on("click", deleteBank);
	$("#search").on("click", search);
});

function search() {
	trimTextInput("bankListForm");
	$("#bankListForm").submit();
}

function addBank() {
	openIFrame(basePath + "/BankController/toBankDetail.do", "medium",
			__resourcecode__);
}

function modifyBank() {
	var bankId = getCheckedId();
	if (bankId) {
		openIFrame(basePath + "/BankController/toBankDetail.do?bankId="
				+ bankId, "medium", __resourcecode__);
	} else {
		alert("请选择记录！");
	}
}

function deleteBank() {
	var bankId = getCheckedId();
	if (bankId) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url : basePath + "/BankController/deleteBank.do",
				type : "post",
				data : {
					bankId : bankId
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

function refresh() {
	submitWithParam("bankListForm", "bankList");
}
