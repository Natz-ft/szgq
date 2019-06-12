package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankInvestor;

@Component
public class PortalRankInvestorNativeDao extends BaseNativeQueryDao {

    String excueteSql = " select a.rid, a.name as name,a.amount as amount,a.time_id as countdate,a.rank_count as raking ,a.demand_count as solveorgnum ,a.company_count as investnum from rp_report_investor a   ";

    /**
     * 
     * <p>
     * 功能描述:[待迁移门户机构榜单]
     * </p>
     * 
     * @return
     * @author:zhanglf
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    @SuppressWarnings("unchecked")
    public List<PlatformRankInvestor> portalRankInvestorList() {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<PlatformRankInvestor> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            Query query = entityManager.createNativeQuery(excueteSql, PlatformRankInvestor.class);
            res = (List<PlatformRankInvestor>) query.getResultList();
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
