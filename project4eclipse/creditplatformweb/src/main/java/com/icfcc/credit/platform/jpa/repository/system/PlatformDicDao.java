package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformDic;
/**
 * 
* @ClassName: PlatformDicDao
* @Description: TODO(字典的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:16:48
*
 */
public interface PlatformDicDao extends PagingAndSortingRepository<PlatformDic, Long>, JpaSpecificationExecutor<PlatformDic>
{
    
    /**
     * <根据type 查询dic>
     * 
     * @param type
     * 
     * @return
     */
	@Query("select d from PlatformDic d where d.type=?1 order by d.id asc")
    List<PlatformDic> findByType(String type);
    
    /**
     * <根据type和value查询数据字典>
     * 
     * @param type
     * @param value
     * @return
     */
    PlatformDic findByTypeAndValue(String type, String value);
    
    /**
     * <根据type和key查询数据字典>
     * 
     * @param type
     * @param value
     * @return
     */
    PlatformDic findByTypeAndName(String type, String name);
    
    /**
     * <查询所有的PlatformDic>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Query(" select a from PlatformDic a order by sortNum desc ")
    List<PlatformDic> findList();
}
