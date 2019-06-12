<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 查询框 -->
<div class="basepopopdiv popup357-381 base-wrap-s" id="query_conditions" style="display: none;">
	<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
		<div class="tiptop">
			<span>查询</span>
			<a onclick="close_dialog('query_conditions');"></a>
		</div>
		<div action="" id="queryFormId">
		    <div class="modal-body selectmodal wrap-s">
				<table class="grayTable" data-prompt-position="topLeft">
					<tbody>
						<tr>
							<th  align="right" class="thStyle"><span>企业或需求名称:</span></th> 
							<td>
								<input type="text" name="search_LIKE1_projectName" value="${page.paramsMap.search_LIKE1_projectName}" onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" />
								<input type="hidden" name="search_IN1_enterpriseId" value="${page.paramsMap.search_IN1_enterpriseId}" onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" />
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle"><span>需求状态:</span></th> 
							<td>
								<select id="status" name="search_EQ_status" class="validate[required] wrap-select">
									<option value="" selected="selected">请选择</option>
									<c:forEach var="status" items="${demandStatus}">
									    <c:if test="${status.dicCode !='00' && status.dicCode !='99'}">
										    <option value="${status.dicCode}">${status.dicName}</option>
										</c:if>
									</c:forEach>
									<option value="88">撤回</option>
									<option value="89">待审核</option>
								</select>
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle"><span>是否未读:</span></th> 
							<td>
								<select id="type" name="search_EQ_systemLook" class="validate[required] wrap-select">
									<option value="" selected="selected">请选择</option>
									<option value="0">已读</option>
									<option value="1">未读</option>
								</select>
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


