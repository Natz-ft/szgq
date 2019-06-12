/**
 * 确认
 */
function reload_dialog() {
	location.reload();
	// window.location.href=window.location.href;
	// window.location.reload;
}

/**
 * 获取项目路径的方法
 */
function getRootPath() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}

/**
 * 弹出确认框和提示框
 * 
 * @param tiptop
 * @param p
 * @param cite
 */
function param(tiptop, p, cite) {
	$("#tipPage .tiptop span").html(tiptop);
	$(".tipinfo p").html(p);
	$(".tipinfo cite").html(cite);
	$("#zhezhao").show();
	$(".zDialogCon").css("overflow", "hidden");
}

/**
 * 新建提交方法
 * 
 * @param create_form_id
 * @param create_dialog_id
 */
function create_submit(create_form_id, create_dialog_id) {
	clearForm(create_form_id);
	var flag = $("#" + create_form_id).validationEngine({
		returnIsValid : true
	});
	// console.info("create_submit 返回值:" + flag);
	if (flag) {
		var form = document.getElementById(create_form_id);
		var list = form.getElementsByTagName("input");
		for (var i = 0; i < list.length; i++) {
			var boxValue = list[i].value;
			var nameVar = list[i].name;
			var errofalg='';
			if (nameVar == "id") {
				$.ajax({
					type : "post",
					url : "ajaxFindById.action",
					async : false,
					data : "validateValue=" + boxValue + "&validateId=" + "61"
							+ "&validateError=" + "ajaxFindByParentId",
					success : function(data) {
						data = JSON.parse(data);
						var result = data.jsonValidateReturn;
						errofalg = result[2];
					}
				})
				if (errofalg == 'false') {
					flag = false;
					param("提示信息", "菜单ID不能重复", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "parentId") {
				$.ajax({
					type : "post",
					url : "ajaxFindByParentId.action",
					async : false,
					data : "validateValue=" + boxValue + "&validateId=" + "61"
							+ "&validateError=" + "ajaxFindByParentId",
					success : function(data) {
						data = JSON.parse(data);
						var result = data.jsonValidateReturn;
						errofalg = result[2];
					}
				})
				if (errofalg == 'false') {
					flag = false;
					param("提示信息", "父菜单ID不存在", "");
					$("#t5").fadeIn(100);
					return;
				}
			}
		}
		if (flag) {
			$("#createBtn").attr("disabled", "disabled");
			$("#" + create_form_id).ajaxSubmit({
				dataType : 'json',
				success : function(data) {
					$("#" + create_dialog_id).hide();
					var flag = create_successprocess(data);
					if ("00000" == data.code) {
						param("提示信息", "新建成功", "");
						$("#t1").fadeIn(200);
					} else if ("00001" == data.code) {
						param("提示信息", "新建失败", "");
						$("#t1").fadeIn(200);
					} else if ("00002" == data.code) {
						param("提示信息", "用户已经存在", "");
						$("#t1").fadeIn(200);
					}
					$("#createBtn").attr("disabled", "");
				},
				error : function() {
					param("提示信息", "系统错误，请重试", "");
					$("#t1").fadeIn(200);
				}
			})
		}

	} else {
		var form = document.getElementById(create_form_id);
		var list = form.getElementsByTagName("input");
		for (var i = 0; i < list.length; i++) {
			var boxValue = list[i].value;
			var nameVar = list[i].name;
			if (nameVar == "username") {
				if (boxValue.length == 0) {
					param("提示信息", "用户账号不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else if (boxValue.length > 30) {
					param("提示信息", "用户账号长度必须在1 至 30 之间!", "");
					$("#t5").fadeIn(200);
					return;
				} else if ($('#org option:selected').val().length == 0) {
					param("提示信息", "请选择机构类别!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "nickname") {
				if (boxValue.length == 0) {
					param("提示信息", "用户姓名不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else if (boxValue.length > 30) {
					param("提示信息", "用户姓名长度必须在1 至 30 之间!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "telephone") {
				if (boxValue.length > 0) {
					var reg = /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][3758][0-9]{9}$)/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "请输入有效的联系电话", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			} else if (nameVar == "email") {
				if (boxValue.length > 0) {
					var reg = /^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "请输入有效的邮件地址", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			} else if (nameVar == "postCode") {
				if (boxValue.length > 0) {
					var reg = /^[1-9][0-9]{5}$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "请输入有效的邮政编码", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			} else if (nameVar == "address") {
				if (boxValue.length > 60) {
					param("提示信息", "通信地址长度必须在1 至 60 之间!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "id") {
				if (boxValue.length == 0) {
					param("提示信息", "菜单ID不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else if (boxValue.length > 0) {
					var reg = /^[0-9]+$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "菜单ID必须是数字", "");
						$("#t5").fadeIn(200);
						return;
					} else {
						var errofalg;
						$.ajax({
							type : "post",
							url : "ajaxFindById.action",
							async : false,
							data : "validateValue=" + boxValue + "&validateId="
									+ "61" + "&validateError="
									+ "ajaxFindByParentId",
							success : function(data) {
								data = JSON.parse(data);
								var result = data.jsonValidateReturn;
								errofalg = result[2];
							}
						})
						if (errofalg == 'false') {
							param("提示信息", "菜单ID不能重复", "");
							$("#t5").fadeIn(200);
							return;
						}
					}
				}
			} else if (nameVar == "name") {
				if (create_form_id == 'create_menuForm_id') {
					if (boxValue.length == 0) {
						param("提示信息", "菜单名称不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					}
				} else if (create_form_id == 'create_form_id') {
					if (boxValue.length == 0) {
						param("提示信息", "角色名称不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else if (boxValue.length > 30) {
						param("提示信息", "角色名称长度必须在1 至 30 之间!", "");
						$("#t5").fadeIn(200);
						return;
					}
				}else if(create_form_id=='create_dicForm_id'){
					if (boxValue.length == 0) {
						param("提示信息", "字典名称不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else if (boxValue.length > 30) {
						param("提示信息", "字典名称必须在1 至 30 之间!", "");
						$("#t5").fadeIn(200);
						return;
					}
				}else if(create_form_id=='create_contUsForm_id'){
					if (boxValue.length == 0) {
						param("提示信息", "金融办名称不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else if (boxValue.length > 30) {
						param("提示信息", "金融办名称必须在1 至 30 之间!", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			}else if (nameVar == "value") {
				if (boxValue.length == 0) {
					param("提示信息", "字典数值不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else if (boxValue.length > 30) {
					param("提示信息", "字典数值必须在1 至 30 之间!", "");
					$("#t5").fadeIn(200);
					return;
				}else if ($('#type option:selected').val().length == 0) {
					param("提示信息", "请选择字典类型!", "");
					$("#t5").fadeIn(200);
					return;
				}
			}else if (nameVar == "hotline") {
				if (boxValue.length > 0) {
					var reg = /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][3758][0-9]{9}$)/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "请输入有效的联系电话", "");
						$("#t5").fadeIn(200);
						return;
					}
				}else{
					param("提示信息", "联系电话不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "alias") {
				reg = /^[a-zA-Z]+$/
				var bool = reg.test(boxValue);
				if (!bool) {
					param("提示信息", "英文别名必须是英文字母", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "link") {
				if (boxValue.length == 0) {
					param("提示信息", "菜单地址不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "sort") {
				if (boxValue.length == 0) {
					param("提示信息", "菜单排序不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else {
					var reg = /^[0-9]+$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "菜单排序必须是数字", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			} else if (nameVar == "parentId") {
				if (boxValue.length == 0) {
					param("提示信息", "父菜单ID不能为空!", "");
					$("#t5").fadeIn(200);
					return false;
				} else if (boxValue.length > 0) {
					var reg = /^[0-9]+$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "父菜单ID必须是数字", "");
						$("#t5").fadeIn(200);
						return;
					} else {
						var errofalg;
						$.ajax({
							type : "post",
							url : "ajaxFindByParentId.action",
							async : false,
							data : "validateValue=" + boxValue + "&validateId="
									+ "61" + "&validateError="
									+ "ajaxFindByParentId",
							success : function(data) {
								data = JSON.parse(data);
								var result = data.jsonValidateReturn;
								errofalg = result[2];
							}
						})
						if (errofalg == 'false') {
							param("提示信息", "父菜单ID不存在", "");
							$("#t5").fadeIn(100);
							return;
						}
					}
				}

			} else if (nameVar == "description") {
				if (boxValue.length == 0) {
					param("提示信息", "角色描述不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else if (boxValue.length > 30) {
					param("提示信息", "角色描述长度必须在1 至 30 之间!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "configName") {
				if (boxValue.length == 0) {
					param("提示信息", "参数名称不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "configValue") {
				if (boxValue.length == 0) {
					param("提示信息", "参数数值不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "configDesc") {
				if (boxValue.length > 0) {
					if (boxValue.length > 500) {
						param("提示信息", "参数描述不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			} 
		}
	}
}
/**
 * 跳转修改页面 回现数据
 * 
 * @returns {Boolean}
 */
function update_button(update_url, ids, update_dialog_id) {

	var boxValue;
	var objArr = document.getElementsByName(ids);
	var checked_counts = 0;
	for (var i = 0; i < objArr.length; i++) {
		if (objArr[i].checked) {
			boxValue = objArr[i].value;
			checked_counts++;
		}
	}
	if (checked_counts == 0) {
		param("提示信息", "请选择信息项", "");
		$("#t5").fadeIn(200);
		return false;
	}
	if (checked_counts > 1) {
		param("提示信息", "请只选择一条信息", "");
		$("#t5").fadeIn(200);
		return false;
	} else {
		$("#" + update_dialog_id).show();
		$("#zhezhao").show();
		$(".zDialogCon").css("overflow", "hidden");
		var url = $("#path").val() + update_url + boxValue;
		$.ajax({
			type : 'post',
			url : url,
			success : function(data) {
				$("#update_page").html(data);
				$("#update_page").show();
			},
			error : function() {
				param("提示信息", "系统错误，请重试", "");
				$("#t1").fadeIn(200);
			}
		});
	}
}
/**
 * 跳转权限赋予页面 回现数据
 * 
 * @returns {Boolean}
 */
function empower_button(empower_url, ids, empower_dialog_id) {

	var boxValue;
	var objArr = document.getElementsByName(ids);
	var checked_counts = 0;
	for (var i = 0; i < objArr.length; i++) {
		if (objArr[i].checked) {
			boxValue = objArr[i].value;
			checked_counts++;
		}
	}
	if (checked_counts == 0) {
		param("提示信息", "请选择信息项", "");
		$("#t1").fadeIn(200);
		return false;
	}
	if (checked_counts > 1) {
		param("提示信息", "请只选择一条信息", "");
		$("#t1").fadeIn(200);
		return false;
	} else {
		$("#" + empower_dialog_id).show();
		$("#zhezhao").show();
		$(".zDialogCon").css("overflow", "hidden");
		var url = $("#path").val() + empower_url + boxValue;
		$.ajax({
			type : 'post',
			url : url,
			success : function(data) {
				$("#empower_page").html(data);
				$("#empower_page").show();
			},
			error : function() {
				param("提示信息", "系统错误，请重试", "");
				$("#t1").fadeIn(200);
			}
		});
	}
}

function updateDic() {
	param("提示信息", "请选择您要更新的目录", "每次只能选择一条，您选择了多条目录!");
	$("#t1").fadeIn(200);
}

/**
 * 新建
 * 
 * @param create_dialog_id
 */
function create_button(create_dialog_id) {
	clearForm('create_form_id');
	$("#zhezhao").show();
	$(".zDialogCon").css("overflow", "hidden");
	$("#" + create_dialog_id).show();
}

/**
 * 修改
 */
function update_submit(update_form, update_dialog_id) {

	var flag = $("#" + update_form).validationEngine({
		returnIsValid : true
	});
	if (flag) {
		var form = document.getElementById(update_form);
		var list = form.getElementsByTagName("input");
		for (var i = 0; i < list.length; i++) {
			var boxValue = list[i].value;
			var nameVar = list[i].name;
			var errofalg='';
			if (nameVar == "parentId") {
				$.ajax({
					type : "post",
					url : "ajaxFindByParentId.action",
					async : false,
					data : "validateValue=" + boxValue + "&validateId=" + "61"
							+ "&validateError=" + "ajaxFindByParentId",
					success : function(data) {
						data = JSON.parse(data);
						var result = data.jsonValidateReturn;
						errofalg = result[2];
					}
				})
				if (errofalg == 'false') {
					flag = false;
					param("提示信息", "父菜单ID不存在", "");
					$("#t5").fadeIn(100);
					return;
				}
			}
		}
		if(flag){
			// 防止重复提交
			$("#updateBtn").attr("disabled", "disabled");
			$("#" + update_form).ajaxSubmit({
				dataType : 'json',
				type : "post",
				success : function(data) {
					$("#" + update_dialog_id).hide();
					var flag = update_successprocess(data);
					if (flag) {
						param("提示信息", "修改成功", "");
						$("#t3").fadeIn(200);
					} else {
						param("提示信息", "修改失败", "");
						$("#t1").fadeIn(200);
					}
				},
				error : function() {
					param("提示信息", "系统错误，请重试", "");
					$("#t1").fadeIn(200);
				}
			})
		}
		
	}else {
			var form = document.getElementById(update_form);
			var list = form.getElementsByTagName("input");
			for (var i = 0; i < list.length; i++) {
				var boxValue = list[i].value;
				var nameVar = list[i].name;
				if (nameVar == "username") {
					if (boxValue.length == 0) {
						param("提示信息", "用户账号不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else if (boxValue.length > 30) {
						param("提示信息", "用户账号长度必须在1 至 30 之间!", "");
						$("#t5").fadeIn(200);
						return;
					} 
				} else if (nameVar == "nickname") {
					if (boxValue.length == 0) {
						param("提示信息", "用户姓名不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else if (boxValue.length > 30) {
						param("提示信息", "用户姓名长度必须在1 至 30 之间!", "");
						$("#t5").fadeIn(200);
						return;
					}else if ($('#org option:selected').val() == '') {
						param("提示信息", "请选择机构类别!", "");	
						$("#t5").fadeIn(200);
						return;
					}
				} else if (nameVar == "telephone") {
					if (boxValue.length > 0) {
						var reg = /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][3758][0-9]{9}$)/
						var bool = reg.test(boxValue);
						if (!bool) {
							param("提示信息", "请输入有效的联系电话", "");
							$("#t5").fadeIn(200);
							return;
						}
					}
				} else if (nameVar == "email") {
					if (boxValue.length > 0) {
						var reg = /^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/
						var bool = reg.test(boxValue);
						if (!bool) {
							param("提示信息", "请输入有效的邮件地址", "");
							$("#t5").fadeIn(200);
							return;
						}
					}
				} else if (nameVar == "postCode") {
					if (boxValue.length > 0) {
						var reg = /^[1-9][0-9]{5}$/
						var bool = reg.test(boxValue);
						if (!bool) {
							param("提示信息", "请输入有效的邮政编码", "");
							$("#t5").fadeIn(200);
							return;
						}
					}
				} else if (nameVar == "address") {
					if (boxValue.length > 60) {
						param("提示信息", "通信地址长度必须在1 至 60 之间!", "");
						$("#t5").fadeIn(200);
						return;
					}
				} else if (nameVar == "name") {
					if (update_form == 'menuUpdate_form_id') {
						if (boxValue.length == 0) {
							param("提示信息", "菜单名称不能为空!", "");
							$("#t5").fadeIn(200);
							return;
						}
					} else if (update_form == 'update_form_id') {
						if (boxValue.length == 0) {
							param("提示信息", "角色名称不能为空!", "");
							$("#t5").fadeIn(200);
							return;
						} else if (boxValue.length > 30) {
							param("提示信息", "角色名称长度必须在1 至 30 之间!", "");
							$("#t5").fadeIn(200);
							return;
						}
					}else if(update_form=='dicUpdate_form_id'){
						if (boxValue.length == 0) {
							param("提示信息", "字典名称不能为空!", "");
							$("#t5").fadeIn(200);
							return;
						} else if (boxValue.length > 10) {
							param("提示信息", "字典名称必须在1 至 10 之间!", "");
							$("#t5").fadeIn(200);
							return;
						}
					}else if(update_form=='conUpdate_form_id'){
						if (boxValue.length == 0) {
							param("提示信息", "金融办名称不能为空!", "");
							$("#t5").fadeIn(200);
							return;
						} else if (boxValue.length > 30) {
							param("提示信息", "金融办名称必须在1 至 30 之间!", "");
							$("#t5").fadeIn(200);
							return;
						}
					}
				}else if (nameVar == "value") {
					if (boxValue.length == 0) {
						param("提示信息", "字典数值不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else if (boxValue.length > 30) {
						param("提示信息", "字典数值必须在1 至 64 之间!", "");
						$("#t5").fadeIn(200);
						return;
					}else if ($('#type option:selected').val().length == 0) {
						param("提示信息", "请选择字典类型!", "");
						$("#t5").fadeIn(200);
						return;
					}
				}else if (nameVar == "hotline") {
					if (boxValue.length > 0) {
						var reg = /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][3758][0-9]{9}$)/
						var bool = reg.test(boxValue);
						if (!bool) {
							param("提示信息", "请输入有效的联系电话", "");
							$("#t5").fadeIn(200);
							return;
						}
					}else{
						param("提示信息", "联系电话不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					}
				} else if (nameVar == "alias") {
					reg = /^[a-zA-Z]+$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "英文别名必须是英文字母", "");
						$("#t5").fadeIn(200);
						return;
					}
				} else if (nameVar == "link") {
					if (boxValue.length == 0) {
						param("提示信息", "菜单地址不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					}
				} else if (nameVar == "sort") {
					if (boxValue.length == 0) {
						param("提示信息", "菜单排序不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else {
						var reg = /^[0-9]+$/
						var bool = reg.test(boxValue);
						if (!bool) {
							param("提示信息", "菜单排序必须是数字", "");
							$("#t5").fadeIn(200);
							return;
						}
					}
				} else if (nameVar == "parentId") {
					if (boxValue.length == 0) {
						param("提示信息", "父菜单ID不能为空!", "");
						$("#t5").fadeIn(200);
						return false;
					} else if (boxValue.length > 0) {
						var reg = /^[0-9]+$/
						var bool = reg.test(boxValue);
						if (!bool) {
							param("提示信息", "父菜单ID必须是数字", "");
							$("#t5").fadeIn(200);
							return;
						} else {
							var errofalg;
							$.ajax({
								type : "post",
								url : "ajaxFindByParentId.action",
								async : false,
								data : "validateValue=" + boxValue + "&validateId="
										+ "61" + "&validateError="
										+ "ajaxFindByParentId",
								success : function(data) {
									data = JSON.parse(data);
									var result = data.jsonValidateReturn;
									errofalg = result[2];
								}
							})
							if (errofalg == 'false') {
								param("提示信息", "父菜单ID不存在", "");
								$("#t5").fadeIn(100);
								return;
							}
						}
					}

				} else if (nameVar == "description") {
					if (boxValue.length == 0) {
						param("提示信息", "角色描述不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else if (boxValue.length > 30) {
						param("提示信息", "角色描述长度必须在1 至 30 之间!", "");
						$("#t5").fadeIn(200);
						return;
					}
				} else if (nameVar == "configName") {
					if (boxValue.length == 0) {
						param("提示信息", "参数名称不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					}
				} else if (nameVar == "configValue") {
					if (boxValue.length == 0) {
						param("提示信息", "参数数值不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					}
				} else if (nameVar == "configDesc") {
					if (boxValue.length > 0) {
						if (boxValue.length > 500) {
							param("提示信息", "参数描述不能为空!", "");
							$("#t5").fadeIn(200);
							return;
						}
					}
				} 
			}
		}
	}


/**
 * 修改之后确认刷新
 * 
 * @param list_form_id:分页列表FormId .
 */
function update_confirm(list_form_id) {
	var list = document.getElementsByName("ids");
	for (var i = 0; i < list.length; i++) {
		list[i].checked = "";
	}
	document.getElementById(list_form_id).submit();
}

/**
 * 删除
 * 
 * @param ids
 * @returns {Boolean}
 */
function delete_button(ids, del_url) {

	$("#delUrl").val(del_url);
	var objArr = document.getElementsByName(ids);
	var checked_counts = 0;
	for (var i = 0; i < objArr.length; i++) {
		if (objArr[i].checked) {
			checked_counts++;
		}
	}
	if (checked_counts == 0) {
		param("提示信息", "请选择要删除项", "");
		$("#t5").fadeIn(200);
		return false;
	} else {
		param("提示信息", "确认是否要删除?", "");
		$("#t2").fadeIn(200);
	}
}
/**
 * 日志删除
 * 
 * @param ids
 * @returns {Boolean}
 */
function delete_button_log(ids, del_url) {
	$("#delUrl").val(del_url);
	var objArr = document.getElementsByName(ids);
	var checked_counts = 0;
	for (var i = 0; i < objArr.length; i++) {
		if (objArr[i].checked) {
			checked_counts++;
		}
	}
	if (checked_counts == 0) {

		param("提示信息", "请选择要删除项", "");
		$("#t1").fadeIn(200);
		return false;
	} else {
		param("提示信息", "确认是否要删除?", "");
		$("#t5").fadeIn(200);
	}
}
/**
 * 确定删除方法
 * 
 * @param ids
 * @param del_url
 */
function delete_confirm_submit(ids, del_url) {

	var objArr = document.getElementsByName(ids);
	var boxValue;
	var checked_counts = 0;
	var arrayObj = new Array();
	for (var i = 0; i < objArr.length; i++) {
		if (objArr[i].checked) {
			arrayObj.push(objArr[i].value);
		}
	}
	var url = $("#path").val() + $("#delUrl").val() + arrayObj;
	$.ajax({
		type : 'post',
		dataType : 'json',
		url : url,
		success : function(data) {
			var flag = del_successprocess(data);
			if (flag) {
				param("提示信息", "删除成功", "");
				$("#t1").fadeIn(200);
			} else {
				param("提示信息", "删除失败", "");
				$("#t1").fadeIn(200);
			}
		},
		error : function() {
			param("提示信息", "系统错误，请重试", "");
			$("#t1").fadeIn(200);
		}
	});
}
/**
 * 确定删除日志方法
 * 
 * @param ids
 * @param del_url
 */
function delete_log_confirm_submit(ids, del_url) {
	$("#t5").hide();
	var objArr = document.getElementsByName(ids);
	var boxValue;
	var checked_counts = 0;
	var arrayObj = new Array();
	for (var i = 0; i < objArr.length; i++) {
		if (objArr[i].checked) {
			arrayObj.push(objArr[i].value);
		}
	}
	var url = $("#path").val() + $("#delUrl").val() + arrayObj;
	$.ajax({
		type : 'post',
		dataType : 'json',
		url : url,
		success : function(data) {
			var flag = del_successprocess(data);
			if (flag) {
				param("提示信息", "删除成功", "");
				$("#t1").fadeIn(200);
			} else {
				param("提示信息", "只能删除6个月之内的日志信息", "");
				$("#t1").fadeIn(200);
			}
		},
		error : function() {
			param("提示信息", "系统错误，请重试", "");
			$("#t1").fadeIn(200);
		}
	});
}

/**
 * 分页的刷新方法
 */
function refresh() {
	location.reload();
}

/**
 * 关闭提示框
 */
window.onload = function() {
	$(".click").click(function() {
		// //$("#t1").fadeIn(200);
	});
	$(".tiptop a").click(function() {
		$("#t1").fadeOut(100);
		$("#t2").fadeOut(100);
		$("#t4").fadeOut(100);
		$("#zhezhao").hide();
		$(".zDialogCon").css("overflow", "auto");
	});
	$("input[name='sure']").click(function() {
		$("#t1").fadeOut(100);
		$("#t2").fadeOut(100);
		$("#t4").fadeOut(100);
		$("#zhezhao").hide();
		$(".zDialogCon").css("overflow", "auto");
	});
	$("input[name='cancel']").click(function() {
		$("#t1").fadeOut(100);
		$("#t2").fadeOut(100);
		$("#t4").fadeOut(100);
		$("#zhezhao").hide();
		$(".zDialogCon").css("overflow", "auto");

	});
};

/**
 * 多选框全选或取消
 * 
 * @param checkbox
 */
function selectAll(checkbox, ids) {
	var list = document.getElementsByName(ids);
	for (var i = 0; i < list.length; i++) {
		list[i].checked = checkbox.checked;
	}
}

/**
 * 弹出页签框
 * 
 * @param query_conditions
 *            页签DIV的ID
 */
function tabs() {
	$("#tab_dialog_id").show();
	$("#step0").show();
	$("#zhezhao").hide();
	$(".zDialogCon").css("overflow", "auto");
}
/**
 * 弹出查询框
 * 
 * @param query_conditions
 */
function query_conditions(query_conditions) {

	$("#" + query_conditions).show();
//	clearForm('list_form_id');
	$("#zhezhao").show();
	$(".zDialogCon").css("overflow", "hidden");
}
/**
 * 清空新建Form控件
 */
function reset(formId) {
	$('#' + formId)[0].reset();
}
/**
 * 关闭对话框
 * 
 * @param dialog_id
 *            :关闭DIV的ID
 */
function close_dialog(dialog_id) {
	$("#" + dialog_id).hide();
	$("#zhezhao").hide();
	$(".zDialogCon").css("overflow", "auto");
}
function close_dialog1(dialog_id,form_id) {
	reset(form_id);
	$("#" + dialog_id).hide();
	$("#zhezhao").hide();
	$(".zDialogCon").css("overflow", "auto");
}
/**
 * 清空查询框Form控件
 * 
 * @param id
 */
function clearForm(id) {
	var objId = document.getElementById(id);
	if (objId == undefined) {
		return;
	}
	for (var i = 0; i < objId.elements.length; i++) {
		var search = objId.elements[i].name;
		// 用来判断是否是对话框的控件.
		if (search.indexOf("search") >= 0) {
			if (objId.elements[i].type == "text") {
				objId.elements[i].value = "";
			} else if (objId.elements[i].type == "password") {
				objId.elements[i].value = "";
			} else if (objId.elements[i].type == "radio") {
				objId.elements[i].checked = false;
			} else if (objId.elements[i].type == "checkbox") {
				objId.elements[i].checked = false;
			} else if (objId.elements[i].type == "select-one") {
				objId.elements[i].options[0].selected = true;
			} else if (objId.elements[i].type == "select-multiple") {
				for (var j = 0; j < objId.elements[i].options.length; j++) {
					objId.elements[i].options[j].selected = false;
				}
			} else if (objId.elements[i].type == "textarea") {
				objId.elements[i].value = "";
			}
		}

	}
}

// 页签
// 改变jquery符号
/* var $F=jQuery.noConflict();$("#"+tabs_submit).val() */
// 全局变量，用于指定页面是否可以直接查看
/**
 * 页签 的提交
 * 
 * @param tabs_submit
 */
/**
 * step1 :控制从第一步到第二步是否可以切换 step2 :控制从第一步到第三步是否可以切换
 */
var step1 = false;
var step2 = false;
$(function() {

	jQuery("#tabs_form0").validationEngine('attach', {
		relative : true,
		overflownDIV : "#divPostion",
		promptPosition : "bottomLeft"
	// 验证弹出框的位置，topRight,topLeft,bottomRight,bottomLeft,centerRight,centerLeft,inline
	});

	$("#submit0").click(
			function() {
				if (true) {
					step1 = true;
					$("ul li").eq(1).addClass("act").siblings().removeClass(
							"act");
					$(".tabs>div").eq(1).stop(true).show().siblings()
							.stop(true).hide();
					$(".modal-footer> div").eq(1).stop(true).show().siblings()
							.stop(true).hide();
				}

			})

	$("#submit1").click(
			function() {
				step2 = true;
				$("ul li").eq(2).addClass("act").siblings().removeClass("act");
				$(".tabs>div").eq(2).stop(true).show().siblings().stop(true)
						.hide();
				$(".modal-footer> div").eq(2).stop(true).show().siblings()
						.stop(true).hide();
			})
	$("ul li").eq(0).addClass("act").siblings().removeClass("act");
	$(".tabs>div").eq(0).stop(true).show().siblings().stop(true).hide();
	$("ul li").each(
			function() {
				var index = $(this).index();
				$("ul li").eq(0).addClass("act");
				$("ul li").eq(0).addClass("act").siblings().removeClass("act");
				$(".tabs>div").eq(0).stop(true).show().siblings().stop(true)
						.hide();
				$(this).click(
						function() {
							if (step1 == false && step2 == false) {
								$("ul li").eq(0).addClass("act").siblings()
										.removeClass("act");
								$(".tabs>div").eq(0).stop(true).show()
										.siblings().stop(true).hide();
								if (step1 == true && step2 == false) {
									if (index == 0 || index == 1) {
										$("ul li").eq(index).addClass("act")
												.siblings().removeClass("act");
										$(".tabs>div").eq(index).stop(true)
												.show().siblings().stop(true)
												.hide();
									}
								}
								if (step1 == true && step2 == true) {
									$("ul li").eq(index).addClass("act")
											.siblings().removeClass("act");
									$(".tabs>div").eq(index).stop(true).show()
											.siblings().stop(true).hide();
								}
							}
						})
			})

})

/**
 * 查询框提交Form
 * 
 * @param form
 */
function querySubmit(form) {
	$("#curPage").val("1");
	document.getElementById(form).submit();
}

/**
 * 权限赋予
 */
function empower() {
	var list = document.getElementsByName("id");
	var count = 0;
	var id;
	for (var i = 0; i < list.length; i++) {
		if (list[i].checked) {
			id = list[i].value;
			count++;
		}
	}
	// 只有选择一条是猜弹出更新页面，否则弹出提示消息，只能选择一条
	if (count == 1) {
		window
				.open(
						"empower?id=" + id,
						"",
						"height=600, width=800, top=200,left=300,alwaysRaised=yes,titlebar=no, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no");
		return;
	} else if (count == 0) {
		param("提示信息", "请选择您要赋予权限的角色", "每次只能选择一条,您未选择!");
		$("#t1").fadeIn(200);
	} else {
		param("提示信息", "请选择您要赋予权限的角色", "每次只能选择一条，您选择了多条目录!");
		$("#t1").fadeIn(200);
	}
}
function showDetail(id) {
	openWindow600("detail?id=" + id);
}

/**
 * 实现页面跳转方法
 */
function jumpPage(url, positionId) {
	$.ajax({
		type : 'post',
		url : url,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("X-Custom-Header1", "Bar");
		},
		success : function(data) {
			$("#" + positionId).html(data);
		},
		error : function() {
			param("提示信息", "系统错误，请重试", "");
			$("#t1").fadeIn(200);
		}
	});
}
/**
 * 新建提交方法
 * 
 * @param create_form_id
 * @param create_dialog_id
 */
function createInvest_submit(create_form_id, create_dialog_id,pass) {
	clearForm(create_form_id);
	var flag = $("#" + create_form_id).validationEngine({
		returnIsValid : true
	});
	// console.info("create_submit 返回值:" + flag);
	if (flag) {
		var form = document.getElementById(create_form_id);
		var list = form.getElementsByTagName("input");
		for (var i = 0; i < list.length; i++) {
			var boxValue = list[i].value;
			var nameVar = list[i].name;
			var errofalg='';
			if (nameVar == "id") {
				$.ajax({
					type : "post",
					url : "ajaxFindById.action",
					async : false,
					data : "validateValue=" + boxValue + "&validateId=" + "61"
							+ "&validateError=" + "ajaxFindByParentId",
					success : function(data) {
						data = JSON.parse(data);
						var result = data.jsonValidateReturn;
						errofalg = result[2];
					}
				})
				if (errofalg == 'false') {
					flag = false;
					param("提示信息", "菜单ID不能重复", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "parentId") {
				$.ajax({
					type : "post",
					url : "ajaxFindByParentId.action",
					async : false,
					data : "validateValue=" + boxValue + "&validateId=" + "61"
							+ "&validateError=" + "ajaxFindByParentId",
					success : function(data) {
						data = JSON.parse(data);
						var result = data.jsonValidateReturn;
						errofalg = result[2];
					}
				})
				if (errofalg == 'false') {
					flag = false;
					param("提示信息", "父菜单ID不存在", "");
					$("#t5").fadeIn(100);
					return;
				}
			}
		}
		if (flag) {
			$("#createBtn").attr("disabled", "disabled");
			$("#" + create_form_id).ajaxSubmit({
				dataType : 'json',
				success : function(data) {
					$("#" + create_dialog_id).hide();
					var flag = create_successprocess(data);
					
					if ("00000" == data.code) {
						param("提示信息", "新建用户成功;"+"<p>"+"初始化密码为:"+pass+"", "");
						$("#t1").fadeIn(200);
					} else if ("00001" == data.code) {
						param("提示信息", "新建失败", "");
						$("#t1").fadeIn(200);
					} else if ("00002" == data.code) {
						param("提示信息", "用户已经存在", "");
						$("#t1").fadeIn(200);
					}
					$("#createBtn").attr("disabled", "");
				},
				error : function() {
					param("提示信息", "系统错误，请重试", "");
					$("#t1").fadeIn(200);
				}
			})
		}

	} else {
		var form = document.getElementById(create_form_id);
		var list = form.getElementsByTagName("input");
		for (var i = 0; i < list.length; i++) {
			var boxValue = list[i].value;
			var nameVar = list[i].name;
			if (nameVar == "username") {
				if (boxValue.length == 0) {
					param("提示信息", "用户账号不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else if (boxValue.length > 30) {
					param("提示信息", "用户账号长度必须在1 至 30 之间!", "");
					$("#t5").fadeIn(200);
					return;
				} else if ($('#org option:selected').val().length == 0) {
					param("提示信息", "请选择机构类别!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "nickname") {
				if (boxValue.length == 0) {
					param("提示信息", "用户姓名不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else if (boxValue.length > 30) {
					param("提示信息", "用户姓名长度必须在1 至 30 之间!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "telephone") {
				if (boxValue.length > 0) {
					var reg = /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][3758][0-9]{9}$)/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "请输入有效的联系电话", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			} else if (nameVar == "email") {
				if (boxValue.length > 0) {
					var reg = /^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "请输入有效的邮件地址", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			} else if (nameVar == "postCode") {
				if (boxValue.length > 0) {
					var reg = /^[1-9][0-9]{5}$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "请输入有效的邮政编码", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			} else if (nameVar == "address") {
				if (boxValue.length > 60) {
					param("提示信息", "通信地址长度必须在1 至 60 之间!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "id") {
				if (boxValue.length == 0) {
					param("提示信息", "菜单ID不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else if (boxValue.length > 0) {
					var reg = /^[0-9]+$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "菜单ID必须是数字", "");
						$("#t5").fadeIn(200);
						return;
					} else {
						var errofalg;
						$.ajax({
							type : "post",
							url : "ajaxFindById.action",
							async : false,
							data : "validateValue=" + boxValue + "&validateId="
									+ "61" + "&validateError="
									+ "ajaxFindByParentId",
							success : function(data) {
								data = JSON.parse(data);
								var result = data.jsonValidateReturn;
								errofalg = result[2];
							}
						})
						if (errofalg == 'false') {
							param("提示信息", "菜单ID不能重复", "");
							$("#t5").fadeIn(200);
							return;
						}
					}
				}
			} else if (nameVar == "name") {
				if (create_form_id == 'create_menuForm_id') {
					if (boxValue.length == 0) {
						param("提示信息", "菜单名称不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					}
				} else if (create_form_id == 'create_form_id') {
					if (boxValue.length == 0) {
						param("提示信息", "角色名称不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else if (boxValue.length > 30) {
						param("提示信息", "角色名称长度必须在1 至 30 之间!", "");
						$("#t5").fadeIn(200);
						return;
					}
				}else if(create_form_id=='create_dicForm_id'){
					if (boxValue.length == 0) {
						param("提示信息", "字典数值不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else if (boxValue.length > 30) {
						param("提示信息", "字典数值必须在1 至 30 之间!", "");
						$("#t5").fadeIn(200);
						return;
					}
				}else if(create_form_id=='create_contUsForm_id'){
					if (boxValue.length == 0) {
						param("提示信息", "金融办名称不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					} else if (boxValue.length > 30) {
						param("提示信息", "金融办名称必须在1 至 30 之间!", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			}else if (nameVar == "value") {
				if (boxValue.length == 0) {
					param("提示信息", "字典名称不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else if (boxValue.length > 30) {
					param("提示信息", "字典名称必须在1 至 30 之间!", "");
					$("#t5").fadeIn(200);
					return;
				}else if ($('#type option:selected').val().length == 0) {
					param("提示信息", "请选择字典类型!", "");
					$("#t5").fadeIn(200);
					return;
				}
			}else if (nameVar == "hotline") {
				if (boxValue.length > 0) {
					var reg = /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][3758][0-9]{9}$)/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "请输入有效的联系电话", "");
						$("#t5").fadeIn(200);
						return;
					}
				}else{
					param("提示信息", "联系电话不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "alias") {
				reg = /^[a-zA-Z]+$/
				var bool = reg.test(boxValue);
				if (!bool) {
					param("提示信息", "英文别名必须是英文字母", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "link") {
				if (boxValue.length == 0) {
					param("提示信息", "菜单地址不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "sort") {
				if (boxValue.length == 0) {
					param("提示信息", "菜单排序不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else {
					var reg = /^[0-9]+$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "菜单排序必须是数字", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			} else if (nameVar == "parentId") {
				if (boxValue.length == 0) {
					param("提示信息", "父菜单ID不能为空!", "");
					$("#t5").fadeIn(200);
					return false;
				} else if (boxValue.length > 0) {
					var reg = /^[0-9]+$/
					var bool = reg.test(boxValue);
					if (!bool) {
						param("提示信息", "父菜单ID必须是数字", "");
						$("#t5").fadeIn(200);
						return;
					} else {
						var errofalg;
						$.ajax({
							type : "post",
							url : "ajaxFindByParentId.action",
							async : false,
							data : "validateValue=" + boxValue + "&validateId="
									+ "61" + "&validateError="
									+ "ajaxFindByParentId",
							success : function(data) {
								data = JSON.parse(data);
								var result = data.jsonValidateReturn;
								errofalg = result[2];
							}
						})
						if (errofalg == 'false') {
							param("提示信息", "父菜单ID不存在", "");
							$("#t5").fadeIn(100);
							return;
						}
					}
				}

			} else if (nameVar == "description") {
				if (boxValue.length == 0) {
					param("提示信息", "角色描述不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				} else if (boxValue.length > 30) {
					param("提示信息", "角色描述长度必须在1 至 30 之间!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "configName") {
				if (boxValue.length == 0) {
					param("提示信息", "参数名称不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "configValue") {
				if (boxValue.length == 0) {
					param("提示信息", "参数数值不能为空!", "");
					$("#t5").fadeIn(200);
					return;
				}
			} else if (nameVar == "configDesc") {
				if (boxValue.length > 0) {
					if (boxValue.length > 500) {
						param("提示信息", "参数描述不能为空!", "");
						$("#t5").fadeIn(200);
						return;
					}
				}
			} 
		}
	}
}