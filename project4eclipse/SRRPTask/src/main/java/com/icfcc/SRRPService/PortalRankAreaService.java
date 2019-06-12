package com.icfcc.SRRPService;

import java.lang.reflect.InvocationTargetException;
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
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalRankArea;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalRankAreaDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankArea;
import com.icfcc.SRRPDao.s1.jpa.repository.PortalRankAreaNativeDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * 
 * <p>
 * 功能描述: 榜单排名-企业榜单
 * </p>
 * 
 * @author zhanglf
 * @version
 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
@Transactional(value = "transactionManager")
public class PortalRankAreaService   {
	@Autowired
	private PortalRankAreaDao dao;

	@Autowired
	private PortalRankAreaNativeDao nativeDao;

	@Autowired
	private PortalDelNativeDao delDao;

	/**
	 * 
	 * <p>
	 * 功能描述:[待迁移到门户地区榜单]
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Transactional(value = "transactionManager1")
	public List<PortalRankArea> portalRankAreaList() throws IllegalAccessException, InvocationTargetException {
		List<PortalRankArea> dataList = null;
		List<PlatformRankArea> res = nativeDao.portalRankAreaList();
		if (res != null && res.size() > 0) {
			dataList = new ArrayList<PortalRankArea>();
			for (PlatformRankArea data : res) {
				PortalRankArea target = new PortalRankArea();
				String value = "0";
					if (data.getDemandNum() == null) {
						data.setDemandNum(new BigDecimal(value.trim()));
					}
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
	@Transactional(value = "transactionManager")
	public void saveAreasInfos(List<PortalRankArea> dataList) {
		// 先删除门户表数据
		del();
		// 新数据迁入
		save(dataList);
	}

	@Transactional(value = "transactionManager")
	public void save(List<PortalRankArea> dataList) {
		// 新数据迁入
		dao.save(dataList);
	}

	@Transactional(value = "transactionManager")
	public void del() {
		// 先删除门户表数据
		delDao.delPortalData("delArea");
	}

}
