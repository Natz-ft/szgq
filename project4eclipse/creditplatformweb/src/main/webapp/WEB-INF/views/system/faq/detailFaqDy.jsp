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

.sub-c > .pro-list {
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
.buttonWrap{
            width: 100%;
            padding: 15px 0;
            margin-left: 0px;
            text-align: center;
            margin-top: 10px;
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
    <div class="mainwraper" style="background:#EEF3F9 ">
        <!--明细内容开始-->
        <div class="sub-c">
            <div class="pro-list">
                <h3>${faq.problem}</h3>
                <b>${faq['faqDate'] }</b>
            </div>
            <div>
                ${faq.answer}
            </div>
           <div class="layui-input-block buttonWrap">
	            <button type="button" lay-close class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
	       </div>
        </div>
        <!--明细内容结束-->
</div>
</body>
</html>