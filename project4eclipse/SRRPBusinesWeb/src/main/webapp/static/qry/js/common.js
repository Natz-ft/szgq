//<!--	
var defaultEmptyOK = true
function formatMoneyTypeValue(obj) {
	 if (obj != null && obj != "") {
			obj = obj.replace(/\d+?(?=(?:\d{3})+(?:$|\.))/g,function(s){
				return s + ',';
			})
			return obj 
	}
}

function formatMoneyType(obj) {
	if (obj != null && obj.value != "") {
		obj.value = obj.value.replace(/\d+?(?=(?:\d{3})+(?:$|\.))/g,function(s){
			return s + ',';
		})
	}
}

function unFormatMoneyType(obj) {
	if (obj != null && obj.value != "") {
		obj.value = obj.value.replace(/,/g,'');
	}
}

function unFormatMoneyTypeValue(obj) {
	if (obj != null && obj != "") {
		return obj.replace(/,/g,'');
	}
}
function IsPassWord(obj) {
	var tmp = obj.value;
	if (isWhitespace(tmp)) {
		return false;
	}
	var strTmp = new String(tmp);
	var str1 = "0123456789";
	var str2 = "abcdefghijklmnopqrstuvwxyz";
	var str3 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var idx1 = -1;
	var idx2 = -1;
	var idx3 = -1;
	for (i = 0; i < strTmp.length; i = i + 1) {
		if (str1.indexOf(strTmp.charAt(i)) > -1) {
			idx1 = 0;
		}
		if (str2.indexOf(strTmp.charAt(i)) > -1) {
			idx2 = 0;
		}
		if (str3.indexOf(strTmp.charAt(i)) > -1) {
			idx3 = 0;
		}
	}
	if (idx1 < 0 || idx2 < 0 || idx3 < 0) {
		return false;
	}
	return true;
}

function isEmptyToo(s) {
	return ((s == null) || (s.length == 0));
}

function isWhitespace(s) {
	var whitespace = " \t\n\r";
	var i;
	if (isEmptyToo(s)) {
		return true;
	}
	for (i = 0; i < s.length; i = i + 1) {
		var c = s.charAt(i);
		if (whitespace.indexOf(c) == -1) {
			return false;
		}
	}
	return true;
}

function strlen(str) {
	var len;
	var i;
	len = 0;
	for (i = 0; i < str.length; i = i + 1) {
		if (str.charCodeAt(i) > 255) {
			len += 2;
		} else {
			len = len + 1;
		}
	}
	return len;
}
function LTrim(str) {
	if (str.charAt(0) == "??" || str.charAt(0) == " ") {
		str = str.slice(1);
		str = LTrim(str);
	}

	return str;
}
function RTrim(str) {
	var whitespace = new String(" \t\n\r");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(s.length - 1)) != -1) {
		var i = s.length - 1;
		while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1) {
			i = i - 1;
		}
		s = s.substring(0, i + 1);
	}
	return s;
}

function Trim(str) {
	return RTrim(LTrim(str));
}

function LTrimZero(str) {
	var whitespace = new String("0");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(0)) != -1) {
		var j = 0, i = s.length;
		while (j < i && whitespace.indexOf(s.charAt(j)) != -1) {
			j = j + 1;
		}
		s = s.substring(j, i);
	}
	return s;
}

window.onbeforeunload = disabledButton;

function disabledButton() {
	if (startProgress()) {
		return;
	}
	for (var m = 0; m < document.forms.length; m++) {
		for (var i = 0; i < document.forms[m].elements.length; i++) {
			var e = document.forms[m].elements[i];
			if (e.type == "button") {
				e.disabled = true;
			}
			if (e.type == "submit") {
				e.disabled = true;
			}
			if (e.type == "reset") {
				e.disabled = true;
			}
		}
	}
}

function enabledButton() {
	if (stopProgress()) {
		return;
	}
	for (var m = 0; m < document.forms.length; m++) {
		for (var i = 0; i < document.forms[m].elements.length; i++) {
			var e = document.forms[m].elements[i];
			if (e.type == "button") {
				e.disabled = false;
			}
			if (e.type == "submit") {
				e.disabled = false;
			}
			if (e.type == "reset") {
				e.disabled = false;
			}
		}
	}
}

