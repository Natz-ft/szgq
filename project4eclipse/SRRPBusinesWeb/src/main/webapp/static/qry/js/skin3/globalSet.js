//===========================设置全局变量===================================
var defaultEmptyOK1 = true

var GLOBAL={};
GLOBAL.namespace=function(str){
	
	var arr=str.split("."),o=GLOBAL;
	for(i=(arr[0]=="GLOBAL")?1:0;i<arr.length;i++)
	{
		o[arr[i]]=o[arr[i]]||{};
		o=o[arr[i]];
	}	
}
GLOBAL.namespace("menu");
GLOBAL.namespace("menu.event");
GLOBAL.namespace("menu.tab");
GLOBAL.menu.title="增值服务应用系统";
GLOBAL.menu.tabArrowMoveDIFValue=40;//tab箭头滑动距离
GLOBAL.menu.tabSelfMoveDIFValue=25;//tab菜单移动偏差值
GLOBAL.menu.leftMenuTipTop=0; //tip拉出菜单的顶部top值
GLOBAL.menu.moveSignal;//定义接收上下移动一级菜单按钮的函数
GLOBAL.menu.leftMenuWidth="15%";//左侧菜单区域的宽度
GLOBAL.menu.mainWidth="85%";
GLOBAL.menu.tab.paddingLeft="16%";//导航的位置
GLOBAL.menu.tab.paddingLeftWhenFoldLeft="10px";//左侧折起时导航的位置
GLOBAL.menu.navDefault=new Array({"mid":"0","name":"首页"});//导航默认菜单
GLOBAL.menu.leafJson=new Array();//为搜索记录叶子节点菜单
GLOBAL.menu.tab.tabArrowLocate="100%";//tabArrow箭头位置
GLOBAL.menu.skinNumber="3";
GLOBAL.menu.arrowSort1=getPageContext()+"/css/"+GLOBAL.menu.skinNumber+"/menu/sort1.jpg";
GLOBAL.menu.arrowSort2=getPageContext()+"/css/"+GLOBAL.menu.skinNumber+"/menu/sort2.jpg";
GLOBAL.menu.arrowSort3=getPageContext()+"/css/"+GLOBAL.menu.skinNumber+"/menu/sort3.png";
GLOBAL.main=getPageContext()+"/pages/skin3/main.jsf";

GLOBAL.menu.isFoldMenuOnLogin=false; //默认登录后是否折叠菜单999
GLOBAL.menu.tab.number=10;//默认打开tab的数量

//===========================事件注册===================================
function controlMenu() {
	var $ = jQuery.noConflict();
	//=================== 设置当前所选项的鼠标滑过事件
	$(".optn").live("mouseenter", showOptn);
	//==================设置当前所选项鼠标移出事件===============
	$(".optn").live("mouseleave", hideOptn);
	$(".tip").live("mouseenter", showTip);
	//=================== 改变选中leaf的背景色===================
	$(".leaf").live("mouseover", selectedLeafClass);
	$(".leaf").live("mouseout", unSelectedLeafClass);
	//=================== 非最后层叶子节点选中时的样式============
	$(".nleaf,.exoptn").live("mouseover", selectNLeaf);
	$(".nleaf,.exoptn").live("mouseout", unSelectNLeaf);
	//=================== 点击菜单执行动作=======================
	$(".leaf,.nleaf,.exoptn").live("click keydown", clickLeaf);
	$("#foldExpand").click(clickFoldExpand);
	//=================== 点击上下箭头移动一级菜单================
	$(".topArrow").live("click",clickTopArrow);
	$(".bottomArrow").live("click",clickBottomArrow);
	
	//增加一级菜单鼠标按下时连续移动菜单的功能
	$(".topArrow").live("mousedown",moveTopArrow);
	$(".bottomArrow").live("mousedown",moveBottomArrow);
	//当鼠标抬起时停止移动一级菜单
	$(".topArrow").live("mouseup",stopMoveSignal);
	$(".bottomArrow").live("mouseup",stopMoveSignal);
	
	
	/* 当点击关闭按钮的操作 */
	$(".selected > .close").live("click",closeTab);
	/* 切换tab标签 */
	$('#tab-title-tt>span').live("click",changeTab);
	/* 左右移动tab的箭头 */
	$(".ltarrow").live("click", moveTabToRight);
	$(".gtarrow").live("click",mouseTabToLeft);
	 

	$(".showArea").mouseenter(hideArrow);
	
	 
}
//===========================菜单icon===================================