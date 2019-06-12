package com.icfcc.ssrp.session;





public class investorArea implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4443524301840097059L;
		// 代码
		
		public String code ;
		// 父级代码
		public String parentcode ;
		// 名称
		public String name ;
		// '1.国，2.省市，3.市，4.区县，5.街道，6.村镇',
		public String level ;
		
	//  @Transient
	    public String areaProvince; // 区域省代码
//		@Transient
	    public String areaCity; // 区域省代码
//		@Transient
	    public String areaCounty; // 区域省代码
	    
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
		public String getAreaProvince() {
			return areaProvince;
		}
		public void setAreaProvince(String areaProvince) {
			this.areaProvince = areaProvince;
		}
		public String getAreaCity() {
			return areaCity;
		}
		public void setAreaCity(String areaCity) {
			this.areaCity = areaCity;
		}
		public String getAreaCounty() {
			return areaCounty;
		}
		public void setAreaCounty(String areaCounty) {
			this.areaCounty = areaCounty;
		}
	

	
}