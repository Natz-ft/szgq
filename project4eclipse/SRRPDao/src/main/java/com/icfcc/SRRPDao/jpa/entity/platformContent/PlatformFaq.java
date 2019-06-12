package com.icfcc.SRRPDao.jpa.entity.platformContent;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name="FAQ_ID")
	private Long id;
	//常见问题
	@Column(name="FAQ_PROBLEM")
	private String problem;
	//回答内容
	@Column(name="FAQ_ANSWER")
	private String answer;
	//创建用户
	@Column(name="FAQ_CREATE_USER")
	private String createUser;
	//创建时间
	@Column(name="FAQ_CREATE_TIME")
	private Date createTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}