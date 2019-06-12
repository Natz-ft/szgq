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
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_news")
public class GataWayNews implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "NEWS_ID")
    private String newsId;

    @Column(name = "NEWS_TITLE")
    private String newsTile;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "NEWS_CONTENT")
    private byte[] newsContents;

    @Column(name = "NEW_PICTURE")
    private String newsPicture;

    @Column(name = "NEWS_CREATE_USER")
    private String newsCreateUser;

    @Column(name = "NEWS_CREATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    private Date newCreateDate;

    @Column(name = "NEWS_SOURCE")
    private String newSource;

    @Column(name = "NEWS_EDITOR")
    private String newEditor;

    @Column(name = "PURPOSE")
    private String purpose;
    @Column(name = "NEW_PICTURE_NAME")
    private String uplodfileName;
    // 新闻概要门户
    @Transient
    private String newSummaryPortal;
    // 新闻标题门户
    @Transient
    private String newTilePortal;
    
    //
    @Column(name="NEWS_DATE")
    private String newsDate;
    
    public String getNewsDate() {
		return newsDate;
	}
	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

    // 新闻概要
    @Transient
    private String newsSummaryNews;
    // 新闻标题
    @Transient
    private String newsTileNews;
    
    // 新闻概要
    @Transient
    private String newsSummaryNewsFirst;
    // 新闻标题

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTile() {
        return newsTile;
    }

    public void setNewsTile(String newsTile) {
        this.newsTile = newsTile;
    }

    public String getNewsContents() {
        String contentStr = null;
        try {
            if (newsContents == null) {
                return "";
            } else {
                contentStr = new String(newsContents, SRRPConstant.DEFAULTCHARTS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentStr;
    }

    public void setNewsContents(String newsContents) {
        try {
            this.newsContents = newsContents.getBytes(SRRPConstant.DEFAULTCHARTS);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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

    public String getNewSummaryPortal() {
        return newSummaryPortal;
    }

    public void setNewSummaryPortal(String newSummaryPortal) {
        this.newSummaryPortal = newSummaryPortal;
    }

    public String getNewTilePortal() {
        return newTilePortal;
    }

    public void setNewTilePortal(String newTilePortal) {
        this.newTilePortal = newTilePortal;
    }

    public String getNewsSummaryNews() {
        return newsSummaryNews;
    }

    public void setNewsSummaryNews(String newsSummaryNews) {
        this.newsSummaryNews = newsSummaryNews;
    }

    public String getNewsTileNews() {
        return newsTileNews;
    }

    public void setNewsTileNews(String newsTileNews) {
        this.newsTileNews = newsTileNews;
    }

    public void setNewsContents(byte[] newsContents) {
        this.newsContents = newsContents;
    }

    public String getNewsSummaryNewsFirst() {
        return newsSummaryNewsFirst;
    }

    public void setNewsSummaryNewsFirst(String newsSummaryNewsFirst) {
        this.newsSummaryNewsFirst = newsSummaryNewsFirst;
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

}