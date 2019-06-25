package com.icfcc.SRRPService.gataway.staticize;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayInfos;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayNews;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayInfosQueryEcologyDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayNewsDao;
import com.icfcc.SRRPDao.pojo.GataWayInitInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <门户静态化新闻动态>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayInfosService extends GataWayBaseStaticzeService {
    private static Logger log = LoggerFactory.getLogger(GataWayInfosService.class);
    // 01新闻；
    // private static String INFOTYPE01 = "01";
    // 02投资动态
    private static String INFOTYPE02 = "02";
    // 03政策指南
    private static String INFOTYPE03 = "03";

    // 行业动态&政策指南
    @Autowired
    private GataWayInfosQueryEcologyDao gataWayInfosQueryEcologyDao;

    // 新闻动态-新闻
    @Autowired
    private GataWayNewsDao newsDao;

    // 新闻动态-新闻模版
    @Value("${news.newsModelFile}")
    private String newsModelFile;

    // 新闻动态-新闻输出路径
    @Value("${news.newsOutFile}")
    private String newsOutFile;

    // 新闻动态-新闻明細模版
    @Value("${news.newsDetailModelFile}")
    private String newsDetailModelFile;

    // 新闻动态-新闻明細输出路径
    @Value("${news.newsDetailOutFile}")
    private String newsDetailOutFile;

    // 新闻动态路径
    private String relNewsFilrPath = "relNews.json";

    // 行业动态路径
    @Value("${news.investorinfo.filepath}")
    private String investorinfoFilePath;

    // 政策指南路径
    @Value("${news.gov.filepath}")
    private String govFilePath;

    // 模版文件
    @Value("${news.modelFile}")
    private String modelFile;

    // 输出文件夹
    @Value("${news.outFile}")
    private String outFile;
    
    @Value("${mainOutPath}")
    private String mainOutPath;

    public void makeInfoDataJson() throws Exception {

        // 行业动态
        List<GataWayInfos> dataList = findGataWayInvestorInfo(INFOTYPE02);
        if (dataList != null && dataList.size() > 0) {
            for (GataWayInfos infos : dataList) {
                String tmp = infos.getContent();
                if (tmp != null && !"".equals(tmp)) {
                	infos.setContent(StringUtils.substringBefore(tmp,"&&"));
			}
//                if (tmp != null) {
//                    if (tmp.length() < 70) {
//                        infos.setContent(tmp);
//                    } else {
//                        infos.setContent(tmp.substring(0, 70) + "....");
//                    }
//                }
            }
            GataWayInitInfo gataWayInitInfo = new GataWayInitInfo();
            gataWayInitInfo.setCount(String.valueOf(dataList.size()));
            gataWayInitInfo.setData(dataList);

            writeFile(this.investorinfoFilePath, JSON.toJSONString(gataWayInitInfo));
        }

        // 政策指南
        dataList = findGataWayInvestorInfo(INFOTYPE03);
        if (dataList != null && dataList.size() > 0) {
            for (GataWayInfos infos : dataList) {
                String tmp = infos.getContent();
                if (tmp != null && !"".equals(tmp)) {
                	infos.setContent(StringUtils.substringBefore(tmp,"&&"));
			    }
//                if (tmp != null) {
//                    if (tmp.length() < 70) {
//                        infos.setContent(tmp);
//                    } else {
//                        infos.setContent(tmp.substring(0, 70) + "....");
//                    }
//                }
            }
            GataWayInitInfo gataWayInitInfo = new GataWayInitInfo();
            gataWayInitInfo.setCount(String.valueOf(dataList.size()));
            gataWayInitInfo.setData(dataList);
            writeFile(this.govFilePath, JSON.toJSONString(gataWayInitInfo));
        }

        //新闻动态
        List<GataWayNews> newsList = newsDao.findNewsListByType("portal.news");
        if (newsList != null && newsList.size() > 0) {
            for (GataWayNews news : newsList) {
                String tmp = news.getNewsContents();
                if (tmp != null && !"".equals(tmp)) {
                    news.setNewsContents(StringUtils.substringBefore(tmp, "&&"));
                }
            }
            GataWayInitInfo gataWayInitInfo = new GataWayInitInfo();
            gataWayInitInfo.setCount(String.valueOf(newsList.size()));
            gataWayInitInfo.setData(newsList);

            writeFile(this.relNewsFilrPath, JSON.toJSONString(gataWayInitInfo));
        }

    }

    // 五条新闻及新闻明细
    public void makeNewsHtmlAndDetail() throws Exception {

        // 新闻动态五条新闻
        Map<String, Object> data = new HashMap<String, Object>();
        List<GataWayNews> dataList = newsDao.findNewsListByType("portal.news");
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < 5; i++) {
                GataWayNews news = (GataWayNews) dataList.get(i);
                String tmpTile = news.getNewsTile();
                String tmpContents = news.getNewsContents();
                // 新闻动态新闻
                if (tmpTile != null) {
                    if (tmpTile.length() < 15) {
                        news.setNewsTileNews(tmpTile);
                    } else {
                        news.setNewsTileNews(tmpTile.substring(0, 13) + "...");
                    }
                    if (tmpTile.length() < 26) {
                        news.setNewsSummaryNewsFirst(tmpTile);
                    } else {
                        news.setNewsSummaryNewsFirst(tmpTile.substring(0, 25) + "...");
                    }
                }
                if (tmpContents != null) {
                	if (tmpContents != null && !"".equals(tmpContents)) {
						news.setNewsSummaryNews(StringUtils.substringBefore(tmpContents,"&&"));
				}
//                	if(tmpContents.indexOf("&&") > -1){
//                		String tmp[] = tmpContents.split("&&");
//                		tmpContents = tmp[0];
//                	}
//                    if (tmpContents.length() < 15) {
//                        news.setNewsSummaryNews(tmpContents);
//                    } else {
//                        news.setNewsSummaryNews(tmpContents.substring(0, 13) + "...");
//                    }
                }
                data.put("GataWayNews" + i, news);
            }
            this.makeStaticzeHtml(data, newsModelFile, newsOutFile);
        }

        // 新闻明细模版
        if (dataList != null && dataList.size() > 0) {
            for (GataWayNews gataWayNews : dataList) {
            	gataWayNews.setNewsContents(StringUtils.substringAfter(gataWayNews.getNewsContents(),"&&"));
                data = new HashMap<String, Object>();
                data.put("gataWayNews", gataWayNews);
                String outFile = newsDetailOutFile + gataWayNews.getNewsId() + ".html";
                // 明细详情制作
                this.makeStaticzeHtml(data, newsDetailModelFile, outFile);
            }
        }
    }

    public void makeInfoDetail() throws Exception {

        // 行业动态/政策指南明细
        List<GataWayInfos> dataList = findGataWayInvestorInfo(null);
        if (dataList != null && dataList.size() > 0) {
            for (GataWayInfos gataWayInfos : dataList) {
                Map<String, Object> data = new HashMap<String, Object>();
                gataWayInfos.setContent(StringUtils.substringAfter(gataWayInfos.getContent(),"&&"));
                data.put("gataWayInfos", gataWayInfos);
                // 明细详情制作
                String outFileUrl = outFile + gataWayInfos.getInfoId() + ".html";
                this.makeStaticzeHtml(data, modelFile, outFileUrl);
            }
        }


    }

    public List<GataWayInfos> findGataWayInvestorInfo(String infoType) {
        List<GataWayInfos> dataList = null;
        try {
            dataList = gataWayInfosQueryEcologyDao.findInfosList(infoType);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }

    private void writeFile(String filePath, String datas) throws IOException {
        FileWriter fw = new FileWriter(mainOutPath+filePath);
        PrintWriter out = new PrintWriter(fw);
        out.write(datas);
        out.println();
        fw.close();
        out.close();
    }
}
