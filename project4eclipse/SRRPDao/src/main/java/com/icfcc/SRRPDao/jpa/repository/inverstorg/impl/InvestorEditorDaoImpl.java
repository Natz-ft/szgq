package com.icfcc.SRRPDao.jpa.repository.inverstorg.impl;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import io.netty.util.internal.StringUtil;
@Component
public class InvestorEditorDaoImpl extends BaseNativeQueryDao {
	String updateSql = "update rp_investor i set";
	/**
	 * 根据获取的投资机构附加信息，修改数据库对应信息
	 * 
	 * @param investor
	 * @return
	 */
	public int updateInvestor(Investor requestInvestor) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		int res = 0;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer replace = new StringBuffer();
			if (!StringUtil.isNullOrEmpty(requestInvestor.getLicensePath())) {
				replace.append(" i.license_path= :license_path");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getFileName())) {
				replace.append(" ,i.filename= :filename");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getZipcode())) {
				replace.append(" ,i.zipcode= :zipcode");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getRelName())) {
				replace.append(" ,i.rel_name= :rel_name");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getRelPhone())) {
				replace.append(" ,i.rel_phone= :rel_phone");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getFax())) {
				replace.append(" ,i.fax= :fax");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getEmail())) {
				replace.append(" ,i.email= :email");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getDescription())) {
				replace.append(" ,i.description= :description");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getCurrency())) {
				replace.append(" ,i.currency= :currency");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getFinanceStage())) {
				replace.append(" ,i.finance_stage= :finance_stage");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getFinanceTrade())) {
				replace.append(" ,i.finance_trade= :finance_trade ");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getLogoName())) {
				replace.append(" ,i.logo_name = :logo_name ");
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getLogoPath())) {
				replace.append(" ,i.logo_path = :logo_path ");
			}
			replace.append(" where i.investor_id = :investorId ");
			Query query = entityManager.createNativeQuery(updateSql + replace.toString(), Investor.class);
			if (!StringUtil.isNullOrEmpty(requestInvestor.getFileName())) {
				query.setParameter("filename", requestInvestor.getFileName());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getLicensePath())) {
				query.setParameter("license_path", requestInvestor.getLicensePath());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getZipcode())) {
				query.setParameter("zipcode", requestInvestor.getZipcode());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getRelName())) {
				query.setParameter("rel_name", requestInvestor.getRelName());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getRelPhone())) {
				query.setParameter("rel_phone", requestInvestor.getRelPhone());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getFax())) {
				query.setParameter("fax", requestInvestor.getFax());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getEmail())) {
				query.setParameter("email", requestInvestor.getEmail());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getDescription())) {
				query.setParameter("description", requestInvestor.getDescription());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getCurrency())) {
				query.setParameter("currency", requestInvestor.getCurrency());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getFinanceStage())) {
				query.setParameter("finance_stage", requestInvestor.getFinanceStage());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getFinanceTrade())) {
				query.setParameter("finance_trade", requestInvestor.getFinanceTrade());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getLogoName())) {
				query.setParameter("logo_name", requestInvestor.getLogoName());
			}
			if (!StringUtil.isNullOrEmpty(requestInvestor.getLogoPath())) {
				query.setParameter("logo_path", requestInvestor.getLogoPath());
			}
			query.setParameter("investorId", requestInvestor.getInvestorId());
			res = query.executeUpdate();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}
	public int updateInvestorStatus(Investor requestInvestor) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		int res = 0;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			String updateSql = "update rp_investor i set i.audit_status = :audit_status  where i.investor_id = :investorId  ";
			Query query = entityManager.createNativeQuery(updateSql, Investor.class);
			query.setParameter("audit_status", requestInvestor.getAuditStatus());
			query.setParameter("investorId", requestInvestor.getInvestorId());
			res = query.executeUpdate();
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
