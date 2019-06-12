package com.icfcc.ssrp.web;

public class GataWay implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7115768274556047575L;
    private String name;
    private String certno;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

}