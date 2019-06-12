package com.icfcc.SRRPDao.jpa.repository.gataway;

import io.netty.util.internal.StringUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfoResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class TestPageDao extends BaseNativeQueryDao {

    private String sql = " select fd.*,cb.name,cbs.industry,cc.TOTALSCORE from rp_finacing_demand fd,rp_company_base cb,rp_company_base_supplement cbs,rp_company_creditscore cc where fd.enterprise_id = cb.enterprise_id and fd.enterprise_id=cbs.enterprise_id and fd.enterprise_id=cc.ENTERPRISE_ID and fd.status!='草稿状态' ";

    @Autowired(required = false)
    /**
     * 查询所有融资需求信息列表
     * 
     * @param sql
     * @param finacingDemand
     * @return
     */
    public List<FinacingDemandInfoResult> getFinacingDemandInfoList(QueryCondition queryCondition) {
        EntityManager entityManager = getEntityManager();
        StringBuffer whereCase = new StringBuffer();
        if (null != queryCondition) {
            if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
                whereCase.append(" and cbs.INDUSTRY= :Industry ");
            }
        }
        whereCase.append(" order by fd.FOLLOW_TIME desc ");
        whereCase.append(this.getPageInfos(queryCondition));

        Query query = entityManager.createNativeQuery(sql + whereCase.toString(), FinacingDemandInfoResult.class);

        if (null != queryCondition) {
            if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
                query.setParameter("Industry", queryCondition.getIndustry());

            }
        }
        
        List<FinacingDemandInfoResult> res = (List<FinacingDemandInfoResult>) query.getResultList();
        return res;
    }

    /**
     * 查询所有融资需求信息总数
     * 
     * @param sql
     * @param finacingDemand
     * @return
     */
    public Object getFinacingDemandInfoCount(QueryCondition queryCondition) {
        EntityManager entityManager = getEntityManager();
        StringBuffer coutSql = new StringBuffer(" select count(*) as resultnum from ( ");
        coutSql.append(sql);
        if (null != queryCondition) {
            if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
                coutSql.append(" and cbs.INDUSTRY= :Industry ");
            }
        }
        coutSql.append(" ) result ");
        System.out.println(coutSql.toString());
        Query query = entityManager.createNativeQuery(coutSql.toString());
        if (null != queryCondition) {
            if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
                query.setParameter("Industry", queryCondition.getIndustry());

            }

        }
        return query.getSingleResult();
    }
}
