package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyMember;

@Component
public interface CompanyMemberDao extends
		PagingAndSortingRepository<CompanyMember, String>,
		JpaSpecificationExecutor<CompanyMember> {

	//根据企业的id查询企业团队成员
	@Query("select cc from CompanyMember cc where cc.enterpriseId=? order by cc.createTime asc")
	public List<CompanyMember> findCompanyMemberByEventId(String enterpriseId);
}
