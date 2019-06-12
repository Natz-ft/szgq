package com.icfcc.SRRPService.enterprise;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanArea;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanEnterprise;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanOrg;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTotal;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTrade;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.QueryAreaFinacingResult;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.QueryIndustryFinacingResult;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.ReportByArea;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.ReportByIndustry;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ReportBeanAreaDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ReportBeanEnterpriseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ReportBeanOrgDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ReportBeanTotalDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ReportBeanTradeDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.CompanyBaseDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.InvestorDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.InvestorLoanDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.RealTimeReportDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.ReportBeanAreaDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.ReportBeanEnterpriseDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.ReportBeanOrgDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.ReportBeanTradeDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.ReportByAreaDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.ReportByIndustryDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.impl.FinacingDemandInfoDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.impl.QueryAreaFinacingResultDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.impl.QueryIndustryFinacingResultDaoImpl;
import com.icfcc.SRRPDao.pojo.CountReportAreaBean;
import com.icfcc.SRRPService.Constants;
import com.icfcc.SRRPService.DicWord;

@Service
@Transactional(value = "transactionManager")
public class ReportService {

	private Logger log = LoggerFactory.getLogger(ReportService.class);

	private final static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM");

	private final static Integer NUM_STATISTIC = 10;

	@Autowired
	private InvestorDaoImpl investorDao;
	@Autowired
	private CompanyBaseDaoImpl companyBaseDao;
	@Autowired
	private InvestorLoanDaoImpl investorLoanDao;
	@Autowired
	private FinacingDemandInfoDaoImpl finacingDemandInfoDao;
	@Autowired
	private ReportBeanTotalDao reportBeanTotalDao;
	@Autowired
	private ReportBeanEnterpriseDao reportBeanEnterpriseDao;
	@Autowired
	private ReportBeanEnterpriseDaoImpl reportBeanEnterpriseDaoImpl;
	@Autowired
	private ReportBeanOrgDao reportBeanOrgDao;
	@Autowired
	private ReportBeanOrgDaoImpl reportBeanOrgDaoImpl;
	@Autowired
	private ReportBeanAreaDao reportBeanAreaDao;
	@Autowired
	private ReportBeanTradeDao reportBeanTradeDao;
	@Autowired
	private ReportBeanAreaDaoImpl reportBeanAreaDaoImpl;
	@Autowired
	private ReportBeanTradeDaoImpl reportBeanTradeDaoImpl;
	@Autowired
	private RealTimeReportDaoImpl realTimeReportDao;
	@Autowired
	private ReportByAreaDao reportByAreaDao;
	@Autowired
	private ReportByIndustryDao reportByIndustryDao;

	@Autowired
	public QueryAreaFinacingResultDaoImpl queryAreaFinacingResultDaoImpl;
	@Autowired
	public QueryIndustryFinacingResultDaoImpl queryIndustryFinacingResultDaoImpl;

	/**
	 * 获取-统计报表-融资统计
	 * 
	 * @param dateFlag
	 * @return
	 */
	public Map<String, ReportBeanTotal> generateReportBeanTotal(String dateFlag) {
		Date now = new Date();
		Map<String, ReportBeanTotal> res = new HashMap<String, ReportBeanTotal>();
		Date start = null, end = null;
		DicWord dw = DicWord.getDicWord();
		Map<String, String> location = dw
				.getDicByType(Constants.KEY_DICTYPE_XZDQ);
		// Calendar c = Calendar.getInstance();
		Date d;
		try {
			if (StringUtils.isNotEmpty(dateFlag)) {
				d = format.parse(dateFlag);
				start = getLastMonthStart(d);
				end = getLastMonthEnd(d);
			}
		} catch (ParseException e) {
			log.error("dataFlag parameter error!");
			return null;
		}
		Map<String, BigDecimal> demandAmount = this.finacingDemandInfoDao
				.sumAmountByArea(null, start, end, null);
		Map<String, BigDecimal> loanAmount = this.investorLoanDao
				.sumAmountByArea(null, start, end, null);
		Map<String, Long> companyCount = this.companyBaseDao.countByArea(null,
				null, null, null);
		Map<String, Long> InvestorCount = this.investorDao.countByArea(null,
				null, null, null);

		for (String key : location.keySet()) {
			ReportBeanTotal o = new ReportBeanTotal();
			o.setDemandAmount(demandAmount.get(key));
			o.setLoanAmount(loanAmount.get(key));
			o.setEnterpriseCount(companyCount.get(key));
			o.setOrgCount(InvestorCount.get(key));
			o.setTimeId(dateFlag);
			o.setArea(key);
			o.setCreateTime(now);
			res.put(key, o);
		}
		return res;
	}

