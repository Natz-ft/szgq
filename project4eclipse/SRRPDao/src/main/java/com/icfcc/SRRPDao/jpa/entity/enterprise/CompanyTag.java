package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 * 企业基本信息实体类
 * 
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name = "rp_company_tag")
public class CompanyTag implements Serializable {


	/**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = 5179134280726712275L;

    @Id
	@Column(name = "ID")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "COMPANY_ID")
	private String companyId;
	
	@Column(name = "COMPANY_NAME")
    private String companyName;

	@Column(name = "COMPANY_CODE_TYPE")
	private String codetype;

	@Column(name = "COMPANY_CODE", length = 20)
	private String code;

	@Column(name = "COMPANY_AREA", precision = 12, scale = 2)
	private String companyArea;

	@Column(name = "TAG_CODE")
	private String tagCode;// 注册资本币种

	@Column(name = "TAG_YEAR")
    private String tagYear;// 注册资本币种
	
	@Column(name = "TAG_PICI")
	private String tagPici;

	@Column(name = "EXPORT_TAG_FILE")
	private String exportTagFile;

	@Transient
	private String reareaDicname;// 所属区域字典值
    
	@Column(name = "sid", length = 10)
    private String sid;
	
	@Column(name = "COMPANY_DESC", length = 10)
    private String companyDesc;
	
	
	@Column(name = "COMPANY_REGION", length = 10)
    private String companyRegion;
	
	@Column(name = "CREATE_TIME", length = 10)
	private Date createTime;
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCompanyArea() {
        return companyArea;
    }

    public void setCompanyArea(String companyArea) {
        this.companyArea = companyArea;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getTagPici() {
        return tagPici;
    }

    public void setTagPici(String tagPici) {
        this.tagPici = tagPici;
    }

    public String getExportTagFile() {
        return exportTagFile;
    }

    public void setExportTagFile(String exportTagFile) {
        this.exportTagFile = exportTagFile;
    }

    public String getReareaDicname() {
        return reareaDicname;
    }

    public void setReareaDicname(String reareaDicname) {
        this.reareaDicname = reareaDicname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTagYear() {
        return tagYear;
    }

    public void setTagYear(String tagYear) {
        this.tagYear = tagYear;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public String getCompanyRegion() {
        return companyRegion;
    }

    public void setCompanyRegion(String companyRegion) {
        this.companyRegion = companyRegion;
    }
    

}
