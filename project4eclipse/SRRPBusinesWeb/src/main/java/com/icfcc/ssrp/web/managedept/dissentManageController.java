package com.icfcc.ssrp.web.managedept;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.ctc.wstx.util.StringUtil;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyInfoVo;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageDissentInfor;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageHisDissentInfor;
import com.icfcc.SRRPDao.jpa.entity.managedept.MessageAlertInfo;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.SRRPService.managedept.ManageDissentService;
import com.icfcc.SRRPService.util.AESUtil;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.MD5;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: dissentManageController
 * @Description: TODO(主管针对在“企业信息管理”和“机构信息管理”模块新增异议操作)
 * @author
 * @date 2018年12月25日 上午9:03:27
 */
@Slf4j
@Controller
@RequestMapping(value = "/dissent")
public class dissentManageController extends SRRPBaseController {

    private static Logger _log = LoggerFactory.getLogger(dissentManageController.class);
  
    @Autowired
    private InvestorService investorService;
    @Autowired
    private ManageDissentService manageDissentService;
    @Autowired
    private PlatformUserService userService;
    @Autowired
    private CompanyInfoService companyInfoService;
   
    /**
     * 
     * 方法说明：区/县金融办对企业信息提交异议 方法名：controllerTest
     * 
     * @param request
     * @param response
     * @return 返回值：String
     */
    @RequestMapping(value = "/callCompanyDissent")
    public String controllerTest(HttpServletRequest request, HttpServletResponse response) {
        String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
        String userId = RedisGetValue.getRedisUser(request, "userId");
        PlatformUser platformuser= userService.findByUserID(userId);
        String userName = platformuser.getNickname();
        String enterpriseId=request.getParameter("enterpriseId");
        String type=request.getParameter("type");
        //1.如果是第一次提议则新增。如果是已提交状态，修改后的覆盖已存在，。如果是解除状态，从新提交异议
        ManageDissentInfor manageDissentInfor=manageDissentService.findDissentBycompanyId(enterpriseId);
        List<ManageHisDissentInfor> manageHisDissentInfors= manageDissentService.findHisDissentBycompanyId(enterpriseId);
        if(manageDissentInfor==null){
            manageDissentInfor=new ManageDissentInfor();
            manageDissentInfor.setDissentStatus(SRRPConstant.DISSENT_STATUS_00);
            manageDissentInfor.setDissentType(type);
            manageDissentInfor.setUserType(userType);
            manageDissentInfor.setCompanyId(enterpriseId);
            manageDissentInfor.setOperUserId(userId);
            manageDissentInfor.setOperUserName(userName);
        }else{
            if(SRRPConstant.DISSENT_STATUS_03.equals(manageDissentInfor.getDissentStatus())){
                manageDissentInfor.setFileName("");
                manageDissentInfor.setFilePath("");
                manageDissentInfor.setDissentContent("");
            }
            if(SRRPConstant.USER_TYPE_03.equals(userType)){//如果是区县金融办用户查看历史纪录，若异议为刚解除的则新增历史记录中
                if(SRRPConstant.DISSENT_STATUS_03.equals(manageDissentInfor.getDissentStatus())){
                    ManageHisDissentInfor manageHisDissentInfor = new ManageHisDissentInfor();
                    // 保存异议历史记录
                    BeanUtils.copyProperties(manageDissentInfor, manageHisDissentInfor);
                    List<ManageHisDissentInfor> hisDissentInfors=new ArrayList<ManageHisDissentInfor>();
                    hisDissentInfors.add(manageHisDissentInfor);
                    for(ManageHisDissentInfor his:manageHisDissentInfors){
                        hisDissentInfors.add(his);
                    }
                    manageHisDissentInfors=null;
                    manageHisDissentInfors=hisDissentInfors;
                }
            }
                for(ManageHisDissentInfor his:manageHisDissentInfors){
                    if(SRRPConstant.DISSENT_STATUS_01.equals(his.getDissentStatus())){
                        his.setDissentStatusStr("新增");
                    }else  if(SRRPConstant.DISSENT_STATUS_02.equals(his.getDissentStatus())){
                        his.setDissentStatusStr("修改");
                    }else if(SRRPConstant.DISSENT_STATUS_03.equals(his.getDissentStatus())){
                        his.setDissentStatusStr("解除");
                        his.setDissentContent(his.getCancelContent());
                        his.setFileName(his.getCancelFileName());
                        his.setFilePath(his.getCancelFilePath());
                    }
                }
        }
        request.setAttribute("manageHisDissentInfor", net.sf.json.JSONArray.fromObject(manageHisDissentInfors));
        request.setAttribute("dissent", manageDissentInfor);
        return "WEB-INF/views/managedept/companyDissent";
    }
    /**
     * 
     * @Title: getcompaniesList
     * @Description: TODO(提交异议方法)
     * @author
     * @date 2018年12月25日 上午9:04:31
     * @param model
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping("/saveDissent")
    @ResponseBody
    public void getcompaniesList(ResultBean rs, HttpServletRequest request,
        HttpServletResponse response){
        try {
            String manageDissentInforString = request.getParameter("manageDissentInfor");
            ManageDissentInfor manageDissentInfor = new ManageDissentInfor();
            if (manageDissentInforString != null && !"".equals(manageDissentInforString)
                && !"\"\"".equals(manageDissentInforString)) {
                manageDissentInfor = (ManageDissentInfor)JSON.parseObject(manageDissentInforString, ManageDissentInfor.class);
            }
            if(!SRRPConstant.DISSENT_STATUS_00.equals(manageDissentInfor.getDissentStatus())){//如果不是新增操作
                //修改前
                ManageDissentInfor copyManageDissentInfor=manageDissentService.findDissentById(manageDissentInfor.getDissentId());
                //历史记录
                ManageHisDissentInfor manageHisDissentInfor = new ManageHisDissentInfor();
                // 保存异议历史记录
                BeanUtils.copyProperties(copyManageDissentInfor, manageHisDissentInfor);
                manageDissentService.saveHisDissent(manageHisDissentInfor);
                manageDissentInfor.setOperUpdateDate(new Date());
                if(SRRPConstant.DISSENT_STATUS_00.equals(manageDissentInfor.getDissentStatus())){
                    manageDissentInfor.setDissentStatus(SRRPConstant.DISSENT_STATUS_01);
                }else {
                    manageDissentInfor.setDissentStatus(SRRPConstant.DISSENT_STATUS_02);
                }
            }else{//新增
                manageDissentInfor.setOperDate(new Date());
                manageDissentInfor.setOperUpdateDate(new Date());
                manageDissentInfor.setDissentStatus(SRRPConstant.DISSENT_STATUS_01);
            }
            
            ManageDissentInfor  dissentInfor=manageDissentService.saveDissent(manageDissentInfor);
            //新增消息提醒记录
            List< MessageAlertInfo> messageAlertInfos =new ArrayList< MessageAlertInfo>();
            List<PlatformUser> users=userService.findSjrbUser();
            String  baseName="";
            if(SRRPConstant.MESSAGE_TYPE_00.equals(dissentInfor.getDissentType())){
                CompanyBase base=companyInfoService.getCompanyBase(dissentInfor.getCompanyId());
                  baseName=base.getName();
            }else{
                Investor invest=  investorService.findInverstorById(dissentInfor.getCompanyId());
                baseName=invest.getName();
            }
            
            String oper=dissentInfor.getOperUserName()+"对"+baseName;
            if(SRRPConstant.DISSENT_STATUS_01.equals(manageDissentInfor.getDissentStatus())){
                oper=oper+"新增了异议";
            }else if(SRRPConstant.DISSENT_STATUS_02.equals(manageDissentInfor.getDissentStatus())){
                oper=oper+"修改了异议";
            }
            for(PlatformUser user:users){
                MessageAlertInfo messageAlertInfo =new MessageAlertInfo();
                messageAlertInfo.setCompanyId(dissentInfor.getCompanyId());
                messageAlertInfo.setMeassgeContent(oper);
                messageAlertInfo.setMessageType(dissentInfor.getDissentType());//未读
                messageAlertInfo.setMessagEventId(dissentInfor.getDissentId());
                messageAlertInfo.setMessagUserId(user.getId());
                messageAlertInfo.setOperDate(new Date());
                messageAlertInfo.setOperUserId(dissentInfor.getOperUserId());
                messageAlertInfos.add(messageAlertInfo);
            }
            manageDissentService.saveMessageAlertInfo(messageAlertInfos);
            // 将数据传输到前端
            rs = ResultBean.sucessResultBean();
        } catch (Exception e) {
            _log.error(e.getMessage());
            e.printStackTrace();
            rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
        }
        this.writeJson(rs, request, response);
    }
    /**
     * 
     * 方法说明：查看异议历史记录 方法名：dissentDitailHistory
     * 
     * @param request
     * @param response
     * @return 返回值：String
     */
    @RequestMapping(value = "/dissentDitailHistory")
    public String dissentDitailHistory(HttpServletRequest request, HttpServletResponse response) {
        String enterpriseId=request.getParameter("enterpriseId");
        //1.如果是第一次提议则新增。如果是已提交状态，修改后的覆盖已存在，。如果是解除状态，从新提交异议
        ManageDissentInfor manageDissentInfor=manageDissentService.findDissentBycompanyId(enterpriseId);
        List<ManageHisDissentInfor> manageHisDissentInfors= manageDissentService.findHisDissentBycompanyId(enterpriseId);
        String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
        if(SRRPConstant.USER_TYPE_03.equals(userType)){//如果是区县金融办用户查看历史纪录，若异议为刚解除的则新增历史记录中
            if(SRRPConstant.DISSENT_STATUS_03.equals(manageDissentInfor.getDissentStatus())){
                ManageHisDissentInfor manageHisDissentInfor = new ManageHisDissentInfor();
                // 保存异议历史记录
                BeanUtils.copyProperties(manageDissentInfor, manageHisDissentInfor);
                List<ManageHisDissentInfor> hisDissentInfors=new ArrayList<ManageHisDissentInfor>();
                hisDissentInfors.add(manageHisDissentInfor);
                for(ManageHisDissentInfor his:manageHisDissentInfors){
                    hisDissentInfors.add(his);
                }
                manageHisDissentInfors=null;
                manageHisDissentInfors=hisDissentInfors;
            }
        }
            for(ManageHisDissentInfor his:manageHisDissentInfors){
                if(SRRPConstant.DISSENT_STATUS_01.equals(his.getDissentStatus())){
                    his.setDissentStatusStr("新增");
                }else  if(SRRPConstant.DISSENT_STATUS_02.equals(his.getDissentStatus())){
                    his.setDissentStatusStr("修改");
                }else if(SRRPConstant.DISSENT_STATUS_03.equals(his.getDissentStatus())){
                    his.setDissentStatusStr("解除");
                    his.setDissentContent(his.getCancelContent());
                    his.setFileName(his.getCancelFileName());
                    his.setFilePath(his.getCancelFilePath());
                }
            }
        request.setAttribute("dissent", manageDissentInfor);
        request.setAttribute("manageHisDissentInfor", net.sf.json.JSONArray.fromObject(manageHisDissentInfors));
        return "WEB-INF/views/managedept/dissentDitailHistory";
    }

