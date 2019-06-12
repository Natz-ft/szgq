<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>testtag</title>
</head>
<!--  -----------隐藏区域 ------------------- -->
<input type="hidden" value="${ctx}"  id="path">
<input type="hidden"  id="delUrl">
<!--  -----------隐藏区域 ------------------- -->

<ex:dictag dicType="BASIC" url="--"></ex:dictag>