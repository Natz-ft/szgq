package com.icfcc.SRRPDao.jpa.entity.platformSystem;

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
 * <系统字典表>
 */
@Entity
@Table(name = "platform_dictionary")
public class SystemDic implements Serializable {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -2339751794840917306L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "CODE")
	private String name;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "DESCRIPTION")
	private String desc;

	@Column(name = "SORT_NUM")
	private Integer sortNum;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 状态更改时间
	 */
	private Date updateTime;

	public SystemDic() {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
