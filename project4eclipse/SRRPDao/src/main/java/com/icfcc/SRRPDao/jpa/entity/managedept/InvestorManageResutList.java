package com.icfcc.SRRPDao.jpa.entity.managedept;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class InvestorManageResutList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7729483419263509375L;

	/**
	 * 机构表中所很出现的列；
	 */
	@Id
	@Column(name = "investor_id", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "assigned")
	@GeneratedValue(generator = "system-uuid")
	private String investorId;// 投资机构的id

	@Column(name = "certtype")
	private String certtype;// 证件类型

	@Column(name = "certno")
	private String certno;// 证件号码

	@Column(name = "name")
	private String name;// 投资机构名称

	@Temporal(TemporalType.DATE)
	@Column(name = "registe_time")
	private Date registeTime;// 注册时间

	@Column(name = "top_investor")
	private String topInvestor;// 所属地区

	@Column(name = "org_type")
	private String orgType;// 机构类型

	@Column(name = "stop_flag")
	private String stopFlag;// 启用停用标识

	@Column(name = "audit_status")
	private String auditStatus;// 审核状态

	@Column(name = "capital_type")
	private String capitalType;// 资本类型

	@Column(name = "area_code")
	private String areaCode;// 行政区划

	@Column(name = "license_path")
	private String licensePath;// 营业执照路径

	@Column(name = "office_address")
	private String address;// 地址

	@Column(name = "zipcode")
	private String zipcode;// 邮编

	@Column(name = "rel_name")
	private String relName;// 联系人姓名

	@Column(name = "rel_phone")
	private String relPhone;// 联系人电话

	@Column(name = "fax")
	private String fax;// 传真

	@Column(name = "email")
	private String email;// 电子邮箱

	@Column(name = "description")
	private String description;// 机构介绍

	@Column(name = "capital_min")
	private Double capital;// 管理资本量

	@Column(name = "capital_max")
	private Double capitalMax;// 管理资本量
	
	@Column(name = "currency")
	private String currency;// 币种

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "finance_stage")
	private byte[] financeStage;// 拟投资阶段

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "finance_trade")
	private byte[] financeTrade;// 拟投资行业

	@Column(name = "create_time")
	private Date createTime; // 平台注册时间
	
	@Column(name = "audit_stage")
	private String auditStage; // 审核阶段
	@Transient
	private Long countOKEvent;
	@Column
	private String score;
	@Transient
	private String amount;
	@Transient
	private String registeTimeString;

	@Transient
	private String areaName;
	@Transient
	private String userType;//当前登录用户的用户类型
	@Transient
	private String stopFlagDicname;// 启用停用标识字符串
	@Transient
	private String auditStatusDicname;// 启用审核状态字符串
	@Transient
	private String orgTypeDicname;// 机构类型字符串
	@Transient
	private String certtypeDicname;// 证件类型字符串
	@Transient
	private String capitalTypeDicname;// 资本类型字符串
	@Transient
	private String financeStageDicname;// 拟投资阶段字符串
	@Transient
	private String financeTradeDicname;// 拟投资行业字符串
	@Transient
	private String topInvestorDicname;//总部字典值
	@Transient 
	private String areaCodeDicname;//所属地区字符串
	@Column(name = "filename")
	private String fileName;// 上传文件名称
	@Transient
	private Integer lockFlag=0;//机构锁定标识
	@Transient
	private String capitalStr;
	
	@Column(name = "operdate")
    private Date operdate; // 平台注册时间
	@Transient
	private String idHaveDissent="0";
	    
	@Transient
	private String isReadDissent="0";
	    
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
	    
	public Date getOperdate() {
        return operdate;
    }

    public void setOperdate(Date operdate) {
        this.operdate = operdate;
    }

    public String getIdHaveDissent() {
            return idHaveDissent;
        }

        public void setIdHaveDissent(String idHaveDissent) {
            this.idHaveDissent = idHaveDissent;
        }

        public String getIsReadDissent() {
            return isReadDissent;
        }

        public void setIsReadDissent(String isReadDissent) {
            this.isReadDissent = isReadDissent;
        }

    //LiWCH  添加管理资本量的非空校验
	public String getCapitalStr() {
		if(this.capital==null){
			return "";
		}else{
			DecimalFormat df = new DecimalFormat("######0.00");
			return  df.format(this.capital);
		}
	}

	public Double getCapitalMax() {
		return capitalMax;
	}

	public void setCapitalMax(Double capitalMax) {
		this.capitalMax = capitalMax;
	}

	public void setCapitalStr(String capitalStr) {
		this.capitalStr = capitalStr;
	}

	public Integer getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getAreaCodeDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, this.areaCode);
	}

	public void setAreaCodeDicname(String areaCodeDicname) {
		this.areaCodeDicname = areaCodeDicname;
	}

	public String getTopInvestorDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, this.topInvestor);
	}

	public void setTopInvestorDicname(String topInvestorDicname) {
		this.topInvestorDicname = topInvestorDicname;
	}

	public String getAmount() {
		if(this.capital==null||this.capital==0){
			return "";
		}else{
		return "CNY " + DigitFormatUtil.digitFormat(this.capital);
		}
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStopFlagDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INVESTORSTATUS,
				this.stopFlag);
	}

	public String getAuditStatusDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_AUDITSTATE,
				this.auditStatus);
	}

	public String getOrgTypeDicname() {
		String[] orgTypes = this.orgType.split(",");
		String typeNames = "";
		for (String type : orgTypes) {
			typeNames += RedisGetValue.getValueByCode(SRRPConstant.DD_ORGTYPE, type)+"、";
		}
		if(typeNames.contains("、")){
			typeNames = typeNames.substring(0,typeNames.length()-1);
		}
		return typeNames;
	}

	public String getCerttypeDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CERTIFICATE,
				this.certtype);
	}

	public String getFinanceStageDicname() throws UnsupportedEncodingException {
		String[] values = getFinanceStage().split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			String Stage = null;
			Stage = RedisGetValue.getValueByCode(SRRPConstant.DD_INVESTMENT,
					values[i]);
			if (Stage != null) {
				if (sb.length() > 0) {
					sb.append(",").append(Stage);
				} else {
					sb.append(Stage);
				}
			}
		}
		return sb.toString();
	}

	public void setFinanceStageDicname(String financeStageDicname) {
		this.financeStageDicname = financeStageDicname;
	}

	public String getFinanceTradeDicname() throws UnsupportedEncodingException {
		String[] values = getFinanceTrade().split(",");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < values.length; i++) {
			String Trade = null;
			Trade = RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY,
					values[i]);
			if (Trade != null) {
				if (sb.length() > 0) {
					sb.append(",").append(Trade);
				} else {
					sb.append(Trade);
				}
			}
		}
		return sb.toString();
	}

	public void setFinanceTradeDicname(String financeTradeDicname) {
		this.financeTradeDicname = financeTradeDicname;
	}

	public String getCapitalTypeDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CAPITALTYPE,
				this.capitalType);
	}

	public void setCapitalTypeDicname(String capitalTypeDicname) {
		this.capitalTypeDicname = capitalTypeDicname;
	}

	public void setStopFlagDicname(String stopFlagDicname) {
		this.stopFlagDicname = stopFlagDicname;
	}

	public void setAuditStatusDicname(String auditStatusDicname) {
		this.auditStatusDicname = auditStatusDicname;
	}

	public void setOrgTypeDicname(String orgTypeDicname) {
		this.orgTypeDicname = orgTypeDicname;
	}

	public void setCerttypeDicname(String certtypeDicname) {
		this.certtypeDicname = certtypeDicname;
	}

	public Long getCountOKEvent() {
		return countOKEvent;
	}

	public void setCountOKEvent(Long countOKEvent) {
		this.countOKEvent = countOKEvent;
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public String getName() {
		return name;
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

	public String getFinanceTrade() throws UnsupportedEncodingException {
		if (financeTrade == null) {
			return "";
		} else {
			return new String(financeTrade, SRRPConstant.DEFAULTCHARTS);
		}
	}

	public void setFinanceTrade(String financeTrade)
			throws UnsupportedEncodingException {
		this.financeTrade = financeTrade.getBytes(SRRPConstant.DEFAULTCHARTS);
	}

	public String getFinanceStage() throws UnsupportedEncodingException {
		if (financeStage == null) {
			return "";
		} else {
			return new String(financeStage, SRRPConstant.DEFAULTCHARTS);
		}
	}

	public void setFinanceStage(String financeStage)
			throws UnsupportedEncodingException {
		this.financeStage = financeStage.getBytes(SRRPConstant.DEFAULTCHARTS);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	public Date getRegisteTime() {
		return registeTime;
	}

	public void setRegisteTime(Date registeTime) {
		this.registeTime = registeTime;
	}

	// public String getTopInvestor() {
	// return RedisGetValue.getValueByCode(SRRPConstant.DD_AREA,
	// this.topInvestor);
	// }
	//
	// public void setTopInvestor(String topInvestor) {
	// this.topInvestor = topInvestor;
	// }

	public String getTopInvestor() {
		return topInvestor;
	}

	public void setTopInvestor(String topInvestor) {
		this.topInvestor = topInvestor;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getLicensePath() {
		return licensePath;
	}

	public void setLicensePath(String licensePath) {
		this.licensePath = licensePath;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getRelPhone() {
		return relPhone;
	}

	public void setRelPhone(String relPhone) {
		this.relPhone = relPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCapital() {
		return this.capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCerttype() {
		return certtype;
	}

	public void setCerttype(String certtype) {
		this.certtype = certtype;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getCapitalType() {
		return capitalType;
	}

	public void setCapitalType(String capitalType) {
		this.capitalType = capitalType;
	}

	public InvestorManageResutList() {

	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getRegisteTimeString() {
		if(this.registeTime==null){
			return "";
		}else{
			return this.registeTime.toString();
		}
		
	}

	public void setRegisteTimeString(String registeTimeString) {
		this.registeTimeString = registeTimeString;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	
}
