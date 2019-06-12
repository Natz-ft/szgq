<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/autoload.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/script/layui/css/layui.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/script/layui/css/layui.cu.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/reset.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/common.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/form.css"
	type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.layui-form-item {
	height: 100px;
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<div class="boxWrap">	
		<div class="formWrap">
			<form>
				<div class="layui-form-item">
					<label class="layui-form-label" style="margin-left: -10%;width: 85px;"><span class="star">*</span>撤回原因：</label>
					<div class="layui-input-block">
						<textarea style="width: 400px; margin-left: -15%;" id="closeReason" placeholder="请输入内容"
							class="layui-textarea"></textarea>
						<input type="hidden" id="infoId" name="infoId" autocomplete="off"
							class="layui-input" value="${demand['infoId']}">
					</div>
				</div>
				<div class="layui-form-item Operatebutton">
					<div class="layui-input-block buttonWrap">
						<button class="layui-btn submit" id="uploadbtnListAction">&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;
						</button>
						<button class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
					</div>

				</div>
			</form>
		</div>
	</div>
</body>
<script
	src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/static/script/layui/layui.all.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js"
	type="text/javascript"></script>
<script>
	$(function() {
		//点击进行关闭操作
		$(".closeBtn").on("click", function() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index); //关闭layer(关闭当前窗口)
		});
		$("#uploadbtnListAction").on(
				'click',
				function() {
					var closeReason = $("#closeReason").val();
					var infoId = $("#infoId").val();
					if (closeReason == null || closeReason.length == 0
							|| closeReason == '') {
						layer.alert('撤回原因不能为空！')
						return false;
					}
					$.ajax({
						type : "post",
						async : false,
						url : "/creditplatformweb/demand/subRevoke",
						data : {
							"closeReason" : closeReason,
							"infoId" : infoId
						},
						success : function(data) {
							data = JSON.parse(data);
			                 var flag = true;
			                 if (flag) {
			                     layer.alert('提交成功', function () {
// 			                    	 parent.location.reload();
			                    	 parent.parent.layer.closeAll();
			                    	 window.parent.parent.location.href = "/creditplatformweb/demand/curdDemand";
			                     });
			                 } else {
			                     layer.alert('提交失败', {
			                         title: '最终的提交信息'
			                     })
			                 }
						},
						error : function(data) {
						}
					});
					return false;
				});
	});
</script>
</html>