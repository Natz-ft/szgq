package com.icfcc.SRRPService.managedept;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.managedept.InvestorHistory;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManagedeptOnlineForum;
import com.icfcc.SRRPDao.jpa.repository.managedept.InvestorHistoryDao;
import com.icfcc.SRRPDao.jpa.repository.managedept.ManagedeptOnlineForumDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;
/**
 * 
* @ClassName: ManagedeptOnlineForumService
* @Description: TODO(在线提问解的业务层)
* @author hugt
* @date 2017年9月19日 上午10:53:17
*
 */
@Component
@Transactional(value = "transactionManager")
public class InvestorHistoryService {
	
	@Autowired
	private InvestorHistoryDao investorHistoryDao;
	
	
	
	/**
	 * 插入记录
	 */
	public void saveInvestorHistory(InvestorHistory investorhis){
		investorHistoryDao.save(investorhis);
	}

	
}
