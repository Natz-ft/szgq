package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformDicType;
/**
 * 
* @ClassName: PlatformDicTypeDao
* @Description: TODO(字典的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:16:48
*
 */
public interface PlatformDicTypeDao extends PagingAndSortingRepository<PlatformDicType, Long>, JpaSpecificationExecutor<PlatformDicType>
{
    
    /**
     * <根据type 查询dic>
     * 
     * @param type
     * 
     * @return
     */
	@Query("select d from PlatformDicType d where d.type=?1 ")
    PlatformDicType findByType(String type);
    
    /**
     * <根据type和value查询数据字典>
     * 
     * @param type
     * @param value
     * @return
     */
    PlatformDicType findByTypeAndValue(String type, String value);
    
      
    /**
     * <查询所有的PlatformDicType>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Query(" select a from PlatformDicType a  ")
    List<PlatformDicType> findList();
}
