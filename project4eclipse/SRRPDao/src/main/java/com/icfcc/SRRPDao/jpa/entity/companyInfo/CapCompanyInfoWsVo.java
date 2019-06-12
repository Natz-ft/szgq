package com.icfcc.SRRPDao.jpa.entity.companyInfo;

import java.io.Serializable;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;



public class CapCompanyInfoWsVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -664558822387989615L;

	// 序号
	private String id;

	// 企业名称corp_name
	private String corpname;
	// 企业ID
	private String corpid;
	// 企业编号
	private String corpcode;
	// 信用分
	private String creditscore;

	// 信用等级
	private String xydji;

	// 评价日期
	private String pjdate;

	// 信用等级
	private String xydjiStr;
	
	// 是否有贷户loan_flag
	private String loanflaginfo;

	// 授权标志
	private String authflaginfo;
	// 金融办授权标志
	private String financeauthflaginfo;
	
	
	public String getCorpname() {
		return corpname;
	}
	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getCorpcode() {
		return corpcode;
	}
	public void setCorpcode(String corpcode) {
		this.corpcode = corpcode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreditscore() {
		return creditscore;
	}
	public void setCreditscore(String creditscore) {
		this.creditscore = creditscore;
	}
	public String getXydji() {
		return xydji;
	}
	public void setXydji(String xydji) {
		this.xydji = xydji;
	}
	
	public String getXydjiStr() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_XYDJI, this.xydji);
	}
	public void setXydjiStr(String xydjiStr) {
		this.xydjiStr = xydjiStr;
	}
	public String getPjdate() {
		return pjdate;
	}
	public void setPjdate(String pjdate) {
		this.pjdate = pjdate;
	}
	public String getLoanflaginfo() {
		return loanflaginfo;
	}
	public void setLoanflaginfo(String loanflaginfo) {
		this.loanflaginfo = loanflaginfo;
	}
	public String getAuthflaginfo() {
		return authflaginfo;
	}
	public void setAuthflaginfo(String authflaginfo) {
		this.authflaginfo = authflaginfo;
	}
	public String getFinanceauthflaginfo() {
		return financeauthflaginfo;
	}
	public void setFinanceauthflaginfo(String financeauthflaginfo) {
		this.financeauthflaginfo = financeauthflaginfo;
	}
}
