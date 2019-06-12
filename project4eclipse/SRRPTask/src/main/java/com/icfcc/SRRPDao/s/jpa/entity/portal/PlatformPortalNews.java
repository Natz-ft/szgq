package com.icfcc.SRRPDao.s.jpa.entity.portal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_news")
public class PlatformPortalNews implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "news_id")
    private long newsId;

    @Column(name = "news_title")
    private String newsTile;

    @Column(name = "news_content")
    private byte[] newsContents;

    @Column(name = "new_picture")
    private String newsPicture;

    @Column(name = "news_create_user")
    private String newsCreateUser;

    @Column(name = "news_create_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    private Date newCreateDate;

    @Column(name = "news_source")
    private String newSource;

    @Column(name = "NEWS_EDITOR")
    private String newEditor;

    @Column(name = "PURPOSE")
    private String purpose;

    @Column(name = "NEW_PICTURE_NAME")
    private String uplodfileName;
    
    // 新闻概要
    @Transient
    private String newSummary;
    
    //
    @Column(name="NEWS_DATE")
    private String newsDate;
    
    public String getNewsDate() {
		return newsDate;
	}
	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public String getNewsTile() {
        return newsTile;
    }

    public void setNewsTile(String newsTile) {
        this.newsTile = newsTile;
    }

    public byte[] getNewsContents() {
        return newsContents;
    }

    public void setNewsContents(byte[] newsContents) {
        this.newsContents = newsContents;
    }

    public String getNewsPicture() {
        return newsPicture;
    }

    public void setNewsPicture(String newsPicture) {
        this.newsPicture = newsPicture;
    }

    public String getNewsCreateUser() {
        return newsCreateUser;
    }

    public void setNewsCreateUser(String newsCreateUser) {
        this.newsCreateUser = newsCreateUser;
    }

    public Date getNewCreateDate() {
        return newCreateDate;
    }

    public void setNewCreateDate(Date newCreateDate) {
        this.newCreateDate = newCreateDate;
    }

    public String getNewSource() {
        return newSource;
    }

    public void setNewSource(String newSource) {
        this.newSource = newSource;
    }

    public String getNewEditor() {
        return newEditor;
    }

    public void setNewEditor(String newEditor) {
        this.newEditor = newEditor;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

   
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getUplodfileName() {
        return uplodfileName;
    }

    public void setUplodfileName(String uplodfileName) {
        this.uplodfileName = uplodfileName;
    }

    public String getNewSummary() {
        return newSummary;
    }

    public void setNewSummary(String newSummary) {
        this.newSummary = newSummary;
    }

}