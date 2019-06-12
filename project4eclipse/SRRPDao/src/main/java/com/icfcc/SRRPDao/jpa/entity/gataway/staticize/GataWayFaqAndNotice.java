package com.icfcc.SRRPDao.jpa.entity.gataway.staticize;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_faqandnotice")
public class GataWayFaqAndNotice implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "title")
    private String title;

    @Lob
	@Column(name="content")
	@Basic(fetch=FetchType.LAZY)
	private byte[] content;
	
	public String getContent()  {
		String answerStr=null;
		try {
			answerStr=new String(content,"gbk");
			answerStr=answerStr.split("&&&&")[0];
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answerStr;
	}
	public void setContent(String answer)  {
		try {
			this.content = answer.getBytes("gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    private Date createTime;

    @Column(name = "TYPE")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}