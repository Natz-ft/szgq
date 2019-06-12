<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.Date" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/jquery-1.4.4.min.js"></script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/static/css/style-test.css" rel="stylesheet" type="text/css" />
<script src="static/js/util.js" type="text/javascript"></script>
<script>
window.onbeforeunload = function(){
	var n = window.event.screenX - window.screenLeft;   
  	var b = n > document.documentElement.scrollWidth-50;
  	if(b && window.event.clientY < 0 || window.event.altKey)   
  	{
  		parent.location.href="logout";
	}
};
</script>
</head>
<body class="headBody">
<script type="text/javascript">
function printCurentDate(){
	var y=new Date(<%=(new Date()).getTime()%>);
	var gy=y.getYear();
	if(gy<200){
	  gy=1900+gy;
	}
	var dName=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
	var mName=new Array("01","02","03","04","05","06","07","08","09","10","11","12");
	document.write("<span class=enb>今天是 ",gy,"</span>","年","<span class=enb>",mName[y.getMonth()],"</span>","月","<span class=enb>",y.getDate(),"</span>","日  ",dName[y.getDay()]);
}
function switchTab(id,sUrl){
	parent.leftFrame.location=sUrl;
	parent.mainFrame.location="main";
}
function doModifyPwd(){
	url='admin/user/changePassword';
	parent.mainFrame.location=url;
}
function doLogout(){
	parent.location.href="logout";
}
function toIndex(){
	$.ajax({
        type: "post",
        url: "/creditplatformweb/logoutIndex",//url地址
        data: {"name": "industry"},// 查询条件
        async: false,//使用同步的方式,true为异步方式
        success: function (data) {
          
        }
    });
	url='/SRRPBusinesWeb/portal/index.html';
	parent.location.href=url;
}
</script>
	<div class="logo-bj">
	    <div class="headWrap clearfix">
			<div class="welcomeWrap">欢迎您，${user.nickname}
			&nbsp;&nbsp;  <script>printCurentDate();</script>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
	        <div class="buttonWrap">
	        	<a class="indexButton" href="javascript:toIndex()" title="首页">
	        	<!-- <span>首页</span> -->
	        	</a>
	        	<a class="passwordButton" href="javascript:doModifyPwd()" title="修改密码">
	        	<!-- <span>修改密码</span> -->
	        	</a>
	            <!-- <a href="javascript:doAbout()">关于</a>
	            <a href="template/User Manual.doc" target="_blank">帮助</a> -->
				<a class="logoutButton" href="javascript:doLogout()" title="退出">
				<!-- <span>退出</span> -->
				</a>
			</div>
	        <div class="logoIcon">
    	        <a>
		            <p></p>
		            <p></p>
		        </a>
	        </div>
	    </div>
	    <!-- <div class="nav">
	        <c:forEach items="${menus}" var="item" varStatus="i">
	            <a href="#" onclick="switchTab(${i.index },'menu?sysNo=${item.id}')">${item.name }</a>
	        </c:forEach>
	    </div> -->
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$(".nav a").click(function(){
			
			$(this).css({"background-color":"#083671"}).siblings().css({"background-color":"#0D4F92"});
		})
	})

</script>
</html>