function isRenderedMessagePanel() {
	if (document.all('messagePanel') == undefined) {
		return false;
	} else {
		return true;
	}
}

function mOverBtn(obj) {
	obj.className = 'MoveOverBtn';
}

function mOutBtn(obj) {
	obj.className = 'MoveOutBtn';
}
function isInteger(s)

{
	var i;

	if (isEmptyToo(s))
		if (isInteger.arguments.length == 1)
			return defaultEmptyOK;
		else
			return (isInteger.arguments[1] == true);

	for (i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (!isDigit(c))
			return false;
	}

	return true;
}

function isDigit(c) {
	return ((c >= "0") && (c <= "9"))
}

function checkPhoneNumber(strTel) {
	for (var i = 0; i < strTel.length; i++) {
		var ch = strTel.charAt(i);
		if ((ch >= '0' && ch <= '9') || ch == '-' || ch == '(' || ch == ')'
				|| ch == '+' || ch == ' ') {
			continue;
		} else {
			return false;
		}
	}
	return true;
}

String.prototype.isEmail = function() {
	return /^\w+@.+\.\w+$/g.test(this);
}

function emailVerify(field) {
	if (field.isEmail()) {
		return true;
	}
	return false;
}

function isChinese(str) {
	var chinese = /[^\x00-\xff]/g;
	if (chinese.test(str)) {
		return true;
	}
	return false;
}
function doNavigete(obj) {
	document.getElementById('topForm:currentMenuId').value = obj;
	document.getElementById('topForm:navbutton').click();
	var o = document.getElementById("topForm:menuid-" + obj);
	var arya = document.getElementsByTagName("a");
	for (var i = 0; i < arya.length; i++) {
		var id = arya[i].id;
		if (id.indexOf("topForm:menuid-") >= 0) {
			arya[i].style.color = "#FFFFFF";
		}
	}
	o.style.color = "#0e267a";
}

function treeNodeChecked(nodeBox, formName) {
	var boxID = nodeBox.name.split("::");
	var prefixName = boxID[0];
	var postfixName = boxID[1];
	var formObject = document.getElementById(formName);

	for (var i = 0; i < formObject.elements.length; i++) {
		var subNode = formObject.elements[i];
		if (subNode.name.indexOf("::" + postfixName) == -1) {
			continue;
		}
		if (subNode.name == nodeBox.name
				|| subNode.name.indexOf(prefixName + ":") == -1) {
			continue;
		}
		subNode.checked = nodeBox.checked;
	}

	var tmpBoxID = nodeBox.name.split("::");
	var tmp = tmpBoxID[0];
	tmp = tmp.substring(0, tmp.lastIndexOf(":"));
	tmp = tmp.substring(0, tmp.lastIndexOf(":"));

	while (tmp.length != 0) {
		var superNodePrefixName = tmp;
		var superNodePostfixName = tmpBoxID[1];
		var superNodeName = superNodePrefixName + "::" + superNodePostfixName;
		var superNode = document.getElementById(superNodeName);
		if (superNode == undefined) {
			return;
		}
		var equCount = 0;
		var equCheckedCount = 0;
		for (var j = 0; j < formObject.elements.length; j++) {
			var equNode = formObject.elements[j];
			if (equNode.name.indexOf("::" + superNodePostfixName) == -1) {
				continue;
			}
			if (equNode.name == superNodeName
					|| equNode.name.indexOf(superNodePrefixName + ":") == -1) {
				continue;
			}
			equCount++;
			if (equNode.checked) {
				equCheckedCount++;
			}
		}
		if (equCount > 0 && equCount == equCheckedCount) {
			superNode.checked = true;
		} else {
			superNode.checked = false;
		}
		tmp = tmp.substring(0, tmp.lastIndexOf(":"));
		tmp = tmp.substring(0, tmp.lastIndexOf(":"));
	}
}

function popRecursiveTree(collName, idName, showName, parentName, completeBtn,
		readonly, radio, checkedCollName) {
	popRecursiveSelectTree(collName, idName, showName, parentName, completeBtn,
			readonly, radio, checkedCollName);
}

function popRecursiveTreeCheckOne(collName, idName, showName, parentName,
		completeBtn, readonly, radio, checkedCollName) {
	popRecursiveSelectTree(collName, idName, showName, parentName, completeBtn,
			readonly, radio, checkedCollName, true);
}

