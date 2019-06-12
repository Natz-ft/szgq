package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyComment;

@Component
public interface CompanyCommentDao extends
		PagingAndSortingRepository<CompanyComment, String>,
		JpaSpecificationExecutor<CompanyComment> {

	@Query("select cc from CompanyComment cc where cc.eventId=?")
	public List<CompanyComment> findCompanyCommentByEventId(String eventId);
}
