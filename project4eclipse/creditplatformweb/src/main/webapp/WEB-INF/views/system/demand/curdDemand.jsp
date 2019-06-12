<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>需求管理</title>
</head>
<!--  -----------隐藏区域 ------------------- -->
<input type="hidden" value="${ctx }"  id="path">
<input type="hidden"  id="delUrl">
<!--  -----------隐藏区域 ------------------- -->
<div id="update_page" style="display:  none;"></div>
<div>
<div class="popupbg" id="zhezhao"  style="display: none;"> </div><table id="listTable" class="listTable"  cellspacing="0" cellpadding="5" width="100%" border="0" >
		<thead >	
              <tr>
               <td class="tr_td_operate" colspan="8" align="left" >
               		 
	      	  	    <button type="button" onclick="query_conditions('query_conditions');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="select_config" >查询</span></button> 
          	   </td>
          		</tr>
		</thead>
		<thead>
			<tr>
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td align="center" style="display:none"></td>
				<td>企业名称</td>
				<td>需求名称</td>
				<td>需求状态</td>
				<td>发布时间</td>
				<td>投递方式</td>
				<td>是否查看</td>
				<td>编辑</td>
				<!-- 开发人员根据业务需求，自定义表头字段  结束-->
			</tr>
		</thead>
		<tbody >
			<c:forEach var="faq" items="${page.list}">
				<tr >
					<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
					<td style="display:none"></td>
					<td>${faq['enterpriseName'] }</td>
					<td><a href="#" onClick="showDetail('${faq['infoId'] }');">${faq['projectNameShow'] }</a></td>
					<td> ${faq['statusStr'] } </td>
					<td><fmt:formatDate value="${faq['operdate'] }" pattern='yyyy-MM-dd HH:mm:ss'/></td>
					<td> ${faq['openStr'] } </td>
					<td> ${faq['sysLookStr'] } </td>
					<td><a href="#" onClick="upProjectName('${faq['infoId'] }');">编辑项目名称</a>
					<c:if test="${faq.revokeFlag== '2' }"><a href="#" onClick="auditDemand('${faq['infoId'] }');">审核</a></c:if>
					
					</td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
				</tr>
			</c:forEach>
		</tbody>
</table>
<form action="curdDemand" id="list_form_id" method="post" name="list_form_name" onkeydown= "if(event.keycode==13)return   false;">  
	<c:import url="/WEB-INF/views/common/pagination.jsp" />
	<!-- 修改为自己查询页面的路径 -->
	<c:import url="/WEB-INF/views/system/demand/query_condition.jsp" />
</form>
</div>
<script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
 <script type="text/javascript">
 function del_successprocess(data) {
		if (data.code =="00000") {
		 		return true;
	 	}else{
	 		return false;
	 	}
	}
function showDetail(id) {
	layer.open({
	  	  type: 2,
	  	  title: '详情',
	  	  shadeClose: true,
	  	  maxmin: true,	
	  	  scrollbar: false,
	  	  area: ['70%', '85%'],
	  	  content:  "/creditplatformweb/demand/detaildemand?id=" + id //iframe的url
	  	}); 
}
function auditDemand(id){
	layer.open({
	  	  type: 2,
	  	  title: '详情',
	  	  shadeClose: true,
	  	  maxmin: true,	
	  	  scrollbar: false,
	  	  area: ['70%', '85%'],
	  	  content:  "/creditplatformweb/demand/detaildemand?id=" + id //iframe的url
	  	}); 
}
function upProjectName(id) {
	layer.open({
	  	  type: 2,
	  	  title: '编辑项目名称',
	  	  shadeClose: true,
	  	  maxmin: true,	
	  	  scrollbar: false,
	  	  area: ['70%', '40%'],
	  	  content:  "/creditplatformweb/demand/upProjectName?id=" + id //iframe的url
	  	}); 
}
</script>