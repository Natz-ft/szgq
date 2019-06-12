<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/common/include.jsp"%>
<%-- <script type="text/javascript" src="${ctx }/static/js/user_manager.js"></script> --%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>通知管理</title>
	<style type="text/css">
	/* .stop1{
	
		background: url(${ctx}/static/image/user/lock.png);
	}
	 */
	.listTable input{
		border: none;
	}
	</style>
</head>
<!-- -----------隐藏区域 ------------------- -->
<input type="hidden" value="${ctx}"  id="path">
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
               <td class="tr_td_operate" colspan="8" align="left" >
               		 
	           	    <button type="button" onclick="create_page();" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="save_config" >新建</button>
	                <!-- 开发人员根据更新服务的URL ，将URL 作为参数传入 例如本案例的更新URL为‘/systemConfig/getSystemConfigById.do’，则在update_button方法中，出入该URL -->
	                <!-- 这里还需要更改后台返回页面 -->
<!-- 	                <button type="button" onclick="update_button('/inverstorUser/detail?id=','id','update_dialog_id');"class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="update_config" >修改</button> -->
	                <button type="button" onclick="delete_button('id','/inverstorUser/delUser?id=');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="delete_config" >删除</button>
	      	  	    <button type="button" onclick="query_conditions('query_conditions');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="select_config" >查询</button> 
          	   </td>
          		</tr>
		</thead>
		<thead>
			<tr>
				<td align="center"><input class="inputCheckBox" type="checkbox" id="id" onclick="selectAll(this,'id');"  /></td>
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td>用户账号</td>
				<td>基金名称</td>
				<!-- <td>用户类型</td> -->
				<td>上次成功登录时间</td>
				<td>操作</td>
				<!-- 开发人员根据业务需求，自定义表头字段  结束-->
			</tr>
		</thead>
		<tbody >
			<c:forEach var="user" items="${page.list}" varStatus="status">
				<tr >
					<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
					<td><input class="inputCheckBox" type="checkbox" name="id" value="${user.id }" type="checkbox"  /></td>
					<td><a href="#" onclick="updateUser('${user.id }')">${user.username}</a></td>
					<td>${user.subacName}</td>
					<%-- <td>
						<c:forEach var="usertype" items="${dicWord.usertype }">
							<c:if test="${user.type== usertype.key }">${usertype.value }</c:if>
						</c:forEach>
					</td> --%>
					<td><fmt:formatDate value="${successLog[status.count-1].loginTime }" pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td>
					<input class="stop1"  style="width:70px;" type="button" value="${user.stopFlag }" onclick="stop_button(this,'stop_reason_id','${user.id }')">
					<input class="lock1"  style="width:70px;" type="button" value="${user.lockFlag }" onclick="unlockUser('${user.id }',this)" >
					<input class="reset1" style="width:90px;" type="button" value="重置密码" onclick="resetPassword('${user.id }','${user.username}')">
					
					</td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
				</tr>
			</c:forEach>
		</tbody>
</table>
<form action="userList.do" id="list_form_id" method="post" name="list_form_name" onkeydown= "if(event.keycode==13)return   false;">  
	<c:import url="/WEB-INF/views/common/pagination.jsp" />
	<c:import url="/WEB-INF/views/system/user/query_investorUser.jsp" />
	<!-- 修改为自己查询页面的路径 -->
</form>
<!-- 提示页面应该作为公共页面，没必要每个人都拷贝 -->
<c:import url="/WEB-INF/views/system/user/show_message2.jsp" /> 
<!-- 修改为自己添加页面的路径 -->
<%-- <c:import url="/WEB-INF/views/system/user/createInvestUser.jsp" /> --%>
<c:import url="/WEB-INF/views/system/user/stop_reason.jsp" />
</div>

 <script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
<script type="text/javascript">
//点击列表中的用户名，可展示该用户详细信息，并可进行修改
function showInfo(id){
	var url = $("#path").val()+"/inverstorUser/detail?id="+id;
	$.ajax({
			type:'post',
			url:url,
			success:function(data) {
				$("#update_page").html(data);
				$("#update_page").show();
				$("#zhezhao").show();
				$(".zDialogCon").css("overflow","hidden");
			},error: function(){  
				param("提示信息","系统错误，请重试","");
			$("#t1").fadeIn(200);  
        }
		});
}


