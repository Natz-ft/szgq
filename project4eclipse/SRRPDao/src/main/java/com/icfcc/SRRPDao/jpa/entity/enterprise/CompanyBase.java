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
@Table(name = "rp_company_base")
public class CompanyBase implements Serializable {

	private static final long serialVersionUID = 5082323220869991788L;

	@Id
	@Column(name = "ENTERPRISE_ID", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String enterpriseId;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "CODETYPE", length = 4)
	private String codetype;

	@Transient
	private String codetypeDicname;// 证件代码类型字典值

	@Column(name = "CODE", length = 20)
	private String code;

	@Column(name = "REGCAPITAL", precision = 12, scale = 2)
	private Double regcapital;

	@Column(name = "reg_currency")
	private String regCurrency;// 注册资本币种

	@Temporal(TemporalType.DATE)
	@Column(name = "REDATE")
	private Date redate;

	@Column(name = "REAREA", length = 50)
	private String rearea;

	@Transient
	private String reareaDicname;// 所属区域字典值

	@Column(name = "CONTACTTYPE", length = 10)
	private String contacttype;

	@Column(name = "LEGALNAME", length = 10)
	private String legalName;

	@Column(name = "LEGALCAL", length = 15)
	private String legalCal;

	@Column(name = "CONTACNAME", length = 10)
	private String contacName;

	@Column(name = "CONTACCAL", length = 15)
	private String contacCal;

	@Column(name = "STOCKNAME", length = 10)
	private String stockName;

	@Column(name = "STOCKCAL", length = 15)
	private String stockCal;

	@Column(name = "REGISTAREA", length = 256)
	private String registArea;// 企业注册地址

	@Column(name = "STOP_FLAG", length = 10)
	private String stopFlag = "1";// 启用停用标识

	@Column(name = "AUDIT_STATUS", length = 15)
	private String auditStatus = "1";// 审核状态
	@Column(name = "INFO_SOURCES", length = 20)
	private String sources = "isNo";// 新增的信息来源字段，0为工商导入，1为股权平台录入

	@Column(name = "audit_stage", length = 15)
	private String auditStage = "03";// 审核阶段
	
	@Column(name = "stock_change_flag")
	private String stockFlag;//股东信息是否更改字段  01代表股东信息进行了修改  00代表没有进行修改
	@Column(name = "platform_flag")
	private String platformFlag;//股东信息是否更改字段  00srrp  01金服
	
	@Column(name = "OBJECTION_ISORNO")
	private String objectionIs="1";// 存放是否存在工商异议

	@Column(name = "objection")
	private String objection;// 工商异议描述字段
	
	@Column(name = "object_change_flag")
	private String objecChangFlag="0";//是否有工商异议更新,如果有的话为1,没有的话为0
	
	@Transient
	private String auditStatusDicName;

	@Transient
	private String enterpriseNameShow;// 企业名称的展示字符串
	
	@Transient
	private String codeShow;// 证件号码的展示字符串
	
	@Transient
	private String redateDicName;
	@Transient
	private String industry2;// 添加二级行业的字段

	@Transient
	private String regCurrencyDicname;// 注册资本币种字典值
	@Transient
	private String linceFlag="0";//股东信息删除标记
	@Transient
	private String authFlag="0";//股东信息删除标记
	
	@Transient
	private String licenseName;
	
	@Transient
	private String creditAuthorizationName;
	
	@Column(name = "OPERDATE")
	private Date operdate;// 操作时间
	
	@Column(name = "create_time")
    private Date createTime;// 操作时间
	
