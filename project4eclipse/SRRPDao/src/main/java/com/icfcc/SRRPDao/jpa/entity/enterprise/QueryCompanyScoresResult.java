package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

import lombok.Data;

/**
 * 企业基本信息与评分表关联查询实体类
 * 
 * @author Administrator
 *         cp.enterprise_id,cp.name,cp.codetype,cp.code,cp.stop_flag
 *         ,cp.audit_status,ccs.score
 */
@Data
@Entity
public class QueryCompanyScoresResult implements Serializable {

	private static final long serialVersionUID = 9048050456768543611L;

	@Id
	@Column(name = "enterprise_id")
	private String enterpriseId;// 企业id

	@Column(name = "name")
	private String name;// 企业名称

	@Column(name = "codetype")
	private String codetype;// 企业证件类型

	@Temporal(TemporalType.DATE)
	@Column(name = "REDATE")//成立时间
	private Date redate;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "OPERDATE")//成立时间
    private Date OPERDATE;

	@Column(name = "REAREA", length = 50)
	private String rearea;

	@Column(name = "CODE")
	private String code;;// 企业证件号码

	@Column(name = "STOP_FLAG")
	private String stopFlag;// 企业启用停用

	@Column(name = "AUDIT_STATUS")
	private String auditStatus;;// 企业审核状态

	@Column(name = "LOCK_FLAG")
	private String lockFlag;// 企业用户锁定标志
	
	@Column(name = "CREATE_TIME")
	private String createTime;// 企业用户锁定标志

	@Column(name = "companytime")
    private Date companyTime;// 企业用户锁定标志
	
	
	@Column(name = "USER_ID")
	private String userId;// 企业用户锁定标志
	
	@Column(name = "AUDIT_STAGE")
	private String auditStage;// 企业的审核阶段
	@Transient
	private String auditStatusDicName;
	
	@Transient
	private String stopFlagDicName;
	@Transient
	private String userType;
	@Transient
	private String userStatus;
	
	@Transient
	private String redateStr;
	
	@Transient
    private String OPERDATEStr;
	
	
	@Transient
    private String idHaveDissent="0";
	
	@Transient
    private String isReadDissent="0";
	
    @Transient
    private String createTimeStr;
    
    
     public String getCreateTimeStr() {
         if (this.companyTime != null) {
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             return sdf.format(this.companyTime);
         } else {
             return "";
         }
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
    
	
	

    public Date getOPERDATE() {
        return OPERDATE;
    }

    public void setOPERDATE(Date oPERDATE) {
        OPERDATE = oPERDATE;
    }

    public String getOPERDATEStr() {
        if (this.OPERDATE != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(this.OPERDATE);
        } else {
            return "";
        }
    }

    public void setOPERDATEStr(String oPERDATEStr) {
        OPERDATEStr = oPERDATEStr;
    }

    public String getIsReadDissent() {
        return isReadDissent;
    }

    public void setIsReadDissent(String isReadDissent) {
        this.isReadDissent = isReadDissent;
    }

    public String getIdHaveDissent() {
        return idHaveDissent;
    }

    public void setIdHaveDissent(String idHaveDissent) {
        this.idHaveDissent = idHaveDissent;
    }

    public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAuditStatusDicName() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_COMPANY_AUSTSTAUS,
				this.auditStatus);
	}

	public void setAuditStatusDicName(String auditStatusDicName) {
		this.auditStatusDicName = auditStatusDicName;
	}

	public String getStopFlagDicName() {
		if ("1".equals(this.stopFlag)) {
			stopFlagDicName = "启用";
		} else {
			stopFlagDicName = "停用";
		}
		return stopFlagDicName;
	}

	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}

	public void setStopFlagDicName(String stopFlagDicName) {
		this.stopFlagDicName = stopFlagDicName;
	}

	@Column(name = "score")
	private String score;// 企业信用评分

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getName() {
		return name;
	}

	public String getRearea() {
		return rearea;
	}

	public void setRearea(String rearea) {
		this.rearea = rearea;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodetype() {
		return codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	};

	public String getRedate() {
		if (this.redate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(this.redate);
		} else {
			return "";
		}
	}

	public void setRedate(Date redate) {
		this.redate = redate;
	}
	
	public String getRedateStr() {
		return redateStr;
	}

	public void setRedateStr(String redateStr) {
		this.redateStr = redateStr;
	}

	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
