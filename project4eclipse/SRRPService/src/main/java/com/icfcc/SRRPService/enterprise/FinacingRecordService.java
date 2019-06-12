package com.icfcc.SRRPService.enterprise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingRecord;
import com.icfcc.SRRPDao.jpa.repository.enterprise.FinacingRecordDao;

@Service
@Transactional(value = "transactionManager")
public class FinacingRecordService {

	@Autowired
	private FinacingRecordDao finacingRecordDao;

	/**
	 * 根据融资事件的id与投资机构的id查询融资记录的详细信息
	 * 
	 * @param infoId
	 * @return
	 */
	public List<FinacingRecord> findFinacingRecordList(String eventId,
			String investorId) {

		return finacingRecordDao.findByInfoIdAndInvestorId(eventId, investorId);
	}
	public void updateByeventId(String eventId){
		finacingRecordDao.updateByeventId(eventId);
	}

}
