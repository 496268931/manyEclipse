//-- Javascript对象扩展--开始-//
/**
 * 去掉开头、结尾的空格
 *
 * @return {}
 */
String.prototype.trim = function() {
    return this.replace(/(^\s+)|\s+$/g, "");
};

/**
 * 转换字符串为json对象
 */
String.prototype.toJson = function() {
    return eval('(' + this + ')');
};

String.prototype.endsWithIgnoreCase = function(str) {
    return (this.toUpperCase().match(str.toUpperCase() + "$") == str.toUpperCase()) ||
    (this.toLowerCase().match(str.toLowerCase() + "$") == str.toLowerCase());
}

function isNull(v){
	return v === null || v === "undefined" || v === "null" || v === "";
}

function isNotNull(v){
	return !isNull(v);
}

/**
 * 输出2010-02-05格式的日期字符串
 *
 * @return {}
 */
Date.prototype.toDateStr = function() {
    return ($.common.browser.isMozila() || $.common.browser.isChrome() ? (this.getYear() + 1900) : this.getYear()) + "-" +
    (this.getMonth() < 10 ? "0" + this.getMonth() : this.getMonth()) +
    "-" +
    (this.getDate() < 10 ? "0" + this.getDate() : this.getDate());
};

/**
 * 日期格式化
 * @param {Object} format
 */
Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}


/**
 * 将字符串格式的日期转换为日期类型对象
 * @param {Object} strDate
 */
Date.toDate = function(strDate) {
    var strDs = strDate.split('-');
    var year = parseInt(strDs[0]);
    var month = parseInt(strDs[1]);
    var date = parseInt(strDs[2]);
    return new Date(year, month, date);
};

/**
 * 通过当前时间计算当前周数
 */
Date.prototype.getWeekNumber = function() {
    var d = new Date(this.getFullYear(), this.getMonth(), this.getDate(), 0, 0, 0);
    var DoW = d.getDay();
    d.setDate(d.getDate() - (DoW + 6) % 7 + 3); // Nearest Thu
    var ms = d.valueOf(); // GMT
    d.setMonth(0);
    d.setDate(4); // Thu in Week 1
    return Math.round((ms - d.valueOf()) / (7 * 864e5)) + 1;
}


//+---------------------------------------------------
//| 日期计算
//+---------------------------------------------------
Date.prototype.DateAdd = function(strInterval, Number) {
    var dtTmp = this;
    switch (strInterval) {
        case 's':
            return new Date(Date.parse(dtTmp) + (1000 * Number));
        case 'n':
            return new Date(Date.parse(dtTmp) + (60000 * Number));
        case 'h':
            return new Date(Date.parse(dtTmp) + (3600000 * Number));
        case 'd':
            return new Date(Date.parse(dtTmp) + (86400000 * Number));
        case 'w':
            return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
        case 'q':
            return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number * 3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
        case 'm':
            return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
        case 'y':
            return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
    }
};

//-- Javascript对象扩展--结束 -//

//-- 自定义类-开始 --/
function StringBuffer() {
    this._strings_ = new Array();
}

StringBuffer.prototype.append = function(str) {
    this._strings_.push(str);
    return this;
};

StringBuffer.prototype.toString = function() {
    return this._strings_.join("").trim();
};

/**
 * 以键值对存储
 */
function Map() {
    var struct = function(key, value) {
        this.key = key;
        this.value = value;
    };

    var put = function(key, value) {
        for (var i = 0; i < this.arr.length; i++) {
            if (this.arr[i].key === key) {
                this.arr[i].value = value;
                return;
            }
        }
        this.arr[this.arr.length] = new struct(key, value);
        this._keys[this._keys.length] = key;
    };

    var get = function(key) {
        for (var i = 0; i < this.arr.length; i++) {
            if (this.arr[i].key === key) {
                return this.arr[i].value;
            }
        }
        return null;
    };

    var remove = function(key) {
        var v;
        for (var i = 0; i < this.arr.length; i++) {
            v = this.arr.pop();
            if (v.key === key) {
                continue;
            }
            this.arr.unshift(v);
            this._keys.unshift(v);
        }
    };

    var size = function() {
        return this.arr.length;
    };

    var keys = function() {
        return this._keys;
    };

    var isEmpty = function() {
        return this.arr.length <= 0;
    };
    
//    var clear = function() {
//    	this.arr = new Array();
//        this._keys = new Array();
//    };

    this.arr = new Array();
    this._keys = new Array();
    this.keys = keys;
    this.get = get;
    this.put = put;
    this.remove = remove;
    this.size = size;
    this.isEmpty = isEmpty;
}

/**
 * 更新jquery ui css
 * @param {Object} locStr
 */
function updateCSS(locStr) {
    var cssLink = $('<link href="' + locStr + '" type="text/css" rel="Stylesheet" class="ui-theme" />');
    $("head").append(cssLink);
    if ($("link.ui-theme").size() > 3) {
        $("link.ui-theme:first").remove();
    }
}

/**
 * 更新自定义CSS
 */
function updateCustomCss() {
    var customStyleUrl = ctx + '/css/style.css';
    var cssLink = $('<link href="' + customStyleUrl + '" type="text/css" rel="Stylesheet" class="custom-style" />');
    $("head").append(cssLink);
    if ($("link.custom-style").size() > 3) {
        $("link.custom-style:first").remove();
    }
}

/**
 * 引入css、script文件
 * @param {Object} file
 */
function include(file) {
    var files = typeof file == "string" ? [file] : file;
    for (var i = 0; i < files.length; i++) {
        var name = files[i].replace(/^\s|\s$/g, "");
        var att = name.split('.');
        var ext = att[att.length - 1].toLowerCase();
        var isCSS = ext == "css";
        var tag = isCSS ? "link" : "script";
        var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
        var link = (isCSS ? "href" : "src") + "='" + '' + name + "'";
        if ($(tag + "[" + link + "]").length == 0) {
            $("<" + tag + attr + link + "></" + tag + ">").appendTo('head');
        }
    }
}

//-- 自定义类-结束 --/