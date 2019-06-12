<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<head>
<script src="${pageContext.request.contextPath}/static/js/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/frame-free/frame/js/framework.js"></script>
<link href="${pageContext.request.contextPath}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css"/> 
<link href="${pageContext.request.contextPath}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css"/> 
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.manage.js"></script>   
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
			<span>修改联系信息</span><a href="javascript:close_dialog('update_dialog_id');"></a>
		</div>
		<div class="modal-body selectmodal wrap-s">
			<form method="post" action="update" id="update_form_id">
				<!-- 不允许修改字段 -->
				<input type="hidden" name="id" value="${contactus.id }">
				<input type="hidden" name="createUser" value="${contactus.createUser }">
				<table class="grayTable" id="ss1" data-prompt-position="topLeft" >
						<tr>
							<th align="right" class="thStyle">联系名:</th>
							<td><input type="text"  name="name"  value="${contactus.name }"  class="validate[required,length[1,128]] textinput"><span class="star">*</span></td>
						</tr>
						<tr>
							<th align="right" class="thStyle">热线:</th>
							<td><input type="text"  name="hotline"  value="${contactus.hotline }"  class="validate[required,length[1,128]] textinput"><span class="star">*</span></td>
						</tr>
						<tr>
							<th align="right" class="thStyle">邮编</th>
							<td >
							<td><input type="text"  name="zipCode"  value="${contactus.zipCode }"  class="validate[required,length[1,128]] textinput"><span class="star">*</span></td>
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle">传真</th>
							<td><input type="text"  name="fax"  value="${contactus.fax }"  class="validate[required,length[1,128]] textinput"><span class="star">*</span></td>
						</tr>
						<tr>
							<th align="right" class="thStyle">邮箱</th>
							<td><input type="text"  name="mail"  value="${contactus.mail }"  class="validate[required,length[1,128]] textinput"><span class="star">*</span></td>
						</tr>
						<tr>
							<th align="right" class="thStyle">地址</th>
							<td><input type="text"  name="adress"  value="${contactus.adress }"  class="validate[required,length[1,128]] textinput"><span class="star">*</span></td>
						</tr>
				 </table>
			 </form> 
		</div>
		<div class="modal-footer">
			<button type="button" class="btn-vip btn-pink" id="uploader_ok" onclick="update_submit('update_form_id','update_dialog_id');">确定</button>
			<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog('update_dialog_id');">关闭</button>
		</div>
	 </div>
</div>
