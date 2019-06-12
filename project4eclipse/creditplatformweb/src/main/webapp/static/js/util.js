function openDialog(url,wid,hei) {
	if (wid=="" || wid==undefined) {
		wid = '800';
	}
	if (hei=="" || hei==undefined) {
		hei = '600';
	}
	return window.showModalDialog(url, window,
		'dialogWidth:'+wid+'px;dialogHeight:'+hei+'px;status:no;help:no;scroll:yes;resizable:yes;');
}
function openDialog1024(url) {
	return openDialog(url,'1000','700');
	//window.open(url,"_blank","scrollbars=1,height=400,width=800,status=yes,toolbar=no,menubar=no,location=no","false");
	//window.open (url,'newwindow','height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') ; 
}
function openWindow1024(url) {
	
	window.open(url,'','height=700,width=1000,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=yes, status=no') ;
}
function openWindow600(url) {
	window.open (url, "", "height=400, width=600, top=100px,left=300px,titlebar=no, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=yes, status=no") 

}

function openDialogNew(url) {
	return window.showModalDialog(url, window,
		'dialogWidth:550px;dialogHeight:530px;status:no;help:no;scroll:no');
}
function openWindow(url) {
	window.open(url);
}
function getDateTime() {
	var d = new Date();
	return d.getTime();
}

var rightChooseFlag = true;
function moveDept(unusefulNames,uerfulNames,boolea){
	if (!rightChooseFlag) {
		return;
	}
	if(boolea){
		var index=unusefulNames.selectedIndex;
		while(index>=0){
			var toMove=unusefulNames.options[index];
			var option=new Option(toMove.text,toMove.value);
			unusefulNames.remove(index);
			uerfulNames.add(option);
			index=unusefulNames.selectedIndex;
		}
	}else{
		var index=uerfulNames.selectedIndex;
		while(index>=0){
			var toMove=uerfulNames.options[index];
			var option=new Option(toMove.text,toMove.value);
			uerfulNames.remove(index);
			unusefulNames.add(option);
			index=uerfulNames.selectedIndex;
		}
	}
}

// 根据选择的权限，选中其相关父子权限
function selParentChildrenRight(contextPath, selObj) {
	var str = "";
	var selOpts = selObj.options;
	var selLength = selObj.length;
	for (var i=0; i<selLength; i++) {
		if (selOpts[i].selected) {
			str += selOpts[i].value + ",";
		}
	}
	document.getElementById("btnRightChoose").disabled = true;
	rightChooseFlag = false;
	$.ajax({url : contextPath + "/sm_right_role_choose_load_right.do",
			type: "post",
			data: "selData="+str,
			success:function(data){
				try {
				eval(data);
				var rdLength = ajaxData.length;
				if (rdLength > 0) {
					// alert(ajaxData);	
					for (var i=0; i<selLength; i++) {
						for (var j=0; j<rdLength; j++) {
							if (selOpts[i].value == ajaxData[j]) {
								selOpts[i].selected = true; 
							}
						}
					}
				}
				} catch (e) {}
				rightChooseFlag = true;
				document.getElementById("btnRightChoose").disabled = false;
			}
		});
}

function validateChecked(msg){
	var size=jQuery(document.forms[0]).find('input:radio[name="ids"]:checked').size();
	if(size==0){
		alert(msg);
		return false;
	}
	return true;
}

function selCheckedAllForCheckbox(checkVal, checkName) {
	if (checkName=="" || checkName==undefined) {
		checkName = "ids";
	}
	$("input[name='"+checkName+"']").attr("checked",checkVal);
	//alert($('input:checkbox[name="ids"]'));	
}

function getCheckedValForCheckbox(checkName) {
	if (checkName=="" || checkName==undefined) {
		checkName = "ids";
	}
	var str="";
	$("input[name='"+checkName+"']:checked").each(function(){
		str+=$(this).val()+",";
	});
	
	return str;
	
}

function chekMoney(obj){
	 var filter=/^[0-9]*(.[0-9]{1,2})?$/;
	 if(!filter.test(obj.value)){
		 return true;
	 }
}

function chekNum(obj){
	var filter = /^[0-9]*$/;
	if(!filter.test(obj.value)){
		return true;
	}
}

function initCalendar(valId,valFmt) {
	var myCalendar = new dhtmlXCalendarObject([valId]);
	if (valFmt=="" || valFmt==undefined) {
		myCalendar.setDateFormat("%Y%m%d");
	} else {
		myCalendar.setDateFormat(valFmt);
	}	
}

