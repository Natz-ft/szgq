<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div style="filter:progid:DXImageTransform.Microsoft.gradient(startcolorstr=#f0f0f0,endcolorstr=#FFFFFF,gradientType=0);">

<div style="min-height: 200px;">
<c:if test="${MAV_ROLE_OPERATOR}">
	<table style="margin-top: 5px; width: 99.2%;" border="0" cellpadding="0"
		cellspacing="0">
<!-- 	<tr class="right-top">
		<td width="5%" align="center"><img src="image/right-01.png"
			width="27" height="22" />
		</td>
		<td width="95%" style="font-size: 18px; color: #343434;" align="left">首次开户行</td>
	</tr> -->
	<tr style="min-height: 50px;">
		<td colspan="2" class="border">
				<div class="right-list">
					<ul>
						<c:if test="${MAV_ADD_VALUE.kuAddCustrom!=null && MAV_ADD_VALUE.kuAddCustrom!=0}">
							<li><a href="#">本机构一级待补录客户<c:out
									value="${MAV_ADD_VALUE.kuAddCustrom}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
						<c:if test="${MAV_ADD_VALUE.kuAddBhCtif!=null && MAV_ADD_VALUE.kuAddBhCtif!=0}">
							<li><a href="#">本机构大额二级待补录客户<c:out
									value="${MAV_ADD_VALUE.kuAddBhCtif}" default="0"></c:out>笔;</a>
						</li>
						</c:if>
						<c:if test="${MAV_ADD_VALUE.kuAddBsCtif!=null && MAV_ADD_VALUE.kuAddBsCtif!=0}">
							<li><a href="#">本机构可疑二级待补录客户<c:out
									value="${MAV_ADD_VALUE.kuAddBsCtif}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
						<!--  <li><a herf="#">本机构客户的大额交易还有<c:out
									value="${MAV_ADD_VALUE.kuAddBhHtcr}" default="0"></c:out>笔待补录;</a>
						</li>
						<li><a herf="#">本机构客户的可疑交易还有<c:out
									value="${MAV_ADD_VALUE.kuAddBsRpdi}" default="0"></c:out>笔待补录;</a>
						</li>-->
					</ul>
				</div>
			</td>
		</tr>
	</table>
</c:if> 
<c:if test="${MAV_ROLE_CHECKER}">
	<table style="margin-top: 5px; width: 99.2%;" border="0" cellpadding="0"
		cellspacing="0">
			
			<!-- 	<tr class="right-top">
			<td width="5%" align="center"><img src="image/right-01.png"
				width="27" height="22" />
			</td>
			<td width="95%" style="font-size: 18px; color: #343434;" align="left">首次开户行</td>
		</tr> -->
		<tr style="min-height: 50px;">
			<td colspan="2" class="border">
				<div class="right-list">
					<ul>
						
						<c:if test="${MAV_CHECK_VALUE.kuCheckCustrom!=null && MAV_CHECK_VALUE.kuCheckCustrom!=0}">
							<li><a href="#">本机构一级待复核客户<c:out
									value="${MAV_CHECK_VALUE.kuCheckCustrom}" default="0"></c:out>人;</a>
							</li>
						</c:if>
						
						<c:if test="${MAV_CHECK_VALUE.zhCheckAccount!=null && MAV_CHECK_VALUE.zhCheckAccount!=0}">
							<li><a href="#">本机构一级待复核账户<c:out
									value="${MAV_CHECK_VALUE.zhCheckAccount}" default="0"></c:out>个;</a>
							</li>
						</c:if>
						
						<c:if test="${MAV_CHECK_VALUE.kuCheckBhCtif!=null && MAV_CHECK_VALUE.kuCheckBhCtif!=0}">
							<li><a href="#">本机构大额二级待复核报告<c:out
									value="${MAV_CHECK_VALUE.kuCheckBhCtif}" default="0"></c:out>份;</a>
							</li>
						</c:if>
						<c:if test="${MAV_CHECK_VALUE.kuCheckBsCtif!=null && MAV_CHECK_VALUE.kuCheckBsCtif!=0}">
							<li><a href="#">本机构可疑二级待复核报告<c:out
									value="${MAV_CHECK_VALUE.kuCheckBsCtif}" default="0"></c:out>份;</a>
							</li>
						</c:if>
						<!--<li><a herf="#">本机构客户的大额报告下交易还有<c:out
									value="${MAV_CHECK_VALUE.kuCheckBhHtcr}" default="0"></c:out>笔待复核;</a>
						</li>
						<li><a herf="#">本机构客户的可疑报告下交易还有<c:out
									value="${MAV_CHECK_VALUE.kuCheckBsRpdi}" default="0"></c:out>笔待复核;</a>
						</li>-->
						
						<c:if test="${MAV_CHECK_VALUE.jyAddBhHtcrJjjz!=null && MAV_CHECK_VALUE.jyAddBhHtcrJjjz!=0}">
							<li><a href="#">本机构大额二级待补录交易（即将截止）<font color="red">
								<c:out value="${MAV_CHECK_VALUE.jyAddBhHtcrJjjz}" default="0"></c:out></font>笔;</a>
							</li>
						</c:if>
						
						<c:if test="${MAV_CHECK_VALUE.secondHvtrCustomerNeedCheckJjjz!=null && MAV_CHECK_VALUE.secondHvtrCustomerNeedCheckJjjz!=0}">
							<li><a href="#">本机构大额二级待复核报告（即将截止）<font color="red">
								<c:out value="${MAV_CHECK_VALUE.secondHvtrCustomerNeedCheckJjjz}" default="0"></c:out></font>笔;</a>
							</li>
						</c:if>
					</ul>
				</div>
			</td>
		</tr>
	</table>
