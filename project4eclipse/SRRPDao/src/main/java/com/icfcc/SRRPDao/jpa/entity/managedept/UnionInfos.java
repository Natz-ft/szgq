package com.icfcc.SRRPDao.jpa.entity.managedept;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Entity
public class UnionInfos implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "INVESTOR_ID")
    private String investor_id;

    // 信息披露表EVENT_ID
    @Column(name = "EVENT_ID")
    private String event_id;

    @Column(name = "CERTTYPE")
    private String certtype;

    @Column(name = "CERTNO")
    private String certno;

    @Column(name = "NAME")
    private String name;

    @Column(name = "REGISTE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date registe_time;

    @Column(name = "TOP_INVESTOR")
    private String top_investor;

    @Column(name = "ORG_TYPE")
    private String org_type;

    @Column(name = "CAPITAL_TYPE")
    private String capital_type;

    @Column(name = "AREA_CODE")
    private String area_code;

    @Column(name = "LICENSE_PATH")
    private String license_path;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ZIPCODE")
    private String zipcode;

    @Column(name = "REL_NAME")
    private String rel_name;

    @Column(name = "REL_PHONE")
    private String rel_phone;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CAPITAL")
    private String capital;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "FINANCE_STAGE")
    private String finance_stage;

    @Column(name = "FINANCE_TRADE")
    private String finance_trade;

    public String getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(String investor_id) {
        this.investor_id = investor_id;
    }

    public String getCerttype() {
        return certtype;
    }

    public void setCerttype(String certtype) {
        this.certtype = certtype;
    }

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getRegiste_time() {
        return registe_time;
    }

    public void setRegiste_time(Date registe_time) {
        this.registe_time = registe_time;
    }

    public String getTop_investor() {
        return top_investor;
    }

    public void setTop_investor(String top_investor) {
        this.top_investor = top_investor;
    }

    public String getOrg_type() {
        return org_type;
    }

    public void setOrg_type(String org_type) {
        this.org_type = org_type;
    }

    public String getCapital_type() {
        return capital_type;
    }

    public void setCapital_type(String capital_type) {
        this.capital_type = capital_type;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getLicense_path() {
        return license_path;
    }

    public void setLicense_path(String license_path) {
        this.license_path = license_path;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getRel_name() {
        return rel_name;
    }

    public void setRel_name(String rel_name) {
        this.rel_name = rel_name;
    }

    public String getRel_phone() {
        return rel_phone;
    }

    public void setRel_phone(String rel_phone) {
        this.rel_phone = rel_phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFinance_stage() {
        return finance_stage;
    }

    public void setFinance_stage(String finance_stage) {
        this.finance_stage = finance_stage;
    }

    public String getFinance_trade() {
        return finance_trade;
    }

    public void setFinance_trade(String finance_trade) {
        this.finance_trade = finance_trade;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

}
