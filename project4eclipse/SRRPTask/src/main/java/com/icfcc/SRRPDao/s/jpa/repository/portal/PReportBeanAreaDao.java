package com.icfcc.SRRPDao.s.jpa.repository.portal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PReportBeanArea;

@Component
public interface PReportBeanAreaDao
		extends PagingAndSortingRepository<PReportBeanArea, String>, JpaSpecificationExecutor<PReportBeanArea> {

	@Query("select t from PReportBeanArea t where t.timeId=?1 order by t.amount desc")
	public List<PReportBeanArea> findByTimeId(String timeId);

}
