package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
 * 企业基本信息待审核表
 * 
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name = "rp_company_pending")
public class CompanyBasePending implements Serializable {

	private static final long serialVersionUID = 4469583355269717077L;

	@Id
	@Column(name = "ID", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String Id;// 历史信息的id

	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;// 企业信息的id

	@Column(name = "NAME", length = 50)
	private String name;// 企业名称

	@Column(name = "CODETYPE", length = 4)
	private String codetype;// 证件类型

	@Transient
	private String codetypeDicname;// 证件代码类型字典值

	@Column(name = "CODE", length = 20)
	private String code;// 证件号码

	@Column(name = "REGCAPITAL", precision = 12, scale = 2)
	private Double regcapital;// 注册资本

	@Column(name = "reg_currency")
	private String regCurrency;// 注册资本币种

	@Temporal(TemporalType.DATE)
	@Column(name = "REDATE")
	private Date redate;// 注册时间

	@Column(name = "REAREA", length = 50)
	private String rearea;// 所属区域

	@Column(name = "REGISTAREA", length = 256)
	private String registArea;// 企业注册地址

	@Transient
	private String reareaDicname;// 证件代码类型字典值

	@Column(name = "CONTACTTYPE", length = 10)
	private String contacttype;// 联系方式

	@Column(name = "LEGALNAME", length = 10)
	private String legalName;// 法定代表人姓名

	@Column(name = "LEGALCAL", length = 15)
	private String legalCal;// 法定代表人手机号

	@Column(name = "CONTACNAME", length = 10)
	private String contacName;// 联系人姓名

	@Column(name = "CONTACCAL", length = 15)
	private String contacCal;// 联系人手机号

	@Column(name = "STOCKNAME", length = 10)
	private String stockName;

	@Column(name = "STOCKCAL", length = 15)
	private String stockCal;

	@Column(name = "OPERDATE")
	private Date operdate;// 操作时间

	@Column(name = "INFO_SOURCES", length = 20)
	private String sources = "isNo";// 新增的信息来源字段，0为工商导入，1为股权平台录入

	@Column(name = "stock_change_flag")
	private String stockFlag;//股东信息是否更改字段  01代表股东信息进行了修改  00代表没有进行修改
	@Column(name = "platform_flag")
	private String platformFlag;//股东信息是否更改字段  00srrp  01金服
	@Column(name = "OBJECTION_ISORNO")
	private String objectionIs="1";// 存放是否存在工商异议

	@Column(name = "objection")
	private String objection;// 工商异议描述字段  
	
	
	@Column(name = "object_change_flag")
	private String objecChangFlag;//是否有工商异议更新如果有的话为0没有的话为1
	@Transient
	private String operdateDicName;

	@Transient
	private String redateDicName;
	@Transient
	private String regCurrencyDicname;// 注册资本币种字典值
	@Transient
	private String licensePath;
	@Transient
	private String licenseName;
	@Transient
	private String creditAuthorizationPath;
	@Transient
	private String creditAuthorizationName;

	@Transient
	private String registerAutographPath;
	@Transient
	private String registerAutographName;
	@Transient
	private String bussinessPlanPath;
	@Transient
	private String bussinessPlanName;
	@Transient
	private String industry2;// 添加二级行业的字段

	@Transient
	private String currency;// 企业补充信息的实收资本
	
	@Transient
	private String currencyDicname;// 实收资本币种字典值
	
	@Transient
	private String stockAddFlag;//股东信息添加标记
	
	@Transient
	private String stockDelFlag;//股东信息删除标记
	
	@Transient
	private String objAddFlag;//股东信息添加标记
	
	@Transient
	private String objDelFlag;//股东信息删除标记
	
	@Transient
	private String linceFlag="0";//股东信息删除标记
	@Transient
	private String authFlag="0";//股东信息删除标记
	
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
	
	public String getLinceFlag() {
		return linceFlag;
	}

	public String getPlatformFlag() {
		return platformFlag;
	}

	public void setPlatformFlag(String platformFlag) {
		this.platformFlag = platformFlag;
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

	public String getLicensePath() {
		return licensePath;
	}

	public void setLicensePath(String licensePath) {
		this.licensePath = licensePath;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public String getCreditAuthorizationPath() {
		return creditAuthorizationPath;
	}

	public void setCreditAuthorizationPath(String creditAuthorizationPath) {
		this.creditAuthorizationPath = creditAuthorizationPath;
	}

	public String getCreditAuthorizationName() {
		return creditAuthorizationName;
	}

	public void setCreditAuthorizationName(String creditAuthorizationName) {
		this.creditAuthorizationName = creditAuthorizationName;
	}

	public String getRegisterAutographPath() {
		return registerAutographPath;
	}

	public void setRegisterAutographPath(String registerAutographPath) {
		this.registerAutographPath = registerAutographPath;
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

	public String getRegisterAutographName() {
		return registerAutographName;
	}

	public void setRegisterAutographName(String registerAutographName) {
		this.registerAutographName = registerAutographName;
	}

	public String getBussinessPlanPath() {
		return bussinessPlanPath;
	}

	public void setBussinessPlanPath(String bussinessPlanPath) {
		this.bussinessPlanPath = bussinessPlanPath;
	}

	public String getBussinessPlanName() {
		return bussinessPlanName;
	}

	public void setBussinessPlanName(String bussinessPlanName) {
		this.bussinessPlanName = bussinessPlanName;
	}

	/**
	 * 添加企业补充信息的五个基本字段
	 * 
	 * @return
	 */
	@Transient
	private String industry;// 补充信息的行业字段

	@Transient
	private Double paidincapital;// 企业补充信息的实际投资字段

	@Transient
	private String iSHiTechPark;// 企业补充信息的是否有投资意向字段

	@Transient
	private String iSEgggenerator;// 企业补充信息的字段

	@Transient
	private String ortherIndustry;// 其他行业字符串
	@Transient
	private String auditStatusDicName;// 审核状态的展示
	@Transient
	private String auditStatus;// 审核状态的展示
	@Transient
	private Map<String, String> flagMap;

	public Map<String, String> getFlagMap() {
		return flagMap;
	}

	public void setFlagMap(Map<String, String> flagMap) {
		this.flagMap = flagMap;
	}

	// LiWCH 添加非空校验
	public String getOperdateDicName() {
		if (this.operdate == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(this.operdate);
		}

	}

	public void setOperdateDicName(String operdateDicName) {
		this.operdateDicName = operdateDicName;
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

	public void setRedateDicName(String redateDicName) {
		this.redateDicName = redateDicName;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReareaDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, this.rearea);
	}

	public void setReareaDicname(String reareaDicname) {
		this.reareaDicname = reareaDicname;
	}

	public String getCodetype() {
		return codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	public String getCodetypeDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CERTIFICATE, this.codetype);
	}

	public void setCodetypeDicname(String codetypeDicname) {
		this.codetypeDicname = codetypeDicname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Date getOperdate() {

		return this.operdate;
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Double getPaidincapital() {
		return paidincapital;
	}

	public void setPaidincapital(Double paidincapital) {
		this.paidincapital = paidincapital;
	}

	public String getiSHiTechPark() {
		return iSHiTechPark;
	}

	public void setiSHiTechPark(String iSHiTechPark) {
		this.iSHiTechPark = iSHiTechPark;
	}

	public String getiSEgggenerator() {
		return iSEgggenerator;
	}

	public void setiSEgggenerator(String iSEgggenerator) {
		this.iSEgggenerator = iSEgggenerator;
	}

	public String getOrtherIndustry() {
		return ortherIndustry;
	}

	public void setOrtherIndustry(String ortherIndustry) {
		this.ortherIndustry = ortherIndustry;
	}

	public String getAuditStatusDicName() {
		return auditStatusDicName;
	}

	public void setAuditStatusDicName(String auditStatusDicName) {
		this.auditStatusDicName = auditStatusDicName;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
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
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.regCurrency);
	}

	public void setRegCurrencyDicname(String regCurrencyDicname) {
		this.regCurrencyDicname = regCurrencyDicname;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyDicname() {
		return currencyDicname;
	}

	public void setCurrencyDicname(String currencyDicname) {
		this.currencyDicname = currencyDicname;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getStockFlag() {
		return stockFlag;
	}

	public void setStockFlag(String stockFlag) {
		this.stockFlag = stockFlag;
	}

	public String getStockAddFlag() {
		return stockAddFlag;
	}

	public void setStockAddFlag(String stockAddFlag) {
		this.stockAddFlag = stockAddFlag;
	}

	public String getStockDelFlag() {
		return stockDelFlag;
	}

	public void setStockDelFlag(String stockDelFlag) {
		this.stockDelFlag = stockDelFlag;
	}


	public String getObjection() {
		return objection;
	}

	public void setObjection(String objection) {
		this.objection = objection;
	}

	public String getObjectionIs() {
		return objectionIs;
	}

	public void setObjectionIs(String objectionIs) {
		this.objectionIs = objectionIs;
	}

	public String getObjAddFlag() {
		return objAddFlag;
	}

	public void setObjAddFlag(String objAddFlag) {
		this.objAddFlag = objAddFlag;
	}

	public String getObjDelFlag() {
		return objDelFlag;
	}

	public void setObjDelFlag(String objDelFlag) {
		this.objDelFlag = objDelFlag;
	}

	public String getObjecChangFlag() {
		return objecChangFlag;
	}

	public void setObjecChangFlag(String objecChangFlag) {
		this.objecChangFlag = objecChangFlag;
	}

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
	
}
