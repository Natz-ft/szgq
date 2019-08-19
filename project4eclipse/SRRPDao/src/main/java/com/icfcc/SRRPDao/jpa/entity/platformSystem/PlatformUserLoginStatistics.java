/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PlatformUserLoginStatistics
 * Author:   whyxs
 * Date:     2019/7/23 15:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.icfcc.SRRPDao.jpa.entity.platformSystem;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author whyxs
 * @create 2019/7/23
 * @since 1.0.0
 */
@Entity
@Table(name = "v_login_statistics_new")
public class PlatformUserLoginStatistics implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3603182012752923736L;

    @Id
    @Column(name="name")
    private String name;

    @Column(name="investor")
    private int investor;

    @Column(name="enterprise")
    private int enterprise;

    @Column(name="admin")
    private int admin;

    @Column(name="allu")
    private int allu;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInvestor() {
        return investor;
    }

    public void setInvestor(int investor) {
        this.investor = investor;
    }

    public int getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(int enterprise) {
        this.enterprise = enterprise;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getAllu() {
        return allu;
    }

    public void setAllu(int allu) {
        this.allu = allu;
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
