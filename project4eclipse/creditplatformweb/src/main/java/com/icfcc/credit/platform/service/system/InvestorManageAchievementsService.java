package com.icfcc.credit.platform.service.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.system.InvestorManageAchievement;
import com.icfcc.credit.platform.jpa.repository.system.InvestorManageAchievementsDao;

@Service
@Transactional(value = "transactionManager")
public class InvestorManageAchievementsService {
	  
	@Autowired 
    private InvestorManageAchievementsDao investorManageAchievementsDao;
	 
	/**
	 * 根据基金名称 查询是否有对应的用户
	 * 
	 * @param inputValue
	 * @return
	 */
	public InvestorManageAchievement findUserBySubacName(String inputValue, String orgId) {

		return investorManageAchievementsDao.findBySubacName(inputValue, orgId);
	}
}
