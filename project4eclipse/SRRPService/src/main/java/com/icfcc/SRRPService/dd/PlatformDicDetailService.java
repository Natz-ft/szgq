package com.icfcc.SRRPService.dd;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icfcc.SRRPDao.jpa.entity.dd.InstitutionDetail;
import com.icfcc.SRRPDao.jpa.entity.dd.InstitutionType;
import com.icfcc.SRRPDao.jpa.entity.dd.PlatformDicDetail;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.investorDicArea;
import com.icfcc.SRRPDao.jpa.repository.dd.InstitutionDetailDao;
import com.icfcc.SRRPDao.jpa.repository.dd.InstitutionTypeDao;
import com.icfcc.SRRPDao.jpa.repository.dd.PlatformDicDetailDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.InvestorInfoListDaoImpl;
import com.icfcc.ssrp.session.investorArea;

/**
 * <字典>
 */
@Service
public class PlatformDicDetailService {
    private static Logger log = LoggerFactory.getLogger(PlatformDicDetailService.class);

    @Autowired
    private PlatformDicDetailDao dao;
    @Autowired
    private InstitutionTypeDao typeDao;
    @Autowired
    private InstitutionDetailDao detailDao;
    @Autowired
	private InvestorInfoListDaoImpl inverstorListDaoImpml;
    public List<InstitutionType> findInstitution() {
        List<InstitutionType> dataList = null;
        try {
            dataList = (List<InstitutionType>) typeDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }
    public List<InstitutionDetail> findByType(String type) {
        List<InstitutionDetail> dataList = null;
        try {
            dataList =  detailDao.findByType(type);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }
    public List<PlatformDicDetail> findDDInfos() {
        List<PlatformDicDetail> dataList = null;
        try {
            dataList = dao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }
    public void save(List<PlatformDicDetail> de) {
    	dao.save(de);
    }
    public List<investorArea>  getAreaDate(String level){
		List<investorDicArea> dicArea=inverstorListDaoImpml.getAreaDate(level);
		List<investorArea> areaList=new ArrayList<investorArea>();
		for(investorDicArea dd:dicArea){
			investorArea area=new investorArea();
			area.setCode(dd.getCode());
			area.setLevel(dd.getLevel());
			area.setName(dd.getName());
			area.setParentcode(dd.getParentcode());
			areaList.add(area);
		}
		return areaList;
		
	}
    public investorArea  getAreaDateByCode(String code){
	     investorArea InvestorArea=new investorArea();
	     InvestorArea.setAreaCity(code);
		 List<investorDicArea> dicArea=inverstorListDaoImpml.getAreaDateByCode(code,"1");
		 investorDicArea area=dicArea.get(0);
	     InvestorArea.setAreaProvince(area.getParentcode());
		return InvestorArea;
		
	}
    //苏州市内
    public investorArea  getSuzhouAreaDateByCode(String code){
	     investorArea InvestorArea=new investorArea();
	     InvestorArea.setAreaCounty(code);
	     List<investorDicArea> dicArea=inverstorListDaoImpml.getAreaDateByCode(code,"1");
		 investorDicArea area=dicArea.get(0);
	     InvestorArea.setAreaCity(area.getParentcode());
	     List<investorDicArea> dicArea1=inverstorListDaoImpml.getAreaDateByCode(area.getParentcode(),"1");
		 investorDicArea area1=dicArea1.get(0);
	     InvestorArea.setAreaProvince(area1.getParentcode());
		return InvestorArea;
		
	}
    public List<investorDicArea>  getAreaDateByParnetCode(String code){
		 List<investorDicArea> dicArea=inverstorListDaoImpml.getAreaDateByCode(code,"2");
		return dicArea;
		
	}
}
