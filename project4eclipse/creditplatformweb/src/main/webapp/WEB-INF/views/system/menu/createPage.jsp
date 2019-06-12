<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
 
<script type="text/javascript">
	  /**
	   **用户提示
	  **/
   function create_successprocess(data) {
		if (data.code =="00000") {
		 		return true;
	 	}else{
	 		return false;
	 	}
	}
</script>
<div class="basepopopdiv popup357-381 base-wrap-m" id="create_dialog_id"  style="display:none;">
 		<div id="uploadModal" class="modal hide in" style="display:block;" aria-hidden="false">
			<div class="tiptop">
				<span>新建</span><a href="javascript:close_dialog1('create_dialog_id','create_menuForm_id');"></a>
			</div>
			<div class="modal-body selectmodal wrap-m" >
					 <form method="post"  action="${ctx}/sysMenu/saveMenu.do"  id="create_menuForm_id">
					 <table class="grayTable" id="ss1" data-prompt-position="topLeft"   cellpadding="0" cellspacing="0">
			         <tr>
					<th align="right" class="thStyle" >菜单ID:</th>
					<td >
			   <input type="text" name="id"  class="validate[required,custom[onlyNumber],ajax[ajaxFindById]] textinput"/><span class="star">*</span>
			         <!-- <input type="text" name="id"  id="id1"  class="validate[required,ajax[ajaxFindById]] textinput"/><span class="star">*</span> -->
					</td>
		 	 <th align="right" class="thStyle" >菜单名称:</th>
					<td >
					<input type="text" name="name" class="validate[required] textinput"/><span class="star">*</span>
					</td> 
					</tr>
					
		    <tr>
					<th align="right" class="thStyle">英文别名:</th>
					<td><input type="text" name="alias" class=" validate[required,custom[onlyLetter]] textinput"  /><span class="star">*</span></td>
					<th align="right" class="thStyle">菜单地址:</th>
					<td><input type="text" name="link" class="validate[required] textinput" /><span class="star">*</span></td>
					</tr>
			
					<tr>
					<th align="right" class="thStyle">菜单排序:</th>	
					<td><input type="text" name="sort"  class=" validate[required,custom[onlyNumber]] textinput" /><span class="star">*</span></td>
					<th align="right" class="thStyle">父菜单ID:</th>
					 <td><input type="text" name="parentId" class="validate[required,custom[onlyNumber],ajax[ajaxFindByParentId]] textinput"/><span class="star">*</span></td>
				    </tr>
	 		<tr>
					<th align="right" class="thStyle">菜单描述:</th>
				<td colspan="3">
					<textarea class="textarea aloneTwoCol" cols="100"  rows="20" name="description"  ></textarea>
					</td>
				</tr>  
			</table>
				</form> 
			</div>
			<div class="modal-footer">
				<button type="button"   class="btn-vip btn-pink" id="createBtn" onclick="create_submit('create_menuForm_id','create_dialog_id','');">确定</button>
				<button type="reset" class="btn-vip btn-white" onclick="reset('create_menuForm_id')">清空</button>
				 <button type="button" class="btn-vip btn-white" id="uploader_close"  onclick="close_dialog1('create_dialog_id','create_menuForm_id');">关闭</button> 
			</div>
	 </div>
</div>
 <script type="text/javascript">
</script>