function onChangeZjlx(selObj,txtObj) {
	if (selObj.value=='19' || selObj.value=='29') {
		txtObj.style.display = 'block';
	} else {
		txtObj.style.display = 'none';
	}
	txtObj.value = selObj.value;
}

function textChangeSelect(txtVal,selObj) {
	//selObj..attr("selected",true);
	//alert(txtVal);
	//alert(selObj.options[1].value)
	for (var i=0;i<selObj.options.length;i++) {
		if (selObj.options[i].value.toUpperCase().indexOf(txtVal.toUpperCase())==0) {
			selObj.options[i].selected=true;
			return;
		} else {
			selObj.options[i].selected=false;
		}
	}
	selObj.options[0].selected=true;
}

function selectChangeText(txtVal,txtId) {
	document.getElementById(txtId).value=txtVal;
}

function linkChangeTextSelect(txtVal,txtId,SelId) {
	selectChangeText(txtVal,txtId);
	textChangeSelect(txtVal,document.getElementById(SelId));
}

function selectCodeToText(txtVal,selId) {
	//$('#'+selId+' option[value="'+txtVal+'"]').text();
	if (txtVal.length==9) {
		alert(txtVal.substr(0,3));
		alert(txtVal.substr(3,9));
	} else if (txtVal.length>=3) {
	
	} else {
		
	}
}

function cadCodeToText(txtObj,countryId,districtId,labId) {
	var txtVal = txtObj.value.toUpperCase();
	if (txtVal.length==9) {
		//$('#'+countryId+' option[value="'+txtVal.substr(0,3)+'"]').text()
		if (txtVal.indexOf('CHN')==0 || 
			txtVal.indexOf('Z01')==0 || 
			txtVal.indexOf('Z02')==0 ||
			txtVal.indexOf('Z03')==0) {
			$('#'+labId).html($('#'+districtId+' option[value="'+txtVal.substr(3,9)+'"]').text());
		}  else {
			$('#'+labId).html("");
		}
	//} else if (txtVal.length==3) {
	//	if (txtVal!='CHN') {
	//		//txtVal = txtVal + '000000';
	//		$('#'+labId).html("");
	//	}
	//} else if (txtVal.length>3 && txtVal.indexOf('CHN')==0) {
	//	$('#'+labId).html($('#'+districtId+' option[value="-1"]').text());
	} else {
		$('#'+labId).html("");
	}
	//txtObj.value = txtVal;
}
function districtCodeToText(txtObj,districtId,labId) {
	var txtVal = txtObj.value;
	if (txtVal.length==6) {
		$('#'+labId).html($('#'+districtId+' option[value="'+txtVal+'"]').text());
	} else {
		$('#'+labId).html("");
	}
}

function Chinese(num){
    if(!/^\d*(\.\d*)?$/.test(num))throw(new Error(-1, "Number is wrong!"));     
    var AA = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖");     
    var BB = new Array("","拾","佰","仟","萬","億","圆","");     
    var CC = new Array("角", "分", "厘");     
    var a = (""+ num).replace(/(^0*)/g, "").split("."), k = 0, re = "";     
    for(var i=a[0].length-1; i>=0; i--){     
        switch(k){     
            case 0 : re = BB[7] + re; break;     
            case 4 : if(!new RegExp("0{4}\\d{"+ (a[0].length-i-1) +"}$").test(a[0]))     
                         re = BB[4] + re; break;     
            case 8 : re = BB[5] + re; BB[7] = BB[5]; k = 0; break;     
        }     
        if(k%4 == 2 && a[0].charAt(i)=="0" && a[0].charAt(i+2) != "0") re = AA[0] + re;     
        if(a[0].charAt(i) != 0) re = AA[a[0].charAt(i)] + BB[k%4] + re; k++;     
    }     
    if(a.length>1){     
        re += BB[6];     
        for(var i=0; i<a[1].length; i++){     
            re += AA[a[1].charAt(i)] + CC[i];     
            if(i==2) break;     
        }   
        if(a[1].charAt(0)=="0" && a[1].charAt(1)=="0"){  
            re+="元整";  
        }    
    }else{  
        re+="元整";  
    }     
    return re;     
} 

/**
 * 人民币金额大小写转化
 */

