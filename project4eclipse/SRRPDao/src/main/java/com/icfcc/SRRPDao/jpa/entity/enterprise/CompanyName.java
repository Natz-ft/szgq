package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 * 企业基本信息实体类
 * 
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name = "rp_company_name")
public class CompanyName implements Serializable {


	
    /**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = -481168719040283741L;

    @Id
	@Column(name = "ID")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "ORG_CODE")
	private String orgCode;
	
	@Column(name = "RELATED_KEY")
    private String relatedkey;

	@Column(name = "RELATED_KEY_NAME")
	private String relatedKeyName;

	@Column(name = "RELATED_VALUE", length = 20)
	private String relatedValue;


	@Column(name = "BUS_COMPANY_ID")
	private String busCompanyId;// 注册地址

	@Column(name = "OP_CODE")
    private String opCode;// 证券代码
	
	@Column(name = "CREATE_TIME", length = 10)
	private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getRelatedkey() {
        return relatedkey;
    }

    public void setRelatedkey(String relatedkey) {
        this.relatedkey = relatedkey;
    }

    public String getRelatedKeyName() {
        return relatedKeyName;
    }

    public void setRelatedKeyName(String relatedKeyName) {
        this.relatedKeyName = relatedKeyName;
    }

    public String getRelatedValue() {
        return relatedValue;
    }

    public void setRelatedValue(String relatedValue) {
        this.relatedValue = relatedValue;
    }

    public String getBusCompanyId() {
        return busCompanyId;
    }

    public void setBusCompanyId(String busCompanyId) {
        this.busCompanyId = busCompanyId;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
	
}