	/**
	 * 保存-统计报表-融资统计到 rp_report_total表
	 * 
	 * @param reportInfo
	 */
	public void saveReportBeanTotal(Map<String, ReportBeanTotal> reportInfo) {
		this.reportBeanTotalDao.save(reportInfo.values());
	}

	/**
	 * 加工 统计报表-融资统计中间表rp_report_total
	 * 
	 * @param dateFlag
	 */
	public void completeReportBeanTotal(String dateFlag) {
		Map<String, ReportBeanTotal> res = this
				.generateReportBeanTotal(dateFlag);
		this.saveReportBeanTotal(res);
	}

	/**
	 * 根據统计时间获取融资总量
	 * 
	 * @param dateFlag
	 * @return
	 */
	public Map<String, ReportBeanTotal> getReportBeanTotal(String dateFlag) {
		ReportBeanTotal total = new ReportBeanTotal();
		List<ReportBeanTotal> tres = this.reportBeanTotalDao
				.findByTimeId(dateFlag);
		Map<String, ReportBeanTotal> res = new HashMap<String, ReportBeanTotal>();
		for (ReportBeanTotal o : tres) {
			res.put(o.getArea(), o);
			total.setDemandAmount(total.getDemandAmount().add(
					o.getDemandAmount()));
			total.setLoanAmount(total.getLoanAmount().add(o.getLoanAmount()));
			total.setEnterpriseCount(total.getEnterpriseCount()
					+ o.getEnterpriseCount());
			total.setOrgCount(total.getOrgCount() + o.getOrgCount());
		}
		res.put("total", total);
		return res;
	}

	/**
	 * 获取-统计报表-企业榜单
	 * 
	 * @param dateFlag
	 * @return
	 */
	public List<ReportBeanEnterprise> generateReportBeanEnterprise(
			String dateFlag) {
		Date now = new Date();
		Date start = null, end = null;
		Date d;
		try {
			d = format.parse(dateFlag);
			start = getLastMonthStart(d);
			end = getLastMonthEnd(d);
		} catch (ParseException e) {
			log.error("dataFlag parameter error!");
			return null;
		}
		Map<String, BigDecimal> loanAmount = this.investorLoanDao
				.sumAmountByEnterpriseId(null, start, end, NUM_STATISTIC);
		List<ReportBeanEnterprise> res = new ArrayList<ReportBeanEnterprise>();
		for (String key : loanAmount.keySet()) {
			ReportBeanEnterprise o = new ReportBeanEnterprise();
			o.setAmount(loanAmount.get(key));
			o.setEnterpriseId(key);
			o.setTimeId(dateFlag);
			o.setTimePoint(start);
			o.setCreateTime(now);
			res.add(o);
		}
		Collections.sort(res);
		return res;
	}

	/**
	 * 保存-统计报表-企业榜单到 rp_report_enterprise表
	 * 
	 * @param res
	 */
	public void saveReportBeanEnterprise(List<ReportBeanEnterprise> res) {
		this.reportBeanEnterpriseDao.save(res);
	}

	/**
	 * 加工-统计报表-企业榜单中间表 rp_report_enterprise
	 * 
	 * @param dateFlag
	 */
	public void completeReportBeanEnterprise(String dateFlag) {
		List<ReportBeanEnterprise> res = this
				.generateReportBeanEnterprise(dateFlag);
		this.saveReportBeanEnterprise(res);
	}

	/**
	 * 根据企业ID获取企业榜单统计结果
	 * 
	 * @param enterpriseId
	 * @param startFlag
	 * @param endFlag
	 * @return
	 */
	public List<ReportBeanEnterprise> getReportBeanEnterprise(
			String enterpriseId, String startFlag, String endFlag) {
		Date start = null, end = null;
		Date d1, d2;
		try {
			if (StringUtils.isNotEmpty(startFlag)) {
				d1 = format.parse(startFlag);
				start = getLastMonthStart(d1);
			}
			if (StringUtils.isNotEmpty(endFlag)) {
				d2 = format.parse(endFlag);
				end = getLastMonthEnd(d2);
			}
		} catch (ParseException e) {
			log.error("dateFlag parameter error!");
			return null;
		}
		List<ReportBeanEnterprise> res = this.reportBeanEnterpriseDaoImpl
				.findStatisticResult(enterpriseId, start, end, null);
		int i = 1;
		for (ReportBeanEnterprise o : res) {
			o.setRank(i);
			i++;
		}
		return res;
	}

