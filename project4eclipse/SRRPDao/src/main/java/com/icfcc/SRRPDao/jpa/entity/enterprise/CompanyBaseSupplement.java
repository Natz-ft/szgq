package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 * 企业补充信息实体类
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "rp_company_base_supplement")
public class CompanyBaseSupplement implements Serializable {

	private static final long serialVersionUID = 2270341371123305172L;

	@Id
	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;

	@Column(name = "INDUSTRY", length = 4)
	private String industry;

	@Transient
	private String industryDicname;// 行业类型字符串

	@Transient
	private String industry2Dicname;// 二级行业类型字符串

	@Transient
	private String industryStr;// 行业展示字符串

	@Column(name = "PAIDINCAPITAL", length = 12)
	private Double paidincapital;

	@Column(name = "currency")
	private String currency;// 实收资本币种

	@Column(name = "ISHITECHPARK", length = 2)
	private String iSHiTechPark;

	@Transient
	private String iSHiTechParkDicname;// 是否获得过股权投资

	@Column(name = "ISEGGGENERATOR", length = 2)
	private String iSEgggenerator="0";

	@Transient
	private String iSEgggeneratorDicname="0";// 是否有股权融资意向

	@Column(name = "ISHISCHOOL", length = 2)
	private String iSHiSchool;

	@Transient
	private String iSHiSchoolDicname;// 是否在高校园区字典值

	@Column(name = "ISCREATIVESPACE", length = 2)
	private String iSCreativespace;

	@Transient
	private String iSCreativespaceDicname;// 是否在众创空间字典值

	@Column(name = "ISHOLDING", length = 2)
	private String iSHolding;

	@Transient
	private String iSHoldingDicname;// 是否是控股企业字典值

	@Column(name = "ISTHREENEWBOARD", length = 2)
	private String iSThreeNewBoard;

	@Transient
	private String iSThreeNewBoardDicname;// 是否是新三板企业字典值

	@Column(name = "ISHITECH", length = 2)
	private String iSHItech;

	@Transient
	private String industry2;// 添加二级行业字段

	@Column(name = "FINANCINGPHASE", length = 2)
	private String financingphase;

	@Transient
	private String financingphaseDicname;// 现融资阶段类型字符串

	@Transient
	private String currencyDicname;// 实收资本币种字典值

	@Column(name = "HISCHOOLNUMBER", precision = 10, scale = 0)
	private Double hiSchoolNumber;

	@Column(name = "DEVELOPNUMBER", precision = 10, scale = 0)
	private Double developNumber;

	@Column(name = "TBDESCRIBES", length = 300)
	private String tbdescribes;

	@Column(name = "ORTHERINDUSTRY")
	private String ortherIndustry;// 其他行业字符串
	// companyBaseSupplement

	@Transient
	private String iSHItechDicname;// 是否是高新技术企业字典值

	public String getiSHiTechParkDicname() {
		String ishicName="";
		if(RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO, this.iSHiTechPark).equals("--")){
			ishicName="";
		}else{
			ishicName=RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO, this.iSHiTechPark);
		}
		return ishicName;
	}

	public void setiSHiTechParkDicname(String iSHiTechParkDicname) {
		this.iSHiTechParkDicname = iSHiTechParkDicname;
	}

	public String getiSEgggeneratorDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO, this.iSEgggenerator);
	}

	public void setiSEgggeneratorDicname(String iSEgggeneratorDicname) {
		this.iSEgggeneratorDicname = iSEgggeneratorDicname;
	}

	public String getiSHiSchoolDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO, this.iSHiSchool);
	}

	public void setiSHiSchoolDicname(String iSHiSchoolDicname) {
		this.iSHiSchoolDicname = iSHiSchoolDicname;
	}

	public String getiSCreativespaceDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO, this.iSCreativespace);
	}

	public void setiSCreativespaceDicname(String iSCreativespaceDicname) {
		this.iSCreativespaceDicname = iSCreativespaceDicname;
	}

	public String getiSHoldingDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO, this.iSHolding);
	}

	public void setiSHoldingDicname(String iSHoldingDicname) {
		this.iSHoldingDicname = iSHoldingDicname;
	}

	public String getiSThreeNewBoardDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO, this.iSThreeNewBoard);
	}

	public void setiSThreeNewBoardDicname(String iSThreeNewBoardDicname) {
		this.iSThreeNewBoardDicname = iSThreeNewBoardDicname;
	}

	public String getiSHItechDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO, this.iSHItech);
	}

	public void setiSHItechDicname(String iSHItechDicname) {
		this.iSHItechDicname = iSHItechDicname;
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

	public String getIndustry() {
		if(industry!=null){
			return industry;
		}else{
			return "";
		}
		
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

	public String getiSHiSchool() {
		return iSHiSchool;
	}

	public void setiSHiSchool(String iSHiSchool) {
		this.iSHiSchool = iSHiSchool;
	}

	public String getiSCreativespace() {
		return iSCreativespace;
	}

	public void setiSCreativespace(String iSCreativespace) {
		this.iSCreativespace = iSCreativespace;
	}

	public String getiSHolding() {
		return iSHolding;
	}

	public void setiSHolding(String iSHolding) {
		this.iSHolding = iSHolding;
	}

	public String getiSThreeNewBoard() {
		return iSThreeNewBoard;
	}

	public void setiSThreeNewBoard(String iSThreeNewBoard) {
		this.iSThreeNewBoard = iSThreeNewBoard;
	}

	public String getiSHItech() {
		return iSHItech;
	}

	public void setiSHItech(String iSHItech) {
		this.iSHItech = iSHItech;
	}

	public String getFinancingphase() {
		return financingphase;
	}

	public void setFinancingphase(String financingphase) {
		this.financingphase = financingphase;
	}

	public String getTbdescribes() {
		return tbdescribes;
	}

	public Double getHiSchoolNumber() {
		return hiSchoolNumber;
	}

	public void setHiSchoolNumber(Double hiSchoolNumber) {
		this.hiSchoolNumber = hiSchoolNumber;
	}

	public Double getDevelopNumber() {
		return developNumber;
	}

	public void setDevelopNumber(Double developNumber) {
		this.developNumber = developNumber;
	}

	public void setTbdescribes(String tbdescribes) {
		this.tbdescribes = tbdescribes;
	}

	public String getOrtherIndustry() {
		return ortherIndustry;
	}

	public void setOrtherIndustry(String ortherIndustry) {
		this.ortherIndustry = ortherIndustry;
	}

	public String getIndustry2() {
		return industry2;
	}

	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}

	public String getIndustry2Dicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2, this.industry);
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
	}

	public void setCurrencyDicname(String currencyDicname) {
		this.currencyDicname = currencyDicname;
	}

}
