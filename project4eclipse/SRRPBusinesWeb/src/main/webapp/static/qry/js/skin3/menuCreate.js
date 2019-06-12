/*===============遍历菜单=================
 * 样式名称：
 * 左侧菜单根节点样式content
 *1、	有子菜单的菜单项 optn
 *2、	子菜单tip
 *3、	最后一级菜单 leaf
 *4、	其他级菜单没有子菜单的：nleaf
 *5、	第一级菜单没有子菜单的：exoptn
 *6、	第一级菜单图片样式：icon
 *7、	菜单选中样式：optnFocus
 *8、	第一级菜单选中的箭头样式：sort
 *9、	其他级菜单选中的箭头样式：sort2
 *10、表示有子菜单的箭头样式：sort3
 *11、展开菜单时页面其他地方被阴影遮罩效果：toumingdu
 */
//<!--	
var $ = jQuery.noConflict();// 防止与jsf的$冲突

function interatesOnlyLeftMenu(data) {
	var defaultShow = "";
	var $ = jQuery.noConflict();
	var leftRoot = $(".menu");
	// 创建左侧菜单根节点
	leftRoot = leftRoot
			.append($("<ul class='content' index='1' id='content1' style="
					+ defaultShow + "></ul>"));
	interatesOverSecondLevels(data, $("#content1"), 1);
}

/*
 * 遍历树枝节点
 */
function interatesOverSecondLevels(data, fatherTag) {
	try {
		var $ = jQuery.noConflict();
		// 与java的区别，l1是索引
		var index = 0;
		var leafCount = 0;
		var isOnlyOneLeft = false;

		// 循环菜单数组

		for ( var l1 in data) {
			var curdata = data[l1];
			curdata["index"] = index;
			curdata["class"] = "";
			var curnode = curdata["isleaf"];
			// 如果有子菜单,则设置样式为optn
			if (!curnode) {
				curdata["class"] = "optn";
			} else {
				leafCount++;
				curdata["class"] = "nleaf";
				// 记录叶子节点菜单
				recordLeafForSearch(curdata["menuid"], curdata["menuname"]);
			}
			createLeftMenu(curdata, fatherTag);
			// 如果有子菜单，则创立下级ul
			if (!curnode) {
				// 创建子菜单的根节点
				var ulTag = $("<ul class='tip'><div class='leafTitle'>&middot; "
						+ curdata["menuname"] + "</div></ul>");
				fatherTag.append(ulTag);
				interatesOverSecondLevels(curdata["children"], ulTag);
			}
			index++;
		}
		// 由于展现方式不同，此处判断是否是最后一层，如果是则配置最后一层的展现样式
		if (leafCount == index) {
			// 此处判断是否左侧第一级就是最后一层
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
 * 改变最后一层节点的样式
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

			// 注意构造顺序不能颠倒
			// 添加样式
			ulTag.addClass("leafTip");
			ulTag.children("li").children(".icon").remove();
			ulTag.children("li").removeClass("nleaf").addClass("leaf");
			// 去掉方点
			// ulTag.children("li").each(function(index1) {
			//
			// $(this).children("a").children("label").remove();
			//
			// });

			// 包裹叶子节点
			sul = $("<ul class='leafContent'></ul>");
			ulTag.wrapInner(sul);
			// 统一目录下叶子结点的父标题
			// ulTag.prepend("<div class='leafTitle'>" + fname
			// + "</div>");

		}
	} catch (error) {
		console.error(error);
	}
}
/*
 * 创建createLeftMenu方法 @data json类型对象
 */
