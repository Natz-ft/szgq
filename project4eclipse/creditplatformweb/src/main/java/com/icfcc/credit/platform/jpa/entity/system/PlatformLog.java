package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Data;

/**
 * 
* @ClassName: PlatformLog
* @Description: TODO<日志记录实体类>
* @author hugt
* @date 2017年9月14日 下午5:57:35
*
 */
@Data
@Entity
@Table(name = "platform_log")
public class PlatformLog implements Serializable
{
    private static final long serialVersionUID = 7666414469336601402L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROLOG_ID")
    private Long proLogId;
    
    private String oprType;
    
    private Date oprTime;
    
    private String oprTableName;
    
    private String oprDataId;
    
    private String oprDataOld;
    
    private String oprDataNew;
    
    private Long oprUserId;
    
    private String oprUserName;
    
    private String oprUserIp;
    
    private String oprComment;
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
