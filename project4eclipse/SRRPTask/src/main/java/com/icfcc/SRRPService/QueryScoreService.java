package com.icfcc.SRRPService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.cfcc.cap.ws.creditscore.WSWebServiceClient;
import com.icfcc.SRRPDao.s1.jpa.entity.RpCompanyCreditscores;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyCreditScoresDao;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyCreditScoresDaoImp;
import com.icfcc.credit.platform.util.SRRPConstant;


@Service
public class QueryScoreService   {

	private static Logger log = LoggerFactory.getLogger(QueryScoreService.class);

	@Autowired
	private CompanyCreditScoresDaoImp crsDaoImp;

	@Autowired
	private CompanyCreditScoresDao crsDao;
	@Autowired
	private WSWebServiceClient client;
	@Value("${count}")
	private int num;
	@Autowired
	public QueryScoreService queryScoreService;
	/**
	 * 查询机构总数
	 */
	@Transactional(value = "transactionManager")
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
	@Transactional(value = "transactionManager")
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
	@Transactional(value = "transactionManager")
	public Set<String>  queryComList() {
		Set<String> comList = null;
		try {
			comList = crsDaoImp.queryCodes();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return comList;
	}
	@Transactional(value = "transactionManager")
	public void saveScore(RpCompanyCreditscores s) {
		crsDao.save(s);
	}
	@Transactional(value = "transactionManager")
	public void saveScore(ArrayList<RpCompanyCreditscores> s) {
		crsDao.save(s);
	}
	@Transactional(value = "transactionManager")
	public List<RpCompanyCreditscores> queryScore(String creditcode) {
		return crsDao.queryScore(creditcode);
	}
	@Transactional(value = "transactionManager")
	public RpCompanyCreditscores queryScore1(String creditcode) {
		return crsDao.findOne(creditcode);
	}
	@Transactional(value = "transactionManager")
	public void deleteScore() {
		crsDao.deleteAll();
	}

//	/**
//	 * 把查询出来的json 处理封装 然后入库
//	 * 
//	 * @param result
//	 *            通过webservice 查询出来的 100 条评分结果
//	 * @return
//	 */
//	public void insertScores(String result) {
//		JSONArray compScores = JSONArray.fromObject(result);
//		System.out.println(compScores.size());
//		// 循环，解析每条json 封装到对象
//		for (int i = 0; i < compScores.size(); i++) {
//			RpCompanyCreditscores scoreInfo = new RpCompanyCreditscores();
//			// 取出来第一个json
//			JSONObject scoreJson = (JSONObject) compScores.get(i);
//			// 解析封装成对象
//			analysisJSON(scoreInfo, scoreJson);
//			// 持久化
//			saveScore(scoreInfo);
//		}

//	}

	/**
	 * 解析单个评分json
	 * 
	 * @param scoreInfo
	 * @param scoreJson
	 * @return
	 */
	public RpCompanyCreditscores analysisJSON(RpCompanyCreditscores scoreInfo, JSONObject scoreJson,Set<String> scoreCodeList,ArrayList<RpCompanyCreditscores> scoreInfos) {
		// 解析，对查询出来的 机构信用代码 进行判断
		// baseJson
//		JSONObject baserJson = scoreJson.getJSONObject("basicinfo");
		JSONObject concodeJson = scoreJson.getJSONObject("returnParmsCode");
		String corporgcode = concodeJson.getString("corporgcode");
		String corpcredit = concodeJson.getString("corpcredit");
		String contype = "1";// 默认是 1 组织机构代码
		scoreInfo.setCreditcode(corpcredit);
		scoreInfo.setCorporgcode(corporgcode);
		scoreInfo.setCreditype(contype);
		scoreInfo.setBasejson(scoreJson.getString("basicinfo"));
		scoreInfo.setBarjson(scoreJson.getString("contrastinfo"));
		scoreInfo.setLinejson(scoreJson.getString("hisinfo"));
		scoreInfo.setRadarjson(scoreJson.getString("scoreinfo"));
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
		insertScores(scoreInfo,scoreCodeList,scoreInfos);
		return scoreInfo;
	}
    public void insertScores(RpCompanyCreditscores rpCompanyCreditscores,Set<String> scoreCodeList,ArrayList<RpCompanyCreditscores> scoreInfos){
    	boolean isReadd;
    	if(scoreCodeList.contains(rpCompanyCreditscores.getCreditcode())){//社会统一信用码
			List<RpCompanyCreditscores>  scor=queryScoreService.queryScore(rpCompanyCreditscores.getCreditcode());
			if(scor.size()>0){//存在update
				rpCompanyCreditscores.setCreditcode(scor.get(0).getCreditcode());
				rpCompanyCreditscores.setCreditype(scor.get(0).getCreditype());
				rpCompanyCreditscores.setCopid(scor.get(0).getCopid());
			}else{//不存在则新增
				String newCopidId = UUID.randomUUID().toString();
				newCopidId = newCopidId.replace("-", "");
				rpCompanyCreditscores.setCopid(newCopidId);
				rpCompanyCreditscores.setCreditcode(rpCompanyCreditscores.getCreditcode());
				rpCompanyCreditscores.setCreditype("2");
			}
			isReadd=true;
			//防止添加重复对象
			if(scoreInfos.size()>0){
				for(RpCompanyCreditscores scoreInfo1:scoreInfos){
					if(scoreInfo1.getCreditcode().equals(rpCompanyCreditscores.getCreditcode())){
						isReadd=false;
					}
				}
			}
			
			if(isReadd){
				scoreInfos.add(rpCompanyCreditscores);
			}
		}
    	if(scoreCodeList.contains(rpCompanyCreditscores.getCorporgcode())){//组织机构代码
			List<RpCompanyCreditscores>  scor=queryScoreService.queryScore(rpCompanyCreditscores.getCorporgcode());
			RpCompanyCreditscores rpCompanyCreditscoresNew=new	RpCompanyCreditscores();
			BeanUtils.copyProperties(rpCompanyCreditscores,rpCompanyCreditscoresNew);
				if(scor.size()>0){
					rpCompanyCreditscoresNew.setCreditcode(scor.get(0).getCreditcode());
					rpCompanyCreditscoresNew.setCreditype(scor.get(0).getCreditype());
					rpCompanyCreditscoresNew.setCopid(scor.get(0).getCopid());
				}else{
					String newCopidId = UUID.randomUUID().toString();
					newCopidId = newCopidId.replace("-", "");
					rpCompanyCreditscoresNew.setCopid(newCopidId);
					rpCompanyCreditscoresNew.setCreditcode(rpCompanyCreditscores.getCorporgcode());
					rpCompanyCreditscoresNew.setCreditype("1");
					
				}
				isReadd=true;
				//防止添加重复对象
				if(scoreInfos.size()>0){
					for(RpCompanyCreditscores scoreInfo1:scoreInfos){
						if(scoreInfo1.getCreditcode().equals(rpCompanyCreditscoresNew.getCreditcode())){
							isReadd=false;
						}
					}
				}
				
				if(isReadd){
					scoreInfos.add(rpCompanyCreditscoresNew);
				}
		}
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
