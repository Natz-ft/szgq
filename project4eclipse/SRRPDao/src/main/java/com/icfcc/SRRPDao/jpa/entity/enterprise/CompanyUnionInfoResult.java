package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

/**
 * 企业信息联合查询，返回值封装对象
 * 
 * @author Administrator
 *
 */

@Entity
public class CompanyUnionInfoResult implements Serializable {

	private static final long serialVersionUID = -2783740120510076112L;

	@Id
	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "CODETYPE", length = 4)
	private String codetype;

	@Transient
	private String codetypeDicname; // 证件代码类型字典值

	@Column(name = "CODE", length = 20)
	private String code;

	@Column(name = "REGCAPITAL", length = 12)
	private Double regcapital;

	@Column(name = "reg_currency")
	private String regCurrency;// 注册资本币种

	@Column(name = "REDATE")
	private Date redate;

	@Column(name = "REAREA", length = 50)
	private String rearea;

	@Transient
	private String reareaDicname;// 所属区域字典值

	@Column(name = "INDUSTRY", length = 4)
	private String industry;

	@Transient
	private String industry2;// 添加二级行业字段

	@Transient
	private String industry2Dicname;// 二级行业类型字符串

	@Transient
	private String industryStr;// 行业展示字符串

	@Transient
	private String industryDicname;// 行业字典值

	@Column(name = "FINANCINGPHASE", length = 20)
	private String financingphase;

	@Transient
	private String financingphaseDicname;// 现融资阶段字典值

	@Transient
	private String enterpriseNameShow;// 企业名称的展示字符串
	
	@Transient
	private String codeShow;// 证件号码的展示字符串
	
	@Transient
	private String sortord;// 排序方式

	@Transient
	private String regCapitalDicname;// 注册资本字符串
	
	@Transient
	private String regCapitalDicnameShow;

	// @Column(name="TOTALSCORE",length=30)
	// private String totalscore;//总评分

	@Column(name = "SCORE", length = 3)
	private Integer score;// 总评分

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public String getIndustryDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY, this.industry);
	}

	public void setIndustryDicname(String industryDicname) {
		this.industryDicname = industryDicname;
	}

	public String getFinancingphaseDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INVESTMENT, this.financingphase);
	}

	public void setFinancingphaseDicname(String financingphaseDicname) {
		this.financingphaseDicname = financingphaseDicname;
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

	public Double getRegcapital() {
		return regcapital;
	}

	public void setRegcapital(Double regcapital) {
		this.regcapital = regcapital;
	}

	public Date getRedate() {
		return redate;
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

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getFinancingphase() {
		return financingphase;
	}

	public void setFinancingphase(String financingphase) {
		this.financingphase = financingphase;
	}

	public String getSortord() {
		return sortord;
	}

	public void setSortord(String sortord) {
		this.sortord = sortord;
	}

	public String getIndustry2() {
		return industry2;
	}

	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}

	public String getIndustry2Dicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2, this.industry2);
	}

	public void setIndustry2Dicname(String industry2Dicname) {
		this.industry2Dicname = industry2Dicname;
	}

	public String getIndustryStr() {
		return industryStr;
	}

	public void setIndustryStr(String industryStr) {
		this.industryStr = industryStr;
	}

	public String getRegCurrency() {
		return regCurrency;
	}

	public void setRegCurrency(String regCurrency) {
		this.regCurrency = regCurrency;
	}

	public String getRegCapitalDicname() {
		String tmpAmount = "";
		if(this.regcapital!=null){
			if (this.regcapital==0) {
				tmpAmount = "0" ;
			} else {
				tmpAmount += DigitFormatUtil.digitFormat(this.regcapital).toString() + " " + RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.regCurrency);
			}
		}
		return tmpAmount;
	}

	public void setRegCapitalDicname(String regCapitalDicname) {
		this.regCapitalDicname = regCapitalDicname;
	}
	
	public String getRegCapitalDicnameShow() {
		String tmpAmount = "";
		if(this.regcapital!=null){
			if (this.regcapital==0) {
				tmpAmount = "0" ;
			} else {
				tmpAmount += DigitFormatUtil.digitFormat(this.regcapital*100).toString() + " " + RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.regCurrency);
			}
		}
		return tmpAmount;
	}

	public void setRegCapitalDicnameShow(String regCapitalDicname) {
		this.regCapitalDicname = regCapitalDicname;
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

	// public String getTotalscore() {
	// return totalscore;
	// }
	//
	// public void setTotalscore(String totalscore) {
	// this.totalscore = totalscore;
	// }

}
