<%@ page pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<% session.setAttribute("dicWord", com.icfcc.credit.platform.jpa.entity.business.DicWord.map); %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
 
<script type="text/javascript" src="${ctx}/static/js/jquery-1.4.4.min.js"></script> 


<link rel="stylesheet" href="${ctx}/static/css/style2.css" type="text/css" />


<%-- <link rel="stylesheet" href="${ctx}/static/css/jquery.ui.all.css" type="text/css"/> --%>

<!-- <link href="${ctx}/static/css/style2.css" rel="stylesheet" type="text/css" /> -->


<link href="${ctx}/static/css/jquery.ui.all.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/static/css/style-test.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/static/css/style_manage.css" rel="stylesheet" type="text/css" />


<!-- <script type="text/javascript" src="${ctx}/static/js/jquery-1.4.4.min.js"></script> -->

<!-- <script rc="${ctx}/static/js/my97/WdatePicker.js"></script> -->

<link href="${ctx}/static/css/style-alert.css" rel="stylesheet" type="text/css" />


<link href="${ctx}/static/css/popup.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/static/css/style-dialog.css" rel="stylesheet" type="text/css"/>

<link href="${ctx}/static/css/bootstrap.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css" />

<link href="${ctx }/static/css/tabs.css" rel="stylesheet" type="text/css" />

<link href="${ctx }/static/css/formatTable.css" rel="stylesheet" type="text/css" />

<%-- <script src="${ctx}/static/js/jquery-1.4.4.min.js" type="text/javascript" ></script> --%>

<script src="${ctx}/static/js/my97/WdatePicker.js" type="text/javascript" ></script>

<!-- <script src="${ctx}/static/js/jquery.form.js" type="text/javascript" ></script> -->

<script src="${ctx}/static/js/page.js" type="text/javascript" ></script>

<script src="${ctx}/static/js/util.js" type="text/javascript"></script>

<script src="${ctx}/static/frame-free/frame/js/framework.js" type="text/javascript" ></script>

<script src="${ctx}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>

<script src="${ctx}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script>

<script src="${ctx}/static/js/jquery.form.js" type="text/javascript" ></script>

<script src="${ctx}/static/js/jquery.form.manage.js" type="text/javascript" ></script>

