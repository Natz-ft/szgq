window.onload=function(){
	/**
	* 禁用backSpace退回上一页面的事件
	* @author:lirui
	* @history:2015-10-22
	* @history:2015-10-31 解决无法判定IE 11的bug
	*/
	if (window.navigator.userAgent.indexOf("MSIE") >= 1 || !!window.ActiveXObject || "ActiveXObject" in window){
		document.getElementsByTagName("body")[0].onkeydown=function(){
			var elem = event.relatedTarget || event.srcElement || event.target || event.currentTarget;
			if(event.keyCode == 8){
				var elem = event.srcElement || event.currentTarget;
				var name = elem.nodeName;
				
				if(name != 'INPUT' && name != 'TEXTAREA'){
					return _stopIt(event);
				}
				var type_e = elem.type.toUpperCase();
				if(name != 'INPUT' && (type_e != 'TEXT' && type_e != 'TEXTAREA' && type_e != 'PASSWORD' && type_e != 'FILE'
					&& type_e != 'DATEBOX' && type_e != 'COMBOBOX' && type_e != 'COMBOGRID' && type_e != 'COMBO')){
					return _stopIt(event);
				}
				
				if(elem.getAttribute('readonly') == true || elem.getAttribute('readonly') == 'readonly' || 
						elem.getAttribute('disabled') == true || elem.getAttribute('disabled') == 'disabled'){
					return _stopIt(event);
				}
			}
		}
	}
}
function _stopIt(event){
	if(event.returnValue){
		event.returnValue = false;
		event.onkeydown = false;
	}
	if(event.preventDefault){
		event.preventDefault();
		event.onkeydown = false;
	}
	return false;
}

$.fn.autoTip = function(G){
/**
 +------------------------------------------------------------------------------
 * input用户名自动提示插件
 * 参数
 * @input 入参
 *     json对象
 *     @ dvalue       input表单提示默认值
 *     @ tip          默认提示信息样式名class
 *     @ tipnone      在指定的input执行click时替换的样式名class
 +------------------------------------------------------------------------------
 *使用方法:
 * $("#xxx").autotip();
 * @ #xxx 为需要提示的input的id
 */
        var D;
        D = {
	        dvalue:"用户名/电子邮箱",//表单默认值
	        tip:"tip",             //默认提示信息样式名class
	        tipnone:"tipnone"      //在指定的input执行click时替换的样式名class
        };
        $.extend(D,G);
//        $(this).css("color","#d0d0d0");
        if ($.trim($(this).val())==""){
            $(this).val(D.dvalue).addClass(D.tip)
            .click(function(){
                if($(this).val()==D.dvalue){
                	$(this).val("");
                	$(this).removeClass(D.tip);
                	$(this).addClass(D.tipnone);
//                	$(this).css("color","#000000");
                	}
                })
            .blur(function(){
	            if($.trim($(this).val())==""){
	            	$(this).removeClass(D.tipnone);
	        		$(this).addClass(D.tip);
	              	$(this).val(D.dvalue);
//	              	$(this).css("color","#d0d0d0");
	            }
        });
    };
}


//设置jquery的ajax全局请求参数 ,处理ajax请求session过期的问题
$.ajaxSetup({
	cache:false,
	complete:function(XMLHttpRequest,textStatus){
	var sessionstatus = XMLHttpRequest.getResponseHeader('ajaxSessionStatus');
	if(sessionstatus=='ajaxTimeOut'){
//		alert(getRootPath());
		window.top.location.href = getRootPath();
		}
	}
});

//js获取项目根路径
function getRootPath(){
  //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
  var curPath=window.document.location.href;
  //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
  var pathName=window.document.location.pathname;
  var pos=curPath.indexOf(pathName);
  //获取主机地址，如： http://localhost:8083
  var localhostPath=curPath.substring(0,pos);
  //获取带"/"的项目名，如：/uimcardprj
  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
  return(localhostPath+projectName);
}