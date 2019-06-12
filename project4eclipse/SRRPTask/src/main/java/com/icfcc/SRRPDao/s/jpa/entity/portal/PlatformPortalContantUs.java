package com.icfcc.SRRPDao.s.jpa.entity.portal;

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
@Table(name = "platform_portal_contactus")
public class PlatformPortalContantUs implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;
    @Id
	@Column(name = "CU_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long cuid;
    
    @Column(name = "CU_NAME")
    private String cuName;

    @Column(name = "CU_HOTLINE")
    private String cuHotLine;

    @Column(name = "CU_ZIPCODE")
    private String cuZipCode;

    @Column(name = "CU_FAX")
    private String cuFax;

    @Column(name = "CU_SERVICES_MAIL")
    private String mail;

    @Column(name = "CU_ADRESS")
    private String address;

    public Long getCuid() {
		return cuid;
	}

	public void setCuid(Long cuid) {
		this.cuid = cuid;
	}

	public String getCuName() {
        return cuName;
    }

    public void setCuName(String cuName) {
        this.cuName = cuName;
    }

    public String getCuHotLine() {
        return cuHotLine;
    }

    public void setCuHotLine(String cuHotLine) {
        this.cuHotLine = cuHotLine;
    }

    public String getCuZipCode() {
        return cuZipCode;
    }

    public void setCuZipCode(String cuZipCode) {
        this.cuZipCode = cuZipCode;
    }

    public String getCuFax() {
        return cuFax;
    }

    public void setCuFax(String cuFax) {
        this.cuFax = cuFax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}