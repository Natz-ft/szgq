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
   <div class="basepopopdiv popup357-381 base-wrap-m" id="update_dialog_id"  >
 		<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
			<div class="tiptop">
				<span>修改</span><a href="javascript:close_dialog('update_dialog_id','menuUpdate_form_id');"></a>
			</div>
				<div class="modal-body selectmodal wrap-m" >
		<form  action="${ctx}/sysMenu/updateMenu.do" id="menuUpdate_form_id" method="post">
			<table class="grayTable" id="ss1" data-prompt-position="topLeft" >
				<!-- 菜单ID -->
				<input type="hidden" name="id"  value="${menu.id }"  />
			<!-- 创建用户 -->
					<input type="hidden" value="${menu.createUser }" name="createUser" />
       		<!-- 创建时间 -->
					<input type="hidden" value="${menu.createTime }" name="createTime" />
	        	
	           <tr>
					<th align="right" class="thStyle" >菜单名称:</th>
					<td >
					<input type="text" name="name" class="validate[required] textinput"  value="${menu.name}"/><span class="star">*</span>
					</td>
					<th align="right" class="thStyle">英文别名:</th>
					<td><input type="text" name="alias" class=" validate[required] textinput"  value="${menu.alias}"/><span class="star">*</span></td>
					</tr>
						
					<tr>
					<th align="right" class="thStyle">菜单地址:</th>
					<td><input type="text" name="link" class="validate[required] textinput"  value="${menu.link}"/><span class="star">*</span></td>
						<th align="right" class="thStyle">菜单排序:</th>
						<td><input type="text" name="sort"  class=" validate[required,custom[onlyNumber]] textinput"  value="${menu.sort}"/><span class="star">*</span></td>
					</tr> 
					
					<tr>
					<th align="right" class="thStyle">父菜单ID:</th>
					 <td colspan="3" >
		 <%-- 
		  <input type="text" name="parentId"    class="validate[required,custom[onlyNumber],ajax[ajaxFindByParentId]] textinput"  value="${menu.parentId}" /> 
		   --%>		 
		    <input type="text" name="parentId"    class="validate[required,custom[onlyNumber],ajax[ajaxFindByParentId]] aloneTwoCol"  value="${menu.parentId}"  /> 
		   
					 <span class="star">*</span></td>
					</tr>
				 <tr>
					<th align="right" class="thStyle">菜单描述:</th>
					<td  colspan="3">
					<textarea class="textarea aloneTwoCol" rows="4" cols="28" name="description"  >${menu.description}</textarea>
					</td>
				</tr>   
			</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="button" class="btn-vip btn-pink"  id ="updateBtn"  onclick="update_submit('menuUpdate_form_id','update_dialog_id')">确定</button>
				<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog('update_dialog_id','menuUpdate_form_id');">关闭</button>
			</div>
	 </div>
</div>
