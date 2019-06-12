function loadEventInit(){
	//ajax获取数据
    //区域统计数据
	 $.ajax({
	        type: "get",//请求方式
	        url: "succFinacedDemand.json",//url地址
	        cache: false,//清楚缓存
	        async: false,
	        success: function (data) {
	        	loadEventData(data);
	        }
	    });
}
//该js共包含三部分内容  1.项目需求--融资轮次统计 2.项目需求--区域统计 3.项目需求--行业前十
function loadEventData(data){
    var event_area_map_data;//数据
    var event_area_map_post;//发布总数(项)
    var event_area_map_amount;//需求总额(M)
    //融资轮次统计柱状图
    var event_finance_histogram_data;
    //行业前十统计倒置柱状图
    var event_rank_downhistogram_data;
    var finac_TotalDate;
    var area_TotalDate;
    var indusry_TotalDate;
    var monthly_TotalDate;
    event_area_map_data = data.areaData;//地图数据
    event_finance_histogram_data = data.finacTurnData;//柱状图数据
    // console.log(event_finance_histogram_data);
    event_rank_downhistogram_data = data.industryData;//倒置柱状图数据
    event_finance_histogram_month_data= data.monthlyData;//倒置柱状图数据
    finac_TotalDate=data.finacTotalDate;
    area_TotalDate=data.areaTotalDate
    indusry_TotalDate=data.indusryTotalDate;
    monthly_TotalDate=data.monthlyTotalDate;


    //1.投融事件--轮次统计
    //柱状图x轴上的数组数据拼接
    //平台共发布融资需求 融资共发布融资总额的获取赋值
    var histogram_data_x_key = '';
    var histogram_data_y_value = '';
    var histogram_data_z_value = '';
    if($.isEmptyObject(event_finance_histogram_data)!=true){
    	histogram_data_x_key = event_finance_histogram_data.x_key.split(',');//x轴数据
        histogram_data_y_value = event_finance_histogram_data.y_value.split(',');//y轴数据
        histogram_data_z_value = event_finance_histogram_data.z_value.split(',');//为了便于悬浮展示需求数量增加一个z轴数据(只是数据项)
    	document.getElementById('finance_event_totle_histogram_need').innerHTML = event_finance_histogram_data.pushCount;
    	document.getElementById('finance_event_totle_histogram_amount').innerHTML = event_finance_histogram_data.finacMoney;
    }else{
    	histogram_data_x_key=["种子轮", "天使轮", "Pre-A轮", "A轮","B轮", "C轮", "D轮", "E轮","F轮", "G轮", "新三板定增", "上市定增"];//x轴数据
    	histogram_data_y_value=["0", "0", "0", "0","0", "0", "0", "0","0", "0", "0", "0"];//x轴数据
    	histogram_data_z_value=["0", "0", "0", "0","0", "0", "0", "0","0", "0", "0", "0"];//x轴数据
    	document.getElementById('finance_event_totle_histogram_need').innerHTML = '0';
        document.getElementById('finance_event_totle_histogram_amount').innerHTML = '0';
    }
    document.getElementById('event_finac_TotalDate').innerHTML = finac_TotalDate;

    var histogramChart = echarts.init(document.getElementById('finance_event_totle_histogram_id'));
    histogramChartOption = {
        color: ['#3398DB'],
        //显示图例
        legend: {
            // 显示策略，可选为：true（显示） | false（隐藏）
            show: true,
            // 布局方式，默认为水平布局，可选为：'horizontal' | 'vertical'
            orient: 'horizontal',
            // 水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
            x: 'center',
            // 垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
            y: '480',
            // 图例背景颜色，默认透明
            // backgroundColor: '#ffffff',
            // 图例边框颜色
            borderColor: '#ECF4FF',
            // 图例边框线宽，单位px，默认为0（无边框）
            borderWidth: 2,
            // 图例边框圆角
            borderRadius: 0,
            // 图例内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距，同css
            padding: [0, 0, 0, 0],
            // 各个item之间的间隔，单位px，默认为10，横向布局时为水平间隔，纵向布局时为纵向间隔
            itemGap: 20,
            // 图例图形宽度
            itemWidth: 15,
            // 图例图形高度
            itemHeight: 12,
            // 默认只设定了图例文字颜色,更个性化的是，要指定文字颜色跟随图例,可设color为'auto'
            textStyle: {
                // 颜色
                color: '#000',
                // 水平对齐方式，可选为：'left' | 'right' | 'center'
                align: 'left',
                // 垂直对齐方式，可选为：'top' | 'bottom' | 'middle'
                baseline: 'bottom',
                // 字体系列
                fontFamily: 'Arial, Microsoft YaHei, sans-serif',
                // 字号 ，单位px
                fontSize: 12,
                // 样式，可选为：'normal' | 'italic' | 'oblique'
                fontStyle: 'normal',
                // 粗细，可选为：'normal' | 'bold' | 'bolder' | 'lighter' | 100 | 300 |... | 900
                fontWeight: 'normal'
            },
            // 选择模式，默认开启图例开关，可选single，multiple
            selectedMode: "multiple",
            // 图例内容数组
            data: [{name: '投融事件(项)', icon: 'image://runningresult/images/need_icon.jpg'}, {name: '投资金额(M)'}]
        },
        tooltip: {
            trigger: 'item',
            // trigger: 'axis',
            show: true,
            showDelay: 0,
            hideDelay: 0,
            transitionDuration: 0,
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow' |'cross'
            },
            backgroundColor: '#ffffff',
            borderColor: '#987ECA',
            borderRadius: 8,
            borderWidth: 1,
            padding: 15,
            textStyle: {
                color: '#333333',
            },
            formatter: function (params, ticket, callback) {
                var tooltip_info;
                //根据params.dataIndex下标取出对应的需求数拼接提示信息(z_value[params.dataIndex])
                if (params.seriesType == 'bar') {
                    tooltip_info = params.name + "融资" + '<br/>' + "投融事件：" + params.value + "项" + '<br/>' + "投资金额：" + histogram_data_y_value[params.dataIndex] + "M";
                } else {
                    tooltip_info = params.name + "融资" + '<br/>' + "投融事件：" + histogram_data_z_value[params.dataIndex] + "项" + '<br/>' + "投资金额：" + params.value + "M";

                }
                return tooltip_info;
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '18%',
            containLabel: true,
        },
        dataZoom: {
            show: false,
            realtime: true,
            start: 0,
            end: 100
        },
        xAxis: [
            {
                //name: "行业",
                type: 'category',
                axisLabel: {
                    show: true,
                    // rotate: 45,
                    interval: 0,
                },
                axisLine: {
                    show: false
                },//坐标值不显示
                axisTick: {
                    alignWithLabel: true,
                    show: false,
                },//坐标值无关键点
                data: histogram_data_x_key,
                // data: ['互联网', '生物技术/医疗健康', 'IT', '信息技术', '保险产业', '卫生服务', '政治资讯'],
            }
        ],
        yAxis: [
            {
                name: '投融事件(项)',
                type: 'value',
                nameTextStyle: {
                    color: "#33425B",
                },//坐标轴名称的文字样式
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#393E46',
                    },
                },//坐标值显示
                axisTick: {
                    show: false
                },//坐标值无关键点
                splitLine: {
                    show: true,
                    interval: 'auto',
                    lineStyle: {
                        color: ['#ddd'],
                        width: 1,
                        type: 'dashed',//'solid' 'dashed' 'dotted'
                    },
                },
            },
            {
                name: '投资金额(M)',
                type: 'value',
                nameTextStyle: {
                    color: "#33425B",
                },//坐标轴名称的文字样式
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#393E46',
                    },
                },//坐标值显示
                axisTick: {
                    show: false
                },//坐标值无关键点
                splitLine: {
                    show: false,//折线图并不显示分割线
                    interval: 'auto',
                    lineStyle: {
                        color: ['#ddd'],
                        width: 1,
                        type: 'dashed',//'solid' 'dashed' 'dotted'
                    },
                },
            }
        ],
        series: [
            {
                name: '投融事件(项)',
                type: 'bar',
                barWidth: '30%',
                data: histogram_data_z_value,
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                itemStyle: {
                    //通常情况下：
                    normal: {
                        //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                        color: function (params) {
                            var colorList = ['#0080BD', '#987ECA', '#FC954E', '#009E92', '#FEB134', '#00E47B', '#277BA2', '#8AAE92', '#B0DEDB', '#00A294'];//十个柱子颜色
                            return colorList[params.dataIndex];
                        }
                    },
                    //鼠标悬停时：
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
            },
            {
                name: '投资金额(M)',
                type: 'line',
                yAxisIndex: 1,
                data: histogram_data_y_value,
                label: {
                    normal: {
                        show: false,
                        position: 'top'
                    }
                },
                //设置折线点的颜色、折线的颜色
                itemStyle: {
                    normal: {
                        //折线点的颜色
                        color: '#005792',
                        //折线的颜色
                        lineStyle: {
                            color: '#109DD9'
                        }
                    }
                },
            }
        ],
        animationDelay: function (idx) {
            // 越往后的数据延迟越大
            return idx * 300;
        }
    };
    histogramChart.setOption(histogramChartOption);

    document.getElementById('event_area_TotalDate').innerHTML = area_TotalDate;

    //2.投融事件--区域统计
    //苏州汇总
    if($.isEmptyObject(event_area_map_data)!=true){
	    document.getElementById('suzhou_event_data_need').innerHTML = event_area_map_data.pushCount;
	    document.getElementById('suzhou_event_data_money').innerHTML = event_area_map_data.finacMoney;
	
	    //张家港市zhangJiaGang
	    document.getElementById('zhangJiaGang_event_data_need').innerHTML = event_area_map_data.data[0].zhangJiaGang;
	    document.getElementById('zhangJiaGang_event_data_money').innerHTML = event_area_map_data.data[1].zhangJiaGang;
	    //常熟市changShu
	    document.getElementById('changShu_event_data_need').innerHTML = event_area_map_data.data[0].changShu;
	    document.getElementById('changShu_event_data_money').innerHTML = event_area_map_data.data[1].changShu;
	    //太仓市taiCang
	    document.getElementById('taiCang_event_data_need').innerHTML = event_area_map_data.data[0].taiCang;
	    document.getElementById('taiCang_event_data_money').innerHTML = event_area_map_data.data[1].taiCang;
	    //昆山市kunShan
	    document.getElementById('kunShan_event_data_need').innerHTML = event_area_map_data.data[0].kunShan;
	    document.getElementById('kunShan_event_data_money').innerHTML = event_area_map_data.data[1].kunShan;
	    //工业园区gongYeYuan
	    document.getElementById('gongYeYuan_event_data_need').innerHTML = event_area_map_data.data[0].gongYeYuan;
	    document.getElementById('gongYeYuan_event_data_money').innerHTML = event_area_map_data.data[1].gongYeYuan;
	    //相城区xiangCheng
	    document.getElementById('xiangCheng_event_data_need').innerHTML = event_area_map_data.data[0].xiangCheng;
	    document.getElementById('xiangCheng_event_data_money').innerHTML = event_area_map_data.data[1].xiangCheng;
	    //姑苏区guSu
	    document.getElementById('guSu_event_data_need').innerHTML = event_area_map_data.data[0].guSu;
	    document.getElementById('guSu_event_data_money').innerHTML = event_area_map_data.data[1].guSu;
	    //高新区gaoXin
	    document.getElementById('gaoXin_event_data_need').innerHTML = event_area_map_data.data[0].gaoXin;
	    document.getElementById('gaoXin_event_data_money').innerHTML = event_area_map_data.data[1].gaoXin;
	    //吴中区wuZhong
	    document.getElementById('wuZhong_event_data_need').innerHTML = event_area_map_data.data[0].wuZhong;
	    document.getElementById('wuZhong_event_data_money').innerHTML = event_area_map_data.data[1].wuZhong;
	    //吴江市wuJiang
	    document.getElementById('wuJiang_event_data_need').innerHTML = event_area_map_data.data[0].wuJiang;
	    document.getElementById('wuJiang_event_data_money').innerHTML = event_area_map_data.data[1].wuJiang;
    }else{
    	document.getElementById('suzhou_event_data_need').innerHTML = '0';
	    document.getElementById('suzhou_event_data_money').innerHTML = '0';
	
	    //张家港市zhangJiaGang
	    document.getElementById('zhangJiaGang_event_data_need').innerHTML = '0';
	    document.getElementById('zhangJiaGang_event_data_money').innerHTML = '0';
	    //常熟市changShu
	    document.getElementById('changShu_event_data_need').innerHTML = '0';
	    document.getElementById('changShu_event_data_money').innerHTML = '0';
	    //太仓市taiCang
	    document.getElementById('taiCang_event_data_need').innerHTML = '0';
	    document.getElementById('taiCang_event_data_money').innerHTML = '0';
	    //昆山市kunShan
	    document.getElementById('kunShan_event_data_need').innerHTML = '0';
	    document.getElementById('kunShan_event_data_money').innerHTML = '0';
	    //工业园区gongYeYuan
	    document.getElementById('gongYeYuan_event_data_need').innerHTML = '0';
	    document.getElementById('gongYeYuan_event_data_money').innerHTML ='0';
	    //相城区xiangCheng
	    document.getElementById('xiangCheng_event_data_need').innerHTML = '0';
	    document.getElementById('xiangCheng_event_data_money').innerHTML = '0';
	    //姑苏区guSu
	    document.getElementById('guSu_event_data_need').innerHTML = '0';
	    document.getElementById('guSu_event_data_money').innerHTML ='0';
	    //高新区gaoXin
	    document.getElementById('gaoXin_event_data_need').innerHTML = '0';
	    document.getElementById('gaoXin_event_data_money').innerHTML = '0';
	    //吴中区wuZhong
	    document.getElementById('wuZhong_event_data_need').innerHTML ='0';
	    document.getElementById('wuZhong_event_data_money').innerHTML = '0';
	    //吴江市wuJiang
	    document.getElementById('wuJiang_event_data_need').innerHTML = '0';
	    document.getElementById('wuJiang_event_data_money').innerHTML = '0';
    }

    //3.投融事件--行业前十
    //柱状图x轴上的数组数据拼接
    
    document.getElementById('event_indusry_TotalDate').innerHTML = indusry_TotalDate;

    
    
    var downhistogram_data_y_key ='';
    var downhistogram_data_x_value ='';
    var downhistogram_data_z_value ='';
    if($.isEmptyObject(event_rank_downhistogram_data)!=true){
    //柱状图x轴上的数组数据拼接
    	downhistogram_data_y_key = event_rank_downhistogram_data.x_key.split(',');//x轴数据
        downhistogram_data_x_value = event_rank_downhistogram_data.y_value.split(',');//金额
        downhistogram_data_z_value = event_rank_downhistogram_data.z_value.split(',');//需求
	   
    }else{
    	
        downhistogram_data_y_key=["IT", "互联网", "电信及增值业务", "半导体","电子及光电设备", "……"];//x轴数据
    	downhistogram_data_x_value=["0", "0", "0", "0","0"];//x轴数据
    	downhistogram_data_z_value=["0", "0", "0", "0","0"];//x轴数据
    }
    
    var downhistogramChart = echarts.init(document.getElementById("rank_event_totle_downhistogram_id"));
    var max = '5';
    var str = '';
    for (var i = 0; i < downhistogram_data_z_value.length; i++) {
        if (downhistogram_data_z_value[i] > 4) {
            str = "true"
        }
    }
    if (str == 'true') {
        max = Math.max.apply(Math, downhistogram_data_z_value);
    }
    downhistogramChartOption = {
        tooltip: {//鼠标悬浮弹窗提示
            trigger: 'item',
            show: true,
            showDelay: 0,
            hideDelay: 0,
            transitionDuration: 0,
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow' |'cross'
            },
            backgroundColor: '#ffffff',
            borderColor: '#987ECA',
            borderRadius: 8,
            borderWidth: 1,
            padding: 15,
            textStyle: {
                color: '#333333',
            },
            formatter: function (params, ticket, callback) {
                //根据params.dataIndex下标取出对应的需求数拼接提示信息(z_value[params.dataIndex])
                var tooltip_info = params.name + "行业" + '<br/>' +
                    "投融事件：" + '<span style=color:#F75940;font-size:18px;>' + params.value + '</span>' + "项"
                    + '<br/>' + "投资金额：" + '<span style=color:#F75940;font-size:18px;>' + downhistogram_data_x_value[params.dataIndex] + '</span>' + "M";
                return tooltip_info;
            }
        },
        calculable: false,
        xAxis: [
            {
                name:'需求数(项)',
                type: 'value',
                max:max,
                axisLine: {
                    show: false
                },//坐标值不显示
                axisTick: {
                    show: false
                },//坐标值无关键点
                splitLine: {
                    show: true,
                    interval: 'auto',
                    lineStyle: {
                        color: ['#ddd'],
                        width: 1,
                        type: 'dashed',//'solid' 'dashed' 'dotted'
                    },
                },
            }
        ],
        grid: {
            left: '1%',
            containLabel: true,
        },//控制内部的距离(x轴、y轴距离边框的值)
        yAxis: [
            {
                name:'行业',
                type: 'category',
                data: downhistogram_data_y_key,
                axisLine: {
                    show: false
                },//坐标值不显示
                axisTick: {
                    show: false
                },//坐标值无关键点
                axisLabel: {
                    show: true,
                    interval: 0
                }

            }
        ],
        series: [
            {
                // name:'',
                type: 'bar',
                barWidth: '30%',
                data: downhistogram_data_z_value,
                itemStyle: {
                    normal: {
                        label: {
                            show: false,
                            position: 'right',
                        },
                        //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                        color: function (params) {
                            var colorList = ['#0080BD', '#987ECA', '#FC954E', '#009E92', '#FEB134', '#00E47B', '#277BA2', '#8AAE92', '#B0DEDB', '#00A294'];//十个柱子颜色
                            return colorList[params.dataIndex];
                        }
                    },
                    emphasis: {
                        label: {
                            show: false,
                            textStyle: {
                                color: 'orange',
                                fontWeight: 'bold'
                            }
                        }
                    }
                }
            }
        ],
        animationDelay: function (idx) {
            // 越往后的数据延迟越大
            return idx * 300;
        }
    };

    downhistogramChart.setOption(downhistogramChartOption);




    //4.月度统计--融资月度统计
    //柱状图x轴上的数组数据拼接
    /*document.getElementById('event_monthly_TotalDate').innerHTML = monthly_TotalDate;*/

  //4.月度统计--融资月度统计
    //柱状图x轴上的数组数据拼接
    var histogram_month_data_x_key ='';
    var histogram_month_data_y_value ='';
    var histogram_month_data_z_value ='';
    if($.isEmptyObject(event_finance_histogram_month_data)!=true){
    //柱状图x轴上的数组数据拼接
    	histogram_month_data_x_key = event_finance_histogram_month_data.x_key.split(',');//x轴数据
        histogram_month_data_y_value = event_finance_histogram_month_data.y_value.split(',');//y轴数据
        histogram_month_data_z_value = event_finance_histogram_month_data.z_value.split(',');//为了便于悬浮展示需求数量增加一个z轴数据(只是数据项)
      //平台共发布融资需求 融资共发布融资总额的获取赋值
        document.getElementById('finance_event_month_totle_histogram_need').innerHTML = event_finance_histogram_month_data.pushCount;
        document.getElementById('finance_event_month_totle_histogram_amount').innerHTML = event_finance_histogram_month_data.finacMoney;
	   
    }else{
    	document.getElementById('finance_event_month_totle_histogram_need').innerHTML = '0';
        document.getElementById('finance_event_month_totle_histogram_amount').innerHTML = '0';
    }
    
    
    var histogramMonthChart = echarts.init(document.getElementById('finance_event_month_totle_histogram_id'));
    histogramonthChartOption = {
        color: ['#3398DB'],
        //显示图例
        legend: {
            // 显示策略，可选为：true（显示） | false（隐藏）
            show: true,
            // 布局方式，默认为水平布局，可选为：'horizontal' | 'vertical'
            orient: 'horizontal',
            // 水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
            x: 'center',
            // 垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
            y: '480',
            // 图例背景颜色，默认透明
            // backgroundColor: '#ffffff',
            // 图例边框颜色
            borderColor: '#ECF4FF',
            // 图例边框线宽，单位px，默认为0（无边框）
            borderWidth: 2,
            // 图例边框圆角
            borderRadius: 0,
            // 图例内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距，同css
            padding: [0, 0, 0, 0],
            // 各个item之间的间隔，单位px，默认为10，横向布局时为水平间隔，纵向布局时为纵向间隔
            itemGap: 20,
            // 图例图形宽度
            itemWidth: 15,
            // 图例图形高度
            itemHeight: 12,
            // 默认只设定了图例文字颜色,更个性化的是，要指定文字颜色跟随图例,可设color为'auto'
            textStyle: {
                // 颜色
                color: '#000',
                // 水平对齐方式，可选为：'left' | 'right' | 'center'
                align: 'left',
                // 垂直对齐方式，可选为：'top' | 'bottom' | 'middle'
                baseline: 'bottom',
                // 字体系列
                fontFamily: 'Arial, Microsoft YaHei, sans-serif',
                // 字号 ，单位px
                fontSize: 12,
                // 样式，可选为：'normal' | 'italic' | 'oblique'
                fontStyle: 'normal',
                // 粗细，可选为：'normal' | 'bold' | 'bolder' | 'lighter' | 100 | 200 |... | 900
                fontWeight: 'normal'
            },
            // 选择模式，默认开启图例开关，可选single，multiple
            selectedMode: "multiple",
            // 图例内容数组
            data: [{name: '投融事件(项)', icon: 'image://runningresult/images/need_icon.jpg'}, {name: '投资金额(M)'}]
        },
        tooltip: {
            trigger: 'item',
            // trigger: 'axis',
            show: true,
            showDelay: 0,
            hideDelay: 0,
            transitionDuration: 0,
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow' |'cross'
            },
            backgroundColor: '#ffffff',
            borderColor: '#987ECA',
            borderRadius: 8,
            borderWidth: 1,
            padding: 15,
            textStyle: {
                color: '#333333',
            },
            formatter: function (params, ticket, callback) {
                var tooltip_info;
                //根据params.dataIndex下标取出对应的需求数拼接提示信息(z_value[params.dataIndex])
                if (params.seriesType == 'bar') {
                    tooltip_info = "统计时间：" +params.name +  '<br/>' + "投融事件：" + params.value + "项" + '<br/>' + "投资金额：" + histogram_month_data_y_value[params.dataIndex] + "M";
                } else {
                    tooltip_info = "统计时间：" +params.name + '<br/>' + "投融事件：" + histogram_month_data_z_value[params.dataIndex] + "项" + '<br/>' + "投资金额：" + params.value + "M";

                }
                return tooltip_info;
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '18%',
            containLabel: true,
        },
        dataZoom: {
            show: false,
            realtime: true,
            start: 0,
            end: 100
        },
        xAxis: [
            {
                //name: "行业",
                type: 'category',
                axisLabel: {
                    show: true,
                    // rotate: 45,
                    interval: 0,
                },
                axisLine: {
                    show: false
                },//坐标值不显示
                axisTick: {
                    show: false,
                    alignWithLabel: true,
                },//坐标值无关键点
                data: histogram_month_data_x_key,
                // data: ['互联网', '生物技术/医疗健康', 'IT', '信息技术', '保险产业', '卫生服务', '政治资讯'],
            }
        ],
        yAxis: [
            {
                name: '投融事件(项)',
                type: 'value',
                nameTextStyle: {
                    color: "#33425B",
                },//坐标轴名称的文字样式
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#393E46',
                    },
                },//坐标值显示

                axisTick: {
                    show: false,
                },//坐标值无关键点
                splitLine: {
                    show: true,
                    interval: 'auto',
                    lineStyle: {
                        color: ['#ddd'],
                        width: 1,
                        type: 'dashed',//'solid' 'dashed' 'dotted'
                    },
                },
            },
            {
                name: '投资金额(M)',
                type: 'value',
                nameTextStyle: {
                    color: "#33425B",
                },//坐标轴名称的文字样式
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#393E46',
                    },
                },//坐标值显示
                axisTick: {
                    show: false
                },//坐标值无关键点
                splitLine: {
                    show: false,//折线图并不显示分割线
                    interval: 'auto',
                    lineStyle: {
                        color: ['#ddd'],
                        width: 1,
                        type: 'dashed',//'solid' 'dashed' 'dotted'
                    },
                },
            }
        ],
        series: [
            {
                name: '投融事件(项)',
                type: 'bar',
                barWidth: '30%',
                data: histogram_month_data_z_value,
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                itemStyle: {
                    //通常情况下：
                    normal: {
                        //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                        color: function (params) {
                            var colorList = ['#0080BD', '#987ECA', '#FC954E', '#009E92', '#FEB134', '#00E47B', '#277BA2', '#8AAE92', '#B0DEDB', '#00A294'];//十个柱子颜色
                            return colorList[params.dataIndex];
                        }
                    },
                    //鼠标悬停时：
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
            },
            {
                name: '投资金额(M)',
                type: 'line',
                yAxisIndex: 1,
                data: histogram_month_data_y_value,
                label: {
                    normal: {
                        show: false,
                        position: 'top'
                    }
                },
                //设置折线点的颜色、折线的颜色
                itemStyle: {
                    normal: {
                        //折线点的颜色
                        color: '#005792',
                        //折线的颜色
                        lineStyle: {
                            color: '#109DD9'
                        }
                    }
                },
            }
        ],
        animationDelay: function (idx) {
            // 越往后的数据延迟越大
            return idx * 300;
        }
    };
    histogramMonthChart.setOption(histogramonthChartOption);


};
