<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="tip" id="t1">
    <div class="tiptop"><span>提示信息</span><a></a></div>
      <div class="tipinfo">
        <span><img src="../static/image/tip/ticon.png" /></span>
        <div class="tipright">
        <p>提示消息</p>
        <cite>提示消息副标题</cite>
        </div>
        </div>
        <div class="tipbtn">
        &nbsp;&nbsp;&nbsp;
        <input name="sure" type="button"  class="btn-vip btn-pink" value="确定" onclick="reload_dialog();" />
        &nbsp;&nbsp;&nbsp;
        </div>
</div>
<div class="tip" id="t2">
	<div class="tiptop">
		<span>提示信息</span><a></a>
	</div>

	<div class="tipinfo">
		<span><img src="../static/image/tip/ticon.png" /></span>
		<div class="tipright">
			<p>是否确认对信息的修改 ？</p>
		</div>
			<cite>如果是请点击确定按钮 ，否则请点取消。</cite>
	</div>

	<div class="tipbtn">
		&nbsp;&nbsp;&nbsp; <input name="sure" type="button" class="btn-vip btn-pink" 
			value="确定" style="width: 60px;" onclick="delete_confirm_submit('id','/news/delNews?id=');" /> &nbsp;&nbsp;&nbsp; <input
			name="cancel" type="button" style="width: 60px;" class="btn-vip btn-white" value="取消"
			onclick="fcancel()" />
	</div>

</div>

<!-- 更新确认的对话框 -->
<div class="tip" id="t3">
    <div class="tiptop"><span>提示信息</span><a></a></div>
      <div class="tipinfo">
        <span><img src="../static/image/tip/ticon.png" /></span>
        <div class="tipright">
        <p>提示消息</p>
        <cite>提示消息副标题</cite>
        </div>
        </div>
        <div class="tipbtn">
        &nbsp;&nbsp;&nbsp;
        <input name="sure" type="button"  class="btn-vip btn-pink" value="确定" onclick="update_confirm('list_form_id');" />
        &nbsp;&nbsp;&nbsp;
        </div>
</div>
<div class="tip" id="t5">
    <div class="tiptop"><span>提示信息</span><a href="javascript:close_dialog('t5');"></a></div>
      <div class="tipinfo">
        <span><img src="${pageContext.request.contextPath}/static/image/tip/ticon.png" /></span>
        <div class="tipright">
        <p>提示消息</p>
        <cite>提示消息副标题</cite>
        </div>
        </div>
        <div class="tipbtn">
        &nbsp;&nbsp;&nbsp;
        <input name="sure" type="button"  class="btn-vip btn-pink" value="确定" onclick="close_dialog('t5');"  />
        &nbsp;&nbsp;&nbsp;
        </div>
</div>
