package com.icfcc.ssrp.web.managedept;

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
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorPublishInfoResult;
import com.icfcc.SRRPService.inverstorg.InvestorPublishInfoService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.ssrp.web.SRRPBaseController;

/**
 * 
 * @ClassName: InvestorPublishManageController
 * @Description: TODO(批露信息查询功能模块)
 * @author hugt
 * @date 2017年10月18日 上午9:12:15
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/publishManage")
public class InvestorPublishManageController extends SRRPBaseController {
	private static Logger log = LoggerFactory
			.getLogger(InvestorPublishManageController.class);

	// 批露信息查询
	@Autowired
	private InvestorPublishInfoService publishService;

	/**
	 * 
	 * @Title: controllerTest
	 * @Description: TODO(查询页面)
	 * @param @param request
	 * @param @param response
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/publishInit")
	public String controllerTest(HttpServletRequest request,
			HttpServletResponse response) {
		return "WEB-INF/views/managedept/investorPublish";
	}

	/**
	 * 
	 * @Title: initInfo
	 * @Description: TODO(批露信息查询)
	 * @param @param model
	 * @param @param page
	 * @param @param request
	 * @param @param response 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/initInfo")
	public void initInfo(Model model, PageBean page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryConditionString = request
					.getParameter("queryCondition");// 查询条件
			String currentPage = request.getParameter("start");// 当前页
			String maxSize = request.getParameter("limit");// 每页显示的条数
			// 查询条件对象需要传到Service,进行sql拼装
			QueryCondition queryCondition = new QueryCondition();
			
			if (queryConditionString != null
					&& !"".equals(queryConditionString)
					&& !"\"\"".equals(queryConditionString)) {
				queryCondition = (QueryCondition) JSON.parseObject(
						queryConditionString, QueryCondition.class);
				System.out.println(queryCondition.getFinancingphase());
			}
			if (StringUtils.isNotBlank(currentPage)) {
				queryCondition.setCurPage(Integer.parseInt(currentPage));
			}
			if (StringUtils.isNotBlank(maxSize)) {
				queryCondition.setMaxSize(Integer.parseInt(maxSize));
			}
			// 查询批露信息数据
			List<?> dataList = publishService
					.getInvestorPublishLists(queryCondition);
			page.setList(dataList);
			if (CollectionUtils.isNotEmpty(dataList)) {

				page.setList(dataList);
				// 设置总的条数
				Integer total = new Integer(String.valueOf(publishService
						.getInvestorPublishListCount(queryCondition)));
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
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @Title: viewDetail
	 * @Description: TODO(查看批露信息详情)
	 * @param @param request
	 * @param @param response
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/publishDetails")
	public String publishDetails(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String publishId = request.getParameter("publishId");// 批露id
			InvestorPublishInfoResult publish = publishService
					.getInvestorPublishInfoById(publishId);// 根据id查询批露信息
			request.setAttribute("publish", publish);
			return "WEB-INF/views/managedept/publishDetails";
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
