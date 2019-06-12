package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyUnionInfoResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

import io.netty.util.internal.StringUtil;

@Component
public class CompanyInfoDaoImpl extends BaseNativeQueryDao {

	String sql = "select bb.*, cc.score from (select cb.*,cbs.industry,cbs.financingphase from rp_company_base cb LEFT JOIN	 rp_company_base_supplement cbs on cb.enterprise_id = cbs.enterprise_id) bb left join rp_company_creditscores cc  on cc.creditcode = bb.code and bb.codetype = cc.creditype where 1=1 ";

	/**
	 * 查询所有企业信息列表
	 * 
	 * @param sql
	 * @param companyInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUnionInfoResult> getCompanyList(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<CompanyUnionInfoResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			List list1 = new ArrayList(); 
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getRearea())) {
					whereCase.append(" and bb.rearea in( :rearea) ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					for(Object industryStr:list){
						if(industryStr.toString().length()<4){
							list1.add(industryStr.toString());
						}
					}
					if(list1!=null && list1.size()>0){
						whereCase.append(" and ( bb.industry in( :industry) ");
						whereCase.append(" or left(bb.industry,2) in( :industry1) ) ");
					}else{
						whereCase.append(" and bb.industry in( :industry) ");

					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinancingphase())) {
					whereCase.append(" and bb.financingphase in( :financingphase )");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getNameAndCode())) {
					whereCase.append(" and ( bb.name like :name or bb.code like :name ) ");
				}
				whereCase.append(" and( bb.audit_status = '04' or bb.audit_status = '05'  or bb.audit_status = '06'  or bb.audit_status = '22' ) ");
				if ((!StringUtil.isNullOrEmpty(queryCondition.getSortord()))
						&& queryCondition.getSortord().equals("01")) {
					whereCase.append(" order by bb.regcapital desc ");
				}
				// 拼接分頁sql
				whereCase.append(this.getPageInfos(queryCondition));
			}

			Query query = entityManager.createNativeQuery(sql + whereCase.toString(), CompanyUnionInfoResult.class);

			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getRearea())) {
					String[] values = queryCondition.getRearea().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("rearea", list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("industry", list);
					if(list1!=null && list1.size()>0){
						query.setParameter("industry1", list1);
					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinancingphase())) {
					String[] values = queryCondition.getFinancingphase().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("financingphase", list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getNameAndCode())) {
					query.setParameter("name","%" + queryCondition.getNameAndCode() + "%");
				}
			}
			res = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	/**
	 * 查询所有企业信息总数
	 * 
	 * @param sql
	 * @param queryCondition
	 * @return
	 */
	public Object getCompanyCount(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer countSql = new StringBuffer(" select count(*) as resultnum from ( ");
			countSql.append(sql);
			List list1 = new ArrayList(); 
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getRearea())) {
					countSql.append(" and bb.rearea in( :rearea) ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					
					for(Object industryStr:list){
						if(industryStr.toString().length()<4){
							list1.add(industryStr.toString());
						}
					}
					if(list1!=null && list1.size()>0){
						countSql.append(" and (bb.industry in( :industry) ");

						countSql.append(" or left(bb.industry,2) in( :industry1)) ");
					}else{
						countSql.append(" and bb.industry in( :industry) ");

					}				
					}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinancingphase())) {
					countSql.append(" and bb.financingphase in( :financingphase) ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getNameAndCode())) {
					countSql.append(" and ( bb.name like :name or bb.code like :name ) ");
				}
				countSql.append(" and( bb.audit_status = '04' or bb.audit_status = '05'  or bb.audit_status = '06'  or bb.audit_status = '22' ) ");
				if ((!StringUtil.isNullOrEmpty(queryCondition.getSortord()))
						&& queryCondition.getSortord().equals("02")) {
					countSql.append(" order by bb.regcapital desc ");
				}
			}
			countSql.append(") result ");
			Query query = entityManager.createNativeQuery(countSql.toString());
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getRearea())) {
					String[] values = queryCondition.getRearea().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("rearea", list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("industry", list);
					if(list1!=null && list1.size()>0){
						query.setParameter("industry1", list1);
					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinancingphase())) {
					String[] values = queryCondition.getFinancingphase().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("financingphase", list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getNameAndCode())) {
					query.setParameter("name", "%" + queryCondition.getNameAndCode() + "%");
				}
			}
			res = query.getSingleResult();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

}
