/*===============�����˵�=================
 * ��ʽ���ƣ�
 * ���˵����ڵ���ʽcontent
 *1��	���Ӳ˵��Ĳ˵��� optn
 *2��	�Ӳ˵�tip
 *3��	���һ���˵� leaf
 *4��	�������˵�û���Ӳ˵��ģ�nleaf
 *5��	��һ���˵�û���Ӳ˵��ģ�exoptn
 *6��	��һ���˵�ͼƬ��ʽ��icon
 *7��	�˵�ѡ����ʽ��optnFocus
 *8��	��һ���˵�ѡ�еļ�ͷ��ʽ��sort
 *9��	�������˵�ѡ�еļ�ͷ��ʽ��sort2
 *10����ʾ���Ӳ˵��ļ�ͷ��ʽ��sort3
 *11��չ���˵�ʱҳ�������ط�����Ӱ����Ч����toumingdu
 */
//<!--	
var $ = jQuery.noConflict();// ��ֹ��jsf��$��ͻ

function interatesOnlyLeftMenu(data) {
	var defaultShow = "";
	var $ = jQuery.noConflict();
	var leftRoot = $(".menu");
	// �������˵����ڵ�
	leftRoot = leftRoot
			.append($("<ul class='content' index='1' id='content1' style="
					+ defaultShow + "></ul>"));
	interatesOverSecondLevels(data, $("#content1"), 1);
}

/*
 * ������֦�ڵ�
 */
function interatesOverSecondLevels(data, fatherTag) {
	try {
		var $ = jQuery.noConflict();
		// ��java������l1������
		var index = 0;
		var leafCount = 0;
		var isOnlyOneLeft = false;

		// ѭ���˵�����

		for ( var l1 in data) {
			var curdata = data[l1];
			curdata["index"] = index;
			curdata["class"] = "";
			var curnode = curdata["isleaf"];
			// ������Ӳ˵�,��������ʽΪoptn
			if (!curnode) {
				curdata["class"] = "optn";
			} else {
				leafCount++;
				curdata["class"] = "nleaf";
				// ��¼Ҷ�ӽڵ�˵�
				recordLeafForSearch(curdata["menuid"], curdata["menuname"]);
			}
			createLeftMenu(curdata, fatherTag);
			// ������Ӳ˵��������¼�ul
			if (!curnode) {
				// �����Ӳ˵��ĸ��ڵ�
				var ulTag = $("<ul class='tip'><div class='leafTitle'>&middot; "
						+ curdata["menuname"] + "</div></ul>");
				fatherTag.append(ulTag);
				interatesOverSecondLevels(curdata["children"], ulTag);
			}
			index++;
		}
		// ����չ�ַ�ʽ��ͬ���˴��ж��Ƿ������һ�㣬��������������һ���չ����ʽ
		if (leafCount == index) {
			// �˴��ж��Ƿ�����һ���������һ��
			// if (fatherTag.attr("class") == "content") {
			// isOnlyOneLeft = true;
			// }
			interatesOverLeaf(data, fatherTag, isOnlyOneLeft);
		}
	} catch (error) {

		console.error(error);
	}
}
/*
 * 
 * �ı����һ��ڵ����ʽ
 */
function interatesOverLeaf(data, ulTag, isOnlyOneLeft) {
	try {
		var $ = jQuery.noConflict();
		if (data) {
			var curdata = data;
			var sul;
			var classname = "leaf";
			var index = 0;
			var fname = "";

			fname = ulTag.prev("li").children("a").text();

			// ע�⹹��˳���ܵߵ�
			// �����ʽ
			ulTag.addClass("leafTip");
			ulTag.children("li").children(".icon").remove();
			ulTag.children("li").removeClass("nleaf").addClass("leaf");
			// ȥ������
			// ulTag.children("li").each(function(index1) {
			//
			// $(this).children("a").children("label").remove();
			//
			// });

			// ����Ҷ�ӽڵ�
			sul = $("<ul class='leafContent'></ul>");
			ulTag.wrapInner(sul);
			// ͳһĿ¼��Ҷ�ӽ��ĸ�����
			// ulTag.prepend("<div class='leafTitle'>" + fname
			// + "</div>");

		}
	} catch (error) {
		console.error(error);
	}
}
/*
 * ����createLeftMenu���� @data json���Ͷ���
 */
