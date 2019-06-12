package com.icfcc.credit.platform.util;




import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author JERRY.CHEN
 * @date 2016��10��29��
 */
public class ReoprtUtil {
	/**
	 * ������󣬽���ת����һ��Map
	 * @param userCla
	 * @param bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @author JERRY.CHEN
	 * @date 2016��10��29��
	 */
	public static Map bean2map(Class userCla ,Object bean) throws IllegalArgumentException, IllegalAccessException {
		   Map map = new HashMap();
	       Field[] fields = userCla.getDeclaredFields();
	       for (Field field : fields) {
		    	    PropertyDescriptor pd;
					try {
						pd = new PropertyDescriptor(field.getName(),userCla);
						 Method getMethod = pd.getReadMethod();//获得get方法
		    	            Object obj = getMethod.invoke(bean);//执行get方法返回一个Object
		    	         map.put(field.getName(),obj );
					} catch (Exception e) {
						e.printStackTrace();
					}
	    	    }
	       System.out.println(map);
	       return map;
	}
	/**
	 * ��� ������ѯ����õ���List�ж����ʱ�����ԣ����ձ���ĸ�ʽ��������
	 * @param list
	 * @return
	 * @author JERRY.CHEN
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @date 2016��10��29��
	 */
	public List<Map> getSortList(List beanlist,Class c) throws IllegalArgumentException, IllegalAccessException{
		
		for(Object obj:beanlist){
			Map m = bean2map(c,obj);
		}
		return null;
	}
	
	public LinkedHashMap sortMap(Map old,List sort) {
	
		return null;
	}
	
	public static void main(String[]args) {
		List<String> nameList = new ArrayList<String>();
		nameList.add("name");
		nameList.add("age");
		nameList.add("sex");
		
		
		
//		ListOrderedMap map = new ListOrderedMap();
	}
}
