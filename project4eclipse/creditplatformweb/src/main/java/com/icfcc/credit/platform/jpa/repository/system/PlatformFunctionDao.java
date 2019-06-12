package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformFunction;
/**
 * 
* @ClassName: PlatformFunctionDao
* @Description: TODO( 平台功能信息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:18:48
*
 */
public interface PlatformFunctionDao extends PagingAndSortingRepository<PlatformFunction, String>, JpaSpecificationExecutor<PlatformFunction>{
  
	/**
   *  根据方法名 和 类名   查询
   * @return
   */
	@Modifying
	@Query(" from PlatformFunction  f    where f.methodName=?1 and f. servicePath=?2")
	public List<PlatformFunction> getFunctionMethodNameAndServicePath(String methodName, String servicePath);
	
	  /**
     * <根据SystemButton buttonId删除>
     * @param buttonId
     */
    @Modifying
    @Query("delete from PlatformFunction  where id=?1")
    public    void deletePlatformFunctionById( String id );
	
	
	
}
