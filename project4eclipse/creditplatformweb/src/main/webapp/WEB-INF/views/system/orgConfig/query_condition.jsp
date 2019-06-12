<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 查询框 -->
<div class="basepopopdiv popup357-381 base-wrap-xs" id="query_conditions" style="display: none;">
	<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
		<div class="tiptop">
			<span>查询</span>
			<a onclick="close_dialog('query_conditions');"></a>
		</div>
		    <div class="modal-body selectmodal wrap-xs" >
		    <div action="121"  id="query_conditions_form_id" method="post">
				<table class="grayTable"  id="ss1" data-prompt-position="topLeft">
					<tbody>
						<tr>
							<th align="right" class="thStyle"><span>应用系统编号:</span></th> 
							<td>
								<input type="text" name="search_LIKE_appid" value="${page.paramsMap.search_LIKE_appid}"  onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" />
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


