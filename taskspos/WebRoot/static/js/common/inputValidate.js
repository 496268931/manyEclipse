function validateChar(sIn,sReg)
{
	var bFlag = true;
	var reg =  new  RegExp( sReg,"i");
		bFlag=reg.test(sIn);
		return bFlag;
		} 
		
function numCheck(obj){
 var /*string*/ txtContent= obj.attr("value");
	if(! validateChar(txtContent,"^\\d+$"))
	{
		obj.select();
		return false;
	}
	return true;
}

function emptyCheck(sIn){
	var BR=$.trim(sIn)===""?false:true;
	 return BR;
}

function drugNameInputCheck(sIn){

	return validateChar(sIn,"^[\-a-zA-Z\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$"); 
	
}

function specialsymbolsInputCheck(/* object*/ obj){
    var validchars ="\'<>@!#$%^&~\\";
    var /*string*/ txtContent= obj.attr("value");//获得文本框的值
        for(var i=0;i<txtContent.length;i++)
        {
          var letter = txtContent.charAt(i).toLowerCase();
          if( validchars.indexOf(letter) != -1)
          {
 
                obj.select();
               return false;
          }
        }
       return true;
}