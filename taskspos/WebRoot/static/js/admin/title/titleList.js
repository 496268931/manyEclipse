$(function(){
    $("#add").on("click", addTitle);
    $("#modify").on("click", modifyTitle);
    $("#delete").on("click", deleteTitle);
    $("#view").on("click", viewTitle);
    $("#search").on("click", search);
    $("#choose").on("click", handleCallbackFn);
    $("#cancel").on("click", closeWindow);
});

function closeWindow(){
	window.close();
}

function search() {
    trimTextInput("titleListForm");
    $("#titleListForm").submit();
}
/**
 * 新增职位
 *
 * @version V1.0  2014-08-08 上午 10:44:14 星期五
 * @author ff(ff@chinazrbc.com)
 */
function addTitle() {
    openIFrame(basePath + "/TitleController/toTitleDetail.do" + "?__resourcecode__="+rc);
}

/**
 * 修改职位
 *
 * @version V1.0  2014-08-08 上午 10:44:14 星期五
 * @author ff(ff@chinazrbc.com)
 */
function modifyTitle() {
    var id = getCheckedId();
    if (id) {
        openIFrame(basePath + "/TitleController/toTitleDetail.do?id=" + id + "&__resourcecode__="+rc);
    } else {
        alert("请选择记录！");
    }
}

/**
 * 查看客户
 */
function viewTitle() {
    var id;
    var param = arguments[0];
    if ($.type(param) === "object") {
        id = getCheckedId();
    } else {
        id = param;
    }
    if (id) {
        openIFrame(basePath + "/TitleController/toTitleDetail.do?id=" + id + "&isView=true" + "&__resourcecode__="+rc);
    } else {
        alert("请选择记录！");
    }
}

/**
 * 删除职位
 *
 * @version V1.0  2014-08-08 上午 10:44:14 星期五
 * @author ff(ff@chinazrbc.com)
 */
function deleteTitle() {
    var id = getCheckedId();
    var now = new Date().getTime();
    if (id) {
        if (confirm("是否确认删除？")) {
            var delLayer = layer.load("正在删除...");
            $.ajax({
                url: basePath + "/TitleController/deleteTitle.do?r=" + now,
                type: "post",
                data: {id: id},
                dataType: "json",
                success: function(data) {
                    if (data.status == 1) {
                        refresh();
                        alert(data.message);
                    } else {
                        alert(data.message);
                    }
                    layer.close(delLayer);
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
 * @version V1.0  2014-08-08 上午 10:44:14 星期五
 * @author ff(ff@chinazrbc.com)
 */
function refresh() {
    submitWithParam("titleListForm", "titleList");
}

/**
 * 获取选中id
 *
 * @version V1.0  2014-08-08 上午 10:44:14 星期五
 * @author ff(ff@chinazrbc.com)
 */
function getCheckedId() {
    return $("[name='_id']:checked").val();
}