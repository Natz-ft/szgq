package com.icfcc.SRRPService.gataway.staticize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayEventQueryInvestor;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayFinacingEventQuery;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorAuditPending;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorRegiter;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayDemand;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayIndexStatic;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayLinks;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayNews;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayPartner;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditPending;
import com.icfcc.SRRPDao.jpa.entity.managedept.InvestorHistory;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformFaq;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformFaqShow;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformNews;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBaseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorAuditPendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.GataWayInvestorAuditRegisterDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.GataWayInvestorRegisterDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayNewsDao;
import com.icfcc.SRRPDao.jpa.repository.managedept.InvestorHistoryDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformUserDao;
import com.icfcc.SRRPService.gataway.GataWayFinacingEventQueryService;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

@Component
@Transactional(value = "transactionManager")
public class GataWayIndexService extends GataWayBaseStaticzeService {

	// 门户统计数据
	@Autowired
	private GataWayService gataWayService;
	// 新闻动态-新闻-轮播
    @Autowired
    private GataWayNewsDao newsDao;
    @Autowired
    private GataWayDao dao;
	// 融资需求
	@Autowired
	private GataWayDemandService gataWayDemandService;
	// 门户新闻
	@Autowired
	private GataWayNewsService gataWayNewsService;
	// 投资动态
	@Autowired
	private GataWayFinacingEventQueryService gataWayFinacingEventQueryService;
	// 门户合作伙伴
	@Autowired
	private GataWayPartnerService gataWayPartnerService;
	// 门户友情链接
	@Autowired
	private GataWayLinksService gataWayLinksService;
	// 融资需求详细
	@Autowired
	private GataWayDemandDetailService gataWayDemandDetailService;
	// 投资机构注册正式表
	@Autowired
	private GataWayInvestorRegisterDao gataWayInvestorRegisterDao;
	// 投资机构注册待审核表
	@Autowired
	private GataWayInvestorAuditRegisterDao gataWayInvestorAuditRegisterDao;

	// 投资机构注册业务库正式表
	@Autowired
	private InvestorDao investorDao;
	// 投资机构注册业务库待审核表
	@Autowired
	private InvestorAuditPendingDao investorAuditPendingDao;
	  @Autowired
	    public CompanyBaseDao compdao;
	  @Autowired
		private PlatformUserDao userDao;

	// 投资机构注册业务库待审核表
	@Autowired
	private InvestorHistoryDao investorHistoryDao;

	// 融资需求模版
	@Value("${index.demandModelFile}")
	private String demandModelFile;
	// 融资输出路径
	@Value("${index.demandOutFile}")
	private String demandOutFile;

	// 首页模版
	@Value("${index.mainModelFile}")
	private String mainModelFile;
	// 首页输出路径
	@Value("${index.mainOutFile}")
	private String mainOutFile;

	// 首页新闻模版
	@Value("${index.newsModelFile}")
	private String newsModelFile;
	// 首页新闻输出
	@Value("${index.newsOutFile}")
	private String newsOutFile;

	// 首页投资动态详情模版
	@Value("${index.inverstorModelFile}")
	private String inverstorModelFile;
	// 首页投资动态详情输出
	@Value("${index.inverstorOutFile}")
	private String inverstorOutFile;

	@Value("${filePath}")
	private String filePath;
	
