package com.icfcc.credit.platform.util.aop;

import java.util.Arrays;
import java.util.Date;

import javassist.NotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.jpa.entity.system.PlatformOperateLog;
import com.icfcc.credit.platform.service.system.PlatformOperateLogService;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.credit.platform.web.BaseController;

/**
 * 记录操作人的增删改记录 线程类
 */
public class LogTask extends BaseController implements Runnable {

	private ProceedingJoinPoint joinPoint;
	private final static Log log = LogFactory.getLog(LogTask.class);
	private PlatformOperateLogService logService;
	private String operateTime;
	private Object returnResult;
	private Date createTime;
	private String ip;

	public LogTask(ProceedingJoinPoint joinPoint,
			PlatformOperateLogService logService, String operateTime,
			Object returnResult, Date createTime, String ip) {
		super();
		this.joinPoint = joinPoint;
		this.logService = logService;
		this.operateTime = operateTime;
		this.returnResult = returnResult;
		this.createTime = createTime;
		this.ip = ip;
	}

	public LogTask() {
		super();
	}

	@Override
	public void run() {
		log.debug("Runnable Run start");
		/*
		 * try { HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()).getRequest(); } catch
		 * (Exception e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */
		PlatformOperateLog operateLog = new PlatformOperateLog();
		ShiroUser user = getCurrentUser();
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		log.debug("success方法名称:" + methodName);
		operateLog.setMethodName(methodName);
		// 目标参数
		String classType = joinPoint.getTarget().getClass().getName();
		Class<?> clazz = null;
		try {
			clazz = Class.forName(classType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 类的路径
		String clazzName = clazz.getName();
		operateLog.setServicePath(clazzName);
		Object[] arg = joinPoint.getArgs();
		if (arg != null) {
			log.info("Args:" + Arrays.toString(joinPoint.getArgs()));
			operateLog.setParam(Arrays.toString(joinPoint.getArgs()));
		}
		// return结果
		log.debug("time:" + operateTime + " ms");
		log.debug("clazzName:" + clazzName);
		if (returnResult == null) {
			operateLog.setReturnResult(JSON.toJSONString(returnResult));
		}
		operateLog.setOperateTime(String.valueOf(operateTime));
		operateLog.setCreateTime(createTime);
		if (null != user) {
			operateLog.setUserName(user.getName());
		}
		operateLog.setIp(ip);

		try {
			log.info("operateLog:" + operateLog);
			logService.operateLog(operateLog);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.debug("Runnable Run end");
	}

}
