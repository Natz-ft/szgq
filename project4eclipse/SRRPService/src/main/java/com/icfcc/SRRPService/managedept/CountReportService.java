package com.icfcc.SRRPService.managedept;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.ReportBeanEnterpriseResult;
import com.icfcc.SRRPDao.jpa.entity.managedept.FinacingStatisticsResult;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.impl.CountReportDaoImpl;
import com.icfcc.SRRPDao.pojo.CountReportAreaBean;

@Service
@Transactional(value = "transactionManager")
public class CountReportService {

	@Autowired
	public CountReportDaoImpl countReportDao;

	/**
	 * 产生机构融资统计数据
	 * 
	 * @param dateFlag
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> sumAmountByArea(String dateFlag) {
		List<Map> list = countReportDao.sumAmountByArea(dateFlag);
		return list;
	}

	public CountReportAreaBean generateReportBeanTotal(String dateFlag) {
		CountReportAreaBean bean = countReportDao.generateReportBeanTotal(dateFlag);
		return bean;
	}

	/**
	 * 通过传递过来的时间，融资轮次统计企业榜单
	 * 
	 * @Title: getCompaniesList
	 * @param queryCondition
	 * @author Daiyx
	 * @return List<ReportBeanEnterpriseResult> 返回类型
	 */
	public List<ReportBeanEnterpriseResult> getCompaniesList(QueryCondition queryCondition) {

		return (List<ReportBeanEnterpriseResult>) countReportDao.getCompaniesList(queryCondition);
	}

	/**
	 * 融资统计列表
	 * 
	 * @param queryCondition
	 * @return
	 */
	public List<FinacingStatisticsResult> getFinacingStatistics(QueryCondition queryCondition) {

		return (List<FinacingStatisticsResult>) countReportDao.getFinacingStatistics(queryCondition);
	}

	/**
	 * 获取平台相应日期的平台企业数 机构数 用户数
	 * 
	 * @param queryCondition
	 * @return
	 */
	public Map<String, String> getCount(QueryCondition queryCondition) {

		return countReportDao.getCount(queryCondition);
	}

	/**
	 * 获取相应日期的发布需求个数 融资金额 解决融资金额个数
	 * 
	 * @param queryCondition
	 * @return
	 */
	public Map<String, String> getMap(QueryCondition queryCondition) {

		return countReportDao.getMap(queryCondition);
	}
}
