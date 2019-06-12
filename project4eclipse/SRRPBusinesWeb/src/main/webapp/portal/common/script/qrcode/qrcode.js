$(function () {
    // 关注我们二维码
    $(".focusUs a").hover(function () {
        $(this).children("div").show();
    }, function () {
        $(this).children("div").hide();
    })
});