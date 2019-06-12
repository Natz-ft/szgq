/**
 * 
 */
package com.icfcc.SRRPDao.s.jpa.entity.portal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lijj
 *
 */
@Entity
@Table(name = "platform_portal_investorinfo")
public class InvestorInfo implements Serializable {

	private static final long serialVersionUID = 4987155500658083490L;

	@Id
	@Column(name = "investor_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long investorId;

	@Column(name = "investor_title")
	private String investorTitle;
	@Column(name = "investor_content")
	private String investorContent;
	@Column(name = "investor_create_user")
	private String createUser;
	@Column(name = "investor_create_time")
	private Date createTime;
	@Column(name = "investor_source")
	private String source;
	@Column(name = "investor_editor")
	private String editor;

	public Long getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Long investorId) {
		this.investorId = investorId;
	}

	public String getInvestorTitle() {
		return investorTitle;
	}

	public void setInvestorTitle(String investorTitle) {
		this.investorTitle = investorTitle;
	}

	public String getInvestorContent() {
		return investorContent;
	}

	public void setInvestorContent(String investorContent) {
		this.investorContent = investorContent;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

}
