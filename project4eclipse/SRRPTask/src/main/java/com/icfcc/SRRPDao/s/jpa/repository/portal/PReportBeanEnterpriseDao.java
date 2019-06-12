package com.icfcc.SRRPDao.s.jpa.repository.portal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PReportBeanEnterprise;

@Component
public interface PReportBeanEnterpriseDao extends PagingAndSortingRepository<PReportBeanEnterprise, String>,
		JpaSpecificationExecutor<PReportBeanEnterprise> {

	@Query("select t from PReportBeanEnterprise t where t.timeId=?1 order by t.amount desc")
	public List<PReportBeanEnterprise> findByTimeId(String timeId);
	
}
