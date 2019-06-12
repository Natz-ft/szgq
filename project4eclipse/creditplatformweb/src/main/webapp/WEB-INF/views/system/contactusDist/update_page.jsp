<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
   
 <div class="basepopopdiv popup357-381 base-wrap-s" id="conUpdate_dialog_id"  >
	<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
		<div class="tiptop">
			<span>修改联系信息</span><a href="javascript:close_dialog1('conUpdate_dialog_id','conUpdate_form_id');"></a>
		</div>
		<div class="modal-body selectmodal wrap-s">
			<form method="post" action="${ctx}/contactusDist/update.do" id="conUpdate_form_id">
				<!-- 不允许修改字段 -->
				<input type="hidden" name="id" value="${contactusDist.id }">
				<input type="hidden" name="createUser" value="${contactusDist.createUser }">
				<table class="grayTable" id="ss1" data-prompt-position="topLeft" >
						<tr>
							<th align="right" class="thStyle">金融办名称:</th>
							<td><input type="text"  name="name"  value="${contactusDist.name }"  class="validate[required,length[1,128]] textinput"><span class="star">*</span></td>
						</tr>
						<tr>
							<th align="right" class="thStyle">联系电话:</th>
							<td><input type="text"  name="hotline"  value="${contactusDist.hotline }"  class="validate[required,custom[telephone]] textinput"><span class="star">*</span></td>
						</tr>
				 </table>
			 </form> 
		</div>
		<div class="modal-footer">
			<button type="button" class="btn-vip btn-pink" id="uploader_ok" onclick="update_submit('conUpdate_form_id','conUpdate_dialog_id');">确定</button>
			<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog1('conUpdate_dialog_id','conUpdate_form_id');">关闭</button>
		</div>
	 </div>
</div>
