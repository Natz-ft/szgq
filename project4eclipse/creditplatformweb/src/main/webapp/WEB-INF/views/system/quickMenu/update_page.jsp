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
			<span>修改新闻</span><a href="javascript:close_dialog('update_dialog_id');"></a>
		</div>
		<div class="modal-body selectmodal wrap-s">
			<form method="post" action="update" id="update_form_id">
				<!-- 不允许修改字段 -->
				<input type="hidden" name="id" value="${quickMenu.id }">
				<input type="hidden" name="createUser" value="${quickMenu.createUser }">
				<table class="grayTable" id="ss1" data-prompt-position="topLeft" >
						<tr>
							<th align="right" class="thStyle">链接名称:</th>
							<td><input type="text"  name="name"  value="${quickMenu.name }"  class="validate[required,length[1,128]] textinput"><span class="star">*</span></td>
						</tr>
						<tr>
							<th align="right" class="thStyle">链接路径:</th>
							<td><input type="text"  name="url"  value="${quickMenu.url }"  class="validate[required,length[1,128]] textinput"><span class="star">*</span></td>
						</tr>
						<tr>
							<th align="right" class="thStyle">链接logo</th>
							<td >
							     <img src="${quickMenu.logo }" height="100" width="120" />
								 <input name="logo"  class="validate[required]" type="file" accept="image/*" />
							</td>
						</tr>
					 	<%-- <tr>
							<th align="right" class="thStyle">通知类型:</th>
							<td >
								<select name="msgtype" class="validate[required] wrap-select" >
									<option value="" selected="selected">请选择</option>
									<c:forEach var="msgtype" items="${dicWord.msgtype }">
										<option value="${msgtype.key }" <c:if test="${msgtype.key==message.msgtype }">
											selected="selected"
										</c:if>>${msgtype.value }</option>
									</c:forEach>
								</select><span class="star">*</span>
							</td>
						</tr> --%>
						
				 </table>
			 </form> 
		</div>
		<div class="modal-footer">
			<button type="button" class="btn-vip btn-pink" id="uploader_ok" onclick="update_submit('update_form_id','update_dialog_id');">确定</button>
			<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog('update_dialog_id');">关闭</button>
		</div>
	 </div>
</div>
