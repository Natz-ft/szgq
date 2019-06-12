package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * 企业商业计划书实体类
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name = "rp_company_businessplan")
public class CompanyBusinessplan implements Serializable{

	
	private static final long serialVersionUID = 1084097815263733068L;

	
	@Id
	@Column(name = "INFO_ID",length = 32)
//  @GenericGenerator(name = "system-uuid", strategy = "uuid")
//	@GeneratedValue(generator = "system-uuid")
	private String infoId;
   
	@Column(name="FILEINFO",length =100 )
    private String fileinfo;

	
	@Column(name = "filename",length =200 )
	private String fileName;// 上传文件名称
	
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getFileinfo() {
		return fileinfo;
	}

	public void setFileinfo(String fileinfo) {
		this.fileinfo = fileinfo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
