package com.icfcc.SRRPDao.s1.jpa.entity;

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
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
* @ClassName: PlatformContactusDist
* @Description: TODO< 门户联系我们各个分区金融办实体类>
* @author hugt
* @date 2017年9月14日 下午5:52:17
*
 */
@Entity
@Table(name = "platform_contactus_dist")
public class PlatformContactusDist implements Serializable
{
    /**
	 * 注释内容
	 */
	private static final long serialVersionUID = 4681707242994507507L;

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIST_ID")
    private Long id;
    
    // 名称
    @Column(name = "DIST_NAME")
    private String name;
    
    // 联系热线
    @Column(name = "DIST_HOTLINE")
    private String hotline;
    //创建用户
	@Column(name = "DIST_CREATE_USER")
	private String createUser;
	//创建时间
	@Column(name = "DIST__CREATE_TIME")
	private Date createTime;
   
    

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getHotline() {
		return hotline;
	}



	public void setHotline(String hotline) {
		this.hotline = hotline;
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



	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
		
	}
}
