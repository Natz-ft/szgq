function checkNum(s) {
	if(!isInteger(s)){
		document.getElementById("errorMsg").innerHTML = "����������";
		return;
	}else{
		document.getElementById("errorMsg").innerHTML = "";
	}
}