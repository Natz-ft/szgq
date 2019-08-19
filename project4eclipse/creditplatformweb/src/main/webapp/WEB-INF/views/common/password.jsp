<%@page import="java.io.InputStream"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ page import="com.icfcc.credit.platform.exception.CaptchaException"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="${ctx}/static/image/favicon1.ico" type="image/x-icon" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-control" content="no-cache">
    <meta http-equiv="Cache" content="no-cache">
    <title>苏州股权融资服务平台</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/style/reset.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/style/common.css">
    <script src="${ctx}/static/js/jquery-1.4.3.js" type="text/javascript"></script>
    <script src="${ctx}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
    <script src="${ctx}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.form.js"></script>
	<script src="static/js/md5.js" type="text/javascript"></script>
	<script src="static/js/util.js" type="text/javascript"></script>
	<link href="${ctx}/static/css/style-alert.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/js/jquery.form.manage.js" type="text/javascript" ></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/style/resetPassword.css"/>
   <link href="${ctx}/static/css/style-alert.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/js/jquery.form.manage.js" type="text/javascript" ></script>
    <script type="text/javascript">

    if (top.location != self.location) {
       top.location = self.location;
    }

    // 阻止浏览器的默认行为
    function stopDefault( e ) {
         //阻止默认浏览器动作(W3C)
         if ( e && e.preventDefault )
              e.preventDefault();
         //IE中阻止函数器默认动作的方式
         else
              window.event.returnValue = false;
              return false;
    }
    // 阻止事件冒泡
    function stopBubble(e) {
         //如果提供了事件对象，则这是一个非IE浏览器
        if ( e && e.stopPropagation )
        //因此它支持W3C的stopPropagation()方法
           e.stopPropagation();
        else
        //否则，我们需要使用IE的方式来取消事件冒泡
           window.event.cancelBubble = true;
    }
    </script>

    <script>
    $(function () {
         // 关注我们二维码
         $(".focusUs a").hover(function () {
            $(this).children("div").show();
             }, function () {
                $(this).children("div").hide();
         })
    });
    </script>
</head>
<body>
<!--头部logo以及登录注册开始-->
<div class="logo clearfix">
    <div class="logoRight">
        <a href="/SRRPBusinesWeb/portal/register.html"> 投资者注册 </a>
        <a href="login.jsp"> 登录 </a>
    </div>
    <div class="logoLeft">
        <a href="/SRRPBusinesWeb/portal/index.html">
            <p></p>
            <p></p>
        </a>
    </div>
</div>
<!--头部logo以及登录注册结束-->
<!-- 操作菜单开始 -->
<div class="header">
    <ul class="nav clearfix">
        <li class="active"><a href="/SRRPBusinesWeb/portal/index.html"> 首 页 </a></li>
        <li><a href="/SRRPBusinesWeb/portal/investevent.html"> 投融事件 </a></li>
        <li><a href="/SRRPBusinesWeb/portal/finace.html"> 融资需求 </a></li>
        <li><a href="/SRRPBusinesWeb/portal/news.html"> 新闻动态 </a></li>
        <li><a href="/SRRPBusinesWeb/portal/result.html"> 运行成果 </a></li>
        <li><a href="/SRRPBusinesWeb/portal/investor.html"> 投资机构 </a></li>
        <li><a href="/SRRPBusinesWeb/portal/relate.html"> 联系我们 </a></li>
    </ul>
</div>
<!-- 操作菜单结束-->
<!--头部信息结束-->
<!-- 位置说明开始 -->