	/**
	 * 获取-统计报表-机构榜单
	 * 
	 * @param dateFlag
	 * @return
	 */
	public List<ReportBeanOrg> generateReportBeanOrg(String dateFlag) {
		Date now = new Date();
		Date start = null, end = null;
		Date d;
		try {
			d = format.parse(dateFlag);
			start = getLastMonthStart(d);
			end = getLastMonthEnd(d);
		} catch (ParseException e) {
			log.error("dataFlag parameter error!");
			return null;
		}
		Map<String, BigDecimal> loanAmount = this.investorLoanDao
				.sumAmountByOrgId(null, start, end, NUM_STATISTIC);
		List<ReportBeanOrg> res = new ArrayList<ReportBeanOrg>();
		for (String key : loanAmount.keySet()) {
			ReportBeanOrg o = new ReportBeanOrg();
			o.setAmount(loanAmount.get(key));
			o.setOrgId(key);
			o.setTimeId(dateFlag);
			o.setTimePoint(start);
			o.setCreateTime(now);
			res.add(o);
		}
		// Collections.sort(res);
		return res;
	}

	/**
	 * 保存-统计报表-机构榜单到 rp_report_org表
	 * 
	 * @param res
	 */
	public void saveReportBeanOrg(List<ReportBeanOrg> res) {
		this.reportBeanOrgDao.save(res);
	}

	/**
	 * 数据落地-统计报表-机构榜单 中间表rp_report_org
	 * 
	 * @param dateFlag
	 */
	public void completeReportBeanOrg(String dateFlag) {
		List<ReportBeanOrg> res = this.generateReportBeanOrg(dateFlag);
		this.saveReportBeanOrg(res);
	}

	/**
	 * 根据企业ID获取机构榜单
	 * 
	 * @param enterpriseId
	 * @param startFlag
	 * @param endFlag
	 * @return
	 */
	public List<ReportBeanOrg> getReportBeanOrg(String orgId, String startFlag,
			String endFlag) {
		Date start = null, end = null;
		Date d1, d2;
		try {
			if (StringUtils.isNotEmpty(startFlag)) {
				d1 = format.parse(startFlag);
				start = getLastMonthStart(d1);
			}
			if (StringUtils.isNotEmpty(endFlag)) {
				d2 = format.parse(endFlag);
				end = getLastMonthEnd(d2);
			}
		} catch (ParseException e) {
			log.error("dateFlag parameter error!");
			return null;
		}
		List<ReportBeanOrg> res = this.reportBeanOrgDaoImpl
				.findStatisticResult(orgId, start, end, null);
		int i = 1;
		for (ReportBeanOrg o : res) {
			o.setRank(i);
			i++;
		}
		return res;
	}

	/**
	 * 获取-统计报表-区域排名
	 * 
	 * @param dateFlag
	 * @return
	 */
	public List<CountReportAreaBean> generateReportBeanArea(String dateFlag) {
		List<CountReportAreaBean> res = null;
		if (!"".equals(dateFlag)) {
			res = this.reportBeanAreaDaoImpl.findStatisticResult(dateFlag);
		}
		return res;
	}

	/**
	 * 获取-统计报表-区域排名 (使用中)
	 * 
	 * @param dateFlag
	 * @return
	 */
	public List<ReportBeanArea> generateReportBeanArea1(String dateFlag) {
		Date now = new Date();
		Date start = null, end = null;
		Date d;
		try {
			d = format.parse(dateFlag);
			// start = getLastMonthStart(d);
			end = getLastMonthEnd(d);
		} catch (ParseException e) {
			log.error("dataFlag parameter error!");
			return null;
		}
		List<ReportBeanArea> res = this.realTimeReportDao
				.findAreaInvestByOrgId(null, null, end, null);
		Double allAmount = this.realTimeReportDao.sumInvestByOrgId(null, null,
				end);
		for (ReportBeanArea rbo : res) {
			rbo.setAllAmount(allAmount);
			rbo.setTimeId(dateFlag);
			rbo.setTimePoint(end);
			rbo.setCreateTime(new Date());
		}
		Collections.sort(res);
		return res;
	}

	/**
	 * 保存-统计报表-区域排名榜单到 rp_report_area_rank表
	 * 
	 * @param o
	 */
	public void saveReportBeanArea(List<ReportBeanArea> o) {
		this.reportBeanAreaDao.save(o);
	}

	/**
	 * 数据落地-统计报表-区域排名中间表 rp_report_area_rank
	 *
	 * @param dateFlag
	 */
	public void completeReportBeanArea(String dateFlag) {
		List<ReportBeanArea> res = generateReportBeanArea1(dateFlag);
		this.saveReportBeanArea(res);
	}

