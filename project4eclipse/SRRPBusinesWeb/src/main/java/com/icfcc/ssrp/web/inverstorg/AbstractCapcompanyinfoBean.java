package com.icfcc.ssrp.web.inverstorg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.icfcc.SRRPDao.jpa.entity.companyInfo.CapCompanyInfoVo;
import com.icfcc.SRRPDao.jpa.entity.companyInfo.SysLookupvalueDto;


/**
 * codecomment:
 * 
 * @author zhaowei
 * @time 16-11-25 18:34:43
 * @generated 子系统: infofilter 模块:companyinfofilter 组件:Capcompanyinfo
 */
public abstract class AbstractCapcompanyinfoBean {
	
	String cntSql = null;
	CapCompanyInfoVo companyInfoVo = null;
	private Object scale;
	private Object region;
	private Object industry;
	private Object register_type;
	private Object investment_type;
	private Object yearnum;
	private Object auth;
	private Object lend;
	private Object loan_status;
	private Object hg_status;
	private Object aq_status;
	private Object nashui_status;
	private Object hb_status;
	private Object rongyu_status;
	private Object pffzstart;
	private Object pffzend;
	private Object xyfdj;
	private Object ss_status;
	private Object yhjr_status;
	private Object authFlagYes;
	private Object authFlagNo;
	// 信息模板
	private String[] templatedesc;
	String filename = null;
	String reason = "";
	private List<SysLookupvalueDto> scoreRatingList = null;

	public void initValue(HttpServletRequest request) {
		scale =request.getParameterValues("scale");

		region =request.getParameterValues("region");

		industry =request.getParameterValues("industry");

		register_type =request.getParameterValues("register_type");

		investment_type =request.getParameterValues("investment_type_map");

		yearnum =request.getParameterValues("bizTerm");

		auth =request.getParameterValues("auth_institution");

		lend =request.getParameterValues("lend_institution");

		loan_status =request.getParameterValues("loan_status");

		hg_status =request.getParameterValues("hg_status");

		aq_status =request.getParameterValues("aq_status");

		nashui_status =request.getParameterValues("nashui_status");

		hb_status =request.getParameterValues("hb_status");

		rongyu_status =request.getParameterValues("rongyu_status");

		pffzstart =request.getParameterValues("pffzstart");

		pffzend =request.getParameterValues("pffzend");

		xyfdj =request.getParameterValues("xyfdj");

		ss_status =request.getParameterValues("ss_status");

		yhjr_status =request.getParameterValues("yhjr_status");


		authFlagYes = this.companyInfoVo.getAuthFlagYes();

		authFlagNo = this.companyInfoVo.getAuthFlagNo();
	}
//
//	public String export() {
//		if (this.getPagingContext().getDatas() == null || this.getPagingContext().getDatas().size() == 0) {
//			saveErrors2("没有要导出的内容", null, "");
//			this.filename = null;
//			return "";
//		}
//		try {
//			UNIBIZ2LoginInfo resultInfo = (UNIBIZ2LoginInfo) this.getLoginInfo();
//			List<CapExportColumnVO> list = CapUtil.getInfoFilterExportColumnAuth(resultInfo);
//			// 构造title
//			List<ColumnData> columnDataList = new ArrayList<ColumnData>();
//
//			for (CapExportColumnVO columnVO : list) {
//				if (columnVO != null) {
//					columnDataList.add(ColumnData.genColumnData(columnVO.getColumnname(), columnVO.getColumncode(), null));
//				}
//			}
//			// 设置导出类型
//			ProjectType._type = ProjectType.Type.jsf;
//			// 获取参数
//			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
//			String filepath = context.getInitParameter("filepath_persistent");
//
//			// 调用导出的后台方法
//			filename = this.capcompanyinfoService.export(columnDataList, filepath, this.getPageRequest(), getBooterInfo());
//			// 导出文件的位置
//			this.filename = filename;
//
//			// 更新日志表
//			CapInfoFilterLogDto newDto = new CapInfoFilterLogDto();
//			newDto.setSeqid(logSeqId);
//			CapInfoFilterLogDto dto = capcompanyinfoService.findByKey(newDto);
//			dto.setExportfile(filename);
//			dto.setExportdate(new Timestamp(new Date().getTime()));
//			capcompanyinfoService.updateQueryLogDto(dto);
//		} catch (Exception e) {
//			saveErrors2("信息筛选导出信息出错", e, "");
//			log.error("信息筛选导出信息出错", e);
//			e.printStackTrace();
//		}
//
//		return "";
//	}
//
//	/**
//	 * Direction: 查看征信报告 ename: viewReport 引用方法: * viewers: * messages:
//	 */
//	public String viewReport() {
//		String corpId = this.selectobj.getCorpid();
//		String back = "";
//		String beanname = "secsReportBean";
//		try {
//			FacesContext fcontext = FacesContext.getCurrentInstance();
//			// Object bean =
//			// fcontext.getExternalContext().getSessionMap().get(beanname);
//			fcontext.getExternalContext().getSessionMap().remove(beanname);
//			// if (bean == null) {
//			Object bean = fcontext.getApplication().getELResolver().getValue(fcontext.getELContext(), null, beanname);
//			BeanUtil.setProperty(bean, "backStr", "capcompanyinfocapcompanyinfosearchViewdefault");
//			BeanUtil.setProperty(bean, "reason", reason);
//			// }
//			MethodUtils.invokeMethod(bean, "setCorpId", corpId);
//			MethodUtils.invokeMethod(bean, "setQueryType", CapConstant.CREDIT_REPORT_TYPE.XXSX);
//			back = (String) MethodUtils.invokeMethod(bean, "toDefaultPage", null);
//		} catch (Exception e) {
//			log.error("查询信用报告异常", e);
//			// this.saveErrors("您没有查询该企业信用报告的权限!", e, null);
//			// saveErrors2("您没有查询该企业信用报告的权限!", null, "");
//			if (e.getCause() instanceof Unibiz2MessageException) {
//				// log.error("-登录失败-", e);
//				saveErrors2("您没有查询该企业信用报告的权限!", null, "");
//			} else {
//				saveErrors2(e.getCause().getMessage(), e, "");
//			}
//			return "reporterror";
//		}
//		// return "reportViewPage";
//		return back;
//	}
//
//	public String queryReport() {
//		UNIBIZ2LoginInfo loginUser = (UNIBIZ2LoginInfo) this.getLoginInfo();
//		try {
//			queryDate = creditScoreService.queryLastQueryDate(loginUser.getUseridstr(), selectobj.getCorpid());
//		} catch (Exception e) {
//			saveErrors2("获取最近一次查询时间出错", e, "");
//			log.error("获取最近一次查询时间出错", e);
//		}
//		return "";
//	}
//
//	public int find() {
//		try {
//			// 若用到多选列操作，一定清理，否则map里面的值会越来越多
//			clearSelectedDtos();
//			PageResponse pr = capcompanyinfoService.query(this.getPageRequest(), getBooterInfo());
//			pagingContext.setPage(pr);
//			return pr.getTotalCount();
//		} catch (Exception e) {
//			saveErrors2("查询信息出错", e, "");
//			log.error("查询信息出错", e);
//			return -1;
//		}
//
//	}
//
//	public void count(int tocalcount) {
//		PageResponse pr;
//		try {
//			analysisList = capcompanyinfoService.count(this.getPageRequest(), getBooterInfo(), tocalcount);
//		} catch (Exception e) {
//			saveErrors2("统计信息出错", e, "");
//			log.error("统计信息出错", e);
//		}
//	}

