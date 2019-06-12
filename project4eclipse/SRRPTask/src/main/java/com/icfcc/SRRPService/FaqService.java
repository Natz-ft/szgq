/**
 * 
 */
package com.icfcc.SRRPService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalFaqAndNotice;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PlatformPortalFaqAndNoticeDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformFaq;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformFaqDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * @author lijj
 * 友情链接业务
 */
@Service
@Transactional(value = "transactionManager")
public class FaqService  {
	@Autowired
	private PlatformPortalFaqAndNoticeDao protalFaqDao;
	@Autowired
	private PlatformFaqDao platformFaqsDao;
	public static final String Faq = "0001";//公告通知
	public static final String Notic = "0002";//常见问题  
	
	/**
	 * 获取最新的n条友情链接
	 * @param n
	 * @return
	 */
	public List<PlatformPortalFaqAndNotice> getLatestFaq(int n) throws Exception{
		Specification<PlatformFaq> spec = new Specification<PlatformFaq>() {
			@Override
			public Predicate toPredicate(Root<PlatformFaq> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> statusP = root.get("type");
				Predicate p = null;
				p = cb.conjunction();
				p = cb.and(cb.equal(statusP, Faq));
				return p;
			} 
		};
		Specification<PlatformFaq> spec1 = new Specification<PlatformFaq>() {
			@Override
			public Predicate toPredicate(Root<PlatformFaq> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> statusP = root.get("type");
				Predicate p = null;
				p = cb.conjunction();
				p = cb.and(cb.equal(statusP, Notic));
				return p;
			} 
		};
		Sort sort = new Sort(Direction.DESC, "createTime");
		Pageable page = new PageRequest(0, n, sort);
		List<PlatformPortalFaqAndNotice> res=new ArrayList<PlatformPortalFaqAndNotice>();
		    Page<PlatformFaq> platformPage=platformFaqsDao.findAll(spec,page);
		    Page<PlatformFaq> platformPage1=platformFaqsDao.findAll(spec1,page);
			for (PlatformFaq pfFaqs : platformPage.getContent()) {
				PlatformPortalFaqAndNotice pfpFaqs=new PlatformPortalFaqAndNotice();
				BeanUtils.copyProperties(pfpFaqs,pfFaqs);
				res.add(pfpFaqs);
			}
			for (PlatformFaq pfFaqs : platformPage1.getContent()) {
				PlatformPortalFaqAndNotice pfpFaqs=new PlatformPortalFaqAndNotice();
				BeanUtils.copyProperties(pfpFaqs,pfFaqs);
				res.add(pfpFaqs);
			}
		return res;
	}

	public void save(List<PlatformPortalFaqAndNotice> res) {
		this.protalFaqDao.deleteAll();
		this.protalFaqDao.save(res);
	}
	
}
