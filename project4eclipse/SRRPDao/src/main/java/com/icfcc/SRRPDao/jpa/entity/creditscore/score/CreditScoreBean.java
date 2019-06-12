package com.icfcc.SRRPDao.jpa.entity.creditscore.score;

/**
 * codecomment:
 * 
 * @author yang
 * @time 16-12-02 14:14:03 子系统: JSubSystemImplac594bcb15c34ba0b5e922e05e71a9b5
 *       模块:calculate 组件:CreditScore
 */
public class CreditScoreBean {
	/** 属性列表 */
	String radarResult ;   //雷达图图例
	String barParam ;   //柱状图图例
	String barData ;    //柱状图数据
	String rankPercent ;   //排名百分比  
	String lineData ;   //折线图  
	int score;    //柱状图分数
	int level ;  //当前分数所在位置
	int total ; //柱状图用的评分
	String midName ;  //中位数企业
	String midScore ;  //中位数企业分数
	int midLevel ; //中位数等级
	String rating ;
	String corpcredit;   //证件代码
    int creditscore;		//当前企业的分
    String allData;    //全部
    String tradeData;//行业
    String areaData;//区域
    String scaleData;//规模
    String entiData;//企业性质
    String investmentData;//投资类型
   
    
    
	public CreditScoreBean() {
		super();
		radarResult = "";
		barParam = "";   
		barData = "";
		allData="";    //全部
		tradeData="";//行业
	    areaData="";//区域
	    scaleData="";//规模
	    entiData="";//企业性质
	    investmentData ="";//投资类型
		rankPercent = "";   //排名百分比  
		lineData = "";
		level= 0;
		total = 0;
		midName = "";  
		midScore = ""; 
		score =0; 
		creditscore=0;
		rating ="";
		corpcredit="";
		
	}
	
	
	
	
	public String getScaleData() {
		return scaleData;
	}

	public void setScaleData(String scaleData) {
		this.scaleData = scaleData;
	}

	public String getEntiData() {
		return entiData;
	}

	public void setEntiData(String entiData) {
		this.entiData = entiData;
	}

	public String getInvestmentData() {
		return investmentData;
	}

	public void setInvestmentData(String investmentData) {
		this.investmentData = investmentData;
	}

	public String getAllData() {
		return allData;
	}
	public void setAllData(String allData) {
		this.allData = allData;
	}
	public String getTradeData() {
		return tradeData;
	}
	public void setTradeData(String tradeData) {
		this.tradeData = tradeData;
	}
	public String getAreaData() {
		return areaData;
	}
	public void setAreaData(String areaData) {
		this.areaData = areaData;
	}
	public String getCorpcredit() {
		return corpcredit;
	}
	public void setCorpcredit(String corpcredit) {
		this.corpcredit = corpcredit;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getCreditscore() {
		return creditscore;
	}
	public void setCreditscore(int creditscore) {
		this.creditscore = creditscore;
	}
	public String getRadarResult() {
		return radarResult;
	}
	public void setRadarResult(String radarResult) {
		this.radarResult = radarResult;
	}
	public String getBarParam() {
		return barParam;
	}
	public void setBarParam(String barParam) {
		this.barParam = barParam;
	}
	public String getBarData() {
		return barData;
	}
	public void setBarData(String barData) {
		this.barData = barData;
	}
	public String getRankPercent() {
		return rankPercent;
	}
	public void setRankPercent(String rankPercent) {
		this.rankPercent = rankPercent;
	}
	public String getLineData() {
		return lineData;
	}
	public void setLineData(String lineData) {
		this.lineData = lineData;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getMidName() {
		return midName;
	}
	public void setMidName(String midName) {
		this.midName = midName;
	}
	public String getMidScore() {
		return midScore;
	}
	public void setMidScore(String midScore) {
		this.midScore = midScore;
	}
	public int getMidLevel() {
		return midLevel;
	}
	public void setMidLevel(int midLevel) {
		this.midLevel = midLevel;
	}
	
}
