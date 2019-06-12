$(function () {

    // banner轮播图
    layui.use('carousel', function () {
        var carousel = layui.carousel;
        //建造实例
        carousel.render({
            elem: '#lunbo'
            , width: '300px' //设置容器宽度
            , height: "339px"
            ,indicator: 'none' //指示器位置 
            , arrow: 'always' //始终显示箭头
            ,anim: 'fade' //切换动画方式
            ,interval: 5000 //切换时长
        });
        carousel.on('change(lunbo)', function(obj){ //lunbo来源于对应HTML容器的 lay-filter="lunbo" 属性值
        	var imgUrl = obj.item.attr("data-imgUrl"); //当前条目的元素对象的自定义属性
        	var url = obj.item.attr("data-url");
        	setTimeout(function(){
        		$(".img-show").each(function(){
        			var $curImg = $(this);
        			if($curImg.is(":visible")){
        				$curImg.hide();
        			}else{
        				$curImg.attr({"src":imgUrl,"onclick":"window.location.href='"+url+"'"}).fadeIn(300);
        			}
        		});
        	},100);
        	
        });
        $(".layui-carousel[lay-arrow=always] .layui-carousel-arrow").css({"top":"89%","left":"100px","border":"1.5px solid"});
        $(".layui-carousel[lay-arrow=always] .layui-carousel-arrow[lay-type=add]").css({"right":"100px"});
    });
    // 关注我们二维码
    $(".focusUs a").hover(function () {
        $(this).children("div").show();
    }, function () {
        $(this).children("div").hide();
    })
    // 返回顶部
    $(".backTop").on("click", function () {
        $('body,html').animate({scrollTop: 0}, 500);
    })
    // 友情链接
    $(".linksBtn a").on("click", function () {
        if (!$(this).hasClass("active")) {
            $(this).addClass("active").siblings("a").removeClass("active");
        }
        var index = $(this).index();
        $(".linksWrap>div").eq(index).show().siblings("div").hide();
    })
});
//首页快捷菜单跳转
var shortcutid;
$(function () {
    $("div.fixMenu ul li a").click(function () {
        shortcutid = $(this).attr("id");
        window.location.href = "relate.html?shortcutid=" + shortcutid;
    });

});

//投資动态滚动播出
//滚动公告
function invest_roll_fun() {
  //每一秒减去60px----->一个li的高度,linear----->滚动更加流畅
  $('#invest_roll table').animate({marginTop: '-=60px'}, 3000, "linear", function () {
      var top = $('#invest_roll table').attr('style');
      if (top == 'margin-top: -60px;') {
          $('#invest_roll table').attr('style', 'margin-top: 0px;')
          $("#invest_roll table tr:first").remove().clone(true).appendTo("#invest_roll table");
      }
  });
}
var timer = setInterval(invest_roll_fun, 3000);
$(function () {
  $("#invest_roll table tr").hover(function () {
      clearInterval(timer);
  }, function () {
      timer = setInterval(invest_roll_fun, 3000)
  });
});

