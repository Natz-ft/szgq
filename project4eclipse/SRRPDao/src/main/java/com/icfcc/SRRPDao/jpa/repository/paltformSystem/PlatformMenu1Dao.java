package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformMenu;

public interface PlatformMenu1Dao extends PagingAndSortingRepository<PlatformMenu, String>, JpaSpecificationExecutor<PlatformMenu> {
	List<PlatformMenu> findById(String id);
}
