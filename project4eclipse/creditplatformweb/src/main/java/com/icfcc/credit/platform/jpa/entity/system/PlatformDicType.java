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
* @ClassName: PlatformDic
* @Description: TODO<系统字典表>
* @author hugt
* @date 2017年9月14日 下午5:53:49
 */
@Data
@Entity
@Table(name = "platform_dic_type")
public class PlatformDicType implements Serializable {

	
	public void setType(String type) {
		this.type = type;
	}


	public String getType() {
		return type;
	}

	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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



	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -2339751794840917306L;

	@Id
	@Column(name = "DICTYPE")
	private String type;

	@Column(name = "DICTYPENAME")
	private String value;
	 @Column(name = "VALIDITYFLAG")
	private String validityFlag;
	 @Column(name = "VALIDITYDATE", nullable = false, length = 10)
	    private Date validityDate;

	
	public PlatformDicType() {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
