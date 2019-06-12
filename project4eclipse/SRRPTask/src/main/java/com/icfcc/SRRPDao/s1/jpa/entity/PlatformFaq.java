package com.icfcc.SRRPDao.s1.jpa.entity;

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

import org.apache.commons.lang.builder.ToStringBuilder;

import lombok.Data;

/**
 * 
* @ClassName: PlatformFaq
* @Description: TODO<门户常见问题实体类>
* @author hugt
* @date 2017年9月14日 下午5:54:44
 */
@Entity
@Table(name="platform_faq")
public class PlatformFaq implements Serializable {

	/**
	 * 常见问题实体类
	 */
	private static final long serialVersionUID = -6308038009161556875L;
	
	//主键
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	//常见问题
	@Column(name="PROBLEM")
	private String title;
	
	//创建用户
	@Column(name="CREATE_USER")
	private String createUser;
	//创建时间
	@Column(name="CREATE_TIME")
	private Date createTime;
	@Column(name = "TYPE")
    private String type;
	
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

	@Lob
	@Column(name="ANSWER")
	@Basic(fetch=FetchType.LAZY)
	private byte[] answer;
	
	public String getAnswer()  {
		String answerStr=null;
		try {
			answerStr=new String(answer,"gbk");
			answerStr=answerStr.split("&&&&")[0];
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answerStr;
	}
	public void setAnswer(String answer)  {
		try {
			this.answer = answer.getBytes("gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