function popRecursiveSelectTree(collName, idName, showName, parentName,
		completeBtn, readonly, radio, checkedCollName, checkone) {
	var width = 548;
	var height = 400;
	var param = "?collName=" + collName + "&idName=" + idName + "&showName="
			+ showName + "&parentName=" + parentName + "&completeBtn="
			+ completeBtn + "&readonly=" + readonly + "&radio=" + radio
			+ "&initTree=true";
	if (checkedCollName != undefined) {
		param = param + "&checkedCollName=" + checkedCollName;
	}
	if (checkone != undefined && checkone) {
		param = param + "&checkone=true";
	} else {
		param = param + "&checkone=false";
	}
	var newin = window
			.open(
					'',
					'PopupTree',
					'width=0, height=0, toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, top='
							+ screen.height + ',left=' + screen.width);
	newin.close();
	var pagePrefix = document.location.href.substring(0, document.location.href
					.indexOf("pages"));
	window
			.open(
					pagePrefix + 'pages/commonmange/recursiveTree/popupRecursiveTree.jsf'
							+ param,
					'PopupTree',
					'width='
							+ width
							+ ', height='
							+ height
							+ ', top='
							+ (screen.height - height)
							/ 2
							+ ',left='
							+ (screen.width - width)
							/ 2
							+ ', toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no');
}

function fspTurnPage(maxPage, inputValue, dataScroll) {
	var s = document.all(inputValue).value;
	if (isEmptyToo(s) || !isInteger(s)) {
		alert('\u8bf7\u5f55\u5165\u8df3\u8f6c\u9875\u9762');
		return false;
	}
	if (s > maxPage) {
		alert('\u8df3\u8f6c\u9875\u9762\u4e0d\u80fd\u5927\u4e8e' + maxPage);
		return false;
	}
	if (s < 1) {
		alert('\u8df3\u8f6c\u9875\u9762\u4e0d\u80fd\u5c0f\u4e8e1');
		return false;
	}
	document.getElementById(dataScroll).component.switchToPage(s);
}

function mOverBtn_Other(obj, btn_type) {
	obj.className = 'MoveOverBtn_' + btn_type;
}

function mOutBtn_Other(obj, btn_type) {
	obj.className = 'btn_' + btn_type;
}

function refreshCtx() {
	var s1 = location.pathname;
	var s2 = s1.split("/");
	var rfCtxWin = window
			.open(
					s2[0] + "/" + s2[1] + "/" + 'refresh',
					'rfCtxWinMdef',
					'width=0, height=0, toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, top='
							+ screen.height + ',left=' + screen.width);
	rfCtxWin.close();
	alert('\u5237\u65b0\u914d\u7f6e\u6587\u4ef6\u5b8c\u6210\u3002');
}

var progressCounter = 1;
var progressIntervalID;
var progressBarID = "progressBarForm:progressBar";
var useParentProgressBar = false;
function startProgress() {
	try {
		useParentProgressBar = (window.parent.document.all(progressBarID) != undefined);
		if (document.all(progressBarID) == undefined) {
			return false;
		}
		progressCounter = 1;
		if (useParentProgressBar) {
			window.parent.document.all(progressBarID).component.setValue(3);
			window.parent.document.all(progressBarID).component.enable();
			window.parent.Richfaces.showModalPanel('progressBarModal');
			progressIntervalID = window.parent.setInterval(updateProgress, 100);
		} else {
			document.all(progressBarID).component.setValue(3);
			document.all(progressBarID).component.enable();
			Richfaces.showModalPanel('progressBarModal');
			progressIntervalID = setInterval(updateProgress, 100);
		}
		return true;
	} catch (e) {
		return false;
	}
}
function updateProgress() {
	if (useParentProgressBar) {
		window.parent.document.all(progressBarID).component
				.setValue(progressCounter * 5);
	} else {
		document.all(progressBarID).component.setValue(progressCounter * 5);
	}
	if ((progressCounter++) > 20) {
		if (useParentProgressBar) {
			window.parent.clearInterval(progressIntervalID);
		} else {
			clearInterval(progressIntervalID);
		}
		startProgress();
	}
}
function stopProgress() {
	try {
		useParentProgressBar = (window.parent.document.all(progressBarID) != undefined);
		if (document.all(progressBarID) == undefined) {
			return false;
		}
		if (useParentProgressBar) {
			window.parent.clearInterval(progressIntervalID);
			window.parent.Richfaces.hideModalPanel('progressBarModal');
			window.parent.document.all(progressBarID).component.disable();
		} else {
			clearInterval(progressIntervalID);
			Richfaces.hideModalPanel('progressBarModal');
			document.all(progressBarID).component.disable();
		}
		return true;
	} catch (e) {
		return false;
	}
}

