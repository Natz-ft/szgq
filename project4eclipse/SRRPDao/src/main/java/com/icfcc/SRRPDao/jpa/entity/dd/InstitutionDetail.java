package com.icfcc.SRRPDao.jpa.entity.dd;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
@Entity
@Table(name = "rp_institution_detail")
public class InstitutionDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2941128843972601764L;

	/**
	 * 
	 */

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "NAME")
    private String name;

   

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
