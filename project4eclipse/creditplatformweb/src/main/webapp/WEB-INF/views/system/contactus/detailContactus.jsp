<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/autoload.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/script/layui/css/layui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/script/layui/css/layui.cu.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/reset.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/form.css" type="text/css" />
<style type="text/css">
        .detailsCon{
            width: auto;
            padding: 10px;
            border-bottom: 1px dotted #ccc;
        }
        .detailsCon .detailsTitle{
            width: 20%;
            height: 100%;
        }
        .detailsCon .detailsTitle span{
            font-size: 16px;
            margin-left: 40px;
            padding: 5px 10px;
            color: #0D4F92;
            border-bottom: 3px solid #0D4F92;
        }
        .detailsTxt{
            width: 70%;
            margin: 0 auto;
        }
        .detailsTxt p{
            width: 40%;
            color: #0D4F92;
            float: left;
            margin-right: 2%;
            margin-top: 2%;
            line-height: 34px;
        }
        .detailsTxt .aloneRow{
            width: 80%;
        }
        .detailsTxt.aloneDiv p{
            width: 80%;
            color: #333
        }
        .detailsTxt span{
            color: #333;
        }
        .detailsTxt span a{
            font-weight: bold;
            color: #0D4F92;
            cursor: pointer;
            text-decoration: underline;
        }
        .detailsGrid{
            width: 870px;
        }

        html,body{
            font-size: 14px;
            font-family: "Microsoft YaHei";
            overflow-x: hidden;
            overflow-y: auto;
        }
        body{
            background-color: #eee;
        }
        .floatL{
            float: left;
        }
        .floatR{
            float: right;
        }
          /*鍙栨秷鍙屽嚮閫変腑*/
        div{
            -moz-user-select:none;/*鐏嫄*/
            -webkit-user-select:none;/*webkit娴忚鍣�*/
            -ms-user-select:none;/*IE10*/
            -khtml-user-select:none;/*鏃╂湡娴忚鍣�*/
              user-select:none;
        }
        .boxWrap{
            background-color: #eee;
            padding-right: 10px;
        }
         .boxTitle{
            font-size: 16px;
            color: #0D4F92;
            padding: 10px 10px;
            border-bottom: 2px solid #0D4F92;
        }
      	.buttonWrap{
            width: 100%;
            padding: 15px 0;
            margin-left: 0px;
            text-align: center;
        }
        .heightAuto{
            height: auto;
        }
        
</style>

<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
 
 <script>
 $(function () {
     //点击进行关闭操作
     $(".closeBtn").on("click", function () {
         var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
         parent.layer.close(index); //关闭layer(关闭当前窗口)
     });
 });
 </script>
</head>
<body >
	<div class="boxWrap">
    <p class="boxTitle">苏州市地方金融监督管理局 </p>
    <div class="detailsCon clearfix">
        <div class="detailsTxt clearfix">
                 <p>
					地址：<span>${contactus.adress }</span>
				</p>
				<p>
					邮编：<span>${contactus.zipCode }</span>
				</p>
				<p>
					热线：<span>${contactus.hotline }</span>
				</p>
				<p>
					电子邮箱：<span>${contactus.mail }</span>
				</p>
				<p>
					传真：<span>${contactus.fax }</span>
				</p>
        </div>
          
    </div>
    <!-- <div class="buttonWrap">
			<button class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
	</div> -->
</div>

</body>
</html>