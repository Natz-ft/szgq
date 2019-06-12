package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
/**
 * 
* @ClassName: PlatformUserHistoryPassword
* @Description: TODO(这里用一句话描述这个类的作用)
* @author hugt
* @date 2017年9月14日 下午6:45:36
*
 */
@Data
@Entity
@Table(name = "platform_user_his_pwd")
public class PlatformUserHistoryPassword implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3603182012752923736L;

	@Id
    @Column(name = "ID")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
    private String id;
	
    @Column(name = "USER_ID")
    private String userId;
	
	@Column(name = "USER_PWD")
    private String password;
	
	@Column(name = "USER_SALT")
    private String salt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
    private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
