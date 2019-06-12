package com.icfcc.SRRPDao.jpa.entity.gataway.staticize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_contactus_dist")
public class GataWayContantUsDist implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIST_ID")
    private Long id;
    @Column(name = "DIST_NAME")
    private String distName;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DIST_HOTLINE")
    private String distHotLine;

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public String getDistHotLine() {
        return distHotLine;
    }

    public void setDistHotLine(String distHotLine) {
        this.distHotLine = distHotLine;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}