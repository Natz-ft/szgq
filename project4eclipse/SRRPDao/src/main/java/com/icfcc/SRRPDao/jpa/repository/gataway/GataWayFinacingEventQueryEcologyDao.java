package com.icfcc.SRRPDao.jpa.repository.gataway;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayFinacingEventQuery;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class GataWayFinacingEventQueryEcologyDao extends BaseNativeQueryDao {

    private String sql = "select * from platform_portal_finacing_event_query where 1=1 ";

    @SuppressWarnings("unchecked")
    public List<GataWayFinacingEventQuery> findEventList(QueryCondition queryCondition) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<GataWayFinacingEventQuery> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer whereCase = new StringBuffer();
            List list1 = new ArrayList(); 
            if (null != queryCondition) {
                if (queryCondition.getIndustry() != null && !"".equals(queryCondition.getIndustry())) {
//                    whereCase.append(" and industry= :industry ");
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
                if (queryCondition.getFinacing_turn() != null && !"".equals(queryCondition.getFinacing_turn())) {
                    whereCase.append(" and finacing_turn= :finacing_turn ");
                }
            }
            whereCase.append(" order by operdate desc ");
            if (null != queryCondition) {
                if (queryCondition.isPortal()) {
                    whereCase.append(" limit 0,11 ");
                }
            }
            Query query = entityManager.createNativeQuery(sql + whereCase.toString(), GataWayFinacingEventQuery.class);
            if (null != queryCondition) {
                if (queryCondition.getIndustry() != null && !"".equals(queryCondition.getIndustry())) {
//                    query.setParameter("industry", queryCondition.getIndustry());
                	String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("industry", list);
					if(list1!=null && list1.size()>0){
						query.setParameter("industry1", list1);
					}

                }
                if (queryCondition.getFinacing_turn() != null && !"".equals(queryCondition.getFinacing_turn())) {
                    query.setParameter("finacing_turn", queryCondition.getFinacing_turn());

                }
            }
            res = (List<GataWayFinacingEventQuery>) query.getResultList();
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
