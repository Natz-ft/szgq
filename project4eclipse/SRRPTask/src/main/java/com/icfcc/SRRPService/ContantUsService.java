/**
 * 
 */
package com.icfcc.SRRPService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalContantUs;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalContantUsDist;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PlatformPortalContantUsDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PlatformPortalContantUsDistDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformContactus;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformContactusDist;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformContactusDao;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformContactusDistDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * @author lijj
 * 友情链接业务
 */
@Service
@Transactional(value = "transactionManager")
public class ContantUsService {
	@Autowired
	private PlatformPortalContantUsDao portalContantUsDao;
	@Autowired
	private PlatformContactusDao contantUsDao;
	@Autowired
	private PlatformPortalContantUsDistDao portalContantUsDistDao;
	@Autowired
	private PlatformContactusDistDao contantDistUsDao;
	
	
	/**
	 * 获取最新的n条友情链接
	 * @param n
	 * @return
	 */
	@Transactional(value = "transactionManager1")
	public List<PlatformPortalContantUs> getLatestContantUs() throws Exception{
		Sort sort = new Sort(Direction.DESC, "createTime");
//		Pageable page = new PageRequest(0, n, sort);
		List<PlatformPortalContantUs> res=new ArrayList<PlatformPortalContantUs>();
		    List<PlatformContactus> platformPage=contantUsDao.findAll(sort);
			for (PlatformContactus pfCutantUs : platformPage) {
				PlatformPortalContantUs pfpCutantUs=new PlatformPortalContantUs();
				pfpCutantUs.setAddress(pfCutantUs.getAdress());
				pfpCutantUs.setCuFax(pfCutantUs.getFax());
				pfpCutantUs.setCuHotLine(pfCutantUs.getHotline());
				pfpCutantUs.setCuName(pfCutantUs.getName());
				pfpCutantUs.setCuZipCode(pfCutantUs.getZipCode());
				pfpCutantUs.setMail(pfCutantUs.getMail());
				res.add(pfpCutantUs);
			}
		return res;
	}
	@Transactional(value = "transactionManager")
	public void saveContantUs(List<PlatformPortalContantUs> res) throws Exception{
		this.portalContantUsDao.deleteAll();
		this.portalContantUsDao.save(res);
	}
	/**
	 * 获取最新的n条友情链接
	 * @param n
	 * @return
	 */
	@Transactional(value = "transactionManager1")
	public List<PlatformPortalContantUsDist> getLatestContantUsDist() throws Exception{
		Sort sort = new Sort(Direction.DESC, "createTime");
//		Pageable page = new PageRequest(0, n, sort);
		List<PlatformPortalContantUsDist> res=new ArrayList<PlatformPortalContantUsDist>();
		List<PlatformContactusDist> platformPage=(List<PlatformContactusDist>) contantDistUsDao.findAll(sort);
			for (PlatformContactusDist pfCutantUsDist : platformPage) {
				PlatformPortalContantUsDist pfpCutantUsDist=new PlatformPortalContantUsDist();
				pfpCutantUsDist.setDistHotLine(pfCutantUsDist.getHotline());
				pfpCutantUsDist.setDistName(pfCutantUsDist.getName());
				res.add(pfpCutantUsDist);
			}
		return res;
	}
	@Transactional(value = "transactionManager")
	public void saveContantUsDist(List<PlatformPortalContantUsDist> res) throws Exception{
		this.portalContantUsDistDao.deleteAll();
		this.portalContantUsDistDao.save(res);
	}
}
