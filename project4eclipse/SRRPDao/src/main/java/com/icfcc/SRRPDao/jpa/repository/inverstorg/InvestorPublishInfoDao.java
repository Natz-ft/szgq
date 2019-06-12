package com.icfcc.SRRPDao.jpa.repository.inverstorg;

import java.util.List;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorPublishInfoResult;
@Component
public interface InvestorPublishInfoDao {

	List<InvestorPublishInfoResult> getInvestorPublishList(QueryCondition queryCondition);

	InvestorPublishInfoResult getInvestorPublishInfoById(String publishId);
	Object getInvestorPublishListCount(QueryCondition queryCondition);

	
}
