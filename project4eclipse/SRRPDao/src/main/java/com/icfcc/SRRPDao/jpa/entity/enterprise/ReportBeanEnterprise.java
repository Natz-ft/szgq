package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 企业上榜统计bean
 * 
 * @author lijj
 *
 */
@Entity
@Table(name = "rp_report_company")
public class ReportBeanEnterprise implements Serializable, Comparable<ReportBeanEnterprise> {

    private static final long serialVersionUID = 4151322816041435070L;

    @Id
    @Column(name = "rid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String rid;

    @Column(name = "enterprise_id")
    private String enterpriseId;

    @Column(name = "name")
    private String name;

    @Column(name = "finacing_turn")
    private String finacingTurn;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "time_id")
    private String timeId;

    @Column(name = "rank_count")
    private Long rankCount;

    @Column(name = "time_point")
    private Date timePoint;

    @Column(name = "create_time")
    private Date createTime;
    @Transient
    private Integer rank;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public Long getRankCount() {
        return rankCount;
    }

    public void setRankCount(Long rankCount) {
        this.rankCount = rankCount;
    }

    public Date getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Date timePoint) {
        this.timePoint = timePoint;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public int compareTo(ReportBeanEnterprise o) {
        return o.getAmount().compareTo(this.getAmount());
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinacingTurn() {
        return finacingTurn;
    }

    public void setFinacingTurn(String finacingTurn) {
        this.finacingTurn = finacingTurn;
    }

}
