package com.icfcc.SRRPService.declareAward;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 放款信息的service
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.repository.declareAward.DeclareRewarInforDao;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarInfor;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarReport;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarReportRecord;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarSearshBean;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEvent;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorPublishInfoResult;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.InvestorSubaccount;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPDao.jpa.repository.declareAward.DeclareAwardResult;
import com.icfcc.SRRPDao.jpa.repository.declareAward.DeclareRewarDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.declareAward.DeclareRewarReportDao;
import com.icfcc.SRRPDao.jpa.repository.declareAward.DeclareRewarReportRecordrDao;
import com.icfcc.SRRPService.PlatformSystem.InvestorSubaccountService;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.enterprise.FinacingEventService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

@Service
@Transactional(value = "transactionManager")
public class DeclareRewardService {
	@Autowired
	private DeclareRewarReportDao rewarDeclareReportDao;
	@Autowired
	private DeclareRewarInforDao declareRewarDao;
	@Autowired
	private DeclareRewarDaoImpl rewarDeclareDaoImpl;
	@Autowired
	private DeclareRewarReportRecordrDao declareRewarReportRecordrDao;
	@Autowired
	private PlatformUserService userService;
	@Autowired
	private FinacingEventService finacingEventService;
	@Autowired      
	private InvestorSubaccountService investorSubaccountService;
	public DeclareRewarInfor  saveDeclareRewarInfor(DeclareRewarInfor declare){
		return declareRewarDao.save(declare);
		
	}
	public DeclareRewarReport  saveDeclareRewarInfor(DeclareRewarReport report){
		return rewarDeclareReportDao.save(report);
	}
	public boolean  FindDeclareRewarInforIsExit(DeclareRewarInfor declare){
		int count=new Integer(String.valueOf(rewarDeclareDaoImpl.FindDeclareRewarInforIsExitve(declare)));
		if(count>0){
			return false;
		}
		return true;
	}
	public List<DeclareRewarInfor> queryDeclareRewarInfor(DeclareRewarSearshBean reward){
		    List<DeclareRewarInfor>  declareList=new ArrayList<DeclareRewarInfor>();
		 try {
			 List<DeclareAwardResult> list=rewarDeclareDaoImpl.queryDeclareRewarInfor(reward,"1");
		 for(DeclareAwardResult result:list){
			 DeclareRewarInfor declare =new DeclareRewarInfor();
			 String uuid = UUID.randomUUID().toString().replace("-", "");
			 declare.setDeclareId(uuid);
			 declare.setInvestorId(result.getInvestorgId());
			 declare.setDeclareName(reward.getDeclareName());//申报单位
			 declare.setCertno(reward.getCertno());//机构代码
			 declare.setAreaProvince(reward.getAreaProvince());//机构注册地-省
			 declare.setAreaCity(reward.getAreaCity());//机构注册地-省
			 declare.setAreaCounty(reward.getAreaCounty());//机构注册地-省
			 declare.setRelName(reward.getRelName());//联系人
			 declare.setRelPhone(reward.getRelPhone());//联系电话
			 declare.setBankDeposit(reward.getBankDeposit());//开户行
			 declare.setBankAccount(reward.getBankAccount());//银行账号
			 declare.setDeclareBeginTime(reward.getDeclare_begin_time());//申报期间--开始时间
			 declare.setDeclareEndTime(reward.getDeclare_end_time());//申报期间--结束时间
			 declare.setCompanyAddress(result.getCompanyAddress());//
			 //被投企业工商注册地
			 reward.setRearea(result.getCompanyAddress());
			 List<DeclareAwardResult> reportList=rewarDeclareDaoImpl.queryDeclareRewarInfor(reward,"2");
             //封装投资记录
			 List<DeclareRewarReport>  reportResult=new ArrayList<DeclareRewarReport>();
			 int i=1;
			 for(DeclareAwardResult report:reportList){
				 FinacingEvent event= finacingEventService.findFinacingEventById(report.getFinacingEventId());
				 PlatformUser user=userService.findByUserID(event.getFoundId());//用户信息
				 if(null != user) {	//存在部分机构删除基金用户的问题
					 DeclareRewarReport rewarReport=new DeclareRewarReport();
					 rewarReport.setSerialNumber(i+"");//序号
					 rewarReport.setEventId(report.getFinacingEventId());//事件ID
					 //判断是基金用户或机构用户
					 if(event.getInvestLevel().equals("0")){//机构
//					 Investor invest= investorService.findInverstorById(user.getOrg());
//					 rewarReport.setSubacName(invest.getName());//管理机构名称
//					 rewarReport.setAmacRecord(invest.getAmacRecord());//基金编号
//					 rewarReport.setAchievementAddress("姑苏");//基金工商注册地
//					 rewarReport.setAchievementCode("20180824-4");//基金机构代码
					 }else{
						 InvestorSubaccount subaccount =investorSubaccountService.findById(user.getId());//基金账户信息
						 rewarReport.setSubacName(subaccount.getSubacName());//基金名称
						 rewarReport.setAmacRecord(subaccount.getAmacRecord());//基金编号
						 if(subaccount.getAreaProvince()!=null&&subaccount.getAreaCity()!=null&&subaccount.getAreaCounty()!=null){
							 String	investorAreaName=RedisGetValue.getAreaValueByCode(SRRPConstant.AREA_Province, subaccount.getAreaProvince())+RedisGetValue.getAreaValueByCode(SRRPConstant.AREA_City, subaccount.getAreaCity())+RedisGetValue.getAreaValueByCode(SRRPConstant.AREA_County, subaccount.getAreaCounty());
							 rewarReport.setAchievementAddress(investorAreaName);//基金工商注册地
						 }
						 rewarReport.setAchievementCode(subaccount.getCertno());//基金机构代码
						 
					 }
					 rewarReport.setInvestedEnterprise(report.getName());//被投企业名称
					 rewarReport.setInvestedEnterpriseCode(report.getCode());//被投企业名称
					 rewarReport.setInvestmentAmount(report.getAmount());//投资金额
					 rewarReport.setIaCurrency(report.getCurrency());//投资金额币种
					 reportResult.add(rewarReport);
					 i++;
				 }
             }
			 List<DeclareRewarReportRecord>  reportRecord =new ArrayList<DeclareRewarReportRecord>();
			 List<DeclareAwardResult> redordList=rewarDeclareDaoImpl.queryDeclareRewarInfor(reward,"3");
			 for(DeclareAwardResult result1:redordList){
				 DeclareRewarReportRecord deredord =new DeclareRewarReportRecord();
//				 deredord.setDeclareId(uuid);
				 deredord.setEventId(result1.getFinacingEventId());
				 deredord.setIaCurrency(result1.getCurrency());
				 deredord.setInvestmentAmount(result1.getAmount());
				 deredord.setLoandate(result1.getLoandate());
				 deredord.setLoanId(result1.getLoanId());
				 deredord.setStatus("00");
				 reportRecord.add(deredord);
			 }
			 declare.setDeclareRewarReportRecord(reportRecord);
			 declare.setDeclareRewarReport(reportResult);
			 declareList.add(declare);
		 }
		 } catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return declareList;
		}
		 return declareList;
	}
	
	
	/**
	* 我的申报查询列表
	* @Title: getDeclareLists 
	* @param declareRewardSearsh
	* @return List<DeclareRewarInfor>    
	* @throws
	 */
	public List<DeclareRewarInfor> getDeclareLists(DeclareRewarSearshBean declareRewardSearsh) {

		return (List<DeclareRewarInfor>) rewarDeclareDaoImpl.getDeclareLists(declareRewardSearsh);
	}
	/**
	* 我的申报查询列表总数
	* @Title: getDeclareCount 
	* @param declareRewardSearsh
	* @return Object   
	* @throws
	 */
	public Object getDeclareCount(DeclareRewarSearshBean declareRewardSearsh) {

		return rewarDeclareDaoImpl.getDeclareCount(declareRewardSearsh);
	}
	/**
	* 申报记录详情查询
	* @Title: investorPublishList 
	* @param declareId
	* @return DeclareRewarInfor  
	* @throws
	 */
	public DeclareRewarInfor getDeclareInfoById(String  declareId) {
		return declareRewarDao.findDeclareBydeclareId(declareId);
	}
	
	public DeclareRewarInfor findDeclareBydeclareIdNoCancle(String  declareId) {
		return declareRewarDao.findDeclareBydeclareIdNoCancle(declareId);
	}
	
	/**
	* 申报查询投资记录详情
	* @Title: investorPublishList 
	* @param declareId
	* @return List<DeclareRewarReport>  
	* @throws
	*/
	public List<DeclareRewarReport> findByDeclarId(String declareId) {
		
		return rewarDeclareReportDao.findByDeclarId(declareId);
	}
	/**
	* 修改申报记录明细状态
	* @Title: updateRecord 
	* @param declareId
	* @return List<DeclareRewarReport>  
	* @throws
	*/
	public void  updateRecord(String status,String declareId) {
		
		 declareRewarReportRecordrDao.updateRecord(status,declareId);
	}
	/**
	* 申报查询查放款记录是否重复申报
	* @Title: findByRecordCount 
	* @param declareId
	* @return List<DeclareRewarReport>  
	* @throws
	*/
	public Long  findByRecordCount(List<String> loanIds) {
		
		return declareRewarReportRecordrDao.findByRecordCount(loanIds);
	}
	
	
	public void  saveDeclareRewarReportRecord(List<DeclareRewarReportRecord>  record){
		 declareRewarReportRecordrDao.save(record);
	}
	/**
	* 申报查询投资记录详情
	* @Title: investorPublishList 
	* @param declareId
	* @return List<DeclareRewarReport>  
	* @throws
	*/
	public List<DeclareRewarReport> findByeventId(String eventId) {
		
		return rewarDeclareReportDao.findByeventId(eventId);
	}
	/**
	* 主管机构-----查询列表
	* @Title: getDeclareLists 
	* @param declareRewardSearsh
	* @return List<DeclareRewarInfor>    
	* @throws
	 */
	public List<DeclareRewarInfor> getManageDeclareLists(DeclareRewarSearshBean declareRewardSearsh) {

		return (List<DeclareRewarInfor>) rewarDeclareDaoImpl.getManageDeclareLists(declareRewardSearsh);
	}
	/**
	* 主管机构-----查询列表条数
	* @Title: getDeclareLists 
	* @param declareRewardSearsh
	* @return List<DeclareRewarInfor>    
	* @throws
	 */
	public Object getManageDeclareCount(DeclareRewarSearshBean declareRewardSearsh) {

		return  rewarDeclareDaoImpl.getManageDeclareCount(declareRewardSearsh);
	}
}
