package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "platform_dic_area")
public class investorDicArea implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4443524301840097059L;
		// 代码
		@Id
		@Column(name = "code")
		public String code ;
		// 父级代码
		@Column(name = "parentcode")
		public String parentcode ;
		// 名称
		@Column(name = "name")
		public String name ;
		// '1.国，2.省市，3.市，4.区县，5.街道，6.村镇',
		@Column(name = "level")
		public String level ;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getParentcode() {
			return parentcode;
		}
		public void setParentcode(String parentcode) {
			this.parentcode = parentcode;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
	

	
}