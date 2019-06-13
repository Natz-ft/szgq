package com.icfcc.SRRPService.enterprise;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryCompanyFinacingEventResult;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryInvestorFinacingEventResult;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievement;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievementPending;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievement;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievementPending;
import com.icfcc.SRRPDao.jpa.entity.managedept.InvestorManageResutList;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.InvestorDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.InvestorInfoListDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.InvestorAchievementDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.InvestorAchievementPendingDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.InvestorManageAchievementDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.InvestorManageAchievementPendingDao;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.ssrp.util.ExcelUtil;

@Service
@Transactional(value = "transactionManager")
public class InvestorService {

	@Autowired
	private InvestorDao inverstorDao;
	@Autowired
	private InvestorDaoImpl investorDaoImpl;
	@Autowired
	private InvestorInfoListDaoImpl investorInfoListDaoImpl;
	@Autowired
	private InvestorAchievementDao investorAchievementDao;
	@Autowired
	private InvestorManageAchievementDao investorManageAchievementDao;
	@Autowired
	private InvestorManageAchievementPendingDao investorManageAchievementPendingDao;
	@Autowired
	private InvestorAchievementPendingDao investorAchievementPengdingDao;

	@Autowired
	private InvestorInfoListDaoImpl inverstorListDaoImpml;
	/**
	 * 
	 * <閫氳繃鏈烘瀯鐨刬d瀵规満鏋勮鎯呰繘琛屾煡璇㈡煡璇㈢粨鏋滆繘琛屾晥鏋滃睍绀�>
	 * 
	 * @param searchParams
	 * @return
	 */
	public Investor findInverstorById(String id) {
		// 璋冪敤dao灞傜殑鏂规硶
		return inverstorDao.findInverstorById(id);

	}

	/**
	 * 鏍规嵁铻嶈祫浜嬩欢鐨刬d鏌ヨ铻嶈祫鏈烘瀯铻嶈祫鐨勪俊鎭�
	 * 
	 * @param fiancingInfoId
	 * @return
	 */
	public List<QueryInvestorFinacingEventResult> findUnionInvestorList(String eventId) {

		return investorInfoListDaoImpl.findUnionInvestorList(eventId);
	}

	/**
	 * <澶氭潯浠舵煡璇㈠垎椤靛垪琛ㄦ樉绀烘姇璧勬満鏋勭殑淇℃伅> 鎶曡祫鏈烘瀯鏌ヨ椤甸潰鐢ㄥ埌鐨勬満鏋勬煡璇�
	 * 
	 * @param searchParams
	 *            鏌ヨ鍙傛暟
	 * @param pageNumber
	 *            鏌ヨ璧峰椤�
	 * @param pageSize
	 *            鏌ヨ椤靛ぇ灏�
	 * @return
	 */
	// queryCondition
	public List<Investor> listInvestorByWhereCase(QueryCondition queryCondition) {
		return investorDaoImpl.findInverstorInfoList(queryCondition, "1");
	}

	/**
	 * 鎶曡祫鏈烘瀯鏌ヨ/鏌ヨ鎶曡祫鏈烘瀯鐨勬�绘暟
	 */
	public Object getInvestorCount(QueryCondition queryCondition) {

		return investorDaoImpl.getInvestorCount(queryCondition, "1");
	}

	/**
	 * 閫夋嫨鎶曡祫鏈烘瀯/鏌ヨ鎶曡祫鏈烘瀯鎬绘暟
	 */
	public Object getInvestorCountByOrgType(QueryCondition queryCondition) {

		return investorDaoImpl.getInvestorCountByOrgType(queryCondition, "1");
	}

	/**
	 * 鏍规嵁鏈烘瀯鐨刬d鏌ヨ鏈烘瀯鐨勫悕绉�
	 */
	public String findInvestorNameById(String investorId) {
		Investor investor = inverstorDao.findOne(investorId);
		String investorName = "";
		if (investor != null) {
			investorName = investor.getName();
		}
		return investorName;
	}

	/**
	 * <澶氭潯浠舵煡璇㈠垎椤靛垪琛ㄦ樉绀烘姇璧勬満鏋勭殑淇℃伅> 閫夋嫨鏈烘瀯椤甸潰鐢ㄥ埌鐨勬姇璧勬満鏋勫鏉′欢鏌ヨ
	 * 
	 * @param investorName
	 *            鏈烘瀯鍚嶇О
	 * @param orgType
	 *            鏈烘瀯鐨勭被鍨�
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Investor> listInvestorByOrgType(QueryCondition queryCondition) {

		return investorDaoImpl.listInvestorByOrgType(queryCondition, "1");
	}

	/**
	 * 涓荤閮ㄩ棬鏌ヨ鎶曡祫鏈烘瀯鍒楄〃撒旦撒旦
	 * 
	 * @param name
	 * @param code
	 * @param status 
	 * @param auditStatus
	 * @param page
	 * @param size
	 * @return
	 */
	public List<InvestorManageResutList> getInvestorsForCharge(String nameOrCode,String rearea, String status, String auditStatus,
			int page, int size,String userType ) {
		return investorDaoImpl.getInvestorsForCharge(nameOrCode,rearea, status, auditStatus, page, size,userType);
	}

