<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/autoload.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/reset.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/form.css" type="text/css" />
<style type="text/css">
.boxWrap .formWrap .termsType{
            height: 10%;
            float: left;
        }
        .boxWrap .formWrap .termsType span{
            display: inline-block;
            width: 100px;
            height: 32px;
            line-height: 32px;
            text-align: right;
            margin-right: 20px;
        }
        .boxWrap .formWrap .termsType a.queryAll{
            color: #0D4F92;
            font-weight: bold;
            cursor: pointer;
            padding: 0px 0px;
        }
        .boxWrap .formWrap .termsType .queryAll.active{
            color: #eee;
            background-color: #0D4F92;
            border-radius: 3px;
        }
        .boxWrap .formWrap .termsList{
            max-width: 800px;
            margin-left: -20px;
            float: left;
            height: 32px;
            overflow: hidden;
        }
        .boxWrap .formWrap .termsList li{
            float: left;
            padding: 2px 2px; 
            margin: 0px 10px; 
            margin-bottom: 5px; 
            cursor: pointer;
        }
        .boxWrap .formWrap .termsList li.active{
            background-color: #0D4F92;
            border-radius: 3px;
        }
        .boxWrap .formWrap .termsList li.active a{
            color: #eee;
        }
        .boxWrap .formWrap .warnNote{
            color: #FF6838;
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
.newsTypeAll{
margin-left:-15px;
}
.layui-form-item .layui-inline {
    margin-bottom: -10px;
    margin-right: -10px;
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
.layui-form-item {
    height: 40px;
    margin-bottom: 10px;
}
.layui-input-block{
    margin-left: 120px !important;
}
.layui-form-label{
	width: 90px !important;
}
</style>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript"></script>
 <link href="${pageContext.request.contextPath}/static/Layui-KnifeZ/css/layui.css" rel="stylesheet"/> 
<script src="${pageContext.request.contextPath}/static/Layui-KnifeZ/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/Layui-KnifeZ/lay/modules/upload.js"></script> 
 <script>
 $(function () {
     //调用富文本编辑器控件
     layui.use(['layedit', 'layer', 'jquery'], function () {
     	var dataJson = {};
     	var filepath="";
	    var uplodfileName="";
	    var indexFilepath="";
	    var indexUplodfileName="";
        var layer = layui.layer;
        var layedit = layui.layedit;
        layedit.set({
           tool: ['strong', 'italic', 'underline', 'del', 
            		'|','removeformat', 'fontFomatt', 'fontfamily','fontSize',
            		'|','left', 'center', 'right','|', 'face','table','link', 'unlink'
            ]
            , height: '176px'
        });
        var index = layedit.build('demandDetails');
      	//监听审核按钮
	     $("#uploadbtnListAction").on('click', function () {
	    	 var problem=$("#problem").val();
	    	 var answer=layedit.getContent(index);
	    	 var faqDate = $("#faqDate").val();
	    	 var indexTitle = $("#indexTitle").val();
	    	 var indexSubTitle = $("#indexSubTitle").val();
	         dataJson["problem"] =problem;
	         dataJson["answer"] = answer+"&&&& "+filepath+"&&&& "+uplodfileName+"&&&& "+faqDate
	         					+"&&&& "+indexTitle+"&&&& "+indexSubTitle+"&&&& "+indexFilepath+"&&&& "+indexUplodfileName;;
	         dataJson["faqtype"] ="0003";
	         dataJson["id"] = $("#newsId").val();
	         var checkType=dataJson.faqtype;
	         if(problem == null || problem.length == 0 || problem == ''){
	        	 layer.msg('标题不能为空！')
	    		 return;
	         }
	         if(problem.length > 30 ){
	        	 layer.msg('标题输入长度必须小于30！')
	    		 return;
	         }
	         if(faqDate == null || faqDate.length == 0 || faqDate == ''){
	        	 layer.msg('日期不能为空！')
	    		 return;
	         }
	         if(answer == null || answer.length == 0 || answer == ''){
	        	 layer.msg('内容不能为空！')
	    		 return;
	         }
	         if(indexTitle == null || indexTitle.length == 0 || indexTitle == ''){
	        	 layer.msg('首页标题不能为空！')
	    		 return;
	         }
	         if(indexSubTitle == null || indexSubTitle.length == 0 || indexSubTitle == ''){
	        	 layer.msg('首页副标题不能为空！')
	    		 return;
	         }
	    	 $.ajax({
	             type: "post",
	             async: false,
	             url: "/creditplatformweb/faq/updateDy",
	             data: {
	                 "faq": JSON.stringify(dataJson)
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
	             }
	         });
	     });
	     
	     // 调用上传文件控件
	     //多文件列表示例
	     var upload = layui.upload;
	     filepath="${faq.filepath}";
	     uplodfileName="${faq.filename}";
	     document.getElementById("showPic").innerText = uplodfileName;
	     var id="";
	     //普通图片上传
	     var uploadInst = upload.render({
	       elem: '#uplodBtn'
	       ,url: '/creditplatformweb/news/upload/',
	       data: {type: '/faq/' ,id: id}
	     , accept: 'file' //普通文件
	     ,before: function(obj){
		        //预读本地文件示例，不支持ie8
		        obj.preview(function(index, file, result){
		         document.getElementById("showPic").innerText = file.name	;
		             });
		        } 
	       ,done: function(res){
	    	   //如果上传失败
	        if(res.code ==  1){
	          return layer.msg('上传失败');
	        }
	        filepath=res.data.src;
	        uplodfileName=res.data.uplodfileName;
	        return layer.msg('上传成功');
	        //上传成功
	      }
	      ,size: 102400
	      ,error: function(){
	        //演示失败状态，并实现重传
	        var demoText = $('#showMesage');
	        demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
	        demoText.find('.demo-reload').on('click', function(){
	          uploadInst.upload();
	        });
	       }
	     });
	     
	     indexFilepath="${faq.indexFilepath}";
	     indexUplodfileName="${faq.indexFilename}";
	     document.getElementById("showIndexPic").innerText = indexUplodfileName;
	     var id="";
	     //普通图片上传
	     var uploadInst2 = upload.render({
	       elem: '#uplodIndexBtn'
	       ,url: '/creditplatformweb/news/upload/',
	       data: {type: '/faq/' ,id: id}
	     , accept: 'file' //普通文件
	     ,before: function(obj){
		        //预读本地文件示例，不支持ie8
		        obj.preview(function(index, file, result){
		         document.getElementById("showIndexPic").innerText = file.name	;
		             });
		        } 
	       ,done: function(res){
	    	   //如果上传失败
	        if(res.code ==  1){
	          return layer.msg('上传失败');
	        }
	        indexFilepath=res.data.src;
	        indexUplodfileName=res.data.uplodfileName;
	        return layer.msg('上传成功');
	        //上传成功
	      }
	      ,size: 102400
	      ,error: function(){
	        //演示失败状态，并实现重传
	        var demoText = $('#showMesage');
	        demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
	        demoText.find('.demo-reload').on('click', function(){
	          uploadInst2.upload();
	        });
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
    <p class="boxTitle"><span>编辑通知</span></p>
    <div class="formWrap">
            <div class="layui-form-item inputTitle">
                <label class="layui-form-label"><span class="star">*</span>标题：</label>
                <div class="layui-input-block" style="padding: 5px 0px;">
                      <input type="hidden" id="newsId" value="${faq.id}">
                    <input type="text" id="problem" name="problem" value="${faq.problem}"  autocomplete="off" class="layui-input">
                </div>
            </div>
            <!-- 日期 -->
             <div class="layui-form-item">
                <label class="layui-form-label"><span class="star">*</span>日期：</label>
                <div class="layui-input-block" style="padding: 5px 0px;">
                    <input type="text" id="faqDate" value="${faq.faqDate}"  class="layui-input" name="faqDate"  onfocus=" WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="" onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="star">*</span>内容：</label>
                <div class="layui-input-block"  style="padding: 12px 0px;">
                    <textarea id="demandDetails" placeholder="请输入内容" class="layui-textarea">${faq.answer}</textarea>
                   <div class="layui-form-mid warnNote">
                   法律提醒：禁止发布、散布、宣扬、危害中国主权、攻击党和国家领导人、故意破坏社会稳定局势、封建迷信等法律、行政法规禁止的其他内容。
                    </div> 
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="star" id="pictureId"></span>添加附件：</label>
                <div class="layui-upload">
                   <button type="button" class="layui-btn layui-btn-small uploadButton" id="uplodBtn">添加附件</button>
                   <div  style="margin-left: 220px;margin-top: -30px"> <a id="showPic" class="img-path-name" ></a></div>   
                </div>
             </div> 
             
              <div class="layui-form-item inputTitle">
                <label class="layui-form-label"><span class="star">*</span>首页标题：</label>
                <div class="layui-input-block"  style="padding: 5px 0px;">
                      <input type="text" id="indexTitle" name="indexTitle" value="${faq.indexTitle }" placeholder="请输入"  autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item inputTitle">
                <label class="layui-form-label"><span class="star">*</span>首页副标题：</label>
                <div class="layui-input-block"  style="padding: 5px 0px;">
                      <input type="text" id="indexSubTitle" name="indexSubTitle" value="${faq.indexSubTitle }" placeholder="请输入"  autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="star"></span>首页背景：</label>
                <div class="layui-upload">
                   <button type="button" class="layui-btn layui-btn-small uploadButton" id="uplodIndexBtn">添加图片</button>
                   <div  style="margin-left: 220px;margin-top: -30px"> <a id="showIndexPic" class="img-path-name" ></a></div>   
                </div>
             </div>
             
           <div class="layui-form-item Operatebutton">
	                <div class="layui-input-block buttonWrap">
	                    <button class="layui-btn submit"  id="uploadbtnListAction"  >&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;
	                    </button>
	                    <button type="button" lay-close class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
	                </div>
             
            </div>
        </form>
    </div>
</div>
</body>
