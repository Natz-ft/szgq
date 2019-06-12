var digits = "0123456789";
var lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
var uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
var whitespace = " \t\n\r";
var defaultEmptyOK = true;

function isMoney(strTemp) {
	strTemp = Trim(strTemp);
	var mark = "";
	for (i = 0; i < strTemp.length; i++) {
		if ((strTemp.charCodeAt(i) < 43) || (strTemp.charCodeAt(i) > 57)
				|| (strTemp.charCodeAt(i) == 44)
				|| (strTemp.charCodeAt(i) == 47)) {
			alert("\u8f93\u5165\u6570\u503c\u4e2d\u542b\u6709\u975e\u6cd5\u5b57\u7b26\u9519\u8bef\uff01");
			return false;
		}
	}

	var s1 = strTemp.lastIndexOf("+");
	var s2 = strTemp.lastIndexOf("-");
	if ((s1 > 0) || (s2 > 0)) {
		alert("\u6b63\u8d1f\u53f7\u7684\u4f4d\u7f6e\u548c\u6570\u91cf\u4e0d\u5bf9\uff01");
		return false;
	}
	if ((s1 > -1) && (s2 > -1)) {
		alert("\u6b63\u8d1f\u53f7\u4e0d\u80fd\u540c\u65f6\u51fa\u73b0\uff01");
		return false;
	}

	if (strTemp.indexOf("-") == 0) {
		mark = "-";
	}
	if ((strTemp.indexOf("-") == 0) || (strTemp.indexOf("+") == 0)) {
		strTemp = strTemp.substring(1, strTemp.length);
	}

	var i = a = b = c = d = e = 0;
	d = strTemp.length;

	if ((strTemp.length == 1) && (strTemp.charCodeAt(i) == 46)) {
		alert("\u8f93\u5165\u6570\u503c\u9519\u8bef\uff01");
		return false;
	}
	if ((strTemp.length == 1) && (strTemp.charCodeAt(i) == 43)) {
		alert("\u8f93\u5165\u6570\u503c\u9519\u8bef\uff01");
		return false;
	}
	if ((strTemp.length == 1) && (strTemp.charCodeAt(i) == 45)) {
		alert("\u8f93\u5165\u6570\u503c\u9519\u8bef\uff01");
		return false;
	}

	if (strTemp.charCodeAt(0) == 46) {
		alert("\u8f93\u5165\u6570\u503c\u9519\u8bef\uff01");
		return false;
	}

	if (strTemp.charCodeAt(strTemp.length - 1) == 46) {
		alert("\u8f93\u5165\u6570\u503c\u9519\u8bef\uff01");
		return false;
	}

	for (i = 0; i < strTemp.length; i++) {
		if (b > 1) {
			alert("\u8f93\u5165\u6570\u503c\u9519\u8bef\uff01");
			return false;
		}
		if (strTemp.charCodeAt(i) == 46) {
			b = b + 1;
		}
	}

	for (i = 0; i < strTemp.length; i++) {
		if (strTemp.charCodeAt(i) == 46) {
			c = i + 1;
		}
	}

	if ((c != 0) && (d - c > 2)) {
		alert("\u5c0f\u6570\u540e\u53ea\u8f93\u51652\u4f4d");
		return false;
	}

	if (c > (16 + 1)) {
		alert("\u5c0f\u6570\u70b9\u524d\u53ea\u8f93\u516516\u4f4d");
		return false;
	}
	if (mark != "") {
		strTemp = "-" + strTemp;
	}
	return true;
}

function isEmptyToo(s) {
	return ((s == null) || (s.length == 0));
}

function isWhitespace(s) {
	var i;
	if (isEmptyToo(s)) {
		return true;
	}
	for (i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (whitespace.indexOf(c) == -1) {
			return false;
		}
	}
	return true;
}

function stripCharsInBag(s, bag) {
	var i;
	var returnString = "";
	for (i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (bag.indexOf(c) == -1) {
			returnString += c;
		}
	}
	return returnString;
}
function stripCharsNotInBag(s, bag) {
	var i;
	var returnString = "";
	for (i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (bag.indexOf(c) != -1) {
			returnString += c;
		}
	}
	return returnString;
}
function stripWhitespace(s) {
	return stripCharsInBag(s, whitespace);
}

function charInString(c, s) {
	for (i = 0; i < s.length; i++) {
		if (s.charAt(i) == c) {
			return true;
		}
	}
	return false;
}

function stripInitialWhitespace(s) {
	var i = 0;
	while ((i < s.length) && charInString(s.charAt(i), whitespace)) {
		i++;
	}
	return s.substring(i, s.length);
}
function isLetter(c) {
	return (((c >= "a") && (c <= "z")) || ((c >= "A") && (c <= "Z")));
}

function isDigit(c) {
	return ((c >= "0") && (c <= "9"));
}

function isLetterOrDigit(c) {
	return (isLetter(c) || isDigit(c));
}
function isTel(c) {
	return (((c >= "0") && (c <= "9")) || (c == "-"));
}

function isInteger(s) {
	var i;
	if (isEmptyToo(s)) {
		if (isInteger.arguments.length == 1) {
			return defaultEmptyOK;
		} else {
			return (isInteger.arguments[1] == true);
		}
	}
	for (i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (!isDigit(c)) {
			return false;
		}
	}
	return true;
}

