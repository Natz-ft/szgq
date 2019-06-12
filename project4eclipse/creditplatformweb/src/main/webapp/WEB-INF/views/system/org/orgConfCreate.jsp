<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/frame-free/frame/js/framework.js" type="text/javascript"></script>
<link href="${ctx}/static/css/flexible.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/style-alert.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/popup.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/style-dialog.css" rel="stylesheet" type="text/css" />
<%-- <script src="${ctx}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${ctx}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script> --%>
<style>
.title {
	/* color: white; */
	height: 26px;
	line-height: 26px;
	float: left;
	padding: 0 0 0 10px;
	font-weight: bold;
	display: block;
}

.spanDiV {
	float: right;
	padding: 0 10px 0 0;
	color: white;
	height: 26px;
	line-height: 26px;
}

.boxContent {
	width: 100%;
	background-color: #fff;
}

.ss1 {
	cursor: pointer;
	cursor: hand;
	color: #bd1067;
}

.box_topcenter {
	width: 100%;
	height: 30px;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
	line-height: 26px;
	background-repeat: repeat-x;
	overflow: hidden;
}
</style>
<script type="text/javascript">
	//动态设置属性,DIV动态设置伸缩框状态
	$(document).ready(function() {
		//获得获取加载之后伸缩框个数
		var boxLength = $(".box").length;
		for (var i = 0; i < boxLength; i++) {
			//给SPAN动态设置DIV 中ID
			$("#flexible" + i + " .ss1").attr("id", i);
			if ($("#orgidHide").val() == "") {
				$("#flexible" + i + " .ss1").hide();
			}
		}
		//将机构基本信息设置展开
		flexible(0, boxLength);
		//动态设置SPAN添加事件
		$(".box .ss1").bind("click", function(event) {
			var idSpan = $(this).attr("id");
			$("#idSpan").val(idSpan);
			flexible(idSpan, boxLength);
		});

	});
	/****
	 * 动态设置伸缩DIV
	 * id:控制伸缩的panel的序号
	 * id: switch+序号：0：收缩  1 ：展开
	 */
	function flexible(id, boxLength) {
		var createUrl = $("#flexible" + id).attr('dataurl');
		var newUrl = null;
		var appid = "";
		newUrl = createUrl.replace("xxx", $("#orgidHide").val());
		 if ($("#operType").val() == "Q") {
			newUrl = newUrl.replace("yyy", $("#operType").val());
		} 
		for (var i = 0; i < boxLength; i++) {
			if (parseInt(id) == i) {
				if ($("#boxContent" + i).is(":hidden")) {
					$("#flexible" + i + " .boxContent").css('display', 'block');
					/* $("#flexible" + i + " .ss1").text('收缩'); */
					$("#flexible" + i + " .ss1").text('');
					$("#iframeTest" + i).attr("src", newUrl);
				} else if ($("#boxContent" + i).is(":hidden") == false) {
					$("#flexible" + i + " .boxContent").css('display', 'none');
					/* $("#flexible" + i + " .ss1").text('展开'); */
					$("#flexible" + i + " .ss1").text('');
				}
			} else {
				$("#flexible" + i + " .boxContent").css('display', 'none');
				/* $("#flexible" + i + " .ss1").text('展开'); */
				$("#flexible" + i + " .ss1").text('');
			}
		}
	}
	//获嵌入IFRAME 高度
	function iframeHeight(id) {
		try {
			//iframe
			/* iframe.contentWindow; */
		     /* 	var thisheight = $("#iframeTest" + id).contents().find(".iframe").height(); */
		var iframe =	document.getElementById('iframeTest'+id);
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
		$("#iframeTest" + id).attr("height", height);
		} catch (e) {
			/* alert(e.name + ": " + e.message); */
		}
	}
	/**
	 * 创建成功之后跳到修改页面      ：获取Appid 获取到这个应用编号下的修改的地址。
	 **/
	function appidUrl() {
		var appId = $("#flexible" + $("#idSpan").val()).attr('appId');
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/sysOrg/findByAppId?appid=' + appId,
			success : function(data) {
				var obj = eval('(' + data + ')');
				var pageUrl = obj.pageUrl;
				pageUrl = pageUrl.replace("xxx", $("#orgidHide").val());
				$("#iframeTest" + $("#idSpan").val()).attr("src", pageUrl);
			},
			error : function() {
				param("提示信息", "系统错误，请重试", "");
				$("#t1").fadeIn(200);
			}
		});
	}
