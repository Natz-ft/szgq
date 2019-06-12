package com.icfcc.SRRPService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalDelNativeDao;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalRankInvestor;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalRankInvestorDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankInvestor;
import com.icfcc.SRRPDao.s1.jpa.repository.PortalRankInvestorNativeDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * 
 * <p>
 * 功能描述: [门户投资机构榜单]
 * </p>
 * 
 * @author zhanglf
 * @version
 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
@Transactional(value = "transactionManager")
public class PortalRankInvestorService   {
	@Autowired
	private PortalRankInvestorDao dao;
	@Autowired
	private PortalRankInvestorNativeDao nativeDao;
	@Autowired
	private PortalDelNativeDao delDao;

	/**
	 * 
	 * <p>
	 * 功能描述:[待迁移到门户机构榜单]
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Transactional(value = "transactionManager1")
	public List<PortalRankInvestor> portalRankInvestorList() throws Exception {
		List<PortalRankInvestor> dataList = null;
		List<PlatformRankInvestor> res = nativeDao.portalRankInvestorList();
		if (res != null && res.size() > 0) {
			dataList = new ArrayList<PortalRankInvestor>();
			for (PlatformRankInvestor data : res) {
				PortalRankInvestor target = new PortalRankInvestor();
				BeanUtils.copyProperties(target, data);
				dataList.add(target);
			}
		}

		return dataList;
	}

	/**
	 * 
	 * <p>
	 * 功能描述:[迁移到门户]
	 * </p>
	 * 
	 * @param dataList
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public void saveInvestorInfo(List<PortalRankInvestor> dataList) {
		del();
		save(dataList);
	}

	@Transactional(value = "transactionManager")
	public void save(List<PortalRankInvestor> dataList) {
		// 新数据迁入
		dao.save(dataList);
	}

	@Transactional(value = "transactionManager")
	public void del() {
		// 先删除门户表数据
		delDao.delPortalData("delInvestor");
	}

}
