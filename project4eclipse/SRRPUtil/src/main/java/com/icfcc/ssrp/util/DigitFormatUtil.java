package com.icfcc.ssrp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DigitFormatUtil {
    /**
     * 
     * <p>功能描述:[数字格式化]</p>
     * @param object
     * @return
     * @author:zhanglf
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static Object digitFormat(Object object) {
        
        if (object == null) {
            return "";
        } else {
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
            if(object instanceof String){
                object = new BigDecimal(object.toString());
            }
            df.applyPattern("#0.##");
            return df.format(object);
        }
    }

}
