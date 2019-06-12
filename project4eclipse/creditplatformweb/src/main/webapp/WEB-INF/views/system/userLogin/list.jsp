<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>用户访问日志</title>
</head>
<!--  -----------隐藏区域 ------------------- -->
<input type="hidden" value="${ctx }"  id="path">
<input type="hidden"  id="delUrl">
<!--  -----------隐藏区域 ------------------- -->
<div id="update_page" style="display: none;"></div>
<div>
<div class="popupbg" id="zhezhao"  style="display: none;"> </div>
<table id="listTable" class="listTable"  cellspacing="0" cellpadding="5" width="100%" border="0" >
		<thead >	
             		 <tr>
		               <td class="tr_td_operate" colspan="10" align="left" >
			      	  	    <button type="button" onclick="query_conditions('query_conditions');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="select_config" >查询</button> 
		          	</td>
	          	</tr>
		</thead>
		<thead>
			<tr>
				<td align="center"><input class="inputCheckBox" type="checkbox" id="id" onclick="selectAll(this,'ids');" /></td>
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td>用户名</td>
				<td>IP</td>
				<td>时间</td>
				<td>登录成功或失败</td>
			</tr>
		</thead>
		<tbody >
			<c:forEach var="SystemUserLoginLog" items="${page.list }">
				<tr >
					<td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
					<input class="inputCheckBox" name="ids" value="${SystemUserLoginLog.id }" type="checkbox"  /></td>
					<td>
					<c:forEach var="sss" items="${sysUserLogMap }">
				    	<c:if test="${sss.key==SystemUserLoginLog.userId }">${sss.value }</c:if>
				    </c:forEach>
					</td>
					<td>${SystemUserLoginLog.loginIp}</td>
				    <td>${SystemUserLoginLog.loginTime }</td>
				    <td>
				    <c:forEach var="sss" items="${dicWord.successFlag }">
				    	<c:if test="${sss.key==SystemUserLoginLog.successFlag }">${sss.value }</c:if>
				    </c:forEach>
				    </td>
				</tr>
			</c:forEach>
		</tbody>
</table>
<form action="list.do" id="list_form_id"  name="list_form_name" onkeydown= "if(event.keycode==13)return   false;" method="post">  
	<c:import url="/WEB-INF/views/common/pagination.jsp" />
	<c:import url="/WEB-INF/views/system/userLogin/queryCondition.jsp" />
</form>
<%-- <c:import url="/WEB-INF/views/system/config/tabs.jsp" /> --%>
<c:import url="/WEB-INF/views/common/tip.jsp" />
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