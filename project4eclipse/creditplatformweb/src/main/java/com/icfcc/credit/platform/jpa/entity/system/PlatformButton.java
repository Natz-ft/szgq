package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
* @ClassName: PlatformButton
* @Description: TODO(这里用一句话描述这个类的作用)
* @author hugt
* @date 2017年9月14日 下午5:50:33
*
 */
@Data
@Entity
@Table(name = "platform_button")
public class PlatformButton implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2686424043984131226L;
    
    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUTTON_ID")
    private Long buttonId;
    
    // 按钮代码
    @Column(name = "BUTTON_CODE")
    private String buttonCode;
    
    // 按钮名称
    @Column(name = "BUTTON_NAME")
    private String buttonName;
    
    // 按钮描述
    @Column(name = "BUTTON_DESC")
    private String buttonDesc;
    
    // 创建人
    @Column(name = "CREATE_USER")
    private String createUser;
    
    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    // 更新时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MENU_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    private PlatformMenu menu;
    
    @Transient
    private boolean checked;
    
    public boolean isChecked()
    {
        return checked;
    }
    
    public Long getButtonId() {
		return buttonId;
	}

	public void setButtonId(Long buttonId) {
		this.buttonId = buttonId;
	}

	public String getButtonCode() {
		return buttonCode;
	}

	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getButtonDesc() {
		return buttonDesc;
	}

	public void setButtonDesc(String buttonDesc) {
		this.buttonDesc = buttonDesc;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public PlatformMenu getMenu() {
		return menu;
	}

	public void setMenu(PlatformMenu menu) {
		this.menu = menu;
	}

	public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
		
	}
}
