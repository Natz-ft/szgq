package com.icfcc.SRRPService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalDelNativeDao;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalRankFinacingTurn;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalRankFinacTurnDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankFinacingTurn;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformRankFinacTurnDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * <融资轮次>
 */
@Service
@Transactional(value = "transactionManager")
public class PortalRankFinacTurnService   {
	private static Logger log = LoggerFactory
			.getLogger(PortalRankFinacTurnService.class);
	@Autowired
	private PlatformRankFinacTurnDao dao;
	@Autowired
	private PortalRankFinacTurnDao portalDao;
	@Autowired
	private PortalDelNativeDao delDao;

	/**
	 * 
	 * <p>
	 * 功能描述:[获取待迁移门户融資輪次榜单]
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Transactional(value = "transactionManager1")
	public List<PortalRankFinacingTurn> portalRankFinacTurnList() throws IllegalAccessException, InvocationTargetException {
		List<PortalRankFinacingTurn> dataList = null;
			List<PlatformRankFinacingTurn> res = (List<PlatformRankFinacingTurn>) dao
					.findAll();
			dataList = new ArrayList<PortalRankFinacingTurn>();
			for (PlatformRankFinacingTurn data : res) {
				PortalRankFinacingTurn target = new PortalRankFinacingTurn();
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
	public void saveFinacingTurnInfos(List<PortalRankFinacingTurn> dataList) {
		del();
		save(dataList);

	}

	@Transactional(value = "transactionManager")
	public void save(List<PortalRankFinacingTurn> dataList) {
		// 新数据迁入
		portalDao.save(dataList);
	}

	@Transactional(value = "transactionManager")
	public void del() {
		// 先删除门户表数据
		delDao.delPortalData("delFinacTurn");
	}


}
