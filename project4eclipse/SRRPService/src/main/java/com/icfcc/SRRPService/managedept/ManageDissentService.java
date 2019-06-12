package com.icfcc.SRRPService.managedept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.managedept.ManageDissentInfor;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageHisDissentInfor;
import com.icfcc.SRRPDao.jpa.entity.managedept.MessageAlertInfo;
import com.icfcc.SRRPDao.jpa.repository.managedept.ManageDissentInforDao;
import com.icfcc.SRRPDao.jpa.repository.managedept.ManageHisDissentInforDao;
import com.icfcc.SRRPDao.jpa.repository.managedept.MessageAlertInfoDao;

/**
 * @ClassName: ManageDissentService
 * @Description: TODO(主管部门异议功能service层)
 * @author
 * @date 2018年12月25日 下午1:21:45
 */

@Service
@Transactional(value = "transactionManager")
public class ManageDissentService {

    /**
     * @Fields manageDissentInforDao : TODO(异议数据处理层)
     */
    @Autowired
    private ManageDissentInforDao manageDissentInforDao;

    /**
     * @Fields manageHisDissentInforDao : TODO(历史异议数据处理层)
     */
    @Autowired
    private ManageHisDissentInforDao manageHisDissentInforDao;

    /**
     * @Fields messageAlertInfoDao : TODO(消息提醒数据处理层)
     */
    @Autowired
    private MessageAlertInfoDao messageAlertInfoDao;

    /**
     * 
     * @Title: findDissentById
     * @Description: TODO(更具id查询异议信息)
     * @author
     * @date 2018年12月25日 下午1:24:10
     * @param dissentId
     * @return
     */
    public ManageDissentInfor findDissentById(String dissentId) {
        return manageDissentInforDao.findDissentById(dissentId);
    }
    /**
    * 
    * @Title: saveDissent
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @author  
    * @date  2018年12月25日 下午1:43:53
    * @param manageDissentInfor
     */
    public ManageDissentInfor saveDissent(ManageDissentInfor manageDissentInfor) {
        return manageDissentInforDao.save(manageDissentInfor);
    }
    /**
     * 
     * @Title: findDissentBycompanyId
     * @Description: TODO(根据企业id查询异议信息)
     * @author
     * @date 2018年12月25日 下午1:24:15
     * @param companyId
     * @return
     */
    public ManageDissentInfor findDissentBycompanyId(String companyId) {
        return manageDissentInforDao.findDissentBycompanyId(companyId);

    }

    /**
     * 
     * @Title: deleteByDissentId
     * @Description: TODO(根据主键删除异议信息)
     * @author
     * @date 2018年12月25日 下午1:24:19
     * @param dissentId
     */
    public void deleteByDissentId(String dissentId) {
        manageDissentInforDao.deleteByDissentId(dissentId);
    }
    
   
    /**
     * 
     * @Title: findHisDissentById
     * @Description: TODO(查询历史异议记录)
     * @author
     * @date 2018年12月25日 下午1:26:20
     * @param dissentId
     * @return
     */
    public List<ManageHisDissentInfor> findHisDissentById(String dissentId) {
        return manageHisDissentInforDao.findDissentById(dissentId);
    }
    /**
    * 
    * @Title: saveHisDissent
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @author  
    * @date  2018年12月25日 下午1:44:29
    * @param manageHisDissentInfor
     */
    public void saveHisDissent(ManageHisDissentInfor manageHisDissentInfor) {
        manageHisDissentInforDao.save(manageHisDissentInfor);
   }
    /**
     * 
     * @Title: findHisDissentBycompanyId
     * @Description: TODO(查询历史异议记录)
     * @author
     * @date 2018年12月25日 下午1:26:24
     * @param companyId
     * @return
     */
    public List<ManageHisDissentInfor> findHisDissentBycompanyId(String companyId) {
        return manageHisDissentInforDao.findDissentBycompanyId(companyId);
    }
    /**
    * 
    * @Title: findMessageById
    * @Description: TODO(查询提醒消息)
    * @author  
    * @date  2018年12月25日 下午1:28:05
    * @param messagUserId
    * @return
     */
    public List<MessageAlertInfo> findMessageById(String messagUserId) {
        return messageAlertInfoDao.findMessageById(messagUserId);
    }
    /**
    * 
    * @Title: findMessageBycompanyId
    * @Description: TODO(查询提醒消息)
    * @author  
    * @date  2018年12月25日 下午1:28:10
    * @param companyId
    * @return
     */
    public List<MessageAlertInfo> findMessageBycompanyId(String companyId,String type,String userId) {
        return messageAlertInfoDao.findMessageBycompanyId(companyId,type, userId);
    }
    
    public List<MessageAlertInfo> findMessageByMessagType(String userId,String type) {
        return messageAlertInfoDao.findMessageByMessagType(userId,type);
    }
    public List<MessageAlertInfo> findMessageByMessagUserId(String companyId,String userId) {
        return messageAlertInfoDao.findMessageByMessagUserId(companyId,userId);
    }
    /**
    * @Title: saveHisDissent
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @author  
    * @date  2018年12月25日 下午1:45:46
    * @param messageAlertInfo
    */
    public void saveMessageAlertInfo(MessageAlertInfo messageAlertInfo) {
        messageAlertInfoDao.save(messageAlertInfo);
   }
    
    
    public void saveMessageAlertInfo(List< MessageAlertInfo> messageAlertInfos) {
        messageAlertInfoDao.save(messageAlertInfos);
   }
    /**
    * 
    * @Title: deleteByMessageId
    * @Description: TODO(删除已读的消息)
    * @author  
    * @date  2018年12月25日 下午1:29:02
    * @param meassgeId
     */
    public void deleteByMessageId(String companyId,String userId,String type) {
         messageAlertInfoDao.deleteByMessageId(companyId,userId,type);
    }
    /**
     * 
     * @Title: deleteByMessageId
     * @Description: TODO(删除已读的消息)
     * @author  
     * @date  2018年12月25日 下午1:29:02
     * @param meassgeId
      */
     public void deleteByMessageId(String userId,String type) {
          messageAlertInfoDao.deleteByMessageId(userId,type);
     }
}
