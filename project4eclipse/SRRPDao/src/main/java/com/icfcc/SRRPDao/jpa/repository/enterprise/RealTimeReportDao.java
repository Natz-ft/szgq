package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.Date;
import java.util.List;

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanArea;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTrade;

public interface RealTimeReportDao {

	/**
	 * 获得机构的所有所有行业投资额度列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<ReportBeanTrade> findTradeInvestByOrgId(String orgId, Date start, Date end,Integer limit);

	/**
	 * 获得企业的所有已投资额度
	 * 
	 * @param orgIds
	 * @return
	 */
	public Double sumInvestByOrgId(String orgId, Date start, Date end);

	/**
	 * 获得机构所有区域投资额度列表
	 * @param orgId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ReportBeanArea> findAreaInvestByOrgId(String orgId, Date start, Date end,Integer limit);

}