var _cleanID;
function cleanProgress(btnID, cleanTime) {
	_cleanID = setTimeout("_cleanProgressBar('" + btnID + "')", cleanTime);
}
function _cleanProgressBar(btnID) {
	stopProgress();
	if (_cleanID != undefined) {
		clearTimeout(_cleanID);
	}
	if (btnID != undefined) {
		document.all(btnID).click();
	}
}

window.onhelp = function() {
	return false;
}

document.onkeydown = function() {
	if ((window.event.altKey)
			&& ((window.event.keyCode == 37) || (window.event.keyCode == 39) || window.event.keyCode == 13)) {
		event.returnValue = false;
		return false;
	}
	if ((event.keyCode == 8 && (event.srcElement == null
			|| (event.srcElement.type != "text"
					&& event.srcElement.type != "textarea" && event.srcElement.type != "password") || isElReadOnly(event.srcElement)))
			|| (event.keyCode == 116) || (event.ctrlKey && event.keyCode == 82)) {
		event.keyCode = 0;
		event.returnValue = false;
	}
	if (event.keyCode == 122) {
		event.keyCode = 0;
		event.returnValue = false;
	}
	if (event.ctrlKey && event.keyCode == 78)
		event.returnValue = false;
	if (event.shiftKey && event.keyCode == 121)
		event.returnValue = false;
	if (window.event.srcElement.tagName == "A" && window.event.shiftKey)
		window.event.returnValue = false;
	if ((window.event.altKey) && (window.event.keyCode == 115)) {
		window.showModelessDialog("about:blank", "",
				"dialogWidth:1px;dialogheight:1px");
		return false;
	}
}

function isElReadOnly(obj) {
	if (obj.readOnly == 'readOnly' || obj.readOnly == 'yes'
			|| obj.readOnly == true || obj.readOnly == 'true'
			|| obj.disabled == 'disabled' || obj.disabled == true
			|| obj.disabled == 'yes') {
		return true;
	} else {
		return false;
	}

}

function mOverTableRow(obj) {
	obj.className = 'MouseOverRow';
}

function mOutTableRow(obj) {
	obj.className = 'MouseOutRow';
}

function menuJump(ssurveyobj) {
	var re = new RegExp("(http://" + location.hostname + ")|(\\?.+)", "g");
	var basePath = location.href.replace(re, "").split('/')[1];
	var param = new Object();
	param.ssurveyobj = ssurveyobj;
	window
			.showModalDialog(
					window.location.protocol
							+ "//"
							+ window.location.host
							+ '/'
							+ basePath
							+ '/pages/datafileprocess/survey/common/showSurveyInfoTable.jsf',
					param,
					'dialogWidth:460px; dialogHeight:460px; dialogLeft:300px; dialogTop:200px; status:no;menu:no;toolbar:no;scroll:yes;resizable=yes;');
}
/*----------------------------------------------------------------
// 命名空间注册方法
// Namespace是命名空间对象
// Namespace.register方法
//----------------------------------------------------------------*/

Namespace = new Object();
Namespace.register = function(fullNS)
{
	// 将命名空间切成N部分, 比如Grandsoft、GEA等
	var nsArray = fullNS.split('.');
	var sEval = "";
	var sNS = "";
	for ( var i = 0; i < nsArray.length; i++)
	{
		if (i != 0)
			sNS += ".";
		sNS += nsArray[i];
		// 依次创建构造命名空间对象（假如不存在的话）的语句
		// 比如先创建Grandsoft，然后创建Grandsoft.GEA，依次下去
		sEval += "if (typeof(" + sNS + ") == 'undefined') " + sNS
				+ " = new Object();"
	}
	if (sEval != "")
		eval(sEval);
}

