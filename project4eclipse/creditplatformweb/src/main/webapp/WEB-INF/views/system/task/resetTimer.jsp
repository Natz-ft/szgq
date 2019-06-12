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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/build/kalendae.css" type="text/css" charset="utf-8">
<!-- <script src="src/moment.js" type="text/javascript" charset="utf-8"></script>
	<script src="build/kalendae.js" type="text/javascript" charset="utf-8"></script> -->
	<script src="${pageContext.request.contextPath}/static/build/kalendae.standalone.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
    /*鏌ヨ鏉′欢*/
.boxWrap .formWrap {
    padding: 20px 10px;
    /* margin: 0 auto; */
    /* overflow: hidden; */
}
.boxWrap {
    background-color: #EAF3F8;
    /* margin: 0 auto; */
    /* overflow: hidden; */
}
        .boxWrap .formWrap .termsType{
            height: 10%;
            float: left;
        }
        .boxWrap .formWrap .termsType span{
            display: inline-block;
            width: 100px;
            height: 32px;
            line-height: 37px;
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
            margin-left: 140px;
            float: left;
            height: 32px;
            overflow: hidden;
        }
        .boxWrap .formWrap .layui-input-inline{
            margin-left: -10px;
            float: left;
            height: 32px;
        }
        .boxWrap .formWrap .layui-input-block {
         margin-left: -10px;
            float: left;
            height: 32px;
        }
        .boxWrap .formWrap .termsList li{
            float: left;
            padding: -20px 2px; 
            margin: 0px 8px; 
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
            width: 200px;
            height:30px;
            line-height:30px;
            border: 1px solid #8DBDDC;
            border-radius: 5px;
        } 
