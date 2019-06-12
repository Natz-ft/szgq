package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformMenu;
/**
 * 
* @ClassName: PlatformMenuDao
* @Description: TODO( �˵���Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:19:26
*
 */
public interface PlatformMenuDao extends PagingAndSortingRepository<PlatformMenu, String>,
    JpaSpecificationExecutor<PlatformMenu>
{
    /**
     * ���ݸ�id��ȡmenu
     * 
     * @param parentId
     * @return
     */
    List<PlatformMenu> findByParentId(String parentId);
    
    /**
     * ����id��ȡmenu
     * 
     * @param id
     * @return
     */
    List<PlatformMenu> findById(String id);
    
    /**
     * <���ݸ�id��ȡmenu>
     * 
     * @param parentId
     * @return
     */
    List<PlatformMenu> findByParentIdOrderBySortAsc(String parentId);
    
    /**
     * <���²˵�����>
     * 
     * @param parentId
     * @param sort
     */
    @Modifying
    @Query("update PlatformMenu set sort=sort-1  where parentId =?1 and sort>?2")
    void updateSort(String parentId, Integer sort);
    
    /**
     * <���ݸ��˵� ��ѯ ��������>
     * 
     * @param parentId
     * @return
     */
    @Query("select MAX(sort) from PlatformMenu where parentId =?1")
    Integer getMaxSortNumByParentId(String parentId);
    
    /**
     * <��ѯ�˵�>
     * 
     * @param menuIdList
     * @return
     */
    @Query("select s from PlatformMenu s where s.id in (?1)")
    List<PlatformMenu> findByMenuIds(List<String> menuIdList);
    
}
