package com.icfcc.credit.platform.jpa.repository.system;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.system.PlatformUser;
import com.icfcc.credit.platform.jpa.entity.system.SrrpSynchronizeJobBean;



/**
 * 
* @ClassName: PlatformNewsDao
* @Description: TODO( 新闻动态信息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:20:12
*
 */
@Component
public interface SrrpSynchronizeTaskDao extends PagingAndSortingRepository<SrrpSynchronizeJobBean, String>
,JpaSpecificationExecutor<SrrpSynchronizeJobBean> 
{
	@Query("select t from SrrpSynchronizeJobBean t where t.jobName=?1")
	public SrrpSynchronizeJobBean findByJobName(String jonName);
	
	@Query("select t from SrrpSynchronizeJobBean t where t.jobId=?1")
	public SrrpSynchronizeJobBean findById(Long jobId);
	
	@Query("select t from SrrpSynchronizeJobBean t where t.isValid='0'")
	public List<SrrpSynchronizeJobBean> getTask();
	
	
}
