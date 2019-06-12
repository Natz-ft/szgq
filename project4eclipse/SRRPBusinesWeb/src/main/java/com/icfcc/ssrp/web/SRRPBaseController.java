package com.icfcc.ssrp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.companyInfo.CapCompanyInfoVo;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarSearshBean;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorRegiter;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 * 
 * @zhanglf
 */
public class SRRPBaseController {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public void writeJson(Object object, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			String json = JSON.toJSONString(object,
					SerializerFeature.DisableCircularReferenceDetect);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	public void noLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			String url = "http://" + request.getServerName() // 服务器地址
					+ ":" + "/creditplatformweb/login";
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMyFina(HttpServletRequest request, HttpServletResponse response) {
		try {
			String url = "http://" + request.getServerName() // 服务器地址
					+ ":" + "/SRRPBusinesWeb/finacingDemand/demandList";
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Map<String, String> getUserInfo(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
		// 未登录强制登录
		if (userInfos == null || userInfos.isEmpty()) {
			noLogin(request, response);
		}
		return userInfos;
	}
	  /**
     * 取出Shiro中的当前用户.
     */
    public ShiroUser getCurrentUser()
    {
        ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
        return user;
    }
    /**
	 * 接收数组变量 ，如checkobx类型 
	 * @param request
	 * @param infoSearch
	 */
	public void initValue(HttpServletRequest request,CapCompanyInfoVo infoSearch) {
		infoSearch.setScale(request.getParameterValues("scale"));

		infoSearch.setRegion(request.getParameterValues("region"));

		infoSearch.setIndustry(request.getParameterValues("industry"));

		infoSearch.setRegister_type(request.getParameterValues("register_type"));

		infoSearch.setInvestment_type(request.getParameterValues("investment_type_map"));

		infoSearch.setYearnum(request.getParameterValues("bizTerm"));

		infoSearch.setAuth(request.getParameterValues("auth_institution"));

		infoSearch.setLend(request.getParameterValues("lend_institution"));

		infoSearch.setLoan_status(request.getParameterValues("loan_status"));

		infoSearch.setHg_status(request.getParameterValues("hg_status"));

		infoSearch.setAq_status(request.getParameterValues("aq_status"));

		infoSearch.setNashui_status(request.getParameterValues("nashui_status"));

		infoSearch.setHb_status(request.getParameterValues("hb_status"));

		infoSearch.setRongyu_status(request.getParameterValues("rongyu_status"));

		infoSearch.setPffzstart(request.getParameterValues("pffzstart"));

		infoSearch.setPffzend(request.getParameterValues("pffzend"));

		infoSearch.setXyfdj(request.getParameterValues("xyfdj"));

		infoSearch.setSs_status(request.getParameterValues("ss_status"));

		infoSearch.setYhjr_status(request.getParameterValues("yhjr_status"));

	}
	public String validate(GataWayInvestorRegiter registerInfo){
		if(StringUtils.isEmpty(registerInfo)){
			return "注册失败，请重新注册";
		}else{
			if(StringUtils.isEmpty(registerInfo.getName())){
            	return "注册失败，请重新注册";
			}
            if(StringUtils.isEmpty(registerInfo.getCertno())){
            	return "证件号不能为空";
			}
            if(StringUtils.isEmpty(registerInfo.getCerttype())){
            	return "证件类型不能为空";
			}
            if(StringUtils.isEmpty(registerInfo.getRelName())){
            	return "联系人姓名不能为空";
			}
            if(StringUtils.isEmpty(registerInfo.getRelPhone())){
            	return "联系人验证码不能为空";
			}
            if(StringUtils.isEmpty(registerInfo.getPassword())){
            	return "密码不能为空";
			}
            
		}
		return "";
	}
	public void initValue(HttpServletRequest request,DeclareRewarSearshBean infoSearch) {
		try {
			infoSearch.setInvestorId(request.getParameter("investorId"));
			infoSearch.setDeclareName(request.getParameter("declareName"));
			infoSearch.setCertno(request.getParameter("certno"));
			infoSearch.setRelName(request.getParameter("relName"));
			infoSearch.setRelPhone(request.getParameter("relPhone"));
			infoSearch.setAreaProvince(request.getParameter("areaProvince"));
			infoSearch.setAreaCity(request.getParameter("areaCity"));
			infoSearch.setAreaCounty(request.getParameter("areaCounty"));
			infoSearch.setBankDeposit(request.getParameter("bankDeposit"));
			infoSearch.setBankAccount(request.getParameter("bankAccount"));
			infoSearch.setDeclare_begin_time(sdf.parse(request.getParameter("declare_begin_time")));
			infoSearch.setDeclare_end_time(sdf.parse(request.getParameter("declare_end_time")));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
