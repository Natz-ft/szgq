<%@page import="java.io.InputStream"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ page import="com.icfcc.credit.platform.exception.CaptchaException"%>
<%@ page import="com.icfcc.credit.platform.exception.SuCompanyUserErrorException"%>
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
    <link rel="stylesheet" type="text/css" href="${ctx}/static/style/resetPwd.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/script/layui/css/layui.css">
    <script src="${ctx}/static/script/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/script/layui/layui.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/style/login.css"/>
	<script src="static/js/md5.js" type="text/javascript"></script>
	<script src="static/js/util.js" type="text/javascript"></script>

<script type="text/javascript">

	if (top.location != self.location) {
		top.location = self.location;
	}

	function validateLoginForm(fm) {
		if (fm.username.value == "") {
			//alert("请输入用户名！");
			fm.username.focus();
			return false;
		}
		if (fm.password.value == "") {
			//alert("请输入密码！");
			fm.password.focus();
			return false;
		}
		if (fm.captcha.value == "") {
			//alert("请输入验证码！");
			fm.captcha.focus();
			return false;
		}
		return true;
	}
	//MD5对密码加密传输
	function MD5pwd(){
		var password=$("#password").val();
		var hex=hex_md5(password);
		$("#password").val(hex);
	}
	// 登录
	function loginOn() {
		if (validateLoginForm(loginForm)) {
			MD5pwd();
			loginForm.submit();
		}
	}
	//关闭
	function loginUrl() {
		window.close();
	}
	//验证码刷新
	function chk_image() {
		var img = document.getElementById("pic");
		img.src = "checkCode?" + Math.random();
	}
	function doModifyPwd() {
		v = openDialog('common_modifPassword_init.do');
		if (v) {
		}
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
	$(function(){
		var evt = window.event || arguments[0];
		$("input[name='captcha']").bind("keydown",function(evt){
			if(evt.keyCode==13){
				loginOn();
			};
		});
	})
</script>
<script>
$(function () {
    // 关注我们二维码
    $(".focusUs a").hover(function () {
        $(this).children("div").show();
    }, function () {
        $(this).children("div").hide();
    })
    // 输入框icon高亮
    $(".inputRow input").focus(function(e){
        stopDefault(e);
        $(this).prev().css({"background-color":"#0D4F92"});
    })
    $(".inputRow input").blur(function(e){
        stopDefault(e);
        $(this).prev().css({"background-color":"#ccc"});
    })
});
</script>
</head>
<body>
<!--头部logo以及登录注册开始-->
<div class="logo clearfix">
    <div class="logoRight">
        <a href="/SRRPBusinesWeb/portal/register.html">注&nbsp;&nbsp;&nbsp;&nbsp;册 </a>
<!--         <a href="/SRRPBusinesWeb/portal/investregister.html"> 企业注册 </a> -->
        <a href="login.jsp">登&nbsp;&nbsp;&nbsp;&nbsp;录 </a>
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

<div style="background:#EEF3F9">
   
    <div class="currentsite">当前位置：用户修改密码</div>
    <!-- 主体部分start -->
    <div class="login w990 bc mt10 regist">
        <div class="login_hd">
            <h2></h2>
            <b></b>
        </div>
        <div class="login_bd">
            <div class="login_form fl">
                <form action="" method="post">
                    <ul>
                        <li>
                            <label>用户名：</label>
                            <input type="text" class="txt" name="username"/>
                            <p>3-20位字符，可由中文、字母、数字和下划线组成</p>
                        </li>
                        <li>
                            <label>密码：</label>
                            <input type="password" class="txt" name="password"/>
                            <p>6-20位字符，可使用字母、数字和符号的组合，不建议使用纯数字、纯字母、纯符号</p>
                        </li>
                        <li>
                            <label>确认密码：</label>
                            <input type="password" class="txt" name="password"/>
                            <p><span>请再次输入密码</span></p>
                        </li>
                        <li class="checkcode">
                            <label>验证码：</label>
                            <input type="text" name="checkcode"/>
                            <img src="${ctx}/static/image/checkcode1.jpg" alt=""/>
                            <span>看不清？<a href="">换一张</a></span>
                        </li>
                        <li>
                            <label>&nbsp;</label>
                            <input type="submit" value="立即修改" class="login_btn"/>
                        </li>
                    </ul>
                </form>
            </div>
            <div class="mobile fl">
                <!--<h3>手机快速修改</h3>  -->
                <p>苏州股权融资服务平台联系电话：</p>
                <p><strong>12345678</strong></p>
            </div>
        </div>
    </div>
    <!-- 主体部分end -->
    <div>&nbsp;</div>
</div>
<!-- 页面主题结束 -->
<!--底部信息开始-->
<div class="footer">
    <!-- 客服信息以及关注我们开始 -->
    <div class="contentAndFocus clearfix">
        <div class="footLeft">
            <span>客服热线 (工作时间 9:00-17:00)</span>
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
                    <li><a href="/SRRPBusinesWeb/portal/index.html">首页</a></li>
                    <li><a href="/SRRPBusinesWeb/portal/result.html">运行成果</a></li>
                    <li><a href="/SRRPBusinesWeb/portal/investevent.html">投融事件</a></li>
                    <li><a href="/SRRPBusinesWeb/portal/news.html">政策指南</a></li>
                    <li><a href="/SRRPBusinesWeb/portal/relate.html">联系我们</a></li>
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
<!--底部信息结束-->
<script type="text/javascript">
    $(function(){
        var height = $(window).height() - $("body").height();
        $(".container").css({"padding": height/2+"px 0px"});
        if($(window).height()>900){
           $(".main-background").css({"background-size": "100%"});
        }
    })
</script>
<script>
    // 输入框icon高亮
    $(".forget-psw").click(function (e) {
        layui.use('layer', function () {
            layer.open({
                title: '友好提示',
                type: 1,
                content: '<div style="text-align:center;padding: 10px 0px;width:360px;">请联系管理员，联系电话：010-123456789</div>',
                btn: '关闭',
                btnAlign: 'c', //按钮居中
                shade: 0,//不显示遮罩
                offset: ['40%', '35%'],
                yes: function () {
                    layer.closeAll();
                }
            });
        });
    });
    $(".go-register").click(function (e) {
        window.location.href = "/SRRPBusinesWeb/portal/register.html";//修改具体路径
    })
</script>
</body>
</html>