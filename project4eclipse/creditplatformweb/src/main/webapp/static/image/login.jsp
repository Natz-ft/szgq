<%@page import="java.io.InputStream"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ page import="com.icfcc.credit.platform.exception.CaptchaException"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>征信系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="static/image/favicon1.ico" type="image/x-icon" />
 <link href="static/css/style-test.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/static/js/jquery-1.4.4.min.js"></script> 
<script src="static/js/md5.js" type="text/javascript"></script>
<script src="static/js/util.js" type="text/javascript"></script>
<script type="text/javascript">
    function keyLogin(){
		 if (event.keyCode==13){  
		   if (validateLoginForm(loginForm)) {
			 MD5pwd();
	     	 loginForm.submit();
	       }
	    }
	}
    
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
	$(function(){
		var evt = window.event || arguments[0];
		$("input[name='captcha']").bind("keydown",function(evt){
			if(evt.keyCode==13){
				loginOn();
			};
		});
	})	
</script>
<style type="text/css">
.err {
	display: inline-block;
	width: 170px;
	color: #5D6FCE;
}
#loginForm td{
	color: #666;
	font-size: 16px;
}
input{
	font-family: Arial,Microsoft YaHei;
	text-indent: 5px;
	font-size: 16px;
	color:#333;
	width: 170px; 
	height: 30px;
	line-height: 30px;
	border: 1px solid #ccc; 
}
input:-webkit-autofill {
    -webkit-box-shadow: 0 0 0px 1000px #fdfbfe inset !important;
}
body {
	margin: 0px;
	background:#fdfbfe;
	overflow-x: hidden;
}
.loginBtn a{
	font-size: 18px;
	width: 170px;
	height: 36px;
	line-height: 36px;
	text-align: center;
	color: #fff;
	background-color:#5D6FCE;
	display: block;
}
.loginBtn a:hover{
	background:#f43499;
}
#main{
	height: 600px;
	background: #fff url(static/image/login/login-bg.jpg) no-repeat center top;
	vertical-align: top;
}
.loginBox td{
	text-align: center;
}
</style>
</head>
<body >
	<form name="loginForm" id="loginForm" method="post" action="${ctx}/login">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="login-bj" >
			<tr>
				<td height="80" valign="middle" style="border-bottom: 2px solid #F3168A;">
					<img src="static/image/login/login-logo.png" width="145" height="32" style="margin-left: 60px;" />
				</td>
			</tr>
			<tr>
				<td  align="center" id="main">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" algin="center" >
						<tr>
							<td height="180" valign="bottom" style="text-align: center;">
								<img src="static/image/login/login-02.jpg" width="267" height="87" style="" />
							</td>
						</tr>
					</table>
					<table style="width: 800px;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td style="background:url(static/image/login/login-01.jpg) no-repeat 20px 20px;vertical-align: top;" width="800" height="480" align="right"><table class="loginBox" width="341" height="280" border="0"
									cellpadding="0" cellspacing="2" style="font-size: 14px;margin-top: 30px;margin-right: 20px;">
									<tr>
										<td style="width: 100px;text-align: right;">用户名:</td>
										<td>
										<input type="text" name="username"  onkeydown="keyLogin()" tabindex="1"/>
										</td>
									</tr>
									<tr>
										<td style="width: 100px;text-align: right;">密 &nbsp;&nbsp;码:</td>
										<td><input id="password" type="password" name="password"  onkeydown="keyLogin()" tabindex="2"/>
										</td>
									</tr>
									<tr>
										<td style="width: 100px;text-align: right;">验证码:</td>
										<td>
										<input type="text" name="captcha" onkeydown="keyLogin()"  tabindex="3" style="width: 100px;*width: 100px;display:inline-block;vertical-align:middle;"/>
											<img border=0 id="pic" height="30" width="65" src="checkCode" onclick="return chk_image();" style="vertical-align:middle;"/>
										</td>
									</tr>
									<tr>
										<td colspan="2" valign="top" style="text-align: center;height: 40px;">
											<%
											String value=request.getParameter("kickout");
											if(value!=null && value.trim().equals("1")){
											%>
											<span class="err">您的账号已在别处登录!!!</span>
											<%} %>
											<c:choose>
												<c:when
													test="${shiroLoginFailure eq 'com.icfcc.credit.platform.exception.CaptchaException'}">
													<span class="err">验证码错误，请重试.</span>
												</c:when>
												<c:when
													test="${shiroLoginFailure eq 'org.apache.shiro.authc.UnknownAccountException'}">
													<span class="error-msg prepend-top">该用户不存在.</span>
												</c:when>
												<c:when
													test="${shiroLoginFailure eq 'org.apache.shiro.authc.IncorrectCredentialsException'}">
													<span class="error-msg prepend-top">用户密码错误，连续输错三次将暂时锁定账户。</span>
												</c:when>
												<c:when
													test="${shiroLoginFailure eq 'com.icfcc.credit.platform.exception.StopUserException'}">
													<span class="err">用户已被停用，请联系qyservice@vipshop.com</span>
												</c:when>
												<c:when
													test="${shiroLoginFailure eq 'com.icfcc.credit.platform.exception.LockUserException'}">
													<span class="err">由于您长时间未登录，账户已被锁定，请联系qyservice@vipshop.com</span>
												</c:when>
												<c:when
													test="${shiroLoginFailure eq 'com.icfcc.credit.platform.exception.MultipleErrorException'}">
													<span class="err">由于您连续输错密码，账户已被锁定，请稍后再试.</span>
												</c:when>
												
												<c:when test="${shiroLoginFailure ne null}">
													<span class="error-msg prepend-top">登录认证错误，请重试.</span>
												</c:when>
											</c:choose>
											<c:if test="${editPassword==true}">您的密码过于简单，请<a href="javascript:doModifyPwd()">点击此</a>修改密码</c:if>
										</td>
									</tr>

									<tr>
										<td>&nbsp;</td>
										<td class="loginBtn"><a href="javascript:loginOn()" tabindex="4">登 录
										</a>
										</td>
									</tr>
									<tr>
										<td  colspan="2">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
