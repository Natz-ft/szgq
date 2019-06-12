<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- <link href="${pageContext.request.contextPath}/static/css/style-message.css" rel="stylesheet" type="text/css" /> --%>
<%--  <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.4.3.js"></script>   --%>
<%-- <script src="${pageContext.request.contextPath}/static/js/util.js" type="text/javascript"></script> --%>
<%-- <script src="${pageContext.request.contextPath}/static/js/page.js" type="text/javascript"></script> --%>
</head>

<!-- <table id="bodyTable" style="width:1280px"> -->
<!-- 	<tr> -->
<!-- 		<td> -->
<!-- 			<div class="divData" > -->
<%-- 				<table id="listTable"> --%>
<%-- 				 --%>
<%-- 					<tbody> --%>
<%-- 						<c:forEach items="${page.list}" var="RC" > --%>
<%-- 								<tr> --%>
<%-- 								<td width="80%"><a href="#" onClick="doReptShow('${RC['id'] }');">${RC['title'] }</a></td> --%>
<%-- 								<td width="20%" style="text-align:center"><fmt:formatDate value="${RC['createTime'] }" pattern='yyyy-MM-dd'/></td> --%>
<%-- 								</tr> --%>
<%-- 						</c:forEach> --%>
<%-- 					</tbody> --%>
<%-- 				</table> --%>
<%-- 				<form action="main" method="post" id="list_form_id" name="list_form_name"> --%>
<%-- 					<c:import url="/WEB-INF/views/common/pagination.jsp" /> --%>
<%-- 				</form> --%>
<!-- 			</div> -->
<!-- 		</td> -->
<!-- 	</tr> -->
<!-- </table> -->


<script type="text/javascript">
function doSubmit() {
	doSubmitList();
}
function doSubmitList() {
	alert(document.forms[0].pageNo.value);
	document.forms[0].submit();
}

function doReptShow(id) {
	openWindow600("message/detail?id="+id);
}

</script>