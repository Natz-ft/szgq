package com.icfcc.ws;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.icfcc.SRRPDao.entity.PlatformUser;

/** 
 * 用户服务接口 
 * @author Ickes 
 */  
public interface PushUserRegistInfor {
	
	 /** 
     * 测试GET方法，传人对象、普通参数；返回对象 
     *  
     */  
    @GET  
    @Path("/getCompanyInfo")  
    @Description(value = "股权平台新增企业信息接口", target = DocTarget.METHOD)
    @Produces({MediaType.APPLICATION_JSON})  
    public  String getCompanyInfo(@QueryParam("companyInfoJsonStr") String companyInfoJsonStr);  
      
    @GET  
    @Path("/updateCompanyInfo")  
    @Description(value = "股权平台更新企业信息接口", target = DocTarget.METHOD)
    @Produces({MediaType.APPLICATION_JSON})  
    public  String updateCompanyInfo(@QueryParam("companyInfoJsonStr") String companyInfoJsonStr); 
    
    @GET  
    @Path("/webSzyhWSClient")  
    @Description(value = "调用苏州金服接口", target = DocTarget.METHOD)
    @Produces({MediaType.APPLICATION_JSON})  
    public  String webSzyhWSClient(@QueryParam("companyInfoJsonStr") String companyInfoJsonStr,@QueryParam("type") String type);  
      
   
    
}
