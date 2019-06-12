package com.icfcc.SRRPDao.jpa.entity.dd;


public class DD implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    private String dicCode;
    private String dicName;

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

}
