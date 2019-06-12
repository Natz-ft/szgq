/**
 * 
 */
package com.icfcc.SRRPService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalPartner;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PlatformPortalPartnerDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformPartner;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformPartnerDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * @author lijj 友情链接业务
 */
@Service
@Transactional(value = "transactionManager")
public class PartnerService   {
	@Autowired
	private PlatformPortalPartnerDao protalPartnerDao;
	@Autowired
	private PlatformPartnerDao platformPartnersDao;

	/**
	 * 完成移动投资事件到门户
	 * 
	 * @param n
	 */
	public void extractPartner(int n) throws Exception {
		List<PlatformPortalPartner> res = this.getLatestPartner(n);
		System.out.println("res=============" + res.size());
		this.save(res);

	}

	/**
	 * 获取最新的n条友情链接
	 * 
	 * @param n
	 * @return
	 */
	public List<PlatformPortalPartner> getLatestPartner(int n) throws Exception {
		Sort sort = new Sort(Direction.DESC, "createTime");
		Pageable page = new PageRequest(0, n, sort);
		List<PlatformPortalPartner> res = new ArrayList<PlatformPortalPartner>();
		Page<PlatformPartner> platformPage = platformPartnersDao.findAll(page);
		for (PlatformPartner pfPartners : platformPage.getContent()) {
			PlatformPortalPartner pfpPartners = new PlatformPortalPartner();
			BeanUtils.copyProperties(pfpPartners, pfPartners);
			res.add(pfpPartners);
		}
		return res;
	}

	public void save(List<PlatformPortalPartner> res) throws Exception {
		this.protalPartnerDao.deleteAll();
		this.protalPartnerDao.save(res);
	}

}
