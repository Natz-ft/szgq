package com.icfcc.SRRPDao.pojo;
/**
 * 
* @ClassName: StaticHistogramData
* @Description: 接收柱状图信息
* @author daiyx
* @date 2017年10月20日 上午8:56:03
*
 */
public class StaticHistogramData {
    
    
 // 统计纬度ex： "x_key": "互联网,生物技术/医疗健康,IT,信息技术,保险产业,卫生服务,政治资讯",
    private String xKey;
    // 投资金额ex:"y_value": "65,52,33,22,20,20,15"
    private String yValue;
	
    public String getxKey() {
		return xKey;
	}
	public void setxKey(String xKey) {
		this.xKey = xKey;
	}
	public String getyValue() {
		return yValue;
	}
	public void setyValue(String yValue) {
		this.yValue = yValue;
	}
}
