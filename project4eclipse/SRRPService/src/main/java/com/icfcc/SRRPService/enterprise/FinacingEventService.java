package com.icfcc.SRRPService.enterprise;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.EventBeanForCharge;
import com.icfcc.SRRPDao.jpa.entity.enterprise.EventBeanForOrg;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEvent;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEventHis;
import com.icfcc.SRRPDao.jpa.entity.enterprise.InvestorFollow;
import com.icfcc.SRRPDao.jpa.entity.enterprise.InvestorPublish;
import com.icfcc.SRRPDao.jpa.entity.enterprise.PublishBeanForCharge;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryFollowInvestorResult;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryInvestorFinacingEventResult;
import com.icfcc.SRRPDao.jpa.entity.enterprise.UnionDemandInvestorResult;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorLoan;
import com.icfcc.SRRPDao.jpa.repository.enterprise.FinacingEventDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.FinacingEventHisDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorFollowDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorLoanDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorPublishDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.FinacingEventDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.InvestorFollowDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.InvestorPublishDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.UnionDemandInvestorDaoImpl;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * 融资事件的相关操作service层
 * 
 * @author john
 *
 */
@Service
@Transactional(value = "transactionManager")
public class FinacingEventService {

	// 查询语句处理关联查询
	@Autowired
	private FinacingEventDao finacingEventDao;
	@Autowired
	private FinacingEventDaoImpl finacingEventDaoImpl;
	@Autowired
	private InvestorFollowDao investorFollowDao;

	@Autowired
	private InvestorFollowDaoImpl investorFollowDaoImpl;
	@Autowired
	private InvestorLoanDao investerLoanDao;
	@Autowired
	private InvestorPublishDao investorPublishDao;

	@Autowired
	private InvestorPublishDaoImpl investorPublishDaoImpl;

	@Autowired
	private FinacingEventHisDao finacingEventHisDao;
	
	@Autowired
	private UnionDemandInvestorDaoImpl demandInvestorDaoImpl;
	@Autowired
	private InvestorFollowDao followDao;
	
	

	/**
	 * <根据参数查询信息列表显示融资信息>
	 * 
	 * @return
	 */
	// 多条件进行融资事件查询
	public List<QueryInvestorFinacingEventResult> listUnionFinacingEventByWhereCase(
			QueryCondition queryCondition, String enterpriseId) {
		// 根据传递参数查询
		return finacingEventDaoImpl.listUnionFinacingEventByWhereCase(
				queryCondition, enterpriseId);
	}

	/**
	 * 企业菜单/我的融资/融资事件列表查询所有融资事件的总的适量
	 * 
	 * @param queryCondition
	 * @return
	 */
	public Object getCounts(QueryCondition queryCondition, String enterpriseId) {
		return finacingEventDaoImpl.getFinacingEventInfoCount(queryCondition,
				enterpriseId);
	}

	/**
	 * 根据融资事件的id查询融资事件
	 */
	public FinacingEvent findFinacingEventById(String eventId) {
		return finacingEventDao.findOne(eventId);
	}

	/**
	 * 根据状态查询一个机构可以查看的，某状态的所有融资事件
	 * 
	 * @param status
	 * @param infoId
	 * @param investOrgId
	 * @param pageable
	 * @return
	 */
	public Page<FinacingEvent> getEventsByStatusOrg(String status,
			String investOrgId, Integer page, Integer size) {
		if (StringUtils.isEmpty(status) || StringUtils.isEmpty(investOrgId)) {
			return null;
		}
		PageRequest pageable = new PageRequest(page, size);
		Page<FinacingEvent> res = this.finacingEventDao.findByStatusOrgId(
				status, investOrgId, pageable);
		return res;
	}

	/**
	 * 机构首页待办查询列表
	 * 
	 * @param status
	 * @param investOrgId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<EventBeanForOrg> getEventBeansByStatusOrg(String status,
			String investOrgId, Integer page, Integer size) {
		PageRequest pageable = new PageRequest(page, size);
		List<EventBeanForOrg> res = this.finacingEventDaoImpl.findForOrg(
				investOrgId, status, page, size);
		Page<EventBeanForOrg> pageRes = new PageImpl<EventBeanForOrg>(res,
				pageable, res == null ? 0 : res.size());
		return pageRes;
	}

	/**
	 * 机构首页待办查询列表
	 * 
	 * @param queryCondition
	 * @return
	 * @author Daiyx
	 */
	public List<EventBeanForOrg> getEventBeansByStatusInvestor(
			QueryCondition queryCondition) {

		return (List<EventBeanForOrg>) finacingEventDaoImpl
				.findForInvestor(queryCondition);
	}