function createLeftMenu(data, fatherTag) {
	try {
		if (fatherTag) {
			var icon = "";// ��ʾ���һ���˵��Ƿ���ͼƬ
			var sort3 = ""; // ��ʾ���Ӳ˵���ָ���Ժ�ɫ��ͷ
			var icon2 = "";// ��ʾ�ڵ�
			var menuurl = "";
			var action = "";
			var menuid = "";
			var sourcevalue = "";
			var targetvalue = "";
			// ƴmenuid
			menuid = "menuid=" + data["menuid"] + " ";

			// �������ߵ�һ���˵�������ͼƬλ��
			if (fatherTag.attr("class") == "content") {
				// ѡ�в˵��ļ�ͷͼƬ
				imgsrc = GLOBAL.menu.arrowSort1;
				icon = "<img class='icon'/>";

				if (data["class"] != "optn") {
					// �����Ҷ�ӽڵ㣬����һ�����������Ҷ�ӽڵ����ʽ��ͬ�����Ե�������һ����ʽ
					data["class"] = "exoptn";
				}
				if (data["icon"]) {
					// ���ͼƬ���Զ��壬��Ӳ˵�����icon�л�ȡ��ͼƬ·��Ӧ������Եģ�
					var iconSrc = data["icon"];

					/*
					 * ���ͼƬ�Ƕ�̬�ģ���Ҫ��mianFrame��init�����ж���json���󣬴���key��value��
					 * ������ģ���ж�ȡ��json�����key��ͬ�ı�����Ӧ��ʾ����ֵ
					 */
					if (constJsonObject[iconSrc]) {
						iconSrc = constJsonObject[iconSrc];
					}
					icon = "<img class='icon' src='" + iconSrc + "'/>";

				}
			} else {
				// ����������ѡ�в˵��ļ�ͷͼƬ
				imgsrc = GLOBAL.menu.arrowSort2;
				// icon2="<label name='dot'>&dot;</label>";
			}
			// ���Ӵ�������һ���ļ�ͷͼƬ(С�ڼ�ͷ)
			if (data["class"] == "optn") {
				sort3 = "<img src='" + GLOBAL.menu.arrowSort3
						+ "' class='sort3'/>"
			}
			// ����˵��ǹ���һ��url��������action��ֱ����ת
			if (data["menuurl"]) {
				menuurl = "menuurl=" + data["menuurl"] + " ";
			}
			// ����˵�����action�������bean�ж�Ӧ����
			if (data["action"]) {
				action = "action=" + data["action"] + " ";
			}
			if (data["sourcevalue"]) {
				sourcevalue = "sourcevalue=" + data["sourcevalue"] + " ";
			}
			if (data["targetvalue"]) {
				targetvalue = "targetvalue=" + data["targetvalue"] + " ";
			}
			// sourcevalue="511" targetvalue="#{reportFlowDefineBean.sid}"
			// ��������ж�ƴ�����ղ˵�
			var menuname = data['menuname'];
			fatherTag.append("<li class='" + data["class"] + "'  index='"
					+ data["index"] + "'" + menuurl + action + menuid
					+ sourcevalue + targetvalue + ">" + icon
					+ "<a href='#' title='" + menuname + "'> " + icon2
					+ menuname + "</a>" + sort3 + "<img  src=" + imgsrc
					+ " class='sort' /></li>");

		}
	} catch (error) {
		console.error(error);
	}
}
var curW; // ��ȡ��ѡ���Widthֵ
var objL; // ��ȡ��ǰ����

/*
 * ���õ�ǰλ����ֵ ����objΪ��ǰ��������
 */
