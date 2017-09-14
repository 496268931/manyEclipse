function searchTitleListByLevel(levelName) {
	var list = null;
	$.ajax({
		url: basePath + "/TitleController/searchTitleListByLevel.do",
		type: "post",
		dataType: "json",
		async: false,
		data:{levelName:levelName},
		success: function(data) {
			if (data != null) {
				list = data;
			}
		}
	});
	return list; 
}

function getEmployeeById(id) {
	var employee = null;
	$.ajax({
		url: basePath + "/EmployeeController/getEmployeeById.do", 
		type: "post",
		data: {id: id},
		dataType: "json",
		async: false,
		success: function(data) {
			if (data != null) {
				employee = data;
			}
		}
	});
	return employee;
}

function getTitleById(id) {
    return returnObject({id: id}, "/TitleController/getTitle.do");
}

function getLendById(id){
	return returnObject({id: id}, "/LendController/getLendById.do");
}

function getProductById(id){
	return returnObject({id: id}, "/LendProductController/getLendProductById.do");
}

function getContractById(id){
	return returnObject({id: id}, "/LendContractController/getLendContractById.do");
}

function getAccountById(id){
	return returnObject({id: id}, "/LendAccountController/getLendAccountById.do");
}

function getLendContractById(id){
	return returnObject({id: id}, "/LendContractController/getLendContractById.do");
}

function getCreditorApById(id){
	return returnObject({cmid: id}, "/CreditorApController/getCreditorApById.do");
}

function getLendAffiliation(param){
	return returnObject(param, "/LendAffiliationController/getAffiliation.do");
}

function returnObject(param, url) {
	var obj = null;
	$.ajax({
		url: basePath + url,
		type: "post",
		data: param,
		dataType: "json",
		async: false,
		success: function(data) {
			if (data != null) {
				obj = data;
			}
		}
	});
	return obj;
}

