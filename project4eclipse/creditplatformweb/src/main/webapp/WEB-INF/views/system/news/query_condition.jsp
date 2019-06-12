<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="${pageContext.request.contextPath}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css"/> 
<link href="${pageContext.request.contextPath}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/frame-free/frame/js/framework.js"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.manage.js"></script>   

<!-- 查询框 -->
<div class="basepopopdiv popup357-381 base-wrap-xs" id="query_conditions" style="display: none;">
	<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
		<div class="tiptop">
			<span>查询</span>
			<a onclick="close_dialog('query_conditions');"></a>
		</div>
		<div action="" id="queryFormId">
		    <div class="modal-body selectmodal wrap-xs" >
				<table class="grayTable" data-prompt-position="topLeft">
					<tbody>
						<tr>
							<th  align="right" class="thStyle"><span>新闻标题:</span></th> 
							<td>
								<input autocomplete="off" type="text" name="search_LIKE_title" value="${page.paramsMap.search_LIKE_title}" onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" />
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle"><span>新闻类型:</span></th> 
							<td>
								<select name="search_EQ_newstype" class="wrap-select">
									<option value=""  selected="selected">请选择</option>
									<c:forEach var="status" items="${statusList}">
										<option value="${status.dicCode}" <c:if test="${status.dicCode==page.paramsMap.search_EQ_newstype}">
											selected="selected"
									</c:if>>${status.dicName }</option>
									</c:forEach>
								</select>
								<%-- <input type="text" name="search_LIKE_org" value="${page.paramsMap.search_LIKE_org }" onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" class="textinput" /> --%>
							</td>
						</tr>
					</tbody>					
				</table>
		   </div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn-vip btn-pink" id="uploader_ok" onclick="querySubmit('list_form_id');">确定</button> 
			<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="clearForm('list_form_id');">清空</button>
			<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog('query_conditions');">关闭</button>
		</div>
	</div>
</div>


