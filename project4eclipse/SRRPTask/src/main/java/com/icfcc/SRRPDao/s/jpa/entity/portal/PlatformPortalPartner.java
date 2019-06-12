package com.icfcc.SRRPDao.s.jpa.entity.portal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_partner")
public class PlatformPortalPartner implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "PARTNET_ID")
    private String partnetId;

    @Column(name = "PARTNET_NEME")
    private String partnetName;

    @Column(name = "PARTNET_LOGO")
    private String partnetLogo;

    @Column(name = "PARTNET_LINK")
    private String partnetLink;

    public String getPartnetId() {
        return partnetId;
    }

    public void setPartnetId(String partnetId) {
        this.partnetId = partnetId;
    }

    public String getPartnetName() {
        return partnetName;
    }

    public void setPartnetName(String partnetName) {
        this.partnetName = partnetName;
    }

    public String getPartnetLogo() {
        return partnetLogo;
    }

    public void setPartnetLogo(String partnetLogo) {
        this.partnetLogo = partnetLogo;
    }

    public String getPartnetLink() {
        return partnetLink;
    }

    public void setPartnetLink(String partnetLink) {
        this.partnetLink = partnetLink;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}