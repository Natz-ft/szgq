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
    /*鏌ヨ鏉′欢*/
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
 
}
</style>
 </head>
<body >
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="boxWrap">
    <p class="boxTitle"><span>新增</span></p>
    <div class="formWrap">
            <div class="layui-form-item inputTitle">
                <label class="layui-form-label"><span class="star">*</span>常见问题：</label>
                <div class="layui-input-block"  style="padding: 5px 0px;">
                      <input type="text" id="problem" name="problem" placeholder="请输入"  autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="star">*</span>解答：</label>
                <div class="layui-input-block" style="padding: 12px 0px;">
                    <textarea id="demandDetails" placeholder="请输入内容" class="layui-textarea"></textarea>
                   <div class="layui-form-mid warnNote">
                   法律提醒：禁止发布、散布、宣扬、危害中国主权、攻击党和国家领导人、故意破坏社会稳定局势、封建迷信等法律、行政法规禁止的其他内容。
                    </div> 
                </div>
            </div>
           <div class="layui-form-item Operatebutton">
	                <div class="layui-input-block buttonWrap">
	                    <button class="layui-btn submit" lay-submit " id="uploadbtnListAction" lay-filter="publishForm" >&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;
	                    </button>
	                    <button type="button" lay-close class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
	                </div>
             
            </div>
    </div>
</div>
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
    		  ,'link' //超链接
    		  ,'unlink' //清除链接
    		  ,'face' //表情
    		  /* ,'image' //插入图片
    		  ,'help' //帮助 */
    		]
    	 ,height: 176
     }); //建立编辑器
     //监听审核按钮
     $("#uploadbtnListAction").on('click', function () {
    	 var problem=$("#problem").val();
    	 var answer=layedit.getContent(index);
         dataJson["problem"] =problem;
         dataJson["answer"] = answer;
         dataJson["faqtype"] ="0001";
         var checkType=dataJson.faqtype;
         if(problem == null || problem.length == 0 || problem == ''){
        	 layer.msg('常见问题不能为空！')
    		 return;
         }
         if(problem.length > 30 ){
        	 layer.msg('常见问题输入长度必须小于30！')
    		 return;
         }
         if(answer == null || answer.length == 0 || answer == ''){
        	 layer.msg('问题解答不能为空！')
    		 return;
         }
    	 $.ajax({
             type: "post",
             async: false,
             url: "/creditplatformweb/faq/insertInform",
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
</body>
