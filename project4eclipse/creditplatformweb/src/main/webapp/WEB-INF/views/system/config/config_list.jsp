<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>系统参数</title>
</head>
<!--  -----------隐藏区域 ------------------- -->
<input type="hidden" value="${ctx }"  id="path">
<input type="hidden"  id="delUrl">
<!--  -----------隐藏区域 ------------------- -->
<div id="update_page" style="display:  none;"></div>
<div>
<div class="popupbg" id="zhezhao"  style="display: none;"> </div>
<table id="listTable" class="listTable"  cellspacing="0" cellpadding="5" width="100%" border="0" >
		<thead >	
              <tr>
               <td class="tr_td_operate" colspan="8" align="left" >
	           	    <button type="button" onclick="create_button('create_dialog_id');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="save_config" >新建</button>
	                <!-- 开发人员根据更新服务的URL ，将URL 作为参数传入 例如本案例的更新URL为‘/systemConfig/getSystemConfigById.do’，则在update_button方法中，出入该URL -->
	                <button type="button" onclick="update_button('/systemConfig/getSystemConfigById.do?id=','ids','update_dialog_id');"class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="update_config" >修改</button>
	                <!-- 开发人员根据删除服务的URL ，将URL 作为参数传入 例如本案例的更新URL为‘/systemConfig/delete_systemConfig.do’，则在delete_button方法中，出入该URL -->
	                <button type="button" onclick="delete_button('ids','/systemConfig/delete_systemConfig.do?ids=');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="delete_config" >删除</span></button>
	      	  	    <button type="button" onclick="query_conditions('query_conditions');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="select_config" >查询</span></button> 
	            	<%-- <button type="button" onclick="tabs();" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="mark_config">页签</span></button>  --%>
          	   </td>
          		</tr>
		</thead>
		<thead>
			<tr>
				<td align="center"><input class="inputCheckBox" type="checkbox" id="id" onclick="selectAll(this,'ids');"/></td>
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td>参数名称</td>
				<td>参数数值</td>
				<td>参数描述</td>
				<td>创建用户</td>
				<td>创建时间</td>
				<td>更改时间</td>
				<!-- 开发人员根据业务需求，自定义表头字段  结束-->
			</tr>
		</thead>
		<tbody >
			<c:forEach var="config" items="${page.list}">
				<tr >
					<td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
					<input autocomplete="off"   name="ids" value="${config.configId}" class="inputCheckBox" type="checkbox"  /></td>
					<td>${config.configName}</td>
					<td>${config.configValue}</td>
					<td>${config.configDesc}</td>
					<td>${config.createUser}</td>
					<td><fmt:formatDate value="${config.createTime }" pattern='yyyy-MM-dd HH:mm:ss' /></td> 
					<td><fmt:formatDate value="${config.updateTime }" pattern='yyyy-MM-dd HH:mm:ss' /></td> 
					<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
				</tr>
			</c:forEach>
		</tbody>
</table>
<form aciton="systemConfig/systemConfigList.do" id="list_form_id"  name="list_form_name" onkeydown= "if(event.keycode==13)return   false;" method="post">  
	<c:import url="/WEB-INF/views/common/pagination.jsp" />
	<!-- 开发人员需要将system/config/query_condition.jsp --》 替换成自己相应的查询条件页面 -->
	<c:import url="/WEB-INF/views/system/config/query_condition.jsp" />
</form>
<!-- 开发人员需要将system/config/tabs.jsp --》 替换成自己相应的页签页面 。 如果没有页签需求，可以不引入改页面。-->
<c:import url="/WEB-INF/views/system/config/tabs.jsp" />
<c:import url="/WEB-INF/views/common/tip.jsp" />
<!-- 开发人员需要将system/config/create_page.jsp --》 替换成自己相应的新建页面 -->
<c:import url="/WEB-INF/views/system/config/create_page.jsp" />
</div>

 
 <script type="text/javascript">
 
 /**
  * 
  * @param data
  * @returns {Boolean}
  */
 function del_successprocess(data) {
 	if (data.code =="00000") {
 	 		return true;
  	}else{
  		return false;
  	}
 }
 </script>