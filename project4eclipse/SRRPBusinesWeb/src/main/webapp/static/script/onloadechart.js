$(function () {
	var score =  document.getElementById('score').value;    
	var level =  document.getElementById('level').value; 
	var allData =  document.getElementById('allData').value; 
	var jsonData = $.parseJSON(allData);
	createRadar();
	createLine();   
	createBar(score,level,jsonData);
	$('#all').bind("click", function(){
		 createBar(score,level,jsonData);
		 var context= "<span>该企业得分为"+score+"优于 "+jsonData.rankPercent+" 的企业 <br/>中位数企业："+jsonData.typeName+" ,  "+jsonData.midScore+"   分</span>" ;
		 $("#allbar").html(context);
	 });
	
	$('#trade').bind("click", function(){
		var trateData =  document.getElementById('trateData').value; 
		var jsonData = $.parseJSON(trateData);
	    createBar(score,level,jsonData);
	    var context= "<span>该企业得分为"+score+"优于 "+jsonData.rankPercent+" 的企业 <br/>中位数企业："+jsonData.typeName+" ,  "+jsonData.midScore+"   分</span>" ;
		 $("#allbar").html(context);
	 });
	
	$('#area').bind("click", function(){
		var areaData =  document.getElementById('areaData').value; 
		var jsonData = $.parseJSON(areaData);
	    createBar(score,level,jsonData);
	    var context= "<span>该企业得分为"+score+"优于 "+jsonData.rankPercent+" 的企业 <br/>中位数企业："+jsonData.typeName+" ,  "+jsonData.midScore+"   分</span>" ;
		 $("#allbar").html(context);
	 });
	
});

var $ = jQuery.noConflict();
   
//雷达
function createRadar(){
	var myChart = echarts.init(document.getElementById('radar')); 
	var bstype = document.getElementById('radarResult').value;
	if(bstype == null || bstype ==""){
		bstype = "[]";
	}
	var result = $.parseJSON(bstype);
	var title = "";
	if(result.title=='1'){
		title = "*由于部分指标数据缺失，评分仅供参考";
	}
    var option = {
    		title:{
    			text:title,
    			x:'center',
    			y:'bottom',
    			textStyle:{
    				fontSize:12
    			}
    		},
	        radar:{
		        indicator:result.param
		    },
	        series: [{
	            type:'radar',
	            data:[{
		            name:'详细信息',
					value:result.value
		            }]
				}
	        ]
	    };
     myChart.setOption(option); 
}


//柱状图
function createBar(score,level,jsonData){
	var num;
	var myChart = echarts.init(document.getElementById('bar')); 
    var bstype =  document.getElementById('barParam').value; 
    //传进来的json
 	var corpData = jsonData.corpNum;
 	var midLevel = jsonData.midLevel;
 	var total =  jsonData.totalCorp;
 	var midName =  jsonData.midName; 
 	var midScore = jsonData.midScore;
	if(corpData == null || corpData ==""){
		corpData = "[]";
	}
	if(bstype == null || bstype ==""){
		bstype = "[]";
	}
	var jsonBstype = $.parseJSON(bstype);
	var offset = 0;
	var midOffset = 0;
	if(level==midLevel){
		offset = 16;
		midOffset = -16;
	}
    var option = {
    	    tooltip: {
	            trigger: 'axis',
	            show:false,  
	        },
	        calculable:true,
	        grid: {
    	        left: '10%',
    	        right: '15%',
    	        top:'20%',
    	        bottom: '3%',
    	        containLabel: true
    	    },
	        xAxis:[{
	               type:'category',
	               name:'得分区间',
	               axisLabel:{
			           interval :0
			           ,rotate : 15
		            },
	               data:jsonBstype.XAxis
	        }],
			yAxis:[
		       {
			       type:'value',
			       name:'企业数'
			   }
			],
	        series: [{
		        name:'企业数',
	            type:'bar',
	            data:corpData,
	            markPoint:{
		            symbolOffset:[0,-20],
	            	label:{
            			normal:{
    						formatter:function(params){
    							return params.data.value;
    						}
    					}
            		},
            		tooltip:{
            			trigger:'item',
            			show:true,
   						formatter:function(params){
   							return params.data.name;
   						}
            		},
	            	data:[
	            	      {name:midName+':'+midScore+'分',symbolSize:60,value:'中位数',xAxis:parseInt(midLevel),yAxis:corpData[midLevel],itemStyle:{normal:{color:'#C1232B'}},symbolOffset:[midOffset,-20]},
	            	      {name:'本企业:'+score+'分',value:score,xAxis:parseInt(level),yAxis:corpData[level],symbolOffset:[offset,-20]}
				    ]
				},
				itemStyle:{
					normal:{
						color:'#4F81BD',
						label:{
							show:true,
							position:'top',
							formatter:function(param){
								if(total == 0){
									num = 100
									return param.value+'('+num.toFixed(2)+'%)';
								}else{
									num = param.value/total*100
									return param.value+'('+num.toFixed(2)+'%)';
								}
							}
						}
					}
				}
				
			}]
	    };
myChart.setOption(option); 
}
     

//折线图
function createLine(){
	var myChart = echarts.init(document.getElementById("line"),'macarons');
	var bstype = document.getElementById('lineData').value;//$("input[id$='lineData']",parent.document).val();  
	if(bstype == null || bstype ==""){
		bstype = "[]";
	}
	var jsonBstype = $.parseJSON(bstype);
    var option = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
        	data:['信用评分','行业平均分']
        },
        //右上角工具条
        toolbox: {
            feature : {
                saveAsImage : {}
            }
        },
        grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        top:'15%',
	        containLabel: true
	    },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data: jsonBstype.month
            }
        ],
        yAxis: {
	        type: 'value',
	        max:850,
	        min:200
	    },
	    series: [
     	        {
     	            name:'信用评分',
     	            type:'line',
     	            data:jsonBstype.data[0].value
     	        },
		        {
		            name:'行业平均分',
		            type:'line',
		            data:jsonBstype.data[5].value
		        }
     	    ]
    };
    // 为echarts对象加载数据
    myChart.setOption(option);
 }
   