function setInitValue(obj) {

	curW = parseInt(obj.parent().width()) - 1;

}
// **********************�Բ˵�����ز���******************************/

function showOptn(event) {
	try {
		var $ = jQuery.noConflict();
		// if (event.keyCode && event.keyCode != 13) {
		// return false;
		// }
		objL = $(this);// ��ȡ��ǰ����<li>
		// objL.children("a").focus();//���ý���
		setInitValue(objL);

		// ȥ������ѡ�е���ʽ
		objL.siblings("li").removeClass("optnFocus");
		hideorshow(objL.siblings("li").children(".sort"), false);

		// ���ӵ�ǰѡ�е���ʽ
		objL.addClass("optnFocus");
		objL.children(".sort").show();
		objL.children(".sort").css("visibility", "visible");

		// ��ʾ�Ӳ˵���tip��

		if (objL.next("ul")) {
			// �Ƴ������Ѿ���ʾ��tip�Ӳ˵�
			objL.next("ul").siblings(".tip").hide();
			// ��ʾ��ǰ�Ӳ˵�
			objL.next("ul").show().css( {
				"top" :GLOBAL.menu.leftMenuTipTop,
				"left" :curW,
				"visibility" :"visible"
			});

			$(".toumingdu").show().css( {
				"visibility" :"visible"
			});
		}
	} catch (error) {
		console.error(error);
	}
}
function hideOptn(event) {
	var $ = jQuery.noConflict();

	$(this).next("ul").hide();// ������ʾ��
	$(".toumingdu").hide();// ��������

}
function showTip(event) {
	try {
		var $ = jQuery.noConflict();

		$(this).show(); // ��ʾ��ǰ�˵�<ul class='tip'>....</ul>
		$(".toumingdu").show().css( {
			"visibility" :"visible"
		});
		objL = $(this).prev("li");// ��ȡ��ǰ�ϼ�li����
		// ������ѡ����ʽ
		objL.siblings("li").removeClass("optnFocus");
		hideorshow(objL.siblings("li").children(".sort"), false);
		// ��ʾ��ǰѡ����ʽ
		objL.children(".sort").show();
		objL.addClass("optnFocus");
	} catch (error) {
		console.error(error);
	}
}
function selectedLeafClass(event) {
	var $ = jQuery.noConflict();
	$(this).addClass("leaf-selected");
}
function unSelectedLeafClass(event) {
	var $ = jQuery.noConflict();
	$(this).removeClass("leaf-selected");
}
function selectNLeaf(event) {
	var $ = jQuery.noConflict();
	$(this).siblings(".tip").hide();
	$(this).siblings(".optn").removeClass("optnFocus");
	hideorshow($(this).siblings("li").children(".sort"), false);
	$(this).addClass("leaf-selected");

}
function unSelectNLeaf(event) {
	var $ = jQuery.noConflict();
	$(this).removeClass("leaf-selected");

}
function clickLeaf(event) {

	try {
		var $ = jQuery.noConflict();

		var obj = $(this);
		// obj.children("a").focus();//���ý���
		var url = obj.attr("menuurl");// ��ȡmenuurl����
		var action = obj.attr("action");// ��ȡaction����
		var menuid = obj.attr("menuid");// ��ȡmenuid����
		var sourcevalue = obj.attr("sourcevalue");// ��ȡsourcevalue����
		var targetvalue = obj.attr("targetvalue");// ��ȡtargetvalue����
		if (menuid == "mydesktop") { // ����ҵ�����url
			url = clickLeafForMydesktop(url);
		}
		// ��ȡ��ǰ�˵�����
		var name = obj.children("a").text();
		// ��ȡ��Ӧ��iframe��id
		var idname = "fr" + menuid;
		// ��ȡ��Ӧ��top�����ж�Ӧ�Ĵ򿪲˵���id�����Ǻ���tabչʾ�˵��Ĺ��ܣ�
		var spanname = "sp" + menuid;
		// ƴjquery����id��ѡ����
		var id = "#" + idname;
		var spanid = "#" + spanname;
		// ����iframe��src����
		var iframesrc = "";
		// ��������ѡ��״̬(in tabCreate.js)
		clearAllSelect();
		// ����id�жϵ�ǰ�˵��Ƿ��Ѿ���
		var haveCreated = $("#tab-content").children(id);
		// �ж�tab�Ƿ��Ѿ����ڣ�������ڣ���ֱ�ӱ��ѡ��״̬�����򴴽�
		if (haveCreated.length > 0) {

			$(spanid).addClass("selected");
			$(id).show();
			$(spanid).children("span").addClass("close");
			// ����tab��λ��
			countTabPosition($(spanid), $(spanid), true, true, true);

		} else {

			// �жϴ�tab������
			var count = $("#tab-title-tt").children("span").length;
			if (count >= GLOBAL.menu.tab.number)// �������tab��ǩ����
			{

				var cormf = confirm("�򿪲˵����ܳ���" + GLOBAL.menu.tab.number
						+ "�����Ƿ�ر��Ѵ򿪵ĵ�һ���˵���");
				if (cormf) {
					// ��ȡ��һ��tab�˵�
					closeTab(null);// ����null����رյ�һ��tab�˵�

				} else {
					return;
				}

			}
			// ����tab��ǩͷ
			$("#tab-title-tt")
					.append(
							"<span class='selected' id='"
									+ spanname
									+ "' menuid='"
									+ menuid
									+ "'>"
									+ name
									+ "<div class='tabArrow'></div><span class='close'></span></span>");

			// �����url��ֱ����ת
			if (url) {
				iframesrc = url;
			}
			// �����action
			else if (action) {

				var beannames = action.split(".");
				var truename = action;
				if (beannames.length > 1) {
					truename = beannames[0].substring(2, beannames[0].length);
				}
				var param = GLOBAL.main + "?action=" + truename;

				if(sourcevalue)
				{var sourcenames = sourcevalue.split(".");
				if (sourcenames.length > 1) {
					sourcevalue = sourcevalue.substring(2,
							sourcevalue.length - 1);
				}
				param=param+"&source="+ sourcevalue;
				}
				if(targetvalue)
				{var targetnames = targetvalue.split(".");
				if (targetnames.length > 1) {
					targetvalue = targetvalue.substring(2,
							targetvalue.length - 1);
				}
				param=param+"&target=" + targetvalue;
				}
				iframesrc = param;
			}

			// ����tab��λ��
			countTabPosition($("#tab-title-tt>span:last"),
					$("#tab-title-tt>span:last"), false, true, true);

			// ����iframe
			$("#tab-content").append(
					"<iframe frameborder='0' src='" + iframesrc + "' id='"
							+ idname + "' menuid='" + menuid + "' ></iframe>");
		}

		// ���˵�����
		hideorshow($(".sort"), false);
		hideorshow($(".tip"), false);
		$("li").removeClass("optnFocus");
		$(".toumingdu").hide();

		// ���嵼��
		setNaviLink(obj);
	} catch (error) {
		console.error(error);
	}
	// ��ֹ�¼�ð��
	return false;
}
function clickFoldExpand(event) {
	try {
		var $ = jQuery.noConflict();

		var lm = $(".leftMenu");
		var rm = $(".main");
		var navbar = $(".navibar");
		var isshow = lm.css("display");
		if (isshow == "none") {

			// չ�����˵�
			lm.removeClass("hideLeftMenu");
			lm.addClass("expendLeftMenu");
			rm.css("width", GLOBAL.menu.mainWidth);
			rm.css("left", GLOBAL.menu.leftMenuWidth);
			navbar.css("padding-left", GLOBAL.menu.tab.paddingLeft);

			$(this).removeClass("expandImage");
			$(this).addClass("foldImage");
			clickExpandForMydesktop(lm);// չ�����˵������ҵ�����λ��
		} else {

			// �������˵�
			lm.removeClass("expendLeftMenu");
			lm.addClass("hideLeftMenu");
			rm.css("width", "100%");
			rm.css("left", "0");
			navbar.css("padding-left", GLOBAL.menu.tab.paddingLeftWhenFoldLeft);

			$(this).removeClass("foldImage");
			$(this).addClass("expandImage");
			clickFoldForMydesktop();// ����˵����������ҵ�����λ��

		}
	} catch (error) {
		console.error(error);
	}
}
function moveTopArrow(event) {
	try {
		var $ = jQuery.noConflict();
		GLOBAL.menu.moveSignal= setInterval("clickTopArrow()",300);
	} catch (error) {
		console.error(error);
	}
}
function moveBottomArrow(event) {
	try {
		var $ = jQuery.noConflict();
		GLOBAL.menu.moveSignal=setInterval("clickBottomArrow()",300);
	}catch (error) {
		console.error(error);
	}
}
function stopMoveSignal(){
	
	if(GLOBAL.menu.moveSignal)
	clearInterval(GLOBAL.menu.moveSignal);
}
function clickTopArrow() {
	try {
		var $ = jQuery.noConflict();
		// �ж�Ҫ��Ҫ�ƶ�
		var arrowtop = ($(".topArrow").offset().top + parseInt($(".topArrow")
				.height()));
		var firsttop = ($(".content>li:first").offset().top);
		if (firsttop < arrowtop) {
			$(".content").stop(true);
			$(".content").animate( {
				"margin-top" :"+=" + GLOBAL.menu.tabArrowMoveDIFValue
			});
		}
	} catch (error) {
		console.error(error);
	}
}
function clickBottomArrow() {
	try {
		var $ = jQuery.noConflict();
		// �ж�Ҫ��Ҫ�ƶ�

		if (($(".content>li:last").offset().top + 40) >= $(".bottomArrow")
				.offset().top) {
			$(".content").stop(true);
			$(".content").animate( {
				"margin-top" :"-=" + GLOBAL.menu.tabArrowMoveDIFValue
			});
		}
	} catch (error) {
		console.error(error);
	}
}