.buttonWrap{
            width: 100%;
            padding: 15px 0;
            margin-left: 20px;
            text-align: center;
            margin-top:  40px;
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
.kalendae .k-days span.closed {
			background:red;
		}
.dates-input {
    width: 340px;
    height: 30px;
    line-height: 30px;
    border: 1px solid #8DBDDC;
    border-radius: 5px;
}
.shuoming0{
margin-top: 4px;
margin-left:3px;
font-size: 9px;
color: #0D4F92
}
.shuoming1{
margin-left: 30px;
font-size: 9px;
color: #0D4F92;
}			
</style>
 </head>
<body style="background-color: #EAF3F8">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="boxWrap">
    <div class="formWrap">
    <form class="layui-form" action="" >
              <div class="layui-form-item">
			     <div class="termsType">
	                    <span>选择类型：</span>
	                </div>
			       <div class="layui-input-block">
			       <input type="radio" name="type"  id="type1" title="指定一次" value="1" lay-filter="type1"  checked>
			      <input type="radio" name="type"  id="type2" title="重复执行" value="2" lay-filter="type2" >
			      <input type="radio" name="type"  id="type3" title="立即执行" value="3" lay-filter="type3" >
			    </div>
		     </div>
		      <div class="layui-form-item" style="display: inline;" id="zd">
			     <div class="termsType">
	                    <span>选择时间：</span>
	                </div>
			      <div class="layui-input-block" style="padding: 5px 0px;">
			         <input type="text" name="appointDate" class="layui-input" id="test5" placeholder="yyyy-MM-dd HH:mm:ss">
			      </div>
			      <div class="termsType shuoming0">
			                   说明：指定具体日期执行并切只执行一次;
			      </div>
		     </div>
		     <div class="chongfu" id="cf" style="display: none;">
		            <div class="layui-form-item">
		     <div class="termsType">
                    <span>选择时间：</span>
                </div>
		      <div class="layui-input-block" style="padding: 5px 0px;">
		        <input type="text" name="reTimer" class="layui-input" id="test4" placeholder="HH:mm:ss">
		      </div>
		     </div>
		    
<!-- 左右两列左侧容器 -->
             	<div class="layui-form-item">
		                <div class="termsType">
		                    <span>选择星期：</span>
		                    <a class="queryAll newsTypeAll" id="statusAll">全部</a>
		                </div>
		                <div class="termsList" id="newstype">
		                    <ul class="clearfix" id="statusType">
										<li><a value="1">周一</a></li>
										<li><a value="2">周二</a></li>
										<li><a value="3">周三</a></li>
										<li><a value="4">周四</a></li>
										<li><a value="5">周五</a></li>
										<li><a value="6">周六</a></li>
										<li><a value="7">周日</a></li>
							</ul>
		                </div>
            		</div>
            		 <div class="layui-form-item">
				   <div class="termsType">
	                    <span>选择日期：</span>
	                </div>
				    <div class="layui-input-block">
<!-- 				       <input type="checkbox" name="month1" id="month1"  lay-skin="primary"  title="每天" value="1" lay-filter="month"> -->
<!-- 				       <input type="checkbox" name="month2" id="month2"  lay-skin="primary"  title="1号"  value="2" lay-filter="month"> -->
<!-- 				       <input type="checkbox" name="month3" id="month3"  lay-skin="primary"  title="15号" value="3" lay-filter="month"> -->
<!-- 				       <input type="checkbox" name="month4" id="month4"  lay-skin="primary"  title="每月最后一天" value="4" lay-filter="month"> -->
				           <select name="days" id="days" lay-verify="" lay-search lay-filter="days" >
							  <option value="">请选择日期</option>
							  <option value="1">每月1号</option>
							  <option value="2">每月2号</option>
							  <option value="3">每月3号</option>
							  <option value="4">每月4号</option>
							  <option value="5">每月5号</option>
							  <option value="6">每月6号</option>
							  <option value="7">每月7号</option>
							  <option value="8">每月8号</option>
							  <option value="9">每月9号</option>
							  <option value="10">每月10号</option>
							  <option value="11">每月11号</option>
							  <option value="12">每月12号</option>
							  <option value="13">每月13号</option>
							  <option value="14">每月14号</option>
							  <option value="15">每月15号</option>
							  <option value="16">每月16号</option>
							  <option value="17">每月17号</option>
							  <option value="18">每月18号</option>
							  <option value="19">每月19号</option>
							  <option value="20">每月20号</option>
							  <option value="21">每月21号</option>
							  <option value="22">每月22号</option>
							  <option value="23">每月23号</option>
							  <option value="24">每月24号</option>
							  <option value="25">每月25号</option>
							  <option value="26">每月26号</option>
							  <option value="27">每月27号</option>
							  <option value="28">每月28号</option>
						  </select>   
				    </div>
				</div>
				    <div class="shuoming1">
			                         说明:</span>选择星期是指每月的周几重复执行;选择日期指每月某天重复执行;
			       </div>
		     </div>
           <div class="layui-form-item Operatebutton">
	                <div class="layui-input-block buttonWrap">
	                    <button  type="button" class="layui-btn submit"  id="uploadbtnListAction" lay-submit lay-filter="restTimer">&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;认&nbsp;&nbsp;
	                    </button>
	                    <button class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
	                </div>
             
            </div>
        </form>
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
     var laydate = layui.laydate;
     var dataJson = {};
     var weeks = new Array();
     var queryAll;
     // 查询条件展开
        var list, showOne, showAll ,list1;
   //时间选择器
     laydate.render({
       elem: '#test4'
       ,type: 'time'
     });
//  	var picker = new Kalendae.Input('input0',{
// 		//attachTo:document.body,
// 		months:1
		
// 	});
 	//限定可选日期
 	  var ins22 = laydate.render({
 	    elem: '#test-limit1'
 	    	 ,min: '2017-12-01'
 	    	    ,max: '2017-12-28'
 	    ,ready: function(){
 	      ins22.hint('日期可选值设定在 <br> 1号 到 28号');
 	    }
 	  });
     var date = new Date();
     var seperator1 = "-";
     var seperator2 = ":";
     var month = date.getMonth() + 1;
     var strDate = date.getDate();
     if (month >= 1 && month <= 9) {
         month = "0" + month;
     }
     if (strDate >= 0 && strDate <= 9) {
         strDate = "0" + strDate;
     }
     var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
             + " " + date.getHours() + seperator2 + date.getMinutes()
             + seperator2 + date.getSeconds();
   //时间选择器
     laydate.render({
       elem: '#test5'
       ,type: 'datetime'
    	,min: currentdate
     });
  
     var divZd = document.getElementById("zd");
     var divCf = document.getElementById("cf");
     form.on('radio(type1)', function (data) {
        	 if (divCf.style.display == "inline") {
        		 divCf.style.display = 'none';//隐藏审核意见 
                 }
        	 if (divZd.style.display == "none") {
            	 divZd.style.display = 'inline';//隐藏审核意见 
             }
     })
     form.on('radio(type2)', function (data) {
        	 if (divCf.style.display == "none") {
        		 divCf.style.display = 'inline';//隐藏审核意见 
                 }
        	 if (divZd.style.display == "inline") {
            	 divZd.style.display = 'none';//隐藏审核意见 
             }
     })
     form.on('radio(type3)', function (data) {
    	 if (divCf.style.display == "inline") {
    		 divCf.style.display = 'none';//隐藏审核意见 
             }
        	 if (divZd.style.display == "inline") {
            	 divZd.style.display = 'none';//隐藏审核意见 
             }
     })
     $(".boxWrap .formWrap .termsList li a").on("click", function () {
         queryAll = $(this).parents(".formWrap").find(".queryAll");
          var flags=new Array();
         if (!$(this).parent().hasClass("active")) {
        	 $(this).parent().addClass("active");
             weeks.push($(this).attr("value"));
         }else{
        	 removeByValue(weeks, $(this).attr("value"));
        	 $(this).parent().removeClass("active");
         }
         list1 =  $(".queryAll").parent().siblings(".termsList");
    	 if (list1.find("li").hasClass("active")) {
    		 document.getElementById("days").disabled=true;
         }else{
        	 document.getElementById("days").disabled=false;
         }
         form.render('select');

     });
     function removeByValue(arr, val) {
    	  for(var i=0; i<arr.length; i++) {
    	    if(arr[i] == val) {
    	      arr.splice(i, 1);
    	      break;
    	    }
    	  }
    	}
     var flags1=new Array();
     $(".queryAll").on("click", function () {
    	 dataJson = {};
    	
         list = $(this).parent().siblings(".termsList");
        	 
        	 if (list.find("li").hasClass("active")) {
        		 weeks.splice(0,weeks.length);
        		 list.find("li").removeClass("active");
             }else{
            	 list.find("li").addClass("active");
            	 weeks.push("01");
            	 weeks.push("02");
            	 weeks.push("03");
            	 weeks.push("04");
            	 weeks.push("05");
            	 weeks.push("06");
            	 weeks.push("07");
             }
        	 list1 =  $(".queryAll").parent().siblings(".termsList");
        	 if (list1.find("li").hasClass("active")) {
        		 document.getElementById("days").disabled=true;
             }else{
            	 document.getElementById("days").disabled=false;
             }
             form.render('select');
     });
   //监听审核按钮
     form.on('submit(restTimer)', function(data) {
    	 var data1 = data.field;
    	 var id="${id}";
    	 var weekstr=weeks.toString();
    	 data1["weekstr"]=weekstr;
    	 data1["id"]=id;
    	 if(data1["type"]=="1"){
    		 var appointDate=$("input[name='appointDate']").val(); 
    		 if(appointDate==null || appointDate==""){
    			 layer.msg('日期不能为空', {
          	        time: 3000
          	      });
    			 return;
    		 }
    	 }
         if(data1["type"]=="2"){
        	 var reTimer=$("input[name='reTimer']").val();
        	 if(reTimer == null || reTimer==""){
        		 layer.msg('时间不能为空', {
          	        time: 3000
          	      });
        		 return;
        	 }
    	 }
    	 $.ajax({
             type: "post",
             async: false,
             url: "/creditplatformweb/task/updateCron",
             data: {"restTimer": JSON.stringify(data1)
             },// 你的formid
             success: function (data) {
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
