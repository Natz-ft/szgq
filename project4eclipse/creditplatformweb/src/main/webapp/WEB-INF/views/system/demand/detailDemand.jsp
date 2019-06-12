<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/autoload.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/script/layui/css/layui.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/script/layui/css/layui.cu.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/reset.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/common.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/form.css"
	type="text/css" />
<style type="text/css">
/*覆盖top中的相同样式*/
a {
	color: #000000;
}

li {
	list-style: none;
}

/*设置整体大小开始*/
.mainwraper {
	width: 100%;
	margin: 0 auto;
	overflow: hidden;
}

/*设置整体大小结束*/
/*当前位置开始说明等样式开始*/

/*当前位置开始说明等样式结束*/
/*页面主题样式开始*/
.sub-c {
	background: #fff;
	padding: 15px;
	overflow: hidden;
	min-height: 350px;
}
/*详细内容开始*/
.sub-c ul li.pro-list {
	border-bottom: none;
}

.sub-c ul li.pro-list h3 {
	position: relative;
	margin-bottom: 10px;
}

.sub-c>.pro-list {
	padding: 0 10px 10px 0;
}

.pro-list {
	position: relative;
	overflow: hidden;
	border-bottom: 1px solid #d2d2d2;
	padding: 5px 0;
}

.pro-list h3 {
	color: #004F8A;
	font-size: 16px;
	font-weight: bold;
	margin-bottom: 10px;
}

.down {
	color: #004F8A;
	margin-bottom: 10px;
}

.buttonWrap {
	width: 100%;
	padding: 15px 0;
	margin-left: 0px;
	text-align: center;
	margin-top: 10px;
}
td p {
color: #0D4F92;
}
td p span{
color: #333
}
</style>

</head>
<body>
	<div class="mainwraper" style="background: #EEF3F9">
	<form class="layui-form" action="">
		<!--明细内容开始-->
		<div class="sub-c">
			<div class="pro-list">
				<h3>${demand.projectNameShow}</h3>
				<b><fmt:formatDate value="${demand['operdate'] }"
						pattern='yyyy-MM-dd HH:mm:ss' /></b>
			</div>
			<div align="center" style="margin-top:20px;">
				<table style="font-size: 14px;">
					<tr>
						<td style="width: 330px;"><p>
								融资金额(万)：<span>${demand.amount} ${demand.currencyStr}</span>
							</p></td>
						<td style="width: 330px;"><p>
								融资轮次：<span>${demand.finacingTurnStr}</span>
							</p></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="width: 330px;"><p>
								出让股权：<span>${demand.sellScale}</span>
							</p></td>
						<td style="width: 330px;"><p>
								投递方式：<span>${demand.openStr}</span>
							</p></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="width: 330px;"><p>
								联系人姓名：<span>${demand.relName}</span>
							</p></td>
						<td style="width: 330px;"><p>
								联系电话：<span>${demand.relPhone}</span>
							</p></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="width: 330px;"><p style="color: #0D4F92;">
								需求状态：<span style="color: #333;">${demand.statusStr}</span>
							</p></td>
						<td style="width: 330px;"><p>
								商业企划书： <span><a class="down" href="${demand.filePath}"
									download='${demand.fileName}'>${demand.fileName}</a></span>
							</p></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<c:if test="${demand.revokeFlag =='1'}">
						<tr>
							<td colspan="2" style="width: 330px;"><p>
									撤回原因： <span> ${demand.closeReason}</span>
						</p></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</c:if>
					<tr>
						<td colspan="2" style="width: 660px;"><p>
								需求详情：<span>${demand.description}</span>
							</p></td>
					</tr>
					
					
                   
				</table>
			</div>
			<c:if test="${demand.revokeFlag =='2'}">		
			   <div class="layui-form-item" style="margin-left: 19%; margin-top: 1%;">
						    <div class="layui-input-block">
						     <input type="radio" name="auditResult" id="auditResult1" value="1" title="审核通过" lay-filter="auditResult" >
                             <input type="radio" name="auditResult" id="auditResult2" value="2" title="审核不通过"  lay-filter="auditResult">
						    </div>
						  </div>
				  
			<div class="detailsCon clearfix" style=" margin-left: -10%;display: none;"  id="auditContentDiv">
			<div class="detail-title-name" style="color: #0D4F92;">
				<span>不通过原因</span>
			</div>
		    <div class="detail-table">
			     <div><textarea name="auditContent"   id="auditContent"  placeholder="请输入不通过原因" class="layui-textarea" lay-verify="auditContent"></textarea> </div>
		    </div>
		</div>	
		</c:if>		  
			<div class="layui-input-block buttonWrap">
				<c:if test="${demand.revokeFlag =='0'}">
					<button type="button" lay-close class="layui-btn revokeBtn">&nbsp;&nbsp;撤&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;</button>
				</c:if>
					<c:if test="${demand.revokeFlag =='2'}">
					<button type="button" lay-close class="layui-btn auditBtn">&nbsp;&nbsp;审&nbsp;&nbsp;&nbsp;核&nbsp;&nbsp;</button>
				</c:if>
				<button type="button" lay-close class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
			</div>
		</div>
		</form>
		<!--明细内容结束-->
	</div>
	<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
