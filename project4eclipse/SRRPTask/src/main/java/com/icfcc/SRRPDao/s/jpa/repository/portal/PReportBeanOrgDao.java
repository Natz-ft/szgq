package com.icfcc.SRRPDao.s.jpa.repository.portal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PReportBeanOrg;

@Component
public interface PReportBeanOrgDao
		extends PagingAndSortingRepository<PReportBeanOrg, String>, JpaSpecificationExecutor<PReportBeanOrg> {
	
	@Query("select t from PReportBeanOrg t where t.timeId=?1 order by t.amount desc")
	public List<PReportBeanOrg> findByTimeId(String timeId);

}
