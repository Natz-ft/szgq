package com.icfcc.ssrp.web.enterprise;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachment;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyMember;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjection;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyProduct;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholder;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyUnionInfoResult;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.someLog.SomeLogService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.ssrp.web.SRRPBaseController;
import com.icfcc.ssrp.web.inverstorg.DesensitizationUtil;

@Slf4j
@Controller
@RequestMapping(value = "/companyInfo")
public class CompanyInfoQureyController extends SRRPBaseController {
	private static Logger log = LoggerFactory
			.getLogger(CompanyInfoQureyController.class);

	// 企业信息查询
	@Autowired
	private CompanyInfoService infoService;

	@RequestMapping(value = "/companyController")
	public String companyInfoController(HttpServletRequest request,
			HttpServletResponse response) {
		return "WEB-INF/views/inverstorg/companyInfoQuery";
	}

	@RequestMapping(value = "/initInfo")
	public void userList(Model model, PageBean page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryConditionString = request
					.getParameter("queryCondition");
			String currentPage = request.getParameter("start");
			String maxSize = request.getParameter("limit");
			// 查詢條件對象需要传到Service,进行sql拼装
			QueryCondition queryCondition = new QueryCondition();
			if (queryConditionString != null
					&& !"".equals(queryConditionString)
					&& !"\"\"".equals(queryConditionString)) {
				queryCondition = (QueryCondition) JSON.parseObject(
						queryConditionString, QueryCondition.class);
			}
			if (StringUtils.isNotBlank(currentPage)) {
				queryCondition.setCurPage(Integer.parseInt(currentPage));
			}
			if (StringUtils.isNotBlank(maxSize)) {
				queryCondition.setMaxSize(Integer.parseInt(maxSize));
			}
			List<CompanyUnionInfoResult> dataList = infoService
					.getCompanyList(queryCondition);
			for (CompanyUnionInfoResult companyUnionInfoResult : dataList) {
				if (StringUtils
						.isNotEmpty(companyUnionInfoResult.getIndustry())) {
					String industryStr = companyUnionInfoResult.getIndustry();// 获取数据库中行业的展示
					if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
						companyUnionInfoResult
								.setIndustry2(companyUnionInfoResult
										.getIndustry());
						companyUnionInfoResult
								.setIndustryStr(companyUnionInfoResult
										.getIndustry2Dicname());
					} else {// 如果是一级的行业显示以及行业
						companyUnionInfoResult
								.setIndustry(companyUnionInfoResult
										.getIndustry());
						companyUnionInfoResult
								.setIndustryStr(companyUnionInfoResult
										.getIndustryDicname());
					}
				}
				companyUnionInfoResult
						.setEnterpriseNameShow(DesensitizationUtil
								.enterpriseName(companyUnionInfoResult
										.getName()));
				companyUnionInfoResult.setCodeShow(DesensitizationUtil
						.changeAll(companyUnionInfoResult.getCode()));
			}
			page.setList(dataList);
			if (CollectionUtils.isNotEmpty(dataList)) {
				page.setList(dataList);
				// 设置总的条数
				Integer total = new Integer(String.valueOf(infoService
						.getCompanyCount(queryCondition)));
				page.setRecordCnt(total);
				if (StringUtils.isNotBlank(maxSize)) {
					page.setMaxSize(Integer.parseInt(maxSize));
				}
				if (StringUtils.isNotBlank(currentPage)) {
					page.setCurPage(Integer.parseInt(currentPage));
				}
				page.pageResult(dataList, page.getRecordCnt(),
						page.getMaxSize(), page.getCurPage());
			}
			// 将数据传输到前端
			this.writeJson(page, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}

	}

	@RequestMapping(value = "/initInfoTmp")
	public void initInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String queryCondition = request.getParameter("queryCondition");
			QueryCondition condition = null;
			if (queryCondition != null && !"".equals(queryCondition)
					&& !"\"\"".equals(queryCondition)) {
				condition = (QueryCondition) JSON.parseObject(queryCondition,
						QueryCondition.class);
			}
			List<?> dataList = infoService.getCompanyList(condition);
			this.writeJson(dataList, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/companyInfoDetails")
	public String viewDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String enterpriseId = request.getParameter("enterpriseId");
			String flag = request.getParameter("flag");
			String showFlag = request.getParameter("showFlag");
			CompanyBase companyBase = infoService.getCompanyBase(enterpriseId);
			CompanyBaseSupplement companyBaseSupplement = infoService
					.getCompanyBaseSupplement(enterpriseId);
			List<CompanyProduct> companyProducts = infoService
					.getCompanyProducts(enterpriseId);
			List<CompanyStockholder> companyStockholders = infoService
					.getCompanyStockholders(enterpriseId);
			List<CompanyAttachment> companyAttachments = infoService
					.getCompanyAttachments(enterpriseId);
			//查询工商异议信息
			List<CompanyObjection> objections=infoService.getObjectionList(enterpriseId);
			List<CompanyMember> companyMembers = infoService
					.findCompanyMemberByEventId(enterpriseId);
			if (companyBaseSupplement != null) {
				if (StringUtils.isNotEmpty(companyBaseSupplement.getIndustry())) {
					String industryStr = companyBaseSupplement.getIndustry();// 获取数据库中行业的展示
					if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
						companyBaseSupplement
								.setIndustry2(companyBaseSupplement
										.getIndustry());
						companyBaseSupplement
								.setIndustryStr(companyBaseSupplement
										.getIndustry2Dicname());
					} else {// 如果是一级的行业显示以及行业
						companyBaseSupplement.setIndustry(companyBaseSupplement
								.getIndustry());
						companyBaseSupplement
								.setIndustryStr(companyBaseSupplement
										.getIndustryDicname());
					}
				}
			}else{
				companyBaseSupplement=new CompanyBaseSupplement();
			}

			if (!"".equals(flag) || !"undefine".equals(flag)) {// 是否脱敏的标识
				if ("true".equals(flag)) {
					//工商异议信息进行脱敏
					for (CompanyObjection companyObjection : objections) {
						String objection=companyObjection.getObjection();
						objection=DesensitizationUtil.changeAllObjection(objection);
						companyObjection.setObjection(objection);
						companyObjection.setShowFlag("0");
					}
					companyBase.setEnterpriseNameShow(DesensitizationUtil
							.enterpriseName(companyBase.getName()));
//					companyBase.setName(DesensitizationUtil
//							.enterpriseName(companyBase.getName()));// 企业名称进行脱敏
					companyBase.setLegalCal(DesensitizationUtil
							.mobilePhone(companyBase.getLegalCal()));// 证件代码进行脱敏
					companyBase.setLegalName(DesensitizationUtil
							.chineseName(companyBase.getLegalName()));// 法定代表联系方式进行脱敏
					companyBase.setStockCal(DesensitizationUtil
							.mobilePhone(companyBase.getStockCal()));// 证件代码进行脱敏
					companyBase.setStockName(DesensitizationUtil
							.chineseName(companyBase.getStockName()));// 法定代表联系方式进行脱敏
					companyBase.setCodeShow(DesensitizationUtil
							.changeAll(companyBase.getCode()));// 股权联系人联系方式进行脱敏
					companyBase.setRegistArea(DesensitizationUtil
							.address(companyBase.getRegistArea()));// 注册地址的脱敏
					//工商数据信息的脱敏
					//股东信息的脱敏
					for (CompanyStockholder companyStockholder : companyStockholders) {
						if (companyStockholder.getHolderName().length() < 5) {
							companyStockholder
									.setHolderName(DesensitizationUtil
											.chineseName(companyStockholder
													.getHolderName()));
						} else {
							companyStockholder
									.setHolderName(DesensitizationUtil
											.enterpriseName(companyStockholder
													.getHolderName()));
						}
					}
					//团队成员的脱敏
					for (CompanyMember companyMember : companyMembers) {
						companyMember.setIntroduction(DesensitizationUtil
								.changeSome(companyMember.getIntroduction(),
										companyMember.getMemberName()));
						companyMember.setMemberName(DesensitizationUtil
								.chineseName(companyMember.getMemberName()));
					}
				}else{
					for (CompanyObjection companyObjection : objections) {
						companyObjection.setShowFlag("1");
					}
					companyBase.setEnterpriseNameShow(companyBase.getName());
					companyBase.setCodeShow(companyBase.getCode());// 股权联系人联系方式进行脱敏
				}
			}
			if(org.springframework.util.StringUtils.isEmpty(showFlag)){
			    showFlag="0";
			}
			request.setAttribute("showFlag",showFlag);
			request.setAttribute("companyBase", companyBase);
			if (companyBaseSupplement != null) {
				request.setAttribute("companyBaseSupplement",
						companyBaseSupplement);
			}
			request.setAttribute("companyProducts",
					net.sf.json.JSONArray.fromObject(companyProducts));
			//向前台传工商异议数据
			request.setAttribute("objections",
					net.sf.json.JSONArray.fromObject(objections));
			request.setAttribute("flag",flag);
			request.setAttribute("companyMembers",
					net.sf.json.JSONArray.fromObject(companyMembers));
			request.setAttribute("companyStockholders",
					net.sf.json.JSONArray.fromObject(companyStockholders));
			request.setAttribute("companyAttachments",
					net.sf.json.JSONArray.fromObject(companyAttachments));
			return "WEB-INF/views/inverstorg/companyDetails";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
