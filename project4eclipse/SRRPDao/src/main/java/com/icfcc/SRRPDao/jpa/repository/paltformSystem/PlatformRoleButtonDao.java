package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformRoleButton;
/**
 * 
* @ClassName: PlatformRoleButtonDao
* @Description: TODO(��ɫ��ť��Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:21:51
*
 */
public interface PlatformRoleButtonDao extends PagingAndSortingRepository<PlatformRoleButton, Long>,
    JpaSpecificationExecutor<PlatformRoleButton>
{
    /**
     * ���ݽ�ɫID��ѯ��ɫ��ťȨ�޹�ϵ
     * 
     * @param roleId
     * @return
     * 
     */
    List<PlatformRoleButton> findByRoleId(Long roleId);
    
    /**
     * ���ݰ�ť�����ѯ��ɫ��ťȨ�޹�ϵ
     * 
     * @param buttonCode
     * @return
     * 
     */
    List<PlatformRoleButton> findByButtonCode(String buttonCode);
    
    /**
     * ɾ����ɫ��Ӧ�İ�ťȨ��
     * 
     * @param roleId
     * 
     */
    @Modifying
    @Query("delete from PlatformRoleButton u where u.roleId=?1")
    void deleteByRoleId(Long roleId);
    
    /**
     * ���ݰ�ť����ɾ����ťȨ��
     * 
     * @param buttonCode
     * 
     */
    @Modifying
    @Query("delete from PlatformRoleButton u where u.buttonCode=?1")
    void deleteByButtonCode(String buttonCode);
    
    /**
     * ���ݰ�ť����ɾ����ťȨ��
     * 
     * @param buttonCode
     * 
     */
    @Modifying
    @Query("update PlatformRoleButton u set u.buttonCode = ?1 where u.roleId = ?2")
    void updateButtonCodeByRoleId(String buttonCode, Long roleId);
}
