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
	   ** 修改 用户提示 返回信息
	  **/
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
				<span>修改</span><a href="javascript:close_dialog('update_dialog_id');"></a>
			</div>
			<div class="modal-body selectmodal wrap-s">
					 <form method="post" action="${ctx}/orgConfig/updateOrgConfig.do" id="update_form_id">
					 <table class="grayTable" id="ss1" data-prompt-position="topLeft">
				<!-- 隐藏 ID -->
				<input type="hidden"    name="id"  value="${orgConfig.id}" >
				<input type="hidden"    name="createTime"  value="${orgConfig.createTime}" >
				<tr>
					<th align="right" class="thStyle" >系统编号:</th>
					<td colspan="3">
					<input type="text"   name="appid" readonly="readonly"   value="${orgConfig. appid}"/><span class="star">*</span>
					</td>
					</tr>
				 	<tr>
					<th align="right" class="thStyle">子系统功能页面URL:</th>
					<td colspan="3"><input type="text" name="pageUrl" class=" validate[required] " id="userPassword"  value="${orgConfig.pageUrl }" /><span class="star">*</span></td>
						</tr>
					<tr>
					<th align="right" class="thStyle">功能类型:</th>
					<td>
					 <!-- 配置管理功能类型   -->
					 <select name="pageType" class ="validate[required] wrap-select">
					 <option value="" selected="selected">--请选择--</option>
						<c:forEach var="curTp" items="${dicList}">
							<option value="${curTp.value }" <c:if test="${curTp.value== orgConfig.pageType }"> selected="selected"
						                </c:if>>${curTp.name }</option>
						</c:forEach></select><span class="star">*</span>
					</td>
				</tr>
				<tr>
					<th align="right" class="thStyle">系统名称:</th>
		        <td><input type="text" name="appName" class=" validate[required] "  maxlength="128"  value="${orgConfig.appName}"  /><span class="star">*</span></td>
				</tr>
				<tr>
					<th align="right" class="thStyle">启停标志:</th> 
					<td>
					    <input class="inputRadio" type="radio" name="startFlag"   checked="checked"  value="1"> 启用
						<input class="inputRadio" type="radio"  name="startFlag"   value="0" >停用 
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
 


