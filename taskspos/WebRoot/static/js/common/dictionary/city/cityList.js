$(function() {
	$("#add").on("click", addCity);
	$("#modify").on("click", modifyCity);
	$("#delete").on("click", deleteCity);
	$("#search").on("click", search);
});

function search() {
	trimTextInput("cityListForm");
	$("#cityListForm").submit();
}

/**
 * 新增城市(二级城市)
 * 
 * @version V1.0 2014-05-07 上午 11:44:13 星期三
 * @author ff(ff@chinazrbc.com)
 */
function addCity() {
	openIFrame(basePath + "/CityController/toCityDetail.do", "medium",
			__resourcecode__);
}

/**
 * 修改城市(二级城市)
 * 
 * @version V1.0 2014-05-07 上午 11:44:13 星期三
 * @author ff(ff@chinazrbc.com)
 */
function modifyCity() {
	var id = getCheckedId();
	if (id) {
		openIFrame(basePath + "/CityController/toCityDetail.do?id=" + id, "medium",
				__resourcecode__);
	} else {
		alert("请选择记录！");
	}
}

/**
 * 删除城市(二级城市)
 * 
 * @version V1.0 2014-05-07 上午 11:44:13 星期三
 * @author ff(ff@chinazrbc.com)
 */
function deleteCity() {
	var id = getCheckedId();
	if (id) {
		if (confirm("是否确认删除？")) {
			var delLayer = layer.load("正在删除...");
			$.ajax({
				url : basePath + "/CityController/deleteCity.do",
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
 * @version V1.0 2014-05-07 上午 11:44:13 星期三
 * @author ff(ff@chinazrbc.com)
 */
function refresh() {
	submitWithParam("cityListForm", "cityList");
}
