package com.icfcc.SRRPDao.jpa.repository.gataway;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorAuditPending;
/**
 * 
* @Description: 在线注册入待审核表
* @author zhanglf
* @date 2017年9月19日 下午5:59:37
*
 */
public interface GataWayInvestorAuditRegisterDao extends PagingAndSortingRepository<GataWayInvestorAuditPending, String>, JpaSpecificationExecutor<GataWayInvestorAuditPending> {
    @Query("from GataWayInvestorAuditPending")
    List<GataWayInvestorAuditPending> findAll();
}
