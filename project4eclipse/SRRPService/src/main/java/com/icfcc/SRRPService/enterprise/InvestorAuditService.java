package com.icfcc.SRRPService.enterprise;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ReportVO;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievement;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievementPending;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditPending;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditRecord;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievement;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievementPending;
import com.icfcc.SRRPDao.jpa.entity.managedept.InvestorHistory;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorAuditPendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorAuditRecordDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.InvestorAchievementDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.InvestorAchievementPendingDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.InvestorManageAchievementDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.InvestorManageAchievementPendingDao;
import com.icfcc.SRRPService.PlatformSystem.PlatformConfigService;
import com.icfcc.SRRPService.PlatformSystem.PlatformRoleService;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.gataway.staticize.GataWayCompanyInfoService;
import com.icfcc.SRRPService.managedept.InvestorHistoryService;
import com.icfcc.SRRPService.util.AESUtil;
import com.icfcc.SRRPService.util.SmsClientUtil;
import com.icfcc.credit.platform.util.FileUtil;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * 机构审核用
 * 
 * @author lijj
 *
 */

@Service
@Transactional(value = "transactionManager")
public class InvestorAuditService {

	@Autowired
	private InvestorDao investorDao;
	@Autowired
	private InvestorAuditPendingDao auditPendingDao;
	@Autowired
	private InvestorAuditRecordDao auditRecordDao;
	@Autowired
	protected PlatformUserService userService;
	@Autowired
	protected PlatformRoleService roleService;
	@Autowired
	private PlatformConfigService config;
	@Autowired
	private InvestorHistoryService investorHistory;
	@Autowired
	private GataWayCompanyInfoService gataWayCompanyInfoService;
	@Autowired
	private InvestorAchievementDao investorAchievementDao;
	@Autowired
	private InvestorManageAchievementDao investorManageAchievementDao;
	@Autowired
	private InvestorManageAchievementPendingDao investorManageAchievementPendingDao;
	@Autowired
	private InvestorAchievementPendingDao investorAchievementPengdingDao;
	@Autowired
	private CompanyInfoService companyInfoService;
	/**
	 * 保存一个待审核机构信息
	 * 
	 * @param pending
	 * @return
	 */
	public InvestorAuditPending saveInvestorAuditPending(
			InvestorAuditPending pending) {
		return this.auditPendingDao.save(pending);
	}

	/**
	 * 保存一个审核记录
	 * 
	 * @param record
	 * @return
	 */
	public InvestorAuditRecord saveInvestorAuditRecord(
			InvestorAuditRecord record) {
		return this.auditRecordDao.save(record);
	}

	public InvestorAuditPending findAuditPendingById(String investorId) {
		return this.auditPendingDao.findOne(investorId);
	}

	/**
	 * 审核流程完整操作，传入一个新建的审核记录，这个记录会被保存
	 * 
	 * @param investorId
	 * @param statuss
	 * @param record
	 * @throws Exception 
	 */

