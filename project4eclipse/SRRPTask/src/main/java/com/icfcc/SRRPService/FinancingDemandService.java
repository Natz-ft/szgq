/**
 * 
 */
package com.icfcc.SRRPService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSONArray;
import com.icfcc.SRRPDao.s.jpa.entity.portal.FinancingInfoDetail;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalDemand;
import com.icfcc.SRRPDao.s.jpa.repository.portal.FinancingInfoDetailDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PlatformPortalDemandDao;
import com.icfcc.SRRPDao.s1.jpa.entity.CompanyBase;
import com.icfcc.SRRPDao.s1.jpa.entity.CompanyBaseSupplement;
import com.icfcc.SRRPDao.s1.jpa.entity.FinacingDemandInfo;
import com.icfcc.SRRPDao.s1.jpa.entity.FinacingEvent;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyBaseDao;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyBaseSupplementDao;
import com.icfcc.SRRPDao.s1.jpa.repository.FinacingEventDao;
import com.icfcc.SRRPDao.s1.jpa.repository.FinacingInfoDao;
import com.icfcc.SRRPService.redis.RedisUtil;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.util.DigitFormatUtil;

import redis.clients.jedis.Jedis;

/**
 * @author lijj 门户-首页-融资需求
 *
 */
@Service
public class FinancingDemandService  {

	@Autowired
	private FinancingInfoDetailDao finacingInfoDetailDao;
	@Autowired
	private FinacingInfoDao finacingInfoDao;
	@Autowired
	private CompanyBaseDao companyBaseDao;
	@Autowired
	private CompanyBaseSupplementDao companyBaseSupplementDao;
	@Autowired
	private PlatformPortalDemandDao gataWayDemandDao;
	@Autowired
	private FinacingEventDao finacingEventDao;
	@Autowired
	private RedisUtil redisUtil;
	public static final String STATUS_CLOSE = "99";
	public static final String STATUS_DRAFT = "00";
	public static final String STATUS_SUCESS = "03";
	public static final String open = "0";


	/**
	 * 获得最新n个融资需求
	 * @param n
	 * @return
	 */
	// @Scheduled(cron="0/5 * * * * ? ") //每5秒执行一次
		public void extractFinacingDemand(int n) {
			List<PlatformPortalDemand> fincaingDemandlist = NewestRelsult(n);
			this.saveLatest(fincaingDemandlist);
		}
		
	@Transactional(value = "transactionManager1")
	public List<PlatformPortalDemand> NewestRelsult(int n) {

    
		Specification<FinacingDemandInfo> spec = new Specification<FinacingDemandInfo>() {
			@Override
			public Predicate toPredicate(Root<FinacingDemandInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> statusP = root.get("status");
				Path<String> openP = root.get("open");
				Path<String> revokeFlag = root.get("revokeFlag");
				Predicate p = null;
				p = cb.conjunction();
				p = cb.and(p,cb.notEqual(statusP, STATUS_CLOSE));
				p = cb.and(p,cb.notEqual(statusP, STATUS_DRAFT));
//				p = cb.and(p,cb.notEqual(statusP, STATUS_SUCESS));
				p = cb.and(p,cb.equal(openP, open));
				p = cb.and(p,cb.equal(revokeFlag, "0"));
				return p;
			}
		};

		Sort sort = new Sort(Direction.DESC, "operdate");
		Pageable page = new PageRequest(0, n, sort);
		Page<FinacingDemandInfo> res = this.finacingInfoDao.findAll(spec, page);
		List<PlatformPortalDemand> extraRes = new ArrayList<PlatformPortalDemand>();
		for (FinacingDemandInfo info : res.getContent()) {
			 if(isFinacingEvent(info.getStatus(),info.getInfoId())){
				 CompanyBase company = companyBaseDao.findById(info.getEnterpriseId());
					CompanyBaseSupplement companyExt = this.companyBaseSupplementDao.findById(info.getEnterpriseId());
					PlatformPortalDemand gwd = new PlatformPortalDemand();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
					gwd.setInfoId(info.getInfoId());
					gwd.setProjectName(info.getProjectName());
					gwd.setAmount(info.getAmountMax());
					String tmpAmount = "";
					if (info.getAmountMin() == null || "".equals(info.getAmountMin())) {
						tmpAmount = "0"+"M";
					} else {
						tmpAmount = DigitFormatUtil.digitFormat(info.getAmountMin()).toString()+"M";
					}
					if (info.getAmountMax() == null || "".equals(info.getAmountMax())) {
						tmpAmount += "~" + "0"+"M";
					} else {
						tmpAmount += "~"
								+ DigitFormatUtil.digitFormat(info.getAmountMax()).toString()+"M";
					}
					
					gwd.setFinancingPurposes(tmpAmount);
					
					String tmpScale = "";
//					if ("0".equals(info.getSell())) {
						if (info.getScaleMin() == null || "".equals(info.getScaleMin())) {
							tmpScale = "0.00%";
						} else {
							tmpScale = DigitFormatUtil.digitFormat(info.getScaleMin()) + "%";
						}
						if (info.getScaleMax() == null || "".equals(info.getScaleMax())) {
							tmpScale += "~" + "0.00%";
						} else {
							tmpScale += "~" + DigitFormatUtil.digitFormat(info.getScaleMax())
									+ "%";
						}
//					}
//			 else {
//						tmpScale= "N/A";
//					}
					gwd.setScale(info.getSell()+","+tmpScale);
					if(companyExt!=null){
						gwd.setIndustry(companyExt.getIndustry());
					}
					gwd.setFinacing_turn(info.getFinacingTurn());
					gwd.setOperdate(info.getOperdate());
					if(company!=null){
						gwd.setRearea(company.getRearea());
					}
					gwd.setRelName(info.getRelName());
					gwd.setRelPhone(info.getRelPhone());
					gwd.setDescription(info.getDescription());
					extraRes.add(gwd);
			 }
			
		}
		
		return extraRes;
	}
public boolean isFinacingEvent(String status,String infoId){
	if("03".equals(status)){//判断是否有完成的融资事件
		//融资需求查询已经进行投资的融资事件
	     FinacingEvent finacingEvent=finacingEventDao.findOKFinacingEventByInfoId(infoId);
	     //获取最小的完成时间
	     //获取当前时间
	     Date now = new Date();
	     //判断两个时间是否相差三个月
	     if(finacingEvent!=null) {
	    	 int diffrent=  differentDays(finacingEvent.getOperdate(),now);
	    	 if(diffrent > 90){
	    		 return false;
	    		 
	    	 }else{
	    		 return true;
	    	 }
	     }else {
	    	 return false;
	     }
	}else{
		return true;
	}
}
public static int differentDays(Date date1,Date date2)
{
    int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
    return days;
}
	/**
	 * 门户存储融资需求
	 * @param res
	 */
	@Transactional(value = "transactionManager")
	public void saveLatest(List<PlatformPortalDemand> res) {
		this.gataWayDemandDao.deleteAll();
		this.gataWayDemandDao.save(res);
	}

	public Page<FinancingInfoDetail> getLatest(int n) {
		Page<FinancingInfoDetail> res = null;
		Sort sort = new Sort(Direction.DESC, "operdate");
		Pageable page = new PageRequest(0, n, sort);
		res = this.finacingInfoDetailDao.findAll(page);
		return res;
	}
	
	
}