	public Object getInvestorsForChargeCount(String nameOrCode,String rearea, String status, String auditStatus,String userType) {
		return investorDaoImpl.getInvestorsForChargeCount(nameOrCode,rearea, status, auditStatus, userType);
	}

	/**
	 * 涓荤閮ㄩ棬淇敼鎶曡祫鏈烘瀯鐘舵��
	 * 
	 * @param orgId
	 * @param stopFlag
	 *            鍋滅敤02锛屽惎鐢�01
	 */
	public void updateStopFlag(String orgId, String stopFlag) {
		this.inverstorDao.updateStopFlag(orgId, stopFlag);
	}

	/**
	 * 涓诲叧閮ㄩ棬淇敼鎶曡祫鏈烘瀯瀹℃牳鐘舵��
	 * 
	 * @param orgId
	 * @param auditStatus
	 */
	public void updateAuditStatus(String orgId, String auditStatus) {
		this.inverstorDao.updateAuditStatus(orgId, auditStatus);
	}

	/**
	 * 投资业绩查询
	 * 
	 * @param investorId
	 * @return
	 */
	public List<InvestorAchievement> findAchievementById(String investorId) {
		return investorAchievementDao.findAchievementById(investorId);
	}
//	public List<InvestorAchievement> findAchievementsByinvestmentFunds(String investmentFunds) {
//		return investorAchievementDao.findAchievementsByinvestmentFunds(investmentFunds);
//	}
	/**
	 * 管理业绩查询
	 * 
	 * @param investorId
	 * @return
	 */
	public List<InvestorManageAchievement> findAllManageAchievementById(String investorId) {
		return investorManageAchievementDao.findAllManageAchievement(investorId);
	}
	public List<InvestorManageAchievementPending> findAllManageAchievementPendingById(String investorId) {
		return investorManageAchievementPendingDao.findAllManageAchievement(investorId);
	}
	public InvestorManageAchievement findAllManageAchievementByfundName(String fundName,String investorId) {
		return investorManageAchievementDao.findAllManageAchievementByfundName(fundName,investorId);
	}

	/**
	 * 修改投资业绩表
	 * 
	 * @param investorAchievement
	 */
	public void saveInvestorAchievement(InvestorAchievement investorAchievement) {
		investorAchievementDao.save(investorAchievement);
	}
	public void saveAchievementList(List<InvestorAchievement> investorAchievements ) {
		investorAchievementDao.save(investorAchievements);
	}
	
	public void saveAchievementPendingList(List<InvestorAchievementPending> investorAchievements ) {
	
		investorAchievementPengdingDao.save(investorAchievements);
	}
	/**
	 * 修改机构次要信息
	 * 
	 * @param investor
	 */
	public void save(Investor investor) {
		inverstorDao.save(investor);
	}

	/**
	 * 添加管理业绩信息
	 * 
	 * @param investorManageAchievement
	 */
	public void saveInvestorManageAchievement(InvestorManageAchievement investorManageAchievement) {
		investorManageAchievementDao.save(investorManageAchievement);
	}
	public void saveManageAchievementList(List<InvestorManageAchievement> investorManageAchievements) {
		investorManageAchievementDao.save(investorManageAchievements);
	}
	
	public void saveManageAchievementPendingList(List<InvestorManageAchievementPending> investorManageAchievements) {
		investorManageAchievementPendingDao.save(investorManageAchievements);
	}
	public void updateManageAchievementsById(String investorId) {
		List<InvestorManageAchievement> list =investorManageAchievementDao.findAllManageAchievement(investorId);
		if(list.size()>0){
			investorManageAchievementDao.deleteById(investorId);
		}
		
	}
	
