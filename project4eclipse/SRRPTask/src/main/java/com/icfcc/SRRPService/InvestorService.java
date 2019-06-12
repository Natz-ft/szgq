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

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSONArray;
import com.icfcc.SRRPDao.s.jpa.entity.portal.InvestorQuery;
import com.icfcc.SRRPDao.s.jpa.repository.portal.InvestorQueryDao;
import com.icfcc.SRRPDao.s1.jpa.entity.CompanyBase;
import com.icfcc.SRRPDao.s1.jpa.entity.Investor;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformConfig;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyBaseDao;
import com.icfcc.SRRPDao.s1.jpa.repository.InvestorDao;
import com.icfcc.SRRPDao.s1.jpa.repository.InvestorLoanDao;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformConfigDao;
import com.icfcc.SRRPService.redis.RedisUtil;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;

/**
 * @author lijj
 *
 */
@Service
public class InvestorService   {

	@Autowired
	private InvestorDao investorDao;
	@Autowired
	private InvestorLoanDao investorLoanDao;
	@Autowired
	private InvestorQueryDao investorQueryDao;
	@Autowired
	private CompanyBaseDao companyBaseDao;
	@Autowired
	private PlatformConfigDao systemConfigDao;
	@Autowired
	private RedisUtil redisUtil;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

	/**
	 * 获取门户-投资机构
	 * 
	 * @param n
	 * @return
	 */
	@Transactional(value = "transactionManager1")
	public List<InvestorQuery> generateLatest(int n) {

		List<InvestorQuery> res = new ArrayList<InvestorQuery>();
		try {
			Specification<Investor> spec = new Specification<Investor>() {
				@Override
				public Predicate toPredicate(Root<Investor> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					Path<String> statusP = root.get("stopFlag");
					Path<String> auditstatusP = root.get("auditStatus");
					Predicate p = null;
					p = cb.conjunction();
					p = cb.and(p, cb.notEqual(statusP, "2"));// 启用状态的用户
					p = cb.and(p, cb.notEqual(auditstatusP,
							SRRPConstant.REFISTER_STATUS_PENGDING));// 注册未审核
					p = cb.and(p, cb.notEqual(auditstatusP,
							SRRPConstant.REFISTER_STATUS_NOPASS));// 注册审核不通过
					p = cb.and(p, cb.notEqual(auditstatusP,
							"0"));// 注册审核不通过
					p = cb.and(p, cb.notEqual(auditstatusP,
							"23"));// 注册审核不通过
					p = cb.and(p, cb.notEqual(auditstatusP,
							"2"));// 注册审核不通过
					return p;
				}
			};
			Sort sort = new Sort(Direction.DESC, "createTime");
			Pageable page = new PageRequest(0, n, sort);
			Page<Investor> tres = this.investorDao.findAll(spec, page);
			Map<String, String> ddMap = reverseDD(SRRPConstant.DD_CAPITALPOWER);
			for (Investor o : tres.getContent()) {
				List<String> investorLoans = this.investorLoanDao
						.findByInvestorDisId(o.getInvestorId());
				String successTrade = this.generateTrades(investorLoans);
				InvestorQuery oo = new InvestorQuery();
				oo.setInvestorId(o.getInvestorId());
				oo.setDesciption(o.getCorepersonnel());
				oo.setCapital(o.getCapital().toString());
				oo.setCapitalType(o.getCapitalType());
				oo.setFinanceTrade(o.getFinanceTrade());
				oo.setFinanceStage(o.getFinanceStage());
				oo.setName(o.getName());
				oo.setOrgType(o.getOrgType());
				oo.setRegisteTime(o.getRegisteTime());
				oo.setSuccessTrade(successTrade);
				// logo
				oo.setLogo(o.getLogoPath());
				// 成立时间
				oo.setSetTime(sdf.format(o.getRegisteTime()));
				// 资本实力
				if (ddMap != null && ddMap.size() > 0) {
					for (String key : ddMap.keySet()) {
						String amountBetween = ddMap.get(key);
						String cpatialMin=o.getCapital().split("~")[0];
						String cpatialMax=o.getCapital().split("~")[1];

						if (amountBetween.indexOf("-") > -1) {
							String start = amountBetween.split("-")[0];
							String end = amountBetween.split("-")[1];
							if (Double.parseDouble(start) <= Double.parseDouble(cpatialMin)
									&&  Double.parseDouble(cpatialMin) < Double.parseDouble(start)) {
								oo.setCapitalType(key);
							}
						} else {
							if (Double.parseDouble(cpatialMin) >= Double
									.parseDouble(amountBetween)) {
								oo.setCapitalType(key);
							}
						}
					}
				}
				res.add(oo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 门户存储投资人
	 * 
	 * @param res
	 */
	@Transactional(value = "transactionManager")
	public void saveLatest(List<InvestorQuery> res) throws Exception {
		this.investorQueryDao.deleteAll();
		this.investorQueryDao.save(res);
	}

	/**
	 * 完成移动投资机构到门户
	 * 
	 * @param n
	 */

	public void completeMoveInvestor(int n) throws Exception {
		List<InvestorQuery> res = this.generateLatest(n);
		this.saveLatest(res);
	}

	@Transactional(value = "transactionManager")
	private String generateTrades(List<String> loans) throws Exception {
		String res = "";
		for (String o : loans) {
			CompanyBase oo = companyBaseDao.findById(o);
			if (oo != null) {
				if (oo.getName() != null && !"".equals(oo.getName())) {

					if (res.equals("")) {
						res += oo.getName() + "<span></span>";
					} else {
						res += oo.getName() + "<span></span>";
					}
				}
			}

		}
		return res;
	}

	private Map<String, String> reverseDD(String ddType) {
		String tmp = "0";

		Map<String, String> ddMap = new HashMap<String, String>();
		Jedis jedis = null;
		try {
			jedis = redisUtil.getConnection();
			String ddValue = jedis.get(ddType);
			List<DD> ddList = JSONArray.parseArray(ddValue, DD.class);
			if (ddList != null && ddList.size() > 0) {
				for (DD dd : ddList) {
					if (dd.getDicName().indexOf("以内") > -1) {
						tmp = tmp
								+ "-"
								+ dd.getDicName().split("以内")[0].replaceAll(
										"M", "");
					} else if (dd.getDicName().indexOf("大于") > -1) {
						tmp = dd.getDicName().replaceAll("大于", "")
								.replaceAll("M", "");
					} else {
						tmp = dd.getDicName().replaceAll("至", "-")
								.replaceAll("M", "");
					}
					ddMap.put(dd.getDicCode(), tmp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			redisUtil.closeConnection(jedis);

		}

		return ddMap;
	}

}
