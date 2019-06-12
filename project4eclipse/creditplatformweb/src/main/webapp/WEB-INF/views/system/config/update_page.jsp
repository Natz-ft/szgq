<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
   <div class="basepopopdiv popup357-381 base-wrap-s" id="update_dialog_id" >
 		<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
			<div class="tiptop">
				<span>修改</span><a href="javascript:close_dialog1('update_dialog_id','update_form_id')"></a>
			</div>
			<div class="modal-body selectmodal wrap-s">
		<form  action="${ctx}/systemConfig/update_systemConfig.do" id="update_form_id" method="post">
			<table class="grayTable" id="ss1" data-prompt-position="topLeft" >
		<!-- 	<tbody> -->
					<tr>
						<!-- 用户ID -->
						<input type="hidden" value="${config.configId }" name="configId" />
						<!-- 创建用户 -->
						<input type="hidden" value="${config.createUser }" name="createUser" />
						<!-- 创建时间 -->
						<input type="hidden" value="${config.createTime }" name="createTime" />
						<th align="right" class="thStyle">参数名称:</span></th>
						<td><input type="text"  disabled="disabled"   name="configName"   id="configName" value="${config.configName }"  ><span class="star">*</span></td>
					</tr>
				 		<tr>
						<th align="right" class="thStyle">参数数值:</th>
						<td><input type="text" name="configValue"  value="${config.configValue }"  class=" validate[required]" id="configValue" /><span class="star">*</span></td>
					</tr>
					<tr>
					<th align="right" class="thStyle">参数描述:</th>
						<td><textarea rows="4" cols="28"  name="configDesc" class="validate[maxSize[500]] textarea">${config.configDesc }</textarea></td>
					</tr> 
				<!-- </tbody> -->
			</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="button" class="btn-vip btn-pink"  id="updateBtn"  onclick="update_submit('update_form_id','update_dialog_id')">确定</button>
				<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog1('update_dialog_id','update_form_id')">关闭</button>
			</div>
	 </div>
</div>
