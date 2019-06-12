package com.icfcc.SRRPService.inverstorg;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.base.Splitter;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyListed;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyName;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyNewOtcMarket;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyTag;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBaseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyListedDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyNameDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyNewOtcMarketDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyTagDao;
import com.icfcc.SRRPService.PlatformSystem.PlatformMenuService;
import com.icfcc.SRRPService.PlatformSystem.PlatformRoleService;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.credit.platform.util.MD5;

import org.apache.poi.hssf.usermodel.HSSFCell;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;

import java.io.File;


import java.io.FileNotFoundException;

import java.io.IOException;

import java.text.DecimalFormat;

import java.text.SimpleDateFormat;


import java.util.Arrays;


@Service
@Transactional(value = "transactionManager")
public class TestTagService {
	 @Autowired
	 protected PlatformUserService userService;
	 @Autowired
     protected  CompanyTagDao companyTagDao;
	 @Autowired
     protected CompanyListedDao  companyListedDao;
	 @Autowired
     protected CompanyNameDao  companyNameDao;
	 @Autowired
     protected CompanyBaseDao companyBaseDao;
	 @Autowired
     protected CompanyNewOtcMarketDao companyNewOtcMarketDao;

    public void match(){
	    try {
	        List<CompanyTag> tagList=companyTagDao.findCompanyTag();//查询企业标签库
	        for(CompanyTag tag:tagList){
	            List<CompanyName> companyNameList =companyNameDao.findByName(tag.getCompanyName());//查询与工商企业匹配的企业
	            if(companyNameList.size()>0){
	                 for(CompanyName companyname:companyNameList){
	                     //匹配上的企业 获取历史变更的结果集
	                     List<CompanyName> busCompanyIdList =  companyNameDao.findBybusCompanyId(companyname.getBusCompanyId());
	                     
	                     for(CompanyName busCompanyId:busCompanyIdList){
	                         // 获取历史变更的结果集与 股权企业匹配
	                         List<CompanyBase> listBase=    companyBaseDao.findByName(busCompanyId.getRelatedValue());
	                         if(listBase.size()==1){
	                             for(CompanyBase companyBase:listBase){
	                                 CompanyTag companttag= companyTagDao.findOne(tag.getId());
	                                 companttag.setCompanyId(companyBase.getEnterpriseId());
	                                     companyTagDao.save(companttag);	                             }
	                         }else if(listBase.size()>1){
	                             for(CompanyBase companyBase:listBase){
	                                 if(companyBase.getCodetype().equals("2")){
	                                     CompanyTag companttag= companyTagDao.findOne(tag.getId());
	                                     companttag.setCompanyId(companyBase.getEnterpriseId());
	                                         companyTagDao.save(companttag);	                                 }
	                             }
	                         }
	                     }
	                 }
	            }
	            
	        }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
	    
    }
    public void match4(){
        try {
            List<CompanyTag> tagList=companyTagDao.findCompanyTag();
            for(CompanyTag tag:tagList){
                if(!StringUtils.isEmpty(tag.getCode())){
                    CompanyBase companyBase=    companyBaseDao.queryByCertno(tag.getCode().trim());
                    if(!StringUtils.isEmpty(companyBase)){
                            CompanyTag companttag= companyTagDao.findOne(tag.getId());
                            companttag.setCompanyId(companyBase.getEnterpriseId());
                                companyTagDao.save(companttag);
                    }
                }
               
                
                
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
    }
    public void match2(){
        try {
            List<CompanyTag> tagList=companyTagDao.findCompanyTag();
            for(CompanyTag tag:tagList){
                List<CompanyBase> listBase=    companyBaseDao.findByName(tag.getCompanyName().trim());
                if(listBase.size()==1){
                    for(CompanyBase companyBase:listBase){
                        CompanyTag companttag= companyTagDao.findOne(tag.getId());
                        companttag.setCompanyId(companyBase.getEnterpriseId());
                            companyTagDao.save(companttag);
                    }
                }else if(listBase.size()>1){
                    for(CompanyBase companyBase:listBase){
                        if(companyBase.getCodetype().equals("2")){
                            CompanyTag companttag= companyTagDao.findOne(tag.getId());
                            companttag.setCompanyId(companyBase.getEnterpriseId());
                                companyTagDao.save(companttag);                        }
                    }
                }
                
                
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
    }
	
    public void match3(){
        try {
            List<CompanyTag> tagList=companyTagDao.findCompanyTag2();//查询企业标签库
            for(CompanyTag tag:tagList){
                CompanyBase companyBase=    companyBaseDao.findById(tag.getCompanyId());
                CompanyTag companttag= companyTagDao.findOne(tag.getId());
                if(!StringUtils.isEmpty(companyBase.getRearea())){
                    companttag.setCompanyArea(companyBase.getRearea());
                }
                if(StringUtils.isEmpty(tag.getCode())){
                    companttag.setCode(companyBase.getCode());
                    companttag.setCodetype(companyBase.getCodetype());
                }
                companyTagDao.save(companttag); 
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
    }
	
}
