package com.icfcc.SRRPDao.jpa.entity.gataway.staticize;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_investorinfo")
public class GataWayInvestorInfo implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "investor_id")
    private String investor_id;

    @Column(name = "investor_title")
    private String investor_title;

    @Column(name = "investor_content")
    private String investor_content;

    @Column(name = "investor_create_user")
    private String investor_create_user;

    @Column(name = "investor_create_time")
    @JsonFormat(pattern = "MM/dd", timezone = "GMT+08:00")
    private Date investor_create_time;

    @Column(name = "investor_source")
    private String investor_source;

    @Column(name = "investor_editor")
    private String investor_editor;

    public String getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(String investor_id) {
        this.investor_id = investor_id;
    }

    public String getInvestor_title() {
        return investor_title;
    }

    public void setInvestor_title(String investor_title) {
        this.investor_title = investor_title;
    }

    public String getInvestor_content() {
        return investor_content;
    }

    public void setInvestor_content(String investor_content) {
        this.investor_content = investor_content;
    }

    public String getInvestor_create_user() {
        return investor_create_user;
    }

    public void setInvestor_create_user(String investor_create_user) {
        this.investor_create_user = investor_create_user;
    }

    public Date getInvestor_create_time() {
        return investor_create_time;
    }

    public void setInvestor_create_time(Date investor_create_time) {
        this.investor_create_time = investor_create_time;
    }

    public String getInvestor_source() {
        return investor_source;
    }

    public void setInvestor_source(String investor_source) {
        this.investor_source = investor_source;
    }

    public String getInvestor_editor() {
        return investor_editor;
    }

    public void setInvestor_editor(String investor_editor) {
        this.investor_editor = investor_editor;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}