//window._alert = window.alert;
//window._confirm = window.confirm;
//
//window.alert = function(s)
//{
//	_alert("系统提示: " + s);
//}
//window.confirm = function(s)
//{
//	_confirm(s);
//}
/*----------------------------------------------------------------
//功能：在使用a4j按钮提交时，为了防止连击的功能，在提交的时候弹出进度条 
//解释：当有两个按钮，一个普通按钮用来校验一个a4j按钮用来提交时，
//    在普通按钮的onclick事件里调用此方法，在返回时a4j按钮的
//    oncomplete方法中调用stopProgress()方法
//
//    <input
//     id="inpute1545f5f-81ff-4a04-a91d-36a79959abbb"
//     onclick="startCheck(this)"
//     onmouseout="mOutBtn(this)"
//     onmouseover="mOverBtn(this)"
//      type="button"
//      value="开始检查" class="btn"/>
//     <a4j:commandButton
//     id="codee1545f5f-81ff-4a04-a91d-36a79959abbb"
//      style="display:none;visibility:hidden;"
//      oncomplete="stopProgress()"
//      action="#{roleMutexManageBean.checkMutex}"
//      reRender="codedb24467a-c009-4c73-92d2-5727f69dfcb1,fspMsgPanel" styleClass="btn"/>
//----------------------------------------------------------------*/
function startProgressToHtmlButton(obj)
{
	startProgress();
	obj.nextSibling.click();
}



function setCookie(key,value)
{
	
	var the_date = new Date("December 31, 2020"); 
	var expiresDate = the_date.toGMTString(); 
	document.cookie = key+"=" + value + ";expires=" + expiresDate; 
	
}
//201604 增加两个方法
//获取上下文名称
function getPageContext()
{
	
   var pathName=window.document.location.pathname;
   var index=pathName.substr(1).indexOf("/");
   var result=pathName.substr(0,index+1);
   return result;
}
//获取cookie值
function getCookieFun(name)
{
	var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;

}

//////////////////////////////////////////fsp直接搬过来的

function radioClick(nodeRadio,formName){ 
	
	formName = nodeRadio.name.split(":")[0];
	                                     
	var nodePostfix =  nodeRadio.name.substring(0,nodeRadio.name.lastIndexOf("::")-1);
	
	
    var formObject = getFormBySuffix(formName); 
    
    for(var i=0; i<formObject.elements.length; i++){
		var tmpNode = formObject.elements[i];
		if(tmpNode.name.indexOf(nodePostfix) < 0){
		    continue;
		}
		
		if(nodeRadio.name == tmpNode.name){
		    continue; 
		}
		tmpNode.checked = false;
    }
	
	/** by xuej , abuilder create id .
	var nodePostfix = nodeRadio.name.split("::")[1];
    var formObject = getFormBySuffix(formName); 
    for(var i=0; i<formObject.elements.length; i++){
		var tmpNode = formObject.elements[i];
		if(tmpNode.name.indexOf(nodePostfix) < 0){
		    continue;
		}
		if(nodeRadio.name == tmpNode.name){
		    continue; 
		}
		tmpNode.checked = false;
    }
    
    */
}

function getFormBySuffix(formNameSuffix){
    var allForms = document.forms;
    for(var i=0; i<allForms.length; i++){
        if(allForms[i].name.indexOf(":"+formNameSuffix) != -1){
            return allForms[i];
        }
    }
    return document.getElementById(formNameSuffix); 
}
function cleanErrorInteger(obj)
{
	if ((obj.value != '') && !isIntegerValue(obj.value))
	{
		obj.value = '';
	}
}

