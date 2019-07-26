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
@Table(name = "v_login_statistics")
public class PlatformUserLoginStatistics implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3603182012752923736L;

    @Id
    @Column(name="name")
    private String name;

    @Column(name="investor0")
    private int investor0;

    @Column(name="investor1")
    private int investor1;

    @Column(name="investor2")
    private int investor2;

    @Column(name="investor3")
    private int investor3;

    @Column(name="investor4")
    private int investor4;

    @Column(name="enterprise0")
    private int enterprise0;

    @Column(name="enterprise1")
    private int enterprise1;

    @Column(name="enterprise2")
    private int enterprise2;

    @Column(name="enterprise3")
    private int enterprise3;

    @Column(name="enterprise4")
    private int enterprise4;

    @Column(name="admin0")
    private int admin0;

    @Column(name="admin1")
    private int admin1;

    @Column(name="admin2")
    private int admin2;

    @Column(name="admin3")
    private int admin3;

    @Column(name="admin4")
    private int admin4;

    @Column(name="all0")
    private int all0;

    @Column(name="all1")
    private int all1;

    @Column(name="all2")
    private int all2;

    @Column(name="all3")
    private int all3;

    @Column(name="all4")
    private int all4;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInvestor0() {
        return investor0;
    }

    public void setInvestor0(int investor0) {
        this.investor0 = investor0;
    }

    public int getInvestor1() {
        return investor1;
    }

    public void setInvestor1(int investor1) {
        this.investor1 = investor1;
    }

    public int getInvestor2() {
        return investor2;
    }

    public void setInvestor2(int investor2) {
        this.investor2 = investor2;
    }

    public int getInvestor3() {
        return investor3;
    }

    public void setInvestor3(int investor3) {
        this.investor3 = investor3;
    }

    public int getInvestor4() {
        return investor4;
    }

    public void setInvestor4(int investor4) {
        this.investor4 = investor4;
    }

    public int getEnterprise0() {
        return enterprise0;
    }

    public void setEnterprise0(int enterprise0) {
        this.enterprise0 = enterprise0;
    }

    public int getEnterprise1() {
        return enterprise1;
    }

    public void setEnterprise1(int enterprise1) {
        this.enterprise1 = enterprise1;
    }

    public int getEnterprise2() {
        return enterprise2;
    }

    public void setEnterprise2(int enterprise2) {
        this.enterprise2 = enterprise2;
    }

    public int getEnterprise3() {
        return enterprise3;
    }

    public void setEnterprise3(int enterprise3) {
        this.enterprise3 = enterprise3;
    }

    public int getEnterprise4() {
        return enterprise4;
    }

    public void setEnterprise4(int enterprise4) {
        this.enterprise4 = enterprise4;
    }

    public int getAdmin0() {
        return admin0;
    }

    public void setAdmin0(int admin0) {
        this.admin0 = admin0;
    }

    public int getAdmin1() {
        return admin1;
    }

    public void setAdmin1(int admin1) {
        this.admin1 = admin1;
    }

    public int getAdmin2() {
        return admin2;
    }

    public void setAdmin2(int admin2) {
        this.admin2 = admin2;
    }

    public int getAdmin3() {
        return admin3;
    }

    public void setAdmin3(int admin3) {
        this.admin3 = admin3;
    }

    public int getAdmin4() {
        return admin4;
    }

    public void setAdmin4(int admin4) {
        this.admin4 = admin4;
    }

    public int getAll1() {
        return all1;
    }

    public void setAll1(int all1) {
        this.all1 = all1;
    }

    public int getAll2() {
        return all2;
    }

    public void setAll2(int all2) {
        this.all2 = all2;
    }

    public int getAll3() {
        return all3;
    }

    public void setAll3(int all3) {
        this.all3 = all3;
    }

    public int getAll4() {
        return all4;
    }

    public void setAll4(int all4) {
        this.all4 = all4;
    }

    public int getAll0() {
        return all0;
    }

    public void setAll0(int all0) {
        this.all0 = all0;
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
