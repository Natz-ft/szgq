package com.icfcc.credit.platform.jpa.entity.query;

import java.io.Serializable;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



import com.icfcc.credit.platform.constants.VipCont;

import lombok.Data;



/**
 * 企业补充信息实体类
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name = "rp_company_base_supplement")
public class CompanyBaseSupplement implements Serializable{

	private static final long serialVersionUID = 2270341371123305172L;
     
	
	@Id
	@Column(name = "ENTERPRISE_ID",length = 32)
	private String enterpriseId;
   
	@Column(name="INDUSTRY",length =4 )
    private String industry;

	@Transient
	private String industryDicname;// 行业类型字符串
	
	@Column(name="PAIDINCAPITAL",length=12)
    private Double paidincapital;
	
	@Column(name="ISHITECHPARK",length = 2)
	private String iSHiTechPark;
    
	@Transient
	private String iSHiTechParkDicname;//是否在高新园区字典值
	
	@Column(name="ISEGGGENERATOR",length=2)
    private String iSEgggenerator;
	
	@Transient
    private String iSEgggeneratorDicname;//是否属于孵化器内字典值
	
	@Column(name="ISHISCHOOL",length = 2)
   	private String iSHiSchool;
	
	@Transient
   	private String iSHiSchoolDicname;//是否在高校园区字典值
    
	@Column(name="ISCREATIVESPACE",length = 2)
	private String iSCreativespace;
	
	@Transient
	private String iSCreativespaceDicname;//是否在众创空间字典值
    
	@Column(name="ISHOLDING",length = 2)
	private String iSHolding;
    
	@Transient
	private String iSHoldingDicname;//是否是控股企业字典值
	
	@Column(name="ISTHREENEWBOARD",length = 2)
	private String iSThreeNewBoard;
	
	@Transient
	private String iSThreeNewBoardDicname;//是否是新三板企业字典值
    
	@Column(name="ISHITECH",length = 2)
	private String iSHItech;
	
	@Transient
	private String iSHItechDicname;//是否是高新技术企业字典值
    
	@Column(name="FINANCINGPHASE",length = 2)
	private String financingphase;
	
	@Transient
	private String financingphaseDicname;// 现融资阶段类型字符串
	
	@Column(name="HISCHOOLNUMBER", precision = 10, scale = 0)
    private Double hiSchoolNumber;
	
	@Column(name = "DEVELOPNUMBER", precision = 10, scale = 0)
    private Double developNumber;
    
	@Column(name="TBDESCRIBES",length = 300)
	private String tbdescribes;

	public String getIndustryDicname() {
		if(this.industry.length()>2){
			industryDicname=VipCont.getValueByCode("dd:industry2",this.industry);
		}else{
			industryDicname=VipCont.getValueByCode("dd:industry",this.industry);
		}
		return industryDicname;
	}

	public void setIndustryDicname(String industryDicname) {
		this.industryDicname = industryDicname;
	}

	public String getiSHiTechParkDicname() {
		return iSHiTechParkDicname;
	}

	public void setiSHiTechParkDicname(String iSHiTechParkDicname) {
		this.iSHiTechParkDicname = iSHiTechParkDicname;
	}

	public String getiSEgggeneratorDicname() {
		return iSEgggeneratorDicname;
	}

	public void setiSEgggeneratorDicname(String iSEgggeneratorDicname) {
		this.iSEgggeneratorDicname = iSEgggeneratorDicname;
	}

	public String getiSHiSchoolDicname() {
		return iSHiSchoolDicname;
	}

	public void setiSHiSchoolDicname(String iSHiSchoolDicname) {
		this.iSHiSchoolDicname = iSHiSchoolDicname;
	}

	public String getiSCreativespaceDicname() {
		return iSCreativespaceDicname;
	}

	public void setiSCreativespaceDicname(String iSCreativespaceDicname) {
		this.iSCreativespaceDicname = iSCreativespaceDicname;
	}

	public String getiSHoldingDicname() {
		return iSHoldingDicname;
	}

	public void setiSHoldingDicname(String iSHoldingDicname) {
		this.iSHoldingDicname = iSHoldingDicname;
	}

	public String getiSThreeNewBoardDicname() {
		return iSThreeNewBoardDicname;
	}

	public void setiSThreeNewBoardDicname(String iSThreeNewBoardDicname) {
		this.iSThreeNewBoardDicname = iSThreeNewBoardDicname;
	}

	public String getiSHItechDicname() {
		return iSHItechDicname;
	}

	public void setiSHItechDicname(String iSHItechDicname) {
		this.iSHItechDicname = iSHItechDicname;
	}

	public String getFinancingphaseDicname() {
		return financingphaseDicname;
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

}
