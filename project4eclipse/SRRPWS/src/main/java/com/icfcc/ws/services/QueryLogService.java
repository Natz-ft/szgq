package com.icfcc.ws.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.entity.CompanyBase;
import com.icfcc.SRRPDao.entity.CompanyInfoVo;
import com.icfcc.SRRPDao.entity.CompanyStockholder;
import com.icfcc.SRRPDao.entity.PlatformUser;
import com.icfcc.SRRPDao.entity.QueryLog;
import com.icfcc.SRRPDao.repository.CompanyBaseDao;
import com.icfcc.SRRPDao.repository.CompanyStockholderDao;
import com.icfcc.SRRPDao.repository.PlatformUserDao;
import com.icfcc.SRRPDao.repository.QueryLogDAO;

@Service
@Transactional(value = "transactionManager")
public class QueryLogService {

	
	@Autowired
	private QueryLogDAO querylogDAO;
	@Autowired
    private CompanyBaseDao companyBaseDao;
	
	@Autowired
    public PlatformUserDao platformuserdao;
	
	@Autowired
    private CompanyStockholderDao companyStockholderDao;
	
	public void addquerylog(QueryLog querylog){
		querylogDAO.save(querylog);
	}
	public CompanyBase queryByCertno(String code){
	   return companyBaseDao.queryByCertno(code);
    }
	
	public CompanyBase findById(String enterpriseId){
	       return companyBaseDao.findById (enterpriseId);
	    }
	public PlatformUser findByUserName(String username){
	    return platformuserdao.findByUserName(username);
	};
	public void saveUser(PlatformUser user){
         platformuserdao.save(user);
    };
    public CompanyBase saveCompanyBaseDao(CompanyBase base){
        return companyBaseDao.save(base);
   };
   public void deleteCompanyStockById(String enterpriseId){
       companyStockholderDao.deleteById(enterpriseId);
  };
  public void addCompanyStockholder(CompanyStockholder companyStockholder) {

      companyStockholderDao.save(companyStockholder);
  }
  public void saveEnterprise(CompanyBase companyBase) {
      companyBaseDao.save(companyBase);
  }
  
  public Map<Object, Object>  saveCompanyInfo(CompanyInfoVo companyInfo ) {
      String validStatus = "1";
      Map<Object, Object> map = new HashMap<Object, Object>();
      try{
          CompanyBase base = new CompanyBase();
          base.setCode(companyInfo.getCode());
          if (!base.getCode().equals("") && base.getCode() != null) {
              if (base.getCode().length() == 18) {
                  base.setCodetype("2");
              } else {
                  base.setCodetype("1");
              }
  
          }
          base.setName(companyInfo.getName());
          base.setLegalCal(companyInfo.getLegalcal());
          base.setLegalName(companyInfo.getLegalname());
          base.setContacName(companyInfo.getContacname());
          base.setContacCal(companyInfo.getContaccal());
          base.setRearea(companyInfo.getBranchno());
          base.setOperdate(new Date());
          base.setCreateTime(new Date());
          base.setPlatformFlag("01");
          base.setSources("isNo");
          base.setAuditStatus("22");
          CompanyBase base1 =saveCompanyBaseDao(base);
          map.put("enterpriseId", base1.getEnterpriseId());
      } catch (Exception e) {
          e.printStackTrace();
          validStatus = "0";
      }
      map.put("validStatus", validStatus);
      return map ;
  }

}
