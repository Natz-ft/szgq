<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="bor" ><div class="title"><font>行政区划代码表</font></div>
<div class="divData" ><!-- 列表显示###### -->
<table id="listTable">
	<thead><tr>
		<th>行政区划代码</th>
		<th>行政区划名称</th>
	</tr></thead>
	<tbody>
	<c:forEach items="${CONS_VALUE['DIC_DISTRICT']}" var="item" >
	<tr>
		<td><c:out value="${item['CODE']}" /></td>
		<td><c:out value="${item['NAME']}" /></td>
	</tr>
	</c:forEach>
	</tbody>
</table>
</div>
<div id="dialogBottom">
<input type="button" value="关闭" onClick="window.close();" class="button4"/>
</div>
</div>						