$(function () {
    // 关注我们二维码
	$.ajax({
        type: "post",
        url: "/SRRPBusinesWeb/index/ajaxIsLogin",//url地址
        data: {"name": ""},// 查询条件
        async: false,//使用同步的方式,true为异步方式
        success: function (data) {
            data = JSON.parse(data);
           if(data.code=='00001'){
        	   document.getElementById("nologin").style.display="block";//隐藏
        	   if($("#logout").length>0){
            	   document.getElementById("logout").style.display="block";//隐藏
        	   }
           }else{
        	   document.getElementById("login").style.display="block";//隐藏
        	   if($("#success").length>0){
            	   document.getElementById("success").style.display="block";//隐藏
        	   }
           }
          
        }
    });
});