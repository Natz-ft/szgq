package com.icfcc.credit.platform.util.jpa;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class DataSourceAspect implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

	private final Log log = LogFactory.getLog(DataSourceAspect.class);

	// service方法执行之前被调用
	public void before(Method method, Object[] args, Object target) throws Throwable {
		// System.out.println("切入点: " + target.getClass().getName() + "类中"
		// + method.getName() + "方法");
		if (method.getName().startsWith("add") || method.getName().startsWith("create")
				|| method.getName().startsWith("save") || method.getName().startsWith("edit")
				|| method.getName().startsWith("update") || method.getName().startsWith("delete")
				|| method.getName().startsWith("remove")) {
			log.info("db: master");
			DataSourceHolder.setDataSource(DataSourceHolder.DATA_SOURCE_MASTER);
		} else {
			log.info("db: slave");
			DataSourceHolder.setDataSource(DataSourceHolder.DATA_SOURCE_SLAVER);
		}
	}

	// service方法执行完之后被调用
	public void afterReturning(Object arg0, Method method, Object[] args, Object target) throws Throwable {
	}

	// 抛出Exception之后被调用
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		DataSourceHolder.setDataSource(DataSourceHolder.DATA_SOURCE_SLAVER);
		log.info("db exception: slave");
	}
}