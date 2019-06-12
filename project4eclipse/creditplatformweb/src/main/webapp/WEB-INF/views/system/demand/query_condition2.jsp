<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 查询框 -->
<div class="basepopopdiv popup357-381 base-wrap-xs" id="query_conditions" style="display: none;">
	<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
		<div class="tiptop">
			<span>查询</span>
			<a onclick="close_dialog('query_conditions');"></a>
		</div>
		<div action="" id="queryFormId">
		    <div class="modal-body selectmodal wrap-xs"style="height:180px;">
				<table class="grayTable" data-prompt-position="topLeft">
					<tbody>
						<tr>
							<th  align="right" class="thStyle"><span>企业或项目名称:</span></th> 
							<td>
								<input type="text" name="search_LIKE1_projectName" value="${page.paramsMap.search_LIKE_projectName}" onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" />
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle"><span>投资轮次:</span></th> 
							<td>
								<select id="finacingTurn" name="search_EQ_finacingTurn" class="validate[required] wrap-select">
									<option value="" selected="selected">请选择</option>
									<c:forEach var="finacingTurn" items="${finacingTurnDic}">
									    <option value="${finacingTurn.dicCode}">${finacingTurn.dicName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle"><span>投资时间:</span></th> 
							<td>
								<input type="text" style="width:110px;"  name="search_GTE_operDate_DATE"  onfocus=" WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${GTE_createTime}" onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" /> 至
							    <input type="text" style="width:110px;"  name="search_LTE_operDate_DATE"  onfocus=" WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="${LTE_createTime}" onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" /> 
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


