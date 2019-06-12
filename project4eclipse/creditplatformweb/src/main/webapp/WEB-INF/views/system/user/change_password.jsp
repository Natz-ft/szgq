<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/static/js/md5.js" type="text/javascript"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/autoload.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/script/layui/css/layui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/script/layui/css/layui.cu.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/style2.css" type="text/css" />
<link href="${ctx}/static/css/style-alert.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/popup.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/style-dialog.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/static/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/css/formatTable.css" rel="stylesheet" type="text/css" />
<style type="text/css">

#change_dialog_id table,#change_dialog_id table th,#change_dialog_id table td{
	margin:0;
	padding:0;
	border:none;
}
#change_dialog_id table p{
	font-size: 18px;
    color: #0D4F92;
    padding: 5px 10px;
    border-bottom: 2px solid #0D4F92;
    width:110px;
}
#change_dialog_id input{
/* 	width:60%; */
/* 	height: 35px; */
	 width: 280px;
    height: 28px;
    border: 1px solid #8DBDDC;
    border-radius: 3px;
    margin-top: 4px;
}
#change_dialog_id .thStyle{
 	width:20%; 
 	height: 35px; 
 	font-size:14px;
/* 	width: 80px; */
/*  padding: 6px 0px; */
}
.layui-btn{
width:97px;}
</style>
</head>
<body>
<div class="d1" id="change_dialog_id"  style="background-color: #EAF3F8;width:100%;height: 100%">
	<div class="d2" style="padding: 0px;width:100%;height: 100%;">
		
		<form class="layui-form" id="change_form_id">
		
			<table  class="grayTable" id="ss1" style="background-color: #EAF3F8;width:100%;height:350px;margin:0px auto;" >
				<tr ><th align=left colspan="2" ><p>修改用户密码</p></th></tr>
				<tr>
					<th align="right" class="thStyle"><span class="star" style="color: #e4398c;text-align:center;">*</span>原密码:&nbsp;&nbsp;&nbsp;&nbsp;</th>
					<td>
						<input id="oldpassword" type="password" name="password" lay-verify="required|oldpassword|oldPwd" autocomplete="off"/>
					</td>
				</tr>
				<tr>
					<th align="right" class="thStyle" ><span class="star" style="color: #e4398c; text-align:center;">*</span>新密码:&nbsp;&nbsp;&nbsp;&nbsp;</th>
					<td>
						<input id="newpassword" type="password" name="newpassword" lay-verify="required|newpassword|newPassword" autocomplete="off"/>
					</td>
				</tr>
				<tr>
					<th align="right" class="thStyle" ><span class="star" style="color: #e4398c; text-align:center;">*</span>确认密码:&nbsp;&nbsp;&nbsp;&nbsp;</th>
					<td>
						<input id="confirm" type="password" name="new" lay-verify="required|confirm|confirmPwd" autocomplete="off"/>
					</td>
				</tr>
				<tr>
					<td colspan="2"  style="padding-left:20%">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="layui-btn" id="uploader_ok" lay-submit lay-filter="editContactus">&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;定&nbsp;&nbsp;</button>
						<button type="button" class="layui-btn" id="uploader_back"  onClick="javascript :history.back(-1);">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
					</td>
				</tr>
			</table>
		</form> 
	</div>
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
<script type="text/javascript">
$(function () { 
	var form = layui.form;
    //自定义验证规则
    form.verify({
   	     oldpassword: [/(.+){6,12}$/, '密码必须6到12位']
        ,newpassword: [/(.+){6,12}$/, '密码必须6到12位']
        ,confirm: [/(.+){6,12}$/, '密码必须6到12位'] 
         ,newPassword:  function(value, item){
        	 var oldpassword=$("#oldpassword").val();
        	  if(oldpassword == value){
                  return "与原密码相同，请重新输入！";
              }else{
              	return false; 
              }
         }
        ,confirmPwd : function(value, item){
        	var pass=$("#newpassword").val();
        if(pass!= value){
            return "两次输入密码不一致，请重新输入！";
        }else{
        	return false; 
        }
      }
      ,oldPwd : function(value, item){
    	var msg = '';
  		var prekey1=$("#oldpassword").val();
  		var prekey2=$("#newpassword").val();
     	var hex1=hex_md5(prekey1);
     	var hex2=hex_md5(prekey2);
          $.ajax({
              type: "post",
              async: false,
              url: "/creditplatformweb/admin/user/getAjaxPassWord",
              data: {password: hex1,newpassword:hex2},// 你的formid
              success: function (data) {
                  data = JSON.parse(data);
                  msg=data.code;
              }
          });
          if(msg=='00002'){
        	  return "旧密码输入错误，请重新输入！";
           }else if(msg=='00003'){
        	  return "与最近三次内历史密码相同!";
           }else{
        	   return false; 
           }
      }
    });
    form.on('submit(editContactus)', function (data) {
        var data = JSON.stringify(data.field);
        MD5pwd('oldpassword');
		MD5pwd('newpassword');
		MD5pwd('confirm');
        $.ajax({
            type: "post",
            async: false,
            url: "/creditplatformweb/admin/user/finalPassword",
            data: $('#change_form_id').serialize(),// 你的formid
            success: function (data) {
                data = JSON.parse(data);
                var flag = change_successprocess(data);
                if (flag) {
                    layer.alert('修改成功', function () {
                   	 parent.location.reload();
                    });
                }
            },error:function(data){
            }
        });
        return false;
    });
  //MD5对密码加密传输
    function MD5pwd(password){
    	var prekey=$("#"+password).val();
    	var hex=hex_md5(prekey);
    	$("#"+password).val(hex);
    }
    
    function change_successprocess(data){
    	if(data.code=="00000"){
    		return true;
    	}else{
    		return false;
    	}
    	
    }
})

</script>
</body>
