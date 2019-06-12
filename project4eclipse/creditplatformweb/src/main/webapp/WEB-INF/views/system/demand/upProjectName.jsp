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
<title>编辑项目名称</title>
</head>
<body>
	<div class="boxWrap">	
		<div class="formWrap">
			<form>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="star">*</span>项目名称：</label>
					<div class="layui-input-block">
					    <input type="text" id="projectName" name="projectName" autocomplete="off"
							class="layui-input" value="${demand['projectName']}">
						<input type="hidden" id="infoId" name="infoId" autocomplete="off"
							class="layui-input" value="${demand['infoId']}">
					</div>
				</div>
				<div class="layui-form-item Operatebutton">
					<div class="layui-input-block buttonWrap">
						<button class="layui-btn submit" id="upProjectName">&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;
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
		$("#upProjectName").on(
				'click',
				function() {
					var projectName = $("#projectName").val();
					var infoId = $("#infoId").val();
					if(projectName=="" || projectName==undefined || projectName==null ||projectName.trim()==""){
						layer.alert('项目名称不能为空');
						return false;
					}
					$.ajax({
						type : "post",
						async : false,
						url : "/creditplatformweb/demand/subProjectName",
						data : {
							"projectName" : projectName,
							"infoId" : infoId
						},
						success : function(data) {
							data = JSON.parse(data);
			                 var flag = true;
			                 if (flag) {
			                     layer.alert('提交成功', function () {
			                    	 parent.location.reload();
			                    	 layer.closeAll();
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