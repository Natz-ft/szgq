package com.icfcc.SRRPDao.jpa.repository.enterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.HistoryCompanyBase;

@Component
public interface HistoryCompanyBaseDao extends
		PagingAndSortingRepository<HistoryCompanyBase, String>,
		JpaSpecificationExecutor<HistoryCompanyBase> {

}
