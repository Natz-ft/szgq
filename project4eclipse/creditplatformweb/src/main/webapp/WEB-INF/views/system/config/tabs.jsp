<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <%@ include file="/WEB-INF/views/common/include.jsp"%>
<!-- <link href="../static/css/style-test.css" rel="stylesheet" type="text/css" />

<link href="../static/css/tabs.css" rel="stylesheet" type="text/css" /> 

  <link href="${pageContext.request.contextPath}/static/frame-free/frame/css/import_basic.css" rel="stylesheet" type="text/css"/> 
  <link href="${pageContext.request.contextPath}/static/frame-free/frame/skins/sky/import_skin.css" rel="stylesheet" type="text/css"/> 
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine-cn.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/frame-free/frame/js/form/validationEngine.js" type="text/javascript"></script> 
<script src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>  
<!-- <script src="../static/js/jquery-1.4.3.js"></script> -->
<!-- <script src="../static/js/jquery.validate.js" type="text/javascript"></script> -->

<script type="text/javascript">

	/**
	 *  用户提示
	 */
   function tabs_successprocess(data) {
	if (data.msg =="1") {
	 		return true;
 	}else{
 		return false;
 	}
}
/* $(document).ready(function(){
	$("#bbb").on("keyup",function(){  
        this.value =this.value.replace(/,/g, '').replace(/\d+?(?=(?:\d{3})+$)/g, function(s){  
            return s +',';  
        });  
    $("#bbb").val(this.value);  
});
	
}); */
</script>
</head>

<body>
	<div class="basepopopdiv popup357-381 base-wrap-l" id="tab_dialog_id"
		style="display: none;">
			<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
				<div class="tiptop">
				<span>页签</span><a onclick="close_dialog('tab_dialog_id');"></a>
			</div>
				<div class="modal-body selectmodal wrap-l">
					<div id="bor">
						<ul id="tabs_ul">
							<li><a href="#">交易信息</a></li>
							<li><a href="#"  >担保信息</a></li>
							<li><a href="#">特殊交易信息</a></li>
						</ul>
						<div class="tabs">
						<!-- 页签一 -->
							<div style="display: none;" id="step0">
							<form id="tabs_form0" method="post" name="create_form_id" aciton="systemConfig/submit_first.do" onkeydown="if(event.keycode==13)return   false;">
								
								<table class="grayTable">
									<tbody>
					<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
	
								<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
								<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>					
									</tbody>
								</table>
							</form>
							</div>
                         <!-- 页签二 -->
							<div style="display: none;" id="step1">
							<form id="tabs_form1" method="post" name="create_form_id" onkeydown="if(event.keycode==13)return   false;">
								<table class="grayTable" >
									<tbody>
	
										<tr>
												<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>

										</tr>
													<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
								<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
											
									</tbody>
								</table>
							</form>
							</div>
							 <!-- 页签三 -->
						<div style="display: none;" id="step2">
							<form id="tabs_form2" method="post" name="create_form_id" onkeydown="if(event.keycode==13)return   false;">
								<table class="grayTable">
									<tbody>
	
															</tr>
													<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
								<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
																</tr>
													<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
								<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
															</tr>
													<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
								<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
																</tr>
													<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
								<tr>	<td align="right" class="thStyle">参数名称:</td><td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td>
						<td align="right" class="thStyle">参数名称:</td>
					<td >
					<input type="text" name="configName" class="validate[required,custom[noSpecialCaracters]]"/><span class="star">*</span>
					</td></tr>
									</tbody>
								</table>
							</form>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer" id="tabs_button" >
		             <div id="tabs_button0" style="display: block;border:none;">
		             <button type="button" id="submit0" class="btn-vip btn-pink" id="uploader_ok" onclick="tabs_submit('')">下一步</button>
					<button type="reset" class="btn-vip btn-white" onclick="reset('tabs_form0');">清空</button>
					<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog('tab_dialog_id');">关闭</button>  
		            </div>
					 <div id="tabs_button1" style="display: none;border:none;">
		             <button type="button" id="submit1" class="btn-vip btn-pink" id="uploader_ok" onclick="tabs_submit('')">下一步</button>
					<button type="reset" class="btn-vip btn-white" onclick="reset('tabs_form1');">清空</button>
					<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog('tab_dialog_id');">关闭</button>  
		            </div>
		            <div id="tabs_button2" style="display: none;border:none;">
		             <button type="button" id="submit2" class="btn-vip btn-pink" id="uploader_ok" onclick="tabs_submit('')">完成</button>
					<button type="reset" class="btn-vip btn-white" onclick="reset('tabs_form2');">清空</button>
					<button type="button" class="btn-vip btn-white" id="uploader_close" onclick="close_dialog('tab_dialog_id');">关闭</button>  
		            </div>
				</div>
			</div>
	</div>
</body>
</html>