function isSignedInteger(s) {
	if (isEmptyToo(s)) {
		if (isSignedInteger.arguments.length == 1) {
			return defaultEmptyOK;
		} else {
			return (isSignedInteger.arguments[1] == true);
		}
	} else {
		var startPos = 0;
		var secondArg = defaultEmptyOK;
		if (isSignedInteger.arguments.length > 1) {
			secondArg = isSignedInteger.arguments[1];
		}
		if ((s.charAt(0) == "-") || (s.charAt(0) == "+")) {
			startPos = 1;
		}
		return (isInteger(s.substring(startPos, s.length), secondArg));
	}
}

function isPositiveInteger(s) {
	var secondArg = defaultEmptyOK;
	if (isPositiveInteger.arguments.length > 1) {
		secondArg = isPositiveInteger.arguments[1];
	}
	return (isSignedInteger(s, secondArg) && ((isEmptyToo(s) && secondArg) || (parseInt(
			s, 10) > 0)));
}

function isNonnegativeInteger(s) {
	var secondArg = defaultEmptyOK;
	if (isNonnegativeInteger.arguments.length > 1) {
		secondArg = isNonnegativeInteger.arguments[1];
	}
	return (isSignedInteger(s, secondArg) && ((isEmptyToo(s) && secondArg) || (parseInt(
			s, 10) >= 0)));
}

function isNegativeInteger(s) {
	var secondArg = defaultEmptyOK;
	if (isNegativeInteger.arguments.length > 1) {
		secondArg = isNegativeInteger.arguments[1];
	}
	return (isSignedInteger(s, secondArg) && ((isEmptyToo(s) && secondArg) || (parseInt(
			s, 10) < 0)));
}

function isNonpositiveInteger(s) {
	var secondArg = defaultEmptyOK;
	if (isNonpositiveInteger.arguments.length > 1) {
		secondArg = isNonpositiveInteger.arguments[1];
	}
	return (isSignedInteger(s, secondArg) && ((isEmptyToo(s) && secondArg) || (parseInt(
			s, 10) <= 0)));
}

function isFloat(s) {
	var i;
	var seenDecimalPoint = false;
	if (isEmptyToo(s)) {
		if (isFloat.arguments.length == 1) {
			return defaultEmptyOK;
		} else {
			return (isFloat.arguments[1] == true);
		}
	}
	if (s == ".") {
		return false;
	}
	for (i = 0; i < s.length; i++) {
		// Check that current character is number.
		var c = s.charAt(i);
		if ((c == ".") && !seenDecimalPoint) {
			seenDecimalPoint = true;
		} else {
			if (!isDigit(c)) {
				return false;
			}
		}
	}
	return true;
}

function isSignedFloat(s) {
	if (isEmptyToo(s)) {
		if (isSignedFloat.arguments.length == 1) {
			return defaultEmptyOK;
		} else {
			return (isSignedFloat.arguments[1] == true);
		}
	} else {
		var startPos = 0;
		var secondArg = defaultEmptyOK;
		if (isSignedFloat.arguments.length > 1) {
			secondArg = isSignedFloat.arguments[1];
		}

		if ((s.charAt(0) == "-") || (s.charAt(0) == "+")) {
			startPos = 1;
		}
		return (isFloat(s.substring(startPos, s.length), secondArg));
	}
}

function isDecimal(s, m, n) {
	if (!isFloat(s)) {
		return false;
	}
	if (String(parseInt(s, 10)).length > m - n) {
		return false;
	}
	var ss = String(parseFloat(s));
	if (ss.indexOf(".") >= 0
			&& ss.substring(ss.indexOf(".") + 1, ss.length).length > n) {
		return false;
	}
	return true;
}

function isAlphabetic(s) {
	var i;
	if (isEmptyToo(s)) {
		if (isAlphabetic.arguments.length == 1) {
			return defaultEmptyOK;
		} else {
			return (isAlphabetic.arguments[1] == true);
		}
	}
	for (i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (!isLetter(c)) {
			return false;
		}
	}
	return true;
}
function isAlphanumeric(s) {
	var i;
	if (isEmptyToo(s)) {
		if (isAlphanumeric.arguments.length == 1) {
			return defaultEmptyOK;
		} else {
			return (isAlphanumeric.arguments[1] == true);
		}
	}
	for (i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (!(isLetter(c) || isDigit(c))) {
			return false;
		}
	}
	return true;
}

