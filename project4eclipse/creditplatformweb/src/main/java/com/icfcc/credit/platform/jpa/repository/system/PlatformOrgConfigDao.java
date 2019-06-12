package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformOrgConfig;

public interface PlatformOrgConfigDao extends PagingAndSortingRepository<PlatformOrgConfig, String>, JpaSpecificationExecutor<PlatformOrgConfig> {

	/**
	 * ID 查询
	 * 
	 * @param orgid
	 * @return
	 */
	List<PlatformOrgConfig> findById(String id);

	/**
	 * orgid 查询
	 * 
	 * @param orgid
	 * @return
	 */
	List<PlatformOrgConfig> findByAppid(String appid);

	/**
	 * orgid 删除
	 * 
	 * @param buttonCode
	 */
	@Modifying
	@Query("delete from PlatformOrgConfig where id =?1")
	void deleteById(String id);

	/**
	 * @param menuIdList
	 * @return
	 */
	@Query("select s from PlatformOrgConfig s where s.startFlag=?1 and s.pageType = ?2 ")
	List<PlatformOrgConfig> findByStartFlag(String startFlag, String pageType);

	@Query("select s from PlatformOrgConfig s where s.startFlag=?1 and s.pageType = ?2 and s.appid=?3 ")
	List<PlatformOrgConfig> findByAppid(String startFlag, String pageType, String appid);
}
