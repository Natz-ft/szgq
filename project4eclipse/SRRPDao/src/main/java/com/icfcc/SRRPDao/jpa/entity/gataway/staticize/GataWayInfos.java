package com.icfcc.SRRPDao.jpa.entity.gataway.staticize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icfcc.credit.platform.util.SRRPConstant;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

@Data
@Entity
@Table(name = "platform_portal_infos")
public class GataWayInfos implements Serializable {
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

    public String getContent()  {
    	String contentStr=null;
    	try {
    		 if (content == null) {
    	            return "";
    	        } else {
    	        	contentStr= new String(content, SRRPConstant.DEFAULTCHARTS);
    	        }
		} catch (Exception e) {
		    e.printStackTrace();
		}
    	return contentStr;
       
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

    public void setContent(String content)  {
        try {
			this.content = content.getBytes(SRRPConstant.DEFAULTCHARTS);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
}
