<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/frame-free/frame/js/framework.js"></script>
<link href="${pageContext.request.contextPath}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css"/> 
<link href="${pageContext.request.contextPath}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css"/> 
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.manage.js"></script>   
 </head>
<style type="text/css">
.mainCon{
display:none;
}
<!--
去掉select框的默认样式
-->
</style>
<script type="text/javascript">
function update_successprocess(data) {
	if (data.code == "00000") {
		return true;
	} else {
		return false;
	}
}
</script>
   
<div class="basepopopdiv popup357-381 base-wrap-m" id="update_dialog_id"  >
	<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
	<div class="tiptop">
		<span>更新用户信息</span><a href="javascript:close_dialog1('update_dialog_id','update_form_id');"></a>
	</div>
	<div class="modal-body selectmodal wrap-m">
		<form method="post" action="updateUser" id="update_form_id">
			<!-- 不需要再页面显示，但是需要提交的数据，此数据不允许更改 -->
			<input type="hidden" name="id" value="${user.id }">
			<table class="grayTable" id="ss1" data-prompt-position="topLeft"  >
				<tr>
					<th align="right" class="thStyle">用户账号:</th>
					<td >
						<input type="text" name="username" value="${user.username }" disabled="disabled" class="validate[required] textinput" /><span class="star">*</span>
					</td>
					<th align="right" class="thStyle">用户姓名:</th>
					<td >
						<input  type="text" name="nickname" value="${user.nickname }" class="validate[required,length[1,50]] textinput"/><span class="star">*</span>
					</td>
				</tr>
				
				<tr>
					<%-- <th align="right" class="thStyle">用户类型:</th>
					<td >
						<select name="type" class="wrap-select">
							<option value="" selected="selected">请选择</option>
							<c:forEach var="usertype" items="${dicWord.usertype}">
								<option value="${usertype.key}" <c:if test="${usertype.key==user.type}">
									selected="selected"
							</c:if>>${usertype.value }</option>
							</c:forEach>
						</select>
					</td> --%>
					<th align="right" class="thStyle" >用户类别:</th>
					<td >
						<select name="org" id='org' class="validate[required] wrap-select">
							<option value=""  selected="selected">请选择</option>
							<c:forEach var="org" items="${sessionScope.org}">
								<option value="${org.key}" <c:if test="${org.key==user.org}">
									selected="selected"
							</c:if>>${org.value }</option>
							</c:forEach>
						</select><span class="star">*</span>
					</td>
				
					<th align="right" class="thStyle">联系电话:</th>
					<td >
						<input  type="text" name="telephone" value="${user.telephone }" class="validate[custom[telephone]] textinput"/>
					</td>
				</tr>
				<tr>
					<th align="right" class="thStyle">邮箱:</th>
					<td >
						<input  type="text" name="email" value="${user.email }" class="validate[custom[email]]"/>
					</td>
					<th align="right" class="thStyle">邮政编码:</th>
					<td >
						<input  type="text" name="postCode" value="${user.postCode }" class="validate[custom[zopcode]] textinput"/>
					</td>
				</tr>
				<tr>
							<th align="right" class="thStyle">所属区域:</th>
							<td >
							   <select id="desc3" name="desc3" class="validate[required] wrap-select">
									<option value="" selected="selected">请选择</option>
									<c:forEach var="arrea" items="${sessionScope.dataList}">
								<option value="${arrea.key}" <c:if test="${arrea.key==user.desc3}">
									selected="selected"
							</c:if>>${arrea.value }</option>
										
									</c:forEach>
								</select><span class="star">*</span>
							</td>
						</tr>
				<tr>
					<th align="right" class="thStyle">通信地址:</th>
					<td colspan="3">
						<input type="text" name="address" value="${user.address }"  class="validate[length[0,80]] aloneTwoCol"/>
					</td>
				</tr>
			</table>
		</form> 
	</div>
	<div class="modal-footer">
		<button type="button" class="btn-vip btn-pink" id="uploader_ok" onclick="update_submit('update_form_id','update_dialog_id');">确定</button>
		<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog1('update_dialog_id','update_form_id');">关闭</button>
	</div>
	</div>
</div>
