package com.icfcc.SRRPDao.jpa.entity.managedept;

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
import javax.persistence.Transient;

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
@Table(name = "rp_his_dissent")
public class ManageHisDissentInfor implements Serializable {

    /**
     * @Fields serialVersionUID : TODO(序列Id)
     */
    private static final long serialVersionUID = 4435699081416344844L;

    /**
     * @Fields dissentId : TODO(主鍵Id)
     */
    @Id
    @Column(name = "dissent_his_id", length = 32)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String dissentHisId;

    /**
     * @Fields dissentId : TODO(主鍵Id)
     */
    @Column(name = "dissent_id", length = 32)
    private String dissentId;

    /**
     * @Fields companyId : TODO(企业/机构id的id)
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * @Fields dissentContent : TODO(异议的内容)
     */
    @Column(name = "dissent_content")
    private String dissentContent;

    /**
     * @Fields cancelContent : TODO(解除原因)
     */
    @Column(name = "dissent_cancel_content")
    private String cancelContent;

    /**
     * @Fields dissentType : TODO(00:企业异议；01：机构异议
     */
    @Column(name = "dissent_type")
    private String dissentType;

    /**
     * @Fields dissentStatus : TODO(00:初始化，01，提交，02：修改,03：已解除)
     */
    @Column(name = "dissent_status")
    private String dissentStatus;

    /**
     * @Fields filePath : TODO(公章异议证明材料路径)
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * @Fields fileName : TODO(工商异议证明材料名称)
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * @Fields fileName : TODO(工商异议证明材料名称)
     */
    @Column(name = "cancel_file_name")
    private String cancelFileName;
    /**
     * @Fields filePath : TODO(公章异议证明材料路径)
     */
    @Column(name = "cancel_file_path")
    private String cancelFilePath;
    
    /**
     * @Fields operUser : TODO(操作用户名称)
     */
    @Column(name = "oper_user_name")
    private String operUserName;

    /**
     * @Fields operUserId : TODO(操作用户ID)
     */
    @Column(name = "oper_user_id")
    private String operUserId;

    /**
     * @Fields operUpdateDate : TODO(操作修改时间)
     */
    @Column(name = "oper_update_date")
    private Date operUpdateDate;

    /**
     * @Fields operDate : TODO(操作创建时间)
     */
    @Column(name = "oper_date")
    private Date operDate;

    /**
     * @Fields userType : TODO(当前登陆用户类别)
     */
    @Transient
    private String userType;
    
    /**
     * @Fields userType : TODO(当前登陆用户类别)
     */
    @Transient
    private String dissentStatusStr;
    

   

    public String getDissentStatusStr() {
        return dissentStatusStr;
    }

    public void setDissentStatusStr(String dissentStatusStr) {
        this.dissentStatusStr = dissentStatusStr;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDissentId() {
        return dissentId;
    }

    public void setDissentId(String dissentId) {
        this.dissentId = dissentId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDissentContent() {
        return dissentContent;
    }

    public void setDissentContent(String dissentContent) {
        this.dissentContent = dissentContent;
    }

    public String getCancelContent() {
        return cancelContent;
    }

    public void setCancelContent(String cancelContent) {
        this.cancelContent = cancelContent;
    }

    public String getDissentType() {
        return dissentType;
    }

    public void setDissentType(String dissentType) {
        this.dissentType = dissentType;
    }

    public String getDissentStatus() {
        return dissentStatus;
    }

    public void setDissentStatus(String dissentStatus) {
        this.dissentStatus = dissentStatus;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public String getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }

    public Date getOperUpdateDate() {
        return operUpdateDate;
    }

    public void setOperUpdateDate(Date operUpdateDate) {
        this.operUpdateDate = operUpdateDate;
    }

    public Date getOperDate() {
        return operDate;
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }

    @Transient
    private String operUpdateDateStr;// 审核时间字符串

    public String getDissentHisId() {
        return dissentHisId;
    }

    public void setDissentHisId(String dissentHisId) {
        this.dissentHisId = dissentHisId;
    }

    public String getOperUpdateDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(this.operUpdateDate);
    }

    public void setOperUpdateDateStr(String operUpdateDateStr) {
        this.operUpdateDateStr = operUpdateDateStr;
    }

    public String getCancelFileName() {
        return cancelFileName;
    }

    public void setCancelFileName(String cancelFileName) {
        this.cancelFileName = cancelFileName;
    }

    public String getCancelFilePath() {
        return cancelFilePath;
    }

    public void setCancelFilePath(String cancelFilePath) {
        this.cancelFilePath = cancelFilePath;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