<script>
	$(function() {
		
		var form = layui.form;
		var id= '${demand.infoId}';
		//监听审核按钮
        form.on('radio(auditResult)', function (data) {
            var id = $("input[name='auditResult']:checked").val();
            var divV = document.getElementById("auditContentDiv");
            if (id == 1 ) {//审核通过
                if (divV.style.display == "inline") {
                    divV.style.display = 'none';//隐藏审核意见
                    $('#auditContent').val('');
                }
            } else if (id == 2 ) {//审核不通过
                if (divV.style.display == "none") {
                    divV.style.display = 'inline';//展示审核意见
                }
            }
        });
		//点击进行关闭操作
		$(".closeBtn")
				.on(
						"click",
						function() {
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index); //关闭layer(关闭当前窗口)
							window.parent.location.href = "/creditplatformweb/demand/curdDemand";
						});
		$(".revokeBtn").on(
				"click",
				function() {
					layer.open({
					  	  type: 2,
					  	  title: '撤回',
					  	  shadeClose: true,
					  	  maxmin: true,	
					  	  scrollbar: false,
					  	  shade: 0,
					  	  area: ['85%', '70%'],
					  	  content:  "/creditplatformweb/demand/revokeDemand?id=" + id //iframe的url
					  	}); 
				});
		$(".auditBtn").on(
				"click",
				function() {
					var auditContent = $("#auditContent").val();
					var auditResult = $("input[name='auditResult']:checked").val();
					
					if(auditResult=='undefined' || auditResult==null ||  auditResult.length==0 ||  auditResult==''){
						layer.alert('请选择是否通过！')
						return false;
					}else{
						if(auditResult==2){
							if(auditContent=='undefined' || auditContent==null ||  auditContent.length==0 ||  auditContent==''){
								layer.alert('请填写不通过原因！')
								return false;
							}
						}
						
					}
					
					
					$.ajax({
						type : "post",
						async : false,
						url : "/creditplatformweb/demand/auditDemand",
						data : {
							"infoId" : id,"auditResult":auditResult,"auditContent":auditContent
						},
						success : function(data) {
							data = JSON.parse(data);
			                 var flag = true;
			                 if (flag) {
			                	 layer.alert('提交成功，审核结果已提交', function () {
		                			 parent.location.reload();
		                        });
			                 } else {
			                     layer.alert('提交失败', {
			                         title: '最终的提交信息'
			                     })
			                 }
						},
						error : function(data) {
						}
					});
					return false;
				});
		
	});
</script>
</body>
