<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
function del_successprocess(data) {
	if (data.code =="00000") {
	 		return true;
 	}else{
 		return false;
 	}
}
/**
 *  用户提示
 */
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
				<span>新建联系方式</span><a href="javascript:close_dialog('create_dialog_id');"></a>
			</div>
			<div class="modal-body selectmodal wrap-s">
				<form method="post" action="insertContactusDist" id="create_form_id">
					 <table class="grayTable" id="ss1" data-prompt-position="topLeft">
						<tr>
							<th align="right" class="thStyle">金融办名称</th>
							<td >
								<input type="text" name="name" class="textinput"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle">联系电话:</th>
							<td >
								<input type="text" name="hotline" class="textinput"/><span class="star">*</span>
							</td>
						</tr>
					</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn-vip btn-pink" id="uploader_ok" onclick="create_submit('create_form_id','create_dialog_id');">确定</button>
				<button type="reset" class="btn-vip btn-white" onclick="reset('create_form_id')">清空</button>
				<button type="button" class="btn-vip btn-white" id="uploader_close"
					onclick="close_dialog('create_dialog_id');">关闭</button>
			</div>
	 </div>
</div>
