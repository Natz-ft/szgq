package com.icfcc.SRRPDao.jpa.repository.dd;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.dd.PlatformDicDetail;

public interface PlatformDicDetailDao extends PagingAndSortingRepository<PlatformDicDetail, String>,
        JpaSpecificationExecutor<PlatformDicDetail> {

    @Query("from PlatformDicDetail where validityFlag = '0' ")
    List<PlatformDicDetail> findAll();

}
