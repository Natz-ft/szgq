package com.icfcc.SRRPDao.jpa.repository.gataway;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorRegiter;
/**
 * 
* @Description: 在线注册入正式表
* @author zhanglf
* @date 2017年9月19日 下午5:59:37
*
 */
public interface GataWayInvestorRegisterDao extends PagingAndSortingRepository<GataWayInvestorRegiter, String>, JpaSpecificationExecutor<GataWayInvestorRegiter> {
    @Query("from GataWayInvestorRegiter ")
    List<GataWayInvestorRegiter> findAll();
    
    @Query("select u from GataWayInvestorRegiter u where  u.certno=? ")
    GataWayInvestorRegiter findById(String certno);
    
    
}
