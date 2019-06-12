package com.icfcc.SRRPDao.pojo;
/**
 * 
* @ClassName: StaticPieData
* @Description: 接收饼状图信息
* @author daiyx
* @date 2017年10月20日 上午8:56:42
*
 */
public class StaticPieData {

	// 投资金额
    private String value;
	// 统计纬度
    private String name;
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
