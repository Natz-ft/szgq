package com.icfcc.SRRPDao.jpa.entity.creditscore.report;


public class NegativeInfoVo implements ICapVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2401290061325950141L;
		//欠缴/逾期类型
		private String h_source;
		//欠缴/逾期金额
		private String h_yqje;
		//欠缴/逾期截至日期
		private String h_yqsj;
		//信息更新日期
		private String h_create_time;
		
		//处罚部门
		private String i_pun_type;
		//处罚决定书号
		private String i_pun_deci;
		//处罚日期
		private String i_pun_time;
		//违法行为
		private String i_illegal_act;
		//违法行为<br />所属期间
		private String i_skssqsr;
		//处罚内容
		private String i_pun_content;
		//信息更新日期
		private String i_create_time;

		//督办级别
		private String gpdb_duty_level;
		//隐患类别
		private String gpdb_problem_type;
		//挂牌日期
		private String gpdb_start_date;
		//整改期限
		private String gpdb_expire_date;
		//跟踪督办部门
		private String gpdb_flow_org;
		
		
		//认定部门
		private String m_rdbm;
		//认定日期
		private String m_fzchrdsj;
		//信息更新日期
		private String m_create_time;
		
		//认定类型
		private String z_leib;
		//信息更新日期
		private String z_create_time;

		//评价机构
		private String k2_evaluate_organ;
		//评价内容
		private String k2_evaluate_content;
		//评价等级
		private String k2_evaluate_grade;
		//评价日期
		private String k2_award_date;
		//信息更新日期
		private String 	k2_create_time;
		
		
		//认定部门
		private String a2_cjjg;
		//检查时间
		private String a2_ccsj;
		//产品名称
		private String a2_cpmc;
		//商标
		private String a2_shangb;
		//不合格项目
		private String a2_bhgxm;
		//检查结论
		private String a2_jyjl;
		
		//评价机构
		private String k3_evaluate_organ;
		//评价内容
		private String k3_evaluate_content;
		//评价结果
		private String k3_evaluate_grade;
		//评价日期
		private String k3_award_date;
		//信息更新日期
		private String k3_create_time;
		public String getH_source() {
			return h_source;
		}
		public void setH_source(String h_source) {
			this.h_source = h_source;
		}
		public String getH_yqje() {
			return h_yqje;
		}
		public void setH_yqje(String h_yqje) {
			this.h_yqje = h_yqje;
		}
		public String getH_yqsj() {
			return h_yqsj;
		}
		public void setH_yqsj(String h_yqsj) {
			this.h_yqsj = h_yqsj;
		}
		public String getH_create_time() {
			return h_create_time;
		}
		public void setH_create_time(String h_create_time) {
			this.h_create_time = h_create_time;
		}
		public String getI_pun_type() {
			return i_pun_type;
		}
		public void setI_pun_type(String i_pun_type) {
			this.i_pun_type = i_pun_type;
		}
		public String getI_pun_deci() {
			return i_pun_deci;
		}
		public void setI_pun_deci(String i_pun_deci) {
			this.i_pun_deci = i_pun_deci;
		}
		public String getI_pun_time() {
			return i_pun_time;
		}
		public void setI_pun_time(String i_pun_time) {
			this.i_pun_time = i_pun_time;
		}
		public String getI_illegal_act() {
			return i_illegal_act;
		}
		public void setI_illegal_act(String i_illegal_act) {
			this.i_illegal_act = i_illegal_act;
		}
		public String getI_skssqsr() {
			return i_skssqsr;
		}
		public void setI_skssqsr(String i_skssqsr) {
			this.i_skssqsr = i_skssqsr;
		}
		public String getI_pun_content() {
			return i_pun_content;
		}
		public void setI_pun_content(String i_pun_content) {
			this.i_pun_content = i_pun_content;
		}
		public String getI_create_time() {
			return i_create_time;
		}
		public void setI_create_time(String i_create_time) {
			this.i_create_time = i_create_time;
		}
		public String getGpdb_duty_level() {
			return gpdb_duty_level;
		}
		public void setGpdb_duty_level(String gpdb_duty_level) {
			this.gpdb_duty_level = gpdb_duty_level;
		}
		public String getGpdb_problem_type() {
			return gpdb_problem_type;
		}
		public void setGpdb_problem_type(String gpdb_problem_type) {
			this.gpdb_problem_type = gpdb_problem_type;
		}
		public String getGpdb_start_date() {
			return gpdb_start_date;
		}
		public void setGpdb_start_date(String gpdb_start_date) {
			this.gpdb_start_date = gpdb_start_date;
		}
		public String getGpdb_expire_date() {
			return gpdb_expire_date;
		}
		public void setGpdb_expire_date(String gpdb_expire_date) {
			this.gpdb_expire_date = gpdb_expire_date;
		}
		public String getGpdb_flow_org() {
			return gpdb_flow_org;
		}
		public void setGpdb_flow_org(String gpdb_flow_org) {
			this.gpdb_flow_org = gpdb_flow_org;
		}
		public String getM_rdbm() {
			return m_rdbm;
		}
		public void setM_rdbm(String m_rdbm) {
			this.m_rdbm = m_rdbm;
		}
		public String getM_fzchrdsj() {
			return m_fzchrdsj;
		}
		public void setM_fzchrdsj(String m_fzchrdsj) {
			this.m_fzchrdsj = m_fzchrdsj;
		}
		public String getM_create_time() {
			return m_create_time;
		}
		public void setM_create_time(String m_create_time) {
			this.m_create_time = m_create_time;
		}
		public String getZ_leib() {
			return z_leib;
		}
		public void setZ_leib(String z_leib) {
			this.z_leib = z_leib;
		}
		public String getZ_create_time() {
			return z_create_time;
		}
		public void setZ_create_time(String z_create_time) {
			this.z_create_time = z_create_time;
		}
		public String getK2_evaluate_organ() {
			return k2_evaluate_organ;
		}
		public void setK2_evaluate_organ(String k2_evaluate_organ) {
			this.k2_evaluate_organ = k2_evaluate_organ;
		}
		public String getK2_evaluate_content() {
			return k2_evaluate_content;
		}
		public void setK2_evaluate_content(String k2_evaluate_content) {
			this.k2_evaluate_content = k2_evaluate_content;
		}
		public String getK2_evaluate_grade() {
			return k2_evaluate_grade;
		}
		public void setK2_evaluate_grade(String k2_evaluate_grade) {
			this.k2_evaluate_grade = k2_evaluate_grade;
		}
		public String getK2_award_date() {
			return k2_award_date;
		}
		public void setK2_award_date(String k2_award_date) {
			this.k2_award_date = k2_award_date;
		}
		public String getK2_create_time() {
			return k2_create_time;
		}
		public void setK2_create_time(String k2_create_time) {
			this.k2_create_time = k2_create_time;
		}
		public String getA2_cjjg() {
			return a2_cjjg;
		}
		public void setA2_cjjg(String a2_cjjg) {
			this.a2_cjjg = a2_cjjg;
		}
		public String getA2_ccsj() {
			return a2_ccsj;
		}
		public void setA2_ccsj(String a2_ccsj) {
			this.a2_ccsj = a2_ccsj;
		}
		public String getA2_cpmc() {
			return a2_cpmc;
		}
		public void setA2_cpmc(String a2_cpmc) {
			this.a2_cpmc = a2_cpmc;
		}
		public String getA2_shangb() {
			return a2_shangb;
		}
		public void setA2_shangb(String a2_shangb) {
			this.a2_shangb = a2_shangb;
		}
		public String getA2_bhgxm() {
			return a2_bhgxm;
		}
		public void setA2_bhgxm(String a2_bhgxm) {
			this.a2_bhgxm = a2_bhgxm;
		}
		public String getA2_jyjl() {
			return a2_jyjl;
		}
		public void setA2_jyjl(String a2_jyjl) {
			this.a2_jyjl = a2_jyjl;
		}
		public String getK3_evaluate_organ() {
			return k3_evaluate_organ;
		}
		public void setK3_evaluate_organ(String k3_evaluate_organ) {
			this.k3_evaluate_organ = k3_evaluate_organ;
		}
		public String getK3_evaluate_content() {
			return k3_evaluate_content;
		}
		public void setK3_evaluate_content(String k3_evaluate_content) {
			this.k3_evaluate_content = k3_evaluate_content;
		}
		public String getK3_evaluate_grade() {
			return k3_evaluate_grade;
		}
		public void setK3_evaluate_grade(String k3_evaluate_grade) {
			this.k3_evaluate_grade = k3_evaluate_grade;
		}
		public String getK3_award_date() {
			return k3_award_date;
		}
		public void setK3_award_date(String k3_award_date) {
			this.k3_award_date = k3_award_date;
		}
		public String getK3_create_time() {
			return k3_create_time;
		}
		public void setK3_create_time(String k3_create_time) {
			this.k3_create_time = k3_create_time;
		}
		
		
	
}
