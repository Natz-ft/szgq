<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link  rel="stylesheet" href="../static/css/zTree/demo.css" type="text/css">
<link  rel="stylesheet" href="../static/css/zTree/zTreeStyle.css" type="text/css">
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
			<c:forEach var="dts" items="${menus}" varStatus="status">
              { id: "${dts.id}", pId: "${dts.parentId}", name: "${dts.name}", nocheck: false },  
			</c:forEach>
              ];

var zNodes2=[
		<c:forEach var="dts" items="${menulist}" varStatus="status">
		{ id: "${dts.id}", pId: "${dts.parentId}", name: "${dts.name}", nocheck: false },  
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
				target+="{id:"+nodes[i].id+",pId:"+nodes[i].pId+",name:\""+nodes[i].name+"\"},"
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
			if(nodes[i].isParent){
			}else{
				//删除叶子节点，如果叶子节点都删除，删除其父节点
				var parentTId = nodes[i].parentTId;
				var node = treeObj2.getNodeByTId(parentTId);
				var parentTId2 = node.parentTId;
				var node2 = treeObj2.getNodeByTId(parentTId2);
				treeObj2.removeNode(nodes[i]);
				if(!node.isParent){
					treeObj2.removeNode(node);
				}
				if(!node2.isParent){
					treeObj2.removeNode(node2);
				}
			}
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
		 /* location.href="grantPower.do?id="+ids;  */
		 var url = "${ctx}/roleManage/grantPower.do?id="+ids;
			$.ajax({
				type : 'post',
				dataType : 'json',
				url : url,
				success : function(data) {
					$("#empower_dialog_id").hide();
					var flag = del_successprocess(data);
					if (flag) {
						param("提示信息", "保存成功", "");
						$("#t1").fadeIn(200);
					} else {
						param("提示信息", "保存失败", "");
						$("#t1").fadeIn(200);
					}
				},
				error : function() {
					param("提示信息", "系统错误，请重试", "");
					$("#t1").fadeIn(200);
				}
			});
		
		/* var timer = setTimeout(function(){
			window.close();
			clearTimeout(timer);
		},100); */
	})
})
</script>
<style type="text/css">
#empower_dialog_id table,#empower_dialog_id td,#empower_dialog_id h1{
	font-family: Microsoft Yahei,Arial;
}
#empower_dialog_id h1{
	color:#666;padding: 20px 0;
}
 #empower_dialog_id table,#empower_dialog_id td{
 	border: none;
 	background-color: #fefefe;
 	color:#666;
 } 
#empower_dialog_id .button{
	cursor: pointer;
	box-shadow: none;
    text-shadow: none;
    border-radius: 0px;
}
.empowerBtn p{
	margin-bottom: 10px;
}
.empowerBtn p input{
	width: 100px;
}
#empower_dialog_id .button {
    border-color: #f4f4f4 #888888 #888888 #f4f4f4;
    border-radius: 0;
}
div.content_wrap {
    height: auto;
    width: auto;
}
div.content_wrap div.left {
    width: auto;
}
div.zTreeDemoBackground {
    height: 220px;
    text-align: center;
}
.ztree li a {
	font-weight: normal;
}
.ztree li,.ztree li ul {
	height: auto;
}
ul.ztree {
    width: 200px;
    height: 220px;
    margin: 0 auto;
}
</style>  
</head>  
<body>  
 <div class="basepopopdiv popup357-381 base-wrap-m" id="empower_dialog_id"  >
	<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
	<div class="tiptop">
		<span>赋予角色系统权限</span><a href="javascript:close_dialog('empower_dialog_id');"></a>
	</div>
	<div class="modal-body selectmodal wrap-m" style="height: 300px;">
	<table align="center"  cellspacing="0px">
		<thead>
			<tr align="center"><td>系统权限</td><td>操作</td><td>选中权限</td></tr>
		</thead>
		<tbody>
			<tr align="center">
				<td>
					<div class="content_wrap">
						<div class="zTreeDemoBackground left">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</div>
				</td>
				<td>
					<div class="empowerBtn">
						<p><input class="btnRole" id="add" type="button" value="&gt;&gt;&gt;"  /></p>
						<p><input class="btnRole" id="del" type="button" value="&lt;&lt;&lt;"  /></p>
						<p><input class="btnRole" id="grant" type="button" value="保 存"  /></p>
					</div>
				</td>
				<td>
					<div class="content_wrap">
						<div class="zTreeDemoBackground left">
							<ul id="treeDemo2" class="ztree"></ul>
						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table> 
  </div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
 /**
  * 
  * @param data
  * @returns {Boolean}
  */
 function del_successprocess(data) {
 	if (data.code =="00000") {
 	 		return true;
  	}else{
  		return false;
  	}
 }
 </script>