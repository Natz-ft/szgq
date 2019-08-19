/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CheckPassword
 * Author:   whyxs
 * Date:     2019/8/6 16:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.icfcc.credit.check;

/**
 * 〈校验密码强度〉<br>
 * 〈〉
 *
 * @author whyxs
 * @create 2019/8/6
 * @since 1.0.0
 */
public class CheckPassword {

    /**
     * 密码强度
     *
     * @return Z = 字母 S = 数字 T = 特殊字符
     */

    /*
    一、假定密码字符数范围6-16，除英文数字和字母外的字符都视为特殊字符：
    弱：^[0-9A-Za-z]{6,16}$
    中：^(?=.{6,16})[0-9A-Za-z]*[^0-9A-Za-z][0-9A-Za-z]*$
    强：^(?=.{6,16})([0-9A-Za-z]*[^0-9A-Za-z][0-9A-Za-z]*){2,}$
    二、假定密码字符数范围6-16，密码字符允许范围为ASCII码表字符：
    弱：^[0-9A-Za-z]{6,16}$
    中：^(?=.{6,16})[0-9A-Za-z]*[\x00-\x2f\x3A-\x40\x5B-\xFF][0-9A-Za-z]*$
    强：^(?=.{6,16})([0-9A-Za-z]*[\x00-\x2F\x3A-\x40\x5B-\xFF][0-9A-Za-z]*){2,}$
    */
    public static String checkPassword(String passwordStr) {
        String regexZ = "\\d*";
        String regexS = "[a-zA-Z]+";
        String regexT = "\\W+$";
        String regexZT = "\\D*";
        String regexST = "[\\d\\W]*";
        String regexZS = "\\w*";
        String regexZST = "[\\w\\W]*";

        if (passwordStr.matches(regexZ)||passwordStr.matches(regexS)||passwordStr.matches(regexT)) {
            return "弱";
        }
        if (passwordStr.matches(regexZT)||passwordStr.matches(regexST)||passwordStr.matches(regexZS)) {
            return "中";
        }
        if (passwordStr.matches(regexZST)) {
            return "强";
        }
        return "强";
    }

    public static void main(String[] args) {
        System.out.println(checkPassword("shfoieh_0&&&"));
    }
}
