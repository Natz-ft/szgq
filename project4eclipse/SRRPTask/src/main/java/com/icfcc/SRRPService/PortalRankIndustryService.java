package com.icfcc.SRRPService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalDelNativeDao;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalRankIndustry;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalRankIndustryDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankIndustry;
import com.icfcc.SRRPDao.s1.jpa.repository.PortalRankIndustryNativeDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * <行业榜单>
 */
@Service
@Transactional(value = "transactionManager")
public class PortalRankIndustryService   {
	@Autowired
	private PortalRankIndustryDao dao;
	@Autowired
	private PortalRankIndustryNativeDao nativeDao;
	@Autowired
	private PortalDelNativeDao delDao;

	/**
	 * 
	 * <p>
	 * 功能描述:[获取待迁移门户行业榜单]
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Transactional(value = "transactionManager1")
	public List<PortalRankIndustry> portalRankIndustryList() throws Exception {
		List<PortalRankIndustry> dataList = null;
		List<PlatformRankIndustry> res = nativeDao.portalRankIndustryList();
		dataList = new ArrayList<PortalRankIndustry>();
		for (PlatformRankIndustry data : res) {
			String value = "0";
			if (data.getDemandNum() == null) {
				data.setDemandNum(new BigDecimal(value.trim()));
			}
			PortalRankIndustry target = new PortalRankIndustry();
			BeanUtils.copyProperties(target, data);
			dataList.add(target);
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
	@Transactional(value = "transactionManager")
	public void saveIndustryInfos(List<PortalRankIndustry> dataList) {
		del();
		save(dataList);

	}

	@Transactional(value = "transactionManager")
	public void save(List<PortalRankIndustry> dataList) {
		// 新数据迁入
		dao.save(dataList);
	}

	@Transactional(value = "transactionManager")
	public void del() {
		// 先删除门户表数据
		delDao.delPortalData("delIndustry");
	}


}