	/**
	 * 
	 * @Title: getCount
	 * @Description: 获取对应状态的提醒投资机构的条数
	 * @param queryCondition
	 * @author Daiyx
	 *
	 */
	public Map<String, BigInteger> getCount(QueryCondition queryCondition) {

		return finacingEventDaoImpl.findCount(queryCondition);
	}

	/**
	 * 融资进度查询
	 * 
	 * @param trade
	 * @param turn
	 * @param amountTop
	 * @param amountBottom
	 * @param status
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<EventBeanForCharge> getEventBeansForCharge(String trade,
			String turn, Double amountTop, Double amountBottom, String status,
			Integer page, Integer size) {
		PageRequest pageable = new PageRequest(page, size);
		List<EventBeanForCharge> res = this.finacingEventDaoImpl.findForCharge(
				trade, turn, amountTop, amountBottom, status, page, size);
		Page<EventBeanForCharge> pageRes = new PageImpl<EventBeanForCharge>(
				res, pageable, res == null ? 0 : res.size());
		return pageRes;
	}

	/**
	 * 给主管部门获取披露列表
	 * 
	 * @param infoType
	 * @param projectName
	 * @param start
	 * @param end
	 * @param page
	 * @param size
	 * @return
	 */

	public Page<PublishBeanForCharge> getPublishBeansForCharge(String infoType,
			String projectName, Date start, Date end, Integer page, Integer size) {

		PageRequest pageable = new PageRequest(page, size);

		List<PublishBeanForCharge> res = this.investorPublishDaoImpl
				.findForCharge(infoType, projectName, start, end, page, size);
		Page<PublishBeanForCharge> pageRes = new PageImpl<PublishBeanForCharge>(
				res, pageable, res == null ? 0 : res.size());

		return pageRes;
	}

	/**
	 * 查询一个融资机构的某个状态的融资事件的数量
	 * 
	 * @param status
	 * @param investOrgId
	 * @return
	 */
	public Long countEventsByStatusOrg(String status, String investOrgId) {
		if (StringUtils.isEmpty(status) || StringUtils.isEmpty(investOrgId)) {
			return 0L;
		}
		Long res = this.finacingEventDao
				.countByStatusOrgId(status, investOrgId);
		return res;
	}

	/**
	 * 根据融资信息的infoid查询融资事件
	 */

	public List<FinacingEvent> findFinacingEventByInfoId(String infoId) {
		return finacingEventDao.findFinacingEventByInfoId(infoId);
	}
	
	
	public List<FinacingEvent> findFinacingEventByInfoIdAndStatus(String infoId) {
		return finacingEventDao.findFinacingEventByInfoIdAndStatus("01",infoId);
	}
	/**
	 * 查询已经投资的投资事件
	 * @param infoId
	 * @return
	 */
	public FinacingEvent findOKFinacingEventByInfoId(String infoId) {
		return finacingEventDao.findOKFinacingEventByInfoId(infoId);
	}
	
	public List<UnionDemandInvestorResult> findDemandInvestorResult(String infoId) {
		return demandInvestorDaoImpl.findDemandInvestorResult(infoId);
	}
	
	
	public List<FinacingEvent> findFinacingEventByInfoIdAndInvestor(String investorgId,String infoId) {
		return finacingEventDao.findFinacingEventByInfoIdAndInvestor("01",investorgId,infoId);
	}
	
	/**
	 * 一个融资信息所发出的所有定向融资事件
	 * 
	 * @param infoId
	 * @return
	 */
	public Long countEventByInfoId(String infoId) {
		if (StringUtils.isEmpty(infoId)) {
			return 0L;
		}
		return this.finacingEventDao.countByInfoId(infoId);
	}

	/**
	 * 
	 * @param status
	 * @param eventId
	 */
	public void updateEventStatus(String status, String eventId) {
		this.finacingEventDao.updateStatus(status, eventId);
	}

	/**
	 * 
	 * @param status
	 * @param eventId
	 */
	public void updateEventInvest(String currency, Double amount) {
		if (StringUtils.isEmpty(currency) || amount == null) {
			return;
		}
		this.finacingEventDao.updateInvest(currency, amount);
	}

