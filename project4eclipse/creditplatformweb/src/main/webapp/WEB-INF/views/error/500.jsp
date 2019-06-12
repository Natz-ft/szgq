<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>
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
		background: #fff url(${ctx}/static/image/error/500.jpg) no-repeat center center;
	}
	</style>
</head>
<body>
	<div class="error-con">1</div>
</body>
</html>
