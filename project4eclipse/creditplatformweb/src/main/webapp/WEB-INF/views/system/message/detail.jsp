<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
body{font-family: Microsoft YaHei,Arial;}
div{margin: 0px;padding: 10px}
.t1{font-size: 24px;}
.t2{border-bottom:3px solid #f10180;}
.d{text-indent:2em;border: 0px solid blue}
.d2{text-align: left;border: 0px solid blue}
.d2{text-align: right;border: 0px solid blue}
</style>
</head>
<body >
	<div class="t1" align="center">
	<div class="t2" align="center"><b>${message.title }</b></div>
	</div>
	<div class="d">
		<div class="d1">${message.content }</div>
		<div class="d2">
		<p>${message.createUser }</p>
		<fmt:formatDate value="${message['createTime'] }" pattern='yyyy-MM-dd'/>
		</div>
	</div>
</body>
</html>