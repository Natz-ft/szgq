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
			苏州股权融资服务平台企业注册登记表
		</div>
        <div>
           
            <table class="table1" >
                <tr>
					<td class="itemname td1">
					 企业名称
					</td>
					<td class="itemvalue td1" colspan="3">
						${(companyBase.name)?default("")}
					</td>
			    </tr>
			    <tr>
					<td class="itemname td1">
					  组织机构代码
					</td>
					<td class="itemvalue td1" colspan="3">
						${(companyBase.code)?default("")}
					</td>
			    </tr>
			    <tr>
					<td class="itemname td1">
					所属区域
					</td>
					<td class="itemvalue td1" colspan="3">
						${(companyBase.rearea)?default("")}
					</td>
			    </tr>
			    <tr>
					<td class="itemname td1">
					  注册地址
					</td>
					<td class="itemvalue td1" colspan="3">
						${(companyBase.registArea)?default("")}
					</td>
			    </tr>
			    <tr>
					<td class="itemname td1">
					  注册资本（万元）
					</td>
					<td class="itemvalue td1" colspan="3">
						${(companyBase.regcapital)?default("")}
					</td>
			    </tr>
			    <tr>
					<td class="itemname td1">
					   法定代表人
					</td>
					<td class="itemvalue td1">
						${(companyBase.legalName)?default("")}
					</td>
					<td class="itemname td1">
					 法定代表人联系方式
					</td>
					<td class="itemvalue td1">
						${(companyBase.legalCal)?default("")}
					</td>
					
			    </tr>
			   
			     <tr>
					<td class="itemname td1">
					  股权事宜联系人姓名
					</td>
					<td class="itemvalue td1">
					${(companyBase.stockName)?default("")}
					</td>
					<td class="itemname td1">
					    股权事宜联系人联系方式
					</td>
					<td class="itemvalue td1">
						${(companyBase.stockCal)?default("")}
					</td>
			    </tr>
			   <tr>
					<td class="itemname td1">
					  是否获得过股权投资
					</td>
					<td class="itemvalue td1" colspan="3">
						<span class="check-titl">是</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly"   <#if  iSHiTechPark ="0">  checked="checked" </#if>/>
				        <span class="check-titl">否</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly"   <#if  iSHiTechPark ="1">  checked="checked" </#if>/>
					</td>
			    </tr>
			    <tr>
					<td class="itemname td1">
					  是否有股权融资意向
					</td>
					<td class="itemvalue td1" colspan="3">
						<span class="check-titl">是</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly"  <#if  iSEgggenerator ="0">  checked="checked" </#if>/>
				        <span class="check-titl">暂不确定</span><input type="checkbox" name="chkAllResourceType" id="chkAllResourceType"  readonly="readonly"  <#if  iSEgggenerator ="1">  checked="checked" </#if> />
				    </td>
			    </tr>
		    </table>
		</div>
    </div>
</body>
</html>