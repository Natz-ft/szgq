package com.icfcc.credit.platform.jpa.repository.system;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformOperateLog;
/**
 * 
* @ClassName: PlatformOperateLogDao
* @Description: TODO( 操作日志的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:20:41
*
 */
public interface PlatformOperateLogDao extends PagingAndSortingRepository<PlatformOperateLog, String>, JpaSpecificationExecutor<PlatformOperateLog>{
    
	/**
     * <根据SystemBuPlatformOperateLogtton id删除>
     * @param buttonId
     */
    @Modifying
    @Query("delete from PlatformOperateLog where id=?1")
    public  void deleteByPlatformOperateLog( String  id );
}
