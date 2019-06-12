package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.ReportBeanArea;

@Component
public interface ReportBeanAreaDao
		extends PagingAndSortingRepository<ReportBeanArea, String>, JpaSpecificationExecutor<ReportBeanArea> {

	@Query("select t from ReportBeanArea t where t.timeId=?1 order by t.amount desc")
	public List<ReportBeanArea> findByTimeId(String timeId);

}
