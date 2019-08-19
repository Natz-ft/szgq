package com.icfcc.ssrp.web.gataway;

import java.io.IOException;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.ctc.wstx.util.StringUtil;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachment;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBasePending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyInfoVo;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformConfigService;
import com.icfcc.SRRPService.PlatformSystem.PlatformRoleService;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.gataway.staticize.GataWayCompanyInfoService;
import com.icfcc.SRRPService.gataway.staticize.GataWayIndexService;
import com.icfcc.SRRPService.util.SmsClientUtil;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisManager;
import com.icfcc.ssrp.web.SRRPBaseController;

import antlr.StringUtils;

/**
 * 门户
 * 
 * @xiaob
 */
@Controller
@RequestMapping(value = "/newCompany")
public class GataWayCompanyController extends SRRPBaseController {

	private static Logger log = LoggerFactory.getLogger(GataWayCompanyController.class);

	// 处理新增企业信息的service
	@Autowired
	private GataWayCompanyInfoService service;
	// 处理用户新增的service
	@Autowired
	private PlatformUserService userService;
	// 处理新增权限的service
	@Autowired
	protected PlatformRoleService roleService;
	// 获取系统参数的service
	@Autowired
	private PlatformConfigService config;
	@Autowired
	private CompanyInfoService commpanService;
	@Autowired
	private RedisManager redisManager;
	@Autowired
	private GataWayIndexService indexservice;

	@RequestMapping(value = "/initDD")
	public void gainDD(HttpServletRequest request, HttpServletResponse response) {
		QueryCondition queryDd = new QueryCondition();
		// 资本类型
		String ddFinancingValus = redisManager.get(SRRPConstant.DD_INVESTMENT);
		queryDd.setFinanceAmout(ddFinancingValus);
		// 机构类型
		String ddIndustrValus = redisManager.get(SRRPConstant.DD_INDUSTRY);
		queryDd.setIndustry(ddIndustrValus);
		// 地区
		String ddAreaValus = redisManager.get(SRRPConstant.DD_AREA);
		queryDd.setArea(ddAreaValus);

		this.writeJson(queryDd, request, response);
	}

