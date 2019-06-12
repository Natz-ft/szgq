package com.icfcc.SRRPDao.jpa.repository.creditscore;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.creditscore.score.RpCompanyCreditscores;

/**
 * 
* @ClassName: PlatformNewsDao
* @Description: TODO( 查询机构总数)
* @author xb
* @date 2017年9月14日 下午7:20:12
*
 */
public interface CompanyCreditScoresDao extends PagingAndSortingRepository<RpCompanyCreditscores, String>, JpaSpecificationExecutor<RpCompanyCreditscores> {
	
	@Query("select c from RpCompanyCreditscores c where c.creditcode=?1")
	List<RpCompanyCreditscores> findBycreditcode(String creditcode);
	 
	
	
	
}
