package com.icfcc.credit.platform.jpa.entity.system;

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


/**
 * 
* @ClassName: PlatformContactus
* @Description: TODO(<联系我们实体类>)
* @author  hugt
* @date 2017年9月14日 下午5:51:30
*
 */
@Data
@Entity
@Table(name = "platform_contactus")
public class PlatformContactus implements Serializable {
	/**
	 *联系我们实体类 
	 */
	private static final long serialVersionUID = -7956943660061196971L;

	@Id
	@Column(name = "CU_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	//联系企业名称
	@Column(name = "CU_NAME")
	private String name;
	//联系热线
	@Column(name = "CU_HOTLINE")
	private String hotline;
    //邮编
	@Column(name = "CU_ZIPCODE")
	private String zipCode;
    //传真
	@Column(name = "CU_FAX")
	private String fax;
    //邮箱
	@Column(name = "CU_SERVICES_MAIL")
	private String mail;
	//联系地址
	@Column(name = "CU_ADRESS")
	private String  adress;
	
	//工作时间
	@Column(name = "CU_WORKING_DATE")
	private Integer workData;
    //新浪二维码
	@Column(name = "CU_SINA_QRCODE")
	private String sinaCode;
	//微信二维码
	@Column(name = "CU_WEChAT_QRCODE")
	private String wechCode;
	//创建用户
	@Column(name = "CU_CREATE_USER")
	private String createUser;
	//创建时间
	@Column(name = "CU_CREATE_TIME")
	private Date createTime;
	
	
	
	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getHotline() {
		return hotline;
	}


	public void setHotline(String hotline) {
		this.hotline = hotline;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getAdress() {
		return adress;
	}


	public void setAdress(String adress) {
		this.adress = adress;
	}


	public Integer getWorkData() {
		return workData;
	}


	public void setWorkData(Integer workData) {
		this.workData = workData;
	}


	public String getSinaCode() {
		return sinaCode;
	}


	public void setSinaCode(String sinaCode) {
		this.sinaCode = sinaCode;
	}


	public String getWechCode() {
		return wechCode;
	}


	public void setWechCode(String wechCode) {
		this.wechCode = wechCode;
	}


	public String getCreateUser() {
		return createUser;
	}


	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	

	

	
}