<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/frame-free/frame/js/framework.js"></script>
<link href="${ctx}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css"/> 
<link href="${ctx}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css"/> 
<script src="${ctx}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${ctx}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script>   
 </head>
<script type="text/javascript">
		
	/**
	***后端程序通过Jquery+Ajax将结果返回值提供给用户。
	
	*/
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
				<span>修改</span><a href="javascript:close_dialog('update_dialog_id');"></a>
			</div>
			<div class="modal-body selectmodal wrap-s">
		<form method="post" action="${pageContext.request.contextPath }/systemButton/update_systemButton.do" id="update_form_id">
			<table class="grayTable" id="ss1" data-prompt-position="topLeft">
					<!-- 隐藏区域  -------start -->
					<input type="hidden" value="${button.buttonId }" name="buttonId">
					<input type="hidden" value="${button.createUser }" name="createUser">
					<input type="hidden" value="${button.updateTime }" name="updateTime">
					<!-- 隐藏区域  -------end -->
					<th align="right" class="thStyle">按钮名称:</th>
					<td><input type="text" name="buttonName" value="${button.buttonName }" class="validate[required]" /><span class="star">*</span></td>
					</tr>
					<tr>
					<th align="right" class="thStyle">按钮代码:</th>
					<td><input type="text" name="buttonCode" value="${button.buttonCode}" class=" validate[required]" /><span class="star">*</span></td>
						</tr>
					<tr>
					<th align="right" class="thStyle">按钮描述:</th>
					<td >
					<textarea rows="4" cols="28" name="buttonDesc"  class=" validate[required] textarea" maxlength="200">${button.buttonDesc}</textarea>
					<span class="star">*</span>
					</td>
				</tr>
			</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="button" class="btn-vip btn-pink" id="updateBtn" onclick="update_submit('update_form_id','update_dialog_id');">确定</button>
				<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog('update_dialog_id');">关闭</button>
			</div>
	 </div>
</div>
