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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalLinks;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PlatformPortalLinksDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformLinks;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformLinksDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * @author lijj 友情链接业务
 */
@Service
@Transactional(value = "transactionManager")
public class FriendlyLinksService   {
	@Autowired
	private PlatformPortalLinksDao protalLinkDao;
	@Autowired
	private PlatformLinksDao platformLinksDao;

	/**
	 * 完成移动投资事件到门户
	 * 
	 * @param n
	 * @throws Exception 
	 */
	public void extractFriendlyLinks(int n) throws Exception {
		List<PlatformPortalLinks> res = this.getLatestLink(n);
		this.save(res);

	}

	/**
	 * 获取最新的n条友情链接
	 * 
	 * @param n
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public List<PlatformPortalLinks> getLatestLink(int n) throws Exception {
		Sort sort = new Sort(Direction.DESC, "createTime");
		Pageable page = new PageRequest(0, n, sort);
		List<PlatformPortalLinks> res = new ArrayList<PlatformPortalLinks>();
			Page<PlatformLinks> platformPage = platformLinksDao.findAll(page);
			for (PlatformLinks pfLinks : platformPage.getContent()) {
				PlatformPortalLinks pfpLinks = new PlatformPortalLinks();
				BeanUtils.copyProperties(pfpLinks, pfLinks);
				res.add(pfpLinks);
			}
		return res;
	}

	public void save(List<PlatformPortalLinks> res) {
		this.protalLinkDao.deleteAll();
		this.protalLinkDao.save(res);
	}

}
