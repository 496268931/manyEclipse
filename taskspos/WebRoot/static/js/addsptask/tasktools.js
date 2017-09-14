function getID(){
	var resultID = "";
	var timestamp3 = new Date().getTime();
	var rand = Math.floor(Math.random () * 900) + 100;
	var rand = "";
	for(var i = 0; i < 3; i++){
	    var r = Math.floor(Math.random() * 10);
	    rand += r;
	}
	resultID = timestamp3 +  rand
	return resultID;
}
