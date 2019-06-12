<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="${pageContext.request.contextPath}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css"/> 
<link href="${pageContext.request.contextPath}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css"/> 
<link href="${pageContext.request.contextPath}/static/css/style-test.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/frame-free/frame/js/framework.js"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>


<div class="basepopopdiv popup357-381 base-wrap-s" id="stop_reason_id"  style="display: none;">
 		<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
			<div class="tiptop">
				<span>录入停用原因</span><a href="javascript:close_dialog('stop_reason_id');"></a>
			</div>
			<div class="modal-body selectmodal wrap-s">
				<form method="post" action="stopUser" id="stop_form_id">
					 <table class="grayTable" id="ss1" data-prompt-position="topLeft">
						<tr>
							<th align="center" class="thStyle">停用原因</th>
						</tr>
						<tr>
							<td align="center">
								<input id="stop_id" type="hidden" name="id" >
								<textarea id="stopReason" name="stopReason" class="validate[required,length[1,300]] textarea"></textarea>
							</td>
						</tr>
					</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn-vip btn-pink" id="uploader_ok" onclick="stop_submit('stop_form_id','stop_reason_id');">确定</button>
				<button type="reset" class="btn-vip btn-white" onclick="reset('stop_form_id')">清空</button>
				<button type="button" class="btn-vip btn-white" id="uploader_close"
					onclick="close_dialog('stop_reason_id');">关闭</button>
			</div>
	 </div>
</div>

<script type="text/javascript">
function stop_button(obj,stop_reason_id,id) {
	if($(obj).val()=="启用"){
		startUser(id);
	}else{
		$("#"+stop_reason_id).show();
		$("#stop_id").val(id);
		$("#stopReason").val("");
		$("#zhezhao").show();
		$(".zDialogCon").css("overflow","hidden");
	}
}
function stop_successprocess(data){
	if(data.code="00000"){
		return true;
	}else{
		return false;
	}
}
function stop_submit(stop_form_id,stop_reason_id) {
	 if ($("#"+stop_form_id).validationEngine({returnIsValid:true})){  
		 $("#"+stop_form_id).ajaxSubmit({
			 dataType : 'json',
            success:function(data){
           	 $("#"+stop_reason_id).hide();
                var flag = stop_successprocess(data);
                if (flag) {
                	param("提示信息","停用成功","");
					$("#t1").fadeIn(200);
				 }else {
					param("提示信息","停用失败","");
					$("#t1").fadeIn(200);
				}
            },error: function(){
                param("提示信息","系统错误，请重试","");
					$("#t1").fadeIn(200);
            }
        })
	 }
}
</script>
 