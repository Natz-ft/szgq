package com.icfcc.SRRPDao.jpa.entity.declareAward;

import java.util.Date;
import java.util.List;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.session.investorArea;

/**
 * 参数对象
 * @author huguo
 *
 */
public class DeclareRewarSearshBean  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1999423151315837496L;
	// 每页最大记录数
	public int maxSize = 10;
	// 首页
	private int firstPage = 1;
	// 上一页
	private int prePage;
	// 下一页
	private int nextPage;
	// 尾页
	private int lastPage;
	// 当前页
	private int curPage = 1;
	//奖励申报id
	public String declareId ;
	//申请机构id
    public String investorId ;
    //奖励投资记录id
    public String finacingEventId ;
    //申报单位
    public String declareName ;
    //申报单位证件类型
    public String certtype ;
    //申报单位证件号码
    public String certno;
    //注册地
    public String registered_address ;
    //被投企业名称
    public String companyName ;
    //被投企业工商注册地
    public String companyAddress ;
    //联系人姓名
    public String relName ;
   //联系人手机号
    public String relPhone ;
   //开户银行
    public String bankDeposit ;
    //银行账号
    public String bankAccount ;
    //申报状态
    public String declareStatus ;
    //申报时间
    public String validityTime ;
    //申报用户
    public String validityUser ;
    //申报有效标识
    public String validityFlag ;
    //管理机构信息
    public Investor invest;
    public String areaProvince; // 区域省代码
    public String areaCity; // 区域省代码
    public String areaCounty; // 区域省代码
    
    public String areaProvinceName; // 区域省名称
    public String areaCityName; // 区域市名称
    public String areaCountyName; // 区域区县名称
    
	public Date declare_begin_time;
	public Date declare_end_time;
	public String rearea;
	Object areaProvinceList=null;
	Object areaCityList=null;
	Object areaCountyList=null;
	public String isQuery;
	public String userType;
	public String getRearea() {
		return rearea;
	}

	public void setRearea(String rearea) {
		this.rearea = rearea;
	}

	public void setAreaCountyList(Object areaCountyList) {
		this.areaCountyList = areaCountyList;
	}

	public Object getAreaProvinceList() {
		return net.sf.json.JSONArray.fromObject(RedisGetValue.getAeaDataList(SRRPConstant.AREA_Province));
	}

	public void setAreaProvinceList(Object areaProvinceList) {
		this.areaProvinceList = areaProvinceList;
	}

	public Object getAreaCityList() {
		return net.sf.json.JSONArray.fromObject(RedisGetValue.getAeaDataList(SRRPConstant.AREA_City));
	}

	public void setAreaCityList(Object areaCityList) {
		this.areaCityList = areaCityList;
	}

	public Object getAreaCountyList() {
		return net.sf.json.JSONArray.fromObject(RedisGetValue.getAeaDataList(SRRPConstant.AREA_County));
	}

	public void setAreaCountyList(List<investorArea> areaCountyList) {
		this.areaCountyList = areaCountyList;
	}

	// 申报投资记录信息
	public DeclareRewarReport report;

	public String getDeclareId() {
		return declareId;
	}

	public void setDeclareId(String declareId) {
		this.declareId = declareId;
	}

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	public String getFinacingEventId() {
		return finacingEventId;
	}

	public void setFinacingEventId(String finacingEventId) {
		this.finacingEventId = finacingEventId;
	}

	public String getDeclareName() {
		return declareName;
	}

	public void setDeclareName(String declareName) {
		this.declareName = declareName;
	}

	public String getCerttype() {
		return certtype;
	}

	public void setCerttype(String certtype) {
		this.certtype = certtype;
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public String getRegistered_address() {
		return registered_address;
	}

	public void setRegistered_address(String registered_address) {
		this.registered_address = registered_address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getRelPhone() {
		return relPhone;
	}

	public void setRelPhone(String relPhone) {
		this.relPhone = relPhone;
	}

	public String getBankDeposit() {
		return bankDeposit;
	}

	public void setBankDeposit(String bankDeposit) {
		this.bankDeposit = bankDeposit;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getDeclareStatus() {
		return declareStatus;
	}

	public void setDeclareStatus(String declareStatus) {
		this.declareStatus = declareStatus;
	}

	public String getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(String validityTime) {
		this.validityTime = validityTime;
	}

	public String getValidityUser() {
		return validityUser;
	}

	public void setValidityUser(String validityUser) {
		this.validityUser = validityUser;
	}

	public String getValidityFlag() {
		return validityFlag;
	}

	public void setValidityFlag(String validityFlag) {
		this.validityFlag = validityFlag;
	}

	public Investor getInvest() {
		return invest;
	}

	public void setInvest(Investor invest) {
		this.invest = invest;
	}

	public DeclareRewarReport getReport() {
		return report;
	}

	public void setReport(DeclareRewarReport report) {
		this.report = report;
	}

	public String getAreaProvince() {
		return areaProvince;
	}

	public void setAreaProvince(String areaProvince) {
		this.areaProvince = areaProvince;
	}

	public String getAreaCity() {
		return areaCity;
	}

	public void setAreaCity(String areaCity) {
		this.areaCity = areaCity;
	}

	public String getAreaCounty() {
		return areaCounty;
	}

	public void setAreaCounty(String areaCounty) {
		this.areaCounty = areaCounty;
	}

	public Date getDeclare_begin_time() {
		return declare_begin_time;
	}

	public void setDeclare_begin_time(Date declare_begin_time) {
		this.declare_begin_time = declare_begin_time;
	}

	public Date getDeclare_end_time() {
		return declare_end_time;
	}

	public void setDeclare_end_time(Date declare_end_time) {
		this.declare_end_time = declare_end_time;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAreaProvinceName() {
		return RedisGetValue.getAreaValueByCode(SRRPConstant.AREA_Province, this.areaProvince);
	}

	public void setAreaProvinceName(String areaProvinceName) {
		this.areaProvinceName = areaProvinceName;
	}

	public String getAreaCityName() {
		return RedisGetValue.getAreaValueByCode(SRRPConstant.AREA_City, this.areaCity);
	}

	public void setAreaCityName(String areaCityName) {
		this.areaCityName = areaCityName;
	}

	public String getAreaCountyName() {
		return RedisGetValue.getAreaValueByCode(SRRPConstant.AREA_County, this.areaCounty);
	}

	public void setAreaCountyName(String areaCountyName) {
		this.areaCountyName = areaCountyName;
	}
	
	

}


