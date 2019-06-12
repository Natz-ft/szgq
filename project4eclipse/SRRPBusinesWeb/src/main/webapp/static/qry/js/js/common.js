var jq = $.noConflict();

//menu
jq(document).ready(function(){
	jq("div.menu_tit").click(function(){
		var arrow=jq(this);
		if(arrow.hasClass("arrow01")){
			arrow.removeClass("arrow01");
			arrow.addClass("arrow02");
		}else if(arrow.hasClass("arrow02")){
			arrow.removeClass("arrow02");
			arrow.addClass("arrow01");
		}
		jq(this).parent().find("div.menu_list").slideToggle(300);
	})
});


//index tab
function show(showContent,selfObj){
	 var tag=document.getElementById("tagUl").getElementsByTagName("a");
	 var taglength=tag.length;
	 for(i=0; i<taglength;i++){
	 	tag[i].className="i_out";
	 }
	 selfObj.className="i_current";
	 for(i=0;j=document.getElementById("box"+i);i++){
	 	j.style.display="none";
	 }
	 document.getElementById(showContent).style.display="block";
}

//table
jq(function(){
	jq(".table tr").mouseover(function(){jq(this).addClass("mouse_color");});
	jq(".table tr").mouseout(function(){jq(this).removeClass("mouse_color");});
		
	jq('.table tr').click(function(){
		if((this.style.backgroundColor=="")||(this.style.backgroundColor=='#fff')){
			this.style.cssText='background-color:#FFFCEB';
		}else{
			this.style.cssText='background-color:none';
		}
	});
})


//table //select
jq(function(){
	jq(".selAll").click(function(){
		jq('input[name="slct"]').attr("checked",this.checked);
	})	
	var jqslct=jq("input[name='slct']");
	jqslct.click(function(){
		jq(".selAll").attr("checked",jqslct.length==jq("input[name='slct']:checked").length ? true:false);
	});
});


//table //sort
jq(function(){
	jq('table.table_b').each(function(){
		var jqtable=jq(this);
		var jqtablehead=jq('.table_h');
		jq('th',jqtablehead).each(function(column){
			var findSortKey;
			if (jq(this).is('.sort-alpha')){
				findSortKey=function(jqcell){
					return jqcell.find('sort-key').text().toUpperCase()+''+jqcell.text().toUpperCase();
				};
			}else if(jq(this).is('.sort-numeric')){
				findSortKey=function(jqcell){
					var key=parseFloat(jqcell.text().replace(/^[^\d.]*/,''));
					return isNaN(key) ? 0:key;
				};
			}
			
			if(findSortKey){
				jq(this).addClass('clickable').hover(function(){jq(this).addClass('hover');},function(){ jq(this).removeClass('hover');}).click(function(){
					var newDirection=1;
					if(jq(this).is('.sorted-asc')){
						newDirection=-1;
					}
					var rows=jqtable.find('tbody>tr').get();
					jq.each(rows,function(index,row){
						row.sortKey=findSortKey(jq(row).children('td').eq(column));
					});
					rows.sort(function(a,b){
						if(a.sortKey < b.sortKey) return -newDirection;
						if(a.sortKey > b.sortKey) return newDirection;
						return 0;
					});
					jq.each(rows,function(index,row){
						jqtable.children('tbody').append(row);
						row.sortKey=null;
					});
					
					jqtablehead.find('th').removeClass('sorted-asc').removeClass('sorted-desc');
					var jqsortHead = jqtablehead.find('th').filter(':nth-child('+(column+1)+')');
					if (newDirection==1){
						jqsortHead.addClass('sorted-asc');
					}else{
						jqsortHead.addClass('sorted-desc');	
					}
				})	
			}
		})
	})	   
});

//style
jq(document).ready(function(){
	jq(".topbtn li").mouseover(function(){
		jq(this).children(".style_control").css("display","block");
		jq(this).addClass("style_hover");
	})
	jq(".topbtn li").mouseout(function(){
		jq(this).children(".style_control").css("display","none");
		jq(this).removeClass("style_hover");
	})
});

//radio
jq(document).ready(function(){
	jq(".radio_t").click(function(){
		jq("#showbox").show(300);
	})
	jq(".radio_f").click(function(){
		jq("#showbox").hide(300);
	})
});

//new 0330 
jq(function(){
	jq("#btnHidden").click(function(){
		jq("#hiddentab").css("display","block");
	});
});


jq(function(){
	jq(".checkbox0").click(function(){
		
		// 复选框总个数
		var num_total=0;
		// 已选中个数
		var num_checked=0;
		
		// 未选中
		var num_unchecked=0;
		
		if(jq(this).hasClass("checkbox1")){
			jq(this).removeClass("checkbox1");
			jq(this).addClass("checkbox2");
			jq(this).attr("checked","true");
			jq(this).parent().find(".input_range").css("display","inline");
			jq(this).parent().find(".boxwh li .checkbox0").each(function(){
											jq(this).removeClass("checkbox1");
											jq(this).addClass("checkbox2");
											jq(this).attr("checked","true");
								})
			}else{
				jq(this).removeClass("checkbox2");
				jq(this).addClass("checkbox1");
				jq(this).removeAttr("checked");
				jq(this).parent().find(".input_range").css("display","none");
				jq(this).parent().find(".boxwh li .checkbox0").each(function(){
											jq(this).removeClass("checkbox2");
											jq(this).addClass("checkbox1");
											jq(this).removeAttr("checked");
								})
			}
		
		jq(this).parent().parent().find(".checkbox0").each(function(){
			num_total++;					
		})

		jq(this).parent().parent().find(".checkbox2").each(function(){
					num_checked++;
		})
		
		jq(this).parent().parent().find(".checkbox1").each(function(){
			num_unchecked++;
		})
		
		
		var index = 0;
		
		if(num_checked == 1){
			var count = 0;
			jq(this).parent().parent().parent().parent().find(".checkbox1").each(function(){
				count ++;
			});

			// 解决逐个取消时，复选框不能取消bug
			if(count > num_unchecked){
				jq(this).parent().parent().parent().parent().find(".checkbox1").each(function(){
					jq(this).removeClass("checkbox1");
					jq(this).addClass("checkbox2");
					jq(this).attr("checked","true");
					index ++;
					return false;
				});
			}
		}
		
		// 全部都未选中，取消根节点全选
		if(num_unchecked == num_total){
			jq(this).parent().parent().parent().parent().find(".checkbox2").each(function(){
			jq(this).removeClass("checkbox2");
			jq(this).addClass("checkbox1");
			jq(this).removeAttr("checked");
			return false;
			})
		}
	});
});

