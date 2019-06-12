/**
 * 
 */
package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.icfcc.ssrp.util.DigitFormatUtil;

/**
 * @author lijj 融资事件进度查询bean、主管部门用
 */
@Entity
public class EventBeanForCharge implements Serializable {

	private static final long serialVersionUID = -7278742436135924575L;

	@Column(name = "enterprise_name")
	private String enterpriseName;// 投资机构名称

	@Column(name = "status")
	private String status;// 拟投资阶段

	@Column(name = "trade")
	private String trade;// 行业

	@Column(name = "project_name")
	private String projectName;// 企业名称

	@Column(name = "turn")
	private String turn;// 企业投资轮次

	@Transient
	private String sell;// 出让比例

	@Column(name = "publish_time")
	private Date publishTime;

	@Column(name = "focus_time")
	private Date focusTime;

	@Transient
	public String amount;

	@Id
	@Column(name = "info_id")
	private String infoId;// 融资事件id

	@Column(name = "enterprise_id")
	private String enterpriseId;// 企业的id

	@Column(name = "AMOUNTMIN")
	private Double amountMin = 0.0;

	@Column(name = "AMOUNTMAX")
	private Double amountMax = 0.0;

	@Column(name = "SCALEMIN", length = 10)
	private String scaleMin;

	@Column(name = "SCALEMAX", length = 10)
	private String scaleMax;

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public String getSell() {
		if ("0".equals(this.sell)) {
			String tmpScale = "";
			if (this.scaleMin == null || "".equals(this.scaleMin)) {
				tmpScale = "0.00%";
			} else {
				tmpScale = DigitFormatUtil.digitFormat(this.scaleMin) + "%";
			}
			if (this.scaleMax == null || "".equals(this.scaleMax)) {
				tmpScale += "~" + "0.00%";
			} else {
				tmpScale += "~" + DigitFormatUtil.digitFormat(this.scaleMax)
						+ "%";
			}
			return tmpScale;
		} else {
			return "N/A";
		}
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getFocusTime() {
		return focusTime;
	}

	public void setFocusTime(Date focusTime) {
		this.focusTime = focusTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getScaleMin() {
		return scaleMin;
	}

	public void setScaleMin(String scaleMin) {
		this.scaleMin = scaleMin;
	}

	public String getScaleMax() {
		return scaleMax;
	}

	public void setScaleMax(String scaleMax) {
		this.scaleMax = scaleMax;
	}
}
