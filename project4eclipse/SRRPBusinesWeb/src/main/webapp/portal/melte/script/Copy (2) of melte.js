//分页组件
layui.use(['laypage', 'layer'], function () {
    var laypage = layui.laypage, layer = layui.layer;
    var listdata = querydata.data;
//    console.log(listdata);
    document.getElementById('record_totle').innerHTML = querydata.count;
    laypage.render({
        elem: 'common-page-ui',
        count: querydata.count,
        theme: '#FF5722',
        jump: function (obj, first) {
            //obj包含了当前分页的所有参数，比如：
            console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
            console.log(obj.limit); //得到每页显示的条数
            //首次不执行
            if (!first) {
                //do something
            }
            //模拟渲染
            document.getElementById('formid').innerHTML = function () {
            	console.log(listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit));
                var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
//                console.log(arr); 
                layui.each(thisData, function (index, item) {
                    arr.push('<div>');
                    arr.push('<ul>');
                    arr.push('<li>');
                    //修改item.对应实体类定义开始
                    arr.push('<a class="melte-tablestyledata">' + item.projectName + '</a>');
                    arr.push('<a class="melte-tablestyledata">' + item.investorName + '</a>');
                    arr.push('<a class="melte-tablestyledata">' + item.enterpriseName + '</a>');
                    arr.push('<a class="melte-tablestyledataother">' + item.finacingTurn + '</a>');
                    arr.push('<a class="melte-tablestyledataother">' + item.finacingTurn + '</a>');
                    arr.push('<a class="melte-tablestyledataother">' + item.finacingTurn + '</a>');
                    arr.push('<a class="melte-tablestyledataother">' + item.amount + '</a>');
                    arr.push('<a class="melte-tablestyledataother">' + item.operDate + '</a>');
                    //修改item.对应实体类定义结束
                    arr.push('<a href="detailinvestevent.html" class="melte-tablestyledatadetail">' + "详情" + '</a>');
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
//获取点选条件
var querydata;
var condition_type;
var condition_name;
$(function () {
    $("div.query-condition dl dd a").click(function () {
        condition_type = $(this).parent().attr("type");
        condition_name = $(this).attr("name");
        console.log("属性：" + condition_type + "----" + "值：" + condition_name);
        if (!$(this).hasClass("current")) {
            $(this).addClass("current").siblings("a").removeClass("current");
        }
    });

    $.ajax({
        type: "post",
        url: "/creditplatformweb/finacEvent",//url地址
        dataType: "json",
        data: {condition_type: condition_type, condition_name: condition_name},//查询条件
        success: function (data) {
            querydata = data;
        }
    });
});
