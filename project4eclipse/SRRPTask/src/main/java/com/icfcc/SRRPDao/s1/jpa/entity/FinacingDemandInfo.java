package com.icfcc.SRRPDao.s1.jpa.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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

import io.netty.util.internal.StringUtil;

/**
 * 融资需求信息实体类
 */
@Entity
@Table(name = "rp_finacing_demand")
public class FinacingDemandInfo implements Serializable {
	private static final long serialVersionUID = 5655672544175384019L;
	/**
	 * 融资需求信息实体类
	 */
		@Id
		@Column(name = "INFO_ID", length = 32)
		@GenericGenerator(name = "system-uuid", strategy = "uuid")
		@GeneratedValue(generator = "system-uuid")
		private String infoId;

		@Column(name = "ENTERPRISE_ID", length = 32)
		private String enterpriseId;

		@Column(name = "PROJECT_NAME", length = 60)
		private String projectName;

		@Temporal(TemporalType.DATE)
		@Column(name = "FOLLOW_TIME")
		private Date followTime;

		@Column(name = "OPEN", length = 1)
		private String open;

		@Column(name = "CURRENCY", length = 3)
		private String currency;

		@Column(name = "SELL", length = 1)
		private String sell;

		@Column(name = "APPOINT_INVESTOR", length = 100)
		private String appointInvestor;

		@Column(name = "REL_NAME", length = 20)
		private String relName;

		@Column(name = "REL_PHONE", length = 20)
		private String relPhone;

		@Column(name = "DESCRIPTION", length = 2000)
		private String description;

		@Column(name = "MULTI", length = 10)
		private String multi;

		@Column(name = "STATUS", length = 10)
		private String status;

		@Column(name = "OPERUSER", length = 10)
		private String operuser;

		@Column(name = "OPERDATE")
		private Date operdate;

		@Column(name = "FINACING_TURN", length = 3)
		private String finacingTurn;

		@Lob
		@Basic(fetch = FetchType.LAZY)
		@Column(name = "CLOSE_REASON")
		private byte[] closeReason;// 关闭原因

		@Column(name = "AMOUNTMIN")
		private Double amountMin;

		@Column(name = "AMOUNTMAX")
		private Double amountMax;

		@Column(name = "SCALEMIN", length = 4)
		private Double scaleMin;

		@Column(name = "SCALEMAX", length = 4)
		private Double scaleMax;
		@Column(name = "update_flag")
		private int updateFlag;
		@Column(name = "revoke_flag")
		private String revokeFlag="0";//主管是否撤销标识1为撤销0为未撤销
		public int getUpdateFlag() {
			return updateFlag;
		}

		public void setUpdateFlag(int updateFlag) {
			this.updateFlag = updateFlag;
		}

		public String getRevokeFlag() {
			return revokeFlag;
		}

		public void setRevokeFlag(String revokeFlag) {
			this.revokeFlag = revokeFlag;
		}

		public String getInfoId() {
			return infoId;
		}

		public void setInfoId(String infoId) {
			this.infoId = infoId;
		}

		public String getEnterpriseId() {
			return enterpriseId;
		}

		public void setEnterpriseId(String enterpriseId) {
			this.enterpriseId = enterpriseId;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public Date getFollowTime() {
			return followTime;
		}

		public void setFollowTime(Date followTime) {
			this.followTime = followTime;
		}

		public String getOpen() {
			return open;
		}

		public void setOpen(String open) {
			this.open = open;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public String getSell() {
			return sell;
		}

		public void setSell(String sell) {
			this.sell = sell;
		}

		public String getAppointInvestor() {
			return appointInvestor;
		}

		public void setAppointInvestor(String appointInvestor) {
			this.appointInvestor = appointInvestor;
		}

		public String getRelName() {
			return relName;
		}

		public void setRelName(String relName) {
			this.relName = relName;
		}

		public String getRelPhone() {
			return relPhone;
		}

		public void setRelPhone(String relPhone) {
			this.relPhone = relPhone;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getMulti() {
			return multi;
		}

		public void setMulti(String multi) {
			this.multi = multi;
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

		public Date getOperdate() {
			return operdate;
		}

		public void setOperdate(Date operdate) {
			this.operdate = operdate;
		}

		public String getFinacingTurn() {
			return finacingTurn;
		}

		public void setFinacingTurn(String finacingTurn) {
			this.finacingTurn = finacingTurn;
		}

		public String getCloseReason() throws UnsupportedEncodingException {
			if (closeReason == null || "".equals(closeReason)) {
				return "";
			}
			return new String(closeReason, "utf-8");
		}

		public void setCloseReason(String closeReason) throws UnsupportedEncodingException {
			if (!StringUtil.isNullOrEmpty(closeReason)) {
				this.closeReason = closeReason.getBytes("utf-8");
			} else {
				this.closeReason = null;
			}

		}

		public Double getAmountMin() {
			return amountMin;
		}

		public void setAmountMin(Double amountMin) {
			this.amountMin = amountMin;
		}

		public Double getAmountMax() {
			return amountMax;
		}

		public void setAmountMax(Double amountMax) {
			this.amountMax = amountMax;
		}

		public Double getScaleMin() {
			return scaleMin;
		}

		public void setScaleMin(Double scaleMin) {
			this.scaleMin = scaleMin;
		}

		public Double getScaleMax() {
			return scaleMax;
		}

		public void setScaleMax(Double scaleMax) {
			this.scaleMax = scaleMax;
		}
}
