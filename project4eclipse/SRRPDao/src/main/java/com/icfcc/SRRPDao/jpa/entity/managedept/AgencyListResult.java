package com.icfcc.SRRPDao.jpa.entity.managedept;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
/**
 * 查询机构榜单结果集实体类
* @ClassName: AgencyListResult
* @author daiyx
* @date 2017年11月13日 下午6:33:37
*
 */
@Entity
public class AgencyListResult implements Serializable {

	private static final long serialVersionUID = -2236482709539842484L;

	@Id
	@Column(name = "investorg_id")
	private String investorgId; // 机构ID

	@Column(name = "rank")
	private Integer rank; // 名次

	@Column(name = "name")
	private String name; // 机构名称

	@Column(name = "amounts")
	private Double sumAmount; // 机构投资总金额

	@Column(name = "toalid")
	private Integer countEnterpriseId; // 机构投资总企业数

	@Column(name = "demands")
	private Integer  countInfoId; // 机构解决需求个数
	
	@Transient
	private Integer countSnedInfoId; // 机构解决需求个数

	public String getInvestorgId() {
		return investorgId;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public void setInvestorgId(String investorgId) {
		this.investorgId = investorgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}

	public Integer getCountEnterpriseId() {
		return countEnterpriseId;
	}

	public void setCountEnterpriseId(Integer countEnterpriseId) {
		this.countEnterpriseId = countEnterpriseId;
	}

	public Integer getCountInfoId() {
		return countInfoId;
	}

	public void setCountInfoId(Integer countInfoId) {
		this.countInfoId = countInfoId;
	}

	public Integer getCountSnedInfoId() {
		return countSnedInfoId;
	}

	public void setCountSnedInfoId(Integer countSnedInfoId) {
		this.countSnedInfoId = countSnedInfoId;
	}

}
