<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="${pageContext.request.contextPath}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css"/> 
<link href="${pageContext.request.contextPath}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/frame-free/frame/js/framework.js"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.manage.js"></script>   
<style type="text/css">
	#query_conditions{
		margin-left: -200px;
	}
	 .base-wrap-xs .wrap-xs{
		width: 400px;
		*width: 100%;
	}
	 .base-wrap-xs .wrap-xs input{
		width: 120px;
	}
</style>
<!-- 查询框 -->
<div class="basepopopdiv popup357-381 base-wrap-xs" id="query_conditions" style="display: none;">
	<div id="uploadModal" class="modal hide in" aria-hidden="false" style="display: block;">
		<div class="tiptop">
			<span>查询</span>
			<a onclick="close_dialog('query_conditions');"></a>
		</div>
		<div action="" id="queryFormId">
		    <div class="modal-body selectmodal wrap-xs">
				<table class="grayTable" data-prompt-position="topLeft">
					<tbody>
				<tr>
							<th align="right" class="thStyle"><span>查询日期:</span></th> 
							
							<td>
								<input type="text" name="search_GTE_loginTime_DATE"  onfocus=" WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${GTE_loginTime}"   onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" />
							至 <input type="text" name="search_LTE_loginTime_DATE"  onfocus=" WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="${LTE_loginTime}"  onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" /> 
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


