package com.icfcc.SRRPDao.jpa.repository.managedept;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.managedept.ManagedeptOnlineForum;
/**
 * 
* @ClassName: ManagedeptOnlineForumDao
* @Description: TODO(在线提问解答Dao)
* @author hugt
* @date 2017年9月19日 下午5:59:37
*
 */
public interface ManagedeptOnlineForumDao extends PagingAndSortingRepository<ManagedeptOnlineForum, String>, JpaSpecificationExecutor<ManagedeptOnlineForum> {
	@Query("select s from ManagedeptOnlineForum s where s.id=?1 order by s.messagedate desc")
	List<ManagedeptOnlineForum> findByUserId(String id);
}
