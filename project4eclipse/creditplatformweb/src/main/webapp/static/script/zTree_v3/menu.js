$(function () {
    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        view: {
            showLine: false,
            showIcon: false,
            fontCss: null,
            selectedMulti: false,
            dblClickExpand: false,
            addDiyDom: addDiyDom
        },
        treeObj: $("#ztree"),
        callback: {
            beforeAsync: null,
            beforeCheck: null,
            beforeClick: beforeClick,
            beforeCollapse: null,
            beforeDblClick: null,
            beforeDrag: null,
            beforeDragOpen: null,
            beforeDrop: null,
            beforeEditName: null,
            beforeExpand: null,
            beforeMouseDown: null,
            beforeMouseUp: null,
            beforeRemove: null,
            beforeRename: null,
            beforeRightClick: null,
            onAsyncError: null,
            onAsyncSuccess: null,
            onCheck: null,
            onClick: onClick,
            onCollapse: null,
            onDblClick: null,
            onDrag: null,
            onDragMove: null,
            onDrop: null,
            onExpand: null,
            onMouseDown: null,
            onMouseUp: null,
            onNodeCreated: null,
            onRemove: null,
            onRename: null,
            onRightClick: null
        }
    };

    /*    function setFontCss(treeId, treeNode) {
     return treeNode.level == 0 ? {background:"red"} : {};
     };*/
    function addDiyDom(treeId, treeNode) {
        var spaceWidth = 5;
        var switchObj = $("#" + treeNode.tId + "_switch"),
            icoObj = $("#" + treeNode.tId + "_ico");
        switchObj.remove();
        icoObj.before(switchObj);

        if (treeNode.level > 1) {
            var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
            switchObj.before(spaceStr);
        }
    }

    function beforeClick(treeId, treeNode) {

        if (treeNode.children && treeNode.children.length > 0) {
            var zTree = $.fn.zTree.getZTreeObj("ztree");
            zTree.expandNode(treeNode);
            return false;
        }
        return true;
    }

    // zTree 的点击跳转
    function onClick(event, treeId, treeNode) {
	     var checkid="#"+treeNode.tId+"_a";
		 $("a[class*='level1 curSelectedNode']").each(function (i) {   			
 			var title = $(this).attr("title");    			
 		    var id = "#" + $(this).attr("id");
 	    	if(checkid!=id&&title=="首页待办事项"){
		    	$(id).removeClass("curSelectedNode");
		    }else if(checkid!=id&&title=="我的需求"){
		    	$(id).removeClass("curSelectedNode");
		    }else if(checkid!=id&&title=="发布需求"){
		    	$(id).removeClass("curSelectedNode");
		    }else if(checkid!=id&&title=="我的融资"){
		    	$(id).removeClass("curSelectedNode");
		    }
 	     }); 
        if (treeNode.link) {
            parent.mainFrame.location = treeNode.link;
            //  $(".systemContent iframe").attr("src", treeNode.link);
        }
    }

    var zNodes;
    var rank;
    $.ajax({
        type: "post",//请求方式
        url: "/creditplatformweb/admin/user/MenuTree",//url地址
        cache: false,//清楚缓存
        async: false,//同步
        dataType: "json",//传递查询条件格式json
        success: function (data) {
            zNodes = data[0].children;
            rank = data[0].rank;
        }
    });
    zTreeObj = $.fn.zTree.init(setting.treeObj, setting, zNodes);
    // 默认全部展开
    zTreeObj.expandAll(true);
    // 默认首个选项展开
    var zTree_Menu = $.fn.zTree.getZTreeObj("ztree");
    var curNode;
    if(rank!=null&&rank!=""&&rank!=undefined){
        curNode = zTree_Menu.getNodes()[0].children[rank];
    }else{
        curNode = zTree_Menu.getNodes()[0].children[0];
    }
    zTree_Menu.selectNode(curNode);
    parent.mainFrame.location = curNode.link;
});
     
