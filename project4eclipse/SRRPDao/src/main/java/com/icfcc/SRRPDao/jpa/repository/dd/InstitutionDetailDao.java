package com.icfcc.SRRPDao.jpa.repository.dd;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.dd.InstitutionDetail;
import com.icfcc.SRRPDao.jpa.entity.dd.PlatformDicDetail;

public interface InstitutionDetailDao extends PagingAndSortingRepository<InstitutionDetail, String>,
        JpaSpecificationExecutor<InstitutionDetail> {

    @Query("from InstitutionDetail where type = ?1 ")
    List<InstitutionDetail> findByType(String type);

}
