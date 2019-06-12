package com.icfcc.SRRPService.enterprise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanArea;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTrade;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.RealTimeReportDaoImpl;

/**
 * 实时报表，包括机构报表和平台实时区域排名，实时行业排名
 * @author lijj
 *
 */
@Service
@Transactional(value = "transactionManager")
public class RealTimeReportService {

	private Logger log = LoggerFactory.getLogger(RealTimeReportService.class);

	@Autowired
	private RealTimeReportDaoImpl realTimeReportDao;

	/**
	 * 获得机构的实时行业投资报表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<ReportBeanTrade> getTradeInvestByOrg(String orgId, Date start, Date end,Integer limit) {
		if (StringUtils.isEmpty(orgId)) {
			log.info("no params orgId!");
			return new ArrayList<ReportBeanTrade>();
		} else {
			List<ReportBeanTrade> res = this.realTimeReportDao.findTradeInvestByOrgId(orgId, start, end,limit);
			Double allAmount = this.realTimeReportDao.sumInvestByOrgId(orgId, start, end);
			for (ReportBeanTrade rbo : res) {
				rbo.setAllAmount(allAmount);
			}
			Collections.sort(res);
			return res;
		}
	}
	/**
	 * 获得机构的实时行业投资报表 时间参数类型改为String
	 * 
	 * 
	 * @param orgId
	 * @return
	 */
	public List<ReportBeanTrade> getTradeInvestByOrgStr(String orgId, String start, String end,Integer limit,String username) {
		if (StringUtils.isEmpty(orgId)) {
			log.info("no params orgId!");
			return new ArrayList<ReportBeanTrade>();
		} else {
			//获取按照行业汇总的数据
			List<ReportBeanTrade> res = this.realTimeReportDao.findTradeInvestByOrgIdStr(orgId, start, end,limit,username);
			//获取该机构的汇总金额
			Double allAmount = this.realTimeReportDao.sumInvestByOrgIdStr(orgId, start, end,username);
			for (ReportBeanTrade rbo : res) {
				rbo.setAllAmount(allAmount);
			}
			Collections.sort(res);
			return res;
		}
	}
	/**
	 * 获得实时行业投资报表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<ReportBeanTrade> getTradeInvest() {

		List<ReportBeanTrade> res = this.realTimeReportDao.findTradeInvestByOrgId(null, null, null,null);
		Double allAmount = this.realTimeReportDao.sumInvestByOrgId(null, null, null);
		for (ReportBeanTrade rbo : res) {
			rbo.setAllAmount(allAmount);
		}
		Collections.sort(res);
		return res;
	}
	
	/**
	 * 获得机构的实时地区投资报表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<ReportBeanArea> getAreaInvestByOrg(String orgId, Date start, Date end,Integer limit) {
		if (StringUtils.isEmpty(orgId)) {
			log.info("no params orgId!");
			return new ArrayList<ReportBeanArea>();
		} else {
			List<ReportBeanArea> res = this.realTimeReportDao.findAreaInvestByOrgId(orgId, start, end,limit);
			Double allAmount = this.realTimeReportDao.sumInvestByOrgId(orgId, start, end);
			for (ReportBeanArea rbo : res) {
				rbo.setAllAmount(allAmount);
			}
			Collections.sort(res);
			return res;
		}
	}
	
	/**
	 * 获得企业的实时地区投资报表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<ReportBeanArea> getAreaInvest() {

		List<ReportBeanArea> res = this.realTimeReportDao.findAreaInvestByOrgId(null, null, null,null);
		Double allAmount = this.realTimeReportDao.sumInvestByOrgId(null, null, null);
		for (ReportBeanArea rbo : res) {
			rbo.setAllAmount(allAmount);
		}
		Collections.sort(res);
		return res;
	}

}
