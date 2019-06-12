<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>苏州股权融资服务平台</title>
	<link rel="shortcut icon" href="${ctx}/static/image/favicon1.ico" type="image/x-icon" />
<!-- 	<link rel="Shortcut Icon" href="http://www.vip.com/favicon.ico"></link>
 -->	<style>
	html,body{
		width: 100%;
		height: 100%;
		margin: 0;
		overflow: hidden;
	}
	.error-con{
		width: 603px;
		height: 193px;
		margin: 40px auto;
		vertical-align: middle;
		text-indent: -9999px;
		background: #fff url(${ctx}/static/image/error/404.jpg) no-repeat center center;
	}
	</style>
</head>
<body>
	<div class="error-con">1</div>
</body>
</html>