package com.icfcc.SRRPDao.jpa.repository.inverstorg;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievementPending;

public interface InvestorManageAchievementPendingDao extends
		PagingAndSortingRepository<InvestorManageAchievementPending, String>,
		JpaSpecificationExecutor<InvestorManageAchievementPending> {
	
	@Query("select ima from InvestorManageAchievementPending ima where  ima.investId=?")
	List<InvestorManageAchievementPending> findAllManageAchievement(String investorId);
	@Query("select ima from InvestorManageAchievementPending ima where  ima.fundName=?")
	List<InvestorManageAchievementPending> findAllManageAchievementByfundName(String fundName);
	@Modifying
	@Query("delete from InvestorManageAchievementPending s where s.investId=?1")
	void deleteById(String investorId);

}
