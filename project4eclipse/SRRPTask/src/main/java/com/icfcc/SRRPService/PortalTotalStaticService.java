package com.icfcc.SRRPService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PortailStaticTotalDB;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortailStaticTotalIndexDB;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalDelNativeDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalTotalStaticDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.PortalTotalStaticIndexDao;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformTotalStatic;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformTotalStaticIndex;
import com.icfcc.SRRPDao.s1.jpa.repository.PortalTotalStaticNativeDao;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * <從业务系统为门户抽取运汇总结果>
 */
@Service
@Transactional(value = "transactionManager")
public class PortalTotalStaticService  {
	private static Logger log = LoggerFactory.getLogger(PortalTotalStaticService.class);
	@Autowired
	private PortalTotalStaticNativeDao nativeDao;
	@Autowired
	private PortalTotalStaticDao dao;
	@Autowired
	private PortalDelNativeDao delDao;
	@Autowired
	private PortalTotalStaticIndexDao indexDao;

	/**
	 * 
	 * <p>
	 * 功能描述:[从业务系统抽数]
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Transactional(value = "transactionManager1")
	public List<PortailStaticTotalDB> portTotalStatic() throws IllegalAccessException, InvocationTargetException {
		List<PortailStaticTotalDB> resultList = new ArrayList<PortailStaticTotalDB>();
			List<PlatformTotalStatic> dataList = nativeDao.portTotalStatic();
			for (PlatformTotalStatic data : dataList) {
				PortailStaticTotalDB target = new PortailStaticTotalDB();
					BeanUtils.copyProperties(target, data);
				resultList.add(target);
			}
		
		return resultList;
	}

	/**
	 * 
	 * <p>
	 * 功能描述:[首页-统计值]
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Transactional(value = "transactionManager1")
	public List<PortailStaticTotalIndexDB> indexStatic() throws IllegalAccessException, InvocationTargetException {
		List<PortailStaticTotalIndexDB> resultList = new ArrayList<PortailStaticTotalIndexDB>();

			List<PlatformTotalStaticIndex> dataList = nativeDao.idexStatic();
			for (PlatformTotalStaticIndex data : dataList) {
				PortailStaticTotalIndexDB target = new PortailStaticTotalIndexDB();
					BeanUtils.copyProperties(target, data);
				
				resultList.add(target);
			}
		
		return resultList;
	}

	

	@Transactional(value = "transactionManager")
	public void staticTrannfer() throws Exception {
			// 运行成果
			List<PortailStaticTotalDB> dataList = portTotalStatic();
			if (dataList != null) {
				//迁移到门户库
				delDao.delPortalData("delPortalTotal");
				dao.save(dataList);
			}

	}
	@Transactional(value = "transactionManager")
	public void indexTrannfer() throws Exception {
			// 首页-统计值
			List<PortailStaticTotalIndexDB> indexDataList = indexStatic();
			if (indexDataList != null) {
				//迁移到门户库
				indexDao.deleteAll();
				indexDao.save(indexDataList);
			}
	}
	

}
