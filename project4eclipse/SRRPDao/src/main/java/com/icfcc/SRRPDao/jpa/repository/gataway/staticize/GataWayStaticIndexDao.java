package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayIndexStatic;

public interface GataWayStaticIndexDao extends
		PagingAndSortingRepository<GataWayIndexStatic, String>,
		JpaSpecificationExecutor<GataWayIndexStatic> {

	@Query("from GataWayIndexStatic where countDate=? ")
	List<GataWayIndexStatic> findByDate(String countDate);

}