jq(function(){
	jq(".arrow_d").click(function(){
		if(jq(this).hasClass("ar1")){
			jq(this).removeClass("ar1");
			jq(this).addClass("ar2");
			if(jq(this).parent().parent().parent().parent().find(".a_tool").hasClass("a_tool_show")){
				jq(this).parent().parent().parent().parent().find(".a_tool").click();
			}
			jq(this).parent().find(".boxwh").css("display","block");
		}else{
			jq(this).removeClass("ar2");
			jq(this).addClass("ar1");
			jq(this).parent().find(".boxwh").css("display","none");
			}
	});
});
/*
jq(function(){
	jq(".listheight").find(".input_block").each(function(){
			jq(this).mouseover(function(){
				if(jq(this).parent().find(".arrow_d").hasClass("ar2")){
					jq(this).find(".boxwh").css("display","none");
					jq(this).parent().find(".arrow_d").removeClass("ar2");
					jq(this).parent().find(".arrow_d").addClass("ar1");
				}else{
					jq(this).find(".boxwh").css("display","block").css("overflow-y","scroll").css("height","120px");
					jq(this).parent().find(".arrow_d").removeClass("ar1");
					jq(this).parent().find(".arrow_d").addClass("ar2");
				}
			}).mouseout(function(){
				if(jq(this).parent().find(".arrow_d").hasClass("ar2")){
					jq(this).find(".boxwh").css("display","none");
					jq(this).parent().find(".arrow_d").removeClass("ar2");
					jq(this).parent().find(".arrow_d").addClass("ar1");
				}else{
					jq(this).find(".boxwh").css("display","block").css("overflow-y","scroll").css("height","120px");
					jq(this).parent().find(".arrow_d").removeClass("ar1");
					jq(this).parent().find(".arrow_d").addClass("ar2");
				}
			})	
	})		
});*/
/*jq(function(){
	jq(".a_all").click(function(){
		jq(this).parent().parent().find(".checkbox1").each(function(){
											jq(this).removeClass("checkbox1");
											jq(this).addClass("checkbox2");							
								})			   
	});
});*/

jq(function(){
	jq(".a_all").click(function(){
		// 复选框总个数
		var num_total=0;
		// 已选中个数
		var num_checked=0;
		
		jq(this).parent().parent().find(".checkbox0").each(function(){
											num_total++;					
		})
				
		jq(this).parent().parent().find(".checkbox2").each(function(){
											num_checked++;					
		})
		
		// 选中所有复选框时，再全选时，取消全选
		if(num_total == num_checked){
			jq(this).parent().parent().find(".checkbox2").each(function(){
				jq(this).removeClass("checkbox2");
				jq(this).addClass("checkbox1");	
				jq(this).removeAttr("checked");
			})
		}else{
			jq(this).parent().parent().find(".checkbox1").each(function(){
				jq(this).removeClass("checkbox1");
				jq(this).addClass("checkbox2");
				jq(this).attr("checked","true");
			})		
		}			   
	});
});

jq(function(){
	jq(".btn_search").click(function(){
		jq(".result").css("display","block");
		jq(".result").css("width","100%");
		jq(".blanktab").css("display","none");
		jq(".blanktab").css("width","100%");
	});
});

//checkbox
jq(function(){
	jq(".btn_search").click(function(){
		jq(".result").css("display","block");
		jq(".result").css("width","100%");
		jq(".blanktab").css("display","none");
		jq(".blanktab").css("width","100%");
	});
});
function show(showContent,selfObj){
	 var tag=document.getElementById("tagUl").getElementsByTagName("a");
	 var taglength=tag.length;
	 for(i=0; i<taglength;i++){
	 	tag[i].className="i_out";
	 }
	 selfObj.className="i_current";
	 for(i=0;j=document.getElementById("box"+i);i++){
	 	j.style.display="none";
	 }
	 document.getElementById(showContent).style.display="block";
}
jq(document).click(function (event){
	var e = event || window.event;
	var elem = e.target || e.srcElement;
	while(elem){
		if(elem.className && (elem.className.indexOf("boxwh")>-1||elem.className.indexOf("arrow_d")>-1)){
			return;
		}
		elem = elem.parentNode;
	}
	jq("div[class='boxwh'][style*='block']").css("display","none").parent().find(".arrow_d").removeClass("ar2").addClass("ar1");
//	for(var i=0;i<jq("div[class='boxwh']").length;i++){
//		if(jq("div[class='boxwh']")[i].style.display=="block"){
//			jq("div[class='boxwh']")[i].style.display="none";
//			jq(jq("div[class='boxwh']")[i]).parent().find(".arrow_d").removeClass("ar2").addClass("ar1");
//		}
//	}
});

/*jq(document).delegate("div[class!='boxwh']","click",function (){
	jq("div[class='boxwh']").css("display","none");
});*/
//