</c:if>
<!-- 添加分行管理层确认提示_yuhl_20130623 -->
<c:if test="${MAV_ROLE_MANAGER}">
	<table style="margin-top: 5px; width: 99.2%;" border="0" cellpadding="0"
		cellspacing="0">
			
				<!-- <tr class="right-top">
			<td width="5%" align="center"><img src="image/right-01.png"
				width="27" height="22" />
			</td>
			<td width="95%" style="font-size: 18px; color: #343434;" align="left">首次开户行</td>
		</tr> -->
		<tr style="min-height: 50px;">
			<td colspan="2" class="border">
				<div class="right-list">
					<ul>
						<c:if test="${MAV_MANAGE_VALUE.HtvrReportOfNeedConfirm!=null && MAV_MANAGE_VALUE.HtvrReportOfNeedConfirm!=0}">
							<li><a href="#">本机构大额待确认报告<c:out
									value="${MAV_MANAGE_VALUE.HtvrReportOfNeedConfirm}" default="0"></c:out>份;</a>
						</li>
						</c:if>
						<c:if test="${MAV_MANAGE_VALUE.BstrReportOfNeedConfirm!=null && MAV_MANAGE_VALUE.BstrReportOfNeedConfirm!=0}">
							<li><a href="#">本机构可疑待确认报告<c:out
									value="${MAV_MANAGE_VALUE.BstrReportOfNeedConfirm}" default="0"></c:out>份;</a>
							</li>
						</c:if>
						
						<!--<li><a herf="#">本机构客户的大额报告下交易还有<c:out
									value="${MAV_CHECK_VALUE.kuCheckBhHtcr}" default="0"></c:out>笔待复核;</a>
						</li>
						<li><a herf="#">本机构客户的可疑报告下交易还有<c:out
									value="${MAV_CHECK_VALUE.kuCheckBsRpdi}" default="0"></c:out>笔待复核;</a>
						</li>-->
					</ul>
				</div>
			</td>
		</tr>
	</table>
</c:if>
<!-- 添加分行管理层确认提示_yuhl_20130623 -->


<c:if test="${MAV_ROLE_OPERATOR}">
  <table style="margin-top: 5px; width: 99.2%;" border="0" cellpadding="0"
		cellspacing="0">
	<!-- <tr class="right-top">
		<td width="5%" align="center"><img src="image/right-01.png"
			width="27" height="22" />
		</td>
		<td width="95%" style="font-size: 18px; color: #343434;">账户开立行</td>
	</tr> -->
	<tr>
		<td colspan="2" class="border">
				<div class="right-list">
					<ul>
						<c:if test="${MAV_ADD_VALUE.zhAddAccount!=null && MAV_ADD_VALUE.zhAddAccount!=0}">
							<li><a href="#">本机构一级待补录账户
								<c:out value="${MAV_ADD_VALUE.zhAddAccount}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
					</ul>
				</div>
			</td>
		</tr>
	</table>
