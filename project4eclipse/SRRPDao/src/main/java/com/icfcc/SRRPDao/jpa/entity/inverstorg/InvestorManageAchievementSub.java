package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 管理业绩实体类
 * 
 * @author Administrator
 */
@Data
@Entity
@Table(name = "rp_investor_manage_achievement")
public class InvestorManageAchievementSub implements Serializable {

	private static final long serialVersionUID = 34610624223264977L;

	@Id
	@Column(name = "manage_id", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String manageId;// 管理业绩id

	@Column(name = "invest_id")
	private String investId;// 投资机构id

	@Column(name = "fund_name")
	private String fundName;// 管理基金名称

	@Column(name = "regist_date")
	private Date fundRegistDate;// 注册时间

	@Column(name = "regist_address")
	private String registAddress;// 注册地址
	@Column(name = "trusteeship")
	private String trusteeship;// 托管机构
	@Column(name = "manage_capital_min")
	private Double manageCapitalMin;// 管理资金最小规模(万元)

	@Column(name = "manage_capital_max")
	private Double manageCapitalMax;// 管理资金最大规模(万元)

	@Column(name = "currency")
	private String currency;// 管理资金规模币种

	@Column(name = "icc_filing_no")
	private String iccFilingNo;// 中基协备案号

	@Column(name = "fund_type")
	private String fundType;// 基金类型

	@Column(name = "invest_trade")
	private String investTrade;// 投资行业定位

	@Column(name = "invest_stage")
	private String investStage;// 投资阶段定位

	@Column(name = "investment_projects")
	private Double investmentProjects;// 投资项目数量

	@Column(name = "cumulative_investment")
	private Double cumulativeInvestment;// 累计投资金额

	@Column(name = "ci_currency")
	private String ciCurrency;// 累计投资金额币种

	@Column(name = "implement_exit_project")
	private Double implementExitProject;// 实现退出项目数

	@Column(name = "invest_name")
	private String investName;

	@Column(name = "subac_name")
	private String subacName;

	@Column(name = "cnt")
	private int cnt;

	public String getInvestName() {
		return investName;
	}

	public void setInvestName(String investName) {
		this.investName = investName;
	}

	public String getSubacName() {
		return subacName;
	}

	public void setSubacName(String subacName) {
		this.subacName = subacName;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Transient
	private String registDateString;// 展示注册时间字符串的字段

	@Transient
	private String manageCapital;// 管理资金拼接的字符串
	@Transient
	private String foundTypeDicname;// 资本类型字符串
	@Transient
	private String financeStageDicname;// 拟投资阶段字符串
	@Transient
	private String financeTradeDicname;// 拟投资行业字符串
	@Transient
	private String financeStageDicCode;// 拟投资阶段字符串
	@Transient
	private String financeTradeDicCode;// 拟投资行业字符串
	@Transient
	private String foundTypeDicCode;// 拟投资行业字符串
	@Transient
	private String ciCurrencyCode;// 拟投资行业字符串
	@Transient
	private String currencyDicnameCode;// 拟投资行业字符串
	
	@Transient
	private String currencyDicname;// 管理资金币种字典值
	@Transient
	private String ciCurrencyDicname;// 累计投资币种字典值
	@Transient
	private String ciCurrencyStr;// 累计投资币种字符串
	@Transient
	private String registAddressStr;// 注册地字符串
	
	@Transient
	private String registAddressCode;// 区域
	
	
	
	public String getRegistAddressCode() {
		return registAddressCode;
	}

	public void setRegistAddressCode(String registAddressCode) {
		if (registAddressCode != null&& !"".equals(registAddressCode)) {
			
			this.registAddress = RedisGetValue.getCodeByValueArea(SRRPConstant.DD_AREA,
					registAddressCode.trim());
		}
	}

	public String getFoundTypeDicname() {
		String foundTypeDicname = "";
		if (fundType != null) {
			foundTypeDicname = RedisGetValue.getValueByCode(SRRPConstant.DD_SUBACTYPE, this.fundType);
		}
		return foundTypeDicname;
	}

	public void setFoundTypeDicname(String foundTypeDicname) {
		this.foundTypeDicname = foundTypeDicname;
	}

	public String getFinanceStageDicname() {
		StringBuffer sb = new StringBuffer();
		if (investStage != null) {
			String[] values = getInvestStage().split(",");
			for (int i = 0; i < values.length; i++) {
				String Stage = null;
				Stage = RedisGetValue.getValueByCode(SRRPConstant.DD_INVESTMENT, values[i]);
				if (Stage != null) {
					if (sb.length() > 0) {
						sb.append(",").append(Stage);
					} else {
						sb.append(Stage);
					}
				}
			}
		}

		return sb.toString();
	}

	public void setFinanceStageDicname(String financeStageDicname) {
		this.financeStageDicname = financeStageDicname;
	}

	public String getFinanceTradeDicname() {
		StringBuffer sb = new StringBuffer();
		if (investTrade != null) {
			String[] values = getInvestTrade().split(",");
			for (int i = 0; i < values.length; i++) {
				String Trade = null;
				Trade = RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY, values[i]);
				if (Trade != null && Trade!="--") {
					if (sb.length() > 0) {
						sb.append(",").append(Trade);
					} else {
						sb.append(Trade);
					}
				}else{
					Trade = RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2, values[i]);
					if (Trade != null) {
						if (sb.length() > 0) {
							sb.append(",").append(Trade);
						} else {
							sb.append(Trade);
						}
				}
				}
					
			}
		}

		return sb.toString();
	}

