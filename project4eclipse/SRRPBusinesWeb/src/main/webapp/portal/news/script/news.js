/*行业动态与政策指南的点击联动*/
$(document).on("click", "#click_relNews_dynamic", function () {
    $("#obj_relNews_dynamic").css("display", "inline");
    $("#obj_policy_guide,#obj_industry_dynamic,#obj_dy_faq").css("display", "none");

    $("#click_relNews_dynamic").attr("class", "relNews-dynamic-click");//点击后重新设置Id为click_industry_dynamic的class属性。
    $("#click_policy_guide").attr("class", "policy-guide");
    $("#click_industry_dynamic").attr("class", "industry-dynamic");
    $("#click_dy_faq").attr("class", "dy-faq");
    return false;
});
$(document).on("click", "#click_industry_dynamic", function () {
    $("#obj_industry_dynamic").css("display", "inline");
    $("#obj_policy_guide,#obj_relNews_dynamic,#obj_dy_faq").css("display", "none");

    $("#click_industry_dynamic").attr("class", "industry-dynamic-click");//点击后重新设置Id为click_industry_dynamic的class属性。
    $("#click_policy_guide").attr("class", "policy-guide");
    $("#click_relNews_dynamic").attr("class", "relNews-dynamic");
    $("#click_dy_faq").attr("class", "dy-faq");
    return false;
});
$(document).on("click", "#click_policy_guide", function () {
    $("#obj_policy_guide").css("display", "inline");
    $("#obj_industry_dynamic,#obj_relNews_dynamic,#obj_dy_faq").css("display", "none");

    $("#click_policy_guide").attr("class", "policy-guide-click");//点击后重新设置Id为policy-guide-click的class属性。
    $("#click_industry_dynamic").attr("class", "industry-dynamic");
    $("#click_relNews_dynamic").attr("class", "relNews-dynamic");
    $("#click_dy_faq").attr("class", "dy-faq");
    return false;
});
$(document).on("click", "#click_dy_faq", function () {
    $("#obj_dy_faq").css("display", "inline");
    $("#obj_industry_dynamic,#obj_relNews_dynamic,#obj_policy_guide").css("display", "none");

    $("#click_dy_faq").attr("class", "dy-faq-click");
    $("#click_policy_guide").attr("class", "policy-guide");
    $("#click_industry_dynamic").attr("class", "industry-dynamic");
    $("#click_relNews_dynamic").attr("class", "relNews-dynamic");
    return false;
});


//分页组件
function getPolicyPage() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = policydata.data;
        laypage.render({
            elem: 'policy-page-ui',
            count: policydata.count,
            theme: '#FF5722',
            limit:5,
            jump: function (obj, first) {
                //首次不执行
                if (!first) {
                }
                //模拟渲染
                document.getElementById('policyformid').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<div class="news-left-tr">');
                        arr.push('<ul>');
                        arr.push('<li>');
                        arr.push('<a class="every-news-content" href="detailnews'+item.infoId+'.html">');
                        arr.push(item.title + '</br>');//修改政策指南标题属性
                        arr.push('<span class="every-news-explain">' + item.content + '</span>');//修改政策指南部分明细属性
                        arr.push('</a>');
                        arr.push('</li>');
                        arr.push('</ul>');
                        arr.push('</div>');
                        arr.push('<div class="news-right-tr">' + item.time + '</div>');//修改时间属性
                        arr.push('<div class="every-divide-line"></div>');
                    });
                    return arr.join('');
                }();
            }
        });
    });
}

//分页组件
function getNewsPage() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = newsdata.data;
        laypage.render({
            elem: 'news-page-ui',
            count: newsdata.count,
            limit:5,
            theme: '#FF5722',
            jump: function (obj, first) {
                //首次不执行
                if (!first) {
                }
                //模拟渲染
                document.getElementById('newsformid').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<div class="news-left-tr">');
                        arr.push('<ul>');
                        arr.push('<li>');
                        arr.push('<a class="every-news-content" href="detailnews'+item.infoId+'.html">');
                        arr.push(item.title + '</br>');//修改行业动态标题属性
                        arr.push('<span class="every-news-explain">' + item.content + '</span>');//修改行业动态部分明细属性
                        arr.push('</a>');
                        arr.push('</li>');
                        arr.push('</ul>');
                        arr.push('</div>');
                        arr.push('<div class="news-right-tr">' + item.time + '</div>');//修改时间属性
                        arr.push('<div class="every-divide-line"></div>');
                    });
                    return arr.join('');
                }();
            }
        });
    });
}

