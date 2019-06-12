package com.icfcc.ssrp.web.declareAward;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarInfor;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarReport;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarReportRecord;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarSearshBean;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorRegiter;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.investorDicArea;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.dd.PlatformDicDetailService;
import com.icfcc.SRRPService.declareAward.DeclareRewardService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.PdfUtils;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.session.investorArea;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/investorRewardDeclare")
public class DeclareRewardController extends SRRPBaseController {
	@Autowired
	private InvestorService investorService;
	@Autowired
	private DeclareRewardService declareRewardService;
	@Autowired
	private PlatformUserService serService;
	// �ֵ�
	@Autowired
	private PlatformDicDetailService dicService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static Logger log = LoggerFactory.getLogger(DeclareRewardController.class);
	/**
	 * ��ʼ�������걨ҳ��
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/onlineRewarDeclare")
	public String onlineRewarDeclare(HttpServletRequest request,
			HttpServletResponse response) {
		// ��õ���¼�û�����ҵid
		Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
		// δ��¼ǿ�Ƶ�¼
		if (userInfos.isEmpty()) {
			noLogin(request, response);
		}
		String userId = userInfos.get(SRRPConstant.LOGINUSERID);// ��ȡ�û���id 
		// �����û���id��ѯ�û���Ϣ�û�
		PlatformUser platformUser = serService.getUser(userId);
		Investor invest=investorService.findInverstorById(platformUser.getOrg());
		String InvestorAreaCode=invest.getAreaCode();//���������������
		DeclareRewarSearshBean reward=new DeclareRewarSearshBean();
		if ("3205".equals(InvestorAreaCode.substring(0, 4))) {//��������
			//
			investorArea InvestorArea=dicService.getSuzhouAreaDateByCode(InvestorAreaCode);
			reward.setAreaCounty(InvestorAreaCode);//����
			reward.setAreaCity(InvestorArea.getAreaCity());
			reward.setAreaProvince(InvestorArea.getAreaProvince());
		}else{//������֮��-����ʡ
			investorArea InvestorArea=dicService.getAreaDateByCode(InvestorAreaCode);
			reward.setAreaProvince(InvestorArea.getAreaProvince());
			reward.setAreaCity(InvestorArea.getAreaCity());
			List<investorDicArea> areaCountyList=dicService.getAreaDateByParnetCode(InvestorArea.getAreaCity());
			reward.setAreaCountyList(areaCountyList);
		}
		reward.setInvest(invest);
		reward.setIsQuery("0");
		request.setAttribute("reward", reward);
		return "WEB-INF/views/inverstorg/onlineRewarDeclare";
	}
	/**
	 * ��ʼ���ҵ��걨ҳ��
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/MyRewarDeclare")
	public String MyRewarDeclare(HttpServletRequest request,
			HttpServletResponse response) {
		// ��õ���¼�û�����ҵid
		Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
		// δ��¼ǿ�Ƶ�¼
		if (userInfos.isEmpty()) {
			noLogin(request, response);
		}
		return "WEB-INF/views/inverstorg/MyRewardDeclare";
	}
	
	/**
	 * ��ʼ�������걨ҳ��-��ѯ
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/query")
	public String query(HttpServletRequest request,
			HttpServletResponse response) {
		DeclareRewarSearshBean reward=new DeclareRewarSearshBean();
		// ��õ���¼�û�����ҵid
				Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
				// δ��¼ǿ�Ƶ�¼
				if (userInfos.isEmpty()) {
					noLogin(request, response);
				}
				String userId = userInfos.get(SRRPConstant.LOGINUSERID);// ��ȡ�û���id
				// �����û���id��ѯ�û���Ϣ�û�
				PlatformUser platformUser = serService.getUser(userId);
		Investor invest=investorService.findInverstorById(platformUser.getOrg());
		reward.setInvest(invest);
		// ��õ���¼�û�����ҵid
		initValue(request,reward);//����������� ����checkobx���� 
		List<DeclareRewarInfor> list=declareRewardService.queryDeclareRewarInfor(reward);
		if(list.size()>0){
			reward.setIsQuery("1");//��������
		}else{
			reward.setIsQuery("0");//��������
		}
		
		request.setAttribute("reward", reward);
		request.setAttribute("result", list);
		request.setAttribute("resultJson",JSON.toJSONString(
				list,
				SerializerFeature.DisableCircularReferenceDetect));
		return "WEB-INF/views/inverstorg/onlineRewarDeclare";
	}
	/**
	 * ��ʼ�������걨ҳ��-��ѯ
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public void save(ResultBean rs, HttpServletRequest request,
			HttpServletResponse response) {
		// ��õ���¼�û�����ҵid
		try {
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			String userId = userInfos.get(SRRPConstant.LOGINUSERID);// ��ȡ�û���id
			// δ��¼ǿ�Ƶ�¼
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			String resultString = request.getParameter("result");
			// ��ʼ���ֵ�ֵ
			List<DeclareRewarInfor>  declareList=new ArrayList<DeclareRewarInfor>();
			// ���������Ϊ�ս�����ת���ɶ���
			if (resultString != null
					&& !"".equals(resultString)
					&& !"\"\"".equals(resultString)) {
				declareList = (List<DeclareRewarInfor>) JSON.parseArray(
						resultString, DeclareRewarInfor.class);
			}
			
			for(DeclareRewarInfor declare:declareList){
				Long count=0L;
				declare.setDeclareStatus(SRRPConstant.DECLARER_RECEIVED_NO);
				declare.setDeclareFlag(SRRPConstant.DECLARER_DECLARE_YES);
				declare.setDeclareCreateTime(new Date());
				declare.setDeclareUser(userId);
				if(declareRewardService.FindDeclareRewarInforIsExit(declare)){
				
				List<DeclareRewarReport>  reportList= declare.getDeclareRewarReport();
				List<DeclareRewarReportRecord>  recordList= declare.getDeclareRewarReportRecord();
				List<String> loanIds=new ArrayList<String>();
						for(DeclareRewarReportRecord record:recordList){
							loanIds.add(record.getLoanId());
						}
				count=	declareRewardService.findByRecordCount(loanIds);
				if(count==0){
					DeclareRewarInfor declarerewarinfor =declareRewardService.saveDeclareRewarInfor(declare);
					for(DeclareRewarReportRecord record:recordList){
								record.setDeclareId(declarerewarinfor.getDeclareId());
					}
					
					for(DeclareRewarReport report:reportList){
							report.setDeclareId(declarerewarinfor.getDeclareId());
							declareRewardService.saveDeclareRewarInfor(report);
					}
					declareRewardService.saveDeclareRewarReportRecord(recordList);
				}else{
					rs = new ResultBean(Constant.ERRORCODE3, Constant.ERRORMSG);
                	this.writeJson(rs, request, response);
                	return;
				}
				
				
			  }else{
				  rs = new ResultBean(Constant.ERRORCODE2, Constant.ERRORMSG);
				  this.writeJson(rs, request, response);
				  return;
			  }
			}
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			// TODO: handle exception
		}
		this.writeJson(rs, request, response);
	}
	/**
	 * ��ʼ���ҵ��걨ҳ��
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/MyDeclareListInitInfo")
	@ResponseBody
	public void MyDeclareList(Model model, PageBean page,HttpServletRequest request,HttpServletResponse response) {
		try {
			String queryConditionString = request.getParameter("queryCondition");// ��ѯ����
			String currentPage = request.getParameter("start");// ��ǰҳ
			String maxSize = request.getParameter("limit");// ÿҳ��ʾ������
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			// ��ѯ����������Ҫ����Service,����sqlƴװ
			DeclareRewarSearshBean declareRewardSearsh=new DeclareRewarSearshBean();

			if (queryConditionString != null
					&& !"".equals(queryConditionString)
					&& !"\"\"".equals(queryConditionString)) {
				declareRewardSearsh = (DeclareRewarSearshBean) JSON.parseObject(
						queryConditionString, DeclareRewarSearshBean.class);
			}
			if (StringUtils.isNotBlank(currentPage)) {
				declareRewardSearsh.setCurPage(Integer.parseInt(currentPage));
			}
			if (StringUtils.isNotBlank(maxSize)) {
				declareRewardSearsh.setMaxSize(Integer.parseInt(maxSize));
			}
			declareRewardSearsh.setInvestorId(investorId);
			List<DeclareRewarInfor> dataList=declareRewardService.getDeclareLists(declareRewardSearsh);
			// ��ѯ��¶��Ϣ����
			page.setList(dataList);
			if (CollectionUtils.isNotEmpty(dataList)) {

				page.setList(dataList);
				// �����ܵ�����
				Integer total = new Integer(String.valueOf(declareRewardService.getDeclareCount(declareRewardSearsh)));
				page.setRecordCnt(total);
				if (StringUtils.isNotBlank(maxSize)) {
					page.setMaxSize(Integer.parseInt(maxSize));
				}
				if (StringUtils.isNotBlank(currentPage)) {
					page.setCurPage(Integer.parseInt(currentPage));
				}
				page.pageResult(dataList, page.getRecordCnt(),
						page.getMaxSize(), page.getCurPage());
			}
			// �����ݴ��䵽ǰ��
			this.writeJson(page, request, response);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * ����ע�������
	 * @param rs
	 * @param registerInfo
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/exportRewardDeclareTable")
	@ResponseBody
	public void exportWarrantInfor(ResultBean rs,
			GataWayInvestorRegiter registerInfo, HttpServletRequest request,
			HttpServletResponse respons) {
		try {
			String resultString = request.getParameter("jsonArray");
			PDFMergerUtility PDFmerger = new PDFMergerUtility();
			ByteArrayOutputStream outStream= new ByteArrayOutputStream();
			PDFmerger.setDestinationStream(outStream);
			// ��ʼ���ֵ�ֵ
			List<DeclareRewarInfor>  declareList=new ArrayList<DeclareRewarInfor>();
			// ���������Ϊ�ս�����ת���ɶ���
			if (resultString != null
					&& !"".equals(resultString)
					&& !"\"\"".equals(resultString)) {
				declareList = (List<DeclareRewarInfor>) JSON.parseArray(
			    resultString, DeclareRewarInfor.class);
			}
			String ftlPath = request.getSession().getServletContext().getRealPath("/");
			ftlPath = ftlPath + "WEB-INF/views/inverstorg/";
			for(DeclareRewarInfor declare:declareList){
				ByteArrayOutputStream bos;
				Map<Object, Object> o = new HashMap<Object, Object>();
				o.put("declare", declare);
				bos = PdfUtils.createNewPDF(ftlPath, SRRPConstant.templatesName_rewardDeclare, o);
				ByteArrayInputStream swapStream = new ByteArrayInputStream(bos.toByteArray());
				PDFmerger.addSource(swapStream); 
			}
			PDFmerger.mergeDocuments();
			PdfUtils.renderPdf(respons, outStream.toByteArray(), SRRPConstant.declare_name);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * �ҵ��걨���鿴����
	 * @param rs
	 * @param registerInfo
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/declareDetail")
	public String declareDetail(ResultBean rs,HttpServletRequest request,
			HttpServletResponse respons) {
		try {
			// ��ҳ�������ҵ��id������ҵ��id��ѯ��ҵ����Ϣ
			String declareId = request.getParameter("declareId");
			String userType = RedisGetValue.getRedisUser(request, "orgNo");// �û�����

			// ����id��ѯ������Ϣ
			DeclareRewarInfor declare=declareRewardService.getDeclareInfoById(declareId);
			List<DeclareRewarReport> listReport=declareRewardService.findByDeclarId(declareId);
			declare.setDeclareRewarReport(listReport);
			declare.setDeclareRewarReportJson(JSON.toJSONString(listReport,SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("declare", declare);
			request.setAttribute("userType", userType);

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "WEB-INF/views/inverstorg/declareDetail";

	}
	/**
	 * �ҵ��걨������Pdf
	 * @param rs
	 * @param registerInfo
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/exportTablePdf")
	@ResponseBody
	public void exportTablePdf(ResultBean rs, HttpServletRequest request,
			HttpServletResponse respons) {
		try {
			String declareId = request.getParameter("declareId");
			PDFMergerUtility PDFmerger = new PDFMergerUtility();
			ByteArrayOutputStream outStream= new ByteArrayOutputStream();
			PDFmerger.setDestinationStream(outStream);
			String ftlPath = request.getSession().getServletContext().getRealPath("/");
			ftlPath = ftlPath + "WEB-INF/views/inverstorg/";
			if(declareId!=null){
				DeclareRewarInfor declare=declareRewardService.getDeclareInfoById(declareId);
				List<DeclareRewarReport> listReport=declareRewardService.findByDeclarId(declare.getDeclareId());
				declare.setDeclareRewarReport(listReport);
				ByteArrayOutputStream bos;
				Map<Object, Object> o = new HashMap<Object, Object>();
				o.put("declare", declare);
				bos = PdfUtils.createNewPDF(ftlPath, SRRPConstant.templatesName_rewardDeclare, o);
				ByteArrayInputStream swapStream = new ByteArrayInputStream(bos.toByteArray());
				PDFmerger.addSource(swapStream);
				PDFmerger.mergeDocuments();
				PdfUtils.renderPdf(respons, outStream.toByteArray(), SRRPConstant.declare_name);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * �ҵ��걨������
	 * @param rs
	 * @param registerInfo
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/canacleDeclare")
	@ResponseBody
	public void canacleDeclare(ResultBean rs,HttpServletRequest request,
			HttpServletResponse respons) {
		try {
			// ��ҳ�������ҵ��id������ҵ��id��ѯ��ҵ����Ϣ
			String declareId = request.getParameter("declareId");
			// ����id��ѯ������Ϣ
			DeclareRewarInfor declare=declareRewardService.getDeclareInfoById(declareId);
			if(!SRRPConstant.DECLARER_RECEIVED_YSE.equals(declare.getDeclareStatus())){
				declare.setDeclareStatus(SRRPConstant.DECLARER_RECEIVED_CANCEL);
				declareRewardService.saveDeclareRewarInfor(declare);
				declareRewardService.updateRecord(SRRPConstant.DECLARER_RECEIVED_CANCEL, declareId);
			}
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
		}
		this.writeJson(rs, request, respons);
	}
	/**
	 * ����--����
	 * @param rs
	 * @param registerInfo
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/answerDeclare")
	@ResponseBody
	public void answerDeclare(ResultBean rs,HttpServletRequest request,
			HttpServletResponse respons) {
		try {
			// ��ҳ�������ҵ��id������ҵ��id��ѯ��ҵ����Ϣ
			String declareId = request.getParameter("declareId");
			// ����id��ѯ������Ϣ
			DeclareRewarInfor declare=declareRewardService.getDeclareInfoById(declareId);
			if(!SRRPConstant.DECLARER_RECEIVED_YSE.equals(declare.getDeclareStatus())){
				declare.setDeclareStatus(SRRPConstant.DECLARER_RECEIVED_YSE);
				declareRewardService.saveDeclareRewarInfor(declare);
				declareRewardService.updateRecord(SRRPConstant.DECLARER_RECEIVED_YSE, declareId);
			}
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
		}
		this.writeJson(rs, request, respons);
	}
}
