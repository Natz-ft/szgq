package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanEnterprise;

@Component
public interface ReportBeanEnterpriseDao extends PagingAndSortingRepository<ReportBeanEnterprise, String>,
		JpaSpecificationExecutor<ReportBeanEnterprise> {

	@Query("select t from ReportBeanEnterprise t where t.timeId=?1 order by t.amount desc")
	public List<ReportBeanEnterprise> findByTimeId(String timeId);

}
