package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "rp_send_sms_log")
public class SendSmsLog implements Serializable {
	
		/** 
		* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
		*/ 
		private static final long serialVersionUID = 3378956715346132025L;

		
			@Id
			@Column(name = "rss_id", length = 32)
			@GenericGenerator(name = "system-uuid", strategy = "uuid")
			@GeneratedValue(generator = "system-uuid")
			private String rssId;
			
			@Column(name = "infoorevent_id")
			private String infoId;
			@Column(name = "enterporinvest_id")
			private String enterporinvestId;

			@Column(name = "mobile")
			private String mobile;

			@Column(name = "operdate")
			private  Date operdate;

			@Column(name = "sid")
			private String sid;

			@Column(name = "send_date")
			private Date sendDate;

			@Column(name = "answer_date")
			private Date answerDate;

			@Column(name = "answer_content")
			private String answer_content;

			@Column(name = "send_status")
			private String sendStatus;
			
			@Column(name = "send_type")
			private String sendType;
			
			
		

			public String getRssId() {
				return rssId;
			}

			public void setRssId(String rssId) {
				this.rssId = rssId;
			}

			public String getInfoId() {
				return infoId;
			}

			public void setInfoId(String infoId) {
				this.infoId = infoId;
			}

			public String getSendType() {
				return sendType;
			}

			public void setSendType(String sendType) {
				this.sendType = sendType;
			}


			public String getEnterporinvestId() {
				return enterporinvestId;
			}

			public void setEnterporinvestId(String enterporinvestId) {
				this.enterporinvestId = enterporinvestId;
			}

			public String getMobile() {
				return mobile;
			}

			public void setMobile(String mobile) {
				this.mobile = mobile;
			}

			

			public Date getOperdate() {
				return operdate;
			}

			public void setOperdate(Date operdate) {
				this.operdate = operdate;
			}

			public String getSid() {
				return sid;
			}

			public void setSid(String sid) {
				this.sid = sid;
			}

			public Date getSendDate() {
				return sendDate;
			}

			public void setSendDate(Date sendDate) {
				this.sendDate = sendDate;
			}

			public Date getAnswerDate() {
				return answerDate;
			}

			public void setAnswerDate(Date answerDate) {
				this.answerDate = answerDate;
			}

			public String getAnswer_content() {
				return answer_content;
			}

			public void setAnswer_content(String answer_content) {
				this.answer_content = answer_content;
			}

			public String getSendStatus() {
				return sendStatus;
			}

			public void setSendStatus(String sendStatus) {
				this.sendStatus = sendStatus;
			}

			
}

