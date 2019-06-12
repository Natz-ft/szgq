//var jq = $.noConflict();
//for corporation select
(function(){
	//change layout
	jq(function(){
		jq(window).resize(function(){
			x=document.body.clientWidth-jq("#left").width()-260;
			jq(".input_list").css("margin-top","-33px");
			jq(".tool_tit a").css("position","relative");
		});
		jq(window).resize();
	});
	
	//
	jq(document).ready(function(){
		jq(".control_ht").click(function(){
			var grid=jq(".input_list");
			if(jq(".control_ht").hasClass("control_ht_tall")){
				grid.animate({height:23},300);
				grid.removeClass("ui-grid-all");
				grid.addClass("ui-grid-1row");
				jq(".control_ht span").html("一键展开");
				jq(this).removeClass("control_ht_tall");
				jq(this).addClass("control_ht_short");
				jq(".a_tool").removeClass("a_tool_hidden");
				jq(".a_tool").addClass("a_tool_show");
				jq(".input_list").each(function(){
					jq(this).parent().find(".a_all").css("display","none");
					/*if(jq(this).find(".listheight").height()<50){
						jq(this).parent().find(".a_all").css("display","block");
					}
					else{jq(this).parent().find(".a_all").css("display","none");}*/
				});
			}else{
				grid.css("height","auto");
				grid.removeClass("ui-grid-1row");
				grid.addClass("ui-grid-all");
				jq(".control_ht span").html("一键收起");
				jq(this).removeClass("control_ht_short");
				jq(this).addClass("control_ht_tall");
				jq(".a_tool").removeClass("a_tool_show");
				jq(".a_tool").addClass("a_tool_hidden");
				jq(".a_all").css("display","block");
			}
		});
		
		jq(".a_tool").click(function(){
			var a=jq(this).parent().parent().find(".input_list");
			if(a.hasClass("ui-grid-1row")){
				a.css("height","auto");
				a.removeClass("ui-grid-1row");
				a.addClass("ui-grid-all");
				jq(this).removeClass("a_tool_show");
				jq(this).addClass("a_tool_hidden");
				jq(this).parent().find(".a_all").css("display","block");
			}else{
				a.animate({height:23},300);
				a.removeClass("ui-grid-all");
				a.addClass("ui-grid-1row");
				jq(this).removeClass("a_tool_hidden");
				jq(this).addClass("a_tool_show");
				jq(this).parent().find(".a_all").css("display","none");
			}
			
		});
		
	});	
	

	jq(window).resize(function(){
		jq(".input_list").each(function(){
			jq(this).parent().find(".a_tool").css("display","block");
			jq(this).parent().find(".a_all").css("display","none");
			//jq(this).parent().find(".a_all").css("display","block");
			/*if(jq(this).find(".listheight").height()>50){
				jq(this).parent().find(".a_tool").css("display","block");	
			}
			else{
				jq(this).parent().find(".a_tool").css("display","none");	
				}
			if(jq(this).height()>=jq(this).find(".listheight").height()-10){
				jq(this).parent().find(".a_all").css("display","block");
			}
			else{
				jq(this).parent().find(".a_all").css("display","none");
				}*/
		});
	});
	
	jq(function(){
		jq(window).resize();
	});
	
})();




