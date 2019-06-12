package com.icfcc.SRRPDao.s.jpa.entity.portal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 */
@Entity
public class PortalStaticByArea implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;
    @Id
    @Column(name = "areacode")
    private String areaCode;

    @Column(name = "amount", columnDefinition = "decimal(15,2)")
    private String amount;

    public String getAreaCode() {
        return areaCode;
    }



    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }



    public String getAmount() {
        return amount;
    }



    public void setAmount(String amount) {
        this.amount = amount;
    }



    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}