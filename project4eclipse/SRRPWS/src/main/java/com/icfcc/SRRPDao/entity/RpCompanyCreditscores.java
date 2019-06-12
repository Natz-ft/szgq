package com.icfcc.SRRPDao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rp_company_creditscores")
public class RpCompanyCreditscores implements Serializable {
	
	private static final long serialVersionUID = 2144309744253641128L;

	@Id
	@Column(name = "cop_id", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String copid;
	
	@Column(name="creditcode")
	private String creditcode;
	
	@Column(name = "creditype")
	private String creditype;
	
	@Column(name="score")
	private int score;
	
	@Column(name="level")
	private int level;

	@Column(name="basejson")
	private String basejson;
	
	@Column(name="barjson")
	private String barjson;

	@Column(name="radarjson")
	private String radarjson;
	
	@Column(name="linejson")
	private String linejson;
	
	
	
	
	
	public String getBarjson() {
		return barjson;
	}

	public void setBarjson(String barjson) {
		this.barjson = barjson;
	}

	public String getCopid() {
		return copid;
	}

	public void setCopid(String copid) {
		this.copid = copid;
	}

	public String getCreditcode() {
		return creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}

	public String getBasejson() {
		return this.basejson;
	}

	public void setBasejson(String basejson) {
		this.basejson = basejson;
	}

	public String getCreditype() {
		return this.creditype;
	}

	public void setCreditype(String creditype) {
		this.creditype = creditype;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLinejson() {
		return this.linejson;
	}

	public void setLinejson(String linejson) {
		this.linejson = linejson;
	}

	public String getRadarjson() {
		return this.radarjson;
	}

	public void setRadarjson(String radarjson) {
		this.radarjson = radarjson;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}


}