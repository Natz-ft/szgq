package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

import lombok.Data;

/**
 *  企业用户工商异议信息实体类
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name = "rp_company_objection")
public class CompanyObjection implements Serializable{

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "OBJID")
	private String objId;
	
	@Column(name = "ENTERPRISE_ID")
	private String enterpriseId;
	
	@Column(name = "OBJECTION")
	private String objection;
	
	@Column(name = "OBJECTION_TYPE")
	private String objectionType;
	
	@Column(name = "FILE_PATH")
	private String objectionfilePath;
	
	@Column(name = "FILE_NAME")
	private String objectionFileName;
	
	@Column(name = "OPER_DATE")
	private Date operDate;
	
	@Transient
	private String objectionTypeStr;
	
	@Transient
	private String showFlag;//页面判断是否脱敏的标志1为不脱敏0为脱敏

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getObjection() {
		return objection;
	}

	public void setObjection(String objection) {
		this.objection = objection;
	}

	public String getObjectionType() {
		return objectionType;
	}

	public void setObjectionType(String objectionType) {
		this.objectionType = objectionType;
	}

	public String getObjectionfilePath() {
		return objectionfilePath;
	}

	public void setObjectionfilePath(String objectionfilePath) {
		this.objectionfilePath = objectionfilePath;
	}

	public String getObjectionFileName() {
		return objectionFileName;
	}

	public void setObjectionFileName(String objectionFileName) {
		this.objectionFileName = objectionFileName;
	}

	public String getObjectionTypeStr() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_OBJECTION_TYPE,
				this.objectionType);
	}

	public void setObjectionTypeStr(String objectionTypeStr) {
		this.objectionTypeStr = objectionTypeStr;
	}

	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}
	
	
}
