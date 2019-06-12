package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

import com.icfcc.credit.platform.constants.VipCont;
/**
 * 后台同步数据定时任务执行表
 * @author huguo
 *
 */
/**
 * 后台同步数据定时任务执行表
 * @author huguo
 *
 */
@Entity
@Table(name = "srrp_synchronize_job")
public class SrrpSynchronizeJobBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8407440406904661851L;

	@Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jobId;//任务id
	
	@Column(name = "job_name")
	private String jobName;//任务名字
	
	@Column(name = "job_class")
	private String jobClass;//任务名字
	
	@Column(name = "job_descripition")
	private String jobDescription;//任务描述
	
	@Column(name = "job_cron")
	private String jobCron;//任务定时执行参数
	
	@Column(name = "job_execute_count")
	private int exeCount;//任务定时执行参数
	
	@Column(name = "job_status")
	private String status;//任务状态
	
	@Column(name="ismodify")
	private String isModify;
	
	@Column(name="stop_flag")
	private String stopFlag;
	
	@Column(name="isvalid")
	private String isValid;

	@Column(name="job_data")
	private Date jobData;
	
	@Column(name="next_job_date")
	private String nextjobData;
	

	public String getNextjobData() {
		return nextjobData;
	}

	public void setNextjobData(String nextjobData) {
		this.nextjobData = nextjobData;
	}
	public Date getJobData() {
		return jobData;
	}

	public void setJobData(Date jobData) {
		this.jobData = jobData;
	}
	@Transient
	private String statusStr;
//	public String getNextCron(String cron)  {
//		String NextCron=null;
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();  
//	       try {
//			cronTriggerImpl.setCronExpression(cron);
//			 Calendar calendar = Calendar.getInstance();
//		       Date now = calendar.getTime();  
//		       calendar.add(Calendar.MONTH, 1);//把统计的区间段设置为从现在到2年后的今天（主要是为了方法通用考虑，如那些1个月跑一次的任务，如果时间段设置的较短就不足20条)  
//		       List<Date> dates = TriggerUtils.computeFireTimesBetween(cronTriggerImpl, null, now, calendar.getTime());//这个是重点，一行代码搞定~~  
//		       if(dates.size()<1){
//		    	   NextCron="时间已过期";
//		       }else{
//		    	   NextCron =df.format(dates.get(0));
//		       }
//		       
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	      
//		return NextCron;
//	}
	public String getStatusStr() {
		return VipCont.getValueByCode("dd:tasktatus",this.status);
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
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


	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getJobCron() {
		return this.jobCron;
	}

	public void setJobCron(String jobCron) {
		this.jobCron = jobCron;
	}

	public int getExeCount() {
		return exeCount;
	}

	public void setExeCount(int exeCount) {
		this.exeCount = exeCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}

	public String getStopFlag() {
		
		return stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	
	
    

}


