package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.InvestorLoan;

@Component
public interface InvestorLoanDao
		extends PagingAndSortingRepository<InvestorLoan, String>, JpaSpecificationExecutor<InvestorLoan> {

	/**
	 * 
	 * @param eventId
	 * @return
	 */
	@Query("select d from InvestorLoan d where d.eventId=?1 order by d.loanDate desc ")
	public List<InvestorLoan> findByEventId(String eventId);

	@Query("select sum(d.amount) from InvestorLoan d ")
	public Double sumLoan();

	@Query("select d from InvestorLoan d where d.orgId=?1 order by d.loanDate desc ")
	public List<InvestorLoan> findByInvestorId(String investorId);
	@Query("select distinct d.enterpriseId from InvestorLoan d where d.orgId=?1 order by d.loanDate desc ")
	public List<String> findByInvestorDisId(String investorId);

}
