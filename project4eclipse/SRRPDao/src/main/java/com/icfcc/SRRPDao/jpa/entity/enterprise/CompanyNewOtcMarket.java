package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


/**
 * 企业基本信息实体类
 * 
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name = "rp_company_new_otc_market")
public class CompanyNewOtcMarket implements Serializable {


	
    /**
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = 3503338630937143629L;

    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */

     @Id
     @Column(name = "ID")
     @GenericGenerator(name = "system-uuid", strategy = "uuid")
     @GeneratedValue(generator = "system-uuid")
     private String id;

     @Column(name = "COMPANY_ID")
     private String companyId;
     
     @Column(name = "COMPANY_NAME")
     private String companyName;

     @Column(name = "COMPANY_CODE_TYPE")
     private String codetype;

     @Column(name = "COMPANY_CODE", length = 20)
     private String code;

   

     @Column(name = "COMPANY_ADRESS")
     private String companyAdress;// 注册地址

     @Column(name = "stockcode")
     private String stockcode;// 证券代码
     
     @Column(name = "Stknme")
     private String Stknme;//证券简称
     
     @Column(name = "listed_date")
     private String listedDate;// 上市日期
     
     @Column(name = "Mode_transfer")
     private String ModeTransfer;//转让方式

     @Column(name = "hierarchy")
     private String hierarchy;//所属分层

     
     @Column(name = "CREATE_TIME", length = 10)
     private Date createTime;


     public String getId() {
         return id;
     }


     public void setId(String id) {
         this.id = id;
     }


     public String getCompanyId() {
         return companyId;
     }


     public void setCompanyId(String companyId) {
         this.companyId = companyId;
     }


     public String getCompanyName() {
         return companyName;
     }


     public void setCompanyName(String companyName) {
         this.companyName = companyName;
     }


     public String getCodetype() {
         return codetype;
     }


     public void setCodetype(String codetype) {
         this.codetype = codetype;
     }


     public String getCode() {
         return code;
     }


     public void setCode(String code) {
         this.code = code;
     }




     public String getCompanyAdress() {
         return companyAdress;
     }


     public void setCompanyAdress(String companyAdress) {
         this.companyAdress = companyAdress;
     }


     public String getListedDate() {
         return listedDate;
     }


     public void setListedDate(String listedDate) {
         this.listedDate = listedDate;
     }


     public String getModeTransfer() {
         return ModeTransfer;
     }


     public void setModeTransfer(String modeTransfer) {
         ModeTransfer = modeTransfer;
     }


     public String getHierarchy() {
         return hierarchy;
     }


     public void setHierarchy(String hierarchy) {
         this.hierarchy = hierarchy;
     }


     public Date getCreateTime() {
         return createTime;
     }


     public void setCreateTime(Date createTime) {
         this.createTime = createTime;
     }


    public String getStockcode() {
        return stockcode;
    }


    public void setStockcode(String stockcode) {
        this.stockcode = stockcode;
    }


    public String getStknme() {
        return Stknme;
    }


    public void setStknme(String stknme) {
        Stknme = stknme;
    }
     

}
