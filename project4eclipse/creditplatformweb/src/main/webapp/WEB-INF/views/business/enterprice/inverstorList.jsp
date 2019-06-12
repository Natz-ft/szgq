<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>投资机构查询</title>
</head>
<body>
	<!-- 页面上方的条件选择，暂时用的是下拉选项框后期改为标签 -->
	<div>
		<span>投资阶段</span> <select id="sid" onchange="selectcity()">
			<option>全部</option>
			<option>种子期</option>
			<option>初创期</option>
			<option>扩展器</option>
			<option>成熟期</option>
		</select>
	</div>
	<div>
		<span>投资行业</span> <select id="sid" onchange="selectcity()">
			<option>全部</option>
			<option>IT</option>
			<option>农林业</option>
			<option>汽车</option>
			<option>房地产</option>
		</select>
	</div>
	<div>
		<span>投资阶段</span> <select id="sid" onchange="selectcity()">
			<option>全部</option>
			<option>天使投资</option>
			<option>VC</option>
			<option>PE</option>
			<option>券商直投</option>
		</select>
	</div>
	<div>
		<span>注册时间</span> <select id="sid" onchange="selectcity()">
			<option>全部</option>
			<option>2016</option>
			<option>2017</option>
		</select>
	</div>
	<div>
		<div class="popupbg" id="zhezhao" style="display: none;"></div>
		<table id="listTable" class="listTable" cellspacing="0"
			cellpadding="5" width="100%" border="0">
			<thead>
				<tr>
					<td align="center"></td>
					<!-- 开发人员根据业务需求，自定义表头字段 开始 -->
					<td>投资机构名</td>
					<td>注册时间</td>
					<td>所属地区</td>
					<td>机构类型</td>
					<td>管理资本量（百万）</td>
					<td>历史投资事件数量</td>
					<td>复选</td>
					<!-- 开发人员根据业务需求，自定义表头字段  结束-->
				</tr>
			</thead>
			<tbody>
				<!-- 循环开始标记 -->
				<c:forEach var="institution" items="${ institutionlist}"
					varStatus="status">
					<tr>
						<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
						<!-- 第一项点击链接可以对机构详细信息进行查询 -->
						<td><a href="#" onclick="showInfo('${institution.id }')">XX创业投资有限公司${institution.username}</a></td>
						<td>2012-01-01${institution.createtime }</td>
						<td>上海市${institution.nickname}</td>
						<td>政府引导资金</td>
						<td>CNY 721</td>
						<td>2</td>
						<td><input class="inputCheckBox" type="checkbox" name="id"
							value="${id }" type="checkbox" /></td>
						<!-- 开发人员根据业务需求，自定义表格内容字段 结束 -->
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>