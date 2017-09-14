var ajaxGet = function ajaxGet(url,data,succ,fail,err){
	$.ajax({
		type:'GET',
		url:url,
		data:data,
		dataType:'json',
		beforeSend:function() {
			this.url = encodeURI(this.url);
		},
		success:function(data){
			if (data == null) {
				return;
			}
			if(!('code' in data)) {
				return;
			}
			var code = parseInt(data.code);
			if(0 == code){
				if('function' == typeof succ ){
					succ(data.data);
				}
			}else{
				if (typeof console == 'object'){
					console.log('结果码：'+data.code+'结果描述：'+data.message);
				}
				if(typeof fail == 'function'){
					fail(data.code, data.msg);
				}
			}
		},
		error:function(response) {
			if (typeof console == 'object')
				console.log('错误码：'+response.status);
			if(typeof err == 'function'){
				err(response.status);
			}
		}
	});
};
var cfgDefault = function(src, def) {
	if (typeof src == 'undefined') return def;
	if (def == null) return src;
	if (typeof def != 'object') return src;

	if (def instanceof Array) {
		if (! (src instanceof Array)) return def;
	} else {
		if (typeof src != 'object' || src instanceof Array) return def;
	}

	for (var i in def) 
		src[i] = arguments.callee(src[i], def[i]);

	return src;
};

// Format(new Date(),"yyyy-MM-dd hh:mm:ss.S") ==> 2014-06-09 08:09:04.423 
// Format(new Date(),"yyyy-M-d h:m:s.S")      ==> 2006-6-9 8:9:4.18 
var Format = function (date,fmt) { //author: meizz 
	var o = {
		"M+": date.getMonth() + 1, //月份 
		"d+": date.getDate(), //日 
		"h+": date.getHours(), //小时 
		"m+": date.getMinutes(), //分 
		"s+": date.getSeconds(), //秒 
		"q+": Math.floor((date.getMonth() + 3) / 3), //季度 
		"S": date.getMilliseconds() //毫秒 
	};
	if (/(y+)/.test(fmt)) 
		fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));

	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) 
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

/*创建Table*/
var qyhDateTable = function(_cfg) {
	this.cfg = cfgDefault(_cfg,{
		date:new Date(),
	});
	//console.log(Format(_cfg.date,'yyyyMMdd'));
	this.config = {
        rows: 6,//行
        cols: 7,//列
        head: ['日', '一', '二', '三', '四', '五', '六']
    };
	var own = this;
	own.date = own.cfg.date;
	own.eles = [];
    own.dates = [];
	var table = own.createTable();
	own.initDays();
    return {
		getRoot:function(){
			return table;
		},
		getEles:function(){
			return own.eles;
		},
        getDates: function() {
            return own.getDates(arguments);
        },
		getCurrDate:function(){
			return own.dates[own.date.getDate()-1];
		},
		getDate:function(inx){
			return own.dates[inx];
		}
    };
};

