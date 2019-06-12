package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachment;

@Component
public interface CompanyAttachmentDao extends
		PagingAndSortingRepository<CompanyAttachment, String>,
		JpaSpecificationExecutor<CompanyAttachment> {

	/**
	 * 根据企业ID查询相关附件信息列表
	 */
	@Query("select c from CompanyAttachment c where c.enterpriseId=?1")
	List<CompanyAttachment> findAll(String enterpriseId);

	@Query("select c from CompanyAttachment c where c.enterpriseId=?1 and c.fileType=?2")
	CompanyAttachment findAttachmentByType(String enterpriseId, String type);
}
