//分页组件
function getPage() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = querydata.data;
        document.getElementById('record_totle').innerHTML = querydata.count;
        laypage.render({
            elem: 'common-page-ui',
            count: querydata.count,
            theme: '#FF5722',
            jump: function (obj, first) {
                //obj包含了当前分页的所有参数，比如：
                //console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                //console.log(obj.limit); //得到每页显示的条数
                //首次不执行
                if (!first) {
                    //do something
                }
                //模拟渲染
                document.getElementById('formid').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<div>');
                        arr.push('<ul>');
                        arr.push('<li>');
                        //修改item.对应实体类定义开始
                        arr.push('<a class="melte-tablestyledata">' + item.projectName + '</a>');
//                        arr.push('<a class="melte-tablestyledata-name">' + item.investorName + '</a>');
//                        arr.push('<a class="melte-tablestyledata-name">' + item.enterpriseName + '</a>');
                        arr.push('<a class="melte-tablestyledataother">' + item.industryName + '</a>');
                        arr.push('<a class="melte-tablestyledataother">' + item.finacingTurnName + '</a>');
                        arr.push('<a class="melte-tablestyledataother">' + item.amount*100 +'万元'+ '</a>');
                        arr.push('<a class="melte-tablestyledataother">' + item.operDate + '</a>');
                        //修改item.对应实体类定义结束
                        arr.push('<a href="javascript:viewDetail(\''+item.eventId+'\');" class="melte-tablestyledatadetail">详情</a>');
                        arr.push('</li>');
                        arr.push('</ul>');
                        arr.push('</div>');
                        arr.push('<div class="dashedstyle"></div>');
                    });
                    return arr.join('');
                }();
            }
        });
    });
}

var querydata;
var condition_type;
var condition_name;

function init(data) {
    $.ajax({
        type: "post",//请求方式
        url: "/SRRPBusinesWeb/portal/eventQuery/finacEvent",//修改url地址
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
function viewDetail(eventId) {
	form.action = "/SRRPBusinesWeb/portal/eventQuery/viewDetail.do?eventId="+eventId;
	form.submit();
}
$(function () {
    init("");
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
        url: "/SRRPBusinesWeb/portal/eventQuery/initDD",//url地址
		data : {"data" : JSON.stringify('')},// 查询条件
		async: false,//使用同步的方式,true为异步方式
        success: function (data) {
        	data = JSON.parse(data);
        	//行业
			ddHtml='<a href="javascript:void(0);" name="" class="current">全部</a>';
        	var dd_industry = data.industry;
        	dd_industry = JSON.parse(dd_industry);
			for (var i = 0; i < dd_industry.length; i++) {
				var _json = dd_industry[i];
				ddHtml += '<a href="javascript:void(0);" name="'+_json.dicCode+'">'+_json.dicName+'</a>';
			}
			$("#industry").append(ddHtml);
			//轮次
			ddHtml='<a href="javascript:void(0);" name="" class="current">全部</a>';
        	var finacing_turn = data.finacing_turn;
        	finacing_turn = JSON.parse(finacing_turn);
			for (var i = 0; i < finacing_turn.length; i++) {
				var _json = finacing_turn[i];
				ddHtml += '<a href="javascript:void(0);" name="'+_json.dicCode+'">'+_json.dicName+'</a>';
			}
			$("#finacing_turn").append(ddHtml);		
        }
    });
}
