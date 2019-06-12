package com.icfcc.SRRPDao.jpa.entity.managedept;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

import lombok.Data;


/**
 * 
* @ClassName: ManagedeptOnlineForum
* @Description: TODO(在线回复实体类)
* @author hugt
* @date 2017年9月19日 上午9:23:56
*
 */
@Entity
@Table(name="platform_portal_online_forum")
public class ManagedeptOnlineForum implements Serializable {
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
	
	//回复内容
    @Column(name="certno")
    private String certno;
    
    //回复人
    @Column(name="name")
    private String name;
    
	//回复时间
	@Column(name="answerdate")
	private Date answerdate;
	@Transient
	private String messagestatusStr;
	@Transient
	private String messagedateStr;
	@Transient
	private String answerdescribeText;
	
	public String getMessagedateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.format(this.messagedate);
		return sdf.format(this.messagedate);
	}
     
	public String getAnswerdescribeText() {
		return answerdescribeText;
	}

	public void setAnswerdescribeText(String answerdescribeText) {
		this.answerdescribeText = answerdescribeText;
	}

	public void setMessagedateStr(String messagedateStr) {
		this.messagedateStr = messagedateStr;
	}
	public String getMessagestatusStr() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_MSG, this.messagestatus);
	}

	public void setMessagestatusStr(String messagestatusStr) {
		this.messagestatusStr = messagestatusStr;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
