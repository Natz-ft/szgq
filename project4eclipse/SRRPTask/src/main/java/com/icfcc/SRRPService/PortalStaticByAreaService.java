package com.icfcc.SRRPService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.GataWayStaticResult;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalDelNativeDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalStaticByAreaDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformStaticByArea;
import com.icfcc.SRRPDao.s1.jpa.repository.PortalStaticByAreaNativeDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * <從业务系统为门户抽取运行结果内按地区统计结果>
 */
@Service
@Transactional(value = "transactionManager")
public class PortalStaticByAreaService   {
	private static Logger log = LoggerFactory.getLogger(PortalStaticByAreaService.class);
	private static Map<String, String> reflectMap = new HashMap<String, String>();
	@Autowired
	private PortalStaticByAreaNativeDao nativeDao;
	@Autowired
	private PortalStaticByAreaDao dao;
	@Autowired
	private PortalDelNativeDao delDao;

	/**
	 * 
	 * <p>
	 * 功能描述:[從业务系统为门户抽取运行结果内按地区统计结果]
	 * </p>
	 * 
	 * @param staticType
	 * @param monthLy
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Transactional(value = "transactionManager1")
	public List<GataWayStaticResult> portStaticResultByType(String staticType, String monthLy) throws Exception {
		// 初始化对应关系
		if (reflectMap.isEmpty()) {
			initMap();
		}
		List<PlatformStaticByArea> dataList = null;
		List<GataWayStaticResult> resultList = null;
		dataList = nativeDao.portStaticResultByType(staticType, monthLy);
		if (dataList != null && dataList.size() > 0) {
			GataWayStaticResult result = new GataWayStaticResult();
			resultList = new ArrayList<GataWayStaticResult>();
			for (PlatformStaticByArea data : dataList) {
				result.setStaticType(staticType);
				String code = null;
				code = data.getAreaCode();
				if (code != null && !"".equals(code)) {
					String reflectColumn = reflectMap.get(data.getAreaCode());
					if (reflectColumn != null && !"".equals(reflectColumn)) {
						reflectSet(result, reflectColumn, data.getAmount());
					}
					result.setMonthly(monthLy);
				}
			}
			resultList.add(result);
		}
		return resultList;
	}

	@Transactional(value = "transactionManager")
	public void saveStaticResults(List<GataWayStaticResult> dataList, String staticType) {
			delDao.delStaticByTypeData("delStaticByType", staticType);
			dao.save(dataList);
		
	}

	private void reflectSet(Object t, String setMethod, String values)
			throws Exception, IllegalArgumentException, InvocationTargetException {
		Method setReadOnly = t.getClass().getMethod(setMethod, String.class);
		setReadOnly.invoke(t, values);
	}

	public void initMap() {
		reflectMap.put("320508", "setGuSu");
		reflectMap.put("320505", "setGaoXin");
		reflectMap.put("320506", "setWuZhong");
		reflectMap.put("320507", "setXiangCheng");
		reflectMap.put("320581", "setChangShu");
		reflectMap.put("320582", "setZhangJiaGang");
		reflectMap.put("320583", "setKunShan");
		reflectMap.put("320584", "setWuJiang");
		reflectMap.put("320585", "setTaiCang");
		reflectMap.put("320599", "setGongYeYuan");
		reflectMap.put("other", "setOther");
	}

}
