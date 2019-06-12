<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>投资需求</title>
    <script src="../static/js/autoload.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/script/layui/css/layui.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/script/layui/css/layui.cu.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/reset.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/frame-free/frame/css/framework/form.css" type="text/css" />
    <!-- <link rel="stylesheet" type="text/css" href="../static/style/page/form.css"> -->
    <style>
       
        .layui-form .industryCheckbox li {
            float: left;
            width: 33%;
        }
        .layui-form-label {
            padding: 6px 0px;
            width: 134px;
        }
        .input-insert-data{
            width: 180px;
            height: 22px;
            border: 1px solid #8DBDDC;
            border-radius: 3px;
        }
        .layui-input, .layui-select, .layui-textarea {
          height: 22px;
        }
       .boxWrap .formWrap .form-title {
          height: 100%;
          padding-top:10px;
          margin-left: 2%;
        }
        .layui-input-block1{
          padding-top:10px;
          padding-right:20px;
        }
        .layui-form-item {
          height: 47px;
          margin-bottom: 5px;
        }
         .layui-form-item2 {
          height: 23px;
          width:90%;
          margin-bottom: 5px;
        }
/*         .boxWrap .formWrap .form-right { */
/* 		    width: 464px; */
/* 		    float: right; */
/* 		} */
		body {
          background-color: #EAF3F8;
        }
        .buttonWrap {
		    width: 100%;
		    padding: 15px 0;
		    margin-left: 0px;
		    text-align: center;
		    /* background: #eee; */
        }
       .tj_one_title {
		    float: left;
		    width: 90px;
		    color: #555555;
		    padding-top: 15px;
		}
		
		.tj_one_title2 {
		    color: #a8a8a8;
		}
		
		.tj_one_title3 {
		    width: 70px;
		}
		
		.tj_fan {
		    margin-left: 90px;
		}
		
		.tj_fan li {
		    float: left;
		    margin-right: 12px;
		    background: #fef6cf;
		    border: solid 1px #f0e4b1;
		    height: 30px;
		    line-height: 30px;
		    padding-left: 10px;
		    margin-top: 10px;
		    color: #5a5c5b;
		}
		
		.tj_fan li a {
		    width: 30px;
		    border-left: dashed 1px #f0e4b1;
		    padding: 8px 9px;
		    margin-left: 6px;
		}
		
		.tj_fan li a img {
		    vertical-align: top;
		    margin-top: 9px;
		}
		
		.tj_jg_btn, .tj_jg li, .tj_more_btn, .tj_all_lion {
		    float: left;
		    padding: 0 10px;
		    height: 26px;
		    line-height: 26px;
		    -moz-border-radius: 5px;
		    -webkit-border-radius: 5px;
		    border-radius: 5px;
		    margin-top: 10px;
		    margin-right: 12px;
		}
		
		.tj_jg_btn {
		    background: #f2941a;
		    border: solid 1px #eb8453;
		}
		
		.tj_jg_btn a {
		    color: #ffffff;
		}
		
		.tj_jg li {
		    background: #ffffff;
		    border: solid 1px #d2d2d2;
		    color: #f2941a;
		    padding-right: 0px;
		    height: auto;
		 }
		
		.tj_jg li b {
		    color: #f2941a;
		    font-weight: normal;
		}
		
		.tj_jg li span {
		    color: #6b6b6b;
		}
		
		.tj_jg li a {
		    border: 0;
		    padding: 0;
		    background: #ffffff;
		}
		
		.tj_jg li a img {
		    margin-top: 8px;
		    margin-left: 15px;
		}
		
		.tj_tck2_result {
		    margin-left: 0px;
		}
		
		.tj_tck_btn {
		    width: 192px;
		    margin: 15px auto 0 auto;
		}
		
		.tj_tck_btn input {
		    width: 86px;
		    height: 32px;
		    -moz-border-radius: 20px;
		    -webkit-border-radius: 20px;
		    border-radius: 20px;
		    text-align: center;
		    cursor: pointer;
		}
		 .layui-form .industryCheckbox li {
		    float: left;
		    width: 20%;
		}
		
.boxContent {
    margin: 0px auto;
    min-width: 780px;
    max-width: 1260px;
    overflow: hidden;
}
    </style>
