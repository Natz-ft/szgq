package com.icfcc.SRRPDao.jpa.entity.platformContent;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.icfcc.credit.platform.util.SRRPConstant;

import lombok.Data;

/**
 * 
* @ClassName: PlatformNews
* @Description: TODO(<新闻实体类>)
* @author hugt
* @date 2017年9月14日 下午5:59:10
*
 */
@Entity
@Table(name="platform_news")
public class PlatformNews implements Serializable {

	/**
	 * 新闻动态实体类
	 */
	private static final long serialVersionUID = 4050450428046933368L;
	//主键
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NEWS_ID")
	private Long id;
	//新闻标题
	@Column(name="NEWS_TITLE")
	private String title;
	//新闻内容
	@Lob
	@Column(name="NEWS_CONTENT")
	@Basic(fetch=FetchType.LAZY)
	private byte[] content;
	//新闻类型
	@Column(name="NEWS_MSGTYPE")
	private String newstype;
	//新闻来源
	@Column(name="NEWS_SOURCE")
	private String newsSource;
	//新闻来源
	@Column(name="NEWS_EDITOR")
	private String newsEditor;	
	//新闻发布人
	@Column(name="NEWS_CREATE_USER")
	private String createUser;
	//新闻发布时间
	@Column(name="NEWS_CREATE_TIME")
	private Date createTime;
	//新闻发布时间
	@Column(name="NEWS_PHOTO")
	private String photo;
	
	@Column(name="NEWS_PHOTO_NAME")
    private String uplodfileName;
	
	////
    @Column(name="NEWS_DATE")
    private String newsDate;
    
    @Column(name="IS_LUNBO")
    private Integer isLunbo;
    
    @Column(name="LUNBO_ORD")
    private Integer lunboOrd;
    
    public String getNewsDate() {
		return newsDate;
	}
	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}
	public Integer getIsLunbo() {
		return isLunbo;
	}
	public void setIsLunbo(Integer isLunbo) {
		this.isLunbo = isLunbo;
	}
	public Integer getLunboOrd() {
		return lunboOrd;
	}
	public void setLunboOrd(Integer lunboOrd) {
		this.lunboOrd = lunboOrd;
	}
	 
	// 新闻概要
    @Transient
    private String newsSummaryNews;
    
    public String getContent4GBK() {
    	String contentStr = null;
        try {
            if (this.content == null) {
                return "";
            } else {
                contentStr = new String(this.content, SRRPConstant.DEFAULTCHARTS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentStr;
    }
    
	public String getNewsSummaryNews() {
		return this.newsSummaryNews;
	}
	public void setNewsSummaryNews(String newsSummaryNews) {
         this.newsSummaryNews = newsSummaryNews;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() throws UnsupportedEncodingException {
		return new String(content,"utf-8");
	}
	public void setContent(String content) throws UnsupportedEncodingException {
		this.content = content.getBytes("utf-8");
	}

	public String getNewstype() {
		return newstype;
	}
	public void setNewstype(String newstype) {
		this.newstype = newstype;
	}
	
	public String getNewsSource() {
		return newsSource;
	}
	public void setNewsSource(String newsSource) {
		this.newsSource = newsSource;
	}
	public String getNewsEditor() {
		return newsEditor;
	}
	public void setNewsEditor(String newsEditor) {
		this.newsEditor = newsEditor;
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
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
    public String getUplodfileName() {
        return uplodfileName;
    }
    public void setUplodfileName(String uplodfileName) {
        this.uplodfileName = uplodfileName;
    }
	
}