	public void makeIndexHtml() throws Exception {
		// 首页对象
		Map<String, Object> data = new HashMap<String, Object>();
		//新闻轮播图
        List<PlatformNews> dataList = newsDao.findNewsListLunbo();
        if (dataList != null && dataList.size() > 0) {
        	for (PlatformNews news : dataList) {
        		
        		String tmpContents = news.getContent4GBK();
        		if (tmpContents != null) {
                	if (tmpContents != null && !"".equals(tmpContents)) {
                		String content =  tmpContents.replace(StringUtils.substringBefore(tmpContents,"&&")+"&&", "");
                		String s = content.replaceAll("<[^>]+>", "").replaceAll("&nbsp;", "").replaceAll(" ", "");
						news.setNewsSummaryNews(s.substring(0, Math.min(70, s.length()))+"...");
                	}
                }
        		
        		Map<String, Object> ndata = new HashMap<String, Object>();
        		GataWayNews gataWayNews = new GataWayNews();
        		gataWayNews.setNewsId(String.valueOf(news.getId()));
        		gataWayNews.setNewsTile(news.getTitle());
        		//gataWayNews.setNewsContents(news.getContent());
        		gataWayNews.setNewEditor(news.getNewsEditor());
    			gataWayNews.setNewSource(news.getNewsSource());
    			gataWayNews.setNewsPicture(news.getPhoto());
    			gataWayNews.setNewsCreateUser(news.getCreateUser());
    			gataWayNews.setUplodfileName(news.getUplodfileName());
    			gataWayNews.setPurpose(news.getNewstype());
    			gataWayNews.setNewsDate(news.getNewsDate());
    			gataWayNews.setNewsContents(StringUtils.substringAfter(tmpContents, "&&"));
    			ndata.put("gataWayNews", gataWayNews);
				// freemaker制作html
				String outFile = newsOutFile + "idx" + gataWayNews.getNewsId() + ".html";
				this.makeStaticzeHtml(ndata, newsModelFile, outFile);
        		
			}
            data.put("platformNewsList", dataList);
        }
        
		// 首页制作
		String queryDate = null;
		// 门户统计数值
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		cal.add(Calendar.MONTH, 0);
		queryDate = sdf.format(cal.getTime());
		GataWayIndexStatic gataWayStatic = gataWayService
				.getIndexStaticResultByQueryDate(queryDate);
		GataWayIndexStatic gataWayStaticCopy = new GataWayIndexStatic();
		BeanUtils.copyProperties(gataWayStaticCopy,gataWayStatic);
		data.put("gataWayStatic", gataWayStaticCopy);
		
		// 融资需求
		List<GataWayDemand> listGataDemands = gataWayDemandService.findGataWayDemand();
		if (listGataDemands.size() == 0) {
			GataWayDemand g1 = new GataWayDemand();
			GataWayDemand g2 = new GataWayDemand();
			GataWayDemand g3 = new GataWayDemand();
			GataWayDemand g4 = new GataWayDemand();
			GataWayDemand g5 = new GataWayDemand();
			listGataDemands.add(g1);
			listGataDemands.add(g2);
			listGataDemands.add(g3);
			listGataDemands.add(g4);
			listGataDemands.add(g5);

		} else if (listGataDemands.size() == 1) {
			GataWayDemand g2 = new GataWayDemand();
			GataWayDemand g3 = new GataWayDemand();
			GataWayDemand g4 = new GataWayDemand();
			GataWayDemand g5 = new GataWayDemand();
			listGataDemands.add(g2);
			listGataDemands.add(g3);
			listGataDemands.add(g4);
			listGataDemands.add(g5);
		} else if (listGataDemands.size() == 2) {
			GataWayDemand g2 = new GataWayDemand();
			GataWayDemand g3 = new GataWayDemand();
			GataWayDemand g4 = new GataWayDemand();
			listGataDemands.add(g2);
			listGataDemands.add(g3);
			listGataDemands.add(g4);
		} else if (listGataDemands.size() == 3) {
			GataWayDemand g2 = new GataWayDemand();
			GataWayDemand g3 = new GataWayDemand();
			listGataDemands.add(g2);
			listGataDemands.add(g3);
		} else if (listGataDemands.size() == 4) {
			GataWayDemand g2 = new GataWayDemand();
			listGataDemands.add(g2);
		}
		data.put("listGataDemands", listGataDemands);
		// 新闻动态
		List<GataWayNews> listGataWayNews = gataWayNewsService
				.findGataWayNews("portal.index");
		if (listGataWayNews != null && listGataWayNews.size() > 0) {
			for (GataWayNews news : listGataWayNews) {
				String tmpTile = news.getNewsTile();
				String tmpContents = news.getNewsContents();
				// 门户新闻
				// 标题
				if (tmpTile != null) {
					if (tmpTile.length() < 28) {
						news.setNewTilePortal(tmpTile);
					} else {
						news.setNewTilePortal(tmpTile.substring(0, 25) + "...");
					}
				}
				// 内容概要
				if (tmpContents != null && !"".equals(tmpContents)) {
					news.setNewSummaryPortal(StringUtils.substringBefore(
							tmpContents, "&&"));
				}

			}
		}
		data.put("listGataWayNews", listGataWayNews);
		// 投资动态
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setPortal(true);
		// List<GataWayInvestorInfo> listGWInvestorInfo =
		// gataWayInvestorInfoService.findGataWayInvestorInfo();
		List<GataWayFinacingEventQuery> listGWInvestorInfo = gataWayFinacingEventQueryService
				.findFinacingEventQuery(queryCondition);
		List<GataWayFinacingEventQuery> showList = new ArrayList<GataWayFinacingEventQuery>();
		if (listGWInvestorInfo != null && listGWInvestorInfo.size() > 0) {
			for (GataWayFinacingEventQuery infos : listGWInvestorInfo) {
				// 字典转换
				if (StringUtils.isNotEmpty(infos.getIndustry())) {
					String industryStr = infos.getIndustry();// 获取数据库中行业的展示
					if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
						infos.setIndustryName(RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2, infos.getIndustry()));
					} else {// 如果是一级的行业显示以及行业
						infos.setIndustryName(RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY, infos.getIndustry()));
					}
				}
				// 加密
				// if (logonMap.isEmpty()) {
				// 游客模式
				String enterpriseName = "0".equals(infos.getOpen())? infos.getEnterpriseName():"某公司";
				String tmp = infos.getIndustryName() + "行业" + enterpriseName + "获得投资" 
						+ String.valueOf((int)(Double.valueOf(infos.getAmount())*100)) + "万元";
				infos.setShowInfosA(tmp);
				if (tmp.length() < 28) {
					infos.setShowInfos(tmp);
				} else {
					infos.setShowInfos((tmp).substring(0, 25) + "...");
				}
				// } else {
				// // TODO登录模式待补充
				//
				// }
				infos.setAwayFromNow(daysAawyFromNow(infos.getOperDate()));
				showList.add(infos);
			}
		}
		data.put("listGWInvestorInfo", showList);
		// 合作伙伴
		List<GataWayPartner> listGataWayPartners = gataWayPartnerService
				.findGataWayPartners();
		data.put("listGataWayPartners", listGataWayPartners);
		// 友情链接
		List<GataWayLinks> listGataWayLinks = gataWayLinksService
				.findGataWayLinks();
		data.put("listGataWayLinks", listGataWayLinks);
		// freemaker制作html
		this.makeStaticzeHtml(data, mainModelFile, mainOutFile);
	}

	public void makeFinaDemandDetail() throws Exception {
		// 融资需求详细
		List<GataWayDemand> listGataDemands = gataWayDemandService
				.findGataWayDemand();
		if (listGataDemands != null && listGataDemands.size() > 0) {
			for (GataWayDemand gataWayDemandDetail : listGataDemands) {
				// 详情对象
				Map<String, Object> data = new HashMap<String, Object>();
				gataWayDemandDetail.setRelName(chineseName(gataWayDemandDetail.getRelName()));
				gataWayDemandDetail.setRelPhone(mobilePhone(gataWayDemandDetail.getRelPhone()));
				gataWayDemandDetail.setAreaName(RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, gataWayDemandDetail.getRearea()));
				data.put("gataWayDemandDetail", gataWayDemandDetail);
				// freemaker制作html
				String outFile = demandOutFile
						+ gataWayDemandDetail.getInfoId() + ".html";
				this.makeStaticzeHtml(data, demandModelFile, outFile);
			}
		}

	}
	public static String chineseName(String fullName) {
		if (StringUtils.isBlank(fullName)) {
			return "";
		}
		String name = StringUtils.left(fullName, 1);
		return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
	}
	public static String mobilePhone(String num) {
		if (StringUtils.isBlank(num)) {
			return "";
		}
		return StringUtils.left(num, 3).concat(
				StringUtils.removeStart(
						StringUtils.leftPad(StringUtils.right(num, 4),
								StringUtils.length(num), "*"), "***"));
	}
	public void makeNewsDetail() throws Exception {
		// 新闻详细
		List<GataWayNews> listGataWayNews = gataWayNewsService
				.findGataWayNews("portal.index");
		if (listGataWayNews != null && listGataWayNews.size() > 0) {
			for (GataWayNews gataWayNews : listGataWayNews) {
				// 详情对象
				Map<String, Object> data = new HashMap<String, Object>();
				gataWayNews.setNewsContents(StringUtils.substringAfter(
						gataWayNews.getNewsContents(), "&&"));
				data.put("gataWayNews", gataWayNews);
				// freemaker制作html
				String outFile = newsOutFile + "idx" + gataWayNews.getNewsId()
						+ ".html";
				this.makeStaticzeHtml(data, newsModelFile, outFile);
			}
		}

	}

	// 首页-投资动态明细
	public void makeInvestorDetail() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		// 投资动态
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setPortal(true);
		List<GataWayFinacingEventQuery> listGWInvestorInfo = gataWayFinacingEventQueryService
				.findFinacingEventQuery(queryCondition);
		if (listGWInvestorInfo != null && listGWInvestorInfo.size() > 0) {
			for (GataWayFinacingEventQuery infos : listGWInvestorInfo) {
				// 字典转换
				infos.setIndustryName(RedisGetValue.getValueByCode(
						SRRPConstant.DD_INDUSTRY, infos.getIndustry()));
				List<GataWayEventQueryInvestor> investorInfoList = gataWayFinacingEventQueryService
						.findInvestorByEventId(infos.getEventId());
				// 加密
				// if (logonMap.isEmpty()) {
				for (GataWayEventQueryInvestor invest : investorInfoList) {
					invest.setInvestorNameStr(invest.getInvestorName());
				}
				if (!SRRPConstant.ISOPEN.equals(infos.getOpen())) {
					// 非公开项目
					infos.setProjectName(SRRPConstant.DEFALUTDEMAND);
					infos.setInvestorName(SRRPConstant.DEFALUTINVERSTOR);
					infos.setEnterpriseName(SRRPConstant.DEFALUTCOMPANY);
					
					for (GataWayEventQueryInvestor invest : investorInfoList) {
						invest.setInvestorNameStr(SRRPConstant.DEFALUTINVERSTOR);
					}

				}
				// } else {
				// // TODO登录模式待补充
				//
				// }
				// 字典转换
				infos.setIndustryName(RedisGetValue.getValueByCode(
						SRRPConstant.DD_INDUSTRY, infos.getIndustry()));
				infos.setFinacingTurnName(RedisGetValue.getValueByCode(
						SRRPConstant.DD_FINACINGTURN, infos.getFinacingTurn()));
				infos.setAreaName(RedisGetValue.getValueByCode(
						SRRPConstant.DD_AREA, infos.getReArea()));
				data.put("finacingEventQuery", infos);
				data.put("investorInfoList", investorInfoList);
				// freemaker制作html
				String outFile = inverstorOutFile + infos.getEventId()
						+ ".html";
				this.makeStaticzeHtml(data, inverstorModelFile, outFile);
			}
		}
	}

	// 判定是否注册过
	public boolean ifRegistered(String certType, String certNo) {
		boolean isExist = false;
		GataWayInvestorRegiter registerInfo = gataWayInvestorRegisterDao
				.findById(certNo);
		if (registerInfo != null) {
			isExist = true;
		}
		return isExist;
	}

	// 获取注册历史信息
	public InvestorHistory findRegisterHisInfos(String certNo) {
		return investorHistoryDao.findByCertNo(certNo);
	}

	// 判定是否注册过业务库
	public boolean ifRegisteredRp(String certNo) {
		boolean isExist = false;
		CompanyBase baseInfo = compdao.queryByCertno(certNo);
		Investor registerInfo = investorDao.findById(certNo);
		PlatformUser user=userDao.findByUserName(certNo);
		if (registerInfo != null || baseInfo!=null || user!=null) {
			isExist = true;
		}
		return isExist;
	}
    
	// 查询机构用户
	public Investor findInvestor(String certNo) {
		Investor registerInfo = investorDao.findById(certNo);
		return registerInfo;
	}
	
	// 投资机构注册正是表
	public void inverstorRegister(GataWayInvestorRegiter registerInfo)
			throws Exception, InvocationTargetException {
		gataWayInvestorRegisterDao.save(registerInfo);
		GataWayInvestorAuditPending auditInfos = new GataWayInvestorAuditPending();
		BeanUtils.copyProperties(auditInfos, registerInfo);
		auditInfos.setStopFlag("1");// 默认启用
		inverstorAuditRegister(auditInfos);
	}

	// 投资机构注册正是表-业务库
	public void inverstorRegisterSrrp(Investor registerInfo) throws Exception,
			InvocationTargetException {
		investorDao.save(registerInfo);
		InvestorAuditPending auditInfos = new InvestorAuditPending();
		BeanUtilsBean.getInstance().getConvertUtils()
				.register(new SqlDateConverter(null), Date.class);
		BeanUtils.copyProperties(auditInfos, registerInfo);
		investorAuditPendingDao.save(auditInfos);
	}

	// 投资机构注册正是表
	public void inverstorAuditRegister(GataWayInvestorAuditPending registerInfo) {
		gataWayInvestorAuditRegisterDao.save(registerInfo);
	}

	// 图片上传
	public Map<String, String> uploadPic(CommonsMultipartFile file,
			String fileType, String imgPath) {
		Map<String, String> map = new HashMap<String, String>();
		FileOutputStream os = null;
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		// 文件全称包含文件格式
		String fileFullName = "";
		// 文件后缀
		String fileSuffix = "";
		// 文件保存名称
		String fileNameSave = "";
		try {
			if (!file.isEmpty()) {
				// 定义输出流 将文件保存在D盘下的dataImage file.getOriginalFilename()为获得文件的名字

				String baseUrl = filePath;// "D:/workspace/SRRPBusinesWeb/src/main/webapp/";
				String url = "static/files/";
				switch (fileType) {
				case "yyzz":// 门户营业执照
					url += "yyzz/";
					break;
				case "syjh":// 商业计划书
					url += "syjh/";
					break;
				case "xgfj":// 企业相关附件
					url += "xgfj/";
					break;
				case "jglg":// 机构logo
					url += "jglg/";
					break;
				case "zmcl":// 证明材料
					url += "zmcl/";
					break;
				case "zxsqs":// 征信授权书
					url += "zxsqs/";
					break;
				case "bsyx":// 背书映像
					url += "bsyx/";
					break;
				case "plcl":// 披露材料
					url += "plcl/";
					break;
				default:
					url += "yyzz/";
					break;
				}
				File files = new File(baseUrl + url);
				if (!files.exists()) {
					files.mkdirs(); // 创建文件
				}
				fileFullName = file.getOriginalFilename();
				fileSuffix = fileFullName.substring(fileFullName
						.lastIndexOf("."));
				fileNameSave = fileType + "_"
						+ UUID.randomUUID().toString().replaceAll("-", "")
						+ fileSuffix;
				String path = files.getPath() + File.separator + fileNameSave;
				os = new FileOutputStream(path);
				InputStream in = file.getInputStream();
				int b = 0;
				while ((b = in.read()) != -1) { // 读取文件
					os.write(b);
				}
				map.put("code", "0");
				map.put("url", path);
				map.put("name", fileFullName);
			} else {
				map.put("code", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "1");
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return map;
	}

	public Map<String, String> uploadPic(CommonsMultipartFile file,
			String fileType) {
		Map<String, String> map = new HashMap<String, String>();
		FileOutputStream os = null;
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		// 文件全称包含文件格式
		String fileFullName = "";
		// 文件后缀
		String fileSuffix = "";
		// 文件保存名称
		String fileNameSave = "";
		try {
			if (!file.isEmpty()) {
				// 定义输出流 将文件保存在D盘下的dataImage file.getOriginalFilename()为获得文件的名字

				String baseUrl = filePath;// "D:/workspace/SRRPBusinesWeb/src/main/webapp/";
				String url = "static/files/";
				switch (fileType) {
				case "yyzz":// 门户营业执照
					url += "yyzz/";
					break;
				case "syjh":// 商业计划书
					url += "syjh/";
					break;
				case "xgfj":// 企业相关附件
					url += "xgfj/";
					break;
				default:
					url += "yyzz/";
					break;
				}
				File files = new File(baseUrl + url);
				if (!files.exists()) {
					files.mkdirs(); // 创建文件
				}
				fileFullName = file.getOriginalFilename();
				fileSuffix = fileFullName.substring(fileFullName
						.lastIndexOf("."));
				fileNameSave = fileType + "_"
						+ UUID.randomUUID().toString().replaceAll("-", "")
						+ fileSuffix;
				String path = files.getPath() + File.separator + fileNameSave;
				os = new FileOutputStream(path);
				InputStream in = file.getInputStream();
				int b = 0;
				while ((b = in.read()) != -1) { // 读取文件
					os.write(b);
				}
				map.put("code", "0");
				map.put("url", "../" + url + fileNameSave);
				map.put("name", fileFullName);
			} else {
				map.put("code", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "1");
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return map;
	}

	private String daysAawyFromNow(String fromDate) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date to = df.parse(fromDate);
		long diff = new Date().getTime() - to.getTime();// 这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24) + 1;
		return days + "天前";
	}
	
	/**
	 * 修改任务状态:重置任务
	 */
	public void changeJobStatus(String status) {
		dao.changeJobStatus(status);
	}
	
	/**
	 * 修改任务状态:重置任务
	 */
	public void changeEnterpriseCount(int count) {
		dao.changeEnterpriseCount(count);
	}
	
	public PlatformFaqShow findFaqDy() {
		return gataWayService.findFaqDy();
	}
	
}
