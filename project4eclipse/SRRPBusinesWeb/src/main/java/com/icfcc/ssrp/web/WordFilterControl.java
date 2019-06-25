package com.icfcc.ssrp.web;

import com.icfcc.ssrp.util.SensitiveWordFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WordFilterControl extends SRRPBaseController {


	@RequestMapping(value = "/wordFilter")
	public Object wordFilter(String words) {

		SensitiveWordFilter sw = new SensitiveWordFilter();
		sw.InitializationWork();
		Map<String,Object> map = sw.filterInfo(words);
		return map;

	}


}