</script>
<!-- 提供给应用的机构编码 -->
<input type="hidden" id="orgidHide" value="${orgid }" />
<input type="hidden" id="operType" value="${operType}" />
<!-- 每个伸缩框序号值 -->
<input type="hidden" id="idSpan">
<div class="basepopopdiv popup357-381 base-wrap-xl" id="orgConfCreate_id" style="display: block; ">
	<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false" style="display: block;" align="center">
		<div class="tiptop">
			<span>机构管理</span><a href="javascript:orgConf_close_dialog('orgConfCreate_id');"></a>
		</div>
		<div class="modal-body selectmodal wrap-xl" style="overflow:auto;">
			<!-- 获取应用配置 -->
			<div id="other" style="height: auto; width: 100%;">
				<table id="usertb" width="655" border="0" cellpadding="2" cellspacing="0">
					<tr>
						<td><div class="box" style="padding: 2px 4px 4px 0;" id="flexible0" panelTitle="机构基本信息" dataUrl="${ctx}/sysOrg/findByOrgId.do?orgid=xxx&operType=yyy">
								<div class="box_topcenter">
										<div class="title">机构基本信息</div>
										<div class="spanDiv">
											<span class="ss1" id="0" style="cursor: pointer;"></span>
										</div>
								</div>
								<div id="boxContent0" class="boxContent" style="display: none;">
									<iframe style="width: 100%;" src="${ctx}/sysOrg/findByOrgId.do?orgid=" id="iframeTest0"  allowtransparency="true" frameborder="no" border="0" marginwidth="0" marginheight="0" onload="iframeHeight(0);"></iframe>
								</div>
							</div></td>
					</tr>
					<c:forEach var="org" items="${orgConfigs}" varStatus="orgStatus">
						<tr>
							<td>
								<div id="app">
									<div class="box" style="padding: 2px 4px 4px 0;" align="left" panelWidth="11306" id="flexible${orgStatus.index+1 }" dataUrl="${org.pageUrl}" appId="${org.appid}">
										<div class="box_topcenter">
												<div class="title">
													${org.appName}<input type="hidden" value="${org.appid}">
												</div>
												<div class="spanDiv">
													<span class="ss1"></span>
												</div>
										</div>
										<div id="boxContent${orgStatus.index+1}" class="boxContent">
											<!-- 设置页面IFRAME	 -->
											<iframe src="" id="iframeTest${orgStatus.index+1 }" style="margin-top: 0px; width: 100%; height: 300px;" allowtransparency="true" frameborder="no" scrolling="no" border="0" marginwidth="0" marginheight="0" onload="iframeHeight(${orgStatus.index+1 })"></iframe>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function affirmTip(id) {
		$("#" + id).hide();
		var orgBoxLength = $(".box").length;
		if ($("#orgidHide").val != "") {
			for (var i = 0; i < orgBoxLength; i++) {
				$("#flexible" + i + " .ss1").show();
				if (parseInt(i) == 0) {
				/* 	$("#flexible" + i + " .ss1").text("收缩"); */
					$("#flexible" + i + " .ss1").text("");
					$("#flexible" + i + " .boxContent").css('display', 'block');
				} else {
					/* $("#flexible" + i + " .ss1").text('展开'); */
					$("#flexible" + i + " .ss1").text(''); 
					$("#flexible" + i + " .boxContent").css('display', 'none');
				}
			}
		}
		$("#iframeTest0").attr("src", "${ctx}/sysOrg/findByOrgId.do?orgid=" + $("#orgidHide").val());
	}
</script>

