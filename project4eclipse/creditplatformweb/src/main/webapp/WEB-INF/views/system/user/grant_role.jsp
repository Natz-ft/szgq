<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="${pageContext.request.contextPath}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css"/> 
<link href="${pageContext.request.contextPath}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css"/> 
<link href="${pageContext.request.contextPath}/static/css/style-test.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/frame-free/frame/js/framework.js"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script> 
<script type="text/javascript">
function grant_successprocess(data) {
	if (data.code =="00000") {
	 		return true;
 	}else{
 		return false;
 	}
}

</script>
<div class="basepopopdiv popup357-381 base-wrap-m" id="grant_dialog_id">
 		<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
			<div class="tiptop">
				<span>角色关联</span><a href="javascript:close_dialog('grant_dialog_id');"></a>
			</div>
			<div class="modal-body selectmodal wrap-m">
				<form method="post" action="relateRole" id="grant_form_id">
					 <table class="grayTable" id="ss1" data-prompt-position="topLeft" >
						<tr align="center"><td>可选角色</td><td>操作</td><td>拥有角色</td></tr>
						<tr>
							<td align="center">
								<select id="select1" style="width:180px;height: 180px;" size="10"  multiple="true" >
									<c:forEach var="role" items="${list }">
										<option value="${role.id }">${role.name }</option>
									</c:forEach>
								</select>
							</td>
							<td align="center">
								<input id="addall" class="btnRole" type="button" value="全部添加"><br>
								<input id="add" class="btnRole" type="button" value=">>>"><br>
								<input id="delete" class="btnRole" type="button" value="<<<"><br>
								<input id="delall" class="btnRole" type="button" value="全部移除">
							</td>
							<td align="center">
								<input type="hidden" name="userId" value="${id }">
								<select id="select2" name="select" style="width:180px;height: 180px;" size="10" multiple="multiple" >
									<c:forEach var="role" items="${roleList }">
										<option value="${role.id }">${role.name }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						
					</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn-vip btn-pink" id="uploader_ok" onclick="grant_submit('grant_form_id','grant_dialog_id');">确定</button>
				<button type="button" class="btn-vip btn-white" id="uploader_close"
					onclick="close_dialog('grant_dialog_id');">关闭</button>
			</div>
	 </div>
</div>
 
<script type="text/javascript">
//添加一个或者多个
$(function(){
	$("#add").click(function(){
		$("#select1 option:selected").each(function(){
			$("#select2").append("<option value="+$(this).val()+">"+$(this).html()+"</option>");
			$(this).remove();
		})
	})
})
//全部添加
$(function(){
	$("#addall").click(function(){
		$("#select1 option").each(function(){
			$("#select2").append("<option value="+$(this).val()+">"+$(this).html()+"</option>");
			$(this).remove();
		})
	})
})
//删除一个或者多个
$(function(){
	$("#delete").click(function(){
		$("#select2 option:selected").each(function(){
			$("#select1").append("<option value="+$(this).val()+">"+$(this).html()+"</option>");
			$(this).remove();
		})
	})
})
//全部删除
$(function(){
	$("#delall").click(function(){
		$("#select2 option").each(function(){
			$("#select1").append("<option value="+$(this).val()+">"+$(this).html()+"</option>");
			$(this).remove();
		})
	})
})
</script>
<script>

function grant_submit(grant_form_id,grant_dialog_id){
	$("#select2 option").each(function(){
		$(this).attr("selected", true);
	})
	$("#"+grant_form_id).ajaxSubmit({
		 dataType : 'json',
        success:function(data){
       	 	$("#"+grant_dialog_id).hide();
            var flag = grant_successprocess(data);
            if (flag) {
            	param("提示信息","关联角色成功","");
				$("#t1").fadeIn(200);
			 }else {
				param("提示信息","关联角色失败","");
				$("#t1").fadeIn(200);
			}
        },error: function(){
            param("提示信息","系统错误，请重试","");
				$("#t1").fadeIn(200);
        }
    })
}
</script>
 
