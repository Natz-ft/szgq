<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>middle</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
body{
	overflow:hidden;
	margin:0px;
	border-left:1px solid #ccc; 
	<%--background-color: #eee;--%>
	background-color:#EAF3F8;
}
img{
	opacity: 0.6;
	filter:Alpha(Opacity=60);
	cursor: pointer;
}
img:hover{
	opacity: 1;
	filter:Alpha(Opacity=100);
}
</style>
<script>
function shift_status(){
	if(parent.menuLayout.cols=="210,12,*"){
		parent.menuLayout.cols="0,12,*";
		document.all.hide.style.display='none';
		document.all.show.style.display='';
	}else{
		parent.menuLayout.cols="210,12,*";
		document.all.hide.style.display='';
		document.all.show.style.display='none';
	}
}
</script>
</head>
<body>
<table  border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" >
              <tr>
                <td style="height: 100px;height: 200px\9;text-indent: -9999px">1</td>
              </tr>
              <tr>
                <td id="hide"><a onclick="shift_status()"><img src="static/image/button/arrow-r.png" width="11"/></a></td>
                <td id="show" style="display:none;"><a onclick="shift_status()"><img src="static/image/button/arrow-l.png" width="11" /></a></td>
              </tr>
              <tr>
                <td style="height: 100px;text-indent: -9999px">1</td>
              </tr>
          </table>
</body>
</html>
