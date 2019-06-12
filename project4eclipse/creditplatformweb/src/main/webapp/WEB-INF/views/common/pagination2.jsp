<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
#page_table .page_select{
	width: 50px;
	height: 20px;
	padding: 0px;
	margin-bottom: 3px;
}
#page_table .page_input{
	width: 50px;
	height: 20px;
	border:1px solid #666;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    margin-bottom: 3px;
    transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
    border-radius: 4px;
    color: #555555;
    display: inline-block;
    font-size: 13px;
    line-height: 20px;
    padding: 4px 6px;
    *padding: 0px;
    vertical-align: middle;
    background: #fbfbfb none repeat scroll 0 0;
    border: 1px solid #666;
    position: relative;
    z-index: 0;
    margin-left: 0;
    font-family: Microsoft YaHei,Simsun,Arial,sans-serif;
}
#page_table,#page_table tr,#page_table td,#page_table td a img{
       border: none;
}
#page_table td {
    padding-left: 2px;
    height: 30px;
}
</style>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<table width="100%" id="page_table" >
	<tr>
		<td align="center"><img src="${ctx}/static/image/list-06.jpg" /> 
			共
			<span class="c60">${page.recordCnt }</span>条, 每页
			<span class="c60" > <select id="pgsz" class="page_select"  onchange="setPageSize(this)">
					<option value="10"
						<c:if test="${page.maxSize == '10' }">selected</c:if>>
						10
					</option>
					<option value="15"
						<c:if test="${page.maxSize == '15' }">selected</c:if>>
						15
					</option>
					<option value="20"
						<c:if test="${page.maxSize == '20' }">selected</c:if>>
						20
					</option>
					<option value="40"
						<c:if test="${page.maxSize == '40' }">selected</c:if>>
						40
					</option>
					<option value="50"
						<c:if test="${page.maxSize == '50' }">selected</c:if>>
						50
					</option>
					<option value="100"
						<c:if test="${page.maxSize == '100' }">selected</c:if>>
						100
					</option>
					<option value="200"
						<c:if test="${page.maxSize == '200' }">selected</c:if>>
						200
					</option>
					<option value="500"
						<c:if test="${page.maxSize == '500' }">selected</c:if>>
						500
					</option>
				</select> </span>条, 当前为第
			<span class="c60">${page.curPage}/${page.lastPage }</span>页
			<a href="javascript:refresh();" ><img alt="刷新" src="${ctx}/static/image/refresh.png"></a>
		<!-- </td> -->
		<!-- <td align="left" > -->
		<span class="c60">	<a href="javascript:doPage(1);">首页</a>&nbsp;|&nbsp;</span>
			<a href="javascript:doPage(${page.prePage});">上一页</a>&nbsp;|&nbsp; 
			<a href="javascript:doPage(${page.nextPage});">下一页</a>&nbsp;|&nbsp;
			<a href="javascript:doPage(${page.lastPage});">末页</a> 转到第
		<span class="c60" >	<input  type="text" id="gono" name="gono"  class="page_input" onkeypress="getEnter()" >
		</span>
		<!-- 当前页面 -->
		 <c:if test="${empty page.curPage}">
		<input  type="hidden" id="curPage" name="curPage" value="0">
		</c:if>
		<c:if test="${!empty page.curPage}">
				<input  type="hidden" id="curPage" name="curPage" value="${page.curPage}">
		</c:if>
		
		<c:if test="${empty page.maxSize}">
		 <input  type="hidden" id="maxSize" class="aaaa" name="maxSize" value="0">
		</c:if>
		<c:if test="${!empty page.maxSize}">
			 <input  type="hidden" id="maxSize" class="aaaa" name="maxSize" value="${page.maxSize}">
		</c:if>
		<c:if test="${empty page.pageCnt}">
		<input  type="hidden" id="pageCnt" name="pageCnt" value="0"> 
		</c:if>
		<c:if test="${!empty page.pageCnt}">
			 <input  type="hidden" id="pageCnt" name="pageCnt" value="${page.pageCnt}"> 
		</c:if>
			页
		<a href="#" onclick="goPage()" >GO</a> 
	 	</td>
	</tr>
</table>

