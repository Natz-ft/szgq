package com.icfcc.SRRPDao.s.jpa.entity.portal;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankCompany;

@Component
public class PortalDelNativeDao {

	String delSql = " select t.name , t.amount as finanmoney,t.rank_count as uplistnum,t.time_id as countdate ,'' as adminarea,t.rank_count as ranking from rp_report_company t ";

	// 删除企业榜单
	String delEnterprise = " delete from platform_portal_company_rangking ";
	// 删除投资机构榜单
	String delInvestor = " delete from platform_portal_rangking_investor ";
	// 删除地区榜单
	String delArea = " delete from platform_portal_rangking_company_area ";
	// 删除行業榜单
	String delIndustry = " delete from platform_portal_rangking_company_industry ";
	// 删除按地区统计各维度指标
	String delStaticByType = " delete from platform_portal_statisbytype where statictype =:staticType ";
	// 删除门户统计总量
	String delPortalTotal = " delete from platform_portal_statictis ";
	// 删除融资轮次统计结果
	String delFinacTurn = " delete from platform_portal_rangking_finacturn ";
	
	// 删除首页统计结果
	String index = " delete from platform_portal_index ";
	
	@Autowired(required = false)
	@Qualifier("entityManagerFactory")
	private LocalContainerEntityManagerFactoryBean ens;

	public EntityManager getEntityManager() {
		return ens.getNativeEntityManagerFactory().createEntityManager();
	}

	public void closeEntityManager(EntityManager entityManager) {
		if (null != entityManager) {
			entityManager.close();
		}
	}

	/**
	 * 
	 * <p>
	 * 功能描述:门户数据按类型删除
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public void delPortalData(String dataType) {
		if (dataType == null || "".equals(dataType)) {
			System.out
					.println("delPortalData is not execute because dataType is empty!");
		} else {
			EntityManager entityManager = this.getEntityManager();
			EntityTransaction entityTransaction = null;
			String delSql = "";
			try {
				entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				switch (dataType) {

				// 删除企业榜单
				case "delEnterprise":
					delSql = delEnterprise;
					break;
				// 删除投资机构榜单
				case "delInvestor":
					delSql = delInvestor;
					break;
				// 删除地區榜单
				case "delArea":
					delSql = delArea;
					break;
				// 删除行業榜单
				case "delIndustry":
					delSql = delIndustry;
					break;
				// 删除门户汇总
				case "delPortalTotal":
					delSql = delPortalTotal;
					break;
				// 删除融资轮次汇总
				case "delFinacTurn":
					delSql = delFinacTurn;
					break;
					// 删除融资轮次汇总
				case "index":
					delSql = index;
					break;
				default:
					break;
				}
				Query query = entityManager.createNativeQuery(delSql);
				query.executeUpdate();
				entityTransaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				entityTransaction.rollback();
			} finally {
				this.closeEntityManager(entityManager);
			}
		}
	}

	/**
	 * 
	 * <p>
	 * 功能描述:门户数据按类型删除
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public void delStaticByTypeData(String dataType, String staticType) {
		if (dataType == null || "".equals(dataType)) {
			System.out
					.println("delPortalData is not execute because dataType is empty!");
		} else {
			EntityManager entityManager = this.getEntityManager();
			EntityTransaction entityTransaction = null;
			String delSql = "";
			try {
				entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				switch (dataType) {

				// 删除地区统计结果
				case "delStaticByType":
					delSql = delStaticByType;
					break;
				default:
					break;
				}
				Query query = entityManager.createNativeQuery(delSql);
				query.setParameter("staticType", staticType);
				query.executeUpdate();
				entityTransaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				entityTransaction.rollback();
			} finally {
				this.closeEntityManager(entityManager);
			}
		}
	}
}