	/**
	 * 获取-统计报表-行业排名
	 * 
	 * @param dateFlag
	 * @return
	 */
	public List<ReportBeanTrade> generateReportBeanTrade(String dateFlag) {
		Date now = new Date();
		Date start = null, end = null;
		Date d;
		try {
			d = format.parse(dateFlag);
			// start = getLastMonthStart(d);
			end = getLastMonthEnd(d);
		} catch (ParseException e) {
			log.error("dataFlag parameter error!");
			return null;
		}
		List<ReportBeanTrade> res = this.realTimeReportDao
				.findTradeInvestByOrgId(null, null, end, null);
		Double allAmount = this.realTimeReportDao.sumInvestByOrgId(null, null,
				end);
		for (ReportBeanTrade rbo : res) {
			rbo.setAllAmount(allAmount);
			rbo.setTimeId(dateFlag);
			rbo.setTimePoint(end);
			rbo.setCreateTime(new Date());
		}
		Collections.sort(res);
		return res;
	}

	/**
	 * 保存-统计报表-行业排名榜单到 rp_report_trade_rank表
	 * 
	 * @param o
	 */
	public void saveReportBeanTrade(List<ReportBeanTrade> o) {
		this.reportBeanTradeDao.save(o);
	}

	/**
	 * 数据落地-统计报表-行业排名中间表 rp_report_trade_rank
	 * 
	 * @param dateFlag
	 */
	public void completeReportBeanTrade(String dateFlag) {
		List<ReportBeanTrade> res = generateReportBeanTrade(dateFlag);
		this.saveReportBeanTrade(res);
	}

	/**
	 * 根据统计时间获取区域榜单
	 * 
	 * @param timeId
	 * @return
	 */
	public List<ReportBeanArea> getReportBeanAreas(String timeId) {
		List<ReportBeanArea> res = this.reportBeanAreaDao.findByTimeId(timeId);
		int i = 1;
		for (ReportBeanArea o : res) {
			o.setRank(i);
			i++;
		}
		return res;
	}

	/**
	 * 查询企业区域排名
	 * 
	 * @return
	 */
	public List<ReportByArea> findReportByArea() {
		return reportByAreaDao.findByTimeId("03");
	}

	/**
	 * 查询企业行业排名
	 * 
	 * @return
	 */
	public List<ReportByIndustry> findReportByIndustry() {
		return reportByIndustryDao.findReportByIndustry("03");
	}

	public List<QueryIndustryFinacingResult> getFinacingResultIndustry(
			String beginTime, String endTime) {
		List<QueryIndustryFinacingResult> FinacingEventResultList = queryIndustryFinacingResultDaoImpl
				.getFinacingEventResult(beginTime, endTime);
		List<QueryIndustryFinacingResult> FinacingDemandResultList = queryIndustryFinacingResultDaoImpl
				.getFinacingDemandResult(beginTime, endTime);
		for (QueryIndustryFinacingResult finacingDemandResult : FinacingDemandResultList) {
			for (QueryIndustryFinacingResult finacingEventResult : FinacingEventResultList) {
				if (finacingDemandResult.getIndustry().equals(finacingEventResult.getIndustry())) {
					finacingDemandResult.setoKAmount(finacingEventResult.getoKAmount());
					finacingDemandResult.setoKCount(finacingEventResult.getoKCount());
				}
			}
		}

		return FinacingDemandResultList;
	}

	public List<QueryAreaFinacingResult> getFinacingResultArea(
			String beginTime, String endTime) {
		List<QueryAreaFinacingResult> FinacingDemandResultList = queryAreaFinacingResultDaoImpl
				.getFinacingDemandResult(beginTime, endTime);
		List<QueryAreaFinacingResult> FinacingEventResultList = queryAreaFinacingResultDaoImpl
				.getFinacingEventResult(beginTime, endTime);
		for (QueryAreaFinacingResult queryEventFinacingResult : FinacingEventResultList) {
			for (QueryAreaFinacingResult queryDemandFinacingResult : FinacingDemandResultList) {
				if (queryEventFinacingResult.getArea().equals(
						queryDemandFinacingResult.getArea())) {
					queryEventFinacingResult.setPublishAmount(queryDemandFinacingResult.getPublishAmount());
					queryEventFinacingResult.setPublishCount(queryDemandFinacingResult.getPublishCount());
				}
			}
		}
		return FinacingEventResultList;
	}

	/**
	 * 根据统计时间获取行业排名
	 * 
	 * @param timeId
	 * @return
	 */
	public List<ReportBeanTrade> getReportBeanTrades(String timeId) {
		List<ReportBeanTrade> res = this.reportBeanTradeDao
				.findByTimeId(timeId);

		int i = 1;
		for (ReportBeanTrade o : res) {
			o.setRank(i);
			i++;
		}
		return res;
	}

	private Date getLastMonthStart(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	private Date getLastMonthEnd(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
		c.set(Calendar.HOUR, c.getActualMaximum(Calendar.HOUR));
		c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
		return c.getTime();
	}

}