function createLeftMenu(data, fatherTag) {
	try {
		if (fatherTag) {
			var icon = "";// 表示左侧一级菜单是否有图片
			var sort3 = ""; // 表示有子菜单的指向性黑色箭头
			var icon2 = "";// 表示黑点
			var menuurl = "";
			var action = "";
			var menuid = "";
			var sourcevalue = "";
			var targetvalue = "";
			// 拼menuid
			menuid = "menuid=" + data["menuid"] + " ";

			// 如果是左边第一级菜单则增加图片位置
			if (fatherTag.attr("class") == "content") {
				// 选中菜单的箭头图片
				imgsrc = GLOBAL.menu.arrowSort1;
				icon = "<img class='icon'/>";

				if (data["class"] != "optn") {
					// 如果是叶子节点，由于一级与其他层的叶子节点的样式不同，所以单独设立一个样式
					data["class"] = "exoptn";
				}
				if (data["icon"]) {
					// 如果图片是自定义，则从菜单属性icon中获取（图片路径应该是相对的）
					var iconSrc = data["icon"];

					/*
					 * 如果图片是动态的，需要在mianFrame的init方法中定义json对象，传入key和value，
					 * 这里会从模型中读取与json对象的key相同的变量对应显示他的值
					 */
					if (constJsonObject[iconSrc]) {
						iconSrc = constJsonObject[iconSrc];
					}
					icon = "<img class='icon' src='" + iconSrc + "'/>";

				}
			} else {
				// 二级及以上选中菜单的箭头图片
				imgsrc = GLOBAL.menu.arrowSort2;
				// icon2="<label name='dot'>&dot;</label>";
			}
			// 增加代表有下一级的箭头图片(小黑箭头)
			if (data["class"] == "optn") {
				sort3 = "<img src='" + GLOBAL.menu.arrowSort3
						+ "' class='sort3'/>"
			}
			// 如果菜单是关联一个url，而不是action则直接流转
			if (data["menuurl"]) {
				menuurl = "menuurl=" + data["menuurl"] + " ";
			}
			// 如果菜单关联action，则调用bean中对应方法
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
			// 结合以上判断拼出最终菜单
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
var curW; // 获取所选项的Width值
var objL; // 获取当前对象

/*
 * 设置当前位置数值 参数obj为当前对象名称
 */
function setInitValue(obj) {

	curW = parseInt(obj.parent().width()) - 1;

}
// **********************对菜单的相关操作******************************/

function showOptn(event) {
	try {
		var $ = jQuery.noConflict();
		// if (event.keyCode && event.keyCode != 13) {
		// return false;
		// }
		objL = $(this);// 获取当前对象<li>
		// objL.children("a").focus();//设置焦点
		setInitValue(objL);

		// 去掉所有选中的样式
		objL.siblings("li").removeClass("optnFocus");
		hideorshow(objL.siblings("li").children(".sort"), false);

		// 增加当前选中的样式
		objL.addClass("optnFocus");
		objL.children(".sort").show();
		objL.children(".sort").css("visibility", "visible");

		// 显示子菜单（tip）

		if (objL.next("ul")) {
			// 移除其他已经显示的tip子菜单
			objL.next("ul").siblings(".tip").hide();
			// 显示当前子菜单
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

	$(this).next("ul").hide();// 隐藏提示框
	$(".toumingdu").hide();// 隐藏遮罩

}
function showTip(event) {
	try {
		var $ = jQuery.noConflict();

		$(this).show(); // 显示当前菜单<ul class='tip'>....</ul>
		$(".toumingdu").show().css( {
			"visibility" :"visible"
		});
		objL = $(this).prev("li");// 获取当前上级li对象
		// 隐藏已选中样式
		objL.siblings("li").removeClass("optnFocus");
		hideorshow(objL.siblings("li").children(".sort"), false);
		// 显示当前选中样式
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
		// obj.children("a").focus();//设置焦点
		var url = obj.attr("menuurl");// 获取menuurl属性
		var action = obj.attr("action");// 获取action属性
		var menuid = obj.attr("menuid");// 获取menuid属性
		var sourcevalue = obj.attr("sourcevalue");// 获取sourcevalue属性
		var targetvalue = obj.attr("targetvalue");// 获取targetvalue属性
		if (menuid == "mydesktop") { // 获得我的桌面url
			url = clickLeafForMydesktop(url);
		}
		// 获取当前菜单名称
		var name = obj.children("a").text();
		// 获取对应的iframe的id
		var idname = "fr" + menuid;
		// 获取对应的top区域中对应的打开菜单的id（就是横向tab展示菜单的功能）
		var spanname = "sp" + menuid;
		// 拼jquery调用id的选择器
		var id = "#" + idname;
		var spanid = "#" + spanname;
		// 定义iframe的src属性
		var iframesrc = "";
		// 清理所有选中状态(in tabCreate.js)
		clearAllSelect();
		// 根据id判断当前菜单是否已经打开
		var haveCreated = $("#tab-content").children(id);
		// 判断tab是否已经存在，如果存在，则直接变成选中状态，否则创建
		if (haveCreated.length > 0) {

			$(spanid).addClass("selected");
			$(id).show();
			$(spanid).children("span").addClass("close");
			// 计算tab的位置
			countTabPosition($(spanid), $(spanid), true, true, true);

		} else {

			// 判断打开tab的数量
			var count = $("#tab-title-tt").children("span").length;
			if (count >= GLOBAL.menu.tab.number)// 如果超出tab标签数量
			{

				var cormf = confirm("打开菜单不能超过" + GLOBAL.menu.tab.number
						+ "个，是否关闭已打开的第一个菜单？");
				if (cormf) {
					// 获取第一个tab菜单
					closeTab(null);// 传入null代表关闭第一个tab菜单

				} else {
					return;
				}

			}
			// 创建tab标签头
			$("#tab-title-tt")
					.append(
							"<span class='selected' id='"
									+ spanname
									+ "' menuid='"
									+ menuid
									+ "'>"
									+ name
									+ "<div class='tabArrow'></div><span class='close'></span></span>");

			// 如果有url则直接流转
			if (url) {
				iframesrc = url;
			}
			// 如果是action
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

			// 计算tab的位置
			countTabPosition($("#tab-title-tt>span:last"),
					$("#tab-title-tt>span:last"), false, true, true);

			// 增加iframe
			$("#tab-content").append(
					"<iframe frameborder='0' src='" + iframesrc + "' id='"
							+ idname + "' menuid='" + menuid + "' ></iframe>");
		}

		// 将菜单隐藏
		hideorshow($(".sort"), false);
		hideorshow($(".tip"), false);
		$("li").removeClass("optnFocus");
		$(".toumingdu").hide();

		// 定义导航
		setNaviLink(obj);
	} catch (error) {
		console.error(error);
	}
	// 防止事件冒泡
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

			// 展开左侧菜单
			lm.removeClass("hideLeftMenu");
			lm.addClass("expendLeftMenu");
			rm.css("width", GLOBAL.menu.mainWidth);
			rm.css("left", GLOBAL.menu.leftMenuWidth);
			navbar.css("padding-left", GLOBAL.menu.tab.paddingLeft);

			$(this).removeClass("expandImage");
			$(this).addClass("foldImage");
			clickExpandForMydesktop(lm);// 展开左侧菜单调整我的桌面位置
		} else {

			// 收起左侧菜单
			lm.removeClass("expendLeftMenu");
			lm.addClass("hideLeftMenu");
			rm.css("width", "100%");
			rm.css("left", "0");
			navbar.css("padding-left", GLOBAL.menu.tab.paddingLeftWhenFoldLeft);

			$(this).removeClass("foldImage");
			$(this).addClass("expandImage");
			clickFoldForMydesktop();// 收起菜单栏，调整我的桌面位置

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
		// 判断要不要移动
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
		// 判断要不要移动

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

// 设置隐藏或显示，隐藏的div中操纵hide方法是无效的，在此用此方法替换
function hideorshow(obj, isshow) {
	if (isshow) {
		obj.css("display", "block");
		obj.css("visibility", "visible");
	} else {
		obj.css("display", "none");
		obj.css("visibility", "hidden");
	}

}
// 登录后自动打开菜单方法
function getMenuShow(menuid) {
	var $ = jQuery.noConflict();// 防止与jsf的$冲突
	var objname = "li[menuid='" + menuid + "']";
	if ($(objname).length > 0) {
		$(objname).click();
	} else {
		
		var pareobj=$(objname,window.parent.document);
		if(pareobj.length>0)
		{
			pareobj.click();
		}else{
		    alert("未找到要打开的菜单，请检查menuid是否正确！");
		}
	}
}
function setNavigatePath(menuid) {

	var $ = jQuery.noConflict();// 防止与jsf的$冲突
	var obj = $("li[menuid=" + menuid + "]");

	// GLOBAL.menu.navDefault.
	// GLOBAL.menu.navDefault.push(obj.children("a").text());
	// $("navibar").text()

}

/**
 * 
 * @param iconObject
 *            显示下拉框的样式
 * @param fun
 *            回调函数
 * @return
 */
function showDropArea(dropAreaName, fun) {
	try {
		var $ = jQuery.noConflict();// 防止与jsf的$冲突
		var top = $(".logon").height();
		$(dropAreaName).css("top", top);
		$(dropAreaName).slideDown();
		// 定义收起事件
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
 * 获取菜单轨迹
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
 * 设置导航链接菜单
 * 
 * @param obj
 *            当前菜单对象
 * @return
 */
function setNaviLink(obj) {
	if (obj) {
		var naviurl = getNaviLink("首页", obj);
		$(".navibar").text(naviurl);
	} else {
		$(".navibar").text("");
	}
}

/**
 * 记录叶子节点菜单
 * 
 * @param menuid
 *            菜单id
 * @param menuname
 *            菜单名称
 * @return
 */
function recordLeafForSearch(menuid, menuname) {
	var curjson = {
		"mid" :menuid,
		"mname" :menuname
	};
	GLOBAL.menu.leafJson.push(curjson);
}

function clickLeafForMydesktop(menuurl) { // 获得我的桌面url
	var userid = document.getElementById("userid").value;
	var pathName = document.location.pathname;// 获得主机地址之后的目录
	var projectName = pathName.substring(0,
			pathName.substring(1).indexOf('/') + 1);
	url = projectName + "/" + menuurl + userid;
	return url;

}

function clickExpandForMydesktop(lm) { // 展开菜单栏我的桌面，调整我的桌面位置
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

function clickFoldForMydesktop() { // 收起菜单栏，调整我的桌面位置
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
