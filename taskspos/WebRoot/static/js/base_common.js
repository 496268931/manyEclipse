//格式化时间戳，输入秒
function timeToStr(timestamp){
	if(timestamp == null || timestamp == 0){
		return "";
	}
	//var d = new Date(timestamp*1000);
	//return d.toString("yyyy-MM-dd hh:mm:ss");
	return formatTime("yyyy-MM-dd hh:mm:ss",timestamp);
}

/*
 * yyyy-MM-dd hh:mm:ss
 */
function formatTime(format, timeStamp) {
	var dateObj = new Date(timeStamp * 1000);
	var o = {
		"M+" : dateObj.getMonth() + 1, // month
		"d+" : dateObj.getDate(), // day
		"w+" : dateObj.getDay(), // week 
		"h+" : dateObj.getHours(), // hour
		"m+" : dateObj.getMinutes(), // minute
		"s+" : dateObj.getSeconds(), // second
		"q+" : Math.floor((dateObj.getMonth() + 3) / 3), // quarter
		"S" : dateObj.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (dateObj.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

function getTimeSec(formatStr) {
	var d;
	if (undefined == formatStr) // 为定义时间时默认为当前时间
	{
		d = new Date();
		d = d.getTime();
	} 
	else {
		formatStr = commonFun.trim(formatStr);
		// 年月日时分秒格式的，parse能解析的格式只能是月,日,年的顺序来表示且用斜线分割（02/06/2012 10:44）
		// 传入格式可以是斜线或横线，按照年月日 时分秒顺序，格式化成能够解析的格式
		var slashStyle = formatStr.replace(/-/g, '/');
		var dateArr = slashStyle.split(" ");
		var strArr = dateArr[0].split("/");
		if (dateArr[1] != undefined) {
			var datestring = strArr[1] + "/" + strArr[2] + "/" + strArr[0] + " " + dateArr[1];
		} else { // 不含有 “时” “分”
			var datestring = strArr[1] + "/" + strArr[2] + "/" + strArr[0];
		}
		d = Date.parse(datestring);
	}
	return Math.round(d / 1000);
}


function getTaskStatus(taskstatus){
	var result;
	switch(taskstatus){
		case -1:
		  result = "等待停止";
		  break;
		case 0:
		  result = "等待启动";
		  break;
	  case 1:
	    result = "正常";
		  break;
	  case 2:
	    result = "停止";
		  break;
	  case 3:
	    result = "完成";
		  break;
	  case 4:
	    result = "排队中";
		  break;
	  case 5:
	    result = "崩溃";
		  break;
	  case 6:
	    result = "等待验证";
		  break;
	  case 7:
	    result = '挂起';
	    break;
	  default:
	    result = "未启动";
		  break;
	}
	return result;
}

