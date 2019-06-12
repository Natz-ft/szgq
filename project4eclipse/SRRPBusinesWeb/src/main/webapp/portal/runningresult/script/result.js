/*初始化样式*/

//第一阶点击背景色变化
function firstinit(first_title) {
    $("a[id*='title_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).removeClass("first-click-color");
        first_title.addClass("first-click-color");
    });
}

//第二阶点击背景色变化
function secondinit() {
    $("div[id*='second_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).css("display", "none");
    });
    //遍历初始化二级菜单背景色
    //平台用户二级菜单背景色
    $(".user-menu a").each(function () {
        if ($(this).hasClass("third-menu-click")) {
            $(this).removeClass("third-menu-click");
        }
        if ($(this).attr("id") == 'platform_company_title_id') {
            $(this).addClass("third-menu-click").siblings("a")
                .removeClass("third-menu-click");
        }
    })
    //项目需求二级菜单背景色
    $(".project-menu a").each(function () {
        if ($(this).hasClass("third-menu-click")) {
            $(this).removeClass("third-menu-click");
        }
        if ($(this).attr("id") == 'finance_project_title_id') {
            $(this).addClass("third-menu-click").siblings("a")
                .removeClass("third-menu-click");
        }
    })

    //投融事件二级菜单背景色
    $(".event-menu a").each(function () {
        if ($(this).hasClass("third-menu-click")) {
            $(this).removeClass("third-menu-click");
        }
        if ($(this).attr("id") == 'finance_event_title_id') {
            $(this).addClass("third-menu-click").siblings("a")
                .removeClass("third-menu-click");
        }
    })

    //榜单排名二级菜单背景色
    $(".rank-menu a").each(function () {
        if ($(this).hasClass("third-menu-click")) {
            $(this).removeClass("third-menu-click");
        }
        if ($(this).attr("id") == 'company_rank_title_id') {
            $(this).addClass("third-menu-click").siblings("a")
                .removeClass("third-menu-click");
        }
    })
}

//第三阶在初始化时,全部设置为display:none
function thirdinit() {
    //第三阶平台用户初始化third_platform_
    $("div[id*='third_platform_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).css("display", "none");
    });
    //第三阶榜单排名初始化
    $("div[id*='third_detail_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).css("display", "none");
    });
    //第三阶项目需求初始化
    $("div[id*='third_project_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).css("display", "none");
    });
    //第三阶投融时间初始化
    $("div[id*='third_event_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).css("display", "none");
    });
}

//点击第一阶段"平台用户"初始化
function platform_init() {
    $("div[id*='third_platform_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).css("display", "none");
        if ($(this).attr("id") == 'third_platform_detail_company_id') {
            $(id).css("display", "inline");
        }
    });
}


//点击第一阶"项目需求"初始化
function project_init() {
    $('#project_detail_id').css("display", "none");
    $("div[id*='third_project_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).css("display", "none");
        if ($(this).attr("id") == 'third_project_detail_finance_id') {
            $(id).css("display", "inline");
        }
    });
}


//点击第一阶"投融事件"初始化
function event_init() {
    $('#event_detail_id').css("display", "none");
    $("div[id*='third_event_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).css("display", "none");
        if ($(this).attr("id") == 'third_event_detail_finance_id') {
            $(id).css("display", "inline");
        }
    });
}

//点击第一阶"榜单排名"初始化
function rank_init() {
    $("div[id*='third_detail_']").each(function (i) {
        var id = "#" + $(this).attr("id");
        $(id).css("display", "none");
        if ($(this).attr("id") == 'third_detail_company_id') {
            $(id).css("display", "inline");
        }
    });
}

