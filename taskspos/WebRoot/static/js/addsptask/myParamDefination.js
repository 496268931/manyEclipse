var myParamDefination;
if (!myParamDefination) {
    myParamDefination = {};
}
(function (){


 if(typeof myParamDefination.init !== 'function'){
	 myParamDefination.init = function(){
		 alert("创建弹出函数！");
	 }
 	
 }
}());