    /**
     * 
     * 方法说明：市金融办查看异议记录详情 方法名：dissentDetails
     * 
     * @param request
     * @param response
     * @return 返回值：String
     */
    @RequestMapping(value = "/dissentDetails")
    public String dissentDetails(HttpServletRequest request, HttpServletResponse response) {
        String enterpriseId=request.getParameter("enterpriseId");
        String userId = RedisGetValue.getRedisUser(request, "userId");
        ManageDissentInfor manageDissentInfor=manageDissentService.findDissentBycompanyId(enterpriseId);
        String  baseName="";
        String companyCode="";
        if(SRRPConstant.MESSAGE_TYPE_00.equals(manageDissentInfor.getDissentType())){
            CompanyBase base=companyInfoService.getCompanyBase(manageDissentInfor.getCompanyId());
              baseName=base.getName();
              companyCode=base.getCode();
        }else{
            Investor invest=  investorService.findInverstorById(manageDissentInfor.getCompanyId());
            baseName=invest.getName();
            companyCode=invest.getCertno();
        }
            if(SRRPConstant.DISSENT_STATUS_01.equals(manageDissentInfor.getDissentStatus())){
                manageDissentInfor.setDissentStatusStr("新增");
            }else  if(SRRPConstant.DISSENT_STATUS_02.equals(manageDissentInfor.getDissentStatus())){
                manageDissentInfor.setDissentStatusStr("修改");
            }else if(SRRPConstant.DISSENT_STATUS_03.equals(manageDissentInfor.getDissentStatus())){
                manageDissentInfor.setDissentStatusStr("解除");
            }
        manageDissentInfor.setCompanyName(baseName);
        manageDissentInfor.setCompanyCode(companyCode);
        List<ManageHisDissentInfor> manageHisDissentInfor= manageDissentService.findHisDissentBycompanyId(enterpriseId);
        for(ManageHisDissentInfor his:manageHisDissentInfor){
           
            if(SRRPConstant.DISSENT_STATUS_01.equals(his.getDissentStatus())){
                his.setDissentStatusStr("新增");
            }else  if(SRRPConstant.DISSENT_STATUS_02.equals(his.getDissentStatus())){
                his.setDissentStatusStr("修改");
            }else if(SRRPConstant.DISSENT_STATUS_03.equals(his.getDissentStatus())){
                his.setDissentStatusStr("解除");
                his.setDissentContent(his.getCancelContent());
                his.setFileName(his.getCancelFileName());
                his.setFilePath(his.getCancelFilePath());
            }
        }
        //查看未读消息完之后删除异议提醒
        manageDissentService.deleteByMessageId(enterpriseId,userId,manageDissentInfor.getDissentType());
        request.setAttribute("dissent", manageDissentInfor);
        request.setAttribute("manageHisDissentInfor", net.sf.json.JSONArray.fromObject(manageHisDissentInfor));
        return "WEB-INF/views/managedept/dissentDetails";
    }

