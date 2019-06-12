package com.icfcc.SRRPService.managedept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBasePending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.HistoryCompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryCompanyScoresResult;
import com.icfcc.SRRPDao.jpa.entity.managedept.QueryCompanyAuditResult;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBasePendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.HistoryCompanyBaseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.CompanyBaseDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.managedept.impl.QueryCompanyAuditResultImpl;

@Service
@Transactional(value = "transactionManager")
public class CompanyManageServise {

	@Autowired
	public CompanyBasePendingDao companyBasePendingDao;

	@Autowired
	public CompanyBaseDaoImpl companyBaseDaoImpl;
	@Autowired
	public HistoryCompanyBaseDao historyCompanyBaseDao;

	@Autowired
	public QueryCompanyAuditResultImpl auditResultImpl;
	
	/**
	 * 多条件查询企业待审核信息
	 * 
	 * @param queryCondition
	 * @return
	 */
	public List<QueryCompanyScoresResult> findCompanyBasePending(
			QueryCondition queryCondition,String userType ) {

		return companyBaseDaoImpl
				.findCompanyBaseByWhereCase(queryCondition,userType);
	}

	/**
	 * 得到待审核信息条数
	 * 
	 * @param queryCondition
	 * @return
	 */
	public Object CountCompanyBasePending(QueryCondition queryCondition,String userType) {
		return companyBaseDaoImpl.CountCompanyBase(queryCondition,userType);
	}

	/**
	 * 根据企业的id查询待审核表中的基本信息
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public CompanyBasePending findCompanyBasePendingByEntsrpriseId(
			String enterpriseId) {

		return companyBasePendingDao.findByEnterpriseId(enterpriseId);
	}

	/**
	 * 向审核历史表中存入审核结果的数据
	 */
	public void addHistoryCompanyBase(HistoryCompanyBase historyCompanyBase) {
		historyCompanyBaseDao.save(historyCompanyBase);
	}

	/**
	 * 根据待审核的id，删除待审核表中的数据
	 */
	public void deleteCompanyBasePendingById(String id) {
		companyBasePendingDao.delete(id);
	}
	public List<QueryCompanyAuditResult> getAuditList(String enterpriseId){
		return auditResultImpl.findCompanyAuditList(enterpriseId);
	}
}
