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
   
<div class="basepopopdiv popup357-381 base-wrap-s" id="update_dialog_id"  >
	<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
	<div class="tiptop">
		<span>更新角色信息</span><a href="javascript:close_dialog1('update_dialog_id','update_form_id');"></a>
	</div>
	<div class="modal-body selectmodal wrap-s">
		<form method="post" action="finalRole" id="update_form_id">
			<!-- 不需要再页面显示，但是需要提交的数据，此数据不允许更改 -->
			<input type="hidden" name="id" value="${role.id }">
			<input type="hidden" name="createTime" value="${role.createTime }">
			<input type="hidden" name="createUser"  value="${role.createUser }">
			<table class="grayTable" id="ss1" data-prompt-position="topLeft" >
					<tr>
						<th align="right" class="thStyle">角色名称:</th>
						<td><input type="text" id='name' name="name"  value="${role.name }"  class="validate[required]"><span class="star">*</span></td>
					</tr>
					<tr>
						<th align="right" class="thStyle">角色描述:</th>
						<td><textarea name="description" id='description' rows="4" cols="28" style="height:150px;"  class="validate[required,length[1,100]] textarea">${role.description }</textarea></td>
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
