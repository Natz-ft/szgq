package com.icfcc.SRRPDao.jpa.entity.creditscore.report;


public class LoanInfoVo implements ICapVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7756652559042685151L;
		//小贷公司名称
		private String mc_bank_name;
		//金额
		private String mc_os_prcp;
		//放款日期
		private String mc_actv_dt;
		//到期日期
		private String mc_last_due_dt;
		//担保方式
		private String mc_main_gur_typ;
		
		
		//担保公司名称
		private String t_assure_corp_name;
		//贷款银行名称
		private String t_bank_name;
		//担保金额<br />(万元)
		private String t_assure_prcp;
		//担保责任<br />发生日期
		private String t_actv_dt;
		//担保责任<br />解除日期
		private String t_last_due_dt;
		//反担保方式
		private String t_unassure_type;
		
		// 金融机构名称/贴现金融机构名称
		private String finance_no;
		//贷款余额/票面金额
		private String loan_num_balance;
		//币种
		private String currency;
		//放款日期/贴现日期
		private String loan_date_send;
		//到期日期
		private String loan_date_expire;
		//担保方式
		private String guarantee_type;
		//五级分类
		private String five_category;

		public String getMc_bank_name() {
			return mc_bank_name;
		}
		public void setMc_bank_name(String mc_bank_name) {
			this.mc_bank_name = mc_bank_name;
		}
		public String getMc_os_prcp() {
			return mc_os_prcp;
		}
		public void setMc_os_prcp(String mc_os_prcp) {
			this.mc_os_prcp = mc_os_prcp;
		}
		public String getMc_actv_dt() {
			return mc_actv_dt;
		}
		public void setMc_actv_dt(String mc_actv_dt) {
			this.mc_actv_dt = mc_actv_dt;
		}
		public String getMc_last_due_dt() {
			return mc_last_due_dt;
		}
		public void setMc_last_due_dt(String mc_last_due_dt) {
			this.mc_last_due_dt = mc_last_due_dt;
		}
		public String getMc_main_gur_typ() {
			return mc_main_gur_typ;
		}
		public void setMc_main_gur_typ(String mc_main_gur_typ) {
			this.mc_main_gur_typ = mc_main_gur_typ;
		}
		public String getT_assure_corp_name() {
			return t_assure_corp_name;
		}
		public void setT_assure_corp_name(String t_assure_corp_name) {
			this.t_assure_corp_name = t_assure_corp_name;
		}
		public String getT_bank_name() {
			return t_bank_name;
		}
		public void setT_bank_name(String t_bank_name) {
			this.t_bank_name = t_bank_name;
		}
		public String getT_assure_prcp() {
			return t_assure_prcp;
		}
		public void setT_assure_prcp(String t_assure_prcp) {
			this.t_assure_prcp = t_assure_prcp;
		}
		public String getT_actv_dt() {
			return t_actv_dt;
		}
		public void setT_actv_dt(String t_actv_dt) {
			this.t_actv_dt = t_actv_dt;
		}
		public String getT_last_due_dt() {
			return t_last_due_dt;
		}
		public void setT_last_due_dt(String t_last_due_dt) {
			this.t_last_due_dt = t_last_due_dt;
		}
		public String getT_unassure_type() {
			return t_unassure_type;
		}
		public void setT_unassure_type(String t_unassure_type) {
			this.t_unassure_type = t_unassure_type;
		}
		public String getFinance_no() {
			return finance_no;
		}
		public void setFinance_no(String finance_no) {
			this.finance_no = finance_no;
		}
		public String getLoan_num_balance() {
			return loan_num_balance;
		}
		public void setLoan_num_balance(String loan_num_balance) {
			this.loan_num_balance = loan_num_balance;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getLoan_date_send() {
			return loan_date_send;
		}
		public void setLoan_date_send(String loan_date_send) {
			this.loan_date_send = loan_date_send;
		}
		public String getLoan_date_expire() {
			return loan_date_expire;
		}
		public void setLoan_date_expire(String loan_date_expire) {
			this.loan_date_expire = loan_date_expire;
		}
		public String getGuarantee_type() {
			return guarantee_type;
		}
		public void setGuarantee_type(String guarantee_type) {
			this.guarantee_type = guarantee_type;
		}
		public String getFive_category() {
			return five_category;
		}
		public void setFive_category(String five_category) {
			this.five_category = five_category;
		}
		

}
