package com.icfcc.SRRPService.PlatformSystem;




import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.InvestorSubaccount;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.InvestorSubaccountDao;



/**
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 * 常见问题服务
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class InvestorSubaccountService {
	@Autowired
	private InvestorSubaccountDao investorSubaccountDao;
	
	public InvestorSubaccount findById(String id){
		InvestorSubaccount subaccount=investorSubaccountDao.findById(id);
		return subaccount;
	}
	
	   public InvestorSubaccount findByCertNo(String certno){
        InvestorSubaccount subaccount=investorSubaccountDao.findByCertNo(certno);
        return subaccount;
    }
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id){
		investorSubaccountDao.deleteById(id);
	}
	/**
	 * 保存
	 * @param message
	 */
	public void save(InvestorSubaccount subaccount){
		investorSubaccountDao.save(subaccount);
	}
	/**
	 * 修改
	 * @param message
	 */
	public void update(InvestorSubaccount subaccount){
		investorSubaccountDao.save(subaccount);
	}
	
	
	
	
}
