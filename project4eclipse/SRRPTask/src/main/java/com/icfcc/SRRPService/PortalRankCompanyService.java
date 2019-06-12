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
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalRankCompany;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalRankCompanyDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankCompany;
import com.icfcc.SRRPDao.s1.jpa.repository.PortalRankCompanyNativeDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * <從业务系统为门户抽取运行结果内按地区统计结果>
 */
@Service
@Transactional(value = "transactionManager")
public class PortalRankCompanyService   {
	@Autowired
	private PortalRankCompanyNativeDao nativeDao;
	@Autowired
	private PortalRankCompanyDao dao;
	@Autowired
	private PortalDelNativeDao delDao;

	/**
	 * 
	 * <p>
	 * 功能描述:[待迁移门户企业榜单]
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Transactional(value = "transactionManager1")
	public List<PortalRankCompany> portalRankCompayList() throws Exception {
		List<PortalRankCompany> dataList = null;
		List<PlatformRankCompany> res = nativeDao.portalRankCompayList();
		if (res != null && res.size() > 0) {
			dataList = new ArrayList<PortalRankCompany>();
			for (PlatformRankCompany platformRankCompany : res) {
				PortalRankCompany portalRankCompany = new PortalRankCompany();
				BeanUtils.copyProperties(portalRankCompany, platformRankCompany);
				dataList.add(portalRankCompany);
			}
		}
		return dataList;
	}

	/**
	 * 
	 * <p>
	 * 功能描述:[迁移到门户表]
	 * </p>
	 * 
	 * @param dataList
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public void saveCompanyInfos(List<PortalRankCompany> dataList) throws Exception {
		del();
		save(dataList);
	}

	@Transactional(value = "transactionManager")
	public void save(List<PortalRankCompany> dataList) throws Exception {
		dao.save(dataList);

	}

	@Transactional(value = "transactionManager")
	public void del() {
		// 先删除门户表数据
		delDao.delPortalData("delEnterprise");
	}

}