</c:if>

<c:if  test="${MAV_ROLE_OPERATOR}">
	<table style="margin-top: 5px; width: 99.2%;" border="0" cellpadding="0"
		cellspacing="0">
	<!-- <tr class="right-top">
		<td width="5%" align="center"><img src="image/right-01.png"
			width="27" height="22" />
		</td>
		<td width="95%" style="font-size: 18px; color: #343434;">交易发生行</td>
	</tr> -->
	<tr>
		<td colspan="2" colspan="2" class="border">
		
				<div class="right-list">
					<ul>
						<c:if test="${MAV_ADD_VALUE.jyAddTrade!=null && MAV_ADD_VALUE.jyAddTrade!=0}">
							<li><a href="#">本机构一级待补录交易<c:out
									value="${MAV_ADD_VALUE.jyAddTrade}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
						<c:if test="${MAV_ADD_VALUE.jyAddBhHtcr!=null && MAV_ADD_VALUE.jyAddBhHtcr!=0}">
							<li><a href="#">本机构大额二级待补录交易<c:out
									value="${MAV_ADD_VALUE.jyAddBhHtcr}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
						<c:if test="${MAV_ADD_VALUE.jyAddBsRpdi!=null && MAV_ADD_VALUE.jyAddBsRpdi!=0}">
							<li><a href="#">本机构可疑二级待补录交易<c:out
									value="${MAV_ADD_VALUE.jyAddBsRpdi}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
						
						<c:if test="${MAV_ADD_VALUE.jyAddBhHtcrJjjz!=null && MAV_ADD_VALUE.jyAddBhHtcrJjjz!=0}">
							<li><a href="#">本机构大额二级待补录交易（即将截止）<font color="red"><c:out
									value="${MAV_ADD_VALUE.jyAddBhHtcrJjjz}" default="0"></c:out></font>笔;</a>
							</li>
						</c:if>
					</ul>
				</div>
				</td>
		</tr>
	</table>
</c:if> 
<!--  二级复核以“报告”为单位，交易发生行看不到报告信息，注释掉
<c:if test="${MAV_ROLE_CHECKER}">
	<table style="margin-top: 5px; width: 99.2%;" border="0" cellpadding="0"
		cellspacing="0">
			
			<tr class="right-top">
		<td width="5%" align="center"><img src="image/right-01.png"
			width="27" height="22" />
		</td>
		<td width="95%" style="font-size: 18px; color: #343434;">交易发生行</td>
	</tr>
	<tr>
		<td colspan="2" colspan="2" class="border">
				<div class="right-list">
					<ul>
					
						<li><a herf="#">本机构二级大额报告待复核交易<c:out
									value="${MAV_CHECK_VALUE.jyCheckBhHtcr}" default="0"></c:out>笔;</a>
						</li>
						<li><a herf="#">本机构二级可疑报告待复核交易<c:out
									value="${MAV_CHECK_VALUE.jyCheckBsRpdi}" default="0"></c:out>笔;</a>
						</li>
					</ul>
				</div>
			 </td>
		</tr>
	</table>
