package com.icfcc.SRRPService.inverstorg;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfoResult;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfoResultNew;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfoResultSub;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.QueryFinacingDemandResult;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.impl.FinacingDemandInfoDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.impl.UnionFinacingDemandDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(value = "transactionManager")
public class FinacingDemandInfoService {

	@Autowired
	private FinacingDemandInfoDaoImpl finacingDemandInfoDaoImpl;

	@Autowired
	private UnionFinacingDemandDaoImpl finacingDemandDaoImpl;

	/**
	 * 查询所有融资需求信息列表
	 * 
	 * @param finacingDemand
	 * @return List<FinacingDemandInfoResult>
	 */
	public List<FinacingDemandInfoResultNew> getFinacingDemandInfoList(QueryCondition queryCondition) {

		return (List<FinacingDemandInfoResultNew>) finacingDemandInfoDaoImpl.getFinacingDemandInfoList(queryCondition);
	}

	/**
	 * 查询所有公开的融资需求信息列表
	 * 
	 * @param finacingDemand
	 * @return List<FinacingDemandInfoResult>
	 */
	public List<FinacingDemandInfoResult> getOpenFinacingDemandInfos(QueryCondition queryCondition) {
		return (List<FinacingDemandInfoResult>) finacingDemandInfoDaoImpl.getOpenFinacingDemandInfos(queryCondition);
	}

	public List<FinacingDemandInfoResultSub> getOpenFinacingDemandInfosSub(QueryCondition queryCondition) {
		return (List<FinacingDemandInfoResultSub>) finacingDemandInfoDaoImpl.getOpenFinacingDemandInfosSub(queryCondition);
	}

	/**
	 * 查询融资信息的总的条数
	 * 
	 * @param queryCondition
	 * @return
	 */
	public Object getFinacingDemandInfoCount(QueryCondition queryCondition) {
		return finacingDemandInfoDaoImpl.getFinacingDemandInfoCount(queryCondition);
	}

	/**
	 * 
	 * 获取公开的融资信息的总条数
	 * 
	 * @Title: getOpenFinacingDemandInfoCount
	 * @param queryCondition
	 * @return Object 返回类型
	 */
	public Object getOpenFinacingDemandInfoCount(QueryCondition queryCondition) {
		return finacingDemandInfoDaoImpl.getOpenFinacingDemandInfoCount(queryCondition);
	}

	public Object getOpenFinacingDemandInfoCountSub(QueryCondition queryCondition) {
		return finacingDemandInfoDaoImpl.getOpenFinacingDemandInfoCountSub(queryCondition);
	}

	/**
	 * 根据企业的id以及多种条件查询需求信息
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public List<QueryFinacingDemandResult> findFinacingDemandListById(QueryCondition queryCondition,
			String enterpriseId) {
		return finacingDemandDaoImpl.findFinacingDemandListById(queryCondition, enterpriseId);
	}

	/**
	 * 企业菜单/我的需求/需求列表中需求的总条数
	 * 
	 * @param queryCondition
	 * @return
	 */
	public Object getCount(QueryCondition queryCondition, String enterpriseId) {
		return finacingDemandDaoImpl.getFinacingDemandInfoCount(queryCondition, enterpriseId);
	}

}
