package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformConfig;

/**
 * 
* @ClassName: PlatformConfigDao
* @Description: TODO(������Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:15:02
*
 */
public interface PlatformConfigDao extends PagingAndSortingRepository<PlatformConfig, Long>,
    JpaSpecificationExecutor<PlatformConfig>
{
    /**
     * <���ݲ�������ѯ>
     * 
     * @param configName ��������
     * @return
     */
    PlatformConfig findByConfigName(String configName);
}
