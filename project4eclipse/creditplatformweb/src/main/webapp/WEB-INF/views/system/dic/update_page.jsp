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
<script type="text/javascript">
function update_successprocess(data) {
	if (data.code == "00000") {
		return true;
	} else {
		return false;
	}
}
</script>
 <div class="basepopopdiv popup357-381 base-wrap-s" id="update_dialog_id" >
	<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
	<div class="tiptop">
		<span>修改数据字典</span><a href="javascript:close_dialog1('update_dialog_id','dicUpdate_form_id');"></a>
	</div>
	<div class="modal-body selectmodal wrap-s">
		<form method="post" action="updateDic" id="dicUpdate_form_id">
		<!-- 不允许修改字段 -->
		<input type="hidden" name="id" value="${dic.id }">
		<input type="hidden" name="validityFlag" value="${dic.validityFlag }">
		<input type="hidden" name="validityDate" value="${dic.validityDate }">
			<table class="grayTable" id="ss1" data-prompt-position="topLeft">
						<tr>
							<th align="right" class="thStyle">字典名称:</th>
							<td><input type="text" name="value" value="${dic.value }" class=" validate[required,length[1,10]] textinput"/><span class="star">*</span></td>
						</tr>
					    <tr>
							<th align="right" class="thStyle">字典数值:</th>
							<td><input type="text"  name="name"  value="${dic.name }" class="validate[required,length[1,30]] textinput" /> <span class="star">*</span>
						</tr>
					<tr>
						<th align="right" class="thStyle">字典类型:</th>
						<td>
						<select id="type" name="type" class="validate[required] wrap-select">
									<option value="" selected="selected">请选择</option>
									<c:forEach var="type" items="${sessionScope.type}">
										<option value="${type.key}" 
										 <c:if test="${dic.type==type.key}">  selected="selected" </c:if>
										> ${type.value }</option>
									</c:forEach>
						</select>
						<span class="star">*</span></td>
					</tr>
			</table>
		</form> 
	</div>
	<div class="modal-footer">
		<button type="button" class="btn-vip btn-pink" id="uploader_ok" onclick="update_submit('dicUpdate_form_id','update_dialog_id');">确定</button>
		<button type="button" class="btn-vip btn-white" id="uploader_close1" onclick="close_dialog('update_dialog_id','dicUpdate_form_id');">关闭</button>
		</div>
 	</div>
</div>
