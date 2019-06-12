<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="${pageContext.request.contextPath}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css"/> 
<link href="${pageContext.request.contextPath}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css"/> 
<link href="../static/css/style-test.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/frame-free/frame/js/framework.js"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script> 
<script type="text/javascript">
function del_successprocess(data) {
	if (data.code =="00000") {
	 		return true;
 	}else{
 		return false;
 	}
}
/**
 *  用户提示
 */
 function create_successprocess(data) {
   if (data.code =="00000") {
 		return true;
	}else{
		return false;
	}
}
</script>
<div class="basepopopdiv popup357-381 base-wrap-s" id="create_dialog_id"  style="display: none;">
 		<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
			<div class="tiptop">
				<span>新建角色</span><a href="javascript:close_dialog1('create_dialog_id','create_form_id');"></a>
			</div>
			<div class="modal-body selectmodal wrap-s">
				<form method="post" action="insertRole" id="create_form_id">
					 <table class="grayTable" id="ss1" data-prompt-position="topLeft">
						<tr>
							<th align="right" class="thStyle">角色名称:</th>
							<td>
							<input type="text" name="name" class="validate[required,length[1,30]]"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle">角色描述:</th>
							<td >
							<textarea name="description" rows="4" cols="28" style="height:150px;" ></textarea>
							</td>
						</tr>
					</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn-vip btn-pink" id="uploader_ok" onclick="create_submit('create_form_id','create_dialog_id');">确定</button>
				<button type="reset" class="btn-vip btn-white" onclick="reset('create_form_id')">清空</button>
				<button type="button" class="btn-vip btn-white" id="uploader_close"
					onclick="close_dialog1('create_dialog_id','create_form_id');">关闭</button>
			</div>
	 </div>
</div>
 