// �������ػ���ʾ�����ص�div�в���hide��������Ч�ģ��ڴ��ô˷����滻
function hideorshow(obj, isshow) {
	if (isshow) {
		obj.css("display", "block");
		obj.css("visibility", "visible");
	} else {
		obj.css("display", "none");
		obj.css("visibility", "hidden");
	}

}
// ��¼���Զ��򿪲˵�����
function getMenuShow(menuid) {
	var $ = jQuery.noConflict();// ��ֹ��jsf��$��ͻ
	var objname = "li[menuid='" + menuid + "']";
	if ($(objname).length > 0) {
		$(objname).click();
	} else {
		
		var pareobj=$(objname,window.parent.document);
		if(pareobj.length>0)
		{
			pareobj.click();
		}else{
		    alert("δ�ҵ�Ҫ�򿪵Ĳ˵�������menuid�Ƿ���ȷ��");
		}
	}
}
function setNavigatePath(menuid) {

	var $ = jQuery.noConflict();// ��ֹ��jsf��$��ͻ
	var obj = $("li[menuid=" + menuid + "]");

	// GLOBAL.menu.navDefault.
	// GLOBAL.menu.navDefault.push(obj.children("a").text());
	// $("navibar").text()

}

/**
 * 
 * @param iconObject
 *            ��ʾ���������ʽ
 * @param fun
 *            �ص�����
 * @return
 */