function toValidateDate(str, format) {
	if (strlen(str) <= 0) {
		return true;
	}
	if (str.length != format.length) {
		return false;
	}
	var year = 2000;
	var month = 1;
	var day = 1;
	var hour = 0;
	var minute = 0;
	var second = 0;
	if (format.indexOf("yyyy") != -1) {
		if (isNaNEx(year = SearchEx(str, format, "yyyy"))) {
			return false;
		}
		format = format.replace(/yyyy/, year);
	}
	if (format.indexOf("MM") != -1) {
		if (isNaNEx(month = SearchEx(str, format, "MM"))) {
			return false;
		}
		format = format.replace(/MM/, month);
	}
	if (format.indexOf("dd") != -1) {
		if (isNaNEx(day = SearchEx(str, format, "dd"))) {
			return false;
		}
		format = format.replace(/dd/, day);
	}
	if (format.indexOf("HH") != -1) {
		if (isNaNEx(hour = SearchEx(str, format, "HH"))) {
			return false;
		}
		if (parseInt(hour, 10) < 0 || parseInt(hour, 10) > 23) {
			return false;
		}
		format = format.replace(/HH/, hour);
	}
	if (format.indexOf("mm") != -1) {
		if (isNaNEx(minute = SearchEx(str, format, "mm"))) {
			return false;
		}
		if (parseInt(minute, 10) < 0 || parseInt(minute, 10) > 59) {
			return false;
		}
		format = format.replace(/mm/, minute);
	}
	if (format.indexOf("ss") != -1) {
		if (isNaNEx(second = SearchEx(str, format, "ss"))) {
			return false;
		}
		if (parseInt(second, 10) < 0 || parseInt(second, 10) > 59) {
			return false;
		}
		format = format.replace(/ss/, second);
	}
	if (format != str) {
		return false;
	}
	return isValidDate(year, month, day);
}
function isNaNEx(str) {
	if (str == "") {
		return true;
	}
	if (isNaN(str)) {
		return true;
	}
	if (str.indexOf(".") != -1) {
		return true;
	}
	return false;
}
function SearchEx(source, pattern, str) {
	var index = pattern.indexOf(str);
	if (index == -1) {
		return "error";
	}
	return source.substring(index, index + str.length);
}
function isValidDate(year, month, day) {
	if (Trim(year) == "") {
		return false;
	}
	if (Trim(year).length != 4) {
		return false;
	}
	year = parseInt(year);
	if (year < 1000) {
		return false;
	}

	if (Trim(month) == "") {
		return false;
	}
	if (Trim(month).length != 2) {
		return false;
	}
	month = parseInt(month, 10);

	if (Trim(day) == "") {
		return false;
	}

	if (Trim(day).length != 2) {
		return false;
	}
	day = parseInt(day, 10);

	if (month < 1 || month > 12) {
		return false;
	}
	if (day < 1 || day > 31) {
		return false;
	}
	if ((month == 4 || month == 6 || month == 9 || month == 11) && (day == 31)) {
		return false;
	}
	if (month == 2) {
		var leap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		if (day > 29 || (day == 29 && !leap)) {
			return false;
		}
	}
	return true;
}

function isResidentID(s) {
	if (s.length != 15 && s.length != 18) {
		return false;
	}
	if (!isAlphanumeric(s)) {
		return false;
	}
	var birthday;
	if (s.length == 18) {
		if (!isInteger(s.substring(0, s.length - 1))) {
			return false;
		}
		birthday = s.substring(6, 14);
	}
	if (s.length == 15) {
		if (!isInteger(s)) {
			return false;
		}
		birthday = "19" + s.substring(6, 12);
	}
	var year_tmp = parseInt(birthday.substring(0, 4), 10);
	if (year_tmp <= 1000 || year_tmp >= 3000) {
		return false;
	}
	if (!isValidDate(birthday.substring(0, 4), birthday.substring(4, 6),
			birthday.substring(6, 8))) {
		return false;
	}
	return true;
}

function reformat(s) {
	var arg;
	var sPos = 0;
	var resultString = "";
	for (var i = 1; i < reformat.arguments.length; i++) {
		arg = reformat.arguments[i];
		if (i % 2 == 1) {
			resultString += arg;
		} else {
			resultString += s.substring(sPos, sPos + arg);
			sPos += arg;
		}
	}
	return resultString;
}

function isPhoneNumber(s) {
	if (stripCharsInBag(s, "0123456789-()+") != "") {
		return false;
	}
	return true;
}

function isPostCode(s) {
	return (isInteger(s) && strlen(s) == 6);
}

function isvalidEmailChar(s) {
	var i;
	for (i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (!(isLetter(c) || isDigit(c) || (c == "@") || (c == ".")
				|| (c == "_") || (c == "-") || (c == "+"))) {
			return false;
		}
	}
	return true;
}

function isEmail(s) {
	if (isEmptyToo(s)) {
		if (isEmail.arguments.length == 1) {
			return defaultEmptyOK;
		} else {
			return (isEmail.arguments[1] == true);
		}
	}
	if (isWhitespace(s)) {
		return false;
	}
	if (!isvalidEmailChar(s)) {
		return false;
	}
	atOffset = s.lastIndexOf("@");
	if (atOffset < 1) {
		return false;
	} else {
		dotOffset = s.indexOf(".", atOffset);
		if (dotOffset < atOffset + 2 || dotOffset > s.length - 2) {
			return false;
		}
	}
	return true;
}

function isChecked(checkbox_name) {
	var items = checkbox_name.length;
	if (items > 1) {
		for (i = 0; i < items; i++) {
			if (checkbox_name[i].checked == true) {
				return true;
			}
		}
	} else {
		if (checkbox_name.checked == true) {
			return true;
		}
	}
	return false;
}

function isCheckedOne(checkbox_name) {
	var items = checkbox_name.length;
	var count = 0;
	if (items > 1) {
		for (i = 0; i < items; i++) {
			if (checkbox_name[i].checked == true) {
				count++;
			}
		}
		if (count == 1) {
			return true;
		} else {
			return false;
		}
	} else {
		if (checkbox_name.checked == true) {
			return true;
		} else {
			return false;
		}
	}
}

function checkAll(checkbox_names, checkbox_select) {
	var items = checkbox_names.length;
	if (items > 1) {
		for (i = 0; i < items; i++) {
			checkbox_names[i].checked = checkbox_select.checked;
		}
	} else {
		checkbox_names.checked = checkbox_select.checked;
	}
}

function selectUnique(theForm) {
	for (i = 0; i < theForm.elements.length; i++) {
		e = theForm.elements[i];
		if (e.type == "select-one" && !e.isDisabled) {
			for (j = 0; j < theForm.elements.length; j++) {
				e2 = theForm.elements[j];
				if (e2.type == "select-one" && !e2.isDisabled) {
					if (e != e2
							&& e.options[e.selectedIndex].value == e2.options[e2.selectedIndex].value) {
						e.focus();
						return false;
					}
				}
			}
		}
	}
	return true;
}

