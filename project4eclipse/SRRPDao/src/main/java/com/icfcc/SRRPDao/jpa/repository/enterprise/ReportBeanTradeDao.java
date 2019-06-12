package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTrade;

@Component
public interface ReportBeanTradeDao
		extends PagingAndSortingRepository<ReportBeanTrade, String>, JpaSpecificationExecutor<ReportBeanTrade> {

	@Query("select t from ReportBeanTrade t where t.timeId=?1 order by t.amount desc")
	public List<ReportBeanTrade> findByTimeId(String timeId);

}
