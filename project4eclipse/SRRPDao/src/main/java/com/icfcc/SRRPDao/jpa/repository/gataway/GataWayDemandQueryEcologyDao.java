package com.icfcc.SRRPDao.jpa.repository.gataway;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayDemandQuery;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class GataWayDemandQueryEcologyDao extends BaseNativeQueryDao {

    private String sql = "select * from platform_portal_demand_query where 1=1 ";

    @SuppressWarnings("unchecked")
    public List<GataWayDemandQuery> findDemandList(QueryCondition queryCondition) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<GataWayDemandQuery> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer whereCase = new StringBuffer();
            List list1 = new ArrayList(); 
            if (null != queryCondition) {
                if (queryCondition.getIndustry() != null && !"".equals(queryCondition.getIndustry())) {
                	String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					for(Object industryStr:list){
						if(industryStr.toString().length()<4){
							list1.add(industryStr.toString());
						}
					}
					if(list1!=null && list1.size()>0){
						whereCase.append(" and (industry in( :industry) ");

						whereCase.append(" or left(industry,2) in( :industry1)) ");
					}else{
						whereCase.append(" and industry in( :industry) ");
					}

                }
                if (queryCondition.getArea() != null && !"".equals(queryCondition.getArea())) {
                    whereCase.append(" and rearea= :rearea ");

                }
                if (queryCondition.getEnterprisePeriod() != null && !"".equals(queryCondition.getEnterprisePeriod())) {
                    whereCase.append(" and enterpriseperiod= :enterprisePeriod ");
                    
                }
                if (queryCondition.getFinanceAmout() != null && !"".equals(queryCondition.getFinanceAmout())) {
                    whereCase.append(" and finacingmoney= :finacingMoney ");
                    
                }
                if (queryCondition.getFinacingTurn() != null && !"".equals(queryCondition.getFinacingTurn())) {
                    whereCase.append(" and financingmode= :finacingTurn ");
                    
                }
            }
            whereCase.append(" order by operdate desc");
            Query query = entityManager.createNativeQuery(sql + whereCase.toString(), GataWayDemandQuery.class);
            if (null != queryCondition) {
                if (queryCondition.getIndustry() != null && !"".equals(queryCondition.getIndustry())) {
                	String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("industry", list);
					if(list1!=null && list1.size()>0){
						query.setParameter("industry1", list1);
					}
                }
                if (queryCondition.getArea() != null && !"".equals(queryCondition.getArea())) {
                    query.setParameter("rearea", queryCondition.getArea());

                }
                if (queryCondition.getEnterprisePeriod() != null && !"".equals(queryCondition.getEnterprisePeriod())) {
                    query.setParameter("enterprisePeriod", queryCondition.getEnterprisePeriod());
                }
                if (queryCondition.getFinanceAmout() != null && !"".equals(queryCondition.getFinanceAmout())) {
                	System.out.println("finacingMoney================="+queryCondition.getFinanceAmout());
                    query.setParameter("finacingMoney", queryCondition.getFinanceAmout());
                    
                }
                if (queryCondition.getFinacingTurn() != null && !"".equals(queryCondition.getFinacingTurn())) {
                	 query.setParameter("finacingTurn", queryCondition.getFinacingTurn());
                    
                }
            }
            res = (List<GataWayDemandQuery>) query.getResultList();
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
