<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="bor" ><div class="title"><font>国家代码表</font></div>
<div class="divData" ><!-- 列表显示###### -->
<table id="listTable">
	<thead><tr>
		<th>国家代码</th>
		<th>国家名称</th>
	</tr></thead>
	<tbody>
	<c:forEach items="${CONS_VALUE['DIC_COUNTRY']}" var="item" >
	<tr>
		<td><c:out value="${item['CONS_CODE']}" /></td>
		<td><c:out value="${item['CONS_VAL']}" /></td>
	</tr>
	</c:forEach>
	</tbody>
</table>
</div>
<div id="dialogBottom">
<input type="button" value="关闭" onClick="window.close();" class="button4"/>
</div>
</div>						