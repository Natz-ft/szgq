package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

@Entity
@Table(name = "rp_company_stockholder_pending")
public class CompanyStockholderPending implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7004197785271636766L;

	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;

	@Id
	@Column(name = "HOLDER_ID", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String holderId;

	@Column(name = "NAME", length = 50)
	private String holderName;

	@Column(name = "HOLDER_TYPE", length = 10)
	private String holderType;

	@Column(name = "CONTRIBUTION", length = 20)
	private Double contribution;

	@Column(name = "c_currency")
	private String cCurrency;

	@Column(name = "RATIO", length = 4)
	private Double ratio;

	@Column(name = "CONTRIBUTION_TYPE", length = 10)
	private Double contributionType;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "CREATER_ID", length = 32)
	private String createrId;


	@Transient
	private String ratioName;

	@Transient
	private String holderTypeDicname;// 股东类型

	@Transient
	private String cCurrencyDicname;// 股出资额的币种展示

	@Transient
	private String contributionTypeDicname;

	@Transient
	private String contributionStr;// 出资金额字符串

	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	// LiWCH 添加非空校验
	public String getContributionTypeDicname() {

		if (this.contributionType == null) {
			return "";
		} else {
			DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
			df.applyPattern("##.##");
			return RedisGetValue.getValueByCode(SRRPConstant.DD_INVESTMENTTYPR,
					df.format(this.contributionType));
		}
	}

	public void setContributionTypeDicname(String contributionTypeDicname) {
		this.contributionTypeDicname = contributionTypeDicname;
	}

	public String getHolderTypeDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_STOCKHOLDER,
				this.holderType);
	}

	public void setHolderTypeDicname(String holderTypeDicname) {
		this.holderTypeDicname = holderTypeDicname;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getHolderId() {
		return holderId;
	}
//
//	public void setHolderId(String holderId) {
//		this.holderId = holderId;
//	}

	public String getHolderType() {
		return holderType;
	}

	public void setHolderType(String holderType) {
		this.holderType = holderType;
	}

	// LiWCH 添加非空校验
	public Double getContribution() {
		return contribution;
	}

	public void setContribution(Double contribution) {
		this.contribution = contribution;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	// 添加出资比例的显示字段
	public String getRatioName() {
		if (this.ratio == null) {
			return "";
		} else {
			return (String) DigitFormatUtil.digitFormat(ratio);
		}
	}

	public void setRatioName(String ratioName) {
		this.ratioName = ratioName;
	}

	public Double getContributionType() {
		return contributionType;
	}

	public void setContributionType(Double contributionType) {
		this.contributionType = contributionType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getcCurrency() {
		return cCurrency;
	}

	public void setcCurrency(String cCurrency) {
		this.cCurrency = cCurrency;
	}

	public String getcCurrencyDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY,
				this.cCurrency);
	}

	public void setcCurrencyDicname(String cCurrencyDicname) {
		this.cCurrencyDicname = cCurrencyDicname;
	}

	public String getContributionStr() {
		String tmpAmount = "";
		if(this.contribution!=null){
			if (this.contribution == 0|| this.contribution==null) {
				tmpAmount = "0"+this.getcCurrencyDicname();
			} else {
				tmpAmount = DigitFormatUtil.digitFormat(this.contribution)
						.toString()+"  "+this.getcCurrencyDicname() ;
			}
		}
		
		return tmpAmount;
	}

	public void setContributionStr(String contributionStr) {
		this.contributionStr = contributionStr;
	}

}
