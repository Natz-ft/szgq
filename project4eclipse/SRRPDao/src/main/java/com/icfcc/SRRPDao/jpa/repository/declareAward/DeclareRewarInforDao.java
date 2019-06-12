package com.icfcc.SRRPDao.jpa.repository.declareAward;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarInfor;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievement;

@Component
public interface DeclareRewarInforDao extends PagingAndSortingRepository<DeclareRewarInfor, String>,
JpaSpecificationExecutor<DeclareRewarInfor> {
   

	@Query("select ima from DeclareRewarInfor ima where   ima.declareId=?")
	DeclareRewarInfor findDeclareBydeclareId(String declareId);
	
	@Query("select ima from DeclareRewarInfor ima where   ima.declareId=? and  declareStatus !='02' ")
	DeclareRewarInfor findDeclareBydeclareIdNoCancle(String declareId);
}
