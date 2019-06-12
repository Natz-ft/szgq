/**
 * 
 */
package com.icfcc.SRRPService.inverstorg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBusinessplan;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfo;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBusinessPlanDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.FinacingInfoDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.impl.FinacingDemandInfoDaoImpl;

/**
 * @author LiWCh
 *
 */
@Service
@Transactional(value = "transactionManager")
public class FinacingDemandService {

	@Autowired
	private FinacingInfoDao finacingInfoDao;
	
	@Autowired
	private FinacingDemandInfoDaoImpl finacingDemandInfoDaoImpl;

	@Autowired
	private CompanyBusinessPlanDao companyBusinessPlanDao;
	
	/**
     * 机构下的基金用户数
     * @param  infoId
     * @return 
     */
	public Object getInvestorUserCount(String orgNo) {
		return finacingDemandInfoDaoImpl.getInvestorUserCount(orgNo);
	};

	/**
	 * 添加商业企划书
	 * 
	 * @param businessplan
	 */
	public void addCompanyBusinessplan(CompanyBusinessplan businessplan) {
		companyBusinessPlanDao.save(businessplan);
	}

	/**
	 * 发布(暂存)需求 添加融资信息方法从页面传递的FinacingInfo对象
	 * 
	 * @param finacingInfo
	 */
	public void saveFinacingDemand(FinacingDemandInfo finacingInfo) {

		finacingInfoDao.save(finacingInfo);
	}
}