	/**
	 * 新增企业补充信息
	 * 
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/addBaseSupplementInfo")
	@ResponseBody
	public void addBaseSupplementInfo(HttpServletRequest request, HttpServletResponse respons) {
		ResultBean rs = null;
		try {
			// 页面传过来的 form 所有的参数值
			String entpriseId = request.getParameter("entpriseId");
			String addform = URLDecoder.decode(request.getParameter("addSuppform"), "utf-8");
			String[] params = addform.split("\\&");
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < params.length; i++) {
				String param = params[i];
				int end = param.indexOf("=");
				map.put(param.substring(0, end), param.substring(end + 1));
			}
			JSONObject jsonObject = JSONObject.fromObject(map);
			CompanyBaseSupplement supplement = (CompanyBaseSupplement) JSON.parseObject(jsonObject.toString(),
					CompanyBaseSupplement.class);
			supplement.setEnterpriseId(entpriseId);
			commpanService.addCompanyBaseSupplement(supplement);

			// 根据表单提交的文件路径和文件名，插入到文件关系对应表
			if (addform.indexOf("&licensePath") > 0) {
				String filesStr = addform.substring(addform.indexOf("&licensePath"), addform.length());
				String[] files = filesStr.split("\\&licensePath");
				for (int j = 1; j < files.length; j++) {
					String file = files[j];
					CompanyAttachment fileInfo = new CompanyAttachment();
					fileInfo.setEnterpriseId(entpriseId);
					fileInfo.setFileName(file.substring(file.lastIndexOf("=") + 1));
					fileInfo.setFilepath(file.substring(file.indexOf("=") + 1, file.indexOf("&")));
					fileInfo.setCreateTime(new Date());
					commpanService.addCompanyAttachment(fileInfo);
				}
			}
			rs = new ResultBean(Constant.SUCCESSCODE, "新增成功！");
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, "新增异常！");
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, respons);
	}

	/**
	 * 新增企业基本信息
	 * 
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/addBascompaseInfo")
	@ResponseBody
	public void addBascompaseInfo(HttpServletRequest request, HttpServletResponse respons) {
		ResultBean rs = null;
		String code="";
		try {
			// 页面传过来的 form 所有的参数值
			String addform = URLDecoder.decode(request.getParameter("addform"), "utf-8");
			String[] params = addform.split("\\&");
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < params.length; i++) {
				String param = params[i];
				int end = param.indexOf("=");
				map.put(param.substring(0, end), param.substring(end + 1));
			}
			JSONObject jsonObject = JSONObject.fromObject(map);
			CompanyBase base = (CompanyBase) JSON.parseObject(jsonObject.toString(), CompanyBase.class);
			code=base.getCode();
			log.info("begin regist  user "+base.getCode()+"====================="+ new Date());
			base.setAuditStatus("00");
			base.setPlatformFlag("00");
			base.setOperdate(new Date());
			base.setCreateTime(new Date());
			service.insertBase(base);
			// 审核表插入记录
			CompanyBasePending basePend = new CompanyBasePending();
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			BeanUtils.copyProperties(base, basePend);
			basePend.setPlatformFlag("00");
			// 新增用户和权限
			CompanyBase newbase = service.queryByCertno(base.getCode());
			
			commpanService.saveCompanyBasePending(basePend);
			boolean addus = addUser(newbase, map.get("password"));
			if (addus) {
				// 新增企业基本信息
				rs = new ResultBean(Constant.SUCCESSCODE, Constant.SUCCESSMSG);
			} else {
				rs = new ResultBean(Constant.ERRORCODE, "新增失败！");
				 newbase = service.queryByCertno(code);
				if(newbase!=null){
					service.deleteByCode(newbase.getCode());
				}
				 basePend=commpanService.findByCode(code);
				if(basePend!=null){
					commpanService.deleteByCode(basePend.getCode());
				}
				PlatformUser user=userService.findUserByUserName(code);
				if(user!=null){
					userService.deleteByInvestId(user.getId());
				}
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, "新增异常！");
			e.printStackTrace();
			log.error(e.getMessage());
			CompanyBase newbase = service.queryByCertno(code);
			if(newbase!=null){
				service.deleteByCode(newbase.getCode());
			}
			CompanyBasePending basePend=commpanService.findByCode(code);
			if(basePend!=null){
				commpanService.deleteByCode(basePend.getCode());
			}
			PlatformUser user=userService.findUserByUserName(code);
			if(user!=null){
				userService.deleteByInvestId(user.getId());
			}
		}
		this.writeJson(rs, request, respons);
	}

	/**
	 * 新增用户和权限
	 * 
	 * @param contno
	 * @return
	 */
	private boolean addUser(CompanyBase base, String password) {
		boolean add = false;
		String code=base.getCode();
		try {
			CompanyInfoVo companyRestVo = new CompanyInfoVo();
			companyRestVo.setName(base.getName());
			companyRestVo.setCodetype(base.getCodetype());
			companyRestVo.setUser_id(base.getCode());
			companyRestVo.setCode(base.getCode());
			companyRestVo.setUser_pwd(password);
			companyRestVo.setBranchno("320508");
			companyRestVo.setContacname(base.getStockName());
			companyRestVo.setContaccal(base.getStockCal());
			companyRestVo.setLegalname(base.getStockName());
			companyRestVo.setLegalcal(base.getStockCal());
			companyRestVo.setStatus("1");
			companyRestVo.setStopFlag("0");
			// 同步企业信息 调用金服平台同步用户信息接口
			Map maps = userService.addCompanyBaseUser(companyRestVo, "01");
			System.out.println("调用信用报告接口结束，返回结果:" + maps.get("validStatus"));
			String validStatus = (String) maps.get("validStatus");
			if ("0".equals(validStatus)) {
				add = false;
			} else {
				PlatformUser user = new PlatformUser();
//				user.setId(newUserId);
				user.setOrg(base.getEnterpriseId());
				user.setStopFlag(1);
				user.setUsername(base.getCode());// 用户名
				user.setNickname(base.getName());// 用户昵称
				user.setTelephone(base.getStockCal());// 联系人名称
				user.setCreateUser(base.getName());// 创建人
				user.setCreateTime(new Date());// 创建时间
				user.setResetFlag(1);// 是否重新修改密码
				user.setPassword(password);
				user.setPwdUpdateTime(new Date());
				user.setUserSource("01");// 来源
				user.setType("02");// 用户类型 企业用户
				userService.addSystemUserNew(user);
				// 新增权限
				relateRole(base.getCode());
				add = true;
			}
			// 新增权限

		} catch (Exception e) {
			add = false;
			CompanyBase newbase = service.queryByCertno(code);
			if(newbase!=null){
				service.deleteByCode(newbase.getCode());
			}
			CompanyBasePending basePend=commpanService.findByCode(code);
			if(basePend!=null){
				commpanService.deleteByCode(basePend.getCode());
			}
			PlatformUser user=userService.findUserByUserName(code);
			if(user!=null){
				userService.deleteByInvestId(user.getId());
			}
			log.error(e.getMessage());
		}
		return add;
	}

