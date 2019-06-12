package com.icfcc.credit.platform.jpa.repository.business;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.query.Investor;
@Component
public interface InvestorDao extends PagingAndSortingRepository<Investor, String>, JpaSpecificationExecutor<Investor>  {

}
