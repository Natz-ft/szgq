//===========================����ȫ�ֱ���===================================
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
GLOBAL.menu.title="��ֵ����Ӧ��ϵͳ";
GLOBAL.menu.tabArrowMoveDIFValue=40;//tab��ͷ��������
GLOBAL.menu.tabSelfMoveDIFValue=25;//tab�˵��ƶ�ƫ��ֵ
GLOBAL.menu.leftMenuTipTop=0; //tip�����˵��Ķ���topֵ
GLOBAL.menu.moveSignal;//������������ƶ�һ���˵���ť�ĺ���
GLOBAL.menu.leftMenuWidth="15%";//���˵�����Ŀ��
GLOBAL.menu.mainWidth="85%";
GLOBAL.menu.tab.paddingLeft="16%";//������λ��
GLOBAL.menu.tab.paddingLeftWhenFoldLeft="10px";//�������ʱ������λ��
GLOBAL.menu.navDefault=new Array({"mid":"0","name":"��ҳ"});//����Ĭ�ϲ˵�
GLOBAL.menu.leafJson=new Array();//Ϊ������¼Ҷ�ӽڵ�˵�
GLOBAL.menu.tab.tabArrowLocate="100%";//tabArrow��ͷλ��
GLOBAL.menu.skinNumber="3";
GLOBAL.menu.arrowSort1=getPageContext()+"/css/"+GLOBAL.menu.skinNumber+"/menu/sort1.jpg";
GLOBAL.menu.arrowSort2=getPageContext()+"/css/"+GLOBAL.menu.skinNumber+"/menu/sort2.jpg";
GLOBAL.menu.arrowSort3=getPageContext()+"/css/"+GLOBAL.menu.skinNumber+"/menu/sort3.png";
GLOBAL.main=getPageContext()+"/pages/skin3/main.jsf";

GLOBAL.menu.isFoldMenuOnLogin=false; //Ĭ�ϵ�¼���Ƿ��۵��˵�999
GLOBAL.menu.tab.number=10;//Ĭ�ϴ�tab������

//===========================�¼�ע��===================================
function controlMenu() {
	var $ = jQuery.noConflict();
	//=================== ���õ�ǰ��ѡ�����껬���¼�
	$(".optn").live("mouseenter", showOptn);
	//==================���õ�ǰ��ѡ������Ƴ��¼�===============
	$(".optn").live("mouseleave", hideOptn);
	$(".tip").live("mouseenter", showTip);
	//=================== �ı�ѡ��leaf�ı���ɫ===================
	$(".leaf").live("mouseover", selectedLeafClass);
	$(".leaf").live("mouseout", unSelectedLeafClass);
	//=================== ������Ҷ�ӽڵ�ѡ��ʱ����ʽ============
	$(".nleaf,.exoptn").live("mouseover", selectNLeaf);
	$(".nleaf,.exoptn").live("mouseout", unSelectNLeaf);
	//=================== ����˵�ִ�ж���=======================
	$(".leaf,.nleaf,.exoptn").live("click keydown", clickLeaf);
	$("#foldExpand").click(clickFoldExpand);
	//=================== ������¼�ͷ�ƶ�һ���˵�================
	$(".topArrow").live("click",clickTopArrow);
	$(".bottomArrow").live("click",clickBottomArrow);
	
	//����һ���˵���갴��ʱ�����ƶ��˵��Ĺ���
	$(".topArrow").live("mousedown",moveTopArrow);
	$(".bottomArrow").live("mousedown",moveBottomArrow);
	//�����̧��ʱֹͣ�ƶ�һ���˵�
	$(".topArrow").live("mouseup",stopMoveSignal);
	$(".bottomArrow").live("mouseup",stopMoveSignal);
	
	
	/* ������رհ�ť�Ĳ��� */
	$(".selected > .close").live("click",closeTab);
	/* �л�tab��ǩ */
	$('#tab-title-tt>span').live("click",changeTab);
	/* �����ƶ�tab�ļ�ͷ */
	$(".ltarrow").live("click", moveTabToRight);
	$(".gtarrow").live("click",mouseTabToLeft);
	 

	$(".showArea").mouseenter(hideArrow);
	
	 
}
//===========================�˵�icon===================================