<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">

	  /**
	   **用户提示
	  **/
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
				<span>新建</span><a href="javascript:close_dialog('create_dialog_id');"></a>
			</div>
			<div class="modal-body selectmodal wrap-s">
					 <form method="post" action="${ctx}/orgConfig/saveOrgConfig.do" id="create_form_id">
					 <table class="grayTable" id="ss1" data-prompt-position="topLeft" >
				<tr>
					<th align="right" class="thStyle" >系统编号:</th>
					<td  colspan="3">
					<input type="text" name="appid" class="validate[required,custom[onlyNumber],length[1,2]]"/><span class="star">*</span>
					</td>
					</tr>
					<tr>
					<th align="right" class="thStyle">子系统功能页面URL:</th>
					<td colspan="3"><input type="text" name="pageUrl" class=" validate[required] textinput" id="userPassword" /><span class="star">*</span></td>
						</tr>
					<tr>
					<th align="right" class="thStyle">功能类型:</th>
					<td>
					<select  name="pageType" class ="validate[required] wrap-select"><option value="" selected="selected">--请选择--</option>
						<c:forEach var="dic" items="${dicList}">
							<option value="${dic.value}">${dic.name }</option>
						</c:forEach>
						</select><span class="star">*</span>
				<%-- 	<c:forEach items="${dicList }" var="dic">
					<option value="${dic.value}" >${dic.name }</option>
						</c:forEach>
					</select> --%>
					</td>
				</tr>
				<tr>
					<th align="right" class="thStyle">系统名称:</th>
		        <td><input type="text" name="appName" class=" validate[required] " maxlength="128" /><span class="star">*</span></td>
				</tr>
				<tr>
					<th align="right" class="thStyle">启停标志:</th>
					<td>
					    <input class="inputRadio" type="radio" name="startFlag"   checked="checked"  value="1"> 启用
						<input class="inputRadio" type="radio" name="startFlag"   value="0" >停用 
					</td>
				</tr>
			</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="button" class="btn-vip btn-pink" id="createBtn" onclick="create_submit('create_form_id','create_dialog_id');">确定</button>
				<button type="reset" class="btn-vip btn-white" onclick="reset('create_form_id')">清空</button>
				<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog('create_dialog_id');">关闭</button>
			</div>
	 </div>
</div>
 


