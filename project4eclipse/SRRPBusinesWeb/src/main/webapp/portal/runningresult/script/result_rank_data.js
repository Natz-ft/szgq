//企业榜单
var company_date;
function getCompanyData() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = queryrankdata.companyRank;
        laypage.render({
            elem: 'common-page-ui',
           
            theme: '#FF5722',
            jump: function (obj, first) {
                if (!first) {
                    //do something
                }
                //模拟渲染
                document.getElementById('company_form_id').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<table><tbody>');
                        arr.push('<tr>');
                        arr.push('<td class="rank-menu-company-detail-data-rank">' + item.ranking + '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-company">' + item.name + '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-amount">'+item.finanMoney +'M'+ '</td>');
                        arr.push('<td class="rank-menu-company-detail-data-num">' + item.upListNum + '</td>');
                        arr.push('</tr>');
                        arr.push('</tbody></table>');
                       if(item.ranking=='1'){
                    	company_date=item.countDate;
                        document.getElementById('rank_company_date').innerHTML = company_date;
                       }
                        
                    });
                    if(thisData==null || thisData=="" || !$.trim(thisData) ){
                    	var date=new Date;
                    	 var year=date.getFullYear(); 
                    	 var month=date.getMonth();
                    	 month =(month<10 ? "0"+month:month); 
                    	 var mydate = (year.toString()+"-"+month.toString());
                        document.getElementById('rank_company_date').innerHTML = mydate;
                    }else{
                    	
                    }
                    return arr.join('');
                }();
            }
        });
    });
}

//机构榜单
var org_date;
function getOrgData() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = queryrankdata.investorRank;
        laypage.render({
            elem: 'common-page-ui',
            
            theme: '#FF5722',
            jump: function (obj, first) {
                if (!first) {
                    //do something
                }
                //模拟渲染
                document.getElementById('org_form_id').innerHTML = function () {
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
                        arr.push('</tbody></table>');
                      if(item.raking=='1'){
                    	org_date=item.countDate;
                        document.getElementById('rank_org_date').innerHTML = org_date;
                    }
                    });
                    if(thisData==null || thisData=="" || !$.trim(thisData) ){
                    	var date=new Date;
                    	 var year=date.getFullYear(); 
                    	 var month=date.getMonth();
                    	 month =(month<10 ? "0"+month:month); 
                    	 var mydate = (year.toString()+"-"+month.toString());
                        document.getElementById('rank_org_date').innerHTML = mydate;
                    }
                    return arr.join('');
                }();
            }
        });
    });
}

//区域榜单
var area_date;
function getAreaData() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = queryrankdata.areaRank;
        laypage.render({
            elem: 'common-page-ui',
            
            theme: '#FF5722',
            jump: function (obj, first) {
                //首次不执行
                if (!first) {
                    //do something
                }
                //模拟渲染
                document.getElementById('area_form_id').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<table><tbody>');
                        arr.push('<tr>');
                        arr.push('<td class="rank-menu-areaindustry-detail-data-rank">' + item.rankingnum + '</td>');
                        arr.push('<td class="rank-menu-areaindustry-detail-data-other">' + item.adminarea + '</td>');
                        arr.push('<td class="rank-menu-areaindustry-detail-eventdata-other">' + item.finanMoney  +'M'+ '</td>');
                        arr.push('<td class="rank-menu-areaindustry-detail-eventdata-other">' + item.demandNum  + '</td>');
						arr.push('</tr>');
                        arr.push('</table></tbody>');
//                        
                        if(item.rankingnum=='1'){
                        	area_date=item.countDate;
                            document.getElementById('rank_area_date').innerHTML = area_date;
                        }
                    });
                    if(thisData==null || thisData=="" || !$.trim(thisData) ){
                    	var date=new Date;
                    	 var year=date.getFullYear(); 
                    	 var month=date.getMonth();
                    	 month =(month<10 ? "0"+month:month); 
                    	 var mydate = (year.toString()+"-"+month.toString());
                        document.getElementById('rank_area_date').innerHTML = mydate;
                    }
                    return arr.join('');
                }();
            }
        });
    });
}

//行业榜单
var industry_date;
function getIndustryData() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = queryrankdata.industryRank;
        laypage.render({
            elem: 'common-page-ui',
            
            theme: '#FF5722',
            jump: function (obj, first) {
                //首次不执行
                if (!first) {
                    //do something
                }
                //模拟渲染
                document.getElementById('industry_form_id').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<table><tbody>');
                        arr.push('<tr>');
                        arr.push('<td class="rank-menu-areaindustry-detail-data-rank">' + item.rankingnum + '</td>');
                        arr.push('<td class="rank-menu-areaindustry-detail-data-other">' + item.trades + '</td>');
                        arr.push('<td class="rank-menu-areaindustry-detail-eventdata-other">' + item.finanMoney  +'M'+'</td>');
						arr.push('<td class="rank-menu-areaindustry-detail-eventdata-other">' + item.demandNum +'</td>');
                        arr.push('</tr>');
                        arr.push('</table></tbody>');
                        if(item.rankingnum=='1'){
                        	industry_date=item.countDate;
                            document.getElementById('rank_industry_date').innerHTML = industry_date;
                        }
                    });
                    if(thisData==null || thisData=="" || !$.trim(thisData) ){
                    	var date=new Date;
                    	 var year=date.getFullYear(); 
                    	 var month=date.getMonth();
                    	 month =(month<10 ? "0"+month:month); 
                    	 var mydate = (year.toString()+"-"+month.toString());
                        document.getElementById('rank_industry_date').innerHTML = mydate;
                    }
                    return arr.join('');
                }();
            }
        });
    });
}


var queryrankdata;

function rankDataInit() {
    $.ajax({
        type: "get",//请求方式
        url: "resultrank.json",//url地址
        cache: false,//清楚缓存
        async: false,//同步
        success: function (data) {
            queryrankdata = data;
            getCompanyData();
            getOrgData();
            getAreaData();
            getIndustryData();
        }
    });
}
//$(function () {
//    rankDataInit();
//});
