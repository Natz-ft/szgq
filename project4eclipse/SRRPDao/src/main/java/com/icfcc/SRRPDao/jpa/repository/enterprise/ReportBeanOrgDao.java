package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanOrg;

@Component
public interface ReportBeanOrgDao
		extends PagingAndSortingRepository<ReportBeanOrg, String>, JpaSpecificationExecutor<ReportBeanOrg> {
	
	@Query("select t from ReportBeanOrg t where t.timeId=?1 order by t.amount desc")
	public List<ReportBeanOrg> findByTimeId(String timeId);

}
