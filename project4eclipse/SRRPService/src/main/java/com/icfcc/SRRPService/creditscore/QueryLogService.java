package com.icfcc.SRRPService.creditscore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.icfcc.SRRPDao.jpa.entity.creditscore.QueryLog;
import com.icfcc.SRRPDao.jpa.repository.creditscore.QueryLogDAO;

@Service
@Transactional(value = "transactionManager")
public class QueryLogService {

	
	@Autowired
	private QueryLogDAO querylogDAO;
	
	
	public void addquerylog(QueryLog querylog){
		querylogDAO.save(querylog);
	}
	
}
