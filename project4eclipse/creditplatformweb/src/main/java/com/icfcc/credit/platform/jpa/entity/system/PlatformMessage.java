package com.icfcc.credit.platform.jpa.entity.system;

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
* @ClassName: PlatformMessage
* @Description: TODO<消息实体类>
* @author hugt
* @date 2017年9月14日 下午5:58:41
*
 */
@Data
@Entity
@Table(name="platform_message")
public class PlatformMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -413304071697879924L;
	//主键
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MESSAGE_ID")
	private Long id;
	//消息标题
	@Column(name="MESSAGE_TITLE")
	private String title;
	//消息内容
	@Column(name="MESSAGE_CONTENT")
	private String content;
	//消息类型
	@Column(name="MESSAGE_MSGTYPE")
	private String msgtype;
	//消息发布人
	@Column(name="MESSAGE_CREATE_USER")
	private String createUser;
	//消息发布时间
	@Column(name="MESSAGE_CREATE_TIME")
	private Date createTime;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
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
