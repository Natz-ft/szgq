package com.icfcc.SRRPDao.s1.jpa.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;



/**
 * 
* @ClassName: PlatformNewsDao
* @Description: TODO( 后台同步任务)
* @author hugt
* @date 2017年9月14日 下午7:20:12
*
 */
@Component
public interface SrrpSynchronizeTaskDao extends JpaRepository<SrrpSynchronizeJobBean, Long>, JpaSpecificationExecutor<SrrpSynchronizeJobBean>
{
	@Query("select t from SrrpSynchronizeJobBean t where t.jobName=?1")
	public SrrpSynchronizeJobBean findByJobName(String jonName);
	
	@Query("select t from SrrpSynchronizeJobBean t where t.isValid='0' order by job_id")
	public List<SrrpSynchronizeJobBean> getTask();
	
	
}
