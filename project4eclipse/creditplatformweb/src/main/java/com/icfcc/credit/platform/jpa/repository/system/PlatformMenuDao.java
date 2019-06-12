package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformMenu;
/**
 * 
* @ClassName: PlatformMenuDao
* @Description: TODO( 菜单信息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:19:26
*
 */
public interface PlatformMenuDao extends PagingAndSortingRepository<PlatformMenu, String>,
    JpaSpecificationExecutor<PlatformMenu>
{
    /**
     * 根据父id获取menu
     * 
     * @param parentId
     * @return
     */
    List<PlatformMenu> findByParentId(String parentId);
    
    /**
     * 根据id获取menu
     * 
     * @param id
     * @return
     */
    List<PlatformMenu> findById(String id);
    
    /**
     * <根据父id获取menu>
     * 
     * @param parentId
     * @return
     */
    List<PlatformMenu> findByParentIdOrderBySortAsc(String parentId);
    
    /**
     * <更新菜单排序>
     * 
     * @param parentId
     * @param sort
     */
    @Modifying
    @Query("update PlatformMenu set sort=sort-1  where parentId =?1 and sort>?2")
    void updateSort(String parentId, Integer sort);
    
    /**
     * <根据父菜单 查询 最大排序号>
     * 
     * @param parentId
     * @return
     */
    @Query("select MAX(sort) from PlatformMenu where parentId =?1")
    Integer getMaxSortNumByParentId(String parentId);
    
    /**
     * <查询菜单>
     * 
     * @param menuIdList
     * @return
     */
    @Query("select s from PlatformMenu s where s.id in (?1)")
    List<PlatformMenu> findByMenuIds(List<String> menuIdList);
    
}
