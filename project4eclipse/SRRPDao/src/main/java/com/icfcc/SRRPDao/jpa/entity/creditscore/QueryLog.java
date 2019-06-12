package com.icfcc.SRRPDao.jpa.entity.creditscore;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rp_querylog")
public class QueryLog {
	
	
	@Id
	@Column(name = "QUERY_ID",length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String query_id;
	
	@Column(name = "USERID")
	private String userid;
	
	@Column(name = "BEGINTIME")
	private Date begintime;
	
	@Column(name = "ENDTIME")
	private Date endtime;  
	
	@Column(name = "QUERYTYPE")
	private String querytype;
	
	@Column(name = "ISSUC")
	private String issuc;   
	
	@Column(name = "CERTNO")
	private String certno;   
	
	@Column(name = "CERTTYPE")
	private String certtype;
	
	@Column(name = "QUERYNAME")
	private String queryname;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Date getBegintime() {
		return begintime;
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getQuerytype() {
		return querytype;
	}
	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}
	public String getIssuc() {
		return issuc;
	}
	public void setIssuc(String issuc) {
		this.issuc = issuc;
	}
	public String getCertno() {
		return certno;
	}
	public void setCertno(String certno) {
		this.certno = certno;
	}
	public String getCerttype() {
		return certtype;
	}
	public void setCerttype(String certtype) {
		this.certtype = certtype;
	}
	public String getQueryname() {
		return queryname;
	}
	public void setQueryname(String queryname) {
		this.queryname = queryname;
	}     
	
	


}
