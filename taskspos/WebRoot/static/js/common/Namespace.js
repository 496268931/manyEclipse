Namespace = new Object();
// 全局对象仅仅存在register函数，参数为名称空间全路径，如"com.chinazrbc.xz..map"
Namespace.register = function(fullNS) {
	// 将命名空间切成N部分, 比如com.chinazrbc.xz.map等
	var nsArray = fullNS.split('.');
	var sEval = "";
	var sNS = "";
	for (var i = 0; i < nsArray.length; i++) {
		if (i != 0) {
			sNS += ".";
		}
		sNS += nsArray[i];
		// 依次创建构造命名空间对象（假如不存在的话）的语句
		// 比如先创建com.chinazrbc.xz.，然后创建com.chinazrbc.xz.map，依次下去
		sEval += "if (typeof(" + sNS + ") == 'undefined') " + sNS
				+ " = new Object();";
	}
	if (sEval != "") {
		eval(sEval);
	}
};

RegisterNamespace = function(nsName) {
	var rootObject = window;
	var namespaceParts = nsName.split('.');
	for (var i = 0; i < namespaceParts.length; i++) {
		var currentPart = namespaceParts[i];
		if (!rootObject[currentPart]) {
			rootObject[currentPart] = new Object();
		}
		rootObject = rootObject[currentPart];
	}
};