package com.icfcc.ssrp.web.managedept;

import com.icfcc.SRRPDao.jpa.entity.managedept.CountLoginInfo;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserLoginStatistics;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.managedept.CountLoginService;
import com.icfcc.ssrp.web.SRRPBaseController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName: CountFinanceController
 * @Description: TODO(主管机构-统计报表-融资统计)
 * @author loudw
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/countLogin")
public class CountFinanceLoginController extends SRRPBaseController{
	private static Logger log = LoggerFactory.getLogger(CountFinanceLoginController.class);

	@Autowired
	private CountLoginService service;
	@Autowired
	private PlatformUserService userService;


	/**
	 *
	 */
	@RequestMapping(value = "/statisticsInitInfo")
	public String controllerTest(HttpServletRequest request, HttpServletResponse response) {
		return "WEB-INF/views/managedept/finacingStatisticsLogin";
	}

	/**
	 *
	 */
	@RequestMapping("/statisticsList")
	public void getcompaniesList(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<PlatformUserLoginStatistics> datas = userService.findAll();
			// 将数据传输到前端
			this.writeJson(datas, request, response);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/statisticsInitInfoUserList")
	public String statisticsInitInfoUserList(HttpServletRequest request, HttpServletResponse response,
												String area,String type,String id ) {
		List<CountLoginInfo> list = service.getCountLoginStatistics(area,type,id);
		request.setAttribute("userList",list);
		return "WEB-INF/views/managedept/finacingStatisticsLoginUserList";
	}

}