function isCreditCard(st) {
	if (st.length > 19) {
		return (false);
	}
	sum = 0;
	mul = 1;
	l = st.length;
	for (i = 0; i < l; i++) {
		digit = st.substring(l - i - 1, l - i);
		tproduct = parseInt(digit, 10) * mul;
		if (tproduct >= 10) {
			sum += (tproduct % 10) + 1;
		} else {
			sum += tproduct;
		}
		if (mul == 1) {
			mul++;
		} else {
			mul--;
		}
	}
	if ((sum % 10) == 0) {
		return (true);
	} else {
		return (false);
	}
}
function isVisa(cc) {
	if (((cc.length == 16) || (cc.length == 13)) && (cc.substring(0, 1) == 4)) {
		return isCreditCard(cc);
	}
	return false;
}
function isMasterCard(cc) {
	firstdig = cc.substring(0, 1);
	seconddig = cc.substring(1, 2);
	if ((cc.length == 16) && (firstdig == 5)
			&& ((seconddig >= 1) && (seconddig <= 5))) {
		return isCreditCard(cc);
	}
	return false;
}
function isAmericanExpress(cc) {
	firstdig = cc.substring(0, 1);
	seconddig = cc.substring(1, 2);
	if ((cc.length == 15) && (firstdig == 3)
			&& ((seconddig == 4) || (seconddig == 7))) {
		return isCreditCard(cc);
	}
	return false;
}
function isDinersClub(cc) {
	firstdig = cc.substring(0, 1);
	seconddig = cc.substring(1, 2);
	if ((cc.length == 14) && (firstdig == 3)
			&& ((seconddig == 0) || (seconddig == 6) || (seconddig == 8))) {
		return isCreditCard(cc);
	}
	return false;
}
function isCarteBlanche(cc) {
	return isDinersClub(cc);
}
function isDiscover(cc) {
	first4digs = cc.substring(0, 4);
	if ((cc.length == 16) && (first4digs == "6011")) {
		return isCreditCard(cc);
	}
	return false;
}
function isEnRoute(cc) {
	first4digs = cc.substring(0, 4);
	if ((cc.length == 15) && ((first4digs == "2014") || (first4digs == "2149"))) {
		return isCreditCard(cc);
	}
	return false;
}
function isJCB(cc) {
	first4digs = cc.substring(0, 4);
	if ((cc.length == 16)
			&& ((first4digs == "3088") || (first4digs == "3096")
					|| (first4digs == "3112") || (first4digs == "3158")
					|| (first4digs == "3337") || (first4digs == "3528"))) {
		return isCreditCard(cc);
	}
	return false;
}
function isAnyCard(cc) {
	if (!isCreditCard(cc)) {
		return false;
	}
	if (!isMasterCard(cc) && !isVisa(cc) && !isAmericanExpress(cc)
			&& !isDinersClub(cc) && !isDiscover(cc) && !isEnRoute(cc)
			&& !isJCB(cc)) {
		return false;
	}
	return true;
}
function isCardMatch(cardType, cardNumber) {
	cardType = cardType.toUpperCase();
	var doesMatch = true;
	if ((cardType == "VISA") && (!isVisa(cardNumber))) {
		doesMatch = false;
	}
	if ((cardType == "MASTERCARD") && (!isMasterCard(cardNumber))) {
		doesMatch = false;
	}
	if (((cardType == "AMERICANEXPRESS") || (cardType == "AMEX"))
			&& (!isAmericanExpress(cardNumber))) {
		doesMatch = false;
	}
	if ((cardType == "DISCOVER") && (!isDiscover(cardNumber))) {
		doesMatch = false;
	}
	if ((cardType == "JCB") && (!isJCB(cardNumber))) {
		doesMatch = false;
	}
	if ((cardType == "DINERS") && (!isDinersClub(cardNumber))) {
		doesMatch = false;
	}
	if ((cardType == "CARTEBLANCHE") && (!isCarteBlanche(cardNumber))) {
		doesMatch = false;
	}
	if ((cardType == "ENROUTE") && (!isEnRoute(cardNumber))) {
		doesMatch = false;
	}
	return doesMatch;
}
function IsCC(st) {
	return isCreditCard(st);
}
function IsVisa(cc) {
	return isVisa(cc);
}
function IsVISA(cc) {
	return isVisa(cc);
}
function IsMasterCard(cc) {
	return isMasterCard(cc);
}
function IsMastercard(cc) {
	return isMasterCard(cc);
}
function IsMC(cc) {
	return isMasterCard(cc);
}
function IsAmericanExpress(cc) {
	return isAmericanExpress(cc);
}
function IsAmEx(cc) {
	return isAmericanExpress(cc);
}
function IsDinersClub(cc) {
	return isDinersClub(cc);
}
function IsDC(cc) {
	return isDinersClub(cc);
}
function IsDiners(cc) {
	return isDinersClub(cc);
}
function IsCarteBlanche(cc) {
	return isCarteBlanche(cc);
}
function IsCB(cc) {
	return isCarteBlanche(cc);
}
function IsDiscover(cc) {
	return isDiscover(cc);
}
function IsEnRoute(cc) {
	return isEnRoute(cc);
}
function IsenRoute(cc) {
	return isEnRoute(cc);
}
function IsJCB(cc) {
	return isJCB(cc);
}
function IsAnyCard(cc) {
	return isAnyCard(cc);
}
function IsCardMatch(cardType, cardNumber) {
	return isCardMatch(cardType, cardNumber);
}

var UPPER_NUMBER = "\u58f9\u8d30\u53c1\u8086\u4f0d\u9646\u67d2\u634c\u7396\u96f6";
var UPPER_BIT = "\u5206\u89d2\u5706\u62fe\u4f70\u4edf\u4e07\u62fe\u4f70\u4edf\u4ebf\u62fe\u4f70\u4edf";


