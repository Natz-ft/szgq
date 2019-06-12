package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserRole;
/**
 * 
* @ClassName: SysUserRoleDao
* @Description: TODO(�û�Ȩ�޵���ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:25:37
*
 */
public interface SysUserRoleDao extends PagingAndSortingRepository<PlatformUserRole, Long>, JpaSpecificationExecutor<PlatformUserRole> {
	/**
	 * <����userId ��ѯ �û���ɫ��ϵ>
	 * 
	 * @param userId
	 * @return
	 * 
	 */
	@Query(" select r from PlatformUserRole r where r.userId=?1 ")
	List<PlatformUserRole> findByUserId(String userId);
}
