var iWaiting=1;
function searchIng(){
	var h = $(document).height();
	var $div = $('<div></div>');
	var $divWait = $('<div id="divInWaiting"><!--Searching......--></div>');
	if(arguments.length>0)
		$divWait = $('<div id="divInWaiting"><!--Loading......--></div>');
	$div.css({
		'background-color':'gray',
		'width':'100%',
		'height':h,
		'position':'absolute',
		'left':'0px',
		'top':'0px',
		'text-align':'center',
		'opacity':'0.6'
	});
	$divWait.css({
		'position':'relative',
		'margin':'0 auto',
		'width':'200px',
		'height':'50px',	
		'top':'200px',
		'background-color':'white',
		'background':'url(../static/imags/0292.gif) no-repeat 0 0'
	});
	$div.append($divWait);
	$('body').append($div);
	/*
	if( arguments.length >0 )
		var timerId=setInterval(changeWaiting2,1000);
	else
		var timerId=setInterval(changeWaiting,1000);
	*/
}
function endSearchIng(){
	document.getElementById("divInWaiting").parentNode.parentNode.removeChild(document.getElementById("divInWaiting").parentNode);
}