function clipMoney(lowerMoney) {
	var integer, deci;
	if (isFloat(lowerMoney) == false) {
		return "false";
	}
	if (lowerMoney.indexOf(".") == -1) {
		integer = lowerMoney + "00";
		integer = convertLowerMoney(integer);
		if (integer == "false") {
			return "false";
		}
		if (integer.indexOf("\u5706") == -1) {
			return integer + "\u5706\u6574";
		} else {
			return integer;
		}
	} else {
		integer = lowerMoney.substring(0, lowerMoney.indexOf(".")) + "00";
		if (integer == "false") {
			return "false";
		}
		integer = convertLowerMoney(integer);
		if (integer.indexOf("\u5706") == -1) {
			integer = integer + "\u96f6";
		}
		deci = "0."
				+ lowerMoney.substring(lowerMoney.indexOf(".") + 1,
						lowerMoney.length);
		if (deci == "false") {
			return "false";
		}
		deci = convertLowerMoney(deci);
		if (deci.indexOf("\u5206") == -1) {
			return integer + deci + "\u6574";
		}
		return integer + deci;
	}
}
function convertLowerMoney(lowerMoney, dotlength) {
	var upperMoney = "";
	if (lowerMoney.indexOf(".") != -1) {
		lowerMoney = lowerMoney.substring(0, lowerMoney.indexOf(".") + 3);
		var str = lowerMoney * 100 + "";
		if (str.indexOf(".") != -1) {
			str = str.substring(0, str.indexOf("."));
		}
	} else {
		var str = lowerMoney;
	}
	var len = str.length;

	if (len > 14) {
		return "false";
	}
	var j, k;
	for (var i = 1; i <= len; i++) {
		j = len - i;

		var bit = UPPER_BIT.substring(j, j + 1);
		k = parseInt(str.substring(i - 1, i));
		if (k == 0) {
			k = 10;
		}
		var number = UPPER_NUMBER.substring(k - 1, k);
		if ("\u96f6" == (number)) {
			if (bit == "\u4ebf" || bit == "\u4e07") {
				upperMoney = upperMoney + bit;
			} else {
				upperMoney = upperMoney + number;
			}
		} else {
			upperMoney = upperMoney + number + bit;
		}
	}
	upperMoney = removeZero(upperMoney);
	upperMoney = dealYiWan(upperMoney);
	upperMoney = removeZero(upperMoney);
	if (upperMoney.substring(upperMoney.length - 1, upperMoney.length) == "\u96f6") {
		upperMoney = upperMoney.substring(0, upperMoney.length - 1);
	}
	return upperMoney;
}
function removeZero(str) {
	var sb = "";
	var old = "a";
	for (var i = 0; i < str.length; i++) {
		if ((str.charAt(i) == old) && (old == "\u96f6")) {

		} else {
			old = str.charAt(i);
			sb = sb + old;
		}
	}
	return sb;
}
function dealYiWan(str) {
	var sb = "";
	var old = "a";
	for (var i = 0; i < str.length; i++) {
		if ((str.charAt(i) == "\u4ebf") && (old == "\u96f6")) {
			sb.deleteCharAt(sb.length - 1);
			sb = sb + str.charAt(i) + "\u96f6";
		} else {
			if ((str.charAt(i) == "\u4e07") && (old == "\u96f6")) {
				sb = sb.substring(0, sb.length - 1);
				if (sb.charAt(sb.length - 1) == "\u4ebf") {
					sb = sb + "\u96f6";
				} else {
					sb = sb + str.charAt(i) + "\u96f6";
				}
			} else {
				old = str.charAt(i);
				sb = sb + old;
			}
		}
	}
	return sb;
}

function changeDate(date, s) {
	var formatType = "YYYY-MM-DD";
	var type, j, month, day, year;
	s = s + "";
	if (s != "undefined") {
		formatType = s;
	}
	if (formatType.length != date.length) {
		return false;
	}
	type = formatType.charAt(0);
	c = -1;
	for (var i = 0; i < formatType.length; i++) {
		c = c + 1;
		if (type != formatType.charAt(i)) {
			if (type == "M") {
				month = date.substring(i - c, i);
				c = 0;
			} else {
				if (type == "D") {
					day = date.substring(i - c, i);
					c = 0;
				} else {
					if (type == "Y") {
						year = date.substring(i - c, i);
						c = 0;
					} else {
						c = 0;
					}
				}
			}
		} else {
			if (i == formatType.length - 1) {
				if (type == "M") {
					month = date.substring(i - c, i + 1);
				}
				if (type == "D") {
					day = date.substring(i - c, i + 1);
				}
				if (type == "Y") {
					year = date.substring(i - c, i + 1);
				}
			}
		}
		type = formatType.charAt(i);
	}
	year = figureChange(year);
	month = figureChange(month);
	day = figureChange(day);
	return year + "\u5e74" + month + "\u6708" + day + "\u65e5";
}
function figureChange(fig) {
	var UPPER_NUMBER = "\u25cb\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d";
	var sb = "";
	var re = "";
	if (fig.length == 2) {
		re = fig.charAt(0);
		if (re == "0") {
			sb = "";
		} else {
			if (re == "1") {
				sb = "\u5341";
			} else {
				sb = UPPER_NUMBER.charAt(re) + "\u5341";
			}
		}
		re = fig.charAt(1);
		if (re != "0") {
			sb = sb + UPPER_NUMBER.charAt(re);
		}
	} else {
		for (var i = 0; i < fig.length; i++) {
			sb = sb + UPPER_NUMBER.charAt(fig.charAt(i));
		}
	}
	return sb;
}

