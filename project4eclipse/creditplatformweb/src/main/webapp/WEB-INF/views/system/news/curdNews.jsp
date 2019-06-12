<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/views/common/include.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
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
	                <button type="button" onclick="delete_button('id','/news/delNews?id=');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="delete_config" >删除</span></button>
	      	  	    <button type="button" onclick="query_conditions('query_conditions');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="select_config" >查询</span></button> 
          	   </td>
          		</tr>
		</thead>
		<thead>
			<tr>
				<td align="center"><input class="inputCheckBox" type="checkbox" id="id" onclick="selectAll(this,'id');"/></td>
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td>新闻标题</td>
				<td>新闻类型</td>
				<td>新闻日期</td>
				<td>是否轮播</td>
				<td>轮播顺序</td>
				<td>创建时间</td>
				<td>操作</td>
				<!-- 开发人员根据业务需求，自定义表头字段  结束-->
			</tr>
		</thead>
		<tbody >
			<c:forEach var="news" items="${page.list}">
				<tr >
					<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
					<td><input autocomplete="off"  name="id" value="${news['id'] }" class="inputCheckBox" type="checkbox"  /></td>
					<td><a href="#" onClick="showDetail('${news['id'] }');">${news['title'] }</a></td>
					<td>${news['newsTypeStr'] }</td>
					<td>${news['newsDate'] }</td>
					<td>
						<c:choose>
							<c:when test="${news['isLunbo']==1 }">是</c:when>
							<c:otherwise>否</c:otherwise>
						</c:choose>
					</td>
					<td><c:if test="${empty news['lunboOrd'] }">--</c:if>${news['lunboOrd'] }</td>
					<td><fmt:formatDate value="${news['createTime'] }" pattern='yyyy-MM-dd HH:mm:ss'/></td>
					<td><a href="#" onClick="updateDetail('${news['id'] }');">编辑</a></td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
				</tr>
			</c:forEach>
		</tbody>
</table>
<form action="curdNews.do" id="list_form_id" method="post" name="list_form_name" onkeydown= "if(event.keycode==13)return   false;">  
	<c:import url="/WEB-INF/views/common/pagination.jsp" />
	<!-- 修改为自己查询页面的路径 -->
	<c:import url="/WEB-INF/views/system/news/query_condition.jsp" />
</form>
<!-- 提示页面应该作为公共页面，没必要每个人都拷贝 -->
<c:import url="/WEB-INF/views/system/news/show_news.jsp" /> 
<!-- 修改为自己添加页面的路径 -->
</div>
 <script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
<script type="text/javascript">
function CheckImgExists(imgurl) {  
    var ImgObj = new Image(); //判断图片是否存在  
    ImgObj.src = imgurl;  
    //没有图片，则返回-1  
    if (ImgObj.fileSize > 0 || (ImgObj.width > 0 && ImgObj.height > 0)) {  
        return true;  
    } else {  
        return false;
    }  
} 
function del_successprocess(data) {
	if (data.code =="00000") {
	 		return true;
 	}else{
 		return false;
 	}
}
function create_page() {
// 	  function callback() {
//       }
//       layerOpen("demoId", "930", "600", "", ["/creditplatformweb/news/createPage"],2, callback);
       layer.open({
    	  type: 2,
    	  title: '新增',
    	  shadeClose: true,
    	  maxmin: true,	
    	  scrollbar: false,
    	  shade: 0,
    	  area: ['70%', '85%'],
    	  content: '/creditplatformweb/news/createPage' //iframe的url
    	});  
	//window.location.href="/creditplatformweb/news/createPage";
}
function showDetail(id) {
	 
	layer.open({
       	  type: 2,
       	  title: '详情',
       	  shadeClose: true,
       	  maxmin: true,	
       	  scrollbar: false,
       	  shade: 0,
       	  area: ['70%', '85%'],
       	  content: "/creditplatformweb/news/detailNews?id=" + id //iframe的url
       	});
	//openWindow600("detailNews?id="+id);
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
   	  content: "/creditplatformweb/news/updateNews?id=" + id //iframe的url
   	});
	 
  
	//openWindow600("detailNews?id="+id);
}
</script>
 
