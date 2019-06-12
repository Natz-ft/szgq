<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>股权投资机构登记表</title>
<style type="text/css">
body {  
    font-family: SimSun;
	font-size: 12px;
	text-align:left;
	margin: 0 auto;
	text-align:center;
}  
.check-titl{
    margin-left: 15px;
}
.redio-titl{
}

.span-titl {
    width: 110px;
    display: inline-block;
    text-align: left;
}
.span-titl-end {
    width: 150px;
    display: inline-block;
    text-align: left;
}
.span-titl0 {
    width: 120px;
    display: inline-block;
    text-align: left;
}
.span-titl1 {
    width: 100px;
    display: inline-block;
    text-align: left;
}
.span-titl2 {
    width: 130px;
    display: inline-block;
    text-align: left;
}
.check-display{
display:none;
}
.span-titl-end1 {
    width: 115px;
    display: inline-block;
    text-align: left;
}
.table1 {
	margin: auto;
	width: 100%;
	border-collapse: collapse;
	border: none;
	table-layout:fixed;
}
.td1 {
	border: 1px solid #444444;
	font-size: 12px;
	margin-left: 5px;
	padding: 6px;
	word-wrap:break-word;
}

 .title {
	text-align: left;
	font-weight: bold;
	font-size: 18px;
	color: #000000;
	padding-bottom: 10px;
}
 .itemname {
	text-align: left;
	font-weight: bold;
	font-size: 12px;
	color: #000000;
}

.itemvalue {
	text-align: left;
	font-size: 12px;
	color: #000000;
	font-weight: bold;
}


