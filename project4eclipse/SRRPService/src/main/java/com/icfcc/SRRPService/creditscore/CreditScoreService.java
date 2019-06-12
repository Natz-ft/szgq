package com.icfcc.SRRPService.creditscore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.icfcc.SRRPDao.jpa.entity.creditscore.score.RpCompanyCreditscores;
import com.icfcc.SRRPDao.jpa.repository.creditscore.CompanyCreditScoresDao;
import com.icfcc.SRRPDao.jpa.repository.creditscore.impl.CreditscoreDAOImpl;
import com.icfcc.SRRPService.creditscore.client.WSWebServiceClient;

import net.sf.json.JSONObject;

@Service
@Transactional(value = "transactionManager")
public class CreditScoreService {
	
		@Autowired
		private CreditscoreDAOImpl creditScore;
		@Autowired
		private CompanyCreditScoresDao crsDao;
		@Autowired
		public WSWebServiceClient client;
		/**
		 * 评分查询
		 * @param queryCondition
		 * @return
		 */
		public  RpCompanyCreditscores queryScore(String creditType , String creditCode){
			 RpCompanyCreditscores s  =null;
			try {
				s=  creditScore.getScoreInfo(creditType, creditCode);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        return s;
	    }
		@Transactional(value = "transactionManager")
		public void saveScore(RpCompanyCreditscores s) {
			crsDao.save(s);
		}
		@Transactional(value = "transactionManager")
		public List<RpCompanyCreditscores>  FindScore(String creditcode) {
			return crsDao.findBycreditcode(creditcode);
		}
		/**
		 * 解析单个评分json
		 * 
		 * @param scoreInfo
		 * @param scoreJson
		 * @return
		 */
		public RpCompanyCreditscores analysisJSON(RpCompanyCreditscores scoreInfo, JSONObject scoreJson) {
			// 解析，对查询出来的 机构信用代码 进行判断
			// baseJson
			JSONObject baserJson = scoreJson.getJSONObject("basicinfo");
			String concode = baserJson.getString("corpcredit");
			String contype = "1";// 默认是 1 组织机构代码
			// 判断 如果 信用代码不是18为，那么信用代码类型 就是 2 统一社会信用码
			if (null != concode) {
				if (concode.length() == 18) {
					contype = "2";
				}
			}
			scoreInfo.setCreditcode(concode);
			scoreInfo.setCreditype(contype);
			scoreInfo.setBasejson(scoreJson.getString("basicinfo"));
			// 需要对柱状图经行解析
			// barjson
			JSONObject barjson = scoreJson.getJSONObject("contrastinfo");
			String level = barjson.getString("level");// 等级
			String score = barjson.getString("score");// 评分分数
			if (!"".equals(level.trim())) {
				scoreInfo.setLevel(Integer.parseInt(level));
			}
			if (!"".equals(score.trim())) {
				scoreInfo.setScore(Integer.parseInt(score));
			}
			// 柱状图
			scoreInfo.setBarjson(scoreJson.getString("contrastinfo"));
			// 折线图
			scoreInfo.setLinejson(scoreJson.getString("hisinfo"));
			// 雷达图
			scoreInfo.setRadarjson(scoreJson.getString("scoreinfo"));
			return scoreInfo;
		}

}
