<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/autoload.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/script/layui/css/layui.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/script/layui/css/layui.cu.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/reset.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/common.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/form.css"
	type="text/css" />
<style type="text/css">
/*覆盖top中的相同样式*/
a {
	color: #000000;
}

li {
	list-style: none;
}

/*设置整体大小开始*/
.mainwraper {
	width: 100%;
	margin: 0 auto;
	overflow: hidden;
}

/*设置整体大小结束*/
/*当前位置开始说明等样式开始*/

/*当前位置开始说明等样式结束*/
/*页面主题样式开始*/
.sub-c {
	background: #fff;
	padding: 15px;
	overflow: hidden;
	min-height: 350px;
}
/*详细内容开始*/
.sub-c ul li.pro-list {
	border-bottom: none;
}

.sub-c ul li.pro-list h3 {
	position: relative;
	margin-bottom: 10px;
}

.sub-c>.pro-list {
	padding: 0 10px 10px 0;
}

.pro-list {
	position: relative;
	overflow: hidden;
	border-bottom: 1px solid #d2d2d2;
	padding: 5px 0;
}

.pro-list h3 {
	color: #004F8A;
	font-size: 16px;
	font-weight: bold;
	margin-bottom: 10px;
}

.down {
	color: #004F8A;
	margin-bottom: 10px;
}

.buttonWrap {
	width: 100%;
	padding: 15px 0;
	margin-left: 0px;
	text-align: center;
	margin-top: 10px;
}
</style>
<script
	src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/static/script/layui/layui.all.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js"
	type="text/javascript"></script>

<script>
	$(function() {
		//点击进行关闭操作
		$(".closeBtn")
				.on(
						"click",
						function() {
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index); //关闭layer(关闭当前窗口)
							window.parent.location.href = "/creditplatformweb/event/curdEvent";
						})
	});
</script>
</head>
<body>
	<div class="mainwraper" style="background: #EEF3F9">
		<!--明细内容开始-->
		<div class="sub-c">
			<div class="pro-list">
				<h3>${event.projectName}</h3>
			</div>
			<div align="center">
				<table style="font-size: 14px;">
				   <tr>
						<td style="width: 330px;"><p>
								企业名称：<span>${base.name}</span>
							</p></td>
						<td style="width: 330px;"><p>
								行业：<span>${baseSupplement.industryDicname}</span>
							</p></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="width: 330px;"><p>
								所属区域：<span>${base.reareaDicname}</span>
							</p></td>
						<td style="width: 330px;"><p>
								融资轮次：<span>${demand.finacingTurnStr}</span>
							</p></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="width: 330px;"><p>
								融资金额(万)：<span>${demand.amount} ${demand.currencyStr}</span>
							</p></td>
						<td style="width: 330px;"><p>
								发布时间：<span><fmt:formatDate value="${demand['operdate'] }" pattern='yyyy-MM-dd HH:mm:ss' /></span>
							</p></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="width: 330px;"><p>
								联系人姓名：<span>${demand.relName}</span>
							</p></td>
						<td style="width: 330px;"><p>
								联系电话：<span>${demand.relPhone}</span>
							</p></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="width: 330px;"><p>
								投资时间：<span><fmt:formatDate value="${event.operdate}"
						pattern='yyyy-MM-dd' /></span>
							</p></td>
						<td style="width: 330px;"><p>
								投资金额(万)：<span>${event.amountStr}</span>
							</p></td> 
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="width: 330px;"><p>
								出让股权：<span>${demand.sellScale}</span>
							</p></td>
						<td style="width: 330px;"><p>
								商业企划书： <span><a class="down" href="${demand.filePath}"
									download='${demand.fileName}'>${demand.fileName}</a></span>
							</p></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2" style="width: 660px;"><p>
								需求详情：<span>${demand.description}</span>
							</p></td>
					</tr>
				</table>
			</div>
			<div class="pro-list" style="margin-left: 5%;">
				<b>投资方信息</b>
			</div>
			<div align="center">
				<table style="font-size: 14px;" class="listTable"  cellspacing="0" cellpadding="2" border="1" >
				   <tr style="background: #3D78D0;" align="center">
						<td style="width: 200px;"><p>
								投资机构名称
							</p></td>
						<td style="width: 150px;"><p>
								机构类型
							</p></td>
						<td style="width: 150px;"><p>
								投资金额(万)
							</p></td>
					</tr>
					<tr align="center">
						<td style="width: 200x;"><p> <span>${investor.name}</span>
							</p></td>
						<td style="width: 150px;"><p><span>${investor.orgTypeDicname}</span>
							</p></td>
						<td style="width: 150px;"><p> <span>${event.amountStr} </span>
							</p></td>
					</tr>
				</table>
			</div>
			<div class="pro-list" style="margin-left: 5%;">
				<b>出资信息</b>
			</div>
			<div align="center">
				<table style="font-size: 14px; " class="listTable"  cellspacing="0" cellpadding="2"border="1">
				   <tr style="background: #3D78D0;" align="center"> 
						<td style="width: 200px;"><p>
								出资方名称
							</p></td>
						<td style="width: 150px;"><p>
								出资日期
							</p></td>
						<td style="width: 150px;"><p>
								出资金额(万)
							</p></td>
					</tr>
				<c:forEach var="faq" items="${loans}">
				    <tr align="center">
						<td style="width: 200px;"><p><span>${investor.name}</span>
							</p></td>
						<td style="width: 150px;"><p><span>${faq.loanDate}</span>
							</p></td>
						<td style="width: 150px;"><p><span>${faq.amountStr}</span>
							</p></td>
					</tr>
				</c:forEach>
				</table>
			</div>
			<div class="layui-input-block buttonWrap">
				<button type="button" lay-close class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
			</div>
		</div>
		<!--明细内容结束-->
	</div>
</body>
</html>