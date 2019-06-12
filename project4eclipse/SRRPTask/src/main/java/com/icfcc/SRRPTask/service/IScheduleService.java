/**
 * 
 */
package com.icfcc.SRRPTask.service;

/**
 * @author lijj
 *
 */
public interface IScheduleService {

	/**
	 * 定时执行门户首页统计
	 */
	public void scheduleStatistic();
	/**
	 * 定时将融资需求导入门户
	 */
	public void scheduleFinacingDemand();
	/**
	 * 定时将后台历史统计数据移动到前台
	 *
	 */
	public void scheduleMoveStatistic();

	/**
	 * 定时将后台的投资机构数据导入门户
	 */
	public void scheduleInvestor();

	/**
	 * 定时将融资事件导入门户
	 */
	public void scheduleFinancingEvent();

	/**
	 * 定时抽取门户融资需求
	 */
	public void scheduleFinancingInfo();
	/**
	 * 定时抽取门户新闻动态
	 */
	public void scheduleNews();
	/**
	 * 定时抽取首页合作伙伴信息
	 */
	public void schedulePartner();
	/**
	 * 定时抽取首页友情链接
	 */
	public void scheduleFriendlyLinks();
	public void scheduleNewsInfors();
	public void scheduleContantUs();
	
	public void queryScore();
}
