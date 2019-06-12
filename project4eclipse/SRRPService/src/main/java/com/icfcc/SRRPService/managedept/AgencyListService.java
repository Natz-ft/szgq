package com.icfcc.SRRPService.managedept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.managedept.AgencyListResult;
import com.icfcc.SRRPDao.jpa.repository.managedept.impl.AgencyListDaoImpl;

@Service
@Transactional(value = "transactionManager")
public class AgencyListService {

	@Autowired
	private AgencyListDaoImpl agencyListDaoImpl;

	
	/**
	 * 根据起止时间融资轮次统计机构榜单
	* @Title: getInvestorStatistics 
	* @param queryCondition
	* @return List<AgencyListResult>    返回类型 
	 */
	public List<AgencyListResult> getInvestorStatistics(QueryCondition queryCondition) {
		List<AgencyListResult> agencyListResult= (List<AgencyListResult>) agencyListDaoImpl.getInvestorStatistics(queryCondition);
		if(agencyListResult!=null){
			if(agencyListResult.size()>0){
				for(AgencyListResult agency:agencyListResult){
					String count=agencyListDaoImpl.sendInforDemandCount(agency.getInvestorgId(),queryCondition).toString();
					if(count!=null && !"".equals(count )){
						agency.setCountSnedInfoId(Integer.parseInt(count));
					}else{
						agency.setCountSnedInfoId(0);
					}
					
				}
			}
		}
		return agencyListResult;
	}

}
