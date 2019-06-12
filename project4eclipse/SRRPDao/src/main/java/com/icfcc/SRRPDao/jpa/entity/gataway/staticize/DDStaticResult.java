package com.icfcc.SRRPDao.jpa.entity.gataway.staticize;



/**
 */

public class DDStaticResult implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    private String id;
    /*
     * 统计类型01：发布融资总额；02：解决融资总额；03：正在融资总额；04：平台企业家数；05：平台机构数；06：月度融资统计
     */
    private String areacode;

    private String amount = "0";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}