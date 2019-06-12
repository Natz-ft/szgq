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
<table id="listTable" class="listTable"  cellspacing="0" cellpadding="5" width="60%" border="0" >
		<thead >	
              <tr>
               <td class="tr_td_operate" colspan="10" align="left" >
	      	  	    <button type="button" onclick="query_conditions('query_conditions');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="select_config" >查询</span></button> 
          	   </td>
          		</tr>
		</thead>
		<thead>
			<tr>
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td align="center" style="display:none"></td>
				<td>任务名称</td>
				<td>执行状态</td>
				<td>开始时间</td>
				<td>结束时间</td>
				<td class="excepiton">执行结果</td>
				<!-- 开发人员根据业务需求，自定义表头字段  结束-->
			</tr>
		</thead>
		<tbody >
			<c:forEach var="log" items="${page.list}">
				<tr >
					<td align="center" style="display:none"></td>
					<td>${log.jobName}</td>
					<td>${log.exeResult}</td> 
					<td><fmt:formatDate value="${log['startTime'] }" pattern='yyyy-MM-dd HH:mm:ss'/></td>
					<td><fmt:formatDate value="${log['endTime'] }" pattern='yyyy-MM-dd HH:mm:ss'/></td> 
					<td style="width:260px">
					<div class="logresultDiv">
						${log.failReason}
					</div>
					</td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
				</tr>
			</c:forEach>
		</tbody>
</table>
<form aciton="systemConfig/systemConfigList.do" id="list_form_id"  name="list_form_name" onkeydown= "if(event.keycode==13)return   false;" method="post">  
	<c:import url="/WEB-INF/views/common/pagination.jsp" />
	<!-- 开发人员需要将system/config/query_condition.jsp --》 替换成自己相应的查询条件页面 -->
	<c:import url="/WEB-INF/views/system/operate/queryCondition.jsp" />
</form>
<c:import url="/WEB-INF/views/system/config/tabs.jsp" />
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