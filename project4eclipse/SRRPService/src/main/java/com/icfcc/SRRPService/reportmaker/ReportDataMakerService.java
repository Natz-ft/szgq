package com.icfcc.SRRPService.reportmaker;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanArea;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanEnterprise;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanOrg;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanOrgTmp;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTotal;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTrade;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ReportBeanAreaDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ReportBeanEnterpriseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ReportBeanOrgDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ReportBeanTotalDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ReportBeanTradeDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.CompanyBaseDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.InvestorDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.InvestorLoanDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.RealTimeReportDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.ReportBeanAreaDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.ReportBeanEnterpriseDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.ReportBeanOrgDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.ReportBeanTradeDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.impl.FinacingDemandInfoDaoImpl;
import com.icfcc.SRRPService.Constants;
import com.icfcc.SRRPService.DicWord;

@Service
@Transactional(value = "transactionManager")
public class ReportDataMakerService {

    private Logger log = LoggerFactory.getLogger(ReportDataMakerService.class);

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

    private final static Integer NUM_STATISTIC = 10;

    @Autowired
    private InvestorDaoImpl investorDao;
    @Autowired
    private CompanyBaseDaoImpl companyBaseDao;
    @Autowired
    private InvestorLoanDaoImpl investorLoanDao;
    @Autowired
    private FinacingDemandInfoDaoImpl finacingDemandInfoDao;
    @Autowired
    private ReportBeanTotalDao reportBeanTotalDao;
    @Autowired
    private ReportBeanEnterpriseDao reportBeanEnterpriseDao;
    @Autowired
    private ReportBeanEnterpriseDaoImpl reportBeanEnterpriseDaoImpl;
    @Autowired
    private ReportBeanOrgDao reportBeanOrgDao;
    @Autowired
    private ReportBeanOrgDaoImpl reportBeanOrgDaoImpl;
    @Autowired
    private ReportBeanAreaDao reportBeanAreaDao;
    @Autowired
    private ReportBeanTradeDao reportBeanTradeDao;
    @Autowired
    private ReportBeanAreaDaoImpl reportBeanAreaDaoImpl;
    @Autowired
    private ReportBeanTradeDaoImpl reportBeanTradeDaoImpl;
    @Autowired
    private RealTimeReportDaoImpl realTimeReportDao;

    // ==================================================平台融资总额========================
    /**
     * 加工 统计报表-融资统计中间表rp_report_total
     * 
     * @param dateFlag
     */
    public void completeReportBeanTotal(String dateFlag) {
        Map<String, ReportBeanTotal> res = this.generateReportBeanTotal(dateFlag);
        this.saveReportBeanTotal(res);
    }

    /**
     * 获取-统计报表-融资统计
     * 
     * @param dateFlag
     * @return
     */
    public Map<String, ReportBeanTotal> generateReportBeanTotal(String dateFlag) {
        Date now = new Date();
        Map<String, ReportBeanTotal> res = new HashMap<String, ReportBeanTotal>();
        Date start = null, end = null;
        DicWord dw = DicWord.getDicWord();
        Map<String, String> location = dw.getDicByType(Constants.KEY_DICTYPE_XZDQ);
        Date d;
        try {
            if (StringUtils.isNotEmpty(dateFlag)) {
                d = format.parse(dateFlag);
                start = getLastMonthStart(d);
                end = getLastMonthEnd(d);
            }
        } catch (ParseException e) {
            log.error("dataFlag parameter error!");
            return null;
        }
        Map<String, BigDecimal> demandAmount = this.finacingDemandInfoDao.sumAmountByArea(null, start, end, null);
        Map<String, BigDecimal> loanAmount = this.investorLoanDao.sumAmountByArea(null, start, end, null);
        Map<String, Long> companyCount = this.companyBaseDao.countByArea(null, null, null, null);
        Map<String, Long> InvestorCount = this.investorDao.countByArea(null, null, null, null);

        for (String key : location.keySet()) {
            ReportBeanTotal o = new ReportBeanTotal();
            o.setDemandAmount(demandAmount.get(key));
            o.setLoanAmount(loanAmount.get(key));
            o.setEnterpriseCount(companyCount.get(key));
            o.setOrgCount(InvestorCount.get(key));
            o.setTimeId(dateFlag);
            o.setArea(key);
            o.setCreateTime(now);
            res.put(key, o);
        }
        return res;
    }