	public void setFinanceTradeDicname(String financeTradeDicname) {
		this.financeTradeDicname = financeTradeDicname;
	}

	public String getManageId() {
		return manageId;
	}

	
	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public Date getFundRegistDate() {
		return fundRegistDate;
	}

	public void setFundRegistDate(Date fundRegistDate) {
		this.fundRegistDate = fundRegistDate;
	}

	public String getRegistAddress() {
		return registAddress;
	}

	public void setRegistAddress(String registAddress) {
		this.registAddress = registAddress;
	}

	public String getTrusteeship() {
		return trusteeship;
	}

	public void setTrusteeship(String trusteeship) {
		this.trusteeship = trusteeship;
	}

	public Double getManageCapitalMin() {

		return manageCapitalMin;
	}

	public void setManageCapitalMin(Double manageCapitalMin) {
		this.manageCapitalMin = manageCapitalMin;
	}

	public Double getManageCapitalMax() {
		return manageCapitalMax;
	}

	public void setManageCapitalMax(Double manageCapitalMax) {
		this.manageCapitalMax = manageCapitalMax;
	}

	public String getInvestTrade() {
		return investTrade;
	}

	public void setInvestTrade(String investTrade) {
		this.investTrade = investTrade;
	}

	public String getRegistDateString() {
		String str = null;
		if (fundRegistDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			str = sdf.format(fundRegistDate);
		}
		return str;
	}

	public void setRegistDateString(String registDateString) {
		this.registDateString = registDateString;
	}

	public String getManageCapital() {
		String manageCapital = "";
		if (this.manageCapitalMin == null || "".equals(this.manageCapitalMin)) {
			manageCapital = "0";
		} else {
			manageCapital = DigitFormatUtil.digitFormat(this.manageCapitalMin).toString();
		}
		if (this.manageCapitalMax == null || "".equals(this.manageCapitalMax)) {
			manageCapital += "—" + "0";
		} else {
			manageCapital += "—" + DigitFormatUtil.digitFormat(this.manageCapitalMax).toString()+" "+this.getCurrencyDicname();
		}
		return manageCapital;
	}

	public void setManageCapital(String manageCapital) {
		this.manageCapital = manageCapital;
	}

	public String getIccFilingNo() {
		if(iccFilingNo!=null){
			iccFilingNo=iccFilingNo.toUpperCase();
		}
		return iccFilingNo;
	}

