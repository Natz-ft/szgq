package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

import lombok.Data;

/**
 * 企业技术产品 信息实体类
 * 
 * @author Administrator
 *
 */

@Data
@Entity
@Table(name = "rp_company_product")
public class CompanyProduct implements Serializable {

	private static final long serialVersionUID = 8001904160772971032L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "PRODUCT_ID", length = 32)
	private String productId;

	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;

	@Column(name = "NAME", length = 60)
	private String name;

	@Column(name = "INCOME_RATIO", length = 4)
	private Double incomeRatio;

	@Column(name = "NATIVELEAD", length = 10)
	private String nativelead;// 是否拥有著作权或专利

	@Transient
	private String nativeleadDicname;// 是否属国内领先水平

	@Column(name = "NATIONALLEAD", length = 10)
	private String nationallead;// 是否获得过奖项或政府评价等

	@Transient
	private String productName;
	@Transient
	private String nationalleadDicname;// 是否属于国际领先水平

	@Column(name = "NATIVE_BANK", length = 38)
	private String productFileName;// 文件名称

	@Column(name = "MARKET_RATIO", length = 4)
	private String productFileUrl;// 文件保存路径

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "CREATER_ID", length = 32)
	private String createrid;

	public String getProductId() {
		return productId;
	}

	public String getProductName() {
		return this.productName = this.name;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getNativeleadDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO,
				this.nativelead);
	}

	public void setNativeleadDicname(String nativeleadDicname) {
		this.nativeleadDicname = nativeleadDicname;
	}

	public String getNationalleadDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO,
				this.nationallead);
	}

	public void setNationalleadDicname(String nationalleadDicname) {
		this.nationalleadDicname = nationalleadDicname;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIncomeRatio() {
		if (this.incomeRatio == null) {
			return "";
		} else {
			return (String) DigitFormatUtil.digitFormat(incomeRatio);
		}
	}

	public void setIncomeRatio(Double incomeRatio) {
		this.incomeRatio = incomeRatio;
	}

	public String getNativelead() {
		return nativelead;
	}

	public void setNativelead(String nativelead) {
		this.nativelead = nativelead;
	}

	public String getNationallead() {
		return nationallead;
	}

	public void setNationallead(String nationallead) {
		this.nationallead = nationallead;
	}

	// public Double getNativeBank() {
	// return nativeBank;
	// }
	//
	// public void setNativeBank(Double nativeBank) {
	// this.nativeBank = nativeBank;
	// }
	//
	// public String getMarketRatio() {
	// if(this.marketRatio==null){
	// return "";
	// }else{
	// return (String) DigitFormatUtil.digitFormat(marketRatio);
	// }
	// }
	//
	// public void setMarketRatio(Double marketRatio) {
	// this.marketRatio = marketRatio;
	// }

	public Date getCreateTime() {
		return createTime;
	}

	public String getProductFileName() {
		return productFileName;
	}

	public void setProductFileName(String productFileName) {
		this.productFileName = productFileName;
	}

	public String getProductFileUrl() {
		return productFileUrl;
	}

	public void setProductFileUrl(String productFileUrl) {
		this.productFileUrl = productFileUrl;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreaterid() {
		return createrid;
	}

	public void setCreaterid(String createrid) {
		this.createrid = createrid;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}