    /**
     * 保存-统计报表-融资统计到 rp_report_total表
     * 
     * @param reportInfo
     */
    public void saveReportBeanTotal(Map<String, ReportBeanTotal> reportInfo) {
        this.reportBeanTotalDao.save(reportInfo.values());
    }

    // ==================================================區域排名========================
    /**
     * 数据落地-统计报表-区域排名中间表 rp_report_area
     *
     * @param dateFlag
     */
    public void completeReportBeanArea(String dateFlag) {
        List<ReportBeanArea> res = generateReportBeanArea1(dateFlag);
        this.saveReportBeanArea(res);
    }

    /**
     * 获取-统计报表-区域排名
     * 
     * @param dateFlag
     * @return
     */
    public List<ReportBeanArea> generateReportBeanArea1(String dateFlag) {
        Date end = null;
        Date d;
        try {
            d = format.parse(dateFlag);
            end = getLastMonthEnd(d);
        } catch (ParseException e) {
            log.error("dataFlag parameter error!");
            return null;
        }
        List<ReportBeanArea> res = this.realTimeReportDao.findAreaInvestByOrgId(null, null, end, null);
        Double allAmount = this.realTimeReportDao.sumInvestByOrgId(null, null, end);
        int rank = 0;
        for (ReportBeanArea rbo : res) {
            rbo.setAllAmount(allAmount);
            rbo.setTimeId(dateFlag);
            rbo.setTimePoint(end);
            rbo.setRank(rank++);
            rbo.setCreateTime(new Date());
        }
        Collections.sort(res);
        return res;
    }

    /**
     * 保存-统计报表-区域排名榜单到 rp_report_area表
     * 
     * @param o
     */
    public void saveReportBeanArea(List<ReportBeanArea> o) {
        this.reportBeanAreaDao.save(o);
    }

    // ==================================================行业排名========================
    /**
     * 数据落地-统计报表-行业排名中间表 rp_report_industry
     * 
     * @param dateFlag
     */
    public void completeReportBeanTrade(String dateFlag) {
        List<ReportBeanTrade> res = generateReportBeanTrade(dateFlag);
        this.saveReportBeanTrade(res);
    }

    /**
     * 获取-统计报表-行业排名
     * 
     * @param dateFlag
     * @return
     */
    public List<ReportBeanTrade> generateReportBeanTrade(String dateFlag) {
        Date end = null;
        Date d;
        try {
            d = format.parse(dateFlag);
            end = getLastMonthEnd(d);
        } catch (ParseException e) {
            log.error("dataFlag parameter error!");
            return null;
        }
        List<ReportBeanTrade> res = this.realTimeReportDao.findTradeInvestByOrgId(null, null, end, null);
        Double allAmount = this.realTimeReportDao.sumInvestByOrgId(null, null, end);
        int rank = 0;
        for (ReportBeanTrade rbo : res) {
            rbo.setAllAmount(allAmount);
            rbo.setTimeId(dateFlag);
            rbo.setTimePoint(end);
            rbo.setRank(rank++);
            rbo.setCreateTime(new Date());
        }
        Collections.sort(res);
        return res;
    }

    /**
     * 保存-统计报表-行业排名榜单到 rp_report_industry表
     * 
     * @param o
     */
    public void saveReportBeanTrade(List<ReportBeanTrade> o) {
        this.reportBeanTradeDao.save(o);
    }

    // ==================================================企业榜单========================
    /**
     * 加工-统计报表-企业榜单中间表 rp_report_company
     * 
     * @param dateFlag
     */
    public void completeReportBeanEnterprise(String dateFlag) {
        List<ReportBeanEnterprise> res = this.generateReportBeanEnterprise(dateFlag);
        this.saveReportBeanEnterprise(res);
    }

