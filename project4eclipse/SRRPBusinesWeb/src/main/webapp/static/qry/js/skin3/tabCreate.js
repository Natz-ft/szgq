/*===============tab�˵�����=================
 * ��ʽ���ƣ�
*1��	Tab�˵�ѡ����ʽ:selected
*2��	Tab�˵��رհ�ť��ʽ��close;
*3��	Tab�˵����ͷ��ʽ��ltarrow
*4��	Tab�˵��Ҽ�ͷ��ʽ��rtarrow
*/
//<!--	
function clearAllSelect() {
	var $ = jQuery.noConflict();// ��ֹ��jsf��$��ͻ
	$("#tab-title-tt>span").removeClass("selected");
	$("#tab-title-tt>span>span").removeClass("close");
	$("#tab-content > iframe").hide();
	$(".tabArrow").hide();//����ѡ�м�ͷ
}

function closeTab(event){
	try{
	var $ = jQuery.noConflict();
	var tab = $(this).parent();
	if(event==null)
	{
		tab=$("#tab-title-tt>span:first");//Ĭ������һ��tab
	}
	var index = tab.attr("menuid");
	var iframe = $("#tab-content > iframe[menuid='" + index + "']");

	// �������tab��ǰ�����
	var nexttab = tab.next().length > 0 ? tab.next() : tab.prev();
	var nextframe = iframe.next().length > 0 ? iframe.next() : iframe
			.prev();

	if (nexttab.length > 0 && nextframe.length > 0) {
		nexttab.addClass("selected");
		nexttab.children("span").addClass("close");
		nextframe.show();
		
		
	}

	// �ƶ������ӷ�Χ��
    countTabPosition(nexttab,$("#tab-title-tt>span"),true,false,false);
	
	// �Ƴ���ǰtab
	iframe.remove();
	tab.remove();
	
	//���¶��嵼��
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
	// ��ֹ�¼�ð��
	return false;
}
function changeTab(event){
	try{
	clearAllSelect();
	var $ = jQuery.noConflict();
	$(this).addClass("selected");
	
	// �ƶ������ӷ�Χ��
	    countTabPosition($(this),$(this),true,true,false);
	
		$("#tab-content > iframe").eq($('#tab-title-tt>span').index(this))
				.show();

		// ���ӹرհ�ť��ʽ
		var close = $(this).children("span");
		close.addClass("close");
		
		//���¶��嵼��
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
	//��ͷ��ʾ��ǰ��ѡ��tab�˵�
	curtab.children(".tabArrow").show();
	curtab.children(".tabArrow").css("top",GLOBAL.menu.tab.tabArrowLocate);
	//�ж��Ƿ���Ҫ���㣬�Ƿ�tab�Ѿ���ʾ������
	var flag=showorhidetabArrow();
	//�����ͷλ��
	//locateTabArrow(curtab);
	if(!flag)
	{
		
		return ;
	}
	
   var position=new tabPosition(movetab, isLeft, isRight, isNew);
   var moveObje=$("#tab-title-tt>span");
   
   
 //����tab��λ��
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
 * moveObject ���ƶ��Ķ���
 * space �ƶ�����
 * mark  �ƶ��ķ��ţ�+,-=,+=��
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
	//����Ĭ��
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
		alert("tabPosition��������");
	}

}
/**
 * �ж��Ƿ���Ҫ��ʾtab�ļ�ͷ
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