//分页组件
function getRelNewsPage() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = relNewsdata.data;
        laypage.render({
            elem: 'relNews-page-ui',
            count: relNewsdata.count,
            limit:5,
            theme: '#FF5722',
            jump: function (obj, first) {
                //首次不执行
                if (!first) {
                }
                //模拟渲染
                document.getElementById('relNewsformid').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<div class="news-left-tr">');
                        arr.push('<ul>');
                        arr.push('<li>');
                        arr.push('<a class="every-news-content" href="detailnewsInfo'+item.newsId+'.html">');
                        arr.push(item.newsTile + '</br>');//修改行业动态标题属性
                        arr.push('<span class="every-news-explain">' + item.newsContents + '</span>');//修改行业动态部分明细属性
                        arr.push('</a>');
                        arr.push('</li>');
                        arr.push('</ul>');
                        arr.push('</div>');
                        arr.push('<div class="news-right-tr">' + item.newsDate + '</div>');//修改时间属性
                        arr.push('<div class="every-divide-line"></div>');
                    });
                    return arr.join('');
                }();
            }
        });
    });
}

//分页组件
function getFaqPage() {
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage, layer = layui.layer;
        var listdata = faqData.data;
        laypage.render({
            elem: 'faq-page-ui',
            count: faqData.count,
            limit:5,
            theme: '#FF5722',
            jump: function (obj, first) {
                //首次不执行
                if (!first) {
                }
                //模拟渲染
                document.getElementById('faqformid').innerHTML = function () {
                    var arr = [], thisData = listdata.concat().splice(obj.curr * obj.limit - obj.limit, obj.limit);
                    layui.each(thisData, function (index, item) {
                        arr.push('<div class="news-left-tr">');
                        arr.push('<ul>');
                        arr.push('<li>');
                        arr.push('<a class="every-news-content" href="inform.html?faqid='+item.id+'" >');
                        arr.push(item.title + '</br>');
                        arr.push('<span class="every-news-explain">' + item.problem + '</span>');
                        arr.push('</a>');
                        arr.push('</li>');
                        arr.push('</ul>');
                        arr.push('</div>');
                        arr.push('<div class="news-right-tr">' + item.faqDate + '</div>');
                        arr.push('<div class="every-divide-line"></div>');
                    });
                    return arr.join('');
                }();
            }
        });
    });
}

var policydata;
var newsdata;
var relNewsdata;
var faqData;

function init(data) {
    $.ajax({
        type: "get",//请求方式
        url: "policy.json",//政策指南url地址
        cache: false,//清楚缓存
        async: false,//同步
        dataType: "json",//传递查询条件格式json
        success: function (data) {
            policydata = data;
            getPolicyPage();
        }
    });
    $.ajax({
        type: "get",//请求方式
        url: "news.json",//行业动态url地址
        cache: false,//清楚缓存
        async: false,//同步
        dataType: "json",//传递查询条件格式json
        success: function (data) {
            newsdata = data;
            getNewsPage();
        }
    });
    $.ajax({
        type: "get",//请求方式
        url: "relNews.json",//行业动态url地址
        cache: false,//清楚缓存
        async: false,//同步
        dataType: "json",//传递查询条件格式json
        success: function (data) {
            relNewsdata = data;
            getRelNewsPage();
        }
    });
    $.ajax({
        type: "get",//请求方式
        url: "faq.json",//行业动态url地址
        cache: false,//清楚缓存
        async: false,//同步
        dataType: "json",//传递查询条件格式json
        success: function (data) {
            faqData = data;
            getFaqPage();
        }
    });
}

$(function () {
    init("", "");
});


