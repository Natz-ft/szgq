function setPageSize(sel) {
  $("#maxSize").val(sel.value);
  recordCnt
  document.list_form_name.submit();  
}


 function doPage(no) {
	if (typeof(no) == "undefined") {
		no = "0";
	}
	$("#curPage").val(no);
	  document.list_form_name.submit();  
}
function goPage() {
	
	var number = /^[0-9]*[1-9][0-9]*$/;
 	if ( !number.test( $("#gono").val() )) {
 		$("#gono").val("");
		return ;
	} 
	 if ( parseInt($("#pageCnt").val() ) < parseInt( $("#gono").val() ) ) {
		$("#gono").val($("#pageCnt").val());
		return ;
	} 
	 
	 if ( parseInt( $("#gono").val() )<1 ) {
		$("#gono").val($("#pageCnt").val());
		return ;
	} 
	$("#curPage").val(document.getElementById("gono").value);
	  document.list_form_name.submit();  
} 




/**
 * 回车时间
 */
function getEnter(){
    if(event.keyCode == 13 && event.srcElement.type!='submit'){
      event.returnValue=false;
      goPage() ;
     } 
  }



function query_conditions(query_conditions) {
	$("#"+query_conditions).show();
	$("#zhezhao").show();
	$(".zDialogCon").css("overflow","hidden"); 
}


/**
 * 查询框的取消
 */
/*function cancel_query_dialog() {
	$("#configName_shurukuang").val("");
	$("#search_LIKE_configName").val("");
}*/
