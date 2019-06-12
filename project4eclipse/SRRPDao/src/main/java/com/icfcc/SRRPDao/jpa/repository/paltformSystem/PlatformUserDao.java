package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
/**
 * 
* @ClassName: PlatformUserDao
* @Description: TODO(�û���Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:23:41
*
 */
public interface PlatformUserDao extends PagingAndSortingRepository<PlatformUser, String>
,JpaSpecificationExecutor<PlatformUser> 
{
	@Query("select u from PlatformUser u where u.username=?1")
	PlatformUser findByUserName(String username);
	@Query("select u from PlatformUser u where u.org=?1")
	PlatformUser findUserByInvestId(String id);
	@Query("select u from PlatformUser u where u.org=?1")
	List<PlatformUser> findByUserOrg(String username);
	@Query("select u from PlatformUser u where u.org=?1 and u.desc1='0'")
	PlatformUser findUser(String username);
	
	@Modifying
	@Query("delete from PlatformUser u where u.org =?1 ")
	public void deleteByInvestId(String orgId);
	
	@Modifying
	@Query("update PlatformUser u set u.stopFlag = ?2 where u.org =?1 ")
	public void updateStatus(String orgId, int stopFlag);
	@Modifying
	@Query("update PlatformUser u set u.stopFlag = ?1 where u.org =?2 ")
	public void updateStatus(int stopFlag, String orgId);
	
    @Query("select u from PlatformUser u where u.org in ('04','06')")
    List<PlatformUser> findSjrbUser();
    @Query("select u from PlatformUser u where u.org in ('03')")
    List<PlatformUser> findQjrbUser();
    

}
