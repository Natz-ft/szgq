/**
 * 
 */
package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.PublishBeanForCharge;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

/**
 * @author lijj
 *
 */
@Component
public class InvestorPublishDaoImpl extends BaseNativeQueryDao {

    /**
     * 给主管部门获得披露信息列表
     * 
     * @param infoType
     * @param projectName
     * @param start
     * @param end
     * @param page
     * @param size
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PublishBeanForCharge> findForCharge(String infoType, String projectName, Date start, Date end, Integer page,
            Integer size) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<PublishBeanForCharge> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            int startnum = 0;
            StringBuffer sql = new StringBuffer(
                    "select t1.publish_id,t2.enterprise_id,t3.name enterprise_name,t2.project_name,t1.infotitle,t1.infotype,t2.event_id,t1.publish_time from rp_investor_publish t1,rp_finacing_event t2,rp_company_base t3 where t1.event_id=t2.event_id and t2.enterprise_id=t3.enterprise_id");
            if (StringUtils.isNotEmpty(infoType)) {
                sql.append(" and t1.infotype=:infoType ");
            }
            if (StringUtils.isNotEmpty(projectName)) {
                sql.append(" and t3.name like :projectName ");
            }
            if (start != null) {
                sql.append(" and t1.publish_time>=:start ");
            }
            if (end != null) {
                sql.append(" and t1.publish_time<=:end ");
            }
            sql.append(" order by t1.publish_time desc");
            if (page != null && size != null) {
                sql.append(" limit :start ,:size");
            }
            Query q = entityManager.createNativeQuery(sql.toString(), PublishBeanForCharge.class);
            if (StringUtils.isNotEmpty(infoType)) {
                q.setParameter("infoType", infoType);
            }
            if (StringUtils.isNotEmpty(projectName)) {
                q.setParameter("projectName", "%" + projectName);
            }
            if (start != null) {
                q.setParameter("start", start);
            }
            if (end != null) {
                q.setParameter("end", end);
            }
            if (page != null && size != null) {
                startnum = page * size;
                q.setParameter("start", startnum);
                q.setParameter("size", size);
            }
            res = q.getResultList();
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
