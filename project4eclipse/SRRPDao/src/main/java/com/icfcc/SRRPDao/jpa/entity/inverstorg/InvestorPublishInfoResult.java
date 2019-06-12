package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import java.io.Serializable;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;


/**
 * 披露信息实体类
 * @author Administrator
 *
 */
@Entity
public class InvestorPublishInfoResult implements Serializable{

	
	private static final long serialVersionUID = 4662212779661041029L;
	
	@Id
	@Column(name = "PUBLISH_ID", length = 32)
	private String publishId;

	@Column(name = "EVENT_ID", length = 32)
	private String eventId; //关联融资事件表查询项目名称
	
	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId; //关联企业基本信息表查询企业名称
	
	@Column(name = "INFOTITLE", length =100 )
	private String infotitle;

	@Column(name = "INFOTYPE", length = 10)
	private String infotype;
	
	@Column(name = "PUBLISH_TIME")
	private Date publishTime;
	
	@Column(name = "OPERUSER", length = 20)
	private String operuser;
	
	@Column(name = "ORGNAME", length = 100)
	private String orgname;
	
	@Column(name = "ORG_ID", length = 32)
	private String orgId;
	
	@Column(name = "file_name")
	private String publishFileName;
	
	@Column(name = "file_path")
	private String publishFilePath;
	
	@Column(name = "NAME", length = 32)
	private String name;
	@Column(name="codetype",length=4)
	private String codetype;//证件类型

	@Column(name="code",length=20)
	private String code;//证件号

	@Column(name = "score", length = 3)
	private Integer score;// 总评分
	@Column(name = "project_name", length =100 )
	private String projectName;//项目名称
	@Transient
	private String publishTimeStr;
	@Transient
	private String infotypeDicName;
	
	public String getPublishTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.format(this.publishTime);
		return sdf.format(this.publishTime);
	}

	public void setPublishTimeStr(String publishTimeStr) {
		this.publishTimeStr = publishTimeStr;
	}

	public String getInfotypeDicName() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_PUBLISHTYPE, this.infotype);
	}

	public void setInfotypeDicName(String infotypeDicName) {
		this.infotypeDicName = infotypeDicName;
	}
	public String getCodetype() {
		return codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "INFOCONTENT")
	private byte[] infocontent;
	
	public String getPublishId() {
		return publishId;
	}

	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getInfotitle() {
		return infotitle;
	}

	public void setInfotitle(String infotitle) {
		this.infotitle = infotitle;
	}

	public String getInfotype() {
		return infotype;
	}

	public void setInfotype(String infotype) {
		this.infotype = infotype;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getOperuser() {
		return operuser;
	}

	public void setOperuser(String operuser) {
		this.operuser = operuser;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getInfocontent() throws UnsupportedEncodingException {
		return new String(infocontent,"gbk");
	}
	public void setInfocontent(String infocontent) throws UnsupportedEncodingException {
		this.infocontent = infocontent.getBytes("utf-8");
	}

	public String getPublishFileName() {
		return publishFileName;
	}

	public void setPublishFileName(String publishFileName) {
		this.publishFileName = publishFileName;
	}

	public String getPublishFilePath() {
		return publishFilePath;
	}

	public void setPublishFilePath(String publishFilePath) {
		this.publishFilePath = publishFilePath;
	}
	
}
