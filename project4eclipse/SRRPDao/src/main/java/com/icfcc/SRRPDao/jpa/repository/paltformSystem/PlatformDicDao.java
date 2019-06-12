package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformDic;
/**
 * 
* @ClassName: PlatformDicDao
* @Description: TODO(�ֵ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:16:48
*
 */
public interface PlatformDicDao extends PagingAndSortingRepository<PlatformDic, Long>, JpaSpecificationExecutor<PlatformDic>
{
    
    /**
     * <����type ��ѯdic>
     * 
     * @param type
     * 
     * @return
     */
	@Query("select d from PlatformDic d where d.type=?1 order by d.id asc")
    List<PlatformDic> findByType(String type);
    
    /**
     * <����type��value��ѯ�����ֵ�>
     * 
     * @param type
     * @param value
     * @return
     */
    PlatformDic findByTypeAndValue(String type, String value);
    
    /**
     * <����type��key��ѯ�����ֵ�>
     * 
     * @param type
     * @param value
     * @return
     */
    PlatformDic findByTypeAndName(String type, String name);
    
    /**
     * <��ѯ���е�PlatformDic>
     * 
     * @return
     * @see [�ࡢ��#��������#��Ա]
     */
    @Query(" select a from PlatformDic a order by sortNum desc ")
    List<PlatformDic> findList();
}