function ChangeToBig(value)
{
        var intFen,i;
        var strArr,strCheck,strFen,strDW,strNum,strBig,strNow;
        if (value == null)   //数据非法时提示，并返回空串
        {
                strErr = "数据"+value+"非法！"
                alert(strErr);
                return "";
        }
        if(value==0)
                return "零";
        strCheck = value+".";
        strArr = strCheck.split(".");
        strCheck = strArr[0];
        if(strCheck.length>12)   //数据大于等于一万亿时提示无法处理
        {
                strErr = "数据"+value+"过大，无法处理！"
                alert(strErr);
                return "";
        }
        try
        {
                i = 0;
                strBig = "";
                intFen = value*100;          //转换为以分为单位的数值
                strFen = intFen.toString();
                strArr = strFen.split(".");
                strFen = strArr[0];
                intFen = strFen.length;      //获取长度
                strArr = strFen.split("");//将各个数值分解到数组内
                while(intFen!=0)   //分解并转换
                {
                        i = i+1;
                        switch(i)              //选择单位
                        {
                                case 1:strDW = "分";break;
                                case 2:strDW = "角";break;
                                case 3:strDW = "元";break;
                                case 4:strDW = "拾";break;
                                case 5:strDW = "佰";break;
                                case 6:strDW = "仟";break;
                                case 7:strDW = "万";break;
                                case 8:strDW = "拾";break;
                                case 9:strDW = "佰";break;
                                case 10:strDW = "仟";break;
                                case 11:strDW = "亿";break;
                                case 12:strDW = "拾";break;
                                case 13:strDW = "佰";break;
                                case 14:strDW = "仟";break;
                        }
                        switch (strArr[intFen-1])              //选择数字
                        {
                                case "1":strNum = "壹";break;
                                case "2":strNum = "贰";break;
                                case "3":strNum = "叁";break;
                                case "4":strNum = "肆";break;
                                case "5":strNum = "伍";break;
                                case "6":strNum = "陆";break;
                                case "7":strNum = "柒";break;
                                case "8":strNum = "捌";break;
                                case "9":strNum = "玖";break;
                                case "0":strNum = "零";break;
                        }
                        //处理特殊情况
                        strNow = strBig.split("");
                        //分为零时的情况
                        if((i==1)&&(strArr[intFen-1]=="0"))
                                strBig = "整";
                        //角为零时的情况
                        else if((i==2)&&(strArr[intFen-1]=="0"))
                        {    //角分同时为零时的情况
                                if(strBig!="整")
                                strBig = "零"+strBig;
                        }
                        //元为零的情况
                        else if((i==3)&&(strArr[intFen-1]=="0"))
                                strBig = "元"+strBig;
                        //拾－仟中一位为零且其前一位（元以上）不为零的情况时补零
                        else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="元"))
                                strBig = "零"+strBig;
                        //拾－仟中一位为零且其前一位（元以上）也为零的情况时跨??
                        else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零"))
                        {} 
                        //拾－仟中一位为零且其前一位是元且为零的情况时跨过
                        else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="元"))
                        {}
                        //当万为零时必须补上万字
                        else if((i==7)&&(strArr[intFen-1]=="0"))
                               strBig ="万"+strBig;
                        //拾万－仟万中一位为零且其前一位（万以上）不为零的情况时补零
                        else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="万"))
                                strBig = "零"+strBig;
                        //拾?颍蛑幸晃晃闱移淝耙晃唬ㄍ蛞陨希┮参愕那榭鍪笨绻?
                        else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="万"))
                        {}
                        //拾万－仟万中一位为零且其前一位为万位且为零的情况时跨过
                        else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零"))
                        {}
                        //万位为零且存在仟位和十万以上时，在万仟间补零
                        else if((i<11)&&(i>8)&&(strArr[intFen-1]!="0")&&(strNow[0]=="万")&&(strNow[2]=="仟"))
                        strBig = strNum+strDW+"万零"+strBig.substring(1,strBig.length);
                        //单独处理亿位
                        else if(i==11)
                        {
                                //亿位为零且万全为零存在仟位时，去掉万补为零
                                if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]=="仟"))
                                        strBig ="亿"+"零"+strBig.substring(1,strBig.length);
                                //亿位为零且万全为零不存在仟位时，去掉万
                                else if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]!="仟"))
                                        strBig ="亿"+strBig.substring(1,strBig.length);
                                //亿位不为零且万全为零存在仟位时，去掉万补为零
                                else if((strNow[0]=="万")&&(strNow[2]=="仟"))
                                        strBig = strNum+strDW+"零"+strBig.substring(1,strBig.length);
                                //亿位不为零且万全为零不存在仟位时，去掉万
                                else if((strNow[0]=="万")&&(strNow[2]!="仟"))
                                        strBig = strNum+strDW+strBig.substring(1,strBig.length);
                                        
                                        //其他正常情况
                                else
                                        strBig = strNum+strDW+strBig;
                        }
                        //拾亿－仟亿中一位为零且其前一位（亿以上）不为零的情况时补零
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="亿"))
                                strBig = "零"+strBig;
                        //拾亿－仟亿中一位为零且其前一位（亿以上）也为零的情况时跨过
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="亿"))
                        {}
                        //拾亿－仟亿中一位为零且其前一位为亿位且为零的情况时跨过
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零"))
                        {}
                        //亿位为零且不存在仟万位和十亿以上时去掉上次写入的??
                        else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]!="仟"))

                                strBig = strNum+strDW+strBig.substring(1,strBig.length);

                        //亿位为零且存在仟万位和十亿以上时，在亿仟万间补零

                        else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]=="仟"))

                                strBig = strNum+strDW+"亿零"+strBig.substring(2,strBig.length);

                        else

                                strBig = strNum+strDW+strBig;
                                strFen = strFen.substring(0,intFen-1);
                                intFen = strFen.length;
                                strArr = strFen.split("");
                }
                return strBig;
        }catch(err){
                return "";      //若失败则返回原值
        }
}

