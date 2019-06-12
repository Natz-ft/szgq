package com.icfcc.SRRPService.inverstorg;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditPending;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorEditorDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.impl.InvestorEditorDaoImpl;

@Service
@Transactional(value = "transactionManager")
public class InvestorEditorService {

	@Autowired
	private InvestorEditorDaoImpl investorEditorDaoImpl;

	@Autowired
	private InvestorEditorDao investorEditorDao;
	
	@Autowired
	private InvestorDao investorDao;
	
	public Investor update(Investor requestInvestor) {

		return  investorDao.save(requestInvestor);
	}
	/**
	 * 根据获取的投资机构附加信息及投资机构investorId 审核状态auditStatus，修改数据库对应信息
	 * 
	 * @param investor
	 * @param investorId
	 * @param auditStatus
	 * @return
	 */
	public int updateInvestor(Investor requestInvestor) {

		return  investorEditorDaoImpl.updateInvestor(requestInvestor);
	}
    
	/**
	 * 根据获取的投资机构附加信息及投资机构investorId 审核状态auditStatus，修改数据库对应信息
	 * 
	 * @param investor
	 * @param investorId
	 * @param auditStatus
	 * @return
	 */
	public int updateInvestorStatus(Investor requestInvestor) {

		return  investorEditorDaoImpl.updateInvestorStatus(requestInvestor);
	}
    
	/**
     * 投资机构中主要信息修改需审核，将该信息添加待审核表
     * @param investorEditor
     */
	public InvestorAuditPending addInvestorAuditPending(InvestorAuditPending editorInvestor) {

		return investorEditorDao.save(editorInvestor);

	}
	/**
     * 投资机构中主要信息修改需审核，将该信息添加待审核表
     * @param investorEditor
     */
	public InvestorAuditPending selectInvestorAuditPending(String  investorId) {

		return investorEditorDao.findOne(investorId);

	}



}
