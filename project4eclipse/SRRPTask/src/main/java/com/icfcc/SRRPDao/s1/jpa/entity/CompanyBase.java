package com.icfcc.SRRPDao.s1.jpa.entity;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

/**
 *  企业基本信息实体类
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
	@Column(name = "ENTERPRISE_ID",length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String enterpriseId;
   
	@Column(name="NAME",length =50 )
    private String name;
	
	@Column(name="CODETYPE",length =4 )
    private String codetype;

	@Column(name="CODE",length = 20)
	private String code;
    
	@Column(name="REGCAPITAL",length=12)
    private Double regcapital;
	
	@Temporal(TemporalType.DATE)
	@Column(name="REDATE")
   	private Date redate;
    
	@Column(name="REAREA",length = 50)
   	private String rearea;
    
	@Column(name="CONTACTTYPE",length = 10)
	private String contacttype;
    
	@Column(name="LEGALNAME",length = 10)
	private String legalName;
    
	@Column(name="LEGALCAL",length = 15)
	private String legalCal;
    
	@Column(name="CONTACNAME",length = 10)
	private String contacName;
    
	@Column(name="CONTACCAL",length = 15)
	private String contacCal;
	@Column(name = "STOCKNAME", length = 10)
	private String stockName;

	@Column(name = "STOCKCAL", length = 15)
	private String stockCal;
	
	public String getEnterpriseId() {
		return enterpriseId;
	}

	
	public String getName() {
		return name;
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
	
	
}
