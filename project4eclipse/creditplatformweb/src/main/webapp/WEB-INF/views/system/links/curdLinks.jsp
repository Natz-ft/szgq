<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/views/common/include.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>通知管理</title>
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
               		 
               		<button type="button" onclick="create_page();" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="save_config" >新建</button>
	                <!-- 开发人员根据更新服务的URL ，将URL 作为参数传入 例如本案例的更新URL为‘/systemConfig/getSystemConfigById.do’，则在update_button方法中，出入该URL -->
	                <!-- 这里还需要更改后台返回页面 -->
	                <button type="button" onclick="delete_button('id','/links/delLinks?id=');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="delete_config" >删除</span></button>
	      	  	    <button type="button" onclick="query_conditions('query_conditions');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="select_config" >查询</span></button> 
          	   </td>
          		</tr>
		</thead>
		<thead>
			<tr>
				<td align="center"><input class="inputCheckBox" type="checkbox" id="id" onclick="selectAll(this,'id');"/></td>
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td>友情链接名称</td>
				<td>友情链接地址</td>
				<td>友情链接logo</td>
				<td>创建时间</td>
				<td>操作</td>
				<!-- 开发人员根据业务需求，自定义表头字段  结束-->
			</tr>
		</thead>
		<tbody >
			<c:forEach var="links" items="${page.list}">
				<tr >
					<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
					<td>
					<input autocomplete="off"   name="id" value="${links['id'] }" class="inputCheckBox" type="checkbox"  /></td>
					<td>${links['name'] }</td>
					<td>${links.url }</td>
					<td><img src="${links.logo }" onerror="this.src='/creditplatformweb/static/image/uploadImg.jpg' " height="30px" width="40px" /></td>
					<td><fmt:formatDate value="${links['createTime'] }" pattern='yyyy-MM-dd HH:mm:ss'/></td>
					<td><a href="#" onClick="updateDetail('${links['id'] }');">编辑</a></td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
				</tr>
			</c:forEach>
		</tbody>
</table>
<form action="curdLinks" id="list_form_id" method="post" name="list_form_name" onkeydown= "if(event.keycode==13)return   false;">  
	<c:import url="/WEB-INF/views/common/pagination.jsp" />
	<!-- 修改为自己查询页面的路径 -->
	<c:import url="/WEB-INF/views/system/links/query_condition.jsp" />
</form>
<!-- 提示页面应该作为公共页面，没必要每个人都拷贝 -->
<c:import url="/WEB-INF/views/system/links/show_links.jsp" /> 
<!-- 修改为自己添加页面的路径 -->
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
function create_page() {
	layer.open({
  	  type: 2,
  	  title: '新增',
  	  shadeClose: true,
  	  maxmin: true,	
  	  scrollbar: false,
  	  shade: 0,
  	  area: ['70%', '85%'],
  	  content: '/creditplatformweb/links/createPage' //iframe的url
  	}); 
	//window.location.href="/creditplatformweb/news/createPage";
}

function updateDetail(id) {
	layer.open({
	  	  type: 2,
	  	  title: '编辑',
	  	  shadeClose: true,
	  	  maxmin: true,	
	  	  scrollbar: false,
	  	  shade: 0,
	  	  area: ['70%', '85%'],
	  	  content: "/creditplatformweb/links/updateLinks?id=" + id //iframe的url
	  	}); 
	 
  
	//openWindow600("detailNews?id="+id);
}
</script>
 
 