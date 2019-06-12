package com.icfcc.SRRPDao.jpa.entity.creditscore.report;


public class PatentInfoVo implements ICapVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7017402545798511143L;
		//专利类型
		private String  pat_type;
		//专利证书号
		private String  pat_cert_no;
		//专利名称
		private String  name;
		//授权公告日
		private String pat_date;
		public String getPat_type() {
			return pat_type;
		}
		public void setPat_type(String pat_type) {
			this.pat_type = pat_type;
		}
		public String getPat_cert_no() {
			return pat_cert_no;
		}
		public void setPat_cert_no(String pat_cert_no) {
			this.pat_cert_no = pat_cert_no;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPat_date() {
			return pat_date;
		}
		public void setPat_date(String pat_date) {
			this.pat_date = pat_date;
		}
		
	
	
}
