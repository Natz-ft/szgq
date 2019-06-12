<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
	<link rel="stylesheet" type="text/css" href="static/script/zTree_v3/style/zTreeStyle.css">
	<link rel="stylesheet" type="text/css" href="static/script/zTree_v3/style/menu.css">
	<script src="static/script/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="static/script/zTree_v3/jquery.ztree.core.min.js" type="text/javascript"></script>
	<script src="static/script/zTree_v3/menu.js" type="text/javascript"></script>
</head>
<script>
    //页面跳转树形菜单特殊处理
    function menu_handle(){
		$("a[id*='ztree_']").each(function (i) {   			
    			var title = $(this).attr("title");    			
    		    var id = "#" + $(this).attr("id");
    		// 融资需求点击关注跳转首页待办事项    菜单特殊处理
    		if(title=="公开需求查询"){
		    	$(id).removeClass("curSelectedNode");
		    }
		    if(title=="首页待办事项"){
		    	$(id).addClass("curSelectedNode");
		    }
		    //todo成对出现
    	}); 
    }
	function menu_select_handle(){
		$("a[id*='ztree_']").each(function (i) {   			
    			var title = $(this).attr("title");    			
    		    var id = "#" + $(this).attr("id");
		    if(title=="投资机构查询"){
		    	$(id).removeClass("curSelectedNode");
		    }
		    if(title=="发布需求"){
		    	$(id).addClass("curSelectedNode");
		    } 
    	}); 
	}
	function menu_sendToSelect_handle(){
		$("a[id*='ztree_']").each(function (i) {   			
    			var title = $(this).attr("title");    			
    		    var id = "#" + $(this).attr("id");
		    if(title=="发布需求"){
		    	$(id).removeClass("curSelectedNode");
		    }
		    if(title=="投资机构查询"){
		    	$(id).addClass("curSelectedNode");
		    } 
    	}); 
	}
	function menu_send_handle(){
		$("a[id*='ztree_']").each(function (i) {   			
    			var title = $(this).attr("title");    			
    		    var id = "#" + $(this).attr("id");
    		    if(title=="发布需求"){
    		    	$(id).removeClass("curSelectedNode");
    		    }
    		    if(title=="我的需求"){
    		    	$(id).addClass("curSelectedNode");
    		    }
    	}); 
	}
	function menu_demandToSend_handle(){
		$("a[id*='ztree_']").each(function (i) {   			
    			var title = $(this).attr("title");    			
    		    var id = "#" + $(this).attr("id");
    		    if(title=="我的需求"){
    		    	$(id).removeClass("curSelectedNode");
    		    }
    		    if(title=="发布需求"){
    		    	$(id).addClass("curSelectedNode");
    		    }
    	}); 
	}
	function menu_inverToEnter_handle(){//从投资机构查询跳到企业详情
		$("a[id*='ztree_']").each(function (i) {   			
    			var title = $(this).attr("title");    			
    		    var id = "#" + $(this).attr("id");
    		    if(title=="投资机构查询"){
    		    	$(id).removeClass("curSelectedNode");
    		    }
    		    if(title=="企业信息"){
    		    	$(id).addClass("curSelectedNode");
    		    }
    	}); 
	}
	function menu_sendToEnter_handle(){//从发布需求跳到企业详情
		$("a[id*='ztree_']").each(function (i) {   			
    			var title = $(this).attr("title");    			
    		    var id = "#" + $(this).attr("id");
    		    if(title=="发布需求"){
    		    	$(id).removeClass("curSelectedNode");
    		    }
    		    if(title=="企业信息"){
    		    	$(id).addClass("curSelectedNode");
    		    }
    	}); 
	}
	function menu_demanToEnter_handle(){
		$("a[id*='ztree_']").each(function (i) {   			
    			var title = $(this).attr("title");    			
    		    var id = "#" + $(this).attr("id");
    		    if(title=="我的需求"){
    		    	$(id).removeClass("curSelectedNode");
    		    }
    		    if(title=="企业信息"){
    		    	$(id).addClass("curSelectedNode");
    		    }
    	}); 
	}
	function menu_eventToEnter_handle(){
		$("a[id*='ztree_']").each(function (i) {   			
    			var title = $(this).attr("title");    			
    		    var id = "#" + $(this).attr("id");
    		    if(title=="我的融资"){
    		    	$(id).removeClass("curSelectedNode");
    		    }
    		    if(title=="企业信息"){
    		    	$(id).addClass("curSelectedNode");
    		    }
    	}); 
	}
</script>
<body>
<!--头部logo以及登录注册开始-->
<div id="systemWrap">
	<div class="systemNav">
		<ul id="ztree" class="ztree showIcon"></ul>
	</div>
</div>
</body>
</html>