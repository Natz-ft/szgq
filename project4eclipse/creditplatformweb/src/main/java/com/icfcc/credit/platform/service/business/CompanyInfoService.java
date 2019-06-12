package com.icfcc.credit.platform.service.business;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.query.CompanyBase;
import com.icfcc.credit.platform.jpa.repository.business.CompanyBaseDao;


/**
 * 
 * 企业相关信息查询
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(value = "transactionManager")
public class CompanyInfoService {

	

	@Autowired
	private CompanyBaseDao companyBaseDao;

	 public CompanyBase queryByCertno(String contno) {
			CompanyBase baseInfo = companyBaseDao.queryByCertno(contno);
			return baseInfo;
		}
	 public List<String> queryIdsByName(String name){
		return companyBaseDao.queryIdsByName("%"+name+"%");
	 }
	public CompanyBase findById(String enterpriseId) {
		
		return companyBaseDao.findOne(enterpriseId);
	}
	
}
