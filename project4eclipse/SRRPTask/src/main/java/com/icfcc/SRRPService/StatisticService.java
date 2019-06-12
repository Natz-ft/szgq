/**
 * 
 */
package com.icfcc.SRRPService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.Statistic;
import com.icfcc.SRRPDao.s.jpa.repository.portal.StatisticDao;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyBaseDao;
import com.icfcc.SRRPDao.s1.jpa.repository.FinacingInfoDao;
import com.icfcc.SRRPDao.s1.jpa.repository.InvestorDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * @author lijj （不需要）
 *
 */
@Service
public class StatisticService   {

	@Autowired
	private CompanyBaseDao companyBaseDao;
	@Autowired
	private FinacingInfoDao finacingInfoDao;
	@Autowired
	private InvestorDao investorDao;
	@Autowired
	private StatisticDao statisticDao;

	// 从业务库获取业务统计数据
	@Transactional(value = "transactionManager1")
	public Statistic generateStatistic() throws Exception {
		Statistic s = new Statistic();
		Long companyCount = this.companyBaseDao.countCompany();// 获取平台企业数
		Long orgCount = this.investorDao.countInvestor();// 获取平台机构数(统计状态为启用并审核通过的机构)
		Long infoCount = this.finacingInfoDao.countInfo();// 获取平台融资需求数
		Double financingAmount = this.finacingInfoDao.sumInfo();// 获取平台融资总量
		// Double successAmount = this.investorLoanDao.sumLoan();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
		s.setCountDate(sdf.format(new Date()));
		s.setEnterpriseCount(companyCount);
		s.setFinancingAmount(financingAmount);
		s.setFinancingCount(infoCount);
		s.setOrgCount(orgCount);
		// s.setSuccessAmount(successAmount);
		return s;
	}

	// 更新门户统计表
	@Transactional(value = "transactionManager")
	public Statistic saveStatistic(Statistic s) {
		return this.statisticDao.save(s);
	}

	// @Scheduled
	/**
	 * 计算门户首页统计数据
	 */
	public void completeStatistic() throws Exception {
		Statistic s = this.generateStatistic();
		this.saveStatistic(s);
	}

	/**
	 * 获得最新一条统计结果
	 * 
	 * @return
	 */
	public Statistic getLatest() {
		Sort sort = new Sort(Direction.DESC, "countDate");
		Pageable page = new PageRequest(0, 1, sort);
		Page<Statistic> res = this.statisticDao.findAll(page);
		if (res.getNumber() > 0) {
			return res.getContent().get(0);
		} else {
			return null;
		}
	}

}