@page {
	size: 8.5in 11in;
	@
	bottom-center
	{
	content
	:
	"page "
	counter(
	page
	)
	" of  "
	counter(
	pages
	);
</style>
</head>
<body>

    <div>
    <div style="" class="title">
		表1：苏州股权融资服务平台股权投资机构注册登记表（必填）
	</div>
        <div>
           
            <table class="table1" >
			<tr>
				<td class="itemname td1">
					机构名称●
				</td>
				<td class="itemvalue td1" colspan="4">
					${(investor.name)?default("")}
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					组织形式●
				</td>
				<td class="itemvalue td1"  colspan="4" style="word-wrap:break-word;">
					
					 <#list ddOrgForm as df>
					 <input type="checkbox" name="resourceType"  id="resourceType"  value="${(df.dicCode)?default("")}" 
			        <#if  investor.organizationalForm =df.dicCode>  checked="checked" </#if> readonly="readonly" /> <span >${(df.dicName)?default("")}</span>
					 </#list>
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					组织机构代码●
				</td>
				<td class="itemvalue td1"  colspan="2">
					${(investor.certno)?default("")}
				</td>
				<td class="itemname td1">
					成立日期●
				</td>
				<td class="itemvalue td1"  >
				<#if investor.registeTime ? exists>
					${investor.registeTime?string("yyyy-MM-dd")}
			    <#else>--</#if>
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					所属区域●
				</td>
				<td class="itemvalue td1"  colspan="4">
				${(investor.areaCode)?default("")}
				</td>
			</tr>
			
			<tr>
				<td class="itemname td1">
					注册地址●
				</td>
				<td class="itemvalue td1"  colspan="4">
				${(investor.registeredAddress)?default("")}
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					办公地址●
				</td>
				<td class="itemvalue td1"  colspan="4">
				${(investor.officeAddress)?default("")}
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					注册资本/认缴出资总额（万元）
				</td>
				<td class="itemvalue td1"  colspan="2">
				${(investor.registeredCapital)?default("")}
				</td>
				<td class="itemname td1"  >
				实缴资本（万元）●
				</td>
				<td class="itemvalue td1"  >
				${(investor.paidCapital)?default("")}
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					管理机构
				</td>
				<td class="itemvalue td1"  colspan="4">
				<#if investor.manageOrg ? exists>
					${investor.manageOrg?default("")}
			    <#else>
				无<input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly" checked="checked"/>
				</#if>
				</td>
				
			</tr>
			<tr>
				<td class="itemname td1">
					托管机构
				</td>
				<td class="itemvalue td1"  colspan="4">
				<#if investor.trusteeship ? exists>
					${investor.trusteeship?default("")}
			    <#else>
				无<input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly" checked="checked"/>
				</#if>
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					主要投资领域●
				</td>
				<#assign size=ddIndustrty?size>
				<#assign iSuetemp=0> 
				<td class="itemvalue td1"  colspan="4" >
				    <#list ddIndustrty as df>
				        <#assign flag=df.dicName?contains("&")?string> 
				        <#if flag="true">
				            <#assign dicname=df.dicName?replace("&","&amp;")> 
				         <#else>
				            <#assign dicname=df.dicName>    
				        </#if>  
				         <#if (iSuetemp) % 4 == 0>
				         	<br></br><input type="checkbox" name="resourceType"  id="resourceType"  value="${(investor.financeTrade)?default("")}" 
				        	 <#if  investor.financeTrade =df.dicCode>  checked="checked" </#if> readonly="readonly" /> <span class="span-titl">${dicname?default("")}</span>
				         <#elseif iSuetemp==size-3>
				             <input type="hidden" name="resourceType"  id="resourceType"  value="${(investor.financeTrade)?default("")}" /> <span class="span-titl0"></span>
				             <input type="checkbox" name="resourceType"  id="resourceType"  value="${(investor.financeTrade)?default("")}" 
				        	 <#if  investor.financeTrade =df.dicCode>  checked="checked" </#if> readonly="readonly" /> <span class="span-titl-end">${dicname?default("")}</span>
				             <br></br>
				         <#elseif iSuetemp==size-2>
				              <input type="checkbox" name="resourceType"  id="resourceType"  value="${(investor.financeTrade)?default("")}" 
				        	  <#if  investor.financeTrade =df.dicCode>  checked="checked" </#if> readonly="readonly" /> <span class="span-titl2">${dicname?default("")}</span>
				             <input type="hidden" name="resourceType"  id="resourceType"  value="${(investor.financeTrade)?default("")}" /> <span class="span-titl1"></span>
				         <#else>
				            <input type="checkbox" name="resourceType"  id="resourceType"  value="${(investor.financeTrade)?default("")}" 
				        	 <#if  investor.financeTrade =df.dicCode>  checked="checked" </#if> readonly="readonly" /> <span class="span-titl">${dicname?default("")}</span>
				         
				         </#if> 
				          <#assign iSuetemp=iSuetemp+1> 
						 </#list>
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					主要投资阶段
				</td>
				<td class="itemvalue td1"  colspan="4">
				<#list ddInvestment as df>
					<span >${(df.dicName)?default("")}</span> <input type="checkbox" name="resourceType"  id="resourceType"  value="${(investor.financeStage)?default("")}" 
			        <#if  investor.financeStage =df.dicCode>  checked="checked" </#if> readonly="readonly" /> 
					 </#list>
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					法定代表人/执行事务合伙人●
				</td>
				<td class="itemvalue td1"  colspan="4">
				${investor.legalRepresentative?default("")}
				</td>
			</tr>
			<tr >
				<td class="itemname td1" rowspan="2">
					日常联系人
				</td>
				<td class="itemname td1"  >
				姓名●
				</td>
				<td class="itemvalue td1"  >
				${investor.relName?default("")}
				</td>
				<td class="itemname td1"  >
				职务
				</td>
				<td class="itemvalue td1" >
				${investor.post?default("")}
				</td>
				
			</tr>
			<tr >
				<td class="itemname td1"  >
				联系电话●
				</td>
				<td class="itemvalue td1"  >
				${investor.relPhone?default("")}
				</td>
				<td class="itemname td1"  >
				电子邮箱
				</td>
				<td class="itemvalue td1" >
				${investor.email?default("")}
				</td>
			</tr>
			<tr>
				<td class="itemname td1" rowspan="2">
					运营资质●
				</td>
				
				<td class="itemname td1" colspan="4">
				是否在中国证券基金业协会履行基金管理人登记手续：
				<span class="check-titl">是</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly" <#if  investor.operationQualification1 ="1">  checked="checked" </#if>/>
				<span class="check-titl">否</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly" <#if  investor.operationQualification1 ="2">  checked="checked" </#if>/>
				
				<br></br>
				基金管理人登记编号：${investor.operationQualification2?default("")}
				</td>
				
			</tr>
			<tr >
				<td class="itemname td1" colspan="4">
				机构是否有过不良诚信记录：<span class="check-titl">是</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly" <#if  investor.operationQualification3 ="1">  checked="checked" </#if>/>
				<span class="check-titl">否</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly" <#if  investor.operationQualification3 ="2">  checked="checked" </#if>/>
				
				</td>
			</tr>
			<tr>
				<td class="itemname td1" rowspan="3">
					团队成员
				</td>
				
				<td class="itemname td1" colspan="2">
				团队总人数
				</td>
				<td class="itemvalue td1" colspan="2">
				${investor.teamCount?default("")}
				</td>
				
			</tr>
			<tr >
				<td class="itemname td1" colspan="2">
				具备3年以上股权投资或相关工作经验的高级管理人数
				</td>
				<td class="itemvalue td1" colspan="2">
				  ${investor.seniorManagement?default("")}
				</td>
			</tr>
			<tr >
				<td class="itemname td1" colspan="2">
				团队成员是否有过不良诚信记录
				</td>
				<td class="itemvalue td1" colspan="2">
				<span class="check-titl">是</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly" <#if  investor.creditUnhealthy ="1">  checked="checked" </#if>/>
				<span class="check-titl">否</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly" <#if  investor.creditUnhealthy ="2">  checked="checked" </#if>/>
				</td>
			</tr>
			<tr>
				<td class="itemname td1" >
				管理机制
				</td>
				<td class="itemname td1" colspan="4">
				是否具备严格合理的投资决策程序、风险控制机制以及健全的财务管理制度：<span class="check-titl">是</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly" <#if  investor.mechanism ="1">  checked="checked" </#if>/>
				<span class="check-titl">否</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly" <#if  investor.mechanism ="2">  checked="checked" </#if>/>
				</td>
				
			</tr>
		</table>
		<div>
		  &nbsp;
		</div>
		<div>
		  ●表示为对外公示项，投资机构填写后会统一对外公示。
		</div>
		<div>
		  &nbsp;
		</div>
		<div>
		  &nbsp;
		</div>
        </div>
        
         <div style="" class="title">
		表2：过往业绩情况表（选填）
	</div>
        <div>
            <#if  investorManageAchievement??>  
             <#assign mentrow=investorManageAchievement?size+1>
            <#else>
            <#assign mentrow=2>
             </#if>
            <table class="table1" >
			<tr>
				<td class="itemname td1" rowspan='${mentrow}'>
					管理业绩
				</td>
				<td class="itemname td1">
					管理的基金名称
				</td>
				<td class="itemname td1" >
					注册时间
				</td>
				<td class="itemname td1" >
				注册地址
				</td>
				<td class="itemname td1" >
				管理资金规模（万元）
				</td>
				<td class="itemname td1" >
				已投项目
				</td>
				<td class="itemname td1" >
				投资行业
				</td>
				<td class="itemname td1" >
				回报率
				</td>
				<td class="itemname td1" >
				投资进度（%）
				</td>
			</tr>
			<#if  investorManageAchievement??>  
			<#list investorManageAchievement as ima>
			<tr>
				<td class="itemvalue td1">
					 ${ima.fundName?default("")}
				</td>
				<td class="itemvalue td1" >
					 ${ima.registDate?default("")}
				</td>
				<td class="itemvalue td1" >
				 ${ima.registAddress?default("")}
				</td>
				<td class="itemvalue td1" >
				 ${ima.manageCapital?default("")}
				</td>
				<td class="itemvalue td1" >
				 ${ima.alreadyInvest?default("")}
				</td>
				<td class="itemvalue td1" >
				 ${ima.investTrade?default("")}
				</td>
				<td class="itemvalue td1" >
				 ${ima.rateReturn?default("")}
				</td>
				<td class="itemvalue td1" >
				  ${ima.InvestProgress?default("")}
				</td>
			</tr>
			</#list>
			
			<#else>
			<tr>
				<td class="itemvalue td1">
					
				</td>
				<td class="itemvalue td1" >
					
				</td>
				<td class="itemvalue td1" >
				
				</td>
				<td class="itemvalue td1" >
				
				</td>
				<td class="itemvalue td1" >
				
				</td>
				<td class="itemvalue td1">
					
				</td>
				<td class="itemvalue td1">
					
				</td>
				<td class="itemvalue td1">
					
				</td>
			</tr>
			</#if>
			<tr>
				<td class="itemname td1" rowspan="8">
					投资业绩
				</td>
				<td class="itemname td1" colspan="4">
					累计投资项目数量
				</td>
				<td class="itemvalue td1" colspan="4">
					 ${investorAchievement.projectCount?default("")}
				</td>
			</tr>
			<tr>
			    <td class="itemname td1" colspan="4">
					累计投资金额（万元）
				</td>
				<td class="itemvalue td1" colspan="4">
					${investorAchievement.investmentAmount?default("")}
				</td>
			</tr>
			<tr>
			    <td class="itemname td1" colspan="4">
					累计实现退出项目数量
				</td>
				<td class="itemvalue td1" colspan="4">
					${investorAchievement.projectQuitCount?default("")}
				</td>
			</tr>
			<tr>
			    <td class="itemname td1" colspan="4">
					其中：上市转让退出项目数
				</td>
				<td class="itemvalue td1" colspan="4">
					${investorAchievement.projectQuitCount1?default("")}
				</td>
			</tr>
			<tr>
			    <td class="itemname td1" colspan="4">
					协议转让退出项目数
				</td>
				<td class="itemvalue td1" colspan="4">
					${investorAchievement.projectQuitCount2?default("")}
				</td>
			</tr>
			<tr>
			    <td class="itemname td1" colspan="4">
					 被整体收购退出项目数
				</td>
				<td class="itemvalue td1" colspan="4">
					${investorAchievement.projectQuitCount3?default("")}
				</td>
			</tr>
			<tr>
			    <td class="itemname td1" colspan="4">
					 企业回购退出项目数
				</td>
				<td class="itemvalue td1" colspan="4">
					${investorAchievement.projectQuitCount4?default("")}
				</td>
			</tr>
			<tr>
			    <td class="itemname td1" colspan="4">
					清算退出项目数
				</td>
				<td class="itemvalue td1" colspan="4">
					${investorAchievement.projectQuitCount5?default("")}
				</td>
			</tr>
			<tr>
			     <td class="itemname td1" >
					综合实力、经营业绩、突出优势、核心人员的介绍●
				</td>
				<td class="itemvalue td1" colspan="8">
					${investorAchievement.corepersonnel?default("")}
				</td>
			</tr>
			<tr>
			     <td class="itemname td1"  colspan="9">
					<p>申请机构承诺对备案信息及相关材料的合法性、真实性、有效性负完全责任。</p>
					<br> </br>
					<br> </br>
                    <p>法定代表人/执行事务合伙人签名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请机构盖章：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                     <br> </br>
					<br> </br> 
					<br> </br> 
				</td>
			</tr>
		</table>
        </div>
    </div>
</body>
</html>