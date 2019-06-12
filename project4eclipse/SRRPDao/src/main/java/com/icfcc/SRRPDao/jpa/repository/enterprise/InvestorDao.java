package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;

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
	@Query("select e from Investor e where e.investorId=?")
	Investor findInverstorById(String id);

	// 查询机构表中的信息所有机构信息EnterpriceInvestor
	@Query("select e from Investor e where  e.stopFlag= ? and e.auditStatus=? ")
	List<Investor> findAllInverstor(String stopFlag, String auditStatus);

	@Query("select u from Investor u where  u.certno=? ")
    Investor findById(String certno);
	
	@Modifying
	@Query("update Investor t set t.stopFlag=?2 where t.id=?1")
	public void updateStopFlag(String id, String stopFlag);
	
	@Modifying
	@Query("update Investor t set t.auditStatus=?2 where t.id=?1")
	public void updateAuditStatus(String id,String auditStatus);
	
	@Query(" SELECT a , case when a.auditStatus = '1' then '90' when a.auditStatus = '4' then '80'  when a.auditStatus = '3' then '70' when a.auditStatus = '6' then '60' when a.auditStatus = '2' then '50' when a.auditStatus = '5' then '40'  else '10' end as sort  FROM Investor a order by sort desc,a.createTime desc" )
	Page<Investor> findAllBySort(Specification<Investor> spec, Pageable pageable);
}
