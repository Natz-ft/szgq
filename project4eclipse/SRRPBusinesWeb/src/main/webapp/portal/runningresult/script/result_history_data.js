//历史总量数据
function getHistoryCountData() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = queryhistorydata.staticResult;
        laypage.render({
            jump: function (obj, first) {
                //模拟渲染
                document.getElementById('history_count_form_id').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<table><tbody>');
                        arr.push('<tr>');
                        arr.push('<td class="rank-menu-history-first-data">' + item.demondCount + 'M</td>');
                        arr.push('<td class="rank-menu-history-first-data">' + item.finacCount + 'M</td>');
                        arr.push('<td class="rank-menu-history-first-data">' + item.investorCount + '</td>');
                        arr.push('<td class="rank-menu-history-first-data">' + item.enterpriseCount + '</td>');
                        arr.push('</tr>');
                        arr.push('</table></tbody>');
                    });
                    return arr.join('');
                }();
            }
        });
    });
}

//企业榜单
function getHistoryCompanyData() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = queryhistorydata.companyRank;//修改
        laypage.render({
            jump: function (obj, first) {
                //模拟渲染
                document.getElementById('history_company_form_id').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<table><tbody>');
                        arr.push('<tr>');
                        arr.push('<td class="rank-menu-company-detail-data-rank">' + item.ranking + '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-company">' + item.name + '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-amount">' + item.finanMoney  +'M'+ '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-num">' + item.upListNum + '</td>');
                        arr.push('</tr>');
                        arr.push('</table></tbody>');
                    });
                    return arr.join('');
                }();
            }
        });
    });
}

//机构榜单
function getHistoryOrgData() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = queryhistorydata.investorRank;//修改
        laypage.render({
            jump: function (obj, first) {
                //模拟渲染
                document.getElementById('history_org_form_id').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<table><tbody>');
                        arr.push('<tr>');
                        arr.push('<td class="rank-menu-org-detail-data-rank">' + item.raking + '</td>');
                        arr.push('<td class="rank-menu-org-detail-data-company">' + item.name + '</td>');
                        arr.push('<td class="rank-menu-org-detail-data-amount">' + item.amount  +'M'+ '</td>');
                        arr.push('<td class="rank-menu-org-detail-data-num">' + item.investNum + '</td>');
                        arr.push('<td class="rank-menu-org-detail-data-need">' + item.solveorgNum + '</td>');
                        arr.push('</tr>');
                        arr.push('</table></tbody>');
                    });
                    return arr.join('');
                }();
            }
        });
    });
}

//行业榜单
function getHistoryIndustryData() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = queryhistorydata.industryRank;//修改
        laypage.render({
            jump: function (obj, first) {
                //模拟渲染
                document.getElementById('history_industry_form_id').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                       
						arr.push('<table><tbody>');
                        arr.push('<tr>');
                        arr.push('<td class="rank-menu-company-detail-data-rank">' + item.rankingnum + '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-company">' + item.trades + '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-amount">' + item.finanMoney  +'M'+ '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-num">' + item.demandNum + '</td>');
                        arr.push('</tr>');
                        arr.push('</table></tbody>');
                    });
                    return arr.join('');
                }();

            }
        });
    });
}

//区域榜单
function getHistoryAreaData() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = queryhistorydata.areaRank;//修改
        laypage.render({
            jump: function (obj, first) {
                //模拟渲染
                document.getElementById('history_area_form_id').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        
						arr.push('<table><tbody>');
                        arr.push('<tr>');
                        arr.push('<td class="rank-menu-company-detail-data-rank">' + item.rankingnum + '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-company">' + item.adminarea + '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-amount">' + item.finanMoney  +'M'+ '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-num">' + item.demandNum + '</td>');
                        arr.push('</tr>');
                        arr.push('</table></tbody>');
                        
                    });
                    return arr.join('');
                }();
               
            }
        });
    });
}

var queryhistorydata;//获取查询数据
var query_date;//查询时间
function historyDataInit(query_date) {
    $.ajax({
        type: "post",//请求方式
        url: "/SRRPBusinesWeb/result/initHistoryRank",//url地址
        cache: false,//清楚缓存
        async: false,//同步
        dataType: "json",//传递查询条件格式json
        data: {"queryDate": query_date},//查询条件(序列化对象)
        success: function (data) {
            queryhistorydata = data;
            getHistoryCountData();
            getHistoryCompanyData();
            getHistoryOrgData();
            getHistoryAreaData();
            getHistoryIndustryData();
        }
    });
}

//$(function () {
//	 var date=new Date;
//	 var year=date.getFullYear(); 
//	 var month=date.getMonth();
//	 month =(month<10 ? "0"+month:month); 
//	 var mydate = (year.toString()+"-"+month.toString());
//	$("#history_query_id").val(mydate);
//    historyDataInit("");//默认时间查询数据
//});

//点选查询时间查询
function history_query_fun() {
    query_date = $("#history_query_id").val();
//    alert(query_date);
    historyDataInit(query_date);
}
