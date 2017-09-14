$(function() {
    computeMainDivPosition();
    $(".holder").jPages({
        containerID: "itemContainer",
        previous: "上一页",
        next: "下一页",
        perPage: 10,
        startRange: 3,
        endRange: 3,
        delay: 0,
        callback: function (pages, items) {
            $("#legend").html(" 第" + (items.count == 0 ? 0 : items.range.start) + "张到第" + items.range.end + "张，共" + items.count + "张");
        }
    });

    $(".colorbox_group").colorbox({
        rel: "colorbox_group",
        current: "{current} of {total}",
        imgError: "图片加载失败",
        retinaImage: true
    });

    $(window).resize(computeMainDivPosition);

    $("#allSelect").click(function () {
        currentPageCheckBoxes().attr("checked", true);
    });
    $("#reverseSelect").click(function () {
        currentPageCheckBoxes().each(function() {
            if ($(this).attr("checked")) {
                $(this).attr("checked", false);
            } else {
                $(this).attr("checked", true);
            }
        });
    });
    $("#deleteBtn").click(function () {
        var deleteIds = [];
        currentPageCheckBoxes().each(function() {
            if ($(this).is(":checked")) {
                deleteIds.push($(this).val());
            }
        });
        if (deleteIds.length <= 0) {
            alert("请选择图片");
            return;
        }
        var delLayer = layer.load("正在删除...");
        $.post(basePath + "/ImageManageController/batchDelete.do",
            {
                from: $("#from").val(),
                deleteIds: deleteIds.join()
            }
            , function (data) {
                if (data.status == 1) {
                    refresh();
                    layer.close(delLayer);
                    alert(data.message);
                } else {
                    alert(data.message);
                }
            }
            , "json");

    });
});

function computeMainDivPosition() {
    var baseWith = window.innerWidth || window.document.documentElement.clientWidth;
    var mainDiv = document.getElementById("main");
    var reg = /(\d+)px/;
    var mainDivWidth = reg.test(mainDiv.style.width) ? RegExp.$1 : baseWith;
    var marginLeft = baseWith > mainDivWidth ? Math.ceil((baseWith - mainDivWidth) / 2, 0) + "px" : "auto";
    mainDiv.style.marginLeft = marginLeft;
}
/**
 * 当前页中checkboxes
 * @returns {*|jQuery}
 */
function currentPageCheckBoxes() {
    return $("#itemContainer").find("li:visible").find(":checkbox");
}

function refresh() {
    $("form").submit();
}