package com.icfcc.credit.platform.jpa.repository.business;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.query.CompanyBase;


@Component
public interface CompanyBaseDao extends
		PagingAndSortingRepository<CompanyBase, String>,
		JpaSpecificationExecutor<CompanyBase> {


	@Query("select u from CompanyBase u where  u.code=? ")
	public CompanyBase queryByCertno(String code);
	
	@Query("select u.enterpriseId from CompanyBase u where  u.name like ? ")
	public List<String> queryIdsByName(String  name );
	
	@Query("select u.name from CompanyBase u where  u.enterpriseId = ? ")
	public  String getNameById(String enterPriseId);

}
