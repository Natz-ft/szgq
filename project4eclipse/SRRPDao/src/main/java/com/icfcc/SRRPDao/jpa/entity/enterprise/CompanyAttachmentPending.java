package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "rp_company_attachment_pending")
public class CompanyAttachmentPending implements Serializable {

	private static final long serialVersionUID = -6232938794442698483L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "CREATER_ID", length = 32)
	private String createrId;

	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;

	@Column(name = "FILEPATH", length = 256)
	private String filepath;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "filename", length = 200)
	private String fileName;// 上传文件名称

	@Column(name = "filetype", length = 200)
	private String fileType;// 上传文件类型

	@Transient
	private String fileTypeName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getCreateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.format(this.createTime);
		return sdf.format(this.createTime);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}
}