	public Map getBooterInfo() {

		Map booter = new HashMap();

		// Booter的封装
		booter.put("dto", companyInfoVo); // 拼接SQL使用的DTO
		// 获取规模
		booter.put("scale", this.scale);
		// //区域
		booter.put("region", this.region);
		// 行业
		booter.put("industry", this.industry);
		// 登记注册类型
		booter.put("register_type", this.register_type);
		// 投资类型
		booter.put("investment_type", this.investment_type);
		// 经营年限
		booter.put("yearnum", this.yearnum);
		// 授权机构
		booter.put("auth", this.auth);
		// 放贷机构
		booter.put("lend", this.lend);
		// 小额贷款状态
		booter.put("loan_status", this.loan_status);
		// 海关评级
		booter.put("hg_status", this.hg_status);
		// 安全事故记录
		booter.put("aq_status", this.aq_status);
		// 税务部门评级
		booter.put("nashui_status", this.nashui_status);
		// 环保信用评价
		booter.put("hb_status", this.hb_status);
		// 荣誉表彰
		booter.put("rongyu_status", this.rongyu_status);
		// 评分分值开始
		booter.put("pffzstart", this.pffzstart);
		// 评分分值结束
		booter.put("pffzend", this.pffzend);
		// 信用分等级
		booter.put("xyfdj", this.xyfdj);
		// 涉诉
		booter.put("ss_status", this.ss_status);
		// 银行业金融机构贷款状态
		booter.put("yhjr_status", this.yhjr_status);

		// 是否授权 - 是
		booter.put("authFlagYes", this.authFlagYes);
		// 是否授权 - 否
		booter.put("authFlagNo", this.authFlagNo);


		return booter;
	}


	//

	public String getCntSql() {
		return cntSql;
	}

	public void setCntSql(String _cntSql) {
		cntSql = _cntSql;
	}

	// 20161128
	public CapCompanyInfoVo getCompanyInfoVo() {
		return companyInfoVo;
	}

	public void setCompanyInfoVo(CapCompanyInfoVo companyInfoVo) {
		this.companyInfoVo = companyInfoVo;
	}


	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	

	public List<SysLookupvalueDto> getScoreRatingList() {
		return scoreRatingList;
	}

	public void setScoreRatingList(List<SysLookupvalueDto> scoreRatingList) {
		this.scoreRatingList = scoreRatingList;
	}

}

