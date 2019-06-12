/**
 * 
 */
package com.icfcc.SRRPService.task.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPService.enterprise.ReportService;
import com.icfcc.SRRPService.task.IScheduleService;

/**
 * @author lijj 0 0 10,14,16 * * ? 每天上午10点，下午2点，4点 0 0/30 9-17 * * ?
 *         朝九晚五工作时间内每半小时 0 0 12 ? * WED 表示每个星期三中午12点 "0 0 12 * * ?" 每天中午12点触发
 *         "0 15 10 ? * *" 每天上午10:15触发 "0 15 10 * * ?" 每天上午10:15触发
 *         "0 15 10 * * ? *" 每天上午10:15触发 "0 15 10 * * ? 2005" 2005年的每天上午10:15触发
 *         "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发 "0 0/5 14 * * ?"
 *         在每天下午2点到下午2:55期间的每5分钟触发 "0 0/5 14,18 * * ?"
 *         在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 "0 0-5 14 * * ?"
 *         在每天下午2点到下午2:05期间的每1分钟触发 "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
 *         "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发 "0 15 10 15 * ?" 每月15日上午10:15触发
 *         "0 15 10 L * ?" 每月最后一日的上午10:15触发 "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
 *         "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
 *         "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
 */
@Component
public class ScheduleService implements IScheduleService {

	private final Logger log = LoggerFactory.getLogger(ScheduleService.class);
	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

	@Autowired
	private ReportService reportService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icfcc.SRRPService.task.IScheduleService#scheduleStatistic()
	 */
	// @Scheduled(cron="0 0 1 1 * ?") //每月1号凌晨1点执行
//	@Scheduled(cron = "0/30 * *  * * ? ")
	@Override
	public void scheduleStatistic() {
		log.info("ScheduleService::scheduleStatistic");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Date d = c.getTime();
		String dateFlag = format.format(d);
		this.reportService.completeReportBeanTotal(dateFlag);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icfcc.SRRPService.task.IScheduleService#scheduleEnterprise()
	 */
	// @Scheduled(cron="0 0 1 1 * ?") //每月1号凌晨1点执行
//	@Scheduled(cron = "0/30 * *  * * ? ")
	@Override
	public void scheduleEnterprise() {
		log.info("ScheduleService::scheduleEnterprise");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Date d = c.getTime();
		String dateFlag = format.format(d);
		this.reportService.completeReportBeanEnterprise(dateFlag);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icfcc.SRRPService.task.IScheduleService#scheduleOrg()
	 */
	// @Scheduled(cron="0 0 1 1 * ?") //每月1号凌晨1点执行
//	@Scheduled(cron = "0/30 * *  * * ? ")
	@Override
	public void scheduleOrg() {
		log.info("ScheduleService::scheduleOrg");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Date d = c.getTime();
		String dateFlag = format.format(d);
		this.reportService.completeReportBeanOrg(dateFlag);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icfcc.SRRPService.task.IScheduleService#scheduleArea()
	 */
	// @Scheduled(cron="0 0 1 1 * ?") //每月1号凌晨1点执行
//	@Scheduled(cron = "0/30 * *  * * ? ")
	@Override
	public void scheduleArea() {
		log.info("ScheduleService::scheduleArea");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Date d = c.getTime();
		String dateFlag = format.format(d);
		this.reportService.completeReportBeanArea(dateFlag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icfcc.SRRPService.task.IScheduleService#scheduleTrade()
	 */
	// @Scheduled(cron="0 0 1 1 * ?") //每月1号凌晨1点执行
//	@Scheduled(cron = "0/30 * *  * * ? ")
	@Override
	public void scheduleTrade() {
		log.info("ScheduleService::scheduleTrade");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Date d = c.getTime();
		String dateFlag = format.format(d);
		this.reportService.completeReportBeanTrade(dateFlag);

	}

}
