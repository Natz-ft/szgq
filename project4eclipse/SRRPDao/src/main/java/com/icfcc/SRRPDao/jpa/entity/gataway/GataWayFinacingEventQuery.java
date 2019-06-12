package com.icfcc.SRRPDao.jpa.entity.gataway;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

@Data
@Entity
@Table(name = "platform_portal_finacing_event_query")
public class GataWayFinacingEventQuery implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "event_id")
    private String eventId;
    
    @Column(name= "info_id")
    private String infoId;
    
    @Column(name = "project_name")
    private String projectName;

    @Column(name = "investorname")
    private String investorName;

    @Column(name = "enterprisename")
    private String enterpriseName;

    @Column(name = "industry")
    private String industry;

    @Transient
    private String industryName;

    @Column(name = "finacing_turn")
    private String finacingTurn;

    @Transient
    private String finacingTurnName;

    @Transient
    private String awayFromNow;

    @Transient
    private String areaName;
    @Transient
    private String financeStageStr;
    
    @Column(name = "rearea")
    private String reArea;

    // 投资阶段
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "finance_stage")
    private byte[] financeStage;

    @Column(name = "amount", columnDefinition = "decimal(15,2)")
    private double amount;

    @Column(name = "scale")
    private String scale;

    @Column(name = "description")
    private String description;

    @Column(name = "operdate")
    private Date operDate;

    @Column(name = "open")
    private String open;

    // 门户展示标题实用
    @Transient
    private String showInfos;
    
    @Transient
    private String showInfosA;
    

    public String getShowInfosA() {
		return showInfosA;
	}

	public void setShowInfosA(String showInfosA) {
		this.showInfosA = showInfosA;
	}

	public String getFinanceStageStr() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INVESTMENT, this.getFinanceStage());
	}

	public void setFinance_stageStr(String financeStageStr) {
		this.financeStageStr = financeStageStr;
	}

	public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getInvestorName() {
        return investorName;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getFinacingTurn() {
        return finacingTurn;
    }

    public void setFinacingTurn(String finacingTurn) {
        this.finacingTurn = finacingTurn;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperDate() {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(this.operDate);
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getReArea() {
        return reArea;
    }

    public void setReArea(String reArea) {
        this.reArea = reArea;
    }

    public String getFinacingTurnName() {
        return finacingTurnName;
    }

    public void setFinacingTurnName(String finacingTurnName) {
        this.finacingTurnName = finacingTurnName;
    }

    public String getFinanceStage() {
        String financeStageStr = "";
        try {
            if (financeStage != null) {

                financeStageStr = new String(financeStage, SRRPConstant.DEFAULTCHARTS);

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return financeStageStr;
    }

    public void setFinanceStage(String financeStage) {
        try {
            this.financeStage = financeStage.getBytes(SRRPConstant.DEFAULTCHARTS);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getAwayFromNow() {
        return awayFromNow;
    }

    public void setAwayFromNow(String awayFromNow) {
        this.awayFromNow = awayFromNow;
    }

    public String getShowInfos() {
        return this.showInfos;
    }

    public void setShowInfos(String showInfos) {
        this.showInfos = showInfos;
    }
    
    public String getInfoId()
	{
		return infoId;
	}

	public void setInfoId(String infoId)
	{
		this.infoId = infoId;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
