package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.s1.jpa.entity.SystemDic;

public interface SystemDicDao extends PagingAndSortingRepository<SystemDic, Long>, JpaSpecificationExecutor<SystemDic>
{
    
    /**
     * <根据type 查询dic>
     * 
     * @param type
     * 
     * @return
     */
	@Query("select d from SystemDic d where d.type=?1 order by d.id asc")
    List<SystemDic> findByType(String type);
    
    /**
     * <根据type和value查询数据字典>
     * 
     * @param type
     * @param value
     * @return
     */
    SystemDic findByTypeAndValue(String type, String value);
    
    /**
     * <根据type和key查询数据字典>
     * 
     * @param type
     * @param value
     * @return
     */
    SystemDic findByTypeAndName(String type, String name);
    
    /**
     * <查询所有的SystemDic>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Query(" select a from SystemDic a order by sortNum desc ")
    List<SystemDic> findList();
}
