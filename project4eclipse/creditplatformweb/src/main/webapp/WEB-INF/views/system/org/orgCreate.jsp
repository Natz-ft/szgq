<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/views/common/include.jsp"%>
<style type="text/css">
	.zDialogCon{
		padding: 0px;
	}
	.modal-son .aloneTwoCol{
		/* width: 468px; */
	}
</style>
<div style="margin: 0px auto;" align="left" class="iframe">
	<form method="post" action="${ctx}/sysOrg/createSystemOrg.do" id="org_create_form_id">
		<div class="modal-son">
			<table class="grayTable" id="ss1" data-prompt-position="topLeft" cellpadding="0" cellspacing="0">
				<tr>
					<th align="right" class="thStyle">机构编码:</th>
					<td><input type="text" name="orgid" class="validate[required,ajax[ajaxFindByOrgId],custom[orgNo]]" /><span class="star">*</span></td>
					<th align="right" class="thStyle">机构名称:</th>
					<td><input type="text" name="orgName" class="validate[required,length[1,20]]" /><span class="star">*</span></td>
					<th align="right" class="thStyle">上级机构:</th>
					<td>
					<select name="uporg"  class="wrap-select">
						<option value="" selected="selected">--请选择--</option>
							<c:forEach var="curTp" items="${orgMap}">
								<option value="${curTp.key}">${curTp.value }</option>
							</c:forEach>
					</select> 
					</td>
				</tr>
				<tr>
					<th align="right" class="thStyle">注册资金(万元):</th>
					<td><input type="text" name="regCapital" class="validate[required,custom[money]]" /><span class="star">*</span></td>
					<th align="right" class="thStyle">邮政编码:</th>
					<td><input type="text" name="postCode" class="validate[required,custom[zopcode]]"  class="validate[required,length[1,6]]" /><span class="star">*</span></td>
					<th align="right" class="thStyle">联系人 :</th>
					<td><input type="text" name="linkMan" class="validate[required,length[1,20]]" /><span class="star">*</span></td>
				</tr>
				<tr>
					<!-- <th align="right" class="thStyle">机构所在地区代码:</th>
					<td><input type="text" name="areaCode"  class="validate[length[1,6]]" /></td> -->
					<th align="right" class="thStyle">联系方式:</th>
					<td><input type="text" name="linkMode" value="${org.linkMode }" class="validate[required,custom[telephone]]" /><span class="star">*</span></td>
					<th align="right" class="thStyle">其他联系方式:</th>
					<td><input type="text" name="otherLinkMode" class="validate[custom[telephone]]" /></td>
				</tr>
				<tr>
					<th align="right" class="thStyle">详细地址:</th>
					<td colspan="6"><input type="text" name="address" class="validate[required,length[1,40]]" style="width: 90%;" /><span class="star">*</span></td>
				</tr>
				<tr>
					<th align="right" class="thStyle">备注:</th>
					<td colspan="7"><textarea maxlength="200" cols="100" rows="20" name="remark" class="validate[length[1,120]] textarea aloneTwoCol"></textarea></td>
				</tr>
				<tr>
					<td align="center" colspan="6">
						<div>
							<button type="button" class="btn-vip btn-pink" id="org_createBtn" onclick="org_create_submit();">提交</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script type="text/javascript">
	/**
	 * 机构信息的提交
	 */
	function org_create_submit() {
		if ($("#org_create_form_id").validationEngine({
			returnIsValid : true
		})) {
			$("#org_create_form_id").ajaxSubmit({
				dataType : 'json',
				success : function(data) {
					if (data.code == "00000") {
						parent.param("提示信息", "新建成功", "");
						/* $("#orgt4", parent.document).fadeIn(200); */
						$("#orgt5", parent.document).fadeIn(200);
					} else {
						parent.param("提示信息", "新建失败", "");
						$("#orgt5", parent.document).fadeIn(200);
					}
					$("#orgidHide", parent.document).val(data.objectData.orgid);
				},
				error : function() {
					param("提示信息", "系统错误，请重试", "");
					$("#t1").fadeIn(200);
				}
			})
		} else {
			return;
		}
	}
</script>