	public Map<String, Object> auditInvestor(InvestorAuditRecord investorAudit, String userType,String basePath) throws Exception {
		String auditStatus = investorAudit.getAuditResult();
		InvestorAuditPending pending = null;
		Investor io = null;
		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("reponseCode", "200");
		String flag = "";
//		try {
			io = investorDao.findOne(investorAudit.getInvestorId());// 正式表信息
			pending = this.auditPendingDao.findOne(investorAudit.getInvestorId());// 待审核信息
			if (SRRPConstant.USER_TYPE_05.equals(userType)) {//
				// 征信公司激活 审核
				if (SRRPConstant.REFISTER_STATUS_PASS.equals(auditStatus)) {
					resultMap=gentInvestorMap(basePath,pending);
					if(SRRPConstant.ZXSQ_RESULT_200.equals(resultMap.get("reponseCode"))){
						investorAudit.setAuditResult("22");
						statusPass(investorAudit, pending, io);// 审核通过
						flag = "3";// 代表征信公司激活
						// 发送短信通知审核通过
						sendAuditMassge(io.getName(), io.getCertno(), "审核",flag,
								investorAudit.getAuditContent(),
								pending.getRelPhone());
					} else if(SRRPConstant.ZXSQ_RESULT_400.equals(resultMap.get("reponseCode") )&&"本机构已登记过该企业授权，不允许重复提交。".equals(resultMap.get("reponseMsg"))){ 
						investorAudit.setAuditResult("22");
						statusPass(investorAudit, pending, io);// 审核通过
						flag = "3";// 代表征信公司激活
						// 发送短信通知审核通过
						sendAuditMassge(io.getName(), io.getCertno(), "审核",flag,
								investorAudit.getAuditContent(),
								pending.getRelPhone());
					}else{
						return resultMap;
					}
					
				} else {// 审核不通过
					investorAudit.setAuditResult("23");
					InvestorHistory investorHis = new InvestorHistory();
					io.setAuditStatus("23");
					investorDao.save(io);
//					investorAchievementPengdingDao.deleteById(io.getInvestorId());
//					investorManageAchievementPendingDao.deleteById(io.getInvestorId());
//					deleteAuditPending(investorAudit);
//					io.setCreateTime(new Date());
					BeanUtils.copyProperties(io, investorHis);
					investorHistory.saveInvestorHistory(investorHis);// 1新增到历史表信息
					// 代表征信公司激活失败
					flag = "5";// 代表机构审核通过
					sendAuditMassge(io.getName(), io.getCertno(), "审核", flag,
							investorAudit.getAuditContent(),
							pending.getRelPhone());
				}
			}
			if (SRRPConstant.USER_TYPE_06.equals(userType)) {//
				// 征信公司待审核用户
				if (SRRPConstant.REFISTER_STATUS_PASS.equals(auditStatus)) {
					resultMap=gentInvestorMap(basePath,pending);
					if(SRRPConstant.ZXSQ_RESULT_200.equals(resultMap.get("reponseCode"))){
						investorAudit.setAuditResult("2");
						InvestorAuditRecord investorAuditRecord2 = new InvestorAuditRecord();
						Date now = new Date();//获取当前时间
						Date afterDate = new Date(now.getTime() - 2000);
						BeanUtils.copyProperties(investorAudit, investorAuditRecord2);
						investorAuditRecord2.setAuditTime(afterDate);
						saveInvestorAuditRecord(investorAuditRecord2);
						investorAudit.setAuditResult("22");
						investorAudit.setAuditTime(new Date());
						statusPass(investorAudit, pending, io);// 审核通过
						//更新业绩表与管理业绩表的数据
//						updateInvestorAchievement(io.getInvestorId());
//						updateManageAchievement(io.getInvestorId());
						flag = "3";// 代表征信公司激活
						// 发送短信通知审核通过
						sendAuditMassge(io.getName(), io.getCertno(), "",flag,
								investorAudit.getAuditContent(),
								pending.getRelPhone());
					}else if(SRRPConstant.ZXSQ_RESULT_400.equals(resultMap.get("reponseCode") )&&"本机构已登记过该企业授权，不允许重复提交。".equals(resultMap.get("reponseMsg"))){ 
						investorAudit.setAuditResult("2");
						InvestorAuditRecord investorAuditRecord2 = new InvestorAuditRecord();
						Date now = new Date();//获取当前时间
						Date afterDate = new Date(now .getTime() - 300000);
						BeanUtils.copyProperties(investorAudit, investorAuditRecord2);
						investorAuditRecord2.setAuditTime(afterDate);
						saveInvestorAuditRecord(investorAuditRecord2);
						investorAudit.setAuditResult("22");
						investorAudit.setAuditTime(new Date());
						statusPass(investorAudit, pending, io);// 审核通过
						//更新业绩表与管理业绩表的数据
//						updateInvestorAchievement(io.getInvestorId());
//						updateManageAchievement(io.getInvestorId());
						flag = "3";// 代表征信公司激活
						// 发送短信通知审核通过
						sendAuditMassge(io.getName(), io.getCertno(), "",flag,
								investorAudit.getAuditContent(),
								pending.getRelPhone());
					}else{
						return resultMap;
					}
					
					
				} 
				if (SRRPConstant.REFISTER_STATUS_NOPASS.equals(auditStatus))  {// 审核不通过
					investorAudit.setAuditResult("3");
					InvestorHistory investorHis = new InvestorHistory();
					io.setAuditStatus("3");
					investorDao.save(io);
//					investorAchievementPengdingDao.deleteById(io.getInvestorId());
//					investorManageAchievementPendingDao.deleteById(io.getInvestorId());
//					deleteAuditPending(investorAudit);
//					io.setCreateTime(new Date());
					BeanUtils.copyProperties(io, investorHis);
					investorHistory.saveInvestorHistory(investorHis);// 1新增到历史表信息
					// 代表征信公司激活失败
					flag = "5";// 代表机构审核通过
					sendAuditMassge(io.getName(), io.getCertno(), "审核", flag,
							investorAudit.getAuditContent(),
							pending.getRelPhone());
				}
				if (SRRPConstant.EDIT_STATUS_NOPASS.equals(auditStatus)) {// 是金融办编辑审核不通过
					io.setAuditStatus("6");
					investorDao.save(io);
					investorAudit.setAuditResult("6");
					flag = "16";//审核不通过
					//investorAchievementPengdingDao.deleteById(io.getInvestorId());
					//investorManageAchievementPendingDao.deleteById(io.getInvestorId());
					sendAuditMassge(io.getName(), io.getCertno(), "审核", flag,
							investorAudit.getAuditContent(),
							pending.getRelPhone());
				}
				
				if (SRRPConstant.EDIT_STATUS_PASS.equals(auditStatus)) {// 市金融办编辑审核通过
					investorAudit.setAuditResult("5");
					statusPass(investorAudit, pending, io);// 审核通过
					//更新业绩表与管理业绩表的数据
//					updateInvestorAchievement(io.getInvestorId());
//					updateManageAchievement(io.getInvestorId());
					// 发送短信通知审核通过
				}
			}
			if (SRRPConstant.USER_TYPE_04.equals(userType)) {//
				if (SRRPConstant.REFISTER_STATUS_PASS.equals(auditStatus)) {// 市金融办注册审核通过
					investorAudit.setAuditResult("2");
					io.setAuditStatus("2");
					io.setAuditStage("05");
				}
				if (SRRPConstant.REFISTER_STATUS_NOPASS.equals(auditStatus)) {// 市金融办注册审核不通过
					investorAudit.setAuditResult("3");
					io.setAuditStatus("3");
					flag = "5";// 代表机构审核通过
					sendAuditMassge(io.getName(), io.getCertno(), "审核", flag,
							investorAudit.getAuditContent(),
							pending.getRelPhone());
				}
				if (SRRPConstant.EDIT_STATUS_NOPASS.equals(auditStatus)) {// 是金融办编辑审核不通过
					io.setAuditStatus("6");
					investorAudit.setAuditResult("6");
					flag = "16";// 代表机构审核通过
//					investorAchievementPengdingDao.deleteById(io.getInvestorId());
//					investorManageAchievementPendingDao.deleteById(io.getInvestorId());
					sendAuditMassge(io.getName(), io.getCertno(), "审核", flag,
							investorAudit.getAuditContent(),
							pending.getRelPhone());
				}
				investorDao.save(io);
				if (SRRPConstant.EDIT_STATUS_PASS.equals(auditStatus)) {// 市金融办编辑审核通过
					investorAudit.setAuditResult("5");
					statusPass(investorAudit, pending, io);// 审核通过
					//更新业绩表与管理业绩表的数据
//					updateInvestorAchievement(io.getInvestorId());
//					updateManageAchievement(io.getInvestorId());
					// 发送短信通知审核通过
				}

			}
			saveInvestorAuditRecord(investorAudit);
		return resultMap;
	}

