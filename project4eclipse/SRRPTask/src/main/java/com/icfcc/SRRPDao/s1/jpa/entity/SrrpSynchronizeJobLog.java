package com.icfcc.SRRPDao.s1.jpa.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 后台同步数据定时任务执行表
 * @author huguo
 *
 */
@Entity
@Table(name = "synchronize_job_log")
public class SrrpSynchronizeJobLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8407440406904661851L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//日志id
	
    @Column(name = "job_id")
	private Long jobId;//任务id
    
    
	@Column(name = "job_name")
	private String jobName;//任务名字
	
	@Column(name = "execute_result")
	private String exeResult;//任务状态
	
	@Column(name = "fail_reason")
	private String failReason;//任务状态
	
	@Column(name="starttime")
	private Date startTime;
	
	@Column(name="endtime")
	private Date endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}


	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}


	public String getExeResult() {
		return exeResult;
	}

	public void setExeResult(String exeResult) {
		this.exeResult = exeResult;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	
	
	
    

}



