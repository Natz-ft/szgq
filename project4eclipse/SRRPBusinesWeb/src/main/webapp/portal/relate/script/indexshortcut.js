//首页快捷菜单点击事件
function getParams(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
};
var shortcutid = getParams("shortcutid");
if (shortcutid !== null && shortcutid !== undefined && shortcutid !== '') {
    contactusinit();
    if (shortcutid == 'shortcut_question') {
        $("#contactus_question").css("display", "inline");
        $("#contactus_click_question").attr("class", "click-color");
    } else if (shortcutid == 'shortcut_online') {
        $("#contactus_answer").css("display", "inline");
        $("#contactus_click_answer").attr("class", "click-color");
    } else if (shortcutid == 'shortcut_notice') {
        $("#contactus_notice").css("display", "inline");
        $("#contactus_click_notice").attr("class", "click-color");
    }else if (shortcutid == 'shortcut_contact') {
        $("#contactus_contact").css("display", "inline");
        $("#contactus_click_contact").attr("class", "click-color");
    }
}