    /**
     * 
     * 方法说明：解除异议方法 方法名：canceldissent
     * 
     * @param request
     * @param response
     * @return 返回值：String
     */
    @RequestMapping(value = "/canceldissent")
    public String canceldissent(HttpServletRequest request, HttpServletResponse response) {
        String dissentId=request.getParameter("dissentId");
        ManageDissentInfor manageDissentInfor=manageDissentService.findDissentById(dissentId);
        request.setAttribute("dissent", manageDissentInfor);
        return "WEB-INF/views/managedept/canceldissent";
    }
    /**
     * 
     * 方法说明：解除异议方法 方法名：canceldissent
     * 
     * @param request
     * @param response
     * @return 返回值：String
     */
    @RequestMapping(value = "/updateCancelDissent")
    @ResponseBody

    public void updateCancelDissent(ResultBean rs, HttpServletRequest request, HttpServletResponse response) {
       try {
           String dissentId=request.getParameter("dissentId");
           String cancelContent=request.getParameter("cancelContent");
           String cancelFilePath=request.getParameter("cancelFilePath");
           String cancelFileName=request.getParameter("cancelFileName");
           ManageDissentInfor manageDissentInfor=manageDissentService.findDissentById(dissentId);
           // 保存异议历史记录
           ManageHisDissentInfor manageHisDissentInfor = new ManageHisDissentInfor();
           BeanUtils.copyProperties(manageDissentInfor, manageHisDissentInfor);
           manageDissentService.saveHisDissent(manageHisDissentInfor);
           manageDissentInfor.setCancelContent(cancelContent);
           if(!StringUtils.isEmpty(cancelFilePath)){
               manageDissentInfor.setCancelFilePath(cancelFilePath);
           }
           if(!StringUtils.isEmpty(cancelFileName)){
               manageDissentInfor.setCancelFileName(cancelFileName);
           }
           manageDissentInfor.setOperUpdateDate(new Date());
           manageDissentInfor.setDissentStatus(SRRPConstant.DISSENT_STATUS_03);
           manageDissentService.saveDissent(manageDissentInfor);
         //新增消息提醒记录
           List< MessageAlertInfo> messageAlertInfos =new ArrayList< MessageAlertInfo>();
           List<PlatformUser> users=userService.findSjrbUser();
           String  baseName="";
           if(SRRPConstant.MESSAGE_TYPE_00.equals(manageDissentInfor.getDissentType())){
               CompanyBase base=companyInfoService.getCompanyBase(manageDissentInfor.getCompanyId());
                 baseName=base.getName();
           }else{
               Investor invest=  investorService.findInverstorById(manageDissentInfor.getCompanyId());
               baseName=invest.getName();
           }
           String oper=manageDissentInfor.getOperUserName()+"对"+baseName+"解除了异议";
           for(PlatformUser user:users){
               MessageAlertInfo messageAlertInfo =new MessageAlertInfo();
               messageAlertInfo.setCompanyId(manageDissentInfor.getCompanyId());
               messageAlertInfo.setMeassgeContent(oper);
               messageAlertInfo.setMessageType(manageDissentInfor.getDissentType());//未读
               messageAlertInfo.setMessagEventId(manageDissentInfor.getDissentId());
               messageAlertInfo.setMessagUserId(user.getId());
               messageAlertInfo.setOperDate(new Date());
               messageAlertInfo.setOperUserId(manageDissentInfor.getOperUserId());
               messageAlertInfos.add(messageAlertInfo);
           }
           manageDissentService.saveMessageAlertInfo(messageAlertInfos);
           
           // 将数据传输到前端
           rs = ResultBean.sucessResultBean();
       } catch (Exception e) {
           _log.error(e.getMessage());
           e.printStackTrace();
           rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
       }
       this.writeJson(rs, request, response);
       
    }
    /**
    * 
    * @Title: cancelMessageAll
    * @Description: TODO(全部已读)
    * @author  
    * @date  2018年12月29日 上午10:45:17
     */
    @RequestMapping(value = "/cancelMessageAll")
    @ResponseBody
   public void cancelMessageAll(HttpServletRequest httpRequest){
        try {
            String userId = RedisGetValue.getRedisUser(httpRequest, "userId");
            String type = httpRequest.getParameter("type");
            manageDissentService.deleteByMessageId(userId,type);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
   }
}
