<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/autoload.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/script/layui/css/layui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/script/layui/css/layui.cu.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/reset.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css" type="text/css" />
<style type="text/css">
.layui-input {
        width: 240px;
        height: 28px;
        border: 1px solid #8DBDDC;
        border-radius: 5px;
 }

.buttonWrap {
        width: 100%;
        padding: 15px 0;
        margin-left: 0px;
        text-align: center;
        margin-top: 10px;
 }
.star {
    color: #e4398c;
}
.boxTitle {
        margin-top: 10px;
}

.boxTitle span {
        font-size: 16px;
        color: #0D4F92;
        padding: 5px 10px;
        border-bottom: 2px solid #0D4F92;
}

.boxWrap .formWrap {
        width: 800px;
        margin: 0 auto;
        overflow: hidden;
        margin-top:20px;
        margin-left: 10px;
}

.boxWrap .formWrap .form-left {
        float: left;
}

.boxWrap .formWrap .form-right {
        float: left;
}

.boxWrap .formWrap .form-title {
        width: 20%;
        height: 100%;
}

.boxWrap .formWrap .form-title span {
        font-size: 16px;
        padding: 5px 10px;
        color: #0D4F92;
        border-bottom: 3px solid #0D4F92;
}
</style>

 </head>

<body >
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="boxWrap">
    <p class="boxTitle"><span>编辑信息</span></p>
    <div class="formWrap">
     <form class="layui-form"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
            <!-- 左右两列左侧容器 -->
            <div class="form-left divInput-left">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="star">*</span>金融办名称：</label>
                    <div class="layui-input-block">
                         <input type="hidden"  name="id"  value='${contactus.id}' autocomplete="off" class="layui-input">
                        <input type="text" name="name" lay-verify="required" value='${contactus.name}' autocomplete="off" class="layui-input" maxlength="20">

                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="star">*</span>地址：</label>
                    <div class="layui-input-block">
                        <input type="text" name="adress" lay-verify="required"  value='${contactus.adress }' autocomplete="off" class="layui-input" maxlength="100">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="star" >*</span>邮箱：</label>
                    <div class="layui-input-block">
                        <input type="text" name="mail" lay-verify = "email" value='${contactus.mail}' autocomplete="off" class="layui-input">
                    </div>
                </div>
             </div>
            <!-- 左右两列右侧容器 -->
            <div class="form-right divInput-right">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="star">*</span>邮编：</label>
                    <div class="layui-input-block">
                        <input type="text" name="zipCode" lay-verify = "zipCode" value='${contactus.zipCode }' autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="star">*</span>热线：</label>
                    <div class="layui-input-block">
                        <input type="text" name="hotline" lay-verify="required|hotline" value='${contactus.hotline}' autocomplete="off" class="layui-input">
                    </div>
                </div>
                 <div class="layui-form-item">
                    <label class="layui-form-label"><span class="star">*</span>传真：</label>
                    <div class="layui-input-block">
                        <input type="text" lay-verify="required"  name="fax" value='${contactus.fax}' autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block buttonWrap">
                    <button class="layui-btn submitBtn" lay-submit lay-filter="editContactus">&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;</button>
                    <button type="button" class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
                </div>
            </div>
         </form>
    </div>
</div>
</div>
 <script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript"></script>

 <script>
 $(function () {
     //调用富文本编辑器控件
     // 表单控件
     var form = layui.form;
     //自定义验证规则
     form.verify({
    	 zipCode: function (value, item) { //value：表单的值、item：表单的DOM对象
    		 var re= /^[1-9][0-9]{5}$/;
             if (!re.test(value)) {
                 return '邮编格式不正确';
             }else{
            	return false; 
             }
         },
         //hotline: function (value, item) { //value：表单的值、item：表单的DOM对象
    	 //var rehotline=/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][3758][0-9]{9}$)/
         //if (!rehotline.test(value)) {
             //return '电话格式不正确';
         //}else{
        	//return false; 
         //}
     }

     });
     //监听提交
     form.on('submit(editContactus)', function (data) {
         var data = JSON.stringify(data.field);
         $.ajax({
             type: "post",
             async: false,
             url: "/creditplatformweb/contactus/update",
             data: {"contactus" : data},// 你的formid
             success: function (data) {
                 data = JSON.parse(data);
                 var flag = true;
                 if (flag) {
                     layer.alert('提交成功', function () {
                    	 parent.location.reload();
                     });
                 } else {
                     layer.alert('提交失败', {
                         title: '最终的提交信息'
                     })
                 }
             },error:function(data){
             }
         });
         return false;
     });

   //增加"关闭"按钮事件
     $(function () {
         //点击进行关闭操作
         $(".closeBtn").on("click", function () {
             var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
             parent.layer.close(index); //关闭layer(关闭当前窗口)
         });
     });
 })

</script>
</body>