	/**
	 * 向角色和用户关系表 新增 企业注册用户的权限
	 * 
	 * @param userName
	 */
	public void relateRole(String userName) {
		try {
			PlatformUser user = userService.findUserByUserName(userName);
			// 需要先删除原有的关联关系，然后存入新的关联关系
			roleService.DelAllRole(user.getId());
			String roleId = config.getConfigValueByName("companyinitrole");
			roleService.InveInsertUserRoleRel(user.getId(), roleId, user.getNickname());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param request
	 * @param respons
	 * @throws IOException
	 */
	@RequestMapping(value = "/sendMessage")
	@ResponseBody
	public void sendMessage(HttpServletRequest request, HttpServletResponse respons) throws IOException {
		boolean result = false;
		try {
			// 验证码用随机数
			int random = (int) ((Math.random() * 9 + 1) * 100000);
			// int random1 =123456;
			String arry[] = new String[1];
			arry[0] = String.valueOf(random);
			// 把随机数放到session 管理
			request.getSession().setAttribute("random", String.valueOf(random));
			// 获取页面输入的联系人电话，调用发送短信的接口
			String contacal = request.getParameter("phone");
			String[] keys = service.keys("1");
			result = SmsClientUtil.querySms(arry, keys, contacal, "1");
		} catch (Exception e) {
			result = false;
			log.error(e.getMessage());
		}
		respons.getWriter().print(result);
	}

	/**
	 * 处理session中的验证码 和 验证 页面输入的验证码和session中的是否一致
	 * 
	 * @param request
	 * @param respons
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkRandom")
	@ResponseBody
	public void checkRandom(HttpServletRequest request, HttpServletResponse respons) throws IOException {
		ResultBean rs = null;
		try {
			// 如果 type = 0 的时候 清除session中存放的 验证码
			String type = request.getParameter("type");
			if ("0".equals(type)) {
				request.getSession().removeAttribute("random");
				rs = new ResultBean(Constant.ERRORCODE, "");
			} else {
				// type == null 的时候
				// 对比 页面传入的 随机码 和 session 中的随机码 对比
				String parmRandom = request.getParameter("random");
				String sessionRandom = (String) request.getSession().getAttribute("random");
				if (null != parmRandom && null != sessionRandom) {
					if (parmRandom.equals(sessionRandom)) {
						rs = new ResultBean(Constant.SUCCESSCODE, "验证码正确！");
						request.getSession().removeAttribute("random");
						System.out.println("验证码正确");
					} else {
						rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
					}
				} else {
					rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
				}
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, respons);

	}

	/**
	 * 异步验证企业证件号码是否存在
	 * 
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/ajaxValidatorCertNo")
	@ResponseBody
	public void ajaxValidatorCertNo(HttpServletRequest request, HttpServletResponse respons) {
		ResultBean rs = null;
		try {
			// 判定是否在业务库存在
			String certNo = request.getParameter("code");
			CompanyInfoVo companyRestVo = new CompanyInfoVo();
			companyRestVo.setCode(certNo);
			companyRestVo.setUser_pwd("1");
			// 同步企业信息 调用金服平台同步用户信息接口
			boolean isBenExist = service.ifRegisteredRC(certNo);
			boolean isExist = false;
			boolean flag = false;
			if (!isBenExist) {
				Map maps = userService.addCompanyBaseUser(companyRestVo, "03");
				String validStatus = (String) maps.get("validStatus");// 验证
																		// 成功，用户不存在
				String result1 = (String) maps.get("result");// 0 未注册 1未注册
																// 2.审核通过
				String source = (String) maps.get("source");// 数据来源 0 金服 1 股权平台
				if (validStatus != null) {
					if ("0".equals(validStatus)) {
						if ("0".equals(source)) {// 金服平台
							isExist = true;
							flag = true;
						} else if ("1".equals(source)) {
							isExist = true;
						} else {
							isExist = false;
						}
					} else {
						isExist = true;
						flag = true;
					}
				}
			} else {
				isExist = true;
			}

			// isExist= service.ifRegisteredRp(certNo);
			if (isExist) {
				if (flag) {
					rs = new ResultBean(Constant.ERRORCODE2, "请勿重复注册！");
				} else {
					rs = new ResultBean(Constant.ERRORCODE, "请勿重复注册！");
				}

			} else {
				rs = new ResultBean(Constant.SUCCESSCODE, "可以注册！");
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, respons);
	}

	@RequestMapping("/fileUpload")
	public void fileUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String fileType = request.getParameter("fileType");
			Map<String, String> map = new HashMap<String, String>();
			map = indexservice.uploadPic(file, fileType);
			this.writeJson(map, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String s = "industry=02&paidincapital=1&iSHiTechPark=0&iSEgggenerator=0&iSHiSchool=0&iSCreativespace=0&iSHolding=0&iSThreeNewBoard=0&iSHItech=0&financingphase=2&hiSchoolNumber=1111&developNumber=1111&tbdescribes=1111&licensePath=../static/files/yyzz/yyzz_75f306cc3d2a44b491686b7679e37723.docx&fileName=1.docx&licensePath=../static/files/yyzz/yyzz_478deb21d8bb437a961abb14d41e6d0f.jpg&fileName=0首页.jpg";
		String b = s.substring(s.indexOf("&licensePath"), s.length());
		System.out.println(b);
		String[] files = b.split("\\&licensePath");
		for (int i = 1; i < files.length; i++) {
			System.out.println(files[i]);
			String a = files[i];
			System.out.println(a.substring(a.indexOf("=") + 1, a.indexOf("&")));
			System.out.println(a.substring(a.lastIndexOf("=") + 1));
		}

	}

	/**
	 * <用户忘记自身密码>
	 *
	 * @param rs
	 * @param password
	 * @param newpassword
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "modifyThePassword")
	@ResponseBody
	public String modifyThePassword(ResultBean rs, HttpServletRequest req) {
		try {

			String username = req.getParameter("username");
			PlatformUser systemUser = userService.findUserByUserName(username);
			String newpassword = req.getParameter("newPassword");
			HttpSession session = req.getSession();
			if ("02".equals(systemUser.getType()) || "0201".equals(systemUser.getType())) {
				CompanyInfoVo companyRestVo = new CompanyInfoVo();
				companyRestVo.setName(systemUser.getNickname());
				companyRestVo.setCode(systemUser.getUsername());
				companyRestVo.setUser_pwd(newpassword);
				Map maps = userService.addCompanyBaseUser(companyRestVo, "02");
				String validStatus = (String) maps.get("validStatus");// 1
																		// 修改密码成功，0修改密码失败
				if (validStatus != null) {
					if ("1".equals(validStatus)) {// 修改密码成功
						// 调用接口修改密码成功，则进行本地的密码修改
						systemUser.setPlainPassword(newpassword);
						userService.changeUserPassword(systemUser);
						session.setAttribute("pwd", "changed");
						rs = ResultBean.sucessResultBean();
					} else {// 修改密码失败
						rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
					}
				}
			} else {
				// 去除原密码
				// 对输入原密码加密后与原密码比较
				systemUser.setPlainPassword(newpassword);
				userService.changeUserPassword(systemUser);
				session.setAttribute("pwd", "changed");
				rs = ResultBean.sucessResultBean();
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 异步验证用户名是否存在
	 * 
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/ajaxValidatorUsername")
	@ResponseBody
	public void ajaxValidatorUsername(HttpServletRequest request, HttpServletResponse respons) {
		ResultBean rs = null;
		try {
			// 判定是否在用户表存在
			String username = request.getParameter("username");
			PlatformUser systemUser = userService.findUserByUserName(username);
			boolean isExist = service.ifRegisteredRp(username);
			if (systemUser != null || isExist) {
				rs = new ResultBean(Constant.SUCCESSCODE, "用户存在！");
				if (systemUser != null) {
					if ("04".equals(systemUser.getType()) || "C".equals(systemUser.getType())) {
						rs = new ResultBean(Constant.ERRORCODE2, "该用户无法修改！");
					}
				}
			} else {
				rs = new ResultBean(Constant.ERRORCODE, "用户不存在！");
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, respons);
	}

	/**
	 * 异步验证手机号是否是预留手机号
	 * 
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/ajaxValidatorContacCal")
	@ResponseBody
	public void ajaxValidatorContacCal(HttpServletRequest request, HttpServletResponse respons) {
		ResultBean rs = null;
		try {
			// 判定是否在用户表存在
			String username = request.getParameter("username");
			CompanyBase company = commpanService.queryByCertno(username);
			Investor investor = indexservice.findInvestor(username);
			PlatformUser systemUser = userService.findUserByUserName(username);
			String stockCal = request.getParameter("stockCal");
			if (investor != null || systemUser != null || company != null) {
				if (investor != null) {
					if (investor.getRelPhone() != null) {
						if (stockCal.equals(investor.getRelPhone())) {
							rs = new ResultBean(Constant.SUCCESSCODE, "用户联系方式正确！");
						} else {
							rs = new ResultBean(Constant.ERRORCODE, "用户联系方式非预留手机号！");
						}
					}
				}
				if (systemUser != null) {
					if (systemUser.getTelephone() != null) {
						if (stockCal.equals(systemUser.getTelephone())) {
							rs = new ResultBean(Constant.SUCCESSCODE, "用户联系方式正确！");
						} else {
							rs = new ResultBean(Constant.ERRORCODE, "用户联系方式非预留手机号！");
						}
					}
				}
				if (company != null) {
					if (company.getStockCal() != null) {
						if (stockCal.equals(company.getStockCal())) {
							rs = new ResultBean(Constant.SUCCESSCODE, "用户联系方式正确！");
						} else {
							rs = new ResultBean(Constant.ERRORCODE, "用户联系方式非预留手机号！");
						}
					} else {
						rs = new ResultBean(Constant.ERRORCODE2, "用户未预留手机号，请联系管理员修改密码。");
					}
				}
			} else {
				rs = new ResultBean(Constant.ERRORCODE, "用户联系方式非预留手机号！");
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, respons);
	}
}