	@Transient
    private String OPERDATEStr;
	@Transient
    private String createTimeStr;
    
    
     public String getCreateTimeStr() {
         if (this.createTime != null) {
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             return sdf.format(this.createTime);
         } else {
             return "";
         }
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
	 public String getOPERDATEStr() {
	        if (this.operdate != null) {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            return sdf.format(this.operdate);
	        } else {
	            return "";
	        }
	    }

	    public void setOPERDATEStr(String oPERDATEStr) {
	        OPERDATEStr = oPERDATEStr;
	    }
	
	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public String getCreditAuthorizationName() {
		return creditAuthorizationName;
	}

	public void setCreditAuthorizationName(String creditAuthorizationName) {
		this.creditAuthorizationName = creditAuthorizationName;
	}

	public String getLinceFlag() {
		return linceFlag;
	}

	public void setLinceFlag(String linceFlag) {
		this.linceFlag = linceFlag;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	// LiWCH 添加非空校验
	public String getRedateDicName() {
		if (this.redate == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(this.redate);
		}
	}

	public String getPlatformFlag() {
		return platformFlag;
	}

	public void setPlatformFlag(String platformFlag) {
		this.platformFlag = platformFlag;
	}

	public void setRedateDicName(String redateDicName) {
		this.redateDicName = redateDicName;
	}

	public String getAuditStatusDicName() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_COMPANY_AUSTSTAUS, this.auditStatus);
	}

	public void setAuditStatusDicName(String auditStatusDicName) {
		this.auditStatusDicName = auditStatusDicName;
	}

	public String getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getCodetypeDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CERTIFICATE, this.codetype);
	}

	public void setCodetypeDicname(String codetypeDicname) {
		this.codetypeDicname = codetypeDicname;
	}

	public String getReareaDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, this.rearea);
	}

	public void setReareaDicname(String reareaDicname) {
		this.reareaDicname = reareaDicname;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
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

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockCal() {
		return stockCal;
	}

	public void setStockCal(String stockCal) {
		this.stockCal = stockCal;
	}

	public Double getRegcapital() {
		return regcapital;
	}

	public void setRegcapital(Double regcapital) {
		this.regcapital = regcapital;
	}

	public Date getRedate() {
		return this.redate;
	}

	public void setRedate(Date redate) {
		this.redate = redate;
	}

	public String getRearea() {
		return rearea;
	}

	public void setRearea(String rearea) {
		this.rearea = rearea;
	}

	public String getRegistArea() {
		return registArea;
	}

	public void setRegistArea(String registArea) {
		this.registArea = registArea;
	}

	public String getContacttype() {
		return contacttype;
	}

	public void setContacttype(String contacttype) {
		this.contacttype = contacttype;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalCal() {
		return legalCal;
	}

	public void setLegalCal(String legalCal) {
		this.legalCal = legalCal;
	}

	public String getContacName() {
		return contacName;
	}

	public void setContacName(String contacName) {
		this.contacName = contacName;
	}

	public String getContacCal() {
		return contacCal;
	}

	public void setContacCal(String contacCal) {
		this.contacCal = contacCal;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	public String getIndustry2() {
		return industry2;
	}

	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}

	public String getRegCurrency() {
		return regCurrency;
	}

	public void setRegCurrency(String regCurrency) {
		this.regCurrency = regCurrency;
	}

	public String getRegCurrencyDicname() {
		if(this.regCurrency==null || this.regCurrency.isEmpty()){
			regCurrencyDicname="";
		}else{
			regCurrencyDicname=RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.regCurrency);
		}
		return regCurrencyDicname;
	}

	public void setRegCurrencyDicname(String regCurrencyDicname) {
		this.regCurrencyDicname = regCurrencyDicname;
	}

	public String getStockFlag() {
		return stockFlag;
	}

	public void setStockFlag(String stockFlag) {
		this.stockFlag = stockFlag;
	}

	public String getObjection() {
		return objection;
	}

	public void setObjection(String objection) {
		this.objection = objection;
	}

	public String getEnterpriseNameShow() {
		return enterpriseNameShow;
	}

	public void setEnterpriseNameShow(String enterpriseNameShow) {
		this.enterpriseNameShow = enterpriseNameShow;
	}

	public String getCodeShow() {
		return codeShow;
	}

	public void setCodeShow(String codeShow) {
		this.codeShow = codeShow;
	}

	public String getObjectionIs() {
		return objectionIs;
	}

	public void setObjectionIs(String objectionIs) {
		this.objectionIs = objectionIs;
	}

	public String getObjecChangFlag() {
		return objecChangFlag;
	}

	public void setObjecChangFlag(String objecChangFlag) {
		this.objecChangFlag = objecChangFlag;
	}

	public Date getOperdate() {
		return operdate;
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
	}

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
