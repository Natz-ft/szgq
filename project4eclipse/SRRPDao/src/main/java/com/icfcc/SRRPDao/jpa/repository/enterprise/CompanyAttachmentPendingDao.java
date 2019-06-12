package com.icfcc.SRRPDao.jpa.repository.enterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachmentPending;

@Component
public interface CompanyAttachmentPendingDao extends
		PagingAndSortingRepository<CompanyAttachmentPending, String>,
		JpaSpecificationExecutor<CompanyAttachmentPending> {
	// 根据企业的id以及文件类型查询待审核表中是否存在待审核的相关附件信息
	@Query("select c from CompanyAttachmentPending c where c.enterpriseId=?1 and c.fileType=?2")
	CompanyAttachmentPending findAttachmentPendingByType(String enterpriseId,
			String type);
}