    /**
     * 获取-统计报表-企业榜单
     * 
     * @param dateFlag
     * @return
     */
    public List<ReportBeanEnterprise> generateReportBeanEnterprise(String dateFlag) {
        Date now = new Date();
        Date start = null, end = null;
        Date d;
        try {
            d = format.parse(dateFlag);
            start = getLastMonthStart(d);
            end = getLastMonthEnd(d);
        } catch (ParseException e) {
            log.error("dataFlag parameter error!");
            return null;
        }
        Map<String, BigDecimal> loanAmount = this.investorLoanDao.sumAmountByEnterpriseId(null, start, end, NUM_STATISTIC);
        List<ReportBeanEnterprise> res = new ArrayList<ReportBeanEnterprise>();
        long rankCount = 0;
        for (String key : loanAmount.keySet()) {
            ReportBeanEnterprise o = new ReportBeanEnterprise();
            o.setAmount(loanAmount.get(key));
            o.setEnterpriseId(key);
            o.setTimeId(dateFlag);
            o.setTimePoint(start);
            o.setCreateTime(now);
            o.setRankCount(rankCount++);
            res.add(o);
        }
        Collections.sort(res);
        return res;
    }

    /**
     * 保存-统计报表-企业榜单到 rp_report_company表
     * 
     * @param res
     */
    public void saveReportBeanEnterprise(List<ReportBeanEnterprise> res) {
        this.reportBeanEnterpriseDao.save(res);
    }

    // ==================================================机构榜单========================
    /**
     * 数据落地-统计报表-机构榜单 中间表rp_report_org
     * 
     * @param dateFlag
     */
    public void completeReportBeanOrg(String dateFlag) {
        List<ReportBeanOrg> res = this.generateReportBeanOrg(dateFlag);
        
        this.saveReportBeanOrg(res);
    }

    /**
     * 获取-统计报表-机构榜单
     * 
     * @param dateFlag
     * @return
     */
    public List<ReportBeanOrg> generateReportBeanOrg(String dateFlag) {
        Date now = new Date();
        Date start = null, end = null;
        Date d;
        try {
            d = format.parse(dateFlag);
            start = getLastMonthStart(d);
            end = getLastMonthEnd(d);
        } catch (ParseException e) {
            log.error("dataFlag parameter error!");
            return null;
        }
        List<ReportBeanOrgTmp> res = this.investorLoanDao.sumAmountByOrg(null, start, end, NUM_STATISTIC);
        long rankCount = 0;
        List <ReportBeanOrg>target = new ArrayList<ReportBeanOrg>();
        for (ReportBeanOrgTmp o: res) {
            System.out.println(o);
            ReportBeanOrg toBean = new ReportBeanOrg();
            BeanUtils.copyProperties(o, toBean);
            toBean.setTimeId(dateFlag);
            toBean.setTimePoint(start);
            toBean.setCreateTime(now);
            toBean.setRankCount(rankCount++);
            target.add(toBean);
        }
        return target;
    }
    /**
     * 获取-统计报表-机构榜单
     * 
     * @param dateFlag
     * @return
     */
    public List<ReportBeanOrg> generateReportBeanOrgBak(String dateFlag) {
        Date now = new Date();
        Date start = null, end = null;
        Date d;
        try {
            d = format.parse(dateFlag);
            start = getLastMonthStart(d);
            end = getLastMonthEnd(d);
        } catch (ParseException e) {
            log.error("dataFlag parameter error!");
            return null;
        }
        Map<String, BigDecimal> loanAmount = this.investorLoanDao.sumAmountByOrgId(null, start, end, NUM_STATISTIC);
        List<ReportBeanOrg> res = new ArrayList<ReportBeanOrg>();
        long rankCount = 0;
        for (String key : loanAmount.keySet()) {
            ReportBeanOrg o = new ReportBeanOrg();
            o.setAmount(loanAmount.get(key));
            o.setOrgId(key);
            o.setTimeId(dateFlag);
            o.setTimePoint(start);
            o.setCreateTime(now);
            o.setRankCount(rankCount++);
            res.add(o);
        }
//        Collections.sort(res);
        return res;
    }

    /**
     * 保存-统计报表-机构榜单到 rp_report_org表
     * 
     * @param res
     */
    public void saveReportBeanOrg(List<ReportBeanOrg> res) {
        this.reportBeanOrgDao.save(res);
    }

    private Date getLastMonthStart(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    private Date getLastMonthEnd(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        c.set(Calendar.HOUR, c.getActualMaximum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
        return c.getTime();
    }

}
