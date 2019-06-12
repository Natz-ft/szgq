var querydata;

function init(data) {
    $.ajax({
        type: "post",//请求方式
        url: "/SRRPBusinesWeb/portal/demandQuery/initInfo",//修改url地址
        cache: false,//清楚缓存
        async: false,//同步
        dataType: "json",//传递查询条件格式json
        data: {"queryCondition": JSON.stringify(data)},//查询条件(序列化对象)
        success: function (data) {
            querydata = data;
            getPage();
        }
    });
}

$(function () {
    init("", "");
    initDD();
    var data = {};
    $("div.query-condition dl dd a").click(function () {
        var key = $(this).parent().attr("type");
        data[key] = $(this).attr("name");
        init(data);
        if (!$(this).hasClass("current")) {
            $(this).addClass("current").siblings("a").removeClass("current");
        }
    });
});
function initDD() {
	var ddHtml='<a href="javascript:void(0);" name="" class="current">全部</a>';
    $.ajax({
        type: "post",
        url: "/SRRPBusinesWeb/portal/demandQuery/initDD",//url地址
		data : {"data" : JSON.stringify('')},// 查询条件
		async: false,//使用同步的方式,true为异步方式
        success: function (data) {
        	data = JSON.parse(data);
        	/*var dd_area = data.area;
        	dd_area = JSON.parse(dd_area);
			for (var i = 0; i < dd_area.length; i++) {
				var _json = dd_area[i];
				ddHtml += '<a href="javascript:void(0);" name="'+_json.dicCode+'">'+_json.dicName+'</a>';
			}
			$("#area").append(ddHtml);
			ddHtml='<a href="javascript:void(0);" name="" class="current">全部</a>';*/
        	var dd_industry = data.industry;
        	dd_industry = JSON.parse(dd_industry);
			for (var i = 0; i < dd_industry.length; i++) {
				var _json = dd_industry[i];
				ddHtml += '<a href="javascript:void(0);" name="'+_json.dicCode+'">'+_json.dicName+'</a>';
			}
			$("#industry").append(ddHtml);
			ddHtml='<a href="javascript:void(0);" name="" class="current">全部</a>';
        	var dd_finacingTurn = data.finacingTurn;
        	dd_finacingTurn = JSON.parse(dd_finacingTurn);
			for (var i = 0; i < dd_finacingTurn.length; i++) {
				var _json = dd_finacingTurn[i];
				ddHtml += '<a href="javascript:void(0);" name="'+_json.dicCode+'">'+_json.dicName+'</a>';
			}
			$("#finacingTurn").append(ddHtml);
			ddHtml='<a href="javascript:void(0);" name="" class="current">全部</a>';
        	/*var dd_enterprisePeriod = data.enterprisePeriod;
        	dd_enterprisePeriod = JSON.parse(dd_enterprisePeriod);
			for (var i = 0; i < dd_enterprisePeriod.length; i++) {
				var _json = dd_enterprisePeriod[i];
				ddHtml += '<a href="javascript:void(0);" name="'+_json.dicCode+'">'+_json.dicName+'</a>';
			}
			$("#enterprisePeriod").append(ddHtml);*/
        }
    });
}
function viewDetail(infoId) {
	
	form.action = "/SRRPBusinesWeb/portal/demandQuery/viewDetail.do?infoId="+infoId;
	form.submit();
}
function financeTalk() {
	$.ajax({
        type: "post",
        url: "/SRRPBusinesWeb/portal/demandQuery/financeTalk",//url地址
        data: {"name": ""},// 查询条件
        async: false,//使用同步的方式,true为异步方式
        success: function (data) {
            data = JSON.parse(data);
           if(data.code=='03'){
        	   layer.alert('当前登陆用户非机构用户，不能投资项目！', {icon: 5});
        	   return false;
        	   
           }else{
        	   form.action = "/creditplatformweb/success?type=financeTalk";
        	   form.submit();
           }
        }
    });
	
	
}
//分页组件
function getPage() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = querydata.data;
        document.getElementById('record_totle').innerHTML = querydata.count;//显示查询条件下的共计条数
        laypage.render({
            elem: 'common-page-ui',
            count: querydata.count,
            theme: '#FF5722',
            jump: function (obj, first) {
                //obj包含了当前分页的所有参数，比如：
                // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                //console.log(obj.limit); //得到每页显示的条数
                //首次不执行
                if (!first) {
                    //do something
                }
                //模拟渲染
                document.getElementById('formid').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<ul>');
                        arr.push('<li class="pro-list">');
                        arr.push('<div class="item_cont">');
                        arr.push('<div class="item_info">');
                        arr.push('<a  href="javascript:viewDetail(\''+item.infoId+'\');"  class="title" id="aa">' + item.projectNameShow + '</a>');//修改项目名称属性
                        arr.push('</div>');
                        arr.push('<div class="item_info">');
                        arr.push('<h4 class="titledate">' + item.operDate + '</h4>');//修改项目时间属性
                        arr.push('<div class="item_meta">');
                        arr.push('<ul class="meta_sep reqs">');
                        arr.push('<li class="money">融资金额：');
                        arr.push('<span class="moneynum">' +item.financingPurposesShow+'</span>');//修改投资金额属性
                        arr.push('</li>');
                        arr.push('<li class="money">融资地区：');
                        arr.push('<span>' + item.areaName + '</span>');//修改投资地区属性
                        arr.push('</li>');
                        arr.push('<li class="money">融资轮次：');
                        arr.push('<span>' + item.financingModeName + '</span>');//修改项目融资方式属性
                        arr.push('</li>');
                        arr.push('</ul>');
                        arr.push('<ul class="meta_sep specs">');
                        arr.push('<li class="rait">所属行业：');
                        arr.push('<span>' + item.industryName + '</span>');//修改所属行业属性
                        arr.push('</li>');
                        arr.push('</ul>');
                        arr.push('<ul class="meta_sep specs">');
                        arr.push('<li class="rait">');
                        arr.push('<span></span>');//修改联系人属性
                        arr.push('</li>');
                        arr.push('</ul>');
                        arr.push('<ul class="meta_sep oper">');
                        arr.push('<a href="javascript:financeTalk()" class="finance-foncus">投资项目</a>');//修改详情页面跳转属性
                        /*arr.push('<a href="/SRRPBusinesWeb/portal/demandQuery/financeTalk" class="finance-talk">项目约谈</a>');*/
                        arr.push(' </ul>');
                        arr.push('</div>');
                        arr.push(' </div>');
                        arr.push('</div>');
                        arr.push('</li>');
                        arr.push('</ul>');
                        arr.push('<hr/>');
                    });
                    return arr.join('');
                }();
            }
        });
    });
}