$(function () {
    //点击菜单栏"运行成功"初始化背景色与边框
	 $('#project_detail_id').css("display", "none");
     $('#event_detail_id').css("display", "none");
     $('#third_platform_detail_company_id').css("display", "inline");
     $("#second_user_id").css("display", "inline");
     $("#platform_user_detail_id").css("display", "inline");
    //第一阶点选
    $(".clearfix a").on("click", function () {
        //在点选第一阶菜单时，第二、三菜单全部初始化一次
        secondinit();
        thirdinit();
        if ($(this).attr("id") == 'title_totle') {//项目需求
            project_init();
            $('#project_detail_id').css("display", "inline");
            $("#second_project_id").css("display", "inline");
        } else if ($(this).attr("id") == 'title_user') {//平台用户
            platform_init();
            $("#second_user_id").css("display", "inline");
            $("#platform_user_detail_id").css("display", "inline");
        } else if ($(this).attr("id") == 'title_monthtotle') {//投融事件
            event_init();
            $('#event_detail_id').css("display", "inline");
            $("#second_event_id").css("display", "inline");
        } else if ($(this).attr("id") == 'title_rank') {//榜单排名
            rank_init();
            $('#result_rank_id').css("display", "inline");
            $("#second_rank_id").css("display", "inline");
        } else if ($(this).attr("id") == 'title_history') {//历史数据
            $('#third_detail_history_id').css("display", "inline");
        }
        //第一阶变换背景色
        if ($(this).parent().hasClass("result-first-menu")) {
            firstinit($(this));
            $(this).parent().removeClass("result-first-menu").siblings("div").addClass("result-first-menu");
        }
    })


    //平台用户子菜单点选
    $(".user-menu a").on("click", function () {
        //在榜单排名子菜单点选时，触发事件
        $("div[id*='third_platform_']").each(function (i) {
            var id = "#" + $(this).attr("id");
            $(id).css("display", "none");
        });
        if ($(this).attr("id") == 'platform_company_title_id') {
            $("#third_platform_detail_company_id").css("display", "inline");
        } else if ($(this).attr("id") == 'platform_org_title_id') {
            $("#third_platform_detail_org_id").css("display", "inline");
        }
        if (!$(this).hasClass("third-menu-click")) {
            $(this).addClass("third-menu-click").siblings("a")
                .removeClass("third-menu-click");
        }
    })


    //项目需求子菜单点选
    $(".project-menu a").on("click", function () {
        //在项目需求子菜单点选时，触发事件
        $("div[id*='third_project_']").each(function (i) {
            var id = "#" + $(this).attr("id");
            $(id).css("display", "none");
        });
        if ($(this).attr("id") == 'finance_project_title_id') {
            $("#third_project_detail_finance_id").css("display", "inline");
        } else if ($(this).attr("id") == 'area_project_title_id') {
            $("#third_project_detail_area_id").css("display", "inline");
        } else if ($(this).attr("id") == 'industry_project_title_id') {
            $("#third_project_detail_rank_id").css("display", "inline");
        }else if ($(this).attr("id") == 'month_project_title_id') {
            $("#third_project_detail_month_finance_id").css("display", "inline");
        }
        if (!$(this).hasClass("third-menu-click")) {
            $(this).addClass("third-menu-click").siblings("a")
                .removeClass("third-menu-click");
        }
    })

    //投融事件子菜单点选
    $(".event-menu a").on("click", function () {
        //在项目需求子菜单点选时，触发事件
        $("div[id*='third_event_']").each(function (i) {
            var id = "#" + $(this).attr("id");
            $(id).css("display", "none");
        });
        if ($(this).attr("id") == 'finance_event_title_id') {
            $("#third_event_detail_finance_id").css("display", "inline");
        } else if ($(this).attr("id") == 'area_event_title_id') {
            $("#third_event_detail_area_id").css("display", "inline");
        } else if ($(this).attr("id") == 'industry_event_title_id') {
            $("#third_event_detail_rank_id").css("display", "inline");
        }else if ($(this).attr("id") == 'month_event_title_id') {
            $("#third_event_detail_month_finance_id").css("display", "inline");
        }
        if (!$(this).hasClass("third-menu-click")) {
            $(this).addClass("third-menu-click").siblings("a")
                .removeClass("third-menu-click");
        }
    })

    //榜单排名子菜单点选
    $(".rank-menu a").on("click", function () {
        //在榜单排名子菜单点选时，触发事件
        $("div[id*='third_detail_']").each(function (i) {
            var id = "#" + $(this).attr("id");
            $(id).css("display", "none");
        });
        if ($(this).attr("id") == 'company_rank_title_id') {
            $("#third_detail_company_id").css("display", "inline");
        } else if ($(this).attr("id") == 'org_rank_title_id') {
            $("#third_detail_org_id").css("display", "inline");
        } else if ($(this).attr("id") == 'area_rank_title_id') {
            $("#third_detail_area_id").css("display", "inline");
        } else if ($(this).attr("id") == 'industry_rank_title_id') {
            $("#third_detail_industry_id").css("display", "inline");
        }
        if (!$(this).hasClass("third-menu-click")) {
            $(this).addClass("third-menu-click").siblings("a")
                .removeClass("third-menu-click");
        }
    })

});
