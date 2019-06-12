package com.icfcc.SRRPDao.jpa.entity.enterprise;

/**
 *融资事件操作记录表实体类
 */
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "rp_finacing_records")
public class FinacingRecord implements Serializable {

	private static final long serialVersionUID = -5312637063685144999L;

	@Id
	@Column(name = "record_id")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String recordId;
	/**
	 * 投融事件的具体列
	 */
	@Column(name = "event_id")
	private String eventId;// 融资事件的id

	@Column(name = "info_id")
	private String infoId;// 融资信息的id

	@Column(name = "enterprise_id")
	private String enterpriseid;// 企业id

	@Column(name = "investorg_id")
	private String investorgid;// 投资机构的id

	@Column(name = "opertype")
	private String opertype;// 操作类型

	@Column(name = "opercontent")
	private String opercontent;// 操作内容

	@Column(name = "status")
	private String status;// 需求状态

	@Column(name = "operuser")
	private String operuser;// 操作人

	@Column(name = "operdate")
	private Date operdate;// 擦合时间

	@Column(name = "un_look")
	private String unLook="1";// 是否被查看
	
	public String getEnterpriseid() {
		return enterpriseid;
	}

	public void setEnterpriseid(String enterpriseid) {
		this.enterpriseid = enterpriseid;
	}

	public String getInvestorgid() {
		return investorgid;
	}

	public void setInvestorgid(String investorgid) {
		this.investorgid = investorgid;
	}

	public String getOpertype() {
		return opertype;
	}

	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}

	public String getOpercontent() {
		return opercontent;
	}

	public void setOpercontent(String opercontent) {
		this.opercontent = opercontent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperuser() {
		return operuser;
	}

	public void setOperuser(String operuser) {
		this.operuser = operuser;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getOperdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(this.operdate);
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getUnLook() {
		return unLook;
	}

	public void setUnLook(String unLook) {
		this.unLook = unLook;
	}

}