function isIntegerValue(ivalue)
{
	if (ivalue - ivalue != 0)
	{
		return false;
	}
	if ((ivalue > 2147483647) || (ivalue < -2147483648))
	{
		return false;
	}
	return true;
}
/** 
 * 根据dto的info信息判断输入框的长度类型和小数位数，此方法是失去焦点时调用
*/
function infoValidateOut(type, len, digit, obj)
{
 
	if (type != "Integer" && digit != "null")
	{
		var value = obj.value;
		// 整数部分
		var part1 = len - digit;
		var pot = value.indexOf(".");
		if (pot == 0)
		{
			obj.value = "0." + value;

		}
		else if (pot == -1 || pot == obj.value.length - 1)
		{
			if (pot == obj.value.length - 1)
			{
				value = value.substr(0, obj.value.length - 1);
			}
			// 小数点补位
			var z = ".";
			for (j = 0; j < digit; j = j + 1)
			{
				z = z + "0";
			}
			if (value.length > part1)
			{
				obj.value = value.substr(0, part1) + z;

			}
			else
			{
				obj.value = value + z;
			}
		}

	}
}
/** 
 * 根据dto的info信息判断输入框的长度类型和小数位数，此方法是按键盘事件时调用
*/
function infoValidate(type, len, digit, obj)
{
	var value = obj.value;
	var flag = true;
	// 整型校验
	if (type == "Integer")
	{
		for (i = 0; i < value.length; i = i + 1)
		{
			var c = value.charAt(i);
			var checkNum = (type == "Integer") && (c >= '0' && c <= '9');
			if (!checkNum)
			{
				flag = false;
				break;
			}

		}
		if (!flag)
		{
			// 有字符就清空
			obj.value = "";
			return;
		}
	}
	// 小数判断
	if (type != "Integer" && digit != "null")
	{
		if (isNaN(value))
		{
			obj.value = "";
			return;
		}

		var pot = value.indexOf(".");
		var intpartlen = len - digit;
		if (pot == -1)
		{
			if (value.length > intpartlen)
			{
				obj.value = value.substr(0, intpartlen);
			}
		}
		else
		{
			var vv = value.split(".");
			if (vv.length == 2)
			{
				var b = vv[0];
				var a = vv[1];
				if (b.length > intpartlen)
					b = b.substr(0, intpartlen);
				if (a.length > digit)
					a = a.substr(pot + 1, digit);
				obj.value = b + "." + a;
			}
		}

		if (value.indexOf(".") > 0 && value.indexOf(".") < value.length - 1)
		{
			len = len + 1;
		}
	}
	// 长度校验,超长的截取

	if (strlen(value) > len)
	{
		if (type == "String")
		{
			obj.value = value.substr(0, len / 2);
		}
		else
		{
			obj.value = value.substr(0, len);
		}
	}
}
function deleteTip(formid, tableid, rowindex, buttonid)
{
	var id = formid + ":" + tableid + ":" + rowindex + ":" + buttonid;
	if (confirm("\u662f\u5426\u5220\u9664\uff1f"))
	{
		var obj = document.all[id].click();

	}

}

function deleteAllTip(button)
{

	if (confirm("\u662f\u5426\u5220\u9664\uff1f"))
	{
		document.all[button].click();
	}

}


function downloadLog() {
	var filed = document
			.getElementById("formsearchTable:codeexport");
	var filename = filed.value;
	if (filename != null && filename != "") {
//		if(IEJudge(8)){
			window
			.location.href=basePath+"/pages/ExportFileDownloadServlet?file=" + filename;
//		}else{
//			window
//			.open(basePath+"/pages/ExportFileDownloadServlet?file=" + filename,
//			"downloadlog");
//		}
	}
	filed.value = "";
}
function IEJudge(num){
	var browser = navigator.appName;
    var b_version = navigator.appVersion;
    var version = b_version.split(";");
    if (version.length > 1) {
            var trim_Version = parseInt(version[1].replace(/[ ]/g, "").replace(/MSIE/g, ""));
            if (trim_Version == num) {
                return true;
            }
        }
    return false;
}

//导出，但不删除
function downloadLog2(filename,flag) {
	if (filename != null && filename != "") {
//		if(IEJudge(8)){
			window
			.location.href=basePath+"/pages/ExportFileDownload2Servlet?file=" + filename;
//		}else{
//			window
//					.open(basePath+"/pages/ExportFileDownload2Servlet?file=" + filename,
//							"_blank");
//		}
	}
	else{
		if(!flag){
			alert("\u6ca1\u6709\u5bfc\u51fa\u6587\u4ef6!");
		}
	}
}

//EXCEL导入-二部begin
function cleanProgressExcel(btnID,cleanTime){
	   _cleanID = setTimeout("_cleanProgressBarExcel('"+btnID+"')",cleanTime);
	}
function _cleanProgressBarExcel(btnID){
    stopProgress();
    if(_cleanID != undefined){
        clearTimeout(_cleanID);
    }
    if(btnID != undefined){
        document.all(btnID).click();
    }
}
//EXCEL导入二部end
//EXCEL导入二部end
/**
 * 打印 areaid打印内容标签的id
 */
