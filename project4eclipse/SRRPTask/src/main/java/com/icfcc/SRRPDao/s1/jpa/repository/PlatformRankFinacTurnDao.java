package com.icfcc.SRRPDao.s1.jpa.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankFinacingTurn;

@Component
public interface PlatformRankFinacTurnDao extends
		PagingAndSortingRepository<PlatformRankFinacingTurn, String>,
		JpaSpecificationExecutor<PlatformRankFinacingTurn> {

}
