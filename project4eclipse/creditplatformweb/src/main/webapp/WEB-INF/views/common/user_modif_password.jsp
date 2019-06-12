<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--用户列表-->
<form action="common_modifPassword.do" method="post">
<div id="bor" ><div class="title"><font>修改密码</font></div>
	<table width="100%" align="center" cellpadding="2" cellspacing="2" class="td_title">
		<tr>
			<td class="td_title2" width="20%">账号</td>
			<td class="td_content" width="80%">
				<c:if test="${CFCC_AML_SESSION_NAME!=null}">
					<input name="loginNo" type="text" onblur="validateloginN(this)" value="${CFCC_AML_SESSION_NAME.sysRcUser.loginNo}" readonly="readonly" size="24" maxlength="50"/><span id="loginN" style="color: red"></span>
				</c:if>
				<c:if test="${CFCC_AML_SESSION_NAME==null}">
					<input name="loginNo" type="text" onblur="validateloginN(this)"  size="24" maxlength="50"/><span id="loginN" style="color: red"></span>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="td_title2" width="20%">原密码</td>
			<td class="td_content" width="80%"><input name="oriPassword" type="password" onblur="validateOriPass(this)" size="25" maxlength="16"/><span id="oriPa" style="color: red"></span></td>
		</tr>
		<tr>
			<td class="td_title2">新密码</td>
			<td class="td_content"><input name="newPassword" type="password" onblur="valiPass(this.value)" size="25" maxlength="16"/><span id="newPa" style="color: red"></span></td>
		</tr>
		<tr>
			<td class="td_title2">确认密码</td>
			<td class="td_content"><input name="affirmPassword" type="password" onblur="valiAffirm(this.form);" size="25" maxlength="16"/><span id="affirmPa" style="color: red"></span></td>
		</tr>
	</table>

<div style="height:50px;"></div>
<div id="dialogBottom" align="center">
<input type="button" value="提交" onClick="doSubmit(this.form);" class="button4"/>
<input type="button" value="关闭" onClick="window.close();" class="button4"/>
</div>
</div>
</form>

<script type="text/javascript">
function validateloginN(obj){
	if(obj.value==""){
		document.getElementById('loginN').innerHTML='请输入账号';
	}else{
		document.getElementById('loginN').innerHTML='';
	}
}
function validateOriPass(obj){
	if(obj.value==""){
		document.getElementById('oriPa').innerHTML='请输入原密码';
	}else{
		document.getElementById('oriPa').innerHTML='';
	}
}
function doSubmit(form) {
	var loginVal = form.loginNo.value;
	var oriValue = form.oriPassword.value;
	var newValue = form.newPassword.value;
	var affirmValue = form.affirmPassword.value;
	var sub = true;
	if(loginVal == ""){
		document.getElementById('loginN').innerHTML='请输入账号';
		form.loginNo.focus();
		sub = false;
	}
	if(oriValue == ""){
		document.getElementById('oriPa').innerHTML='请输入原密码';
		form.oriPassword.focus();
		sub = false;
	}
	if(newValue == ""){
		document.getElementById('newPa').innerHTML='请输入新密码';
		form.newPassword.focus();
		sub = false;
	}
	if(affirmValue == ""){
		document.getElementById('affirmPa').innerHTML='请输入确认密码';
		form.affirmPassword.focus();
		sub = false;
	}
	if(!sub){
		return ;
	}
	
	if(!valiPass(newValue)){
		return ;
	}
	
	if(oriValue==newValue){
		document.getElementById('newPa').innerHTML='原密码和新密码不能相同';
		return ;
	}
	
	if(newValue!=affirmValue){
		document.getElementById('affirmPa').innerHTML='两次填写的密码不一致';
		return ;
	}
	document.forms[0].submit();
};


function valiPass(value){
	if(value!=""){
		if((/[\u4e00-\u9fa5]+/i).test(value)){    
			document.getElementById('newPa').innerHTML='密码不能为汉字';
	        return false;
	    }
		if(!(/[a-z]+/g).test(value)){
			document.getElementById('newPa').innerHTML='密码必须是 大写字母、小写字母及数字的组合';
			return false;
		}
		if(!(/[A-Z]+/g).test(value)){
			document.getElementById('newPa').innerHTML='密码必须是 大写字母、小写字母及数字的组合';
			return false;
		} 
		if(!(/[0-9]+/g).test(value)){
			document.getElementById('newPa').innerHTML='密码必须是 大写字母、小写字母及数字的组合';
			return false;
		} 
		if(value.length < 7){
			document.getElementById('newPa').innerHTML='密码不能少于7位';
			return false;
		}
	}else{
		document.getElementById('newPa').innerHTML='请输入新密码';
		return false;
	}
	
	document.getElementById('newPa').innerHTML='';
	return true;
}
function valiAffirm(form){
	var newValue = form.newPassword.value;
	var affirmValue = form.affirmPassword.value;
	if(newValue!=affirmValue){
		document.getElementById('affirmPa').innerHTML='两次填写的密码不一致';
		return ;
	}
	if(affirmValue!=""){
		document.getElementById('affirmPa').innerHTML='';
	}
	
};
</script>