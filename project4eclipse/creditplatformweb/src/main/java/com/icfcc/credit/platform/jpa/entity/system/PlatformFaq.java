package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
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
import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * 
* @ClassName: PlatformFaq
* @Description: TODO<门户常见问题实体类>
* @author hugt
* @date 2017年9月14日 下午5:54:44
 */
@Data
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
	private String problem;
	//回答内容
//	@Column(name="ANSWER")
//	private String answer;
	@Lob
	@Column(name="ANSWER")
	@Basic(fetch=FetchType.LAZY)
	private byte[] answer;
	
	public String getAnswer()  {
		String answerStr="";
		try {
			answerStr=new String(answer,"gbk");
			answerStr=answerStr.split("&&&&")[0];
			return answerStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	public void setAnswer(String answer)  {
		try {
			this.answer = answer.getBytes("gbk");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transient
	private String filepath;
	
	@Transient
	private String filename;
	
	@Transient
	private String faqDate;
	
	@Transient
	private String indexTitle;
	
	@Transient
	private String indexSubTitle;
	
	@Transient
	private String indexFilepath;
	
	@Transient
	private String indexFilename;
	
	
	
	
	
	public String getIndexTitle() {
		String answerStr="";
		try {
			answerStr=new String(answer,"gbk");
			answerStr=answerStr.split("&&&&")[4].trim();
			return answerStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	public void setIndexTitle(String indexTitle) {
		this.indexTitle = indexTitle;
	}
	public String getIndexSubTitle() {
		String answerStr="";
		try {
			answerStr=new String(answer,"gbk");
			answerStr=answerStr.split("&&&&")[5].trim();
			return answerStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	public void setIndexSubTitle(String indexSubTitle) {
		this.indexSubTitle = indexSubTitle;
	}
	public String getIndexFilepath() {
		String answerStr="";
		try {
			answerStr=new String(answer,"gbk");
			answerStr=answerStr.split("&&&&")[6].trim();
			return answerStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	public void setIndexFilepath(String indexFilepath) {
		this.indexFilepath = indexFilepath;
	}
	public String getIndexFilename() {
		String answerStr="";
		try {
			answerStr=new String(answer,"gbk");
			answerStr=answerStr.split("&&&&")[7].trim();
			return answerStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	public void setIndexFilename(String indexFilename) {
		this.indexFilename = indexFilename;
	}
	public String getFaqDate() {
		String answerStr="";
		try {
			answerStr=new String(answer,"gbk");
			answerStr=answerStr.split("&&&&")[3].trim();
			return answerStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	public void setFaqDate(String faqDate) {
		this.faqDate = faqDate;
	}
	public String getFilepath() {
		String answerStr="";
		try {
			answerStr=new String(answer,"gbk");
			answerStr=answerStr.split("&&&&")[1].trim();
			return answerStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFilename() {
		String answerStr="";
		try {
			answerStr=new String(answer,"gbk");
			answerStr=answerStr.split("&&&&")[2].trim();
			return answerStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	//创建用户
	@Column(name="CREATE_USER")
	private String createUser;
	//创建时间
	@Column(name="CREATE_TIME")
	private Date createTime;
	@Column(name="TYPE")
	private String faqtype;
	@Transient
	private String faqtypeStr;
	
	public String getFaqtypeStr() {
		String str="";
		if(this.faqtype!=null && !faqtype.equals("")){
			if("0001".equals(this.faqtype)){
				str="常见问题";
			}else if("0002".equals(this.faqtype)){
				str="公告通知";
			}else if("0003".equals(this.faqtype)){
				str="动态通知";
			}
		}else{
			str ="--";
		}
		return str;
	}

	public void setFaqtypeStr(String faqtypeStr) {
		this.faqtypeStr = faqtypeStr;
	}

	public String getFaqtype() {
		return faqtype;
	}

	public void setFaqtype(String faqtype) {
		this.faqtype = faqtype;
	}

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
