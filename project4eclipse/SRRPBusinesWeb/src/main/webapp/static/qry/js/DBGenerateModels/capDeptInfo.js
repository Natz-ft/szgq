function checkNum(s) {
	if(!isInteger(s)){
		document.getElementById("errorMsg").innerHTML = "ÇëÊäÈëÊı×Ö";
		return;
	}else{
		document.getElementById("errorMsg").innerHTML = "";
	}
}