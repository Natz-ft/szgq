package com.icfcc.SRRPDao.jpa.entity.creditscore.report;

public class CWZBVo implements ICapVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7262122545207505896L;
	public String time_current_year_caiwu;
	public String styleType;
	public String indexnum;
	public String zbStr;
	public String beforelastyear;
	public String lastyear;
	public String lastyearcurmonth;
	public String curyear;
	
	public CWZBVo(String indexnum,String zbStr,String beforelastyear,String lastyear,String lastyearcurmonth,String curyear){
		this.indexnum=indexnum;
		this.zbStr=zbStr;
		this.beforelastyear=beforelastyear;
		this.lastyear=lastyear;
		this.lastyearcurmonth=lastyearcurmonth;
		this.curyear=curyear;
	}
	public String getIndexnum() {
		return indexnum;
	}
	public void setIndexnum(String indexnum) {
		this.indexnum = indexnum;
	}
	public String getZbStr() {
		return zbStr;
	}
	public void setZbStr(String zbStr) {
		this.zbStr = zbStr;
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
	public String getLastyearcurmonth() {
		return lastyearcurmonth;
	}
	public void setLastyearcurmonth(String lastyearcurmonth) {
		this.lastyearcurmonth = lastyearcurmonth;
	}
	
	
}
