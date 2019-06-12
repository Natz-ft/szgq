package com.icfcc.SRRPDao.jpa.repository.inverstorg;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievement;

@Component
public interface InvestorAchievementDao
		extends PagingAndSortingRepository<InvestorAchievement, String>, JpaSpecificationExecutor<InvestorAchievement> {

	@Query("select ia from InvestorAchievement ia where ia.investId=?")
	List<InvestorAchievement> findAchievementById(String investorId);
	
	@Query("select ia from InvestorAchievement ia where ia.investId=?")
	List<InvestorAchievement> findAchievementsById(String investorId);
	@Query("select ia from InvestorAchievement ia where ia.investmentFunds=? and ia.investId=?")
	List<InvestorAchievement> findAchievementsByinvestmentFunds(String investmentFunds,String investorId);
	@Modifying
	@Query("delete from InvestorAchievement s where s.investId=?1")
	void deleteById(String investorId);

}
