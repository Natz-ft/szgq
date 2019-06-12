package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformOrg;
/**
 * 
* @ClassName: PlatformOrgDao
* @Description: TODO(Ͷ�ʻ�����Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:21:10
*
 */
public interface PlatformOrgDao extends PagingAndSortingRepository<PlatformOrg, String>, JpaSpecificationExecutor<PlatformOrg> {

	/**
	 * orgid ��ѯ
	 * 
	 * @param orgid
	 * @return
	 */
	@Query("from PlatformOrg where orgid =?1 and deleteState=?2 ")
	List<PlatformOrg> findByOrgid(String orgid, Short deleteState);

	/**
	 * orgid ɾ��
	 * 
	 * @param buttonCode
	 */
	@Modifying
	@Query("delete from PlatformOrg where orgid =?1")
	void deleteByOrgid(String orgid);

	@Modifying
	@Query("update PlatformOrg set deleteState = ?1  where orgid=?2")
	void updateByOrgid(Short deleteState, String orgid);

	@Query("from PlatformOrg where id =?1 and deleteState=?2")
	List<PlatformOrg> findById(String id, Short deleteState);

	@Query("from PlatformOrg where  deleteState=?1 and recordStopFlag=?2")
	List<PlatformOrg> findByAllValid(Short deleteState, String recordStopFlag);

}
