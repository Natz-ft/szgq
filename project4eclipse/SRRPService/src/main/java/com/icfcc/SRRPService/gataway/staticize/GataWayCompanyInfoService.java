package com.icfcc.SRRPService.gataway.staticize;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBaseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformUserDao;
import com.icfcc.SRRPService.PlatformSystem.PlatformConfigService;

/**
 * <门户静态化投资动态>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayCompanyInfoService extends GataWayBaseStaticzeService {
    private static Logger log = LoggerFactory.getLogger(GataWayCompanyInfoService.class);

    @Autowired
    public CompanyBaseDao compdao;
    @Autowired
	private  PlatformConfigService config;
	// 投资机构注册业务库正式表
	@Autowired
	private InvestorDao investorDao;
	 @Autowired
		private PlatformUserDao userDao;
    public boolean ifRegistered(String centno) {
    	Long s = compdao.countByCertno(centno);
    	if(s>0) {
    		return false;
    	}else {
    		return true;
    	}
    }
    
    public void insertBase(CompanyBase baseInfo) {
    	compdao.save(baseInfo);
    }
    public void deleteByCode(String code) {
    	compdao.deleteByCode(code);
    }
    
    public CompanyBase queryByCertno(String contno) {
		CompanyBase baseInfo = compdao.queryByCertno(contno);
		return baseInfo;
	}
    
    

	// 判定是否注册过业务库
	public boolean ifRegisteredRp(String certNo) {
		boolean isExist = false;
		CompanyBase baseInfo  = queryByCertno(certNo);
		if (baseInfo != null) {
			isExist = true;
		}
		return isExist;
	}
	// 判定是否注册过业务库
		public boolean ifRegisteredRC(String certNo) {
			boolean isExist = false;
			CompanyBase baseInfo = compdao.queryByCertno(certNo);
			Investor registerInfo = investorDao.findById(certNo);
			PlatformUser user=userDao.findByUserName(certNo);
			if (baseInfo != null ||registerInfo != null || user!=null) {
				isExist = true;
			}
			return isExist;
		}
    public String [] keys(String flag) {
		String []params =new String[3];
	    String appid = (config.getConfigValueByName("appid"));
	    String appkey = config.getConfigValueByName("appkey");
	    params[0]=appid;
	    params[1]=appkey;
	    if("1".equals(flag) || "2".equals(flag)){//注册成功
	    	params[2] = (config.getConfigValueByName("register"));
		}else if("3".equals(flag) || "4".equals(flag) ){//审核通过
			params[2] = (config.getConfigValueByName("pass"));
		}else if("5".equals(flag) || "6".equals(flag)){//审核不通过
			params[2] = (config.getConfigValueByName("noPass"));
		}else if("16".equals(flag)){
			params[2] = (config.getConfigValueByName("noUpdatePass"));
		}else if("7".equals(flag)){
			params[2] = (config.getConfigValueByName("onlineAnswer"));
		}else if("01".equals(flag)){
			params[2] = (config.getConfigValueByName("IsOpen"));
		}else if("02".equals(flag)){
			params[2] = (config.getConfigValueByName("isInvestPush"));
		}else if("03".equals(flag)){
			params[2] = (config.getConfigValueByName("isInvestFundPush"));
		}else if("04".equals(flag)){
			params[2] = (config.getConfigValueByName("focusBefoExprice"));
		}else if("05".equals(flag)){
			params[2] = (config.getConfigValueByName("focusAfterExprice"));
		}else if("06".equals(flag)){
			params[2] = (config.getConfigValueByName("talkExpriceOne"));
		}else if("07".equals(flag)){
			params[2] = (config.getConfigValueByName("talkExpriceTwo"));
		}else if("08".equals(flag)){
			params[2] = (config.getConfigValueByName("answerOpen"));
		}else if("09".equals(flag)){
			params[2] = (config.getConfigValueByName("answerInvesting"));
		}else if("010".equals(flag)){
			params[2] = (config.getConfigValueByName("answerError"));
		}
	    return params;
	}

	
    
}
