package com.icfcc.SRRPDao.jpa.entity.creditscore.report;


public class SeniorExecutiveInfoVo implements ICapVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4047860818502132843L;
		//职务
		private String   member_type;
				//姓名
		private String  member_name;
		public String getMember_type() {
			return member_type;
		}
		public void setMember_type(String member_type) {
			this.member_type = member_type;
		}
		public String getMember_name() {
			return member_name;
		}
		public void setMember_name(String member_name) {
			this.member_name = member_name;
		}
		
	
}
