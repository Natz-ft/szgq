package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.Investor;

@Component
public interface InvestorDao
		extends PagingAndSortingRepository<Investor, String>, JpaSpecificationExecutor<Investor> {

/**
 * 根据机构的id查询机构的基本信息
 * @param id
 * @param stopFlag
 * @param auditStatus
 * @return
 */
	@Query("select e from Investor e where e.investorId=? and e.stopFlag=? and e.auditStatus=? ")
	Investor findInverstorById(String id, String stopFlag, String auditStatus);

	// 查询机构表中的信息所有机构信息EnterpriceInvestor
	@Query("select e from Investor e where  e.stopFlag= ? and e.auditStatus=? ")
	List<Investor> findAllInverstor(String stopFlag, String auditStatus);

	@Modifying
	@Query("update Investor t set t.stopFlag=?2 where t.id=?1")
	public void updateStopFlag(String id, String stopFlag);
	
	@Modifying
	@Query("update Investor t set t.auditStatus=?2 where t.id=?1")
	public void updateAuditStatus(String id,String auditStatus);
	
	@Query("select count(e) from Investor e where e.stopFlag= '1' and e.auditStatus='2'")
	public Long countInvestor();

}
