/**
 * 
 */
package com.icfcc.SRRPDao.jpa.repository.enterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditPending;

/**
 * @author lijj
 *
 */
@Component
public interface InvestorAuditPendingDao extends PagingAndSortingRepository<InvestorAuditPending, String>,
		JpaSpecificationExecutor<InvestorAuditPending> {
	
	


}
