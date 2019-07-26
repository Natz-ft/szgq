/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestA2B
 * Author:   whyxs
 * Date:     2019/7/24 17:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author whyxs
 * @create 2019/7/24
 * @since 1.0.0
 */
public class TestA2B {

    public static void main(String[] args) {

        List<Map<String,String>> listA = new ArrayList<>();
        List<Map<String,String>> listB = new ArrayList<>();


        Map<String, String> b = new HashMap<>();
        String idName = "";
        for (Map<String, String> a : listA) {
            String lineName = a.get("lineName");

            b.put("lineName",lineName);


        }


    }

}
