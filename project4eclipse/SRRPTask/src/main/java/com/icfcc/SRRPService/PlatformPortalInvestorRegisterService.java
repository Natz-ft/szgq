package com.icfcc.SRRPService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalInvestorAuditPending;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalInvestorRegiter;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalInvestorAuditDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalInvestorRegisterDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformPortalInvestorAuditPending;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformPortalInvestorRegiter;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformPortalInvestorAuditDao;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformPortalInvestorRegisterDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * 
 * <p>
 * 功能描述:投资机构注册信息由门户库抽取到业务系统库
 * </p>
 * 
 * @author zhanglf
 * @version
 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
@Transactional(value = "transactionManager1")
public class PlatformPortalInvestorRegisterService   {
	private static Logger log = LoggerFactory.getLogger(PlatformPortalInvestorRegisterService.class);

	// 门户库投资机构信息
	@Autowired
	private PortalInvestorRegisterDao portalInvestorRegisterDao;

	// 门户库投资机构信息待审核
	@Autowired
	private PortalInvestorAuditDao portalInvestorAuditDao;

	// 业务系统投资机构信息
	@Autowired
	private PlatformPortalInvestorRegisterDao platformPortalInvestorRegisterDao;
	// 业务系统投资机构待审核信息
	@Autowired
	private PlatformPortalInvestorAuditDao platformPortalInvestorAuditDao;
	public int size = 0;

	/**
	 * 
	 * <p>
	 * 功能描述:[将注册信息从门户库迁移到业务库]
	 * </p>
	 * 
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Transactional(value = "transactionManager1")
	public void transferRegitsterInfosPortalToRp() {

		// 门户投资机构注册信息
		List<PortalInvestorRegiter> registerList = (List<PortalInvestorRegiter>) portalInvestorRegisterDao.findAll();
		// 业务系统投资机构信息
		List<PlatformPortalInvestorRegiter> saveRegitstList = null;
		if (registerList != null && registerList.size() > 0) {
			saveRegitstList = new ArrayList<PlatformPortalInvestorRegiter>();
			for (PortalInvestorRegiter portalInfo : registerList) {
				PlatformPortalInvestorRegiter target = new PlatformPortalInvestorRegiter();
				BeanUtils.copyProperties(portalInfo, target);
				saveRegitstList.add(target);
			}
		}
		// 门户投资机构注册审核信息
		List<PortalInvestorAuditPending> auditList = (List<PortalInvestorAuditPending>) portalInvestorAuditDao
				.findAll();
		// 业务系统投资机构待审核信息
		List<PlatformPortalInvestorAuditPending> saveAduditList = null;
		if (auditList != null && auditList.size() > 0) {
			saveAduditList = new ArrayList<PlatformPortalInvestorAuditPending>();
			for (PortalInvestorAuditPending auditInfo : auditList) {
				PlatformPortalInvestorAuditPending target = new PlatformPortalInvestorAuditPending();
				BeanUtils.copyProperties(auditInfo, target);
				saveAduditList.add(target);
			}
		}
		if (saveRegitstList != null && saveAduditList != null) {
			// 注册信息迁移到业务系统
			platformPortalInvestorRegisterDao.save(saveRegitstList);
			platformPortalInvestorAuditDao.save(saveAduditList);
			// 注册信息删除
			portalInvestorRegisterDao.deleteAll();
			portalInvestorAuditDao.deleteAll();
			size = saveRegitstList.size();
			log.info("transferRegitsterInfosPortalToRp is successed");
		}
	}

	
}
