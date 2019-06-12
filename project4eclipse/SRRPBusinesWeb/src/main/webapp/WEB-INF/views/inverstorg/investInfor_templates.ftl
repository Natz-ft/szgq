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
.desc{
font-size: 12px;
text-align: left;
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
					 <input type="checkbox"  value="${(df.dicCode)?default("")}" 
			        <#if  investor.organizationalForm =df.dicCode>  checked="checked" </#if> readonly="readonly" /> <span >${(df.dicName)?default("")}</span>
					 </#list>
				</td>
			</tr>
			<tr>
			<#if  investor.certtype ='1'> 
			 	<td class="itemname td1">
					组织机构代码●
				</td>
		  </#if>
			
				<#if  investor.certtype ='2'> 
					 <td class="itemname td1">
						统一社会信用代码●
					 </td>
				</#if>
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
				${(investor.areaName)?default("")}
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
					机构类型●
				</td>
				<td class="itemvalue td1"  colspan="2">
				${(investor.orgTypeDicname)?default("")}
				</td>
				<td class="itemname td1"  >
				资本类型
				</td>
				<td class="itemvalue td1"  >
				${(investor.capitalTypeDicname)?default("")}
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					注册资本/认缴出资总额（万）●
				</td>
				<td class="itemvalue td1"  colspan="2">
				${(investor.registeredCapital)?default("")}&nbsp;&nbsp;${(investor.regCurrencyDicname)?default("")}
				</td>
				<td class="itemname td1"  >
				实缴资本（万）●
				</td>
				<td class="itemvalue td1"  >
				${(investor.paidCapital)?default("")}&nbsp;&nbsp;${(investor.pcCurrencyDicname)?default("")}
				</td>
			</tr>
			
			<tr>
				<td class="itemname td1">
					管理规模(亿)<br></br>（填写区间）●
				</td>
				<td class="itemvalue td1"  colspan="4">
				${investor.capital?default("")}
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					主要投资领域●
				</td>
				<#assign size=ddIndustrty?size>
				<#assign iSuetemp=0> 
				<td class="itemvalue td1"  colspan="4" >
				<#assign flag=investor.financeTradeDicname?contains("&")?string> 
				        <#if flag="true">
				            <#assign dicname=investor.financeTradeDicname?replace("&","&amp;")> 
				         <#else>
				            <#assign dicname=investor.financeTradeDicname>    
				        </#if>  
				    ${dicname?default("")}
				</td>
			</tr>
			<tr>
				<td class="itemname td1">
					主要投资阶段●
				</td>
				<td class="itemvalue td1"  colspan="4">
				 ${investor.financeStageDicname?default("")}
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
			<tr>
				<td class="itemname td1">
					法定代表人/执行事务合伙人联系电话(手机)
				</td>
				<td class="itemvalue td1"  colspan="4">
				
				${investor.legalRepresentativeCall?default("")}
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
				联系电话
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
				<span class="check-titl">是</span><input type="checkbox"   readonly="readonly" <#if  investor.operationQualification1 ="0">  checked="checked" </#if>/>
				<span class="check-titl">否</span><input type="checkbox"   readonly="readonly" <#if  investor.operationQualification1 ="1">  checked="checked" </#if>/>
				
				<br></br>
				基金管理人登记编号：${investor.operationQualification2?default("")}
				</td>
				
			</tr>
			<tr >
				<td class="itemname td1" colspan="4">
				机构是否有过不良诚信记录：<span class="check-titl">是</span><input type="checkbox"   readonly="readonly" <#if  investor.operationQualification3 ="0">  checked="checked" </#if>/>
				<span class="check-titl">否</span><input type="checkbox"   readonly="readonly" <#if  investor.operationQualification3 ="1">  checked="checked" </#if>/>
				
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
				从事股权投资且具备3年以上相关工作经验的人员数量（获得基金从业资格证）
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
				<#if investor.creditUnhealthy??>
				     <#assign flag1=investor.creditUnhealthy> 
				<#else>
				    <#assign flag1='2'> 
				</#if>
				<span class="check-titl">是</span><input type="checkbox"   readonly="readonly" <#if  flag1="0">  checked="checked" </#if>/>
				<span class="check-titl">否</span><input type="checkbox"   readonly="readonly" <#if  flag1 ="1">  checked="checked" </#if>/>
				</td>
			</tr>
			<tr>
				<td class="itemname td1" >
				管理机制
				</td>
				<#if investor.mechanism??>
				     <#assign flag2=investor.mechanism> 
				<#else>
				    <#assign flag2='2'> 
				</#if>
				<td class="itemname td1" colspan="4">
				是否具备严格合理的投资决策程序、风险控制机制以及健全的财务管理制度：<span class="check-titl">是</span><input type="checkbox"   readonly="readonly" <#if  flag2 ="0">  checked="checked" </#if>/>
				<span class="check-titl">否</span><input type="checkbox"   readonly="readonly" <#if  flag2 ="1">  checked="checked" </#if>/>
				</td>
				
			</tr>
			<tr>
				<td class="itemname td1" >
				综合实力●
				</td>

				<td class="itemname td1" colspan="4">
				${investor.corepersonnel?default("")}
				</td>
				
			</tr>
			<tr>
				<td class="itemname td1" >
				核心团队●
				</td>
				<td class="itemname td1" colspan="4">
				${investor.coreteam?default("")}
				</td>
			</tr>
		</table>
		<div>
		  &nbsp;
		</div>
		
		<div>
		  &nbsp;
		</div>
		<div>
		  &nbsp;
		</div>
        </div>
        
         <div style="" class="title">
		表2：过往业绩情况表（必填）
		
	</div>
	
        <div>
			<#if   (investorManageAchievement?size>0)>  
             <#assign mentrow=investorManageAchievement?size+1>
            <#else>
            <#assign mentrow=2>
             </#if>
            <#if   (investorAchievement?size>0)>  
            <#assign mentrow1=investorAchievement?size+1>
              <#else>
            <#assign mentrow1=2>
             </#if>
            <table class="table1" >
            <#if  investor.manageAchievementFalg ='2'> 
            <tr>
                <td class="itemname td1" rowspan='2'>
					管理业绩
			    </td>
			    <td class="itemname td1" colspan="3" >
					是否有管理基金情况:
				</td>
				<td class="itemname td1"  colspan="9">
					无
				</td>
            </tr>
              <tr>
                  <td class="itemname td1" colspan="3">
					说明原因：
				</td>
				<td class="itemname td1" colspan="9">
					${investor.manageAchievementDesc?default("")}
				</td>
              </tr>
               <#else>
			<tr>
				<td class="itemname td1" rowspan='${mentrow}'>
					管理业绩
				</td>
				<td class="itemname td1" >
					管理基金名称
				</td>
				<td class="itemname td1" >
					注册时间
				</td>
				<td class="itemname td1" >
				注册地
				</td>
				<td class="itemname td1" >
				托管机构
				</td>
				<td class="itemname td1" >
				管理资金规模(万)
				</td>
				<td class="itemname td1" >
				基金编号(中基协)
				</td>
				<td class="itemname td1" >
				基金类型
				</td>
				<td class="itemname td1" >
				投资行业定位
				</td>
				<td class="itemname td1" >
				投资阶段定位
				</td>

				<td class="itemname td1" >
				投资项目数量
				</td>
				<td class="itemname td1" >
				累计投资金额（万）
				</td>
				<td class="itemname td1" colspan="1">
				实现退出项目数量
				</td>
			</tr>
				<#list investorManageAchievement as ima>
				<tr>
					<td class="itemvalue td1">
						 ${ima.fundName?default("")}
					</td>
					<td class="itemvalue td1" >
						 ${ima.registDateString?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.registAddressStr?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.trusteeship?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.manageCapital?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.iccFilingNo?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.foundTypeDicname?default("")}
					</td>
					<td class="itemvalue td1" >
					  <#assign flag=ima.financeTradeDicname?contains("&")?string> 
				        <#if flag="true">
				            <#assign dicname=ima.financeTradeDicname?replace("&","&amp;")> 
				         <#else>
				            <#assign dicname=ima.financeTradeDicname>    
				        </#if>  
				    ${dicname?default("")}
					</td>
					<td class="itemvalue td1" >
				         ${ima.financeStageDicname?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.investmentProjects?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.ciCurrencyStr?default("")}
					</td>
					<td class="itemvalue td1" colspan="1">
					 ${ima.implementExitProject?default("")}
					</td>
				
				</tr>
				</#list>
			 </#if>
			  <#if  investor.achievementFalg ='2'> 
            <tr>
                <td class="itemname td1" rowspan='2'>
					投资业绩
			    </td>
			    <td class="itemname td1" colspan="3" >
					是否有投资项目情况:
				</td>
				<td class="itemname td1"  colspan="9">
					无
				</td>
            </tr>
              <tr>
                  <td class="itemname td1" colspan="3">
					说明原因：
				</td>
				<td class="itemname td1" colspan="9">
					${investor.achievementDesc?default("")}
				</td>
              </tr>
               <#else>
			<tr>
				<td class="itemname td1" rowspan='${mentrow1}'>
					投资业绩
				</td>
				<td class="itemname td1">
					投资基金名称
				</td>
				<td class="itemname td1" >
					已投企业名称
				</td>
				<td class="itemname td1" >
				  已投企业行业
				</td>
				<td class="itemname td1" >
				  已投企业所属区域
				</td>
				<td class="itemname td1" >
				  已投企业注册资本(万)
				</td>
				<td class="itemname td1" >
				投资时间
				</td>
				<td class="itemname td1" >
				投资金额(万)
				</td>
				<td class="itemname td1" >
				股权比例(%)
				</td>

				<td class="itemname td1" >
				投资轮次
				</td>
				<td class="itemname td1" >
				投资阶段
				</td>
				<td class="itemname td1" >
				退出时间
				</td>
				
				<td class="itemname td1" >
				回报率(%)
				</td>
			
			</tr>
				<#list investorAchievement as ima>
				<tr>
					<td class="itemvalue td1">
						 ${ima.investmentFunds?default("")}
					</td>
					<td class="itemvalue td1" >
						 ${ima.investedEnterprise?default("")}
					</td>
					<td class="itemvalue td1" >
					 <#assign flag=ima.investedEnterpriseIndustryStr?contains("&")?string> 
				        <#if flag="true">
				            <#assign dicname=ima.investedEnterpriseIndustryStr?replace("&","&amp;")> 
				         <#else>
				            <#assign dicname=ima.investedEnterpriseIndustryStr>    
				        </#if>  
				    ${dicname?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.investedEnterpriseAreaStr?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.ecCurrencyStr?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.investmentTimeStr?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.iaCurrencyStr?default("")}
					</td>
					<td class="itemvalue td1" >
					  ${ima.shareRatio?default("")}
					</td>
					
					<td class="itemvalue td1" >
					 ${ima.roundOfInvestmentStr?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.investmentStageStr?default("")}
					</td>
					<td class="itemvalue td1" >
					 ${ima.exitTimeStr?default("")}
					</td>
					<td class="itemvalue td1" >
					  ${ima.rateOfReturnStr?default("")}
					</td>
				</tr>
				</#list>
			</#if>
            <tr>
			     <td class="itemname td1"  colspan="13">
					<p>申请机构承诺对备案信息及相关材料的合法性、真实性、有效性负完全责任。</p>
					<br> </br>
					<br> </br>
                    <p>法定代表人/执行事务合伙人签名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请机构盖章：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                     <br> </br>
					<br> </br> 
					<br> </br> 
				</td>
			</tr>
				
		</table>
		</div>
		 <div class="desc">1.●表示为对外公示项，投资机构填写后会统一对外公示。</div>
		 <div class="desc">2.凡是有涉及在苏州注册的基金和投在苏州的项目，需填写相应业绩信息；若任一情况不存在，请填写说明原因，我方会进行核实。</div>
		 <div class="desc">3.对于信息填写不全或存在虚假信息填报的机构，一经发现，在平台业务开展和政策优惠等方面会有所限制。</div>
    </div>
		 
</body>
</html>