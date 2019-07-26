/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PlatformUserLoginStatisticsDao
 * Author:   whyxs
 * Date:     2019/7/23 15:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserLoginStatistics;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author whyxs
 * @create 2019/7/23
 * @since 1.0.0
 */
public interface PlatformUserLoginStatisticsDao extends PagingAndSortingRepository<PlatformUserLoginStatistics, String>
        , JpaSpecificationExecutor<PlatformUserLoginStatistics> {

    @Query("select p from PlatformUserLoginStatistics p")
    List<PlatformUserLoginStatistics> findall();
}
 

