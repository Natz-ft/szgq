package com.icfcc.SRRPDao.jpa.entity.managedept;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @ClassName: ManageDissentInfor
 * @Description: TODO(异议信息实体类)
 * @author hugt
 * @date 2018年12月25日 上午10:48:37
 */
@Data
@Entity
@Table(name = "rp_alerts_meassge")
public class MessageAlertInfo implements Serializable {
    /**
     * @Fields serialVersionUID : TODO(序列Id)
     */
    private static final long serialVersionUID = 2143208181708241898L;

    /**
    * @Fields dissentId : TODO(主鍵Id)
    */
    @Id
    @Column(name = "meassge_id", length = 32)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String meassgeId;

    /**
     * @Fields companyId : TODO(企业/机构id的id)
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * @Fields dissentContent : TODO(异议的内容)
     */
    @Column(name = "meassge_content")
    private String meassgeContent;

    /**
     * @Fields cancelContent : TODO(解除原因)
     */
    @Column(name = "message_event_id")
    private String messagEventId;

    /**
     * @Fields dissentType : TODO(00:企业异议；01：机构异议
     */
    @Column(name = "message_user_id")
    private String messagUserId;

    /**
     * @Fields dissentStatus : TODO(00:企业新增，01，异议新增)
     */
    @Column(name = "message_type")
    private String messageType;

    /**
     * @Fields operUserId : TODO(操作用户ID)
     */
    @Column(name = "oper_user_id")
    private String operUserId;

    /**
     * @Fields operDate : TODO(操作创建时间)
     */
    @Column(name = "oper_date")
    private Date operDate;

    

    public String getMeassgeId() {
        return meassgeId;
    }



    public void setMeassgeId(String meassgeId) {
        this.meassgeId = meassgeId;
    }



    public String getCompanyId() {
        return companyId;
    }



    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }



    public String getMeassgeContent() {
        return meassgeContent;
    }



    public void setMeassgeContent(String meassgeContent) {
        this.meassgeContent = meassgeContent;
    }



    public String getMessagEventId() {
        return messagEventId;
    }



    public void setMessagEventId(String messagEventId) {
        this.messagEventId = messagEventId;
    }



    public String getMessagUserId() {
        return messagUserId;
    }



    public void setMessagUserId(String messagUserId) {
        this.messagUserId = messagUserId;
    }



   



    public String getMessageType() {
        return messageType;
    }



    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }



    public String getOperUserId() {
        return operUserId;
    }



    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }



    public Date getOperDate() {
        return operDate;
    }



    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