</head>
<body>
<!--用户启用禁用状态    默认是启用-->
<input id="stopFlag" type="hidden" value="1"/>
<input type="hidden" id="selectIndustryArr"  readonly="readonly"/>
<input type="hidden" id="trusteeshipFlag" value='${subac.trusteeship}'>
<!--头部logo以及登录注册开始-->
<div class="boxWrap">
<div class="boxContent">
    <div class="formWrap">
         <form id="form1" name ="form1" class="layui-form" method="post" > <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
                <div class="layui-form-item" >
                    <div class="form-title"><span>账户信息</span></div>
                </div>          
                 <div class="form-left" style="margin-left:2%" >
		              <input type="hidden"  name ="subac_id" value="${subac.subac_id}">
		              <!-- 左右两列左侧容器 -->
	                  <div class="layui-form-item">
                        <label class="layui-form-label"  style="width:134px;"><span class="star">*</span>基金名称：</label>
                        <div class="layui-input-block">
                           <input type="text" name="subacName" value='${subac.subacName}' lay-verify="required|subacName" autocomplete="off" class="form-input input-insert-data"  style="margin-top:4px;">
                        </div>
                       </div>
                        <div class="layui-form-item">
		                    <label class="layui-form-label" style="width:134px;"><span class="star">*</span>统一社会信用代码：</label>
		                     <div class="layui-input-block">
                                 <input type="text" name="certno" id="certno" lay-verify="required|certno" value='${subac.certno}' autocomplete="off" class="form-input input-insert-data"  maxlength="18" style="margin-top:4px;">
		                   </div>
		                </div>
		                   <div class="layui-form-item">
			                 <label class="layui-form-label" ><span class="star">*</span>法定代表人：</label>
			                 <div class="layui-input-block">
			                     <input type="text" name="legalRepresentative" id="legalRepresentative" maxlength="20" lay-verify="required|legalRepresentative" value='${subac.legalRepresentative}' autocomplete="off" class="form-input input-insert-data"   >(委托代表)
			               </div>
		    		   </div>
		                <div class="layui-form-item">
		                    <label class="layui-form-label" style="width:134px;"><span class="star">*</span>注册时间：</label>
		                     <div class="layui-input-block">
		                       <input type="text" name="registeTime"  id ="registeTime" autocomplete="off"   class="form-input input-insert-data" style="margin-top:4px;">
		                   </div>
		                </div>
		                  <div class="layui-form-item">
		                    <label class="layui-form-label" style="width:134px;"><span class="star">*</span>基金类型：</label>
		                    <div class="input-insert-data" style="margin-left: 134px;">
		                        <select name="subacType" id="subacType" lay-verify="required" lay-search="" lay-filter="selectFilter" style="margin-top:4px;">
		                            <c:forEach items='${ddsubacType}' var="d">
		                    		<option value='${d.dicCode}' >${d.dicName}</option>
		                            </c:forEach>
		                        </select>
		                    </div>
		                </div>
		                
		                <div id="flagId" class="layui-form-item" >
                          <label class="layui-form-label" style="margin-top: -2px;"><span class="star" style="margin-left:20px;">*</span>托管机构：</label>
					      <div class="layui-input-block" style="margin-left: 110px;">
					            <div class="layui-input-inline" style="margin-top: 2px; height: 22px;width: 100px;border: 1px solid #8DBDDC;border-radius: 3px">
                                <select id="trusteeshipRadio" name="trusteeshipRadio" lay-filter="trusteeshipRadio">
                                    <option value="0" 
                                    <c:if test='${subac.trusteeshipRadio=="0"}'>selected</c:if>
                                    >有</option>
                                    <option value="1" 
                                    <c:if test='${subac.trusteeshipRadio=="1"}'> selected</c:if>
                                    >无</option>
                                </select>
					            </div>
					                <input id="trusteeshipid"  name="trusteeship" lay-verify="trusteeship" maxlength="20" placeholder="请输入" 
					                <c:if test='${subac.trusteeshipRadio=="1"}'>type="hidden"</c:if>
					                  <c:if test='${subac.trusteeshipRadio=="0"}'>value='${subac.trusteeship}'</c:if>
					                   class="form-input"  autocomplete="off" style="width: 170px;float:left;border: 1px solid #8DBDDC;height:22px;margin-top:2px;border-radius: 3px;">
                         </div>
                      </div>
                      <div class="layui-form-item">
		                  <label class="layui-form-label" style="width: 35%;margin-left:-5%"><span class="star">*</span>实现退出项目数：</label>
		                  <div class="layui-input-block">
		                    <input type="text" name="implementExitProject"  id="implementExitProject" value='<fmt:formatNumber value='${subac.implementExitProject}' pattern="####.##"/>' lay-verify="required|implementExitProject" autocomplete="off" class="form-input input-insert-data"  maxlength="5" style="margin-top:4px;">
		                  </div>
                       </div>
		       </div>
            <!-- 左右两列右侧容器 -->
            <div class="form-right">
               <div class="layui-form-item">
		        <label class="layui-form-label" style="width:110px;"><span class="star">*</span>用户账号：</label>
		        <div class="layui-input-block">
                  <input type="text" name="userName" value='${user.username}' autocomplete="off" class="form-input input-insert-data" maxlength="18" style="margin-top:4px;">
		        </div>
		       </div>
		       
