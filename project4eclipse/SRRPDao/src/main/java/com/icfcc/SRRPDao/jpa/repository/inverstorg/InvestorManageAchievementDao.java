package com.icfcc.SRRPDao.jpa.repository.inverstorg;

import java.util.List;



import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievement;

@Component
public interface InvestorManageAchievementDao extends PagingAndSortingRepository<InvestorManageAchievement, String>,
JpaSpecificationExecutor<InvestorManageAchievement> {
   

	@Query("select ima from InvestorManageAchievement ima where  ima.investId=?")
	List<InvestorManageAchievement> findAllManageAchievement(String investorId);
	@Query("select ima from InvestorManageAchievement ima where  ima.fundName=? and ima.investId=?")
	InvestorManageAchievement findAllManageAchievementByfundName(String fundName,String investorId);
	@Modifying
	@Query("delete from InvestorManageAchievement s where s.investId=?1")
	void deleteById(String investorId);
}
