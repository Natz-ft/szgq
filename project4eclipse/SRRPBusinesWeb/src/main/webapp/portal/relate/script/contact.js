/*菜单栏联系我们点击事件*/

/*初始化联系我们点击前的样式*/
function contactusinit() {
    $("div[id*='contactus_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).css("display", "none");
    });
    $("a[id*='contactus_click_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).attr("class", "");
    });
}

/*公告通知点击事件*/
$(document).on("click", "#contactus_click_notice", function () {
    contactusinit();
    $("#contactus_notice").css("display", "inline");
    $("#contactus_click_notice").attr("class", "click-color");

    return false;
})

/*联系方式点击事件*/
$(document).on("click", "#contactus_click_contact", function () {
    contactusinit();
    $("#contactus_contact").css("display", "inline");
    $("#contactus_click_contact").attr("class", "click-color");
    return false;
})
/*在线提问点击事件*/
$(document).on("click", "#contactus_click_answer", function () {
    contactusinit();
    $("#contactus_answer").css("display", "inline");
    $("#contactus_click_answer").attr("class", "click-color");
    return false;
});

/*常见问题点击事件*/
$(document).on("click", "#contactus_click_question", function () {
    contactusinit();
    $("#contactus_question").css("display", "inline");
    $("#contactus_click_question").attr("class", "click-color");
    return false;
});
//监听折叠
layui.use(['element', 'layer'], function () {
    var element = layui.element;
    var layer = layui.layer;
    element.on('collapse(test)', function (data) {
//                layer.msg('展开状态：' + data.show);
    });
});
// 表单控件

function addInfos(formId){
	$.ajax({
		cache: true,
		type: "POST",
		url:"/SRRPBusinesWeb/contantUs/addOnlineForum",
		data:$('#'+formId).serialize(),// 你的formid
		async: false,
		success: function(data) {
			data = JSON.parse(data);
			if(data.msg =='sucessful'){
//				layer.msg("感谢您的提问，我们将尽快通过短信方式给您回复！");
				layer.alert('感谢您的提问，我们将尽快通过短信方式给您回复！', function () {
                    location.reload();
                });
//				$("#onlineForm").form("clear");
			}else{
				layer.msg("提交失败，请核查填入内容！");
			}
		},
		error: function(request) {
			layer.msg("提交失败，请稍后再试！");
		}
	});
}

layui.use('form', function() {
	var form = layui.form;// 表单控件
	    // 表单验证
	    form.verify({
	        //手机号校验
	    	contactnumber: function (value, item) {
	            if (value==""||value==null||value==undefined) {
	                return '手机号不能为空';
	            }
	            if (!/^[0-9]{11}$/.test(value)) {
	                return '请正确输入手机号码';
	            }
	        },
	        email: function (value, item) {
	            if (value==""||value==null||value==undefined) {
	                return '邮箱不能为空';
	            }
	            if (!/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/.test(value)) {
	                return '请正确输入邮箱';
	            }
	        },
	        certno: function (value, item) {
	            if (value!="" && value!=null && value!=undefined) {
	            	
	            	if(value.length == '18'){
	            		var objRegExp= /[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}/;

	    				var r = value.match(objRegExp); 
	    				if(r==null){
	    					 return '企业/机构证件号证件号格式不正确';
	    				}
	    			}else{
	    				var objRegExp= /[A-Za-z0-9]{8}-[A-Za-z0-9]/;
	    				var r = value.match(objRegExp); 
	    				if(r==null){
	    					 return '企业/机构证件号证件号格式不正确';

	    				}
	    			}
	            }
	        }
	    });
	    //监听提交
	    form.on('submit(onlineForm)', function (data) {
	    	addInfos('onlineForm');
	        return false;
	    });
})
