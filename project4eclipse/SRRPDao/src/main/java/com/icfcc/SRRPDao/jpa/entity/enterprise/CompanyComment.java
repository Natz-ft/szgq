package com.icfcc.SRRPDao.jpa.entity.enterprise;

/**
 * 评价信息表实体类
 */
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "rp_company_comment")
public class CompanyComment implements Serializable {
	private static final long serialVersionUID = 3873015531678024001L;

	@Id
	@Column(name = "event_id", length = 32)
	private String eventId;// 融资事件id

	@Column(name = "stars", length=2)
	private Double stars;// 星级

	@Column(name = "evacontent",length=1000)
	private String evacontent;// 评价内容

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "operdate")
	private Date operDate;// 操作时间

	@Column(name = "operuser",length=32)
	private String operUser;// 操作用户

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Double getStars() {
		return stars;
	}

	public void setStars(Double stars) {
		this.stars = stars;
	}
	//添加操作时间的非空验证，防止格式化报错
	public String getOperDate() {
		if(this.operDate==null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(this.operDate);
		}
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public String getEvacontent() {
		return evacontent;
	}

	public void setEvacontent(String evacontent) {
		this.evacontent = evacontent;
	}

}
