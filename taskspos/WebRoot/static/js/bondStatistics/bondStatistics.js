/* 个贷端 债权统计 */
$(document).ready(function(){
	$("#search").click(function(){
   		selectGDTJ();
	});
	$("#exportExcel").click(function(){
		expExcel();
	});
	$("#modifyTime").click(function(){
		  WdatePicker();		
	});
	$("#createTime").click(function(){
		  WdatePicker();
	});
 
}); 
function selectGDTJ() {
	trimTextInput("bondStatisticsForm");
	$("#bondStatisticsForm").submit();
}

/********************************************************************************************************/

//导出EXCELL
function expExcel(){
	var dpid = $('#dpid option:selected') .val();//获取选中的职场代码
	//alert(dpid);
	document.location= basePath + "/LoanCreditorBondStatisticsController/exportBondStatisticsExcel.do";	
}

/********************************************************************************************************/

/* 财富端 债权预期统计 */
$(document).ready(function(){
	$("#searchExpectLendCreditor").click(function(){
   		selectZQCX();
	});
	$("#nowTime").click(function(){
		  WdatePicker();
	});
});

function selectZQCX() {
	trimTextInput("expectLendCreditorListForm");
	$("#expectLendCreditorListForm").submit();
}

/********************************************************************************************************/

/* 财富端 债权申请报表 */
$(document).ready(function(){
	$("#searchApplyLendCreditor").click(function(){
   		selectZQSQ();
	});
	$("#startTime1").click(function(){
		  WdatePicker();
	});
	$("#endTime1").click(function(){
		  WdatePicker();
	});
});

function selectZQSQ() {
	trimTextInput("applyLendCreditorListForm");
	$("#applyLendCreditorListForm").submit();
}