	public void updateManageAchievementPendingsById(String investorId) {
		List<InvestorManageAchievementPending> list =investorManageAchievementPendingDao.findAllManageAchievement(investorId);
		if(list.size()>0){
			investorManageAchievementPendingDao.deleteById(investorId);
		}
		
	}
	/**
	 * 删除管理业绩信息
	 * 
	 * @param manageId
	 */
	public void deleteInvestorManageAchievementById(String manageId) {
		investorManageAchievementDao.delete(manageId);
	}
	public void isValiProjestCount(String investorId,String foundName)  {
		try {
			//得到管理基金的投资项目数
			InvestorManageAchievement InvestorMann=findAllManageAchievementByfundName(foundName,investorId);
			Double count =InvestorMann.getInvestmentProjects();
			//得到详情总个数
			List<InvestorAchievement> list=investorAchievementDao.findAchievementsByinvestmentFunds(foundName,investorId);
//			if(list>=count){//
//				//提示机构进行核实
//			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询投资业绩信息列表
	 * 
	 * @param investorId
	 * @return
	 */
	public List<InvestorAchievement> findAchievementsById(String investorId) {
		return investorAchievementDao.findAchievementsById(investorId);
	}
	public List<InvestorAchievementPending> findAchievementspendingById(String investorId) {
		return investorAchievementPengdingDao.findAchievementsById(investorId);
	}
	public void updateAchievementsById(String investorId) {
		List<InvestorAchievement> list =investorAchievementDao.findAchievementsById(investorId);
		if(list.size()>0){
			investorAchievementDao.deleteById(investorId);
		}
		
	}
	
	public void updateAchievementPendingsById(String investorId) {
		List<InvestorAchievementPending> list =investorAchievementPengdingDao.findAchievementsById(investorId);
		if(list.size()>0){
			investorAchievementPengdingDao.deleteById(investorId);
		}
		
	}
	/**
	 * 删除投资业绩信息
	 * 
	 * @param achievementId
	 */
	public void deleteInvestorAchievementById(String achievementId) {
		investorAchievementDao.delete(achievementId);
	}

	public int readManageAchievementExcel(CommonsMultipartFile file, String investorId,Sheet sheet,Map<Integer, String> beanpros,Class<?> clazz,ResultBean rs,int count,List<InvestorManageAchievement> investorManageAchievements) {
			List<Object> results = new ArrayList<Object>();
//			Sheet sheet = ExcelUtil.getExcleSheet(file, 0);// 获取表格sheet
			// 读取excel数据映射到指定bean中 如过异常返回错误信息
			String s = ExcelUtil.getDatasByrc(beanpros, clazz, results, sheet);
			if (results != null && results.size() > 0) {
				
				for (Object obj : results) {
					InvestorManageAchievement bean = (InvestorManageAchievement) obj;
					bean.setInvestId(investorId);
//					List<InvestorManageAchievement> manList=investorManageAchievementDao.findAllManageAchievement(investorId);
//					for(InvestorManageAchievement  achive:manList){//存在做对比
//						if(achive.getFundName().equals(bean.getFundName())){
//							bean.setManageId(achive.getManageId());
//						}else{
//							
//						}
//					}
					//saveInvestorManageAchievement(bean);
					investorManageAchievements.add(bean);
					count++;
				}
//
//				Investor investor = findInverstorById(investorId);
//				investor.setManageAchievementDesc("");
//				investor.setManageAchievementFalg("1");
//				save(investor);
//				count =results.size();
				
				rs = new ResultBean(Constant.SUCCESSCODE, "导入成功，共导入"+results.size()+"条");
			}
			
		return count;

	}

	public int readAchievementExcel(CommonsMultipartFile file, String investorId,Sheet sheet,Map<Integer, String> beanpros,Class<?> clazz,ResultBean rs,int count,List<InvestorAchievement> investorAchievement) {
			List<Object> results = new ArrayList<Object>();
//			Sheet sheet = ExcelUtil.getExcleSheet(file,1);// 获取表格sheet
			// 对象封装到map中
			

			// 读取excel数据映射到指定bean中 如过异常返回错误信息
			ExcelUtil.getDatasByrc(beanpros, clazz, results, sheet);
             
			if (results != null && results.size() > 0) {
				for (Object obj : results) {
					InvestorAchievement bean = (InvestorAchievement) obj;
					bean.setInvestId(investorId);
					investorAchievement.add(bean);
					//saveInvestorAchievement(bean);
					count++;
				}
//				Investor investor = findInverstorById(investorId);
//				investor.setAchievementDesc("");
//				investor.setAchievementFalg("1");
//				save(investor);
//				count=results.size();
				
			}
		
		return count;

	}
	
	public List<QueryCompanyFinacingEventResult> findEventListByInvestor(
			String investorId){
		return inverstorListDaoImpml.findEventListByInvestor(investorId);
	}
	
	public List<QueryCompanyFinacingEventResult> findUnionCompanyEventList(
			String investorId,String enterpriseId){
		return inverstorListDaoImpml.findUnionCompanyEventList(investorId);
	}
	
	

}
