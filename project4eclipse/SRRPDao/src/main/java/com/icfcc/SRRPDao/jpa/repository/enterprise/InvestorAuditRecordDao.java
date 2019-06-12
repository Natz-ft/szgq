/**
 * 
 */
package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditRecord;

/**
 * @author lijj
 *
 */
@Component
public interface InvestorAuditRecordDao extends PagingAndSortingRepository<InvestorAuditRecord, String>,
		JpaSpecificationExecutor<InvestorAuditRecord> {

	@Query("select t from InvestorAuditRecord t where t.investorId=?1 order by t.auditTime desc")
	public List<InvestorAuditRecord> findByInvestorId(String investorId);

}
