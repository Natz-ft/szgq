package com.icfcc.SRRPService.gataway.staticize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayContantUs;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayContantUsDist;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayFaqAndNotice;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayContantUsDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayContantUsDistDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayFaqAndNoticeDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * <门户静态化联系我们>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayContantUsService extends GataWayBaseStaticzeService {
    private static Logger log = LoggerFactory.getLogger(GataWayContantUsService.class);

    @Autowired
    private GataWayContantUsDao gataWayContantUsDao;

    @Autowired
    private GataWayContantUsDistDao gataWayContantUsDistDao;

    @Autowired
    private GataWayFaqAndNoticeDao gataWayFaqAndNoticeDao;

    // 联系我们模版
    @Value("${relate.relateModelFile}")
    private String relateModelFile;
    // 联系我们输出路径
    @Value("${relate.relateOutFile}")
    private String relateOutFile;
    // 联系我们公告模版
    @Value("${relate.noticeModelFile}")
    private String noticeModelFile;
    // 联系我们公告输出路径
    @Value("${relate.noticeOutFile}")
    private String noticeOutFile;

    // 联系我们HTML
    public void makeContantUsHtml() throws Exception {

        // 联系我们HTML对象
        Map<String, Object> data = new HashMap<String, Object>();
        // 联系我们
        List<GataWayContantUs> listContantUs = findContanUsList();
        data.put("listContantUs", listContantUs);
        // 行政区联系方式
        List<GataWayContantUsDist> listContantUsDist = findContanUsDistList();
        data.put("listContantUsDist", listContantUsDist);
        // 公告&常见问题
        List<GataWayFaqAndNotice> tmpDataList = findFaqAndNoticeList();
        List<GataWayFaqAndNotice> listFaq = new ArrayList<GataWayFaqAndNotice>();
        List<GataWayFaqAndNotice> listNotice = new ArrayList<GataWayFaqAndNotice>();
        if (tmpDataList != null && tmpDataList.size() > 0) {
            for (GataWayFaqAndNotice infos : tmpDataList) {
                if (SRRPConstant.TYPE0001.equals(infos.getType())) {
                    listFaq.add(infos);
                } else if (SRRPConstant.TYPE0002.equals(infos.getType())) {
                    listNotice.add(infos);
                }
            }
        }
        data.put("listFaq", listFaq);
        data.put("listNotice", listNotice);
        // freemaker制作html
        this.makeStaticzeHtml(data, relateModelFile, relateOutFile);
    }

    // 联系我们公告明细
    public void makeNoticesDetail() throws Exception {
        // 公告明细
        List<GataWayFaqAndNotice> dataList = findFaqAndNoticeList();
        if (dataList != null && dataList.size() > 0) {
            for (GataWayFaqAndNotice infos : dataList) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("infos", infos);
                // freemaker制作html
                String outFile = noticeOutFile + infos.getId() + ".html";
                this.makeStaticzeHtml(data, noticeModelFile, outFile);
            }
        }
    }

    // 联系我们
    public List<GataWayContantUs> findContanUsList() {
        List<GataWayContantUs> dataList = null;
        try {
            dataList = gataWayContantUsDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }

    // 行政区联系方式
    public List<GataWayContantUsDist> findContanUsDistList() {
        List<GataWayContantUsDist> dataList = null;
        try {
            dataList = gataWayContantUsDistDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }

    // 公告&常见问题
    public List<GataWayFaqAndNotice> findFaqAndNoticeList() {
        List<GataWayFaqAndNotice> dataList = null;
        try {
            dataList = gataWayFaqAndNoticeDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }
}
