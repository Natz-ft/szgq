package com.icfcc.SRRPService.inverstorg;

import java.util.Date;






import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBusinessplan;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEvent;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingRecord;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfo;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBusinessPlanDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.FinacingEventDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.FinacingRecordDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.FinacingInfoDao;

/**
 * 详情按钮触发查询事件
* @ClassName: DemandInfoService
* @author daiyx
* @date 2017年9月20日 下午4:22:25
*
 */
@Service
@Transactional(value = "transactionManager")
public class DemandInfoService {

    @Autowired
    private FinacingInfoDao finacingInfoDao;
    
    @Autowired
    private FinacingEventDao finacingEventDao;
    
    @Autowired
    private CompanyBusinessPlanDao companyBusinessPlanDao;
    
    @Autowired
    private InvestorDao investorDao;
    
    @Autowired
    private FinacingRecordDao finacingRecordDao;
    
    
    /**
     * 根据需求infoId查询融资信息
    * @Title: getFinacingInfo 
    * @param infoId
    * @return FinacingInfoDTO   
    * @throws
     */
    public FinacingDemandInfo getFinacingInfo(String infoId) {
    	 
    	 return finacingInfoDao.findByInfoId(infoId);
    }
    
    
    /**
     * 根据需求ID与企业ID查询事件表  获取一个最新的需求状态
    * @Title: getMaxStatus 
    * @param infoId
    * @param investorId
    * @author Daiyx
    * @return String    返回类型 
     */
    public String getMaxStatus(String infoId , String investorId){
    	
    	return finacingEventDao.getMaxStatus(infoId,investorId);
    }
    
    
    /**
     * 根据需求infoId获取商业计划书信息
    * @Title: getCompanyBusinessPlan 
    * @param  infoId
    * @return CompanyBusinessPlan  
    * @throws
     */
    public CompanyBusinessplan getCompanyBusinessPlan(String infoId) {
    	 
    	return companyBusinessPlanDao.findById(infoId);	
    }
	
    /**
     * 根据查询出的投资机构investorgId查询投资机构信息
    * @Title: getInvestor 
    * @param @param investorgId
    * @return InvestorDTO   
    * @throws
     */
    public Investor getInvestor(String investorgId) {

    	return investorDao.findInverstorById(investorgId);
    }
    
    /**
     * 通过企业enterpriseId与融资需求infoId查询投资机构investorId与融资需求阶段
    * @Title: getFinacingEvent 
    * @Description: TODO 
    * @param infoId
    * @param enterpriseId
    * @return FinacingEventDTO    
    * @throws
    */
    public FinacingEvent getFinacingEventByIds(String infoId, String enterpriseId, String investorId,String operuser) {
    	
    	return finacingEventDao.findFinacingEventByIds(infoId,enterpriseId,operuser);
	}

    public String getScheduleById(String eventId){
    	return finacingEventDao.getScheduleById(eventId);
    }
    public FinacingEvent getFinacingEvent(String infoId, String enterpriseId) {
    	
    	return finacingEventDao.findInvestorByIds(infoId,enterpriseId);
	}
    
    public void saveRecord(FinacingRecord record) {
    	try {
    		this.finacingRecordDao.save(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void saveRecord(String infoId, String investOrgId, String opertype, String opercontent, String operuser) {
		FinacingRecord finacingRecord = new FinacingRecord();
		finacingRecord.setInfoId(infoId);
		finacingRecord.setInvestorgid(investOrgId);
		finacingRecord.setOpertype(opertype);
		finacingRecord.setOpercontent(opercontent);
		finacingRecord.setOperuser(operuser);
		finacingRecord.setOperdate(new Date());
		this.finacingRecordDao.save(finacingRecord);
	}
	/**
	 * 
	 * <p>功能描述:根据三个条件获取融资记录</p>
	 * @param userName
	 * @param status
	 * @param inverstorId
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public List<FinacingRecord> findFinacingRecord(String userName,String status,String inverstorId,String infoId){
		return this.finacingRecordDao.findFinacingRecord(userName, status, inverstorId,infoId);
	}

}
