package com.icfcc.SRRPDao.jpa.entity.dd;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
@Entity
@Table(name = "platform_dic_detail")
public class PlatformDicDetail implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "DICTYPE")
    private String dicType;

    @Column(name = "DICCODE")
    private String dicCode;

    @Column(name = "DICNAME")
    private String dicName;

    @Column(name = "VALIDITYFLAG")
    private String validityFlag;

    @Column(name = "VALIDITYDATE")
    private Date validityDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getDicType() {
        return dicType;
    }



    public void setDicType(String dicType) {
        this.dicType = dicType;
    }



    public String getDicCode() {
        return dicCode;
    }



    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }



    public String getDicName() {
        return dicName;
    }



    public void setDicName(String dicName) {
        this.dicName = dicName;
    }



    public String getValidityFlag() {
        return validityFlag;
    }



    public void setValidityFlag(String validityFlag) {
        this.validityFlag = validityFlag;
    }



    public Date getValidityDate() {
        return validityDate;
    }



    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