	/**
	 * 审核结果发送短信
	 * 
	 * @param name
	 *            企业名称
	 * @param userName
	 *            用户名
	 * @param keys
	 *            短信主键 4 的时候是审核通过 6的时候是审核不通过
	 * @param auditContent
	 *            审核不通过的原因 phoneNum
	 */
	public void sendAuditMassge(String name, String userName, String status,String flag,
			String auditContent, String phoneNum) {
		boolean result = false;
		try {
			if ("3".equals(flag)) {// 审核通过的情况下
				String arry[] = new String[3];
				arry[0] = name;// 第一个参数为企业名称
				arry[1] = userName;
				arry[2] = "查看企业融资";
				String[] keys = gataWayCompanyInfoService.keys(flag);
				result = SmsClientUtil.querySms(arry, keys, phoneNum, flag);
			} else if ("5".equals(flag)|| "16".equals(flag)) {
				String arry[] = new String[3];
				arry[0] = name;// 第一个参数为企业名称
				arry[1] = status;// 第一个参数为企业名称
				arry[2] = auditContent;// 第二个参数为审核没有通过的原因
				String[] keys = gataWayCompanyInfoService.keys(flag);
				result = SmsClientUtil.querySms(arry, keys, phoneNum, flag);
			}
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}

	}

	/**
	 * 更新机构投资业绩信息
	 * 
	 * @param investorId
	 */
	public void updateInvestorAchievement(String investorId) {
		// 查询待审核表中的数据
		List<InvestorAchievementPending> list = investorAchievementPengdingDao
				.findAchievementsById(investorId);
		if(list.size()>0){
			// 查询基本表的数据
			List<InvestorAchievement> oldList = investorAchievementDao
					.findAchievementsById(investorId);
			if (oldList.size()>0) {
				investorAchievementDao.deleteById(investorId);
			}
			for (InvestorAchievementPending investorAchievementPending : list) {
				InvestorAchievement achievement = new InvestorAchievement();
				BeanUtils.copyProperties(investorAchievementPending, achievement);
				investorAchievementDao.save(achievement);
			}
			investorAchievementPengdingDao.deleteById(investorId);
		}
	}

