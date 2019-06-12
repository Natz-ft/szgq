<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>授权书</title>
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
	text-align: center;
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
	
}
.itemtest{
	text-align: left;
	font-size: 12px;
	color: #000000;
	text-indent:2em;
	font-weight: bold;
}
.itemgz{
    text-align: left;
}
.divtest{
line-height:30px;
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
                                 苏州市地方企业征信系统
         </div>                        
	    <div style="" class="title">
			信息采集及信息查询授权委托书
		</div>
        <div>
           
            <table class="table1" >
			    <tr>
					<td class="itemname td1">
					<input type="checkbox" 
					<#if  companyBase.codetype ='1'>  checked="checked" </#if>
					    readonly="readonly" />	组织机构代码
					<br></br>
					<input type="checkbox" 
					<#if  companyBase.codetype ='2'>  checked="checked" </#if>
					    readonly="readonly" />	统一社会信用代码
					</td>
					<td class="itemvalue td1" colspan="3">
						${(companyBase.code)?default("")}
					</td>
					<td class="itemgz td1" rowspan="5">
					    <span style="font-size: 14px;color: #000000;font-weight: bold;"> 授权企业确认授权条款及填写信息加盖公章处</span>
					   <br></br>
					   <br></br>
					   <br></br>
					   <br></br>
					   <br></br>
					   <br></br>
					   <span style="font-size: 17px;color:#D8D8D8">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公章</span>
					   <br></br>
					   <br></br>
					   <br></br>
					   <br></br>
					   <br></br>
					   <br></br>
					</td>
			    </tr>
			    <tr>
					<td class="itemname td1">
					  授权企业名称
					</td>
					<td class="itemvalue td1" colspan="3">
					<#assign flag=companyBase.name?contains("&")?string> 
				        <#if flag="true">
				            <#assign enterpriceName=companyBase.name?replace("&","&amp;")> 
				         <#else>
				            <#assign enterpriceName=companyBase.name>    
				        </#if>  
				    ${enterpriceName?default("")}
					</td>
			    </tr>
			    <tr>
					<td class="itemname td1">
					   法定代表人
					</td>
					<td class="itemvalue td1">
					<#assign flag=companyBase.legalName?contains("&")?string> 
				        <#if flag="true">
				            <#assign legalName=companyBase.legalName?replace("&","&amp;")> 
				         <#else>
				            <#assign legalName=companyBase.legalName>    
				        </#if>  
				    ${legalName?default("")}
					</td>
					<td class="itemname td1">
					   法定代表人<br></br>手机号码
					</td>
					<td class="itemvalue td1">
						${(companyBase.legalCal)?default("")}
					</td>
			    </tr>
			     <tr>
					<td class="itemname td1">
					  企业联系人
					</td>
					<td class="itemvalue td1">
					<#assign flag=companyBase.stockName?contains("&")?string> 
				        <#if flag="true">
				            <#assign stockName=companyBase.stockName?replace("&","&amp;")> 
				         <#else>
				            <#assign stockName=companyBase.stockName>    
				        </#if>  
				    ${stockName?default("")}
					</td>
					<td class="itemname td1">
					     企业联系人<br></br>手机号码
					</td>
					<td class="itemvalue td1">
						${(companyBase.stockCal)?default("")}
					</td>
			    </tr>
			    <tr>
					<td class="itemname td1">
					授权书签订日期
					</td>
					<td class="itemvalue td1" colspan="3">
						        &nbsp;&nbsp;${(YEAR)?default("")}&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;${(MONTH)?default("")}&nbsp;&nbsp;&nbsp; 月&nbsp;&nbsp;&nbsp;&nbsp;${(DAY_OF_MONTH)?default("")}&nbsp;&nbsp;&nbsp;  日
					</td>
			    </tr>
			    <tr >
			        <td  class="itemtest td1" colspan="5">
			        <div class="divtest">
                        <p>一、授权企业在此授权苏州地方征信平台运营单位一苏州企业征信服务有限公司（下称“平台运营单位”）采集本企业如下信息：
                      		  <br></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、企业的基本信息、企业的汇总经营信息、企业的负面信息、企业的金融信息等企业向相关部门提供的和相关部门在管理工作中产生的信息;
                       		  <br></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、国家法律、行政法规、部门规章、地方性法规等未禁止征信机构采集的与授权企业有关的其他信息。
                       	</p>	 
                        <p>二、授权企业在此授权平台运营单位可以从国家机关、政府部门、事业单位、金融机构等相关部门和单位采集上述信息（包括不限于历史信息及更新），也可从行业协会、社会团体、互联网信息平台渠道等取得上述信息。授权企业在此授权上述有关部门和单位向平台运营单位提供本企业上述信息。</p>
                        <p>三、授权企业在此授权苏州自主创新企业综合金融服务平台（包含苏州股权融资服务平台等平台）的使用单位(金融机构)可以向苏州地方征信平台查询使用本企业的上述信息,并授权平台运营单位将该信息提供给被授权单位。</p>
                       	<p>四、授权企业在此承诺国家机关、政府部门、事业单位等部门和单位，因行政管理、公共服务等需要，可以直接查询企业的上述信息。平台运营单位可以向上述部门和单位提供上述信息。</p>
                       	<p>五、本授权自授权书签订之日起生效，授权期限为长期。</p>
                        <p>六、如需终止本授权的查询授权部分，需要填写《苏州市地方企业征信系统信息查询授权终止书》并提供相关资料，经平台运营单位审核，满足终止授权条件的可以终止。如需终止本授权的采集授权部分，需携带《苏州市地方企业征信系统信息采集授权终止书》、查询授权终止的回执及相关企业材料到平台运营单位现场办理。 </p>                                               
			            <p>七、授权企业在此声明已知悉并理解本授权委托书，以及完全承担因提供非公开信息及负面信息可能导致的任何不利后果。     </p>
			            <p>八、在法律范围内，最终解释权归本平台所有。    </p>
			      </div>                              
			        </td>
			    </tr>
		    </table>
		</div>
    </div>
</body>
</html>