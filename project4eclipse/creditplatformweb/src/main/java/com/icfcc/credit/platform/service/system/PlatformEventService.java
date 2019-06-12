package com.icfcc.credit.platform.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.query.CompanyBaseSupplement;
import com.icfcc.credit.platform.jpa.entity.query.Investor;
import com.icfcc.credit.platform.jpa.entity.query.InvestorLoan;
import com.icfcc.credit.platform.jpa.repository.business.CompanyBaseSupplementDao;
import com.icfcc.credit.platform.jpa.repository.business.InvestorDao;
import com.icfcc.credit.platform.jpa.repository.business.InvestorLoanDao;


@Service
@Transactional(value = "transactionManager")
public class PlatformEventService {

	@Autowired 
	private CompanyBaseSupplementDao baseSupplementDao;
	@Autowired 
	private  InvestorDao investorDao;
	
	@Autowired 
	private  InvestorLoanDao loanDao;
	
	public CompanyBaseSupplement findCompanyBaseSupplement(String enterpriseId) {
		
		return baseSupplementDao.findById(enterpriseId);
	}

	public Investor findByInvestorId(String investorgId) {
		// TODO Auto-generated method stub
		return investorDao.findOne(investorgId);
	}

	public List<InvestorLoan> findByEventId(String eventId) {
		// TODO Auto-generated method stub
		return loanDao.findLoanInfoByEventId(eventId);
	}
	
	
}
