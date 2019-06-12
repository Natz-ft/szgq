<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>奖励申报</title>
<style type="text/css">
body {  
    font-family: SimSun;
	font-size: 5px;
	margin: 0 auto;
	text-align:center;
}  
 .title {
	text-align: center;
	font-weight: bold;
	font-size: 16px;
	color: #000000;
	padding-bottom: 5px;
}
.table1 {
	margin: auto;
	width: 100%;
	border-collapse: collapse;
	border: none;
	table-layout:fixed;
}
.table2 {
	margin: auto;
	width: 100%;
	border-collapse: collapse;
	border: none;
	table-layout:fixed;
	rules:none;
	frame:void
}
.td1 {
	border: 0.5px solid #444444;
	font-size: 12px;
	margin-left: 5px;
	padding: 6px;
	height:25px;
	word-wrap:break-word;
}
.td2 {
	border: 0px;
	font-size: 5px;
	margin-left: 5px;
	padding: 6px;
	word-wrap:break-word;
}

 .itemname {
	text-align: center;
	font-weight: bold;
	font-size: 10px;
	color: #000000;
}
 .itemname1 {
	text-align: left;
	font-weight: bold;
	font-size: 10px;
	color: #000000;
}
.itemvalue {
	text-align: left;
	font-size: 10px;
	color: #000000;
	
}
.itemvalue1 {
	text-align: center;
	font-size: 10px;
	color: #000000;
	
}


@page {
            size: 300mm 219mm;
        }
</style>
</head>
<body>

    <div class="contentdiv">
        <div style="" class="title">    
                                   基金管理人奖励申报表
         </div>                        
	    <br></br>
        <div>
			 <table class="table2" >
			 <tr>
				<td class="itemname1 td2" colspan="2">申报单位:${(declare.declareName)?default("")}</td>
			    <td class="itemname1 td2" colspan="2">统一社会信用代码:${(declare.certno)?default("")}</td>
				<td class="itemname1 td2" colspan="2">注册地:${(declare.investorAreaName)?default("")}</td>
				<td class="itemname1 td2" colspan="2">申报期间:${declare.declareBeginTime?string('yyyy-MM-dd')} <span style="text-align:center;line-height:18px;">～</span> ${declare.declareEndTime?string('yyyy-MM-dd')}</td>
			 </tr>
			 <tr>
				<td class="itemname1 td2" colspan="2">联系人:${(declare.relName)?default("")}</td>
				<td class="itemname1 td2" colspan="2">联系电话:${(declare.relPhone)?default("")}</td>
				<td class="itemname1 td2" colspan="2">开户银行及账号:${(declare.bankDeposit)?default("")}:${declare.bankAccount}</td>
				<td class="itemname1 td2" colspan="2">被投企业工商注册地:${(declare.reareaDicname)?default("") }</td>
			 </tr>
			 </table>			
			 <br></br>					
            <table class="table1" >
			    <tr>
					<td class="itemname td1">序号</td>
					<td class="itemname td1">管理基金名称</td>
					<td class="itemname td1">基金工商注册地</td>
					<td class="itemname td1">基金统一社会信息代码</td>
					<td class="itemname td1">基金编号</td>
					
					<td class="itemname td1">被投企业名称</td>
					<td class="itemname td1">被投企业证件代码</td>
					<td class="itemname td1">投资金额</td>
					<td class="itemname td1">备注</td>
			    </tr>
			    <#list declare.declareRewarReport as report>
				    <tr>
						<td class="itemvalue1 td1">${(report.serialNumber)?default("")}</td>
						<td class="itemvalue1 td1">${(report.subacName)?default("")}</td>
						<td class="itemvalue1 td1">${(report.achievementAddress)?default("")}</td>
						<td class="itemvalue1 td1">${(report.achievementCode)?default("")}</td>
						<td class="itemvalue1 td1">${(report.amacRecord)?default("")}</td>
						<td class="itemvalue1 td1">${(report.investedEnterprise)?default("")}  </td>
						<td class="itemvalue1 td1">${(report.investedEnterpriseCode)?default("")}</td>
						<td class="itemvalue1 td1">${(report.investmentAmountStr)?default("")}</td>
						<td class="itemvalue1 td1"></td>
					</tr>
			    </#list>
				<tr>
				<td class="itemname td1">基金人管理承诺</td>
				<td class="itemvalue td1" colspan="8">
				<div style="line-height:18px;">
				<br></br>
				  <p>
						                                                                         本管理人根据《市政府关于印发苏州市金融支持企业自主创新行动计划（2015～2020）的通知》（苏府〔2015〕136号）、《关于促进创业投资持续健康发展的实施意见》（苏府〔2018〕115号）和《苏州股权融资服务平台奖励政策实施细则（试行）》（苏府金发〔2018〕70号）等文件相关规定，现申请奖励，证明材料附后。本管理人承诺严格遵守有关规定，承诺对申报材料的真实性、合法性、有效性负完全责任，并将积极配合市财政局委托的第三方中介机构对相关业务进行复审。
						                      </p>  
						                        <br></br>
				       法人代表/执行事务合伙人（签名）    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     
				       （单位盖章）
				       <br></br>
				       	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     
				       	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     
				        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     
				         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                          年    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                         月&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;  日
				     <br></br>
				   <br></br> 
									      </div>
				</td>
				
				</tr>
				<tr>
				<!-- 开发人员根据业务需求，自定义表格内容字段 开始 -->
				<td class="itemname td1">市（区）金融办初审意见</td>
				<td class="itemvalue td1" colspan="8">
				<br></br>
				<br></br>
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       
				       （盖章）
				        <br></br>
				         <br></br>
				         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                          年    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;  日
				  <br></br>
				   <br></br>
				   <br></br>                      
				</td>
				</tr>
		    </table>
		</div>
    </div>
</body>
</html>