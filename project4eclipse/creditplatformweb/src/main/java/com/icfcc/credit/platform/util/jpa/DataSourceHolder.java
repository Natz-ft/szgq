package com.icfcc.credit.platform.util.jpa;

public class DataSourceHolder {
	
	
	public static String DATA_SOURCE_MASTER="MASTER";
	public static String DATA_SOURCE_SLAVER="SLAVE";
    //线程本地环境
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();
    //设置数据源
    public static void setDataSource(String customerType) {
        dataSources.set(customerType);
    }
    //获取数据源
    public static String getDataSource() {
        return (String) dataSources.get();
    }
    //清除数据源
    public static void clearDataSource() {
        dataSources.remove();
    }
 
}
