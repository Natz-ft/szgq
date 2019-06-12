package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformMenu;

public interface PlatformMenu1Dao extends PagingAndSortingRepository<PlatformMenu, String>, JpaSpecificationExecutor<PlatformMenu> {
	List<PlatformMenu> findById(String id);
}