function printme(areaid) {

	try {
		if (document.getElementById(areaid) != null) {

			var oldstr = document.body.innerHTML;
			document.body.innerHTML = document.getElementById(areaid).innerHTML;
			window.print();
			document.body.innerHTML = oldstr;
		}
	} catch (err) {
	}

}
//折叠richpanel
function foldRichPanel(head) {
	try {

		//鼠标点击的当前对象
		var curhead = window.event.srcElement;
		//当前panel对象
		var str=head.id;
		//获取当前panel的head对象
		var header = document
				.getElementById(str+"_header");
		//如果鼠标点击的对象正好是当前panel的head对象
	if (curhead.id == header.id)
		{
              //当前要被折叠的区域
			var obj = document
					.getElementById(str+"_body");
           //如果折叠了则打开
			if (obj.className == "foldRichPanel")
				obj.className = "rich-panel-body";
			//如果展开了就折叠
			else
				obj.className = "foldRichPanel";
		} 

	} catch (err) {
		//alert(err.toLocaleString());
	}
}

//若初始化panel为折叠则调用
function initfoldRichPanel(panel,flag)
{ 
	
	//若折叠
	if(flag=="true")
	{
		//当前panel对象
		var str=panel.id;
		//当前要被折叠的区域
		var obj = document
				.getElementById(str+"_body");
		obj.className = "foldRichPanel";
	}
}


/* 
 * 让列表的title随着滚动条走
 * 支持单列表的title滚动
 * 支持ie，chrome，暂不支持firefox
 * */
var globaldirection;
if(document.addEventListener){
	
	document.addEventListener('DOMMouseScroll',checkDirection,false);
}
window.onmousewheel=document.onmousewheel=checkDirection;
function checkDirection(e)
{
	
		 var direction;
		 var ee=e|| window.event;
		 if(e && e.wheelDelta){//IE/Opera/Chrome
			 if(e.wheelDelta>0)
			 {
				 direction="up";
			 }else
			 {
				 direction="down";
			 }
		 }else if(e && e.detail){//Firefox
			 if(e.detail>0)
			 {
				 direction="down";
			 }else
			 {
				 direction="up";
			 }
		 }
		 globaldirection=direction;
		
		 
}
window.onscroll=scrollTableTitle;
 function scrollTableTitle() {
	 try{

		var $=jQuery.noConflict();
		
		var top = document.documentElement.scrollTop || document.body.scrollTop;
		
		var activeobj=getActiveTable();
		
		if(!activeobj)return; //当前页面没有要滚动的列表，即没有<rich:dataTable/>控件
		var trtop = activeobj.children("th").offset().top;
		var tdtop = activeobj.offset().top;
        var tableHeight=(activeobj.parent().parent()).height();
       
		
		var lastRecord=(tableHeight+tdtop)-top;
		if (top >= tdtop) {
			activeobj.children("th").css({
				"background-color" :"#e8f1f6",
				"position" :"relative",
				"top" :(top - tdtop)
			});
	     if(lastRecord <= 0) 
           {
	    	 activeobj.children("th").css({
					"position" :""
				});
           }
		} else {
			activeobj.children("th").css( {
				"position" :""
			});
		
		}
	
	 }catch(error)
	 {
		 //console.log(error);
	 }
}


function getActiveTable() {
	var $ = jQuery.noConflict();
	var activetable;
	var overtopTable;

	
	    activetable=$(".rich-table-subheader");
	    if(activetable.length==1)return activetable;//如果只有一个table就不需要判断当前是哪个table 需要移动title了
		var index=0;
	 	while(index<activetable.length)
	 	{
	 		var curActiveTh=$(activetable[index]);
	 		var tableHeight=curActiveTh.parent().parent().height();
	 		var top=curActiveTh.offset().top;
	 		var topwindow = document.documentElement.scrollTop || document.body.scrollTop;
	 		
	 	   if(topwindow-top>0 && (topwindow-(tableHeight+top)<=0))
	 		{
	 		  activetable=curActiveTh;
	 		  break;
	 		}
	 	   index++;
	 	}
	
	
	return activetable;
}

//-->
