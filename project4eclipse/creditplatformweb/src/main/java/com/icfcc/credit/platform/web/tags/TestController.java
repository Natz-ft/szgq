package com.icfcc.credit.platform.web.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.icfcc.credit.platform.web.system.PlatformBasicController;

@Controller
@Scope("prototype")
public class TestController extends PlatformBasicController {

	private static Logger log = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/test/dic")
	public ModelAndView successPage(HttpServletRequest req, HttpServletResponse rep) {
		ModelAndView model = new ModelAndView();
		model.setViewName("test/dictest");
//		model.setViewName("common/login");
		return model;
	}

}
