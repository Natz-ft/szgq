var querydata;

$(function () {
    init("", "");
    initDD();
    var data = {};
    $("div.query-condition dl dd a").click(function () {
        var type = $(this).parent().attr("type");
        data[type] = $(this).attr("name");
        init(data);
        if (!$(this).hasClass("current")) {
            $(this).addClass("current").siblings("a").removeClass("current");
        }
    });
});
//初始化/查询
function init(data) {
    $.ajax({
        type: "post",//请求方式
        url: "/SRRPBusinesWeb/portal/investorQuery/initInfo",//url地址
        cache: false,//清楚缓存
        async: false,//同步
        dataType: "json",//传递查询条件格式json
        data: {queryCondition: JSON.stringify(data)},//查询条件(序列化对象)
        success: function (data) {
            querydata = data;
            getPage();
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
                //首次不执行
                if (!first) {
                    //do something
                }
                //模拟渲染
                document.getElementById('formid').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<div class="agencydatadiv">');
                        arr.push('<ul>');
                        arr.push('<li>');
                        arr.push('<img class="agency-tablestyeleimg" src="' + item.logo + '"/>');//修改投资机构logo地址属性
                        arr.push('<div class="agency-tablestyledatadd1">');
                        arr.push('<a href="javascript:viewDetail(\''+item.investorId+'\')">' + item.investorName + '</a>');//修改投资机构名称属性
                        arr.push('</div>');
                        arr.push('<div class="agency-tablestyledatadd2">');
                        arr.push(item.description);//修改投资机构简介属性
                        arr.push('</div>');
                        arr.push('<div class="agency-tablestyledatadetaildiv">');
                        arr.push('<a href="javascript:viewDetail(\''+item.investorId+'\');" class="agency-tablestyledatadetail">详情资料</a></li>');
                        arr.push('</div>');
//                        arr.push('<a href="javascript:viewDetail('+item.investorId+');" class="agency-tablestyledatadetail">详情资料</a></li>');
                        arr.push('</ul>');
                        arr.push('</div>');
                        arr.push('<hr/>');
                    });
                    return arr.join('');
                }();
            }
        });
    });
}
//初始化DD
function initDD() {
	var ddHtml='<a href="javascript:void(0);" class="current" name="0">全部</a>';
    var ddValue;
    $.ajax({
        type: "post",
        url: "/SRRPBusinesWeb/portal/investorQuery/initDD",//url地址
		async: false,//使用同步的方式,true为异步方式
        success: function (data) {
        	data = JSON.parse(data);
        	//资本实力
        	ddValue = data.capitalPower;
        	ddValue =  JSON.parse(ddValue);
			for (var i = 0; i < ddValue.length; i++) {
				var _json = ddValue[i];
				ddHtml += '<a href="javascript:void(0);" name="'+_json.dicCode+'">'+_json.dicName+'</a>';
			}
			$("#capitalPower").append(ddHtml);
			//机构类型
			ddHtml = '<a href="javascript:void(0);" class="current" name="0">全部</a>';
			ddValue = data.orgType;
        	ddValue =  JSON.parse(ddValue);
			for (var i = 0; i < ddValue.length; i++) {
				var _json = ddValue[i];
				ddHtml += '<a href="javascript:void(0);" name="'+_json.dicCode+'">'+_json.dicName+'</a>';
			}
			$("#orgType").append(ddHtml);
			//成立时间
			ddHtml = '<a href="javascript:void(0);" class="current" name="0">全部</a>';
			ddValue = data.setTime;
			ddValue =  JSON.parse(ddValue);
			for (var i = 0; i < ddValue.length; i++) {
				var _json = ddValue[i];
				ddHtml += '<a href="javascript:void(0);" name="'+_json.dicCode+'">'+_json.dicName+'</a>';
			}
			$("#setTime").append(ddHtml);
        }
    });
}
//明细
function viewDetail(investorId) {
	form.action = "/SRRPBusinesWeb/portal/investorQuery/viewDetail.do?investorId="+investorId;
	form.submit();
}