function strlen(str) {
	var len;
	var i;
	len = 0;
	for (i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) > 255) {
			len += 2;
		} else {
			len++;
		}
	}
	return len;
}

function LTrim(str) {
	var whitespace = new String(" \t\n\r");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(0)) != -1) {
		var j = 0, i = s.length;
		while (j < i && whitespace.indexOf(s.charAt(j)) != -1) {
			j++;
		}
		s = s.substring(j, i);
	}
	return s;
}

function RTrim(str) {
	var whitespace = new String(" \t\n\r");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(s.length - 1)) != -1) {
		var i = s.length - 1;
		while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1) {
			i--;
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
			j++;
		}
		s = s.substring(j, i);
	}
	return s;
}

function greatthanCurDate(s) {
	var valueArray = new Array();
	valueArray = s.split("-");
	var year = parseInt(valueArray[0], 10);
	var month = parseInt(valueArray[1], 10);
	var day = parseInt(valueArray[2], 10);
	var date2 = new Date(year, month - 1, day);
	if (date2 > new Date()) {
		return true;
	} else {
		return false;
	}
}
function lessthanCurDate(s) {
	var valueArray = new Array();
	valueArray = s.split("-");
	var year = parseInt(valueArray[0], 10);
	var month = parseInt(valueArray[1], 10);
	var day = parseInt(valueArray[2], 10);
	var date2 = new Date(year, month - 1, day);
	alert(date2);
	alert(new Date());
	if (date2 < new Date()) {
		return true;
	} else {
		return false;
	}
}

function getTodayString() {
	var date = new Date();
	var today = "";
	var year = "";
	var month = "";
	var day = "";
	year = date.getFullYear();
	month = parseInt(date.getMonth()) + 1;
	month = month + "";
	day = date.getDate();
	if (month.length == 1) {
		month = "0" + month + "";
	}
	if (parseInt(day) < 10) {
		day = "0" + day + "";
	}
	today = year + "-" + month + "-" + day;
	return today;
}
function getLoginTodayString() {
	var calFormat = "yyyymmdd";
	var date = calFormat, d = new Date();
	date = date.replace(/yyyy/i, d.getFullYear());
	date = date.replace(/mm/i, get2Digits(d.getMonth() + 1));
	date = date.replace(/dd/i, get2Digits(d.getDate()));
	return date;
}
function get2Digits(n) {
	return ((n < 10) ? "0" : "") + n;
}
function docheckall() {
	for (var i = 0; i < document.forms[0].elements.length; i++) {
		var e = document.forms[0].elements[i];
		if (e.type == "checkbox") {
			e.checked = true;
		}
	}
}

function compareDate1GreatDate2(date1, str1, date2, str2) {
//	alert("a");
	if (date1 == null || date1 == "" || date2 == null || date2 == "") {
		return true;
	}
	var mydate1 = geneatorDate(date1);
	var mydate2 = geneatorDate(date2);
	if (mydate1 - mydate2 > 0) {
		return true;
	} else {
//		alert(str1 + "\u4e0d\u80fd\u5c0f\u4e8e\u6216\u7b49\u4e8e" + str2 + "!");
		return false;
	}
}

function compareDate1GreatEqualDate2(date1, str1, date2, str2) {

	// alert(date1+"in"+date2);
	if (date1 == null || date1 == "" || date2 == null || date2 == "") {
		return true;
	}
	var mydate1 = geneatorDate(date1);
	var mydate2 = geneatorDate(date2);
	if (mydate1 - mydate2 >= 0) {
		return true;
	} else {
		alert(str1 + "\u4e0d\u80fd\u5c0f\u4e8e" + str2 + "!");
		return false;
	}
}

function compareDate1GreatEqualDate2ByWP(date1, str1, date2, str2) {
	if (date1 == null || date1 == "" || date2 == null || date2 == "") {
		return true;
	}
	var mydate1 = geneatorDate(date1);
	var mydate2 = geneatorDate(date2);
	if (mydate1 - mydate2 >= 0) {
		return true;
	} else {
		return false;
	}
}

function compareDate1LitteDate2(date1, str1, date2, str2) {
	if (date1 == null || date1 == "" || date2 == null || date2 == "") {
		return true;
	}
	var mydate1 = geneatorDate(date1);
	var mydate2 = geneatorDate(date2);
	if (mydate1 - mydate2 < 0) {
		return true;
	} else {
		alert(str1 + "\u4e0d\u80fd\u5927\u4e8e\u6216\u7b49\u4e8e" + str2 + "!");
		return false;
	}
}

function compareDate1LitteEqualDate2(date1, str1, date2, str2) {
	if (date1 == null || date1 == "" || date2 == null || date2 == "") {
		return true;
	}
	var mydate1 = geneatorDate(date1);
	var mydate2 = geneatorDate(date2);
	if (mydate1 - mydate2 <= 0) {
		return true;
	} else {
		alert(str1 + "\u4e0d\u80fd\u5927\u4e8e" + str2 + "!");
		return false;
	}
}

function geneatorDate(datestr) {
	if (datestr == null || datestr == "") {
		return datestr;
	}
	var datestrt = new String(datestr);
	var syear = datestrt.substring(0, datestrt.indexOf("-"));
	var smonth = datestrt.substring(datestrt.indexOf("-") + 1, datestrt
					.lastIndexOf("-"));
	var sdate = datestrt.substring(datestrt.lastIndexOf("-") + 1,
			datestrt.length);
	return new Date(syear, smonth - 1, sdate);
}

