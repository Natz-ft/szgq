package com.icfcc.ws.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.entity.RpCompanyCreditscores;
import com.icfcc.SRRPDao.repository.CompanyCreditScoresDao;
import com.icfcc.SRRPDao.repository.CompanyCreditScoresDaoImp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(value = "transactionManager")
public class QueryScoreService   {

	private static Logger log = LoggerFactory.getLogger(QueryScoreService.class);

	@Autowired
	private CompanyCreditScoresDaoImp crsDaoImp;
	@Autowired
	private CompanyCreditScoresDao crsDao;

	/**
	 * 查询机构总数
	 */
	public long queryCount() {
		long count = 0;
		try {
			count = crsDaoImp.countCompany();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return count;
	}

	/**
	 * 通过查询机构总数，每次查询100条机构的评分 start 初始值 0 end 初始值 100
	 */
	public List queryComList(int start, int end) {
		List comList = null;
		try {
			comList = crsDaoImp.queryCodes(start, end);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return comList;
	}

	public void saveScore(RpCompanyCreditscores s) {
		crsDao.save(s);
	}

	public void deleteScore() {
		crsDao.deleteAll();
	}
	public List<RpCompanyCreditscores> FindScore(String creditcode) {
		return crsDao.findBycreditcode(creditcode);
	}
	/**
	 * 把查询出来的json 处理封装 然后入库
	 * 
	 * @param result
	 *            通过webservice 查询出来的 100 条评分结果
	 * @return
	 */
	public void insertScores(String result) {
		JSONArray compScores = JSONArray.fromObject(result);
		System.out.println(compScores.size());
		// 循环，解析每条json 封装到对象
		for (int i = 0; i < compScores.size(); i++) {
			RpCompanyCreditscores scoreInfo = new RpCompanyCreditscores();
			// 取出来第一个json
			JSONObject scoreJson = (JSONObject) compScores.get(i);
			// 解析封装成对象
			analysisJSON(scoreInfo, scoreJson);
			// 持久化
			saveScore(scoreInfo);
		}

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

	/**
	 * 查询100条机构的组织机构代码或者社会统一代码拼接成websevice 查询条件
	 * 
	 * @param scoreList
	 * @return
	 */
	public String returnCodes(List scoreList) {
		String codes = "";
		for (int i = 0; i < scoreList.size(); i++) {
			Object[] cb = (Object[]) scoreList.get(i);
			codes += cb[0] + ",";
		}
		return codes;
	}

}
