<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>我的桌面</title>
	<style type="text/css">
		.tb{
		    
			border:1px solid #c5d0dc; 
			width:1000px;
			margin:0 auto;
		}
		tr{height: 23px}
		th{
			border-right:1px solid #c5d0dc; 
			border-bottom:1px solid #c5d0dc; 
			text-align:center;
			font-weight: normal !important;
		}
		
		.th1{
			text-align :left;
			text-indent:20px;
			border-right:1px solid #c5d0dc; 
		}

		 a:hover,
		 a:active{
			color:RGB(0,0,255);
			text-decoration: none;
		}
		
		.th2 a:link,
		.th2 a:visited{
			color:RGB(255,0,0);
			text-decoration: none;
		}
		
		.th3 a:link,
		.th3 a:visited{
			color:RGB(0,0,255);
			text-decoration: none;
		}
		
		
		.headr1{
			text-align:center;
			border-right:1px solid #c5d0dc; 
		}
		
		.headcell1{
			text-align:center;
			border-right:1px solid #c5d0dc; 
			width:150px;
			background:#DE3762 !important;
			color:#fff !important;
			font-weight: bold;
		}
		
		.headcell2,.headcell3{
			text-align:center;
			border-right:1px solid #c5d0dc; 
			background: #DE3762;
			color:#fff !important;
			font-weight: bold;
		}
		
	</style>
	<script type="text/javascript">
		function creatcell(rst,rsurl,flag){
			var rshtml="";
			if(rst==""){
				rshtml="<th>-</th>";
			}else{
				if(rst!=0){
					if(rsurl!=""){
						if(flag==6||flag==11){
							rshtml="<th  class='th2' ><a  href="+rsurl+">"+Number(rst)+"</a></th>";
						}else{
							rshtml="<th class='th3'><a href="+rsurl+">"+Number(rst)+"</a></th>";	
						}
						
					}else if(rsurl==""){
						rshtml="<th>"+rst+"</th>";
					}
				}else if(rst==0){
					rshtml="<th>"+rst+"</th>";
				}
			}
			return rshtml;
		}
		function pageLoad(){
			var datas = {};
			datas.data = eval('${data}');
			var orgspace ="<tr><th class='headcell1' rowspan='2'>对公</br>业务事项</th><th colspan='6' class='headcell2'>今日事项</th><th colspan='5' class='headcell3'>逾期事项</th></tr>"
				+"<tr><th class='headcell2'>待处理</th><th class='headcell2'>待审核</th><th class='headcell2'>待审批</th><th class='headcell2'>个人退回</th><th class='headcell2'>网点退回</th><th class='headcell2'>今日未完成</th>"
				+"<th class='headcell3'>待处理</th><th class='headcell3'>待审核</th><th class='headcell3'>待审批</th><th class='headcell3'>个人退回</th><th class='headcell3'>网点退回</th></tr>";
			
			var personspace ="<tr><th class='headcell1' rowspan='2'>对私</br>业务事项</th><th colspan='6' class='headcell2'>今日事项</th><th colspan='5' class='headcell3'>逾期事项</th></tr>"
				+"<tr><th class='headcell2'>待处理</th><th class='headcell2'>待审核</th><th class='headcell2'>待审批</th><th class='headcell2'>个人退回</th><th class='headcell2'>网点退回</th><th class='headcell2'>今日未完成</th>"
				+"<th class='headcell3'>待处理</th><th class='headcell3'>待审核</th><th class='headcell3'>待审批</th><th class='headcell3'>个人退回</th><th class='headcell3'>网点退回</th></tr>";
				
			var otherspace ="<tr><th rowspan='2' class='headcell1'>工作事项</th><th colspan='6' class='headcell2'>今日事项</th><th colspan='5' class='headcell3'>逾期事项</th></tr>"
				+"<tr><th class='headcell2'>待处理</th><th class='headcell2'>待审核</th><th class='headcell2'>待审批</th><th class='headcell2'>个人退回</th><th class='headcell2'>网点退回</th><th class='headcell2'>今日未完成</th>"
				+"<th class='headcell3'>待处理</th><th class='headcell3'>待审核</th><th class='headcell3'>待审批</th><th class='headcell3'>个人退回</th><th class='headcell3'>网点退回</th></tr>";
			top1 = "",top2 = "",top3 = "";
			if(datas['data'] != null){
				for(var k = 0;k<datas['data'].length;k++){
					if(datas['data'][k]['typ_cd']=="TASK_ORG"){
						top1 =top1+"<tr><th class='th1'>"+datas['data'][k]['tsk_nm']+"</th>";
						top1 =top1+creatcell(datas['data'][k]['tsk2_rst'],datas['data'][k]['tsk2_url'],2);
						top1 =top1+creatcell(datas['data'][k]['tsk4_rst'],datas['data'][k]['tsk4_url'],4);
						top1 =top1+creatcell(datas['data'][k]['tsk5_rst'],datas['data'][k]['tsk5_url'],5);
						top1 =top1+creatcell(datas['data'][k]['tsk6_rst'],datas['data'][k]['tsk6_url'],6);
						top1 =top1+creatcell(datas['data'][k]['tsk3_rst'],datas['data'][k]['tsk3_url'],3);
						top1 =top1+creatcell(datas['data'][k]['tsk1_rst'],datas['data'][k]['tsk1_url'],1);
							
						top1 =top1+creatcell(datas['data'][k]['tsk7_rst'],datas['data'][k]['tsk7_url'],7);
						top1 =top1+creatcell(datas['data'][k]['tsk9_rst'],datas['data'][k]['tsk9_url'],9);
						top1 =top1+creatcell(datas['data'][k]['tsk10_rst'],datas['data'][k]['tsk10_url'],10);
						top1 =top1+creatcell(datas['data'][k]['tsk11_rst'],datas['data'][k]['tsk11_url'],11);
						top1 =top1+creatcell(datas['data'][k]['tsk8_rst'],datas['data'][k]['tsk8_url'],8);
						top1 =top1+"</tr>";
					}
					
					if(datas['data'][k]['typ_cd']=="TASK_PERSON"){
						
						top2 =top2+"<tr><th class='th1'>"+datas['data'][k]['tsk_nm']+"</th>";
						top2 =top2+creatcell(datas['data'][k]['tsk2_rst'],datas['data'][k]['tsk2_url'],2);
						top2 =top2+creatcell(datas['data'][k]['tsk4_rst'],datas['data'][k]['tsk4_url'],4);
						top2 =top2+creatcell(datas['data'][k]['tsk5_rst'],datas['data'][k]['tsk5_url'],5);
						top2 =top2+creatcell(datas['data'][k]['tsk6_rst'],datas['data'][k]['tsk6_url'],6);
						top2 =top2+creatcell(datas['data'][k]['tsk3_rst'],datas['data'][k]['tsk3_url'],3);
						top2 =top2+creatcell(datas['data'][k]['tsk1_rst'],datas['data'][k]['tsk1_url'],1);
						
						top2 =top2+creatcell(datas['data'][k]['tsk7_rst'],datas['data'][k]['tsk7_url'],7);
						top2 =top2+creatcell(datas['data'][k]['tsk9_rst'],datas['data'][k]['tsk9_url'],9);
						top2 =top2+creatcell(datas['data'][k]['tsk10_rst'],datas['data'][k]['tsk10_url'],10);
						top2 =top2+creatcell(datas['data'][k]['tsk11_rst'],datas['data'][k]['tsk11_url'],11);
						top2 =top2+creatcell(datas['data'][k]['tsk8_rst'],datas['data'][k]['tsk8_url'],8);
						top2 =top2+"</tr>";
					}
					
					if(datas['data'][k]['typ_cd']=="TASK_MD"){
						
						top3 =top3+"<tr><th class='th1'>"+datas['data'][k]['tsk_nm']+"</th>";
						top3 =top3+creatcell(datas['data'][k]['tsk2_rst'],datas['data'][k]['tsk2_url'],2);
						top3 =top3+creatcell(datas['data'][k]['tsk4_rst'],datas['data'][k]['tsk4_url'],4);
						top3 =top3+creatcell(datas['data'][k]['tsk5_rst'],datas['data'][k]['tsk5_url'],5);
						top3 =top3+creatcell(datas['data'][k]['tsk6_rst'],datas['data'][k]['tsk6_url'],6);
						top3 =top3+creatcell(datas['data'][k]['tsk3_rst'],datas['data'][k]['tsk3_url'],3);
						top3 =top3+creatcell(datas['data'][k]['tsk1_rst'],datas['data'][k]['tsk1_url'],1);
						
						top3 =top3+creatcell(datas['data'][k]['tsk7_rst'],datas['data'][k]['tsk7_url'],7);
						top3 =top3+creatcell(datas['data'][k]['tsk9_rst'],datas['data'][k]['tsk9_url'],9);
						top3 =top3+creatcell(datas['data'][k]['tsk10_rst'],datas['data'][k]['tsk10_url'],10);
						top3 =top3+creatcell(datas['data'][k]['tsk11_rst'],datas['data'][k]['tsk11_url'],11);
						top3 =top3+creatcell(datas['data'][k]['tsk8_rst'],datas['data'][k]['tsk8_url'],8);
						top3 =top3+"</tr>";
					}
					
					orgspace=orgspace+top1;
					personspace=personspace+top2;
					otherspace=otherspace+top3;
					top1="";
					top2="";
					top3="";
				}
				try{
					document.getElementById("tborgspace").innerHTML=orgspace;
					document.getElementById("tbpersonspace").innerHTML=  personspace;
					document.getElementById("tbotherspace").innerHTML =  otherspace;
				}catch(e){
					var div = document.createElement("div");
					div.innerHTML= '<table>' + orgspace +'</table>';
					document.getElementById("tborgspace").parentNode.replaceChild(div.firstChild.firstChild, document.getElementById("tborgspace"));
					
					div.innerHTML= '<table>' + personspace +'</table>';
					document.getElementById("tbpersonspace").parentNode.replaceChild(div.firstChild.firstChild, document.getElementById("tbpersonspace"));
					
					div.innerHTML= '<table>' + otherspace +'</table>';
					document.getElementById("tbotherspace").parentNode.replaceChild(div.firstChild.firstChild, document.getElementById("tbotherspace"));
				}
				
				/* $('tborgspace').set("html",orgspace); 
				$('tbpersonspace').set("html",personspace); 
				$('tbotherspace').set("html",otherspace); */
			}
		}
	</script>
</head>
	<body style="border: 0;padding: 0;margin: 0;background: #FFFFFF;font-family:font-family:Microsoft YaHei,arial,helvetica,clean,sans-serif;font-size: 14px" onload="pageLoad()"> 
		
		<div style="height:15px; width:100%"></div>
		<table class="tb">
			<tbody id="tbotherspace">
			</tbody>
		</table>
		<div style="height:15px; width:100%"></div>
		<table class="tb">
			<tbody id="tbpersonspace">
			</tbody>
		</table>
		<div style="height:15px; width:100%"></div>
		
		<table class="tb">
			<tbody id="tborgspace">
			</tbody>
		</table>
		


	</body>
</html>