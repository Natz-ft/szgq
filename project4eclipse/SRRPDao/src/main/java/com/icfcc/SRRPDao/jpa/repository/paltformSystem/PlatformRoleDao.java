package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformRole;
/**
 * 
* @ClassName: PlatformRoleDao
* @Description: TODO(��ɫ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:22:43
*
 */
public interface PlatformRoleDao extends PagingAndSortingRepository<PlatformRole, Long>,
    JpaSpecificationExecutor<PlatformRole>
{
    /**
     * <��ѯ���� ��ѡroleid�� ������ɫ�б�>
     * 
     * @param roleId
     * @return
     */
    List<PlatformRole> findByIdNotIn(List<Long> roleId);
    
    /**
     * <��ѯ���н�ɫ>
     * 
     * @return
     */
    List<PlatformRole> findAll();
    
    /**
     * <һ�仰���ܼ���>
     * 
     * @param roleName
     * @return
     */
    PlatformRole findByName(String roleName);
    
    /**
     * <һ�仰���ܼ���>
     * 
     * @param roleId
     * @param roleName
     * @return
     */
    @Query("select p from PlatformRole p where name =?2 and id !=?1")
    PlatformRole findByIdAndName(Long roleId, String roleName);
}
