package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingRecord;

@Component
public interface FinacingRecordDao
		extends PagingAndSortingRepository<FinacingRecord, String>, JpaSpecificationExecutor<FinacingRecord> {

	@Query("select efr from  FinacingRecord efr where efr.infoId=?1 order by efr.operdate desc ")
	public FinacingRecord findTheLastByInfoId(String infoId);

	@Query("select efr from  FinacingRecord efr where efr.infoId=?1 order by efr.operdate desc ")
	public List<FinacingRecord> findByInfoId(String infoId);
	
	@Modifying
	@Query("delete  from  FinacingRecord efr where efr.infoId=?1")
	public void deleteFinacingRecord(String infoId);
	/**
	 * 根据融资事件的id以及机构的id查询融资记录表中记录
	 * 
	 * @param eventId
	 * @param investorId
	 * @return
	 */
	@Query("select efr from  FinacingRecord efr where efr.eventId=?1 and efr.investorgid=?2 order by efr.operdate asc,efr.status asc")
	public List<FinacingRecord> findByInfoIdAndInvestorId(String eventId, String investorId);

	@Query("select count(efr) from  FinacingRecord efr where efr.infoId=?1 ")
	public Long countByInfoId();
	
	//根据投资机构、用户、操作状态 查询融资记录
	@Query("select efr from  FinacingRecord efr where efr.operuser=?1 and opertype=?2 and investorgid=?3  and infoId=?4")
	public List<FinacingRecord> findFinacingRecord(String userName,String status,String inverstorId,String infoId);
	//将未读改为已读
	@Modifying
	@Query("update FinacingRecord fr set fr.unLook='0' where fr.eventId=?1 ")
	public void updateByeventId(String eventId);
}
