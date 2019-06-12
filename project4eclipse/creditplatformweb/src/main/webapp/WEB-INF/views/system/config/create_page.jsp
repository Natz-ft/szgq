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
					 <form method="post" action="${ctx}/systemConfig/add_systemConfig.do" id="create_form_id">
					 <table class="grayTable" id="ss1" data-prompt-position="topLeft" >
				<tr>
					<th align="right" class="thStyle" >参数名称:</th>
					<td >
					<input type="text" name="configName" class="validate[required]"/><span class="star">*</span>
					</td>
					</tr>
					<tr>
					<th align="right" class="thStyle">参数数值:</th>
					<td><input type="text" name="configValue" class="validate[required]"  /><span class="star">*</span></td>
						</tr>
					<tr>
					<th align="right" class="thStyle">参数描述:</th>
					<td >
					<textarea rows="4" cols="28" name="configDesc"  class="validate[maxSize[500]] textarea"></textarea>
					</td>
				</tr>
			</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="button" class="btn-vip btn-pink" id="createBtn" onclick="create_submit('create_form_id','create_dialog_id');">确定</button>
				<button type="reset" class="btn-vip btn-white" onclick="reset()">清空</button>
				<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog('create_dialog_id','create_form_id')";>关闭</button>
			</div>
	 </div>
</div>
 


