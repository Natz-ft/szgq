<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style type="text/css">
.mainCon{
display:none;
}
</style> 
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
<div class="basepopopdiv popup357-381 base-wrap-m" id="create_dialog_id"  style="display: none;">
 		<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
			<div class="tiptop">
				<span>新建用户</span><a href="javascript:close_dialog1('create_dialog_id','create_form_id');"></a>
			</div>
			<div class="modal-body selectmodal wrap-m">
				<form method="post" action="addUser" id="create_form_id">
					 <table class="grayTable" id="ss1" data-prompt-position="topLeft">
						<tr>
							<th align="right" class="thStyle">用户账号:</th>
							<td >
								<input type="text" name="username" class="validate[required,length[1,30]] textinput" /><span class="star">*</span>
							</td>
							<th align="right" class="thStyle">用户姓名:</th>
							<td >
								<input type="text" name="nickname" class="validate[required,length[1,30]] textinput"/><span class="star">*</span>
							</td>
						</tr>
						
						<tr>
							<%-- <th align="right" class="thStyle">用户类型:</th>
							<td >
								<select class="wrap-select" name="type" >
									<option value="" selected="selected">请选择</option>
									<c:forEach var="usertype" items="${dicWord.usertype }">
										<option value="${usertype.key }">${usertype.value }</option>
									</c:forEach>
								</select>
							</td> --%>
							<th align="right" class="thStyle">用户类别:</th>
							<td >
							   <select id="org" name="org" class="validate[required] wrap-select">
									<option value="" selected="selected">请选择</option>
									<c:forEach var="org" items="${sessionScope.org}">
										<option value="${org.key}">${org.value}</option>
									</c:forEach>
								</select><span class="star">*</span>
								
							</td>
						
							<th align="right" class="thStyle">联系电话:</th>
							<td >
								<input type="text" name="telephone" class="validate[custom[telephone]] textinput"/>
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle">邮箱:</th>
							<td >
								<input type="text" name="email" class="validate[custom[email]] textinput"/>
							</td>
							<th align="right" class="thStyle">邮政编码:</th>
							<td >
								<input  type="text" name="postCode" class="validate[custom[zopcode]] textinput"/>
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle">所属区域:</th>
							<td >
							   <select id="desc3" name="desc3" class="validate[required] wrap-select">
									<option value="" selected="selected">请选择</option>
									<c:forEach var="org" items="${sessionScope.dataList}">
										<option value="${org.key}">${org.value}</option>
									</c:forEach>
								</select><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<th align="right" class="thStyle">通信地址:</th>
							<td colspan="4">
								<input type="text" name="address"  class="validate[length[1,60]] textinput aloneTwoCol"/>
							</td>
						</tr>
					</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn-vip btn-pink" id="uploader_ok" onclick="create_submit('create_form_id','create_dialog_id');">确定</button>
				<button type="reset" class="btn-vip btn-white" onclick="reset('create_form_id','create_form_id')">清空</button>
				<button type="button" class="btn-vip btn-white" id="uploader_close"
					onclick="close_dialog1('create_dialog_id','create_form_id');">关闭</button>
			</div>
	 </div>
</div>
