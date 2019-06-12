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
* @ClassName: PlatformUserActionLog
* @Description: TODO(这里用一句话描述这个类的作用)
* @author hugt
* @date 2017年9月14日 下午6:45:26
*
 */
@Data
@Entity
@Table(name = "platform_user_action_log")
public class PlatformUserActionLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3875516013008542736L;

	
	@Id
    @Column(name = "ID")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
    private String id;
	
	@Column(name = "USER_ID")
    private String userId;
	
	@Column(name = "BUSS_ID")
    private String businessId;
	
	@Column(name = "COMPONENT")
    private String component;
	
	@Column(name = "UPDATE_PARAMS")
    private String updateParams;
	
	@Column(name = "USE_TIME_MS")
    private String useTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPERATE_TIME")
    private Date operateTime;
	
	@Column(name = "OPERATE_USERID")
    private String operateUser;

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
	
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getUpdateParams() {
		return updateParams;
	}

	public void setUpdateParams(String updateParams) {
		this.updateParams = updateParams;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	@Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
