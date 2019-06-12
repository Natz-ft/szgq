package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformButton;

public interface PlatformButtonDao extends PagingAndSortingRepository<PlatformButton, String>,
    JpaSpecificationExecutor<PlatformButton>
{
    /**
     * <根据菜单ID获取菜单下的功能权限> <功能详细描述>
     * 
     * @param parentId
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<PlatformButton> findByMenuId(Long menuId);
    
    /**
     * <根据SystemButtonCode获取SystemButton信息> <功能详细描述>
     * 
     * @param parentId
     * @return
     * @see [类、类#方法、类#成员]
     */
    PlatformButton findByButtonCode(String ButtonCode);
    
    /**
     * <根据SystemButton code删除>
     * 
     * @param SystemButtonCode
     */
    @Modifying
    @Query("delete from PlatformButton where buttonCode=?1")
    void deleteByButtonCode(String buttonCode);
    
    /**
     * <根据SystemButton buttonId删除>
     * @param buttonId
     */
    @Modifying
    @Query("delete from PlatformButton where buttonId=?1")
    void deleteByButtonId( Long buttonId);
    
    /**
     * 
     * @param buttonId
     * @return
     */
    PlatformButton  findByButtonId( Long buttonId);
    
}
