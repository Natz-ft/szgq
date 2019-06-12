/**
 * 用户管理中用到的js
 */
/*
userList界面
*/
//点击列表中的用户名，可展示该用户详细信息，并可进行修改
function showInfo(id){
	var url = $("#path").val()+"/admin/user/detail?id="+id;
	$.ajax({
			type:'post',
			url:url,
			success:function(data) {
				$("#update_page").html(data);
				$("#update_page").show();
			},error: function(){  
				param("提示信息","系统错误，请重试","");
			$("#t1").fadeIn(200);  
        }
		});
}


function stopUser(id){
		var url=$("#path").val()+"/admin/user/stopUser?id="+id;
		$.ajax({
			type:'post',
			url:url,
			success:function(data) {
				param("提示信息","停用用户成功","");
				$("#t1").fadeIn(200);
			},error: function(){  
				param("提示信息","停用用户失败","");
				$("#t1").fadeIn(200);  
	    }
		});
}
function startUser(id){
	var url=$("#path").val()+"/admin/user/startUser?id="+id;
	$.ajax({
		type:'post',
		url:url,
		success:function(data) {
			param("提示信息","启用用户成功","");
			$("#t1").fadeIn(200);
		},error: function(){  
			param("提示信息","启用用户失败","");
			$("#t1").fadeIn(200);  
    }
	});
}
$(function(){
	$(".stop1").each(function(){
		if($(this).val()==1){
			$(this).val("启用");
			$(this).css("background","url(${ctx}/static/image/user/start.png) no-repeat left center");
		}else{
			$(this).val("停用");
			$(this).css("background","url(${ctx}/static/image/user/stop.png) no-repeat left center");
		}
	})
	$(".lock1").each(function(){
		if($(this).val()==2){
			$(this).val("锁定");
			$(this).css("background","url(${ctx}/static/image/user/lock.png) no-repeat left center");
		}else if($(this).val()==0){
			$(this).val("未锁");
			$(this).css("background","url(${ctx}/static/image/user/unlock.png) no-repeat left center");
		}
	})
}); 


function resetPassword(id){
	var url = $("#path").val()+"/admin/user/resetPassword?id="+id;
	$.ajax({
		type:'post',
		url:url,
		success:function(data) {
			param("提示信息","重置密码成功","");
			$("#t1").fadeIn(200);
		},error: function(){  
			param("提示信息","重置密码失败","");
			$("#t1").fadeIn(200);  
    }
	});
}
function unlockUser(id,obj){
	//alert($(obj).val());
	if($(obj).val()=="解锁"){
		var url=$("#path").val()+"/admin/user/unlockUser?id="+id;
		$.ajax({
			type:'post',
			url:url,
			success:function(data) {
				param("提示信息","用户已解锁","");
				$("#t1").fadeIn(200);
			},error: function(){  
				param("提示信息","解锁用户失败","");
				$("#t1").fadeIn(200);  
	    }
		});
	}else{
	}
	
}
$(function(){
	$(".lock1").each(function(){
		$(this).mouseover(function(){
			if($(this).val()=="锁定"){
				$(this).val("解锁");
			}
		});
		$(this).mouseout(function(){
			if($(this).val()=="解锁"){
				$(this).val("锁定");
			}
		});
	});
});

function grantRole(id){
	var url = $("#path").val()+"/admin/user/searchRole?id="+id;
	$("#grant_dialog_id").show();
	$("#zhezhao").show();
	$(".zDialogCon").css("overflow","hidden");
	$.ajax({
			type:'post',
			url:url,
			success:function(data) {
				$("#grant_role").html(data);
				$("#grant_role").show();
			},error: function(){  
				param("提示信息","系统错误，请重试","");
				$("#t1").fadeIn(200);  
        	}
	});
}
function create_button(create_dialog_id) {
	
	var url = $("#path").val()+'/admin/user/createPage';
	$.ajax({
		type:'post',
		data:'org=org',
		dataType : 'json',
		url:url,
		success:function(data){
			var obj=$("#org");
			$.each(data,function(key,value){
				obj.append("<option value='"+value+"'>"+key+"</option>"); 
			})
			$("#"+create_dialog_id).show();
			$("#zhezhao").show();
			$(".zDialogCon").css("overflow","hidden");
		},error:function(){
			alert("系统错误");
		}
	})
	
}
/*
stop_reason界面
*/
function stop_button(obj,stop_reason_id,id) {
	if($(obj).val()=="启用"){
		startUser(id);
	}else{
		$("#"+stop_reason_id).show();
		$("#stop_id").val(id);
		$("#zhezhao").show();
		$(".zDialogCon").css("overflow","hidden");
	}
}
function stop_successprocess(data){
	if(data.code="00000"){
		return true;
	}else{
		return false;
	}
}
function stop_submit(stop_form_id,stop_reason_id) {
	 if ($("#"+stop_form_id).validationEngine({returnIsValid:true})){  
		 $("#"+stop_form_id).ajaxSubmit({
			 dataType : 'json',
            success:function(data){
           	 $("#"+stop_reason_id).hide();
                var flag = stop_successprocess(data);
                if (flag) {
                	param("提示信息","停用成功","");
					$("#t1").fadeIn(200);
				 }else {
					param("提示信息","停用失败","");
					$("#t1").fadeIn(200);
				}
            },error: function(){
                param("提示信息","系统错误，请重试","");
					$("#t1").fadeIn(200);
            }
        })
	 }
}
/*
grant_role界面
*/
//添加一个或者多个
$(function(){
	$("#add").click(function(){
		$("#select1 option:selected").each(function(){
			$("#select2").append("<option value="+$(this).val()+">"+$(this).html()+"</option>");
			$(this).remove();
		})
	})
})
//全部添加
$(function(){
	$("#addall").click(function(){
		$("#select1 option").each(function(){
			$("#select2").append("<option value="+$(this).val()+">"+$(this).html()+"</option>");
			$(this).remove();
		})
	})
})
//删除一个或者多个
$(function(){
	$("#delete").click(function(){
		$("#select2 option:selected").each(function(){
			$("#select1").append("<option value="+$(this).val()+">"+$(this).html()+"</option>");
			$(this).remove();
		})
	})
})
//全部删除
$(function(){
	$("#delall").click(function(){
		$("#select2 option").each(function(){
			$("#select1").append("<option value="+$(this).val()+">"+$(this).html()+"</option>");
			$(this).remove();
		})
	})
})


function grant_submit(grant_form_id,grant_dialog_id){
	$("#select2 option").each(function(){
		$(this).attr("selected", true);;
	})
	$("#"+grant_form_id).ajaxSubmit({
		 dataType : 'json',
        success:function(data){
       	 	$("#"+grant_dialog_id).hide();
            var flag = grant_successprocess(data);
            if (flag) {
            	param("提示信息","关联角色成功","");
				$("#t1").fadeIn(200);
			 }else {
				param("提示信息","关联角色失败","");
				$("#t1").fadeIn(200);
			}
        },error: function(){
            param("提示信息","系统错误，请重试","");
				$("#t1").fadeIn(200);
        }
    })
}