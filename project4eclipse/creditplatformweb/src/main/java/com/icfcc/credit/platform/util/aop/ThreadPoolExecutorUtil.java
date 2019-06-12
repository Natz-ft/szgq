package com.icfcc.credit.platform.util.aop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.icfcc.credit.platform.util.ResourceManager;

/**
 * 日志管理 线程池
 * 
 * @author tanshengrui
 * 
 */
public class ThreadPoolExecutorUtil {
	// 池中允许的最大线程数
	private static ExecutorService threadPool = null;
	
	public static ExecutorService getExecutorService() {

		int maximumPoolSize = ResourceManager.getInstance().getIntValue( "maximumPoolSize");
		threadPool = Executors.newFixedThreadPool(maximumPoolSize);
		return threadPool;
	}
}
