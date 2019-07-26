package com.icfcc.SRRPService.managedept;

import com.icfcc.SRRPDao.jpa.entity.managedept.CountLoginInfo;
import com.icfcc.SRRPDao.jpa.repository.managedept.impl.CountLoginStatisticsDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(value = "transactionManager")
public class CountLoginService {

	@Autowired
	private CountLoginStatisticsDaoImpl DaoImpl;

	
	/**
	 */
	public List<CountLoginInfo> getCountLoginStatistics(String area, String type, String id) {
		return DaoImpl.getCountLoginStatistics(area,type,id);
	}

}
