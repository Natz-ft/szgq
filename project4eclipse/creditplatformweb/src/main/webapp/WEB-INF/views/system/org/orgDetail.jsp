<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
.TableBlock {
    border: 1px #dddddd solid;
    line-height: 20px;
    font-size: 12pt;
    border-collapse: collapse;
}

.TableBlock .TableContent td, .TableBlock td.TableContent {
    background: #f2f2f2;
    border: 1px #dddddd solid;
    border-right: 1px #dddddd solid;
    padding: 3px;
    height: 20px;
    }
    td {
    font-size: 12px;
}
table, td, th {
    border: 1px solid #e2e2e2;
    margin-left: 13px;
}
</style>
<div align="left">
	<form action="">
		<table id="ss1" class="TableBlock" style=" height: auto; padding-left: 0px;" border="0">
			<tr>
				<td nowrap="" align="left" width="80" class="TableContent">机构编码:</td>
				<td nowrap="" align="left" class="TableData" width="200">${org.orgid }</td>
				<td nowrap="" align="left" width="80" class="TableContent">机构名称:</td>
				<td nowrap="" align="left" class="TableData" width="200">${org.orgName }</td>
				<td nowrap="" align="left" width="80" class="TableContent">上级机构:</td>
				<td nowrap="" align="left" class="TableData" width="200">${org.uporg }</td>
			</tr>
			<tr>
			<td nowrap="" align="left" width="80" class="TableContent">注册资金(万元):</td>
				<td  nowrap="" align="left" class="TableData" width="200"><fmt:formatNumber value="${org.regCapital}" pattern="#0.00"/></td>
				<td nowrap="" align="left" width="80" class="TableContent">邮政编码:</td>
				<td nowrap="" align="left" class="TableData" width="200">${org.postCode }</td>
				<td nowrap="" align="left" width="80" class="TableContent">联系人:</td>
				<td nowrap="" align="left" class="TableData" width="200">${org.linkMan }</td>
			</tr>
			<tr>
				<%-- <td nowrap="" align="left" width="80" class="TableContent">机构所在地区代码:</td>
				<td nowrap="" align="left" class="TableData" width="200">${org.areaCode }</td> --%>
				<td nowrap="" align="left" width="80" class="TableContent">联系方式:</td>
				<td nowrap="" align="left" class="TableData" width="200">${org.linkMode }</td>
				<%-- <td  nowrap="" align="left" width="80" class="TableContent">其他联系方式:</td>
				<td  colspan="2" nowrap="" align="left" class="TableData" width="200">${org.otherLinkMode }</td> --%>
				<td  nowrap="" align="left" width="80" class="TableContent">其他联系方式:</td>
				<td  colspan="3" nowrap="" align="left"  width="200">${org.otherLinkMode }</td>
			</tr>

			<tr>
				
				<td nowrap="" align="left" width="80" class="TableContent">启停标志:</td>
				<td nowrap="" align="left" class="TableData" width="200">
				<c:if test="${org.recordStopFlag==1 }">启用</c:if>
				<c:if test="${org.recordStopFlag==2 }">停用</c:if>
				</td>
				<td nowrap="" align="left" width="80" class="TableContent">启停时间:</td>
				<td nowrap="" align="left" colspan="3" class="TableData" width="200">${org.recordStopTime }</td>
			</tr>
			<tr>
			<tr>
				<td nowrap="" align="left" width="80" class="TableContent">详细地址:</td>
				<td nowrap="" colspan="5" align="left" class="TableData" width="200">${org.address }</td>
			</tr>
			<td nowrap="" align="left" width="80" class="TableContent">备注:</td>
				<td nowrap="" colspan="5" align="left" class="TableData" width="200">${org.remark }</td>
			</tr>
		</table>
	</form>
</div>