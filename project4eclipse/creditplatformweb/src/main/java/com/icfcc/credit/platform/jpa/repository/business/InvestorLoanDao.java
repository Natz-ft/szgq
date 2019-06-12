package com.icfcc.credit.platform.jpa.repository.business;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.query.InvestorLoan;


@Component
public interface InvestorLoanDao extends PagingAndSortingRepository<InvestorLoan, String>, JpaSpecificationExecutor<InvestorLoan> {

	/**
	 * 
	 * @param eventId
	 * @return
	 */
	@Query("select d from InvestorLoan d where d.eventId=?1 order by d.loanDate desc ")
	public List<InvestorLoan> findByEventId(String eventId);
	
	/***
	 * 
	 * <p>功能描述:根据需求ID获取放款信息</p>
	 * @param infoId
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Query("select d from InvestorLoan d where d.infoId=?1  ")
	public List<InvestorLoan> findLoanInfoByInfoId(String infoId);

	@Query("select d from InvestorLoan d where d.eventId=?1  ")
	public List<InvestorLoan> findLoanInfoByEventId(String eventId);
}
