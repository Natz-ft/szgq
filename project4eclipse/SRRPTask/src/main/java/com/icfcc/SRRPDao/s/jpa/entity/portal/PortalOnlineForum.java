package com.icfcc.SRRPDao.s.jpa.entity.portal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;


/**
 * 
* @ClassName: PortalOnlineForum
* @Description: TODO(在线回复实体类)
* @author zhanglf
* @date 2017年9月19日 上午9:23:56
*
 */
@Entity
@Table(name="platform_portal_online_forum")
public class PortalOnlineForum implements Serializable {
	/**
	* @Fields serialVersionUID : TODO(用于版本控制)
	*/
	private static final long serialVersionUID = 4902828724840650747L;
    //主键id
	@Id
	@Column(name="messageid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	
	//留言日期
	@Column(name="messagedate")
	private Date messagedate;
	
	//留言状态
	@Column(name="messagestatus")
	private String messagestatus;
	
	//留言主题
	@Column(name="messagetheme")
	private String messagetheme;
	
	//联系人
	@Column(name="contacts")
	private String contacts;
	
	//联系电话
	@Column(name="contactnumber")
	private String contactnumber;
	
	//邮箱地址
	@Column(name="email")
	private String email;
	
	//留言内容
	@Column(name="messagedescribe")
	private String messagedescribe;
	
	//回复内容
	@Column(name="answerdescribe")
	private String answerdescribe;
	
	//回复人
	@Column(name="answerpeople")
	private String answerpeople;
	
	//回复时间
	@Column(name="answerdate")
	private Date answerdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getMessagedate() {
		return messagedate;
	}

	public void setMessagedate(Date messagedate) {
		this.messagedate = messagedate;
	}

	public String getMessagestatus() {
		return messagestatus;
	}

	public void setMessagestatus(String messagestatus) {
		this.messagestatus = messagestatus;
	}

	public String getMessagetheme() {
		return messagetheme;
	}

	public void setMessagetheme(String messagetheme) {
		this.messagetheme = messagetheme;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessagedescribe() {
		return messagedescribe;
	}

	public void setMessagedescribe(String messagedescribe) {
		this.messagedescribe = messagedescribe;
	}

	public String getAnswerdescribe() {
		return answerdescribe;
	}

	public void setAnswerdescribe(String answerdescribe) {
		this.answerdescribe = answerdescribe;
	}

	public String getAnswerpeople() {
		return answerpeople;
	}

	public void setAnswerpeople(String answerpeople) {
		this.answerpeople = answerpeople;
	}

	public Date getAnswerdate() {
		return answerdate;
	}

	public void setAnswerdate(Date answerdate) {
		this.answerdate = answerdate;
	}
	

}
