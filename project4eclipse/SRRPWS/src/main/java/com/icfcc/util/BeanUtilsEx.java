/**
 * 
 */
package com.icfcc.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;

/**
 * @author lijj
 *
 */
public class BeanUtilsEx extends BeanUtils {

    //private static Map cache = new HashMap();
    //private static Log logger = LogFactory.getFactory().getInstance(BeanUtilEx.class);

    private BeanUtilsEx() {
    }

    static {
        // 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.sql.Date.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.util.Date.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlTimestampConverter(null),
                java.sql.Timestamp.class);
        // 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
        ConvertUtils.register(new LongConverter(null), Long.class);  
        ConvertUtils.register(new ShortConverter(null), Short.class);  
        ConvertUtils.register(new IntegerConverter(null), Integer.class);  
        ConvertUtils.register(new DoubleConverter(null), Double.class);  
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);  
    }

    public static void copyProperties(Object target, Object source) throws InvocationTargetException,
            IllegalAccessException {
        // 支持对日期copy
        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);

    }

}