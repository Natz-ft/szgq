package com.icfcc.SRRPService.enterprise;

/**
 * 放款信息的service
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryInvestorLoanResult;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.InvestorLoanDaoImpl;

@Service
@Transactional(value = "transactionManager")
public class InvestorLoanService {
	@Autowired
	private InvestorLoanDaoImpl investorLoanDao;

	/**
	 * 根据事件的id查询机构融资的放款信息
	 * 
	 * @param eventId
	 * @return
	 */
	public List<QueryInvestorLoanResult> listUnionInfoLoan(String infoId) {
		return investorLoanDao.findUnionInfoLoans(infoId);
	}
}
