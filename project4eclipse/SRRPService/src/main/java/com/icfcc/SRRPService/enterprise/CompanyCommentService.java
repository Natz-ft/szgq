package com.icfcc.SRRPService.enterprise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyComment;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyCommentDao;

/**
 * 评价信息的service
 * 
 * @author john
 *
 */
@Service
@Transactional(value = "transactionManager")
public class CompanyCommentService {

	@Autowired
	private CompanyCommentDao companyCommentDao;
	/**
	 * 像数据库中插入评价信息
	 * @param comment
	 */
	public void addCompanyComment(CompanyComment comment) {
		try {

			companyCommentDao.save(comment);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public List<CompanyComment> findCompanyCommentByEventId(String eventId){
		return companyCommentDao.findCompanyCommentByEventId(eventId);
	}
	
}
