package com.icfcc.credit.platform.jpa.repository.system;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.system.SrrpSynchronizeJobLog;



/**
 * 
* @ClassName: PlatformNewsDao
* @Description: TODO( 后台同步任务)
* @author hugt
* @date 2017年9月14日 下午7:20:12
*
 */
@Component
public interface SrrpSynchronizeJobLogDao extends JpaRepository<SrrpSynchronizeJobLog, Long>, JpaSpecificationExecutor<SrrpSynchronizeJobLog>
{
	
	
}
