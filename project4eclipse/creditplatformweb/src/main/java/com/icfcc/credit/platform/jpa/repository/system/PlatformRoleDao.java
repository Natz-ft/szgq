package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformRole;
/**
 * 
* @ClassName: PlatformRoleDao
* @Description: TODO(角色的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:22:43
*
 */
public interface PlatformRoleDao extends PagingAndSortingRepository<PlatformRole, Long>,
    JpaSpecificationExecutor<PlatformRole>
{
    /**
     * <查询不在 所选roleid的 其他角色列表>
     * 
     * @param roleId
     * @return
     */
    List<PlatformRole> findByIdNotIn(List<Long> roleId);
    
    /**
     * <查询所有角色>
     * 
     * @return
     */
    List<PlatformRole> findAll();
    
    /**
     * <一句话功能简述>
     * 
     * @param roleName
     * @return
     */
    PlatformRole findByName(String roleName);
    
    /**
     * <一句话功能简述>
     * 
     * @param roleId
     * @param roleName
     * @return
     */
    @Query("select p from PlatformRole p where name =?2 and id !=?1")
    PlatformRole findByIdAndName(Long roleId, String roleName);
}
