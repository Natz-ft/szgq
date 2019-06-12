package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformOperateLog;
/**
 * 
* @ClassName: PlatformOperateLogDao
* @Description: TODO( ������־����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:20:41
*
 */
public interface PlatformOperateLogDao extends PagingAndSortingRepository<PlatformOperateLog, String>, JpaSpecificationExecutor<PlatformOperateLog>{
    
	/**
     * <����SystemBuPlatformOperateLogtton idɾ��>
     * @param buttonId
     */
    @Modifying
    @Query("delete from PlatformOperateLog where id=?1")
    public  void deleteByPlatformOperateLog( String  id );
}
