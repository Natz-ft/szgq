package com.icfcc.SRRPDao.jpa.entity.creditscore.report;


public class SocialSecurityVo implements ICapVO{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 4511629961221353189L;
		//缴存月份
		private String   b_deposit_month;
		//社保人数
		private String   sbrs;
		//社保金额
		private String   sbje;
		//公积金人数
		private String   b_person_num;
		//公积金金额
		private String  b_amount;
		public String getB_deposit_month() {
			return b_deposit_month;
		}
		public void setB_deposit_month(String b_deposit_month) {
			this.b_deposit_month = b_deposit_month;
		}
		public String getSbrs() {
			return sbrs;
		}
		public void setSbrs(String sbrs) {
			this.sbrs = sbrs;
		}
		public String getSbje() {
			return sbje;
		}
		public void setSbje(String sbje) {
			this.sbje = sbje;
		}
		public String getB_person_num() {
			return b_person_num;
		}
		public void setB_person_num(String b_person_num) {
			this.b_person_num = b_person_num;
		}
		public String getB_amount() {
			return b_amount;
		}
		public void setB_amount(String b_amount) {
			this.b_amount = b_amount;
		}
		
	
	
}