</c:if> -->
<c:if test="${MAV_ROLE_CONTROL}">
   <table style="margin-top: 5px; width: 99.2%;" border="0" cellpadding="0"
		cellspacing="0">
	<!-- <tr class="right-top">
		<td width="5%" align="center"><img src="image/right-01.png"
			width="27" height="22" />
		</td>
		<td width="95%" style="font-size: 18px; color: #343434;" align="left">总行监控人员提示</td>
	</tr> -->
	<tr style="min-height: 50px;">
		<td colspan="2" class="border">
		
				<div class="right-list">
					<ul>
						<c:if test="${MAV_CONTROL_VALUE.jkcontrolTrade!=null && MAV_CONTROL_VALUE.jkcontrolTrade!=0}">
							<li><a href="#">一级待补录交易<c:out
									value="${MAV_CONTROL_VALUE.jkcontrolTrade}" default="0"></c:out>笔;</a>
						</li>
						</c:if>
						<c:if test="${MAV_CONTROL_VALUE.firstAccountNeedSupply!=null && MAV_CONTROL_VALUE.firstAccountNeedSupply!=0}">
							<li><a href="#">一级待补录账户<c:out
									value="${MAV_CONTROL_VALUE.firstAccountNeedSupply}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
						<c:if test="${MAV_CONTROL_VALUE.firstCustomerNeedSupply!=null && MAV_CONTROL_VALUE.firstCustomerNeedSupply!=0}">
								<li><a href="#">一级待补录客户<c:out
									value="${MAV_CONTROL_VALUE.firstCustomerNeedSupply}" default="0"></c:out>笔;</a>
								</li>					
						</c:if>
						<c:if test="${MAV_CONTROL_VALUE.jkcontrolBhHtcr!=null && MAV_CONTROL_VALUE.jkcontrolBhHtcr!=0}">
							<li><a href="#">大额二级待补录交易<c:out
									value="${MAV_CONTROL_VALUE.jkcontrolBhHtcr}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
						<c:if test="${MAV_CONTROL_VALUE.jkcontrolBsRpdi!=null && MAV_CONTROL_VALUE.jkcontrolBsRpdi!=0}">
							<li><a href="#">可疑二级待补录交易<c:out
									value="${MAV_CONTROL_VALUE.jkcontrolBsRpdi}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
						<c:if test="${MAV_CONTROL_VALUE.secondHvtrCustomerNeedSupply!=null && MAV_CONTROL_VALUE.secondHvtrCustomerNeedSupply!=0}">
							<li><a href="#">大额二级待补录客户<c:out
									value="${MAV_CONTROL_VALUE.secondHvtrCustomerNeedSupply}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
						<c:if test="${MAV_CONTROL_VALUE.secondBstrCustomerNeedSupply!=null && MAV_CONTROL_VALUE.secondBstrCustomerNeedSupply!=0}">
							<li><a href="#">可疑二级待补录客户<c:out
									value="${MAV_CONTROL_VALUE.secondBstrCustomerNeedSupply}" default="0"></c:out>笔;</a>
							</li>
						</c:if>
						<c:if test="${MAV_CONTROL_VALUE.secondHvtrCustomerNeedCheck!=null && MAV_CONTROL_VALUE.secondHvtrCustomerNeedCheck!=0}">
							<li><a href="#">大额二级待复核报告<c:out
									value="${MAV_CONTROL_VALUE.secondHvtrCustomerNeedCheck}" default="0"></c:out>份;</a>
							</li>
						</c:if>
						<c:if test="${MAV_CONTROL_VALUE.secondBstrCustomerNeedCheck!=null && MAV_CONTROL_VALUE.secondBstrCustomerNeedCheck!=0}">
							<li><a href="#">可疑二级待复核报告<c:out
									value="${MAV_CONTROL_VALUE.secondBstrCustomerNeedCheck}" default="0"></c:out>份;</a>
							</li>
						</c:if>
						<c:if test="${MAV_CONTROL_VALUE.hvtrCustomerNeedConfirm!=null && MAV_CONTROL_VALUE.hvtrCustomerNeedConfirm!=0}">
							<li><a href="#">分行管理层大额待确认报告<c:out
									value="${MAV_CONTROL_VALUE.hvtrCustomerNeedConfirm}" default="0"></c:out>份;</a>
							</li>
						</c:if>
						<c:if test="${MAV_CONTROL_VALUE.bstrCustomerNeedConfirm!=null && MAV_CONTROL_VALUE.bstrCustomerNeedConfirm!=0}">
							<li><a href="#">分行管理层可疑待确认报告<c:out
									value="${MAV_CONTROL_VALUE.bstrCustomerNeedConfirm}" default="0"></c:out>份;</a>
							</li>
						</c:if>
					</ul>
				</div>
			</td>
		</tr>
   </table>
</c:if>
</div>

<table width="100%">
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td align="center">
			<input type="button" value="确认退出" onclick="window.returnValue='SUC';window.close();" class="button5"/>
			<input type="button" value="取消" onclick="window.returnValue='FAL';window.close();" class="button4"/>
		</td>
	</tr>
</table>

</div>