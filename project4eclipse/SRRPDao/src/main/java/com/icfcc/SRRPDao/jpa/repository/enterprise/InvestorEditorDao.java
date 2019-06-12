package com.icfcc.SRRPDao.jpa.repository.enterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditPending;
@Component
public interface InvestorEditorDao extends
PagingAndSortingRepository<InvestorAuditPending, String>,
JpaSpecificationExecutor<InvestorAuditPending>{
    
	/**
	 * 投资机构中主要信息修改需审核，将该信息添加待审核表
	 * @param requestInvestor
	 * @return InvestorAuditPending
	 */
	@SuppressWarnings("unchecked")
	InvestorAuditPending save(InvestorAuditPending editorInvestor);
   


	
	
	
}
