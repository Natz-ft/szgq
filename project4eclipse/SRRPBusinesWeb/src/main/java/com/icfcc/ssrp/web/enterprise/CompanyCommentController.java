package com.icfcc.ssrp.web.enterprise;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyComment;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyCommentService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.enterprise.FinacingEventService;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/companyComment")
public class CompanyCommentController extends SRRPBaseController {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory
			.getLogger(CompanyCommentController.class);

	@Autowired
	private CompanyCommentService commentService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private FinacingEventService finacingEventService;
	@Autowired
	private PlatformUserService serService;

	/**
	 * 初始化评价信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/initCommentHtml")
	public String controllerTest(HttpServletRequest request,
			HttpServletResponse response) {
		String eventId = request.getParameter("eventId");
		String infoId = request.getParameter("infoId");
		request.setAttribute("eventId", eventId);
		request.setAttribute("infoId", infoId);
		return "WEB-INF/views/enterprise/CompanyComment";
	}

	/**
	 * 添加评价信息
	 * 
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping(value = "/addCompanyComment")
	public String addCompanyComment(HttpServletRequest request,
			HttpServletResponse respons) {
		// 得到页面的传参
		String eventId = request.getParameter("eventId");
		try {
			// 构造评价信息对象
			CompanyComment companyComment = new CompanyComment();
			// 获取页面参数
			Double start = Double.parseDouble(request.getParameter("stars"));
			companyComment.setStars(start);
			companyComment.setEvacontent(request.getParameter("evacontent"));
			companyComment.setOperDate(new Date());
			companyComment.setEventId(eventId);
			// 获取当前登陆用户的id
			String userId = RedisGetValue.getRedisUser(request, "userId");
			companyComment.setOperUser(userId);
			commentService.addCompanyComment(companyComment);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return "WEB-INF/views/enterprise/MyFinacingEvent";
	}
}
