package com.icfcc.SRRPService.inverstorg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorPublishInfoResult;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.InvestorPublishInfoDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.impl.InvestorPublishInfoDaoImpl;

@Service
@Transactional(value = "transactionManager")
public class InvestorPublishInfoService {

	
	@Autowired
	private InvestorPublishInfoDao investorPublishInfoDao;
	
	@Autowired
	private InvestorPublishInfoDaoImpl investorPublishInfoDaoImpl;
	/**
	 * 披露信息查询列表
	* @Title: investorPublishList 
	* @param infotype
	* @param projectName
	* @param startTime
	* @param endTime
	* @return List<InvestorPublishInfo>    
	* @throws
	 */
	public List<InvestorPublishInfoResult> getInvestorPublishList(QueryCondition queryCondition) {

		return (List<InvestorPublishInfoResult>) investorPublishInfoDao.getInvestorPublishList(queryCondition);
	}
	
	/**
	 * 披露信息查询列表
	* @Title: investorPublishList 
	* @param infotype
	* @param projectName
	* @param startTime
	* @param endTime
	* @return List<InvestorPublishInfo>    
	* @throws
	 */
	public List<InvestorPublishInfoResult> getInvestorPublishLists(QueryCondition queryCondition) {

		return (List<InvestorPublishInfoResult>) investorPublishInfoDaoImpl.getInvestorPublishList(queryCondition);
	}
	
	/**
	 * 查询批露信息总条数
	 * @param queryCondition
	 * @return
	 */
	public Object getInvestorPublishListCount(QueryCondition queryCondition) {

		return investorPublishInfoDao.getInvestorPublishListCount(queryCondition);
	}
	/**
	 * 通过publishId查询披露信息
	* @Title: getInvestorPublishInfoById 
	* @param publishId
	* @return InvestorPublishInfo    
	* @throws
	 */
	public InvestorPublishInfoResult getInvestorPublishInfoById(String publishId) {

		return (InvestorPublishInfoResult) investorPublishInfoDao.getInvestorPublishInfoById(publishId);
	}

}