function showDropArea(dropAreaName, fun) {
	try {
		var $ = jQuery.noConflict();// ��ֹ��jsf��$��ͻ
		var top = $(".logon").height();
		$(dropAreaName).css("top", top);
		$(dropAreaName).slideDown();
		// ���������¼�
		$(dropAreaName).mouseleave( function() {
			$(this).fadeOut();
		});
		if (fun)
			fun.call();
	} catch (error) {
		console.error(error);
	}
}

/**
 * ��ȡ�˵��켣
 * 
 * @param link
 *            array
 * @param obj
 *            leafobj
 * @return
 */
function getNaviLink(str, obj) {

	var key = obj.attr["menuid"];
	var value = obj.children("a").text();
	var prevMenu;
	if (obj.parent(".leafContent").length > 0) {
		prevMenu = obj.parent(".leafContent").parent().prev("li");
	} else {
		prevMenu = obj.parent().prev("li");
	}
	if (prevMenu.length > 0) {
		str = getNaviLink(str, prevMenu);
	}
	str = str + "<<" + value;
	return str;
}

/**
 * ���õ������Ӳ˵�
 * 
 * @param obj
 *            ��ǰ�˵�����
 * @return
 */
function setNaviLink(obj) {
	if (obj) {
		var naviurl = getNaviLink("��ҳ", obj);
		$(".navibar").text(naviurl);
	} else {
		$(".navibar").text("");
	}
}