<!-- 		          <div class="layui-form-item"> -->
<!-- 		                    <label class="layui-form-label" style="width:110px;"><span class="star">*</span>证件号：</label> -->
<!-- 		                     <div class="layui-input-block"> -->
<%--                                  <input type="text" name="certno" id="certno" lay-verify="required|certno" value='${subac.certno}' autocomplete="off" class="form-input input-insert-data"  maxlength="18" style="margin-top:4px;"> --%>
<!-- 		                   </div> -->
<!-- 		                </div> -->
               <div class="layui-form-item">
                          <label class="layui-form-label" style="width:110px;"><span class="star">*</span>所属区域：</label>
                          <div class="layui-input-inline" style="border: 1px solid #8DBDDC;border-radius: 3px; width:90px;margin-top: 5px;">
                                  <select name="areaProvince" id="areaProvince" lay-search="" lay-verify="required"  lay-filter="areaProvince" > 
			                         <option value="">请选择省</option>
				                      <c:forEach items="${areaProvince}" var="dind">
												<option value="${dind.code}" <c:if test ='${dind.code eq subac.areaProvince}'>selected</c:if> >${dind.name}</option>
									 </c:forEach>
			                        </select>
                          </div>
                         <div class="layui-input-inline" style="width:90px;margin-top: 5px;border: 1px solid #8DBDDC;border-radius: 3px;">
							<select id="areaCity" name="areaCity" lay-search="" lay-verify="required"  lay-filter="areaCity">
							<option value="">请选择市</option>
							</select>
				        </div>
						  <div class="layui-input-inline" style="width:90px;margin-top: 5px;border: 1px solid #8DBDDC;border-radius: 3px;">
								<select id="areaCounty" name="areaCounty" lay-verify="required"  lay-search="">
								<option value="">请选择县/区</option>
								</select>
						  </div>
				</div>  
				  <div class="layui-form-item">
                          <label class="layui-form-label" style="width:110px;"><span class="star">*</span>法定代表电话：</label>
                          <div class="layui-input-block">
                                <input type="text" name="legalRepresentativeCall" id="legalRepresentativeCall" lay-verify="required|legalRepresentativeCall"  maxlength="11" value='${subac.legalRepresentativeCall}' autocomplete="off" class="form-input input-insert-data"   style="margin-top:4px;width:150px;">（委托代表）
                          </div>
                </div>
				
               <div class="layui-form-item">
                  <label class="layui-form-label" style="width: 32%;margin-left:-5%"><span class="star">*</span>基金编号(中基协)：</label>
                  <div class="layui-input-block">
                    <input type="text" name="amacRecord" value='${subac.amacRecord}' lay-verify="required|amacRecord" autocomplete="off" class="form-input input-insert-data"  maxlength="6" style="margin-top:4px;">
                  </div>
               </div>
               <div class="layui-form-item">
                  <label class="layui-form-label" style="width: 32%;margin-left:-5%"><span class="star">*</span>投资项目数量：</label>
                  <div class="layui-input-block">
                    <input type="text" name="investmentProjects"  id="investmentProjects" value='<fmt:formatNumber value='${subac.investmentProjects}' pattern="####.##"/>' lay-verify="required|investmentProjects" autocomplete="off" class="form-input input-insert-data"  maxlength="5" style="margin-top:4px;">
                  </div>
               </div>  
               <div class="layui-form-item">
	                   <label class="layui-form-label"><span class="star">*</span>累计投资金额：</label>
	                   <div class="layui-input-block">
	                        <input type="text" maxlength="8" name="cumulativeInvestment"  id="cumulativeInvestment"  lay-verify="required|cumulativeInvestment"  autocomplete="off" class="form-input"
                                       value="<fmt:formatNumber value='${subac.cumulativeInvestment}' pattern="####.##"/>" style="width: 70px;height: 22px;float:left;margin-top: 5px; border: 1px solid #8DBDDC; border-radius: 3px;">
	                        <div class="layui-form-mid" style="left:6px;">万元</div>
	                        <div class="layui-input-inline formEleSpliceUnit form-select" style="height: 22px; width: 72px;margin-top: 5px; border: 1px solid #8DBDDC; border-radius: 3px;">
	                            <select name="ciCurrency" id="ciCurrency" lay-verify="required">
	                                 <option value='' >请选择类型</option>
	                                 <c:forEach items='${ddCurrency}' var="d">
	                                      <option value='${d.dicCode}' 
	                                          <c:if test="${d.dicCode eq subac.ciCurrency}">selected</c:if>
	                                      >${d.dicName}</option>
	                                 </c:forEach>
	                           </select>         
	                        </div>
	                    </div>
                  </div>
            </div>
            <!-- 左右两列左侧容器 -->
            <div class="layui-form-item" style="border-top: 1px dotted #ccc;heigth:20%;">
               <div class="form-title"><span>联系方式</span></div>
            </div>
                 <div class="form-left" style="margin-left:2%"  >
                 <div class="layui-form-item">
                    <label class="layui-form-label"><span class="star">*</span>联系人姓名：</label>
                    <div class="layui-input-block">
                        <input type="text" name="relName" value='${subac.relName}'lay-verify="required" autocomplete="off" class="form-input input-insert-data" maxlength="18" style="margin-top:4px;">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="star">*</span>联系人手机号：</label>
                    <div class="layui-input-block">
                        <input type="text" name="relPhone" value='${subac.relPhone}' lay-verify="required|phone" autocomplete="off" class="form-input input-insert-data" maxlength="11" style="margin-top:4px;">
                    </div>
                </div>
             </div>
              
            <!-- 左右两列右侧容器 -->
            <div class="form-right">
               
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="star">*</span>电子邮箱：</label>
                    <div class="layui-input-block">
                        <input type="text" name="email" value='${subac.email}' lay-verify="email" autocomplete="off" class="form-input input-insert-data" maxlength="50" style="margin-top:4px;">
                    </div>
                </div>
            </div>
              
             <div class="layui-form-item" style="border-top: 1px dotted #ccc;heigth:20%;">
               <div class="form-title"><span>投资策略</span></div>
            </div>
            <div >   
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="star">*</span>管理资本量：</label>
                <div class="layui-input-block">
                        <input type="text" maxlength="10" name="capitalMin" id="capitalMin" lay-verify="required|capitalMin"  placeholder="请输入" autocomplete="off" class="form-input" value="<fmt:formatNumber value="${subac.capitalMin}" pattern="####.##"/>" style="width: 70px;height: 22px;float:left;margin-top: 5px; border: 1px solid #8DBDDC; border-radius: 3px;">
                        <div class="layui-form-mid">-</div>
                        <input type="text" maxlength="10" name="capitalMax" id="capitalMax" placeholder="请输入" autocomplete="off" class="form-input" value="<fmt:formatNumber value= "${subac.capitalMax}" pattern="####.##"/>" style="width: 70px;height: 22px;float:left;margin-top: 5px; border: 1px solid #8DBDDC; border-radius: 3px;">
                        <div class="layui-form-mid" style="left:6px;">万元</div>
                        <div class="layui-input-inline formEleSpliceUnit form-select" style="height: 22px; width: 72px;margin-top: 5px; border: 1px solid #8DBDDC; border-radius: 3px;">
                           <select name="currency">
                                 <c:forEach items='${ddCurrency}' var="d">
                                      <option value='${d.dicCode}' 
                                          <c:if test="${d.dicCode eq subac.currency}">selected</c:if>
                                      >${d.dicName}</option>
                                 </c:forEach>
                            </select>
                        </div>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="star">*</span>拟投资阶段：</label>
                <div class="layui-input-block">
                  <c:forEach items='${ddfinanceStage}' var="d">
                    <input type="checkbox" name="financeStage" lay-verify="required" value='${d.dicCode}' title='${d.dicName}'  lay-skin="primary" <c:if test ='${fn:contains(subac.financeStage, d.dicCode)}'>checked</c:if>>
                  </c:forEach>
                </div>
            </div>
            <div class="layui-form-item2">
                <label class="layui-form-label"><span class="star">*</span>拟投资行业：</label>
                <div class="layui-input-block">
                   <a href="javascript:void(0)"  id="select_investors" style="display:inline-block;padding-top: 6px;color: #ff0000">&nbsp;&nbsp;选择主要投资行业</a>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
		          <div class="layui-input-block">
		               <ul class="clearfix industryCheckbox" id="check_data"> </ul>
		       </div>
            </div>
              <div class="layui-form-item">