function getRealByte(strVal) {
	if (strVal == null) {
		return 0;
	}
	return strVal.replace(/[^\x00-\xff]/gi, "xx").length;
}

function inMaxlength(strVal, nMaxlen) {
	return getRealByte(strVal) <= nMaxlen;
}

function isNumber(obj) {
	if (!isPhoneNumber(obj.value)) {
		alert("\u8bf7\u5f55\u5165\u6570\u5b57!");
		obj.value = "";
		obj.focus();
		return false;
	}
}

function checkDynamicPanel(dform) {

	for (var i = 0; i < dform.elements.length; i++) {
		var e = dform.elements[i];
		var sname = e.name;

		var len = sname.lastIndexOf("0");
		if (len > 0) {
			var smsg = sname.substr(len + 1);
			var sid = sname.substr(0, len);
			if (sname.lastIndexOf("radio") != -1) {
				var finalid = sid.substr(0, len - 5);
				var rd = document.all[finalid];
				if (!checkRadioValue(rd)) {
					alert("\u8bf7\u9009\u62e9" + smsg);
					return false;
				}
			} else if (sname.lastIndexOf("chbox") != -1) {
				var finalid = sid.substr(0, len - 5);
				var rd = document.all[finalid];
				if (!checkRadioValue(rd)) {
					alert("\u8bf7\u9009\u62e9" + smsg);
					return false;
				}
			} else {
				var o = document.all(sid);
				var strs = o.value;

				if (isWhitespace(strs)) {
					alert(smsg + "\u4e0d\u80fd\u4e3a\u7a7a!");
					return false;
				}
			}
		}
	}
	return true;
}

function checkRadioValue(rd) {

	for (var i = 1; i < rd.length; i++) {
		if (rd[i].checked) {
			return true;
		}
	}
	return false;

}

function checkPositiveInteger(obj) {
	if (!isPositiveInteger(obj.value)) {
		alert("\u53ea\u80fd\u662f\u5927\u4e8e0\u7684\u6b63\u6574\u6570\uff01");
		obj.value = "";
		obj.focus();
		return false;
	}
}

function isDate(dateStr) {
	dateStr = Trim(dateStr);
	var tempStr = "1234567890-";
	for (var i = 0; i < dateStr.length; i++) {
		var aa = dateStr.charAt(i);
		if (tempStr.indexOf(aa) == -1) {
			return false;
			break;
		}
	}
	var aa = new Array();
	aa = dateStr.split("-");
	if (aa.length != 3) {
		return false;
	}
	if (dateStr.length != 10) {
		return false;
	}
	if (aa[0].length != 4) {
		return false;
	}
	if (aa[1].length != 2) {
		return false;
	}
	if (aa[2].length != 2) {
		return false;
	}
	var is = 0;
	if ((aa[0] % 4 == 0) && (aa[0] % 100 != 0)) {
		is = 1;
	} else if (aa[0] % 400 == 0) {
		is = 1;
	}
	tempStrMonth = "01,02,03,04,05,06,07,08,09,10,11,12";
	if (tempStrMonth.indexOf(aa[1]) == -1) {
		return false;
	}
	tempStrDay = "01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31";
	if (tempStrDay.indexOf(aa[2]) == -1) {
		return false;
	}
	var _tempStrMonth = "01,03,05,07,08,10,12";
	if (_tempStrMonth.indexOf(aa[1]) == -1) {
		if (aa[1] == "31") {
			return false;
		}
	}
	if (aa[1] == "02") {
		if (aa[2] == 30) {
			return false;
		}
	}
	if (is == 0 && aa[1] == "02") {
		if (aa[2] == 29) {
			return false;
		}
	}
	return true;
}

function validateCfsmDate(istime, startdate, enddate, sigledate) {
	if (istime == '5') {
		if (isWhitespace(startdate)) {
			alert("\u8d77\u59cb\u65e5\u671f\u4e0d\u80fd\u4e3a\u7a7a,\u8bf7\u9009\u62e9\u8d77\u59cb\u65e5\u671f!");
			return false;
		} else {
			startdate = allTrim(startdate);
			if (!toValidateDate(startdate, "yyyy-MM-dd")) {
				alert("\u8d77\u59cb\u65e5\u671f\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u683c\u5f0f\u4e3ayyyy-MM-dd\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165!");
				return false;
			}
		}
		if (isWhitespace(enddate)) {
			alert("\u622a\u6b62\u65e5\u671f\u4e0d\u80fd\u4e3a\u7a7a,\u8bf7\u9009\u62e9\u622a\u6b62\u65e5\u671f!");
			return false;
		} else {
			enddate = allTrim(enddate);
			if (!toValidateDate(enddate, "yyyy-MM-dd")) {
				alert("\u622a\u6b62\u65e5\u671f\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u683c\u5f0f\u4e3ayyyy-mm-dd\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165!");
				return false;
			}
		}

		if (startdate != null || startdate != '' || enddate != null
				|| enddate != '') {
			var mybt = geneatorDate(startdate);
			var myet = geneatorDate(enddate);
			if (mybt - myet > 0) {
				alert("\u8d77\u59cb\u65e5\u671f\u4e0d\u80fd\u5927\u4e8e\u622a\u6b62\u65e5\u671f!");
				return false;
			}
		}
	} else {
		if (isWhitespace(sigledate)) {
			alert("\u65e5\u671f\u4e0d\u80fd\u4e3a\u7a7a,\u8bf7\u9009\u62e9\u65e5\u671f!");
			return false;
		} else {
			sigledate = allTrim(sigledate);
			if (!toValidateDate(sigledate, "yyyy-MM-dd")) {
				alert("\u65e5\u671f\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u683c\u5f0f\u4e3ayyyy-MM-dd\uff0c\u8bf7\u91cd\u65b0\u5f55\u5165!");
				return false;
			}
		}
	}
	return true;
}

