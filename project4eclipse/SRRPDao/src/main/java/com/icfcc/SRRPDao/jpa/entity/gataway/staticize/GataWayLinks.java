package com.icfcc.SRRPDao.jpa.entity.gataway.staticize;

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
@Table(name = "platform_portal_links")
public class GataWayLinks implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "LINKS_ID")
    private String linksId;

    @Column(name = "LINKS_NEME")
    private String linksName;

    @Column(name = "LINKS_URL")
    private String linksUrl;
    
    @Column(name = "LINKS_PICTURE")
    private String linksPicture;

    public String getLinksId() {
        return linksId;
    }

    public void setLinksId(String linksId) {
        this.linksId = linksId;
    }

    public String getLinksName() {
        return linksName;
    }

    public void setLinksName(String linksName) {
        this.linksName = linksName;
    }

    public String getLinksUrl() {
        return linksUrl;
    }

    public void setLinksUrl(String linksUrl) {
        this.linksUrl = linksUrl;
    }

    public String getLinksPicture() {
        return linksPicture;
    }

    public void setLinksPicture(String linksPicture) {
        this.linksPicture = linksPicture;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}