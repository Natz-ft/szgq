<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/common/include.jsp"%>
<%-- <script type="text/javascript" src="${ctx }/static/js/user_manager.js"></script> --%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>任务管理</title>
	<style type="text/css">
	.listTable input{
		border: none;
	}
	</style>
</head>
<!-- -----------隐藏区域 ------------------- -->
<input type="hidden" value="${ctx }"  id="path">
<input type="hidden"  id="delUrl">
<!-- -----------隐藏区域 ------------------- -->

<div id="update_page" style="display:   none;"></div>
<div id="grant_role" style="display: none;"></div>
<input id="reset_flag" type="hidden"/>

<div>
<div class="popupbg" id="zhezhao"  style="display: none;"> </div>
<table id="listTable" class="listTable"  cellspacing="0" cellpadding="5" width="100%" border="0" >
		<thead >	
              <tr>
               <td class="tr_td_operate" colspan="9" align="left" >
<!-- 	      	  	    <button type="button" onclick="create_page();" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="save_config" >新建</button> -->
<!-- 	                <button type="button" onclick="delete_button('id','/news/delNews?id=');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="delete_config" >删除</span></button> -->
	      	  	    
	      	  	    <button type="button" onclick="query_conditions('query_conditions');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="select_config" >查询</button> 
          	   </td>
          		</tr>
		</thead>
		<thead>
			<tr>
<!-- 				<td align="center"><input class="inputCheckBox" type="checkbox" id="id" onclick="selectAll(this,'id');"  /></td> -->
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td align="center" style="display:none"></td>
				<td>任务名称</td>
				<td>任务描述</td>
				<td>任务状态</td>
				<td>执行次数</td>
				<td>本次执行时间</td>
				<td>下次执行时间</td>
				<td>操作</td>
				<!-- 开发人员根据业务需求，自定义表头字段  结束-->
			</tr>
		</thead>
		<tbody >
			<c:forEach var="task" items="${page.list}" varStatus="status">
				<tr >
					<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
					<td align="center" style="display:none"></td>
<%-- 					<td><input class="inputCheckBox" type="checkbox" name="id" value="${task.jobId }" type="checkbox"  /></td> --%>
					<td>${task.jobName}</td>
					<td>${task.jobDescription}</td>
					<td>${task.statusStr}</td>
					
<!-- 					<td> --> 
<%-- 						<c:forEach var="status" items="${sessionScope.status }"> --%>
<%-- 							<c:if test="${task.status== status.key }">${status.value }</c:if> --%>
<%-- 						</c:forEach> --%>
<!-- 					</td> -->
					<td>${task.exeCount}</td>
					<td><fmt:formatDate value="${task['jobData'] }" pattern='yyyy-MM-dd HH:mm:ss'/></td>
					<td>${task.nextjobData}</td>
					<td>
                     <!--立即执行 -->
<%-- 					<input class="stop1"  style="width:70px;" type="button" value="${user.stopFlag }" onclick="stop_button(this,'stop_reason_id','${user.id }')"> --%>
<%-- 					<c:if test="${task.status != '00' }"> --%>
						<input class="remove1"  style="width:90px;" type="button" value="${task.stopFlag }" onclick="removeJob('${task.jobId }','${task.stopFlag }')" >
<%-- 					</c:if> --%>
					<input class="reset1" style="width:90px;" type="button" value="设置时间" onclick="resetCron('${task.jobId }')">
					</td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
				</tr>
			</c:forEach>
		</tbody>
</table>
<form action="taskList" id="list_form_id" method="post" name="list_form_name" onkeydown= "if(event.keycode==13)return   false;">  
	<c:import url="/WEB-INF/views/common/pagination.jsp" />
	<c:import url="/WEB-INF/views/system/task/queryCondition.jsp" />
	<!-- 修改为自己查询页面的路径 -->
</form>
<!-- 提示页面应该作为公共页面，没必要每个人都拷贝 -->
<c:import url="/WEB-INF/views/system/user/show_message.jsp" /> 
<!-- 修改为自己添加页面的路径 -->
<c:import url="/WEB-INF/views/system/task/create_page.jsp" />
<c:import url="/WEB-INF/views/system/user/stop_reason.jsp" />
</div>
 <script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
<script>
function removeJob(id,isStop){
	if(isStop == "2"){//恢复任务
		var url=$("#path").val()+"/task/startJob?id="+id;
		$.ajax({
			type:'post',
			url:url,
			success:function(data) {
				param("提示信息","移除任务成功","");
				$("#t1").fadeIn(200);
			},error: function(){  
				param("提示信息","移除任务失败","");
				$("#t1").fadeIn(200);  
	    }
		});
	}else{//移除任务
		var url=$("#path").val()+"/task/stopJob?id="+id;
		$.ajax({
			type:'post',
			url:url,
			success:function(data) {
				param("提示信息","恢复任务成功","");
				$("#t1").fadeIn(200);
			},error: function(){  
				param("提示信息","恢复任务失败","");
				$("#t1").fadeIn(200);  
	    }
		});
	}
		
}
function resetCron(id){
	layer.open({
	 	  type: 2,
	 	  title: '设置时间',
	 	  shadeClose: false,
	 	  maxmin: false,	
	 	  scrollbar: false,
	 	  shade: 0,
	 	  area: ['50%', '77%'],
	 	  content: "/creditplatformweb/task/resetCron?id=" + id //iframe的url
	 	});
	
}
$(function(){
	$(".remove1").each(function(){
		var stop1 = $(this).val();
		if($.trim(stop1)==2){
			$(this).css("background","url(${ctx}/static/image/user/start.png) no-repeat left center");
			$(this).val("恢复任务");
		}else if($.trim(stop1)==1 || $.trim(stop1)==0){
			$(this).css("background","url(${ctx}/static/image/user/stop.png) no-repeat left center");
			$(this).val("移除任务");
		}else{
			
		}
	})
}); 



</script>
  