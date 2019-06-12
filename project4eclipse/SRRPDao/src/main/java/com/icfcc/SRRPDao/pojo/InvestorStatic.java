package com.icfcc.SRRPDao.pojo;

import java.util.List;

public class InvestorStatic {


    // 按行业统计数据
    private List<?> industryData;
    
    // 饼状图统计数据
    private List<?> breaddata;

    // 柱状图数据
    private List<?> histogramData;

    public List<?> getIndustryData() {
        return industryData;
    }

    public void setIndustryData(List<?> industryData) {
        this.industryData = industryData;
    }

    public List<?> getBreaddata() {
		return breaddata;
	}

	public void setBreaddata(List<?> breaddata) {
		this.breaddata = breaddata;
	}

	public List<?> getHistogramData() {
        return histogramData;
    }

    public void setHistogramData(List<?> histogramData) {
        this.histogramData = histogramData;
    }
}
