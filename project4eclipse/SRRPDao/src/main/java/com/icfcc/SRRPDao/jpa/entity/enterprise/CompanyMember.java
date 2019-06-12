package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rp_company_member")
public class CompanyMember implements Serializable{


	private static final long serialVersionUID = 18673912526092393L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "MEMBER_ID", length = 32)
	private String memberId;//团队成员的id
	
	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId; //企业的id
	
	@Column(name = "NAME", length = 32)
	private String memberName; //成员姓名
	
	@Column(name = "PORT", length = 32)
	private String port; //成员职务
	
	@Column(name = "INTRODUCTION", length = 32)
	private String introduction; //成员资历介绍
	
	@Column(name = "CREATE_ID", length = 32)
	private String createId; //创建用户id
	
	@Column(name = "CREATE_TIME", length = 32)
	private Date createTime; //创建时间

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
