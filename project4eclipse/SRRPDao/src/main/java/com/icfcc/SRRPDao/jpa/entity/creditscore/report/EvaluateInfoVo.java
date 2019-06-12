package com.icfcc.SRRPDao.jpa.entity.creditscore.report;


public class EvaluateInfoVo implements ICapVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7019670127422038224L;
	//评价机构
	public String evaluate_organ;
	//评价内容
	public String evaluate_content;
	//评价结果
	public String  evaluate_grade;
	//文号
	public String file_no;
	//评价日期
	public String award_date;
	//信息更新日期
	public String create_time;
	public String getEvaluate_organ() {
		return evaluate_organ;
	}
	public void setEvaluate_organ(String evaluate_organ) {
		this.evaluate_organ = evaluate_organ;
	}
	public String getEvaluate_content() {
		return evaluate_content;
	}
	public void setEvaluate_content(String evaluate_content) {
		this.evaluate_content = evaluate_content;
	}
	public String getEvaluate_grade() {
		return evaluate_grade;
	}
	public void setEvaluate_grade(String evaluate_grade) {
		this.evaluate_grade = evaluate_grade;
	}
	public String getFile_no() {
		return file_no;
	}
	public void setFile_no(String file_no) {
		this.file_no = file_no;
	}
	public String getAward_date() {
		return award_date;
	}
	public void setAward_date(String award_date) {
		this.award_date = award_date;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
}
