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

import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalOnlineForum;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalOnlineFormDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformPortalInvestorAuditPending;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformPortalOnlineFormDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * 
 * <p>
 * 功能描述:在线提问由门户库抽取到业务系统库
 * </p>
 * 
 * @author zhanglf
 * @version
 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
@Transactional(value = "transactionManager1")
public class PlatformPortalOnlineFormService   {
	private static Logger log = LoggerFactory.getLogger(PlatformPortalOnlineFormService.class);
	// 门户库在线提问信息
	@Autowired
	private PortalOnlineFormDao portalOnlineFormDao;

	// 业务系统在线提问信息
	@Autowired
	private PlatformPortalOnlineFormDao platformPortalOnlineFormDao;
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
	public void transferOnlineFormsInfosPortalToRp() throws Exception {

		// 门户在线提问信息
		List<PortalOnlineForum> infosList = (List<PortalOnlineForum>) portalOnlineFormDao.findAll();
		// 业务系统投资机构信息
		List<PlatformPortalInvestorAuditPending> saveOnlineFormList = null;
		if (infosList != null && infosList.size() > 0) {
			saveOnlineFormList = new ArrayList<PlatformPortalInvestorAuditPending>();
			for (PortalOnlineForum infos : infosList) {
				PlatformPortalInvestorAuditPending target = new PlatformPortalInvestorAuditPending();
				BeanUtils.copyProperties(infos, target);
				saveOnlineFormList.add(target);
			}
		}
		if (saveOnlineFormList != null) {
			// 在线提问迁移到业务系统
			platformPortalOnlineFormDao.save(saveOnlineFormList);
			size = saveOnlineFormList.size();
			portalOnlineFormDao.deleteAll();
			log.info("transferOnlineFormsInfosPortalToRp is successed");
		}
	}

}
