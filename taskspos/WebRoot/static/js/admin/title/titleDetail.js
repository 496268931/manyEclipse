$(function(){
    $("#save").on("click", saveOrUpdateTitle);
    $("#cancel").on("click", closePage);

    // 表单校验
    $("#titleForm").validator({
        stopOnError: true,
        timely: true,
        focusCleanup: true,
        fields: {
            "#level": "职位等级:required",
            "#provinceId": "省份:required",
            "#titleName": "职位名称:required;specialchar;length[~20];remote[" + basePath + "/TitleController/checkTitle.do, id]"
        }
    });
});

/**
 * 保存或更新职位
 *
 * @version V1.0  2014-08-08 上午 10:44:14 星期五
 * @author ff(ff@chinazrbc.com)
 */
function saveOrUpdateTitle() {
    submitWithValidator("titleForm", function(){
        $.ajax({
            url: basePath + "/TitleController/saveOrUpdateTitle.do?r=" + new Date().getTime(),
            type: "post",
            data: $("#titleForm").serialize(),
            dataType: "json",
            success: function(data) {
                if (data.status == 1) {
                    alert(data.message);
                    parent.refresh();
                } else {
                    alert(data.message);
                }
            }
        });
    });
}

/**
 * 关闭遮罩层
 */
function closePage() {
    parent.layer.close(parent.layer.getFrameIndex());
}