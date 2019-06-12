package com.icfcc.credit.platform.listener;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.system.PlatformDic;
import com.icfcc.credit.platform.jpa.repository.system.PlatformDicDao;
import com.icfcc.credit.platform.util.cache.DicCache;
@Component
public class InitDic{
	
	@Autowired
	private PlatformDicDao systemDicDao;
	@Autowired
	private DicCache dicCache;
	//@PostConstruct
	public void initDic() {
		Map<String, LinkedHashMap<String, String>> cachMap = dicCache.getCachMap();
		List<PlatformDic> listAll = (List<PlatformDic>) systemDicDao.findAll();
		
		for (PlatformDic tbDic1 : listAll) {
			if("BASIC".equals(tbDic1.getType())){
				List<PlatformDic> dicListByType = systemDicDao.findByType(tbDic1.getValue());
				LinkedHashMap<String, String> dic=new LinkedHashMap<String,String>();
				for (PlatformDic tbDic2 : dicListByType) {
					dic.put(tbDic2.getValue(), tbDic2.getName());
				}
				cachMap.put(tbDic1.getValue(), dic);
			}
		}
	}


}