<div class="main-background">
    <!--<div>&nbsp;</div>-->
    <div class="container">
        <div class="main">
                <div class="login-box">
                <div class="login-title">
                    <p>修改密码
                    <!-- <span>Login</span> -->
                    </p>
                </div>
                <form method="post" action="${ctx}/admin/user/finalPassword" id="change_form_id" name="change_form_id">
                   <div style="margin-top:10px;margin-left:18px;">
                    <span>原密码：</span>
                    <input id="oldpassword" class="validate[required]"   class="validate[required]" type="password" name="password" tabindex="2"/ >
                   </div>
                   <div style="margin-top:10px;margin-left:18px;">
                    <span>新密码：</span>
                    <input id="newpassword" type="password" class="validate[required,custom[complex]]" name="newpassword" tabindex="2"/>
                   </div>
                   <div style="margin-top:10px;margin-left:4px;">
                    <span>确认密码：</span>
                    <input id="confirm" type="password" name="new" tabindex="2" class="validate[confirm[newpassword]]"/>
                   </div>
                   <div class="loginButton">
                        <input type="button" class="button" onclick="change_submit('change_form_id')" value="确定"/>
                    </div>
                    
                </form>
            </div>
        </div>
    </div>
    <!--<div>&nbsp;</div>-->
</div>
<!-- 页面主题结束 -->
<!--底部信息开始-->
<div class="footer">
    <!-- 客服信息以及关注我们开始 -->
    <div class="contentAndFocus clearfix">
        <div class="footLeft">
            <span>客服热线（工作时间 9:00-17:00）</span>
            <p>68555158-6207、68555158-3105、68555158-5115</p>
            <span>客服邮箱 XXXX@XXXXX.com</span>
        </div>
        <div class="footRight">
            <div class="focusUs">
                <span>关注我们</span>
                <!-- <a id="weibo">
                    <div><p>扫描二维码<br>关注微博</p></div>
                </a> -->
                <a id="wechat">
                    <div><p>扫描二维码<br>关注微信</p></div>
                </a>
            </div>
            <div class="footNav">
                <ul class="clearfix">
                    <li><a href="index.html">首页</a></li>
                    <li><a href="#">运行成果</a></li>
                    <li><a href="investevent.html">投融事件</a></li>
                    <li><a href="news.html">政策指南</a></li>
                    <li><a href="relate.html">联系我们</a></li>
                </ul>
            </div>
            <!-- 客服信息以及关注我们结束 -->
        </div>
    </div>
    <!-- 版权声明信息开始 -->
    <div class="copyright">
        <p> COPYRIGHT 2015 www.szjrb.suzhou.gov.cn All Rights Reserved. 版权所有：苏州市地方金融监督管理局</p>
    </div>
    <!-- 版权声明信息结束 -->
</div>
<c:import url="/WEB-INF/views/system/user/show_message.jsp" />
<!--底部信息结束-->

<script type="text/javascript">
//MD5对密码加密传输
function MD5pwd(password){
	var prekey=$("#"+password).val();
	var hex=hex_md5(prekey);
	$("#"+password).val(hex);
}
function change_successprocess(data){
	if(data.code=="00000"){
		return true;
	}else{
		return false;
	}
}

function reset(formId) {
	$('#' + formId)[0].reset();
}


function change_submit(change_form_id) {

	if ($("#" + change_form_id).validationEngine({
		returnIsValid : true
	})) {
			MD5pwd('oldpassword');
			MD5pwd('newpassword');
			MD5pwd('confirm');
		  $("#" + change_form_id).ajaxSubmit({
			 dataType : 'json',
          success:function(data){
               var flag = change_successprocess(data);
              if (flag) {
              	location.href="${ctx}/success";
				 }else {
					param("提示信息","密码修改失败","原密码错误");
					$("#t1").fadeIn(200);
				}
          },error: function(){
              param("提示信息","系统错误，请重试","");
					$("#t1").fadeIn(200);
          }
      })
	 }
}
</script>
<script type="text/javascript">
    $(function(){
        var height = $(window).height() - $("body").height();
        $(".container").css({"padding": height/2+"px 0px"});
        if($(window).height()>900){
           $(".main-background").css({"background-size": "100%"}); 
        }
    })
</script>
</body>
</html>