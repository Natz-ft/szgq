package com.icfcc.SRRPDao.s1.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String enterpriseId;
   
	@Column(name="INDUSTRY",length =4 )
    private String industry;

	@Column(name="PAIDINCAPITAL",length=12)
    private Double paidincapital;
	
	@Column(name="ISHITECHPARK",length = 2)
	private String iSHiTechPark;
    
	@Column(name="ISEGGGENERATOR",length=2)
    private String iSEgggenerator;
	
	@Column(name="ISHISCHOOL",length = 2)
   	private String iSHiSchool;
    
	@Column(name="ISCREATIVESPACE",length = 2)
	private String iSCreativespace;
    
	@Column(name="ISHOLDING",length = 2)
	private String iSHolding;
    
	@Column(name="ISTHREENEWBOARD",length = 2)
	private String iSThreeNewBoard;
    
	@Column(name="ISHITECH",length = 2)
	private String iSHItech;
    
	@Column(name="FINANCINGPHASE",length = 2)
	private String financingphase;
	
	@Column(name="HISCHOOLNUMBER",length=6)
    private Double hiSchoolNumber;
	
	@Column(name="DEVELOPNUMBER",length=6)
    private Double developNumber;
    
	@Column(name="TBDESCRIBES",length = 300)
	private String tbdescribes;

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

	public String getTbdescribes() {
		return tbdescribes;
	}

	public void setTbdescribes(String tbdescribes) {
		this.tbdescribes = tbdescribes;
	}

	
	
	
	
}
