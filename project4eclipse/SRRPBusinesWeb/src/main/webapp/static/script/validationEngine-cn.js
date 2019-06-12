/*edited by fukai*/
(function($) {
	$.fn.validationEngineLanguage = function() {
	};
	$.validationEngineLanguage = {
		newLang : function() {
			$.validationEngineLanguage.allRules = {
				"required" : {
					"regex" : "none",
					"alertText" : "* 非空选项.",
					"alertTextCheckboxMultiple" : "* 请选择一个单选框.",
					"alertTextCheckboxe" : "* 请选择一个复选框."
				},
				"length" : {
					"regex" : "none",
					"alertText" : "* 长度必须在",
					"alertText2" : " 至 ",
					"alertText3" : "之间."
				},
				"maxCheckbox" : {
					"regex" : "none",
					"alertText" : "* 最多选择 ",
					"alertText2" : " 项."
				},
				"minCheckbox" : {
					"regex" : "none",
					"alertText" : "* 至少选择 ",
					"alertText2" : " 项."
				},
				"confirm" : {
					"regex" : "none",
					"alertText" : "* 两次输入不一致,请重新输入."
				},
				"telephone" : {
					"regex" : "/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][3758][0-9]{9}$)/",
					"alertText" : "* 请输入有效的电话号码,如:010-29292929,或手机号."
				},
				"mobilephone" : {
					"regex" : "/(^0?[1][358][0-9]{9}$)/",
					"alertText" : "* 请输入有效的手机号码."
				},
				"email" : {
					"regex" : "/^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/",
					"alertText" : "* 请输入有效的邮件地址."
				},
				"date" : {
					"regex" : "/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/",
					"alertText" : "* 请输入有效的日期,如:2008-08-08."
				},
				"ip" : {
					"regex" : "/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/",
					"alertText" : "* 请输入有效的IP."
				},
				"chinese" : {
					"regex" : "/^[\u4e00-\u9fa5]+$/",
					"alertText" : "* 请输入中文."
				},
				"url" : {
					"regex" : "/^[a-zA-z]:\\/\\/[^s]$/",
					"alertText" : "* 请输入有效的网址."
				},
				"zopcode" : {
					"regex" : "/^[1-9][0-9]{5}$/",
					"alertText" : "* 请输入有效的邮政编码."
				},
				"qq" : {
					"regex" : "/^[1-9][0-9]{4,9}$/",
					"alertText" : "* 请输入有效的QQ号码."
				},
				"onlyNumber" : {
					"regex" : "/^[0-9]+$/",
					"alertText" : "* 请输入数字."
				},
				"onlyLetter" : {
					"regex" : "/^[a-zA-Z]+$/",
					"alertText" : "* 请输入英文字母."
				},
				"onlyLetter1" : {
					"alertText" : "* 请输入英文字母."
				},
				"noSpecialCaracters" : {
					"regex" : "/^[0-9a-zA-Z]+$/",
					"alertText" : "* 请输入英文字母或数字."
				},
				/*
				 * "ajaxUser":{
				 * 
				 * "file":"/creditplatformweb/admin/user/checkUsername",
				 * 
				 * "file":"findById.action",
				 * 
				 * "alertTextOk":"* 帐号可以使用.", "alertTextLoad":"* 检查中, 请稍后...",
				 * "alertText":"* 帐号不能使用."},
				 */
				// 判断菜单ID不能重复 不能存在
				"ajaxFindById" : {
					"file" : "ajaxFindById.action",
					"alertTextLoad" : "* 检查中, 请稍后...",
					"alertText" : "* 菜单ID不能重复."
				},
				// 判断父级菜单ID是否存在
				"ajaxFindByParentId" : {
					"file" : "ajaxFindByParentId.action",
					"alertTextLoad" : "* 检查中, 请稍后...",
					"alertText" : "* 父菜单ID不存在."
				},
				// 判断机构编码是否重复
				"ajaxFindByOrgId" : {
					"file" : "ajaxFindByOrgId.action",
					"alertTextLoad" : "* 检查中, 请稍后...",
					"alertText" : "* 机构编码已经重复."
				},
				// 判断机构编码是否重复
				"ajaxFindByAppid" : {
					"file" : "ajaxFindByAppid.action",
					"alertTextLoad" : "* 检查中, 请稍后...",
					"alertText" : "* 应用编号已经重复."
				},
				"ajaxName" : {
					"file" : "validateUser.php",
					"alertText" : "* This name is already taken",
					"alertTextOk" : "* This name is available",
					"alertTextLoad" : "* Loading, please wait"
				},
				"complex" : {
					"regex" : "/^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{8,20}$/",
					"alertText" : "* 密码必须由8位以上数字、字母及特殊字符组成"
				},
				"identitys" : {
					"regex" : /(^\d{15}$)|(^\d{17}([0-9]|X)$)/,
					"alertText" : "* 请输入有效的身份证号码"
				},
				"orgNo" : {
					"regex" : "/^[a-zA-Z0-9]{8}-[a-zA-Z0-9]?$/",
					
					"alertText" : "组织机构代码共十位，第九位为-前八位与最后一位只能是数字或字母."
				},
				"numAndLet" : {
					"regex" : "/^[a-zA-Z0-9]+$/",
					"alertText" : "* 只能输入数字或字母."
				},
				"orgNo" : {
					"regex" : "/^[a-zA-Z0-9]{8}-[a-zA-Z0-9]?$/",
					
					"alertText" : "机构编码共十位，第九位为-前八位与最后一位只能是数字或字母."
				},
						"money" : {
							"regex" : "/^[0-9]{1,6}([.][0-9]{1,2})?$/",
							"alertText" : "* 能输入数字或小数,小数点前最多6位,小数点后最多两位.."
						}
				/*"money" : {
					"regex" : "/^[0-9]+(.[0-9]{1,2})?$/",
					"alertText" : "* 只能输入数字或小数,小数点后保留两位."
				}*/

			}
		}
	}
})(jQuery);

$(document).ready(function() {
	$.validationEngineLanguage.newLang();
	// alert("TTT:"+JSON.stringify( $.validationEngineLanguage.newLang() ));
});