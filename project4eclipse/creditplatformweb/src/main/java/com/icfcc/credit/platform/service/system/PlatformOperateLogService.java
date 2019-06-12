package com.icfcc.credit.platform.service.system;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.system.PlatformOperateLog;
import com.icfcc.credit.platform.jpa.entity.system.SrrpSynchronizeJobLog;
import com.icfcc.credit.platform.jpa.repository.system.PlatformOperateLogDao;
import com.icfcc.credit.platform.jpa.repository.system.SrrpSynchronizeJobLogDao;
import com.icfcc.credit.platform.util.jpa.PageUtil;

/**
 * 日志管理类
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformOperateLogService {

	@Autowired
	private PlatformOperateLogDao operateLogDao;
	@Autowired
	private SrrpSynchronizeJobLogDao srrpSynchronizeJobLogDao;
	/**
	 * 操作日志表
	 * 
	 * @param id
	 */
	public void removeperateLog(String id) {
		try {
			operateLogDao.deleteByPlatformOperateLog(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 日志管理 <分页查询信息>
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<PlatformOperateLog> getSystemOperateLogList(Map<String, Object> searchParams, int pageNumber, int pageSize, String direction, String orderBy) {
		PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
		Specification<PlatformOperateLog> spec = PageUtil.buildSpecification(searchParams, PlatformOperateLog.class);
		Page<PlatformOperateLog> list = null;
		try {
			list = operateLogDao.findAll(spec, pageRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 日志管理 <分页查询信息>
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<SrrpSynchronizeJobLog> getTaskJobLogList(Map<String, Object> searchParams, int pageNumber, int pageSize, String direction, String orderBy) {
		PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
		Specification<SrrpSynchronizeJobLog> spec = PageUtil.buildSpecification(searchParams, SrrpSynchronizeJobLog.class);
		Page<SrrpSynchronizeJobLog> list = null;
		try {
			list = srrpSynchronizeJobLogDao.findAll(spec, pageRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 保存日志
	 * 
	 * @param log
	 */
	public void operateLog(PlatformOperateLog operateLog) {

		operateLogDao.save(operateLog);
	}
}
