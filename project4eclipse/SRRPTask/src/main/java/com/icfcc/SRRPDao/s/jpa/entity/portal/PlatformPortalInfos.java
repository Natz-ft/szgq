package com.icfcc.SRRPDao.s.jpa.entity.portal;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icfcc.credit.platform.util.SRRPConstant;

@Data
@Entity
@Table(name = "platform_portal_infos")
public class PlatformPortalInfos implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "infoid")
    private String infoId;

    @Column(name = "title")
    private String title;

    //内容
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    private byte[] content;

    @Column(name = "time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    private String time;

    @Column(name = "infotype")
    private String infoType;

    @Column(name="NEWS_PHOTO")
    private String photo;
    
    @Column(name="NEWS_PHOTO_NAME")
    private String uplodfileName;
    
    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
    	String contentStr=null;
        	try {
                if (content == null) {
                	contentStr= "";
                } else {
                	contentStr=new String(content, SRRPConstant.DEFAULTCHARTS);
                }
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return contentStr;
    }

    public void setContent(String content) throws UnsupportedEncodingException {
        this.content = content.getBytes(SRRPConstant.DEFAULTCHARTS);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUplodfileName() {
        return uplodfileName;
    }

    public void setUplodfileName(String uplodfileName) {
        this.uplodfileName = uplodfileName;
    }
    
    //
    @Column(name="NEWS_DATE")
    private String newsDate;
    
    public String getNewsDate() {
		return newsDate;
	}
	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}
    
}
