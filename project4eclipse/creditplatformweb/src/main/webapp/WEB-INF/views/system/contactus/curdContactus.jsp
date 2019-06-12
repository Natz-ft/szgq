<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	                <!-- 开发人员根据更新服务的URL ，将URL 作为参数传入 例如本案例的更新URL为‘/systemConfig/getSystemConfigById.do’，则在update_button方法中，出入该URL -->
	                <!-- 这里还需要更改后台返回页面 -->
	                <button type="button" onclick="delete_button('id','/contactus/delContactus?id=');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="delete_config" >删除</span></button>
          	   </td>
          		</tr>
		</thead>
		<thead>
			<tr>
				<td align="center"><input autocomplete="off"   class="inputCheckBox" type="checkbox" id="id" onclick="selectAll(this,'id');"/></td>
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td>金融办名称</td>
				<td>地址</td>
				<td>邮编</td>
				<td>热线</td>
				<td>电子邮箱</td>
				<td>传真</td>
				<td>操作</td>
				<!-- 开发人员根据业务需求，自定义表头字段  结束-->
			</tr>
		</thead>
		<tbody >
			<c:forEach var="contactus" items="${page.list}">
				<tr >
					<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
					<td>
					<input name="id" value="${contactus['id'] }" class="inputCheckBox" type="checkbox"  /></td>
					<td>${contactus['name'] }</td>
					<td>${contactus['adress'] }</td>
					<td>${contactus['zipCode'] }</td>
					<td>${contactus['hotline'] }</td>
					<td>${contactus['mail'] }</td>
					<td>${contactus['fax'] }</td>
					<td><a href="#" onClick="updateDetail('${contactus['id'] }');">编辑</a></td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
				</tr>
			</c:forEach>
		</tbody>
</table>

<!-- 提示页面应该作为公共页面，没必要每个人都拷贝 -->
<c:import url="/WEB-INF/views/system/contactus/show_contactus.jsp" /> 
<!-- 修改为自己添加页面的路径 -->
<c:import url="/WEB-INF/views/system/contactus/create_page.jsp" />
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
	  function callback() {
      }

      layerOpen("demoId", "930", "600", "", "/creditplatformweb/news/createPage",2, callback);
  
	//window.location.href="/creditplatformweb/news/createPage";
}
function showDetail(id) {
	  function callback() {
      }

      layerOpen("demoId", "930", "600", "", "/creditplatformweb/contactus/detailContactus?id=" + id, 2, callback);
  
	//openWindow600("detailNews?id="+id);
}
function updateDetail(id) {
// 	  function callback() {
//       }

//       layerOpen("demoId", "930", "600", "", "/creditplatformweb/contactus/updateContactus?id=" + id, 2, callback);
      layer.open({
    	  type: 2,
    	  title: '编辑',
    	  shadeClose: true,
    	  maxmin: true,	
    	  scrollbar: false,
    	  shade: 0,
    	  area: ['70%', '85%'],
    	  content: '/creditplatformweb/contactus/updateContactus?id=' + id //iframe的url
    	});  
	//openWindow600("detailNews?id="+id);
}
</script>
 