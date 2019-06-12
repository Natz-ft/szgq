/**
 * 
 */
package com.icfcc.SRRPService;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PReportBeanArea;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PReportBeanEnterprise;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PReportBeanOrg;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PReportBeanTotal;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PReportBeanTrade;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PReportBeanAreaDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PReportBeanEnterpriseDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PReportBeanOrgDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PReportBeanTotalDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PReportBeanTradeDao;
import com.icfcc.SRRPDao.s1.jpa.entity.ReportBeanArea;
import com.icfcc.SRRPDao.s1.jpa.entity.ReportBeanEnterprise;
import com.icfcc.SRRPDao.s1.jpa.entity.ReportBeanOrg;
import com.icfcc.SRRPDao.s1.jpa.entity.ReportBeanTotal;
import com.icfcc.SRRPDao.s1.jpa.entity.ReportBeanTrade;
import com.icfcc.SRRPDao.s1.jpa.repository.ReportBeanAreaDao;
import com.icfcc.SRRPDao.s1.jpa.repository.ReportBeanEnterpriseDao;
import com.icfcc.SRRPDao.s1.jpa.repository.ReportBeanOrgDao;
import com.icfcc.SRRPDao.s1.jpa.repository.ReportBeanTotalDao;
import com.icfcc.SRRPDao.s1.jpa.repository.ReportBeanTradeDao;
import com.icfcc.SRRPService.util.BeanUtilsEx;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * @author lijj 移动历史统计数据到门户（不需要）
 */
@Service
public class StatisticMoveService   {

	@Autowired
	private ReportBeanAreaDao reportBeanAreaDao;
	@Autowired
	private ReportBeanEnterpriseDao reportBeanEnterpriseDao;
	@Autowired
	private ReportBeanOrgDao reportBeanOrgDao;
	@Autowired
	private ReportBeanTradeDao reportBeanTradeDao;
	@Autowired
	private ReportBeanTotalDao reportBeanTotalDao;
	@Autowired
	private PReportBeanAreaDao preportBeanAreaDao;
	@Autowired
	private PReportBeanEnterpriseDao preportBeanEnterpriseDao;
	@Autowired
	private PReportBeanOrgDao preportBeanOrgDao;
	@Autowired
	private PReportBeanTradeDao preportBeanTradeDao;
	@Autowired
	private PReportBeanTotalDao preportBeanTotalDao;

	/**
	 * 从源数据源 按时间标签获取历史统计数据
	 * 
	 * @param timeId
	 * @return
	 */
	@Transactional(value = "transactionManager1")
	public HashMap<String, List<?>> getStatistic(String timeId) throws Exception {
		HashMap<String, List<?>> res = new HashMap<String, List<?>>();
		List<ReportBeanArea> area = this.reportBeanAreaDao.findByTimeId(timeId);
		List<ReportBeanEnterprise> enterprise = this.reportBeanEnterpriseDao.findByTimeId(timeId);
		List<ReportBeanOrg> org = this.reportBeanOrgDao.findByTimeId(timeId);
		List<ReportBeanTotal> total = this.reportBeanTotalDao.findByTimeId(timeId);
		List<ReportBeanTrade> trade = this.reportBeanTradeDao.findByTimeId(timeId);
		for (ReportBeanEnterprise o : enterprise) {
			o.setRankCount(this.reportBeanEnterpriseDao.countByEnterpriseId(o.getEnterpriseId()));
		}
		for (ReportBeanOrg o : org) {
			o.setRankCount(this.reportBeanOrgDao.countByOrgId(o.getOrgId()));
		}
		ReportBeanTotal totalo = new ReportBeanTotal();
		for (ReportBeanTotal o : total) {
			totalo.setArea("total");
			totalo.setCreateTime(o.getCreateTime());
			totalo.setTimeId(o.getTimeId());
			totalo.setDemandAmount(totalo.getDemandAmount().add(o.getDemandAmount()));
			totalo.setLoanAmount(totalo.getLoanAmount().add(o.getLoanAmount()));
			totalo.setEnterpriseCount(totalo.getEnterpriseCount() + o.getEnterpriseCount());
			totalo.setOrgCount(totalo.getOrgCount() + o.getOrgCount());
		}
		res.put("total", total);
		res.put("area", area);
		res.put("enterprise", enterprise);
		res.put("org", org);
		res.put("total", total);
		res.put("trade", trade);
		return res;
	}

	/**
	 * 向目标数据源保存一组历史同居数据
	 * 
	 * @param ol
	 */
	@Transactional(value = "transactionManager")
	public void saveStatistic(HashMap<String, List<?>> ol) {
		List<PReportBeanArea> area = new ArrayList<PReportBeanArea>();
		for (Object o : ol.get("area")) {
			ReportBeanArea oo = (ReportBeanArea) o;
			PReportBeanArea to = new PReportBeanArea();
			try {
				BeanUtilsEx.copyProperties(to, oo);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			area.add(to);
		}
		List<PReportBeanEnterprise> enterprise = new ArrayList<PReportBeanEnterprise>();
		for (Object o : ol.get("enterprise")) {
			ReportBeanEnterprise oo = (ReportBeanEnterprise) o;
			PReportBeanEnterprise to = new PReportBeanEnterprise();
			try {
				BeanUtilsEx.copyProperties(to, oo);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			enterprise.add(to);
		}
		List<PReportBeanOrg> org = new ArrayList<PReportBeanOrg>();
		for (Object o : ol.get("org")) {
			ReportBeanOrg oo = (ReportBeanOrg) o;
			PReportBeanOrg to = new PReportBeanOrg();
			try {
				BeanUtilsEx.copyProperties(to, oo);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			org.add(to);
		}
		List<PReportBeanTotal> total = new ArrayList<PReportBeanTotal>();
		for (Object o : ol.get("total")) {
			ReportBeanTotal oo = (ReportBeanTotal) o;
			PReportBeanTotal to = new PReportBeanTotal();
			try {
				BeanUtilsEx.copyProperties(to, oo);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			total.add(to);
		}
		List<PReportBeanTrade> trade = new ArrayList<PReportBeanTrade>();
		for (Object o : ol.get("trade")) {
			ReportBeanTrade oo = (ReportBeanTrade) o;
			PReportBeanTrade to = new PReportBeanTrade();
			try {
				BeanUtilsEx.copyProperties(to, oo);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			trade.add(to);
		}
		this.preportBeanAreaDao.save(area);
		this.preportBeanEnterpriseDao.save(enterprise);
		this.preportBeanOrgDao.save(org);
		this.preportBeanTotalDao.save(total);
		this.preportBeanTradeDao.save(trade);

	}

	/**
	 * 完全执行一次历史统计数据移动
	 * 
	 * @param timeId
	 */
	// @Scheduled
	public void completeMoveStatistic(String timeId) throws Exception {
		HashMap<String, List<?>> res = this.getStatistic(timeId);
		this.saveStatistic(res);
	}

}