function isDate(valObj,len) {
	//var val = valObj.value.replace(/t/g," ");
	//alert("|"+val+"|");
	var val = valObj.value;
	if (val=='') {
		return false;
	}
	var filter = /^[0-9]*$/;
	if(!filter.test(valObj.value.substring(0,8))){
		return true;
	}
	if (val.length<len) {
		return true;
	}
	if ((val.length%2)!=0) {
		return true;
	}
	var year = val.substring(0,4);
	var month = val.substring(4,6);
	var day = val.substring(6,8);
	var h = val.substring(8,10);
	var m = val.substring(10,12);
	var s = val.substring(12,14);
	//alert('year:'+year+';month:'+month+';day:'+day+';h:'+Number(h)+';m:'+m+';s:'+s);
	if (val.length>=8) {
		if(Number(year) < 1900 || Number(month) > 12 || Number(day) > 31 ||Number(month)<1 || Number(day)<1 ||  year.length != 4) {
           return true;
	    } else  if(day > getLastDayOfMonth(Number(year),Number(month))) {
           return true;
        }
	}
	if (val.length>=10) {
		if (!filter.test(h) || h=='' || Number(h)>23) {
    		return true;
    	}
	}
	if (val.length>=12) {
		if (!filter.test(m) || m=='' || Number(m)>59) {
    		return true;
    	}
	}
	if (val.length>=14) {
		if (!filter.test(s) || s=='' || Number(s)>59) {
    		return true;
    	}
	}
	return false;
}

//如果输入的内容中包含‘-’，则按照‘-’分割来去年月日，否则直接按照位数取
function checkSpecialChar(inputDateValue) {
    var index = inputDateValue.indexOf('-');
    var year = 0;
    var month = 0;
    var day = 0;
    var h = 0;
    var m = 0;
    var s = 0;
    if(index > -1) {
       var lastIndex = inputDateValue.lastIndexOf('-');
       //只能是YYYY-M-DD或者YYYY-MM-DD的形式
       if(lastIndex - index < 1 || lastIndex - index > 3) {
           return false;
       }
       var arrDate = inputDateValue.split('-');
           year = arrDate[0];
           month = arrDate[1];
           day = arrDate[2];
       } else {
           year = inputDateValue.substring(0,4);
           month = inputDateValue.substring(4,6);
           day = inputDateValue.substring(6,8);
           h = inputDateValue.substring(8,10);
           m = inputDateValue.substring(10,12);
           s = inputDateValue.substring(12,14);
           alert('year:'+year+';month:'+month+';day:'+day+';h:'+Number(h)+';m:'+m+';s:'+s);
       }
       if(Number(month) > 12 || Number(day) > 31 ||Number(month)<1
                           || Number(day)<1 ||  year.length != 4) {
           return false;
    } else  if(day > getLastDayOfMonth(Number(year),Number(month))) {
           return false;
    } else if (h.length=='' || Number(h)>23) {
    	return false;
    } else if (m.length=='' || Number(m)>59) {
    	return false;
    } else if (s.length=='' || Number(s)>59) {
    	return false;
    }
    return true;
}

//获得一个月中的最后一天
function getLastDayOfMonth(year,month){
    var days=0;
    switch(month){
    case 1: case 3: case 5: case 7: case 8: case 10: case 12: days=31;break;
    case 4: case 6: case 9: case 11: days=30;break;
    case 2: if(isLeapYear(year)) days=29;else days=28;break;
    }
    return days;
}

//判断是否为闰年
function isLeapYear(year){
    if((year %4==0 && year %100!=0) || (year %400==0))
		return true;
    else
    	return false;
}

function getByteLength(str) {
	return str.replace(/[^\x00-\xff]/g,"**").length;
}