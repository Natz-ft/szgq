<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="tipPage">
	<!-- 机构管理 -->
	<div class="tip" id="orgt4">
		<div class="tiptop">
			<span onclick="closetipt('orgt4')">提示信息</span><a></a>
		</div>
		<div class="tipinfo">
			<span><img src="../static/image/tip/ticon.png" /></span>
			<div class="tipright">
				<p>提示消息</p>
				<cite>提示消息副标题</cite>
			</div>
		</div>
		<div class="tipbtn">
			&nbsp;&nbsp;&nbsp; <input name="sure" type="button" class="btn-vip btn-pink" value="确定" onclick="affirmTip('orgt4');" /> &nbsp;&nbsp;&nbsp;
		</div>
	</div>
	
	<div class="tip" id="orgt5">
		<div class="tiptop">
			<span >提示信息</span><a onclick="closeOrgt5dialog();"></a>
		</div>
		<div class="tipinfo">
			<span><img src="../static/image/tip/ticon.png" /></span>
			<div class="tipright">
				<p>提示消息</p>
				<cite>提示消息副标题</cite>
			</div>
		</div>
		<div class="tipbtn">
			&nbsp;&nbsp;&nbsp; <input name="sure" type="button" class="btn-vip btn-pink" value="确定" onclick="closeOrgt5dialog();" /> &nbsp;&nbsp;&nbsp;
		</div>
	</div>
	
	
	
</div>
<script type="text/javascript">
function closetipt() {
	$("#orgt4").hide();
}

function closeOrgt5dialog() {
	location.reload(true);
}
</script>