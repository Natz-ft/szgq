package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformTotalStatic;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformTotalStaticIndex;

@Component
public class PortalTotalStaticNativeDao extends BaseNativeQueryDao {

    // 平台数据汇总
    String excueteSql = "select a.time_id as countdate,sum(a.finacing_count)  as finacingCount,sum(a.demand_amount)  as demondcount,sum(a.loan_amount) as finaccount,sum(a.org_count) as investorcount,sum(a.enterprise_count) as enterprisecount from rp_report_total a group by a.time_id ";

    //首页-统计
    String indexPortalSql = " select a.time_id as countdate,a.finacing_count  as finacingCount,a.demand_amount as demondcount,a.loan_amount as finaccount,a.org_count as investorcount,a.enterprise_count as enterprisecount,a.enterprise_success_count as enterprisesuccesscount from rp_report_total_index a ";
    
    /**
     * 
     * <p>功能描述:[运行成果-历史数据]</p>
     * @return
     * @author:zhanglf
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    @SuppressWarnings("unchecked")
    public List<PlatformTotalStatic> portTotalStatic() {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<PlatformTotalStatic> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            Query query = entityManager.createNativeQuery(excueteSql, PlatformTotalStatic.class);
            res = (List<PlatformTotalStatic>) query.getResultList();
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
     * 
     * <p>功能描述:[首页-统计值]</p>
     * @return
     * @author:zhanglf
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    @SuppressWarnings("unchecked")
    public List<PlatformTotalStaticIndex> idexStatic() {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<PlatformTotalStaticIndex> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            Query query = entityManager.createNativeQuery(indexPortalSql, PlatformTotalStaticIndex.class);
            res = (List<PlatformTotalStaticIndex>) query.getResultList();
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
