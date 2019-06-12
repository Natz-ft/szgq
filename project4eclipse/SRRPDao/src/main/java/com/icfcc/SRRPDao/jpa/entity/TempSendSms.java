package com.icfcc.SRRPDao.jpa.entity;

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
@Table(name = "temp_send_sms")
public class TempSendSms implements Serializable {
	
		/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 3996918086977541696L;

		
			@Id
			@Column(name = "tss_id", length = 32)
			@GenericGenerator(name = "system-uuid", strategy = "uuid")
			@GeneratedValue(generator = "system-uuid")
			private String tssId;
			
			@Column(name = "rss_id", length = 32)
			private String rssId;
			
			@Column(name = "enterporinvest_id")
			private String enterporinvestId;

			@Column(name = "mobile")
			private String mobile;

			@Column(name = "sid")
			private String sid;

			@Column(name = "send_type")
			private String sendType;
			@Column(name = "name")
			private String name;
			@Column(name = "info_name")
			private String infoName;
			@Column(name = "send_time")
			private Date sendTime;
			@Column(name = "send_status")
			private String sendStatus;
			@Column(name = "infoorevent_id")
			private String infooreventId;
			
			public String getInfooreventId() {
				return infooreventId;
			}

			public void setInfooreventId(String infooreventId) {
				this.infooreventId = infooreventId;
			}

			

			public String getInfoName() {
				return infoName;
			}

			public void setInfoName(String infoName) {
				this.infoName = infoName;
			}

		

			public String getSendStatus() {
				return sendStatus;
			}

			public void setSendStatus(String sendStatus) {
				this.sendStatus = sendStatus;
			}

			public Date getSendTime() {
				return sendTime;
			}

			public void setSendTime(Date sendTime) {
				this.sendTime = sendTime;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getRssId() {
				return rssId;
			}

			public void setRssId(String rssId) {
				this.rssId = rssId;
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

			

			public String getSid() {
				return sid;
			}

			public void setSid(String sid) {
				this.sid = sid;
			}

			public String getTssId() {
				return tssId;
			}

			public void setTssId(String tssId) {
				this.tssId = tssId;
			}

			
			

}