	/**
	 * 更新机构管理业绩信息
	 * 
	 * @param investorId
	 */
	public void updateManageAchievement(String investorId) {
		// 查询待审核表中的数据
		List<InvestorManageAchievementPending> list = investorManageAchievementPendingDao
				.findAllManageAchievement(investorId);
		if(list.size()>0){
			// 查询基本表的数据
			List<InvestorManageAchievement> oldList = investorManageAchievementDao
					.findAllManageAchievement(investorId);
			if (oldList.size()>0) {
				investorManageAchievementDao.deleteById(investorId);
			}
			for (InvestorManageAchievementPending investorManageAchievementPending : list) {
				InvestorManageAchievement manageAchievement = new InvestorManageAchievement();
				BeanUtils.copyProperties(investorManageAchievementPending, manageAchievement);
				investorManageAchievementDao.save(manageAchievement);
			}
			investorManageAchievementPendingDao.deleteById(investorId);
		}
	}

	// 审核通过
	public void statusPass(InvestorAuditRecord investorAudit,
			InvestorAuditPending pending, Investor io) {
		saveInvestor(investorAudit, pending, io);// 1.审核信息新增到待审核表中
		if(io.getAchievementFalg().equals("2")){
			investorAchievementDao.deleteById(io.getInvestorId());
		}
		if(io.getAchievementFalg().equals("1")){
			List<InvestorAchievementPending> list = investorAchievementPengdingDao
					.findAchievementsById(io.getInvestorId());
			List<InvestorAchievement> oldList = investorAchievementDao
					.findAchievementsById(io.getInvestorId());
			if (oldList.size()>0) {
				investorAchievementDao.deleteById(io.getInvestorId());
			}
				// 查询基本表的数据
				for (InvestorAchievementPending investorAchievementPending : list) {
					InvestorAchievement achievement = new InvestorAchievement();
					BeanUtils.copyProperties(investorAchievementPending, achievement);
					investorAchievementDao.save(achievement);
				}
		}
		if(io.getManageAchievementFalg().equals("2")){
			investorManageAchievementDao.deleteById(io.getInvestorId());
		}
		if(io.getManageAchievementFalg().equals("1")){
			List<InvestorManageAchievementPending> list = investorManageAchievementPendingDao
					.findAllManageAchievement(io.getInvestorId());
			List<InvestorManageAchievement> oldList = investorManageAchievementDao
					.findAllManageAchievement(io.getInvestorId());
			if (oldList.size()>0) {
				investorManageAchievementDao.deleteById(io.getInvestorId());
			}
			for (InvestorManageAchievementPending investorManageAchievementPending : list) {
				InvestorManageAchievement manageAchievement = new InvestorManageAchievement();
				BeanUtils.copyProperties(investorManageAchievementPending, manageAchievement);
				investorManageAchievementDao.save(manageAchievement);
			}
		}
		deleteAuditPending(investorAudit);// 2.删除待审核表的信息
		addInvestorUser(investorAudit);// 3.发给机构一个用户
	}