function stopUser(id){
		var url=$("#path").val()+"/admin/user/stopUser?id="+id;
		$.ajax({
			type:'post',
			url:url,
			success:function(data) {
				param("提示信息","停用用户成功","");
				$("#t1").fadeIn(200);
			},error: function(){  
				param("提示信息","停用用户失败","");
				$("#t1").fadeIn(200);  
	    }
		});
}
function startUser(id){
	var url=$("#path").val()+"/admin/user/startUser?id="+id;
	$.ajax({
		type:'post',
		url:url,
		success:function(data) {
			param("提示信息","启用用户成功","");
			$("#t1").fadeIn(200);
		},error: function(){  
			param("提示信息","启用用户失败","");
			$("#t1").fadeIn(200);  
    }
	});
}
$(function(){
	var stop1,lock1 = "";
	$(".stop1").each(function(){
		stop1 = $(this).val();
		if($.trim(stop1)==2){
			$(this).css("background","url(${ctx}/static/image/user/start.png) no-repeat left center");
			$(this).val("启用");
		}else if($.trim(stop1)==1){
			$(this).css("background","url(${ctx}/static/image/user/stop.png) no-repeat left center");
			$(this).val("停用");
		}else{
			
		}
	}) 
	 $(".lock1").each(function(){
	 	lock1 = $(this).val();
		if($.trim(lock1)==2){
			$(this).val("锁定");
			$(this).css("background","url(${ctx}/static/image/user/lock.png) no-repeat left center");
		}else if($.trim(lock1)==0){
			$(this).val("未锁");
			$(this).css("background","url(${ctx}/static/image/user/unlock.png) no-repeat left center");
		}else if($.trim(lock1)==1){
			$(this).val("自动锁");
			$(this).css("background","url(${ctx}/static/image/user/lock.png) no-repeat left center");
		}else{
			
		}
	}) 
}); 


function resetPassword(id,username){
	var pass=${sessionScope.password};
	param("提示信息","确认重置"+username+"的密码 ？"+"初始化密码为:"+pass+"&nbsp;&nbsp;","");
	$("#t4").fadeIn(200);
	$("#reset_flag").val(id);
}

function confirmReset(){
	var id=$("#reset_flag").val();
	var url = $("#path").val()+"/inverstorUser/resetPassword?id="+id;
	$.ajax({
		type:'post',
		url:url,
		success:function(data) {
			data = JSON.parse(data);
			param("提示信息","密码重置成功","");
			$("#t1").fadeIn(200);
		},error: function(){  
			param("提示信息","密码重置失败","");
			$("#t1").fadeIn(200);  
    }
	});
}

function unlockUser(id,obj){
	if($(obj).val()=="解锁"){
		var url=$("#path").val()+"/admin/user/unlockUser?id="+id;
		$.ajax({
			type:'post',
			url:url,
			success:function(data) {
				param("提示信息","用户已解锁","");
				$("#t1").fadeIn(200);
			},error: function(){  
				param("提示信息","解锁用户失败","");
				$("#t1").fadeIn(200);  
	    }
		});
	}else{
	}
	
}
$(function(){
	$(".lock1").each(function(){
		$(this).mouseover(function(){
			if($(this).val()=="锁定"){
				$(this).val("解锁");
			}
		});
		$(this).mouseout(function(){
			if($(this).val()=="解锁"){
				$(this).val("锁定");
			}
		});
	});
});
function create_page() {
//	  function callback() {
//     }
//     layerOpen("demoId", "930", "600", "", ["/creditplatformweb/news/createPage"],2, callback);
    
     layer.open({
         title: '创建用户页面',
     	 content: '/creditplatformweb/inverstorUser/createInvestUser' ,//iframe的url
   	  	 area: ['100%', '100%'],
   	     offset: ['0%', '0%'],
         type:2,
         scrollbar: false,
     });
	//window.location.href="/creditplatformweb/news/createPage";
}
function updateUser(id) {
//	  function callback() {
//   }
//   layerOpen("demoId", "930", "600", "", ["/creditplatformweb/news/createPage"],2, callback);
  
   layer.open({
       title: '修改用户页面',
   	 content: '/creditplatformweb/inverstorUser/detail?id='+id ,//iframe的url
       area: ['100%', '100%'],
       offset: ['0%', '0%'],
       type:2,
       scrollbar: false,
   });
	//window.location.href="/creditplatformweb/news/createPage";
}
function create_button(create_dialog_id) {
	
	var url = $("#path").val()+'/inverstorUser/createPage';
	$.ajax({
		type:'post',
		data:'org=org',
		dataType : 'json',
		url:url,
		success:function(data){
			$("#"+create_dialog_id).show();
			$("#zhezhao").show();
			$(".zDialogCon").css("overflow","hidden");
		},error:function(){
			param("提示信息","系统错误，请重试","");
			$("#t1").fadeIn(200);  
		}
	});
	
}
function del_successprocess(data) {
	if (data.code =="00000") {
	 		return true;
 	}else{
 		return false;
 	}
}
</script>
  