package com.icfcc.credit.platform.util.aop;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.icfcc.credit.platform.service.system.PlatformOperateLogService;
import com.icfcc.credit.platform.util.NetworkUtil;

/**
 * 日志记录，添加、删除、修改方法AOP
 * 
 * 
 */
@Aspect
@Component
public class LogAspect {

	private final static Log log = LogFactory.getLog(LogAspect.class);
	@Autowired
	private PlatformOperateLogService logService;

	/**
	 * 只对增删改进行日志统计
	 */
	@Pointcut("execution(* com.icfcc.credit.platform.service.*.*.*save*(..))||"
	 + "execution(* com.icfcc.credit.platform.service.*.*.*update*(..))||"
	 + "execution(* com.icfcc.credit.platform.service.*.*.*delete*(..))||"
	 + "execution(* com.icfcc.credit.platform.service.*.*.*update*(..))||" 
	 + "execution(* com.icfcc.credit.platform.service.*.*.*delete*(..))||"
	 + "execution(* com.icfcc.credit.platform.service.*.*.*update*(..))||" 
	 + "execution(* com.icfcc.credit.platform.service.*.*.*add*(..))")
	private void serviceCall() {
	}

	/**
	 * 环绕通知
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "serviceCall()")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		String ip = "";
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			ip = NetworkUtil.getIpAddress(request);
			log.info("IP:" + ip);
		} catch (Exception e1) { // TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object result = null;
		Date createTime = new Date();
		long start = System.currentTimeMillis();
		// 返回值
		result = joinPoint.proceed();
		long end = System.currentTimeMillis();
		// 操作执行时间
		long operateTime = end - start;
		log.debug("result:" + result);
		ThreadPoolExecutorUtil.getExecutorService().execute(new LogTask(joinPoint, logService, String.valueOf(operateTime), result, createTime, ip));
		return result;
	}

	public PlatformOperateLogService getLogService() {
		return logService;
	}

	public void setLogService(PlatformOperateLogService logService) {
		this.logService = logService;
	}

	public static Log getLog() {
		return log;
	}

}
