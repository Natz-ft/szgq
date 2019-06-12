package com.icfcc.credit.platform.jpa.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

/**
 * 
* @ClassName: PlatformOrg
* @Description: TODO(投资机构信息实体类)
* @author hugt
* @date 2017年9月14日 下午6:02:28
*
 */
@Data
@Entity
@Table(name = "system_org")
public class PlatformOrg implements java.io.Serializable {

	private static final long serialVersionUID = 1103743372761261651L;
	// Fields
	private String id;
	private String orgid;
	private String orgName;
	private String uporg;
	private String areaCode;
	private String address;
	private String postCode;
	private String linkMan;
	private String linkMode;
	private String otherLinkMode;
	private String remark;
	private String recordStopFlag;
	private Date recordStopTime;
	private Double regCapital;
	private Short deleteState;

	// Constructors

	/** default constructor */
	public PlatformOrg() {
	}

	/** minimal constructor */
	public PlatformOrg(Date recordStopTime) {
		this.recordStopTime = recordStopTime;
	}

	/** full constructor */
	public PlatformOrg(String orgid, String orgName, String uporg, String areaCode, String address, String postCode, String linkMan, String linkMode, String otherLinkMode, String remark, String recordStopFlag, Date recordStopTime, Double regCapital, Short deleteState) {
		this.orgid = orgid;
		this.orgName = orgName;
		this.uporg = uporg;
		this.areaCode = areaCode;
		this.address = address;
		this.postCode = postCode;
		this.linkMan = linkMan;
		this.linkMode = linkMode;
		this.otherLinkMode = otherLinkMode;
		this.remark = remark;
		this.recordStopFlag = recordStopFlag;
		this.recordStopTime = recordStopTime;
		this.regCapital = regCapital;
		this.deleteState = deleteState;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ORGID", length = 64)
	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	@Column(name = "ORG_NAME", length = 80)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "UPORG", length = 18)
	public String getUporg() {
		return this.uporg;
	}

	public void setUporg(String uporg) {
		this.uporg = uporg;
	}

	@Column(name = "AREA_CODE", length = 6)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "ADDRESS", length = 80)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "POST_CODE", length = 6)
	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "LINK_MAN", length = 40)
	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Column(name = "LINK_MODE", length = 100)
	public String getLinkMode() {
		return this.linkMode;
	}

	public void setLinkMode(String linkMode) {
		this.linkMode = linkMode;
	}

	@Column(name = "OTHER_LINK_MODE", length = 100)
	public String getOtherLinkMode() {
		return this.otherLinkMode;
	}

	public void setOtherLinkMode(String otherLinkMode) {
		this.otherLinkMode = otherLinkMode;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "RECORD_STOP_FLAG", length = 1)
	public String getRecordStopFlag() {
		return this.recordStopFlag;
	}

	public void setRecordStopFlag(String recordStopFlag) {
		this.recordStopFlag = recordStopFlag;
	}

	@Column(name = "RECORD_STOP_TIME", nullable = false, length = 0)
	public Date getRecordStopTime() {
		return this.recordStopTime;
	}

	public void setRecordStopTime(Date recordStopTime) {
		this.recordStopTime = recordStopTime;
	}

	@Column(name = "REG_CAPITAL", precision = 10, scale = 4)
	public Double getRegCapital() {
		return this.regCapital;
	}

	public void setRegCapital(Double regCapital) {
		this.regCapital = regCapital;
	}

	@Column(name = "DELETE_STATE")
	public Short getDeleteState() {
		return this.deleteState;
	}

	public void setDeleteState(Short deleteState) {
		this.deleteState = deleteState;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}