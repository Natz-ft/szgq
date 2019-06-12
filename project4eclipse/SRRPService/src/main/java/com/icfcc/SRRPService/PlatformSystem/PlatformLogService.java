package com.icfcc.SRRPService.PlatformSystem;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformLog;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformLogDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;

/**
 * <系统日志>
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformLogService
{
    @Autowired
    private PlatformLogDao proLogDao;
    
    public Page<PlatformLog> queryProLogList(Map<String, Object> searchParams, int pageNumber, int pageSize)
    {
        String[] orderBy = {"proLogId"};
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, "desc", orderBy);
        Specification<PlatformLog> spec = PageUtil.buildSpecification(searchParams, PlatformLog.class);
        return proLogDao.findAll(spec, pageRequest);
    }
    
    public PlatformLog queryProLogDetail(Long logId)
    {
        return proLogDao.findOne(logId);
    }
    
    public void deleteByIds(List<Long> ids)
    {
        Iterable<PlatformLog> proLogs = proLogDao.findAll(ids);
        proLogDao.delete(proLogs);
    }
    
    public void save(PlatformLog systemLog)
    {
        proLogDao.save(systemLog);
    }
}
