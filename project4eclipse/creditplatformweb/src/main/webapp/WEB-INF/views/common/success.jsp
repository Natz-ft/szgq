<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="bor" ><div class="title"><font>操作结果</font></div>
<table width="100%" height="189" style="font-size:14px; font-weight:bold; color:#0088dd;">
<tr>
	<td width="37%">&nbsp;</td>
	<td width="10%"><c:if test="${fn:length(msgList) == 0}"><img src="image/003.jpg" /></c:if></td>
	<td width="53%">
		<c:forEach var="msg" items="${msgList}">${msg}<br /></c:forEach>
		<c:if test="${fn:length(msgList) == 0}">操作成功！</c:if>
	</td>
</tr>
</table>
<div id="dialogBottom">
<input type="button" value="关闭" onClick="closeWin();" class="button4"/>
</div>
</div>


	
<script  type="text/javascript">
function closeWin(){
	window.open("", "_self");
	<c:choose>
		<c:when test="${returnValue!=null && returnValue!=''}">
			window.returnValue="${returnValue}";
		</c:when>
		<c:otherwise>
			<c:if test="${fn:length(msgList) == 0}">window.returnValue="SUCCESS";</c:if>
			<c:if test="${fn:length(msgList) != 0}">window.returnValue="FAIL";</c:if>
		</c:otherwise>
	</c:choose>
	window.close();
}

window.onunload = function(){
	try {
		if(typeof(window.opener.doSubmitList)!="undefined") {
			window.opener.doSubmitList();
		}
	} catch(e) {
	}
}
</script>