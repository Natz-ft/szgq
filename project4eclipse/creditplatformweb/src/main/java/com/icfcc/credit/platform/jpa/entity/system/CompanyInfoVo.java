package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
/**
 * 
* @ClassName: CompanyRestVo 
* @Description: TODO(接口同步企业信息VO) 
* @author hugt
* @date 2018年4月8日 下午6:18:10 
*
 */
public class CompanyInfoVo implements Serializable {

	
	private static final long serialVersionUID = 750768917144751176L;
	/**
	 * 企业名称
	 */
	private String name;
	/**
	 * 企业证件代码类型
	 */
	private String codetype;
	/**
	 * 企业证件代码号
	 */
	private String code;
	/**
	 * 用户ID
	 */
	private String user_id;
	/**
	 * 用户密码
	 */
	private String user_pwd;
	/**
	 * 联系人姓名
	 */
	private String contacname;
	/**
	 *联系人手机号
	 */
	private String contaccal;
	/**
	 * 法人代表姓名
	 */
	private String legalname;
	/**
	 * 法人代表手机号
	 */
	private String legalcal;
	/**
	 * 区域
	 */
	private String branchno;
	/**
	 * 九大类
	 */
	private String type;
	
	/**
     * 启用/停用标识； 0：启用  1：停用
     */
    private String stopFlag;
    
    
    
    
    public String getStopFlag() {
        return stopFlag;
    }
    public void setStopFlag(String stopFlag) {
        this.stopFlag = stopFlag;
    }
	/**
	 * 审核状态
	 */
	private String status;
	
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getContacname() {
		return contacname;
	}
	public void setContacname(String contacname) {
		this.contacname = contacname;
	}
	public String getContaccal() {
		return contaccal;
	}
	public void setContaccal(String contaccal) {
		this.contaccal = contaccal;
	}
	public String getLegalname() {
		return legalname;
	}
	public void setLegalname(String legalname) {
		this.legalname = legalname;
	}
	public String getLegalcal() {
		return legalcal;
	}
	public void setLegalcal(String legalcal) {
		this.legalcal = legalcal;
	}
	public String getBranchno() {
		return branchno;
	}
	public void setBranchno(String branchno) {
		this.branchno = branchno;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
