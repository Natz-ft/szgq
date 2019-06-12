<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link  rel="stylesheet" href="../static/css/zTree/demo.css" type="text/css">
<link  rel="stylesheet" href="../static/css/zTree/zTreeStyle.css" type="text/css">

<style type="text/css">
table,td,h1{font-family: Microsoft Yahei,Arial;}
h1{color:#666;padding: 20px 0;}
table,td{border: 1px solid #f10180;background-color: #fefefe;color:#666;}
td{padding:5px;}
td input{border:1px solid #f10180;color: #f10180;background-color:#fff;border-radius: 3px;}
td input:hover{color: #fff;background-color: #f10180;}
.button{
	text-align:center;
	background:url(../static/image/list-02.png) ;
	cursor: pointer;
	height:28px;
	display: inline-block;
	font-weight: bold;
}
</style>

<script src="../static/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="../static/js/zTree/jquery.ztree.core.js" type="text/javascript"></script>
<script src="../static/js/zTree/jquery.ztree.excheck.js" type="text/javascript"></script>
<script src="../static/js/zTree/jquery.ztree.exhide.js" type="text/javascript"></script>
<script src="../static/js/zTree/jquery.ztree.exedit.js" type="text/javascript"></script>

<script type="text/javascript">
 
var setting = {  
    check: {  
        enable: true,  
        nocheckInherit: false  
    },  
    data: {  
        simpleData: {  
            enable: true  
        }  
    },
    edit:{
    	enable: true,
		showRemoveBtn: false,
		showRenameBtn: false
    }
    
};  
//ztree用于初始化的静态数据       
var zNodes = [
			<c:forEach var="dts" items="${buttons}" varStatus="status">
              { id: "${dts.buttonId}",name: "${dts.buttonName}", nocheck: false },  
			</c:forEach>
              ];

var zNodes2=[
		<c:forEach var="dts" items="${buttonlist}" varStatus="status">
		{ id: "${dts.buttonId}" , name: "${dts.buttonName}", nocheck: false },  
		</c:forEach>
             ];
  
///页面加载后初始化zTree数据且默认展开所有节点  
$(document).ready(function () {  
    $.fn.zTree.init($("#treeDemo"), setting, zNodes).expandAll(true);
    $.fn.zTree.init($("#treeDemo2"), setting, zNodes2).expandAll(true);
});  
///动态移植选择的checkbox
 $(document).ready(function () {
	$("#add").click(function(){
		   
		   var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		   var treeObj2 = $.fn.zTree.getZTreeObj("treeDemo2");
		   var nodes = treeObj.getCheckedNodes();
			  var target="[";
			 for (var i=0, l=nodes.length; i < l; i++) {
				target+="{id:"+nodes[i].id+",name:\""+nodes[i].name+"\"},"
			}
			 target = target.substring(0,target.length-1);
			target+="];";
			
			var nodesArray = eval(target);
			
			$.fn.zTree.init($("#treeDemo2"), setting, nodesArray).expandAll(true);

	})
})
///删除右侧树种选择的节点
$(document).ready(function(){
	$("#del").click(function(){
		var treeObj2=$.fn.zTree.getZTreeObj("treeDemo2");
		var nodes=treeObj2.getCheckedNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
				treeObj2.removeNode(nodes[i]);
		}
		
	})
})
//要求先循环删除子节点，如果子节点都被删除，则页删除父节点
$(function(){
	$("#grant").click(function(){
		var treeObj2=$.fn.zTree.getZTreeObj("treeDemo2");
		var nodes=treeObj2.transformToArray(treeObj2.getNodes());
		var ids="${roleId}";
		for (var i=0, l=nodes.length; i < l; i++){
			ids+=","+nodes[i].id;
		}
		location.href="grantPowerButton?id="+ids;
		window.close();
	})
})
</script>  
</head>  
<body>  
 
<h1>赋予角色按钮权限</h1>
<table align="center"  cellspacing="0px">
		<thead>
			<tr align="center"><td>按钮权限</td><td>操作</td><td>选中权限</td></tr>
		</thead>
		<tbody>
			<tr align="center"><td>
			<div class="content_wrap">
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
			</div>
			</td>
			<td>
				<input id="add" type="button" value="&gt;&gt;&gt;"  />
				<br><br>
				<input id="del" type="button" value="&lt;&lt;&lt;"  />
			</td>
			<td>
			<div class="content_wrap">
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo2" class="ztree"></ul>
				</div>
			</div>
			</td></tr>
			<tr align="center"><td colspan="3">
				<input id="grant" type="button" value="保存"  />
			</td></tr>
		</tbody>

</table> 
  
</body>
</html>