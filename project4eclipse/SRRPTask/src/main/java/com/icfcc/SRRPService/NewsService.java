/**
 * 
 */
package com.icfcc.SRRPService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalInfos;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalNews;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PlatformPortalInforsDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PlatformPortalNewsDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformConfig;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformNews;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformConfigDao;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformNewsDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * @author lijj 新闻业务
 */
@Service
@Transactional(value = "transactionManager")
public class NewsService   {

	@Autowired
	private PlatformNewsDao platformNewsDao;
	@Autowired
	private PlatformPortalNewsDao gataWayNewsDao;
	@Autowired
	private PlatformPortalInforsDao platformPortalInforsDao;
	public static final String NEWS_NEW = "01";// 新闻动态
	public static final String NEWS_INDUSTRY = "02";// 行业动态
	public static final String NEWS_POLICY = "03";// 政策指南动态
	@Autowired
	private PlatformConfigDao systemConfigDao;

	/**
	 * 获取最新的n条新闻动态
	 * 
	 * @param n
	 * @return
	 */
	// @Scheduled(cron="0/5 * * * * ? ") //每5秒执行一次
	public void extractNews(int n) throws Exception {
		List<PlatformPortalNews> fincaingDemandlist = NewestRelsult(n);
		this.saveLatest(fincaingDemandlist);
	}

	@Transactional(value = "transactionManager1")
	public List<PlatformPortalNews> NewestRelsult(int n)  {
		Specification<PlatformNews> spec = new Specification<PlatformNews>() {
			@Override
			public Predicate toPredicate(Root<PlatformNews> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> statusP = root.get("newstype");
				Predicate p = null;
				p = cb.conjunction();
				p = cb.or(cb.equal(statusP, NEWS_NEW));
				return p;
			}
		};
		Sort sort = new Sort(Direction.DESC, "createTime");
		Pageable page = new PageRequest(0, n, sort);
		List<PlatformPortalNews> extraRes = new ArrayList<PlatformPortalNews>();
		Page<PlatformNews> res = this.platformNewsDao.findAll(spec, page);
		for (PlatformNews pfnews : res.getContent()) {
			PlatformPortalNews gatWayNews = new PlatformPortalNews();
			gatWayNews.setNewsId(pfnews.getId());
			gatWayNews.setNewsTile(pfnews.getTitle());
			gatWayNews.setNewsContents(pfnews.getContent());
			gatWayNews.setNewEditor(pfnews.getNewsEditor());
			gatWayNews.setNewSource(pfnews.getNewsSource());
			gatWayNews.setNewsPicture(pfnews.getPhoto());
			gatWayNews.setNewsCreateUser(pfnews.getCreateUser());
			gatWayNews.setNewCreateDate(pfnews.getCreateTime());
			gatWayNews.setUplodfileName(pfnews.getUplodfileName());
			gatWayNews.setPurpose(pfnews.getNewstype());
			gatWayNews.setNewsDate(pfnews.getNewsDate());
			extraRes.add(gatWayNews);
		}
		return extraRes;
	}

	/**
	 * 门户存储融资需求
	 * 
	 * @param res
	 */
	@Transactional(value = "transactionManager")
	public void saveLatest(List<PlatformPortalNews> res)  {
		if (res != null) {
			this.gataWayNewsDao.deleteAll();
			this.gataWayNewsDao.save(res);
		}
	}

	@Transactional(value = "transactionManager1")
	public List<PlatformPortalInfos> NewestRelsultNewsInfors() throws Exception {
		List<PlatformPortalInfos> extraRes = new ArrayList<PlatformPortalInfos>();
		Specification<PlatformNews> specIndustry = new Specification<PlatformNews>() {
			@Override
			public Predicate toPredicate(Root<PlatformNews> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> statusP = root.get("newstype");
				Predicate p = null;
				p = cb.conjunction();
				p = cb.or(cb.equal(statusP, NEWS_INDUSTRY), cb.equal(statusP, NEWS_POLICY));
				return p;
			}
		};
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<PlatformNews> resNewsIndustry = this.platformNewsDao.findAll(specIndustry,sort);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (PlatformNews pfnews : resNewsIndustry) {
			PlatformPortalInfos gatWayNews = new PlatformPortalInfos();
			String contentStr = "";
			if (pfnews.getContent() != null) {
				contentStr = new String(pfnews.getContent(), SRRPConstant.DEFAULTCHARTS);
			}
			gatWayNews.setContent(contentStr);
			gatWayNews.setInfoId(String.valueOf(pfnews.getId()));
			gatWayNews.setInfoType(pfnews.getNewstype());
			//gatWayNews.setTime(sdf.format(pfnews.getCreateTime()));
			gatWayNews.setTime(pfnews.getNewsDate());
			gatWayNews.setTitle(pfnews.getTitle());
			if(!StringUtils.isEmpty(pfnews.getPhoto())&& !StringUtils.isEmpty(pfnews.getUplodfileName())){
			    gatWayNews.setPhoto(pfnews.getPhoto());
	            gatWayNews.setUplodfileName(pfnews.getUplodfileName());
			}
			
			extraRes.add(gatWayNews);
		}
		Specification<PlatformNews> specPolicy = new Specification<PlatformNews>() {
			@Override
			public Predicate toPredicate(Root<PlatformNews> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> statusP = root.get("newstype");
				Predicate p = null;
				p = cb.conjunction();
				p = cb.and(cb.equal(statusP, NEWS_POLICY));
				return p;
			}
		};
		List<PlatformNews> resPolicy = this.platformNewsDao.findAll(specPolicy);
		for (PlatformNews pfnews : resPolicy) {
			PlatformPortalInfos gatWayNews = new PlatformPortalInfos();
			String contentStr = "";
			if (pfnews.getContent() != null) {
				contentStr = new String(pfnews.getContent(), SRRPConstant.DEFAULTCHARTS);
			}
			gatWayNews.setContent(contentStr);
			gatWayNews.setInfoId(String.valueOf(pfnews.getId()));
			gatWayNews.setInfoType(pfnews.getNewstype());
			//gatWayNews.setTime(sdf.format(pfnews.getCreateTime()));
			gatWayNews.setTime(pfnews.getNewsDate());
			gatWayNews.setTitle(pfnews.getTitle());
			if(!StringUtils.isEmpty(pfnews.getPhoto())&& !StringUtils.isEmpty(pfnews.getUplodfileName())){
                gatWayNews.setPhoto(pfnews.getPhoto());
                gatWayNews.setUplodfileName(pfnews.getUplodfileName());
            }
			extraRes.add(gatWayNews);
		}

		return extraRes;
	}

	/**
	 * 门户存储融资需求
	 * 
	 * @param res
	 */
	@Transactional(value = "transactionManager")
	public void saveLatestNewsInfors(List<PlatformPortalInfos> res) throws Exception {
		if (res != null) {
			this.platformPortalInforsDao.deleteAll();
			this.platformPortalInforsDao.save(res);
		}

	}

}
