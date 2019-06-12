package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformStaticByArea;

@Component
public class PortalStaticByAreaNativeDao extends BaseNativeQueryDao {
	// public class PortalStaticByAreaNativeDao {

	// 统计类型01：发布融资总额；02：解决融资总额；03：正在融资总额；04：平台企业家数；05：平台机构数；06：月度融资统计
	// 发布融资总额 -01
	String releaseStaticSql = "select sum(a.amountCNYmax) as amount ,b.rearea as areacode from rp_finacing_demand a ,rp_company_base b where a.enterprise_id=b.enterprise_id and a.status not in ('99')  group by b.rearea";
	// 解决融资总额-02
	String successFinancingStaticSql = "select ROUND(SUM(c.exchange_ratio)/1000000,2), 0)  as amount ,b.rearea as areacode from rp_finacing_demand a ,rp_company_base b,rp_finacing_event c  where a.enterprise_id=b.enterprise_id and a.enterprise_id =c.enterprise_id  and c.status in ('31','41','42','43','44','45') group by b.rearea";
	// 正在融资总额-03
	String financingStaticSql = "select sum(a.amountCNYmax) as amount ,b.rearea from rp_finacing_demand a ,rp_company_base b where a.enterprise_id=b.enterprise_id and a.status in('02')  group by b.rearea";
	// 平台企业数-04
	String enterpriseStaticSql = "SELECT count(a.enterprise_id) AS amount,IFNULL(a.rearea, '') AS areacode FROM rp_company_base a,system_user u WHERE a.enterprise_id = u.org_id AND u.user_type IN ('02', '0201')  and u.create_time <= last_day(str_to_date(:Monthly, '%Y-%m')) and a.rearea is not null GROUP BY a.rearea";
	// 平台机构数-05
	String investorStaticSql = "select count(aa.investor_id) as amount,IFNULL(aa.area_code,'') as areacode from ( select investor_id, case LEFT(area_code,'2')  when  '32' then  area_code else 'other' END as area_code from rp_investor a where a.create_time <= last_day(str_to_date(:Monthly, '%Y-%m')) and a.area_code is not null and a.audit_status<>0 ) aa  group by aa.area_code";
	// 月度融资统计 - 06
	String monthlyStaticSql = "select ROUND(SUM(a.amount)/1000000,2), 0) as amount,b.rearea as areacode from rp_investor_loan a,rp_company_base b  where a.enterprise_id =b.enterprise_id and  str_to_date(a.loandate,'%Y-%m') =:Monthly group by b.rearea";
	// 机构用户数统计 - 07
//	String monthlyStaticUserSql = "select aa.area_code as areacode,count(aa.certno) as amount from(select ri.certno,case LEFT(ri.area_code,'2')  when  '32' then  area_code else 'other' END as area_code from rp_investor ri  where ri.create_time <= last_day(str_to_date(:Monthly, '%Y-%m')) and ri.area_code !=null ) aa group by aa.area_code ";

	String excueteSql = "";

	@SuppressWarnings("unchecked")
	public List<PlatformStaticByArea> portStaticResultByType(String staticType,
			String monthLy) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<PlatformStaticByArea> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			if (null != staticType) {
				switch (staticType) {
				case "01":
					excueteSql = releaseStaticSql;
					break;
				case "02":
					excueteSql = successFinancingStaticSql;
					break;
				case "03":
					excueteSql = financingStaticSql;
					break;
				case "04":
					excueteSql = enterpriseStaticSql;
					break;
				case "05":
					excueteSql = investorStaticSql;
					break;
				case "06":
					excueteSql = monthlyStaticSql;
					break;
				default:
					break;
				}
			}
			Query query = entityManager.createNativeQuery(excueteSql,
					PlatformStaticByArea.class);
			if ("04".equals(staticType) && null != monthLy
					&& !"".equals(monthLy)) {
				query.setParameter("Monthly", monthLy);
			}
			if ("05".equals(staticType) && null != monthLy
					&& !"".equals(monthLy)) {
				query.setParameter("Monthly", monthLy);
			}
			if ("06".equals(staticType) && null != monthLy
					&& !"".equals(monthLy)) {
				query.setParameter("Monthly", monthLy);
			}
			if ("07".equals(staticType) && null != monthLy
					&& !"".equals(monthLy)) {
				query.setParameter("Monthly", monthLy);
			}
			res = (List<PlatformStaticByArea>) query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}
}
