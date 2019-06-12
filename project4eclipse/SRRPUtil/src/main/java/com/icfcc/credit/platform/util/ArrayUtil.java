package com.icfcc.credit.platform.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Array工具类 <功能详细描述>
 * 
 * @version [版本号, 2014-9-23]
 * @see [相关类/方法]aaaaaaaaaaaaaaaaaaaa
 * @since [产品/模块版本]
 */
public class ArrayUtil
{
    /**
     * <拆分集合>
     * @param resList 要拆分的集合
     * @param count 每个集合的元素个数
     * @return 返回拆分后的各个集合
     * @author Densen.Liu
     * @date 2017年5月16日
     */
    public static <T> List<List<T>> split(List<T> resList, int count) {

	if (resList == null || resList.size() == 0 || count < 1)
	    return null;
	List<List<T>> ret = new ArrayList<List<T>>();
	int size = resList.size();
	if (size <= count) { // 数据量不足count指定的大小
	    ret.add(resList);
	} else {
	    int pre = size / count;
	    int last = size % count;
	    // 前面pre个集合，每个大小都是count个元素
	    for (int i = 0; i < pre; i++) {
		List<T> itemList = new ArrayList<T>();
		for (int j = 0; j < count; j++) {
		    itemList.add(resList.get(i * count + j));
		}
		ret.add(itemList);
	    }
	    // last的进行处理
	    if (last > 0) {
		List<T> itemList = new ArrayList<T>();
		for (int i = 0; i < last; i++) {
		    itemList.add(resList.get(pre * count + i));
		}
		ret.add(itemList);
	    }
	}
	return ret;

    }
    /**
     * 数组转list(去除空字符串元素) <功能详细描述>
     * 
     * @param ids
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static <T> List<?> toList(T[] ids, Class<?> type)
    {
        @SuppressWarnings("rawtypes")
        List list = new ArrayList();
        try
        {
            for (int i = 0; i < ids.length; i++)
            {
                if ("".equals(ids[i]))
                {
                    continue;
                }
                if (type.equals(String.class))
                {
                    list.add(String.valueOf(ids[i]));
                }
                if (type.equals(Integer.class))
                {
                    list.add(Integer.valueOf(String.valueOf(ids[i])));
                }
                if (type.equals(Long.class))
                {
                    list.add(Long.valueOf(String.valueOf(ids[i])));
                }
                if (type.equals(Float.class))
                {
                    list.add(Float.valueOf(String.valueOf(ids[i])));
                }
                if (type.equals(Double.class))
                {
                    list.add(Double.valueOf(String.valueOf(ids[i])));
                }
                if (type.equals(Boolean.class))
                {
                    list.add(Boolean.valueOf(String.valueOf(ids[i])));
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("数组类型转换成list时发生异常!", e);
        }
        return list;
    }
    
    /**
     * 字符串数组转换为Long类型的list <功能详细描述>
     * 
     * @param ids
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<Long> convertStringArrayToLongList(String[] ids)
    {
        List<Long> list = new ArrayList<Long>();
        try
        {
            for (int i = 0; i < ids.length; i++)
            {
                if (StringUtils.isBlank(ids[i]))
                {
                    continue;
                }
                list.add(Long.valueOf(ids[i]));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("字符串数组转换为Long类型的list时发生异常!", e);
        }
        return list;
    }
    
    /**
     * 字符串数组转换为Integer类型的list <功能详细描述>
     * 
     * @param ids
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<Integer> convertStringArrayToIntegerList(String[] ids)
    {
        List<Integer> list = new ArrayList<Integer>();
        try
        {
            for (int i = 0; i < ids.length; i++)
            {
                if (StringUtils.isBlank(ids[i]))
                {
                    continue;
                }
                list.add(Integer.valueOf(ids[i]));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("字符串数组转换为Integer类型的list时发生异常!", e);
        }
        return list;
    }
    
    /**
     * 字符串数组转换为String类型的list <功能详细描述>
     * 
     * @param ids
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<String> convertStringArrayToStringList(String[] ids)
    {
        List<String> list = new ArrayList<String>();
        try
        {
            for (int i = 0; i < ids.length; i++)
            {
                if (StringUtils.isBlank(ids[i]))
                {
                    continue;
                }
                list.add(ids[i]);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("字符串数组转换为String类型的list时发生异常!", e);
        }
        return list;
    }
}
