/*===============tab菜单操作=================
 * 样式名称：
*1、	Tab菜单选中样式:selected
*2、	Tab菜单关闭按钮样式：close;
*3、	Tab菜单左箭头样式：ltarrow
*4、	Tab菜单右箭头样式：rtarrow
*/
//<!--	
function clearAllSelect() {
	var $ = jQuery.noConflict();// 防止与jsf的$冲突
	$("#tab-title-tt>span").removeClass("selected");
	$("#tab-title-tt>span>span").removeClass("close");
	$("#tab-content > iframe").hide();
	$(".tabArrow").hide();//隐藏选中箭头
}

function closeTab(event){
	try{
	var $ = jQuery.noConflict();
	var tab = $(this).parent();
	if(event==null)
	{
		tab=$("#tab-title-tt>span:first");//默认左侧第一个tab
	}
	var index = tab.attr("menuid");
	var iframe = $("#tab-content > iframe[menuid='" + index + "']");

	// 让其后面tab往前或后移
	var nexttab = tab.next().length > 0 ? tab.next() : tab.prev();
	var nextframe = iframe.next().length > 0 ? iframe.next() : iframe
			.prev();

	if (nexttab.length > 0 && nextframe.length > 0) {
		nexttab.addClass("selected");
		nexttab.children("span").addClass("close");
		nextframe.show();
		
		
	}

	// 移动到可视范围内
    countTabPosition(nexttab,$("#tab-title-tt>span"),true,false,false);
	
	// 移除当前tab
	iframe.remove();
	tab.remove();
	
	//重新定义导航
	var nextindex;
	var obj;
	if(nextframe.length>0)
	{
		nextindex=nextframe.attr("menuid");
	    obj=$("li[menuid="+nextindex+"]");
	}
	setNaviLink(obj);
	}catch(error)
	{
		console.error(error);
	}
	// 防止事件冒泡
	return false;
}
function changeTab(event){
	try{
	clearAllSelect();
	var $ = jQuery.noConflict();
	$(this).addClass("selected");
	
	// 移动到可视范围内
	    countTabPosition($(this),$(this),true,true,false);
	
		$("#tab-content > iframe").eq($('#tab-title-tt>span').index(this))
				.show();

		// 增加关闭按钮样式
		var close = $(this).children("span");
		close.addClass("close");
		
		//重新定义导航
		var index = $(this).attr("menuid");
		var obj=$("li[menuid="+index+"]");
		setNaviLink(obj);
	}catch(error)
	{
		console.error(error);
		
	}
}
function moveTabToRight(event)
{
	try{
	var $ = jQuery.noConflict();
	var lleft = $("#tab-title-tt").offset().left;
	var sleft = $("#tab-title-tt>span:first").offset().left;

	if (sleft >= lleft) {
		return;
	}
	$("#tab-title-tt>span").stop(true);
	$("#tab-title-tt>span").animate({
		left :"+="+GLOBAL.menu.tabArrowMoveDIFValue
	});
	}catch(error)
	{
		console.error(error);
	}
}
function mouseTabToLeft(event)
{
	try{
	var $ = jQuery.noConflict();
	var lright = $("#tab-title-tt").offset().left
			+ $("#tab-title-tt").width();
	var sright = $("#tab-title-tt>span:last").offset().left
			+ $("#tab-title-tt>span:last").width();

	if (sright <= lright) {
		return;
	}
	$("#tab-title-tt>span").stop(true);
	$("#tab-title-tt>span").animate( {
		left :"-="+GLOBAL.menu.tabArrowMoveDIFValue
	});}catch(error)
	{
		console.error(error);
	}
}


function countTabPosition(curtab,movetab, isLeft, isRight, isNew)
{
	var $ = jQuery.noConflict();
	//箭头显示当前的选中tab菜单
	curtab.children(".tabArrow").show();
	curtab.children(".tabArrow").css("top",GLOBAL.menu.tab.tabArrowLocate);
	//判断是否需要计算，是否tab已经显示不下了
	var flag=showorhidetabArrow();
	//计算箭头位置
	//locateTabArrow(curtab);
	if(!flag)
	{
		
		return ;
	}
	
   var position=new tabPosition(movetab, isLeft, isRight, isNew);
   var moveObje=$("#tab-title-tt>span");
   
   
 //计算tab的位置
   var minus;
	if ((position.curtabLeft + position.curtabWidth) >= (position.rarrowLeft))
	{
		
		if(position.curtabLeft<position.rarrowLeft)
		{
			 minus=(position.curtabWidth-(position.rarrowLeft-position.curtabLeft));
		}else
		{
			 minus=(position.curtabLeft-position.rarrowLeft+position.curtabWidth);
		}
		moveTabLeft(curtab,moveObje,minus+GLOBAL.menu.tabSelfMoveDIFValue,"-=");
	}
	else if(position.curtabLeft<=position.larrowLeft)
	{
			minus=position.larrowLeft-position.curtabLeft;
		moveTabLeft(curtab,moveObje,minus+GLOBAL.menu.tabSelfMoveDIFValue,"+=");
	}
	
}
/*
 * moveObject 被移动的对象
 * space 移动距离
 * mark  移动的符号（+,-=,+=）
 * */
function moveTabLeft(curtab,moveObject, space,mark)
{   var $ = jQuery.noConflict();
    var count=moveObject.length;
    var index=0;
    moveObject.stop(true);
	moveObject.animate({left:""+mark+space},function(event){
		//index++;
		//if(index==count){
		//locateTabArrow(curtab);
		//}
	});
	
}
function tabPosition(curtab, isLeft, isRight, isNew) {
	try{
		var $ = jQuery.noConflict();
		this.larrowLeft = $(".ltarrow").offset().left;
		this.rarrowLeft = $(".gtarrow").offset().left;
	if (isRight) {
		
		this.lastSpanLeft = $("#tab-title-tt>span:last").css("left");
		this.lastSpanWidth = $("#tab-title-tt>span:last").width();
	}
	if (isLeft) {
		
		this.firstSpanLeft = $("#tab-title-tt>span:first").css("left");
		this.firstSpanWidth = $("#tab-title-tt>span:first").width();
	}
	//定义默认
	this.everySpanLeft=0;
	if (isNew) {
		this.everySpanLeft = $("#tab-title-tt>span").css("left");
		if (this.everySpanLeft == "auto") {
			this.everySpanLeft = 0;
		} 
		curtab.css("left",this.everySpanLeft);
	}
	
	
	this.curtabLeft = curtab.offset().left;
	this.curtabWidth = curtab.width();
	
	}catch(error)
	{
		alert("tabPosition方法错误");
	}

}
/**
 * 判断是否需要显示tab的箭头
 * @return
 */
function showorhidetabArrow(){
	var $ = jQuery.noConflict();
	
	var alltabWidth=$('#tab-title-tt>span:last').offset().left-$('#tab-title-tt>span:first').offset().left+ $('#tab-title-tt>span:last').width();
	var areaWidth=$('#tab-title-tt').width();

	
	if(alltabWidth>=areaWidth)
	{
		$(".ltarrow").show();
		$(".gtarrow").show();
	}
	else
	{
		$(".ltarrow").hide();
		$(".gtarrow").hide();
		return false;
	}
	
	return true;
}


// -->
