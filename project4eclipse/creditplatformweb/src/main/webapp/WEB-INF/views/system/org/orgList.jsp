<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>机构管理</title>
	<style type="text/css">
	.listTable input{
		border: none;
	}
	</style>
</head>
<!--  -----------隐藏区域 ------------------- -->
<input type="hidden" value="${ctx }"  id="path">
<input type="hidden"  id="delUrl">
<!--  -----------隐藏区域 ------------------- -->

<div id="update_page" style="display: none;"></div>
<div>
<div class="popupbg" id="zhezhao"  style="display: none;"> </div>
	<table id="listTable" class="listTable" cellspacing="0" cellpadding="5" width="100%" border="0">
		<thead>
              <tr>
				<td class="tr_td_operate" colspan="8" align="left">
					<button type="button" onclick="orgConfCreate();" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6" role="button" aria-disabled="false" id="save_config">
						新建
					</button> <!-- 开发人员根据更新服务的URL ，将URL 作为参数传入 例如本案例的更新URL为‘/systemConfig/getSystemConfigById.do’，则在update_button方法中，出入该URL -->
					<button type="button" onclick="orgConfUpdate();" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6" role="button" aria-disabled="false" id="update_config">
						修改
					</button> <!-- 开发人员根据删除服务的URL ，将URL 作为参数传入 例如本案例的更新URL为‘/systemConfig/delete_systemConfig.do’，则在delete_button方法中，出入该URL -->
					<button type="button" onclick="deleteButton('ids','/sysOrg/deleteOrg.do?ids=');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6" role="button" aria-disabled="false" id="delete_config">
						删除</span>
					</button>
					<button type="button" onclick="query_conditions('query_conditions');" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6" role="button" aria-disabled="false" id="select_config">
						查询</span>
					</button>
					<button type="button" onclick="orgConfDetail();" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6" role="button" aria-disabled="false" id="select_config">
						详情</span>
					</button> <%-- <button type="button" onclick="tabs();" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover button6"  role="button" aria-disabled="false" id="select_config">页签</span></button>  --%>
				</td>
			</tr>
		</thead>
		<thead>
			<tr>
				<td align="center"><input class="inputCheckBox" type="checkbox" id="id" onclick="selectAll(this,'ids');"  /></td>
				<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
				<td>机构编码</td>
				<td>机构名称</td>
				<td>上级机构</td>
				<!-- <td>机构所在地区代码</td> -->
				<td>详细地址</td>
				<td>邮政编码</td>
				<td>操作</td>
				<!-- <td>操作</td> -->
				<!-- 开发人员根据业务需求，自定义表头字段  结束-->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="org" items="${page.list}">
				<tr>
					<td>
						<!-- 开发人员根据业务需求，自定义表格内容字段 开始 --> <input class="inputCheckBox" name="ids" value="${org.orgid}" type="checkbox" />
					</td>
					<td>${org.orgid}</td>
					<td>${org.orgName}</td>
					<td>${org.uporg }</td>
					<%-- <td>${org.areaCode}</td> --%>
					<td>${org.address}</td>
					<td>${org.postCode}</td>
					<td><input class="stop1" type="button" value="${org.recordStopFlag}" onclick="stopButton('${org.recordStopFlag}','${org.id}')"></td>
					<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form aciton="systemConfig/systemConfigList.do" id="list_form_id" name="list_form_name" onkeydown="if(event.keycode==13)return   false;"  method="post">
		<c:import url="/WEB-INF/views/common/pagination.jsp" />
		<c:import url="/WEB-INF/views/system/org/query_condition.jsp" />
	</form>
	<c:import url="/WEB-INF/views/common/tip.jsp" />
	<!-- 自定义提示框 -->
	<c:import url="/WEB-INF/views/system/org/orgtip.jsp" />
	<div id="orgConfCreate">
		<!-- 修改 添加 嵌入的内容 -->
	</div>

	<!-- 配置机构信息提交后信息框 -->
	<div class="tip" id="t4">
		<div class="tiptop">
			<span>提示信息</span><a></a>
		</div>
		<div class="tipinfo">
			<span><img src="../static/image/tip/ticon.png" /></span>
			<div class="tipright">
				<p>提示消息</p>
				<cite>提示消息副标题</cite>
			</div>
		</div>
		<div class="tipbtn">
			&nbsp;&nbsp;&nbsp; <input name="sure" type="button" class="btn-vip btn-pink" value="确定" onclick="close_tip();" /> &nbsp;&nbsp;&nbsp;
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$(".stop1").each(function() {
			var recordStopFlag = $(this).attr("value");
			if (recordStopFlag == "1") {
				$(this).val("停用");
				$(this).css("background", "url(${ctx}/static/image/user/stop.png) no-repeat left center");
			} else {
				$(this).val("启用");
				$(this).css("background", "url(${ctx}/static/image/user/start.png) no-repeat left center");
			}
		})
	})
	/**
	 *recordStopFlag 启用  或者停用
	 * id
	 **/
	function stopButton(recordStopFlag, id) {
		if (recordStopFlag == "1") {
			recordStopFlag = 2;
			flagMess = "停用";
		} else {
			recordStopFlag = 1;
			flagMess = "启用";
		}
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/sysOrg/updateStopFlag.do?id=' + id + "&recordStopFlag=" + recordStopFlag,
			success : function(data) {
				var obj = eval('(' + data + ')');
				if (obj.code == "00000") {
					if (obj.objectData.recordStopFlag == "1") {
						param("提示信息", "启用成功", "");
						$("#t1").fadeIn(200);
					} else {
						param("提示信息", "停用成功", "");
						$("#t1").fadeIn(200);
						
					}
					$("#t1").fadeIn(200);
				} else {
					param("提示信息", "操作失败", "");
					$("#t1").fadeIn(200);
				}
			},
			error : function() {
				alert("系统错误，请重试");
			}
		});
	}
	/**
	 * Button提交之后弹出的提示框
	 */
	function close_tip() {
		$("#t4").hide();
		$('#org_create_form_id')[0].reset();
	}
	/**
	 * 
	 * @param data
	 * @returns {Boolean}
	 */
	function del_successprocess(data) {
		if (data.code == "00000") {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 机构配置详情
	 **/
	function orgConfDetail() {
		$("#zhezhao").show();
		$(".zDialogCon").css("overflow","hidden");
		//查询机构基本信息
		var boxValue;
		var objArr = document.getElementsByName("ids");
		var checked_counts = 0;
		for (var i = 0; i < objArr.length; i++) {
			if (objArr[i].checked) {
				boxValue = objArr[i].value;
				checked_counts++;
			}
		}
		if (checked_counts == 0) {
			param("提示信息", "请选择信息项", "");
			$("#t1").fadeIn(200);
			return false;
		}
		if (checked_counts > 1) {
			param("提示信息", "请只选择一条信息", "");
			$("#t1").fadeIn(200);
			return false;
		} else {
			//查询机构基本信息
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/sysOrg/getOrgFindByOrgidQuery.do?orgid=' + boxValue + "&operType=Q",
				success : function(data) {
					$("#orgConfCreate").html(data);
				},
				error : function() {
					alert("系统错误，请重试");
				}
			});
		}
	}
	/***
	 * 机构创建
	 */
	function orgConfCreate() {
		$("#zhezhao").show();
		$(".zDialogCon").css("overflow","hidden");
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/sysOrg/getOrgConfCreate.do?orgid=' + "" + "&operType=S",
			success : function(data) {
				$("#orgConfCreate").html(data);
			},
			error : function() {
				alert("系统错误，请重试");
			}
		}); 
	}
	/**
	 * 机构修改
	 **/
	function orgConfUpdate() {
		$("#zhezhao").show();
		$(".zDialogCon").css("overflow","hidden");
		//查询机构基本信息
		var boxValue;
		var objArr = document.getElementsByName("ids");
		var checked_counts = 0;
		for (var i = 0; i < objArr.length; i++) {
			if (objArr[i].checked) {
				boxValue = objArr[i].value;
				checked_counts++;
			}
		}
		if (checked_counts == 0) {
			param("提示信息", "请选择信息项", "");
			$("#t1").fadeIn(200);
			return false;
		}
		if (checked_counts > 1) {
			param("提示信息", "请只选择一条信息", "");
			$("#t1").fadeIn(200);
			return false;
		} else {
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/sysOrg/getOrgConfCreate.do?orgid=' + boxValue + "&operType=U",
				success : function(data) {
					$("#orgConfCreate").html(data);
				},
				error : function() {
					alert("系统错误，请重试");
				}
			});
		}
	}

	function deleteButton(ids) {
		var objArr = document.getElementsByName("ids");
		var checked_counts = 0;
		for (var i = 0; i < objArr.length; i++) {
			if (objArr[i].checked) {
				checked_counts++;
			}
		}
		if (checked_counts == 0) {
			param("提示信息", "请选择要删除项", "");
			$("#t1").fadeIn(200);
			return false;
		} else {
			param("提示信息", "确认是否要删除?", "");
			$("#orgt2").fadeIn(200);
		}

	}
	function deleteOrg() {
		$("#orgt2").hide();
		var objArr = document.getElementsByName("ids");
		var boxValue;
		var checked_counts = 0;
		var arrayObj = new Array();
		for (var i = 0; i < objArr.length; i++) {
			if (objArr[i].checked) {
				arrayObj.push(objArr[i].value);
			}
		}
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : '${pageContext.request.contextPath}/sysOrg/deleteOrg.do?ids=' + arrayObj,
			success : function(data) {
				var flag = del_successprocess(data);
				if (flag) {
					param("提示信息", "删除成功", "");
					$("#t1").fadeIn(200);
				} else {
					param("提示信息", "删除失败", "");
					$("#t1").fadeIn(200);
				}
			},
			error : function() {
				param("提示信息", "系统错误，请重试", "");
				$("#t1").fadeIn(200);
			}
		});
	}
	/***
	 关闭窗口
	 * id <div class="basepopopdiv popup357-381 base-wrap-m" id="orgConfCreate_id" > 即  
	 */
	function orgConf_close_dialog(id) {
		window.location.reload();
	}
</script>