package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserRole;

/**
 * 
 * @ClassName: PlatformUserRoleDao
 * @Description: TODO(ʵ���û�Ȩ�޵���ɾ�Ĳ�)
 * @author hugt
 * @date 2017��9��14�� ����7:24:41
 *
 */
public interface PlatformUserRoleDao extends
		PagingAndSortingRepository<PlatformUserRole, Long>,
		JpaSpecificationExecutor<PlatformUserRole> {

	/**
	 * <����userId ��ѯ �û���ɫ��ϵ>
	 * 
	 * @param userId
	 * @return
	 * 
	 */
	@Query(" select r from PlatformUserRole r where r.userId=?1 ")
	List<PlatformUserRole> findByUserId(String userId);

	/**
	 * <����userId + roleId ɾ�� �û���ɫ��ϵ>
	 * 
	 * @param uId
	 * @param rId
	 * 
	 */
	@Modifying
	@Query("delete  from PlatformUserRole s where s.userId=?1 and s.roleId=?2 ")
	void deleteByUserRoleId(String uId, Long rId);

	/**
	 * <����userIdɾ�����еĹ�����ϵ�����ڽ�ɫ���ʱ>
	 * 
	 * @param userId
	 */
	@Modifying
	@Query("delete from PlatformUserRole s where s.userId=?1")
	void deleteByUserId(String userId);

	/**
	 * <���� userId �� roleId��ѯ �Ƿ���� �û���ɫ��ϵ>
	 * 
	 * @param uId
	 * @param rId
	 * @return
	 */
	@Query(" select COUNT(r) from PlatformUserRole r where r.userId=?1  and r.roleId=?2")
	Long getCountByUserRoleId(String uId, Long rId);

	@Query(" select pfur from PlatformUserRole pfur where pfur.userId=?1 ")
	PlatformUserRole getRoleById(String userId);

	/**
	 * <�����û�id ��ѯ ��ɫid>
	 * 
	 * @param string
	 * @return
	 */
	@Query(" select roleId from PlatformUserRole r where r.userId=?1 ")
	List<Long> findRoleIdByUserId(String string);

}
