$(function() {
	$("#cancel").on("click", closePage);
	
});


function closePage() {
	parent.layer.close(parent.layer.getFrameIndex());
}






