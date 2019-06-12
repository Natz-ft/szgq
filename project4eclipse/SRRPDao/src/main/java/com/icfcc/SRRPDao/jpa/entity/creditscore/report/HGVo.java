package com.icfcc.SRRPDao.jpa.entity.creditscore.report;


public class HGVo implements ICapVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2578090339945261618L;
	public String typeStr;
	public String beforelastyear;
	public String lastyear;
	public String curyear;
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	public String getBeforelastyear() {
		return beforelastyear;
	}
	public void setBeforelastyear(String beforelastyear) {
		this.beforelastyear = beforelastyear;
	}
	public String getLastyear() {
		return lastyear;
	}
	public void setLastyear(String lastyear) {
		this.lastyear = lastyear;
	}
	public String getCuryear() {
		return curyear;
	}
	public void setCuryear(String curyear) {
		this.curyear = curyear;
	}
	
}
