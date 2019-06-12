package com.icfcc.SRRPService.someLog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.SomeLog;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.EmptyConditionUtils;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ReportConstant;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachment;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachmentPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBasePending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyMember;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjection;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjectionPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyProduct;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholder;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholderPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyUnionInfoResult;
import com.icfcc.SRRPDao.jpa.entity.enterprise.HistoryCompanyBase;
import com.icfcc.SRRPDao.jpa.entity.managedept.QueryCompanyAuditResult;
import com.icfcc.SRRPDao.jpa.repository.SomeLogDao;
import com.icfcc.SRRPDao.jpa.repository.SomeLogDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyAttachmentDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyAttachmentPendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBaseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBasePendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBaseSupplementDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyMemberDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyObjectionDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyObjectionPendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyProductDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyStockholderDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyStockholderPendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.HistoryCompanyBaseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.CompanyInfoDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.managedept.impl.QueryCompanyAuditResultImpl;
import com.icfcc.SRRPService.util.AESUtil;
import com.icfcc.credit.platform.util.FileUtil;
import com.icfcc.credit.platform.util.JsonUtil;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * 详细日志记录
 * @author whyxs
 *
 */
@Service
@Transactional(value = "transactionManager")
public class SomeLogService {

	@Autowired
	private SomeLogDao someLogDao;
	
	@Autowired
	private SomeLogDaoImpl someLogDaoImpl;
	
	public void saveSomeLog(SomeLog someLog) {
		someLogDao.save(someLog);
	}

}
