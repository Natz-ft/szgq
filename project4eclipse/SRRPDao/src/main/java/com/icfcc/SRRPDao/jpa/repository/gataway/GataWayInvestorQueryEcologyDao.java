package com.icfcc.SRRPDao.jpa.repository.gataway;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorQuery;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

@Component
public class GataWayInvestorQueryEcologyDao extends BaseNativeQueryDao {

    private String sql = "select * from platform_portal_investor_query where 1=1 ";

    @SuppressWarnings("unchecked")
    public List<GataWayInvestorQuery> findInvestorList(QueryCondition queryCondition) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<GataWayInvestorQuery> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer whereCase = new StringBuffer();
            // 机构类型
            if (null != queryCondition) {
                if (queryCondition.getOrgType() != null
                        && !SRRPConstant.QUERYCONDITION_DEFAULT.equals(queryCondition.getOrgType())) {
                    whereCase.append(" and org_type= :org_type ");
                }
                if (queryCondition.getCapitalPower() != null) {
                	String amountRange = RedisGetValue.getValueByCode(
							SRRPConstant.DD_CAPITALPOWER,
							queryCondition.getCapitalPower());
                	if (amountRange.contains("至")) {
						String[] amoutArray = amountRange.split("至");
						int amountmin=Integer.parseInt(amoutArray[0].replaceAll("M", ""))/100;
						int amountmax=Integer.parseInt(amoutArray[1].replaceAll("M", ""))/100;	
						
						whereCase.append(" and (substring_index(capital, '~', 1) > "
								+ amountmin + " and substring_index(capital, '~', -1) <="
								+ amountmax
								+ " )  ");
					} else {
						if (amountRange.equals("100M以内") ) {
							String[] amoutArray = amountRange.split("M");
							int amountmin=Integer.parseInt(amoutArray[0])/100;
							whereCase.append(" and substring_index(capital, '~', -1) <="
									+ amountmin);
						}
						if (amountRange.equals("大于1000M") ) {
							whereCase.append(" and substring_index(capital, '~', -1) >10");
						}
					}
                    //whereCase.append(" and capital_type= :capitalType ");
                }
                if (queryCondition.getSetTime() != null
                        && !SRRPConstant.QUERYCONDITION_DEFAULT.equals(queryCondition.getSetTime())) {
                	if(queryCondition.getSetTime().equals("yearbefor")){
                		 whereCase.append(" and settime < :setTime "); 
                	}else{
                		 whereCase.append(" and settime= :setTime ");
                	}
                   
                }

            }
            whereCase.append(" order by capital desc");
            Query query = entityManager.createNativeQuery(sql + whereCase.toString(), GataWayInvestorQuery.class);
            if (null != queryCondition) {
                if (queryCondition.getOrgType() != null
                        && !SRRPConstant.QUERYCONDITION_DEFAULT.equals(queryCondition.getOrgType())) {
                    query.setParameter("org_type", queryCondition.getOrgType());
                }
//                if (queryCondition.getCapitalPower() != null
//                        && !SRRPConstant.QUERYCONDITION_DEFAULT.equals(queryCondition.getCapitalPower())) {
//                    query.setParameter("capitalType", queryCondition.getCapitalPower());
//                }
                if (queryCondition.getSetTime() != null
                        && !SRRPConstant.QUERYCONDITION_DEFAULT.equals(queryCondition.getSetTime())) {
                	if(queryCondition.getSetTime().equals("yearbefor")){
                		  query.setParameter("setTime", "2007");
                	}else{
              		  query.setParameter("setTime", queryCondition.getSetTime());

                	}
                  
                }

            }
            res = (List<GataWayInvestorQuery>) query.getResultList();
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