<!--            style="border-top: 1px dotted #ccc;heigth:20%;" -->
              <div class="form-title" style="width: 60%;"><span>导出文件</span><span style="font-size: 12px;">请导出授权书，加盖公章后再进行上传。</span></div>
            </div>   
            <div style="margin-left:3%;"> 
                   <div class="layui-form-item">
                   <label class="layui-form-label" style="width: 17%;">导出授权书：</label>
	                    <button type="button"  class="layui-btn layui-btn-small uploadButton" id="downLoadWarrantPdf"  lay-submit lay-filter="exportWarrant"><i class="layui-icon">&#xe61e;</i>&nbsp;&nbsp;&nbsp;导出授权书 &nbsp;&nbsp;&nbsp;</button>
	                  <span style="font-size: 10px;color: #FF6838;">*注：信息录入完成后可生成授权书</span>
                   </div>
           
           </div>
            <div class="layui-form-item" >
                    <div class="form-title"><span>上传文件</span></div>
            </div>          
          <div style="margin-left:3%;">
		          <div class="layui-form-item">
                     <label class="layui-form-label" style="width: 17%;">授权书：</label>
                     <input type="hidden" id="fileupHidden" name="creditAuthorizationPath" value='${subac.creditAuthorizationPath}' autocomplete="off" class="layui-input">
                     <input type="hidden" id="creditAuthorizationName" name="creditAuthorizationName" value="${subac.creditAuthorizationName}">       
                    <button type="button" class="layui-btn layui-btn-small uploadButton" id="creditAuthUploadFile">
                         <i class="layui-icon">&#xe67c;</i>上&nbsp;传&nbsp;&nbsp;&nbsp;授&nbsp;&nbsp;权&nbsp;书
                    </button>
                    <a id="downloadFile" style="cursor:pointer;font-weight:bold;color:#0D4F92;text-decoration: underline;" download="${subac.creditAuthorizationName}"
                                    href="${subac.creditAuthorizationPath}">${subac.creditAuthorizationName}
	                  </a>
                   </div>
             </div>
             <div style="margin-left:3%;">
                 <div class="layui-form-item">
                      <label class="layui-form-label" style="width: 17%;">营业执照：</label>
                      <div class="layui-input-block clearfix" style="width: 300%;">
                                   <input type="hidden" id="imgHidden" name="licensePath" value='${subac.licensePath}' autocomplete="off" class="layui-input">
                                   <input type="hidden" id="fileName" name="fileName" value="${subac.fileName}">
                             <button type="button" class="layui-btn layui-btn-small uploadButton" id="licenseUploadBtn">
                               <i class="layui-icon">&#xe67c;</i>&nbsp;上传营业执照&nbsp;&nbsp;
                            </button>
                        <a  id="preview" name = "preview" download="${subac.fileName}" style="cursor:pointer;font-weight:bold;color:#0D4F92;text-decoration: underline;" href="${subac.licensePath}">${subac.fileName}</a>
                    </div>
                </div>
                
           </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block buttonWrap">
                    <button class="layui-btn submitBtn" lay-submit lay-filter="investor">&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;交&nbsp;&nbsp;</button>
                    <button type="button" class="layui-btn closeBtn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
                </div>
            </div>
        </form>
    </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/script/layui/layui.all.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/script/layui/layer.cu.js" type="text/javascript"></script>