/**
 * ��¼Ҷ�ӽڵ�˵�
 * 
 * @param menuid
 *            �˵�id
 * @param menuname
 *            �˵�����
 * @return
 */
function recordLeafForSearch(menuid, menuname) {
	var curjson = {
		"mid" :menuid,
		"mname" :menuname
	};
	GLOBAL.menu.leafJson.push(curjson);
}

function clickLeafForMydesktop(menuurl) { // ����ҵ�����url
	var userid = document.getElementById("userid").value;
	var pathName = document.location.pathname;// ���������ַ֮���Ŀ¼
	var projectName = pathName.substring(0,
			pathName.substring(1).indexOf('/') + 1);
	url = projectName + "/" + menuurl + userid;
	return url;

}

function clickExpandForMydesktop(lm) { // չ���˵����ҵ����棬�����ҵ�����λ��
	if ($(window.frames["frmydesktop"]).length != 0) {
		var obj = $(window.frames["frmydesktop"].document);
		if (obj.length > 0) {
			var currTab = obj.find(".currTab");
			var desktopInnerPanel = obj.find("#desktopInnerPanel");
		}
		if (currTab.length > 0) {
			var mydesktopId = currTab.attr("id");
			if (mydesktopId == "mydesktop2") {
				desktopInnerPanel.css( {
					"left" :desktopInnerPanel.offset().left + lm.width()
				});
			} else if (mydesktopId == "mydesktop3") {
				desktopInnerPanel.css( {
					"left" :desktopInnerPanel.offset().left + 2 * lm.width()
				});
			}
		}
		obj.find(".deskIcon").width(document.body.clientWidth - lm.width());
		var expandShoppingCartSize = desktopInnerPanel.width() / 3 - lm.width()
				- 1000;
		obj.find(".shopping_cart").css( {
			"margin-left" :expandShoppingCartSize
		});
	}
}

function clickFoldForMydesktop() { // ����˵����������ҵ�����λ��
	if ($(window.frames["frmydesktop"]).length != 0) {
		var obj = $(window.frames["frmydesktop"].document);
		if (obj.length > 0) {
			var currTab = obj.find(".currTab");
			var desktopInnerPanel = obj.find("#desktopInnerPanel");
		}
		if (currTab.length > 0) {
			var mydesktopId1 = currTab.attr("id");
			if (mydesktopId1 == "mydesktop2") {
				desktopInnerPanel.css( {
					"left" :0 - document.body.clientWidth
				});
			} else if (mydesktopId1 == "mydesktop3") {
				desktopInnerPanel.css( {
					"left" :0 - 2 * document.body.clientWidth
				});
			}
		}
		desktopInnerPanel.css( {
			"width" :3 * (document.body.clientWidth)
		});
		obj.find(".deskIcon").width(document.body.clientWidth);
		var foldShoppingCartSize = desktopInnerPanel.width() / 3 - 1000;
		obj.find(".shopping_cart").css( {
			"margin-left" :foldShoppingCartSize
		});
	}
}

//-->