	public void setIccFilingNo(String iccFilingNo) {
		this.iccFilingNo = iccFilingNo;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getInvestStage() {
		return investStage;
	}

	public void setInvestStage(String investStage) {
		this.investStage = investStage;
	}

	public Double getInvestmentProjects() {
		return investmentProjects;
	}

	public void setInvestmentProjects(Double investmentProjects) {
		this.investmentProjects = investmentProjects;
	}

	public Double getCumulativeInvestment() {
		return cumulativeInvestment;
	}

	public void setCumulativeInvestment(Double cumulativeInvestment) {
		this.cumulativeInvestment = cumulativeInvestment;
	}

	public Double getImplementExitProject() {
		return implementExitProject;
	}

	public void setImplementExitProject(Double implementExitProject) {
		this.implementExitProject = implementExitProject;
	}

	public String getFinanceStageDicCode() {
		return financeStageDicCode;
	}

	public void setFinanceStageDicCode(String financeStageDicCode) {
		StringBuffer sb = new StringBuffer();
		
		if (financeStageDicCode != null) {
			financeStageDicCode=financeStageDicCode.replace(",", "，");
			String[] values = null;
			if(financeStageDicCode.contains(",")){
				values = financeStageDicCode.split(",");

			}else if(financeStageDicCode.contains("，")){
				values = financeStageDicCode.split("，");

			}else{
				values = financeStageDicCode.split(",");
			}
			for (int i = 0; i < values.length; i++) {
				String Trade = null;
				Trade = RedisGetValue.getCodeByValue(SRRPConstant.DD_INVESTMENT, values[i].trim());
				if (Trade != null) {
					if (sb.length() > 0) {
						sb.append(",").append(Trade);
					} else {
						sb.append(Trade);
					}
				}
			}
			this.investStage = sb.toString();
		}

	}

	public String getFinanceTradeDicCode() {
		return financeTradeDicCode;
	}

	public void setFinanceTradeDicCode(String financeTradeDicCode) {
		StringBuffer sb = new StringBuffer();
		if (financeTradeDicCode != null && !"".equals(financeTradeDicCode)) {
			financeTradeDicCode=financeTradeDicCode.replace(",", "，");
			String[] values = null;
			if(financeTradeDicCode.contains(",")){
				values = financeTradeDicCode.split(",");

			}else if(financeTradeDicCode.contains("，")){
				values = financeTradeDicCode.split("，");

			}else{
				values = financeTradeDicCode.split(",");
			}
			for (int i = 0; i < values.length; i++) {
				String Trade = null;
				
				Trade = RedisGetValue.getCodeByValue(SRRPConstant.DD_INDUSTRY, values[i].trim());
				if (Trade != null && !"".equals(Trade)) {
					if (sb.length() > 0) {
						sb.append(",").append(Trade);
					} else {
						sb.append(Trade);
					}
				}else{
					Trade = RedisGetValue.getCodeByValue(SRRPConstant.DD_INDUSTRY_2, values[i].trim());
					if (Trade != null) {
						if (sb.length() > 0) {
							sb.append(",").append(Trade);
						} else {
							sb.append(Trade);
						}
					}	
				}
			}
			this.investTrade = sb.toString();
		}
	}

	public String getFoundTypeDicCode() {
		return foundTypeDicCode;
	}

	public void setFoundTypeDicCode(String foundTypeDicCode) {
		String Trade = "";
		if (foundTypeDicCode != null  && !"".equals(foundTypeDicCode)) {
			Trade = RedisGetValue.getCodeByValue(SRRPConstant.DD_SUBACTYPE, foundTypeDicCode.trim());
			this.fundType = Trade;
		}

	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCiCurrency() {
		return ciCurrency;
	}

	public void setCiCurrency(String ciCurrency) {
		this.ciCurrency = ciCurrency;
	}

	public String getCurrencyDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
	}

	public void setCurrencyDicname(String currencyDicname) {
		this.currencyDicname = currencyDicname;
	}

	public String getCiCurrencyDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.ciCurrency);
	}

	public void setCiCurrencyDicname(String ciCurrencyDicname) {
		this.ciCurrencyDicname = ciCurrencyDicname;
	}

	public String getCiCurrencyStr() {
		String ciCurrencyStr = "";
		if (this.cumulativeInvestment==0) {
			ciCurrencyStr = "0";
		} else {
			ciCurrencyStr = DigitFormatUtil.digitFormat(this.cumulativeInvestment).toString()+" "+this.getCiCurrencyDicname();
		}
		return ciCurrencyStr;
	}

	public String getCiCurrencyCode() {
		return ciCurrencyCode;
	}

	public void setCiCurrencyCode(String ciCurrencyCode) {
		String Trade = "";
		if (ciCurrencyCode != null && !"".equals(ciCurrencyCode)) {
			if("人民币".equals(ciCurrencyCode)){
				ciCurrencyCode="CNY";
			}else if ("美元".equals(ciCurrencyCode)){
				ciCurrencyCode="USD";
			}
			Trade = RedisGetValue.getCodeByValue(SRRPConstant.DD_CURRENCY, ciCurrencyCode.trim());
			this.ciCurrency = Trade;
		}
	}

	public String getCurrencyDicnameCode() {
		return currencyDicnameCode;
	}

	public void setCurrencyDicnameCode(String currencyDicnameCode) {
		String Trade = "";
		if (currencyDicnameCode != null && !"".equals(currencyDicnameCode)) {
			if("人民币".equals(currencyDicnameCode)){
				currencyDicnameCode="CNY";
			}else if ("美元".equals(currencyDicnameCode)){
				currencyDicnameCode="USD";
			}
			Trade = RedisGetValue.getCodeByValue(SRRPConstant.DD_CURRENCY, currencyDicnameCode.trim());
			this.currency = Trade;
		}
	}

	public void setCiCurrencyStr(String ciCurrencyStr) {
		this.ciCurrencyStr = ciCurrencyStr;
	}
	/*
	 * public String getInvestProgressString() { if (this.investProgress !=
	 * null) { return
	 * DigitFormatUtil.digitFormat(this.investProgress).toString(); } else {
	 * return null; } }
	 * 
	 * public void setInvestProgressString(String investProgressString) {
	 * this.investProgressString = investProgressString; }
	 */
	public static void main(String arg[]){
		String password="12345678";
		for (int i =0;i<password.length();i++){
			System.out.println(String.valueOf((int)password.charAt(i)));
		}
	}

	public String getRegistAddressStr() {
		if (this.registAddress != null) {
			registAddressStr = RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, this.registAddress);
		}
		return registAddressStr;
	}

	public void setRegistAddressStr(String registAddressStr) {
		this.registAddressStr = registAddressStr;
	}
	 public String toString(){
		   String str=getFundName()+getRegistDateString()+getRegistAddress()+getTrusteeship()+
				   getManageCapitalMin()+getManageCapitalMax()+getCurrency()+getIccFilingNo()+getFundType()+getInvestTrade()
				   +getInvestStage()+getInvestmentProjects()+getCumulativeInvestment()+getCiCurrency()+getImplementExitProject();
		   return str;
		   
	   }
}
