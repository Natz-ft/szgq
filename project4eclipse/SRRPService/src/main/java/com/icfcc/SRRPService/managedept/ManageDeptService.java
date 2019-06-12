package com.icfcc.SRRPService.managedept;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "transactionManager")
public class ManageDeptService {

//    private String unionSQL = "select * from rp_investor a ,rp_investor_publish b where a.investor_id = b.enterprise_id ";
//    @Autowired
//    private ManageDeptDao manageDeptDao;
//
//    @Autowired
//    private ManageDeptDaoImple manageDeptDaoImple;
//
//    /**
//     * 根据机构ID获取机构信息
//     * 
//     * @param id
//     * @return
//     */
//    public InvestorDTO getInvestOrgInfo(String id) {
//        return manageDeptDao.findOne(id);
//    }
//    public Page<ManageDept> getInvestInfosList(Map<String, Object> searchParams, int pageNumber, int pageSize) {
//        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize);
//        Specification<ManageDept> spec = PageUtil.buildSpecification(searchParams, ManageDept.class);
//        return manageDeptDao.findAll(spec, pageRequest);
//    }
//    @SuppressWarnings("unchecked")
//    public <T> List<T> auditInfoQuery(String wherecase) {
//        return (List<T>) manageDeptDaoImple.auditInfoQuery(unionSQL + wherecase,UnionInfos.class);
//    }
    
}
