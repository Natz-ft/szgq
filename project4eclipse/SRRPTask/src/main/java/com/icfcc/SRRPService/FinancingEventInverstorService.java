/**
 * 
 */
package com.icfcc.SRRPService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.icfcc.SRRPDao.s.jpa.entity.portal.platformPortalEventQueryInvestor;
import com.icfcc.SRRPDao.s.jpa.repository.portal.platformPortalEventQueryInvestorDao;
import com.icfcc.SRRPDao.s1.jpa.entity.FinacingEventInvestor;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformConfig;
import com.icfcc.SRRPDao.s1.jpa.repository.FinacingEventInvestorDao;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformConfigDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * @author lijj
 *
 */
@Service
public class FinancingEventInverstorService {
	@Autowired
	private FinacingEventInvestorDao finacingEventInvestorDao;
	@Autowired
	private platformPortalEventQueryInvestorDao ppqueryDao;
	@Autowired
	private PlatformConfigDao systemConfigDao;
	/**
	 * 门户投融事件
	 * 
	 * @param res
	 */
	@Transactional(value = "transactionManager")
	public void saveLatest(List<platformPortalEventQueryInvestor> res) {
		
		this.ppqueryDao.save(res);
	}
	@Transactional(value = "transactionManager")
	public void deleteLatest() {
			this.ppqueryDao.deleteAll();
	}
	/**
	 * 获取最新的n个融资事件
	 * 
	 * @param n
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Transactional(value = "transactionManager1")
	public List<platformPortalEventQueryInvestor> generateLatest(int n) throws IllegalAccessException, InvocationTargetException {
		List<platformPortalEventQueryInvestor> res = new ArrayList<platformPortalEventQueryInvestor>();
			List<FinacingEventInvestor> list = finacingEventInvestorDao.getFinacingEventInvestor(n);
			for (FinacingEventInvestor feinvestor : list) {
				platformPortalEventQueryInvestor pfpLinks = new platformPortalEventQueryInvestor();
				BeanUtils.copyProperties(pfpLinks, feinvestor);
				res.add(pfpLinks);
			}
		

		return res;
	}

	
}
