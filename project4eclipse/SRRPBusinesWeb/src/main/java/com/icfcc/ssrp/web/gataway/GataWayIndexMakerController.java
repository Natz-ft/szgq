package com.icfcc.ssrp.web.gataway;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorRegiter;
import com.icfcc.SRRPDao.jpa.entity.managedept.InvestorHistory;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformFaqShow;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.InvestorSubaccount;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.InvestorSubaccountService;
import com.icfcc.SRRPService.PlatformSystem.PlatformConfigService;
import com.icfcc.SRRPService.PlatformSystem.PlatformRoleService;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.gataway.staticize.GataWayIndexService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.session.RedisManager;
import com.icfcc.ssrp.web.SRRPBaseController;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 门户首页
 * 
 * @zhanglf
 */
@Controller
@RequestMapping(value = "/index")
public class GataWayIndexMakerController extends SRRPBaseController {
	private static Logger log = LoggerFactory.getLogger(GataWayIndexMakerController.class);

	@Autowired
	private RedisManager redisManager;
	@Autowired
    protected PlatformRoleService roleService;
	@Autowired
	private GataWayIndexService service;
	@Autowired
	private PlatformConfigService config;
	@Autowired
	private PlatformUserService userService;
	@Autowired      
    private InvestorSubaccountService investorSubaccountService;
	
	
	@RequestMapping(value = "initIndex")
	public void initIndex(HttpServletRequest request,
			HttpServletResponse response) {
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		try { 	
			service.makeIndexHtml();
			service.makeFinaDemandDetail();
			service.makeNewsDetail();
			service.makeInvestorDetail();
			executeResult = SRRPConstant.EXECUTSUCC;
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		Map<String,String> resultMap =  new HashMap<String,String>();
		resultMap.put("code", executeResult);
		resultMap.put("msg", msg);
		//发送客户端响应码
		this.writeJson(resultMap, request, response);
	}

	/**
	 * 在线注册
	 * 
	 * @param
	 */
	@RequestMapping(value = "/addInvestorInfos")
	@ResponseBody
	public void addInvestorInfos(ResultBean rs,
			GataWayInvestorRegiter registerInfo, HttpServletRequest request,
			HttpServletResponse respons) {
		try {
			//增加后台校验
			String validateMessage=validate(registerInfo);
			if(!validateMessage.equals("")){
				rs = new ResultBean(Constant.ERRORCODE, validateMessage);
			}else{
				boolean isExist = service.ifRegisteredRp(registerInfo.getCertno());
				if (isExist) {
					rs = new ResultBean(Constant.ERRORCODE, "请勿重复注册！");
				} else {
					InvestorHistory hisInfos = service
							.findRegisterHisInfos(registerInfo.getCertno());
					if (hisInfos != null) {
						registerInfo.setInvestorId(hisInfos.getInvestorId());
					} else {
						registerInfo.setInvestorId(UUID.randomUUID().toString().replace("-", ""));
					}
					// 入业务库
					Investor infos = new Investor();
					BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
					BeanUtils.copyProperties(infos, registerInfo);
					infos.setAuditStatus("0");
	//				infos.setRegisteTime(new Date());
					infos.setBaseFlag("0");//第一次注册  标识 为0
					infos.setPerFlag("0");
					infos.setCreateTime(new Date());
					infos.setOperdate(new Date());
					service.inverstorRegisterSrrp(infos);
					// 入门户库
					// service.inverstorRegister(registerInfo);
					//用户表初始化一条记录
					PlatformUser SystemUser = new PlatformUser();
					SystemUser.setOrg(infos.getInvestorId());
					SystemUser.setPassword(registerInfo.getPassword());
					SystemUser.setUsername(registerInfo.getCertno());
					SystemUser.setType("01");//投资机构
					SystemUser.setStopFlag(1);
		        	SystemUser.setTelephone(registerInfo.getRelPhone());
		        	SystemUser.setCreateUser(registerInfo.getCertno());
		        	SystemUser.setCreateTime(new Date());
		            SystemUser.setNickname(registerInfo.getName());
		            SystemUser.setDesc1("0");
					userService.addSystemUserNew(SystemUser);
					relateRole(registerInfo.getCertno());//赋予用户角色权限
					rs = ResultBean.sucessResultBean();
				}
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, respons);
	}
	
	
	 public void relateRole(String userName) {//赋予用户角色
	        try {
	        	PlatformUser user=userService.findUserByUserName(userName);
	            //需要先删除原有的关联关系，然后存入新的关联关系
	            roleService.DelAllRole(user.getId());
            String roleId=config.getConfigValueByName("invesSubUserRole");
//	            String roleId="54";
	            roleService.InveInsertUserRoleRel(user.getId(), roleId,user.getNickname());
	        } catch (Exception e) {
	           e.printStackTrace();
	        }
	    }
	@RequestMapping(value = "/initDD")
	public void gainDD(HttpServletRequest request, HttpServletResponse response) {
		QueryCondition queryDd = new QueryCondition();
		// 机构类型
		String ddOrgTypeValus = redisManager.get(SRRPConstant.DD_ORGTYPE);
		queryDd.setOrgType(ddOrgTypeValus);
		// 地区
		String ddAreaValus = redisManager.get(SRRPConstant.DD_AREA);
		queryDd.setArea(ddAreaValus);
		// 资本类型
		String ddCapTypeValus = redisManager.get(SRRPConstant.DD_CAPITALTYPE);
		queryDd.setCapitalType(ddCapTypeValus);
		// 拟投资行业
		String ddIndustryValus = redisManager.get(SRRPConstant.DD_INDUSTRY);
		queryDd.setFinanceTrade(ddIndustryValus);
		// 拟投资阶段
		String ddInvestMentValus = redisManager.get(SRRPConstant.DD_INVESTMENT);
		queryDd.setFinanceStage(ddInvestMentValus);
		this.writeJson(queryDd, request, response);
	}

	/**
	 * 
	 * @Title: fileUpload
	 * @Description: 营业执照上传
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws
	 */
	@RequestMapping("/fileUpload")
	public void fileUpload(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String fileType = request.getParameter("fileType");
			Map<String, String> map = new HashMap<String, String>();
			String imgPath=request.getSession().getServletContext().getRealPath("/");
			map = service.uploadPic(file, fileType,imgPath);
			this.writeJson(map, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 动态验证
	 * 
	 * @param
	 */
	@RequestMapping(value = "/ajaxValidatorCertNo")
	@ResponseBody
	public void ajaxValidatorCertNo(HttpServletRequest request,
			HttpServletResponse respons) {
		ResultBean rs = null;
		try {
			// 判定是否在业务库存在
			String certNo = request.getParameter("certno");
			InvestorSubaccount subaccount=investorSubaccountService.findByCertNo(certNo);
			boolean isExist = service.ifRegisteredRp(certNo);
			if (isExist) {
				rs = new ResultBean(Constant.ERRORCODE, "请勿重复注册！");
			}else{
			    if(subaccount!=null){
			        rs = new ResultBean(Constant.ERRORCODE2, "请勿重复注册！");
			    }else{
			        rs = new ResultBean(Constant.SUCCESSCODE, "可以注册！");
			    }
				
				
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, respons);
	}
	/**
	 * 动态验证
	 * 
	 * @param
	 */
	@RequestMapping(value = "/ajaxIsLogin")
	@ResponseBody
	public void ajaxIsLogin(HttpServletRequest request,
			HttpServletResponse respons) {
		ResultBean rs = null;
		try {
			// 判定是否在业务库存在
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			if (userInfos.isEmpty()) {
				rs = new ResultBean(Constant.ERRORCODE, "请勿重复注册！");
			}else{
				rs = new ResultBean(Constant.SUCCESSCODE, "可以注册！");
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, respons);
	}
	
	@ResponseBody
	@RequestMapping("/getFaq")
	public Map getFaq(@RequestParam(required = false) String id) {
		PlatformFaqShow faq = (PlatformFaqShow) service.findFaqDy(id);
		Map<String,Object> map = new HashMap();
		map.put("problem", faq.getProblem());
		map.put("answer", faq.getAnswer());
		map.put("faqDate", faq.getFaqDate());
		map.put("faqFilename", faq.getFilename());
		map.put("faqFilepath", faq.getFilepath());
		map.put("indexTitle", faq.getIndexTitle());
		map.put("indexSubTitle", faq.getIndexSubTitle());
		map.put("indexFilepath", faq.getIndexFilepath());
		return map;
	}
	
	/**
	 */
	@RequestMapping(value = "/changeJobStatus")
	@ResponseBody
	public void changeJobStatus(HttpServletRequest request,HttpServletResponse respons) {
		Map<String,Object> map = new HashMap();
		String status = request.getParameter("status");
		try {
			service.changeJobStatus(status);
			map.put("code", "01");
			map.put("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "02");
			map.put("msg", e.getMessage());
		}
		this.writeJson(map, request, respons);
	}
	
	/**
	 */
	@RequestMapping(value = "/changeEnterpriseCount")
	@ResponseBody
	public void changeEnterpriseCount(HttpServletRequest request,HttpServletResponse respons) {
		Map<String,Object> map = new HashMap();
		String cs = request.getParameter("count");
		int count = Integer.valueOf(cs); 
		try {
			service.changeEnterpriseCount(count);
			map.put("code", "01");
			map.put("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "02");
			map.put("msg", e.getMessage());
		}
		this.writeJson(map, request, respons);
	}
	
	
	public static void main(String args[]) {
	}
}
