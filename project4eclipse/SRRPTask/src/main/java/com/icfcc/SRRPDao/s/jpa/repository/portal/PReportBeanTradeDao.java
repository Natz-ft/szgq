package com.icfcc.SRRPDao.s.jpa.repository.portal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PReportBeanTrade;

@Component
public interface PReportBeanTradeDao
		extends PagingAndSortingRepository<PReportBeanTrade, String>, JpaSpecificationExecutor<PReportBeanTrade> {

	@Query("select t from PReportBeanTrade t where t.timeId=?1 order by t.amount desc")
	public List<PReportBeanTrade> findByTimeId(String timeId);

}
