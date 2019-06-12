package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformRoleMenu;
/**
 * 
* @ClassName: PlatformRoleMenuDao
* @Description: TODO(�˵�Ȩ�޵���ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:23:10
*
 */
public interface PlatformRoleMenuDao extends PagingAndSortingRepository<PlatformRoleMenu, String>, JpaSpecificationExecutor<PlatformRoleMenu> {
	/**
     * <���ݲ˵�Id�ͽ�ɫIdɾ����ťȨ��>
     * 
     * @param menuId
     * @param roleId
     * 
     */
    @Modifying
    @Query("delete from PlatformRoleMenu u where u.menuId=?1")
    void deleteByMenuId(String menuId);
    
    List<PlatformRoleMenu> findByRoleId(Long roleId);
    
    @Modifying
    @Query("delete from PlatformRoleMenu u where u.roleId=?1")
    void deleteByRoleId(Long id);
    
    
    
    /**
     * <һ�仰���ܼ���>
     * 
     * @param roleIdList
     * @return
     * 
     */
    @Query("select distinct(menuId) from  PlatformRoleMenu u where u.roleId in(?1)")
    List<String> findMenuIdByRoleId(List<Long> roleIdList);
}