	/**
	 * 
	 * @param event
	 */
	public void saveEvent(FinacingEvent event) {
		if (event == null) {
			return;
		}
		try {
			this.finacingEventDao.save(event);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @Title: saveFollow
	 * @Description: 投资成功添加投资信息
	 * @param follow
	 *            设定文件
	 * @return void 返回类型
	 * 
	 */
	public void saveFollow(InvestorFollow follow) {
		if (follow == null) {
			return;
		}
		try {
			this.investorFollowDao.save(follow);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存一个放款信息，此时修改了融资事件状态
	 * 
	 * @param loan
	 */
	public void saveLoan(InvestorLoan loan) {
		if (loan == null) {
			return;
		}
		this.investerLoanDao.save(loan);
		// FinacingEvent event =
		// this.finacingEventDao.findOne(loan.getEventId());
		// if (event.getStatus().compareTo(FinacingEvent.STATUS_LOANED) < 0) {
		// this.finacingEventDao.updateStatus(FinacingEvent.STATUS_LOANED,
		// event.getEventId());
		// }
	}

	/**
	 * 保存一个放款信息
	 * 
	 * @author Daiyx
	 * @param loan
	 */
	public void addLoan(InvestorLoan loan) {
		if (loan == null) {
			return;
		}
		try {
			this.investerLoanDao.save(loan);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存一个信息信息披露
	 * 
	 * @param publish
	 */
	public void addPublish(InvestorPublish publish) {

		if (publish == null) {
			return;
		}
		try {
			this.investorPublishDao.save(publish);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * <p>
	 * 功能描述:投资机构是否披露过
	 * </p>
	 * 
	 * @param eventId
	 * @param enterpriseId
	 * @param orgId
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public List<InvestorPublish> ifPublished(String eventId,
			String enterpriseId, String orgId) {
		return this.investorPublishDao
				.ifPublished(eventId, enterpriseId, orgId);
	}

	/**
	 * 保存一个信息信息披露，此时修改了融资事件状态
	 * 
	 * @param publish
	 */
	public void savePublish(InvestorPublish publish) {

		if (publish == null) {
			return;
		}
		this.investorPublishDao.save(publish);
		FinacingEvent event = this.finacingEventDao.findOne(publish
				.getEventId());
		if (event.getStatus().compareTo(FinacingEvent.STATUS_PUBLISHED) < 0) {
			this.finacingEventDao.updateStatus(FinacingEvent.STATUS_PUBLISHED,
					event.getEventId());
		}
	}

	/**
	 * 获得一个融资事件所有相关的跟投信息
	 * 
	 * @param eventId
	 * @return
	 */
	public List<InvestorFollow> getFollowsByEvent(String eventId) {
		if (StringUtils.isEmpty(eventId)) {
			return new ArrayList<InvestorFollow>();
		}
		return this.investorFollowDao.findByEventId(eventId);
	}

	/**
	 * 获得一个融资时间所有放款信息，按时间倒叙排列
	 * 
	 * @param eventId
	 * @return
	 */
	public List<InvestorLoan> getLoansByEvent(String eventId) {
		if (StringUtils.isEmpty(eventId)) {
			return new ArrayList<InvestorLoan>();
		}
		return this.investerLoanDao.findByEventId(eventId);
	}

	/**
	 * 获取机构投资成功的融资事件数量
	 * 
	 * @param orgId
	 * @return
	 */
	public Long countOKEventsByOrg(String orgId) {
		if (StringUtils.isEmpty(orgId)) {
			return 0L;
		}
		return this.finacingEventDao.countByStatusOrgId("31", orgId);
	}
	
	public int countOKEventsByOrgStatus(String orgId) {
		if (StringUtils.isEmpty(orgId)) {
			return 0;
		}
		return Integer.parseInt(this.finacingEventDaoImpl.countByOrgIdStatus("31", orgId).toString());
	}
	/**
	 * 根据融资需求的id删除需求对应的融资事件的所有信息
	 * 
	 * @param infoId
	 */
	public void deleteFinacingEvent(String eventId) {
		finacingEventDao.delete(eventId);
	}

	/**
	 * @author loudw
	 * @param infoid
	 *            融资需求ID
	 * @用途 更新需求状态的公用方法，根据需求ID，分别在需求表和事件表找到对的状态，并判断是否更新 需求状态：01 发布 02 融资中 03 融资成功
	 *     融资进度：01 发布 11 关注 21 启动约谈 22 取消关注 31 投资成功 32 拒绝投资 99 关闭
	 *     当融资进度发生变化时，即时更新需求状态
	 * 
	 */
	public void updateDemadStatus(String infoid) {
		// 获取事件状态 关注、 启动约谈、 投资成功 是否有对应的记录
		String eventStatus = finacingEventDao.getMaxEventStatus(infoid);
		// 获取需求状态
		String infoStatus = finacingEventDao.getDemandStatus(infoid);

		// 更改状态逻辑
		// 1:当事件状态为 关注，启动约谈 ，需求状态为 01 时 修改为 02
		// 2:当事件状态为 null时，需求状态为02，是取消关注或者拒绝投资操作，状态为发布
		// 3:当事件状态为投资成功 时，需求状态修改为03 融资成功
		if (("11".equals(eventStatus) || "21".equals(eventStatus))
				&& "01".equals(infoStatus)) {
			finacingEventDao.updateDemandStatus("02", infoid);// 更改为融资中
		} else if (("11".equals(eventStatus) || "21".equals(eventStatus))
				&& "02".equals(infoStatus)) {
			finacingEventDao.updateDemandStatus("02", infoid);// 更改为融资中
		} else if (eventStatus == null && "02".equals(infoStatus)) {
			finacingEventDao.updateDemandStatus("02", infoid);// 更改为发布
		} else if (SRRPConstant.FINANCPHASE41.equals(eventStatus)
				&& "02".equals(infoStatus)) {
			finacingEventDao.updateDemandStatus("03", infoid);// 更改为融资成功
		} else if ((SRRPConstant.FINANCPHASE21.equals(eventStatus) || eventStatus==null )&&( "03".equals(infoStatus)|| infoStatus == null)) {
			finacingEventDao.updateDemandStatus("03", infoid);// 更改为融资成功
		}
	}

	/***
	 * 
	 * <p>
	 * 功能描述:根据需求ID获取放款信息
	 * </p>
	 * 
	 * @param infoId
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public List<InvestorLoan> findLoanInfoByInfoId(String infoId) {
		return investerLoanDao.findLoanInfoByInfoId(infoId);
	}
	public List<InvestorLoan> findLoanInfoByEventId(String eventId) {
		return investerLoanDao.findLoanInfoByEventId(eventId);
	}

	/**
	 * 
	 * <p>
	 * 功能描述:[融资事件保存历史]
	 * </p>
	 * 
	 * @param finacingEventHis
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public void saveFinacingEventHis(FinacingEventHis finacingEventHis) {
		finacingEventHisDao.save(finacingEventHis);
	}
	
	/**
	 * 根据融资事件的id查询跟投信息表
	 */
	public List<QueryFollowInvestorResult> findInvestorFollowListByEventId(String  eventId) {
		return  investorFollowDaoImpl.findInvestorFollowListByEventId(eventId);
	}

	/**
	 * 
	 * <p>功能描述:[根據需求ID查找跟頭信息]</p>
	 * @param infoId
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public List<InvestorFollow> findInvestorInfo(String infoId){
		return followDao.findInvestorInfo(infoId);
	}
	
	/**
	 * 根据需求的id以及机构的id查询融资事件
	 * @param infoId
	 * @param investorId
	 * @return
	 */
	public FinacingEvent findFinacingEventByInfoId(String infoId,String investorId,String operuser){
	    return	finacingEventDao.findFinacingEventByInfoId(infoId, investorId,operuser);
	}
	/**
	 * 根据融资事件的id更改事件的为已经披露操作
	 * @param eventId
	 */
	public void updatePublishFlag(String eventId){
		finacingEventDao.updatePublishFlag(eventId);
	}
	public void updateUnPublishFlag(String eventId){
		finacingEventDao.updateUnPublishFlag(eventId);
	}
	public List<FinacingEvent> geteventByInfoidAndOrgId(String infoId,
			String investorgId) {
		return finacingEventDao.geteventByInfoidAndOrgId(infoId, investorgId);
	}
	public FinacingEvent geteventByMangageOrg(String infoId,
			String investorgId,String level) {
		return finacingEventDao.geteventByMangageOrg(infoId, investorgId,level);
	}
	public Long countFocusByUser(String userName) {
		return finacingEventDao.countFocusByUser(userName);
	}
	public List<FinacingEvent> findFocusByUser(String userName) {
		return finacingEventDao.findFocusByUser(userName);
	}
}