	// 审核不通过
	public void statusNoPass(InvestorAuditRecord investorAudit, Investor io) {
		String auditStatus = investorAudit.getAuditResult();
		InvestorHistory investorHis = new InvestorHistory();
		if (SRRPConstant.REFISTER_STATUS_NOPASS.equals(auditStatus)) {// 注册审核不通过
			io.setAuditStatus(auditStatus);
			investorDao.save(io);
			deleteAuditPending(investorAudit);// 2.删除待审核表的信息
			// userService.deleteByInvestId(investorAudit.getInvestorId());
		}
		if (SRRPConstant.EDIT_STATUS_NOPASS.equals(auditStatus)) {// 编辑审核不通过
			io.setAuditStatus(auditStatus);
			investorDao.save(io);
		}
//		io.setCreateTime(new Date());
		BeanUtils.copyProperties(io, investorHis);
		investorHistory.saveInvestorHistory(investorHis);// 1新增到历史表信息
	}

	public void addInvestorUser(InvestorAuditRecord investorAudit) {
		try {
			Investor io = investorDao.findOne(investorAudit.getInvestorId());// 正式表信息
			// 创建时间和创建人id
			if (SRRPConstant.REFISTER_STATUS_PASS.equals(investorAudit
					.getAuditResult())) {
				PlatformUser user = userService.findUserByInvestId(io
						.getInvestorId());
				if (user == null) {
					user = new PlatformUser();
					user.setCreateTime(new Date());
				}
				user.setUsername(io.getCertno());
				user.setEmail(io.getEmail());
				user.setAddress(io.getOfficeAddress());
				user.setStopFlag(1);
				user.setOrg(io.getInvestorId());
				user.setTelephone(io.getRelPhone());
				user.setCreateUser(investorAudit.getAuditorName());
				user.setNickname(io.getName());
				user.setDesc1("0");
				userService.saveSystemUser(user);
				relateRole(io.getCertno());// 赋予用户角色权限
			} else {
				PlatformUser user = userService.findUser(io.getInvestorId());
				if (user == null) {
					user = new PlatformUser();
					user.setCreateTime(new Date());
					user.setCreateUser(investorAudit.getAuditorName());
				}
				user.setUsername(io.getCertno());
				user.setEmail(io.getEmail());
				user.setAddress(io.getOfficeAddress());
				user.setStopFlag(1);
				user.setOrg(io.getInvestorId());
				user.setTelephone(io.getRelPhone());
				user.setNickname(io.getName());
				user.setDesc1("0");
				userService.addSystemUser(user);
				relateRole(io.getCertno());// 赋予用户角色权限
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void relateRole(String userName) {// 赋予用户角色
		try {
			PlatformUser user = userService.findUserByUserName(userName);
			// 需要先删除原有的关联关系，然后存入新的关联关系
			roleService.DelAllRole(user.getId());
			String roleId = config.getConfigValueByName("invesUserRole");
			roleService.InveInsertUserRoleRel(user.getId(), roleId,
					user.getNickname());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveInvestor(InvestorAuditRecord record,
			InvestorAuditPending pending, Investor io) {
		if (io == null) {
			io = new Investor();
			io.setInvestorId(pending.getInvestorId());
			io.setCreateTime(pending.getCreateTime());
		}

		io.setOfficeAddress(pending.getOfficeAddress());

		io.setAreaCode(pending.getAreaCode());
		io.setAuditStatus(record.getAuditResult());
		io.setCerttype(pending.getCerttype());
		io.setCapitalMin(pending.getCapitalMin());
		io.setCapitalMax(pending.getCapitalMax());
		io.setCurrency(pending.getCurrency());
		io.setCertno(pending.getCertno());
		io.setCapitalType(pending.getCapitalType());
		io.setCertno(pending.getCertno());
		io.setCerttype(pending.getCerttype());
		io.setDescription(pending.getDescription());
		io.setEmail(pending.getEmail());
		io.setFax(pending.getFax());
		io.setPost(pending.getPost());
		io.setStopFlag("1");
		io.setRegisteredCapital(pending.getRegisteredCapital());
		io.setCorepersonnel(pending.getCorepersonnel());
		io.setCoreteam(pending.getCoreteam());
		io.setOperationQualification1(pending.getOperationQualification1());
		io.setOperationQualification2(pending.getOperationQualification2());
		io.setOperationQualification3(pending.getOperationQualification3());
		io.setTeamCount(pending.getTeamCount());
		io.setCreditUnhealthy(pending.getCreditUnhealthy());
		io.setSeniorManagement(pending.getSeniorManagement());
		io.setMechanism(pending.getMechanism());
		io.setFinanceStage(pending.getFinanceStage());
		io.setFinanceTrade(pending.getFinanceTrade());
		io.setLicensePath(pending.getLicensePath());
		io.setName(pending.getName());
		io.setRegCurrency(pending.getRegCurrency());
		io.setLegalRepresentative(pending.getLegalRepresentative());
		io.setOrgType(pending.getOrgType());
		io.setRelName(pending.getRelName());
		io.setRelPhone(pending.getRelPhone());
		io.setTopInvestor(pending.getTopInvestor());
		io.setZipcode(pending.getZipcode());
		io.setRegisteTime(pending.getRegisteTime());
		io.setRegisteredAddress(pending.getRegisteredAddress());
		io.setOrganizationalForm(pending.getOrganizationalForm());
		io.setLegalRepresentativeCall(pending.getLegalRepresentativeCall());
		io.setPaidCapital(pending.getPaidCapital());
		io.setPcCurrency(pending.getPcCurrency());
		io.setAchievementDesc(pending.getAchievementDesc());
		io.setAchievementFalg(pending.getAchievementFalg());
		io.setManageAchievementFalg(pending.getManageAchievementFalg());
		io.setManageAchievementDesc(pending.getManageAchievementDesc());
		io.setLicensePath(pending
				.getLicensePath());
		io.setFileName(pending.getFileName());
		io.setLogoName(pending.getLogoName());
		io.setLogoPath(pending.getLogoPath());
		io.setRegisterAutographName(pending
				.getRegisterAutographName());
		io.setRegisterAutographPath(pending
				.getRegisterAutographPath());
		io.setCreditAuthorizationName(pending
				.getCreditAuthorizationName());
		io.setCreditAuthorizationPath(pending
				.getCreditAuthorizationPath());
		io.setOperdate(pending.getOperdate());
		this.investorDao.save(io);
	}

	// 删除待审核中的信息
	public void deleteAuditPending(InvestorAuditRecord record) {
		this.auditPendingDao.delete(record.getInvestorId());
	}

	/**
	 * 按机构搜索审核记录，分页获取，按审核时间倒叙排列
	 * 
	 * @param investorId
	 * @param page
	 * @param size
	 * @return
	 */
	public List<InvestorAuditRecord> getAuditRecordByInvestorId(
			String investorId) {
		Pageable pageable = null;
		// if (page != null && size != null) {
		// pageable = new PageRequest(page, size, null);
		// }
		return this.auditRecordDao.findByInvestorId(investorId);
	}
	public  Map<String, Object> gentInvestorMap(String basePath,InvestorAuditPending investor)throws Exception {
			Map<String, String> baseMap=new HashMap<String, String>();
			Map<String, Object> resultMap=null;
			String filePath=investor.getCreditAuthorizationPath();
			String fileType=filePath.substring(filePath.lastIndexOf(".")+ 1);
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			baseMap.put("authFilePath",encoder.encode(FileUtil.getBytesByFile(filePath)));//影像信息的流
			baseMap.put("corpName",investor.getName());//企业或者机构的名称
			baseMap.put("fileType",fileType);//文件的后缀
			baseMap.put("cardCode", investor.getCertno());//组织机构码、社会信用码
			baseMap.put("cardType", investor.getCerttype());//证件码类型
			baseMap.put("legalName", investor.getLegalRepresentative());//法人姓名
			baseMap.put("legalPhone",investor.getLegalRepresentativeCall());//法人电话
			baseMap.put("contactName",investor.getRelName());//联系人姓名
			baseMap.put("contactPhone",investor.getRelPhone());//联系人电话
			resultMap=companyInfoService.importZXSQInfo(baseMap,"02");
		return resultMap;
	}
}
