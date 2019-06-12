<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/autoload.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/script/layui/css/layui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/script/layui/css/layui.cu.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/reset.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/form.css" type="text/css" />
<style type="text/css">
.layui-upload-img {
    position: relative;
    width: 100px;
    height: 60px;
    left: 30px;
    top: 30PX;
}
.img {
    display: inline-block;
    border: none;
}
.inputTitle{
margin-top:2px;
}
.Operatebutton{
margin-top:200px;
}
.uploadPicDiv {
margin-left:20px;
/* margin-top:200px;
 */}
.uploadPicButton {
position: relative;
margin-top:120px;
right:10px;
}

 .layui-input {
            width: 400px;
            height:30px;
            line-height:30px;
            border: 1px solid #8DBDDC;
            border-radius: 5px;
        } 
.buttonWrap{
            width: 100%;
            padding: 15px 0;
            margin-left: 40px;
            text-align: center;
            margin-top:  -190px;
        }     
.uploadButton{
margin-top: 10px;
margin-left: 10px;
}
.uploadButton-img{
margin-top: -20px;
margin-left: -25px;
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
</style>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript"></script>

 <script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
 <script>
 $(function () {
     //调用富文本编辑器控件
     // 表单控件
     var form = layui.form;
     var layedit = layui.layedit;
     var dataJson = {};
     var filepath;
   //点击a标签时触发选中事件(此事件放在字典值赋值之后)
     $(".termsList ul li a").on("click", function () {
         var dicType = $(this).parents(".termsList").attr("id");
         dataJson[dicType] = $(this).attr("value");
     })
     var index = layedit.build('demandDetails',{
    	 tool: [
    		  'strong' //加粗
    		  ,'italic' //斜体
    		  ,'underline' //下划线
    		  ,'del' //删除线
    		  ,'|' //分割线
    		  ,'left' //左对齐
    		  ,'center' //居中对齐
    		  ,'right' //右对齐
    		  ,'link' //超链接地址
    		  ,'unlink' //清除链接地址
    		  ,'face' //表情
    		  /* ,'image' //插入图片
    		  ,'help' //帮助 */
    		]
    	 ,height: 176
     }); //建立编辑器
     // 调用上传文件控件
     //多文件列表示例
     var uploadInst = layui.upload;
   
   //普通图片上传
     uploadInst.render({
       elem: '#uplodBtn'
       ,url: '/creditplatformweb/news/upload/',
       data: {type: '/partner/' ,id: ""}
       //,multiple: true
        ,before: function(obj){
   	        //预读本地文件示例，不支持ie8
   	        obj.preview(function(index, file, result){
   	         document.getElementById("showPic").innerText = file.name	;
	             });
   	        } 
       ,done: function(res){
    	   //如果上传失败
        if(res.code >  0){
          return layer.msg('上传失败');
        }
        filepath=res.data.src;
        return layer.msg('上传成功');
        //上传成功
      }
      ,size: 1024
      ,error: function(){
        //演示失败状态，并实现重传
        var demoText = $('#showMesage');
        demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
        demoText.find('.demo-reload').on('click', function(){
          uploadInst.upload();
        });
       }
     });
     //触发事件
     $('#showPic').on('click', function () {
    	 var  content= '<div style="text-align: center;"><img src="'+filepath+'" height="300px;" width="350px;"/></div>';
         layer.open({
             type: 1,
             title: false, //不显示标题栏
             closeBtn: false,
             //area: '300px;',
             shade: 0,
             id: 'LAY_layuipro', //设定一个id，防止重复弹出
             btn: ['关闭'],
             btnAlign: 'c',
             moveType: 1,//拖拽模式，0或者1
             content: content,
             success: function (layero) {
             }
         });
     });	
     //监听审核按钮
     $("#uploadbtnListAction").on('click', function () {
         var name=$("#name").val();
         var link=$("#link").val();
         dataJson["name"] = name;
         dataJson["link"] =link;
         dataJson["logo"] = filepath;
         if(name == null || name.length == 0 || name == ''){
        	 layer.msg('合作伙伴不能为空！')
    		 return;
         }
         if(link == null || link.length == 0 || link == ''){
        	 layer.msg('链接地址不能为空！')
    		 return;
         }
         if(filepath == null || filepath.length == 0 || filepath == ''){
              layer.msg('必须上传合作伙伴logo！')
              return;
            }
    	 $.ajax({
             type: "post",
             async: false,
             url: "/creditplatformweb/partner/insertPartner",
             data: {
                 "partner": JSON.stringify(dataJson)
             },// 你的formid
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

     });
   
     //点击进行关闭操作
     $(".closeBtn").on("click", function () {
         var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
         parent.layer.close(index); //关闭layer(关闭当前窗口)
     });
 
 })
 
</script>
 </head>

<body >
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="boxWrap">
    <p class="boxTitle"><span>新增合作伙伴</span></p>
    <div class="formWrap">
            <div class="layui-form-item inputTitle">
                <label class="layui-form-label"><span class="star">*</span>合作伙伴：</label>
                <div class="layui-input-block" style="padding: 5px 0px;">
                    <input type="text" id="name" name="name" placeholder="请输入"  autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item inputTitle">
                <label class="layui-form-label"><span class="star">*</span>链接地址：</label>
                <div class="layui-input-block" style="padding: 5px 0px;">
                    <input type="text" id="link" name="link" placeholder="请输入"  autocomplete="off" class="layui-input">
                </div>
            </div>
<!-- 左右两列左侧容器 -->
              <div class="layui-form-item">
                 <label class="layui-form-label"><span class="star">*</span>合作伙伴logo：</label>
                <div class="layui-upload">
                   <button type="button" class="layui-btn layui-btn-small uploadButton" id="uplodBtn">上传图片</button>
                   <div  style="margin-left: 200px;margin-top: -30px"> <a id="showPic" class="img-path-name" ></a></div>   
                </div>
		           <div class="layui-form-item Operatebutton">
			                <div class="layui-input-block buttonWrap">
			                    <button class="layui-btn submit" lay-submit " id="uploadbtnListAction" lay-filter="publishForm" >&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;
			                    </button>
	                    <button class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
			                </div>
		             
		            </div>
		       </div>     
           </div>
</div>
</body>
