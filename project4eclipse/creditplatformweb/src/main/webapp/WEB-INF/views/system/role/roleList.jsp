<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>角色管理</title>
</head>
<!--  -----------隐藏区域 ------------------- -->
<input type="hidden" value="${ctx }"  id="path">
<input type="hidden"  id="delUrl">
<!--  -----------隐藏区域 ------------------- -->

<div id="update_page" style="display:  none;"></div>
<div id="empower_page" style="display:  none;"></div>
<div>
<div class="popupbg" id="zhezhao"  style="display: none;"> </div>
<table id="listTable" class="listTable"  cellspacing="0" cellpadding="5" width="100%" border="0" >
		<thead >	
            <tr>
               <td class="tr_td_operate" colspan="8" align="left" >
               		 
	           	    <button type="button" onclick="create_button('create_dialog_id');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="save_config" >新建</button>
	                <!-- 开发人员根据更新服务的URL ，将URL 作为参数传入 例如本案例的更新URL为‘/systemConfig/getSystemConfigById.do’，则在update_button方法中，出入该URL -->
	                <!-- 这里还需要更改后台返回页面 -->
	                <button type="button" onclick="update_button('/roleManage/updateRole?id=','id','update_dialog_id');"class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="update_config" >修改</button>
	                <button type="button" onclick="delete_button('id','/roleManage/delRole?id=');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="delete_config" >删除</button>
	      	  	    <button type="button" onclick="query_conditions('query_conditions');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="select_config" >查询</button> 
	      	  	    <button type="button" onclick="empower_button('/roleManage/empower?id=','id','empower_dialog_id');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="role_config" style="width: 100px;">菜单权限</button> 
	      	  	   <!--  <button type="button" onclick="empowerButton();" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="button_config" style="width: 100px;">按钮权限</button>  -->
	      	  	    
          	   </td>
          	</tr>
		</thead>
		<thead>
			<tr>
				<td align="center"><input autocomplete="off"   class="inputCheckBox" type="checkbox" id="id" onclick="selectAll(this,'id');"  /></td>
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td>角色名称</td>
				<td>角色描述</td>
				<td>创建者</td>
				<td>创建时间</td>
				<td>更新时间</td>
				<!-- 开发人员根据业务需求，自定义表头字段  结束-->
			</tr>
		</thead>
		<tbody >
			<c:forEach var="role" items="${page.list}">
				<tr >
					<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
					<td><input name="id" value="${role.id }" class="inputCheckBox" type="checkbox"  /></td>
					<td>${role.name }</td>
					<td>${role.description }</td>
					<td>${role.createUser }</td>
					<td><fmt:formatDate value="${role.createTime }" pattern='yyyy-MM-dd HH:mm:ss'/></td>
					<td><fmt:formatDate value="${role.updateTime }" pattern='yyyy-MM-dd HH:mm:ss'/></td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
				</tr>
			</c:forEach>
		</tbody>
</table>
<form action="roleList" id="list_form_id" method="post" name="list_form_name" onkeydown= "if(event.keycode==13)return   false;">  
	<c:import url="/WEB-INF/views/common/pagination.jsp" />
	<!-- 修改为自己查询页面的路径 -->
	<c:import url="/WEB-INF/views/system/role/query_condition.jsp" />
</form>
<c:import url="/WEB-INF/views/system/role/show_message.jsp" /> 
<!-- 修改为自己添加页面的路径 -->
<c:import url="/WEB-INF/views/system/role/create_page.jsp" />
</div>

<script type="text/javascript">

function empowerButton() {
	var list = document.getElementsByName("id");
	var count = 0;
	var id;
	for (var i = 0; i < list.length; i++) {
		if (list[i].checked) {
			id = list[i].value;
			count++;
		}
	}
	// 只有选择一条是猜弹出更新页面，否则弹出提示消息，只能选择一条
	if (count == 1) {
		window.open("empowerButton?id=" + id, "", "height=600, width=800, top=200,left=300,alwaysRaised=yes,titlebar=no, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no");
		return;
	} else if (count == 0) {
		param("提示信息", "请选择您要赋予权限的角色", "每次只能选择一条,您未选择!");
		$("#t1").fadeIn(200);
	} else {
		param("提示信息", "请选择您要赋予权限的角色", "每次只能选择一条，您选择了多条目录!");
		$("#t1").fadeIn(200);
	}
}

</script>
