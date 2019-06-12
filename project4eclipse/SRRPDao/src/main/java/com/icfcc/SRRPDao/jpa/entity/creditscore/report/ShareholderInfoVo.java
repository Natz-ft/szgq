package com.icfcc.SRRPDao.jpa.entity.creditscore.report;


public class ShareholderInfoVo implements ICapVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3140905487443913531L;
		//股东名称
		private String   inv;
		//股东类型
		private String  invtype;
		//出资额(万)
		private String  subconam;
		public String getInv() {
			return inv;
		}
		public void setInv(String inv) {
			this.inv = inv;
		}
		public String getInvtype() {
			return invtype;
		}
		public void setInvtype(String invtype) {
			this.invtype = invtype;
		}
		public String getSubconam() {
			return subconam;
		}
		public void setSubconam(String subconam) {
			this.subconam = subconam;
		}
	
}