<script>
	function delLi(id, index) {
		$('#industry_ul li').each(function (index,element) {
	        var in_id = $(this).attr("id");
	        if(in_id ==id){
	        	  $(this).remove();
	        }
	    });
	}
	
	
     var industCheckDate=new Array();
     var industrList=new Array();
     function addIndustData(industryAaary) {
    		
    		for(var i =0;i<industryAaary.length;i++){
    			industCheckDate.push(industryAaary[i]);
    		}
    		industCheckDate=industCheckDate.unique3();
    		return industCheckDate;
     }
     //数组去重
     Array.prototype.unique3 = function () {
       var res = [];
       var json = {};
       for (var i = 0; i < this.length; i++) {
           if (!json[this[i]]) {
               res.push(this[i]);
               json[this[i]] = 1;
           }
       }
       return res;
     }
     function getIndustryIdArray(){
    		var str=industCheckDate.toString()
    		return str;
     }
     function addhtml(nodes){
    		var list =  $(".queryMore1").parent().siblings(".termsList");
    	    var index=1;
    		var industryHtml = '';//行业选项

    		nodes.each(function () {
    	        var id = $(this).attr("id");
    	        var name = $(this).attr("name");
    	        var val=$.inArray(id, industCheckDate) ;
    	        if(val=='-1'){
    	        	var val1=$.inArray(id, industrList) ;
    	        	if(val1=='-1'){
    	                industryHtml += '<li id="' + id + '" name="'+name + '" onclick="delLi(' +"'"+ id +"'"+ ',' + index + ')">' + name + '<a><i class="layui-icon">&#x1007;</i> </a></li>';
    	        	}else{
    	        		$('#' +id).addClass("active");
    	        	}
    	        }
    	    });
    		$("#dd_industry").append(industryHtml);
    		 var showAll = list.find("ul").height();
    	    list.animate({height: showAll});

    }
     
   
   
   
	
	//设置select控件选中 
	function set_select_checked(selectId, checkValue){  
	    var select = document.getElementById(selectId);  
	    for (var i = 0; i < select.options.length; i++){  
	        if (select.options[i].value == checkValue){  
	            select.options[i].selected = true;  
	            var form = layui.form;
	            form.render('select');
	            break;  
	        } 
	    }  
	}
	//回显投资阶段
		function set_checkBox_checked(checkValue){  
		var	result=checkValue.split(",");  
	    for(var i=0;i<result.length;i++){
    		$("input:checkbox[value='"+result[i]+"']").attr("checked","true");
    		layui.form.render(); //重新渲染显示效果
    		}
	}
	 //回显行业
    function showLi(industryVo) {
        	 var treeType =0;
         	 var str = '';
              str += '<div class="tj_fan tj_jg tj_tck2_result">';
              str += '<ul id="industry_ul">';
                 var nodes=industryVo;
                 if(nodes!=null&&nodes!=""&&nodes!=undefined){
                	 nodes =JSON.parse(nodes);
                 }
                 var index =1;
                 for (var i=0;i<nodes.length;i++){
 	                 if(nodes[i].name!="--"){
 	                     str += '<li id="' + nodes[i].id + '" name="'+nodes[i].name+'" onclick="delLi(' +"'"+ nodes[i].id +"'"+ ',' + index + ')">' + nodes[i].name + '<a><img src="../static/image/data_delete.jpg"/></a></li>';
 	                     industCheckDate.push(nodes[i].id );

 	                 }
                  }  
              str += '</ul></div></div>';
              $('#check_data').html(str);
     }
	
    $(function (){
    	var layer=layui.layer;
    	var industryArr;
    	  // 上传文件控件
        var upload = layui.upload;
    	
    	//指定允许上传的文件类型 上传征信授权书
        var  creditAuthUpload=upload.render({
             elem: '#creditAuthUploadFile'
//              , exts: 'jpg|jpeg|png|bmp|gif|txt|TXT|xls|XLS|xlsx|docx|doc|pdf'
             ,data : {"fileType" : 'zxsqs'}
             , url: '/SRRPBusinesWeb/index/fileUpload' //上传接口
             , accept: 'file' //普通文件
             , auto: false
             , size: 1024 * 128  //设置文件上传的大小最大为128M
             , before: function () {
                 $("#downloadFile").text("文件上传中请稍候…………");
                 document.getElementById("downloadFile").style.color = '#FF6838';
             }
             ,choose: function(obj){
                 //将每次选择的文件追加到文件队列
                 var files = obj.pushFile();
                 //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                 obj.preview(function(index, file, result){
                   if(file.size == 0){
                 	  $("#downloadFile").text("请上传非空文件！");
                 	  document.getElementById("downloadFile").style.color = '#FF6838';
                   }else{
                 	  obj.upload(index, file);
                   }
                 });
               }
             , done: function (res) {
                 if (res.code == 0) {
                 	layer.msg(res.name + "上传成功了");
                     document.getElementById("downloadFile").style.color = '#0D4F92';
                     $("#fileupHidden").attr("value", res.url);
                     $("#creditAuthorizationName").attr("value", res.name);
                     $("#downloadFile").text(res.name)
                     $("#downloadFile").attr("href",res.url);
                     $('#downloadFile').attr('download',res.name);
                 }else{
                 	$("#downloadFile").text("请上传非空文件！");
                 }
             }
         });
        //执行实例 上传营业执照
        var uploadInst = upload.render({
                   elem: '#licenseUploadBtn' //绑定元素
//                    , exts: 'jpg|jpeg|png|bmp|gif|txt|TXT|xls|XLS|xlsx|docx|doc|pdf'
        			,data : {"fileType" : 'yyzz'}
                   , accept: 'file' //普通文件
                   , size: 1024 * 128  //设置文件上传的大小最大为128M
       			,url: '/SRRPBusinesWeb/index/fileUpload' //上传接口
                   , done: function (data) {
                   	//上传完毕回调
                   	if(data.code == 0){
                           $('#imgHidden').attr('value',data.url);
                           $('#fileName').attr('value',data.name);
                           $('#preview').text(data.name);
                           $("#preview").attr("href",data.url);
                           $('#preview').attr('download',data.name);
                       }
                   }
                   , error: function (data) {
                   	alert(data);
                       //请求异常回调
                   }
          });
      
    	
    	//选择行业弹出窗口，返回html
    	$("#select_investors").on("click", function () {
    		layer.open({
                title: '行业分类选择',
                //调用行业分类数据接口
                content: "/SRRPBusinesWeb/finacingManage/checkIndustry" ,
                area: ['80%', '95%'],
                offset: ['5%', '5%'],
                type:2,
                scrollbar: false,
                end: function () {           //关闭弹出层触发
                  }
                });

        });

    	//三级联动
    	var cityNodes='${areaCity}';
    	var countyNodes='${areaCounty}';
    	
    	//获取市
    	function getCityChildren(parent){
    		var childrenStr="";
    		var childrenData=JSON.parse(cityNodes);
    		var areaCity="${investor.areaCity}";
    		for(var i=0;i<childrenData.length;i++){
    	        if(childrenData[i].parentcode==parent){
    	        	
    	        	if(childrenData[i].code==areaCity){
    	            	childrenStr+='<option value="'+ childrenData[i].code +'" selected>' + childrenData[i].name + '</option>';
    	        	}else{
    	            	childrenStr+='<option value="'+ childrenData[i].code +'" >' + childrenData[i].name + '</option>';

    	        	}
    	        	 
    	        }
    	    }
    	    return childrenStr;
    	}
    	//获取县区
    	function getCountyChildren(parent){
    		var childrenStr="";
    		var childrenData=JSON.parse(countyNodes);
    		var areaCounty="${investor.areaCounty}";
    		for(var i=0;i<childrenData.length;i++){
    	        if(childrenData[i].parentcode==parent){
    	        	if(childrenData[i].code==areaCounty){
    	            	childrenStr+='<option value="'+ childrenData[i].code +'" selected>'  + childrenData[i].name + '</option>';
    	        	}else{
    	            	childrenStr+='<option value="'+ childrenData[i].code +'" >' + childrenData[i].name + '</option>';

    	        	}
    	        	 
    	        }
    	    }
    	    return childrenStr;
    	}
    		
        	
        //调用日期控件
        var laydate = layui.laydate;
        laydate.render({
            elem: '#registeTime' //指定元素
            ,max: 0  //当前时间为选择最大时间
        });
        var table = layui.table;
		layui.use('form', function(){
			    var form = layui.form;
			    form.on('select(trusteeshipRadio)', function(data){
				    	var options = data.value;
				        if (options == '1') {
				        	document.getElementById("trusteeshipid").style.visibility="hidden";
				        } else {
				        	document.getElementById("trusteeshipid").style.visibility="visible";
			        		document.getElementById("trusteeshipid").value='';//回显托管机构
				        }
			    });   
			    if("" !="${investor.areaProvince}"){
					var options="${investor.areaProvince}";
					console.log(options);
					var childrenDataStr="";
					childrenDataStr+=getCityChildren(options);
		  		    $("#areaCity").append(childrenDataStr);
		  		    
		  		     var areaCity="${investor.areaCity}";
					var childrenDataStr1="";
					childrenDataStr1+=getCountyChildren(areaCity);
		  		    $("#areaCounty").append(childrenDataStr1);
				    form.render('select');
				}
			 
			 //选择市
			 form.on('select(areaProvince)', function(data){
			   $("#areaCity").html("");
			   $("#areaCounty").html("");
			   var childrenDataStr="";
		       var options = $("#areaProvince option:selected").val();
		       childrenDataStr='<option value="">请选择市</option>';
		       childrenDataStr+=getCityChildren(options);
		       $("#areaCity").append(childrenDataStr);
		       $("#areaCounty").append('<option value="">请选择县/区</option>');
		       form.render('select');
		
		})
		//选择县/区
		form.on('select(areaCity)', function(data){
			   $("#areaCounty").html("");
			   var childrenDataStr="";
		       var options = $("#areaCity option:selected").val();
		       childrenDataStr='<option value="">请选择县/区</option>';
		       childrenDataStr+=getCountyChildren(options);
		       $("#areaCounty").append(childrenDataStr);
		       form.render('select');
		})
			    //自定义验证规则
		        form.verify({
		        	userName: function (value, item) { //value：表单的值、item：表单的DOM对象
		        		var myReg = /^[a-zA-Z0-9_]{0,}$/;
		        		if(value==''||value==null||value==undefined){
				            return "请输入用户名";
				        }else if(ajaxValidatorUseName(value)=='1'){
				        		return "用户名已存在,请勿重复注册！";
			           }else 
			        	   if (!myReg.test(value)) {
			        		   return "用户名不能含有中文或特殊字符！";
			        	   } else{
			        	   return false;
			           }
		            },
// 		            subacName: function (value, item) { //value：表单的值、item：表单的DOM对象
// 		        		 if(ajaxValidatorSubacName(value)=='1'){
// 				        		return "基金名称已存在,请勿重复注册！";
// 				           }else{
// 				        	   return false;
// 				           }
// 		            },
		            amacRecord:function(value,item){
		            	var regIccFilingNo = /^[S][A-Za-z0-9]{5}$/;
		            	if(value!='无'){
		            		if(!(regIccFilingNo.test(value))){
						    	  return"请正确输入以S开头的基金编号(中基协)";
						    }
		            	}
		            	
		            },certno:function(value, item){
		            	     var  objRegExp;
		                	 if(value.length==18){
		                		 objRegExp= /[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}/;
		                		 
			        		 }else  if(value.length==10){
			        			 objRegExp= /[A-Za-z0-9]{8}-[A-Za-z0-9]/;
			        		 }else{
			        			 return "请输入符合规范的统一社会信用代码";
			        		 }
		                	 if(!objRegExp.test(value)){
		                		 return "统一社会信用代码格式不正确";
		                	 }
		                	 if(ajaxValidatorCertno(value)=='1'){
		                		 return "统一社会信用代码已在股权平台注册,请重新输入！";
		                	 }
		                	 
		            },
		            legalRepresentative: function (value, item) { //value：表单的值、item：表单的DOM对象
		        		 if(value.length>'20'){
				        		return "法人代表长度不能大于20位！";
				           }
		            },
		            legalRepresentativeCall: function (value, item) { //value：表单的值、item：表单的DOM对象
						  var phoneReg =/^[1][3,4,5,7,8][0-9]{9}$/;
		        		 if(!(phoneReg.test(value))){
				        		return "法人代表得电话号码无效，请重新输入";
				           }
		            },
		            investmentProjects:function(){
		            	var regNumber  = /^[0-9]*$/;
		            	var investmentProjectsVal = $("#investmentProjects").val();
		            	var implementExitProjectVal = $("#implementExitProject").val();
		            	if(investmentProjectsVal=="" || investmentProjectsVal==null || investmentProjectsVal==undefined){
			            		return"请输入投资项目数量";
		            	}else if(!regNumber.test(investmentProjectsVal)){
		            		    return"投资项目数为整数"
		            	}else{
		            		if(implementExitProjectVal!="" && implementExitProjectVal!=null && implementExitProjectVal!=undefined){
		            			if(parseInt(implementExitProjectVal)>parseInt(investmentProjectsVal)){
			            			return"实现退出项目数应小于投资项目数";
			            		}
			            	}
		            	}
		            	
		            },
		            implementExitProject:function(){
		            	var regNumber  = /^[0-9]*$/;
		            	var investmentProjectsVal = $("#investmentProjects").val();
		            	var implementExitProjectVal = $("#implementExitProject").val();
		            	if(implementExitProjectVal=="" || implementExitProjectVal==null || implementExitProjectVal==undefined){
		            		return"请输入实现退出项目数";
		            	}else if(!regNumber.test(implementExitProjectVal)){
		            		return"实现退出项目数为整数";
		            	}else{
                            if(investmentProjectsVal!="" && investmentProjectsVal!=null && investmentProjectsVal!=undefined){
		            			if(parseInt(implementExitProjectVal)>parseInt(investmentProjectsVal)){
		            				return"实现退出项目数应小于投资项目数";
		            			}
		            		}
	            		}	
		            },
		            cumulativeInvestment: function (value, item) { //value：表单的值、item：表单的DOM对象
		        		var cumulativeInvestmentVal = $("#cumulativeInvestment").val();
		        		var manageCapitalReg =/^\d+(\.\d{1,2})?$/;
		            	if(cumulativeInvestmentVal==''||cumulativeInvestmentVal==null||cumulativeInvestmentVal==undefined){
				            return "请输入累计投资金额";
				        }else if(!(manageCapitalReg.test(cumulativeInvestmentVal))){
				        	return"累计投资金额只能是最多保留两位小数的数字";
				        }
		            },
		            capitalMin: function (value, item) { //value：表单的值、item：表单的DOM对象
		        		 if(amountOfMoney()=='0'){
				        		return "最小金额不能为空。";
				           }else if(amountOfMoney()=='1'){
				        	   return "最小金额只能是最多保留两位小数的数字。";
				           }else if(amountOfMoney()=='2'){
				        	   return "最大金额不能为空。";
				           }else if(amountOfMoney()=='3'){
				        	   return "最大金额只能是最多保留两位小数的数字。";
				           }else if(amountOfMoney()=='4'){
				        	   return "最小金额应该小于最大金额。";
				           }else{
				        	   return false;
				           }
		            }
		        });
			  form.on('submit(exportWarrant)', function(data){
		        	 
						  document.getElementById("form1").action="/SRRPBusinesWeb/investor/exportJijinWarrantInfor";
						  document.getElementById("form1").submit();
					  
						 
				  });
			  var lock=true;
			  //监听提交
			  form.on('submit(investor)', function(data){
				  var stageFlag='0';
				  var tradeFlag='0';
				  var financeStage_value="";
				  var financeTrade_value="";
				  var reg = /(^[1-9]([0-9]{0,3})(\.[0-9]{2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
				  //获取拟投资阶段复选框选中的值
				  $("input:checkbox[name='financeStage']:checked").each(function() {
		    		  var financeStageChecked = $(this).val();
		    		  if(financeStageChecked == null || financeStageChecked == undefined || financeStageChecked == ''){
		    			  financeStage_value=financeStageChecked;
		    		  }else{
		    			  financeStage_value=financeStageChecked+","+financeStage_value;
		    		  }
		    	  });
				  //获取拟投资行业弹出框选中的值
				  var new_financeTrade = [];
		          $('#industry_ul li').each(function (index,element) {
		              var in_id = $(this).attr("id");
		              new_financeTrade.push(in_id);
		          });
		          financeTrade_value=new_financeTrade.join(",");
				  data.field.financeTrade=financeTrade_value;
				  data.field.financeStage=financeStage_value;
				  if(financeTrade_value!=null && financeTrade_value!='' && financeTrade_value!=undefined){
					  tradeFlag='1';
				  }
				  if(financeStage_value!=null && financeStage_value!='' && financeStage_value!=undefined){
					  stageFlag='1';
				  }
				  if(tradeFlag=='1' && stageFlag=='1'){
					  $.ajax({
						    type : "post",
						    async : false,
							url : "/creditplatformweb/inverstorUser/addUser",
						    data : {"investorData" : JSON.stringify(data.field)},
						    success : function(data) {
								layer.confirm('用户创建成功,初始化密码为:'+'12345678',{
						            btn:['确定','取消']
							        },function(index){
						        	  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						        	  parent.location.reload();
						        	  parent.layer.close(index); //关闭layer(关闭当前窗口)
						        	},function(index){
						        	    location.reload(); 
						        	    layer.close(index);
						        	}
						        )
						     }
					  });
					  return false; 
				   }else{
					   pointOut();
				   } 
			      return false; 
			    });
			});
        //关闭后直接进行全查请求(刷新编辑页面)
		$(".closeBtn").on("click", function () {
           var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
           parent.layer.close(index); //关闭layer(关闭当前窗口)
        });
		function ajaxValidatorUseName(userName){
			var code;
			  $.ajax({
				   type : "post",
				  async : false,
					url : "/creditplatformweb/inverstorUser/ajaxValidatorUseName",
				   data : {"userName" : userName},
				success : function(data) {
					data = JSON.parse(data);
			        if (data.code == '00000') {
			        	code= "0"; 
			        }else if (data.code == '00001') {
			        	code= "1"; 
			       	} 
				          }
			        });
			  return code;
		}
		function ajaxValidatorSubacName(subacName){
			var code;
			  $.ajax({
				   type : "post",
				  async : false,
					url : "/creditplatformweb/inverstorUser/ajaxValidatorSubacName",
				   data : {"subacName" : subacName},
				success : function(data) {
					data = JSON.parse(data);
			        if (data.code == '00000') {
			        	code= "0"; 
			        }else if (data.code == '00001') {
			        	code= "1"; 
			       	} 
				          }
			        });
			  return code;
		}
		function ajaxValidatorCertno(certno){
			var code;
			  $.ajax({
				   type : "post",
				  async : false,
					url : "/creditplatformweb/inverstorUser/ajaxValidatorCertno",
				   data : {"certno" : certno},
				success : function(data) {
					data = JSON.parse(data);
			        if (data.code == '00000') {
			        	code= "0"; 
			        }else if (data.code == '00001') {
			        	code= "1"; 
			       	} 
				          }
			        });
			  return code;
		}
		function pointOut(){
	    	 layer.open({
                 type: 1,
                 title: '', //不显示标题栏
                 offset: ['27%','42%'],
                 closeBtn: false,
                 shade: 0,
                 id: 'public_id', //设定一个id，防止重复弹出
                 moveType: 1,//拖拽模式，0或者1
                 time: 3000,//自动关闭时间
                 anim: 6,//弹出动画
                 resize: false,//不允许拉伸
                 content:'<div class="layui-layer layui-layer-dialog layui-layer-border layui-layer-msg layer-anim-06" id="layui-layer16" type="dialog" times="16" showtime="3000" contype="string" style="z-index: 19891030; top: 85.5px; text-align: center;"><div id="" class="layui-layer-content layui-layer-padding"><i class="layui-layer-ico layui-layer-ico5"></i>必选项不能为空</div><span class="layui-layer-setwin"></span></div>',
             });
	     }
		//金额的一系列校验
		function amountOfMoney(){
			var code;
			var manageCapitalReg =/^\d+(\.\d{1,2})?$/;
			var capitalMax = $("#capitalMax").val();
			var capitalMin = $("#capitalMin").val();
			if(capitalMin==''){
					code='0'
			}else if(!(manageCapitalReg.test(capitalMin))){
					code='1';
			}
			if(capitalMax==''){
				code='2'
		    }else if(!(manageCapitalReg.test(capitalMax))){
				code='3';
		    }
			if((capitalMin*100) > (capitalMax*100)){
					code='4';
			}
			return code;
		}
    });
    
    function inputOnBlur(){
		var inputValue = $("#subacName").val();
		$.ajax({
			   type : "post",
			  async : false,
				url : "/creditplatformweb/inverstorUser/subacNameValue",
			   data : {"inputValue" : inputValue}, 
			success : function(data) { 
				data = JSON.parse(data);
		        if(data.code=='00000'){
		    		document.getElementById("subacName").value=data.fundName;//基金名称
// 		    		document.getElementById("registAddress").value=data.registAddressStr;//回显注册地址
		    		document.getElementById("amacRecord").value=data.iccFilingNo;//回显中基协备案号
		        	document.getElementById("registeTime").value=data.registDateString;//时间
		        	document.getElementById("investmentProjects").value=data.investmentProjects;//回显投资项目数量 
		        	document.getElementById("cumulativeInvestment").value=data.cumulativeInvestment;//回显累计投资金额        
		        	document.getElementById("implementExitProject").value=data.implementExitProject;//回显实现退出项目数
		        	document.getElementById("capitalMin").value=data.manageCapitalMin;//回显最小资本量
		        	document.getElementById("capitalMax").value=data.manageCapitalMax;//回显最大资本量
		        	if(data.trusteeship!="无"){
		        		document.getElementById("trusteeshipid").style.visibility="visible";
		        		document.getElementById("trusteeshipid").value=data.trusteeship;//回显托管机构
		        		set_select_checked('trusteeshipRadio','0');//回显有无托管机构
		        	}else{
		        		document.getElementById("trusteeshipid").style.visibility="hidden";
		        		set_select_checked('trusteeshipRadio','1');//回显有无托管机构
		        	}
		        	set_select_checked('subacType',data.fundType);//回显下拉框   
		        	set_select_checked('ciCurrency',data.ciCurrency);//回显累计投资金额币种;
		        	set_select_checked('currency',data.currency);//回显币种
		        	//多选框
		        	showLi(data.industryVo);//回显行业
		        	set_checkBox_checked(data.investStage);//回显阶段
		            return false;
		        }else{
		        	document.getElementById("registeTime").value=null;//清空输入框
// 		        	document.getElementById("registAddress").value=null;//清空注册地址
		    		document.getElementById("amacRecord").value=null;//清空中基协备案号
		    		document.getElementById("investmentProjects").value=null;//回显投资项目数量 
		        	document.getElementById("cumulativeInvestment").value=null;//回显累计投资金额        
		        	document.getElementById("implementExitProject").value=null;//回显实现退出项目数
		    		document.getElementById("capitalMin").value=null;//清空最小资本量
		        	document.getElementById("capitalMax").value=null;//清空最大资本量
		        	document.getElementById("trusteeshipid").style.visibility="visible";
		        	document.getElementById("trusteeshipid").value="";//清空托管机构
		        	set_select_checked('subacType','');//情况下拉框
		        	set_select_checked('currency','');//币种清空
		        	set_select_checked('ciCurrency','');//币种清空
		        	set_select_checked('trusteeshipRadio','');//有无托管机构下拉框清空
		        	//清空投资阶段
		        	$("input[type=checkbox][checked]").each(function(){ //由于复选框一般选中的是多个,所以可以循环输出 
		        		$(this).removeAttr("checked");
		        		layui.form.render(); //重新渲染显示效果
					}); 
		        	//清空行业
		        	 $('#industry_ul li').each(function (index,element) {
			              var in_id = $(this).attr("id");
			              delLi(in_id,index);
			          });
		            return false;
		        }
		        
			 } 
		 }); 
	}
</script>
</body>
