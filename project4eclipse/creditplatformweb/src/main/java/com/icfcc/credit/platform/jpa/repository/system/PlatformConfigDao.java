package com.icfcc.credit.platform.jpa.repository.system;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformConfig;
/**
 * 
* @ClassName: PlatformConfigDao
* @Description: TODO(配置信息得增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:15:02
*
 */
public interface PlatformConfigDao extends PagingAndSortingRepository<PlatformConfig, Long>,
    JpaSpecificationExecutor<PlatformConfig>
{
    /**
     * <根据参数名查询>
     * 
     * @param configName 参数名称
     * @return
     */
    PlatformConfig findByConfigName(String configName);
}
