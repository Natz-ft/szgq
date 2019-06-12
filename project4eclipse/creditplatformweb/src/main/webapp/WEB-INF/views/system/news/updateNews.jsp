<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<script src="${pageContext.request.contextPath}/static/js/my97/WdatePicker.js" type="text/javascript" ></script>
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
.newsTypeAll{
margin-left:-15px;
}
.uploadButton{
margin-top: 10px;
margin-left: 10px;
}
.uploadButton-img{
margin-top: -20px;
margin-left: -25px;
}        
.img-path-name {
    padding: 0 10px;
    color: #999;
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
    <p class="boxTitle"><span>编辑新闻</span></p>
    <div class="formWrap">
            <div class="layui-form-item inputTitle">
                <label class="layui-form-label"><span class="star">*</span>新闻标题：</label>
                <div class="layui-input-block" style="padding: 5px 0px;">
                    <input type="text" id="titleId" name="title" value="${news.title}"  autocomplete="off" class="layui-input">
                </div>
            </div>
<!-- 左右两列左侧容器 -->
             <div class="layui-form-item">
                    <!-- <span>新闻类型：</span> -->
                <label class="layui-form-label"><span class="star">*</span>新闻类型：</label>
                <div class="termsList" id="newstype" style="margin-left:-5px;margin-top:4px;">
                    <ul class="clearfix" id="statusType">
 						<c:forEach var="newsdd" items="${statusList}">
				            <li <c:if test ='${newsdd.dicCode eq news.newstype}'>class="active" </c:if>><a value="${newsdd.dicCode}">${newsdd.dicName} </a></li>
						</c:forEach>
					</ul>
                </div>
            </div>
            
            <!-- 新闻日期 -->
             <div class="layui-form-item">
                <label class="layui-form-label"><span class="star">*</span>新闻日期：</label>
                <div class="layui-input-block" style="padding: 5px 0px;">
                    <input type="text" id="newsDate" value="${news.newsDate}"  class="layui-input" name="newsDate"  onfocus=" WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="" onkeydown="if(event.keyCode==13){event.keyCode=0;return false}" />
                </div>
            </div>
            <!-- 是否轮播 -->
             <div class="layui-form-item">
                <label class="layui-form-label"><span class="star">*</span>是否轮播：</label>
                <div class="layui-input-block" id="isLunbo" style="padding: 12px 0px;">
                    <input type="radio" name="isLunbo" value="1" <c:if test="${news.isLunbo==1}">checked</c:if>/>
                    <label> 是</label>&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="isLunbo" value="0" <c:if test="${news.isLunbo==0}">checked</c:if>/>
                    <label> 否</label>　
                </div>
            </div>
            <!-- 轮播顺序 -->
             <div class="layui-form-item">
                <label class="layui-form-label"><span class="star">*</span>轮播顺序：</label>
                <div class="layui-input-block" style="padding: 5px 0px;">
                    <input type="text" value="${news.lunboOrd}" id="lunboOrd" name="lunboOrd"  autocomplete="off" class="layui-input" <c:if test="${news.isLunbo==0}">readonly</c:if>>
                </div>
            </div>
            
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="star">*</span>新闻内容：</label>
                <div class="layui-input-block" style="padding: 12px 0px;">
                    <textarea id="demandDetails" placeholder="请输入内容" class="layui-textarea">${news.content}</textarea>
                   <div class="layui-form-mid warnNote">
                   法律提醒：禁止发布、散布、宣扬、危害中国主权、攻击党和国家领导人、故意破坏社会稳定局势、封建迷信等法律、行政法规禁止的其他内容。
                    </div> 
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="star" id="pictureId"><c:if test='${news.newstype=="01" }'>*</c:if></span>修改图片：</label>
                <div class="layui-upload">
                   <button type="button" class="layui-btn layui-btn-small uploadButton" id="uplodBtn">上传图片</button>
                   <div  style="margin-left: 200px;margin-top: -30px"> <a id="showPic" class="img-path-name" ></a></div>   
                </div>
             </div>   
            <%--  <div class="layui-form-item uploadPicDiv">
             <div class="layui-form-item uploadPicButton">
             <button type="button" class="layui-btn" id="uplodBtn">上传图片</button>
             </div>
                      <img src="${news.photo}" class="layui-upload-img" id="showPic"  height="200" width="200">
					    <p id="showMesage" class="layui-input-block"></p>
             </div> 
             <div>
                     <button type="button" class="layui-btn layui-btn-small uploadButton" id="uplodBtn">选择文件</button>
                     <button type="button" class="layui-btn layui-btn-small uploadButton" id="beginupload">开始上传</button>
                </div>
             --%>
           <div class="layui-form-item Operatebutton">
	                <div class="layui-input-block buttonWrap">
	                    <button class="layui-btn submit"  id="uploadbtnListAction"  >&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;
	                    </button>
	                    <button class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
	                </div>
             
            </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
 <script>
   	$('input[type=radio][name="isLunbo"]').change(function(){
	    if(this.value == '1'){
	        $("#lunboOrd").removeAttr("readonly");
	    }else if(this.value == '0') {
	        $('#lunboOrd').val("").attr("readonly","readonly");
	    }
	});
     	
</script>
<script>
 $(function () {
     //调用富文本编辑器控件
     // 表单控件
     var form = layui.form;
     var layedit = layui.layedit;
     var dataJson = {};
     var content;
     var type="${news.newstype}";
   //点击a标签时触发选中事件(此事件放在字典值赋值之后)
     $(".termsList ul li a").on("click", function () {
         var dicType = $(this).parents(".termsList").attr("id");
         type=$(this).attr("value");
     })
     var queryAll;
     $(".boxWrap .formWrap .termsList li a").on("click", function () {
         queryAll = $(this).parents(".formWrap").find(".queryAll");
         var t = $(this);
         if (queryAll.hasClass("active")) {
             queryAll.removeClass("active");
         }
         if (!$(this).parent().hasClass("active")) {
             $(this).parent().addClass("active").siblings().removeClass("active");
         }
     });
     // 查询条件展开
     var list, showOne, showAll;
     $(".queryAll").on("click", function () {
         list = $(this).parent().siblings(".termsList");
         showAll = list.find("ul").height();
         showOne = list.height();
         $(this).addClass("active");
         list.find("li").removeClass("active");
         if (showOne < showAll) {
             list.animate({height: showAll});
         } else {
             list.animate({height: 24});
         }
     });
     layedit.set({
      	  uploadImage: {
      		  url: '/creditplatformweb/news/uploadImage/'
      			  , type: 'post' //默认post
      				, data: {type: '/news/' ,id: id}
      	  }
      	});
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
    		  //,'face' //表情
    		   ,'image' //插入图片
    		  //,'help' //帮助
    		]
    	 ,height: 200
     }); //建立编辑器
     
     // 调用上传文件控件
     //多文件列表示例
     var upload = layui.upload;
    
     var filepath="${news.photo}";
     var uplodfileName="${news.uplodfileName}";
     var arr = filepath.split('/');
     document.getElementById("showPic").innerText = uplodfileName;
     var id=${news.id};
     //普通图片上传
     var uploadInst = upload.render({
       elem: '#uplodBtn'
       ,url: '/creditplatformweb/news/upload/',
       data: {type: '/news/' ,id: id}
     , accept: 'file' //普通文件
     ,before: function(obj){
	        //预读本地文件示例，不支持ie8
	        obj.preview(function(index, file, result){
	         document.getElementById("showPic").innerText = file.name	;
	             });
	        } 
       /* ,auto: false //选择文件后不自动上传
       ,bindAction: '#beginupload' */
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
    	 var error="this.src='/creditplatformweb/static/image/uploadImg.jpg' ";
    	 var  content= '<div style="text-align: center; padding: 2px 0px;"><img src="'+filepath+'" onerror="'+ error +'" height="300px;" width="350px;"/></div>';
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
    	 var content= layedit.getContent(index);
     	 var textCont=layedit.getText(index);
     	 var dicType = $("input:text").attr("name");
     	 var checkType = type;
         dataJson["newstype"] = checkType;
     	 var title=$("#titleId").val();
     	 var checkType=dataJson.newstype;
     	 if(checkType==undefined){
     		 checkType ="${news.newstype}";
     	 }
     	 if(checkType=='01'){
    		textCont=textCont.substring(0, 25)+"...";
     	 }else{
     		textCont=textCont.substring(0, 70)+"...";
     	 }
      	  textCont=textCont.replace(/\s+/g,""); 
     	 content=textCont+"&&"+content;
     	 
     	 var newsDate = $("#newsDate").val();
     	 var isLunbo = $('input[type=radio][name="isLunbo"]:checked').val();
     	 var lunboOrd = $("#lunboOrd").val();
     	 
         dataJson[dicType] =title;
         dataJson["content"] = content;
         dataJson["photo"] = filepath;
         dataJson["uplodfileName"] = uplodfileName;
         
         dataJson["newsDate"]=newsDate;
         dataJson["isLunbo"]=isLunbo;
         dataJson["lunboOrd"]=lunboOrd;
         
         dataJson["id"] = id;
         if(title == null || title.length == 0 || title == ''){
        	 layer.msg('新闻标题不能为空！')
    		 return;
         }
         if(title.length >60){
        	 layer.msg('新闻标题长度必须小于30！')
    		 return;
         }
         
         if(newsDate == null || newsDate.length == 0 || newsDate == ''){
        	 layer.msg('新闻日期不能为空！')
    		 return;
         }
         if(isLunbo==1 && (lunboOrd == null || lunboOrd.length == 0 || lunboOrd == '')){
        	 layer.msg('轮播顺序不能为空！')
    		 return;
         }
         if(isLunbo==1 ){
         	if(filepath == null || filepath.length == 0 || filepath == ''){
           		 layer.msg('轮播新闻必须上传图片！')
           		 return;
           	 }
         }
         
         if(layedit.getContent(index) == null || layedit.getContent(index).length == 0 || layedit.getContent(index) == ''){
        	 layer.msg('内容不能为空！')
    		 return;
         }
         if(checkType == null || checkType.length == 0 || checkType == ''){
        	 layer.msg('请选择新闻类型！')
    		 return;
         }else{
        	 if(checkType == "01"){
            	 if(filepath == null || filepath.length == 0 || filepath == ''){
            		 layer.msg('新闻动态必须上传图片！')
            		 return;
            	 }
             }
         }
    	 $.ajax({
             type: "post",
             async: false,
             url: "/creditplatformweb/news/update",
             data: {
                 "contentNews": JSON.stringify(dataJson)
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
/*          init(data);

*/     });
//      function initDD() {
//          //状态下拉框展示
//          var newsTypeHtml = '';
//          $.ajax({
//              type: "post",
//              url: "/SRRPBusinesWeb/initInfo/getDDList",//url地址
//              data: {"name": "newsType"},// 查询条件
//              async: false,//使用同步的方式,true为异步方式
//              success: function (data) {
//                  data = JSON.parse(data);
//                  for (var i = 0; i < data.length; i++) {
//                      var _json = data[i];
//                      var active="active";
//                      if (_json.dicName == "${news.newstype}"){
//                     	 newsTypeHtml += "<li class=\"" + active + "\"><a  value=\"" + _json.dicCode + "\">" + _json.dicName + "</a></li>";
//                          dataJson["newstype"] =  _json.dicCode;
//                      }else{
//                     	 newsTypeHtml += "<li><a value=\"" + _json.dicCode + "\">" + _json.dicName + "</a></li>";
//                      }
//                  }
//                  $("#statusType").append(newsTypeHtml);
//              }
//          });
//      }
     //上传图片是否必须判定
	 $("#statusType").on("click",function(){
	    var checkType=dataJson.newstype;
	    if(checkType=='01'){
	    	document.getElementById('pictureId').innerHTML="*";
	    }else{
	    	document.getElementById('pictureId').innerHTML="";
	    } 
	 })
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
