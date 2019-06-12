package com.icfcc.SRRPDao.jpa.repository.inverstorg;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievementPending;

public interface InvestorAchievementPendingDao extends
		PagingAndSortingRepository<InvestorAchievementPending, String>,
		JpaSpecificationExecutor<InvestorAchievementPending> {
	@Query("select ia from InvestorAchievementPending ia where ia.investId=?")
	List<InvestorAchievementPending> findAchievementById(String investorId);
	
	@Query("select ia from InvestorAchievementPending ia where ia.investId=?")
	List<InvestorAchievementPending> findAchievementsById(String investorId);
	@Query("select ia from InvestorAchievementPending ia where ia.investmentFunds=?")
	List<InvestorAchievementPending> findAchievementsByinvestmentFunds(String investmentFunds);
	@Modifying
	@Query("delete from InvestorAchievementPending s where s.investId=?1")
	void deleteById(String investorId);
}
