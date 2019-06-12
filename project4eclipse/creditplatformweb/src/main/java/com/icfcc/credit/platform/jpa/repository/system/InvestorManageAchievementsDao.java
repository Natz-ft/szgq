package com.icfcc.credit.platform.jpa.repository.system;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.InvestorManageAchievement;
/**
 * 
* @ClassName: InvestorManageAchievementsDao
* @Description: TODO(基金用户信息的查询接口)
* @author hugt
* @date 2017年9月14日 下午7:23:41
*
 */
public interface InvestorManageAchievementsDao extends PagingAndSortingRepository<InvestorManageAchievement, String>
,JpaSpecificationExecutor<InvestorManageAchievement> 
{
	
	@Query("select u from InvestorManageAchievement u where u.fundName=?1 and u.investId=?2")
	InvestorManageAchievement findBySubacName(String subacName,String orgId);
}
