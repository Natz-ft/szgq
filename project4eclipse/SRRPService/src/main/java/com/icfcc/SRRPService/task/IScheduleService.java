/**
 * 
 */
package com.icfcc.SRRPService.task;

/**
 * @author lijj
 *
 */
public interface IScheduleService {
	
	/**
	 * 定时生成汇总的统计结果
	 */
	public void scheduleStatistic();

	/**
	 * 定时生成企业排名统计
	 */
	public void scheduleEnterprise();

	/**
	 * 定时生成机构排名统计
	 */
	public void scheduleOrg();

	/**
	 * 定时生成地区排名统计
	 */
	public void scheduleArea();

	/**
	 * 定时生成行业排名统计
	 */
	public void scheduleTrade();

}
