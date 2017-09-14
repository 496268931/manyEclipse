if (!String.prototype.trim) {
	String.prototype.trim = function () {
		return this.replace(/^\s+|\s+$/g, '');
	};
}


$(document).ready(function () {
	if ($("#1_info")) {
		$("#1_info").removeClass().addClass("active");
		$("#1_content").show();
		divLayout();
	}

});

function divLayout() {
	var dw = $('.active').width();
	var childs = $('.main_nav').children().not(".active");
	var zw = $('.main_nav').width() - dw;
	var len = childs.length;
	if (len * dw > zw) {
		var nw = zw / len;
		childs.each(function () {
			$(this).css({"width": nw + 'px'});
			$(this).click(function () {
				$(this).css({"width": dw + 'px'});
				$(this).siblings().css({"width": nw + 'px'});
			});
		});
	}
	$('.active').click(function () {
		$(this).css({"width": dw + 'px'});
		$(this).siblings().css({"width": nw + 'px'});
	});
}

function showDiv(id, url) {
	$("[id$='_content']").hide();//所有以_content值结尾的属性为id的元素隐藏
	$("#" + id + "_content").show();
	$("[id$='_info']").removeClass().addClass("nav");//所有以_subSystem值结尾的属性为id的元素隐藏
	$("#" + id + "_info").removeClass().addClass("active");
//	if ($("#" + id + "_content").text().trim() == '') {
		if (url != undefined) {
			refreshPart(id, url);
		}
//	}
}

function showDiv1(id, url) {
	$("[id$='_content']").hide();//所有以_content值结尾的属性为id的元素隐藏
	$("#" + id + "_content").show();
	$("[id$='_info']").removeClass().addClass("nav");//所有以_subSystem值结尾的属性为id的元素隐藏
	$("#" + id + "_info").removeClass().addClass("active");
	if (url != undefined) {
		refreshPart(id, url);
	}
}


/*function showDivForAudit(id, url, callBackFn) {
	$("[id$='_content']").hide();//所有以_content值结尾的属性为id的元素隐藏
	$("#" + id + "_content").show();
	$("[id$='_info']").removeClass().addClass("nav");//所有以_subSystem值结尾的属性为id的元素隐藏
	$("#" + id + "_info").removeClass().addClass("active");
//	if ($("#" + id + "_content").text().trim() == '') {
		callBackFn.call(this);
//	}
}*/