function allTrim(text) {
	text = text.replace(/\s/ig, '');
	return text;
}

function checkFloat(obj) {
	if (!isBigDecimal(obj.value, 15, 2)) {
		alert("\u8bf7\u5f55\u5165\u6570\u503c\u578b\u6570\u636e,\u6570\u503c\u7684\u6574\u6570\u90e8\u5206\u81f3\u5c11\u5e94\u67091\u4f4d,\u4f46\u4e0d\u80fd\u8d85\u8fc715\u4f4d,\u5c0f\u6570\u90e8\u5206\u4e0d\u80fd\u8d85\u8fc72\u4f4d!");
		obj.focus();
		return false;
	}
	return true;
}

function checkSpecialChar(str) {
	if (strlen(str) > 0) {
		if (str.indexOf("%") >= 0) {
			return true;
		}
		if (str.indexOf("％") >= 0) {
			return true;
		}
		if (str.indexOf("_") >= 0) {
			return true;
		}
		if (str.indexOf(ascii2native("\uff3f")) >= 0) {
			return true;
		}
		if (str.indexOf("'") >= 0) {
			return true;
		}
		if (str.indexOf("\"") >= 0) {
			return true;
		}
		if (str.indexOf("#") >= 0) {
			return true; 
		}
		if (str.indexOf(">") >= 0) {
			return true; 
		}
		if (str.indexOf("<") >= 0) {
			return true; 
		}if (str.indexOf(";") >= 0) {
			return true; 
		}
	}
	return false;
}

function checkSpecialCharWithPrompt(str) {
	if (strlen(str) > 0) {
		if (str.indexOf("%") >= 0) {
			return "\u5185\u5bb9\u4e0d\u80fd\u5305\u542b\u767e\u5206\u53f7";
		}
		if (str.indexOf("％") >= 0) {
			return "\u5185\u5bb9\u4e0d\u80fd\u5305\u542b\u767e\u5206\u53f7";
		}
		if (str.indexOf("_") >= 0) {
			return "\u5185\u5bb9\u4e0d\u80fd\u5305\u542b\u4e0b\u5212\u7ebf";
		}
		if (str.indexOf(ascii2native("\uff3f")) >= 0) {
			return "\u5185\u5bb9\u4e0d\u80fd\u5305\u542b\u4e0b\u5212\u7ebf";
		}
		if (str.indexOf("'") >= 0) {
			return "\u5185\u5bb9\u4e0d\u80fd\u5305\u542b\u82f1\u6587\u5355\u5f15\u53f7";
		}
		if (str.indexOf("\"") >= 0) {
			return "\u5185\u5bb9\u4e0d\u80fd\u5305\u542b\u82f1\u6587\u53cc\u5f15\u53f7";
		}
		if (str.indexOf("#") >= 0) {
			return "内容不能包含#"; 
		}
		if (str.indexOf(">") >= 0) {
			return "内容不能包含大于号"; 
		}
		if (str.indexOf("<") >= 0) {
			return "内容不能包含小于号"; 
		}if (str.indexOf(";") >= 0) {
			return "内容不能包含 ;"; 
		}
	}
	return "";
}

function inputTextValidate(obj) {
	var msg = checkSpecialCharWithPrompt(obj.value);
	if (strlen(msg) > 0) {
		obj.value = "";
		obj.focus();
		alert(msg);
		return false;
	}
}

function preventAheadTime(day) {
	var curDt = new Date();
	if (curDt.getTime() - day.date.getTime() >= 0) {
		return true;
	} else {
		return false;
	}
}

function disabledClassesProv(day) {
	var curDt = new Date();
	if (curDt.getTime() - day.date.getTime() >= 0) {
		return '';
	} else {
		return 'disableAheadDay'
	}
}

var hexChars = "0123456789ABCDEF";

function toHex(n) {
	var nH = (n >> 4) & 0x0f;
	var nL = n & 0x0f;
	return hexChars.charAt(nH) + hexChars.charAt(nL);
}

function native2ascii(strNative) {
	var output = "";
	for (var i = 0; i < strNative.length; i++) {
		var c = strNative.charAt(i);
		var cc = strNative.charCodeAt(i);
		if (cc > 0xff)
			output += "\\u" + toHex(cc >> 8) + toHex(cc & 0xff);
		else
			output += c;
	}
	return output;
}

function ascii2native(strAscii) {
	var output = "";
	var posFrom = 0;
	var posTo = strAscii.indexOf("\\u", posFrom);
	while (posTo >= 0) {
		output += strAscii.substring(posFrom, posTo);
		output += toChar(strAscii.substr(posTo, 6));
		posFrom = posTo + 6;
		posTo = strAscii.indexOf("\\u", posFrom);
	}
	output += strAscii.substr(posFrom);
	return output;
}

function toChar(str) {
	if (str.substr(0, 2) != "\\u")
		return str;
	var code = 0;
	for (var i = 2; i < str.length; i++) {
		var cc = str.charCodeAt(i);
		if (cc >= 0x30 && cc <= 0x39)
			cc = cc - 0x30;
		else if (cc >= 0x41 && cc <= 0x5A)
			cc = cc - 0x41 + 10;
		else if (cc >= 0x61 && cc <= 0x7A)
			cc = cc - 0x61 + 10;
		code <<= 4;
		code += cc;
	}
	if (code < 0xff)
		return str;
	return String.fromCharCode(code);
}