qyhDateTable.prototype = {
	//获取指定范围的日期
    getDates: function(args) {
        if (args.length > 3) {
            return;
        }
		var dates = this.dates;
        var start = 0;
        var end = dates.length; //默认返回全部
        if (args.length == 1) { //0-指定位置
            end = args[0];
        }

        if (args.length == 2) { //指定位置1 - 指定位置 2
            start = args[0];
            end = args[1];
        }
        return dates.slice(start, end);
    },
	initDays:function(){
		var own = this;
		var dates = [];
		var eles = own.eles;
		var len = own.getMonthLen();
		var start = own.getMonthStart();
		for (var i = start; i < start + len; i++) {
			own.processDateEle(eles[i], i - start + 1);
			dates.push(eles[i]);
		};
		own.dates = dates;
	},
	getMonthStart:function(){
		var own = this;
		var date = new Date(own.date.getFullYear(),own.date.getMonth(),1);
		//console.log(Format(date,'yyyyMMdd'));
		return date.getDay();
	},
    getMonthLen: function() {
		var own = this;
		var year = own.date.getFullYear();
		var month = own.date.getMonth()+1;
        var own = this;
		var len31 = '1-3-5-7-8-10-12-';
		var len30 = '4-6-9-11-';
        if ( - 1 != len31.indexOf(month)) {
            return 31;
        } else if ( - 1 != len30.indexOf(month)) {
            return 30;
        } else {
            if (own.isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
    },
    isLeapYear: function(year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    },
	createTable:function(){
		var own = this;
		var tb = document.createElement('table');
		tb.className = 'tb';
		tb.cellpadding = 0;
		tb.cellspacing = 0;
		var thead = own.createThead();
		var tbody = own.createTbody();
		tb.appendChild(thead);
		tb.appendChild(tbody);
		return tb;
	},
	createTbody: function() {
        var own = this;
        var rows = own.config.rows;
        var cols = own.config.cols;
        var tbody = document.createElement('tbody');
        for (var r = 0; r < rows; r++) {
            var tr = document.createElement('tr');
            for (var c = 0; c < cols; c++) {
                var td = document.createElement('td');
                own.eles.push(td);
                //td.innerHTML = r * cols + c;
                //eles[r * cols + c] = td;
				if(c == cols-1){
					td.className='end';
				}
                tr.appendChild(td);
            }
			tbody.style.cursor='not-allowed';
            tbody.appendChild(tr);
        }
        return tbody;
    },
    //根据配置信息创建头
    createThead: function() {
        var _head = this.config.head;
        var thead = document.createElement('thead');
        var tr = document.createElement('tr');
        for (var k in _head) {
            var th = document.createElement('th');
			if(k==0 || k==6){
				//console.log(th);
				th.className='lred';
			}
            th.innerHTML = _head[k];
            tr.appendChild(th);
        }
        thead.appendChild(tr);
        return thead;
    },
	
    //处理每一个日期节点
    processDateEle: function(ele, inx) {
        var own = this;
		var date = own.cfg.date;
        var nDate = new Date(date.getFullYear(),date.getMonth(),inx);
		ele.setAttribute('date',Format(nDate,'yyyy-MM-dd'));
		//var dateStr = date.getFullYear()+'-'+(date.getMonth()+1)+'-'+inx;
		//ele.setAttribute('date',dateStr);
        ele.innerHTML = inx;
    }
};

var qyhDate =function(_cfg){
	this.cfg = cfgDefault(_cfg,{
		date:new Date(),
		data:[],
		renderTo:null,
		cb_start:null,
		cb_end:null,
		cb_confirm:null, 
		cb_cancel:null

	});
	var own = this;
	own.tables = [];
	own.root = document.createElement('div');
	$(own.root).addClass('qyh-date');
	own.createCon();
	own.init();

	return{
		getRoot:function(){
			return own.root;
		},
		hide:function(){
			own.hide();
		},
		show:function(){
			own.show();
		},
		reflushData:function(data){
			if(null!=data && data.length>0){
				own.cfg.data = data;
				own.initData(data);
			}
		}
	};
};
qyhDate.prototype ={
	hide:function(){
		var own = this;
		$(own.root).hide();
	},
	show:function(){
		var own = this;
		$(own.root).show();
	},
	init:function(){
		var own = this;
		var _par = own.cfg.renderTo;
		if(null != _par){
			$('body').append(own.root);
			own.initPosition(_par);
		}
			
		var data = own.cfg.data;
		own.initData(data);
	},
	initPosition:function(_par){
		var own = this;
		own.fixPosition(own.root, _par, $('body')[0]);
		$(_par).toggle(function() {
			$(own.root).show();
		},function() {
			$(own.root).hide();
		});

	},
	initData:function(data){
		var own = this;
		for(var k in data){
			var tb = own.tables[k];
			var d = data[k];
			if(null!=data && data.length>0){
				own.initTable(tb,d);
			}
		}
	},
	initTable:function(table,data){
		var own = this;
		var dates = table.getDates();
		var date = own.cfg.date;
		own.startDate = null;//----
		own.endDate = null;//----
		own.startEle = null;
		own.endEle = null;
		$(dates).each(function(inx){
			var idstr = $(this).attr('date').split('-'); 
			var idate = new Date(idstr[0],parseInt(idstr[1])-1,idstr[2]);
			//own.printDate(date);
			if(idate < date){//非有效日期
				var _con = own.createDateCon1(inx+1);
				$(this).empty().append(_con);
			}else{
				var cb_start = own.cfg.cb_start;
				var cb_end = own.cfg.cb_end;
				var preData = data[inx-1];
				var _con1 = own.createEnabledEle(idate,data[inx],preData,function(sdate){
					if(null != own.startEle){
						$(own.startEle).trigger('reset');
					} 
					own.startEle = _con1;
					own.startDate = sdate;
					if('function' == typeof cb_start){
						cb_start(sdate);
					}
				},function(edate){
					if(null != own.endEle){
						$(own.endEle).trigger('reset');
					} 
					own.endEle = _con1;
					own.endDate = edate;
					if('function' == typeof cb_end){
						cb_end(edate);
					}
				});
				$(this).empty().append(_con1);
			}
		});
	},
	createEnabledEle:function(date,data,preData,cb_start,cb_end){
		var own = this;
		//var cb_start = own.cfg.cb_start;
		//var cb_end = own.cfg.cb_end;
		var _div = document.createElement('div');
		var flags = [true,true];
		if(null == preData || true != preData.inventory.exist ){ //前一天无效或无房 不能选离开日期
			flags[1] = false;
		}
		
		if(true != data.inventory.exist ){//当天无效 不能选开始日期
			flags[0] = false;
		}

		var _con = own.createDateCon2(date.getDate(),data);
		var _hse = own.createDateConHov(function(){
			_con.setOrange();
			//if('function' == typeof cb_start){
				cb_start(date);
			//}
		},function(){
			_con.setBlue();
			//if('function' == typeof cb_end){
				cb_end(date);
			//}
		},flags);
		
		$(_div).hover(function(){
			$(_hse.getRoot()).show();
			$(_con.getRoot()).hide();
		},function(){
			$(_hse.getRoot()).hide();
			$(_con.getRoot()).show();
		});	
		$(_div).bind('reset',function(){
			_con.reset();
			_hse.reset();
		});
		$(_div).append(_con.getRoot()).append(_hse.getRoot()).css({'cursor':'pointer'});
		return _div;
	},
	printDate:function(date){
		console.log(date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate());
	},
	createCon:function(){
		var own = this;
		var _div = document.createElement('div');
		$(_div).addClass('clr_after content');
		var date1 = own.cfg.date;
		var curr_mon = date1.getMonth()+1;//当前月份
		var date2 = new Date(date1.getFullYear(),curr_mon,1);
		//own.printDate(date2);
		var left = own.createInnerCon(curr_mon,date1);
		$(left).addClass('fL');
		var right = own.createInnerCon(curr_mon+1,date2);
		$(right).addClass('fR');
		$(_div).append(left).append(right);
		var _bottom = own.createBottom();
		$(own.root).append(_div).append(_bottom);
	},
	createBottomRight:function(){
		var own = this;
		var cb_confirm = own.cfg.cb_confirm; 
		var cb_cancel = own.cfg.cb_cancel;
		var span =  document.createElement('span');
		var _confirm_btn =  document.createElement('button');
		$(_confirm_btn).addClass('mody_2').html('确认修改');
		$(_confirm_btn).click(function(){
			own.hide();
			if('function' == typeof cb_confirm){
				cb_confirm(own.startDate,own.endDate);
			}
		});
		var _cancel_btn = document.createElement('button');
		$(_cancel_btn).addClass('mody').html('取消修改');
		$(_cancel_btn).click(function(){
			own.hide();
			if('function' == typeof cb_cancel){
				cb_cancel(own.startDate,own.endDate);
			}
		});
		$(span).addClass('fR').append(_confirm_btn).append(_cancel_btn);
		return span;
	},
	createBottomLeft:function(){
		var p = document.createElement('p');
		$(p).addClass('tip');
		$(p).html('<em><s class="blue"></s></em>价格'+
				  '<em><s></s></em>剩余房间'+
			      '<em><s class="red"></s></em>满房');
		return p;
	},
	createBottom:function(){
	   var own = this;
	   var _div = document.createElement('div');
	   $(_div).addClass('clr_after info');
	   var _left = own.createBottomLeft();
	   var _right = own.createBottomRight();
	   $(_div).append(_right).append(_left);
	   return _div;
	},
	//创建某个月日期
	createInnerCon:function(inx,date){
	   var own = this;
	   var _div = document.createElement('div');
	   $(_div).addClass('inner');
	   
	   var str = date.getFullYear( )+'年'+(date.getMonth()+1)+'月';
	   var _top = own.createTop(str);
	   var table = new qyhDateTable({
		  date:date
	   });
	   own.tables[inx] = table;
	   //var _bgd = own.createBgd(date.getMonth()+1);
	   $(_div).append(_top).append(table.getRoot());//.append(_bgd);
	   return _div;
	},
	createTop:function(text){
		var _div = document.createElement('div');
		$(_div).addClass('top');
		var _mon = document.createElement('div');
		$(_mon).html(text);
		$(_div).append(_mon);
		return _div;
	},
	createBgd:function(text){
		//<div class="cm_6">6</div>
		var _div = document.createElement('div');
		$(_div).addClass('dm').html(text);
		return _div;
	},
	//非有效日期
	createDateCon1:function(day){
		var _p = document.createElement('p');
		var _b = document.createElement('b');
		$(_b).addClass('date').html(day);
		$(_p).append(_b);
		return _p;
	},
	//有效日期
	createDateCon2:function(day,data){
		var _p = document.createElement('p');
		var _b = document.createElement('b');
		$(_b).addClass('date').html(day);
		var _pspan = document.createElement('span');
		$(_pspan).addClass('blue');
		var _tspan = document.createElement('span');
		if(data.inventory.exist==true){
			$(_pspan).html(data.price.price);
			$(_tspan).addClass('green').html('需申请');
		}else{
			$(_p).addClass('no_house');
			$(_tspan).addClass('red').html('无房');
		}
		$(_p).append(_b).append(_pspan).append(_tspan);
		return {
			getRoot:function(){
				return _p;
			},
			reset:function(){
				$(_p).removeClass('blue_line').removeClass('orange_line');
			},
			setBlue:function(){
				$(_p).removeClass('orange_line').addClass('blue_line');
			},
			setOrange:function(){
				$(_p).removeClass('blue_line').addClass('orange_line');
			}
		};
	}, 
	createDateConHov:function(cb_start,cb_end,def_flags){
		var  _p = document.createElement('p');
		$(_p).addClass('no_line');
		var startd = document.createElement('span');
		$(startd).addClass('gray').html('入店日期');
		var endd = document.createElement('span');
		$(endd).addClass('white').html('离店日期');
		var flags = def_flags;
		
		$(startd).click(function(){
			if(flags[0]){
				$(_p).trigger('orange');
				$(_p).trigger('disabled','end');
				//if('function' == typeof cb_start){
					cb_start();
				//}
			}
		});
		$(endd).click(function(){
			if(flags[1]){
				$(_p).trigger('blue');
				//$(_p).trigger('disabled','start'); 
				//if('function' == typeof cb_end){
					cb_end();
				//}
			}
		});

		var setFlags = function(){
			if(null == flags){
				flags = [true,true];//是否可用标示
			}
			//for(var k in flags){
				if(false == flags[0]){
					$(startd).css({'cursor':'not-allowed'});
				}
				if(false == flags[1]){
					$(endd).css({'cursor':'not-allowed'});
				}
			//}
		};
		setFlags();
		$(_p).bind('blue',function(){
			$(_p).removeClass('no_line').removeClass('orange_line').addClass('blue_line');
		});
		$(_p).bind('orange',function(){
			$(_p).removeClass('no_line').removeClass('blue_line').addClass('orange_line');
		});
		//设置开始 结束日期是否可选
		$(_p).bind('disabled',function(e,p){
			if(p == 'start'){//开始日期不可选
				$(startd).css({'cursor':'not-allowed'});
				flags[0] = false;
			}else if(p == 'end'){//结束日期不可选
				$(endd).css({'cursor':'not-allowed'});
				flags[1] = false;
			}else{//都不可选
				$(startd).css({'cursor':'not-allowed'});
				flags[0] = false;
				$(endd).css({'cursor':'not-allowed'});
				flags[1] = false;
			}
		});
		$(_p).append(startd).append(endd).hide();
		return {
			getRoot:function(){
				return _p;
			},
			reset:function(){
				$(_p).removeClass('orange_line').removeClass('blue_line').addClass('no_line');
				flags = def_flags;
				setFlags();
			},
			setStart:function(){
				$(startd).trigger('click');
			},
			setEnd:function(){
				$(endd).trigger('click');
			}
		};
	},
	//修正位置 
    //_child:待修正元素 _par:显示位置(显示效果上的父元素) root:真实追加位置 父元素
    fixPosition: function(_child, _par, root) {
        if (null == root) {
            root = document.body;
        }
        var pof = $(_par).offset();
        var rof = $(root).offset();
        $(_child).css({
            top: (pof.top - rof.top) + $(_par).outerHeight() + "px",
            left: (pof.left - rof.left) + "px"
        });
    }
};

var getDate = function(_par,houseType,cb_confirm,cb_cancel){
	var d = new Date();
	var date = new Date(d.getFullYear(),d.getMonth(),d.getDate());//去除时分秒
	
	var qDate = new qyhDate({
		date:date,
		//data:data,
		renderTo:_par,
		cb_start:function(d1){
			//alert(d1);
		},
		cb_end:function(d2){
			//alert(d2);
		},
		cb_confirm:function(d1,d2){
			if('function' == typeof cb_confirm){
				var sd = (null==d1?null:Format(d1,'yyyy-MM-dd'));
				var ed = (null==d2?null:Format(d2,'yyyy-MM-dd'));
				cb_confirm(sd,ed);
			}
			//console.log(d1);
			//console.log(d2);
		},
		cb_cancel:function(d1,d2){
			
			if('function' == typeof cb_cancel){
				var sd = (null==d1?null:Format(d1,'yyyy-MM-dd'));
				var ed = (null==d2?null:Format(d2,'yyyy-MM-dd'));
				cb_cancel(sd,ed);
			}
			//console.log(d1);
			//console.log(d2);
		}
	});
	var getInfoByMonth = function(month,data){
		var res = [];
		for(var k in data){
			var d = data[k];
			if(d.month == month){
				res[d.day-1] = data[k];
			}
		}
		return res;
	};
	
	var date1 = new Date(d.getFullYear(),d.getMonth(),1);
	var mon = date1.getMonth()+1;
	var date2 = new Date(d.getFullYear(),mon+1,1);
	
	var sd = Format(date1,'yyyy-MM-dd');
	var ed = Format(date2,'yyyy-MM-dd');
	//qyh.jsonAjax.GET
	ajaxGet(RELATIVE_PATH+'InventoryAndPriceController/getInventoryAndPrice.do',
			{houseType:houseType,startDate:sd,endDate:ed},function(data){
				var pdata = [];
				var info1 = getInfoByMonth(mon,data);
				var info2 = getInfoByMonth(mon + 1,data);
				pdata[mon] = info1;
				pdata[(mon+1)] = info2;
				qDate.reflushData(pdata);
	},function(code,msg){
		alert('未获取'+msg);
	});
	return qDate;
};





